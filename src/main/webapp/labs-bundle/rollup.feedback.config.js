// 단독 ESM 번들 `npm run build:feedback` → dist/sayaya-ui-feedback.js (Release 첨부물 sayaya-ui-feedback-<version>.js 의 원본)
// md-tooltip, md-snackbar, md-toast 만 정의한다(@material/web 컴포넌트·md-button-group 은 넣지 않음). lit 은 함께 묶는다.
// 앱이 이미 Material Web 을 따로 번들해 싣는 경우를 위한 것으로, 각 define 은 '이미 정의돼 있으면 건너뜀'이다.
import {nodeResolve} from '@rollup/plugin-node-resolve';
import terser from '@rollup/plugin-terser';
import {readFileSync} from 'node:fs';

const lit = JSON.parse(readFileSync('node_modules/lit/package.json', 'utf8')).version;
const version = process.env.SAYAYA_UI_VERSION || 'dev';
const commit = process.env.GITHUB_SHA || 'unknown';

export default {
  input: 'src/sayaya-feedback.js',
  output: {
    file: 'dist/sayaya-ui-feedback.js',
    format: 'es',
    banner: `/*! sayaya-ui ${version} (${commit}) feedback ESM bundle: md-tooltip, md-snackbar, md-toast (+ lit ${lit}). ` +
      'Defines are skipped when the name is already registered. License: lit (BSD-3-Clause). */'
  },
  plugins: [nodeResolve(), terser({format: {comments: /^!/}})]
};
