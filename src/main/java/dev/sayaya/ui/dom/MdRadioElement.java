package dev.sayaya.ui.dom;

import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLFormElement;
import elemental2.dom.HTMLLabelElement;
import elemental2.dom.NodeList;
import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

@JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
public class MdRadioElement extends HTMLElement {
    public boolean disabled;
    public String value;
    public boolean checked;
    public String name;
    public boolean required;
    public HTMLFormElement form;
    public NodeList<HTMLLabelElement> labels;
}
