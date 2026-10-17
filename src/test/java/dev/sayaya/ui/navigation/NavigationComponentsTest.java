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
        printSeparator();
        var section = div().style("margin", "20px").element();
        body().add(section);

        var appBar = topAppBar().variant(TopAppBarVariant.Medium).headline("문서")
                .leading(icon("menu")).trailing(icon("more_vert")).scrolled(true).element();
        check(appBar, "md-top-app-bar", "medium".equals(appBar.variant) && appBar.scrolled
                && appBar.querySelector("[slot='leading']") != null && appBar.querySelector("[slot='trailing']") != null
                && "문서".equals(appBar.querySelector("[slot='expanded-headline']").textContent));
        section.appendChild(appBar);

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
        section.appendChild(bar);

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
        section.appendChild(div().style("height", "400px").add(rail).element());

        var drawer = navigationDrawer().opened(true).pivot(DrawerPivot.Start).add(div().text("메뉴")).element();
        check(drawer, "md-navigation-drawer", drawer.opened && "start".equals(drawer.pivot));
        section.appendChild(drawer);
        var modal = navigationDrawerModal().opened(false).pivot(DrawerPivot.End).element();
        check(modal, "md-navigation-drawer-modal", !modal.opened && "end".equals(modal.pivot));
        section.appendChild(modal);

        DomGlobal.setTimeout(t1 -> {
            for (HTMLElement el : new HTMLElement[]{appBar, bar, tab2El, rail, item2El, drawer, modal})
                assertTrue("nav: " + el.tagName.toLowerCase() + " 업그레이드", shadow(el) != null);
            assertTrue("nav: md-top-app-bar scrolled 속성 반영", appBar.hasAttribute("scrolled"));
            assertTrue("nav: md-navigation-rail expanded 항목 전파", item2El.expanded && item2El.hasAttribute("expanded"));
            assertTrue("nav: md-navigation-drawer opened 반영", drawer.opened);
            // 실제 클릭: shadow DOM 안의 버튼
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
    private static ShadowRoot shadow(HTMLElement el) {
        return Js.uncheckedCast(Js.asPropertyMap(el).get("shadowRoot"));
    }
    private static void click(HTMLElement el) {
        var root = shadow(el);
        HTMLElement button = root == null ? null : Js.uncheckedCast(root.querySelector("button"));
        if (button != null) button.click();
    }
}
