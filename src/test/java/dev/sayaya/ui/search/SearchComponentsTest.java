package dev.sayaya.ui.search;

import elemental2.dom.*;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

import java.util.ArrayList;
import java.util.List;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.ButtonElementBuilder.button;
import static dev.sayaya.ui.elements.SearchElementBuilder.*;
import static org.jboss.elemento.Elements.div;

/** M3 search(bar + docked/full-screen view): 토큰 수치(computed style) + combobox 동작. 메시지 접두어 "se: ". */
public class SearchComponentsTest {
    private static long h(Element e) { return Math.round(e.getBoundingClientRect().height); }
    private static HTMLElement q(Element root, String sel) { return (HTMLElement) root.querySelector(sel); }
    private static void key(Element target, String key) {
        JsPropertyMap<Object> m = JsPropertyMap.of();
        m.set("key", key); m.set("bubbles", true); m.set("cancelable", true);
        target.dispatchEvent(new KeyboardEvent("keydown", Js.<KeyboardEventInit>uncheckedCast(m)));
    }
    /** keydown 을 보내고, 핸들러가 기본 동작을 막지 않았으면 true(dispatchEvent 반환값) */
    private static boolean keyEv(Element target, String key, boolean composing) {
        JsPropertyMap<Object> m = JsPropertyMap.of();
        m.set("key", key); m.set("bubbles", true); m.set("cancelable", true); m.set("isComposing", composing);
        return target.dispatchEvent(new KeyboardEvent("keydown", Js.<KeyboardEventInit>uncheckedCast(m)));
    }
    /** .leading 안에서 지금 보이는 아이콘의 data-icon */
    private static String visibleIcon(Element root) {
        var boxes = root.querySelectorAll(".leading .icon");
        for (int i = 0; i < boxes.length; i++) {
            var b = boxes.item(i);
            if (!"none".equals(cs(b).getPropertyValue("display")) && b.firstElementChild != null) return b.firstElementChild.getAttribute("data-icon");
        }
        return null;
    }
    private static void type(HTMLInputElement input, String text) {
        input.value = text;
        input.dispatchEvent(new Event("input"));
    }

    public static void test() {
        printSectionHeader("M3 Search");
        printDescription("검색어로 제품 안을 찾아가는 검색입니다. 검색 바에 포커스하면 제안 목록이 열리고, 입력할수록 제안이 걸러집니다. docked 는 검색 바 아래로 목록이 열리고 스크림이 본문을 덮으며, full-screen 은 화면 전체로 펼쳐집니다. ↑/↓ 로 제안을 고르고 Enter 로 선택하며 Esc 로 닫습니다.");
        printSeparator();
        var section = section("Search");
        var docEl = documented(section);
        List<String> searched = new ArrayList<>();
        List<String> inputs = new ArrayList<>();

        var docked = search().docked().placeholder("메일 검색").ariaLabel("메일 검색")
                .suggestions("회의록", "휴가 신청", "주간 보고", "프로젝트 회의")
                .trailing(button().icon("mic").ariaLabel("음성 검색"))
                .onSearch(searched::add).onInput(inputs::add);
        var el = docked.element();
        el.style.setProperty("width", "360px");
        demo(section, "📘 Search bar + docked view", "높이 56dp, 모서리 full, surface container high 색의 검색 바입니다. 포커스하면 아래로 제안 목록(모서리 28dp)이 열리고 스크림이 본문을 덮습니다.",
            """
            search().docked().placeholder("메일 검색")
                .suggestions("회의록", "휴가 신청", "주간 보고")
                .trailing(button().icon("mic").ariaLabel("음성 검색"))
                .onSearch(q -> load(q))
                .element();
            """, el);

        var full = search().fullScreen().placeholder("연락처 검색").suggestions("김민수", "이서연", "박지훈").divided(true);
        demo(section, "📘 Full-screen view (divided)", "모바일에서는 화면 전체로 펼쳐지고 헤더가 72dp 로 커집니다. 앞 아이콘이 뒤로 가기 버튼으로 바뀌어 닫을 수 있습니다. divided(true)는 검색 바와 목록 사이에 구분선을 둡니다.",
            """
            search().fullScreen().placeholder("연락처 검색")
                .suggestions("김민수", "이서연", "박지훈")
                .divided(true)
                .element();
            """, full.element());

        var input = docked.input();
        var bar = q(el, ".bar");
        assertEquals("se: role search", "search", el.getAttribute("role"));
        assertEquals("se: 입력창 role combobox", "combobox", input.getAttribute("role"));
        assertEquals("se: aria-autocomplete list", "list", input.getAttribute("aria-autocomplete"));
        assertEquals("se: aria-controls 연결", q(el, "[role=listbox]").id, input.getAttribute("aria-controls"));
        assertEquals("se: 닫힘 aria-expanded false", "false", input.getAttribute("aria-expanded"));
        assertEquals("se: placeholder", "메일 검색", input.placeholder);
        palette(); // 2.5.0.7 명령 팔레트 검사(동기, 아래 지연 검사보다 먼저 끝난다)

        later(500, () -> {
            assertEquals("se: 검색 바 높이 56px", 56, (int) h(bar));
            assertEquals("se: 검색 바 모서리 full", "9999px", cs(bar).getPropertyValue("border-top-left-radius"));
            assertEquals("se: 검색 바 surface-container-high", "rgb(236, 230, 240)", cs(bar).getPropertyValue("background-color"));
            assertEquals("se: 앞 아이콘 on-surface", "rgb(29, 27, 32)", cs(q(el, ".leading")).getPropertyValue("color"));
            assertEquals("se: 뒤 아이콘 on-surface-variant", "rgb(73, 69, 79)", cs(q(el, ".trailing")).getPropertyValue("color"));
            assertEquals("se: 닫힘 목록 숨김", "none", cs(q(el, ".view")).getPropertyValue("display"));
            assertTrue("se: 빈 입력이면 지우기 버튼 숨김", q(el, ".clear").hasAttribute("hidden"));
            input.focus();
            later(100, () -> {
                var view = q(el, ".view");
                assertEquals("se: 포커스하면 열림", "true", input.getAttribute("aria-expanded"));
                assertEquals("se: 열림 목록 표시", "block", cs(view).getPropertyValue("display"));
                assertEquals("se: 제안 전체 표시", 4, el.querySelectorAll("[role=option]").length);
                bar.style.setProperty("transition", "none"); // 모서리 전환 도중이 아닌 목표 모양을 잰다
                assertEquals("se: docked 열림 바 위 모서리 28px", "28px", cs(bar).getPropertyValue("border-top-left-radius"));
                assertEquals("se: docked 열림 바 아래 모서리 0", "0px", cs(bar).getPropertyValue("border-bottom-left-radius"));
                bar.style.removeProperty("transition");
                assertEquals("se: docked 목록 아래 모서리 28px", "28px", cs(view).getPropertyValue("border-bottom-left-radius"));
                assertEquals("se: docked 목록 배경 surface-container-high", "rgb(236, 230, 240)", cs(view).getPropertyValue("background-color"));
                var dbr = q(docEl, ".bar").getBoundingClientRect();
                var hit = DomGlobal.document.elementFromPoint(dbr.left + dbr.width / 2, dbr.top + dbr.height / 2);
                assertTrue("se: 하나를 열면 다른 검색 바는 스크림 아래", hit != null && !docEl.contains(hit) && el.contains(hit));
                type(input, "회의");
                assertEquals("se: 입력하면 제안 걸러짐", 2, el.querySelectorAll("[role=option]").length);
                assertTrue("se: onInput 호출", inputs.contains("회의"));
                assertTrue("se: 입력 있으면 지우기 버튼", !q(el, ".clear").hasAttribute("hidden"));
                key(input, "ArrowDown");
                var first = q(el, "[role=option]");
                assertEquals("se: ↓ 로 첫 제안 활성", first.id, input.getAttribute("aria-activedescendant"));
                assertEquals("se: 활성 제안 aria-selected", "true", first.getAttribute("aria-selected"));
                key(input, "ArrowDown");
                key(input, "Enter");
                assertEquals("se: Enter 로 제안 선택", "프로젝트 회의", input.value);
                assertTrue("se: 선택하면 onSearch", searched.contains("프로젝트 회의"));
                assertEquals("se: 선택 후 닫힘", "false", input.getAttribute("aria-expanded"));
                q(el, ".clear").click();
                assertEquals("se: 지우기 버튼으로 비움", "", input.value);
                docked.open(true);
                type(input, "새 검색어");
                key(input, "Enter");
                assertTrue("se: 제안 없이 Enter 는 입력값 검색", searched.contains("새 검색어") && !docked.isOpen());
                docked.open(true);
                key(input, "Escape");
                assertTrue("se: Esc 로 닫힘", !docked.isOpen());
                docked.open(true);
                q(el, ".scrim").click();
                assertTrue("se: 스크림 클릭으로 닫힘", !docked.isOpen());
                docked.open(true);
                type(input, "없는 검색어");
                assertEquals("se: 걸러진 제안이 없으면 빈 목록 숨김", "none", cs(q(el, ".view")).getPropertyValue("display"));
                assertTrue("se: 바 모서리 전환", cs(bar).getPropertyValue("transition-property").contains("border-radius"));
                bar.style.setProperty("transition", "none");
                assertEquals("se: 빈 목록이면 바 하단 모서리도 둥글게 28px", "28px", cs(bar).getPropertyValue("border-bottom-left-radius"));
                assertEquals("se: 빈 목록이면 바 상단 모서리 28px 유지", "28px", cs(bar).getPropertyValue("border-top-left-radius"));
                bar.style.removeProperty("transition");
                type(input, "");
                docked.open(false);
                assertEquals("se: 테스트 뒤 데모 입력 비움(제안 4개 복원)", 4, el.querySelectorAll("[role=option]").length);

                var fel = full.element();
                full.input().focus();
                later(100, () -> {
                    var fbar = q(fel, ".bar");
                    assertEquals("se: full-screen 화면 고정", "fixed", cs(fel).getPropertyValue("position"));
                    assertEquals("se: full-screen 화면 전체 높이", (int) DomGlobal.window.innerHeight, (int) h(fel));
                    assertEquals("se: full-screen 헤더 72px", 72, (int) h(fbar));
                    assertEquals("se: full-screen 모서리 없음", "0px", cs(fbar).getPropertyValue("border-top-left-radius"));
                    assertEquals("se: full-screen 뒤로 가기 아이콘", "arrow_back", visibleIcon(fel));
                    assertEquals("se: divided 구분선 1px", "1px", cs(q(fel, ".view")).getPropertyValue("border-top-width"));
                    q(fel, ".leading").click();
                    assertTrue("se: 뒤로 가기로 닫힘", !full.isOpen() && "search".equals(visibleIcon(fel)));
                });
            });
        });
    }

    /** 2.5.0.7: 명령 팔레트 — 풍부한 항목·행별 동작, 목록 교체(하이라이트 유지·늦은 결과 버림), IME 조합, SVG 아이콘, 다이얼로그 안(inline·focus·reset·Esc). 모두 동기 검사 */
    private static void palette() {
        var section = section("Search: 명령 팔레트(2.5.0.7)");
        var demoPalette = search().docked().inline(true).filter(false).placeholder("명령 검색")
                .suggestions(suggestion("새 세션", "세션 · ⌘N", div().text("S").element(), () -> {}),
                             suggestion("설정 열기", "앱", div().text("A").element(), () -> {}));
        demoPalette.element().style.setProperty("width", "360px");
        demo(section, "📘 Command palette (inline, rich items)", "이름·보조 문구·앞 요소·행별 동작을 갖는 항목입니다. inline(true)는 목록을 바 아래 흐름 안에 두고 스크림을 쓰지 않아 다이얼로그 안에서 씁니다. filter(false)면 앱이 setSuggestions 로 정한 목록을 그대로 보여 줍니다.",
            """
            search().docked().inline(true).filter(false).placeholder("명령 검색")
                .suggestions(suggestion("새 세션", "세션 · ⌘N", sIcon, () -> newSession()).key("new-session"))
                .element();
            // 다이얼로그를 연 뒤 palette.focus(), 닫을 때 palette.reset()
            """, demoPalette.element());

        List<String> ran = new ArrayList<>(), searched = new ArrayList<>(), selected = new ArrayList<>();
        var letter = div().text("F").element();
        var a = suggestion("열기", "파일", letter, () -> ran.add("open-file"));
        var b = suggestion("열기", "세션", (Element) null, () -> ran.add("open-session"));
        var p = search().docked().inline(true).filter(false).placeholder("명령 검색")
                .suggestions(a, b).onSearch(searched::add).onSelect(s -> selected.add(s.supportingText()));
        var pel = p.element();
        pel.style.setProperty("width", "360px");
        var dialog = (HTMLDialogElement) DomGlobal.document.createElement("dialog");
        dialog.appendChild(pel);
        section.appendChild(dialog);
        dialog.show();
        var input = p.input();
        var opts = pel.querySelectorAll("[role=option]");
        var row0 = opts.item(0);
        assertTrue("se: 항목 이름·보조 문구·앞 요소", opts.length == 2 && "열기".equals(row0.querySelector(".headline").textContent)
                && "파일".equals(row0.querySelector(".supporting").textContent) && row0.querySelector(".lead").firstElementChild == letter
                && opts.item(1).querySelector(".lead") == null);
        // 5) focus(): 입력 포커스 + 열림
        p.focus();
        assertTrue("se: focus() 로 입력 포커스·열림", DomGlobal.document.activeElement == input && p.isOpen());
        assertEquals("se: inline 목록은 흐름 안(static)", "static", cs(q(pel, ".view")).getPropertyValue("position"));
        assertEquals("se: inline 은 스크림 없음", "none", cs(q(pel, ".scrim")).getPropertyValue("display"));
        // 1) 같은 이름 둘 중 두 번째를 클릭 -> 그 행의 동작만
        ((HTMLElement) opts.item(1)).click();
        assertTrue("se: 같은 이름 항목도 행별 동작 실행", ran.size() == 1 && "open-session".equals(ran.get(0)) && selected.size() == 1 && "세션".equals(selected.get(0)));
        assertTrue("se: 동작 항목은 입력값·onSearch 를 건드리지 않고 닫음", "".equals(input.value) && searched.isEmpty() && !p.isOpen());
        keyEv(input, "ArrowDown", false);
        keyEv(input, "Enter", false);
        assertTrue("se: ↓·Enter 로 동작 항목 실행", ran.size() == 2 && "open-file".equals(ran.get(1)) && !p.isOpen());
        // 3) IME 조합 중 키는 무시
        keyEv(input, "ArrowDown", false);
        String act = input.getAttribute("aria-activedescendant");
        boolean notPrevented = keyEv(input, "Enter", true);
        assertTrue("se: 조합 중 Enter 는 선택하지 않음", ran.size() == 2 && p.isOpen() && notPrevented && act != null && act.equals(input.getAttribute("aria-activedescendant")));
        keyEv(input, "ArrowDown", true);
        assertEquals("se: 조합 중 ↓ 는 하이라이트를 옮기지 않음", act, input.getAttribute("aria-activedescendant"));
        keyEv(input, "Escape", true);
        assertTrue("se: 조합 중 Esc 는 닫지 않음", p.isOpen());
        // 2) 목록 교체: 같은 객체가 남으면 하이라이트 유지(a 는 0번 -> 새 목록 1번)
        var c = suggestion("새 세션", "세션", (Element) null, () -> ran.add("new"));
        p.setSuggestions(List.of(c, a, b));
        var o2 = pel.querySelectorAll("[role=option]");
        assertTrue("se: 목록 교체 뒤 같은 항목에 하이라이트 유지", o2.length == 3 && o2.item(1).id.equals(input.getAttribute("aria-activedescendant"))
                && "true".equals(o2.item(1).getAttribute("aria-selected")) && "false".equals(o2.item(0).getAttribute("aria-selected")));
        var x = suggestion("X", "키 1", (Element) null, () -> {}).key("k1");
        var y = suggestion("Y", "키 2", (Element) null, () -> {}).key("k2");
        p.setSuggestions(x, y);
        assertTrue("se: 사라진 항목이면 하이라이트 해제(aria-activedescendant 없음)", !input.hasAttribute("aria-activedescendant")
                && pel.querySelectorAll("[aria-selected=true]").length == 0);
        keyEv(input, "ArrowDown", false); keyEv(input, "ArrowDown", false);
        p.setSuggestions(suggestion("Y", "키 2(새 객체)", (Element) null, () -> {}).key("k2"), suggestion("X", "키 1(새 객체)", (Element) null, () -> {}).key("k1"));
        var o3 = pel.querySelectorAll("[role=option]");
        String ad = input.getAttribute("aria-activedescendant");
        assertTrue("se: key 가 같으면 새 객체로 바꿔도 하이라이트 유지", ad != null && ad.equals(o3.item(0).id) && DomGlobal.document.getElementById(ad) == o3.item(0)
                && "키 2(새 객체)".equals(o3.item(0).querySelector(".supporting").textContent));
        type(input, "ab");
        var z = suggestion("Z", null, (Element) null, () -> {});
        p.setSuggestions("a", List.of(z));
        assertEquals("se: 늦게 온 다른 질의 결과는 버림", 2, pel.querySelectorAll("[role=option]").length);
        p.setSuggestions("ab", List.of(z));
        assertTrue("se: 현재 질의 결과는 그대로 반영(filter false)", pel.querySelectorAll("[role=option]").length == 1 && "Z".equals(q(pel, ".headline").textContent));
        p.clearSuggestions();
        assertTrue("se: clearSuggestions 로 비움", pel.querySelectorAll("[role=option]").length == 0 && pel.classList.contains("empty") && !input.hasAttribute("aria-activedescendant"));
        // 5) Esc: 열려 있으면 소비, 닫혀 있으면 다이얼로그로 넘김 / reset()
        p.setSuggestions(a, b);
        p.open(true);
        boolean openEsc = keyEv(input, "Escape", false);
        assertTrue("se: 열린 상태 Esc 는 소비하고 닫음", !openEsc && !p.isOpen());
        assertTrue("se: 닫힌 상태 Esc 는 기본 동작 유지(다이얼로그가 받음)", keyEv(input, "Escape", false));
        type(input, "xyz");
        p.open(true);
        p.reset();
        assertTrue("se: reset() 으로 값 비우고 닫음", "".equals(p.value()) && !p.isOpen() && "false".equals(input.getAttribute("aria-expanded")));
        // 4) 아이콘: 폰트 없는 SVG 기본값, 교체
        var hist = search().docked().suggestions("기록");
        var hel = hist.element();
        section.appendChild(hel);
        assertTrue("se: 기본 아이콘은 SVG(search·close·arrow_back·history)",
                "search".equals(pel.querySelector(".leading .search-icon svg").getAttribute("data-icon")) && "arrow_back".equals(pel.querySelector(".leading .back-icon svg").getAttribute("data-icon"))
                && "close".equals(pel.querySelector(".clear svg").getAttribute("data-icon")) && "history".equals(hel.querySelector("[role=option] .lead svg").getAttribute("data-icon")));
        assertTrue("se: 아이콘 글자 없음(폰트 불필요)", q(pel, ".leading").textContent.trim().isEmpty() && q(pel, ".clear").textContent.trim().isEmpty()
                && "24".equals(String.valueOf(Math.round(pel.querySelector(".leading .search-icon svg").getBoundingClientRect().width))));
        var mySearch = div().text("⌕").element();
        var myClear = div().text("×").element();
        var custom = search().docked().searchIcon(mySearch).clearIcon(myClear).historyIcon(() -> div().css("my-hist").text("•").element()).suggestions("a");
        var cel = custom.element();
        section.appendChild(cel);
        assertTrue("se: 아이콘 교체(searchIcon·clearIcon·historyIcon)", q(cel, ".leading .search-icon").firstElementChild == mySearch
                && q(cel, ".clear .icon").firstElementChild == myClear && q(cel, "[role=option] .lead .my-hist") != null);
        dialog.close();
        hel.remove(); cel.remove();
    }

    /** 가이드라인: "In M3 Expressive, the search bar expands when focused. The margins change from 24dp to 12dp." */
    private static HTMLElement documented(HTMLElement section) {
        var s = search().docked().placeholder("Search messages").suggestions("Weekly report", "Meeting notes");
        var el = s.element();
        el.style.setProperty("width", "400px");
        var custom = search().docked().style("--md-search-bar-margin", "0px").element();
        demo(section, "📘 Search bar expands when focused", "가이드라인: \"In M3 Expressive, the search bar expands when focused. The margins change from 24dp to 12dp.\" 여백은 --md-search-bar-margin / --md-search-bar-focused-margin 으로 바꿉니다.",
            """
            search().docked().placeholder("Search messages")
                .suggestions("Weekly report", "Meeting notes")
                .element();
            """, el);
        section.appendChild(div().style("position", "absolute").style("left", "-9999px").style("width", "300px").add(custom).element());
        later(700, () -> {
            var bar = q(el, ".bar");
            assertEquals("se: 닫힌 바 여백 24px", "24px", cs(bar).getPropertyValue("margin-left"));
            long closed = Math.round(bar.getBoundingClientRect().width);
            assertEquals("se: 닫힌 바 너비(400-48)", 352, (int) closed);
            assertEquals("se: 여백 변수로 변경", "0px", cs(q(custom, ".bar")).getPropertyValue("margin-left"));
            s.open(true);
            later(30, () -> { // 여는 도중(전환 중)에도 목록이 바와 같은 폭이어야 한다
                var br = bar.getBoundingClientRect(); var vr = q(el, ".view").getBoundingClientRect();
                assertTrue("se: 여는 도중 목록 페이드 인(opacity < 1)", Double.parseDouble(cs(q(el, ".view")).getPropertyValue("opacity")) < 0.99);
                assertTrue("se: 여는 도중 스크림 페이드 인(opacity < 0.32)", Double.parseDouble(cs(q(el, ".scrim")).getPropertyValue("opacity")) < 0.31);
                assertTrue("se: 여는 도중 목록 왼쪽 끝 = 바 왼쪽 끝", Math.abs(br.left - vr.left) <= 1);
                assertTrue("se: 여는 도중 목록 오른쪽 끝 = 바 오른쪽 끝", Math.abs(br.right - vr.right) <= 1);
            });
            later(400, () -> {
                assertEquals("se: 포커스하면 여백 12px", "12px", cs(bar).getPropertyValue("margin-left"));
                assertEquals("se: 포커스하면 바가 24px 넓어짐", 376, (int) Math.round(bar.getBoundingClientRect().width));
                assertEquals("se: 목록은 바와 같은 왼쪽 끝", (int) Math.round(bar.getBoundingClientRect().left), (int) Math.round(q(el, ".view").getBoundingClientRect().left));
                assertEquals("se: docked 스크림 0.32", "0.32", cs(q(el, ".scrim")).getPropertyValue("opacity"));
                assertEquals("se: 열린 검색은 형제 위로(z-index)", "10", cs(el).getPropertyValue("z-index"));
                s.open(false);
                later(40, () -> {
                    var v = q(el, ".view");
                    assertTrue("se: 닫는 도중 목록이 바로 사라지지 않음(전환)", "block".equals(cs(v).getPropertyValue("display")) && Double.parseDouble(cs(v).getPropertyValue("opacity")) < 0.99);
                    later(500, () -> {
                        assertEquals("se: 닫힘 전환 뒤 목록 숨김", "none", cs(q(el, ".view")).getPropertyValue("display"));
                        assertEquals("se: 닫힘 전환 뒤 z-index 복귀", "0", cs(el).getPropertyValue("z-index"));
                    });
                });
            });
        });
        return el;
    }
}
