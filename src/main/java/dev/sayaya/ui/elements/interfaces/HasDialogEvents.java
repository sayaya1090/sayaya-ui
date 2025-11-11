package dev.sayaya.ui.elements.interfaces;

import elemental2.dom.Event;
import elemental2.dom.HTMLElement;
import org.jboss.elemento.EventCallbackFn;
import org.jboss.elemento.IsElement;

/**
 * Interface for elements that support dialog-specific events.
 * Provides event handlers for dialog open/close lifecycle events.
 */
public interface HasDialogEvents<E extends HTMLElement, SELF extends HasDialogEvents<E, SELF>> extends IsElement<E> {
    SELF that();

    default SELF onOpen(EventCallbackFn<Event> callback) {
        element().addEventListener("open", callback::onEvent);
        return that();
    }

    default SELF onOpened(EventCallbackFn<Event> callback) {
        element().addEventListener("opened", callback::onEvent);
        return that();
    }

    default SELF onClose(EventCallbackFn<Event> callback) {
        element().addEventListener("close", callback::onEvent);
        return that();
    }

    default SELF onClosed(EventCallbackFn<Event> callback) {
        element().addEventListener("closed", callback::onEvent);
        return that();
    }

    default SELF onCancel(EventCallbackFn<Event> callback) {
        element().addEventListener("cancel", callback::onEvent);
        return that();
    }
}
