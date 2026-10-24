package dev.sayaya.ui.datepicker;

import elemental2.dom.*;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

import java.util.ArrayList;
import java.util.List;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.ButtonElementBuilder.button;
import static dev.sayaya.ui.elements.DatePickerElementBuilder.*;
import static org.jboss.elemento.Elements.body;

/** M3 date picker(docked/modal): 토큰 수치(computed style) + grid 키보드 + 확정/취소. 메시지 접두어 "dp: ". */
public class DatePickerComponentsTest {
    private static CSSStyleDeclaration cs(Element e) {
        elemental2.core.Function f = Js.uncheckedCast(Js.asPropertyMap(DomGlobal.window).get("getComputedStyle"));
        return Js.uncheckedCast(f.call(DomGlobal.window, e));
    }
    private static long w(Element e) { return Math.round(e.getBoundingClientRect().width); }
    private static long h(Element e) { return Math.round(e.getBoundingClientRect().height); }
    private static void later(int ms, Runnable r) { DomGlobal.setTimeout(t -> r.run(), ms); }
    private static HTMLElement q(Element root, String sel) { return (HTMLElement) root.querySelector(sel); }
    private static HTMLElement day(Element root, String iso) { return q(root, "[data-date='" + iso + "']"); }
    private static String focused() { var a = DomGlobal.document.activeElement; return a == null ? null : a.getAttribute("data-date"); }
    private static void key(String key) {
        JsPropertyMap<Object> m = JsPropertyMap.of();
        m.set("key", key); m.set("bubbles", true); m.set("cancelable", true);
        DomGlobal.document.activeElement.dispatchEvent(new KeyboardEvent("keydown", Js.<KeyboardEventInit>uncheckedCast(m)));
    }

    public static void test() {
        printSectionHeader("M3 Date picker");
        printDescription("날짜나 기간을 고르는 달력입니다. docked 는 입력란 아래 등 화면에 붙어 있고 날짜를 고르면 바로 확정됩니다. modal 은 스크림 위 대화상자로, 확인을 눌러야 확정됩니다. 오늘과 선택한 날짜를 분명하게 표시하고, 키보드(방향키·PageUp/PageDown·Home/End·Enter)로도 고를 수 있습니다.");
        printSeparator();
        var section = section("Date picker");
        List<String> docked = new ArrayList<>();
        List<String> modalChanges = new ArrayList<>();

        var dp = datePicker().docked().today(2026, 10, 24).value(2026, 10, 15).onChange(docked::add).element();
        demo(section, "📘 Docked date picker", "너비 360dp, 모서리 28dp, surface container high 색의 달력입니다. 날짜는 40dp 원형이며 선택한 날짜는 primary, 오늘은 primary 1dp 테두리로 표시합니다. 날짜를 고르면 onChange 로 바로 전달됩니다.",
            """
            datePicker().docked().value(2026, 10, 15)
                .onChange(iso -> save(iso))   // "2026-10-15"
                .element();
            """, dp);

        var modal = datePicker().modal().headline("날짜 선택").today(2026, 10, 24).value(2026, 10, 15).onChange(modalChanges::add);
        body().add(modal.element());
        demo(section, "📘 Modal date picker", "스크림 위 대화상자로 열리며 헤더(120dp)에 고른 날짜가 보입니다. 확인을 눌러야 onChange 가 불리고, 취소·스크림·Esc 는 고른 날짜를 되돌립니다.",
            """
            var picker = datePicker().modal().headline("날짜 선택").onChange(iso -> save(iso));
            body().add(picker.element());
            picker.open(true);
            """, button().filled().text("날짜 선택 열기").onClick(e -> modal.open(true)).element());

        var grid = q(dp, "[role=grid]");
        assertTrue("dp: role grid", grid != null);
        assertEquals("dp: 요일 7개", 7, dp.querySelectorAll("[role=columnheader]").length);
        assertEquals("dp: 10월 31일", 31, dp.querySelectorAll("[data-date]").length);
        assertEquals("dp: 월 표시", "2026년 10월", q(dp, ".month").textContent);
        var firstRow = dp.querySelectorAll("[role=row]").item(1);
        assertEquals("dp: 1일은 목요일 칸", "2026-10-01", firstRow.querySelectorAll(":scope > *").item(4).getAttribute("data-date"));
        assertEquals("dp: 선택 aria-selected", "true", day(dp, "2026-10-15").getAttribute("aria-selected"));
        assertEquals("dp: 오늘 aria-current", "date", day(dp, "2026-10-24").getAttribute("aria-current"));
        assertEquals("dp: 날짜 aria-label", "2026년 10월 15일", day(dp, "2026-10-15").getAttribute("aria-label"));
        assertEquals("dp: 선택 날짜만 tab 순서", 1, dp.querySelectorAll("[tabindex='0']").length);

        later(400, () -> {
            var card = q(dp, ".card");
            var sel = day(dp, "2026-10-15"); var today = day(dp, "2026-10-24");
            assertEquals("dp: 컨테이너 360px", 360, (int) w(card));
            assertEquals("dp: 컨테이너 모서리 28px", "28px", cs(card).getPropertyValue("border-top-left-radius"));
            assertEquals("dp: 컨테이너 surface-container-high", "rgb(236, 230, 240)", cs(card).getPropertyValue("background-color"));
            assertTrue("dp: 컨테이너 그림자(elevation 3)", !"none".equals(cs(card).getPropertyValue("box-shadow")));
            assertEquals("dp: 날짜 40px", 40, (int) w(sel));
            assertEquals("dp: 날짜 높이 40px", 40, (int) h(sel));
            assertEquals("dp: 날짜 원형", "9999px", cs(sel).getPropertyValue("border-top-left-radius"));
            assertEquals("dp: 선택 primary", "rgb(103, 80, 164)", cs(sel).getPropertyValue("background-color"));
            assertEquals("dp: 선택 글자 on-primary", "rgb(255, 255, 255)", cs(sel).getPropertyValue("color"));
            assertEquals("dp: 오늘 테두리 1px", "1px", cs(today).getPropertyValue("border-top-width"));
            assertEquals("dp: 오늘 테두리 primary", "rgb(103, 80, 164)", cs(today).getPropertyValue("border-top-color"));
            assertEquals("dp: 오늘 글자 primary", "rgb(103, 80, 164)", cs(today).getPropertyValue("color"));

            sel.focus();
            key("ArrowRight");
            assertEquals("dp: → 다음 날", "2026-10-16", focused());
            key("ArrowDown");
            assertEquals("dp: ↓ 다음 주", "2026-10-23", focused());
            key("PageDown");
            assertEquals("dp: PageDown 다음 달", "2026-11-23", focused());
            assertEquals("dp: PageDown 월 표시", "2026년 11월", q(dp, ".month").textContent);
            key("PageUp");
            assertEquals("dp: PageUp 이전 달", "2026-10-23", focused());
            key("Home");
            assertEquals("dp: Home 주의 시작(일요일)", "2026-10-18", focused());
            key("Enter");
            assertTrue("dp: Enter 로 선택·onChange", docked.contains("2026-10-18"));
            assertEquals("dp: 선택 이동", "true", day(dp, "2026-10-18").getAttribute("aria-selected"));
            q(dp, ".next").click();
            assertEquals("dp: 다음 달 버튼", "2026년 11월", q(dp, ".month").textContent);
            day(dp, "2026-11-03").click();
            assertTrue("dp: 클릭으로 선택·onChange", docked.contains("2026-11-03"));

            var mel = modal.element();
            assertEquals("dp: modal 닫힘 숨김", "none", cs(mel).getPropertyValue("display"));
            modal.open(true);
            later(200, () -> {
                var mcard = q(mel, ".card");
                assertEquals("dp: modal 열림", "flex", cs(mel).getPropertyValue("display"));
                assertEquals("dp: modal role dialog", "dialog", mcard.getAttribute("role"));
                assertEquals("dp: modal aria-modal", "true", mcard.getAttribute("aria-modal"));
                assertEquals("dp: modal 제목 연결", q(mel, ".headline").id, mcard.getAttribute("aria-labelledby"));
                assertEquals("dp: modal 헤더 120px", 120, (int) h(q(mel, ".header")));
                assertEquals("dp: modal 헤더 날짜", "10월 15일", q(mel, ".header-date").textContent);
                assertEquals("dp: modal 스크림 0.32", "0.32", cs(q(mel, ".scrim")).getPropertyValue("opacity"));
                assertEquals("dp: 열면 선택 날짜 포커스", "2026-10-15", focused());
                day(mel, "2026-10-20").click();
                assertEquals("dp: modal 고르면 헤더 갱신", "10월 20일", q(mel, ".header-date").textContent);
                assertTrue("dp: modal 확인 전 onChange 없음", modalChanges.isEmpty());
                q(mel, ".action").click();
                assertTrue("dp: modal 취소는 되돌림", !modal.isOpen() && "2026-10-15".equals(modal.value()));
                modal.open(true);
                day(mel, "2026-10-20").click();
                q(mel, ".action.ok").click();
                assertTrue("dp: modal 확인으로 확정", !modal.isOpen() && modalChanges.contains("2026-10-20") && "2026-10-20".equals(modal.value()));
                modal.open(true);
                key("Escape");
                assertTrue("dp: modal Esc 로 닫힘", !modal.isOpen());
            });
        });
    }
}
