// 단독 ESM 번들 `npm run build:navigation` → dist/sayaya-ui-navigation.js (Release 첨부물 sayaya-ui-navigation-<version>.js 의 원본)
// md-top-app-bar, md-navigation-rail(-item), md-navigation-bar, md-navigation-tab 만 정의한다(@material/web 컴포넌트 미포함). 공통 설정은 rollup.esm-bundle.js
import {esmBundle} from './rollup.esm-bundle.js';

export default esmBundle({
  input: 'src/sayaya-navigation-standalone.js',
  file: 'dist/sayaya-ui-navigation.js',
  describe: (lit) => `navigation ESM bundle: md-top-app-bar, md-navigation-rail, md-navigation-rail-item, md-navigation-bar, md-navigation-tab (+ lit ${lit})`,
  note: 'Requires md-icon-button from the app for the rail menu button. ',
});
