package dev.sayaya.ui.elements;

import dev.sayaya.ui.dom.MdSelectElement;
import dev.sayaya.ui.dom.MdSelectElement.MdFilledSelectElement;
import dev.sayaya.ui.dom.MdSelectElement.MdOutlinedSelectElement;
import dev.sayaya.ui.dom.MdSelectOptionElement;
import dev.sayaya.ui.elements.interfaces.*;
import org.jboss.elemento.*;

import static org.jboss.elemento.Elements.htmlContainer;

public interface SelectElementBuilder<E extends MdSelectElement, SELF extends SelectElementBuilder<E, SELF>> extends HTMLElementStyleMethods<E, SELF>, HTMLElementVisibilityMethods<E, SELF>,
        ElementAttributeMethods<E, SELF>, ElementClassListMethods<E, SELF>, ElementContainerMethods<E, SELF>, ElementEventMethods<E, SELF>, ElementTextMethods<E, SELF>,
        Requireable<E, SELF>, Disableable<E, SELF>, FormAssociable<E, SELF>, HasLeadingIconSlot<E, SELF>,
        HasInputEvent<E, SELF>, HasChangeEvent<E, SELF>, HasMenuEvents<E, SELF> {
    static SelectPrepareElementBuilder select() {
        return new SelectPrepareElementBuilder();
    }
    default SelectOptionElementBuilder<SELF> option() {
        return new SelectOptionElementBuilder<>(this);
    }
    @Override default SELF required(boolean required) {
        element().required = required;
        return that();
    }
    @Override default SELF disabled(boolean disabled) {
        element().disabled = disabled;
        return that();
    }
    default String value() {
        return element().value;
    }
    default SELF removeAllOptions() {
        element().innerHTML = "";
        return that();
    }
    default SELF supportingText(String supportingText) {
        element().supportingText = supportingText;
        return that();
    }
    default SELF label(String label) {
        element().label = label;
        return that();
    }
    default SELF errorText(String errorText) {
        element().errorText = errorText;
        return that();
    }
    default SELF error(boolean error) {
        element().error = error;
        return that();
    }
    default SELF quick(boolean quick) {
        element().quick = quick;
        return that();
    }
    default SELF clampMenuWidth(boolean clamp) {
        element().clampMenuWidth = clamp;
        return that();
    }
    default SELF typeaheadDelay(double delay) {
        element().typeaheadDelay = delay;
        return that();
    }
    default SELF menuPositioning(String positioning) {
        element().menuPositioning = positioning;
        return that();
    }
    default void reset() {
        element().reset();
    }
    default void selectByValue(String value) {
        element().select(value);
    }
    default void selectByIndex(int index) {
        element().selectIndex(index);
    }
    default void showPicker() {
        element().showPicker();
    }
    default boolean checkValidity() {
        return element().checkValidity();
    }
    default boolean reportValidity() {
        return element().reportValidity();
    }
    default SELF setCustomValidity(String error) {
        element().setCustomValidity(error);
        return that();
    }
    E element();
    final class SelectPrepareElementBuilder {
        public FilledSelectElementBuilder filled() {
            return new FilledSelectElementBuilder();
        }
        public OutlinedSelectElementBuilder outlined() {
            return new OutlinedSelectElementBuilder();
        }
    }
    final class SelectOptionElementBuilder<P extends SelectElementBuilder<?, P>> implements
            HTMLElementStyleMethods<MdSelectOptionElement, SelectOptionElementBuilder<P>>, HTMLElementVisibilityMethods<MdSelectOptionElement, SelectOptionElementBuilder<P>>,
            ElementAttributeMethods<MdSelectOptionElement, SelectOptionElementBuilder<P>>, ElementClassListMethods<MdSelectOptionElement, SelectOptionElementBuilder<P>>,
            ElementEventMethods<MdSelectOptionElement, SelectOptionElementBuilder<P>>, ElementTextMethods<MdSelectOptionElement, SelectOptionElementBuilder<P>>,
            HasAriaLabel<MdSelectOptionElement, SelectOptionElementBuilder<P>>, HasHeadlineSlot<MdSelectOptionElement, SelectOptionElementBuilder<P>>, HasSupportingTextSlot<MdSelectOptionElement, SelectOptionElementBuilder<P>>,
            Selectable<MdSelectOptionElement, SelectOptionElementBuilder<P>>, Disableable<MdSelectOptionElement, SelectOptionElementBuilder<P>> {
        private final HTMLContainerBuilder<MdSelectOptionElement> that;
        private final SelectElementBuilder<?, P> parent;
        private SelectOptionElementBuilder(SelectElementBuilder<?, P> parent) {
            that = htmlContainer("md-select-option", MdSelectOptionElement.class);
            this.parent = parent;
            parent.add(this);
        }
        public SelectElementBuilder<?, P> end() {
            return parent;
        }
        public SelectOptionElementBuilder<P> value(String value) {
            element().value = value;
            return that();
        }
        @Override public SelectOptionElementBuilder<P> select(boolean selected) {
            element().selected = selected;
            return that();
        }
        @Override public boolean isSelected() {
            return element().selected;
        }
        @Override public MdSelectOptionElement element() {
            return that.element();
        }
        @Override public SelectOptionElementBuilder<P> that() {
            return this;
        }
    }
    final class FilledSelectElementBuilder implements SelectElementBuilder<MdFilledSelectElement, FilledSelectElementBuilder> {
        private final HTMLContainerBuilder<MdFilledSelectElement> that = htmlContainer("md-filled-select", MdFilledSelectElement.class);
        @Override
        public FilledSelectElementBuilder that() {
            return this;
        }
        @Override
        public MdFilledSelectElement element() {
            return that.element();
        }
    }
    final class OutlinedSelectElementBuilder implements SelectElementBuilder<MdOutlinedSelectElement, OutlinedSelectElementBuilder> {
        private final HTMLContainerBuilder<MdOutlinedSelectElement> that = htmlContainer("md-outlined-select", MdOutlinedSelectElement.class);
        @Override
        public OutlinedSelectElementBuilder that() {
            return this;
        }
        @Override
        public MdOutlinedSelectElement element() {
            return that.element();
        }
    }
}
