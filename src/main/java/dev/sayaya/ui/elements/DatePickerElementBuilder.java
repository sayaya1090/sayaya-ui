package dev.sayaya.ui.elements;

import dev.sayaya.ui.elements.interfaces.Openable;
import dev.sayaya.ui.elements.interfaces.HasLabels;
import dev.sayaya.ui.elements.interfaces.HasLocale;
import dev.sayaya.ui.elements.interfaces.UiLabel;
import elemental2.core.JsDate;
import elemental2.dom.*;
import elemental2.dom.DomGlobal;
import jsinterop.base.Js;
import org.jboss.elemento.*;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static org.jboss.elemento.Elements.*;

/**
 * M3 date picker — docked / modal (sayaya-ui 자체 구현, elemento 엘리먼트만 사용).
 * <pre>{@code
 * import static dev.sayaya.ui.elements.DatePickerElementBuilder.*;
 *
 * var due = datePicker().docked().value(2026, 10, 24).onChange(iso -> save(iso)).element();
 * var modal = datePicker().modal().headline("날짜 선택").onChange(iso -> save(iso));
 * body().add(modal); modal.open(true);
 * }</pre>
 * 날짜는 ISO 문자열(yyyy-MM-dd)로 주고받는다. 월은 1~12.
 * 수치 출처: Compose Material3 DatePickerModalTokens — 컨테이너 360dp·모서리 extra large(28dp)·surface container high·elevation 3,
 * 헤더 120dp, 날짜 40dp 원형(선택 primary/on primary, 오늘 primary 1dp 테두리), 요일 on surface.
 * 달력은 ARIA grid 이다: ←/→ 하루, ↑/↓ 한 주, PageUp/PageDown 한 달, Home/End 주의 처음/끝, Enter·Space 선택. modal 은 Esc 로 취소한다.
 * 월 표시를 누르면 연도 선택(SelectionYear 72×36dp, 선택 primary/on primary, 기본 on surface variant)으로 바뀐다.
 * range(true)는 기간 선택이다: 두 번 눌러 시작·끝을 고르며(거꾸로 누르면 바꿔 넣음), 범위는 높이 40dp secondary container 띠와
 * on secondary container 글자로 표시한다(RangeSelectionActiveIndicator*). modal 기간 선택의 헤더는 128dp(RangeSelectionHeaderContainerHeight).
 */
public final class DatePickerElementBuilder extends CustomElementBuilder<DatePickerElementBuilder> implements
        Openable<DatePickerElementBuilder>, HasLabels<DatePickerElementBuilder.Label, DatePickerElementBuilder>, HasLocale<DatePickerElementBuilder> {
    public static final String CLASS = "sayaya-date-picker";
    static final String STYLE_ID = "sayaya-date-picker-style";
    /** UI 문구. 기본은 영어이고 {@link #label(Label, String)} 로 덮어쓴다 */
    public enum Label implements UiLabel {
        PreviousMonth("Previous month"), NextMonth("Next month"), SelectYear("Select year"),
        Cancel("Cancel"), Ok("OK"), NoSelection("Select a date"), RangeStart("Start date"), RangeEnd("End date");
        public final String english;
        Label(String english) { this.english = english; }
        @Override public String english() { return english; }
    }
    private static int seq = 0;

    public static DatePickerPrepare datePicker() { return new DatePickerPrepare(); }
    public static final class DatePickerPrepare {
        /** 입력란 아래 등 화면에 붙어 있는 달력. 날짜를 고르면 바로 확정된다 */
        public DatePickerElementBuilder docked() { return new DatePickerElementBuilder(false); }
        /** 스크림 위 대화상자. 확인을 눌러야 확정된다 */
        public DatePickerElementBuilder modal() { return new DatePickerElementBuilder(true); }
    }

    private final boolean modal;
    private final HTMLElement card, monthLabel, grid, headerDate, headline, years, navButtons;
    private boolean range = false, yearMode = false;
    private String rangeStart, rangeEnd, committedStart, committedEnd;
    private BiConsumer<String, String> onRangeChange;
    private String[] monthNames = null; // 지정하면 Intl 월 이름 대신 쓴다
    private String locale = null;       // null 이면 Locales.resolve 기본
    private Integer weekStart = null;   // null 이면 locale 기본(0=일요일)
    private final java.util.Map<Label, String> labels = new java.util.EnumMap<>(Label.class);
    private HTMLElement prevBtn, nextBtn, cancelBtn, okBtn;
    private int viewYear, viewMonth;      // 보이는 달 (month 0~11)
    private int fy, fm, fd;              // 포커스된 날짜
    private String selected, committed;  // ISO
    private String today;
    private Consumer<String> onChange;
    private final String gridId;

    private DatePickerElementBuilder(boolean modal) {
        injectStyle();
        this.modal = modal;
        gridId = "sayaya-date-grid-" + (++seq);
        JsDate now = new JsDate();
        today = iso(now.getFullYear(), now.getMonth(), now.getDate());
        viewYear = now.getFullYear(); viewMonth = now.getMonth();
        fy = viewYear; fm = viewMonth; fd = now.getDate();
        element().classList.add(CLASS, modal ? "modal" : "docked");

        headline = div().css("headline").text("Select date").element();
        headerDate = div().css("header-date").element();
        monthLabel = button().css("month").attr("type", "button").attr("aria-live", "polite").attr("aria-expanded", "false").element();
        monthLabel.addEventListener("click", e -> { yearMode = !yearMode; render(); if (yearMode) scrollYear(); else focusCell(); });
        years = div().css("years").attr("role", "listbox").element();
        var prev = button().css("nav", "prev").attr("type", "button")
                .add(span().css("icon").attr("aria-hidden", "true").text("chevron_left")).element();
        var next = button().css("nav", "next").attr("type", "button")
                .add(span().css("icon").attr("aria-hidden", "true").text("chevron_right")).element();
        prevBtn = prev; nextBtn = next;
        prev.addEventListener("click", e -> moveMonth(-1, false));
        next.addEventListener("click", e -> moveMonth(1, false));
        grid = div().css("grid").attr("role", "grid").attr("id", gridId).element();
        grid.addEventListener("keydown", this::onKey);
        navButtons = div().css("nav-buttons").add(prev).add(next).element();
        var nav = div().css("nav-row").add(monthLabel).add(navButtons).element();
        card = div().css("card").element();
        if (modal) {
            card.setAttribute("role", "dialog");
            card.setAttribute("aria-modal", "true");
            String hid = gridId + "-headline";
            headline.id = hid;
            card.setAttribute("aria-labelledby", hid);
            card.appendChild(div().css("header").add(headline).add(headerDate).element());
        }
        card.appendChild(nav);
        card.appendChild(years);
        card.appendChild(grid);
        if (modal) {
            var cancel = button().css("action").attr("type", "button").element();
            var ok = button().css("action", "ok").attr("type", "button").element();
            cancelBtn = cancel; okBtn = ok;
            cancel.addEventListener("click", e -> cancel());
            ok.addEventListener("click", e -> confirm());
            card.appendChild(div().css("actions").add(cancel).add(ok).element());
            var scrim = div().css("scrim").element();
            scrim.addEventListener("click", e -> cancel());
            element().appendChild(scrim);
            element().addEventListener("keydown", e -> { if ("Escape".equals(((KeyboardEvent) Js.uncheckedCast(e)).key)) { cancel(); e.preventDefault(); } });
        }
        element().appendChild(card);
        applyLabels();
        render();
    }
    @Override public DatePickerElementBuilder that() { return this; }

    /** 선택된 날짜(월 1~12) */
    public DatePickerElementBuilder value(int year, int month, int day) {
        selected = committed = iso(year, month - 1, day);
        viewYear = fy = year; viewMonth = fm = month - 1; fd = day;
        render();
        return this;
    }
    public DatePickerElementBuilder value(String iso) {
        if (iso == null || iso.isEmpty()) { selected = committed = null; render(); return this; }
        String[] p = iso.split("-");
        return value(Integer.parseInt(p[0]), Integer.parseInt(p[1]), Integer.parseInt(p[2]));
    }
    /** 오늘로 표시할 날짜(기본: 시스템 날짜) */
    public DatePickerElementBuilder today(int year, int month, int day) { today = iso(year, month - 1, day); render(); return this; }
    /** 보이는 달 이동(월 1~12) */
    public DatePickerElementBuilder month(int year, int month) { viewYear = fy = year; viewMonth = fm = month - 1; fd = 1; render(); return this; }
    public DatePickerElementBuilder headline(String text) { headline.textContent = text; return this; }
    /** 월 이름을 직접 지정(12개). 지정하지 않으면 locale 의 Intl 월 이름을 쓴다 */
    public DatePickerElementBuilder monthNames(String... names) { if (names.length == 12) monthNames = names; render(); return this; }
    /** 날짜 표기·요일·주 시작 요일의 기준 locale(예: "ko-KR", "en-US"). 기본: Locales.setDefault → html lang → 브라우저 언어 */
    public DatePickerElementBuilder locale(String locale) { this.locale = locale; render(); return this; }
    /** 주의 첫 요일(0=일요일 … 6=토요일). 기본은 locale 을 따른다 */
    public DatePickerElementBuilder weekStart(int day) { this.weekStart = ((day % 7) + 7) % 7; render(); return this; }
    /** UI 문구 덮어쓰기(기본 영어) */
    public DatePickerElementBuilder label(Label label, String text) { labels.put(label, text); applyLabels(); render(); return this; }
    private String text(Label l) { String v = labels.get(l); return v == null ? l.english : v; }
    private void applyLabels() {
        prevBtn.setAttribute("aria-label", text(Label.PreviousMonth));
        nextBtn.setAttribute("aria-label", text(Label.NextMonth));
        years.setAttribute("aria-label", text(Label.SelectYear));
        if (cancelBtn != null) { cancelBtn.textContent = text(Label.Cancel); okBtn.textContent = text(Label.Ok); }
    }
    private int ws() { return weekStart != null ? weekStart : Locales.firstDayOfWeek(locale); }
    private String fmt(int y, int m0, int d, String... opts) { return Locales.formatDate(locale, y, m0, d, Locales.options(opts)); }
    /** 기간 선택(시작·끝 두 번 선택) */
    public DatePickerElementBuilder range(boolean range) {
        this.range = range;
        if (range) element().classList.add("range"); else element().classList.remove("range");
        render();
        return this;
    }
    /** 기간이 확정될 때(docked: 끝을 고를 때, modal: 확인). ISO 시작·끝 */
    public DatePickerElementBuilder onRangeChange(BiConsumer<String, String> cb) { this.onRangeChange = cb; return this; }
    public String rangeStart() { return committedStart; }
    public String rangeEnd() { return committedEnd; }
    /** docked: 날짜를 고를 때, modal: 확인을 누를 때. ISO(yyyy-MM-dd) */
    public DatePickerElementBuilder onChange(Consumer<String> cb) { this.onChange = cb; return this; }

    public String value() { return committed; }
    public boolean isOpen() { return element().classList.contains("open"); }
    /** modal 을 연다/닫는다. 열면 선택된(없으면 오늘) 날짜로 포커스가 간다 */
    public DatePickerElementBuilder open(boolean open) {
        if (!modal) return this;
        if (open) {
            selected = committed; rangeStart = committedStart; rangeEnd = committedEnd; yearMode = false;
            element().classList.add("open");
            render();
            focusCell();
        } else element().classList.remove("open");
        return this;
    }

    private void cancel() { selected = committed; rangeStart = committedStart; rangeEnd = committedEnd; open(false); }
    private void confirm() {
        if (range) {
            committedStart = rangeStart; committedEnd = rangeEnd;
            open(false);
            if (onRangeChange != null && committedStart != null && committedEnd != null) onRangeChange.accept(committedStart, committedEnd);
            return;
        }
        committed = selected;
        open(false);
        if (onChange != null && committed != null) onChange.accept(committed);
    }
    private void chooseRange(String id) {
        if (rangeStart == null || rangeEnd != null) { rangeStart = id; rangeEnd = null; }
        else if (id.compareTo(rangeStart) < 0) { rangeEnd = rangeStart; rangeStart = id; }
        else rangeEnd = id;
        if (!modal && rangeEnd != null) {
            committedStart = rangeStart; committedEnd = rangeEnd;
            if (onRangeChange != null) onRangeChange.accept(committedStart, committedEnd);
        }
    }
    private void pickYear(int year) {
        viewYear = fy = year;
        fm = viewMonth;
        fd = Math.min(fd, daysIn(viewYear, viewMonth));
        yearMode = false;
        render();
        focusCell();
    }
    private void scrollYear() {
        var sel = (HTMLElement) years.querySelector("[aria-selected=true]");
        if (sel != null) { years.scrollTop = Math.max(0, sel.offsetTop - years.offsetTop - 120); sel.focus(); }
    }
    private void choose(int y, int m, int d) {
        if (range) { fy = y; fm = m; fd = d; chooseRange(iso(y, m, d)); render(); focusCell(); return; }
        selected = iso(y, m, d);
        fy = y; fm = m; fd = d;
        if (!modal) { committed = selected; if (onChange != null) onChange.accept(committed); }
        render();
        focusCell();
    }
    private void moveMonth(int delta, boolean keepFocus) {
        JsDate d = new JsDate(viewYear, viewMonth + delta, 1);
        viewYear = d.getFullYear(); viewMonth = d.getMonth();
        int max = daysIn(viewYear, viewMonth);
        fy = viewYear; fm = viewMonth; fd = Math.min(fd, max);
        render();
        if (keepFocus) focusCell();
    }
    private void onKey(Event e) {
        KeyboardEvent k = Js.uncheckedCast(e);
        int delta;
        switch (k.key) {
            case "ArrowLeft": delta = Locales.rtl(element()) ? 1 : -1; break;
            case "ArrowRight": delta = Locales.rtl(element()) ? -1 : 1; break;
            case "ArrowUp": delta = -7; break;
            case "ArrowDown": delta = 7; break;
            case "Home": delta = -((new JsDate(fy, fm, fd).getDay() - ws() + 7) % 7); break;
            case "End": delta = 6 - ((new JsDate(fy, fm, fd).getDay() - ws() + 7) % 7); break;
            case "PageUp": moveMonth(-1, true); e.preventDefault(); return;
            case "PageDown": moveMonth(1, true); e.preventDefault(); return;
            case "Enter": case " ": choose(fy, fm, fd); e.preventDefault(); return;
            default: return;
        }
        JsDate d = new JsDate(fy, fm, fd + delta);
        fy = d.getFullYear(); fm = d.getMonth(); fd = d.getDate();
        if (fy != viewYear || fm != viewMonth) { viewYear = fy; viewMonth = fm; }
        render();
        focusCell();
        e.preventDefault();
    }
    private void focusCell() {
        var cell = (HTMLElement) grid.querySelector("[tabindex='0']");
        if (cell != null) cell.focus();
    }

    private static int daysIn(int y, int m) { return new JsDate(y, m + 1, 0).getDate(); }
    private static String pad(int v) { return v < 10 ? "0" + v : String.valueOf(v); }
    private static String iso(int y, int m0, int d) { return y + "-" + pad(m0 + 1) + "-" + pad(d); }

    private void render() {
        monthLabel.textContent = monthNames != null ? monthNames[viewMonth] + " " + viewYear : fmt(viewYear, viewMonth, 1, "year", "numeric", "month", "long");
        monthLabel.setAttribute("aria-expanded", String.valueOf(yearMode));
        if (yearMode) element().classList.add("years-mode"); else element().classList.remove("years-mode");
        renderYears();
        if (range) {
            headerDate.textContent = (rangeStart == null ? text(Label.RangeStart) : shortDate(rangeStart)) + " – " + (rangeEnd == null ? text(Label.RangeEnd) : shortDate(rangeEnd));
        } else if (selected != null) {
            headerDate.textContent = shortDate(selected);
        } else headerDate.textContent = text(Label.NoSelection);
        while (grid.firstChild != null) grid.removeChild(grid.firstChild);
        var head = div().css("row", "weekdays").attr("role", "row").element();
        int ws = ws();
        for (int i = 0; i < 7; i++) { // 2026-10-04 는 일요일 — 요일 이름만 얻는 기준일
            int dow = (ws + i) % 7;
            head.appendChild(span().css("weekday").attr("role", "columnheader").attr("data-day", String.valueOf(dow))
                    .attr("aria-label", fmt(2026, 9, 4 + dow, "weekday", "long")).text(fmt(2026, 9, 4 + dow, "weekday", "narrow")).element());
        }
        grid.appendChild(head);
        int first = (new JsDate(viewYear, viewMonth, 1).getDay() - ws + 7) % 7;
        int days = daysIn(viewYear, viewMonth);
        HTMLElement row = null;
        for (int i = 0; i < first + days; i++) {
            if (i % 7 == 0) { row = div().css("row").attr("role", "row").element(); grid.appendChild(row); }
            if (i < first) { row.appendChild(span().css("cell", "empty").attr("role", "gridcell").element()); continue; }
            int day = i - first + 1;
            String id = iso(viewYear, viewMonth, day);
            boolean focus = fy == viewYear && fm == viewMonth && fd == day;
            boolean sel = range ? (id.equals(rangeStart) || id.equals(rangeEnd)) : id.equals(selected);
            var cell = button().css("cell").attr("type", "button").attr("role", "gridcell").attr("data-date", id)
                    .attr("tabindex", focus ? "0" : "-1").attr("aria-selected", String.valueOf(sel))
                    .attr("aria-label", fmt(viewYear, viewMonth, day, "year", "numeric", "month", "long", "day", "numeric")).text(String.valueOf(day)).element();
            if (id.equals(today)) cell.setAttribute("aria-current", "date");
            if (range && rangeStart != null && rangeEnd != null && !rangeStart.equals(rangeEnd)) {
                if (id.compareTo(rangeStart) > 0 && id.compareTo(rangeEnd) < 0) cell.classList.add("in-range");
                if (id.equals(rangeStart)) cell.classList.add("range-start");
                if (id.equals(rangeEnd)) cell.classList.add("range-end");
            }
            int y = viewYear, m = viewMonth;
            cell.addEventListener("click", e -> choose(y, m, day));
            row.appendChild(cell);
        }
    }

    private String shortDate(String iso) {
        String[] p = iso.split("-");
        return fmt(Integer.parseInt(p[0]), Integer.parseInt(p[1]) - 1, Integer.parseInt(p[2]), "month", "short", "day", "numeric");
    }
    private void renderYears() {
        while (years.firstChild != null) years.removeChild(years.firstChild);
        if (!yearMode) return;
        int selYear = selected != null ? Integer.parseInt(selected.split("-")[0]) : viewYear;
        for (int y = viewYear - 100; y <= viewYear + 100; y++) {
            int year = y;
            var b = button().css("year").attr("type", "button").attr("role", "option").attr("data-year", String.valueOf(y))
                    .attr("aria-selected", String.valueOf(y == selYear)).text(String.valueOf(y)).element();
            b.addEventListener("click", e -> pickYear(year));
            years.appendChild(b);
        }
    }

    private static void injectStyle() {
        HTMLStyleElement style = newStyle(STYLE_ID);
        if (style == null) return;
        String c = "." + CLASS;
        style.textContent =
            c + "{display:inline-block;font-family:Roboto,system-ui,sans-serif}"
          + c + " .icon{font-family:'Material Symbols Outlined';font-size:24px;line-height:1;-webkit-font-feature-settings:'liga';font-feature-settings:'liga'}"
          // 컨테이너 360dp, 모서리 28dp, surface container high, elevation 3
          + c + " .card{box-sizing:border-box;width:360px;border-radius:var(--md-date-picker-container-shape,var(--md-sys-shape-corner-extra-large,28px));padding:0 12px 12px;background:var(--md-sys-color-surface-container-high,#ece6f0);"
          + "color:var(--md-sys-color-on-surface,#1d1b20);box-shadow:0 1px 3px 0 rgba(0,0,0,.3),0 4px 8px 3px rgba(0,0,0,.15)}"
          // 헤더 120dp (modal)
          + c + " .header{box-sizing:border-box;height:120px;margin:0 -12px;padding:16px 24px 12px;border-bottom:1px solid var(--md-sys-color-outline-variant,#cac4d0);display:flex;flex-direction:column;justify-content:space-between}"
          + c + " .headline{font-size:14px;line-height:20px;font-weight:500;color:var(--md-sys-color-on-surface-variant,#49454f)}"
          + c + " .header-date{font-size:32px;line-height:40px;color:var(--md-sys-color-on-surface-variant,#49454f)}"
          + c + " .nav-row{display:flex;align-items:center;justify-content:space-between;height:56px;padding-inline:12px 4px}"
          + c + ":dir(rtl) .nav .icon{transform:scaleX(-1)}"
          + c + " .month{font-size:14px;font-weight:500;color:var(--md-sys-color-on-surface-variant,#49454f);border:0;background:none;cursor:pointer;height:40px;padding:0 8px;border-radius:var(--md-date-picker-month-button-shape,var(--md-sys-shape-corner-full,9999px));font-family:inherit}"
          + c + " .month::after{content:' \\25BE'}"
          // 연도 선택: 72×36dp, 선택 primary/on primary, 기본 on surface variant
          + c + " .years{display:none;grid-template-columns:repeat(3,72px);justify-content:space-evenly;row-gap:16px;max-height:288px;overflow-y:auto;padding:8px 0}"
          + c + ".years-mode .years{display:grid}"
          + c + ".years-mode .grid," + c + ".years-mode .nav-buttons{display:none}"
          + c + " .year{width:72px;height:36px;border:0;border-radius:var(--md-date-picker-year-shape,var(--md-sys-shape-corner-full,9999px));background:none;cursor:pointer;font:400 16px/24px Roboto,system-ui,sans-serif;color:var(--md-sys-color-on-surface-variant,#49454f)}"
          + c + " .year[aria-selected=true]{background:var(--md-sys-color-primary,#6750a4);color:var(--md-sys-color-on-primary,#ffffff)}"
          + c + " .year:focus-visible{outline:3px solid var(--md-sys-color-secondary,#625b71);outline-offset:2px}"
          + c + " .nav{width:40px;height:40px;border:0;border-radius:var(--md-date-picker-nav-button-shape,var(--md-sys-shape-corner-full,50%));background:none;cursor:pointer;color:var(--md-sys-color-on-surface-variant,#49454f);display:inline-flex;align-items:center;justify-content:center}"
          + c + " .row{display:grid;grid-template-columns:repeat(7,48px);justify-content:center;height:48px;align-items:center;justify-items:center}"
          + c + " .weekday{font-size:16px;color:var(--md-sys-color-on-surface,#1d1b20)}"
          // 날짜 40dp 원형
          + c + " .cell{box-sizing:border-box;width:40px;height:40px;border-radius:var(--md-date-picker-date-shape,var(--md-sys-shape-corner-full,9999px));border:0;padding:0;background:none;font:400 16px/24px Roboto,system-ui,sans-serif;"
          + "color:var(--md-sys-color-on-surface,#1d1b20);cursor:pointer}"
          + c + " .cell.empty{cursor:default}"
          + c + " .cell:hover{background:color-mix(in srgb,var(--md-sys-color-on-surface,#1d1b20) 8%,transparent)}"
          + c + " .cell[aria-current=date]{border:1px solid var(--md-sys-color-primary,#6750a4);color:var(--md-sys-color-primary,#6750a4)}"
          + c + " .cell[aria-selected=true]{background:var(--md-sys-color-primary,#6750a4);color:var(--md-sys-color-on-primary,#ffffff);border:0}"
          + c + " .cell:focus-visible{outline:3px solid var(--md-sys-color-secondary,#625b71);outline-offset:2px}"
          // 기간 선택: 범위 띠 40dp secondary container, 범위 안 글자 on secondary container, 시작·끝은 띠 위 primary 원
          + c + ".range .header{height:128px}"
          + c + ".range .cell{position:relative;isolation:isolate}"
          + c + ".range .cell.in-range{color:var(--md-sys-color-on-secondary-container,#1d192b)}"
          + c + ".range .cell.in-range::before," + c + ".range .cell.range-start::before," + c + ".range .cell.range-end::before{content:'';position:absolute;top:0;height:40px;inset-inline:-4px;"
          + "background:var(--md-sys-color-secondary-container,#e8def8);z-index:-1}"
          + c + ".range .cell.range-start::before{inset-inline-start:50%}"
          + c + ".range .cell.range-end::before{inset-inline-end:50%}"
          + c + ".range .cell[aria-selected=true]{background:none;color:var(--md-sys-color-on-primary,#ffffff)}"
          + c + ".range .cell[aria-selected=true]::after{content:'';position:absolute;inset:0;border-radius:var(--md-date-picker-date-shape,var(--md-sys-shape-corner-full,9999px));background:var(--md-sys-color-primary,#6750a4);z-index:-1}"
          + c + " .actions{display:flex;justify-content:flex-end;gap:8px;padding-top:8px}"
          + c + " .action{height:40px;padding:0 12px;border:0;border-radius:var(--md-date-picker-action-shape,var(--md-sys-shape-corner-full,9999px));background:none;cursor:pointer;font:500 14px/20px Roboto,system-ui,sans-serif;color:var(--md-sys-color-primary,#6750a4)}"
          // Modal: 스크림 위 가운데. 닫혀 있으면 숨김
          + c + ".modal{display:none}"
          + c + ".modal.open{display:flex;position:fixed;inset:0;z-index:1000;align-items:center;justify-content:center}"
          + c + " .scrim{position:absolute;inset:0;background:var(--md-sys-color-scrim,#000);opacity:.32}"
          + c + ".modal .card{position:relative}";
        DomGlobal.document.head.appendChild(style);
    }
}
