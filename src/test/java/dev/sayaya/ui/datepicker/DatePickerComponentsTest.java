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
import static org.jboss.elemento.Elements.div;

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
        yearAndRange(section);
    }

    private static CSSStyleDeclaration pseudo(Element e, String which) {
        elemental2.core.Function f = Js.uncheckedCast(Js.asPropertyMap(DomGlobal.window).get("getComputedStyle"));
        return Js.uncheckedCast(f.call(DomGlobal.window, e, which));
    }

    /** 연도 선택(SelectionYear)과 기간 선택(RangeSelection) */
    private static void yearAndRange(HTMLElement section) {
        List<String> ranges = new ArrayList<>();
        var yp = datePicker().docked().today(2026, 10, 24).value(2026, 10, 15).element();
        demo(section, "📘 연도 선택", "월 표시(2026년 10월 ▾)를 누르면 연도 목록으로 바뀝니다. 연도는 72×36dp 이며 선택한 연도는 primary 입니다. 연도를 고르면 같은 달의 달력으로 돌아옵니다.",
            """
            datePicker().docked().value(2026, 10, 15).element();   // 월 표시 → 연도 목록
            """, yp);
        var rp = datePicker().docked().range(true).today(2026, 10, 24).month(2026, 10).onRangeChange((a, b) -> ranges.add(a + "/" + b)).element();
        var mr = datePicker().modal().range(true).headline("기간 선택").today(2026, 10, 24).month(2026, 10).onRangeChange((a, b) -> ranges.add("modal:" + a + "/" + b));
        body().add(mr.element());
        demo(section, "📘 기간 선택", "range(true)는 시작일과 종료일을 차례로 고릅니다(거꾸로 골라도 순서를 맞춤). 범위는 높이 40dp secondary container 띠로, 시작·끝은 primary 원으로 표시합니다. modal 기간 선택은 헤더가 128dp 이며 확인을 눌러야 onRangeChange 가 불립니다.",
            """
            datePicker().docked().range(true)
                .onRangeChange((start, end) -> search(start, end))   // "2026-10-05", "2026-10-09"
                .element();
            """, div().style("display", "flex").style("gap", "16px").style("flex-wrap", "wrap").style("align-items", "flex-start")
                .add(rp).add(button().filled().text("기간 선택 열기").onClick(e -> mr.open(true))).element());

        later(1500, () -> {
            var month = q(yp, ".month");
            month.click();
            var years = q(yp, ".years");
            assertEquals("dp: 월 버튼 aria-expanded", "true", month.getAttribute("aria-expanded"));
            assertEquals("dp: 연도 목록 표시", "grid", cs(years).getPropertyValue("display"));
            assertEquals("dp: 연도 모드에서 달력 숨김", "none", cs(q(yp, "[role=grid]")).getPropertyValue("display"));
            assertEquals("dp: 연도 role listbox", "listbox", years.getAttribute("role"));
            assertEquals("dp: 연도 201개", 201, yp.querySelectorAll(".year").length);
            var y2026 = q(yp, ".year[data-year='2026']"); var y2027 = q(yp, ".year[data-year='2027']");
            assertEquals("dp: 연도 72px", 72, (int) w(y2026));
            assertEquals("dp: 연도 높이 36px", 36, (int) h(y2026));
            assertEquals("dp: 선택 연도 aria-selected", "true", y2026.getAttribute("aria-selected"));
            assertEquals("dp: 선택 연도 primary", "rgb(103, 80, 164)", cs(y2026).getPropertyValue("background-color"));
            assertEquals("dp: 선택 연도 글자 on-primary", "rgb(255, 255, 255)", cs(y2026).getPropertyValue("color"));
            assertEquals("dp: 다른 연도 on-surface-variant", "rgb(73, 69, 79)", cs(y2027).getPropertyValue("color"));
            q(yp, ".year[data-year='2030']").click();
            assertEquals("dp: 연도 고르면 달력으로", "2030년 10월", q(yp, ".month").textContent);
            assertEquals("dp: 연도 고르면 목록 닫힘", "none", cs(q(yp, ".years")).getPropertyValue("display"));

            day(rp, "2026-10-09").click();
            assertTrue("dp: 기간 시작만 고르면 이벤트 없음", ranges.isEmpty());
            day(rp, "2026-10-05").click();
            assertTrue("dp: 거꾸로 골라도 시작·끝 정렬", ranges.contains("2026-10-05/2026-10-09"));
            var start = day(rp, "2026-10-05"); var mid = day(rp, "2026-10-07"); var end = day(rp, "2026-10-09");
            assertTrue("dp: 기간 양 끝 aria-selected", "true".equals(start.getAttribute("aria-selected")) && "true".equals(end.getAttribute("aria-selected")));
            assertTrue("dp: 범위 안 날짜 표시", mid.classList.contains("in-range") && !"true".equals(mid.getAttribute("aria-selected")));
            assertEquals("dp: 범위 띠 secondary-container", "rgb(232, 222, 248)", pseudo(mid, "::before").getPropertyValue("background-color"));
            assertEquals("dp: 범위 띠 높이 40px", "40px", pseudo(mid, "::before").getPropertyValue("height"));
            assertEquals("dp: 범위 안 글자 on-secondary-container", "rgb(29, 25, 43)", cs(mid).getPropertyValue("color"));
            assertEquals("dp: 기간 끝 primary 원", "rgb(103, 80, 164)", pseudo(end, "::after").getPropertyValue("background-color"));
            assertEquals("dp: 기간 끝 글자 on-primary", "rgb(255, 255, 255)", cs(end).getPropertyValue("color"));
            assertTrue("dp: 시작 띠는 가운데부터", start.classList.contains("range-start") && end.classList.contains("range-end"));
            day(rp, "2026-10-20").click();
            assertTrue("dp: 새로 고르면 기간 다시 시작", "true".equals(day(rp, "2026-10-20").getAttribute("aria-selected"))
                    && !"true".equals(day(rp, "2026-10-05").getAttribute("aria-selected")) && rp.querySelectorAll(".in-range").length == 0);

            mr.open(true);
            later(200, () -> {
                var mel = mr.element();
                assertEquals("dp: 기간 modal 헤더 128px", 128, (int) h(q(mel, ".header")));
                day(mel, "2026-10-03").click();
                day(mel, "2026-10-06").click();
                assertEquals("dp: 기간 modal 헤더 범위", "10월 3일 – 10월 6일", q(mel, ".header-date").textContent);
                assertTrue("dp: 기간 modal 확인 전 이벤트 없음", ranges.stream().noneMatch(x -> x.startsWith("modal:")));
                q(mel, ".action.ok").click();
                assertTrue("dp: 기간 modal 확인으로 확정", ranges.contains("modal:2026-10-03/2026-10-06") && "2026-10-03".equals(mr.rangeStart()) && "2026-10-06".equals(mr.rangeEnd()));
            });
        });
    }
}
