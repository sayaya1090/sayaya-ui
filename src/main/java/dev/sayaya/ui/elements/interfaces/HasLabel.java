package dev.sayaya.ui.elements.interfaces;

import elemental2.dom.HTMLElement;
import org.jboss.elemento.IsElement;

public interface HasLabel<E extends HTMLElement, SELF extends HasLabel<E, SELF>> extends IsElement<E> {
    SELF that();

    default SELF label(String label) {
        element().setAttribute("label", label);
        return that();
    }
}
