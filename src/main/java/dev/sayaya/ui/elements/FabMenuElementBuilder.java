package dev.sayaya.ui.elements;

import dev.sayaya.ui.elements.interfaces.HasAriaLabel;
import dev.sayaya.ui.elements.interfaces.Openable;
import elemental2.dom.*;
import elemental2.dom.DomGlobal;
import jsinterop.base.Js;
import org.jboss.elemento.*;

import java.util.function.Consumer;

import static org.jboss.elemento.Elements.*;

/**
 * M3 FAB menu (sayaya-ui 자체 구현, elemento 엘리먼트만 사용).
 * <pre>{@code
 * import static dev.sayaya.ui.elements.FabMenuElementBuilder.*;
 *
 * var menu = fabMenu().icon("add").ariaLabel("만들기")
 *     .item("edit", "새 메모", e -> newNote())
 *     .item("photo_camera", "사진", e -> takePhoto())
 *     .element();
 * }</pre>
 * M3: FAB 에서 열리고, 열리면 FAB 이 닫기 버튼으로 바뀐다. 모든 FAB 크기에 메뉴 크기는 하나이며 primary/secondary/tertiary 색 구성이 있다.
 * 수치 출처: Compose Material3 FabMenuBaselineTokens(닫기 버튼 56dp·모서리 full·아이콘 20dp, 항목 높이 56dp·좌우 24dp·아이콘 24dp·간격 4dp,
 * 아이콘-라벨 8dp, 닫기 버튼-목록 8dp, elevation level 3), 닫힌 FAB 은 FabBaseline 56dp·모서리 16dp.
 * Esc 를 누르거나 항목을 고르면 닫히고 포커스는 토글 버튼으로 돌아간다.
 */
public final class FabMenuElementBuilder extends CustomElementBuilder<FabMenuElementBuilder> implements
        HasAriaLabel<HTMLDivElement, FabMenuElementBuilder>, Openable<FabMenuElementBuilder> {
    public static final String CLASS = "sayaya-fab-menu";
    static final String STYLE_ID = "sayaya-fab-menu-style";
    /** 색 구성. 각 역할은 --md-sys-color-{토큰} 이 없을 때 기본값(M3 baseline)을 쓴다. CSS 는 이 enum 에서만 생성한다 */
    public enum Color {
        Primary("primary", "primary-container", "#eaddff", "on-primary-container", "#21005d", "primary", "#6750a4", "on-primary", "#ffffff"),
        Secondary("secondary", "secondary-container", "#e8def8", "on-secondary-container", "#1d192b", "secondary", "#625b71", "on-secondary", "#ffffff"),
        Tertiary("tertiary", "tertiary-container", "#ffd8e4", "on-tertiary-container", "#31111d", "tertiary", "#7d5260", "on-tertiary", "#ffffff");
        final String value;
        final String container, containerFallback, onContainer, onContainerFallback, strong, strongFallback, onStrong, onStrongFallback;
        Color(String value, String container, String containerFallback, String onContainer, String onContainerFallback,
              String strong, String strongFallback, String onStrong, String onStrongFallback) {
            this.value = value;
            this.container = container; this.containerFallback = containerFallback;
            this.onContainer = onContainer; this.onContainerFallback = onContainerFallback;
            this.strong = strong; this.strongFallback = strongFallback;
            this.onStrong = onStrong; this.onStrongFallback = onStrongFallback;
        }
    }

    public static FabMenuElementBuilder fabMenu() { return new FabMenuElementBuilder(); }

    private final HTMLButtonElement toggle;
    private final HTMLElement toggleIcon;
    private final HTMLElement list;
    private String icon = "add";
    private Consumer<Boolean> onToggle;

    private FabMenuElementBuilder() {
        injectStyle();
        element().classList.add(CLASS, Color.Primary.value);
        list = div().css("items").attr("role", "menu").attr("id", nextId()).element();
        toggleIcon = span().css("icon").attr("aria-hidden", "true").text(icon).element();
        toggle = button().css("toggle").attr("type", "button").attr("aria-haspopup", "menu").attr("aria-expanded", "false")
                .attr("aria-controls", list.id).add(toggleIcon).element();
        toggle.addEventListener("click", e -> open(!isOpen()));
        element().addEventListener("keydown", e -> {
            KeyboardEvent k = Js.uncheckedCast(e);
            if ("Escape".equals(k.key) && isOpen()) { open(false); toggle.focus(); e.preventDefault(); }
        });
        element().appendChild(list);
        element().appendChild(toggle);
    }
    private static int seq = 0;
    private static String nextId() { return "sayaya-fab-menu-" + (++seq); }
    @Override public FabMenuElementBuilder that() { return this; }

    /** 닫혀 있을 때 FAB 아이콘(Material Symbols 이름). 열리면 close 로 바뀐다 */
    public FabMenuElementBuilder icon(String icon) { this.icon = icon; if (!isOpen()) toggleIcon.textContent = icon; return this; }
    /** 토글 버튼의 접근성 라벨 */
    public FabMenuElementBuilder ariaLabel(String label) { toggle.setAttribute("aria-label", label); return this; }
    public FabMenuElementBuilder color(Color color) {
        for (Color c : Color.values()) element().classList.remove(c.value);
        element().classList.add(color.value);
        return this;
    }
    /** 메뉴 항목. 고르면 handler 를 부르고 메뉴를 닫는다 */
    public FabMenuElementBuilder item(String icon, String label, EventListener handler) {
        var item = button().css("item").attr("type", "button").attr("role", "menuitem").attr("tabindex", "-1")
                .add(span().css("icon").attr("aria-hidden", "true").text(icon)).add(span().css("label").text(label)).element();
        item.addEventListener("click", e -> { if (handler != null) handler.handleEvent(e); open(false); toggle.focus(); });
        list.appendChild(item);
        return this;
    }
    public FabMenuElementBuilder onToggle(Consumer<Boolean> cb) { this.onToggle = cb; return this; }

    public boolean isOpen() { return element().classList.contains("open"); }
    /** 메뉴를 연다/닫는다. 열면 첫 항목으로 포커스가 간다 */
    public FabMenuElementBuilder open(boolean open) {
        if (open == isOpen()) return this;
        if (open) element().classList.add("open"); else element().classList.remove("open");
        toggle.setAttribute("aria-expanded", String.valueOf(open));
        toggleIcon.textContent = open ? "close" : icon;
        NodeList<Element> items = list.querySelectorAll(":scope > *");
        for (int i = 0; i < items.length; i++) items.item(i).setAttribute("tabindex", open ? "0" : "-1");
        if (open && items.length > 0) ((HTMLElement) Js.uncheckedCast(items.item(0))).focus();
        if (onToggle != null) onToggle.accept(open);
        return this;
    }

    private static void injectStyle() {
        HTMLStyleElement style = newStyle(STYLE_ID);
        if (style == null) return;
        String c = "." + CLASS;
        String shadow = "box-shadow:0 1px 3px 0 rgba(0,0,0,.3),0 4px 8px 3px rgba(0,0,0,.15);"; // elevation level 3
        StringBuilder css = new StringBuilder()
            .append(c).append("{display:inline-flex;flex-direction:column;align-items:flex-end;gap:8px}") // CloseButtonBetweenSpace 8
            .append(c).append(" .icon{font-family:'Material Symbols Outlined';font-weight:normal;font-style:normal;line-height:1;letter-spacing:normal;")
            .append("text-transform:none;white-space:nowrap;direction:ltr;-webkit-font-feature-settings:'liga';font-feature-settings:'liga';-webkit-font-smoothing:antialiased}")
            .append(c).append(" .toggle{box-sizing:border-box;width:56px;height:56px;border:0;padding:0;cursor:pointer;display:inline-flex;align-items:center;justify-content:center;")
            .append("border-radius:var(--md-fab-menu-fab-shape,var(--md-sys-shape-corner-large,16px));background:var(--_container);color:var(--_on-container);").append(shadow)
            .append("transition:border-radius 300ms cubic-bezier(0.2,0,0,1),background-color 300ms linear,color 300ms linear}")
            .append(c).append(" .toggle .icon{font-size:24px}")
            .append(c).append(".open .toggle{border-radius:var(--md-fab-menu-close-button-shape,var(--md-sys-shape-corner-full,9999px));background:var(--_strong);color:var(--_on-strong)}") // CloseButtonContainerShape full
            .append(c).append(".open .toggle .icon{font-size:20px}") // CloseButtonIconSize 20
            .append(c).append(" .items{display:none;flex-direction:column;align-items:flex-end;gap:4px;"
                + "box-sizing:border-box;max-height:var(--md-fab-menu-max-height,calc(100vh - 96px));overflow-y:auto;overscroll-behavior:contain;padding:4px 16px 16px;margin:-4px -16px -16px;"
                + "scrollbar-width:none;pointer-events:none}") // 그림자(elevation 3: 아래 약 15px, 옆 약 11px)가 스크롤 영역에서 잘리지 않도록 안쪽 여백을 두고 같은 만큼 음수 margin 으로 배치를 유지 // M3: 항목은 닫기 버튼 뒤로 스크롤, 닫기 버튼은 가리지 않는다 // ListItemBetweenSpace 4
            .append(c).append(".open .items{display:flex}")
            .append(c).append(" .items::-webkit-scrollbar{display:none}") // 스크롤은 되지만 스크롤바는 보이지 않는다
            .append(c).append(" .items>*{pointer-events:auto}") // 그림자 여백(투명)이 닫기 버튼을 가려 클릭을 막지 않도록 항목만 입력을 받는다
            .append(c).append(" .item{box-sizing:border-box;height:56px;border:0;border-radius:var(--md-fab-menu-item-shape,var(--md-sys-shape-corner-full,9999px));padding:0 24px;gap:8px;display:inline-flex;align-items:center;cursor:pointer;")
            .append("background:var(--_container);color:var(--_on-container);font:500 16px/24px Roboto,system-ui,sans-serif;").append(shadow).append("}")
            .append(c).append(" .item .icon{font-size:24px}")
            .append(c).append(" .item:focus-visible,").append(c).append(" .toggle:focus-visible{outline:3px solid var(--md-sys-color-secondary,#625b71);outline-offset:2px}")
            .append(c).append(" .item:hover{filter:brightness(0.96)}");
        for (Color k : Color.values())
            css.append(c).append('.').append(k.value).append("{--_container:var(--md-sys-color-").append(k.container).append(',').append(k.containerFallback)
               .append(");--_on-container:var(--md-sys-color-").append(k.onContainer).append(',').append(k.onContainerFallback)
               .append(");--_strong:var(--md-sys-color-").append(k.strong).append(',').append(k.strongFallback)
               .append(");--_on-strong:var(--md-sys-color-").append(k.onStrong).append(',').append(k.onStrongFallback).append(")}");
        css.append("@media (prefers-reduced-motion: reduce){").append(c).append(" .toggle{transition:none}}");
        style.textContent = css.toString();
        DomGlobal.document.head.appendChild(style);
    }
}
