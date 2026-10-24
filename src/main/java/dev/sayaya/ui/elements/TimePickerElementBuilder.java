package dev.sayaya.ui.elements;

import elemental2.dom.*;
import elemental2.dom.DomGlobal;
import jsinterop.base.Js;
import org.jboss.elemento.*;

import java.util.function.Consumer;

import static org.jboss.elemento.Elements.*;

/**
 * M3 time picker — dial / input (sayaya-ui 자체 구현, elemento 엘리먼트만 사용).
 * <pre>{@code
 * import static dev.sayaya.ui.elements.TimePickerElementBuilder.*;
 *
 * var alarm = timePicker().dial().value(7, 30).onChange(hhmm -> save(hhmm)).element();   // "07:30"
 * var modal = timePicker().input().modal(true).headline("시간 입력").onChange(hhmm -> save(hhmm));
 * body().add(modal); modal.open(true);
 * }</pre>
 * 값은 24시간제 "HH:mm" 로 주고받는다. modal 이면 확인을 눌러야 확정되고, 아니면 고를 때마다 onChange 가 불린다.
 * 수치 출처: Compose Material3 TimePickerTokens — 다이얼 256dp 원형 surface container highest, 선택 핸들 48dp·트랙 2dp·중심점 8dp primary,
 * 시간 선택칸 96×80dp 모서리 small(8dp)(선택 primary container / 기본 surface container highest), 오전·오후 52×80dp 테두리 1dp outline(선택 tertiary container),
 * 컨테이너 모서리 extra large(28dp) surface container high. TimeInputTokens — 입력칸 96×72dp, 오전·오후 52×72dp.
 */
public final class TimePickerElementBuilder implements
        HTMLElementStyleMethods<HTMLDivElement, TimePickerElementBuilder>, HTMLElementVisibilityMethods<HTMLDivElement, TimePickerElementBuilder>,
        ElementAttributeMethods<HTMLDivElement, TimePickerElementBuilder>, ElementClassListMethods<HTMLDivElement, TimePickerElementBuilder>,
        ElementEventMethods<HTMLDivElement, TimePickerElementBuilder>, ElementIdMethods<HTMLDivElement, TimePickerElementBuilder>,
        ElementConsumerMethods<HTMLDivElement, TimePickerElementBuilder> {
    public static final String CLASS = "sayaya-time-picker";
    static final String STYLE_ID = "sayaya-time-picker-style";
    /** [파생] 숫자 배치 반경(바깥 고리/24시간 안쪽 고리). 토큰에 값이 없어 256dp 다이얼과 48dp 핸들에 맞춰 정했다 */
    static final int OUTER_RADIUS = 104, INNER_RADIUS = 68;
    private static int seq = 0;

    public static TimePickerPrepare timePicker() { return new TimePickerPrepare(); }
    public static final class TimePickerPrepare {
        /** 시계 다이얼로 고른다 */
        public TimePickerElementBuilder dial() { return new TimePickerElementBuilder(true); }
        /** 키보드로 숫자를 입력한다 */
        public TimePickerElementBuilder input() { return new TimePickerElementBuilder(false); }
    }

    private final HTMLContainerBuilder<HTMLDivElement> that = div();
    private final boolean dial;
    private final HTMLElement card, headline, hourField, minuteField, periodBox, am, pm, face, handle, track, actions;
    private HTMLInputElement hourInput, minuteInput;
    private int hour = 0, minute = 0;          // 24시간제
    private int committedHour = 0, committedMinute = 0;
    private boolean hour24 = false, modal = false, minuteMode = false;
    private Consumer<String> onChange;

    private TimePickerElementBuilder(boolean dial) {
        injectStyle();
        this.dial = dial;
        String id = "sayaya-time-" + (++seq);
        element().classList.add(CLASS, dial ? "dial" : "input");
        headline = div().css("headline").attr("id", id + "-headline").text(dial ? "Select time" : "Enter time").element();
        if (dial) {
            hourField = button().css("field", "hour").attr("type", "button").attr("aria-label", "시 선택").element();
            minuteField = button().css("field", "minute").attr("type", "button").attr("aria-label", "분 선택").element();
            hourField.addEventListener("click", e -> mode(false));
            minuteField.addEventListener("click", e -> mode(true));
        } else {
            hourInput = Elements.input(InputType.text).css("field", "hour").attr("inputmode", "numeric").attr("maxlength", "2").attr("aria-label", "시").element();
            minuteInput = Elements.input(InputType.text).css("field", "minute").attr("inputmode", "numeric").attr("maxlength", "2").attr("aria-label", "분").element();
            hourInput.addEventListener("change", e -> fromInputs());
            minuteInput.addEventListener("change", e -> fromInputs());
            hourInput.addEventListener("keydown", e -> step(e, true));
            minuteInput.addEventListener("keydown", e -> step(e, false));
            hourField = hourInput; minuteField = minuteInput;
        }
        am = button().css("period", "am").attr("type", "button").text("AM").element();
        pm = button().css("period", "pm").attr("type", "button").text("PM").element();
        am.addEventListener("click", e -> setPeriod(false));
        pm.addEventListener("click", e -> setPeriod(true));
        periodBox = div().css("periods").attr("role", "group").attr("aria-label", "오전/오후").add(am).add(pm).element();
        var row = div().css("time-row").add(hourField).add(span().css("separator").text(":")).add(minuteField).add(periodBox).element();
        card = div().css("card").add(headline).add(row).element();
        if (dial) {
            track = div().css("track").element();
            handle = div().css("handle").element();
            face = div().css("face").attr("role", "listbox").attr("aria-label", "시계").add(track).add(handle).add(div().css("center")).element();
            card.appendChild(div().css("face-wrap").add(face).element());
        } else { track = handle = face = null; }
        var cancel = button().css("action").attr("type", "button").text("취소").element();
        var ok = button().css("action", "ok").attr("type", "button").text("확인").element();
        cancel.addEventListener("click", e -> { hour = committedHour; minute = committedMinute; render(); open(false); });
        ok.addEventListener("click", e -> { fromInputsIfNeeded(); commit(); open(false); });
        actions = div().css("actions").add(cancel).add(ok).element();
        card.appendChild(actions);
        var scrim = div().css("scrim").element();
        scrim.addEventListener("click", e -> cancel.click());
        element().appendChild(scrim);
        element().appendChild(card);
        element().addEventListener("keydown", e -> { if (modal && "Escape".equals(((KeyboardEvent) Js.uncheckedCast(e)).key)) { cancel.click(); e.preventDefault(); } });
        render();
    }
    @Override public TimePickerElementBuilder that() { return this; }
    @Override public HTMLDivElement element() { return that.element(); }

    /** 24시간제 값 */
    public TimePickerElementBuilder value(int hour, int minute) {
        this.hour = committedHour = ((hour % 24) + 24) % 24;
        this.minute = committedMinute = Math.max(0, Math.min(59, minute));
        render();
        return this;
    }
    public TimePickerElementBuilder hour24(boolean hour24) { this.hour24 = hour24; render(); return this; }
    public TimePickerElementBuilder headline(String text) { headline.textContent = text; return this; }
    /** 스크림 위 대화상자로 띄우고 확인/취소로 확정한다 */
    public TimePickerElementBuilder modal(boolean modal) {
        this.modal = modal;
        if (modal) {
            element().classList.add("modal");
            card.setAttribute("role", "dialog"); card.setAttribute("aria-modal", "true"); card.setAttribute("aria-labelledby", headline.id);
        } else {
            element().classList.remove("modal");
            card.removeAttribute("role"); card.removeAttribute("aria-modal"); card.removeAttribute("aria-labelledby");
        }
        return this;
    }
    public TimePickerElementBuilder onChange(Consumer<String> cb) { this.onChange = cb; return this; }
    public String value() { return pad(committedHour) + ":" + pad(committedMinute); }
    public boolean isOpen() { return element().classList.contains("open"); }
    public TimePickerElementBuilder open(boolean open) {
        if (!modal) return this;
        if (open) { hour = committedHour; minute = committedMinute; mode(false); element().classList.add("open"); render(); hourField.focus(); }
        else element().classList.remove("open");
        return this;
    }

    private static String pad(int v) { return v < 10 ? "0" + v : String.valueOf(v); }
    private int displayHour() { return hour24 ? hour : (hour % 12 == 0 ? 12 : hour % 12); }
    private void changed() { if (!modal) commit(); render(); }
    private void commit() {
        committedHour = hour; committedMinute = minute;
        if (onChange != null) onChange.accept(value());
    }
    private void mode(boolean minutes) { minuteMode = minutes; render(); }
    private void setPeriod(boolean afternoon) {
        if (afternoon && hour < 12) hour += 12; else if (!afternoon && hour >= 12) hour -= 12;
        changed();
    }
    private void pick(int value) {
        if (minuteMode) { minute = value; changed(); return; }
        if (hour24) hour = value % 24;
        else { int h = value % 12; hour = hour >= 12 ? h + 12 : h; }
        minuteMode = true;
        changed();
    }
    private void fromInputsIfNeeded() { if (!dial) fromInputs(); }
    private void fromInputs() {
        int h = parse(hourInput.value, displayHour()), m = parse(minuteInput.value, minute);
        m = Math.max(0, Math.min(59, m));
        if (hour24) hour = Math.max(0, Math.min(23, h));
        else { h = Math.max(1, Math.min(12, h)) % 12; hour = hour >= 12 ? h + 12 : h; }
        minute = m;
        changed();
    }
    private static int parse(String s, int fallback) { try { return Integer.parseInt(s.trim()); } catch (Exception ex) { return fallback; } }
    private void step(Event e, boolean isHour) {
        String key = ((KeyboardEvent) Js.uncheckedCast(e)).key;
        int d = "ArrowUp".equals(key) ? 1 : "ArrowDown".equals(key) ? -1 : 0;
        if (d == 0) return;
        if (isHour) hour = (hour + d + 24) % 24; else minute = (minute + d + 60) % 60;
        changed();
        e.preventDefault();
    }

    private void render() {
        if (dial) {
            hourField.textContent = pad(displayHour());
            minuteField.textContent = pad(minute);
            hourField.setAttribute("aria-pressed", String.valueOf(!minuteMode));
            minuteField.setAttribute("aria-pressed", String.valueOf(minuteMode));
            renderFace();
        } else {
            hourInput.value = pad(displayHour());
            minuteInput.value = pad(minute);
        }
        periodBox.style.setProperty("display", hour24 ? "none" : "");
        am.setAttribute("aria-pressed", String.valueOf(hour < 12));
        pm.setAttribute("aria-pressed", String.valueOf(hour >= 12));
        element().classList.toggle("h24", hour24);
    }
    private void renderFace() {
        for (Element old = face.querySelector(".label"); old != null; old = face.querySelector(".label")) face.removeChild(old);
        int selValue; boolean inner = false;
        if (minuteMode) selValue = minute;
        else if (hour24) { selValue = hour; inner = hour == 0 || hour > 12; }
        else selValue = displayHour();
        for (int i = 1; i <= 12; i++) {
            int value = minuteMode ? (i % 12) * 5 : i;
            addLabel(value, minuteMode ? pad(value) : String.valueOf(value), i, OUTER_RADIUS, value == selValue && !inner);
        }
        if (hour24 && !minuteMode) for (int i = 1; i <= 12; i++) {
            int value = (i + 12) % 24;
            addLabel(value, pad(value), i, INNER_RADIUS, inner && value == selValue);
        }
        // 핸들·트랙: 분은 5분 단위가 아니어도 그 각도에 둔다
        double angle = minuteMode ? minute * 6 : (hour24 ? (hour % 12) * 30 : displayHour() * 30);
        int r = inner ? INNER_RADIUS : OUTER_RADIUS;
        double rad = Math.toRadians(angle);
        double x = 128 + r * Math.sin(rad), y = 128 - r * Math.cos(rad);
        handle.style.setProperty("left", (x - 24) + "px");
        handle.style.setProperty("top", (y - 24) + "px");
        track.style.setProperty("height", r + "px");
        track.style.setProperty("transform", "rotate(" + angle + "deg)");
        face.setAttribute("aria-label", minuteMode ? "분 선택" : "시 선택");
    }
    private void addLabel(int value, String text, int position, int radius, boolean selected) {
        double rad = Math.toRadians(position * 30);
        double x = 128 + radius * Math.sin(rad), y = 128 - radius * Math.cos(rad);
        var label = button().css("label").attr("type", "button").attr("role", "option").attr("data-value", String.valueOf(value))
                .attr("aria-selected", String.valueOf(selected)).attr("aria-label", text + (minuteMode ? "분" : "시")).text(text).element();
        label.style.setProperty("left", (x - 24) + "px");
        label.style.setProperty("top", (y - 24) + "px");
        if (radius == INNER_RADIUS) label.classList.add("inner");
        label.addEventListener("click", e -> pick(value));
        face.appendChild(label);
    }

    private static void injectStyle() {
        if (DomGlobal.document.getElementById(STYLE_ID) != null) return;
        HTMLStyleElement style = htmlElement("style", HTMLStyleElement.class).id(STYLE_ID).element();
        String c = "." + CLASS;
        style.textContent =
            c + "{display:inline-block;font-family:Roboto,system-ui,sans-serif}"
          + c + " .card{box-sizing:border-box;display:inline-flex;flex-direction:column;gap:20px;padding:24px;border-radius:28px;"
          + "background:var(--md-sys-color-surface-container-high,#ece6f0);color:var(--md-sys-color-on-surface,#1d1b20)}"
          + c + ".modal .card{box-shadow:0 1px 3px 0 rgba(0,0,0,.3),0 4px 8px 3px rgba(0,0,0,.15)}"
          + c + " .headline{font-size:12px;line-height:16px;font-weight:500;color:var(--md-sys-color-on-surface-variant,#49454f)}"
          + c + " .time-row{display:flex;align-items:center;gap:0}"
          // 시간 선택칸 96×80(dial) / 96×72(input), 모서리 8dp
          + c + " .field{box-sizing:border-box;width:96px;height:80px;border:0;border-radius:8px;padding:0;text-align:center;font:400 57px/64px Roboto,system-ui,sans-serif;"
          + "background:var(--md-sys-color-surface-container-highest,#e6e0e9);color:var(--md-sys-color-on-surface,#1d1b20);cursor:pointer}"
          + c + ".input .field{height:72px;font-size:45px;outline:none}"
          + c + ".input .field:focus," + c + " .field[aria-pressed=true]{background:var(--md-sys-color-primary-container,#eaddff);color:var(--md-sys-color-on-primary-container,#21005d)}"
          + c + ".input .field:focus{box-shadow:inset 0 0 0 2px var(--md-sys-color-primary,#6750a4)}"
          + c + " .separator{width:24px;text-align:center;font-size:57px;line-height:64px;color:var(--md-sys-color-on-surface,#1d1b20)}"
          // 오전·오후: 52×80(dial) / 52×72(input), 테두리 1dp outline, 선택 tertiary container
          + c + " .periods{box-sizing:border-box;display:flex;flex-direction:column;width:52px;height:80px;margin-left:12px;border:1px solid var(--md-sys-color-outline,#79747e);border-radius:8px;overflow:hidden}"
          + c + ".input .periods{height:72px}"
          + c + " .period{flex:1;border:0;background:none;cursor:pointer;font:500 16px/24px Roboto,system-ui,sans-serif;color:var(--md-sys-color-on-surface-variant,#49454f)}"
          + c + " .period+.period{border-top:1px solid var(--md-sys-color-outline,#79747e)}"
          + c + " .period[aria-pressed=true]{background:var(--md-sys-color-tertiary-container,#ffd8e4);color:var(--md-sys-color-on-tertiary-container,#31111d)}"
          // 다이얼 256dp 원형
          + c + " .face-wrap{display:flex;justify-content:center}"
          + c + " .face{position:relative;width:256px;height:256px;border-radius:9999px;background:var(--md-sys-color-surface-container-highest,#e6e0e9)}"
          + c + " .label{position:absolute;width:48px;height:48px;border:0;border-radius:50%;background:none;z-index:2;cursor:pointer;font:400 16px/24px Roboto,system-ui,sans-serif;color:var(--md-sys-color-on-surface,#1d1b20)}"
          + c + " .label.inner{font-size:14px}"
          + c + " .label[aria-selected=true]{color:var(--md-sys-color-on-primary,#ffffff)}"
          + c + " .handle{position:absolute;width:48px;height:48px;border-radius:9999px;background:var(--md-sys-color-primary,#6750a4);z-index:1}"
          + c + " .track{position:absolute;left:127px;bottom:128px;width:2px;transform-origin:50% 100%;background:var(--md-sys-color-primary,#6750a4)}"
          + c + " .center{position:absolute;left:124px;top:124px;width:8px;height:8px;border-radius:9999px;background:var(--md-sys-color-primary,#6750a4)}"
          + c + " .actions{display:none;justify-content:flex-end;gap:8px}"
          + c + ".modal .actions{display:flex}"
          + c + " .action{height:40px;padding:0 12px;border:0;border-radius:9999px;background:none;cursor:pointer;font:500 14px/20px Roboto,system-ui,sans-serif;color:var(--md-sys-color-primary,#6750a4)}"
          + c + " .scrim{display:none}"
          + c + ".modal{display:none}"
          + c + ".modal.open{display:flex;position:fixed;inset:0;z-index:1000;align-items:center;justify-content:center}"
          + c + ".modal.open .scrim{display:block;position:absolute;inset:0;background:var(--md-sys-color-scrim,#000);opacity:.32}"
          + c + ".modal .card{position:relative}";
        DomGlobal.document.head.appendChild(style);
    }
}
