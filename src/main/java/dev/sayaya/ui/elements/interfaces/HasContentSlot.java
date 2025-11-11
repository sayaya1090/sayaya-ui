package dev.sayaya.ui.elements.interfaces;

import elemental2.dom.HTMLElement;
import org.jboss.elemento.ElementContainerMethods;
import org.jboss.elemento.IsElement;

import static org.jboss.elemento.Elements.div;

public interface HasContentSlot<E extends HTMLElement, SELF extends HasContentSlot<E, SELF>> extends ElementContainerMethods<E, SELF> {
    default SELF content(String content) {
        return content(div().add(content));
    }
    default SELF content(IsElement<? extends HTMLElement> element) {
        return content(element.element());
    }
    default SELF content(HTMLElement element) {
        element.setAttribute("slot", "content");
        add(element);
        return that();
    }
}
