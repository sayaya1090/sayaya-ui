package dev.sayaya.ui.elements.interfaces;

import dev.sayaya.ui.elements.IconButtonElementBuilder;
import dev.sayaya.ui.elements.IconElementBuilder;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLImageElement;
import org.jboss.elemento.ElementContainerMethods;
import org.jboss.elemento.HTMLElementBuilder;

public interface HasTrailingIconSlot<E extends HTMLElement, SELF extends HasTrailingIconSlot<E, SELF>> extends ElementContainerMethods<E, SELF> {
    default SELF iconTrailing(String icon) {
        return iconTrailing(IconElementBuilder.icon(icon));
    }
    default SELF iconTrailing(IconElementBuilder icon) {
        return iconTrailing(icon.element());
    }
    default SELF iconTrailing(IconButtonElementBuilder<?, ?> icon) {
        return iconTrailing(icon.element());
    }
    default SELF iconTrailing(HTMLElementBuilder<HTMLImageElement> icon) {
        return iconTrailing(icon.element());
    }
    default SELF iconTrailing(HTMLElement icon) {
        this.add(icon);
        icon.setAttribute("slot", "trailing-icon");
        return that();
    }
}