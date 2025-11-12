package dev.sayaya.ui.elements;

import dev.sayaya.ui.dom.MdRadioElement;
import dev.sayaya.ui.elements.interfaces.*;
import org.jboss.elemento.*;

import static org.jboss.elemento.Elements.htmlElement;

public class RadioElementBuilder implements ElementAttributeMethods<MdRadioElement, RadioElementBuilder>, ElementClassListMethods<MdRadioElement, RadioElementBuilder>, ElementEventMethods<MdRadioElement, RadioElementBuilder>,
        HTMLElementStyleMethods<MdRadioElement, RadioElementBuilder>,
        HTMLElementVisibilityMethods<MdRadioElement, RadioElementBuilder>, HasAriaLabel<MdRadioElement, RadioElementBuilder>,
        Selectable<MdRadioElement, RadioElementBuilder>, Disableable<MdRadioElement, RadioElementBuilder>,
        FormAssociable<MdRadioElement, RadioElementBuilder>, Requireable<MdRadioElement, RadioElementBuilder>,
        HasInputEvent<MdRadioElement, RadioElementBuilder>, HasChangeEvent<MdRadioElement, RadioElementBuilder> {
    public static RadioElementBuilder radio() {
        return new RadioElementBuilder();
    }
    private final HTMLElementBuilder<MdRadioElement> that = htmlElement("md-radio", MdRadioElement.class);

    @Override public RadioElementBuilder select(boolean checked) {
        element().checked = checked;
        return that();
    }
    @Override public boolean isSelected() {
        return element().checked;
    }
    @Override public RadioElementBuilder disabled(boolean disabled) {
        element().disabled = disabled;
        return that();
    }
    public boolean isDisabled() {
        return element().disabled;
    }
    @Override public RadioElementBuilder required(boolean required) {
        element().required = required;
        return that();
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
    @Override
    public MdRadioElement element() {
        return that.element();
    }
    @Override
    public RadioElementBuilder that() {
        return this;
    }
}
