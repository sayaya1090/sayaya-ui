package dev.sayaya.ui.elements;

import dev.sayaya.ui.dom.MdChipElement;
import dev.sayaya.ui.dom.MdChipElement.MdAssistChipElement;
import dev.sayaya.ui.dom.MdChipElement.MdFilterChipElement;
import dev.sayaya.ui.dom.MdChipElement.MdInputChipElement;
import dev.sayaya.ui.dom.MdChipElement.MdSuggestionChipElement;
import dev.sayaya.ui.dom.MdChipSetElement;
import dev.sayaya.ui.elements.interfaces.*;
import org.jboss.elemento.*;

import static org.jboss.elemento.Elements.htmlContainer;

public class ChipsElementBuilder implements HTMLElementStyleMethods<MdChipSetElement, ChipsElementBuilder>,
        HTMLElementVisibilityMethods<MdChipSetElement, ChipsElementBuilder>,
        HasAriaLabel<MdChipSetElement, ChipsElementBuilder> {
    public static ChipsElementBuilder chips() {
        return new ChipsElementBuilder(htmlContainer("md-chip-set", MdChipSetElement.class));
    }
    protected final HTMLContainerBuilder<MdChipSetElement> that;
    private ChipsElementBuilder(HTMLContainerBuilder<MdChipSetElement> that) {
        this.that = that;
    }
    @Override
    public MdChipSetElement element() {
        return that.element();
    }
    public AssistChipElementBuilder assist() {
        return new AssistChipElementBuilder(this);
    }
    public FilterChipElementBuilder filter() {
        return new FilterChipElementBuilder(this);
    }
    public InputChipElementBuilder input() {
        return new InputChipElementBuilder(this);
    }
    public SuggestionChipElementBuilder suggestion() {
        return new SuggestionChipElementBuilder(this);
    }
    public ChipsElementBuilder ariaLabelledBy(String ariaLabelledBy) {
        element().setAttribute("aria-labelledby", ariaLabelledBy);
        return that();
    }
    @Override
    public ChipsElementBuilder that() {
        return this;
    }

    interface ChipElementBuilder<E extends MdChipElement, SELF extends ChipElementBuilder<E, SELF>> extends
            HTMLElementStyleMethods<E, SELF>, HTMLElementVisibilityMethods<E, SELF>,
            ElementAttributeMethods<E, SELF>, ElementClassListMethods<E, SELF>, ElementEventMethods<E, SELF>, ElementTextMethods<E, SELF>,
            HasAriaLabel<E, SELF>, HasIconSlot<E, SELF>, Disableable<E, SELF> {
        default SELF label(String label) {
            element().label = label;
            return that();
        }
        @Override default SELF disabled(boolean disabled) {
            this.element().disabled = disabled;
            return that();
        }
        default SELF alwaysFocusable(boolean alwaysFocusable) {
            this.element().alwaysFocusable = alwaysFocusable;
            return that();
        }
        ChipsElementBuilder end();
        @Override E element();
    }
    public final static class AssistChipElementBuilder implements ChipElementBuilder<MdAssistChipElement, AssistChipElementBuilder>, Elevatable<MdAssistChipElement, AssistChipElementBuilder>, Linkable<MdAssistChipElement, AssistChipElementBuilder> {
        private final HTMLContainerBuilder<MdAssistChipElement> that;
        private final ChipsElementBuilder parent;
        private AssistChipElementBuilder(ChipsElementBuilder parent) {
    		that = htmlContainer("md-assist-chip", MdAssistChipElement.class);
            this.parent = parent;
            parent.that.add(this);
    	}
        @Override public ChipsElementBuilder end() {
            return parent;
        }
        @Override public MdAssistChipElement element() {
            return that.element();
        }
        @Override public AssistChipElementBuilder that() {
            return this;
        }
        @Override public AssistChipElementBuilder elevated(boolean elevated) {
            element().elevated = elevated;
            return that();
        }
    }
    public final static class FilterChipElementBuilder implements ChipElementBuilder<MdFilterChipElement, FilterChipElementBuilder>, Elevatable<MdFilterChipElement, FilterChipElementBuilder>, Selectable<MdFilterChipElement, FilterChipElementBuilder>, HasRemoveEvent<MdFilterChipElement, FilterChipElementBuilder> {
        private final HTMLContainerBuilder<MdFilterChipElement> that;
        private final ChipsElementBuilder parent;
        private FilterChipElementBuilder(ChipsElementBuilder parent) {
            that = htmlContainer("md-filter-chip", MdFilterChipElement.class);
            this.parent = parent;
            parent.that.add(this);
        }
        @Override public ChipsElementBuilder end() {
            return parent;
        }
        @Override public MdFilterChipElement element() {
            return that.element();
        }
        @Override public FilterChipElementBuilder that() {
            return this;
        }
        @Override public FilterChipElementBuilder elevated(boolean elevated) {
            element().elevated = elevated;
            return that();
        }
        @Override public FilterChipElementBuilder select(boolean selected) {
            element().selected = selected;
            return that();
        }
        @Override public boolean isSelected() {
            return element().selected;
        }
        public FilterChipElementBuilder removable(boolean removable) {
            element().removable = removable;
            return that();
        }
        public FilterChipElementBuilder removable() {
            return removable(true);
        }
        public FilterChipElementBuilder ariaLabelRemove(String ariaLabelRemove) {
            element().ariaLabelRemove = ariaLabelRemove;
            return that();
        }
    }
    public final static class InputChipElementBuilder implements ChipElementBuilder<MdInputChipElement, InputChipElementBuilder>, Selectable<MdInputChipElement, InputChipElementBuilder>, Linkable<MdInputChipElement, InputChipElementBuilder>, HasRemoveEvent<MdInputChipElement, InputChipElementBuilder> {
        private final HTMLContainerBuilder<MdInputChipElement> that;
        private final ChipsElementBuilder parent;
        private InputChipElementBuilder(ChipsElementBuilder parent) {
            that = htmlContainer("md-input-chip", MdInputChipElement.class);
            this.parent = parent;
            parent.that.add(this);
        }
        public InputChipElementBuilder avatar() {
            element().avatar = true;
            return that();
        }
        @Override public ChipsElementBuilder end() {
            return parent;
        }
        @Override public MdInputChipElement element() {
            return that.element();
        }
        @Override public InputChipElementBuilder that() {
            return this;
        }
        @Override public InputChipElementBuilder select(boolean selected) {
            element().selected = selected;
            return that();
        }
        @Override public boolean isSelected() {
            return element().selected;
        }
        public InputChipElementBuilder removeOnly(boolean removeOnly) {
            element().removeOnly = removeOnly;
            return that();
        }
        public InputChipElementBuilder removeOnly() {
            return removeOnly(true);
        }
        public InputChipElementBuilder ariaLabelRemove(String ariaLabelRemove) {
            element().ariaLabelRemove = ariaLabelRemove;
            return that();
        }
    }
    public final static class SuggestionChipElementBuilder implements ChipElementBuilder<MdSuggestionChipElement, SuggestionChipElementBuilder>, Elevatable<MdSuggestionChipElement, SuggestionChipElementBuilder>, Linkable<MdSuggestionChipElement, SuggestionChipElementBuilder> {
        private final HTMLContainerBuilder<MdSuggestionChipElement> that;
        private final ChipsElementBuilder parent;
        private SuggestionChipElementBuilder(ChipsElementBuilder parent) {
            that = htmlContainer("md-suggestion-chip", MdSuggestionChipElement.class);
            this.parent = parent;
            parent.that.add(this);
        }
        @Override public ChipsElementBuilder end() {
            return parent;
        }
        @Override public MdSuggestionChipElement element() {
            return that.element();
        }
        @Override public SuggestionChipElementBuilder that() {
            return this;
        }
        @Override public SuggestionChipElementBuilder elevated(boolean elevated) {
            element().elevated = elevated;
            return that();
        }
    }
}
