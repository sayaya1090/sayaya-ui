// Material Web Labs 컴포넌트 번들
// all.js와 함께 사용하기 위한 Labs 컴포넌트들
// elevation 충돌을 피하기 위해 all.js가 먼저 로드되어야 함

import '@material/web/labs/card/elevated-card.js';
import '@material/web/labs/card/filled-card.js';
import '@material/web/labs/card/outlined-card.js';
import '@material/web/labs/badge/badge.js';

// 향후 추가 가능한 Labs 컴포넌트
// import '@material/web/labs/navigationbar/navigation-bar.js';
// import '@material/web/labs/navigationdrawer/navigation-drawer.js';

// Material Web 2.5.0 labs/gb (Expressive) 컴포넌트
import '@material/web/labs/gb/components/badge/md-gb-badge.js';
import '@material/web/labs/gb/components/button/md-gb-button.js';
import '@material/web/labs/gb/components/card/md-gb-card.js';
import '@material/web/labs/gb/components/checkbox/md-gb-checkbox.js';
import '@material/web/labs/gb/components/divider/md-gb-divider.js';
import '@material/web/labs/gb/components/fab/md-gb-fab.js';
import '@material/web/labs/gb/components/iconbutton/md-gb-icon-button.js';
import '@material/web/labs/gb/components/list/md-gb-list.js';
import '@material/web/labs/gb/components/list/md-gb-list-item.js';
import '@material/web/labs/gb/components/menu/md-gb-menu.js';
import '@material/web/labs/gb/components/menu/md-gb-menu-group.js';
import '@material/web/labs/gb/components/menu/md-gb-menu-item.js';
import '@material/web/labs/gb/components/radio/md-gb-radio.js';
import '@material/web/labs/gb/components/splitbutton/md-gb-split-button.js';
import '@material/web/labs/gb/components/switch/md-gb-switch.js';
import '@material/web/labs/gb/styles/icon/md-gb-icon.js';

// Material Web labs navigation drawer
import '@material/web/labs/navigationdrawer/navigation-drawer.js';
import '@material/web/labs/navigationdrawer/navigation-drawer-modal.js';

// sayaya-ui 자체 구현: top app bar, navigation rail
import './sayaya-navigation.js';
// sayaya-ui 자체 구현: M3 flexible navigation bar (labs baseline 대체)
import './sayaya-navbar.js';

// sayaya-ui 자체 구현: tooltip, snackbar, button group
import './sayaya-feedback.js';

// sayaya-ui 자체 구현: side sheet
import './sayaya-sheet.js';

// md-gb-icon 은 --md-icon-font 기본값이 "Material Symbols" 라서, 일반적으로 로드하는
// "Material Symbols Outlined" 폰트와 이름이 달라 아이콘 대신 글자가 보인다. md-icon 과 같은 폰트를 쓰도록 맞춘다.
// (@property --md-icon-font 는 <string># 목록·상속을 허용하므로 :root 에 한 번 지정하면 된다. 앱에서 재지정 가능)
{
  const iconFont = new CSSStyleSheet();
  iconFont.replaceSync(':root { --md-icon-font: "Material Symbols Outlined", "Material Symbols"; }');
  document.adoptedStyleSheets = [iconFont, ...document.adoptedStyleSheets];
}
