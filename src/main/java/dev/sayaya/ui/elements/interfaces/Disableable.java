package dev.sayaya.ui.elements.interfaces;

import elemental2.dom.HTMLElement;
import org.jboss.elemento.IsElement;

public interface Disableable<E extends HTMLElement, SELF extends Disableable<E, SELF>> extends IsElement<E> {
    SELF that();

    default SELF disabled(boolean disabled) {
        element().setAttribute("disabled", String.valueOf(disabled));
        return that();
    }

    default SELF disabled() {
        return disabled(true);
    }

    default SELF enabled() {
        return disabled(false);
    }

    default SELF enabled(boolean enabled) {
        return disabled(!enabled);
    }
}
