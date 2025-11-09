package dev.sayaya.ui.elements.interfaces;

import elemental2.dom.HTMLElement;

public interface Selectable<E extends HTMLElement, SELF extends Selectable<E, SELF>> {
    SELF select(boolean selected);
    default SELF select() {
        return select(true);
    }
    boolean isSelected();
}
