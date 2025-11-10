package dev.sayaya.ui.elements.interfaces;

import elemental2.dom.Event;
import elemental2.dom.HTMLElement;
import org.jboss.elemento.EventCallbackFn;
import org.jboss.elemento.IsElement;

/**
 * Interface for elements that support change events.
 * Typically used for form controls that fire change events when their value is committed.
 */
public interface HasChangeEvent<E extends HTMLElement, SELF extends HasChangeEvent<E, SELF>> extends IsElement<E> {
    SELF that();

    default SELF onChange(EventCallbackFn<Event> callback) {
        element().addEventListener("change", callback::onEvent);
        return that();
    }
}
