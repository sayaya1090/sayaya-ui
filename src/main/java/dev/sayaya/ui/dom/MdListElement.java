package dev.sayaya.ui.dom;

import elemental2.dom.HTMLElement;
import elemental2.promise.Promise;
import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

@JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
public class MdListElement extends HTMLElement {
    public MdListItemElement[] items;
    public native MdListItemElement activateNextItem();
    public native MdListItemElement activatePreviousItem();

    public native Promise<Boolean> getUpdateComplete();
}