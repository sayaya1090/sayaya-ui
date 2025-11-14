package dev.sayaya.ui.elements;

import dev.sayaya.ui.dom.MdCardElement;
import dev.sayaya.ui.dom.MdCardElement.MdElevatedCardElement;
import dev.sayaya.ui.dom.MdCardElement.MdFilledCardElement;
import dev.sayaya.ui.dom.MdCardElement.MdOutlinedCardElement;
import dev.sayaya.ui.elements.interfaces.Clickable;
import dev.sayaya.ui.elements.interfaces.HasAriaLabel;
import org.jboss.elemento.*;

import static org.jboss.elemento.Elements.htmlContainer;

public interface CardElementBuilder<E extends MdCardElement, SELF extends CardElementBuilder<E, SELF>> extends
        HTMLElementStyleMethods<E, SELF>, HTMLElementVisibilityMethods<E, SELF>,
        ElementAttributeMethods<E, SELF>, ElementClassListMethods<E, SELF>,
        ElementContainerMethods<E, SELF>, ElementEventMethods<E, SELF>,
        ElementConsumerMethods<E, SELF>,
        Clickable<E, SELF>, HasAriaLabel<E, SELF> {
    static CardPrepareElementBuilder card() {
        return new CardPrepareElementBuilder();
    }
    E element();
    final class CardPrepareElementBuilder {
        public ElevatedCardElementBuilder elevated() {
            return new ElevatedCardElementBuilder();
        }
        public FilledCardElementBuilder filled() {
            return new FilledCardElementBuilder();
        }
        public OutlinedCardElementBuilder outlined() {
            return new OutlinedCardElementBuilder();
        }
    }
    final class ElevatedCardElementBuilder implements CardElementBuilder<MdElevatedCardElement, ElevatedCardElementBuilder> {
        private final HTMLContainerBuilder<MdElevatedCardElement> that = htmlContainer("md-elevated-card", MdElevatedCardElement.class);
        @Override
        public ElevatedCardElementBuilder that() {
            return this;
        }
        @Override
        public MdElevatedCardElement element() {
            return that.element();
        }
    }
    final class FilledCardElementBuilder implements CardElementBuilder<MdFilledCardElement, FilledCardElementBuilder> {
        private final HTMLContainerBuilder<MdFilledCardElement> that = htmlContainer("md-filled-card", MdFilledCardElement.class);
        @Override
        public FilledCardElementBuilder that() {
            return this;
        }
        @Override
        public MdFilledCardElement element() {
            return that.element();
        }
    }
    final class OutlinedCardElementBuilder implements CardElementBuilder<MdOutlinedCardElement, OutlinedCardElementBuilder> {
        private final HTMLContainerBuilder<MdOutlinedCardElement> that = htmlContainer("md-outlined-card", MdOutlinedCardElement.class);
        @Override
        public OutlinedCardElementBuilder that() {
            return this;
        }
        @Override
        public MdOutlinedCardElement element() {
            return that.element();
        }
    }
}
