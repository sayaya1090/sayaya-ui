package dev.sayaya.ui.elements;

import dev.sayaya.ui.dom.MdMenuElement;
import dev.sayaya.ui.dom.MdMenuItemElement;
import dev.sayaya.ui.dom.MdSubMenuElement;
import dev.sayaya.ui.elements.interfaces.HasEndSlot;
import dev.sayaya.ui.elements.interfaces.HasHeadlineSlot;
import dev.sayaya.ui.elements.interfaces.HasStartSlot;
import dev.sayaya.ui.elements.interfaces.HasSupportingTextSlot;
import elemental2.dom.Event;
import elemental2.dom.HTMLElement;
import org.jboss.elemento.*;

import static org.jboss.elemento.Elements.htmlContainer;

public abstract class MenuElementBuilder<E extends HTMLElement, SELF extends MenuElementBuilder<E, SELF>> implements ElementAttributeMethods<E, SELF>, ElementClassListMethods<E, SELF>, ElementContainerMethods<E, SELF>,
        ElementEventMethods<E, SELF>, HTMLElementStyleMethods<E, SELF>, HTMLElementVisibilityMethods<E, SELF>, HasMenuItemElementBuilder<E, SELF> {
    public static TopMenuElementBuilder menu() {
        return new TopMenuElementBuilder();
    }
    public SubMenuGroupBuilder<SELF> sub() {
        var subMenu = new SubMenuGroupBuilder<>(that());
        that().add(subMenu);
        return subMenu;
    }
    public static class TopMenuElementBuilder extends MenuElementBuilder<MdMenuElement, TopMenuElementBuilder> {
        private final HTMLContainerBuilder<MdMenuElement> that = htmlContainer("md-menu", MdMenuElement.class);
        public TopMenuElementBuilder anchor(String id) {
            that.element().anchor = id;
            return that();
        }
        public TopMenuElementBuilder anchorElement(IsElement<? extends HTMLElement> elem) {
            return anchorElement(elem.element());
        }
        public TopMenuElementBuilder anchorElement(IsElement<? extends HTMLElement> elem, String event) {
            return anchorElement(elem.element(), event);
        }
        public TopMenuElementBuilder anchorElement(HTMLElement anchor) {
            return anchorElement(anchor, "click");
        }
        public TopMenuElementBuilder anchorElement(HTMLElement anchor, String event) {
            that.element().anchorElement = anchor;
            anchor.addEventListener(event, evt->toggle());
            return that();
        }
        public TopMenuElementBuilder positioning(Position position) {
            if(position==null)  that.element().positioning = Position.Absolute.value;
            else that.element().positioning = position.value;
            return that();
        }
        public void open() {
            open(true);
        }
        public void open(boolean opened) {
            element().open = opened;
        }
        public void close() {
            open(false);
        }
        public TopMenuElementBuilder offset(int x, int y) {
            that.element().xOffset = x;
            that.element().yOffset = y;
            return that();
        }
        public void toggle() {
            element().open = !element().open;
        }
        public TopMenuElementBuilder overflow() {
            return overflow(true);
        }
        public TopMenuElementBuilder overflow(boolean hasOverflow) {
            that.element().hasOverflow = hasOverflow;
            return that();
        }
        public TopMenuElementBuilder quick() {
            return quick(true);
        }
        public TopMenuElementBuilder quick(boolean quick) {
            that.element().quick = quick;
            return that();
        }
        public TopMenuElementBuilder stayOpenOnOutsideClick() {
            return stayOpenOnOutsideClick(true);
        }
        public TopMenuElementBuilder stayOpenOnOutsideClick(boolean stayOpen) {
            that.element().stayOpenOnOutsideClick = stayOpen;
            return that();
        }
        public TopMenuElementBuilder stayOpenOnFocusout() {
            return stayOpenOnFocusout(true);
        }
        public TopMenuElementBuilder stayOpenOnFocusout(boolean stayOpen) {
            that.element().stayOpenOnFocusout = stayOpen;
            return that();
        }
        public TopMenuElementBuilder skipRestoreFocus() {
            return skipRestoreFocus(true);
        }
        public TopMenuElementBuilder skipRestoreFocus(boolean skip) {
            that.element().skipRestoreFocus = skip;
            return that();
        }
        public TopMenuElementBuilder defaultFocus(String defaultFocus) {
            that.element().defaultFocus = defaultFocus;
            return that();
        }
        public TopMenuElementBuilder typeaheadDelay(double delay) {
            that.element().typeaheadDelay = delay;
            return that();
        }
        public TopMenuElementBuilder anchorCorner(String corner) {
            that.element().anchorCorner = corner;
            return that();
        }
        public TopMenuElementBuilder menuCorner(String corner) {
            that.element().menuCorner = corner;
            return that();
        }
        @Override public MdMenuElement element() {return that.element();}
        @Override public TopMenuElementBuilder that() {return this;}
    }
    public enum Position {
        Absolute("absolute"), Fixed("fixed"), Popover("popover"), Document("document");
        private final String value;
        Position(String value) {
            this.value = value;
        }
        public String toString() {
            return value;
        }
    }
    public static class SubMenuGroupBuilder<P extends MenuElementBuilder<?, P>> implements HasMenuItemElementBuilder<MdSubMenuElement, SubMenuGroupBuilder<P>> {
        private final HTMLContainerBuilder<MdSubMenuElement> that = htmlContainer("md-sub-menu", MdSubMenuElement.class);
        private final P parent;
        private SubMenuGroupBuilder(P parent) {
            this.parent = parent;
        }
        public MenuItemElementBuilder<SubMenuGroupBuilder<P>> item() {
            var item = new MenuItemElementBuilder<>(that());
            item.element().setAttribute("slot", "item");
            that().add(item);
            return item;
        }
        public SubMenuElementBuilder<P> menu() {
            var subMenu = new SubMenuElementBuilder<>(this);
            subMenu.element().setAttribute("slot", "menu");
            that().add(subMenu);
            return subMenu;
        }
        public SubMenuGroupBuilder<P> menuCorner(String menuCorner) {
            element().setAttribute("menu-corner", menuCorner);
            return that();
        }
        public SubMenuGroupBuilder<P> anchorCorner(String anchorCorner) {
            element().setAttribute("anchor-corner", anchorCorner);
            return that();
        }
        public SubMenuGroupBuilder<P> hoverOpenDelay(double delay) {
            element().hoverOpenDelay = delay;
            return that();
        }
        public SubMenuGroupBuilder<P> hoverCloseDelay(double delay) {
            element().hoverCloseDelay = delay;
            return that();
        }
        public P done() { return parent; }
        @Override public MdSubMenuElement element() {return that.element();}
        @Override public SubMenuGroupBuilder<P> that() {return this;}
    }
    public static class SubMenuElementBuilder<P extends MenuElementBuilder<?, P>> extends MenuElementBuilder<MdMenuElement, SubMenuElementBuilder<P>> {
        private final HTMLContainerBuilder<MdMenuElement> that = htmlContainer("md-menu", MdMenuElement.class);
        private final SubMenuGroupBuilder<P> parent;
        private SubMenuElementBuilder(SubMenuGroupBuilder<P> parent) {
            this.parent = parent;
        }
        public SubMenuGroupBuilder<P> done() { return parent; }
        @Override public MdMenuElement element() {return that.element();}
        @Override public SubMenuElementBuilder<P> that() {return this;}
    }
    public static class MenuItemElementBuilder<P extends HasMenuItemElementBuilder<?, P>> implements HasHeadlineSlot<MdMenuItemElement, MenuItemElementBuilder<P>>,
            HasStartSlot<MdMenuItemElement, MenuItemElementBuilder<P>>, HasEndSlot<MdMenuItemElement, MenuItemElementBuilder<P>>,
            HasSupportingTextSlot<MdMenuItemElement, MenuItemElementBuilder<P>>,
            dev.sayaya.ui.elements.interfaces.Disableable<MdMenuItemElement, MenuItemElementBuilder<P>>,
            dev.sayaya.ui.elements.interfaces.Selectable<MdMenuItemElement, MenuItemElementBuilder<P>>,
            dev.sayaya.ui.elements.interfaces.Linkable<MdMenuItemElement, MenuItemElementBuilder<P>>,
            dev.sayaya.ui.elements.interfaces.Typeable<MdMenuItemElement, MenuItemElementBuilder<P>> {
        private final HTMLContainerBuilder<MdMenuItemElement> that = htmlContainer("md-menu-item", MdMenuItemElement.class);
        private final P parent;
        MenuItemElementBuilder(P parent) {
            this.parent = parent;
        }
        public <V extends Event> MenuItemElementBuilder<P> on(EventType<V, ?> type, EventCallbackFn<V> callback) {
            EventType.bind(this.element(), type, callback);
            return this.that();
        }

        @Override
        public MenuItemElementBuilder<P> disabled(boolean disabled) {
            element().disabled = disabled;
            return that();
        }

        @Override
        public MenuItemElementBuilder<P> select(boolean selected) {
            element().selected = selected;
            return that();
        }

        @Override
        public boolean isSelected() {
            return element().selected;
        }

        public MenuItemElementBuilder<P> keepOpen() {
            return keepOpen(true);
        }

        public MenuItemElementBuilder<P> keepOpen(boolean keepOpen) {
            element().keepOpen = keepOpen;
            return that();
        }

        public MenuItemElementBuilder<P> typeaheadText(String text) {
            element().typeaheadText = text;
            return that();
        }

        public P done() { return parent; }
        @Override public MdMenuItemElement element() {return that.element();}
        @Override public MenuItemElementBuilder<P> that() {return this;}
    }
}
