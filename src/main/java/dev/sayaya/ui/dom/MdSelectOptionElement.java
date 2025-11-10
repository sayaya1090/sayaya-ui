package dev.sayaya.ui.dom;

import elemental2.dom.HTMLElement;
import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

@JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
public class MdSelectOptionElement extends HTMLElement {
    public boolean disabled;
    public boolean selected;
    public String value;
    public String type;
    public String typeaheadText;
    public String displayText;
}