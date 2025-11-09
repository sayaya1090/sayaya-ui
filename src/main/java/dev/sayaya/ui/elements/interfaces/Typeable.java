package dev.sayaya.ui.elements.interfaces;

import elemental2.dom.HTMLElement;
import org.jboss.elemento.IsElement;

public interface Typeable<E extends HTMLElement, SELF extends Typeable<E, SELF>> extends IsElement<E> {
    SELF that();

    default SELF type(String type) {
        element().setAttribute("type", type);
        return that();
    }
}
