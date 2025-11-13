package dev.sayaya.ui.dom;

import elemental2.dom.HTMLElement;
import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

@JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
public class MdMenuItemElement extends HTMLElement {
    public boolean disabled;
    public String type;
    public String href;
    public String target;
    public boolean keepOpen;
    public boolean selected;
    public String typeaheadText;
}
