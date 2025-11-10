package dev.sayaya.ui.elements.interfaces;

import elemental2.dom.HTMLElement;
import org.jboss.elemento.ElementContainerMethods;
import org.jboss.elemento.IsElement;

import static org.jboss.elemento.Elements.div;

public interface HasSupportingTextSlot<E extends HTMLElement, SELF extends HasSupportingTextSlot<E, SELF>> extends ElementContainerMethods<E, SELF> {
    default SELF supportingTextSlot(String text) {
        return supportingTextSlot(div().add(text));
    }
    default SELF supportingTextSlot(IsElement<? extends HTMLElement> element) {
        return supportingTextSlot(element.element());
    }
    default SELF supportingTextSlot(HTMLElement element) {
        element.setAttribute("slot", "supporting-text");
        add(element);
        return that();
    }
}
