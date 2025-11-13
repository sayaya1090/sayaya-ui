package dev.sayaya.ui.elements;

import dev.sayaya.ui.dom.MdButtonElement;
import dev.sayaya.ui.dom.MdButtonElement.*;
import dev.sayaya.ui.dom.MdIconElement;
import dev.sayaya.ui.elements.interfaces.*;
import org.jboss.elemento.*;

import static org.jboss.elemento.Elements.htmlContainer;

public interface ButtonElementBuilder<E extends MdButtonElement, SELF extends ButtonElementBuilder<E, SELF>> extends HTMLElementStyleMethods<E, SELF>,
        HTMLElementVisibilityMethods<E, SELF>, ElementAttributeMethods<E, SELF>, ElementClassListMethods<E, SELF>, ElementContainerMethods<E, SELF>, ElementEventMethods<E, SELF>,
        ElementTextMethods<E, SELF>, ElementIdMethods<E, SELF>, HasIconSlot<E, SELF>, HasAriaLabel<E, SELF>, Disableable<E, SELF>, Linkable<E, SELF>, FormAssociable<E, SELF>, Typeable<E, SELF>, Clickable<E, SELF> {
    static ButtonPrepareElementBuilder button() {
        return new ButtonPrepareElementBuilder();
    }
    default SELF icon(String icon) {
        return icon(icon, false);
    }
    default SELF icon(String icon, boolean trailing) {
        return icon(IconElementBuilder.icon(icon), trailing);
    }
    default SELF icon(IconElementBuilder icon) {
        return icon(icon.element(), false);
    }
    default SELF icon(IconElementBuilder icon, boolean trailing) {
        return icon(icon.element(), trailing);
    }
    default SELF icon(MdIconElement icon) {
        return icon(icon, false);
    }
    default SELF icon(MdIconElement icon, boolean trailing) {
        this.add(icon);
        icon.setAttribute("slot", "icon");
        element().trailingIcon = trailing;
        return that();
    }
    @Override default SELF disabled(boolean disabled) {
        element().disabled = disabled;
        return that();
    }
    default SELF softDisabled(boolean softDisabled) {
        element().softDisabled = softDisabled;
        return that();
    }
    default SELF download(String filename) {
        element().download = filename;
        return that();
    }
    default SELF text(String text) {
        element().append(elemental2.dom.DomGlobal.document.createTextNode(text));
        return that();
    }
    E element();
    final class ButtonPrepareElementBuilder {
        public ElevatedButtonElementBuilder elevated() {
            return new ElevatedButtonElementBuilder();
        }
        public FilledButtonElementBuilder filled() {
            return new FilledButtonElementBuilder();
        }
        public FilledTonalButtonElementBuilder filledTonal() {
            return new FilledTonalButtonElementBuilder();
        }
        public OutlinedButtonElementBuilder outlined() {
            return new OutlinedButtonElementBuilder();
        }
        public TextButtonElementBuilder text() {
            return new TextButtonElementBuilder();
        }
        public IconButtonElementBuilder.PlainIconButtonElementBuilder icon(String icon) {
            return new IconButtonElementBuilder.PlainIconButtonElementBuilder().icon(icon);
        }
        /*public FabElementBuilder.PlainFabElementBuilder fab() {
            return new FabElementBuilder.PlainFabElementBuilder();
        }*/
    }
    final class ElevatedButtonElementBuilder implements ButtonElementBuilder<MdElevatedButtonElement, ElevatedButtonElementBuilder> {
        private final HTMLContainerBuilder<MdElevatedButtonElement> that = htmlContainer("md-elevated-button", MdElevatedButtonElement.class);
        @Override
        public ElevatedButtonElementBuilder that() {
            return this;
        }
        @Override
        public MdElevatedButtonElement element() {
            return that.element();
        }
    }
    final class FilledButtonElementBuilder implements ButtonElementBuilder<MdFilledButtonElement, FilledButtonElementBuilder> {
        private final HTMLContainerBuilder<MdFilledButtonElement> that = htmlContainer("md-filled-button", MdFilledButtonElement.class);
        @Override
        public FilledButtonElementBuilder that() {
            return this;
        }
        @Override
        public MdFilledButtonElement element() {
            return that.element();
        }
    }
    final class FilledTonalButtonElementBuilder implements ButtonElementBuilder<MdFilledTonalButtonElement, FilledTonalButtonElementBuilder> {
        private final HTMLContainerBuilder<MdFilledTonalButtonElement> that = htmlContainer("md-filled-tonal-button", MdFilledTonalButtonElement.class);
        @Override
        public FilledTonalButtonElementBuilder that() {
            return this;
        }
        @Override
        public MdFilledTonalButtonElement element() {
            return that.element();
        }
    }
    final class OutlinedButtonElementBuilder implements ButtonElementBuilder<MdOutlinedButtonElement, OutlinedButtonElementBuilder> {
        private final HTMLContainerBuilder<MdOutlinedButtonElement> that = htmlContainer("md-outlined-button", MdOutlinedButtonElement.class);
        @Override
        public OutlinedButtonElementBuilder that() {
            return this;
        }
        @Override
        public MdOutlinedButtonElement element() {
            return that.element();
        }
    }
    final class TextButtonElementBuilder implements ButtonElementBuilder<MdTextButtonElement, TextButtonElementBuilder> {
        private final HTMLContainerBuilder<MdTextButtonElement> that = htmlContainer("md-text-button", MdTextButtonElement.class);
        @Override
        public TextButtonElementBuilder that() {
            return this;
        }
        @Override
        public MdTextButtonElement element() {
            return that.element();
        }
    }
}
