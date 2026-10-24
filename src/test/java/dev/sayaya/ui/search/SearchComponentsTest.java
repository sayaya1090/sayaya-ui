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
    private static void type(HTMLInputElement input, String text) {
        input.value = text;
        input.dispatchEvent(new Event("input"));
    }

    public static void test() {
        printSectionHeader("M3 Search");
        printDescription("검색어로 제품 안을 찾아가는 검색입니다. 검색 바에 포커스하면 제안 목록이 열리고, 입력할수록 제안이 걸러집니다. docked 는 검색 바 아래로 목록이 열리고 스크림이 본문을 덮으며, full-screen 은 화면 전체로 펼쳐집니다. ↑/↓ 로 제안을 고르고 Enter 로 선택하며 Esc 로 닫습니다.");
        printSeparator();
        var section = section("Search");
        documented(section);
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
                assertEquals("se: docked 열림 바 위 모서리 28px", "28px", cs(bar).getPropertyValue("border-top-left-radius"));
                assertEquals("se: docked 열림 바 아래 모서리 0", "0px", cs(bar).getPropertyValue("border-bottom-left-radius"));
                assertEquals("se: docked 목록 아래 모서리 28px", "28px", cs(view).getPropertyValue("border-bottom-left-radius"));
                assertEquals("se: docked 목록 배경 surface-container-high", "rgb(236, 230, 240)", cs(view).getPropertyValue("background-color"));
                assertEquals("se: docked 스크림 0.32", "0.32", cs(q(el, ".scrim")).getPropertyValue("opacity"));
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

                var fel = full.element();
                full.input().focus();
                later(100, () -> {
                    var fbar = q(fel, ".bar");
                    assertEquals("se: full-screen 화면 고정", "fixed", cs(fel).getPropertyValue("position"));
                    assertEquals("se: full-screen 화면 전체 높이", (int) DomGlobal.window.innerHeight, (int) h(fel));
                    assertEquals("se: full-screen 헤더 72px", 72, (int) h(fbar));
                    assertEquals("se: full-screen 모서리 없음", "0px", cs(fbar).getPropertyValue("border-top-left-radius"));
                    assertEquals("se: full-screen 뒤로 가기 아이콘", "arrow_back", q(fel, ".leading .icon").textContent);
                    assertEquals("se: divided 구분선 1px", "1px", cs(q(fel, ".view")).getPropertyValue("border-top-width"));
                    q(fel, ".leading").click();
                    assertTrue("se: 뒤로 가기로 닫힘", !full.isOpen() && "search".equals(q(fel, ".leading .icon").textContent));
                });
            });
        });
    }

    /** 가이드라인: "In M3 Expressive, the search bar expands when focused. The margins change from 24dp to 12dp." */
    private static void documented(HTMLElement section) {
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
            later(400, () -> {
                assertEquals("se: 포커스하면 여백 12px", "12px", cs(bar).getPropertyValue("margin-left"));
                assertEquals("se: 포커스하면 바가 24px 넓어짐", 376, (int) Math.round(bar.getBoundingClientRect().width));
                assertEquals("se: 목록은 바와 같은 왼쪽 끝", (int) Math.round(bar.getBoundingClientRect().left), (int) Math.round(q(el, ".view").getBoundingClientRect().left));
                s.open(false);
            });
        });
    }
}
