package dev.sayaya.ui.elements.interfaces;

import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLFormElement;
import org.jboss.elemento.IsElement;

public interface FormAssociable<E extends HTMLElement, SELF extends FormAssociable<E, SELF>> extends IsElement<E> {
    SELF that();

    default SELF name(String name) {
        element().setAttribute("name", name);
        return that();
    }

    default SELF value(String value) {
        element().setAttribute("value", value);
        return that();
    }

    default SELF form(IsElement<HTMLFormElement> form) {
        return form(form.element());
    }

    default SELF form(HTMLFormElement form) {
        element().setAttribute("form", form.id);
        return that();
    }
}
