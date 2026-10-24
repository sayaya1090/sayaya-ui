package dev.sayaya.ui.elements;

import elemental2.core.Function;
import elemental2.core.JsArray;
import elemental2.core.JsDate;
import elemental2.dom.CSSStyleDeclaration;
import elemental2.dom.DomGlobal;
import elemental2.dom.Element;
import jsinterop.base.Any;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

/**
 * sayaya-ui 자체 구현 컴포넌트의 지역화 도우미. 번역 데이터 없이 브라우저 {@code Intl} API 를 쓴다.
 * <ul>
 *   <li>locale 결정 순서: 빌더의 {@code locale(...)} → {@link #setDefault(String)} → {@code <html lang>} → {@code navigator.language} → {@code en-US}</li>
 *   <li>UI 문구의 기본값은 영어이며 각 빌더의 {@code label(...)} 로 덮어쓴다(locale 로 번역되지 않는다)</li>
 *   <li>날짜·월·요일·오전/오후 표기, 주 시작 요일, 12/24시간 기본값은 locale 을 따른다</li>
 * </ul>
 */
public final class Locales {
    private static String defaultLocale = null;
    private Locales() {}

    /** 모든 자체 구현 컴포넌트의 기본 locale(예: "ko-KR"). null 이면 문서 언어를 따른다 */
    public static void setDefault(String locale) { defaultLocale = locale; }

    /** 실제로 쓸 locale 태그 */
    public static String resolve(String locale) {
        if (notEmpty(locale)) return locale;
        if (notEmpty(defaultLocale)) return defaultLocale;
        String lang = DomGlobal.document.documentElement.getAttribute("lang");
        if (notEmpty(lang)) return lang;
        Object nav = Js.asPropertyMap(DomGlobal.navigator).get("language");
        return nav != null && notEmpty(Js.asString(nav)) ? Js.asString(nav) : "en-US";
    }
    private static boolean notEmpty(String s) { return s != null && !s.isEmpty(); }

    private static Object intl(String member) { return Js.asPropertyMap(Js.asPropertyMap(Js.global()).get("Intl")).get(member); }
    private static Object dateFormat(String locale, JsPropertyMap<Object> options) {
        Function ctor = Js.uncheckedCast(intl("DateTimeFormat"));
        return construct(ctor, resolve(locale), options);
    }
    /** new ctor(...args) — 전역 Reflect.construct 로 호출한다 */
    private static Object construct(Function ctor, Object... args) {
        return invoke(Js.asPropertyMap(Js.global()).get("Reflect"), "construct", ctor, JsArray.of(args));
    }
    private static Object invoke(Object target, String method, Object... args) {
        Function f = Js.uncheckedCast(Js.asPropertyMap(target).get(method));
        return f.apply(target, Js.uncheckedCast(JsArray.of(args)));
    }

    /** Intl.DateTimeFormat 으로 날짜를 서식화한다(month0 은 0~11). options 예: {year:'numeric', month:'long'} */
    public static String formatDate(String locale, int year, int month0, int day, JsPropertyMap<Object> options) {
        return Js.asString(invoke(dateFormat(locale, options), "format", new JsDate(year, month0, day)));
    }
    public static JsPropertyMap<Object> options(String... keyValues) {
        JsPropertyMap<Object> m = JsPropertyMap.of();
        for (int i = 0; i + 1 < keyValues.length; i += 2) m.set(keyValues[i], keyValues[i + 1]);
        return m;
    }

    /** 주의 첫 요일(Date#getDay 기준 0=일요일 … 6=토요일). Intl.Locale weekInfo 를 지원하지 않으면 0 */
    public static int firstDayOfWeek(String locale) {
        try {
            Function ctor = Js.uncheckedCast(intl("Locale"));
            if (ctor == null) return 0;
            Object loc = construct(ctor, resolve(locale));
            Object info = Js.asPropertyMap(loc).get("getWeekInfo") != null ? invoke(loc, "getWeekInfo") : Js.asPropertyMap(loc).get("weekInfo");
            if (info == null) return 0;
            Any first = Js.asAny(Js.asPropertyMap(info).get("firstDay"));
            return first == null ? 0 : first.asInt() % 7;
        } catch (Throwable e) {
            return 0;
        }
    }

    /** locale 의 기본 시계가 12시간제인지(hourCycle h11/h12) */
    public static boolean hour12(String locale) {
        Object resolved = invoke(dateFormat(locale, options("hour", "numeric")), "resolvedOptions");
        Object cycle = Js.asPropertyMap(resolved).get("hourCycle");
        if (cycle != null) { String c = Js.asString(cycle); return "h11".equals(c) || "h12".equals(c); }
        Object h12 = Js.asPropertyMap(resolved).get("hour12");
        return h12 != null && Js.isTruthy(h12);
    }

    /** 오전/오후 표기(en: AM/PM, ko: 오전/오후). 찾지 못하면 AM/PM */
    public static String dayPeriod(String locale, boolean afternoon) {
        JsPropertyMap<Object> o = options("hour", "numeric");
        o.set("hour12", true);
        JsArray<Object> parts = Js.uncheckedCast(invoke(dateFormat(locale, o), "formatToParts", new JsDate(2000, 0, 1, afternoon ? 15 : 9, 0)));
        for (int i = 0; i < parts.length; i++) {
            JsPropertyMap<Object> p = Js.asPropertyMap(parts.getAt(i));
            if ("dayPeriod".equals(Js.asString(p.get("type")))) return Js.asString(p.get("value"));
        }
        return afternoon ? "PM" : "AM";
    }

    /** 요소가 오른쪽→왼쪽(RTL) 방향으로 배치되는지 */
    public static boolean rtl(Element element) {
        Function f = Js.uncheckedCast(Js.asPropertyMap(DomGlobal.window).get("getComputedStyle"));
        CSSStyleDeclaration cs = Js.uncheckedCast(f.call(DomGlobal.window, element));
        return "rtl".equals(cs.getPropertyValue("direction"));
    }
}
