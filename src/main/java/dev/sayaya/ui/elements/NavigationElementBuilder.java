package dev.sayaya.ui.elements;

import dev.sayaya.ui.dom.MdNavigationElement.*;
import dev.sayaya.ui.elements.interfaces.Disableable;
import dev.sayaya.ui.elements.interfaces.HasAriaLabel;
import elemental2.dom.CustomEvent;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;
import jsinterop.base.Js;
import org.jboss.elemento.*;

import java.util.function.Consumer;
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

    /** M3 Expressive: Medium/Large 는 medium flexible(112dp)/large flexible(120dp). CenterAligned 는 Small + centered(true) 와 같다. */
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
        private HTMLElement subtitle, expandedSubtitle;
        TopAppBarBuilder() { super("md-top-app-bar", MdTopAppBarElement.class); }
        @Override public TopAppBarBuilder that() { return this; }
        public TopAppBarBuilder variant(TopAppBarVariant variant) { element().variant = variant.value; return this; }
        /** 제목. medium/large 에서는 확장 영역의 큰 제목으로도 표시된다. 다시 호출하면 이전 제목을 바꾼다. 긴 제목은 한 줄 말줄임. */
        public TopAppBarBuilder headline(String text) {
            clearSlot("headline");
            slot("headline", Elements.span().text(text));
            if (expanded == null) { expanded = Elements.span().element(); slot("expanded-headline", expanded); }
            expanded.textContent = text;
            return this;
        }
        /**
         * 제목 자리에 요소(워크스페이스 셀렉터, 검색 상자 등)를 둔다. 다시 호출하면 이전 제목을 바꾼다.
         * 요소에는 텍스트용 한 줄 말줄임(nowrap/overflow:hidden)이 적용되지 않는다.
         * DOM 요소는 한 곳에만 붙을 수 있으므로 medium/large 의 확장 영역 제목에는 복제되지 않는다(small 에서 쓰는 것을 권장).
         */
        public TopAppBarBuilder headline(Element element) {
            clearSlot("headline");
            return slot("headline", element);
        }
        public TopAppBarBuilder headline(IsElement<? extends Element> element) { return headline(element.element()); }
        private void clearSlot(String name) {
            var old = element().querySelectorAll(":scope > [slot='" + name + "']");
            for (int i = old.length - 1; i >= 0; i--) element().removeChild(old.item(i));
        }
        /** 부제. flexible(medium/large) 앱 바는 부제가 있으면 높이가 136/152dp 로 커진다. */
        public TopAppBarBuilder subtitle(String text) {
            if (subtitle == null) { subtitle = Elements.span().element(); slot("subtitle", subtitle); expandedSubtitle = Elements.span().element(); slot("expanded-subtitle", expandedSubtitle); }
            subtitle.textContent = text; expandedSubtitle.textContent = text;
            return this;
        }
        /** 제목·부제 가운데 정렬 */
        public TopAppBarBuilder centered(boolean centered) { element().centered = centered; return this; }
        /** flexible 앱 바가 스크롤되면 small 로 줄어든다(autoScroll/scrolled 와 함께 사용) */
        public TopAppBarBuilder collapseOnScroll(boolean collapse) { element().collapseOnScroll = collapse; return this; }
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
        /** 접힌 너비 80dp(기본 96dp) */
        public NavigationRailBuilder narrow(boolean narrow) { element().narrow = narrow; return this; }
        /** 펼쳤을 때 본문 위에 뜨는 modal 레일(스크림·Esc·목적지 선택 시 접힘) */
        public NavigationRailBuilder modal(boolean modal) { element().modal = modal; return this; }
        /** 내장 메뉴 버튼: 펼침/접힘 전환, 펼치면 아이콘이 menu_open 으로 바뀐다 */
        public NavigationRailBuilder menu(boolean menu) { element().menu = menu; return this; }
        /** 메뉴 버튼 접근성 라벨(기본 "Open menu" / "Close menu") */
        public NavigationRailBuilder menuLabels(String openLabel, String closeLabel) {
            element().setAttribute("menu-open-label", openLabel); element().setAttribute("menu-close-label", closeLabel); return this;
        }
        /** 접혀 있을 때 레일을 숨긴다(펼칠 때만 보임) */
        public NavigationRailBuilder hideWhenCollapsed(boolean hide) { element().hideWhenCollapsed = hide; return this; }
        /**
         * [M3 외] 접힌 레일에서 항목 라벨을 숨기고 아이콘만 보인다. 라벨은 버튼의 aria-label·title(툴팁)로 남는다.
         * 펼친 레일에서는 라벨이 그대로 보인다. 항목 높이를 줄이려면 {@code --md-navigation-rail-item-min-height} 를 함께 지정한다.
         */
        public NavigationRailBuilder hideCollapsedLabels(boolean hide) { element().hideCollapsedLabels = hide; return this; }
        /**
         * 펼친 레일에서 모든 항목의 indicator(활성 표시·호버 영역)를 항목 폭만큼 채운다. 기본(false)은 M3 Expressive 대로 내용 폭에 맞춘다.
         * M3 가이드: "consider modifying the active indicator to fill the container"(baseline navigation drawer 와 비슷한 모양).
         */
        public NavigationRailBuilder fullWidthIndicator(boolean fill) { element().fullWidthIndicator = fill; return this; }
        /**
         * 펼친 레일의 항목 사이 간격(CSS 길이, 예: "4px"). 기본 0 = 토큰 md.comp.nav-rail.expanded.between-item-space.
         * {@code --md-navigation-rail-expanded-item-space} 변수를 지정한다. 접힌 레일 간격(기본 4px)은 {@code --md-navigation-rail-item-space} 로 조정한다.
         */
        public NavigationRailBuilder expandedItemSpace(String length) { element().style.setProperty("--md-navigation-rail-expanded-item-space", length); return this; }
        /** 펼침/접힘이 바뀔 때 호출(expanded-changed) */
        public NavigationRailBuilder onExpandedChanged(java.util.function.Consumer<Boolean> callback) {
            element().addEventListener("expanded-changed", evt -> callback.accept(element().expanded));
            return this;
        }
        /** 상단 헤더: 메뉴 버튼, FAB 등 */
        public NavigationRailBuilder header(Element e) { return slot("header", e); }
        public NavigationRailBuilder header(IsElement<? extends Element> e) { return slot("header", e); }
        public NavigationRailBuilder item(NavigationRailItemBuilder item) { return add(item); }
        /**
         * 레일 맨 아래 영역(설정·계정 버튼 등). footer 의 {@link NavigationRailItemBuilder} 는 펼침 상태를 따르지만
         * 목적지(activeIndex·onActivated) 대상은 아니므로 클릭은 직접 처리한다.
         */
        public NavigationRailBuilder footer(Element e) { return slot("footer", e); }
        public NavigationRailBuilder footer(IsElement<? extends Element> e) { return slot("footer", e); }
        /** 항목 활성화 시 호출(navigation-rail-activated) */
        public NavigationRailBuilder onActivated(IntConsumer callback) { return onIndexEvent("navigation-rail-activated", callback); }
        /**
         * navigation-rail-activated 원본 이벤트 구독. detail 은 {@code {activeIndex, item, href}} 이다.
         * 링크 항목({@link NavigationRailItemBuilder#href(String)})에서 {@code evt.preventDefault()} 를 부르면 브라우저 기본 이동을 막는다.
         * 수식 키(Cmd/Ctrl/Shift/Alt) 클릭·가운데 클릭은 이 이벤트 없이 브라우저 기본 동작(새 탭 등)으로 간다.
         */
        public NavigationRailBuilder onActivatedEvent(Consumer<CustomEvent<?>> callback) {
            element().addEventListener("navigation-rail-activated", evt -> callback.accept(Js.uncheckedCast(evt)));
            return this;
        }
        /**
         * 링크 항목의 일반 클릭을 앱 라우터로 넘긴다: 기본 이동을 막고 href 를 router 에 준다(SPA 라우팅).
         * 수식 키·가운데 클릭은 그대로 새 탭으로 열린다. href 가 없는 항목에는 호출하지 않는다.
         */
        public NavigationRailBuilder interceptLinks(Consumer<String> router) {
            return onActivatedEvent(evt -> {
                Object detail = evt.detail;
                Object href = detail == null ? null : Js.asPropertyMap(detail).get("href");
                if (href == null) return;
                evt.preventDefault();
                router.accept(Js.asString(href));
            });
        }
        /** 메뉴 버튼 아이콘(접힘 상태, 펼침 아이콘을 따로 주지 않으면 펼침에도 쓰인다). 기본은 폰트 없이 그려지는 menu SVG */
        public NavigationRailBuilder menuIcon(Element e) { return slot("menu-icon", e); }
        public NavigationRailBuilder menuIcon(IsElement<? extends Element> e) { return slot("menu-icon", e); }
        /** 메뉴 버튼 아이콘(펼침 상태). 기본은 menu-icon, 그것도 없으면 menu_open SVG */
        public NavigationRailBuilder menuOpenIcon(Element e) { return slot("menu-open-icon", e); }
        public NavigationRailBuilder menuOpenIcon(IsElement<? extends Element> e) { return slot("menu-open-icon", e); }
    }

    public static final class NavigationRailItemBuilder extends NavBuilder<MdNavigationRailItemElement, NavigationRailItemBuilder> implements
            Disableable<MdNavigationRailItemElement, NavigationRailItemBuilder> {
        NavigationRailItemBuilder() { super("md-navigation-rail-item", MdNavigationRailItemElement.class); }
        @Override public NavigationRailItemBuilder that() { return this; }
        public NavigationRailItemBuilder label(String label) { element().label = label; return this; }
        /**
         * 링크 목적지: 항목을 {@code <a href>} 로 그린다(가운데 클릭·Cmd/Ctrl 클릭 새 탭, 주소 복사). 활성이면 aria-current="page".
         * 일반 클릭은 navigation-rail-activated 를 내고, 앱이 그 이벤트를 preventDefault 하지 않으면 링크대로 이동한다.
         */
        public NavigationRailItemBuilder href(String href) { element().href = href; return this; }
        public NavigationRailItemBuilder icon(String icon) { return slot("inactive-icon", IconElementBuilder.icon(icon)); }
        public NavigationRailItemBuilder icon(Element icon) { return slot("inactive-icon", icon); }
        public NavigationRailItemBuilder activeIcon(String icon) { return slot("active-icon", IconElementBuilder.icon(icon).filled(true)); }
        public NavigationRailItemBuilder badge(String value) { element().badgeValue = value; element().showBadge = true; return this; }
        public NavigationRailItemBuilder badge() { return badge(""); }
        /** [M3 외] 라벨 아래 보조 텍스트. 펼친 레일에서만 보인다. 다시 호출하면 바꾼다. */
        public NavigationRailItemBuilder supportingText(String text) {
            var old = element().querySelector(":scope > [slot='supporting-text']");
            if (old != null) element().removeChild(old);
            return slot("supporting-text", Elements.span().text(text));
        }
        /** [M3 외] 항목 끝(오른쪽) 요소: 개수, 아이콘 버튼 등. 펼친 레일에서만 보인다. */
        public NavigationRailItemBuilder trailing(Element e) { return slot("trailing", e); }
        public NavigationRailItemBuilder trailing(IsElement<? extends Element> e) { return slot("trailing", e); }
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
