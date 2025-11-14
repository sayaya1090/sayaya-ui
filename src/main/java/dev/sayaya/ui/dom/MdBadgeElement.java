package dev.sayaya.ui.dom;

import elemental2.dom.HTMLElement;
import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

@JsType(isNative = true, namespace = GLOBAL, name="HTMLElement")
public class MdBadgeElement extends HTMLElement {
    public String anchor;
    public HTMLElement anchorElement;
    public String value;
}
