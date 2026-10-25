// 단독 ESM 번들 `npm run build:navigation` → dist/sayaya-ui-navigation.js (Release 첨부물 sayaya-ui-navigation-<version>.js 의 원본)
// md-top-app-bar, md-navigation-rail(-item), md-navigation-bar, md-navigation-tab 만 정의한다(@material/web 컴포넌트 미포함). lit 은 함께 묶는다.
// 앱이 이미 Material Web 을 따로 번들해 싣는 경우를 위한 것으로, 각 define 은 '이미 정의돼 있으면 건너뜀'이다.
import {nodeResolve} from '@rollup/plugin-node-resolve';
import terser from '@rollup/plugin-terser';
import {readFileSync} from 'node:fs';

const lit = JSON.parse(readFileSync('node_modules/lit/package.json', 'utf8')).version;
const version = process.env.SAYAYA_UI_VERSION || 'dev';
const commit = process.env.GITHUB_SHA || 'unknown';

export default {
  input: 'src/sayaya-navigation-standalone.js',
  output: {
    file: 'dist/sayaya-ui-navigation.js',
    format: 'es',
    banner: `/*! sayaya-ui ${version} (${commit}) navigation ESM bundle: md-top-app-bar, md-navigation-rail, md-navigation-rail-item, md-navigation-bar, md-navigation-tab (+ lit ${lit}). ` +
      'Requires md-icon-button from the app for the rail menu button. Defines are skipped when the name is already registered. License: lit (BSD-3-Clause). */'
  },
  plugins: [nodeResolve(), terser({format: {comments: /^!/}})]
};
