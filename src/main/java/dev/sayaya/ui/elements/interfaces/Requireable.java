package dev.sayaya.ui.elements.interfaces;

import elemental2.dom.HTMLElement;

public interface Requireable<E extends HTMLElement, SELF extends Requireable<E, SELF>> {
    default SELF required() {
        return required(true);
    }
    SELF required(boolean required);
}
