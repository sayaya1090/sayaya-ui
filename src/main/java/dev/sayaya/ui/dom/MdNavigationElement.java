package dev.sayaya.ui.dom;

import elemental2.dom.HTMLElement;
import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

/**
 * M3 네비게이션 컴포넌트 JsInterop 바인딩.
 * <ul>
 *   <li>md-navigation-bar / md-navigation-tab / md-navigation-drawer(-modal): Material Web 2.5.0 labs</li>
 *   <li>md-top-app-bar / md-navigation-rail / md-navigation-rail-item: sayaya-ui 자체 구현(labs-bundle/src/sayaya-navigation.js)</li>
 * </ul>
 */
public final class MdNavigationElement {
    private MdNavigationElement() {}

    @JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
    public static class MdTopAppBarElement extends HTMLElement {
        public String variant;
        public boolean scrolled;
        public boolean autoScroll;
    }

    @JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
    public static class MdNavigationBarElement extends HTMLElement {
        public double activeIndex;
        public boolean hideInactiveLabels;
    }

    @JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
    public static class MdNavigationTabElement extends HTMLElement {
        public boolean active;
        public boolean disabled;
        public boolean hideInactiveLabel;
        public String label;
        public String badgeValue;
        public boolean showBadge;
    }

    @JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
    public static class MdNavigationDrawerElement extends HTMLElement {
        public boolean opened;
        public String pivot;
    }

    @JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
    public static class MdNavigationDrawerModalElement extends HTMLElement {
        public boolean opened;
        public String pivot;
    }

    @JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
    public static class MdNavigationRailElement extends HTMLElement {
        public double activeIndex;
        public String alignment;
        public boolean expanded;
    }

    @JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
    public static class MdNavigationRailItemElement extends HTMLElement {
        public boolean active;
        public boolean disabled;
        public boolean expanded;
        public String label;
        public String badgeValue;
        public boolean showBadge;
    }
}
