package dev.sayaya.ui.elements.interfaces;

import elemental2.dom.HTMLElement;
import org.jboss.elemento.IsElement;

public interface HasValue<E extends HTMLElement, SELF extends HasValue<E, SELF>> extends IsElement<E> {
    SELF that();

    default String value() {
        return element().getAttribute("value");
    }

    default String name() {
        return element().getAttribute("name");
    }
}
