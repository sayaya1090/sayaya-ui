package dev.sayaya.ui.dom;

import elemental2.dom.HTMLElement;
import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

@JsType(isNative = true)
public class MdProgressElement extends HTMLElement {
    public Double value;
    public Double max;
    public boolean indeterminate;
    public boolean fourColor;

    @JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
    public static class MdLinearProgressElement extends MdProgressElement {
        public Double buffer;
    }
    @JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
    public static class MdCircularProgressElement extends MdProgressElement {

    }
}
