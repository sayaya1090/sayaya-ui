package dev.sayaya.ui.sheet;

import elemental2.dom.*;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

import java.util.ArrayList;
import java.util.List;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.BottomSheetElementBuilder.*;
import static dev.sayaya.ui.elements.ButtonElementBuilder.button;
import static org.jboss.elemento.Elements.body;
import static org.jboss.elemento.Elements.div;

/** M3 bottom sheet: 토큰(md-comp-sheet-bottom)·스펙 수치 + 열기/닫기/끌기/포커스. 메시지 접두어 "bs: ". */
public class BottomSheetComponentsTest {
    private static ShadowRoot root(HTMLElement host) { return Js.uncheckedCast(Js.asPropertyMap(host).get("shadowRoot")); }
    private static HTMLElement inner(HTMLElement host, String sel) { ShadowRoot r = root(host); return r == null ? null : Js.uncheckedCast(r.querySelector(sel)); }
    private static String css(HTMLElement host, String sel, String prop) { var e = inner(host, sel); return e == null ? "" : cs(e).getPropertyValue(prop); }
    private static long w(HTMLElement e) { return e == null ? -1 : Math.round(e.getBoundingClientRect().width); }
    private static long h(HTMLElement e) { return e == null ? -1 : Math.round(e.getBoundingClientRect().height); }
    private static void pointer(HTMLElement target, String type, double y) {
        JsPropertyMap<Object> m = JsPropertyMap.of();
        m.set("clientY", y); m.set("clientX", 10); m.set("bubbles", true); m.set("composed", true); m.set("pointerId", 1);
        PointerEventInit init = Js.uncheckedCast(m);
        target.dispatchEvent(new PointerEvent(type, init));
    }

    public static void test() {
        printSectionHeader("M3 Bottom sheet");
        printDescription("화면 아래에 붙어 보조 콘텐츠를 보여 주는 시트입니다. standard 는 뒤 화면을 계속 쓸 수 있고, modal 은 스크림 위에 떠서 닫을 때까지 뒤 화면을 막습니다. 드래그 핸들을 누르면 펼침/접힘이 바뀌고, 아래로 끌어 내리면 접히거나 닫힙니다.");
        printSeparator();
        var section = section("Bottom sheet");
        List<String> reasons = new ArrayList<>();

        var longList = div();
        for (int i = 1; i <= 40; i++) longList.add(div().style("padding", "12px 0").text("공유 대상 " + i));
        var modal = bottomSheet().modal().add(longList).onClosed(reasons::add).element();
        var std = bottomSheet().standard().add(div().style("padding", "8px 0 24px").text("재생 중: 노래 제목")).element();
        body().add(modal); body().add(std);
        demo(section, "📘 Modal bottom sheet", "스크림 위에 열리는 시트입니다. 처음에는 화면 높이의 50% 까지만 열리고, 핸들을 누르거나 위로 끌면 펼쳐집니다. 스크림 클릭·Esc·아래로 끌기로 닫히며, onClosed 로 닫힌 이유(scrim/escape/swipe/close)를 받습니다.",
            """
            var share = bottomSheet().modal().add(shareTargets)
                .onClosed(reason -> log(reason))
                .element();
            body().add(share);
            share.show();
            """, button().filled().text("공유 시트 열기").onClick(e -> modal.show()).element());
        demo(section, "📘 Standard bottom sheet", "스크림 없이 화면 아래에 붙어, 뒤 화면을 계속 쓸 수 있는 시트입니다(예: 음악 플레이어).",
            """
            var player = bottomSheet().standard().add(nowPlaying).element();
            body().add(player);
            player.show();
            """, button().outlined().text("플레이어 시트 열기/닫기").onClick(e -> { if (std.open) std.close(); else std.show(); }).element());

        assertEquals("bs: bottom-sheet 태그명", "MD-BOTTOM-SHEET", modal.tagName);
        assertTrue("bs: bottom-sheet 속성 설정", modal.modal && !std.modal && !modal.open);
        var probe = div().element(); body().add(probe);

        later(1500, () -> {
            assertTrue("bs: bottom-sheet 업그레이드", root(modal) != null);
            if (!isAutomated()) return; // 시트를 자동으로 열지 않는다. 데모 버튼으로 확인
            probe.style.setProperty("color", "var(--md-sys-color-surface-container-low, #f7f2fa)");
            String containerLow = cs(probe).getPropertyValue("color");
            double vw = DomGlobal.document.documentElement.clientWidth, vh = DomGlobal.window.innerHeight;
            modal.show();
            later(400, () -> {
                var c = inner(modal, ".container");
                assertEquals("bs: 너비 최대 640px", Math.min(640, (int) vw - 112), (int) w(c));
                var r = c.getBoundingClientRect();
                assertEquals("bs: 가운데 정렬", (int) Math.round((vw - r.width) / 2), (int) Math.round(r.left));
                assertEquals("bs: 화면 아래에 붙음", (int) vh, (int) Math.round(r.bottom));
                assertEquals("bs: 위쪽 모서리 28px", "28px", cs(c).getPropertyValue("border-top-left-radius"));
                assertEquals("bs: 아래쪽 모서리 0px", "0px", cs(c).getPropertyValue("border-bottom-left-radius"));
                assertEquals("bs: 컨테이너 surface-container-low", containerLow, cs(c).getPropertyValue("background-color"));
                assertTrue("bs: 그림자(elevation 1)", !"none".equals(cs(c).getPropertyValue("box-shadow")));
                var handle = inner(modal, ".handle");
                assertEquals("bs: 드래그 핸들 32x4", "32x4", w(handle) + "x" + h(handle));
                assertEquals("bs: 드래그 핸들 불투명도 0.4", "0.4", cs(handle).getPropertyValue("opacity"));
                assertEquals("bs: 드래그 핸들 위아래 패딩 22px", "22px 22px", css(modal, ".handle-area", "padding-top") + " " + css(modal, ".handle-area", "padding-bottom"));
                assertEquals("bs: 스크림 불투명도 0.32", "0.32", css(modal, ".scrim", "opacity"));
                assertEquals("bs: modal role dialog", "dialog", c.getAttribute("role"));
                assertTrue("bs: modal 처음 높이는 화면의 50% 이하", r.height <= vh * 0.5 + 1);
                Object active = Js.asPropertyMap(root(modal)).get("activeElement");
                assertTrue("bs: 열면 드래그 핸들로 포커스", active != null && ((Element) Js.uncheckedCast(active)).classList.contains("handle-area"));
                inner(modal, ".handle-area").click();
                later(400, () -> {
                    var r2 = inner(modal, ".container").getBoundingClientRect();
                    assertTrue("bs: 핸들 클릭으로 펼침", modal.expanded && r2.height > r.height);
                    assertEquals("bs: 펼친 시트 위 여백 56px(창 640 초과)", 56, (int) Math.round(r2.top));
                    var ha = inner(modal, ".handle-area");
                    pointer(ha, "pointerdown", 100); pointer(ha, "pointermove", 400); pointer(ha, "pointerup", 400);
                    later(300, () -> {
                        assertTrue("bs: 펼친 상태에서 아래로 끌면 접힘", modal.open && !modal.expanded);
                        pointer(ha, "pointerdown", 100); pointer(ha, "pointermove", 400); pointer(ha, "pointerup", 400);
                        later(300, () -> {
                            assertTrue("bs: 접힌 상태에서 아래로 끌면 닫힘", !modal.open && reasons.contains("swipe"));
                            modal.show();
                            later(300, () -> {
                                inner(modal, ".scrim").click();
                                assertTrue("bs: 스크림 클릭으로 닫힘", !modal.open && reasons.contains("scrim"));
                                modal.show();
                                later(300, () -> {
                                    KeyboardEventInit init = Js.uncheckedCast(JsPropertyMap.of("key", "Escape", "bubbles", true, "composed", true));
                                    inner(modal, ".container").dispatchEvent(new KeyboardEvent("keydown", init));
                                    assertTrue("bs: Esc 로 닫힘", !modal.open && reasons.contains("escape"));
                                    std.show();
                                    later(300, () -> {
                                        assertTrue("bs: standard 는 스크림 없음", inner(std, ".scrim") == null);
                                        assertEquals("bs: standard 는 뒤 화면 클릭을 막지 않음", "none", cs(std).getPropertyValue("pointer-events"));
                                        std.close();
                                        later(200, () -> assertEquals("bs: 닫힌 시트는 숨김", "none", cs(std).getPropertyValue("display")));
                                    });
                                });
                            });
                        });
                    });
                });
            });
        });
    }
}
