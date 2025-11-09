package dev.sayaya.ui.dom;

import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLFormElement;
import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

@JsType(isNative = true, namespace = GLOBAL)
public class MdButtonElement extends HTMLElement {
    public boolean disabled;
    public boolean softDisabled;
    public String href;
    public String target;
    public String download;
    public boolean trailingIcon;
    public boolean hasIcon;
    public String type;
    public String value;
    public String name;
    public HTMLFormElement form;
    @JsType(isNative = true, namespace = GLOBAL, name="HTMLElement")
    public static class MdElevatedButtonElement extends MdButtonElement {

    }
    @JsType(isNative = true, namespace = GLOBAL, name="HTMLElement")
    public static class MdFilledButtonElement extends MdButtonElement {

    }
    @JsType(isNative = true, namespace = GLOBAL, name="HTMLElement")
    public static class MdFilledTonalButtonElement extends MdButtonElement {

    }
    @JsType(isNative = true, namespace = GLOBAL, name="HTMLElement")
    public static class MdOutlinedButtonElement extends MdButtonElement {

    }
    @JsType(isNative = true, namespace = GLOBAL, name="HTMLElement")
    public static class MdTextButtonElement extends MdButtonElement {

    }
}