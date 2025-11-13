package dev.sayaya.ui.elements.interfaces;

import elemental2.dom.Element;
import elemental2.dom.MouseEvent;
import org.jboss.elemento.EventCallbackFn;
import org.jboss.elemento.EventType;

public interface Clickable<E extends Element, SELF extends Clickable<E, SELF>>
        extends org.jboss.elemento.ElementEventMethods<E, SELF> {

    default SELF onClick(EventCallbackFn<MouseEvent> callback) {
        return on(EventType.click, callback);
    }
}
