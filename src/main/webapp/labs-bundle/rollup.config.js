import {nodeResolve} from '@rollup/plugin-node-resolve';
import terser from '@rollup/plugin-terser';
import {dirname, resolve} from 'node:path';

// labs/gb 컴포넌트는 CSS module script(`import x from './a.css' with {type: 'css'}`)를 쓴다.
// rollup은 CSS를 파싱하지 못하므로, 같은 위치에 함께 배포되는 `.cssresult.js`
// (default export = CSSStyleSheet, 패키지의 google3-only 대체 import와 동일)로 연결한다.
const isGb = (id) => !!id && id.includes('/labs/gb/');

export default {
  input: 'src/index.js',
  output: {
    file: '../labs.bundle.js',
    format: 'es',
    sourcemap: true
  },
  plugins: [
    {
      name: 'css-module-to-cssresult',
      resolveId(source, importer) {
        if (importer && importer.includes('/@material/web/') && source.endsWith('.css')) {
          return resolve(dirname(importer), source.replace(/\.css$/, '.cssresult.js'));
        }
        return null;
      },
      transform(code, id) {
        // `.cssresult.js`는 일반 JS 모듈이므로 CSS import attribute를 제거
        if (!id.includes('/@material/web/') || !code.includes("type: 'css'") && !code.includes("type:'css'")) return null;
        return {code: code.replace(/(\.css['"])\s*with\s*\{\s*type:\s*['"]css['"]\s*\}/g, '$1'), map: null};
      }
    },
    {
      name: 'remove-elevation',
      resolveId(source, importer) {
        // gb(Expressive) 컴포넌트는 자체 elevation 토큰이 필요하므로 제외
        if (isGb(importer)) return null;
        // elevation 관련 모듈은 빈 모듈로 대체
        if (source.includes('/elevation/') || source.includes('elevation.js')) {
          return '\0empty-elevation';
        }
        return null;
      },
      load(id) {
        // 빈 모듈 제공
        if (id === '\0empty-elevation') {
          return 'export default {};';
        }
        return null;
      },
      transform(code, id) {
        if (isGb(id)) return null;
        // elevation import 구문 제거
        if (code.includes('/elevation/')) {
          code = code.replace(/import\s+['"][^'"]*\/elevation\/[^'"]*['"]\s*;?/g, '');
          code = code.replace(/import\s+.*\s+from\s+['"][^'"]*\/elevation\/[^'"]*['"]\s*;?/g, '');
        }
        // customElements.define('md-elevation', ...) 호출 제거
        if (code.includes("'md-elevation'") || code.includes('"md-elevation"')) {
          code = code.replace(/customElements\.define\s*\(\s*['"]md-elevation['"]\s*,\s*[^)]+\)\s*;?/g, '// elevation define removed');
        }
        return { code, map: null };
      }
    },
    nodeResolve(),
    terser()
  ]
};
