package dev.sayaya.ui.elements.interfaces;

/**
 * 날짜·시간 표기 등이 locale 을 따르는 빌더. null 이면 {@code Locales.setDefault} → {@code <html lang>} → 브라우저 언어 순으로 정한다.
 */
public interface HasLocale<SELF extends HasLocale<SELF>> {
    SELF locale(String locale);
}
