package dev.sayaya.ui.timepicker;

import elemental2.dom.*;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

import java.util.ArrayList;
import java.util.List;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.ButtonElementBuilder.button;
import static dev.sayaya.ui.elements.TimePickerElementBuilder.*;
import static org.jboss.elemento.Elements.body;
import static org.jboss.elemento.Elements.div;

/** M3 time picker(dial/input): 토큰 수치(computed style) + 시·분·오전오후 선택 + 확정/취소. 메시지 접두어 "tp: ". */
public class TimePickerComponentsTest {
    private static CSSStyleDeclaration cs(Element e) {
        elemental2.core.Function f = Js.uncheckedCast(Js.asPropertyMap(DomGlobal.window).get("getComputedStyle"));
        return Js.uncheckedCast(f.call(DomGlobal.window, e));
    }
    private static long w(Element e) { return Math.round(e.getBoundingClientRect().width); }
    private static long h(Element e) { return Math.round(e.getBoundingClientRect().height); }
    private static double cx(Element e) { var r = e.getBoundingClientRect(); return r.left + r.width / 2; }
    private static double cy(Element e) { var r = e.getBoundingClientRect(); return r.top + r.height / 2; }
    private static void later(int ms, Runnable r) { DomGlobal.setTimeout(t -> r.run(), ms); }
    private static HTMLElement q(Element root, String sel) { return (HTMLElement) root.querySelector(sel); }
    private static HTMLElement label(Element root, String value) { return q(root, ".label[data-value='" + value + "']:not(.inner)"); }
    private static void key(Element target, String key) {
        JsPropertyMap<Object> m = JsPropertyMap.of();
        m.set("key", key); m.set("bubbles", true); m.set("cancelable", true);
        target.dispatchEvent(new KeyboardEvent("keydown", Js.<KeyboardEventInit>uncheckedCast(m)));
    }

    public static void test() {
        printSectionHeader("M3 Time picker");
        printDescription("시·분·오전/오후를 고르는 시간 선택기입니다. dial 은 시계 다이얼을 눌러 고르고(시를 고르면 분으로 넘어감), input 은 숫자를 직접 입력합니다. 모바일에서 손으로 쉽게 고를 수 있도록 다이얼과 선택칸이 큽니다. modal(true)이면 스크림 위 대화상자로 열리고 확인을 눌러야 확정됩니다.");
        printSeparator();
        var section = section("Time picker");
        List<String> changes = new ArrayList<>();
        List<String> inputChanges = new ArrayList<>();
        List<String> modalChanges = new ArrayList<>();

        var dial = timePicker().dial().value(9, 30).onChange(changes::add).element();
        demo(section, "📘 Time picker dial", "256dp 다이얼(surface container highest)에서 48dp primary 핸들로 시를 고르면 분 선택으로 넘어갑니다. 시간 선택칸은 96×80dp(모서리 8dp)이며 선택된 칸은 primary container, 오전/오후는 52×80dp 이고 선택된 쪽이 tertiary container 입니다.",
            """
            timePicker().dial().value(9, 30)
                .onChange(hhmm -> save(hhmm))   // "09:30"
                .element();
            """, dial);
        var dial24 = timePicker().dial().hour24(true).value(15, 0).element();
        var input = timePicker().input().value(7, 5).onChange(inputChanges::add).element();
        demo(section, "📘 24시간 다이얼 / Time input", "hour24(true)는 안쪽 고리에 13~00시를 두고 오전/오후 선택을 숨깁니다. input 은 96×72dp 입력칸에 숫자를 입력하며 ↑/↓ 로 값을 바꿉니다.",
            """
            timePicker().dial().hour24(true).value(15, 0).element();
            timePicker().input().value(7, 5).onChange(hhmm -> save(hhmm)).element();
            """, div().style("display", "flex").style("gap", "16px").style("flex-wrap", "wrap").add(dial24).add(input).element());
        var modal = timePicker().dial().modal(true).headline("알람 시간").value(6, 0).onChange(modalChanges::add);
        body().add(modal.element());
        demo(section, "📘 Modal time picker", "modal(true)는 스크림 위 대화상자로 열리며 확인을 눌러야 onChange 가 불립니다. 취소·스크림·Esc 는 고른 값을 되돌립니다.",
            """
            var picker = timePicker().dial().modal(true).headline("알람 시간").onChange(hhmm -> save(hhmm));
            body().add(picker.element());
            picker.open(true);
            """, button().filled().text("시간 선택 열기").onClick(e -> modal.open(true)).element());

        var hour = q(dial, ".field.hour"); var minute = q(dial, ".field.minute");
        assertEquals("tp: 시 표시", "09", hour.textContent);
        assertEquals("tp: 분 표시", "30", minute.textContent);
        assertEquals("tp: 처음에는 시 선택", "true", hour.getAttribute("aria-pressed"));
        assertEquals("tp: 오전 선택", "true", q(dial, ".period.am").getAttribute("aria-pressed"));
        assertEquals("tp: 시 숫자 12개", 12, dial.querySelectorAll(".label").length);
        assertEquals("tp: 선택된 시 aria-selected", "true", label(dial, "9").getAttribute("aria-selected"));
        assertEquals("tp: 24시간 숫자 24개", 24, dial24.querySelectorAll(".label").length);
        assertEquals("tp: 24시간 오전/오후 숨김", "none", cs(q(dial24, ".periods")).getPropertyValue("display"));
        assertEquals("tp: 입력칸 값", "07", ((HTMLInputElement) q(input, ".field.hour")).value);

        later(400, () -> {
            var face = q(dial, ".face"); var handle = q(dial, ".handle");
            assertEquals("tp: 다이얼 256px", 256, (int) w(face));
            assertEquals("tp: 다이얼 원형", "9999px", cs(face).getPropertyValue("border-top-left-radius"));
            assertEquals("tp: 다이얼 surface-container-highest", "rgb(230, 224, 233)", cs(face).getPropertyValue("background-color"));
            assertEquals("tp: 핸들 48px", 48, (int) w(handle));
            assertEquals("tp: 핸들 primary", "rgb(103, 80, 164)", cs(handle).getPropertyValue("background-color"));
            assertEquals("tp: 트랙 2px", 2, q(dial, ".track").offsetWidth); // 회전 전 레이아웃 너비
            assertEquals("tp: 중심점 8px", 8, (int) w(q(dial, ".center")));
            assertTrue("tp: 핸들은 선택한 시 위", Math.abs(cx(handle) - cx(label(dial, "9"))) < 1.5 && Math.abs(cy(handle) - cy(label(dial, "9"))) < 1.5);
            assertEquals("tp: 선택된 숫자 on-primary", "rgb(255, 255, 255)", cs(label(dial, "9")).getPropertyValue("color"));
            assertEquals("tp: 시간 선택칸 96px", 96, (int) w(hour));
            assertEquals("tp: 시간 선택칸 높이 80px", 80, (int) h(hour));
            assertEquals("tp: 시간 선택칸 모서리 8px", "8px", cs(hour).getPropertyValue("border-top-left-radius"));
            assertEquals("tp: 선택 칸 primary-container", "rgb(234, 221, 255)", cs(hour).getPropertyValue("background-color"));
            assertEquals("tp: 기본 칸 surface-container-highest", "rgb(230, 224, 233)", cs(minute).getPropertyValue("background-color"));
            var periods = q(dial, ".periods");
            assertEquals("tp: 오전/오후 52px", 52, (int) w(periods));
            assertEquals("tp: 오전/오후 높이 80px", 80, (int) h(periods));
            assertEquals("tp: 오전/오후 테두리 outline", "rgb(121, 116, 126)", cs(periods).getPropertyValue("border-top-color"));
            assertEquals("tp: 선택 오전 tertiary-container", "rgb(255, 216, 228)", cs(q(dial, ".period.am")).getPropertyValue("background-color"));
            assertEquals("tp: 카드 모서리 28px", "28px", cs(q(dial, ".card")).getPropertyValue("border-top-left-radius"));

            label(dial, "3").click();
            assertTrue("tp: 시 고르면 onChange", changes.contains("03:30"));
            assertEquals("tp: 시 고르면 분 선택으로", "true", minute.getAttribute("aria-pressed"));
            assertTrue("tp: 분 숫자 00~55", label(dial, "0") != null && "00".equals(label(dial, "0").textContent) && label(dial, "55") != null);
            label(dial, "45").click();
            assertTrue("tp: 분 고르면 onChange", changes.contains("03:45"));
            q(dial, ".period.pm").click();
            assertTrue("tp: 오후로 바꾸면 15시", changes.contains("15:45"));
            assertEquals("tp: 오후 선택 표시", "true", q(dial, ".period.pm").getAttribute("aria-pressed"));
            hour.click();
            assertEquals("tp: 시 칸을 누르면 시 선택", "true", hour.getAttribute("aria-pressed"));
            q(dial24, ".label.inner[data-value='20']").click();
            assertEquals("tp: 24시간 안쪽 고리 20시", "20", q(dial24, ".field.hour").textContent);

            var hourInput = (HTMLInputElement) q(input, ".field.hour"); var minuteInput = (HTMLInputElement) q(input, ".field.minute");
            assertEquals("tp: 입력칸 96px", 96, (int) w(hourInput));
            assertEquals("tp: 입력칸 높이 72px", 72, (int) h(hourInput));
            assertEquals("tp: 입력 오전/오후 높이 72px", 72, (int) h(q(input, ".periods")));
            assertEquals("tp: 입력칸 inputmode numeric", "numeric", hourInput.getAttribute("inputmode"));
            hourInput.value = "11";
            hourInput.dispatchEvent(new Event("change"));
            assertTrue("tp: 시 입력하면 onChange", inputChanges.contains("11:05"));
            key(hourInput, "ArrowUp");
            assertTrue("tp: ↑ 로 한 시간 증가", inputChanges.contains("12:05"));
            minuteInput.value = "75";
            minuteInput.dispatchEvent(new Event("change"));
            assertEquals("tp: 범위 밖 분은 59로", "59", minuteInput.value);

            var mel = modal.element();
            assertEquals("tp: modal 닫힘 숨김", "none", cs(mel).getPropertyValue("display"));
            modal.open(true);
            later(200, () -> {
                var mcard = q(mel, ".card");
                assertEquals("tp: modal 열림", "flex", cs(mel).getPropertyValue("display"));
                assertEquals("tp: modal role dialog", "dialog", mcard.getAttribute("role"));
                assertEquals("tp: modal 제목", "알람 시간", q(mel, ".headline").textContent);
                assertEquals("tp: modal 스크림 0.32", "0.32", cs(q(mel, ".scrim")).getPropertyValue("opacity"));
                label(mel, "8").click();
                assertTrue("tp: modal 확인 전 onChange 없음", modalChanges.isEmpty());
                q(mel, ".action").click();
                assertTrue("tp: modal 취소는 되돌림", !modal.isOpen() && "06:00".equals(modal.value()));
                modal.open(true);
                label(mel, "8").click();
                q(mel, ".action.ok").click();
                assertTrue("tp: modal 확인으로 확정", !modal.isOpen() && modalChanges.contains("08:00"));
                modal.open(true);
                key(DomGlobal.document.activeElement, "Escape");
                assertTrue("tp: modal Esc 로 닫힘", !modal.isOpen());
            });
        });
    }
}
