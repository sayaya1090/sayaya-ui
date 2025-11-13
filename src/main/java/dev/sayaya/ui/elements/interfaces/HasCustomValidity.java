package dev.sayaya.ui.elements.interfaces;

import elemental2.dom.HTMLElement;
import org.jboss.elemento.IsElement;

public interface HasCustomValidity<E extends HTMLElement&Validatable, SELF extends HasCustomValidity<E, SELF>> extends IsElement<E>, Validatable {
    SELF that();
    SELF setCustomValidity(String error);
    @Override default boolean checkValidity() {
        return that().reportValidity();
    }
    @Override default boolean reportValidity() {
        return that().reportValidity();
    }
}
