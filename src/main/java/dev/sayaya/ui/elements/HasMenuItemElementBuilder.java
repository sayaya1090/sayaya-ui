package dev.sayaya.ui.elements;

import elemental2.dom.HTMLElement;
import org.jboss.elemento.ElementContainerMethods;

public interface HasMenuItemElementBuilder<E extends HTMLElement, SELF extends HasMenuItemElementBuilder<E, SELF>> extends ElementContainerMethods<E, SELF> {
    default MenuElementBuilder.MenuItemElementBuilder<SELF> item() {
        var item = new MenuElementBuilder.MenuItemElementBuilder<>(that());
        that().add(item);
        return item;
    }
}
