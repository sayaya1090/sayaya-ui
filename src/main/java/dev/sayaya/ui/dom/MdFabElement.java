package dev.sayaya.ui.dom;

import elemental2.dom.HTMLElement;
import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

@JsType(isNative = true)
public class MdFabElement extends HTMLElement {
    public String variant;
    public String touchTarget;
    public String size;
    public String label;
    public boolean lowered;

    @JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
    public static class MdPlainFabElement extends MdFabElement {

    }

    @JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
    public static class MdBrandedFabElement extends MdFabElement {

    }
}
