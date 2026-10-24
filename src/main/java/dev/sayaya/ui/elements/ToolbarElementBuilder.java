package dev.sayaya.ui.elements;

import dev.sayaya.ui.elements.interfaces.HasAriaLabel;
import elemental2.dom.*;
import elemental2.dom.DomGlobal;
import jsinterop.base.Js;
import org.jboss.elemento.*;

import static org.jboss.elemento.Elements.div;
import static org.jboss.elemento.Elements.htmlElement;

/**
 * M3 toolbar — docked / floating (sayaya-ui 자체 구현, elemento 엘리먼트만 사용).
 * <pre>{@code
 * import static dev.sayaya.ui.elements.ToolbarElementBuilder.*;
 *
 * var docked = toolbar().docked().ariaLabel("편집 도구")
 *     .add(button().icon("undo")).add(button().icon("redo")).element();
 * var floating = toolbar().floating().vibrant(true).ariaLabel("서식")
 *     .add(button().icon("format_bold")).add(button().icon("format_italic")).element();
 * }</pre>
 * M3: baseline bottom app bar 는 더 이상 권장되지 않으며 docked toolbar 로 대체한다. 내비게이션 바와 동시에 표시하지 않는다.
 * 수치 출처: Compose Material3 DockedToolbarTokens / FloatingToolbarTokens.
 * 방향키(가로: ←/→, 세로: ↑/↓)와 Home/End 로 항목 사이를 이동한다(ARIA toolbar 패턴).
 * <p>CSS 변수: {@code --md-toolbar-container-color}, {@code --md-toolbar-content-color}.</p>
 */
public final class ToolbarElementBuilder implements
        HTMLElementStyleMethods<HTMLDivElement, ToolbarElementBuilder>, HTMLElementVisibilityMethods<HTMLDivElement, ToolbarElementBuilder>,
        ElementAttributeMethods<HTMLDivElement, ToolbarElementBuilder>, ElementClassListMethods<HTMLDivElement, ToolbarElementBuilder>,
        ElementContainerMethods<HTMLDivElement, ToolbarElementBuilder>, ElementEventMethods<HTMLDivElement, ToolbarElementBuilder>,
        ElementIdMethods<HTMLDivElement, ToolbarElementBuilder>, ElementConsumerMethods<HTMLDivElement, ToolbarElementBuilder>,
        HasAriaLabel<HTMLDivElement, ToolbarElementBuilder> {
    public static final String CLASS = "sayaya-toolbar";
    static final String STYLE_ID = "sayaya-toolbar-style";

    public static ToolbarPrepare toolbar() { return new ToolbarPrepare(); }
    public static final class ToolbarPrepare {
        /** 화면 아래에 붙는 전체 너비 툴바(bottom app bar 대체) */
        public ToolbarElementBuilder docked() { return new ToolbarElementBuilder("docked"); }
        /** 콘텐츠 위에 떠 있는 둥근 툴바 */
        public ToolbarElementBuilder floating() { return new ToolbarElementBuilder("floating"); }
    }
    /** floating 툴바를 화면에 고정할 위치. 화면 가장자리 여백 16dp(ContainerExternalPadding) */
    public enum Position { None(null), BottomCenter("bottom-center"), BottomEnd("bottom-end"), EndCenter("end-center");
        final String value; Position(String v) { value = v; } }

    private final HTMLContainerBuilder<HTMLDivElement> that = div();
    private ToolbarElementBuilder(String variant) {
        injectStyle();
        element().classList.add(CLASS, variant);
        element().setAttribute("role", "toolbar");
        element().addEventListener("keydown", this::onKey);
    }
    @Override public ToolbarElementBuilder that() { return this; }
    @Override public HTMLDivElement element() { return that.element(); }

    /** vibrant 색 구성(primary container). 더 강하게 강조할 때 */
    public ToolbarElementBuilder vibrant(boolean vibrant) { return setClass("vibrant", vibrant); }
    /** 세로 배치(floating 전용) */
    public ToolbarElementBuilder vertical(boolean vertical) {
        setClass("vertical", vertical);
        if (vertical) element().setAttribute("aria-orientation", "vertical"); else element().removeAttribute("aria-orientation");
        return this;
    }
    public ToolbarElementBuilder position(Position position) {
        if (position == null || position.value == null) element().removeAttribute("data-position");
        else element().setAttribute("data-position", position.value);
        return this;
    }
    private ToolbarElementBuilder setClass(String cls, boolean on) {
        if (on) element().classList.add(cls); else element().classList.remove(cls);
        return this;
    }

    private void onKey(Event e) {
        KeyboardEvent k = Js.uncheckedCast(e);
        boolean vertical = element().classList.contains("vertical");
        boolean rtl = !vertical && Locales.rtl(element()); // 오른쪽→왼쪽 언어에서는 ← 가 다음 항목
        String next = vertical ? "ArrowDown" : (rtl ? "ArrowLeft" : "ArrowRight"), prev = vertical ? "ArrowUp" : (rtl ? "ArrowRight" : "ArrowLeft");
        if (!k.key.equals(next) && !k.key.equals(prev) && !k.key.equals("Home") && !k.key.equals("End")) return;
        NodeList<Element> items = element().querySelectorAll(":scope > *");
        int n = items.length;
        if (n == 0) return;
        int current = -1;
        for (int i = 0; i < n; i++) if (items.item(i) == e.target || items.item(i).contains(Js.uncheckedCast(e.target))) current = i;
        int target;
        if (k.key.equals("Home")) target = 0;
        else if (k.key.equals("End")) target = n - 1;
        else if (k.key.equals(next)) target = current < 0 ? 0 : (current + 1) % n;
        else target = current < 0 ? n - 1 : (current - 1 + n) % n;
        ((HTMLElement) Js.uncheckedCast(items.item(target))).focus();
        e.preventDefault();
    }

    private static void injectStyle() {
        if (DomGlobal.document.getElementById(STYLE_ID) != null) return;
        HTMLStyleElement style = htmlElement("style", HTMLStyleElement.class).id(STYLE_ID).element();
        String c = "." + CLASS;
        style.textContent =
                c + "{display:flex;align-items:center;box-sizing:border-box;height:64px;" // ContainerHeight 64
              + "background:var(--md-toolbar-container-color,var(--md-sys-color-surface-container,#f3edf7));"
              + "color:var(--md-toolbar-content-color,var(--md-sys-color-on-surface,#1d1b20));"
              + "--md-icon-button-icon-color:var(--md-toolbar-content-color,var(--md-sys-color-on-surface-variant,#49454f))}"
              // Docked: 전체 너비, 모서리 없음, 좌우 16dp, 항목 간격 최소 4dp, 균등 배치
              + c + ".docked{width:100%;border-radius:var(--md-toolbar-docked-container-shape,var(--md-sys-shape-corner-none,0px));padding:0 16px;gap:4px;justify-content:space-between}"
              // Floating: 모서리 full, 좌우 8dp, 항목 간격 4dp. [파생] 그림자는 토큰에 없어 elevation level 3 으로 둔다
              + c + ".floating{display:inline-flex;border-radius:var(--md-toolbar-floating-container-shape,var(--md-sys-shape-corner-full,9999px));padding:0 8px;gap:4px;justify-content:center;"
              + "box-shadow:0 1px 3px 0 rgba(0,0,0,.3),0 4px 8px 3px rgba(0,0,0,.15)}"
              + c + ".floating.vertical{flex-direction:column;height:auto;width:64px;padding:8px 0}"
              // Vibrant: primary container / on primary container
              + c + ".vibrant{--md-toolbar-container-color:var(--md-sys-color-primary-container,#eaddff);"
              + "--md-toolbar-content-color:var(--md-sys-color-on-primary-container,#21005d)}"
              // 화면 고정 위치(가장자리 16dp)
              + c + "[data-position]{position:fixed;z-index:3}"
              + c + "[data-position=bottom-center]{bottom:16px;left:50%;transform:translateX(-50%)}"
              + c + "[data-position=bottom-end]{bottom:16px;inset-inline-end:16px}"
              + c + "[data-position=end-center]{inset-inline-end:16px;top:50%;transform:translateY(-50%)}";
        DomGlobal.document.head.appendChild(style);
    }
}
