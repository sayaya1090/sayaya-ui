package dev.sayaya.ui.elements;

import dev.sayaya.ui.dom.MdBadgeElement;
import elemental2.dom.HTMLElement;
import org.jboss.elemento.*;

import static org.jboss.elemento.Elements.htmlElement;

public class BadgeElementBuilder implements ElementAttributeMethods<MdBadgeElement, BadgeElementBuilder>, ElementClassListMethods<MdBadgeElement, BadgeElementBuilder>, ElementEventMethods<MdBadgeElement, BadgeElementBuilder>,
        HTMLElementStyleMethods<MdBadgeElement, BadgeElementBuilder>, HTMLElementVisibilityMethods<MdBadgeElement, BadgeElementBuilder> {
    public static BadgeElementBuilder badge() {
        return new BadgeElementBuilder();
    }
    private final HTMLElementBuilder<MdBadgeElement> that = htmlElement("md-badge", MdBadgeElement.class);
    public BadgeElementBuilder anchor(String id) {
        that.element().anchor = id;
        return that();
    }
    public BadgeElementBuilder anchorElement(IsElement<? extends HTMLElement> elem) {
        return anchorElement(elem.element());
    }
    public BadgeElementBuilder anchorElement(HTMLElement anchor) {
        that.element().anchorElement = anchor;
        return that();
    }
    public BadgeElementBuilder value(String value) {
        that.element().value = value;
        return that();
    }
    public BadgeElementBuilder value(int value) {
        return value(String.valueOf(value));
    }
    @Override
    public MdBadgeElement element() {
        return that.element();
    }
    @Override
    public BadgeElementBuilder that() {
        return this;
    }
}
