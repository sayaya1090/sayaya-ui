package dev.sayaya.ui.elements.interfaces;

import dev.sayaya.ui.elements.IconButtonElementBuilder;
import dev.sayaya.ui.elements.IconElementBuilder;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLImageElement;
import org.jboss.elemento.ElementContainerMethods;
import org.jboss.elemento.HTMLElementBuilder;

public interface HasEndSlot<E extends HTMLElement, SELF extends HasEndSlot<E, SELF>> extends ElementContainerMethods<E, SELF> {
    default SELF end(IconElementBuilder icon) {
        return end(icon.element());
    }
    default SELF end(IconButtonElementBuilder<?, ?> icon) {
        return end(icon.element());
    }
    default SELF end(HTMLElementBuilder<HTMLImageElement> icon) {
        return end(icon.element());
    }
    default SELF end(IsSvgElement<?, ?> element) {
        return end(element.element());
    }
    default SELF end(Element icon) {
        this.add(icon);
        icon.setAttribute("slot", "end");
        return that();
    }
}
