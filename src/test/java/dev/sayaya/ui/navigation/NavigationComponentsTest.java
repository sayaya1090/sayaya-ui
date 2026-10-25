package dev.sayaya.ui.navigation;

import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLElement;
import elemental2.dom.ShadowRoot;
import jsinterop.base.Js;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.ButtonElementBuilder.button;
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
        var item3 = railItem().icon("notifications").label("알림").badge("3");
        var rail = navigationRail().alignment(RailAlignment.Center).expanded(true)
                .header(icon("menu"))
                .item(railItem().icon("home").label("홈"))
                .item(item2)
                .item(item3)
                .onActivated(i -> railEvent[0] = i).element();
        var item2El = item2.element();
        var item3El = item3.element();
        check(rail, "md-navigation-rail", "center".equals(rail.alignment) && rail.expanded && rail.querySelector("[slot='header']") != null);
        check(item2El, "md-navigation-rail-item", "받은편지함".equals(item2El.label) && item2El.showBadge && "".equals(item2El.badgeValue));
        demo(section, "📘 Navigation rail", "medium 이상 화면의 세로 네비게이션입니다. expanded(false)는 96dp 접힌 레일(narrow(true)면 80dp), expanded(true)는 220~360dp 펼친 레일입니다. menu(true)는 펼침/접힘 메뉴 버튼, modal(true)는 펼쳤을 때 본문 위에 뜨는 형태입니다. header 에는 메뉴 버튼이나 FAB 을 둡니다.",
            """
            var rail = navigationRail().alignment(RailAlignment.Center).expanded(true)
                .header(icon("menu"))
                .item(railItem().icon("home").label("홈"))
                .item(railItem().icon("inbox").label("받은편지함").badge())
    .item(railItem().icon("notifications").label("알림").badge("3"))
                .onActivated(index -> route(index))
                .element();
            """, div().style("height", "400px").add(rail).element());

        var drawer = navigationDrawer().opened(true).pivot(DrawerPivot.Start).add(div().text("메뉴")).element();
        check(drawer, "md-navigation-drawer", drawer.opened && "start".equals(drawer.pivot));
        demo(section, "📘 Navigation drawer", "화면 옆에 고정되는 목적지 목록입니다(Material Web labs). opened 로 열고 닫으며, pivot 으로 붙는 쪽을 정합니다. 데모의 버튼으로 열고 닫아 보세요.",
            """
            var drawer = navigationDrawer().opened(true).pivot(DrawerPivot.Start)
                .add(div().text("메뉴"))
                .element();
            """, div().style("display", "flex").style("flex-direction", "column").style("gap", "12px")
                .add(button().outlined().text("drawer 열기/닫기").onClick(e -> drawer.opened = !drawer.opened))
                .add(div().style("height", "160px").style("display", "flex").add(drawer)).element());
        var modal = navigationDrawerModal().opened(false).pivot(DrawerPivot.End).element();
        check(modal, "md-navigation-drawer-modal", !modal.opened && "end".equals(modal.pivot));
        var openModal = button().filled().text("모달 drawer 열기").onClick(e -> modal.opened = true).element();
        body().add(modal);
        demo(section, "📘 Navigation drawer - 모달", "compact 화면에서 스크림 위에 열리는 drawer 입니다. 데모의 버튼으로 열고, 스크림(바깥)을 누르면 닫힙니다.",
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
            for (HTMLElement it : new HTMLElement[]{item2El, item3El}) {
                var b = inner(it, ".badge").getBoundingClientRect(); var l = inner(it, ".label").getBoundingClientRect();
                assertTrue("nav: 펼친 레일 배지가 라벨과 겹치지 않음(" + (it == item2El ? "점" : "숫자") + ")", b.right <= l.left || b.left >= l.right);
            }
            assertEquals("nav: 모달 drawer 는 화면 기준(fixed)", "fixed", cs(modal).getPropertyValue("position"));
            assertEquals("nav: 닫힌 모달 drawer 는 클릭을 막지 않음", "none", cs(modal).getPropertyValue("pointer-events"));
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
            modal.opened = true;
            DomGlobal.setTimeout(t3 -> {
                var msr = shadow(modal);
                HTMLElement panel = Js.uncheckedCast(msr.querySelector(".md3-navigation-drawer-modal"));
                HTMLElement scrim = Js.uncheckedCast(msr.querySelector(".md3-navigation-drawer-modal__scrim"));
                assertEquals("nav: 모달 drawer 열면 너비 360px", 360, (int) Math.round(panel.getBoundingClientRect().width));
                assertEquals("nav: 모달 drawer 스크림 0.32", "0.32", cs(scrim).getPropertyValue("opacity"));
                scrim.click();
                assertTrue("nav: 모달 drawer 스크림 클릭으로 닫힘", !modal.opened);
            }, 600);
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
        audit();
        extensions();
        links();
        motion();
    }

    /** M3 토큰·가이드 대조(rail: nav-rail-*, Compose WideNavigationRail / app bar: app-bar-*, top-app-bar-*). */
    private static void audit() {
        var box = section("M3 대조용 측정");
        var cRail = navigationRail()
                .item(railItem().icon("home").label("홈"))
                .item(railItem().icon("inbox").label("받은편지함"))
                .item(railItem().icon("star").label("즐겨찾기")).element();
        var eRail = navigationRail().expanded(true)
                .item(railItem().icon("home").label("홈"))
                .item(railItem().icon("inbox").label("받은편지함").badge("3")).element();
        box.appendChild(div().style("display", "flex").style("gap", "24px").style("height", "360px").add(cRail).add(eRail).element());
        var small = topAppBar().headline("작게").trailing(icon("more_vert")).element();
        var medium = topAppBar().variant(TopAppBarVariant.Medium).headline("중간").element();
        var large = topAppBar().variant(TopAppBarVariant.Large).headline("크게").element();
        var scrolledBar = topAppBar().headline("스크롤").scrolled(true).element();
        box.appendChild(div().style("width", "600px").add(small).add(medium).add(large).add(scrolledBar).element());
        var mSub = topAppBar().variant(TopAppBarVariant.Medium).headline("받은편지함").subtitle("새 메일 3통").element();
        var lSub = topAppBar().variant(TopAppBarVariant.Large).headline("사진").subtitle("2026년 10월").element();
        var cen = topAppBar().headline("가운데").centered(true).element();
        var legacyCen = topAppBar().variant(TopAppBarVariant.CenterAligned).headline("예전 가운데").element();
        box.appendChild(div().style("width", "600px").add(mSub).add(lSub).add(cen).add(legacyCen).element());
        boolean[] railChanged = {false};
        var menuRail = navigationRail().menu(true).modal(true).onExpandedChanged(x -> railChanged[0] = true)
                .item(railItem().icon("home").label("홈")).item(railItem().icon("inbox").label("받은편지함")).element();
        var narrowRail = navigationRail().narrow(true).item(railItem().icon("home").label("홈")).element();
        box.appendChild(div().style("display", "flex").style("gap", "24px").style("height", "300px").add(menuRail).add(narrowRail).element());
        var longBar = navigationBar().layout(NavigationBarLayout.Vertical)
                .tab(navigationTab().label("홈").icon("home"))
                .tab(navigationTab().label("즐겨찾기 목록").icon("star"))
                .tab(navigationTab().label("알림 설정").icon("notifications"))
                .tab(navigationTab().label("보관함").icon("inventory"))
                .tab(navigationTab().label("프로필").icon("person")).element();
        box.appendChild(div().style("width", "320px").add(longBar).element());
        var probe = div().element(); box.appendChild(probe);
        DomGlobal.setTimeout(t -> {
            java.util.function.Function<String, String> color = v -> { probe.style.setProperty("color", v); return cs(probe).getPropertyValue("color"); };
            String secondary = color.apply("var(--md-sys-color-secondary, #625b71)");
            String onSecContainer = color.apply("var(--md-sys-color-on-secondary-container, #1d192b)");
            String onSurfaceVariant = color.apply("var(--md-sys-color-on-surface-variant, #49454f)");
            HTMLElement c0 = Js.uncheckedCast(cRail.querySelectorAll("md-navigation-rail-item").item(0));
            HTMLElement c1 = Js.uncheckedCast(cRail.querySelectorAll("md-navigation-rail-item").item(1));
            assertEquals("nav: 접힌 레일 너비 96px", 96, (int) Math.round(cRail.getBoundingClientRect().width));
            assertEquals("nav: 레일 위쪽 여백 44px", "44px", cs(cRail).getPropertyValue("padding-top"));
            assertEquals("nav: 접힌 레일 항목 높이 64px", 64, (int) Math.round(c0.getBoundingClientRect().height));
            assertEquals("nav: 접힌 레일 항목 간격 4px", 4, (int) Math.round(c1.getBoundingClientRect().top - c0.getBoundingClientRect().bottom));
            assertEquals("nav: 접힌 레일 활성 라벨 secondary", secondary, cs(inner(c0, ".label")).getPropertyValue("color"));
            HTMLElement e0 = Js.uncheckedCast(eRail.querySelectorAll("md-navigation-rail-item").item(0));
            HTMLElement e1 = Js.uncheckedCast(eRail.querySelectorAll("md-navigation-rail-item").item(1));
            double ew = eRail.getBoundingClientRect().width;
            assertTrue("nav: 펼친 레일 너비 220~360px", ew >= 220 && ew <= 360);
            var ind = inner(e0, ".indicator");
            assertEquals("nav: 펼친 레일 indicator 높이 56px", 56, (int) Math.round(ind.getBoundingClientRect().height));
            assertEquals("nav: 펼친 레일 indicator 앞 여백 16px", "16px", cs(ind).getPropertyValue("padding-left"));
            assertEquals("nav: 펼친 레일 아이콘-라벨 간격 8px", "8px", cs(ind).getPropertyValue("column-gap"));
            assertTrue("nav: 펼친 레일 indicator 는 내용에 맞춤", ind.getBoundingClientRect().width < e0.getBoundingClientRect().width - 1);
            assertEquals("nav: 펼친 레일 활성 라벨 on-secondary-container", onSecContainer, cs(inner(e0, ".label")).getPropertyValue("color"));
            double gap = inner(e1, ".badge").getBoundingClientRect().left - inner(e1, ".label").getBoundingClientRect().right;
            assertTrue("nav: 펼친 레일 배지는 라벨 바로 옆", gap >= 0 && gap <= 12);
            assertEquals("nav: small 앱 바 높이 64px", 64, (int) Math.round(small.getBoundingClientRect().height));
            assertEquals("nav: medium flexible 앱 바 높이 112px", 112, (int) Math.round(medium.getBoundingClientRect().height));
            assertEquals("nav: large flexible 앱 바 높이 120px", 120, (int) Math.round(large.getBoundingClientRect().height));
            assertEquals("nav: small 제목 title-large 22px", "22px", cs(inner(small, ".headline")).getPropertyValue("font-size"));
            assertEquals("nav: medium flexible 제목 headline-medium 28px", "28px", cs(inner(medium, ".expanded")).getPropertyValue("font-size"));
            assertEquals("nav: large flexible 제목 display-small 36px", "36px", cs(inner(large, ".expanded")).getPropertyValue("font-size"));
            assertEquals("nav: 앱 바 trailing 아이콘 on-surface-variant", onSurfaceVariant, cs(small.querySelector("[slot='trailing']")).getPropertyValue("color"));
            assertTrue("nav: 스크롤된 앱 바 elevation level2", !"none".equals(cs(scrolledBar).getPropertyValue("box-shadow")));
            assertEquals("nav: 앱 바 좌우 여백 4px", "4px", cs(inner(small, ".row")).getPropertyValue("padding-left"));
            {
                HTMLElement lt = Js.uncheckedCast(longBar.querySelectorAll("md-navigation-tab").item(1));
                var lbl = inner(lt, ".label");
                assertTrue("nav: navigation bar 라벨 말줄임 없음", !"ellipsis".equals(cs(lbl).getPropertyValue("text-overflow")) && !"hidden".equals(cs(lbl).getPropertyValue("overflow-x")) && "nowrap".equals(cs(lbl).getPropertyValue("white-space")));
                HTMLElement at = Js.uncheckedCast(longBar.querySelectorAll("md-navigation-tab").item(0));
                HTMLElement ai = Js.uncheckedCast(at.querySelector("md-icon"));
                String fvs = cs(ai).getPropertyValue("font-variation-settings");
                assertTrue("nav: navigation bar 활성 아이콘 채움(FILL 1)", fvs.replaceAll("\\s", "").contains("\"FILL\"1"));
                HTMLElement it = Js.uncheckedCast(longBar.querySelectorAll("md-navigation-tab").item(3));
                String fvs2 = cs((HTMLElement) Js.uncheckedCast(it.querySelector("md-icon"))).getPropertyValue("font-variation-settings");
                assertTrue("nav: navigation bar 비활성 아이콘 외곽선(FILL 0)", !fvs2.replaceAll("\\s", "").contains("\"FILL\"1"));
            }
            assertEquals("nav: medium flexible 부제 있으면 136px", 136, (int) Math.round(mSub.getBoundingClientRect().height));
            assertEquals("nav: large flexible 부제 있으면 152px", 152, (int) Math.round(lSub.getBoundingClientRect().height));
            assertEquals("nav: medium flexible 부제 label-large 14px", "14px", cs(inner(mSub, ".xsub")).getPropertyValue("font-size"));
            assertEquals("nav: 앱 바 제목 가운데 정렬", "center", cs(inner(cen, ".titles")).getPropertyValue("text-align"));
            assertTrue("nav: center-aligned 는 small+centered", "small".equals(Js.asPropertyMap(legacyCen).get("variant")) && legacyCen.hasAttribute("centered") && Math.round(legacyCen.getBoundingClientRect().height) == 64);
            assertEquals("nav: narrow 레일 너비 80px", 80, (int) Math.round(narrowRail.getBoundingClientRect().width));
            var menuBtn = inner(menuRail, ".menu");
            assertTrue("nav: 레일 메뉴 버튼은 위쪽 header", menuBtn != null && menuBtn.getBoundingClientRect().top < inner(menuRail, ".items").getBoundingClientRect().top);
            assertEquals("nav: 레일 메뉴 아이콘(접힘) menu", "menu", menuBtn.querySelector("svg").getAttribute("data-icon")); // 폰트 없는 기본 SVG
            if (!isAutomated()) return; // 사람이 볼 때는 modal 레일을 자동으로 열지 않는다
            menuBtn.click();
            DomGlobal.setTimeout(t2 -> {
                var btn2 = inner(menuRail, ".menu");
                assertTrue("nav: 메뉴 버튼으로 레일 펼침", Boolean.TRUE.equals(Js.asPropertyMap(menuRail).get("expanded")) && railChanged[0]);
                assertEquals("nav: 레일 메뉴 아이콘(펼침) menu_open", "menu_open", btn2.querySelector("svg").getAttribute("data-icon"));
                assertEquals("nav: modal 펼친 레일은 화면 기준(fixed)", "fixed", cs(menuRail).getPropertyValue("position"));
                assertEquals("nav: modal 펼친 레일 모서리 16px", "16px", cs(menuRail).getPropertyValue("border-top-right-radius"));
                inner(menuRail, ".scrim").click();
                DomGlobal.setTimeout(t3 -> assertTrue("nav: modal 레일 스크림 클릭으로 접힘", !Boolean.TRUE.equals(Js.asPropertyMap(menuRail).get("expanded"))), 200);
            }, 300);
        }, 1500);
    }
    /** 2.5.0.6: 링크 목적지(href)·aria-current·interceptLinks·메뉴 아이콘 슬롯(빌더 API). 키보드·수식 키 클릭은 NavigationEsmBundleTest 에서 검사 */
    private static void links() {
        var box = section("2.5.0.6 링크 목적지·메뉴 아이콘");
        String[] routed = {null};
        var custom = div().text("≡").element();
        var linkRail = navigationRail().menu(true).menuIcon(custom).interceptLinks(h -> routed[0] = h)
                .item(railItem().icon("home").label("홈").href("#home"))
                .item(railItem().icon("inbox").label("받은편지함").href("#inbox")).element();
        box.appendChild(div().style("height", "240px").add(linkRail).element());
        DomGlobal.setTimeout(t -> {
            HTMLElement i0 = Js.uncheckedCast(linkRail.querySelectorAll("md-navigation-rail-item").item(0));
            HTMLElement i1 = Js.uncheckedCast(linkRail.querySelectorAll("md-navigation-rail-item").item(1));
            var a1 = inner(i1, "[part='button']");
            assertEquals("nav: railItem href 는 <a href>", "a #inbox", a1.localName + " " + a1.getAttribute("href"));
            assertEquals("nav: 활성 링크 aria-current=page", "page", inner(i0, "[part='button']").getAttribute("aria-current"));
            assertTrue("nav: menuIcon 슬롯", "menu-icon".equals(custom.getAttribute("slot")) && custom.getBoundingClientRect().width > 0);
            if (!isAutomated()) return; // 사람이 볼 때는 자동 클릭하지 않는다
            Object hashBefore = Js.asPropertyMap(DomGlobal.location).get("hash");
            a1.click();
            assertEquals("nav: interceptLinks 는 href 를 라우터로", "#inbox", routed[0]);
            assertEquals("nav: interceptLinks 는 기본 이동을 막음", String.valueOf(hashBefore), String.valueOf(Js.asPropertyMap(DomGlobal.location).get("hash")));
            assertEquals("nav: 링크 클릭 시 activeIndex", 1, (int) linkRail.activeIndex);
        }, 1500);
    }
    /** 2.5.0.3 확장: 앱 바 높이 변수·제목 요소, 레일 치수 변수·part·라벨 숨김·보조 슬롯·footer. */
    private static void extensions() {
        var box = section("2.5.0.3 확장(변수·슬롯)");
        var bar56 = topAppBar().headline("56").style("--md-top-app-bar-container-height", "56px").element();
        var med56 = topAppBar().variant(TopAppBarVariant.Medium).headline("56").style("--md-top-app-bar-container-height", "56px").element();
        var picker = div().style("display", "inline-flex").text("워크스페이스 ▾").element();
        var elBar = topAppBar().headline(picker).element();
        var reBar = topAppBar().headline("처음").headline("나중 제목은 아주 길어서 앱 바 너비를 넘어가면 말줄임으로 잘려야 합니다 나중 제목은 아주 길어서").element();
        box.appendChild(div().style("width", "400px").add(bar56).add(med56).add(elBar).add(reBar).element());
        var vRail = navigationRail().style("--md-navigation-rail-collapsed-width", "56px").style("--md-navigation-rail-item-min-height", "48px")
                .style("--md-navigation-rail-item-indicator-width", "40px").hideCollapsedLabels(true)
                .item(railItem().icon("home").label("홈")).item(railItem().icon("inbox").label("받은편지함"))
                .footer(railItem().icon("settings").label("설정")).element();
        var wRail = navigationRail().expanded(true).hideCollapsedLabels(true).style("--md-navigation-rail-expanded-min-width", "256px")
                .item(railItem().icon("folder").label("문서").supportingText("12개 항목").trailing(icon("more_vert")))
                .item(railItem().icon("inbox").label("받은편지함")).element();
        var hugRail = navigationRail().expanded(true).item(railItem().icon("home").label("홈")).item(railItem().icon("inbox").label("받은편지함 보관")).element();
        var fullRail = navigationRail().expanded(true).fullWidthIndicator(true).expandedItemSpace("8px").item(railItem().icon("home").label("홈")).item(railItem().icon("inbox").label("받은편지함 보관")).element();
        box.appendChild(div().style("display", "flex").style("gap", "24px").style("height", "360px").add(vRail).add(wRail).add(hugRail).add(fullRail).element());
        DomGlobal.setTimeout(t -> {
            assertEquals("nav: 앱 바 높이 변수 56px", 56, (int) Math.round(bar56.getBoundingClientRect().height));
            assertEquals("nav: medium 앱 바는 높이 변수 + 48px", 104, (int) Math.round(med56.getBoundingClientRect().height));
            assertTrue("nav: 앱 바 제목 요소 슬롯", picker.parentElement == elBar && "headline".equals(picker.getAttribute("slot")));
            assertTrue("nav: 앱 바 제목 요소는 잘리지 않음", "visible".equals(cs(inner(elBar, ".headline")).getPropertyValue("overflow-x"))
                    && !"ellipsis".equals(cs(picker).getPropertyValue("text-overflow")) && !"hidden".equals(cs(picker).getPropertyValue("overflow-x")));
            var spans = reBar.querySelectorAll(":scope > [slot='headline']");
            HTMLElement span = Js.uncheckedCast(spans.item(0));
            assertTrue("nav: 앱 바 제목 재지정은 교체", spans.length == 1 && span.textContent.startsWith("나중"));
            assertTrue("nav: 앱 바 텍스트 제목 한 줄 말줄임", "ellipsis".equals(cs(span).getPropertyValue("text-overflow")) && "nowrap".equals(cs(span).getPropertyValue("white-space"))
                    && span.scrollWidth > span.clientWidth && Math.round(reBar.getBoundingClientRect().height) == 64);
            HTMLElement v0 = Js.uncheckedCast(vRail.querySelectorAll("md-navigation-rail-item").item(0));
            assertEquals("nav: 레일 접힘 너비 변수 56px", 56, (int) Math.round(vRail.getBoundingClientRect().width));
            assertEquals("nav: 레일 항목 높이 변수 48px", 48, (int) Math.round(v0.getBoundingClientRect().height));
            assertEquals("nav: 레일 indicator 너비 변수 40px", 40, (int) Math.round(inner(v0, ".indicator").getBoundingClientRect().width));
            assertTrue("nav: 레일 항목 part button/label", inner(v0, "[part~='button']") != null && inner(v0, "[part~='label']") != null);
            assertTrue("nav: 접힌 레일 라벨 숨김", v0.hasAttribute("hide-collapsed-labels") && "none".equals(cs(inner(v0, ".label")).getPropertyValue("display"))
                    && "홈".equals(inner(v0, "button").getAttribute("title")) && "홈".equals(inner(v0, "button").getAttribute("aria-label")));
            HTMLElement w0 = Js.uncheckedCast(wRail.querySelectorAll("md-navigation-rail-item").item(0));
            assertTrue("nav: 펼친 레일은 라벨 숨김 무시", !"none".equals(cs(inner(w0, ".label")).getPropertyValue("display")) && inner(w0, "button").getAttribute("title") == null);
            double ww = wRail.getBoundingClientRect().width;
            assertTrue("nav: 펼친 레일 최소 너비 변수 256px", ww >= 256 && ww <= 360);
            var sup = inner(w0, ".supporting"); var trl = inner(w0, ".trailing");
            assertTrue("nav: 레일 항목 supporting-text 슬롯", !"none".equals(cs(sup).getPropertyValue("display")) && sup.getBoundingClientRect().top >= inner(w0, ".label").getBoundingClientRect().bottom - 1);
            assertTrue("nav: 레일 항목 trailing 슬롯은 끝에", !"none".equals(cs(trl).getPropertyValue("display"))
                    && trl.getBoundingClientRect().right > inner(w0, ".text").getBoundingClientRect().right + 8);
            HTMLElement foot = Js.uncheckedCast(vRail.querySelector("[slot='footer']"));
            assertTrue("nav: 레일 footer 는 항목 아래·목적지 아님", foot.getBoundingClientRect().top >= inner(vRail, ".items").getBoundingClientRect().bottom - 1
                    && ((elemental2.core.JsArray<?>) Js.uncheckedCast(Js.asPropertyMap(vRail).get("items"))).length == 2 && foot.hasAttribute("hide-collapsed-labels"));
            HTMLElement h0 = Js.uncheckedCast(hugRail.querySelectorAll("md-navigation-rail-item").item(0));
            HTMLElement h1 = Js.uncheckedCast(hugRail.querySelectorAll("md-navigation-rail-item").item(1));
            double hw0 = inner(h0, ".indicator").getBoundingClientRect().width, hw1 = inner(h1, ".indicator").getBoundingClientRect().width;
            assertTrue("nav: 펼친 레일 indicator 기본은 항목마다 내용 폭", hw1 > hw0 + 8 && hw0 < h0.getBoundingClientRect().width - 8);
            HTMLElement f0 = Js.uncheckedCast(fullRail.querySelectorAll("md-navigation-rail-item").item(0));
            HTMLElement f1 = Js.uncheckedCast(fullRail.querySelectorAll("md-navigation-rail-item").item(1));
            double fw0 = inner(f0, ".indicator").getBoundingClientRect().width, fw1 = inner(f1, ".indicator").getBoundingClientRect().width;
            assertTrue("nav: fullWidthIndicator 는 모든 항목 indicator 를 항목 폭만큼",
                    f0.hasAttribute("full-width-indicator") && Math.abs(fw0 - fw1) < 1 && Math.abs(fw0 - f0.getBoundingClientRect().width) < 1);
            assertEquals("nav: 펼친 레일 항목 간격 기본 0", 0, (int) Math.round(h1.getBoundingClientRect().top - h0.getBoundingClientRect().bottom));
            assertEquals("nav: expandedItemSpace 8px", 8, (int) Math.round(f1.getBoundingClientRect().top - f0.getBoundingClientRect().bottom));
        }, 1500);
    }
    /** 펼침/접힘 전환 모션: 중간 프레임은 접힘↔펼침 사이 값, 끝나면 처음부터 그 상태인 레일과 같고 인라인 스타일·잔상이 정리된다 */
    private static void motion() {
        var box = section("펼침/접힘 전환 모션");
        var anim = motionRail(false); var stillE = motionRail(true); var stillC = motionRail(false);
        box.appendChild(div().style("display", "flex").style("gap", "24px").style("height", "320px").add(anim).add(stillE).add(stillC).element());
        DomGlobal.setTimeout(t0 -> {
            if (!isAutomated()) return; // 사람이 볼 때는 자동으로 펼치지 않는다(메뉴 레일 데모로 확인)
            double yC = iconY(anim, 2), yE = iconY(stillE, 2);
            anim.toggle(true);
            DomGlobal.setTimeout(t1 -> {
                double w = anim.getBoundingClientRect().width, y = iconY(anim, 2);
                assertTrue("nav: 레일 펼침 전환 중 너비는 중간값", w > 100 && w < 236);
                assertTrue("nav: 레일 펼침 전환 중 아이콘 위치는 중간값", Math.min(yC, yE) + 0.5 < y && y < Math.max(yC, yE) - 0.5);
                HTMLElement i0 = Js.uncheckedCast(anim.querySelectorAll("md-navigation-rail-item").item(0));
                assertTrue("nav: 레일 펼침 전환 중 라벨 페이드", Double.parseDouble(cs(inner(i0, ".label")).getPropertyValue("opacity")) < 0.95);
                DomGlobal.setTimeout(t2 -> {
                    assertTrue("nav: 레일 펼침 전환 후 정적 레일과 같음", sameLayout(anim, stillE));
                    assertTrue("nav: 레일 전환 후 인라인 스타일 정리", motionClean(anim));
                    anim.toggle(false);
                    DomGlobal.setTimeout(t3 -> {
                        double w2 = anim.getBoundingClientRect().width;
                        assertTrue("nav: 레일 접힘 전환 중 너비는 중간값", w2 > 100 && w2 < 236);
                        DomGlobal.setTimeout(t4 -> assertTrue("nav: 레일 접힘 전환 후 정적 레일과 같음", sameLayout(anim, stillC) && motionClean(anim)), 900);
                    }, 80);
                }, 900);
            }, 80);
        }, 1500);
    }
    private static dev.sayaya.ui.dom.MdNavigationElement.MdNavigationRailElement motionRail(boolean expanded) {
        return navigationRail().expanded(expanded).style("--md-navigation-rail-expanded-min-width", "240px")
                .item(railItem().icon("home").label("홈")).item(railItem().icon("inbox").label("받은편지함"))
                .item(railItem().icon("settings").label("설정")).element();
    }
    private static double iconY(HTMLElement rail, int i) {
        HTMLElement it = Js.uncheckedCast(rail.querySelectorAll("md-navigation-rail-item").item(i));
        return inner(it, ".icon").getBoundingClientRect().top - rail.getBoundingClientRect().top;
    }
    /** 레일 너비와 항목별 indicator·아이콘·라벨 위치(레일 기준)가 0.5px 안에서 같다 */
    private static boolean sameLayout(HTMLElement a, HTMLElement b) {
        if (Math.abs(a.getBoundingClientRect().width - b.getBoundingClientRect().width) > 0.5) return false;
        var ra = a.getBoundingClientRect(); var rb = b.getBoundingClientRect();
        for (int i = 0; i < 3; i++) {
            HTMLElement ia = Js.uncheckedCast(a.querySelectorAll("md-navigation-rail-item").item(i));
            HTMLElement ib = Js.uncheckedCast(b.querySelectorAll("md-navigation-rail-item").item(i));
            for (String sel : new String[]{".indicator", ".icon", ".label"}) {
                var x = inner(ia, sel).getBoundingClientRect(); var y = inner(ib, sel).getBoundingClientRect();
                if (Math.abs((x.left - ra.left) - (y.left - rb.left)) > 0.5 || Math.abs((x.top - ra.top) - (y.top - rb.top)) > 0.5
                        || Math.abs(x.width - y.width) > 0.5 || Math.abs(x.height - y.height) > 0.5) return false;
            }
        }
        return true;
    }
    /** 전환이 끝나면 레일·항목에 모션용 인라인 스타일과 라벨 잔상이 남지 않고, 소비자가 지정한 style 은 보존된다 */
    private static boolean motionClean(HTMLElement rail) {
        if (!rail.style.getPropertyValue("width").isEmpty() || !rail.getAttribute("style").contains("--md-navigation-rail-expanded-min-width")) return false;
        for (int i = 0; i < 3; i++) {
            HTMLElement it = Js.uncheckedCast(rail.querySelectorAll("md-navigation-rail-item").item(i));
            if (it.hasAttribute("style") && !it.getAttribute("style").isEmpty()) return false;
            if (shadow(it).querySelectorAll(":scope > span").length != 0) return false;
            var ind = inner(it, ".indicator"); var lbl = inner(it, ".label");
            if ((ind.hasAttribute("style") && !ind.getAttribute("style").isEmpty()) || (lbl.hasAttribute("style") && !lbl.getAttribute("style").isEmpty())) return false;
        }
        return true;
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
