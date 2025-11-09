package dev.sayaya.ui.dom;

import elemental2.dom.HTMLElement;
import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

@JsType(isNative = true, namespace = GLOBAL, name="HTMLElement")
public class MdFocusRingElement extends HTMLElement {
    public boolean visible;
    public boolean inward;
    public String htmlFor;
    public HTMLElement control;

    public native void attach(HTMLElement control);
    public native void detach();
}
