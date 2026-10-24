package dev.sayaya.ui.elements;

import dev.sayaya.ui.elements.interfaces.Openable;
import dev.sayaya.ui.elements.interfaces.HasLabels;
import dev.sayaya.ui.elements.interfaces.HasLocale;
import dev.sayaya.ui.elements.interfaces.UiLabel;
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
public final class TimePickerElementBuilder extends CustomElementBuilder<TimePickerElementBuilder> implements
        Openable<TimePickerElementBuilder>, HasLabels<TimePickerElementBuilder.Label, TimePickerElementBuilder>, HasLocale<TimePickerElementBuilder> {
    public static final String CLASS = "sayaya-time-picker";
    static final String STYLE_ID = "sayaya-time-picker-style";
    /** [파생] 숫자 배치 반경(바깥 고리/24시간 안쪽 고리). 토큰에 값이 없어 256dp 다이얼과 48dp 핸들에 맞춰 정했다 */
    static final int OUTER_RADIUS = 104, INNER_RADIUS = 68;
    private static int seq = 0;
    /** UI 문구. 기본은 영어이고 {@link #label(Label, String)} 로 덮어쓴다 */
    public enum Label implements UiLabel {
        SelectTime("Select time"), EnterTime("Enter time"), SelectHour("Select hour"), SelectMinute("Select minute"),
        Hour("Hour"), Minute("Minute"), Period("AM/PM"), Clock("Clock"), HoursSuffix("hours"), MinutesSuffix("minutes"),
        Cancel("Cancel"), Ok("OK"), SwitchToInput("Switch to text input"), SwitchToClock("Switch to clock");
        public final String english;
        Label(String english) { this.english = english; }
        @Override public String english() { return english; }
    }

    public static TimePickerPrepare timePicker() { return new TimePickerPrepare(); }
    public static final class TimePickerPrepare {
        /** 시계 다이얼로 고른다 */
        public TimePickerElementBuilder dial() { return new TimePickerElementBuilder(true); }
        /** 키보드로 숫자를 입력한다 */
        public TimePickerElementBuilder input() { return new TimePickerElementBuilder(false); }
    }

    private boolean dial;
    private final HTMLElement card, headline, periodBox, am, pm, face, handle, track, actions, row, separator, modeBtn, hourBtn, minuteBtn;
    private final HTMLInputElement hourInput, minuteInput;
    private HTMLElement hourField, minuteField; // 현재 모드의 시·분 칸
    private int hour = 0, minute = 0;          // 24시간제
    private int committedHour = 0, committedMinute = 0;
    private boolean modal = false, minuteMode = false;
    private Boolean hour24Explicit = null;
    private String locale = null, customHeadline = null;
    private final java.util.Map<Label, String> labels = new java.util.EnumMap<>(Label.class);
    private HTMLElement cancelBtn, okBtn;
    private Consumer<String> onChange;

    private TimePickerElementBuilder(boolean dial) {
        injectStyle();
        this.dial = dial;
        String id = "sayaya-time-" + (++seq);
        element().classList.add(CLASS);
        headline = div().css("headline").attr("id", id + "-headline").element();
        // dial 의 시·분 선택칸(버튼)과 input 의 시·분 입력칸을 모두 만들어 두고 모드에 따라 바꿔 끼운다
        hourBtn = button().css("field", "hour").attr("type", "button").element();
        minuteBtn = button().css("field", "minute").attr("type", "button").element();
        hourBtn.addEventListener("click", e -> mode(false));
        minuteBtn.addEventListener("click", e -> mode(true));
        hourInput = Elements.input(InputType.text).css("field", "hour").attr("inputmode", "numeric").attr("maxlength", "2").element();
        minuteInput = Elements.input(InputType.text).css("field", "minute").attr("inputmode", "numeric").attr("maxlength", "2").element();
        hourInput.addEventListener("change", e -> fromInputs());
        minuteInput.addEventListener("change", e -> fromInputs());
        hourInput.addEventListener("keydown", e -> step(e, true));
        minuteInput.addEventListener("keydown", e -> step(e, false));
        am = button().css("period", "am").attr("type", "button").element();
        pm = button().css("period", "pm").attr("type", "button").element();
        am.addEventListener("click", e -> setPeriod(false));
        pm.addEventListener("click", e -> setPeriod(true));
        periodBox = div().css("periods").attr("role", "group").add(am).add(pm).element();
        separator = span().css("separator").text(":").element();
        row = div().css("time-row").element();
        card = div().css("card").add(headline).add(row).element();
        track = div().css("track").element();
        handle = div().css("handle").element();
        face = div().css("face").attr("role", "listbox").add(track).add(handle).add(div().css("center")).element();
        card.appendChild(div().css("face-wrap").add(face).element());
        var cancel = button().css("action").attr("type", "button").element();
        var ok = button().css("action", "ok").attr("type", "button").element();
        cancelBtn = cancel; okBtn = ok;
        cancel.addEventListener("click", e -> { hour = committedHour; minute = committedMinute; render(); open(false); });
        ok.addEventListener("click", e -> { fromInputsIfNeeded(); commit(); open(false); });
        // M3: 키보드 아이콘으로 input 으로, 시계 아이콘으로 dial 로 전환한다
        modeBtn = button().css("mode").attr("type", "button").add(span().css("icon").attr("aria-hidden", "true")).element();
        modeBtn.addEventListener("click", e -> inputMode(this.dial)); // 생성자 매개변수 dial 이 아니라 현재 모드(필드)
        actions = div().css("actions").add(modeBtn).add(cancel).add(ok).element();
        card.appendChild(actions);
        var scrim = div().css("scrim").element();
        scrim.addEventListener("click", e -> cancel.click());
        element().appendChild(scrim);
        element().appendChild(card);
        element().addEventListener("keydown", e -> { if (modal && "Escape".equals(((KeyboardEvent) Js.uncheckedCast(e)).key)) { cancel.click(); e.preventDefault(); } });
        applyMode();
        render();
    }
    private void applyMode() {
        hourField = dial ? hourBtn : hourInput;
        minuteField = dial ? minuteBtn : minuteInput;
        while (row.firstChild != null) row.removeChild(row.firstChild);
        row.appendChild(hourField); row.appendChild(separator); row.appendChild(minuteField); row.appendChild(periodBox);
        setClass("dial", dial); setClass("input", !dial);
    }
    /** input 모드(키보드로 숫자 입력)와 dial 모드를 전환한다. 입력하던 값은 반영된다 */
    public TimePickerElementBuilder inputMode(boolean on) {
        if (on == !dial) return this;
        if (!dial) fromInputs();
        dial = !on;
        minuteMode = false;
        applyMode();
        render();
        if (isOpen()) hourField.focus();
        return this;
    }
    public boolean isInputMode() { return !dial; }
    @Override public TimePickerElementBuilder that() { return this; }

    /** 24시간제 값 */
    public TimePickerElementBuilder value(int hour, int minute) {
        this.hour = committedHour = ((hour % 24) + 24) % 24;
        this.minute = committedMinute = Math.max(0, Math.min(59, minute));
        render();
        return this;
    }
    /** 24시간제 여부. 지정하지 않으면 locale 기본 시계(hourCycle)를 따른다 */
    public TimePickerElementBuilder hour24(boolean hour24) { this.hour24Explicit = hour24; render(); return this; }
    public TimePickerElementBuilder headline(String text) { customHeadline = text; render(); return this; }
    /** 오전/오후 표기와 12/24시간 기본값의 기준 locale. 기본: Locales.setDefault → html lang → 브라우저 언어 */
    public TimePickerElementBuilder locale(String locale) { this.locale = locale; render(); return this; }
    /** UI 문구 덮어쓰기(기본 영어) */
    public TimePickerElementBuilder label(Label label, String text) { labels.put(label, text); render(); return this; }
    private String text(Label l) { String v = labels.get(l); return v == null ? l.english : v; }
    private boolean h24() { return hour24Explicit != null ? hour24Explicit : !Locales.hour12(locale); }
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
    private int displayHour() { return h24() ? hour : (hour % 12 == 0 ? 12 : hour % 12); }
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
        if (h24()) hour = value % 24;
        else { int h = value % 12; hour = hour >= 12 ? h + 12 : h; }
        minuteMode = true;
        changed();
    }
    private void fromInputsIfNeeded() { if (!dial) fromInputs(); }
    private void fromInputs() {
        int h = parse(hourInput.value, displayHour()), m = parse(minuteInput.value, minute);
        m = Math.max(0, Math.min(59, m));
        if (h24()) hour = Math.max(0, Math.min(23, h));
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
        headline.textContent = customHeadline != null ? customHeadline : text(dial ? Label.SelectTime : Label.EnterTime);
        am.textContent = Locales.dayPeriod(locale, false);
        pm.textContent = Locales.dayPeriod(locale, true);
        periodBox.setAttribute("aria-label", text(Label.Period));
        if (!minuteMode) face.setAttribute("aria-label", text(Label.SelectHour));
        modeBtn.firstElementChild.textContent = dial ? "keyboard" : "schedule";
        modeBtn.setAttribute("aria-label", text(dial ? Label.SwitchToInput : Label.SwitchToClock));
        cancelBtn.textContent = text(Label.Cancel); okBtn.textContent = text(Label.Ok);
        hourField.setAttribute("aria-label", text(dial ? Label.SelectHour : Label.Hour));
        minuteField.setAttribute("aria-label", text(dial ? Label.SelectMinute : Label.Minute));
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
        periodBox.style.setProperty("display", h24() ? "none" : "");
        am.setAttribute("aria-pressed", String.valueOf(hour < 12));
        pm.setAttribute("aria-pressed", String.valueOf(hour >= 12));
        element().classList.toggle("h24", h24());
    }
    private void renderFace() {
        for (Element old = face.querySelector(".label"); old != null; old = face.querySelector(".label")) face.removeChild(old);
        int selValue; boolean inner = false;
        if (minuteMode) selValue = minute;
        else if (h24()) { selValue = hour; inner = hour >= 12; } // M3: 0–11 은 바깥, 12–23 은 안쪽 다이얼
        else selValue = displayHour();
        boolean h24Hours = h24() && !minuteMode;
        for (int i = 1; i <= 12; i++) {
            int value = minuteMode ? (i % 12) * 5 : (h24Hours ? i % 12 : i);
            addLabel(value, minuteMode || h24Hours ? pad(value) : String.valueOf(value), i, OUTER_RADIUS, value == selValue && !inner);
        }
        if (h24Hours) for (int i = 1; i <= 12; i++) {
            int value = i % 12 + 12;
            addLabel(value, pad(value), i, INNER_RADIUS, inner && value == selValue);
        }
        // 핸들·트랙: 분은 5분 단위가 아니어도 그 각도에 둔다
        double angle = minuteMode ? minute * 6 : (h24() ? (hour % 12) * 30 : displayHour() * 30);
        int r = inner ? INNER_RADIUS : OUTER_RADIUS;
        double rad = Math.toRadians(angle);
        double x = 128 + r * Math.sin(rad), y = 128 - r * Math.cos(rad);
        handle.style.setProperty("left", (x - 24) + "px");
        handle.style.setProperty("top", (y - 24) + "px");
        track.style.setProperty("height", r + "px");
        track.style.setProperty("transform", "rotate(" + angle + "deg)");
        face.setAttribute("aria-label", text(minuteMode ? Label.SelectMinute : Label.SelectHour));
    }
    private void addLabel(int value, String text, int position, int radius, boolean selected) {
        double rad = Math.toRadians(position * 30);
        double x = 128 + radius * Math.sin(rad), y = 128 - radius * Math.cos(rad);
        var label = button().css("label").attr("type", "button").attr("role", "option").attr("data-value", String.valueOf(value))
                .attr("aria-selected", String.valueOf(selected)).attr("aria-label", text + " " + text(minuteMode ? Label.MinutesSuffix : Label.HoursSuffix)).text(text).element();
        label.style.setProperty("left", (x - 24) + "px");
        label.style.setProperty("top", (y - 24) + "px");
        if (radius == INNER_RADIUS) label.classList.add("inner");
        label.addEventListener("click", e -> pick(value));
        face.appendChild(label);
    }

    private static void injectStyle() {
        HTMLStyleElement style = newStyle(STYLE_ID);
        if (style == null) return;
        String c = "." + CLASS;
        style.textContent =
            c + "{display:inline-block;font-family:Roboto,system-ui,sans-serif}"
          + c + " .card{box-sizing:border-box;display:inline-flex;flex-direction:column;gap:20px;padding:24px;border-radius:var(--md-time-picker-container-shape,var(--md-sys-shape-corner-extra-large,28px));"
          + "background:var(--md-sys-color-surface-container-high,#ece6f0);color:var(--md-sys-color-on-surface,#1d1b20)}"
          + c + ".modal .card{box-shadow:0 1px 3px 0 rgba(0,0,0,.3),0 4px 8px 3px rgba(0,0,0,.15)}"
          + c + " .headline{font-size:12px;line-height:16px;font-weight:500;color:var(--md-sys-color-on-surface-variant,#49454f)}"
          + c + " .time-row{display:flex;align-items:center;gap:0}"
          // 시간 선택칸 96×80(dial) / 96×72(input), 모서리 8dp
          + c + " .field{box-sizing:border-box;width:96px;height:80px;border:0;border-radius:var(--md-time-picker-time-selector-shape,var(--md-sys-shape-corner-small,8px));padding:0;text-align:center;font:400 57px/64px Roboto,system-ui,sans-serif;"
          + "background:var(--md-sys-color-surface-container-highest,#e6e0e9);color:var(--md-sys-color-on-surface,#1d1b20);cursor:pointer}"
          + c + ".input .field{height:72px;font-size:45px;outline:none}"
          + c + ".input .field:focus," + c + " .field[aria-pressed=true]{background:var(--md-sys-color-primary-container,#eaddff);color:var(--md-sys-color-on-primary-container,#21005d)}"
          + c + ".input .field:focus{box-shadow:inset 0 0 0 2px var(--md-sys-color-primary,#6750a4)}"
          + c + " .separator{width:24px;text-align:center;font-size:57px;line-height:64px;color:var(--md-sys-color-on-surface,#1d1b20)}"
          // 오전·오후: 52×80(dial) / 52×72(input), 테두리 1dp outline, 선택 tertiary container
          + c + " .periods{box-sizing:border-box;display:flex;flex-direction:column;width:52px;height:80px;margin-left:12px;border:1px solid var(--md-sys-color-outline,#79747e);border-radius:var(--md-time-picker-period-selector-shape,var(--md-sys-shape-corner-small,8px));overflow:hidden}"
          + c + ".input .periods{height:72px}"
          + c + " .period{flex:1;border:0;background:none;cursor:pointer;font:500 16px/24px Roboto,system-ui,sans-serif;color:var(--md-sys-color-on-surface-variant,#49454f)}"
          + c + " .period+.period{border-top:1px solid var(--md-sys-color-outline,#79747e)}"
          + c + " .period[aria-pressed=true]{background:var(--md-sys-color-tertiary-container,#ffd8e4);color:var(--md-sys-color-on-tertiary-container,#31111d)}"
          // 다이얼 256dp 원형
          + c + " .face-wrap{display:flex;justify-content:center}"
          + c + " .face{position:relative;width:256px;height:256px;border-radius:var(--md-time-picker-clock-dial-shape,var(--md-sys-shape-corner-full,9999px));background:var(--md-sys-color-surface-container-highest,#e6e0e9)}"
          + c + " .label{position:absolute;width:48px;height:48px;border:0;border-radius:var(--md-time-picker-clock-dial-label-shape,var(--md-sys-shape-corner-full,50%));background:none;z-index:2;cursor:pointer;font:400 16px/24px Roboto,system-ui,sans-serif;color:var(--md-sys-color-on-surface,#1d1b20)}"
          + c + " .label.inner{font-size:14px}"
          + c + " .label[aria-selected=true]{color:var(--md-sys-color-on-primary,#ffffff)}"
          + c + " .handle{position:absolute;width:48px;height:48px;border-radius:var(--md-time-picker-clock-dial-selector-handle-shape,var(--md-sys-shape-corner-full,9999px));background:var(--md-sys-color-primary,#6750a4);z-index:1}"
          + c + " .track{position:absolute;left:127px;bottom:128px;width:2px;transform-origin:50% 100%;background:var(--md-sys-color-primary,#6750a4)}"
          + c + " .center{position:absolute;left:124px;top:124px;width:8px;height:8px;border-radius:var(--md-time-picker-clock-dial-selector-center-shape,var(--md-sys-shape-corner-full,9999px));background:var(--md-sys-color-primary,#6750a4)}"
          + c + " .actions{display:none;align-items:center;justify-content:flex-end;gap:8px}"
          + c + ".modal .actions{display:flex}"
          + c + " .mode{margin-inline-end:auto;width:48px;height:48px;border:0;padding:0;background:none;cursor:pointer;display:inline-flex;align-items:center;justify-content:center;"
          + "color:var(--md-sys-color-on-surface-variant,#49454f);border-radius:var(--md-time-picker-icon-button-shape,var(--md-sys-shape-corner-full,9999px))}"
          + c + " .mode .icon{font-family:'Material Symbols Outlined';font-size:24px;line-height:1;-webkit-font-feature-settings:'liga';font-feature-settings:'liga'}"
          + c + ".input .face-wrap{display:none}"
          + c + " .action{height:40px;padding:0 12px;border:0;border-radius:var(--md-time-picker-action-shape,var(--md-sys-shape-corner-full,9999px));background:none;cursor:pointer;font:500 14px/20px Roboto,system-ui,sans-serif;color:var(--md-sys-color-primary,#6750a4)}"
          + c + " .scrim{display:none}"
          + c + ".modal{display:none}"
          + c + ".modal.open{display:flex;position:fixed;inset:0;z-index:1000;align-items:center;justify-content:center}"
          + c + ".modal.open .scrim{display:block;position:absolute;inset:0;background:var(--md-sys-color-scrim,#000);opacity:.32}"
          + c + ".modal .card{position:relative}";
        DomGlobal.document.head.appendChild(style);
    }
}
