package dev.sayaya.ui.dom;

import dev.sayaya.ui.elements.interfaces.Validatable;
import elemental2.dom.*;
import elemental2.promise.Promise;
import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

@JsType(isNative = true)
public class MdSelectElement extends HTMLElement implements Validatable {
    public boolean quick;
    public boolean required;
    public boolean disabled;
    public String errorText;
    public String label;
    public String supportingText;
    public boolean error;
    public String menuPositioning;
    public boolean clampMenuWidth;
    public double typeaheadDelay;
    public boolean hasLeadingIcon;
    public String displayText;
    public String value;
    public Double selectedIndex;
    public MdSelectOptionElement[] options;
    public MdSelectOptionElement[] selectedOptions;
    public String name;
    public HTMLFormElement form;
    public NodeList<HTMLLabelElement> labels;
    public ValidityState validity;
    public String validationMessage;
    public boolean willValidate;

    public native void select(String value);
    public native void selectIndex(int index);
    public native void reset();
    public native boolean checkValidity();
    public native boolean reportValidity();
    public native void setCustomValidity(String error);
    public native Promise<Boolean> getUpdateComplete();
    public native void showPicker();

    @JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
    public static class MdFilledSelectElement extends MdSelectElement { }
    @JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
    public static class MdOutlinedSelectElement extends MdSelectElement { }
}