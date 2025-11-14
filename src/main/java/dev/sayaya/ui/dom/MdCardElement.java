package dev.sayaya.ui.dom;

import elemental2.dom.HTMLElement;
import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

@JsType(isNative = true)
public class MdCardElement extends HTMLElement {
    @JsType(isNative = true, namespace = GLOBAL, name="HTMLElement")
    public static class MdElevatedCardElement extends MdCardElement {

    }
    @JsType(isNative = true, namespace = GLOBAL, name="HTMLElement")
    public static class MdFilledCardElement extends MdCardElement {

    }
    @JsType(isNative = true, namespace = GLOBAL, name="HTMLElement")
    public static class MdOutlinedCardElement extends MdCardElement {

    }
}
