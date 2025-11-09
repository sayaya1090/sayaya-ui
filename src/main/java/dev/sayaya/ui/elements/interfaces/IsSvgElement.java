package dev.sayaya.ui.elements.interfaces;

import dev.sayaya.ui.dom.SvgElement;

public interface IsSvgElement<E extends SvgElement, B> {
    E element();
    B that();
    default B style(String style) {
        return style(style, true);
    }
    default B style(String style, boolean append) {
        if (append) {
            element().style.cssText += style;
        } else {
            element().style.cssText = style;
        }
        return that();
    }
    default B style(String property, int value) {
        return style(property, String.valueOf(value), false);
    }
    default B style(String property, String value) {
        return style(property, value, false);
    }
    default B style(String property, int value, boolean important) {
        return style(property, String.valueOf(value), important);
    }
    default B style(String property, String value, boolean important) {
        String priority = important ? "important" : "";
        element().style.setProperty(property, value, priority);
        return that();
    }
}
