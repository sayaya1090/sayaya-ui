package dev.sayaya.ui.feedback;

import elemental2.dom.CSSStyleDeclaration;
import elemental2.dom.DomGlobal;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;
import jsinterop.base.Js;

import java.util.ArrayList;
import java.util.List;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.ButtonElementBuilder.button;
import static dev.sayaya.ui.elements.FeedbackElementBuilder.*;
import static org.jboss.elemento.Elements.body;
import static org.jboss.elemento.Elements.div;

/** M3 tooltip / snackbar / button group: 토큰 수치(computed style) + 동작 검증. 메시지 접두어 "fb: ". */
public class FeedbackComponentsTest {
    private static CSSStyleDeclaration cs(Element e) {
        elemental2.core.Function f = Js.uncheckedCast(Js.asPropertyMap(DomGlobal.window).get("getComputedStyle"));
        return Js.uncheckedCast(f.call(DomGlobal.window, e));
    }
    private static HTMLElement child(HTMLElement host, int i) { return Js.uncheckedCast(host.querySelectorAll(":scope > *").item(i)); }
    private static Element inner(HTMLElement host, String sel) {
        Object root = Js.asPropertyMap(host).get("shadowRoot");
        return root == null ? null : ((elemental2.dom.ShadowRoot) Js.uncheckedCast(root)).querySelector(sel);
    }
    private static String prop(HTMLElement e, String name) { return e.style.getPropertyValue(name).trim(); }
    private static void later(int ms, Runnable r) { DomGlobal.setTimeout(t -> r.run(), ms); }

    public static void test() {
        printSectionHeader("M3 Tooltip / Snackbar / Button group");
        printDescription("sayaya-ui 가 직접 구현한 M3 tooltip·snackbar·button group 입니다. 색·모양·크기는 Material Web 2.5.0 의 md-comp 토큰과 M3 스펙 수치를 따릅니다.");
        printSeparator();
        var section = section("Tooltip / Snackbar / Button group");

        // ---------- tooltip ----------
        var anchor1 = button().filled().text("저장").element();
        var anchor2 = button().outlined().text("정보").element();
        demo(section, "📘 Tooltip - plain / rich", "버튼에 마우스를 올리거나 포커스하면 delay 후 표시됩니다. plain 은 짧은 설명, rich 는 제목·본문·액션을 가집니다. 창에는 툴팁이 하나만 표시되고 Esc 로 닫힙니다.",
            """
            tooltip().plain().anchor(saveButton).delay(100).text("문서를 저장합니다").element();
            tooltip().rich().anchor(infoButton)
                .subhead("권한").text("편집자만 수정할 수 있습니다")
                .action(button().text().text("자세히"))
                .element();
            """, div().style("display", "flex").style("gap", "12px").add(anchor1).add(anchor2).element());
        int[] opened = {0};
        var plain = tooltip().plain().anchor(anchor1).delay(100).text("문서를 저장합니다").onOpened(t -> opened[0]++).element();
        var rich = tooltip().rich().anchor(anchor2).subhead("권한").text("편집자만 수정할 수 있습니다")
                .action(button().text().text("자세히")).element();
        body().add(plain); body().add(rich);
        assertEquals("fb: tooltip 태그명", "MD-TOOLTIP", plain.tagName);
        assertTrue("fb: tooltip 진단 variant plain", "plain".equals(plain.variant));
        assertTrue("fb: tooltip 진단 variant rich", "rich".equals(rich.variant));
        assertTrue("fb: tooltip 진단 persistent", rich.persistent);
        assertTrue("fb: tooltip 진단 anchor", plain.anchor == anchor1);
        assertTrue("fb: tooltip 진단 subhead", rich.querySelector("[slot='subhead']") != null);
        assertTrue("fb: tooltip 속성 설정", "plain".equals(plain.variant) && "rich".equals(rich.variant) && rich.persistent
                && plain.anchor == anchor1 && rich.querySelector("[slot='subhead']") != null);

        // ---------- snackbar ----------
        List<String> reasons = new ArrayList<>();
        int[] actions = {0};
        var sb = snackbar().text("삭제했습니다").action("실행 취소").closeable(true).timeout(0)
                .onAction(s -> actions[0]++).onClosed(reasons::add).element();
        var sbTimeout = snackbar().text("저장했습니다").timeout(300).onClosed(reasons::add).element();
        var sbMulti = snackbar().text("두 줄 메시지").multiline(true).timeout(0).element();
        body().add(sb); body().add(sbTimeout); body().add(sbMulti);
        demo(section, "📘 Snackbar", "화면 아래에 잠깐 표시되는 알림입니다. 한 번에 하나만 표시되고, 액션·닫기 버튼·자동 닫힘(timeout)·두 줄 형태를 지원합니다. onClosed 로 닫힌 이유(action/dismiss/timeout/replaced)를 받습니다.",
            """
            var sb = snackbar().text("삭제했습니다").action("실행 취소").closeable(true).timeout(0)
                .onAction(s -> undo()).onClosed(reason -> log(reason))
                .element();
            body().add(sb);
            sb.show();
            """,
            div().style("display", "flex").style("gap", "12px")
                .add(button().outlined().text("액션 스낵바").onClick(e -> sb.show()))
                .add(button().outlined().text("자동 닫힘").onClick(e -> sbTimeout.show()))
                .add(button().outlined().text("두 줄").onClick(e -> sbMulti.show())).element());
        assertEquals("fb: snackbar 태그명", "MD-SNACKBAR", sb.tagName);
        assertTrue("fb: snackbar 속성 설정", "실행 취소".equals(sb.action) && sb.closeable && sb.timeout == 0 && sbMulti.multiline);

        // ---------- button group ----------
        var stdSmall = buttonGroup().standard().size(ButtonGroupSize.Small)
                .add(button().outlined().text("하나")).add(button().outlined().text("둘")).element();
        var stdXs = buttonGroup().standard().size(ButtonGroupSize.XSmall)
                .add(button().outlined().text("가")).add(button().outlined().text("나")).element();
        int[][] selected = {null};
        var conn = buttonGroup().connected().size(ButtonGroupSize.Small).selection(ButtonGroupSelection.Single)
                .add(button().outlined().text("일")).add(button().outlined().text("주")).add(button().outlined().text("월"))
                .onSelectionChanged(s -> selected[0] = s).element();
        var connLarge = buttonGroup().connected().size(ButtonGroupSize.Large)
                .add(button().filled().text("A")).add(button().filled().text("B")).add(button().filled().text("C")).element();
        demo(section, "📘 Button group - standard", "관련 버튼을 한데 묶습니다. size 에 따라 버튼 높이와 간격이 정해지고(small 40dp/12dp, xsmall 32dp/18dp), 누른 버튼은 15% 넓어집니다.",
            """
            buttonGroup().standard().size(ButtonGroupSize.Small)
                .add(button().outlined().text("하나")).add(button().outlined().text("둘"))
                .element();
            """, div().style("display", "flex").style("flex-direction", "column").style("gap", "12px").add(stdSmall).add(stdXs).element());
        demo(section, "📘 Button group - connected (단일 선택)", "버튼이 2dp 간격으로 붙은 토글 그룹입니다. selection(Single)이면 하나만 선택되고, 선택된 버튼은 안쪽 모서리가 둥글어집니다.",
            """
            buttonGroup().connected().size(ButtonGroupSize.Small).selection(ButtonGroupSelection.Single)
                .add(button().outlined().text("일")).add(button().outlined().text("주")).add(button().outlined().text("월"))
                .onSelectionChanged(selected -> apply(selected))
                .element();
            """, div().style("width", "360px").add(conn).element());
        demo(section, "📘 Button group - connected (large)", "large 크기는 버튼 높이 96dp, 안쪽 모서리 16dp 입니다.",
            """
            buttonGroup().connected().size(ButtonGroupSize.Large)
                .add(button().filled().text("A")).add(button().filled().text("B")).add(button().filled().text("C"))
                .element();
            """, div().style("width", "420px").add(connLarge).element());
        assertEquals("fb: button-group 태그명", "MD-BUTTON-GROUP", conn.tagName);
        assertTrue("fb: button-group 속성 설정", "connected".equals(conn.variant) && "small".equals(conn.size) && "single".equals(conn.selection));

        later(1500, () -> {
            if (!isAutomated()) return; // 툴팁·스낵바를 자동으로 띄우지 않는다. 데모 버튼/마우스로 확인
            // 업그레이드
            for (HTMLElement el : new HTMLElement[]{plain, sb, conn})
                assertTrue("fb: " + el.tagName.toLowerCase() + " 업그레이드", Js.asPropertyMap(el).get("shadowRoot") != null);

            // plain tooltip 토큰: corner-extra-small 4, body-small 12/16, [토큰 외] 24 높이·8 패딩
            plain.show();
            later(100, () -> {
                var p = cs(plain);
                assertEquals("fb: plain tooltip 모서리 4px", "4px", p.getPropertyValue("border-top-left-radius"));
                assertEquals("fb: plain tooltip 글자 12px", "12px", p.getPropertyValue("font-size"));
                assertEquals("fb: plain tooltip 줄높이 16px", "16px", p.getPropertyValue("line-height"));
                assertEquals("fb: plain tooltip 좌우 패딩 8px", "8px", p.getPropertyValue("padding-left"));
                assertTrue("fb: plain tooltip 높이 24px 이상", plain.getBoundingClientRect().height >= 24);
                assertEquals("fb: plain tooltip role", "tooltip", plain.getAttribute("role"));
                // rich tooltip 토큰: corner-medium 12, elevation level2, subhead title-small 14
                rich.show();
                later(100, () -> {
                    var r = cs(rich);
                    assertEquals("fb: rich tooltip 모서리 12px", "12px", r.getPropertyValue("border-top-left-radius"));
                    assertTrue("fb: rich tooltip 그림자(elevation 2)", !"none".equals(r.getPropertyValue("box-shadow")));
                    assertEquals("fb: rich tooltip 본문 14px", "14px", r.getPropertyValue("font-size"));
                    assertEquals("fb: rich tooltip 최대 너비 320px", "320px", r.getPropertyValue("max-width"));
                    assertEquals("fb: tooltip 최소 너비 40px", "40px", r.getPropertyValue("min-width"));
                    assertTrue("fb: tooltip 창에 하나만", rich.open && !plain.open);
                    rich.hide();
                    // 포커스로 표시 (delay 100ms 후)
                    anchor1.focus();
                    later(400, () -> {
                        assertTrue("fb: tooltip 포커스 시 지연 후 표시", plain.open && opened[0] >= 2);
                        anchor1.blur();
                        later(100, () -> assertTrue("fb: tooltip 포커스 해제 시 닫힘", !plain.open));
                    });
                });
            });

            // snackbar 토큰: corner-extra-small 4, 48/68 높이, body-medium 14
            sb.show();
            later(100, () -> {
                var c = inner(sb, ".container");
                assertEquals("fb: snackbar 모서리 4px", "4px", cs(c).getPropertyValue("border-top-left-radius"));
                assertTrue("fb: snackbar 한 줄 높이 48px", Math.round(c.getBoundingClientRect().height) == 48);
                assertTrue("fb: snackbar 그림자(elevation 3)", !"none".equals(cs(c).getPropertyValue("box-shadow")));
                assertEquals("fb: snackbar 본문 14px", "14px", cs(inner(sb, ".text")).getPropertyValue("font-size"));
                assertEquals("fb: snackbar role status", "status", sb.getAttribute("role"));
                assertEquals("fb: snackbar 하단 여백 8px", "8px", cs(sb).getPropertyValue("bottom"));
                sbMulti.show(); // sb 를 대체
                later(100, () -> {
                    assertTrue("fb: snackbar 두 줄 높이 68px", Math.round(inner(sbMulti, ".container").getBoundingClientRect().height) == 68);
                    assertTrue("fb: snackbar 하나만(replaced)", !sb.open && reasons.contains("replaced"));
                    sbMulti.close();
                    sb.show();
                    later(100, () -> {
                        ((HTMLElement) inner(sb, ".action")).click();
                        assertTrue("fb: snackbar 액션 이벤트와 닫힘", actions[0] == 1 && !sb.open && reasons.contains("action"));
                        sbTimeout.show();
                        later(600, () -> assertTrue("fb: snackbar timeout 자동 닫힘", !sbTimeout.open && reasons.contains("timeout")));
                    });
                });
            });

            // button group 토큰
            assertEquals("fb: standard small 간격 12px", "12px", cs(stdSmall).getPropertyValue("column-gap"));
            assertEquals("fb: standard xsmall 간격 18px", "18px", cs(stdXs).getPropertyValue("column-gap"));
            assertEquals("fb: connected 간격 2px", "2px", cs(conn).getPropertyValue("column-gap"));
            var b0 = child(conn, 0); var b1 = child(conn, 1); var b2 = child(conn, 2);
            assertTrue("fb: small 버튼 높이 40px", Math.round(b0.getBoundingClientRect().height) == 40);
            assertTrue("fb: xsmall 버튼 높이 32px", Math.round((child(stdXs, 0)).getBoundingClientRect().height) == 32);
            assertTrue("fb: large 버튼 높이 96px", Math.round((child(connLarge, 0)).getBoundingClientRect().height) == 96);
            assertEquals("fb: connected 바깥 모서리 full", "20px", prop(b0, "--md-outlined-button-container-shape-start-start"));
            assertEquals("fb: connected 안쪽 모서리 small 8px", "8px", prop(b0, "--md-outlined-button-container-shape-start-end"));
            assertEquals("fb: connected 가운데 시작 모서리 8px", "8px", prop(b1, "--md-outlined-button-container-shape-start-start"));
            assertEquals("fb: connected large 안쪽 모서리 16px", "16px", prop(child(connLarge, 1), "--md-filled-button-container-shape-start-start"));
            var shapeInner = inner(b1, ".background") != null ? inner(b1, ".background") : inner(b1, "#button");
            assertTrue("fb: connected 모서리가 버튼 내부에 적용", shapeInner != null
                    && "8px".equals(cs(shapeInner).getPropertyValue("border-top-left-radius")));
            // 단일 선택
            later(50, () -> {
                b1.click();
                later(50, () -> {
                    assertTrue("fb: connected 선택 이벤트", selected[0] != null && selected[0].length == 1 && selected[0][0] == 1);
                    assertEquals("fb: connected 선택 시 안쪽 모서리 50%", "20px", prop(b1, "--md-outlined-button-container-shape-start-start"));
                    assertEquals("fb: connected aria-pressed", "true", b1.getAttribute("aria-pressed"));
                    b2.click();
                    later(50, () -> assertTrue("fb: connected 단일 선택 전환", !b1.hasAttribute("selected") && b2.hasAttribute("selected")
                            && selected[0].length == 1 && selected[0][0] == 2));
                });
            });
            // standard 누름: 너비 +15%
            var s0 = child(stdSmall, 0);
            double w = s0.getBoundingClientRect().width;
            s0.dispatchEvent(new elemental2.dom.Event("pointerdown", Js.uncheckedCast(jsinterop.base.JsPropertyMap.of("bubbles", true))));
            later(300, () -> {
                double ratio = s0.getBoundingClientRect().width / w;
                assertTrue("fb: standard 누름 시 너비 +15%", Math.abs(ratio - 1.15) < 0.02);
                s0.dispatchEvent(new elemental2.dom.Event("pointerup", Js.uncheckedCast(jsinterop.base.JsPropertyMap.of("bubbles", true))));
                later(300, () -> assertTrue("fb: standard 뗌 시 원래 너비", Math.abs(s0.getBoundingClientRect().width - w) < 1));
            });
        });
    }
}
