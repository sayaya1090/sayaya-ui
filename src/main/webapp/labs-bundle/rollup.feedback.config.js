// 단독 ESM 번들 `npm run build:feedback` → dist/sayaya-ui-feedback.js (Release 첨부물 sayaya-ui-feedback-<version>.js 의 원본)
// md-tooltip, md-snackbar, md-toast 만 정의한다(@material/web 컴포넌트·md-button-group 은 넣지 않음). 공통 설정은 rollup.esm-bundle.js
import {esmBundle} from './rollup.esm-bundle.js';

export default esmBundle({
  input: 'src/sayaya-feedback.js',
  file: 'dist/sayaya-ui-feedback.js',
  describe: (lit) => `feedback ESM bundle: md-tooltip, md-snackbar, md-toast (+ lit ${lit})`,
});
