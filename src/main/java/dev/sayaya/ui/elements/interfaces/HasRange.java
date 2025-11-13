package dev.sayaya.ui.elements.interfaces;

import elemental2.dom.HTMLElement;
import org.jboss.elemento.IsElement;

public interface HasRange<E extends HTMLElement, SELF extends HasRange<E, SELF>> extends IsElement<E> {
    SELF that();

    default SELF min(String min) {
        element().setAttribute("min", min);
        return that();
    }

    default SELF max(String max) {
        element().setAttribute("max", max);
        return that();
    }
}
