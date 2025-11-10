package dev.sayaya.ui.elements.interfaces;

import elemental2.dom.Event;
import elemental2.dom.HTMLElement;
import org.jboss.elemento.EventCallbackFn;
import org.jboss.elemento.IsElement;

/**
 * Interface for elements that support menu opening/closing events.
 * Typically used for components with dropdown menus like Select or Menu.
 */
public interface HasMenuEvents<E extends HTMLElement, SELF extends HasMenuEvents<E, SELF>> extends IsElement<E> {
    SELF that();

    default SELF onOpening(EventCallbackFn<Event> callback) {
        element().addEventListener("opening", callback::onEvent);
        return that();
    }

    default SELF onOpened(EventCallbackFn<Event> callback) {
        element().addEventListener("opened", callback::onEvent);
        return that();
    }

    default SELF onClosing(EventCallbackFn<Event> callback) {
        element().addEventListener("closing", callback::onEvent);
        return that();
    }

    default SELF onClosed(EventCallbackFn<Event> callback) {
        element().addEventListener("closed", callback::onEvent);
        return that();
    }
}
