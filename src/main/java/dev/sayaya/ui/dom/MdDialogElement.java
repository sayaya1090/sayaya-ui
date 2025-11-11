package dev.sayaya.ui.dom;

import elemental2.dom.HTMLElement;
import elemental2.promise.Promise;
import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

@JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
public class MdDialogElement extends HTMLElement {
    public String returnValue;
    public String type;
    public boolean open;
    public boolean quick;
    public boolean noFocusTrap;

    public native Promise<Void> show();
    public native Promise<String> close();
}
