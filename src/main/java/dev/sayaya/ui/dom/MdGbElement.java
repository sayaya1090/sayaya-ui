package dev.sayaya.ui.dom;

import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLFormElement;
import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

/**
 * Material Web 2.5.0 labs/gb(Expressive) 컴포넌트(md-gb-*)에 대한 JsInterop 바인딩.
 * 각 필드는 @material/web/labs/gb 의 *-element.d.ts 에 선언된 공개 속성과 1:1로 대응한다.
 */
public final class MdGbElement {
    private MdGbElement() {}

    /** md-gb-badge */
    @JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
    public static class MdGbBadgeElement extends HTMLElement {
    }

    /** md-gb-button */
    @JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
    public static class MdGbButtonElement extends HTMLElement {
        public String color;
        public String size;
        public boolean square;
        public String type;
        public boolean softDisabled;
        public boolean selected;
        public String href;
        public String download;
        public String target;
        public boolean disabled;
        public String name;
        public HTMLFormElement form;
    }

    /** md-gb-card */
    @JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
    public static class MdGbCardElement extends HTMLElement {
        public String color;
        public boolean disabled;
        public boolean interactive;
    }

    /** md-gb-checkbox */
    @JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
    public static class MdGbCheckboxElement extends HTMLElement {
        public boolean checked;
        public boolean defaultChecked;
        public boolean indeterminate;
        public boolean error;
        public boolean required;
        public boolean disabled;
        public String value;
        public String name;
        public HTMLFormElement form;
    }

    /** md-gb-divider */
    @JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
    public static class MdGbDividerElement extends HTMLElement {
        public boolean vertical;
    }

    /** md-gb-fab */
    @JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
    public static class MdGbFabElement extends HTMLElement {
        public String color;
        public String size;
    }

    /** md-gb-icon */
    @JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
    public static class MdGbIconElement extends HTMLElement {
    }

    /** md-gb-icon-button */
    @JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
    public static class MdGbIconButtonElement extends HTMLElement {
        public String color;
        public String size;
        public boolean square;
        public String width;
        public String type;
        public boolean softDisabled;
        public boolean selected;
        public String href;
        public String download;
        public String target;
        public boolean disabled;
        public String name;
        public HTMLFormElement form;
    }

    /** md-gb-list */
    @JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
    public static class MdGbListElement extends HTMLElement {
        public boolean segmented;
    }

    /** md-gb-list-item */
    @JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
    public static class MdGbListItemElement extends HTMLElement {
        public boolean checked;
        public boolean disabled;
        public boolean nonInteractive;
    }

    /** md-gb-menu */
    @JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
    public static class MdGbMenuElement extends HTMLElement {
        public String color;
        public HTMLElement[] items;
    }

    /** md-gb-menu-group */
    @JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
    public static class MdGbMenuGroupElement extends HTMLElement {
        public String checkable;
        public HTMLElement menu;
        public HTMLElement[] items;
    }

    /** md-gb-menu-item */
    @JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
    public static class MdGbMenuItemElement extends HTMLElement {
        public boolean checked;
        public boolean disabled;
        public HTMLElement menu;
    }

    /** md-gb-radio */
    @JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
    public static class MdGbRadioElement extends HTMLElement {
        public boolean checked;
        public boolean defaultChecked;
        public boolean required;
        public boolean disabled;
        public String value;
        public String name;
        public HTMLFormElement form;
    }

    /** md-gb-split-button */
    @JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
    public static class MdGbSplitButtonElement extends HTMLElement {
        public String color;
        public String size;
        public boolean selected;
    }

    /** md-gb-switch */
    @JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
    public static class MdGbSwitchElement extends HTMLElement {
        public boolean selected;
        public boolean defaultSelected;
        public boolean required;
        public boolean disabled;
        public String value;
        public String name;
        public HTMLFormElement form;
    }
}
