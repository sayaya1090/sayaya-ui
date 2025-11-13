package dev.sayaya.ui.elements.interfaces;

import elemental2.dom.HTMLElement;
import org.jboss.elemento.IsElement;

public interface Requireable<E extends HTMLElement, SELF extends Requireable<E, SELF>> extends IsElement<E> {
    SELF that();

    default SELF required() {
        return required(true);
    }

    SELF required(boolean required);

    default boolean isRequired() {
        return element().hasAttribute("required");
    }
}
