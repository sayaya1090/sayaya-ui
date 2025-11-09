package dev.sayaya.ui.elements.interfaces;

import elemental2.dom.HTMLElement;
import org.jboss.elemento.IsElement;

public interface Linkable<E extends HTMLElement, SELF extends Linkable<E, SELF>> extends IsElement<E> {
    SELF that();

    default SELF href(String href) {
        element().setAttribute("href", href);
        return that();
    }

    default SELF target(String target) {
        element().setAttribute("target", target);
        return that();
    }

    default SELF targetBlank() {
        return target("_blank");
    }

    default SELF targetSelf() {
        return target("_self");
    }
}
