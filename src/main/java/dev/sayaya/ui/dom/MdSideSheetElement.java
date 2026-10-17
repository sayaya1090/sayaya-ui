package dev.sayaya.ui.dom;

import elemental2.dom.HTMLElement;
import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

/** sayaya-ui 자체 구현 M3 side sheet 바인딩 (labs-bundle/src/sayaya-sheet.js). */
@JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
public class MdSideSheetElement extends HTMLElement {
    public boolean modal;
    public boolean open;
    public String pivot;
    public boolean detached;
    public boolean divider;
    public boolean back;
    public String headline;
    public String closeLabel;
    public String backLabel;
    public native void show();
    public native void close();
    public native void close(String reason);
}
