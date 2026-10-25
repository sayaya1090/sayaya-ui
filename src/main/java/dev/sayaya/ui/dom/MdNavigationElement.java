package dev.sayaya.ui.dom;

import elemental2.dom.HTMLElement;
import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

/**
 * M3 네비게이션 컴포넌트 JsInterop 바인딩.
 * <ul>
 *   <li>md-navigation-drawer(-modal): Material Web 2.5.0 labs</li>
 *   <li>md-navigation-bar / md-navigation-tab: sayaya-ui 자체 구현 M3 flexible navigation bar(labs-bundle/src/sayaya-navbar.js)</li>
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
        public boolean centered;
        public boolean collapseOnScroll;
    }

    @JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
    public static class MdNavigationBarElement extends HTMLElement {
        public double activeIndex;
        public boolean hideInactiveLabels;
        public String layout;
        public String resolvedLayout;
    }

    @JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
    public static class MdNavigationTabElement extends HTMLElement {
        public boolean active;
        public boolean disabled;
        public boolean hideInactiveLabel;
        public String label;
        public String badgeValue;
        public boolean showBadge;
        public String layout;
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
        public boolean narrow;
        public boolean modal;
        public boolean menu;
        public boolean hideWhenCollapsed;
        public boolean hideCollapsedLabels;
        public boolean fullWidthIndicator;
        public native void toggle();
        public native void toggle(boolean expanded);
    }

    @JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
    public static class MdNavigationRailItemElement extends HTMLElement {
        public boolean active;
        public boolean disabled;
        public boolean expanded;
        public String label;
        public String badgeValue;
        public boolean showBadge;
        public boolean hideCollapsedLabels;
        public boolean fullWidthIndicator;
        /** 있으면 항목을 {@code <a href>} 로 그린다(링크 목적지) */
        public String href;
    }
}
