// 단독 ESM 번들(feedback·navigation) rollup 설정 공통 부분. 각 설정은 입력·출력 파일과 배너 설명만 다르다.
// 앱이 이미 Material Web 을 따로 번들해 싣는 경우를 위한 것으로, 각 define 은 '이미 정의돼 있으면 건너뜀'이다. lit 은 함께 묶는다.
import {nodeResolve} from '@rollup/plugin-node-resolve';
import terser from '@rollup/plugin-terser';
import {readFileSync} from 'node:fs';

const lit = JSON.parse(readFileSync('node_modules/lit/package.json', 'utf8')).version;
const version = process.env.SAYAYA_UI_VERSION || 'dev';
const commit = process.env.GITHUB_SHA || 'unknown';

/** describe(lit) → 배너의 '<이름> ESM bundle: …' 부분. note → 라이선스 앞에 붙는 안내 문장(없으면 '') */
export const esmBundle = ({input, file, describe, note = ''}) => ({
  input,
  output: {
    file,
    format: 'es',
    banner: `/*! sayaya-ui ${version} (${commit}) ${describe(lit)}. ` +
      `${note}Defines are skipped when the name is already registered. License: lit (BSD-3-Clause). */`
  },
  plugins: [nodeResolve(), terser({format: {comments: /^!/}})]
});
