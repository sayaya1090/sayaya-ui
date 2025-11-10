package dev.sayaya.ui.elements.interfaces;

import elemental2.dom.Event;
import elemental2.dom.HTMLElement;
import org.jboss.elemento.EventCallbackFn;
import org.jboss.elemento.IsElement;

/**
 * Interface for elements that support input events.
 * Typically used for form controls that fire input events when their value changes.
 */
public interface HasInputEvent<E extends HTMLElement, SELF extends HasInputEvent<E, SELF>> extends IsElement<E> {
    SELF that();

    default SELF onInput(EventCallbackFn<Event> callback) {
        element().addEventListener("input", callback::onEvent);
        return that();
    }
}
