package dev.sayaya.ui.elements.interfaces;

import elemental2.dom.HTMLElement;
import org.jboss.elemento.ElementContainerMethods;
import org.jboss.elemento.IsElement;

public interface HasActionsSlot<E extends HTMLElement, SELF extends HasActionsSlot<E, SELF>> extends ElementContainerMethods<E, SELF> {
    default SELF actions(IsElement<? extends HTMLElement> element) {
        return actions(element.element());
    }
    default SELF actions(HTMLElement element) {
        element.setAttribute("slot", "actions");
        add(element);
        return that();
    }
}
