package dev.sayaya.ui.elements;

import elemental2.core.JsDate;
import elemental2.dom.*;
import elemental2.dom.DomGlobal;
import jsinterop.base.Js;
import org.jboss.elemento.*;

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
 */
public final class DatePickerElementBuilder implements
        HTMLElementStyleMethods<HTMLDivElement, DatePickerElementBuilder>, HTMLElementVisibilityMethods<HTMLDivElement, DatePickerElementBuilder>,
        ElementAttributeMethods<HTMLDivElement, DatePickerElementBuilder>, ElementClassListMethods<HTMLDivElement, DatePickerElementBuilder>,
        ElementEventMethods<HTMLDivElement, DatePickerElementBuilder>, ElementIdMethods<HTMLDivElement, DatePickerElementBuilder>,
        ElementConsumerMethods<HTMLDivElement, DatePickerElementBuilder> {
    public static final String CLASS = "sayaya-date-picker";
    static final String STYLE_ID = "sayaya-date-picker-style";
    private static final String[] WEEKDAYS = {"S", "M", "T", "W", "T", "F", "S"};
    private static int seq = 0;

    public static DatePickerPrepare datePicker() { return new DatePickerPrepare(); }
    public static final class DatePickerPrepare {
        /** 입력란 아래 등 화면에 붙어 있는 달력. 날짜를 고르면 바로 확정된다 */
        public DatePickerElementBuilder docked() { return new DatePickerElementBuilder(false); }
        /** 스크림 위 대화상자. 확인을 눌러야 확정된다 */
        public DatePickerElementBuilder modal() { return new DatePickerElementBuilder(true); }
    }

    private final HTMLContainerBuilder<HTMLDivElement> that = div();
    private final boolean modal;
    private final HTMLElement card, monthLabel, grid, headerDate, headline;
    private String[] monthNames = {"1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"};
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
        monthLabel = span().css("month").attr("aria-live", "polite").element();
        var prev = button().css("nav", "prev").attr("type", "button").attr("aria-label", "이전 달")
                .add(span().css("icon").attr("aria-hidden", "true").text("chevron_left")).element();
        var next = button().css("nav", "next").attr("type", "button").attr("aria-label", "다음 달")
                .add(span().css("icon").attr("aria-hidden", "true").text("chevron_right")).element();
        prev.addEventListener("click", e -> moveMonth(-1, false));
        next.addEventListener("click", e -> moveMonth(1, false));
        grid = div().css("grid").attr("role", "grid").attr("id", gridId).element();
        grid.addEventListener("keydown", this::onKey);
        var nav = div().css("nav-row").add(monthLabel).add(div().css("nav-buttons").add(prev).add(next)).element();
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
        card.appendChild(grid);
        if (modal) {
            var cancel = button().css("action").attr("type", "button").text("취소").element();
            var ok = button().css("action", "ok").attr("type", "button").text("확인").element();
            cancel.addEventListener("click", e -> cancel());
            ok.addEventListener("click", e -> confirm());
            card.appendChild(div().css("actions").add(cancel).add(ok).element());
            var scrim = div().css("scrim").element();
            scrim.addEventListener("click", e -> cancel());
            element().appendChild(scrim);
            element().addEventListener("keydown", e -> { if ("Escape".equals(((KeyboardEvent) Js.uncheckedCast(e)).key)) { cancel(); e.preventDefault(); } });
        }
        element().appendChild(card);
        render();
    }
    @Override public DatePickerElementBuilder that() { return this; }
    @Override public HTMLDivElement element() { return that.element(); }

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
    public DatePickerElementBuilder monthNames(String... names) { if (names.length == 12) monthNames = names; render(); return this; }
    /** docked: 날짜를 고를 때, modal: 확인을 누를 때. ISO(yyyy-MM-dd) */
    public DatePickerElementBuilder onChange(Consumer<String> cb) { this.onChange = cb; return this; }

    public String value() { return committed; }
    public boolean isOpen() { return element().classList.contains("open"); }
    /** modal 을 연다/닫는다. 열면 선택된(없으면 오늘) 날짜로 포커스가 간다 */
    public DatePickerElementBuilder open(boolean open) {
        if (!modal) return this;
        if (open) {
            selected = committed;
            element().classList.add("open");
            render();
            focusCell();
        } else element().classList.remove("open");
        return this;
    }

    private void cancel() { selected = committed; open(false); }
    private void confirm() {
        committed = selected;
        open(false);
        if (onChange != null && committed != null) onChange.accept(committed);
    }
    private void choose(int y, int m, int d) {
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
            case "ArrowLeft": delta = -1; break;
            case "ArrowRight": delta = 1; break;
            case "ArrowUp": delta = -7; break;
            case "ArrowDown": delta = 7; break;
            case "Home": delta = -new JsDate(fy, fm, fd).getDay(); break;
            case "End": delta = 6 - new JsDate(fy, fm, fd).getDay(); break;
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
        monthLabel.textContent = viewYear + "년 " + monthNames[viewMonth];
        if (selected != null) {
            String[] p = selected.split("-");
            headerDate.textContent = Integer.parseInt(p[1]) + "월 " + Integer.parseInt(p[2]) + "일";
        } else headerDate.textContent = "날짜를 고르세요";
        while (grid.firstChild != null) grid.removeChild(grid.firstChild);
        var head = div().css("row", "weekdays").attr("role", "row").element();
        for (String w : WEEKDAYS) head.appendChild(span().css("weekday").attr("role", "columnheader").text(w).element());
        grid.appendChild(head);
        int first = new JsDate(viewYear, viewMonth, 1).getDay();
        int days = daysIn(viewYear, viewMonth);
        HTMLElement row = null;
        for (int i = 0; i < first + days; i++) {
            if (i % 7 == 0) { row = div().css("row").attr("role", "row").element(); grid.appendChild(row); }
            if (i < first) { row.appendChild(span().css("cell", "empty").attr("role", "gridcell").element()); continue; }
            int day = i - first + 1;
            String id = iso(viewYear, viewMonth, day);
            boolean focus = fy == viewYear && fm == viewMonth && fd == day;
            var cell = button().css("cell").attr("type", "button").attr("role", "gridcell").attr("data-date", id)
                    .attr("tabindex", focus ? "0" : "-1").attr("aria-selected", String.valueOf(id.equals(selected)))
                    .attr("aria-label", viewYear + "년 " + (viewMonth + 1) + "월 " + day + "일").text(String.valueOf(day)).element();
            if (id.equals(today)) cell.setAttribute("aria-current", "date");
            int y = viewYear, m = viewMonth;
            cell.addEventListener("click", e -> choose(y, m, day));
            row.appendChild(cell);
        }
    }

    private static void injectStyle() {
        if (DomGlobal.document.getElementById(STYLE_ID) != null) return;
        HTMLStyleElement style = htmlElement("style", HTMLStyleElement.class).id(STYLE_ID).element();
        String c = "." + CLASS;
        style.textContent =
            c + "{display:inline-block;font-family:Roboto,system-ui,sans-serif}"
          + c + " .icon{font-family:'Material Symbols Outlined';font-size:24px;line-height:1;-webkit-font-feature-settings:'liga';font-feature-settings:'liga'}"
          // 컨테이너 360dp, 모서리 28dp, surface container high, elevation 3
          + c + " .card{box-sizing:border-box;width:360px;border-radius:28px;padding:0 12px 12px;background:var(--md-sys-color-surface-container-high,#ece6f0);"
          + "color:var(--md-sys-color-on-surface,#1d1b20);box-shadow:0 1px 3px 0 rgba(0,0,0,.3),0 4px 8px 3px rgba(0,0,0,.15)}"
          // 헤더 120dp (modal)
          + c + " .header{box-sizing:border-box;height:120px;margin:0 -12px;padding:16px 24px 12px;border-bottom:1px solid var(--md-sys-color-outline-variant,#cac4d0);display:flex;flex-direction:column;justify-content:space-between}"
          + c + " .headline{font-size:14px;line-height:20px;font-weight:500;color:var(--md-sys-color-on-surface-variant,#49454f)}"
          + c + " .header-date{font-size:32px;line-height:40px;color:var(--md-sys-color-on-surface-variant,#49454f)}"
          + c + " .nav-row{display:flex;align-items:center;justify-content:space-between;height:56px;padding:0 4px 0 12px}"
          + c + " .month{font-size:14px;font-weight:500;color:var(--md-sys-color-on-surface-variant,#49454f)}"
          + c + " .nav{width:40px;height:40px;border:0;border-radius:50%;background:none;cursor:pointer;color:var(--md-sys-color-on-surface-variant,#49454f);display:inline-flex;align-items:center;justify-content:center}"
          + c + " .row{display:grid;grid-template-columns:repeat(7,48px);justify-content:center;height:48px;align-items:center;justify-items:center}"
          + c + " .weekday{font-size:16px;color:var(--md-sys-color-on-surface,#1d1b20)}"
          // 날짜 40dp 원형
          + c + " .cell{box-sizing:border-box;width:40px;height:40px;border-radius:9999px;border:0;padding:0;background:none;font:400 16px/24px Roboto,system-ui,sans-serif;"
          + "color:var(--md-sys-color-on-surface,#1d1b20);cursor:pointer}"
          + c + " .cell.empty{cursor:default}"
          + c + " .cell:hover{background:color-mix(in srgb,var(--md-sys-color-on-surface,#1d1b20) 8%,transparent)}"
          + c + " .cell[aria-current=date]{border:1px solid var(--md-sys-color-primary,#6750a4);color:var(--md-sys-color-primary,#6750a4)}"
          + c + " .cell[aria-selected=true]{background:var(--md-sys-color-primary,#6750a4);color:var(--md-sys-color-on-primary,#ffffff);border:0}"
          + c + " .cell:focus-visible{outline:3px solid var(--md-sys-color-secondary,#625b71);outline-offset:2px}"
          + c + " .actions{display:flex;justify-content:flex-end;gap:8px;padding-top:8px}"
          + c + " .action{height:40px;padding:0 12px;border:0;border-radius:9999px;background:none;cursor:pointer;font:500 14px/20px Roboto,system-ui,sans-serif;color:var(--md-sys-color-primary,#6750a4)}"
          // Modal: 스크림 위 가운데. 닫혀 있으면 숨김
          + c + ".modal{display:none}"
          + c + ".modal.open{display:flex;position:fixed;inset:0;z-index:1000;align-items:center;justify-content:center}"
          + c + " .scrim{position:absolute;inset:0;background:var(--md-sys-color-scrim,#000);opacity:.32}"
          + c + ".modal .card{position:relative}";
        DomGlobal.document.head.appendChild(style);
    }
}
