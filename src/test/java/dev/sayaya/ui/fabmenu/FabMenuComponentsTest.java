package dev.sayaya.ui.fabmenu;

import elemental2.dom.*;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.FabMenuElementBuilder.*;
import static org.jboss.elemento.Elements.div;

/** M3 FAB menu: 토큰 수치(computed style) + 열기/닫기/포커스/키보드. 메시지 접두어 "fm: ". */
public class FabMenuComponentsTest {
    private static long w(Element e) { return Math.round(e.getBoundingClientRect().width); }
    private static long h(Element e) { return Math.round(e.getBoundingClientRect().height); }
    private static HTMLElement q(HTMLElement root, String sel) { return (HTMLElement) root.querySelector(sel); }

    public static void test() {
        printSectionHeader("M3 FAB menu");
        printDescription("FAB 에서 열리는 관련 동작 메뉴입니다. 열리면 FAB 이 닫기 버튼으로 바뀌고, 항목은 FAB 위로 쌓입니다. 모든 FAB 크기에 메뉴 크기는 하나이며 primary/secondary/tertiary 색 구성이 있습니다. Esc 나 항목 선택으로 닫히고 포커스는 FAB 으로 돌아갑니다.");
        printSeparator();
        var section = section("FAB menu");
        documented(section);
        int[] picked = {0};
        java.util.List<Boolean> toggles = new java.util.ArrayList<>();

        var builder = fabMenu().icon("add").ariaLabel("만들기")
                .item("edit", "새 메모", e -> picked[0]++)
                .item("photo_camera", "사진", e -> {})
                .item("mic", "음성 메모", e -> {})
                .onToggle(toggles::add);
        var menu = builder.element();
        demo(section, "📘 FAB menu", "닫혀 있을 때는 56dp FAB(모서리 16dp)입니다. 누르면 56dp 둥근 닫기 버튼으로 바뀌고, 높이 56dp·좌우 24dp 항목이 4dp 간격으로 나타납니다.",
            """
            fabMenu().icon("add").ariaLabel("만들기")
                .item("edit", "새 메모", e -> newNote())
                .item("photo_camera", "사진", e -> takePhoto())
                .item("mic", "음성 메모", e -> record())
                .element();
            """, div().style("height", "260px").style("display", "flex").style("align-items", "flex-end").add(menu).element());
        var secondary = fabMenu().color(Color.Secondary).icon("edit").ariaLabel("편집").item("text_fields", "텍스트", null);
        var tertiary = fabMenu().color(Color.Tertiary).icon("star").ariaLabel("즐겨찾기").item("bookmark", "북마크", null);
        demo(section, "📘 색 구성 - secondary / tertiary", "color(Color.Secondary|Tertiary)로 색 구성을 바꿉니다.",
            """
            fabMenu().color(Color.Secondary).icon("edit").ariaLabel("편집").item("text_fields", "텍스트", e -> {}).element();
            """, div().style("height", "140px").style("display", "flex").style("align-items", "flex-end").style("gap", "24px").add(secondary.element()).add(tertiary.element()).element());

        var toggle = q(menu, ".toggle");
        var items = q(menu, ".items");
        assertEquals("fm: 토글 aria-haspopup menu", "menu", toggle.getAttribute("aria-haspopup"));
        assertEquals("fm: 닫힘 aria-expanded false", "false", toggle.getAttribute("aria-expanded"));
        assertEquals("fm: aria-controls 연결", items.id, toggle.getAttribute("aria-controls"));
        assertEquals("fm: 토글 aria-label", "만들기", toggle.getAttribute("aria-label"));
        assertEquals("fm: 항목 role menuitem", 3, menu.querySelectorAll("[role=menuitem]").length);

        later(500, () -> {
            assertEquals("fm: FAB 56px", 56, (int) w(toggle));
            assertEquals("fm: FAB 높이 56px", 56, (int) h(toggle));
            assertEquals("fm: 닫힌 FAB 모서리 16px", "16px", cs(toggle).getPropertyValue("border-top-left-radius"));
            assertEquals("fm: 닫힌 FAB primary-container", "rgb(234, 221, 255)", cs(toggle).getPropertyValue("background-color"));
            assertEquals("fm: 닫힘 목록 숨김", "none", cs(items).getPropertyValue("display"));
            assertEquals("fm: 닫힌 아이콘", "add", q(toggle, ".icon").textContent);
            toggle.click();
            later(450, () -> {
                var first = (HTMLElement) items.firstElementChild;
                assertEquals("fm: 열림 aria-expanded true", "true", toggle.getAttribute("aria-expanded"));
                assertEquals("fm: 열림 아이콘 close", "close", q(toggle, ".icon").textContent);
                assertEquals("fm: 닫기 버튼 모서리 full", "9999px", cs(toggle).getPropertyValue("border-top-left-radius"));
                assertEquals("fm: 닫기 버튼 primary", "rgb(103, 80, 164)", cs(toggle).getPropertyValue("background-color"));
                assertEquals("fm: 닫기 버튼 56px", 56, (int) w(toggle));
                assertEquals("fm: 닫기 버튼 아이콘 20px", "20px", cs(q(toggle, ".icon")).getPropertyValue("font-size"));
                assertEquals("fm: 열림 목록 표시", "flex", cs(items).getPropertyValue("display"));
                assertEquals("fm: 닫기 버튼-목록 간격 8px", 8, (int) Math.round(toggle.getBoundingClientRect().top - items.lastElementChild.getBoundingClientRect().bottom));
                assertEquals("fm: 항목 간격 4px", "4px", cs(items).getPropertyValue("row-gap"));
                assertEquals("fm: 항목 높이 56px", 56, (int) h(first));
                assertEquals("fm: 항목 좌우 여백 24px", "24px", cs(first).getPropertyValue("padding-left"));
                assertEquals("fm: 항목 아이콘-라벨 8px", "8px", cs(first).getPropertyValue("column-gap"));
                assertEquals("fm: 항목 모서리 full", "9999px", cs(first).getPropertyValue("border-top-left-radius"));
                assertEquals("fm: 항목 primary-container", "rgb(234, 221, 255)", cs(first).getPropertyValue("background-color"));
                assertEquals("fm: 항목 아이콘 24px", "24px", cs(q(first, ".icon")).getPropertyValue("font-size"));
                assertTrue("fm: 항목 그림자", !"none".equals(cs(first).getPropertyValue("box-shadow")));
                assertTrue("fm: 열면 첫 항목 포커스", DomGlobal.document.activeElement == first);
                assertTrue("fm: onToggle(true)", toggles.size() == 1 && toggles.get(0));
                first.click();
                assertEquals("fm: 항목 선택 handler", 1, picked[0]);
                assertTrue("fm: 항목 선택 후 닫힘", !builder.isOpen() && "false".equals(toggle.getAttribute("aria-expanded")));
                assertTrue("fm: 닫히면 FAB 으로 포커스", DomGlobal.document.activeElement == toggle);
                toggle.click();
                KeyboardEventInit init = Js.uncheckedCast(JsPropertyMap.of("key", "Escape", "bubbles", true, "cancelable", true));
                DomGlobal.document.activeElement.dispatchEvent(new KeyboardEvent("keydown", init));
                assertTrue("fm: Esc 로 닫힘", !builder.isOpen() && DomGlobal.document.activeElement == toggle);
                assertEquals("fm: 닫힌 아이콘 복원", "add", q(toggle, ".icon").textContent);
                secondary.open(true); tertiary.open(true);
                later(450, () -> {
                    assertEquals("fm: secondary 항목 secondary-container", "rgb(232, 222, 248)", cs(q(secondary.element(), ".item")).getPropertyValue("background-color"));
                    assertEquals("fm: secondary 닫기 버튼 secondary", "rgb(98, 91, 113)", cs(q(secondary.element(), ".toggle")).getPropertyValue("background-color"));
                    assertEquals("fm: tertiary 항목 tertiary-container", "rgb(255, 216, 228)", cs(q(tertiary.element(), ".item")).getPropertyValue("background-color"));
                    assertTrue("fm: 한글 라벨 한 줄(줄바꿈 없음)", h(q(secondary.element(), ".label")) <= 24 && h(q(tertiary.element(), ".label")) <= 24);
                    secondary.open(false); tertiary.open(false);
                });
            });
        });
    }

    /** 가이드라인: "FAB menus can have 2-6 items", "Allow the menu items to scroll behind the close button" */
    private static void documented(HTMLElement section) {
        var b = fabMenu().icon("add").ariaLabel("Create").style("--md-fab-menu-max-height", "150px");
        String[][] items = {{"edit", "Note"}, {"photo_camera", "Photo"}, {"mic", "Voice"}, {"draw", "Drawing"}, {"checklist", "List"}, {"link", "Link"}};
        for (String[] it : items) b.item(it[0], it[1], null);
        var el = b.element();
        demo(section, "📘 FAB menu — 6 items, short screen", "가이드라인: \"FAB menus can have 2-6 items\", \"Allow the menu items to scroll behind the close button\", \"Don't obstruct the close button in short screens like horizontal orientation\". 화면이 낮으면 목록만 스크롤되고 닫기 버튼은 그대로 보입니다(--md-fab-menu-max-height).",
            """
            fabMenu().icon("add").ariaLabel("Create")
                .item("edit", "Note", e -> {}).item("photo_camera", "Photo", e -> {})
                // ... 최대 6개
                .element();
            """, div().style("height", "240px").style("display", "flex").style("align-items", "flex-end").add(el).element());
        var plain = fabMenu().icon("add").ariaLabel("Create").item("edit", "Note", null).item("photo_camera", "Photo", null).item("mic", "Voice", null);
        var pel = plain.element();
        demo(section, "📘 FAB menu — 떠 있는 메뉴(항목이 적을 때)", "메뉴는 FAB 위에 떠서(absolute) 열립니다. 열어도 FAB 위치와 페이지 배치는 그대로이고, 항목이 넘치지 않으면 스크롤도 생기지 않습니다. 항목 그림자도 잘리지 않습니다.",
            """
            fabMenu().icon("add").ariaLabel("Create")
                .item("edit", "Note", e -> {}).item("photo_camera", "Photo", e -> {}).item("mic", "Voice", e -> {})
                .element();
            """, div().style("height", "260px").style("display", "flex").style("align-items", "flex-end").add(pel).element());
        var ptoggle = q(pel, ".toggle");
        double[] before = {0, 0, 0};
        later(300, () -> {
            before[0] = ptoggle.getBoundingClientRect().top - pel.getBoundingClientRect().top; before[1] = pel.getBoundingClientRect().height;
            before[2] = DomGlobal.document.documentElement.scrollHeight;
            plain.open(true);
            later(100, () -> {
                assertTrue("fm: 열어도 FAB 위치 그대로(떠 있는 메뉴)", Math.abs(ptoggle.getBoundingClientRect().top - pel.getBoundingClientRect().top - before[0]) <= 0.5);
                assertTrue("fm: 열어도 자리 크기 그대로", Math.abs(pel.getBoundingClientRect().height - before[1]) <= 0.5);
                assertTrue("fm: 열어도 문서 높이 그대로(숨은 스크롤 없음)", DomGlobal.document.documentElement.scrollHeight <= before[2] + 0.5);
                assertTrue("fm: 목록은 FAB 위에 뜸", q(pel, ".items").lastElementChild.getBoundingClientRect().bottom <= ptoggle.getBoundingClientRect().top - 7.5);
            });
        });
        b.open(true);
        later(700, () -> {
            var pl = q(plain.element(), ".items");
            var lastItem = (HTMLElement) pl.lastElementChild;
            var lr = pl.getBoundingClientRect(); var ir = lastItem.getBoundingClientRect();
            assertTrue("fm: 넘치지 않는 메뉴는 스크롤 없음", pl.scrollHeight <= pl.clientHeight && pl.scrollWidth <= pl.clientWidth);
            assertTrue("fm: 마지막 항목 그림자가 아래에서 잘리지 않음(여유 15px 이상)", lr.bottom - ir.bottom >= 15);
            assertTrue("fm: 항목 그림자가 옆에서 잘리지 않음(여유 11px 이상)", lr.right - ir.right >= 11 && ir.left - lr.left >= 11);
            assertEquals("fm: 스크롤바 숨김", "none", cs(q(el, ".items")).getPropertyValue("scrollbar-width"));
            plain.open(false);
            var list = q(el, ".items"); var close = q(el, ".toggle");
            assertEquals("fm: 6개 항목", 6, el.querySelectorAll(".item").length);
            assertEquals("fm: 목록 최대 높이", 150, (int) Math.round(list.getBoundingClientRect().height));
            assertTrue("fm: 넘치면 목록만 스크롤", "auto".equals(cs(list).getPropertyValue("overflow-y")) && list.scrollHeight > list.clientHeight);
            close.scrollIntoView(); // elementFromPoint 는 화면 안 좌표만 본다
            var hit = DomGlobal.document.elementFromPoint(close.getBoundingClientRect().left + close.getBoundingClientRect().width / 2, close.getBoundingClientRect().top + 2);
            assertTrue("fm: 닫기 버튼은 가려지지 않음", hit != null && (hit == close || close.contains(hit)) && h(close) == 56);
            b.open(false);
        });
    }
}
