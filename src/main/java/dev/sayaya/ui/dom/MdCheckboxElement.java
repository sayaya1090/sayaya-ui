package dev.sayaya.ui.dom;

import elemental2.dom.HTMLElement;
import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

@JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
public class MdCheckboxElement extends HTMLElement {
    public boolean checked;
    public boolean indeterminate;
    public boolean disabled;
    public boolean required;
    public String value;
    public String name;
}
