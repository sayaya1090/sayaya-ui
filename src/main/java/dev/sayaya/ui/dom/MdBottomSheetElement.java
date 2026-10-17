package dev.sayaya.ui.dom;

import elemental2.dom.HTMLElement;
import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

/** sayaya-ui 자체 구현 M3 bottom sheet 바인딩 (labs-bundle/src/sayaya-sheet.js). */
@JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
public class MdBottomSheetElement extends HTMLElement {
    public boolean modal;
    public boolean open;
    public boolean expanded;
    public boolean dragHandle;
    public String handleLabel;
    public native void show();
    public native void close();
    public native void close(String reason);
}
