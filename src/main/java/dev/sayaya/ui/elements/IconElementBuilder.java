package dev.sayaya.ui.elements;

import dev.sayaya.ui.dom.MdIconElement;
import dev.sayaya.ui.elements.interfaces.IsSvgElement;
import org.jboss.elemento.*;

import static org.jboss.elemento.Elements.htmlElement;

public class IconElementBuilder implements HTMLElementStyleMethods<MdIconElement, IconElementBuilder>, HTMLElementVisibilityMethods<MdIconElement, IconElementBuilder>, ElementAttributeMethods<MdIconElement, IconElementBuilder>,
        ElementClassListMethods<MdIconElement, IconElementBuilder>, ElementEventMethods<MdIconElement, IconElementBuilder>, ElementTextMethods<MdIconElement, IconElementBuilder> {
    public static IconElementBuilder icon() {
        return new IconElementBuilder();
    }
    public static IconElementBuilder icon(String icon) {
        return new IconElementBuilder().add(icon);
    }
    public static IconElementBuilder icon(IsSvgElement<?, ?> icon) {
        return new IconElementBuilder().add(icon);
    }

    private final HTMLElementBuilder<MdIconElement> that = htmlElement("md-icon", MdIconElement.class);

    // String을 추가할 때는 textContent로 설정
    public IconElementBuilder add(String icon) {
        that.element().append(icon);
        return that();
    }

    // SVG 요소를 추가
    public IconElementBuilder add(IsSvgElement<?, ?> icon) {
        element().append(icon.element());
        return that();
    }

    // HTMLContainerBuilder를 자식으로 추가 (span 등)
    public <E extends elemental2.dom.HTMLElement, B extends HTMLContainerBuilder<E>> IconElementBuilder add(B builder) {
        element().appendChild(builder.element());
        return that();
    }

    // Filled variant
    public IconElementBuilder filled(boolean filled) {
        if (filled) {
            element().setAttribute("filled", "");
        } else {
            element().removeAttribute("filled");
        }
        return that();
    }

    @Override
    public MdIconElement element() {
        return that.element();
    }

    @Override
    public IconElementBuilder that() {
        return this;
    }
}
