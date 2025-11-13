package dev.sayaya.ui.dom;

import elemental2.dom.HTMLElement;
import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

@JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
public class MdSubMenuElement extends HTMLElement {
    public String anchorCorner;
    public String menuCorner;
    public double hoverOpenDelay;
    public double hoverCloseDelay;
    public boolean isSubmenu;
    public MdMenuItemElement[] items;
    public MdMenuElement menu;

    public native void close();
    public native void show();
}
