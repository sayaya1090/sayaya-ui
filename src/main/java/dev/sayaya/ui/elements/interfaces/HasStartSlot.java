package dev.sayaya.ui.elements.interfaces;

import dev.sayaya.ui.elements.IconButtonElementBuilder;
import dev.sayaya.ui.elements.IconElementBuilder;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLImageElement;
import org.jboss.elemento.ElementContainerMethods;
import org.jboss.elemento.HTMLElementBuilder;

public interface HasStartSlot<E extends HTMLElement, SELF extends HasStartSlot<E, SELF>> extends ElementContainerMethods<E, SELF> {
    default SELF start(IconElementBuilder icon) {
        return start(icon.element());
    }
    default SELF start(IconButtonElementBuilder<?, ?> icon) {
        return start(icon.element());
    }
    default SELF start(HTMLElementBuilder<HTMLImageElement> icon) {
        return start(icon.element());
    }
    default SELF start(IsSvgElement<?, ?> element) {
        return start(element.element());
    }
    default SELF start(Element icon) {
        this.add(icon);
        icon.setAttribute("slot", "start");
        return that();
    }
}
