import {nodeResolve} from '@rollup/plugin-node-resolve';
import terser from '@rollup/plugin-terser';

export default {
  input: 'src/index.js',
  output: {
    file: '../labs.bundle.js',
    format: 'es',
    sourcemap: true
  },
  plugins: [
    {
      name: 'remove-elevation',
      resolveId(source) {
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
