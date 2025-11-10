package dev.sayaya.ui.elements.interfaces;

import elemental2.dom.Event;
import elemental2.dom.HTMLElement;
import org.jboss.elemento.EventCallbackFn;
import org.jboss.elemento.IsElement;

/**
 * Interface for elements that support remove events.
 * Typically used for removable items like chips.
 */
public interface HasRemoveEvent<E extends HTMLElement, SELF extends HasRemoveEvent<E, SELF>> extends IsElement<E> {
    SELF that();

    default SELF onRemove(EventCallbackFn<Event> callback) {
        element().addEventListener("remove", callback::onEvent);
        return that();
    }
}
