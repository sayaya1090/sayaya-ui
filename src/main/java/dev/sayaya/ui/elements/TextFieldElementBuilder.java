package dev.sayaya.ui.elements;

import dev.sayaya.ui.dom.MdTextFieldElement;
import dev.sayaya.ui.dom.MdTextFieldElement.MdFilledTextFieldElement;
import dev.sayaya.ui.dom.MdTextFieldElement.MdOutlinedTextFieldElement;
import dev.sayaya.ui.elements.interfaces.*;
import elemental2.dom.Event;
import org.jboss.elemento.*;

import static org.jboss.elemento.Elements.htmlContainer;

public interface TextFieldElementBuilder<E extends MdTextFieldElement, SELF extends TextFieldElementBuilder<E, SELF>> extends ElementAttributeMethods<E, SELF>, ElementClassListMethods<E, SELF>, ElementConsumerMethods<E, SELF>,
        ElementEventMethods<E, SELF>, ElementTextMethods<E, SELF>, HTMLElementStyleMethods<E, SELF>, HTMLElementVisibilityMethods<E, SELF>, HasLeadingIconSlot<E, SELF>, HasTrailingIconSlot<E, SELF>, HasAriaLabel<E, SELF>,
        Requireable<E, SELF>, Disableable<E, SELF> {
    static TextFieldPrepareElementBuilder textField() {
        return new TextFieldPrepareElementBuilder();
    }
    HTMLContainerBuilder<E> delegate();
    default SELF style(String style) {
        delegate().style(style);
        return that();
    }
    default SELF name(String name) {
        element().name = name;
        return that();
    }
    default SELF label(String label) {
        element().label = label;
        return that();
    }
    default SELF value(String value) {
        element().value = value;
        return that();
    }
    default String value() {
        return element().value;
    }
    default SELF type(InputType type) {
        element().type = type!=null?type.name():InputType.text.name();
        return that();
    }
    default SELF placeholder(String placeholder) {
        element().placeholder = placeholder;
        return that();
    }
    default SELF rows(int rows) {
        element().rows = rows;
        return that();
    }
    default SELF cols(int cols) {
        element().cols = cols;
        return that();
    }
    @Override default SELF required(boolean required) {
        element().required = required;
        return that();
    }
    @Override default SELF disabled(boolean disabled) {
        element().disabled = disabled;
        return that();
    }
    default SELF pattern(String pattern) {
        attr("pattern", pattern);
        return that();
    }
    default SELF error(boolean error) {
        element().error = error;
        return that();
    }
    default SELF error(String errorText) {
        return error(true).errorText(errorText);
    }
    default SELF errorText(String errorText) {
        element().errorText = errorText;
        return that();
    }
    default SELF prefixText(String prefixText) {
        element().prefixText = prefixText;
        return that();
    }
    default SELF suffixText(String suffixText) {
        element().suffixText = suffixText;
        return that();
    }
    default SELF supportingText(String supportingText) {
        return attr("supporting-text", supportingText);
    }
    default SELF max(String max) {
        element().max = max;
        return that();
    }
    default SELF maxLength(int maxLength) {
        element().maxLength = maxLength;
        return that();
    }
    default SELF min(String min) {
        element().min = min;
        return that();
    }
    default SELF minLength(int minLength) {
        element().minLength = minLength;
        return that();
    }
    default SELF readOnly(boolean readOnly) {
        element().readOnly = readOnly;
        return that();
    }
    default SELF multiple(boolean multiple) {
        element().multiple = multiple;
        return that();
    }
    default SELF step(String step) {
        element().step = step;
        return that();
    }
    default SELF autocomplete(String autocomplete) {
        element().autocomplete = autocomplete;
        return that();
    }
    default SELF inputMode(String inputMode) {
        element().inputMode = inputMode;
        return that();
    }
    default SELF onChange(EventCallbackFn<Event> callback) {
        element().addEventListener("change", callback::onEvent);
        return that();
    }
    default SELF onInput(EventCallbackFn<Event> callback) {
        element().addEventListener("input", callback::onEvent);
        return that();
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
    default SELF select() {
        element().select();
        return that();
    }
    default SELF reset() {
        element().reset();
        return that();
    }
    E element();
    final class TextFieldPrepareElementBuilder {
        public FilledTextFieldElementBuilder filled() {
            return new FilledTextFieldElementBuilder();
        }
        public OutlinedTextFieldElementBuilder outlined() {
            return new OutlinedTextFieldElementBuilder();
        }
    }
    final class FilledTextFieldElementBuilder implements TextFieldElementBuilder<MdFilledTextFieldElement, FilledTextFieldElementBuilder> {
        private final HTMLContainerBuilder<MdFilledTextFieldElement> that = htmlContainer("md-filled-text-field", MdFilledTextFieldElement.class);
        @Override
        public FilledTextFieldElementBuilder that() {
            return this;
        }
        @Override
        public HTMLContainerBuilder<MdFilledTextFieldElement> delegate() {
            return that;
        }
        @Override
        public MdFilledTextFieldElement element() {
            return that.element();
        }
    }
    final class OutlinedTextFieldElementBuilder implements TextFieldElementBuilder<MdOutlinedTextFieldElement, OutlinedTextFieldElementBuilder> {
        private final HTMLContainerBuilder<MdOutlinedTextFieldElement> that = htmlContainer("md-outlined-text-field", MdOutlinedTextFieldElement.class);
        @Override
        public OutlinedTextFieldElementBuilder that() {
            return this;
        }
        @Override
        public HTMLContainerBuilder<MdOutlinedTextFieldElement> delegate() {
            return that;
        }
        @Override
        public MdOutlinedTextFieldElement element() {
            return that.element();
        }
    }
}
