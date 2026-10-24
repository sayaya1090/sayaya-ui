package dev.sayaya.ui.elements;

import dev.sayaya.ui.elements.interfaces.HasAriaLabel;
import dev.sayaya.ui.elements.interfaces.Openable;
import dev.sayaya.ui.elements.interfaces.HasLabels;
import dev.sayaya.ui.elements.interfaces.UiLabel;
import elemental2.dom.*;
import elemental2.dom.DomGlobal;
import jsinterop.base.Js;
import org.jboss.elemento.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static org.jboss.elemento.Elements.*;

/**
 * M3 search — search bar + search view(docked / full-screen) (sayaya-ui 자체 구현, elemento 엘리먼트만 사용).
 * <pre>{@code
 * import static dev.sayaya.ui.elements.SearchElementBuilder.*;
 *
 * var mail = search().docked().placeholder("메일 검색")
 *     .suggestions("회의록", "휴가 신청", "주간 보고")
 *     .onSearch(q -> load(q)).element();
 * }</pre>
 * 수치 출처: Compose Material3 SearchBarTokens(높이 56dp·모서리 full·surface container high·앞 아이콘 on surface·뒤 아이콘 on surface variant),
 * SearchViewTokens(docked 모서리 extra large 28dp·헤더 56dp, full-screen 모서리 없음·헤더 72dp, 구분선 outline).
 * 입력창은 combobox, 제안 목록은 listbox 이다. ↑/↓ 로 제안을 고르고 Enter 로 선택·검색, Esc·스크림 클릭으로 닫는다.
 */
public final class SearchElementBuilder extends CustomElementBuilder<SearchElementBuilder> implements
        HasAriaLabel<HTMLDivElement, SearchElementBuilder>, Openable<SearchElementBuilder>, HasLabels<SearchElementBuilder.Label, SearchElementBuilder> {
    public static final String CLASS = "sayaya-search";
    static final String STYLE_ID = "sayaya-search-style";
    private static int seq = 0;
    /** UI 문구. 기본은 영어이고 {@link #label(Label, String)} 로 덮어쓴다 */
    public enum Label implements UiLabel {
        Search("Search"), Clear("Clear"), CloseSearch("Close search");
        public final String english;
        Label(String english) { this.english = english; }
        @Override public String english() { return english; }
    }
    private final java.util.Map<Label, String> labels = new java.util.EnumMap<>(Label.class);
    /** UI 문구 덮어쓰기(기본 영어) */
    public SearchElementBuilder label(Label label, String text) { labels.put(label, text); applyLabels(); return this; }
    private String text(Label l) { String v = labels.get(l); return v == null ? l.english : v; }
    private void applyLabels() {
        boolean closing = isOpen() && element().classList.contains("fullscreen");
        leading.setAttribute("aria-label", text(closing ? Label.CloseSearch : Label.Search));
        clear.setAttribute("aria-label", text(Label.Clear));
    }

    public static SearchPrepare search() { return new SearchPrepare(); }
    public static final class SearchPrepare {
        /** 검색 바 아래로 목록이 열리고 스크림이 본문을 덮는다(태블릿·데스크톱) */
        public SearchElementBuilder docked() { return new SearchElementBuilder(false); }
        /** 화면 전체로 펼쳐진다(모바일) */
        public SearchElementBuilder fullScreen() { return new SearchElementBuilder(true); }
    }

    private final HTMLInputElement input;
    private final HTMLElement leading, clear, trailing, view, list, scrim;
    private final List<String> suggestions = new ArrayList<>();
    private Consumer<String> onSearch, onInput;
    private final String listId;
    private int active = -1;

    private SearchElementBuilder(boolean fullScreen) {
        injectStyle();
        int id = ++seq;
        listId = "sayaya-search-list-" + id;
        element().classList.add(CLASS, fullScreen ? "fullscreen" : "docked");
        element().setAttribute("role", "search");
        leading = button().css("leading").attr("type", "button").attr("tabindex", "-1")
                .add(span().css("icon").attr("aria-hidden", "true").text("search")).element();
        input = Elements.input(InputType.search).css("input").attr("role", "combobox").attr("aria-expanded", "false")
                .attr("aria-controls", listId).attr("aria-autocomplete", "list").attr("autocomplete", "off").element();
        clear = button().css("clear").attr("type", "button").attr("hidden", "")
                .add(span().css("icon").attr("aria-hidden", "true").text("close")).element();
        trailing = span().css("trailing").element();
        list = div().css("list").attr("role", "listbox").attr("id", listId).element();
        view = div().css("view").add(list).element();
        scrim = div().css("scrim").element();
        element().appendChild(scrim);
        element().appendChild(div().css("bar").add(leading).add(input).add(clear).add(trailing).element());
        element().appendChild(view);

        input.addEventListener("focus", e -> open(true));
        input.addEventListener("input", e -> { render(); if (onInput != null) onInput.accept(input.value); });
        input.addEventListener("keydown", this::onKey);
        leading.addEventListener("click", e -> { if (isOpen() && element().classList.contains("fullscreen")) open(false); else input.focus(); });
        clear.addEventListener("click", e -> { input.value = ""; render(); input.focus(); if (onInput != null) onInput.accept(""); });
        scrim.addEventListener("click", e -> open(false));
        applyLabels();
    }
    @Override public SearchElementBuilder that() { return this; }

    public SearchElementBuilder placeholder(String placeholder) { input.placeholder = placeholder; return this; }
    /** 입력창 접근성 라벨(없으면 placeholder 가 읽힌다) */
    public SearchElementBuilder ariaLabel(String label) { input.setAttribute("aria-label", label); return this; }
    public SearchElementBuilder value(String value) { input.value = value; render(); return this; }
    /** contained(기본) 대신 구분선으로 검색 바와 목록을 나누는 divided(baseline) 스타일 */
    public SearchElementBuilder divided(boolean divided) {
        if (divided) element().classList.add("divided"); else element().classList.remove("divided");
        return this;
    }
    /** 입력에 맞춰 걸러 보여 줄 제안(대소문자 무시, 포함 검색) */
    public SearchElementBuilder suggestions(String... items) { for (String s : items) suggestions.add(s); render(); return this; }
    /** 뒤쪽 아이콘 버튼이나 아바타 */
    public SearchElementBuilder trailing(Element element) { trailing.appendChild(element); return this; }
    public SearchElementBuilder trailing(IsElement<? extends Element> element) { return trailing(element.element()); }
    /** Enter 나 제안 선택으로 검색을 실행할 때 */
    public SearchElementBuilder onSearch(Consumer<String> cb) { this.onSearch = cb; return this; }
    public SearchElementBuilder onInput(Consumer<String> cb) { this.onInput = cb; return this; }

    public String value() { return input.value; }
    public HTMLInputElement input() { return input; }
    public boolean isOpen() { return element().classList.contains("open"); }
    public SearchElementBuilder open(boolean open) {
        if (open == isOpen()) return this;
        if (open) element().classList.add("open"); else element().classList.remove("open");
        input.setAttribute("aria-expanded", String.valueOf(open));
        boolean full = element().classList.contains("fullscreen");
        ((HTMLElement) leading.firstElementChild).textContent = open && full ? "arrow_back" : "search";
        applyLabels();
        if (open && full) leading.removeAttribute("tabindex"); else leading.setAttribute("tabindex", "-1");
        setActive(-1);
        if (!open && full) input.blur();
        return this;
    }

    private void render() {
        while (list.firstChild != null) list.removeChild(list.firstChild);
        String q = input.value == null ? "" : input.value.trim().toLowerCase();
        int i = 0;
        for (String s : suggestions) {
            if (!q.isEmpty() && !s.toLowerCase().contains(q)) continue;
            String text = s;
            var opt = div().css("option").attr("role", "option").attr("id", listId + "-" + (i++)).attr("aria-selected", "false")
                    .add(span().css("icon").attr("aria-hidden", "true").text("history")).add(span().text(text)).element();
            opt.addEventListener("mousedown", e -> e.preventDefault()); // 입력창 포커스 유지
            opt.addEventListener("click", e -> select(text));
            list.appendChild(opt);
        }
        if (input.value == null || input.value.isEmpty()) clear.setAttribute("hidden", ""); else clear.removeAttribute("hidden");
        setActive(-1);
    }
    private NodeList<Element> options() { return list.querySelectorAll("[role=option]"); }
    private void setActive(int index) {
        var opts = options();
        active = opts.length == 0 ? -1 : Math.max(-1, Math.min(index, opts.length - 1));
        for (int i = 0; i < opts.length; i++) opts.item(i).setAttribute("aria-selected", String.valueOf(i == active));
        if (active < 0) input.removeAttribute("aria-activedescendant");
        else input.setAttribute("aria-activedescendant", opts.item(active).id);
    }
    private void select(String text) {
        input.value = text;
        render();
        open(false);
        if (onSearch != null) onSearch.accept(text);
    }
    private void onKey(Event e) {
        KeyboardEvent k = Js.uncheckedCast(e);
        switch (k.key) {
            case "ArrowDown": open(true); setActive(active + 1); e.preventDefault(); break;
            case "ArrowUp": setActive(active <= 0 ? options().length - 1 : active - 1); e.preventDefault(); break;
            case "Enter":
                if (active >= 0) select(options().item(active).lastElementChild.textContent);
                else { open(false); if (onSearch != null) onSearch.accept(input.value); }
                e.preventDefault(); break;
            case "Escape": open(false); e.preventDefault(); break;
            default:
        }
    }

    private static void injectStyle() {
        HTMLStyleElement style = newStyle(STYLE_ID);
        if (style == null) return;
        String c = "." + CLASS;
        style.textContent =
            c + "{position:relative;display:block;--_bg:var(--md-search-container-color,var(--md-sys-color-surface-container-high,#ece6f0))}"
          + c + " .icon{font-family:'Material Symbols Outlined';font-size:24px;line-height:1;font-weight:normal;font-style:normal;-webkit-font-feature-settings:'liga';font-feature-settings:'liga'}"
          // Search bar: 56dp, full, surface container high, 앞 아이콘 on surface, 뒤 아이콘 on surface variant
          + c + " .bar{position:relative;z-index:2;box-sizing:border-box;height:56px;border-radius:var(--md-search-bar-container-shape,var(--md-sys-shape-corner-full,9999px));background:var(--_bg);display:flex;align-items:center;gap:4px;padding:0 4px}"
          + c + " .leading," + c + " .clear{flex:none;width:48px;height:48px;border:0;border-radius:var(--md-search-icon-button-shape,var(--md-sys-shape-corner-full,50%));background:none;padding:0;display:inline-flex;align-items:center;justify-content:center;cursor:pointer}"
          + c + " .leading{color:var(--md-sys-color-on-surface,#1d1b20)}"
          + c + " .clear," + c + " .trailing{color:var(--md-sys-color-on-surface-variant,#49454f)}"
          + c + " .clear[hidden]{display:none}"
          + c + " .trailing{display:inline-flex;align-items:center;gap:4px}"
          + c + " .trailing:empty{display:none}"
          + c + " .input{flex:1;min-width:0;border:0;outline:none;background:none;height:100%;font:400 16px/24px Roboto,system-ui,sans-serif;color:var(--md-sys-color-on-surface,#1d1b20)}"
          + c + " .input::placeholder{color:var(--md-sys-color-on-surface-variant,#49454f)}"
          + c + " .input::-webkit-search-cancel-button{display:none}"
          + c + " .view{display:none;box-sizing:border-box;background:var(--_bg);overflow:auto}"
          + c + ".open .view{display:block}"
          + c + " .option{display:flex;align-items:center;gap:16px;min-height:56px;padding:0 16px;cursor:pointer;font:400 16px/24px Roboto,system-ui,sans-serif;color:var(--md-sys-color-on-surface,#1d1b20)}"
          + c + " .option .icon{color:var(--md-sys-color-on-surface-variant,#49454f)}"
          + c + " .option[aria-selected=true]," + c + " .option:hover{background:color-mix(in srgb,var(--md-sys-color-on-surface,#1d1b20) 8%,transparent)}"
          + c + ".divided.open .view{border-top:1px solid var(--md-sys-color-outline,#79747e)}" // SearchView DividerColor outline
          // Docked: 열리면 바 위 모서리 28dp(extra large) + 목록 아래 모서리 28dp, 본문은 스크림
          + c + " .scrim{display:none}"
          + c + ".docked.open .scrim{display:block;position:fixed;inset:0;z-index:1;background:var(--md-sys-color-scrim,#000);opacity:.32}"
          + c + ".docked.open .bar{border-radius:var(--md-search-view-docked-shape,var(--md-sys-shape-corner-extra-large,28px)) var(--md-search-view-docked-shape,var(--md-sys-shape-corner-extra-large,28px)) 0 0}"
          + c + ".docked .view{position:absolute;z-index:2;left:0;right:0;top:56px;max-height:min(480px,70vh);border-radius:0 0 var(--md-search-view-docked-shape,var(--md-sys-shape-corner-extra-large,28px)) var(--md-search-view-docked-shape,var(--md-sys-shape-corner-extra-large,28px));padding-bottom:8px}"
          // Full-screen: 화면 전체, 모서리 없음, 헤더 72dp
          + c + ".fullscreen.open{position:fixed;inset:0;z-index:1000;display:flex;flex-direction:column;background:var(--_bg)}"
          + c + ".fullscreen.open .bar{height:72px;border-radius:var(--md-search-view-full-screen-shape,var(--md-sys-shape-corner-none,0px));flex:none}"
          + c + ".fullscreen.open .view{flex:1}";
        DomGlobal.document.head.appendChild(style);
    }
}
