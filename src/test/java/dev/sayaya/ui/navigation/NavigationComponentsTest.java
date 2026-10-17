package dev.sayaya.ui.navigation;

import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLElement;
import elemental2.dom.ShadowRoot;
import jsinterop.base.Js;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.IconElementBuilder.icon;
import static dev.sayaya.ui.elements.NavigationElementBuilder.*;
import static org.jboss.elemento.Elements.body;
import static org.jboss.elemento.Elements.div;

/** M3 네비게이션 7종 검증: 즉시(태그/속성) → 1.5초 후(업그레이드·동작) → 클릭 후(이벤트). */
public class NavigationComponentsTest {
    public static void test() {
        printSectionHeader("M3 Navigation (top app bar / navigation bar / rail / drawer)");
        printDescription("화면 간 이동을 담당하는 M3 네비게이션 컴포넌트입니다. top app bar·navigation rail·navigation bar 는 sayaya-ui 가 직접 구현했고(M3 토큰 기준), navigation drawer 는 Material Web labs 를 래핑합니다.");
        printSeparator();
        var section = section("Navigation");

        var appBar = topAppBar().variant(TopAppBarVariant.Medium).headline("문서")
                .leading(icon("menu")).trailing(icon("more_vert")).scrolled(true).element();
        check(appBar, "md-top-app-bar", "medium".equals(appBar.variant) && appBar.scrolled
                && appBar.querySelector("[slot='leading']") != null && appBar.querySelector("[slot='trailing']") != null
                && "문서".equals(appBar.querySelector("[slot='expanded-headline']").textContent));
        demo(section, "📘 Top app bar", "앞/뒤 액션과 제목을 가진 상단 바입니다. variant(small/center-aligned/medium/large), 스크롤 시 표면색 전환(autoScroll/scrolled)을 지원합니다.",
            """
            var appBar = topAppBar().variant(TopAppBarVariant.Medium).headline("문서")
                .leading(icon("menu")).trailing(icon("more_vert")).scrolled(true)
                .element();
            """, div().style("width", "100%").add(appBar).element());

        int[] barEvent = {-1};
        var tab2 = navigationTab().label("검색").icon("search").activeIcon("search").badge("3");
        var bar = navigationBar().activeIndex(0)
                .tab(navigationTab().label("홈").icon("home"))
                .tab(tab2)
                .tab(navigationTab().label("설정").icon("settings").disabled(true))
                .onActivated(i -> barEvent[0] = i).element();
        var tab2El = tab2.element();
        check(bar, "md-navigation-bar", bar.activeIndex == 0 && bar.childElementCount == 3);
        check(tab2El, "md-navigation-tab", "검색".equals(tab2El.label) && tab2El.showBadge && "3".equals(tab2El.badgeValue)
                && tab2El.querySelector("[slot='active-icon']") != null);
        demo(section, "📘 Navigation bar", "compact 화면 하단의 목적지 3~5개. 탭을 누르면 activeIndex 가 바뀌고 onActivated 가 호출됩니다. badge(\"3\")는 숫자 배지, badge()는 점 배지입니다.",
            """
            var bar = navigationBar().activeIndex(0)
                .tab(navigationTab().label("홈").icon("home"))
                .tab(navigationTab().label("검색").icon("search").activeIcon("search").badge("3"))
                .tab(navigationTab().label("설정").icon("settings").disabled(true))
                .onActivated(index -> route(index))
                .element();
            """, div().style("width", "400px").add(bar).element());
        // flexible navigation bar 토큰 대조용: 강제 세로형/가로형, auto(좁은/넓은 컨테이너)
        var vBar = navigationBar().layout(NavigationBarLayout.Vertical)
                .tab(navigationTab().label("홈").icon("home").badge("12")).tab(navigationTab().label("알림").icon("notifications").badge())
                .tab(navigationTab().label("설정").icon("settings")).element();
        var hBar = navigationBar().layout(NavigationBarLayout.Horizontal)
                .tab(navigationTab().label("홈").icon("home")).tab(navigationTab().label("검색").icon("search")).tab(navigationTab().label("설정").icon("settings")).element();
        var narrow = navigationBar().tab(navigationTab().label("A").icon("home")).tab(navigationTab().label("B").icon("search")).element();
        var wide = navigationBar().tab(navigationTab().label("A").icon("home")).tab(navigationTab().label("B").icon("search")).element();
        demo(section, "📘 Navigation bar - 세로형 항목", "compact 창의 기본 배치입니다. 높이 64dp, 항목은 같은 너비로 나뉘고 indicator 는 56×32dp 입니다.",
            """
            navigationBar().layout(NavigationBarLayout.Vertical)
                .tab(navigationTab().label("홈").icon("home").badge("12"))
                .tab(navigationTab().label("알림").icon("notifications").badge())
                .tab(navigationTab().label("설정").icon("settings"))
                .element();
            """, div().style("width", "400px").add(vBar).element());
        demo(section, "📘 Navigation bar - 가로형 항목", "medium 창(600dp 이상) 배치입니다. 아이콘과 라벨이 높이 40dp indicator 안에 나란히 놓이고, 항목들은 가운데로 모입니다.",
            """
            navigationBar().layout(NavigationBarLayout.Horizontal)
                .tab(navigationTab().label("홈").icon("home"))
                .tab(navigationTab().label("검색").icon("search"))
                .tab(navigationTab().label("설정").icon("settings"))
                .element();
            """, div().style("width", "700px").add(hBar).element());
        demo(section, "📘 Navigation bar - 자동 배치 (400px)", "layout 을 지정하지 않으면(Auto) 바 너비 600dp 미만에서는 세로형으로 배치됩니다.",
            "navigationBar().tab(...).tab(...).element();  // layout 기본값 Auto", div().style("width", "400px").add(narrow).element());
        demo(section, "📘 Navigation bar - 자동 배치 (700px)", "같은 코드라도 바 너비가 600dp 이상이면 가로형으로 바뀝니다.",
            "navigationBar().tab(...).tab(...).element();  // layout 기본값 Auto", div().style("width", "700px").add(wide).element());

        int[] railEvent = {-1};
        var item2 = railItem().icon("inbox").label("받은편지함").badge();
        var rail = navigationRail().alignment(RailAlignment.Center).expanded(true)
                .header(icon("menu"))
                .item(railItem().icon("home").label("홈"))
                .item(item2)
                .onActivated(i -> railEvent[0] = i).element();
        var item2El = item2.element();
        check(rail, "md-navigation-rail", "center".equals(rail.alignment) && rail.expanded && rail.querySelector("[slot='header']") != null);
        check(item2El, "md-navigation-rail-item", "받은편지함".equals(item2El.label) && item2El.showBadge && "".equals(item2El.badgeValue));
        demo(section, "📘 Navigation rail", "medium 이상 화면의 세로 네비게이션입니다. expanded(false)는 80dp 아이콘 기둥, expanded(true)는 라벨이 옆에 붙는 펼친 상태입니다. header 에는 메뉴 버튼이나 FAB 을 둡니다.",
            """
            var rail = navigationRail().alignment(RailAlignment.Center).expanded(true)
                .header(icon("menu"))
                .item(railItem().icon("home").label("홈"))
                .item(railItem().icon("inbox").label("받은편지함").badge())
                .onActivated(index -> route(index))
                .element();
            """, div().style("height", "400px").add(rail).element());

        var drawer = navigationDrawer().opened(true).pivot(DrawerPivot.Start).add(div().text("메뉴")).element();
        check(drawer, "md-navigation-drawer", drawer.opened && "start".equals(drawer.pivot));
        demo(section, "📘 Navigation drawer", "화면 옆에 고정되는 목적지 목록입니다(Material Web labs). opened 로 열고 닫으며, pivot 으로 붙는 쪽을 정합니다.",
            """
            var drawer = navigationDrawer().opened(true).pivot(DrawerPivot.Start)
                .add(div().text("메뉴"))
                .element();
            """, drawer);
        var modal = navigationDrawerModal().opened(false).pivot(DrawerPivot.End).element();
        check(modal, "md-navigation-drawer-modal", !modal.opened && "end".equals(modal.pivot));
        var openModal = div().text("모달 drawer 열기/닫기").style("cursor", "pointer").style("color", "#007bff").element();
        openModal.addEventListener("click", e -> modal.opened = !modal.opened);
        body().add(modal);
        demo(section, "📘 Navigation drawer - 모달", "compact 화면에서 스크림 위에 열리는 drawer 입니다. 데모의 링크를 눌러 열고 닫을 수 있습니다.",
            """
            var modal = navigationDrawerModal().opened(false).pivot(DrawerPivot.End).element();
            modal.opened = true;  // 열기
            """, openModal);

        DomGlobal.setTimeout(t1 -> {
            for (HTMLElement el : new HTMLElement[]{appBar, bar, tab2El, rail, item2El, drawer, modal})
                assertTrue("nav: " + el.tagName.toLowerCase() + " 업그레이드", shadow(el) != null);
            assertTrue("nav: md-top-app-bar scrolled 속성 반영", appBar.hasAttribute("scrolled"));
            assertTrue("nav: md-navigation-rail expanded 항목 전파", item2El.expanded && item2El.hasAttribute("expanded"));
            assertTrue("nav: md-navigation-drawer opened 반영", drawer.opened);
            // ---- M3 flexible navigation bar 토큰 (md-comp-nav-bar*, Compose ShortNavigationBar) ----
            assertTrue("nav: md-navigation-bar 는 flexible 구현(labs baseline 아님)", Js.asPropertyMap(bar).has("resolvedLayout"));
            assertEquals("nav: navigation bar 높이 64px", 64, (int) Math.round(vBar.getBoundingClientRect().height));
            var vTab = (HTMLElement) vBar.querySelector("md-navigation-tab");
            var vInd = inner(vTab, ".indicator");
            assertEquals("nav: 세로형 indicator 56x32", "56x32", Math.round(vInd.getBoundingClientRect().width) + "x" + Math.round(vInd.getBoundingClientRect().height));
            assertEquals("nav: 세로형 위 패딩 6px", "6px", cs(inner(vTab, "button")).getPropertyValue("padding-top"));
            assertEquals("nav: 세로형 indicator-라벨 간격 4px", "4px", cs(inner(vTab, "button")).getPropertyValue("row-gap"));
            assertEquals("nav: 세로형 항목 균등 너비", Math.round(vBar.getBoundingClientRect().width / 3), Math.round(vTab.getBoundingClientRect().width));
            assertEquals("nav: 라벨 label-medium 12px", "12px", cs(inner(vTab, ".label")).getPropertyValue("font-size"));
            assertEquals("nav: large 배지 높이 16px", 16, (int) Math.round(inner(vTab, ".badge").getBoundingClientRect().height));
            var smallBadge = inner((HTMLElement) vBar.querySelectorAll("md-navigation-tab").item(1), ".badge");
            assertEquals("nav: small 배지 6px", "6x6", Math.round(smallBadge.getBoundingClientRect().width) + "x" + Math.round(smallBadge.getBoundingClientRect().height));
            var hTab = (HTMLElement) hBar.querySelector("md-navigation-tab");
            var hInd = inner(hTab, ".indicator");
            assertEquals("nav: 가로형 indicator 높이 40px", 40, (int) Math.round(hInd.getBoundingClientRect().height));
            assertEquals("nav: 가로형 indicator 앞 16px", "16px", cs(hInd).getPropertyValue("padding-left"));
            assertEquals("nav: 가로형 아이콘-라벨 4px", "4px", cs(hInd).getPropertyValue("column-gap"));
            assertTrue("nav: 가로형 라벨이 indicator 안에", hInd.querySelector(".label") != null);
            assertEquals("nav: 가로형 3개 가운데 정렬 양옆 20%", Math.round(hBar.getBoundingClientRect().width * 0.2), Math.round(Double.parseDouble(cs(hBar).getPropertyValue("padding-left").replace("px", ""))));
            assertEquals("nav: auto 600 미만 세로형", "vertical", narrow.resolvedLayout);
            assertEquals("nav: auto 600 이상 가로형", "horizontal", wide.resolvedLayout);
            // 실제 클릭: shadow DOM 안의 버튼
            if (!isAutomated()) return; // 사람이 볼 때는 자동 클릭하지 않는다(직접 눌러 확인)
            click(item2El);
            click(tab2El);
            DomGlobal.setTimeout(t2 -> {
                assertEquals("nav: md-navigation-rail 클릭 시 activeIndex", 1, (int) rail.activeIndex);
                assertEquals("nav: md-navigation-rail onActivated 이벤트", 1, railEvent[0]);
                assertTrue("nav: md-navigation-rail-item 클릭 후 active", item2El.active);
                assertEquals("nav: md-navigation-bar 클릭 시 activeIndex", 1, (int) bar.activeIndex);
                assertEquals("nav: md-navigation-bar onActivated 이벤트", 1, barEvent[0]);
                assertTrue("nav: md-navigation-tab 클릭 후 active", tab2El.active);
            }, 500);
        }, 1500);
    }

    private static void check(HTMLElement el, String tag, boolean props) {
        assertEquals("nav: " + tag + " 태그명", tag.toUpperCase(), el.tagName);
        assertTrue("nav: " + tag + " 속성 설정", props);
    }
    private static elemental2.dom.CSSStyleDeclaration cs(elemental2.dom.Element e) {
        elemental2.core.Function f = Js.uncheckedCast(Js.asPropertyMap(DomGlobal.window).get("getComputedStyle"));
        return Js.uncheckedCast(f.call(DomGlobal.window, e));
    }
    private static HTMLElement inner(HTMLElement host, String sel) { var r = shadow(host); return r == null ? null : Js.uncheckedCast(r.querySelector(sel)); }
    private static ShadowRoot shadow(HTMLElement el) {
        return Js.uncheckedCast(Js.asPropertyMap(el).get("shadowRoot"));
    }
    private static void click(HTMLElement el) {
        var root = shadow(el);
        HTMLElement button = root == null ? null : Js.uncheckedCast(root.querySelector("button"));
        if (button != null) button.click();
    }
}
