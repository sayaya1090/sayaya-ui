// 단일 classic-script 번들(IIFE, CDN 불필요)용 설정. `npm run build:standalone` → dist/sayaya-ui.js
// 1) @material/web/all.js 를 IIFE 로, 2) src/index.js(labs + sayaya-ui 자체 구현)를 기존 플러그인 그대로 IIFE 로 빌드하고
// standalone.mjs 가 이 순서로 이어 붙인다. all.js 를 따로 빌드하는 이유: remove-elevation 플러그인이 all.js 의 md-elevation 까지 지우지 않도록.
import {nodeResolve} from '@rollup/plugin-node-resolve';
import terser from '@rollup/plugin-terser';
import labs from './rollup.config.js';

export default [
  {
    input: '@material/web/all.js',
    output: {file: 'dist/parts/material-web-all.iife.js', format: 'iife'},
    plugins: [nodeResolve(), terser()]
  },
  {
    input: labs.input,
    output: {file: 'dist/parts/sayaya-labs.iife.js', format: 'iife', intro: labs.output.intro, outro: labs.output.outro},
    plugins: labs.plugins
  }
];
