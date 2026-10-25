// 단독 navigation ESM 번들(sayaya-ui-navigation-<version>.js)의 진입점.
// md-top-app-bar, md-navigation-rail, md-navigation-rail-item, md-navigation-bar, md-navigation-tab 만 정의한다(lit 포함).
// 각 정의는 이름이 이미 등록돼 있으면 건너뛴다. @material/web 컴포넌트는 넣지 않는다:
// 레일의 내장 메뉴 버튼(menu=true)은 md-icon-button 을 쓰므로 앱이 Material Web icon-button 을 따로 실어야 한다.
// md-navigation-drawer(-modal) 은 Material Web labs 요소라 넣지 않는다(내부 요소를 가드 없이 정의해 앱 번들과 겹칠 수 있음).
import './sayaya-navigation.js';
import './sayaya-navbar.js';
