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

/** M3 tooltip / snackbar / button group, [M3 외 확장] toast: 토큰 수치(computed style) + 동작 검증. 메시지 접두어 "fb: ". */
public class FeedbackComponentsTest {
    private static HTMLElement child(HTMLElement host, int i) { return Js.uncheckedCast(host.querySelectorAll(":scope > *").item(i)); }
    private static Element inner(HTMLElement host, String sel) {
        Object root = Js.asPropertyMap(host).get("shadowRoot");
        return root == null ? null : ((elemental2.dom.ShadowRoot) Js.uncheckedCast(root)).querySelector(sel);
    }
    private static String prop(HTMLElement e, String name) { return e.style.getPropertyValue(name).trim(); }
    /** e 에서 지금 진행 중인 CSS transition 의 속성 이름들(getAnimations). 브라우저가 논리 속성을 물리 속성으로 바꿔 보고할 수 있다 */
    private static String transitions(Element e) {
        if (e == null) return "";
        elemental2.core.Function f = Js.uncheckedCast(Js.asPropertyMap(e).get("getAnimations"));
        if (f == null) return "";
        elemental2.core.JsArray<Object> list = Js.uncheckedCast(f.call(e));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.length; i++) sb.append(Js.asPropertyMap(list.getAt(i)).get("transitionProperty")).append(' ');
        return sb.toString().trim();
    }
    private static boolean radiusMoving(Element e) { return transitions(e).contains("radius"); }
    /** 손가락(pointerType touch) 포인터 이벤트 */
    private static void touch(HTMLElement target, String type, double x, double y) {
        jsinterop.base.JsPropertyMap<Object> m = jsinterop.base.JsPropertyMap.of();
        m.set("clientX", x); m.set("clientY", y); m.set("bubbles", !type.equals("pointerenter") && !type.equals("pointerleave")); m.set("composed", true);
        m.set("pointerId", 7); m.set("pointerType", "touch"); m.set("isPrimary", true);
        target.dispatchEvent(new elemental2.dom.PointerEvent(type, Js.uncheckedCast(m)));
    }
    private static boolean connected(Element e) { return Boolean.TRUE.equals(Js.asPropertyMap(e).get("isConnected")); }

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
                .add(button().outlined().text("하나")).add(button().outlined().text("둘")).add(button().outlined().text("셋"))
                .add(button().outlined().text("넷")).add(button().outlined().text("다섯")).element();
        var stdXs = buttonGroup().standard().size(ButtonGroupSize.XSmall)
                .add(button().outlined().text("가")).add(button().outlined().text("나")).add(button().outlined().text("다"))
                .add(button().outlined().text("라")).add(button().outlined().text("마")).element();
        int[][] selected = {null};
        var conn = buttonGroup().connected().size(ButtonGroupSize.Small).selection(ButtonGroupSelection.Single)
                .add(button().outlined().text("일")).add(button().outlined().text("주")).add(button().outlined().text("월"))
                .onSelectionChanged(s -> selected[0] = s).element();
        var connLarge = buttonGroup().connected().size(ButtonGroupSize.Large)
                .add(button().filled().text("A")).add(button().filled().text("B")).add(button().filled().text("C")).element();
        demo(section, "📘 Button group - standard", "관련 버튼을 한데 묶습니다. size 에 따라 버튼 높이와 간격이 정해지고(small 40dp/12dp, xsmall 32dp/18dp), 누른 버튼은 15% 넓어지고 양옆 버튼이 그만큼 좁아집니다(그룹 너비 유지).",
            """
            buttonGroup().standard().size(ButtonGroupSize.Small)
                .add(button().outlined().text("하나")).add(button().outlined().text("둘")).add(button().outlined().text("셋"))
                .add(button().outlined().text("넷")).add(button().outlined().text("다섯"))
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
                // 같은 시각에 도는 버튼 그룹 자동 클릭이 포커스를 옮기면 persistent rich tooltip 은 가이드대로 닫히므로 즉시 확인한다
                assertTrue("fb: tooltip 창에 하나만", rich.open && !plain.open);
                later(100, () -> {
                    var r = cs(rich);
                    assertEquals("fb: rich tooltip 모서리 12px", "12px", r.getPropertyValue("border-top-left-radius"));
                    assertTrue("fb: rich tooltip 그림자(elevation 2)", !"none".equals(r.getPropertyValue("box-shadow")));
                    assertEquals("fb: rich tooltip 본문 14px", "14px", r.getPropertyValue("font-size"));
                    assertEquals("fb: rich tooltip 최대 너비 320px", "320px", r.getPropertyValue("max-width"));
                    assertEquals("fb: tooltip 최소 너비 40px", "40px", r.getPropertyValue("min-width"));
                    rich.hide();
                    // 포커스로 표시 (delay 100ms 후)
                    anchor1.focus();
                    later(400, () -> {
                        assertTrue("fb: tooltip 포커스 시 지연 후 표시", plain.open && opened[0] >= 2);
                        anchor1.blur();
                        later(300, () -> assertTrue("fb: tooltip 떠난 뒤 1.5초 동안 유지", plain.open));
                        later(1700, () -> assertTrue("fb: tooltip 포커스 해제 시 닫힘", !plain.open));
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

        // ---------- button group: M3 가이드 동작 (선택 색·모양, 누름 시 이웃 반응, connected 기본 선택, XS 모서리, 최소 너비) ----------
        var bgSel = buttonGroup().connected().size(ButtonGroupSize.Small)
                .add(button().outlined().text("목록")).add(button().outlined().text("격자")).element();
        var bgXs = buttonGroup().connected().size(ButtonGroupSize.XSmall)
                .add(button().outlined().text("가")).add(button().outlined().text("나")).add(button().outlined().text("다")).element();
        var bgStd = buttonGroup().standard().size(ButtonGroupSize.Small).selection(ButtonGroupSelection.Single)
                .add(button().filled().text("하나")).add(button().filled().text("둘둘")).add(button().filled().text("셋")).element();
        demo(section, "📘 Button group - 선택과 누름", "connected 그룹은 기본으로 하나를 선택하는 토글입니다. 선택된 버튼은 색과 모양이 함께 바뀝니다. standard 그룹에서 버튼을 누르면 그 버튼이 넓어지고 양옆 버튼이 좁아져, 그룹 전체 너비는 그대로입니다.",
            """
            buttonGroup().connected().size(ButtonGroupSize.Small)   // selection 기본값: Single
                .add(button().outlined().text("목록")).add(button().outlined().text("격자"))
                .element();
            buttonGroup().standard().size(ButtonGroupSize.Small).selection(ButtonGroupSelection.Single)
                .add(button().filled().text("하나")).add(button().filled().text("둘둘")).add(button().filled().text("셋"))
                .element();
            """, div().style("display", "flex").style("flex-direction", "column").style("gap", "16px").style("width", "360px")
                .add(bgSel).add(bgXs).add(div().add(bgStd)).element());
        later(1800, () -> {
            assertEquals("fb: connected XS 안쪽 모서리 4px", "4px", prop(child(bgXs, 1), "--md-outlined-button-container-shape-start-start"));
            assertEquals("fb: connected XS 최소 너비 48px", "48px", cs(child(bgXs, 0)).getPropertyValue("min-width"));
            if (!isAutomated()) return; // 사람이 볼 때는 자동 클릭하지 않는다
        });
        later(3500, () -> { // 툴팁 포커스 검사(≈2.4초까지)가 끝난 뒤 클릭해 포커스를 빼앗지 않는다
            if (!isAutomated()) return;
            var sel1 = child(bgSel, 1);
            String bgBefore = cs(sel1).getPropertyValue("background-color"), fgBefore = cs(sel1).getPropertyValue("color");
            sel1.click();
            later(100, () -> {
                assertTrue("fb: connected 기본으로 단일 선택", sel1.hasAttribute("selected"));
                String bgAfter = cs(sel1).getPropertyValue("background-color");
                assertTrue("fb: 선택된 outlined 버튼 배경 변화 [" + bgBefore + " -> " + bgAfter + "]", !"rgba(0, 0, 0, 0)".equals(bgAfter) && !bgAfter.equals(bgBefore));
                assertTrue("fb: 선택된 outlined 버튼 글자색 변화", !fgBefore.equals(prop(sel1, "--md-outlined-button-label-text-color")) && !prop(sel1, "--md-outlined-button-label-text-color").isEmpty());
                // standard 누름
                var s0 = child(bgStd, 0); var s1 = child(bgStd, 1); var s2 = child(bgStd, 2);
                double gw = bgStd.getBoundingClientRect().width, w0 = s0.getBoundingClientRect().width, w2 = s2.getBoundingClientRect().width;
                s1.dispatchEvent(new elemental2.dom.Event("pointerdown", Js.uncheckedCast(jsinterop.base.JsPropertyMap.of("bubbles", true))));
                later(300, () -> {
                    assertTrue("fb: standard 누름 시 그룹 너비 유지", Math.abs(bgStd.getBoundingClientRect().width - gw) < 1.5);
                    assertTrue("fb: standard 누름 시 이웃 버튼이 좁아짐", s0.getBoundingClientRect().width < w0 - 0.5 && s2.getBoundingClientRect().width < w2 - 0.5);
                    assertEquals("fb: standard 누름 시 모서리 8px", "8px", prop(s1, "--md-filled-button-container-shape-start-start"));
                    s1.dispatchEvent(new elemental2.dom.Event("pointerup", Js.uncheckedCast(jsinterop.base.JsPropertyMap.of("bubbles", true))));
                    s0.click();
                    later(300, () -> {
                        assertEquals("fb: standard 선택 시 모서리 12px", "12px", prop(s0, "--md-filled-button-container-shape-start-start"));
                        assertTrue("fb: 선택된 filled 버튼 primary 색", prop(s0, "--md-filled-button-container-color").contains("primary"));
                        assertTrue("fb: standard 뗀 뒤 너비 복원", Math.abs(s0.getBoundingClientRect().width - w0) < 1.5);
                    });
                });
            });
        });
        // persistent rich tooltip(액션 있음): hover 가 아니라 클릭으로 열리고, 다른 요소와 상호작용할 때 닫힘. 기본 위치는 오른쪽 아래
        later(5000, () -> {
            if (!isAutomated()) return;
            rich.hide();
            anchor2.dispatchEvent(new elemental2.dom.Event("pointerenter"));
            later(700, () -> {
                assertTrue("fb: persistent rich tooltip 은 hover 로 열리지 않음", !rich.open);
                anchor2.click();
                later(200, () -> {
                    assertTrue("fb: persistent rich tooltip 클릭으로 열림", rich.open);
                    var ar = anchor2.getBoundingClientRect(); var tr = rich.getBoundingClientRect();
                    assertTrue("fb: rich tooltip 기본 위치 오른쪽 아래", tr.top >= ar.bottom - 0.5 && tr.left >= ar.left + ar.width / 2 - 0.5);
                    anchor2.dispatchEvent(new elemental2.dom.Event("pointerleave"));
                    later(1700, () -> {
                        assertTrue("fb: persistent rich tooltip 은 떠나도 유지", rich.open);
                        DomGlobal.document.body.dispatchEvent(new elemental2.dom.Event("pointerdown", Js.uncheckedCast(jsinterop.base.JsPropertyMap.of("bubbles", true, "composed", true))));
                        later(100, () -> assertTrue("fb: persistent rich tooltip 바깥 상호작용으로 닫힘", !rich.open));
                    });
                });
            });
        });
        // ---------- button group: 토글 모양 전환(shape morph). Compose ToggleButton/ButtonGroup 과 같이 md.sys.motion.spring.fast.spatial ----------
        var bgMorph = buttonGroup().standard().size(ButtonGroupSize.Small).selection(ButtonGroupSelection.Single)
                .add(button().filled().text("일")).add(button().outlined().text("주")).add(button().filled().text("월"))
                .add(button().outlined().text("분기")).add(button().filled().text("년")).element();
        var bgIcon = buttonGroup().standard().size(ButtonGroupSize.Small).selection(ButtonGroupSelection.Single)
                .add(button().icon("star").filled().ariaLabel("별"))
                .add(button().icon("favorite").outlined().ariaLabel("좋아요"))
                .add(button().icon("bookmark").filled().ariaLabel("북마크"))
                .add(button().icon("share").outlined().ariaLabel("공유"))
                .add(button().icon("thumb_up").filled().ariaLabel("추천")).element();
        demo(section, "📘 Button group - 토글 모양 전환", "버튼을 선택하거나 누르면 모서리 모양이 spring 곡선(md.sys.motion.spring.fast.spatial)을 따라 부드럽게 바뀝니다. 누를 때 넓어지는 너비도 같은 곡선을 씁니다. 선택 색은 Compose ToggleButton 처럼 바로 바뀌고, outlined 버튼의 테두리 색만 서서히 사라집니다.",
            """
            buttonGroup().standard().size(ButtonGroupSize.Small).selection(ButtonGroupSelection.Single)
                .add(button().filled().text("일")).add(button().outlined().text("주")).add(button().filled().text("월"))
                .add(button().outlined().text("분기")).add(button().filled().text("년"))
                .element();
            buttonGroup().standard().size(ButtonGroupSize.Small).selection(ButtonGroupSelection.Single)
                .add(button().icon("star").filled().ariaLabel("별"))
                .add(button().icon("favorite").outlined().ariaLabel("좋아요"))
                .add(button().icon("bookmark").filled().ariaLabel("북마크"))
                .add(button().icon("share").outlined().ariaLabel("공유"))
                .add(button().icon("thumb_up").filled().ariaLabel("추천"))
                .element();
            """, div().style("display", "flex").style("flex-direction", "column").style("align-items", "flex-start").style("gap", "16px").add(bgMorph).add(bgIcon).element());
        later(5000, () -> {
            var m0 = child(bgMorph, 0); var m1 = child(bgMorph, 1); var i0 = child(bgIcon, 0);
            String tp = cs(m0).getPropertyValue("transition-property");
            assertTrue("fb: 버튼 그룹 모양 전환 transition [" + tp + "]", tp.contains("border-start-start-radius") && tp.contains("border-end-end-radius"));
            assertTrue("fb: 버튼 그룹 누름 너비 전환 유지", tp.contains("width"));
            assertTrue("fb: 버튼 그룹 전환 spring(fast spatial) 곡선", cs(m0).getPropertyValue("transition-timing-function").contains("linear("));
            Element out = inner(m1, ".outline");
            assertTrue("fb: outlined 버튼 테두리 모양 전환", out != null && cs(out).getPropertyValue("transition-property").contains("border-start-start-radius"));
            assertTrue("fb: outlined 버튼 테두리 색 전환", out != null && cs(out).getPropertyValue("transition-property").contains("border-color"));
            Element ib = inner(i0, ".icon-button");
            assertTrue("fb: icon 버튼 모양 전환", ib != null && cs(ib).getPropertyValue("transition-property").contains("border-start-start-radius"));
            if (!isAutomated()) return; // 사람이 볼 때는 자동 클릭하지 않는다
            m0.click(); // 선택: 20px(full) -> 12px
            String moving = transitions(m0);
            assertTrue("fb: 토글 시 모서리가 애니메이션됨 [" + moving + "]", moving.contains("radius"));
            i0.click();
            String iconMoving = transitions(ib);
            assertTrue("fb: icon 토글 시 모서리가 애니메이션됨 [" + iconMoving + "]", iconMoving.contains("radius"));
            later(600, () -> {
                assertEquals("fb: 토글 전환 뒤 모서리 12px", "12px", cs(m0).getPropertyValue("border-start-start-radius"));
                assertTrue("fb: 토글 전환 끝남", !radiusMoving(m0));
                var i1 = child(bgIcon, 1); // outlined icon 버튼: 선택 색을 host 배경으로 칠하므로 host 도 둥글어야 한다
                i1.click();
                later(600, () -> {
                    Element ib1 = inner(i1, ".icon-button");
                    assertEquals("fb: outlined icon 버튼 선택 시 host 모서리 둥금", cs(ib1).getPropertyValue("border-start-start-radius"), cs(i1).getPropertyValue("border-start-start-radius"));
                    assertTrue("fb: outlined icon 버튼 선택 시 host 모서리 0 아님", !"0px".equals(cs(i1).getPropertyValue("border-end-end-radius")));
                });
            });
        });
        // snackbar: 액션이 있으면 자동으로 닫히지 않음(가이드: 웹 접근성), 왼쪽 정렬 옵션
        var sbAct = snackbar().text("보관함으로 옮겼습니다").action("실행 취소").element();
        var sbStart = snackbar().text("왼쪽 정렬").timeout(0).element();
        sbStart.setAttribute("align", "start");
        body().add(sbAct); body().add(sbStart);
        later(6200, () -> {
            if (!isAutomated()) return;
            sbStart.show();
            later(100, () -> {
                assertEquals("fb: snackbar 왼쪽 정렬 8px", 8, (int) Math.round(sbStart.getBoundingClientRect().left));
                sbAct.show();
                later(4500, () -> {
                    assertTrue("fb: 액션 있는 snackbar 는 자동으로 닫히지 않음", sbAct.open);
                    sbAct.close();
                });
            });
        });

        // ---------- toast [M3 외 확장] ----------
        List<String> toastReasons = new ArrayList<>();
        int[] toastActions = {0};
        demo(section, "📘 Toast [M3 외 확장]", "데스크톱 웹용 알림입니다. 화면 오른쪽 위에 쌓이고(새 것이 아래), info·success 는 3초 뒤 자동으로 닫히며 warning·error 는 닫을 때까지 남습니다. 포인터를 올리면 타이머가 멈춥니다. M3 에는 없는 컴포넌트이므로 모바일·짧은 단일 알림에는 snackbar 를 쓰세요.",
            """
            toast().success().text("저장했습니다").show();
            toast().error().text("저장하지 못했습니다")
                .action("다시 시도").onAction(t -> retry())
                .show();
            """,
            div().style("display", "flex").style("gap", "12px")
                .add(button().outlined().text("info").onClick(e -> toast().info().text("새 버전이 있습니다").show()))
                .add(button().outlined().text("success").onClick(e -> toast().success().text("저장했습니다").show()))
                .add(button().outlined().text("warning").onClick(e -> toast().warning().text("세션이 곧 만료됩니다").show()))
                .add(button().outlined().text("error").onClick(e -> toast().error().text("저장하지 못했습니다").action("다시 시도").show()))
                .element());
        var tInfo = toast().info().text("정보 토스트").timeout(0).onClosed(toastReasons::add).element();
        var tErr = toast().error().text("오류 토스트").action("다시 시도").onAction(t -> toastActions[0]++).onClosed(toastReasons::add).element();
        assertEquals("fb: toast 태그명", "MD-TOAST", tInfo.tagName);
        assertTrue("fb: toast 속성 설정", "info".equals(tInfo.level) && "error".equals(tErr.level) && tInfo.timeout == 0 && "다시 시도".equals(tErr.action));
        later(150, () -> {
            if (!isAutomated()) return; // 사람이 볼 때는 토스트를 자동으로 띄우지 않는다. 데모 버튼으로 확인
            assertTrue("fb: toast 자동 닫힘 기본값", toast().info().element().effectiveTimeout == 3000 && toast().success().element().effectiveTimeout == 3000
                    && toast().warning().element().effectiveTimeout == 0 && tErr.effectiveTimeout == 0 && tErr.closeable);
            tInfo.show(); tErr.show();
            later(400, () -> {
                // Lit 은 DOM 에 붙을 때(connectedCallback) shadow root 를 만든다 — show() 로 스택에 붙은 뒤 확인한다
                assertTrue("fb: md-toast 업그레이드", Js.asPropertyMap(tInfo).get("shadowRoot") != null && Js.asPropertyMap(tErr).get("shadowRoot") != null);
                HTMLElement stack = Js.uncheckedCast(tInfo.parentElement);
                assertTrue("fb: toast 스택에 붙음", stack != null && "md-toast-stack".equals(stack.className) && tErr.parentElement == stack);
                assertEquals("fb: toast 스택 위 60px", "60px", cs(stack).getPropertyValue("top"));
                assertEquals("fb: toast 스택 오른쪽 20px", "20px", cs(stack).getPropertyValue("right"));
                assertTrue("fb: toast 새 것이 아래로 쌓임", tErr.getBoundingClientRect().top > tInfo.getBoundingClientRect().top);
                assertTrue("fb: toast handbook 클래스", tErr.classList.contains("ui-toast") && tErr.classList.contains("ui-toast-error") && tInfo.classList.contains("ui-toast-info"));
                assertTrue("fb: toast error role alert", "alert".equals(tErr.getAttribute("role")) && "assertive".equals(tErr.getAttribute("aria-live")));
                assertTrue("fb: toast info role status", "status".equals(tInfo.getAttribute("role")) && "polite".equals(tInfo.getAttribute("aria-live")));
                var ci = inner(tInfo, ".container");
                var ce = inner(tErr, ".container");
                assertEquals("fb: toast 모서리 8px", "8px", cs(ci).getPropertyValue("border-top-left-radius"));
                assertTrue("fb: toast 그림자(elevation 2)", !"none".equals(cs(ci).getPropertyValue("box-shadow")));
                assertEquals("fb: toast 본문 14px", "14px", cs(inner(tInfo, ".text")).getPropertyValue("font-size"));
                String bgInfo = cs(ci).getPropertyValue("background-color"), bgErr = cs(ce).getPropertyValue("background-color");
                assertTrue("fb: toast 레벨별 배경색 [" + bgInfo + " / " + bgErr + "]", !bgInfo.isEmpty() && !bgInfo.equals(bgErr) && !"rgba(0, 0, 0, 0)".equals(bgErr));
                ((HTMLElement) Js.uncheckedCast(inner(tInfo, ".close"))).click();
                ((HTMLElement) Js.uncheckedCast(inner(tErr, ".action"))).click();
                later(500, () -> {
                    assertTrue("fb: toast 닫기 버튼으로 닫힘", toastReasons.contains("dismiss") && !connected(tInfo));
                    assertTrue("fb: toast 액션 이벤트와 닫힘", toastActions[0] == 1 && toastReasons.contains("action") && !connected(tErr));
                });
            });
            var tAuto = toast().success().text("자동 닫힘").onClosed(toastReasons::add).show();
            var tHover = toast().info().text("포인터를 올리면 멈춤").timeout(500).show();
            tHover.dispatchEvent(new elemental2.dom.Event("pointerenter"));
            later(1000, () -> {
                assertTrue("fb: toast 포인터가 머무는 동안 닫히지 않음", tHover.open && !tHover.leaving);
                tHover.dispatchEvent(new elemental2.dom.Event("pointerleave"));
                later(1500, () -> assertTrue("fb: toast 포인터가 떠나면 다시 닫힘", !connected(tHover)));
            });
            later(3600, () -> {
                assertTrue("fb: toast 기본 3초 뒤 자동 닫힘", !connected(tAuto) && toastReasons.contains("timeout"));
                // 최대 개수: 이전 토스트가 모두 닫힌 뒤 스택이 빈 상태에서 확인한다
                List<String> overflow = new ArrayList<>();
                var o1 = toast().info().text("1").timeout(0).max(2).onClosed(overflow::add).show();
                var o2 = toast().info().text("2").timeout(0).max(2).onClosed(overflow::add).show();
                var o3 = toast().info().text("3").timeout(0).max(2).onClosed(overflow::add).show();
                later(400, () -> {
                    assertTrue("fb: toast 최대 개수를 넘으면 가장 오래된 것이 닫힘", !connected(o1) && connected(o2) && connected(o3) && overflow.contains("overflow"));
                    o2.close(); o3.close();
                });
            });
        });

        // ---------- tooltip (2.5.0.5): 기본 지연, 터치 길게 누르기, 하나만 열림, 제거 시 정리, 문구 교체, 가려짐(top layer) ----------
        var tAnchor = button().outlined().text("길게 누르기").element();
        var tAnchor2 = button().outlined().text("기본 지연").element();
        var hidAnchor = button().outlined().text("숨은 부모").element();
        int[] tClicks = {0};
        tAnchor.addEventListener("click", e -> tClicks[0]++);
        // overflow:hidden + transform 컨테이너(위치 기준과 잘림을 모두 만든다) 안의 앵커와 툴팁
        var clip = div().style("overflow", "hidden").style("transform", "translateZ(0)").style("width", "140px").style("height", "48px")
                .style("margin-top", "48px").style("display", "flex").style("align-items", "center").element();
        var clipAnchor = button().text().text("잘리는 곳").element();
        clip.appendChild(clipAnchor);
        var hiddenParent = div().style("visibility", "hidden").style("height", "0").element();
        demo(section, "📘 Tooltip - 터치·수명·가려짐", "터치에서는 길게(500ms) 누르면 열리고, 떼고 1.5초 뒤 닫힙니다. 짧은 탭·스크롤(움직임)로는 열리지 않고, 길게 누른 뒤의 click 은 앵커 동작을 일으키지 않습니다. 툴팁은 popover(top layer)로 표시되어 overflow·transform 조상이나 visibility:hidden 부모에 가려지지 않습니다. 요소를 DOM 에서 떼면 리스너와 타이머가 정리되고, text() 를 다시 부르면 문구가 바뀝니다.",
            """
            var tip = tooltip().plain().anchor(iconButton).text("저장").element();
            body().add(tip);           // document.body 아래에 두고 anchor 로 연결
            tip.text = "다른 이름으로 저장"; // 문구만 바꾸기(빌더라면 .text(...) 를 다시 호출)
            tip.remove();              // 떼면 앵커 리스너·타이머 정리
            """, div().style("display", "flex").style("gap", "12px").style("align-items", "center").style("flex-wrap", "wrap")
                .add(tAnchor).add(tAnchor2).add(hidAnchor).add(clip).add(hiddenParent).element());
        var tDef = tooltip().plain().anchor(tAnchor2).text("기본 지연 500ms").element();
        var tTouch = tooltip().plain().anchor(tAnchor).text("길게 누르면 보입니다").element();
        var tClip = tooltip().plain().anchor(clipAnchor).text("overflow 밖으로").element();
        var tHid = tooltip().plain().anchor(hidAnchor).text("숨은 부모 안의 툴팁").element();
        body().add(tDef); body().add(tTouch); clip.appendChild(tClip); hiddenParent.appendChild(tHid);
        var tTxt = tooltip().rich().subhead("제목").text("첫 문구").text("둘째 문구").element();
        assertTrue("fb: tooltip text 다시 부르면 문구 교체", "둘째 문구".equals(tTxt.text) && "제목둘째 문구".equals(tTxt.textContent));
        tTxt.text = "셋째 문구";
        assertTrue("fb: tooltip text 속성으로 문구 교체(subhead 유지)", tTxt.textContent.equals("제목셋째 문구"));
        assertEquals("fb: tooltip 기본 지연 500ms", 500, (int) tDef.delay);
        assertEquals("fb: tooltip 기본 길게 누르기 500ms", 500, (int) tTouch.longPressDelay);
        int T = 8000;
        // 1) 기본 지연: 마우스 hover 뒤 500ms
        later(T, () -> {
            if (!isAutomated()) return;
            tAnchor2.dispatchEvent(new elemental2.dom.Event("pointerenter"));
            later(300, () -> assertTrue("fb: tooltip 기본 지연 전(300ms)에는 닫혀 있음", !tDef.open));
            later(700, () -> { assertTrue("fb: tooltip 기본 지연 뒤 표시", tDef.open); tDef.hide(); });
        });
        // 2) 터치 길게 누르기: 500ms 뒤 열림, 떼면 click 차단, 1.5초 뒤 닫힘
        later(T + 800, () -> {
            if (!isAutomated()) return;
            var r = tAnchor.getBoundingClientRect(); double x = r.left + r.width / 2, y = r.top + r.height / 2;
            touch(tAnchor, "pointerenter", x, y); touch(tAnchor, "pointerdown", x, y);
            later(300, () -> assertTrue("fb: tooltip 길게 누르기 전(300ms)에는 닫혀 있음", !tTouch.open));
            later(650, () -> {
                assertTrue("fb: tooltip 터치 길게 누르면 열림", tTouch.open);
                touch(tAnchor, "pointerup", x, y); touch(tAnchor, "pointerleave", x, y);
                tAnchor.click();
                assertEquals("fb: tooltip 길게 누른 뒤 click 은 앵커 동작을 막음", 0, tClicks[0]);
                later(1000, () -> assertTrue("fb: tooltip 길게 누른 뒤 손을 떼도 1.5초 동안 유지", tTouch.open));
                later(1700, () -> assertTrue("fb: tooltip 길게 누른 뒤 떼고 1.5초 뒤 닫힘", !tTouch.open));
            });
        });
        // 3) 짧은 탭: 열지 않고 click 은 그대로. 탭이 만든 focusin 으로도 열지 않음
        later(T + 3300, () -> {
            if (!isAutomated()) return;
            var r = tAnchor.getBoundingClientRect(); double x = r.left + r.width / 2, y = r.top + r.height / 2;
            touch(tAnchor, "pointerdown", x, y);
            later(100, () -> {
                touch(tAnchor, "pointerup", x, y); touch(tAnchor, "pointerleave", x, y);
                tAnchor.dispatchEvent(new elemental2.dom.Event("focusin"));
                tAnchor.click();
                assertEquals("fb: tooltip 짧은 탭은 click 을 그대로 전달", 1, tClicks[0]);
            });
            later(800, () -> assertTrue("fb: tooltip 짧은 탭(과 탭 포커스)으로는 열지 않음", !tTouch.open));
        });
        // 4) 움직이면(스크롤) 취소, pointercancel 이면 취소
        later(T + 4200, () -> {
            if (!isAutomated()) return;
            var r = tAnchor.getBoundingClientRect(); double x = r.left + r.width / 2, y = r.top + r.height / 2;
            touch(tAnchor, "pointerdown", x, y);
            later(100, () -> touch(tAnchor, "pointermove", x, y + 30));
            later(700, () -> { assertTrue("fb: tooltip 터치가 움직이면(스크롤) 열지 않음", !tTouch.open); touch(tAnchor, "pointerup", x, y + 30); });
        });
        later(T + 5000, () -> {
            if (!isAutomated()) return;
            var r = tAnchor.getBoundingClientRect(); double x = r.left + r.width / 2, y = r.top + r.height / 2;
            touch(tAnchor, "pointerdown", x, y);
            later(100, () -> touch(tAnchor, "pointercancel", x, y));
            later(700, () -> assertTrue("fb: tooltip pointercancel 이면 열지 않음", !tTouch.open));
        });
        // 5) 한 번에 하나: plain 끼리
        later(T + 5800, () -> {
            if (!isAutomated()) return;
            tDef.show(); tTouch.show();
            assertTrue("fb: tooltip plain 둘 중 나중 것만 열림", tTouch.open && !tDef.open);
            tTouch.hide();
        });
        // 6) 수명: 표시 예약 중 떼었다 바로 붙여도 예약이 남지 않음, 뗀 동안 앵커에서 분리, 다시 붙이면 같은 앵커로 동작, 열린 채 떼면 닫힘
        later(T + 6000, () -> {
            if (!isAutomated()) return;
            tAnchor2.dispatchEvent(new elemental2.dom.Event("pointerenter"));
            later(100, () -> {
                tDef.remove();
                assertTrue("fb: tooltip 제거하면 앵커 리스너 해제", Js.asPropertyMap(tDef).get("_anchor") == null && tDef.anchor == tAnchor2);
                body().add(tDef);
            });
            later(800, () -> {
                assertTrue("fb: tooltip 제거하면 예약된 표시 타이머 정리", !tDef.open);
                tDef.hide(); // 이전 예약으로 열렸더라도 닫고, 다시 붙인 뒤의 hover 로만 열리는지 본다
                tAnchor2.dispatchEvent(new elemental2.dom.Event("pointerenter"));
            });
            later(1500, () -> {
                assertTrue("fb: tooltip 다시 붙이면 같은 앵커로 동작", tDef.open);
                tDef.remove();
                assertTrue("fb: tooltip 열린 채 제거하면 닫힘", !tDef.open);
                body().add(tDef);
            });
        });
        // 7) 가려짐: overflow:hidden + transform 컨테이너 안에 두어도 top layer 로 앵커 위에 보임, visibility:hidden 부모 안에서도 보임
        later(T + 7700, () -> {
            if (!isAutomated()) return;
            elemental2.core.Function siv = Js.uncheckedCast(Js.asPropertyMap(clip).get("scrollIntoView"));
            siv.call(clip, jsinterop.base.JsPropertyMap.of("block", "center"));
            tClip.show();
            later(150, () -> {
                assertTrue("fb: tooltip popover(top layer)로 표시", tClip.matches(":popover-open"));
                var ar = clipAnchor.getBoundingClientRect(); var tr = tClip.getBoundingClientRect(); var cr = clip.getBoundingClientRect();
                assertTrue("fb: tooltip transform 조상 안에서도 앵커 바로 위에 배치", Math.abs(tr.bottom - (ar.top - 4)) < 1.5);
                assertTrue("fb: tooltip overflow 컨테이너 밖에 위치", tr.bottom <= cr.top + 0.5);
                tClip.style.setProperty("pointer-events", "auto");
                Element hit = DomGlobal.document.elementFromPoint(tr.left + tr.width / 2, tr.top + tr.height / 2);
                tClip.style.removeProperty("pointer-events");
                assertTrue("fb: tooltip overflow·transform 조상에 잘리지 않고 보임", hit == tClip);
                tClip.hide();
                tHid.show();
                later(100, () -> {
                    assertEquals("fb: tooltip visibility:hidden 부모 안에서도 보임", "visible", cs(tHid).getPropertyValue("visibility"));
                    assertTrue("fb: tooltip visibility:hidden 부모 안에서도 크기 있음", tHid.getBoundingClientRect().height >= 24);
                    tHid.hide();
                    boolean outOfTopLayer = !tHid.matches(":popover-open"); // hidePopover 는 즉시, display 는 Lit 속성 반영(비동기) 뒤
                    later(100, () -> assertTrue("fb: tooltip 닫으면 top layer 에서 내려감", outOfTopLayer && "none".equals(cs(tHid).getPropertyValue("display"))));
                });
            });
        });
    }
}
