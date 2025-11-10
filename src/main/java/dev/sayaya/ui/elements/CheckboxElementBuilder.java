package dev.sayaya.ui.elements;

import dev.sayaya.ui.dom.MdCheckboxElement;
import dev.sayaya.ui.elements.interfaces.*;
import org.jboss.elemento.*;

import static org.jboss.elemento.Elements.htmlElement;

public class CheckboxElementBuilder implements ElementAttributeMethods<MdCheckboxElement, CheckboxElementBuilder>, ElementClassListMethods<MdCheckboxElement, CheckboxElementBuilder>, ElementEventMethods<MdCheckboxElement, CheckboxElementBuilder>,
        HTMLElementStyleMethods<MdCheckboxElement, CheckboxElementBuilder>, HTMLElementVisibilityMethods<MdCheckboxElement, CheckboxElementBuilder>,
        HasAriaLabel<MdCheckboxElement, CheckboxElementBuilder>, Selectable<MdCheckboxElement, CheckboxElementBuilder>, Disableable<MdCheckboxElement, CheckboxElementBuilder>, FormAssociable<MdCheckboxElement, CheckboxElementBuilder>, Requireable<MdCheckboxElement, CheckboxElementBuilder>,
        HasInputEvent<MdCheckboxElement, CheckboxElementBuilder>, HasChangeEvent<MdCheckboxElement, CheckboxElementBuilder> {
    public static CheckboxElementBuilder checkbox() {
        return new CheckboxElementBuilder();
    }
    private final HTMLElementBuilder<MdCheckboxElement> that = htmlElement("md-checkbox", MdCheckboxElement.class);
    @Override
    public MdCheckboxElement element() {
        return that.element();
    }

    @Override
    public CheckboxElementBuilder that() {
        return this;
    }
    public CheckboxElementBuilder id(String id) {
        element().id = id;
        return this;
    }
    @Override public CheckboxElementBuilder select(boolean checked) {
        return state(checked? CheckboxState.CHECKED: CheckboxState.UNCHECKED);
    }
    @Override public boolean isSelected() {
        var elem = element();
        if(elem.indeterminate) return false;
        return elem.checked;
    }
    public boolean isIndeterminate() {
        return element().indeterminate;
    }
    public CheckboxElementBuilder indeterminate() {
        return state(CheckboxState.INDETERMINATE);
    }
    private CheckboxElementBuilder state(CheckboxState state) {
        if(state==null || state == CheckboxState.UNCHECKED) {
            element().checked = false;
            element().indeterminate = false;
        } else if(state == CheckboxState.CHECKED) {
            element().checked = true;
            element().indeterminate = false;
        } else if(state == CheckboxState.INDETERMINATE) {
            element().checked = false;
            element().indeterminate = true;
        }
        return this;
    }
    @Override public CheckboxElementBuilder disabled(boolean disabled) {
        element().disabled = disabled;
        return this;
    }
    public boolean isDisabled() {
        return element().disabled;
    }
    @Override public CheckboxElementBuilder required(boolean required) {
        element().required = required;
        return this;
    }
    public boolean isRequired() {
        return element().required;
    }
    public String value() {
        return element().value;
    }
    public String name() {
        return element().name;
    }
    private enum CheckboxState {
        UNCHECKED, CHECKED, INDETERMINATE
    }
}
