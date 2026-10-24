package dev.sayaya.ui.shape;

import dev.sayaya.ui.elements.FabMenuElementBuilder;
import elemental2.dom.*;
import jsinterop.base.Js;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.BottomSheetElementBuilder.bottomSheet;
import static dev.sayaya.ui.elements.CarouselElementBuilder.carousel;
import static dev.sayaya.ui.elements.CenteredSliderElementBuilder.centeredSlider;
import static dev.sayaya.ui.elements.DatePickerElementBuilder.datePicker;
import static dev.sayaya.ui.elements.FabMenuElementBuilder.fabMenu;
import static dev.sayaya.ui.elements.FeedbackElementBuilder.snackbar;
import static dev.sayaya.ui.elements.FeedbackElementBuilder.toast;
import static dev.sayaya.ui.elements.LoadingIndicatorElementBuilder.loadingIndicator;
import static dev.sayaya.ui.elements.NavigationElementBuilder.navigationRail;
import static dev.sayaya.ui.elements.NavigationElementBuilder.railItem;
import static dev.sayaya.ui.elements.SearchElementBuilder.search;
import static dev.sayaya.ui.elements.SideSheetElementBuilder.sideSheet;
import static dev.sayaya.ui.elements.TimePickerElementBuilder.timePicker;
import static dev.sayaya.ui.elements.ToolbarElementBuilder.toolbar;
import static dev.sayaya.ui.elements.WavyProgressElementBuilder.wavyProgress;
import static org.jboss.elemento.Elements.div;

/** 모양(모서리) CSS 변수 주입: 컴포넌트 변수 → 시스템 shape 토큰 → 기본값. 메시지 접두어 "sh: ". */
public class ShapeComponentsTest {
    private static CSSStyleDeclaration cs(Element e) {
        elemental2.core.Function f = Js.uncheckedCast(Js.asPropertyMap(DomGlobal.window).get("getComputedStyle"));
        return Js.uncheckedCast(f.call(DomGlobal.window, e));
    }
    private static String r(Element e) { return e == null ? "(없음)" : cs(e).getPropertyValue("border-top-left-radius"); }
    private static String rb(Element e) { return e == null ? "(없음)" : cs(e).getPropertyValue("border-bottom-left-radius"); }
    private static HTMLElement q(Element root, String sel) { return (HTMLElement) root.querySelector(sel); }
    private static HTMLElement sr(Element host, String sel) {
        ShadowRoot root = Js.uncheckedCast(Js.asPropertyMap(host).get("shadowRoot"));
        return root == null ? null : Js.uncheckedCast(root.querySelector(sel));
    }
    private static void later(int ms, Runnable run) { DomGlobal.setTimeout(t -> run.run(), ms); }

    public static void test() {
        printSectionHeader("Shape (모서리) 사용자 지정");
        printDescription("sayaya-ui 가 직접 구현한 컴포넌트의 모서리는 CSS 변수로 바꿀 수 있습니다. 각 모서리는 컴포넌트 변수(예: --md-date-picker-container-shape) → M3 시스템 토큰(--md-sys-shape-corner-extra-large 등) → 기본값 순으로 정해집니다. 아무것도 지정하지 않으면 M3 기본 모양 그대로입니다.");
        printSeparator();
        var section = section("Shape");

        var li = loadingIndicator().contained(true).style("--md-loading-indicator-container-shape", "12px").element();
        var docked = toolbar().docked().style("--md-toolbar-docked-container-shape", "12px").element();
        var floating = toolbar().floating().style("--md-toolbar-floating-container-shape", "12px").element();
        FabMenuElementBuilder fm = fabMenu().icon("add").ariaLabel("Create").item("edit", "Note", null)
                .style("--md-fab-menu-fab-shape", "4px").style("--md-fab-menu-item-shape", "6px").style("--md-fab-menu-close-button-shape", "10px");
        var fmOpen = fabMenu().icon("add").ariaLabel("Create").item("edit", "Note", null).style("--md-fab-menu-close-button-shape", "10px");
        var se = search().docked().style("--md-search-bar-container-shape", "6px").style("--md-search-view-docked-shape", "14px");
        var dp = datePicker().docked().today(2026, 10, 24).value(2026, 10, 15)
                .style("--md-date-picker-container-shape", "4px").style("--md-date-picker-date-shape", "6px").element();
        var dpSys = datePicker().docked().value(2026, 10, 15).element();
        var dpSysWrap = div().style("--md-sys-shape-corner-extra-large", "10px").add(dpSys).element();
        var tp = timePicker().dial().value(9, 30).style("--md-time-picker-container-shape", "4px")
                .style("--md-time-picker-time-selector-shape", "2px").style("--md-time-picker-clock-dial-shape", "12px").element();
        var tpSys = timePicker().dial().value(9, 30).element();
        var tpSysWrap = div().style("--md-sys-shape-corner-small", "3px").add(tpSys).element();
        var ca = carousel().multiBrowse().itemWidth(200).item(div().style("background", "#6750a4").style("height", "100%").element(), "A")
                .item(div().style("background", "#625b71").style("height", "100%").element(), "B")
                .style("--md-carousel-item-shape", "8px").element();
        ca.style.setProperty("width", "480px");
        var wp = wavyProgress().value(0.5).style("--md-wavy-progress-track-shape", "0px").style("width", "200px").element();
        var cs = centeredSlider().range(-10, 10).value(5).style("--md-centered-slider-track-shape", "4px").style("--md-centered-slider-handle-shape", "1px").element();
        cs.style.setProperty("width", "240px");

        demo(section, "📘 컴포넌트 변수로 모서리 바꾸기", "부위마다 --md-<컴포넌트>-<부위>-shape 변수가 있습니다. 앱 전체에 적용하려면 상위 요소나 :root 에 지정합니다.",
            """
            datePicker().docked()
                .style("--md-date-picker-container-shape", "4px")   // 기본 extra-large(28dp)
                .style("--md-date-picker-date-shape", "6px")        // 기본 full
                .element();
            toolbar().floating().style("--md-toolbar-floating-container-shape", "12px").element();
            carousel().multiBrowse().style("--md-carousel-item-shape", "8px")...element();
            """, div().style("display", "flex").style("gap", "16px").style("flex-wrap", "wrap").style("align-items", "flex-start")
                .add(dp).add(tp).add(floating).add(li).add(fm.element()).element());
        demo(section, "📘 시스템 shape 토큰으로 한꺼번에 바꾸기", "--md-sys-shape-corner-* 토큰을 지정하면 그 토큰을 쓰는 모든 부위가 함께 바뀝니다. 예: extra-large 를 10px 로 두면 date picker·time picker 컨테이너와 carousel 항목이 모두 10px 이 됩니다.",
            """
            :root { --md-sys-shape-corner-extra-large: 10px; --md-sys-shape-corner-small: 3px; }
            """, div().style("display", "flex").style("gap", "16px").style("flex-wrap", "wrap").add(dpSysWrap).add(tpSysWrap).element());
        section.appendChild(div().style("display", "flex").style("flex-direction", "column").style("gap", "16px")
                .add(docked).add(se.element()).add(ca).add(wp).add(cs).add(fmOpen.element()).element());
        fmOpen.open(true); // 모서리 전환(300ms)이 끝난 뒤에 잰다

        // labs JS 직접 구현 컴포넌트
        var sb = snackbar().text("Deleted").action("Undo").closeable(true).timeout(0)
                .style("--md-snackbar-close-shape", "4px").style("--md-snackbar-action-shape", "6px").element();
        var to = toast().info().text("Saved").closeable(true).style("--md-toast-close-shape", "4px").element();
        var ss = sideSheet().standard().headline("Details").open(true).style("--md-side-sheet-standard-container-shape", "12px").element();
        var bs = bottomSheet().standard().style("--md-bottom-sheet-drag-handle-shape", "0px").element();
        var rail = navigationRail().item(railItem().icon("inbox").label("Inbox").badge("3"))
                .style("--md-navigation-rail-badge-shape", "2px").element();
        section.appendChild(div().style("position", "absolute").style("left", "-9999px").style("height", "400px")
                .add(sb).add(to).add(ss).add(bs).add(rail).element());

        later(900, () -> {
            assertEquals("sh: loading indicator 컨테이너", "12px", r(li));
            assertEquals("sh: docked toolbar", "12px", r(docked));
            assertEquals("sh: floating toolbar", "12px", r(floating));
            assertEquals("sh: FAB menu 닫힌 FAB", "4px", r(q(fm.element(), ".toggle")));
            assertEquals("sh: FAB menu 항목", "6px", r(q(fm.element(), ".item")));
            assertEquals("sh: FAB menu 닫기 버튼", "10px", r(q(fmOpen.element(), ".toggle")));
            fmOpen.open(false);
            assertEquals("sh: search bar", "6px", r(q(se.element(), ".bar")));
            se.open(true);
            assertEquals("sh: search docked 열림 바 위 모서리", "14px", r(q(se.element(), ".bar")));
            assertEquals("sh: search docked 뷰 아래 모서리", "14px", rb(q(se.element(), ".view")));
            se.open(false);
            assertEquals("sh: date picker 컨테이너", "4px", r(q(dp, ".card")));
            assertEquals("sh: date picker 날짜", "6px", r(q(dp, "[data-date='2026-10-15']")));
            assertEquals("sh: 시스템 토큰 extra-large → date picker", "10px", r(q(dpSys, ".card")));
            assertEquals("sh: time picker 컨테이너", "4px", r(q(tp, ".card")));
            assertEquals("sh: time picker 시간 칸", "2px", r(q(tp, ".field.hour")));
            assertEquals("sh: time picker 다이얼", "12px", r(q(tp, ".face")));
            assertEquals("sh: 시스템 토큰 small → time picker 시간 칸", "3px", r(q(tpSys, ".field.hour")));
            assertEquals("sh: 시스템 토큰 small → 오전/오후", "3px", r(q(tpSys, ".periods")));
            var item = q(ca, ".item");
            assertEquals("sh: carousel 항목", "8px", r(item));
            assertTrue("sh: carousel 잘림도 같은 모서리", cs(item).getPropertyValue("clip-path").contains("8px"));
            assertEquals("sh: wavy progress 트랙", "0px", r(q(wp, ".track")));
            assertEquals("sh: centered slider 트랙", "4px", r(q(cs, ".active")));
            assertEquals("sh: centered slider 핸들", "1px", r(q(cs, ".handle")));
            assertEquals("sh: snackbar 닫기 버튼", "4px", r(sr(sb, ".close")));
            assertEquals("sh: snackbar 액션", "6px", r(sr(sb, ".action")));
            assertEquals("sh: toast 닫기 버튼", "4px", r(sr(to, ".close")));
            assertEquals("sh: standard side sheet 컨테이너", "12px", r(sr(ss, ".container")));
            assertEquals("sh: bottom sheet 드래그 핸들", "0px", r(sr(bs, ".handle")));
            var railItemEl = rail.querySelector("md-navigation-rail-item");
            assertEquals("sh: rail 배지", "2px", r(sr(railItemEl, ".badge")));
        });
    }
}
