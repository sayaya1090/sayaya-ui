package dev.sayaya.ui.dom;

import elemental2.dom.*;
import jsinterop.annotations.JsType;

import java.util.Date;

import static jsinterop.annotations.JsPackage.GLOBAL;

@JsType(isNative = true)
public class MdTextFieldElement extends HTMLElement {
    public boolean disabled;
    public boolean error;
    public String errorText;
    public String label;
    public boolean required;
    public String value;
    public String prefixText;
    public String suffixText;
    public boolean hasLeadingIcon;
    public boolean hasTrailingIcon;
    public String supportingText;
    public String textDirection;
    public int rows;
    public int cols;
    public String inputMode;
    public String max;
    public int maxLength;
    public String min;
    public int minLength;
    public String pattern;
    public String placeholder;
    public boolean readOnly;
    public boolean multiple;
    public String step;
    public String type;
    public String autocomplete;
    public HTMLFormElement form;
    public NodeList<HTMLLabelElement> labels;
    public String name;
    public String selectionDirection;
    public int selectionEnd;
    public int selectionStart;
    public String validationMessage;
    public ValidityState validity;
    public Double valueAsNumber;
    public Date valueAsDate;
    public boolean willValidate;

    public native boolean checkValidity();
    public native boolean reportValidity();
    public native void select();
    public native void setCustomValidity(String error);
    public native void setRangeText(String replacement);
    public native void setSelectionRange(int start, int end);
    public native void setSelectionRange(int start, int end, String direction);
    public native void stepDown();
    public native void stepDown(int n);
    public native void stepUp();
    public native void stepUp(int n);
    public native void reset();

    @JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
    public static class MdFilledTextFieldElement extends MdTextFieldElement { }

    @JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
    public static class MdOutlinedTextFieldElement extends MdTextFieldElement { }
}
