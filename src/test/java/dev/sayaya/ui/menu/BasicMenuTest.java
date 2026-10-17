package dev.sayaya.ui.menu;

import dev.sayaya.ui.elements.MenuElementBuilder;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.ButtonElementBuilder.button;
import static dev.sayaya.ui.elements.IconElementBuilder.icon;
import static dev.sayaya.ui.elements.MenuElementBuilder.menu;
import static org.jboss.elemento.Elements.*;

public class BasicMenuTest {
    public static void test() {
        printSectionHeader("1. 기본 Menu (Basic Menu)");
        printDescription("Material Design Menu는 사용자에게 선택 가능한 옵션 목록을 제공하는 컴포넌트입니다.");
        printSeparator();

        var basicSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(basicSection);

        basicSection.appendChild(h(3).text("Basic Menu").element());

        // Simple Menu
        var simpleExample = addExampleCode(basicSection,
            "📘 Simple Menu (간단한 메뉴)",
            "기본적인 메뉴 항목들을 표시합니다.",
            """
            var anchor = button().filled()
                .text("메뉴 열기")
                .id("anchor1")
                .element();
            var menuElem = menu()
                .anchor("anchor1")
                .positioning(MenuElementBuilder.Position.Fixed)
                .item()
                    .headline("사과")
                .done()
                .item()
                    .headline("바나나")
                .done()
                .item()
                    .headline("오렌지")
                .done()
                .element();
            anchor.addEventListener("click", e -> menuElem.open = !menuElem.open);
            """);
        var anchor1 = button().filled()
                .text("메뉴 열기")
                .id("anchor1")
                .element();
        var simpleMenu = menu()
                .anchor("anchor1")
                .positioning(MenuElementBuilder.Position.Fixed)
                .item()
                    .headline("사과")
                .done()
                .item()
                    .headline("바나나")
                .done()
                .item()
                    .headline("오렌지")
                .done()
                .element();
        anchor1.addEventListener("click", e -> simpleMenu.open = !simpleMenu.open);
        var simpleContainer = div().add(anchor1).add(simpleMenu).element();
        simpleExample.addInteractiveDemo(simpleContainer, false);
        simpleMenu.getUpdateComplete().then(result -> {
            assertEquals("menu: 태그명은 MD-MENU", "MD-MENU", simpleMenu.tagName);
            assertEquals("menu: items가 3개", 3, simpleMenu.items.length);
            return null;
        });

        // Menu with Supporting Text
        var supportingTextExample = addExampleCode(basicSection,
            "📘 Menu with Supporting Text (보조 텍스트가 있는 메뉴)",
            "각 항목에 보조 설명 텍스트를 추가할 수 있습니다.",
            """
            var anchor = button().filled()
                .text("과일 선택")
                .id("anchor2")
                .element();
            var menuElem = menu()
                .anchor("anchor2")
                .positioning(MenuElementBuilder.Position.Fixed)
                .item()
                    .headline("사과")
                    .supportingText("신선하고 달콤한 과일")
                .done()
                .item()
                    .headline("바나나")
                    .supportingText("에너지가 풍부한 과일")
                .done()
                .element();
            anchor.addEventListener("click", e -> menuElem.open = !menuElem.open);
            """);
        var anchor2 = button().filled()
                .text("과일 선택")
                .id("anchor2")
                .element();
        var supportingMenu = menu()
                .anchor("anchor2")
                .positioning(MenuElementBuilder.Position.Fixed)
                .item()
                    .headline("사과")
                    .supportingText("신선하고 달콤한 과일")
                .done()
                .item()
                    .headline("바나나")
                    .supportingText("에너지가 풍부한 과일")
                .done()
                .element();
        anchor2.addEventListener("click", e -> supportingMenu.open = !supportingMenu.open);
        var supportingContainer = div().add(anchor2).add(supportingMenu).element();
        supportingTextExample.addInteractiveDemo(supportingContainer, false);
        assertEquals("Supporting Text (보조 텍스트): 항목마다 supporting-text 슬롯", 2, supportingMenu.querySelectorAll("[slot='supporting-text']").length);

        // Menu with Icons
        var iconsExample = addExampleCode(basicSection,
            "📘 Menu with Icons (아이콘이 있는 메뉴)",
            "항목의 시작 위치에 아이콘을 추가할 수 있습니다.",
            """
            var anchor = button().filled()
                .text("액션 선택")
                .id("anchor3")
                .element();
            var menuElem = menu()
                .anchor("anchor3")
                .positioning(MenuElementBuilder.Position.Fixed)
                .item()
                    .start(icon("content_cut"))
                    .headline("잘라내기")
                .done()
                .item()
                    .start(icon("content_copy"))
                    .headline("복사")
                .done()
                .item()
                    .start(icon("content_paste"))
                    .headline("붙여넣기")
                .done()
                .element();
            anchor.addEventListener("click", e -> menuElem.open = !menuElem.open);
            """);
        var anchor3 = button().filled()
                .text("액션 선택")
                .id("anchor3")
                .element();
        var iconsMenu = menu()
                .anchor("anchor3")
                .positioning(MenuElementBuilder.Position.Fixed)
                .item()
                    .start(icon("content_cut"))
                    .headline("잘라내기")
                .done()
                .item()
                    .start(icon("content_copy"))
                    .headline("복사")
                .done()
                .item()
                    .start(icon("content_paste"))
                    .headline("붙여넣기")
                .done()
                .element();
        anchor3.addEventListener("click", e -> iconsMenu.open = !iconsMenu.open);
        var iconsContainer = div().add(anchor3).add(iconsMenu).element();
        iconsExample.addInteractiveDemo(iconsContainer, false);
        assertEquals("Icons (아이콘): 항목마다 start 슬롯 아이콘", 3, iconsMenu.querySelectorAll("[slot='start']").length);
    }
}
