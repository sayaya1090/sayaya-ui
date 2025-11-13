package dev.sayaya.ui.elements;

import dev.sayaya.ui.dom.MdListElement;
import dev.sayaya.ui.dom.MdListItemElement;
import dev.sayaya.ui.elements.interfaces.*;
import elemental2.dom.HTMLElement;
import org.jboss.elemento.*;

import static org.jboss.elemento.Elements.div;
import static org.jboss.elemento.Elements.htmlContainer;

public class ListElementBuilder implements HTMLElementStyleMethods<MdListElement, ListElementBuilder>, HTMLElementVisibilityMethods<MdListElement, ListElementBuilder>, ElementContainerMethods<MdListElement, ListElementBuilder> {
    public static ListElementBuilder list() {
        return new ListElementBuilder(htmlContainer("md-list", MdListElement.class));
    }
    protected final HTMLContainerBuilder<MdListElement> that;
    private ListElementBuilder(HTMLContainerBuilder<MdListElement> that) {
        this.that = that;
    }
    @Override
    public MdListElement element() {
        return that.element();
    }
    public ListItemElementBuilder item() {
        return new ListItemElementBuilder(this);
    }
    @Override
    public ListElementBuilder that() {
        return this;
    }
    public ListElementBuilder divider() {
        this.add(DividerElementBuilder.divider());
        return this;
    }
    public ListElementBuilder dividerInset() {
        this.add(DividerElementBuilder.dividerInset());
        return this;
    }
    public ListElementBuilder dividerInsetStart() {
        this.add(DividerElementBuilder.dividerInsetStart());
        return this;
    }

    public final static class ListItemElementBuilder implements
            HTMLElementStyleMethods<MdListItemElement, ListItemElementBuilder>, HTMLElementVisibilityMethods<MdListItemElement, ListItemElementBuilder>,
            ElementAttributeMethods<MdListItemElement, ListItemElementBuilder>, ElementClassListMethods<MdListItemElement, ListItemElementBuilder>,
            ElementEventMethods<MdListItemElement, ListItemElementBuilder>, ElementTextMethods<MdListItemElement, ListItemElementBuilder>, HasIconSlot<MdListItemElement, ListItemElementBuilder>,
            HasHeadlineSlot<MdListItemElement, ListItemElementBuilder>, HasSupportingTextSlot<MdListItemElement, ListItemElementBuilder>, HasStartSlot<MdListItemElement, ListItemElementBuilder>, HasEndSlot<MdListItemElement, ListItemElementBuilder>, Typeable<MdListItemElement, ListItemElementBuilder>, Disableable<MdListItemElement, ListItemElementBuilder>, Linkable<MdListItemElement, ListItemElementBuilder>, Clickable<MdListItemElement, ListItemElementBuilder> {
        private final HTMLContainerBuilder<MdListItemElement> that;
        private final ListElementBuilder parent;
        private ListItemElementBuilder(ListElementBuilder parent) {
    		that = htmlContainer("md-list-item", MdListItemElement.class);
            this.parent = parent;
            parent.that.add(this);
    	}
        @Override
        public ListItemElementBuilder disabled(boolean disabled) {
            element().disabled = disabled;
            return that();
        }
        public ListItemElementBuilder tabindex(int tabindex) {
            element().tabindex = tabindex;
            return that();
        }
        public ListElementBuilder done() {
            return parent;
        }
        public ListItemElementBuilder trailingSupportingText(String supportingText) {
            return trailingSupportingText(div().add(supportingText));
        }
        public ListItemElementBuilder trailingSupportingText(IsElement<? extends HTMLElement> element) {
            return trailingSupportingText(element.element());
        }
        public ListItemElementBuilder trailingSupportingText(HTMLElement element) {
            element.setAttribute("slot", "trailing-supporting-text");
            add(element);
            return that();
        }
        @Override public MdListItemElement element() {
            return that.element();
        }
        @Override public ListItemElementBuilder that() {
            return this;
        }
    }
}
