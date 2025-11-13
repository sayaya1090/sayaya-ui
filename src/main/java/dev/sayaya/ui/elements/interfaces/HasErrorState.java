package dev.sayaya.ui.elements.interfaces;

import elemental2.dom.HTMLElement;
import org.jboss.elemento.IsElement;

public interface HasErrorState<E extends HTMLElement, SELF extends HasErrorState<E, SELF>> extends IsElement<E> {
    SELF that();

    default SELF error(boolean error) {
        element().setAttribute("error", String.valueOf(error));
        return that();
    }

    default SELF error(String errorText) {
        return error(true).errorText(errorText);
    }

    default SELF errorText(String errorText) {
        element().setAttribute("error-text", errorText);
        return that();
    }
}
