package dev.sayaya.ui.elements;

import dev.sayaya.ui.dom.MdGbElement.*;
import dev.sayaya.ui.elements.interfaces.*;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;
import org.jboss.elemento.*;

import static org.jboss.elemento.Elements.htmlContainer;

/**
 * Material Web 2.5.0 labs/gb(Expressive) 컴포넌트(md-gb-*) 빌더.
 * <pre>{@code
 * import static dev.sayaya.ui.elements.GbElementBuilder.gb;
 *
 * var save = gb().button().color(GbElementBuilder.ButtonColor.Tonal).size(GbElementBuilder.Size.LG).text("저장").element();
 * }</pre>
 * labs 컴포넌트이므로 Material Web 버전이 바뀌면 API가 달라질 수 있다.
 */
public final class GbElementBuilder {
    public static GbElementBuilder gb() {
        return new GbElementBuilder();
    }
    private GbElementBuilder() {}

    public GbBadgeElementBuilder badge() { return new GbBadgeElementBuilder(); }
    public GbButtonElementBuilder button() { return new GbButtonElementBuilder(); }
    public GbCardElementBuilder card() { return new GbCardElementBuilder(); }
    public GbCheckboxElementBuilder checkbox() { return new GbCheckboxElementBuilder(); }
    public GbDividerElementBuilder divider() { return new GbDividerElementBuilder(); }
    public GbFabElementBuilder fab() { return new GbFabElementBuilder(); }
    public GbIconElementBuilder icon() { return new GbIconElementBuilder(); }
    public GbIconElementBuilder icon(String name) { return new GbIconElementBuilder().text(name); }
    public GbIconButtonElementBuilder iconButton() { return new GbIconButtonElementBuilder(); }
    public GbListElementBuilder list() { return new GbListElementBuilder(); }
    public GbListItemElementBuilder listItem() { return new GbListItemElementBuilder(); }
    public GbMenuElementBuilder menu() { return new GbMenuElementBuilder(); }
    public GbMenuGroupElementBuilder menuGroup() { return new GbMenuGroupElementBuilder(); }
    public GbMenuItemElementBuilder menuItem() { return new GbMenuItemElementBuilder(); }
    public GbRadioElementBuilder radio() { return new GbRadioElementBuilder(); }
    public GbSplitButtonElementBuilder splitButton() { return new GbSplitButtonElementBuilder(); }
    public GbSwitchElementBuilder sw() { return new GbSwitchElementBuilder(); }

    // ---- .d.ts 유니온 타입 ----
    public enum ButtonColor { Filled("filled"), Elevated("elevated"), Tonal("tonal"), Outlined("outlined"), Text("text");
        public final String value; ButtonColor(String v) { value = v; } }
    public enum Size { XS("xs"), SM("sm"), MD("md"), LG("lg"), XL("xl");
        public final String value; Size(String v) { value = v; } }
    public enum CardColor { Elevated("elevated"), Filled("filled"), Outlined("outlined");
        public final String value; CardColor(String v) { value = v; } }
    public enum FabColor { Primary("primary"), PrimaryContainer("primary-container"), Secondary("secondary"),
        SecondaryContainer("secondary-container"), Tertiary("tertiary"), TertiaryContainer("tertiary-container");
        public final String value; FabColor(String v) { value = v; } }
    public enum FabSize { Default("default"), MD("md"), LG("lg");
        public final String value; FabSize(String v) { value = v; } }
    public enum IconButtonColor { Filled("filled"), Tonal("tonal"), Outlined("outlined"), Standard("standard");
        public final String value; IconButtonColor(String v) { value = v; } }
    public enum IconButtonWidth { Default(""), Narrow("narrow"), Wide("wide");
        public final String value; IconButtonWidth(String v) { value = v; } }
    public enum MenuColor { Standard("standard"), Vibrant("vibrant");
        public final String value; MenuColor(String v) { value = v; } }
    public enum MenuCheckable { Single("single"), Multiple("multiple");
        public final String value; MenuCheckable(String v) { value = v; } }
    public enum SplitButtonColor { Filled("filled"), Elevated("elevated"), Tonal("tonal"), Outlined("outlined");
        public final String value; SplitButtonColor(String v) { value = v; } }

    /** 모든 md-gb-* 빌더의 공통 기반 */
    public abstract static class GbBuilder<E extends HTMLElement, SELF extends GbBuilder<E, SELF>> implements
            HTMLElementStyleMethods<E, SELF>, HTMLElementVisibilityMethods<E, SELF>, ElementAttributeMethods<E, SELF>,
            ElementClassListMethods<E, SELF>, ElementContainerMethods<E, SELF>, ElementEventMethods<E, SELF>,
            ElementTextMethods<E, SELF>, ElementIdMethods<E, SELF>, ElementConsumerMethods<E, SELF>, HasAriaLabel<E, SELF> {
        private final HTMLContainerBuilder<E> that;
        GbBuilder(String tag, Class<E> type) {
            that = htmlContainer(tag, type);
        }
        @Override public E element() {
            return that.element();
        }
        protected SELF slot(String slot, Element child) {
            child.setAttribute("slot", slot);
            element().appendChild(child);
            return that();
        }
        protected SELF slot(String slot, IsElement<? extends Element> child) {
            return slot(slot, child.element());
        }
    }

    public static final class GbBadgeElementBuilder extends GbBuilder<MdGbBadgeElement, GbBadgeElementBuilder> {
        GbBadgeElementBuilder() { super("md-gb-badge", MdGbBadgeElement.class); }
        @Override public GbBadgeElementBuilder that() { return this; }
        public GbBadgeElementBuilder value(String value) { return text(value); }
        public GbBadgeElementBuilder value(int value) { return value(String.valueOf(value)); }
    }

    public static final class GbButtonElementBuilder extends GbBuilder<MdGbButtonElement, GbButtonElementBuilder> implements
            Disableable<MdGbButtonElement, GbButtonElementBuilder>, Selectable<MdGbButtonElement, GbButtonElementBuilder>,
            Linkable<MdGbButtonElement, GbButtonElementBuilder>, FormAssociable<MdGbButtonElement, GbButtonElementBuilder>,
            Clickable<MdGbButtonElement, GbButtonElementBuilder> {
        GbButtonElementBuilder() { super("md-gb-button", MdGbButtonElement.class); }
        @Override public GbButtonElementBuilder that() { return this; }
        public GbButtonElementBuilder color(ButtonColor color) { element().color = color.value; return this; }
        public GbButtonElementBuilder size(Size size) { element().size = size.value; return this; }
        public GbButtonElementBuilder square(boolean square) { element().square = square; return this; }
        public GbButtonElementBuilder type(String type) { element().type = type; return this; }
        public GbButtonElementBuilder softDisabled(boolean softDisabled) { element().softDisabled = softDisabled; return this; }
        public GbButtonElementBuilder download(String filename) { element().download = filename; return this; }
        public GbButtonElementBuilder icon(String icon) { return add(new GbIconElementBuilder().text(icon)); }
        /** 2.5.0의 container 슬롯 */
        public GbButtonElementBuilder container(Element container) { return slot("container", container); }
        public GbButtonElementBuilder container(IsElement<? extends Element> container) { return slot("container", container); }
        @Override public GbButtonElementBuilder disabled(boolean disabled) { element().disabled = disabled; return this; }
        @Override public GbButtonElementBuilder select(boolean selected) { element().selected = selected; return this; }
        @Override public boolean isSelected() { return element().selected; }
    }

    public static final class GbCardElementBuilder extends GbBuilder<MdGbCardElement, GbCardElementBuilder> implements
            Disableable<MdGbCardElement, GbCardElementBuilder>, Clickable<MdGbCardElement, GbCardElementBuilder> {
        GbCardElementBuilder() { super("md-gb-card", MdGbCardElement.class); }
        @Override public GbCardElementBuilder that() { return this; }
        public GbCardElementBuilder color(CardColor color) { element().color = color.value; return this; }
        public GbCardElementBuilder interactive(boolean interactive) { element().interactive = interactive; return this; }
        /** 2.5.0의 container 슬롯 */
        public GbCardElementBuilder container(Element container) { return slot("container", container); }
        public GbCardElementBuilder container(IsElement<? extends Element> container) { return slot("container", container); }
        @Override public GbCardElementBuilder disabled(boolean disabled) { element().disabled = disabled; return this; }
    }

    public static final class GbCheckboxElementBuilder extends GbBuilder<MdGbCheckboxElement, GbCheckboxElementBuilder> implements
            Disableable<MdGbCheckboxElement, GbCheckboxElementBuilder>, Selectable<MdGbCheckboxElement, GbCheckboxElementBuilder>,
            Requireable<MdGbCheckboxElement, GbCheckboxElementBuilder>, FormAssociable<MdGbCheckboxElement, GbCheckboxElementBuilder>,
            HasInputEvent<MdGbCheckboxElement, GbCheckboxElementBuilder>, HasChangeEvent<MdGbCheckboxElement, GbCheckboxElementBuilder> {
        GbCheckboxElementBuilder() { super("md-gb-checkbox", MdGbCheckboxElement.class); }
        @Override public GbCheckboxElementBuilder that() { return this; }
        public GbCheckboxElementBuilder indeterminate(boolean indeterminate) { element().indeterminate = indeterminate; return this; }
        public GbCheckboxElementBuilder error(boolean error) { element().error = error; return this; }
        @Override public GbCheckboxElementBuilder disabled(boolean disabled) { element().disabled = disabled; return this; }
        @Override public GbCheckboxElementBuilder select(boolean selected) { element().checked = selected; return this; }
        @Override public boolean isSelected() { return element().checked; }
        @Override public GbCheckboxElementBuilder required(boolean required) { element().required = required; return this; }
    }

    public static final class GbDividerElementBuilder extends GbBuilder<MdGbDividerElement, GbDividerElementBuilder> {
        GbDividerElementBuilder() { super("md-gb-divider", MdGbDividerElement.class); }
        @Override public GbDividerElementBuilder that() { return this; }
        public GbDividerElementBuilder vertical(boolean vertical) { element().vertical = vertical; return this; }
    }

    public static final class GbFabElementBuilder extends GbBuilder<MdGbFabElement, GbFabElementBuilder> implements
            Clickable<MdGbFabElement, GbFabElementBuilder> {
        GbFabElementBuilder() { super("md-gb-fab", MdGbFabElement.class); }
        @Override public GbFabElementBuilder that() { return this; }
        public GbFabElementBuilder color(FabColor color) { element().color = color.value; return this; }
        public GbFabElementBuilder size(FabSize size) { element().size = size.value; return this; }
        public GbFabElementBuilder icon(String icon) { return add(new GbIconElementBuilder().text(icon)); }
    }

    public static final class GbIconElementBuilder extends GbBuilder<MdGbIconElement, GbIconElementBuilder> {
        GbIconElementBuilder() { super("md-gb-icon", MdGbIconElement.class); }
        @Override public GbIconElementBuilder that() { return this; }
    }

    public static final class GbIconButtonElementBuilder extends GbBuilder<MdGbIconButtonElement, GbIconButtonElementBuilder> implements
            Disableable<MdGbIconButtonElement, GbIconButtonElementBuilder>, Selectable<MdGbIconButtonElement, GbIconButtonElementBuilder>,
            Linkable<MdGbIconButtonElement, GbIconButtonElementBuilder>, FormAssociable<MdGbIconButtonElement, GbIconButtonElementBuilder>,
            Clickable<MdGbIconButtonElement, GbIconButtonElementBuilder> {
        GbIconButtonElementBuilder() { super("md-gb-icon-button", MdGbIconButtonElement.class); }
        @Override public GbIconButtonElementBuilder that() { return this; }
        public GbIconButtonElementBuilder color(IconButtonColor color) { element().color = color.value; return this; }
        public GbIconButtonElementBuilder size(Size size) { element().size = size.value; return this; }
        public GbIconButtonElementBuilder width(IconButtonWidth width) { element().width = width.value; return this; }
        public GbIconButtonElementBuilder square(boolean square) { element().square = square; return this; }
        public GbIconButtonElementBuilder type(String type) { element().type = type; return this; }
        public GbIconButtonElementBuilder softDisabled(boolean softDisabled) { element().softDisabled = softDisabled; return this; }
        public GbIconButtonElementBuilder download(String filename) { element().download = filename; return this; }
        public GbIconButtonElementBuilder icon(String icon) { return add(new GbIconElementBuilder().text(icon)); }
        @Override public GbIconButtonElementBuilder disabled(boolean disabled) { element().disabled = disabled; return this; }
        @Override public GbIconButtonElementBuilder select(boolean selected) { element().selected = selected; return this; }
        @Override public boolean isSelected() { return element().selected; }
    }

    public static final class GbListElementBuilder extends GbBuilder<MdGbListElement, GbListElementBuilder> {
        GbListElementBuilder() { super("md-gb-list", MdGbListElement.class); }
        @Override public GbListElementBuilder that() { return this; }
        public GbListElementBuilder segmented(boolean segmented) { element().segmented = segmented; return this; }
        public GbListElementBuilder item(GbListItemElementBuilder item) { return add(item); }
    }

    public static final class GbListItemElementBuilder extends GbBuilder<MdGbListItemElement, GbListItemElementBuilder> implements
            Disableable<MdGbListItemElement, GbListItemElementBuilder>, Selectable<MdGbListItemElement, GbListItemElementBuilder>,
            Clickable<MdGbListItemElement, GbListItemElementBuilder> {
        GbListItemElementBuilder() { super("md-gb-list-item", MdGbListItemElement.class); }
        @Override public GbListItemElementBuilder that() { return this; }
        public GbListItemElementBuilder nonInteractive(boolean nonInteractive) { element().nonInteractive = nonInteractive; return this; }
        public GbListItemElementBuilder headline(String headline) { return text(headline); }
        public GbListItemElementBuilder avatar(Element e) { return slot("avatar", e); }
        public GbListItemElementBuilder avatar(IsElement<? extends Element> e) { return slot("avatar", e); }
        public GbListItemElementBuilder leading(Element e) { return slot("leading", e); }
        public GbListItemElementBuilder leading(IsElement<? extends Element> e) { return slot("leading", e); }
        public GbListItemElementBuilder overline(String text) { return slot("overline", Elements.span().text(text)); }
        public GbListItemElementBuilder supportingText(String text) { return slot("supporting-text", Elements.span().text(text)); }
        public GbListItemElementBuilder trailing(Element e) { return slot("trailing", e); }
        public GbListItemElementBuilder trailing(IsElement<? extends Element> e) { return slot("trailing", e); }
        @Override public GbListItemElementBuilder disabled(boolean disabled) { element().disabled = disabled; return this; }
        @Override public GbListItemElementBuilder select(boolean selected) { element().checked = selected; return this; }
        @Override public boolean isSelected() { return element().checked; }
    }

    public static final class GbMenuElementBuilder extends GbBuilder<MdGbMenuElement, GbMenuElementBuilder> {
        GbMenuElementBuilder() { super("md-gb-menu", MdGbMenuElement.class); }
        @Override public GbMenuElementBuilder that() { return this; }
        public GbMenuElementBuilder color(MenuColor color) { element().color = color.value; return this; }
        public GbMenuElementBuilder item(GbMenuItemElementBuilder item) { return add(item); }
        public GbMenuElementBuilder group(GbMenuGroupElementBuilder group) { return add(group); }
    }

    public static final class GbMenuGroupElementBuilder extends GbBuilder<MdGbMenuGroupElement, GbMenuGroupElementBuilder> {
        GbMenuGroupElementBuilder() { super("md-gb-menu-group", MdGbMenuGroupElement.class); }
        @Override public GbMenuGroupElementBuilder that() { return this; }
        public GbMenuGroupElementBuilder checkable(MenuCheckable checkable) { element().checkable = checkable.value; return this; }
        public GbMenuGroupElementBuilder item(GbMenuItemElementBuilder item) { return add(item); }
    }

    public static final class GbMenuItemElementBuilder extends GbBuilder<MdGbMenuItemElement, GbMenuItemElementBuilder> implements
            Disableable<MdGbMenuItemElement, GbMenuItemElementBuilder>, Selectable<MdGbMenuItemElement, GbMenuItemElementBuilder>,
            Clickable<MdGbMenuItemElement, GbMenuItemElementBuilder> {
        GbMenuItemElementBuilder() { super("md-gb-menu-item", MdGbMenuItemElement.class); }
        @Override public GbMenuItemElementBuilder that() { return this; }
        public GbMenuItemElementBuilder headline(String headline) { return text(headline); }
        public GbMenuItemElementBuilder leading(Element e) { return slot("leading", e); }
        public GbMenuItemElementBuilder leading(IsElement<? extends Element> e) { return slot("leading", e); }
        public GbMenuItemElementBuilder supportingText(String text) { return slot("supporting-text", Elements.span().text(text)); }
        public GbMenuItemElementBuilder trailing(Element e) { return slot("trailing", e); }
        public GbMenuItemElementBuilder trailing(IsElement<? extends Element> e) { return slot("trailing", e); }
        @Override public GbMenuItemElementBuilder disabled(boolean disabled) { element().disabled = disabled; return this; }
        @Override public GbMenuItemElementBuilder select(boolean selected) { element().checked = selected; return this; }
        @Override public boolean isSelected() { return element().checked; }
    }

    public static final class GbRadioElementBuilder extends GbBuilder<MdGbRadioElement, GbRadioElementBuilder> implements
            Disableable<MdGbRadioElement, GbRadioElementBuilder>, Selectable<MdGbRadioElement, GbRadioElementBuilder>,
            Requireable<MdGbRadioElement, GbRadioElementBuilder>, FormAssociable<MdGbRadioElement, GbRadioElementBuilder>,
            HasInputEvent<MdGbRadioElement, GbRadioElementBuilder>, HasChangeEvent<MdGbRadioElement, GbRadioElementBuilder> {
        GbRadioElementBuilder() { super("md-gb-radio", MdGbRadioElement.class); }
        @Override public GbRadioElementBuilder that() { return this; }
        @Override public GbRadioElementBuilder disabled(boolean disabled) { element().disabled = disabled; return this; }
        @Override public GbRadioElementBuilder select(boolean selected) { element().checked = selected; return this; }
        @Override public boolean isSelected() { return element().checked; }
        @Override public GbRadioElementBuilder required(boolean required) { element().required = required; return this; }
    }

    /**
     * Split Button. leading/trailing 슬롯에는 {@code <button>} 요소가 들어가야 하며,
     * 기본 슬롯은 trailing 버튼의 팝오버 메뉴용이다.
     */
    public static final class GbSplitButtonElementBuilder extends GbBuilder<MdGbSplitButtonElement, GbSplitButtonElementBuilder> implements
            Selectable<MdGbSplitButtonElement, GbSplitButtonElementBuilder> {
        GbSplitButtonElementBuilder() { super("md-gb-split-button", MdGbSplitButtonElement.class); }
        @Override public GbSplitButtonElementBuilder that() { return this; }
        public GbSplitButtonElementBuilder color(SplitButtonColor color) { element().color = color.value; return this; }
        public GbSplitButtonElementBuilder size(Size size) { element().size = size.value; return this; }
        public GbSplitButtonElementBuilder leading(Element button) { return slot("leading", button); }
        public GbSplitButtonElementBuilder leading(IsElement<? extends Element> button) { return slot("leading", button); }
        public GbSplitButtonElementBuilder trailing(Element button) { return slot("trailing", button); }
        public GbSplitButtonElementBuilder trailing(IsElement<? extends Element> button) { return slot("trailing", button); }
        public GbSplitButtonElementBuilder menu(GbMenuElementBuilder menu) { return add(menu); }
        @Override public GbSplitButtonElementBuilder select(boolean selected) { element().selected = selected; return this; }
        @Override public boolean isSelected() { return element().selected; }
    }

    public static final class GbSwitchElementBuilder extends GbBuilder<MdGbSwitchElement, GbSwitchElementBuilder> implements
            Disableable<MdGbSwitchElement, GbSwitchElementBuilder>, Selectable<MdGbSwitchElement, GbSwitchElementBuilder>,
            Requireable<MdGbSwitchElement, GbSwitchElementBuilder>, FormAssociable<MdGbSwitchElement, GbSwitchElementBuilder>,
            HasInputEvent<MdGbSwitchElement, GbSwitchElementBuilder>, HasChangeEvent<MdGbSwitchElement, GbSwitchElementBuilder> {
        GbSwitchElementBuilder() { super("md-gb-switch", MdGbSwitchElement.class); }
        @Override public GbSwitchElementBuilder that() { return this; }
        public GbSwitchElementBuilder onIcon(String icon) { return slot("on-icon", new GbIconElementBuilder().text(icon)); }
        public GbSwitchElementBuilder offIcon(String icon) { return slot("off-icon", new GbIconElementBuilder().text(icon)); }
        @Override public GbSwitchElementBuilder disabled(boolean disabled) { element().disabled = disabled; return this; }
        @Override public GbSwitchElementBuilder select(boolean selected) { element().selected = selected; return this; }
        @Override public boolean isSelected() { return element().selected; }
        @Override public GbSwitchElementBuilder required(boolean required) { element().required = required; return this; }
    }
}
