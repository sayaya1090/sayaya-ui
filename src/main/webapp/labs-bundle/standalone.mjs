// dist/parts/*.iife.js 를 all.js → labs 순서로 이어 붙여 dist/sayaya-ui.js 를 만든다.
// 버전·커밋은 환경 변수 SAYAYA_UI_VERSION / GITHUB_SHA(없으면 'dev' / 'unknown')로 헤더에 남긴다.
import {readFileSync, writeFileSync} from 'node:fs';

const mw = JSON.parse(readFileSync('node_modules/@material/web/package.json', 'utf8')).version;
const version = process.env.SAYAYA_UI_VERSION || 'dev';
const commit = process.env.GITHUB_SHA || 'unknown';
const header = `/*! sayaya-ui ${version} (${commit}) standalone bundle: @material/web ${mw} all.js + sayaya-ui labs. ` +
  'IIFE classic script, load once before the GWT app. Licenses: @material/web, lit (Apache-2.0 / BSD-3-Clause). */\n';
const parts = ['dist/parts/material-web-all.iife.js', 'dist/parts/sayaya-labs.iife.js'].map((f) => readFileSync(f, 'utf8').trimEnd());
writeFileSync('dist/sayaya-ui.js', header + parts.join(';\n') + '\n');
console.log(`dist/sayaya-ui.js written (sayaya-ui ${version}, @material/web ${mw})`);
