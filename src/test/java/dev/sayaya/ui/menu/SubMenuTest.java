package dev.sayaya.ui.menu;

import dev.sayaya.ui.elements.MenuElementBuilder;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.ButtonElementBuilder.button;
import static dev.sayaya.ui.elements.IconElementBuilder.icon;
import static dev.sayaya.ui.elements.MenuElementBuilder.menu;
import static org.jboss.elemento.Elements.*;

public class SubMenuTest {
    public static void test() {
        printSectionHeader("3. SubMenu (서브메뉴)");
        printDescription("중첩된 메뉴 구조를 만들 수 있습니다.");
        printSeparator();

        var subMenuSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(subMenuSection);

        subMenuSection.appendChild(h(3).text("SubMenu").element());

        // Basic SubMenu
        var basicSubMenuExample = addExampleCode(subMenuSection,
            "📘 Basic SubMenu (기본 서브메뉴)",
            "메뉴 항목에 중첩된 서브메뉴를 추가할 수 있습니다.",
            """
            var anchor = button().filled()
                    .text("파일 메뉴")
                    .id("anchor9")
                    .element();
            var menuElem = menu()
                .anchor("anchor9")
                .positioning(MenuElementBuilder.Position.Fixed)
                .overflow()
                .item()
                    .headline("새 파일")
                .done()
                .sub()
                    .item()
                        .headline("열기")
                        .end(icon("arrow_right"))
                    .done()
                    .menu()
                        .item()
                            .headline("최근 파일 1")
                        .done()
                        .item()
                            .headline("최근 파일 2")
                        .done()
                    .done()
                .done()
                .item()
                    .headline("저장")
                .done()
                .element();
            anchor9.addEventListener("click", e -> menuElem.open = !menuElem.open);
            """);
        var anchor9 = button().filled()
                .text("파일 메뉴")
                .id("anchor9")
                .element();
        var basicSubMenu = menu()
                .anchor("anchor9")
                .positioning(MenuElementBuilder.Position.Fixed)
                .overflow()
                .item()
                    .headline("새 파일")
                .done()
                .sub()
                    .item()
                        .headline("열기")
                        .end(icon("arrow_right"))
                    .done()
                    .menu()
                        .item()
                            .headline("최근 파일 1")
                        .done()
                        .item()
                            .headline("최근 파일 2")
                        .done()
                    .done()
                .done()
                .item()
                    .headline("저장")
                .done()
                .element();
        anchor9.addEventListener("click", e -> basicSubMenu.open = !basicSubMenu.open);
        var basicSubMenuContainer = div().add(anchor9).add(basicSubMenu).element();
        basicSubMenuExample.addInteractiveDemo(basicSubMenuContainer, false);

        // Nested SubMenu with Icons
        var nestedSubMenuExample = addExampleCode(subMenuSection,
            "📘 Nested SubMenu with Icons (아이콘이 있는 중첩 서브메뉴)",
            "여러 단계의 서브메뉴를 만들 수 있습니다.",
            """
            var anchor = button().filled()
                    .text("내보내기")
                    .id("anchor10")
                    .element();
            var menuElem = menu()
                .anchor("anchor10")
                .positioning(MenuElementBuilder.Position.Fixed)
                .overflow()
                .sub()
                    .item()
                        .start(icon("image"))
                        .headline("이미지로 내보내기")
                        .end(icon("arrow_right"))
                    .done()
                    .menu()
                        .item()
                            .headline("PNG")
                        .done()
                        .item()
                            .headline("JPEG")
                        .done()
                        .item()
                            .headline("SVG")
                        .done()
                    .done()
                .done()
                .sub()
                    .item()
                        .start(icon("description"))
                        .headline("문서로 내보내기")
                        .end(icon("arrow_right"))
                    .done()
                    .menu()
                        .item()
                            .headline("PDF")
                        .done()
                        .item()
                            .headline("Word")
                        .done()
                    .done()
                .done()
                .element();
            anchor10.addEventListener("click", e -> menuElem.open = !menuElem.open);
            """);
        var anchor10 = button().filled()
                .text("내보내기")
                .id("anchor10")
                .element();
        var nestedSubMenu = menu()
                .anchor("anchor10")
                .positioning(MenuElementBuilder.Position.Fixed)
                .overflow()
                .sub()
                    .item()
                        .start(icon("image"))
                        .headline("이미지로 내보내기")
                        .end(icon("arrow_right"))
                    .done()
                    .menu()
                        .item()
                            .headline("PNG")
                        .done()
                        .item()
                            .headline("JPEG")
                        .done()
                        .item()
                            .headline("SVG")
                        .done()
                    .done()
                .done()
                .sub()
                    .item()
                        .start(icon("description"))
                        .headline("문서로 내보내기")
                        .end(icon("arrow_right"))
                    .done()
                    .menu()
                        .item()
                            .headline("PDF")
                        .done()
                        .item()
                            .headline("Word")
                        .done()
                    .done()
                .done()
                .element();
        anchor10.addEventListener("click", e -> nestedSubMenu.open = !nestedSubMenu.open);
        var nestedSubMenuContainer = div().add(anchor10).add(nestedSubMenu).element();
        nestedSubMenuExample.addInteractiveDemo(nestedSubMenuContainer, false);

        // SubMenu with Hover Delay
        var hoverDelayExample = addExampleCode(subMenuSection,
            "📘 SubMenu with Hover Delay (호버 지연 시간 설정)",
            "서브메뉴의 열림/닫힘 지연 시간을 설정할 수 있습니다.",
            """
            var anchor = button().filled()
                    .text("설정 메뉴")
                    .id("anchor11")
                    .element();
            var menuElem = menu()
                .anchor("anchor11")
                .positioning(MenuElementBuilder.Position.Fixed)
                .overflow()
                .sub()
                    .hoverOpenDelay(400)
                    .hoverCloseDelay(200)
                    .item()
                        .headline("환경설정")
                        .end(icon("arrow_right"))
                    .done()
                    .menu()
                        .item()
                            .headline("일반")
                        .done()
                        .item()
                            .headline("고급")
                        .done()
                    .done()
                .done()
                .element();
            anchor11.addEventListener("click", e -> menuElem.open = !menuElem.open);
            """);
        var anchor11 = button().filled()
                .text("설정 메뉴")
                .id("anchor11")
                .element();
        var hoverDelayMenu = menu()
                .anchor("anchor11")
                .positioning(MenuElementBuilder.Position.Fixed)
                .overflow()
                .sub()
                    .hoverOpenDelay(400)
                    .hoverCloseDelay(200)
                    .item()
                        .headline("환경설정")
                        .end(icon("arrow_right"))
                    .done()
                    .menu()
                        .item()
                            .headline("일반")
                        .done()
                        .item()
                            .headline("고급")
                        .done()
                    .done()
                .done()
                .element();
        anchor11.addEventListener("click", e -> hoverDelayMenu.open = !hoverDelayMenu.open);
        var hoverDelayContainer = div().add(anchor11).add(hoverDelayMenu).element();
        hoverDelayExample.addInteractiveDemo(hoverDelayContainer, false);
    }
}
