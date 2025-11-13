package dev.sayaya.ui.elements.interfaces;

import jsinterop.annotations.JsType;

@JsType(isNative = true)
public interface Validatable {
    boolean checkValidity();
    boolean reportValidity();
}
