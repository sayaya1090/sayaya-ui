package dev.sayaya.ui.elements;

import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLStyleElement;
import org.jboss.elemento.*;

/**
 * sayaya-ui 가 elemento 엘리먼트만으로 직접 구현한 컴포넌트 빌더의 공통 기반(dom 바인딩·labs JS 불필요).
 * <ul>
 *   <li>루트는 {@code div} 이고 elemento 의 스타일·표시·속성·클래스·이벤트·id·consumer 메서드를 그대로 쓴다</li>
 *   <li>컴포넌트 스타일시트는 {@link #newStyle(String)} 로 문서에 한 번만 주입한다</li>
 * </ul>
 * 하위 빌더는 {@link #that()} 만 구현하면 된다. 열림 상태·지역화·문구 덮어쓰기는
 * {@link dev.sayaya.ui.elements.interfaces.Openable}, {@link dev.sayaya.ui.elements.interfaces.HasLocale},
 * {@link dev.sayaya.ui.elements.interfaces.HasLabels} 인터페이스로 맞춘다.
 */
public abstract class CustomElementBuilder<SELF extends CustomElementBuilder<SELF>> implements
        HTMLElementStyleMethods<HTMLDivElement, SELF>, HTMLElementVisibilityMethods<HTMLDivElement, SELF>,
        ElementAttributeMethods<HTMLDivElement, SELF>, ElementClassListMethods<HTMLDivElement, SELF>,
        ElementEventMethods<HTMLDivElement, SELF>, ElementIdMethods<HTMLDivElement, SELF>,
        ElementConsumerMethods<HTMLDivElement, SELF> {
    private final HTMLDivElement root = Elements.div().element();

    @Override public HTMLDivElement element() { return root; }

    /**
     * 컴포넌트 스타일시트용 {@code <style id>}. 같은 id 가 이미 문서에 있으면 null 을 돌려주므로 호출 측은 그대로 return 한다.
     * 호출 측은 textContent 를 채운 뒤 {@code DomGlobal.document.head.appendChild(style)} 로 붙인다.
     */
    protected static HTMLStyleElement newStyle(String id) {
        if (DomGlobal.document.getElementById(id) != null) return null;
        HTMLStyleElement style = Elements.htmlElement("style", HTMLStyleElement.class).id(id).element();
        return style;
    }
    /** 루트에 클래스를 붙이거나 뗀다 */
    protected SELF setClass(String cls, boolean on) {
        if (on) element().classList.add(cls); else element().classList.remove(cls);
        return that();
    }
}
