package dev.sayaya.ui.dom;

import dev.sayaya.ui.elements.interfaces.Validatable;
import elemental2.dom.*;
import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

@JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
public class MdSwitchElement extends HTMLElement implements Validatable {
    public boolean disabled;
    public boolean selected;
    public boolean icons;
    public boolean showOnlySelectedIcon;
    public boolean required;
    public String value;
    public String name;
    public HTMLFormElement form;
    public NodeList<HTMLLabelElement> labels;
    public ValidityState validity;
    public String validationMessage;
    public boolean willValidate;

    public native boolean checkValidity();
    public native boolean reportValidity();
    public native void setCustomValidity(String message);
}
