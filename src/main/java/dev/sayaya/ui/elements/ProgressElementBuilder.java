package dev.sayaya.ui.elements;

import dev.sayaya.ui.dom.MdProgressElement;
import dev.sayaya.ui.dom.MdProgressElement.MdCircularProgressElement;
import dev.sayaya.ui.dom.MdProgressElement.MdLinearProgressElement;
import dev.sayaya.ui.elements.interfaces.HasAriaLabel;
import org.jboss.elemento.*;

import static org.jboss.elemento.Elements.htmlContainer;

public interface ProgressElementBuilder<E extends MdProgressElement, SELF extends ProgressElementBuilder<E, SELF>> extends HTMLElementStyleMethods<E, SELF>, HTMLElementVisibilityMethods<E, SELF>,
        ElementAttributeMethods<E, SELF>, ElementClassListMethods<E, SELF>, HasAriaLabel<E, SELF> {
    static ProgressPrepareElementBuilder progress() {
        return new ProgressPrepareElementBuilder();
    }

    default SELF value(double value) {
        element().value = value;
        return that();
    }
    default double getValue() {
        Double val = element().value;
        return val != null ? val : 0;
    }

    default SELF max(double max) {
        element().max = max;
        return that();
    }
    default double getMax() {
        Double val = element().max;
        return val != null ? val : 1;
    }

    default SELF indeterminate() {
        return indeterminate(true);
    }
    default SELF indeterminate(boolean indeterminate) {
        element().indeterminate = indeterminate;
        return that();
    }
    default boolean isIndeterminate() {
        return element().indeterminate;
    }

    default SELF fourColor() {
        return fourColor(true);
    }
    default SELF fourColor(boolean fourColor) {
        element().fourColor = fourColor;
        return that();
    }
    default boolean isFourColor() {
        return element().fourColor;
    }

    E element();
    SELF that();
    final class ProgressPrepareElementBuilder {
        public LinearProgressElementBuilder linear() {
            return new LinearProgressElementBuilder();
        }
        public CircularProgressElementBuilder circular() {
            return new CircularProgressElementBuilder();
        }
    }
    final class LinearProgressElementBuilder implements ProgressElementBuilder<MdLinearProgressElement, LinearProgressElementBuilder> {
        private final HTMLContainerBuilder<MdLinearProgressElement> that = htmlContainer("md-linear-progress", MdLinearProgressElement.class);

        @Override
        public LinearProgressElementBuilder that() {
            return this;
        }
        @Override
        public MdLinearProgressElement element() {
            return that.element();
        }

        public LinearProgressElementBuilder buffer(double buffer) {
            element().buffer = buffer;
            return that();
        }
        public double getBuffer() {
            Double val = element().buffer;
            return val != null ? val : 0;
        }
    }
    final class CircularProgressElementBuilder implements ProgressElementBuilder<MdCircularProgressElement, CircularProgressElementBuilder> {
        private final HTMLContainerBuilder<MdCircularProgressElement> that = htmlContainer("md-circular-progress", MdCircularProgressElement.class);
        @Override
        public CircularProgressElementBuilder that() {
            return this;
        }
        @Override
        public MdCircularProgressElement element() {
            return that.element();
        }
    }
}
