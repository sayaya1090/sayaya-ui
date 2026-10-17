package dev.sayaya.ui.elements;

import dev.sayaya.ui.dom.MdNavigationElement.*;
import dev.sayaya.ui.elements.interfaces.Disableable;
import dev.sayaya.ui.elements.interfaces.HasAriaLabel;
import elemental2.dom.CustomEvent;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;
import jsinterop.base.Js;
import org.jboss.elemento.*;

import java.util.function.IntConsumer;

import static org.jboss.elemento.Elements.htmlContainer;

/**
 * M3 네비게이션 빌더: top app bar, navigation bar, navigation rail, navigation drawer.
 * <pre>{@code
 * import static dev.sayaya.ui.elements.NavigationElementBuilder.*;
 *
 * var bar = topAppBar().headline("문서").leading(menuButton).trailing(themeToggle).autoScroll(true).element();
 * var rail = navigationRail()
 *     .header(fab)
 *     .item(railItem().icon("home").label("홈"))
 *     .item(railItem().icon("inbox").label("받은편지함").badge("3"))
 *     .onActivated(index -> route(index))
 *     .element();
 * }</pre>
 * 레이아웃 상태 대응(handbook DrawerState / magi RailModes):
 * COLLAPSE → {@code rail.expanded(false)}, EXPAND → {@code rail.expanded(true)},
 * HIDE → {@code rail.hide()}, OVERLAY → {@code navigationDrawerModal().opened(true)}.
 */
public final class NavigationElementBuilder {
    private NavigationElementBuilder() {}

    public static TopAppBarBuilder topAppBar() { return new TopAppBarBuilder(); }
    public static NavigationBarBuilder navigationBar() { return new NavigationBarBuilder(); }
    public static NavigationTabBuilder navigationTab() { return new NavigationTabBuilder(); }
    public static NavigationRailBuilder navigationRail() { return new NavigationRailBuilder(); }
    public static NavigationRailItemBuilder railItem() { return new NavigationRailItemBuilder(); }
    public static NavigationDrawerBuilder navigationDrawer() { return new NavigationDrawerBuilder(); }
    public static NavigationDrawerModalBuilder navigationDrawerModal() { return new NavigationDrawerModalBuilder(); }

    public enum TopAppBarVariant { Small("small"), CenterAligned("center-aligned"), Medium("medium"), Large("large");
        public final String value; TopAppBarVariant(String v) { value = v; } }
    /** flexible navigation bar 항목 배치. Auto: 바 너비 600dp 미만 세로형, 이상 가로형(M3 가이드) */
    public enum NavigationBarLayout { Auto("auto"), Vertical("vertical"), Horizontal("horizontal");
        public final String value; NavigationBarLayout(String v) { value = v; } }
    public enum RailAlignment { Top("top"), Center("center"), Bottom("bottom");
        public final String value; RailAlignment(String v) { value = v; } }
    public enum DrawerPivot { Start("start"), End("end");
        public final String value; DrawerPivot(String v) { value = v; } }

    /** 공통 기반 */
    public abstract static class NavBuilder<E extends HTMLElement, SELF extends NavBuilder<E, SELF>> implements
            HTMLElementStyleMethods<E, SELF>, HTMLElementVisibilityMethods<E, SELF>, ElementAttributeMethods<E, SELF>,
            ElementClassListMethods<E, SELF>, ElementContainerMethods<E, SELF>, ElementEventMethods<E, SELF>,
            ElementIdMethods<E, SELF>, ElementConsumerMethods<E, SELF>, HasAriaLabel<E, SELF> {
        private final HTMLContainerBuilder<E> that;
        NavBuilder(String tag, Class<E> type) { that = htmlContainer(tag, type); }
        @Override public E element() { return that.element(); }
        protected SELF slot(String slot, Element child) {
            child.setAttribute("slot", slot);
            element().appendChild(child);
            return that();
        }
        protected SELF slot(String slot, IsElement<? extends Element> child) { return slot(slot, child.element()); }
        /** activeIndex 를 detail 로 담은 CustomEvent 구독 */
        protected SELF onIndexEvent(String type, IntConsumer callback) {
            element().addEventListener(type, evt -> {
                Object detail = ((CustomEvent<?>) Js.uncheckedCast(evt)).detail;
                Object index = detail == null ? null : Js.asPropertyMap(detail).get("activeIndex");
                callback.accept(index == null ? -1 : Js.asInt(index));
            });
            return that();
        }
    }

    // ---------------- Top app bar (md-top-app-bar) ----------------
    public static final class TopAppBarBuilder extends NavBuilder<MdTopAppBarElement, TopAppBarBuilder> {
        private HTMLElement expanded;
        TopAppBarBuilder() { super("md-top-app-bar", MdTopAppBarElement.class); }
        @Override public TopAppBarBuilder that() { return this; }
        public TopAppBarBuilder variant(TopAppBarVariant variant) { element().variant = variant.value; return this; }
        /** 제목. medium/large 에서는 확장 영역의 큰 제목으로도 표시된다. */
        public TopAppBarBuilder headline(String text) {
            slot("headline", Elements.span().text(text));
            if (expanded == null) { expanded = Elements.span().element(); slot("expanded-headline", expanded); }
            expanded.textContent = text;
            return this;
        }
        public TopAppBarBuilder leading(Element e) { return slot("leading", e); }
        public TopAppBarBuilder leading(IsElement<? extends Element> e) { return slot("leading", e); }
        public TopAppBarBuilder trailing(Element e) { return slot("trailing", e); }
        public TopAppBarBuilder trailing(IsElement<? extends Element> e) { return slot("trailing", e); }
        /** 창 스크롤 시 표면색을 surface-container 로 바꾼다(M3 on-scroll). */
        public TopAppBarBuilder autoScroll(boolean auto) { element().autoScroll = auto; return this; }
        /** 직접 스크롤 상태를 지정한다(스크롤 컨테이너가 window 가 아닐 때). */
        public TopAppBarBuilder scrolled(boolean scrolled) { element().scrolled = scrolled; return this; }
    }

    // ---------------- Navigation bar (md-navigation-bar, sayaya-ui M3 flexible navigation bar) ----------------
    public static final class NavigationBarBuilder extends NavBuilder<MdNavigationBarElement, NavigationBarBuilder> {
        NavigationBarBuilder() { super("md-navigation-bar", MdNavigationBarElement.class); }
        @Override public NavigationBarBuilder that() { return this; }
        public NavigationBarBuilder activeIndex(int index) { element().activeIndex = index; return this; }
        /** labs 호환용. M3 가이드는 라벨 제거를 권하지 않는다. */
        public NavigationBarBuilder hideInactiveLabels(boolean hide) { element().hideInactiveLabels = hide; return this; }
        public NavigationBarBuilder layout(NavigationBarLayout layout) { element().layout = layout.value; return this; }
        public NavigationBarBuilder tab(NavigationTabBuilder tab) { return add(tab); }
        /** 탭 활성화 시 호출(navigation-bar-activated) */
        public NavigationBarBuilder onActivated(IntConsumer callback) { return onIndexEvent("navigation-bar-activated", callback); }
    }

    public static final class NavigationTabBuilder extends NavBuilder<MdNavigationTabElement, NavigationTabBuilder> implements
            Disableable<MdNavigationTabElement, NavigationTabBuilder> {
        NavigationTabBuilder() { super("md-navigation-tab", MdNavigationTabElement.class); }
        @Override public NavigationTabBuilder that() { return this; }
        public NavigationTabBuilder label(String label) { element().label = label; return this; }
        public NavigationTabBuilder icon(String icon) { return slot("inactive-icon", IconElementBuilder.icon(icon)); }
        public NavigationTabBuilder activeIcon(String icon) { return slot("active-icon", IconElementBuilder.icon(icon).filled(true)); }
        public NavigationTabBuilder badge(String value) { element().badgeValue = value; element().showBadge = true; return this; }
        /** 숫자 없는 점 배지 */
        public NavigationTabBuilder badge() { return badge(""); }
        public NavigationTabBuilder active(boolean active) { element().active = active; return this; }
        @Override public NavigationTabBuilder disabled(boolean disabled) { element().disabled = disabled; return this; }
    }

    // ---------------- Navigation rail (md-navigation-rail) ----------------
    public static final class NavigationRailBuilder extends NavBuilder<MdNavigationRailElement, NavigationRailBuilder> {
        NavigationRailBuilder() { super("md-navigation-rail", MdNavigationRailElement.class); }
        @Override public NavigationRailBuilder that() { return this; }
        public NavigationRailBuilder activeIndex(int index) { element().activeIndex = index; return this; }
        public NavigationRailBuilder alignment(RailAlignment alignment) { element().alignment = alignment.value; return this; }
        /** 펼침(라벨이 아이콘 옆, 220dp) / 접힘(80dp) */
        public NavigationRailBuilder expanded(boolean expanded) { element().expanded = expanded; return this; }
        /** 상단 헤더: 메뉴 버튼, FAB 등 */
        public NavigationRailBuilder header(Element e) { return slot("header", e); }
        public NavigationRailBuilder header(IsElement<? extends Element> e) { return slot("header", e); }
        public NavigationRailBuilder item(NavigationRailItemBuilder item) { return add(item); }
        /** 항목 활성화 시 호출(navigation-rail-activated) */
        public NavigationRailBuilder onActivated(IntConsumer callback) { return onIndexEvent("navigation-rail-activated", callback); }
    }

    public static final class NavigationRailItemBuilder extends NavBuilder<MdNavigationRailItemElement, NavigationRailItemBuilder> implements
            Disableable<MdNavigationRailItemElement, NavigationRailItemBuilder> {
        NavigationRailItemBuilder() { super("md-navigation-rail-item", MdNavigationRailItemElement.class); }
        @Override public NavigationRailItemBuilder that() { return this; }
        public NavigationRailItemBuilder label(String label) { element().label = label; return this; }
        public NavigationRailItemBuilder icon(String icon) { return slot("inactive-icon", IconElementBuilder.icon(icon)); }
        public NavigationRailItemBuilder icon(Element icon) { return slot("inactive-icon", icon); }
        public NavigationRailItemBuilder activeIcon(String icon) { return slot("active-icon", IconElementBuilder.icon(icon).filled(true)); }
        public NavigationRailItemBuilder badge(String value) { element().badgeValue = value; element().showBadge = true; return this; }
        public NavigationRailItemBuilder badge() { return badge(""); }
        @Override public NavigationRailItemBuilder disabled(boolean disabled) { element().disabled = disabled; return this; }
    }

    // ---------------- Navigation drawer (md-navigation-drawer[-modal], labs) ----------------
    public static final class NavigationDrawerBuilder extends NavBuilder<MdNavigationDrawerElement, NavigationDrawerBuilder> {
        NavigationDrawerBuilder() { super("md-navigation-drawer", MdNavigationDrawerElement.class); }
        @Override public NavigationDrawerBuilder that() { return this; }
        public NavigationDrawerBuilder opened(boolean opened) { element().opened = opened; return this; }
        public NavigationDrawerBuilder pivot(DrawerPivot pivot) { element().pivot = pivot.value; return this; }
    }

    public static final class NavigationDrawerModalBuilder extends NavBuilder<MdNavigationDrawerModalElement, NavigationDrawerModalBuilder> {
        NavigationDrawerModalBuilder() { super("md-navigation-drawer-modal", MdNavigationDrawerModalElement.class); }
        @Override public NavigationDrawerModalBuilder that() { return this; }
        public NavigationDrawerModalBuilder opened(boolean opened) { element().opened = opened; return this; }
        public NavigationDrawerModalBuilder pivot(DrawerPivot pivot) { element().pivot = pivot.value; return this; }
    }
}
