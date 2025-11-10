package dev.sayaya.ui.elements.interfaces;

import elemental2.dom.HTMLElement;
import org.jboss.elemento.ElementContainerMethods;
import org.jboss.elemento.IsElement;

import static org.jboss.elemento.Elements.div;

public interface HasHeadlineSlot<E extends HTMLElement, SELF extends HasHeadlineSlot<E, SELF>> extends ElementContainerMethods<E, SELF> {
    default SELF headline(String headline) {
        return headline(div().add(headline));
    }
    default SELF headline(IsElement<? extends HTMLElement> element) {
        return headline(element.element());
    }
    default SELF headline(HTMLElement element) {
        element.setAttribute("slot", "headline");
        add(element);
        return that();
    }
}
