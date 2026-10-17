package dev.sayaya.ui.sheet;

import dev.sayaya.ui.dom.MdSideSheetElement;
import elemental2.dom.*;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

import java.util.ArrayList;
import java.util.List;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.ButtonElementBuilder.button;
import static dev.sayaya.ui.elements.SideSheetElementBuilder.*;
import static org.jboss.elemento.Elements.body;
import static org.jboss.elemento.Elements.div;

/** M3 side sheet: 토큰·스펙 수치(computed style) + 열기/닫기/포커스/이벤트. 메시지 접두어 "ss: ". */
public class SideSheetComponentsTest {
    private static CSSStyleDeclaration cs(Element e) {
        elemental2.core.Function f = Js.uncheckedCast(Js.asPropertyMap(DomGlobal.window).get("getComputedStyle"));
        return Js.uncheckedCast(f.call(DomGlobal.window, e));
    }
    private static ShadowRoot root(HTMLElement host) { return Js.uncheckedCast(Js.asPropertyMap(host).get("shadowRoot")); }
    private static HTMLElement inner(HTMLElement host, String sel) { ShadowRoot r = root(host); return r == null ? null : Js.uncheckedCast(r.querySelector(sel)); }
    private static String css(HTMLElement host, String sel, String prop) { var e = inner(host, sel); return e == null ? "" : cs(e).getPropertyValue(prop); }
    private static long w(HTMLElement e) { return e == null ? -1 : Math.round(e.getBoundingClientRect().width); }
    private static long h(HTMLElement e) { return e == null ? -1 : Math.round(e.getBoundingClientRect().height); }
    private static void later(int ms, Runnable r) { DomGlobal.setTimeout(t -> r.run(), ms); }

    public static void test() {
        printSectionHeader("M3 Side sheet");
        printDescription("화면 옆에 보조 콘텐츠를 보여 주는 시트입니다. standard 는 본문 옆에 나란히, modal 은 스크림 위에 표시됩니다. 닫기 버튼은 항상 있고, modal 은 스크림 클릭과 Esc 로도 닫힙니다.");
        printSeparator();
        var section = section("Side sheet");
        List<String> reasons = new ArrayList<>();
        int[] backs = {0};

        var std = sideSheet().standard().headline("세부 정보").divider(true).open(true)
                .add(div().text("본문")).action(button().filled().text("저장")).action(button().outlined().text("취소")).element();
        var wide = sideSheet().standard().headline("넓은 시트").open(true).style("--md-side-sheet-width", "600px").element();
        var layout = div().style("display", "flex").style("height", "500px").add(div().style("flex", "1").text("메인")).add(std).add(wide).element();
        layout.style.setProperty("width", "100%");
        demo(section, "📘 Standard side sheet", "본문 옆에 놓이는 너비 256dp 시트입니다. divider(true)는 본문 쪽 구분선과 액션 위 구분선을 그립니다. 하단 액션 영역은 높이 72dp 입니다. 오른쪽 시트는 --md-side-sheet-width 를 600px 로 줘도 최대 400dp 로 제한됩니다.",
            """
            var details = sideSheet().standard().headline("세부 정보").divider(true).open(true)
                .add(div().text("본문"))
                .action(button().filled().text("저장")).action(button().outlined().text("취소"))
                .element();
            """, layout);

        var opener = button().filled().text("필터 열기").element();
        var modal = sideSheet().modal().headline("필터").add(div().text("필터 폼")).onClosed(reasons::add).element();
        var modalStart = sideSheet().modal().pivot(Pivot.Start).detached(true).back(true).headline("하위 단계")
                .onBack(s -> backs[0]++).element();
        body().add(modal); body().add(modalStart);
        opener.addEventListener("click", e -> modal.show());
        demo(section, "📘 Modal side sheet", "스크림 위에 열리는 시트입니다. 열면 닫기 버튼으로 포커스가 가고, 닫으면 원래 자리로 돌아옵니다. onClosed 로 닫힌 이유(close/scrim/escape)를 받습니다.",
            """
            var filters = sideSheet().modal().headline("필터").add(filterForm)
                .onClosed(reason -> log(reason))
                .element();
            body().add(filters);
            filters.show();
            """, opener);
        demo(section, "📘 Modal side sheet - 왼쪽, detached, 뒤로 버튼", "pivot(Start)으로 왼쪽에 붙이고, detached(true)로 화면 가장자리에서 16dp 띄웁니다. back(true)는 다단계 시트용 뒤로 버튼입니다.",
            """
            sideSheet().modal().pivot(Pivot.Start).detached(true).back(true).headline("하위 단계")
                .onBack(s -> previousStep())
                .element();
            """, button().outlined().text("하위 단계 시트 열기").onClick(e -> modalStart.show()).element());

        assertEquals("ss: side-sheet 태그명", "MD-SIDE-SHEET", std.tagName);
        assertTrue("ss: side-sheet 속성 설정", !std.modal && std.open && std.divider && modal.modal && "start".equals(modalStart.pivot)
                && modalStart.detached && modalStart.back && std.querySelectorAll("[slot='actions']").length == 2);

        later(1500, () -> {
            if (!isAutomated()) return; // 시트를 자동으로 열지 않는다. 데모 버튼으로 확인
            assertTrue("ss: side-sheet 업그레이드", root(std) != null);
            var c = inner(std, ".container");
            assertEquals("ss: standard 너비 256px", 256, (int) w(c));
            assertEquals("ss: standard 높이 100%", 500, (int) h(c));
            assertEquals("ss: standard 모서리 0px", "0px", cs(c).getPropertyValue("border-top-left-radius"));
            assertEquals("ss: standard 그림자 없음(elevation 0)", "none", cs(c).getPropertyValue("box-shadow"));
            assertEquals("ss: standard 구분선 1px", "1px", cs(c).getPropertyValue("border-left-width"));
            assertEquals("ss: 헤더 좌우 패딩 24px", "24px", css(std, ".header", "padding-left"));
            assertEquals("ss: 헤더 요소 간격 12px", "12px", css(std, ".header", "column-gap"));
            assertEquals("ss: 헤드라인 title-large 22px", "22px", css(std, ".headline", "font-size"));
            assertTrue("ss: 닫기 아이콘 버튼 항상 있음", inner(std, ".close") != null);
            var a = inner(std, ".actions");
            var firstAction = (HTMLElement) std.querySelector("[slot='actions']");
            assertEquals("ss: 액션 영역 높이 72px", 72, (int) h(a));
            assertEquals("ss: 액션 버튼 높이 32px(XSmall)", 32, (int) h(firstAction));
            assertTrue("ss: 액션 위 구분선 표시", !"none".equals(cs(a).getPropertyValue("box-shadow")));
            assertEquals("ss: 액션 위 패딩 16px", "16px", cs(a).getPropertyValue("padding-top"));
            assertEquals("ss: 액션 아래 패딩 24px", "24px", cs(a).getPropertyValue("padding-bottom"));
            assertEquals("ss: 액션 왼쪽 정렬", "flex-start", cs(a).getPropertyValue("justify-content"));
            assertEquals("ss: 본문 가로 스크롤 없음", "hidden", css(std, ".content", "overflow-x"));
            assertEquals("ss: 본문 세로 스크롤", "auto", css(std, ".content", "overflow-y"));
            assertEquals("ss: 최대 너비 400px", 400, (int) w(inner(wide, ".container")));

            modal.show();
            later(300, () -> {
                var m = inner(modal, ".container");
                assertEquals("ss: modal role dialog", "dialog", m.getAttribute("role"));
                assertEquals("ss: modal aria-modal", "true", m.getAttribute("aria-modal"));
                assertEquals("ss: modal 시작 모서리 16px", "16px", cs(m).getPropertyValue("border-top-left-radius"));
                assertEquals("ss: modal 끝 모서리 0px", "0px", cs(m).getPropertyValue("border-top-right-radius"));
                assertTrue("ss: modal 그림자(elevation 1)", !"none".equals(cs(m).getPropertyValue("box-shadow")));
                assertEquals("ss: modal 오른쪽 가장자리에 붙음", (int) DomGlobal.document.documentElement.clientWidth, (int) Math.round(m.getBoundingClientRect().right));
                assertEquals("ss: 스크림 불투명도 0.32", "0.32", css(modal, ".scrim", "opacity"));
                Object active = Js.asPropertyMap(root(modal)).get("activeElement");
                assertTrue("ss: 열면 닫기 버튼으로 포커스", active != null && ((Element) Js.uncheckedCast(active)).classList.contains("close"));
                KeyboardEventInit init = Js.uncheckedCast(JsPropertyMap.of("key", "Escape", "bubbles", true, "composed", true));
                m.dispatchEvent(new KeyboardEvent("keydown", init));
                assertTrue("ss: Esc 로 닫힘", !modal.open && reasons.contains("escape"));
                modal.show();
                later(200, () -> {
                    inner(modal, ".scrim").click();
                    assertTrue("ss: 스크림 클릭으로 닫힘", !modal.open && reasons.contains("scrim"));
                    modal.show();
                    later(200, () -> {
                        inner(modal, ".close").click();
                        assertTrue("ss: 닫기 버튼으로 닫힘", !modal.open && reasons.contains("close"));
                        modalStart.show();
                        later(300, () -> {
                            var s = inner(modalStart, ".container");
                            assertEquals("ss: detached 여백 16px", 16, (int) Math.round(s.getBoundingClientRect().left));
                            assertEquals("ss: detached 위 여백 16px", "16px", cs(s).getPropertyValue("top"));
                            assertEquals("ss: detached 모든 모서리 16px", "16px", cs(s).getPropertyValue("border-top-right-radius"));
                            assertEquals("ss: start 시트 왼쪽 모서리 16px", "16px", cs(s).getPropertyValue("border-top-left-radius"));
                            assertEquals("ss: 뒤로 아이콘 있을 때 시작 패딩 16px", "16px", css(modalStart, ".header", "padding-left"));
                            inner(modalStart, ".back").click();
                            assertEquals("ss: 뒤로 버튼 이벤트", 1, backs[0]);
                            modalStart.close();
                            std.open = false;
                            later(200, () -> assertEquals("ss: 닫힌 standard 는 숨김", "none", cs(std).getPropertyValue("display")));
                        });
                    });
                });
            });
        });
    }
}
