package dev.sayaya.ui.elements.interfaces;

import elemental2.dom.Element;
import org.jboss.elemento.IsElement;

public interface HasAriaLabel<E extends Element, SELF extends HasAriaLabel<E, SELF>> extends IsElement<E> {
    SELF that();
    default SELF ariaLabel(String label) {
        element().setAttribute("aria-label", label);
        return that();
    }
}
