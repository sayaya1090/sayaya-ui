package dev.sayaya.ui.elements;

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
public final class FabMenuElementBuilder implements
        HTMLElementStyleMethods<HTMLDivElement, FabMenuElementBuilder>, HTMLElementVisibilityMethods<HTMLDivElement, FabMenuElementBuilder>,
        ElementAttributeMethods<HTMLDivElement, FabMenuElementBuilder>, ElementClassListMethods<HTMLDivElement, FabMenuElementBuilder>,
        ElementEventMethods<HTMLDivElement, FabMenuElementBuilder>, ElementIdMethods<HTMLDivElement, FabMenuElementBuilder>,
        ElementConsumerMethods<HTMLDivElement, FabMenuElementBuilder> {
    public static final String CLASS = "sayaya-fab-menu";
    static final String STYLE_ID = "sayaya-fab-menu-style";
    public enum Color { Primary("primary"), Secondary("secondary"), Tertiary("tertiary");
        final String value; Color(String v) { value = v; } }

    public static FabMenuElementBuilder fabMenu() { return new FabMenuElementBuilder(); }

    private final HTMLContainerBuilder<HTMLDivElement> that = div();
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
    @Override public HTMLDivElement element() { return that.element(); }

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
        if (DomGlobal.document.getElementById(STYLE_ID) != null) return;
        HTMLStyleElement style = htmlElement("style", HTMLStyleElement.class).id(STYLE_ID).element();
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
            .append(c).append(" .items{display:none;flex-direction:column;align-items:flex-end;gap:4px}") // ListItemBetweenSpace 4
            .append(c).append(".open .items{display:flex}")
            .append(c).append(" .item{box-sizing:border-box;height:56px;border:0;border-radius:var(--md-fab-menu-item-shape,var(--md-sys-shape-corner-full,9999px));padding:0 24px;gap:8px;display:inline-flex;align-items:center;cursor:pointer;")
            .append("background:var(--_container);color:var(--_on-container);font:500 16px/24px Roboto,system-ui,sans-serif;").append(shadow).append("}")
            .append(c).append(" .item .icon{font-size:24px}")
            .append(c).append(" .item:focus-visible,").append(c).append(" .toggle:focus-visible{outline:3px solid var(--md-sys-color-secondary,#625b71);outline-offset:2px}")
            .append(c).append(" .item:hover{filter:brightness(0.96)}");
        String[][] sets = {
            {"primary", "primary-container", "#eaddff", "on-primary-container", "#21005d", "primary", "#6750a4", "on-primary", "#ffffff"},
            {"secondary", "secondary-container", "#e8def8", "on-secondary-container", "#1d192b", "secondary", "#625b71", "on-secondary", "#ffffff"},
            {"tertiary", "tertiary-container", "#ffd8e4", "on-tertiary-container", "#31111d", "tertiary", "#7d5260", "on-tertiary", "#ffffff"}};
        for (String[] s : sets)
            css.append(c).append('.').append(s[0]).append("{--_container:var(--md-sys-color-").append(s[1]).append(',').append(s[2])
               .append(");--_on-container:var(--md-sys-color-").append(s[3]).append(',').append(s[4])
               .append(");--_strong:var(--md-sys-color-").append(s[5]).append(',').append(s[6])
               .append(");--_on-strong:var(--md-sys-color-").append(s[7]).append(',').append(s[8]).append(")}");
        css.append("@media (prefers-reduced-motion: reduce){").append(c).append(" .toggle{transition:none}}");
        style.textContent = css.toString();
        DomGlobal.document.head.appendChild(style);
    }
}
