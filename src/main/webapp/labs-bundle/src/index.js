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

// Material Web labs navigation (bar/tab/drawer)
import '@material/web/labs/navigationbar/navigation-bar.js';
import '@material/web/labs/navigationtab/navigation-tab.js';
import '@material/web/labs/navigationdrawer/navigation-drawer.js';
import '@material/web/labs/navigationdrawer/navigation-drawer-modal.js';

// sayaya-ui 자체 구현: top app bar, navigation rail
import './sayaya-navigation.js';
