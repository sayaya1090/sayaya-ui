package dev.sayaya.ui.elements;

import dev.sayaya.ui.elements.interfaces.HasAriaLabel;
import dev.sayaya.ui.elements.interfaces.Openable;
import dev.sayaya.ui.elements.interfaces.HasLabels;
import dev.sayaya.ui.elements.interfaces.UiLabel;
import elemental2.dom.*;
import elemental2.dom.DomGlobal;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;
import org.jboss.elemento.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.jboss.elemento.Elements.*;

/**
 * M3 search — search bar + search view(docked / full-screen) (sayaya-ui 자체 구현, elemento 엘리먼트만 사용).
 * <pre>{@code
 * import static dev.sayaya.ui.elements.SearchElementBuilder.*;
 *
 * var mail = search().docked().placeholder("메일 검색")
 *     .suggestions("회의록", "휴가 신청", "주간 보고")
 *     .onSearch(q -> load(q)).element();
 *
 * // 명령 팔레트: 이름·보조 문구·앞 요소·행별 동작, 앱이 목록을 통째로 교체(걸러내지 않음)
 * var palette = search().docked().inline(true).filter(false).placeholder("명령 검색");
 * palette.onInput(q -> palette.setSuggestions(q, List.of(
 *     suggestion("새 세션", "세션", iconSvg, () -> newSession()).key("cmd:new-session"))));
 * }</pre>
 * 수치 출처: Compose Material3 SearchBarTokens(높이 56dp·모서리 full·surface container high·앞 아이콘 on surface·뒤 아이콘 on surface variant),
 * SearchViewTokens(docked 모서리 extra large 28dp·헤더 56dp, full-screen 모서리 없음·헤더 72dp, 구분선 outline).
 * 입력창은 combobox, 제안 목록은 listbox 이다. ↑/↓ 로 제안을 고르고 Enter 로 선택·검색, Esc·스크림 클릭으로 닫는다.
 * IME 조합 중(isComposing, keyCode 229)의 Enter·화살표·Esc 는 조합에 맡기고 처리하지 않는다.
 * 아이콘은 폰트 없이 그려지는 SVG(google/material-design-icons 24px)가 기본이고 {@link #searchIcon(Element)} 등으로 바꿀 수 있다.
 */
public final class SearchElementBuilder extends CustomElementBuilder<SearchElementBuilder> implements
        HasAriaLabel<HTMLDivElement, SearchElementBuilder>, Openable<SearchElementBuilder>, HasLabels<SearchElementBuilder.Label, SearchElementBuilder> {
    public static final String CLASS = "sayaya-search";
    static final String STYLE_ID = "sayaya-search-style";
    private static int seq = 0;
    private static final String SVG_NS = "http://www.w3.org/2000/svg";
    // google/material-design-icons src/{action/search, navigation/close, navigation/arrow_back, action/history}/materialicons/24px.svg 의 path
    static final String SEARCH_D = "M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z";
    static final String CLOSE_D = "M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z";
    static final String ARROW_BACK_D = "M20 11H7.83l5.59-5.59L12 4l-8 8 8 8 1.41-1.41L7.83 13H20v-2z";
    static final String HISTORY_D = "M13 3c-4.97 0-9 4.03-9 9H1l3.89 3.89.07.14L9 12H6c0-3.87 3.13-7 7-7s7 3.13 7 7-3.13 7-7 7c-1.93 0-3.68-.79-4.94-2.06l-1.42 1.42C8.27 19.99 10.51 21 13 21c4.97 0 9-4.03 9-9s-4.03-9-9-9zm-1 5v5l4.28 2.54.72-1.21-3.5-2.08V8H12z";
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

    /**
     * 제안 항목: 이름 + 보조 문구 + 앞 요소(아이콘·글자) + 고르면 실행할 동작.
     * 같은 이름의 항목이 여럿이어도 각 항목(객체)을 따로 다룬다. {@link #key(String)} 를 주면 목록을 새 객체로 바꿔도 하이라이트가 그 항목을 따라간다.
     */
    public static final class Suggestion {
        private final String name, supportingText;
        private final Element leading;
        private final Runnable onSelect;
        private final boolean history;
        private String key;
        private Suggestion(String name, String supportingText, Element leading, Runnable onSelect, boolean history) {
            this.name = name == null ? "" : name; this.supportingText = supportingText; this.leading = leading; this.onSelect = onSelect; this.history = history;
        }
        /** 목록 교체 사이에 같은 항목임을 알리는 식별자(없으면 같은 객체일 때만 같은 항목) */
        public Suggestion key(String key) { this.key = key; return this; }
        public String name() { return name; }
        public String supportingText() { return supportingText; }
        public Element leading() { return leading; }
        public String key() { return key; }
        boolean sameAs(Suggestion o) { return o == this || (o != null && key != null && key.equals(o.key)); }
        boolean matches(String q) {
            return name.toLowerCase().contains(q) || (supportingText != null && supportingText.toLowerCase().contains(q));
        }
    }
    /** 제안 항목. leading·supportingText·onSelect 는 null 이어도 된다. onSelect 가 있으면 고를 때 입력값을 바꾸지 않고 그 동작만 실행한다 */
    public static Suggestion suggestion(String name, String supportingText, Element leading, Runnable onSelect) {
        return new Suggestion(name, supportingText, leading, onSelect, false);
    }
    public static Suggestion suggestion(String name, String supportingText, IsElement<? extends Element> leading, Runnable onSelect) {
        return suggestion(name, supportingText, leading == null ? null : leading.element(), onSelect);
    }
    public static Suggestion suggestion(String name, Runnable onSelect) { return suggestion(name, null, (Element) null, onSelect); }

    public static SearchPrepare search() { return new SearchPrepare(); }
    public static final class SearchPrepare {
        /** 검색 바 아래로 목록이 열리고 스크림이 본문을 덮는다(태블릿·데스크톱) */
        public SearchElementBuilder docked() { return new SearchElementBuilder(false); }
        /** 화면 전체로 펼쳐진다(모바일) */
        public SearchElementBuilder fullScreen() { return new SearchElementBuilder(true); }
    }

    private final HTMLInputElement input;
    private final HTMLElement leading, searchIconBox, backIconBox, clear, clearIconBox, trailing, view, list, scrim;
    private final List<Suggestion> suggestions = new ArrayList<>();
    private final List<Suggestion> shown = new ArrayList<>();
    private Consumer<String> onSearch, onInput;
    private Consumer<Suggestion> onSelect;
    private Supplier<? extends Element> historyIcon = () -> svgIcon("history", HISTORY_D);
    private boolean filter = true;
    private final String listId;
    private int active = -1;

    private SearchElementBuilder(boolean fullScreen) {
        injectStyle();
        int id = ++seq;
        listId = "sayaya-search-list-" + id;
        element().classList.add(CLASS, fullScreen ? "fullscreen" : "docked");
        element().setAttribute("role", "search");
        searchIconBox = iconBox("search-icon", svgIcon("search", SEARCH_D));
        backIconBox = iconBox("back-icon", svgIcon("arrow_back", ARROW_BACK_D));
        leading = button().css("leading").attr("type", "button").attr("tabindex", "-1").add(searchIconBox).add(backIconBox).element();
        input = Elements.input(InputType.search).css("input").attr("role", "combobox").attr("aria-expanded", "false")
                .attr("aria-controls", listId).attr("aria-autocomplete", "list").attr("autocomplete", "off").element();
        clearIconBox = iconBox(null, svgIcon("close", CLOSE_D));
        clear = button().css("clear").attr("type", "button").attr("hidden", "").add(clearIconBox).element();
        trailing = span().css("trailing").element();
        list = div().css("list").attr("role", "listbox").attr("id", listId).element();
        view = div().css("view").add(list).element();
        scrim = div().css("scrim").element();
        element().appendChild(scrim);
        element().appendChild(div().css("bar").add(leading).add(input).add(clear).add(trailing).element());
        element().appendChild(view);

        input.addEventListener("focus", e -> open(true));
        input.addEventListener("input", e -> { render(false); if (onInput != null) onInput.accept(input.value); });
        input.addEventListener("keydown", this::onKey);
        leading.addEventListener("click", e -> { if (isOpen() && element().classList.contains("fullscreen")) open(false); else input.focus(); });
        clear.addEventListener("click", e -> { input.value = ""; render(false); input.focus(); if (onInput != null) onInput.accept(""); });
        scrim.addEventListener("click", e -> open(false));
        applyLabels();
    }
    @Override public SearchElementBuilder that() { return this; }

    /** 폰트 없이 그려지는 24px SVG 아이콘(fill currentColor, data-icon=name) */
    static Element svgIcon(String name, String d) {
        Element svg = DomGlobal.document.createElementNS(SVG_NS, "svg");
        svg.setAttribute("viewBox", "0 0 24 24"); svg.setAttribute("width", "24"); svg.setAttribute("height", "24");
        svg.setAttribute("fill", "currentColor"); svg.setAttribute("aria-hidden", "true"); svg.setAttribute("focusable", "false");
        svg.setAttribute("data-icon", name);
        Element path = DomGlobal.document.createElementNS(SVG_NS, "path");
        path.setAttribute("d", d);
        svg.appendChild(path);
        return svg;
    }
    private static HTMLElement iconBox(String extra, Element icon) {
        var box = span().css("icon").attr("aria-hidden", "true").element();
        if (extra != null) box.classList.add(extra);
        box.appendChild(icon);
        return box;
    }
    private static void replaceIcon(HTMLElement box, Element icon) {
        while (box.firstChild != null) box.removeChild(box.firstChild);
        if (icon != null) box.appendChild(icon);
    }

    public SearchElementBuilder placeholder(String placeholder) { input.placeholder = placeholder; return this; }
    /** 입력창 접근성 라벨(없으면 placeholder 가 읽힌다) */
    public SearchElementBuilder ariaLabel(String label) { input.setAttribute("aria-label", label); return this; }
    /** 입력값을 정한다(onInput 은 부르지 않는다). 하이라이트는 해제된다 */
    public SearchElementBuilder value(String value) { input.value = value; render(false); return this; }
    /** contained(기본) 대신 구분선으로 검색 바와 목록을 나누는 divided(baseline) 스타일 */
    public SearchElementBuilder divided(boolean divided) {
        if (divided) element().classList.add("divided"); else element().classList.remove("divided");
        return this;
    }
    /**
     * [M3 외] docked 목록을 바 아래 흐름 안에 두고(겹쳐 뜨지 않음) 스크림을 쓰지 않는다.
     * md-dialog 같은 컨테이너 안에서 명령 팔레트로 쓸 때: 목록이 컨테이너 밖으로 잘리거나 스크림이 이중으로 덮이지 않는다.
     */
    public SearchElementBuilder inline(boolean inline) {
        if (inline) element().classList.add("inline"); else element().classList.remove("inline");
        return this;
    }
    /** 입력에 맞춰 제안을 걸러낼지(기본 true: 이름·보조 문구에 대소문자 무시 포함 검색). 앱이 목록을 정하면 false */
    public SearchElementBuilder filter(boolean filter) { this.filter = filter; render(true); return this; }
    /** 입력에 맞춰 걸러 보여 줄 제안(대소문자 무시, 포함 검색). 앞에 기록(history) 아이콘이 붙는다. 기존 목록 뒤에 더한다 */
    public SearchElementBuilder suggestions(String... items) {
        for (String s : items) suggestions.add(new Suggestion(s, null, null, null, true));
        render(true); return this;
    }
    /** 제안 항목을 기존 목록 뒤에 더한다 */
    public SearchElementBuilder suggestions(Suggestion... items) { suggestions.addAll(Arrays.asList(items)); render(true); return this; }
    /**
     * 제안 목록을 통째로 바꾼다. 하이라이트된 항목이 새 목록에도 있으면(같은 객체 또는 같은 {@link Suggestion#key(String)}) 그 항목에 남고,
     * 없으면 하이라이트를 해제한다(aria-activedescendant 제거). 사라진 행을 가리키는 일은 없다.
     */
    public SearchElementBuilder setSuggestions(List<Suggestion> items) {
        suggestions.clear();
        if (items != null) for (Suggestion s : items) if (s != null) suggestions.add(s);
        render(true); return this;
    }
    public SearchElementBuilder setSuggestions(Suggestion... items) { return setSuggestions(Arrays.asList(items)); }
    /**
     * 늦게 도착하는 결과용: forQuery 가 지금 입력값과 같을 때만 목록을 바꾸고, 그사이 입력이 바뀌었으면 버린다(지난 질의의 결과가 새 질의 결과를 덮지 않게).
     */
    public SearchElementBuilder setSuggestions(String forQuery, List<Suggestion> items) {
        if (!Objects.equals(forQuery == null ? "" : forQuery, value() == null ? "" : value())) return this;
        return setSuggestions(items);
    }
    /** 제안을 모두 지운다 */
    public SearchElementBuilder clearSuggestions() { suggestions.clear(); render(true); return this; }
    /** 지금 목록(걸러지기 전)의 항목 */
    public List<Suggestion> suggestionItems() { return new ArrayList<>(suggestions); }
    /** 뒤쪽 아이콘 버튼이나 아바타 */
    public SearchElementBuilder trailing(Element element) { trailing.appendChild(element); return this; }
    public SearchElementBuilder trailing(IsElement<? extends Element> element) { return trailing(element.element()); }
    /** 앞 검색 아이콘(기본 search SVG) */
    public SearchElementBuilder searchIcon(Element icon) { replaceIcon(searchIconBox, icon); return this; }
    public SearchElementBuilder searchIcon(IsElement<? extends Element> icon) { return searchIcon(icon.element()); }
    /** full-screen 으로 열렸을 때의 뒤로 가기 아이콘(기본 arrow_back SVG) */
    public SearchElementBuilder backIcon(Element icon) { replaceIcon(backIconBox, icon); return this; }
    public SearchElementBuilder backIcon(IsElement<? extends Element> icon) { return backIcon(icon.element()); }
    /** 지우기 버튼 아이콘(기본 close SVG) */
    public SearchElementBuilder clearIcon(Element icon) { replaceIcon(clearIconBox, icon); return this; }
    public SearchElementBuilder clearIcon(IsElement<? extends Element> icon) { return clearIcon(icon.element()); }
    /** {@link #suggestions(String...)} 항목 앞 아이콘을 만드는 함수(기본 history SVG). 행마다 새 요소를 돌려줘야 한다 */
    public SearchElementBuilder historyIcon(Supplier<? extends Element> icon) { historyIcon = icon; render(true); return this; }
    /** Enter 나 제안 선택으로 검색을 실행할 때(동작이 있는 항목을 고를 때는 부르지 않는다) */
    public SearchElementBuilder onSearch(Consumer<String> cb) { this.onSearch = cb; return this; }
    public SearchElementBuilder onInput(Consumer<String> cb) { this.onInput = cb; return this; }
    /** 제안 항목을 고를 때(클릭·Enter). 항목의 onSelect 동작 뒤에 불린다 */
    public SearchElementBuilder onSelect(Consumer<Suggestion> cb) { this.onSelect = cb; return this; }

    public String value() { return input.value; }
    public HTMLInputElement input() { return input; }
    /** 입력창에 포커스를 준다(포커스하면 열린다). 다이얼로그를 연 직후에 부른다 */
    public SearchElementBuilder focus() { input.focus(); return this; }
    /** 입력값을 비우고 닫는다(onInput 은 부르지 않는다). 다이얼로그를 닫을 때 부른다. 제안 목록은 그대로 둔다 */
    public SearchElementBuilder reset() { input.value = ""; render(false); open(false); return this; }
    public boolean isOpen() { return element().classList.contains("open"); }
    public SearchElementBuilder open(boolean open) {
        if (open == isOpen()) return this;
        if (open) element().classList.add("open"); else element().classList.remove("open");
        input.setAttribute("aria-expanded", String.valueOf(open));
        boolean full = element().classList.contains("fullscreen");
        applyLabels();
        if (open && full) leading.removeAttribute("tabindex"); else leading.setAttribute("tabindex", "-1");
        setActive(-1);
        if (!open && full) input.blur();
        return this;
    }

    private void render(boolean keepActive) {
        Suggestion prev = keepActive && active >= 0 && active < shown.size() ? shown.get(active) : null;
        while (list.firstChild != null) list.removeChild(list.firstChild);
        shown.clear();
        String q = input.value == null ? "" : input.value.trim().toLowerCase();
        int i = 0;
        for (Suggestion s : suggestions) {
            if (filter && !q.isEmpty() && !s.matches(q)) continue;
            shown.add(s);
            var opt = div().css("option").attr("role", "option").attr("id", listId + "-" + (i++)).attr("aria-selected", "false").element();
            Element lead = s.leading != null ? s.leading : (s.history && historyIcon != null ? historyIcon.get() : null);
            if (lead != null) opt.appendChild(iconBox("lead", lead));
            var text = span().css("text").add(span().css("headline").text(s.name));
            if (s.supportingText != null && !s.supportingText.isEmpty()) text.add(span().css("supporting").text(s.supportingText));
            opt.appendChild(text.element());
            opt.addEventListener("mousedown", e -> e.preventDefault()); // 입력창 포커스 유지
            opt.addEventListener("click", e -> select(s));
            list.appendChild(opt);
        }
        if (input.value == null || input.value.isEmpty()) clear.setAttribute("hidden", ""); else clear.removeAttribute("hidden");
        if (list.firstChild == null) element().classList.add("empty"); else element().classList.remove("empty");
        int next = -1;
        if (prev != null) for (int j = 0; j < shown.size(); j++) if (shown.get(j).sameAs(prev)) { next = j; break; }
        setActive(next);
    }
    private NodeList<Element> options() { return list.querySelectorAll("[role=option]"); }
    private void setActive(int index) {
        var opts = options();
        active = opts.length == 0 ? -1 : Math.max(-1, Math.min(index, opts.length - 1));
        for (int i = 0; i < opts.length; i++) opts.item(i).setAttribute("aria-selected", String.valueOf(i == active));
        if (active < 0) input.removeAttribute("aria-activedescendant");
        else input.setAttribute("aria-activedescendant", opts.item(active).id);
    }
    private void select(Suggestion s) {
        if (s.onSelect != null) {             // 동작이 있는 항목: 입력값은 그대로 두고 닫은 뒤 그 동작만 실행
            open(false);
            s.onSelect.run();
            if (onSelect != null) onSelect.accept(s);
            return;
        }
        input.value = s.name;
        render(false);
        open(false);
        if (onSelect != null) onSelect.accept(s);
        if (onSearch != null) onSearch.accept(s.name);
    }
    private void onKey(Event e) {
        KeyboardEvent k = Js.uncheckedCast(e);
        JsPropertyMap<Object> m = Js.asPropertyMap(k);
        // IME 조합 중(한국어·일본어 등)의 키는 글자 확정용이다. Safari 는 조합을 끝내는 Enter 를 isComposing=false, keyCode 229 로 보낸다
        if (Js.isTruthy(m.get("isComposing")) || Js.coerceToDouble(m.get("keyCode")) == 229) return;
        switch (k.key) {
            case "ArrowDown": open(true); setActive(active + 1); e.preventDefault(); break;
            case "ArrowUp": setActive(active <= 0 ? options().length - 1 : active - 1); e.preventDefault(); break;
            case "Enter":
                if (active >= 0 && active < shown.size()) select(shown.get(active));
                else { open(false); if (onSearch != null) onSearch.accept(input.value); }
                e.preventDefault(); break;
            case "Escape":                       // 열려 있을 때만 소비한다 - 닫혀 있으면 감싼 다이얼로그가 Esc 를 받는다
                if (isOpen()) { open(false); e.preventDefault(); e.stopPropagation(); }
                break;
            default:
        }
    }

    private static void injectStyle() {
        HTMLStyleElement style = newStyle(STYLE_ID);
        if (style == null) return;
        String c = "." + CLASS;
        style.textContent =
            c + "{position:relative;display:block;--_bg:var(--md-search-container-color,var(--md-sys-color-surface-container-high,#ece6f0))}"
          // 검색마다 쌓임 맥락을 만들어 열린 검색(스크림 포함)만 형제 위로 올린다 - 다른 검색 바가 스크림 위에 떠 같이 활성화돼 보이지 않게.
          // 닫을 때는 스크림/목록이 사라지는 전환(250ms)이 끝난 뒤 내려간다.
          + c + ".docked{z-index:0;transition:z-index 0s linear 250ms}"
          + c + ".docked.open{z-index:var(--md-search-open-z-index,10);transition-delay:0s}"
          // 아이콘: 폰트 없이 그려지는 24px SVG(바꿔 끼운 요소도 같은 상자에 놓인다). full-screen 으로 열리면 앞 아이콘이 뒤로 가기로 바뀐다
          + c + " .icon{display:inline-flex;align-items:center;justify-content:center;flex:none;width:24px;height:24px}"
          + c + " .icon svg{width:24px;height:24px;fill:currentColor}"
          + c + " .leading .back-icon{display:none}"
          + c + ".fullscreen.open .leading .search-icon{display:none}"
          + c + ".fullscreen.open .leading .back-icon{display:inline-flex}"
          // Search bar: 56dp, full, surface container high, 앞 아이콘 on surface, 뒤 아이콘 on surface variant
          + c + " .bar{position:relative;z-index:2;box-sizing:border-box;height:56px;border-radius:var(--md-search-bar-container-shape,var(--md-sys-shape-corner-full,9999px));background:var(--_bg);display:flex;align-items:center;gap:4px;padding:0 4px;"
          + "margin-inline:var(--md-search-bar-margin,24px);transition:margin 200ms cubic-bezier(0.2,0,0,1)}"
          + c + ".docked .bar{transition:margin 200ms cubic-bezier(0.2,0,0,1),border-radius 200ms cubic-bezier(0.2,0,0,1)}" // 목록이 생기고 사라질 때 모서리를 부드럽게
          // M3 Expressive: 포커스하면 바가 넓어진다 — 여백 24dp → 12dp
          + c + ".docked.open .bar{margin-inline:var(--md-search-bar-focused-margin,12px)}"
          + c + ".fullscreen.open .bar{margin-inline:0}"
          // 목록도 바와 같은 여백에서 출발해 같은 곡선으로 넓어진다(열 때 display:none 에서 나타나므로 출발값은 @starting-style)
          + c + ".docked.open .view{inset-inline:var(--md-search-bar-focused-margin,12px);opacity:1;clip-path:inset(0)}"
          + "@starting-style{" + c + ".docked.open .view{inset-inline:var(--md-search-bar-margin,24px);opacity:0;clip-path:inset(0 0 100% 0)}}"
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
          + c + ".docked.open.empty .view{display:none;transition:none}" // 걸러진 제안이 없으면 빈 목록 띠를 남기지 않는다
          + c + " .option{display:flex;align-items:center;gap:16px;min-height:56px;padding:0 16px;cursor:pointer;font:400 16px/24px Roboto,system-ui,sans-serif;color:var(--md-sys-color-on-surface,#1d1b20)}"
          + c + " .option .icon{color:var(--md-sys-color-on-surface-variant,#49454f)}"
          // 항목: 이름(body-large) + 보조 문구(body-medium 14/20, on surface variant) - Compose ListItem 의 headline/supporting 구성
          + c + " .option .text{display:flex;flex-direction:column;min-width:0;padding:8px 0}"
          + c + " .option .headline," + c + " .option .supporting{overflow:hidden;text-overflow:ellipsis;white-space:nowrap}"
          + c + " .option .supporting{font:400 14px/20px Roboto,system-ui,sans-serif;color:var(--md-sys-color-on-surface-variant,#49454f)}"
          + c + " .option[aria-selected=true]," + c + " .option:hover{background:color-mix(in srgb,var(--md-sys-color-on-surface,#1d1b20) 8%,transparent)}"
          + c + ".divided.open .view{border-top:1px solid var(--md-sys-color-outline,#79747e)}" // SearchView DividerColor outline
          // Docked: 열리면 바 위 모서리 28dp(extra large) + 목록 아래 모서리 28dp, 본문은 스크림
          + c + " .scrim{display:none}"
          + c + ".docked .scrim{position:fixed;inset:0;z-index:1;background:var(--md-sys-color-scrim,#000);opacity:0;transition:opacity 200ms linear,display 200ms allow-discrete}"
          + c + ".docked.open .scrim{display:block;opacity:.32}"
          + "@starting-style{" + c + ".docked.open .scrim{opacity:0}}"
          + c + ".docked.open.empty .bar{border-radius:var(--md-search-view-docked-shape,var(--md-sys-shape-corner-extra-large,28px))}" // 목록이 비면 하단 모서리도 상단처럼 둥글게
          + c + ".docked.open:not(.empty) .bar{border-radius:var(--md-search-view-docked-shape,var(--md-sys-shape-corner-extra-large,28px)) var(--md-search-view-docked-shape,var(--md-sys-shape-corner-extra-large,28px)) 0 0}"
          + c + ".docked .view{position:absolute;z-index:2;inset-inline:var(--md-search-bar-margin,24px);opacity:0;clip-path:inset(0 0 100% 0);transition:inset-inline-start 200ms cubic-bezier(0.2,0,0,1),inset-inline-end 200ms cubic-bezier(0.2,0,0,1),opacity 150ms linear,clip-path 250ms cubic-bezier(0.2,0,0,1),display 250ms allow-discrete;top:56px;max-height:min(480px,70vh);border-radius:0 0 var(--md-search-view-docked-shape,var(--md-sys-shape-corner-extra-large,28px)) var(--md-search-view-docked-shape,var(--md-sys-shape-corner-extra-large,28px));padding-bottom:8px}"
          // Full-screen: 화면 전체, 모서리 없음, 헤더 72dp
          + c + ".fullscreen.open{position:fixed;inset:0;z-index:1000;display:flex;flex-direction:column;background:var(--_bg)}"
          + c + ".fullscreen.open .bar{height:72px;border-radius:var(--md-search-view-full-screen-shape,var(--md-sys-shape-corner-none,0px));flex:none}"
          + c + ".fullscreen.open .view{flex:1}"
          + c + ".fullscreen{transition:opacity 200ms linear}"
          + "@starting-style{" + c + ".fullscreen.open{opacity:0}}"
          // [M3 외] inline: 목록을 바 아래 흐름 안에 두고 스크림을 쓰지 않는다(다이얼로그 안 명령 팔레트)
          + c + ".inline .scrim{display:none!important}"
          + c + ".inline.open .view{position:static;clip-path:none;opacity:1;max-height:var(--md-search-inline-max-height,min(480px,70vh))}"
          + "@media (prefers-reduced-motion:reduce){" + c + "," + c + " *{transition:none!important}}";
        DomGlobal.document.head.appendChild(style);
    }
}
