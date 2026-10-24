package dev.sayaya.ui.i18n;

import dev.sayaya.ui.elements.CarouselElementBuilder;
import dev.sayaya.ui.elements.CenteredSliderElementBuilder;
import dev.sayaya.ui.elements.DatePickerElementBuilder;
import dev.sayaya.ui.elements.Locales;
import dev.sayaya.ui.elements.SearchElementBuilder;
import dev.sayaya.ui.elements.TimePickerElementBuilder;
import elemental2.dom.*;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.BottomSheetElementBuilder.bottomSheet;
import static dev.sayaya.ui.elements.ButtonElementBuilder.button;
import static dev.sayaya.ui.elements.CarouselElementBuilder.carousel;
import static dev.sayaya.ui.elements.CenteredSliderElementBuilder.centeredSlider;
import static dev.sayaya.ui.elements.DatePickerElementBuilder.datePicker;
import static dev.sayaya.ui.elements.FeedbackElementBuilder.snackbar;
import static dev.sayaya.ui.elements.FeedbackElementBuilder.toast;
import static dev.sayaya.ui.elements.NavigationElementBuilder.navigationRail;
import static dev.sayaya.ui.elements.SearchElementBuilder.search;
import static dev.sayaya.ui.elements.SideSheetElementBuilder.sideSheet;
import static dev.sayaya.ui.elements.TimePickerElementBuilder.timePicker;
import static dev.sayaya.ui.elements.ToolbarElementBuilder.toolbar;
import static org.jboss.elemento.Elements.div;

/** 지역화: 영어 기본 문구 + 덮어쓰기, Intl 기반 locale 표기, RTL. 메시지 접두어 "i18: ". */
public class I18nComponentsTest {
    private static HTMLElement q(Element root, String sel) { return (HTMLElement) root.querySelector(sel); }
    private static HTMLElement sr(Element host, String sel) {
        ShadowRoot r = Js.uncheckedCast(Js.asPropertyMap(host).get("shadowRoot"));
        return r == null ? null : Js.uncheckedCast(r.querySelector(sel));
    }
    private static String aria(HTMLElement e) { return e == null ? null : e.getAttribute("aria-label"); }
    private static long left(Element e, Element base) { return Math.round(e.getBoundingClientRect().left - base.getBoundingClientRect().left); }
    private static void key(Element target, String key) {
        JsPropertyMap<Object> m = JsPropertyMap.of();
        m.set("key", key); m.set("bubbles", true); m.set("cancelable", true);
        target.dispatchEvent(new KeyboardEvent("keydown", Js.<KeyboardEventInit>uncheckedCast(m)));
    }
    private static HTMLElement rtl(HTMLElement child) { return div().attr("dir", "rtl").add(child).element(); }

    public static void test() {
        printSectionHeader("Internationalization (i18n)");
        printDescription("sayaya-ui 자체 구현 컴포넌트의 UI 문구는 영어가 기본이고 빌더의 label(...)·closeLabel(...) 등으로 덮어씁니다. 날짜·월·요일·오전/오후 표기와 주 시작 요일, 12/24시간 기본값은 브라우저 Intl 로 locale 을 따릅니다. locale 은 빌더 locale(...) → Locales.setDefault(...) → <html lang> → 브라우저 언어 순으로 정해집니다. dir=\"rtl\" 에서는 배치와 방향키가 뒤집힙니다.");
        printSeparator();
        var section = section("i18n");
        labs(section);
        locales();
        pickers(section);
        searchLabels(section);
        rtlLayouts(section);
    }

    /** labs JS 직접 구현 컴포넌트: 영어 기본 + 덮어쓰기 */
    private static void labs(HTMLElement section) {
        var sb = snackbar().text("Deleted").closeable(true).timeout(0).element();
        var sbKo = snackbar().text("삭제했습니다").closeable(true).closeLabel("닫기").timeout(0).element();
        var to = toast().info().text("Saved").closeable(true).element();
        var toKo = toast().info().text("저장했습니다").closeable(true).closeLabel("닫기").element();
        var rail = navigationRail().menu(true).element();
        var railKo = navigationRail().menu(true).menuLabels("메뉴 열기", "메뉴 닫기").element();
        var ss = sideSheet().standard().back(true).headline("Details").open(true).element();
        var ssKo = sideSheet().standard().back(true).closeLabel("닫기").backLabel("뒤로").open(true).element();
        var bs = bottomSheet().standard().element();
        var hidden = div().style("position", "absolute").style("left", "-9999px").style("height", "400px")
                .add(sb).add(sbKo).add(to).add(toKo).add(rail).add(railKo).add(ss).add(ssKo).add(bs).element();
        section.appendChild(hidden);
        demo(section, "📘 기본 문구 덮어쓰기(labs 컴포넌트)", "닫기·뒤로·메뉴 버튼의 접근성 라벨은 영어(Close, Back, Open menu/Close menu, Resize sheet)가 기본입니다. 다른 언어는 빌더로 지정합니다.",
            """
            snackbar().text("삭제했습니다").closeable(true).closeLabel("닫기").element();
            toast().info().text("저장했습니다").closeable(true).closeLabel("닫기").element();
            navigationRail().menu(true).menuLabels("메뉴 열기", "메뉴 닫기").element();
            sideSheet().standard().closeLabel("닫기").backLabel("뒤로").element();
            bottomSheet().modal().handleLabel("시트 크기 조절").element();
            """, div().text("(검사용 요소는 화면 밖에 있습니다)").element());
        later(800, () -> {
            assertEquals("i18: snackbar 닫기 기본 Close", "Close", aria(sr(sb, ".close")));
            assertEquals("i18: snackbar 닫기 덮어쓰기", "닫기", aria(sr(sbKo, ".close")));
            assertEquals("i18: toast 닫기 기본 Close", "Close", aria(sr(to, ".close")));
            assertEquals("i18: toast 닫기 덮어쓰기", "닫기", aria(sr(toKo, ".close")));
            assertEquals("i18: rail 메뉴 기본 Open menu", "Open menu", aria(sr(rail, ".menu")));
            assertEquals("i18: rail 메뉴 덮어쓰기(접힘)", "메뉴 열기", aria(sr(railKo, ".menu")));
            assertEquals("i18: side sheet 닫기 기본 Close", "Close", aria(sr(ss, ".close")));
            assertEquals("i18: side sheet 뒤로 기본 Back", "Back", aria(sr(ss, ".back")));
            assertTrue("i18: side sheet 덮어쓰기", "닫기".equals(aria(sr(ssKo, ".close"))) && "뒤로".equals(aria(sr(ssKo, ".back"))));
            assertEquals("i18: bottom sheet 핸들 기본 Resize sheet", "Resize sheet", aria(sr(bs, ".handle-area")));
            sr(rail, ".menu").click();
            sr(railKo, ".menu").click();
            later(300, () -> {
                assertEquals("i18: rail 펼치면 Close menu", "Close menu", aria(sr(rail, ".menu")));
                assertEquals("i18: rail 덮어쓰기(펼침)", "메뉴 닫기", aria(sr(railKo, ".menu")));
            });
        });
    }

    /** Locales: locale 결정 순서와 Intl 결과 */
    private static void locales() {
        assertEquals("i18: 지정 locale 우선", "fr-FR", Locales.resolve("fr-FR"));
        var html = DomGlobal.document.documentElement;
        String oldLang = html.getAttribute("lang");
        html.setAttribute("lang", "fr");
        assertEquals("i18: html lang 사용", "fr", Locales.resolve(null));
        Locales.setDefault("ko-KR");
        assertEquals("i18: setDefault 가 html lang 보다 우선", "ko-KR", Locales.resolve(null));
        Locales.setDefault(null);
        if (oldLang == null) html.removeAttribute("lang"); else html.setAttribute("lang", oldLang);
        assertEquals("i18: en-US 주 시작 일요일", 0, Locales.firstDayOfWeek("en-US"));
        assertEquals("i18: de-DE 주 시작 월요일", 1, Locales.firstDayOfWeek("de-DE"));
        assertTrue("i18: en-US 12시간제", Locales.hour12("en-US"));
        assertFalse("i18: en-GB 24시간제", Locales.hour12("en-GB"));
        assertEquals("i18: ko-KR 오전 표기", "오전", Locales.dayPeriod("ko-KR", false));
        assertEquals("i18: en-US PM 표기", "PM", Locales.dayPeriod("en-US", true));
    }

    /** date / time picker: 영어 기본 문구, Intl 표기, 주 시작 요일, 12/24시간, RTL */
    private static void pickers(HTMLElement section) {
        var en = datePicker().docked().locale("en-US").today(2026, 10, 24).value(2026, 10, 15).element();
        var de = datePicker().docked().locale("de-DE").today(2026, 10, 24).value(2026, 10, 15).element();
        var mon = datePicker().docked().locale("en-US").weekStart(1).month(2026, 10).element();
        var enModal = datePicker().modal().locale("en-US").value(2026, 10, 15);
        var koModal = datePicker().modal().locale("ko-KR").label(DatePickerElementBuilder.Label.Cancel, "취소").label(DatePickerElementBuilder.Label.Ok, "확인");
        var dpRtl = datePicker().docked().locale("ar-EG").today(2026, 10, 24).value(2026, 10, 15).element();
        demo(section, "📘 Date picker — locale", "locale(\"en-US\")은 'October 2026', locale(\"de-DE\")는 'Oktober 2026' 처럼 표기하고, 독일어 locale 은 월요일부터 주가 시작합니다. 버튼 문구는 label(...)로 바꿉니다.",
            """
            datePicker().docked().locale("de-DE").element();               // 월요일 시작, 독일어 월·요일
            datePicker().docked().locale("en-US").weekStart(1).element();  // 주 시작 요일 직접 지정
            datePicker().modal().locale("ko-KR")
                .label(DatePickerElementBuilder.Label.Cancel, "취소")
                .label(DatePickerElementBuilder.Label.Ok, "확인");
            """, div().style("display", "flex").style("gap", "16px").style("flex-wrap", "wrap").add(en).add(de).add(rtl(dpRtl)).element());
        section.appendChild(div().style("position", "absolute").style("left", "-9999px").add(mon).add(enModal.element()).add(koModal.element()).element());

        var tEn = timePicker().dial().locale("en-US").value(9, 30).element();
        var tKo = timePicker().dial().locale("ko-KR").value(9, 30).element();
        var tGb = timePicker().dial().locale("en-GB").value(15, 0).element();
        var tGb12 = timePicker().dial().locale("en-GB").hour24(false).value(15, 0).element();
        var tLabel = timePicker().input().locale("ko-KR").label(TimePickerElementBuilder.Label.EnterTime, "시간 입력")
                .label(TimePickerElementBuilder.Label.Hour, "시").value(7, 5).element();
        demo(section, "📘 Time picker — locale", "오전/오후 표기(AM/PM, 오전/오후)와 12/24시간 기본값은 locale 을 따릅니다(en-GB 는 24시간). hour24(...)로 명시하면 그 값을 씁니다.",
            """
            timePicker().dial().locale("ko-KR").element();                 // 오전/오후
            timePicker().dial().locale("en-GB").element();                 // 24시간 기본
            timePicker().input().label(TimePickerElementBuilder.Label.EnterTime, "시간 입력").element();
            """, div().style("display", "flex").style("gap", "16px").style("flex-wrap", "wrap").add(tEn).add(tKo).add(tGb).element());
        section.appendChild(div().style("position", "absolute").style("left", "-9999px").add(tGb12).add(tLabel).element());

        later(600, () -> {
            assertTrue("i18: en-US 월 표시", "October".equals(q(en, ".month-menu").textContent) && "2026".equals(q(en, ".year-menu").textContent));
            assertEquals("i18: en-US 날짜 aria-label", "October 15, 2026", q(en, "[data-date='2026-10-15']").getAttribute("aria-label"));
            assertEquals("i18: 이전 달 기본 Previous month", "Previous month", aria(q(en, ".prev")));
            assertEquals("i18: en-US 첫 요일 일요일", "0", q(en, ".weekday").getAttribute("data-day"));
            assertEquals("i18: en-US 요일 narrow", "S", q(en, ".weekday").textContent);
            assertEquals("i18: de-DE 월 표시", "Oktober", q(de, ".month-menu").textContent);
            assertEquals("i18: de-DE 첫 요일 월요일", "1", q(de, ".weekday").getAttribute("data-day"));
            var deFirstRow = de.querySelectorAll("[role=row]").item(1);
            assertEquals("i18: de-DE 1일은 넷째 칸(목)", "2026-10-01", deFirstRow.querySelectorAll(":scope > *").item(3).getAttribute("data-date"));
            q(de, "[data-date='2026-10-15']").focus();
            key(DomGlobal.document.activeElement, "Home");
            assertEquals("i18: de-DE Home 은 월요일", "2026-10-12", DomGlobal.document.activeElement.getAttribute("data-date"));
            assertEquals("i18: weekStart(1) 지정", "1", q(mon, ".weekday").getAttribute("data-day"));
            var enEl = enModal.element();
            assertEquals("i18: modal 취소 기본 Cancel", "Cancel", q(enEl, ".action").textContent);
            assertEquals("i18: modal 확인 기본 OK", "OK", q(enEl, ".action.ok").textContent);
            assertEquals("i18: en-US 헤더 날짜", "Oct 15", q(enEl, ".header-date").textContent);
            assertEquals("i18: 헤드라인 기본 Select date", "Select date", q(enEl, ".headline").textContent);
            assertTrue("i18: date label 덮어쓰기", "취소".equals(q(koModal.element(), ".action").textContent) && "확인".equals(q(koModal.element(), ".action.ok").textContent));
            q(dpRtl, "[data-date='2026-10-15']").focus();
            key(DomGlobal.document.activeElement, "ArrowLeft");
            assertEquals("i18: RTL 달력 ← 는 다음 날", "2026-10-16", DomGlobal.document.activeElement.getAttribute("data-date"));

            assertEquals("i18: en-US AM 표기", "AM", q(tEn, ".period.am").textContent);
            assertEquals("i18: en-US 12시간(오전/오후 표시)", "flex", cs(q(tEn, ".periods")).getPropertyValue("display"));
            assertEquals("i18: 시간 헤드라인 기본 Select time", "Select time", q(tEn, ".headline").textContent);
            assertEquals("i18: 시 선택 기본 Select hour", "Select hour", aria(q(tEn, ".field.hour")));
            assertTrue("i18: ko-KR 오전/오후", "오전".equals(q(tKo, ".period.am").textContent) && "오후".equals(q(tKo, ".period.pm").textContent));
            assertEquals("i18: en-GB 24시간 기본", "none", cs(q(tGb, ".periods")).getPropertyValue("display"));
            assertEquals("i18: en-GB 숫자 24개", 24, tGb.querySelectorAll(".label").length);
            assertEquals("i18: hour24(false) 덮어쓰기", "flex", cs(q(tGb12, ".periods")).getPropertyValue("display"));
            assertEquals("i18: time label 덮어쓰기(헤드라인)", "시간 입력", q(tLabel, ".headline").textContent);
            assertEquals("i18: time label 덮어쓰기(시)", "시", aria(q(tLabel, ".field.hour")));
        });
    }

    private static void searchLabels(HTMLElement section) {
        var s = search().docked().placeholder("Search mail");
        var ko = search().docked().label(SearchElementBuilder.Label.Search, "검색").label(SearchElementBuilder.Label.Clear, "지우기");
        var full = search().fullScreen();
        section.appendChild(div().style("position", "absolute").style("left", "-9999px").add(s.element()).add(ko.element()).add(full.element()).element());
        assertEquals("i18: search 기본 Search", "Search", aria(q(s.element(), ".leading")));
        assertEquals("i18: 지우기 기본 Clear", "Clear", aria(q(s.element(), ".clear")));
        assertTrue("i18: search label 덮어쓰기", "검색".equals(aria(q(ko.element(), ".leading"))) && "지우기".equals(aria(q(ko.element(), ".clear"))));
        full.open(true);
        assertEquals("i18: full-screen 닫기 기본 Close search", "Close search", aria(q(full.element(), ".leading")));
        full.open(false);
        assertEquals("i18: 닫으면 다시 Search", "Search", aria(q(full.element(), ".leading")));
    }

    /** RTL: toolbar·carousel·centered slider */
    private static void rtlLayouts(HTMLElement section) {
        var b1 = button().icon("undo").ariaLabel("Undo").element();
        var b2 = button().icon("redo").ariaLabel("Redo").element();
        var tb = toolbar().docked().ariaLabel("Edit").add(b1).add(b2).add(button().icon("share").ariaLabel("Share")).element();
        CarouselElementBuilder cb = carousel().multiBrowse().itemWidth(200).ariaLabel("RTL carousel");
        String[] colors = {"#6750a4", "#625b71", "#7d5260", "#386a20", "#00639b", "#8c4a00"};
        for (int i = 0; i < colors.length; i++) cb.item(div().style("width", "100%").style("height", "100%").style("background", colors[i]).element(), "#" + (i + 1));
        var ca = cb.element();
        ca.style.setProperty("width", "480px");
        CenteredSliderElementBuilder sl = centeredSlider().range(-10, 10).value(5).ariaLabel("Balance");
        var slider = sl.element();
        slider.style.setProperty("width", "400px");
        demo(section, "📘 RTL (dir=\"rtl\")", "오른쪽→왼쪽 언어에서는 툴바·캐러셀에서 ← 가 다음 항목이고, 캐러셀은 오른쪽부터 시작하며, 가운데 슬라이더는 양수 값이 왼쪽으로 그려집니다.",
            """
            // <html dir="rtl"> 또는 상위 요소에 dir="rtl" 이면 자동으로 적용됩니다
            toolbar().docked().add(...).element();
            carousel().multiBrowse().item(...).element();
            centeredSlider().range(-10, 10).value(5).element();
            """, div().style("display", "flex").style("flex-direction", "column").style("gap", "16px")
                .add(rtl(div().style("width", "360px").add(tb).element())).add(rtl(ca)).add(rtl(slider)).element());
        later(900, () -> {
            b1.focus();
            key(b1, "ArrowLeft");
            assertTrue("i18: RTL 툴바 ← 는 다음 항목", DomGlobal.document.activeElement == b2);
            HTMLElement i0 = q(ca, ".item[data-index='0']"), i2 = q(ca, ".item[data-index='2']");
            assertTrue("i18: RTL 캐러셀 첫 항목은 오른쪽", i0.getBoundingClientRect().right > i2.getBoundingClientRect().right);
            assertEquals("i18: RTL 캐러셀 첫 항목 다 보임", "200", i0.getAttribute("data-visible"));
            assertEquals("i18: RTL 캐러셀 가장자리 항목 40px", "40", i2.getAttribute("data-visible"));
            i0.focus();
            key(i0, "ArrowLeft");
            assertTrue("i18: RTL 캐러셀 ← 는 다음 항목", DomGlobal.document.activeElement == q(ca, ".item[data-index='1']"));
            assertEquals("i18: RTL 슬라이더 +5 핸들 왼쪽 25%", 98, (int) left(q(slider, ".handle"), slider));
            assertEquals("i18: RTL 슬라이더 활성 트랙 시작", 108, (int) left(q(slider, ".active"), slider));
            assertEquals("i18: RTL 슬라이더 활성 트랙 너비", 92, (int) Math.round(q(slider, ".active").getBoundingClientRect().width));
        });
    }
}
