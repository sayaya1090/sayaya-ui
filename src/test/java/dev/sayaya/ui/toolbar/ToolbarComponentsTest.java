package dev.sayaya.ui.toolbar;

import elemental2.dom.*;
import dev.sayaya.ui.elements.ToolbarElementBuilder;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.ButtonElementBuilder.button;
import static dev.sayaya.ui.elements.ToolbarElementBuilder.*;
import static org.jboss.elemento.Elements.div;

/** M3 toolbar(docked/floating): 토큰 수치(computed style) + role·키보드 이동. 메시지 접두어 "tb: ". */
public class ToolbarComponentsTest {
    private static CSSStyleDeclaration cs(Element e) {
        elemental2.core.Function f = Js.uncheckedCast(Js.asPropertyMap(DomGlobal.window).get("getComputedStyle"));
        return Js.uncheckedCast(f.call(DomGlobal.window, e));
    }
    private static long w(Element e) { return Math.round(e.getBoundingClientRect().width); }
    private static long h(Element e) { return Math.round(e.getBoundingClientRect().height); }
    private static void later(int ms, Runnable r) { DomGlobal.setTimeout(t -> r.run(), ms); }
    private static void key(Element target, String key) {
        JsPropertyMap<Object> m = JsPropertyMap.of();
        m.set("key", key); m.set("bubbles", true); m.set("composed", true); m.set("cancelable", true);
        KeyboardEventInit init = Js.uncheckedCast(m);
        target.dispatchEvent(new KeyboardEvent("keydown", init));
    }
    private static Element active() { return DomGlobal.document.activeElement; }

    public static void test() {
        printSectionHeader("M3 Toolbar");
        printDescription("현재 페이지와 관련된 자주 쓰는 동작을 모아 보여 주는 툴바입니다. docked 는 화면 아래에 붙는 전체 너비 툴바로, 더 이상 권장되지 않는 bottom app bar 를 대체합니다. floating 은 콘텐츠 위에 떠 있는 둥근 툴바이며 가로·세로 배치와 vibrant 색 구성을 지원합니다. 내비게이션 바와 동시에 표시하지 않습니다.");
        printSeparator();
        var section = section("Toolbar");

        var undo = button().icon("undo").ariaLabel("실행 취소").element();
        var redo = button().icon("redo").ariaLabel("다시 실행").element();
        var share = button().icon("share").ariaLabel("공유").element();
        var docked = toolbar().docked().ariaLabel("편집 도구").add(undo).add(redo).add(share)
                .add(button().icon("delete").ariaLabel("삭제")).element();
        var dockedWrap = div().style("width", "480px").add(docked).element();
        demo(section, "📘 Docked toolbar", "높이 64dp, 좌우 여백 16dp, 모서리 없음, surface container 색입니다. 항목은 균등 간격(최소 4dp)으로 놓입니다. ←/→ 와 Home/End 로 항목 사이를 이동합니다.",
            """
            var tools = toolbar().docked().ariaLabel("편집 도구")
                .add(button().icon("undo").ariaLabel("실행 취소"))
                .add(button().icon("redo").ariaLabel("다시 실행"))
                .add(button().icon("share").ariaLabel("공유"))
                .element();
            """, dockedWrap);

        var bold = button().icon("format_bold").ariaLabel("굵게").element();
        var floating = toolbar().floating().ariaLabel("서식").add(bold)
                .add(button().icon("format_italic").ariaLabel("기울임")).add(button().icon("format_underlined").ariaLabel("밑줄")).element();
        var vibrant = toolbar().floating().vibrant(true).ariaLabel("서식(vibrant)")
                .add(button().icon("format_bold").ariaLabel("굵게")).add(button().icon("format_italic").ariaLabel("기울임")).element();
        demo(section, "📘 Floating toolbar - standard / vibrant", "높이 64dp, 모서리 full, 좌우 여백 8dp, 항목 간격 4dp 입니다. vibrant 는 primary container 색으로 더 강조하며 아이콘은 on primary container 색입니다.",
            """
            toolbar().floating().ariaLabel("서식")
                .add(button().icon("format_bold").ariaLabel("굵게"))
                .add(button().icon("format_italic").ariaLabel("기울임"))
                .element();
            toolbar().floating().vibrant(true).ariaLabel("서식")...element();
            """, div().style("display", "flex").style("gap", "16px").add(floating).add(vibrant).element());

        var v1 = button().icon("edit").ariaLabel("편집").element();
        var v2 = button().icon("brush").ariaLabel("브러시").element();
        var vertical = toolbar().floating().vertical(true).ariaLabel("그리기 도구").add(v1).add(v2)
                .add(button().icon("palette").ariaLabel("색")).element();
        demo(section, "📘 Vertical floating toolbar", "세로 배치는 너비 64dp 이며 ↑/↓ 로 이동합니다. 화면 가장자리에 둘 때는 position(Position.EndCenter)로 16dp 띄워 고정합니다.",
            """
            toolbar().floating().vertical(true).ariaLabel("그리기 도구")
                .add(button().icon("edit").ariaLabel("편집"))
                .add(button().icon("brush").ariaLabel("브러시"))
                .element();
            """, vertical);
        var fixed = toolbar().floating().position(Position.BottomCenter).ariaLabel("고정").style("visibility", "hidden").element();
        section.appendChild(fixed);

        assertEquals("tb: role toolbar", "toolbar", docked.getAttribute("role"));
        assertEquals("tb: aria-label", "편집 도구", docked.getAttribute("aria-label"));
        assertEquals("tb: 세로 aria-orientation", "vertical", vertical.getAttribute("aria-orientation"));
        assertTrue("tb: 가로는 aria-orientation 없음", !floating.hasAttribute("aria-orientation"));

        later(800, () -> {
            assertEquals("tb: docked 높이 64px", 64, (int) h(docked));
            assertEquals("tb: docked 전체 너비", 480, (int) w(docked));
            assertEquals("tb: docked 좌우 여백 16px", "16px", cs(docked).getPropertyValue("padding-left"));
            assertEquals("tb: docked 모서리 없음", "0px", cs(docked).getPropertyValue("border-top-left-radius"));
            assertEquals("tb: docked 배경 surface-container", "rgb(243, 237, 247)", cs(docked).getPropertyValue("background-color"));
            assertEquals("tb: docked 기본 간격 최대 32px", "32px", cs(docked).getPropertyValue("column-gap"));
            assertEquals("tb: floating 높이 64px", 64, (int) h(floating));
            assertEquals("tb: floating 모서리 full", "9999px", cs(floating).getPropertyValue("border-top-left-radius"));
            assertEquals("tb: floating 좌우 여백 8px", "8px", cs(floating).getPropertyValue("padding-left"));
            assertEquals("tb: floating 간격 4px", "4px", cs(floating).getPropertyValue("column-gap"));
            assertEquals("tb: floating 배경 surface-container", "rgb(243, 237, 247)", cs(floating).getPropertyValue("background-color"));
            assertTrue("tb: floating 그림자", !"none".equals(cs(floating).getPropertyValue("box-shadow")));
            assertEquals("tb: vibrant 배경 primary-container", "rgb(234, 221, 255)", cs(vibrant).getPropertyValue("background-color"));
            assertEquals("tb: vibrant 아이콘 on-primary-container", "#21005d", cs(vibrant).getPropertyValue("--md-icon-button-icon-color").trim());
            assertEquals("tb: 세로 너비 64px", 64, (int) w(vertical));
            assertEquals("tb: 세로 배치", "column", cs(vertical).getPropertyValue("flex-direction"));
            assertEquals("tb: 고정 위치 fixed", "fixed", cs(fixed).getPropertyValue("position"));
            assertEquals("tb: 고정 위치 하단 16px", "16px", cs(fixed).getPropertyValue("bottom"));

            undo.focus();
            key(undo, "ArrowRight");
            assertTrue("tb: → 로 다음 항목", active() == redo);
            key(redo, "ArrowLeft");
            assertTrue("tb: ← 로 이전 항목", active() == undo);
            key(undo, "ArrowLeft");
            assertTrue("tb: 처음에서 ← 는 마지막으로", active() == docked.lastElementChild);
            key(active(), "Home");
            assertTrue("tb: Home 은 첫 항목", active() == undo);
            key(undo, "End");
            assertTrue("tb: End 는 마지막 항목", active() == docked.lastElementChild);
            v1.focus();
            key(v1, "ArrowDown");
            assertTrue("tb: 세로는 ↓ 로 다음 항목", active() == v2);
            key(v2, "ArrowRight");
            assertTrue("tb: 세로는 → 무시", active() == v2);
        });
        documented(section);
    }

    private static HTMLElement icons(int n, ToolbarElementBuilder b) {
        String[] names = {"undo", "redo", "content_cut", "content_copy", "content_paste"};
        for (int i = 0; i < n; i++) b.add(button().icon(names[i]).ariaLabel(names[i]));
        return b.element();
    }
    private static int expectedGap(HTMLElement bar) {
        var items = bar.querySelectorAll(":scope > *");
        double used = 0;
        for (int i = 0; i < items.length; i++) used += ((HTMLElement) items.item(i)).offsetWidth;
        return (int) Math.max(4, Math.min(32, Math.floor((bar.clientWidth - 32 - used) / (items.length - 1))));
    }

    /** 가이드라인: 가운데 정렬·균등 간격(4~32dp), 정렬 구성, embedded primary action, floating + FAB(별도 배치) */
    private static void documented(HTMLElement section) {
        var narrow = icons(4, toolbar().docked().ariaLabel("narrow"));
        var tiny = icons(4, toolbar().docked().ariaLabel("tiny"));
        var center = icons(3, toolbar().docked().alignment(Alignment.Center).ariaLabel("center"));
        var start = icons(3, toolbar().docked().alignment(Alignment.Start).ariaLabel("start"));
        var edges = icons(3, toolbar().docked().alignment(Alignment.Edges).ariaLabel("edges"));
        var embedded = toolbar().docked().ariaLabel("Message")
                .add(button().icon("attach_file").ariaLabel("Attach")).add(button().icon("image").ariaLabel("Image"))
                .add(button().filled().text("Send")).add(button().icon("mood").ariaLabel("Emoji")).element();
        demo(section, "📘 Docked toolbar 배치", "가이드라인: \"By default all toolbars are 64dp high, center-aligned, have equal padding between items, and have a minimum outside padding of 16dp\", \"The 32dp padding between items is just the default\". 폭이 좁으면 간격이 4dp 까지 줄어듭니다. alignment(...)로 \"Center-aligned, 8dp padding between items\", \"Left and right alignment\", 가장자리 정렬을 고릅니다.",
            """
            toolbar().docked().add(...).element();                          // 가운데·균등 간격(4~32dp)
            toolbar().docked().alignment(Alignment.Center).add(...).element(); // 간격 8dp
            toolbar().docked().alignment(Alignment.Edges).add(...).element();  // 양 끝 정렬
            """, div().style("display", "flex").style("flex-direction", "column").style("gap", "8px")
                .add(div().style("width", "260px").add(narrow)).add(div().style("width", "190px").add(tiny))
                .add(div().style("width", "480px").add(center)).add(div().style("width", "480px").add(start)).add(div().style("width", "480px").add(edges)).element());
        var vibrantFloating = icons(3, toolbar().floating().vibrant(true).ariaLabel("Format"));
        var fab = button().fab().icon("add").ariaLabel("Create").element();
        var pair = div().style("display", "flex").style("align-items", "center").style("gap", "8px").add(vibrantFloating).add(fab).element();
        demo(section, "📘 Embedded primary action / Floating + FAB", "가이드라인 예시: \"Docked with embedded primary action instead of FAB\", \"Floating, vibrant color scheme and paired with FAB\". 웹에서는 \"On other platforms, each component needs to be added separately\" 이므로 FAB 를 툴바 옆에 따로 둡니다. \"Don't emphasize multiple buttons with bold, primary colors, such as a button and FAB together\" — 한 번에 하나만 강조합니다.",
            """
            toolbar().docked().add(button().icon("attach_file")).add(button().filled().text("Send")).element();
            div().add(toolbar().floating().vibrant(true).add(...)).add(button().fab().icon("add")).element();
            """, div().style("display", "flex").style("flex-direction", "column").style("gap", "16px")
                .add(div().style("width", "480px").add(embedded)).add(pair).element());

        later(1500, () -> {
            int g = expectedGap(narrow);
            assertTrue("tb: 좁으면 간격이 4~32 사이로 줄어듦", g > 4 && g < 32);
            assertEquals("tb: 좁은 폭 간격 계산", g + "px", cs(narrow).getPropertyValue("column-gap"));
            assertEquals("tb: 아주 좁으면 최소 4px", "4px", cs(tiny).getPropertyValue("column-gap"));
            var first = (HTMLElement) narrow.firstElementChild; var last = (HTMLElement) narrow.lastElementChild;
            double leftGap = first.getBoundingClientRect().left - narrow.getBoundingClientRect().left;
            double rightGap = narrow.getBoundingClientRect().right - last.getBoundingClientRect().right;
            assertTrue("tb: 기본 배치는 가운데 정렬(양쪽 여백 같음)", Math.abs(leftGap - rightGap) <= 1 && leftGap >= 16);
            assertEquals("tb: Center 정렬 간격 8px", "8px", cs(center).getPropertyValue("column-gap"));
            assertEquals("tb: Center 정렬 가운데", "center", cs(center).getPropertyValue("justify-content"));
            assertEquals("tb: Start 정렬", "flex-start", cs(start).getPropertyValue("justify-content"));
            assertEquals("tb: Start 첫 항목은 앞 여백 16px", 16, (int) Math.round(((HTMLElement) start.firstElementChild).getBoundingClientRect().left - start.getBoundingClientRect().left));
            assertEquals("tb: Edges 정렬", "space-between", cs(edges).getPropertyValue("justify-content"));
            assertEquals("tb: Edges 마지막 항목은 끝 여백 16px", 16, (int) Math.round(edges.getBoundingClientRect().right - ((HTMLElement) edges.lastElementChild).getBoundingClientRect().right));
            assertEquals("tb: embedded primary action 높이 64px 유지", 64, (int) h(embedded));
            assertTrue("tb: floating 과 FAB 는 따로 배치", vibrantFloating.parentElement == pair && fab.parentElement == pair && !vibrantFloating.contains(fab));
        });
    }
}
