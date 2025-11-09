package dev.sayaya.ui.dom;

import elemental2.dom.CSSStyleDeclaration;
import elemental2.dom.Element;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class SvgElement extends Element {
    public int tabIndex;
    public CSSStyleDeclaration style;
}
