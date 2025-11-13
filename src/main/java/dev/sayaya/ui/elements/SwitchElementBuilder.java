package dev.sayaya.ui.elements;

import dev.sayaya.ui.dom.MdSwitchElement;
import dev.sayaya.ui.elements.interfaces.*;
import org.jboss.elemento.*;

import static org.jboss.elemento.Elements.htmlElement;

public class SwitchElementBuilder implements ElementAttributeMethods<MdSwitchElement, SwitchElementBuilder>, ElementClassListMethods<MdSwitchElement, SwitchElementBuilder>, ElementEventMethods<MdSwitchElement, SwitchElementBuilder>,
        HTMLElementStyleMethods<MdSwitchElement, SwitchElementBuilder>, HTMLElementVisibilityMethods<MdSwitchElement, SwitchElementBuilder>,
        HasAriaLabel<MdSwitchElement, SwitchElementBuilder>,
        Selectable<MdSwitchElement, SwitchElementBuilder>, Requireable<MdSwitchElement, SwitchElementBuilder>, Disableable<MdSwitchElement, SwitchElementBuilder>,
        FormAssociable<MdSwitchElement, SwitchElementBuilder>, HasInputEvent<MdSwitchElement, SwitchElementBuilder>, HasChangeEvent<MdSwitchElement, SwitchElementBuilder> {
    public static SwitchElementBuilder sw() {
        return new SwitchElementBuilder();
    }
    private final HTMLElementBuilder<MdSwitchElement> that = htmlElement("md-switch", MdSwitchElement.class);

    @Override public SwitchElementBuilder select(boolean selected) {
        element().selected = selected;
        return that();
    }
    @Override public boolean isSelected() {
        return element().selected;
    }

    @Override public SwitchElementBuilder disabled(boolean disabled) {
        element().disabled = disabled;
        return that();
    }
    public boolean isDisabled() {
        return element().disabled;
    }

    @Override public SwitchElementBuilder required(boolean required) {
        element().required = required;
        return that();
    }
    public boolean isRequired() {
        return element().required;
    }

    public SwitchElementBuilder icons() {
        return icons(true);
    }
    public SwitchElementBuilder icons(boolean icons) {
        element().icons = icons;
        return that();
    }
    public boolean hasIcons() {
        return element().icons;
    }

    public SwitchElementBuilder showOnlySelectedIcon() {
        return showOnlySelectedIcon(true);
    }
    public SwitchElementBuilder showOnlySelectedIcon(boolean showOnlySelectedIcon) {
        element().showOnlySelectedIcon = showOnlySelectedIcon;
        return icons();
    }
    public boolean showsOnlySelectedIcon() {
        return element().showOnlySelectedIcon;
    }

    public String value() {
        return element().value;
    }
    public String name() {
        return element().name;
    }

    public boolean checkValidity() {
        return element().checkValidity();
    }
    public boolean reportValidity() {
        return element().reportValidity();
    }
    public SwitchElementBuilder setCustomValidity(String message) {
        element().setCustomValidity(message);
        return that();
    }
    @Override public MdSwitchElement element() {
        return that.element();
    }
    @Override public SwitchElementBuilder that() {
        return this;
    }
}
