package dev.sayaya.ui.elements;

import dev.sayaya.ui.dom.MdSliderElement;
import dev.sayaya.ui.elements.interfaces.Disableable;
import dev.sayaya.ui.elements.interfaces.FormAssociable;
import dev.sayaya.ui.elements.interfaces.HasChangeEvent;
import dev.sayaya.ui.elements.interfaces.HasInputEvent;
import org.jboss.elemento.*;

import static org.jboss.elemento.Elements.htmlElement;

public abstract class SliderElementBuilder<SELF extends SliderElementBuilder<SELF>> implements HTMLElementStyleMethods<MdSliderElement, SELF>, HTMLElementVisibilityMethods<MdSliderElement, SELF>,
        ElementAttributeMethods<MdSliderElement, SELF>, ElementClassListMethods<MdSliderElement, SELF>, ElementEventMethods<MdSliderElement, SELF>,
        Disableable<MdSliderElement, SELF>, FormAssociable<MdSliderElement, SELF>,
        HasInputEvent<MdSliderElement, SELF>, HasChangeEvent<MdSliderElement, SELF> {
    public static SliderContinuousElementBuilder slider() {
        return new SliderContinuousElementBuilder(htmlElement("md-slider", MdSliderElement.class));
    }
    final HTMLElementBuilder<MdSliderElement> that;
    private SliderElementBuilder(HTMLElementBuilder<MdSliderElement> element) {
        this.that = element;
    }

    public SELF labeled() {
        return labeled(true);
    }
    public SELF labeled(boolean labeled) {
        element().labeled = labeled;
        return that();
    }
    public SELF min(double min) {
        element().min = min;
        return that();
    }
    public SELF max(double max) {
        element().max = max;
        return that();
    }
    public SELF step(double step) {
        element().step = step;
        return that();
    }
    public SELF valueLabel(String label) {
        element().valueLabel = label;
        return that();
    }

    @Override public SELF disabled(boolean disabled) {
        element().disabled = disabled;
        return that();
    }

    public double getMin() {
        return element().min;
    }
    public double getMax() {
        return element().max;
    }
    public double getStep() {
        return element().step;
    }
    public boolean isLabeled() {
        return element().labeled;
    }
    public boolean isTicks() {
        return element().ticks;
    }
    public boolean isDisabled() {
        return element().disabled;
    }

    @Override public MdSliderElement element() {return that.element();}

    public static class SliderContinuousElementBuilder extends SliderElementBuilder<SliderContinuousElementBuilder> {
        private SliderContinuousElementBuilder(HTMLElementBuilder<MdSliderElement> element) {
            super(element);
            element().range = false;
            element().ticks = false;
        }
        @Override public SliderContinuousElementBuilder that() {return this;}

        public SliderContinuousElementBuilder value(double value) {
            element().value = value;
            return that();
        }
        public double getValue() {
            Double val = element().value;
            return val != null ? val : 0;
        }

        public SliderDiscreteElementBuilder ticks(double step) {
            return new SliderDiscreteElementBuilder(that).ticks(step);
        }
        public SliderRangeElementBuilder range() {
            return new SliderRangeElementBuilder(that);
        }
    }

    public static class SliderDiscreteElementBuilder extends SliderElementBuilder<SliderDiscreteElementBuilder> {
        private SliderDiscreteElementBuilder(HTMLElementBuilder<MdSliderElement> element) {
            super(element);
            element().ticks = true;
            element().range = false;
        }

        public SliderDiscreteElementBuilder value(double value) {
            element().value = value;
            return that();
        }
        public double getValue() {
            Double val = element().value;
            return val != null ? val : 0;
        }

        public SliderDiscreteElementBuilder ticks(double step) {
            element().step = step;
            return that();
        }

        public SliderContinuousElementBuilder continuous() {
            element().ticks = false;
            return new SliderContinuousElementBuilder(that);
        }
        public SliderRangeElementBuilder range() {
            return new SliderRangeElementBuilder(that);
        }
        @Override public SliderDiscreteElementBuilder that() {return this;}
    }

    public static class SliderRangeElementBuilder extends SliderElementBuilder<SliderRangeElementBuilder> {
        private SliderRangeElementBuilder(HTMLElementBuilder<MdSliderElement> element) {
            super(element);
            element().range = true;
            element().ticks = false;
        }

        public SliderRangeElementBuilder valueStart(double value) {
            element().valueStart = value;
            return that();
        }
        public SliderRangeElementBuilder valueEnd(double value) {
            element().valueEnd = value;
            return that();
        }
        public SliderRangeElementBuilder valueLabelStart(String label) {
            element().valueLabelStart = label;
            return that();
        }
        public SliderRangeElementBuilder valueLabelEnd(String label) {
            element().valueLabelEnd = label;
            return that();
        }
        public SliderRangeElementBuilder ariaLabelStart(String label) {
            element().ariaLabelStart = label;
            return that();
        }
        public SliderRangeElementBuilder ariaLabelEnd(String label) {
            element().ariaLabelEnd = label;
            return that();
        }
        public SliderRangeElementBuilder nameStart(String name) {
            element().nameStart = name;
            return that();
        }
        public SliderRangeElementBuilder nameEnd(String name) {
            element().nameEnd = name;
            return that();
        }

        public double getValueStart() {
            Double val = element().valueStart;
            return val != null ? val : 0;
        }
        public double getValueEnd() {
            Double val = element().valueEnd;
            return val != null ? val : 0;
        }
        public boolean isRange() {
            return element().range;
        }

        public SliderRangeDiscreteElementBuilder ticks(double step) {
            return new SliderRangeDiscreteElementBuilder(that).ticks(step);
        }

        @Override public SliderRangeElementBuilder that() {return this;}
    }

    public static class SliderRangeDiscreteElementBuilder extends SliderElementBuilder<SliderRangeDiscreteElementBuilder> {
        private SliderRangeDiscreteElementBuilder(HTMLElementBuilder<MdSliderElement> element) {
            super(element);
            element().range = true;
            element().ticks = true;
        }

        public SliderRangeDiscreteElementBuilder valueStart(double value) {
            element().valueStart = value;
            return that();
        }
        public SliderRangeDiscreteElementBuilder valueEnd(double value) {
            element().valueEnd = value;
            return that();
        }
        public SliderRangeDiscreteElementBuilder ticks(double step) {
            element().step = step;
            return that();
        }
        public SliderRangeDiscreteElementBuilder valueLabelStart(String label) {
            element().valueLabelStart = label;
            return that();
        }
        public SliderRangeDiscreteElementBuilder valueLabelEnd(String label) {
            element().valueLabelEnd = label;
            return that();
        }
        public SliderRangeDiscreteElementBuilder ariaLabelStart(String label) {
            element().ariaLabelStart = label;
            return that();
        }
        public SliderRangeDiscreteElementBuilder ariaLabelEnd(String label) {
            element().ariaLabelEnd = label;
            return that();
        }
        public SliderRangeDiscreteElementBuilder nameStart(String name) {
            element().nameStart = name;
            return that();
        }
        public SliderRangeDiscreteElementBuilder nameEnd(String name) {
            element().nameEnd = name;
            return that();
        }

        public double getValueStart() {
            Double val = element().valueStart;
            return val != null ? val : 0;
        }
        public double getValueEnd() {
            Double val = element().valueEnd;
            return val != null ? val : 0;
        }

        @Override public SliderRangeDiscreteElementBuilder that() {return this;}
    }
}
