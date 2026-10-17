package dev.sayaya.ui.menu;

import dev.sayaya.ui.elements.MenuElementBuilder;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.ButtonElementBuilder.button;
import static dev.sayaya.ui.elements.IconElementBuilder.icon;
import static dev.sayaya.ui.elements.MenuElementBuilder.menu;
import static org.jboss.elemento.Elements.*;

public class MenuItemPropertiesTest {
    public static void test() {
        printSectionHeader("2. MenuItem 속성 (MenuItem Properties)");
        printDescription("MenuItem의 다양한 속성들을 테스트합니다:");
        printDescription("- disabled: 비활성화");
        printDescription("- selected: 선택 상태");
        printDescription("- keepOpen: 클릭 후 메뉴 열림 유지");
        printDescription("- href/target: 링크 속성");
        printDescription("- type: 항목 타입");
        printSeparator();

        var propertiesSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(propertiesSection);

        propertiesSection.appendChild(h(3).text("MenuItem Properties").element());

        // Disabled Item
        var disabledExample = addExampleCode(propertiesSection,
            "📘 Disabled Item (비활성화된 항목)",
            "특정 항목을 비활성화할 수 있습니다.",
            """
            var anchor = button().filled()
                    .text("편집 메뉴")
                    .id("anchor4")
                    .element();
            var menuElem = menu()
                .anchor("anchor4")
                .positioning(MenuElementBuilder.Position.Fixed)
                .item()
                    .headline("잘라내기")
                .done()
                .item()
                    .headline("복사")
                    .disabled()
                .done()
                .item()
                    .headline("붙여넣기")
                .done()
                .element();
            anchor4.addEventListener("click", e -> menuElem.open = !menuElem.open);
            """);
        var anchor4 = button().filled()
                .text("편집 메뉴")
                .id("anchor4")
                .element();
        var disabledMenu = menu()
                .anchor("anchor4")
                .positioning(MenuElementBuilder.Position.Fixed)
                .item()
                    .headline("잘라내기")
                .done()
                .item()
                    .headline("복사")
                    .disabled()
                .done()
                .item()
                    .headline("붙여넣기")
                .done()
                .element();
        anchor4.addEventListener("click", e -> disabledMenu.open = !disabledMenu.open);
        var disabledContainer = div().add(anchor4).add(disabledMenu).element();
        disabledExample.addInteractiveDemo(disabledContainer, false);
        disabledMenu.getUpdateComplete().then(result -> {
            assertTrue("두 번째 항목: disabled 속성 true", disabledMenu.items[1].disabled);
            return null;
        });

        // Selected Item
        var selectedExample = addExampleCode(propertiesSection,
            "📘 Selected Item (선택된 항목)",
            "항목을 선택된 상태로 표시할 수 있습니다.",
            """
            var anchor = button().filled()
                    .text("정렬 기준")
                    .id("anchor5")
                    .element();
            var menuElem = menu()
                .anchor("anchor5")
                .positioning(MenuElementBuilder.Position.Fixed)
                .item()
                    .start(icon("check"))
                    .headline("이름순")
                    .select()
                .done()
                .item()
                    .headline("날짜순")
                .done()
                .item()
                    .headline("크기순")
                .done()
                .element();
            anchor5.addEventListener("click", e -> menuElem.open = !menuElem.open);
            """);
        var anchor5 = button().filled()
                .text("정렬 기준")
                .id("anchor5")
                .element();
        var selectedMenu = menu()
                .anchor("anchor5")
                .positioning(MenuElementBuilder.Position.Fixed)
                .item()
                    .start(icon("check"))
                    .headline("이름순")
                    .select()
                .done()
                .item()
                    .headline("날짜순")
                .done()
                .item()
                    .headline("크기순")
                .done()
                .element();
        anchor5.addEventListener("click", e -> selectedMenu.open = !selectedMenu.open);
        var selectedContainer = div().add(anchor5).add(selectedMenu).element();
        selectedExample.addInteractiveDemo(selectedContainer, false);
        selectedMenu.getUpdateComplete().then(result -> {
            assertTrue("첫 번째 항목: selected 속성 true", selectedMenu.items[0].selected);
            return null;
        });

        // Keep Open
        var keepOpenExample = addExampleCode(propertiesSection,
            "📘 Keep Open (메뉴 열림 유지)",
            "항목 클릭 후에도 메뉴를 열린 상태로 유지합니다.",
            """
            var anchor = button().filled()
                    .text("필터 선택")
                    .id("anchor6")
                    .element();
            var menuElem = menu()
                .anchor("anchor6")
                .positioning(MenuElementBuilder.Position.Fixed)
                .item()
                    .headline("전체")
                    .keepOpen()
                .done()
                .item()
                    .headline("진행 중")
                    .keepOpen()
                .done()
                .item()
                    .headline("완료")
                    .keepOpen()
                .done()
                .element();
            anchor6.addEventListener("click", e -> menuElem.open = !menuElem.open);
            """);
        var anchor6 = button().filled()
                .text("필터 선택")
                .id("anchor6")
                .element();
        var keepOpenMenu = menu()
                .anchor("anchor6")
                .positioning(MenuElementBuilder.Position.Fixed)
                .item()
                    .headline("전체")
                    .keepOpen()
                .done()
                .item()
                    .headline("진행 중")
                    .keepOpen()
                .done()
                .item()
                    .headline("완료")
                    .keepOpen()
                .done()
                .element();
        anchor6.addEventListener("click", e -> keepOpenMenu.open = !keepOpenMenu.open);
        var keepOpenContainer = div().add(anchor6).add(keepOpenMenu).element();
        keepOpenExample.addInteractiveDemo(keepOpenContainer, false);
        keepOpenMenu.getUpdateComplete().then(result -> {
            assertTrue("모든 항목: keepOpen 속성 true",
                keepOpenMenu.items[0].keepOpen &&
                keepOpenMenu.items[1].keepOpen &&
                keepOpenMenu.items[2].keepOpen);
            return null;
        });

        // Link Item
        var linkExample = addExampleCode(propertiesSection,
            "📘 Link Item (링크 항목)",
            "MenuItem에 href와 target 속성을 지정할 수 있습니다.",
            """
            var anchor = button().filled()
                    .text("빠른 링크")
                    .id("anchor7")
                    .element();
            var menuElem = menu()
                .anchor("anchor7")
                .positioning(MenuElementBuilder.Position.Fixed)
                .item()
                    .headline("Google")
                    .href("https://google.com")
                    .targetBlank()
                .done()
                .item()
                    .headline("GitHub")
                    .href("https://github.com")
                    .targetBlank()
                .done()
                .element();
            anchor7.addEventListener("click", e -> menuElem.open = !menuElem.open);
            """);
        var anchor7 = button().filled()
                .text("빠른 링크")
                .id("anchor7")
                .element();
        var linkMenu = menu()
                .anchor("anchor7")
                .positioning(MenuElementBuilder.Position.Fixed)
                .item()
                    .headline("Google")
                    .href("https://google.com")
                    .targetBlank()
                .done()
                .item()
                    .headline("GitHub")
                    .href("https://github.com")
                    .targetBlank()
                .done()
                .element();
        anchor7.addEventListener("click", e -> linkMenu.open = !linkMenu.open);
        var linkContainer = div().add(anchor7).add(linkMenu).element();
        linkExample.addInteractiveDemo(linkContainer, false);
        linkMenu.getUpdateComplete().then(result -> {
            assertEquals("첫 번째 항목: href 속성 google.com", "https://google.com", linkMenu.items[0].href);
            assertEquals("첫 번째 항목: target 속성 _blank", "_blank", linkMenu.items[0].target);
            return null;
        });

        // Type Attribute
        var typeExample = addExampleCode(propertiesSection,
            "📘 Type Attribute (타입 속성)",
            "MenuItem의 type 속성을 지정할 수 있습니다.",
            """
            var anchor = button().filled()
                    .text("작업 메뉴")
                    .id("anchor8")
                    .element();
            var menuElem = menu()
                .anchor("anchor8")
                .positioning(MenuElementBuilder.Position.Fixed)
                .item()
                    .headline("일반 항목")
                    .type("button")
                .done()
                .item()
                    .headline("링크 항목")
                    .type("link")
                    .href("#")
                .done()
                .element();
            anchor8.addEventListener("click", e -> menuElem.open = !menuElem.open);
            """);
        var anchor8 = button().filled()
                .text("작업 메뉴")
                .id("anchor8")
                .element();
        var typeMenu = menu()
                .anchor("anchor8")
                .positioning(MenuElementBuilder.Position.Fixed)
                .item()
                    .headline("일반 항목")
                    .type("button")
                .done()
                .item()
                    .headline("링크 항목")
                    .type("link")
                    .href("#")
                .done()
                .element();
        anchor8.addEventListener("click", e -> typeMenu.open = !typeMenu.open);
        var typeContainer = div().add(anchor8).add(typeMenu).element();
        typeExample.addInteractiveDemo(typeContainer, false);
        typeMenu.getUpdateComplete().then(result -> {
            assertEquals("첫 번째 항목: type 속성 button", "button", typeMenu.items[0].type);
            assertEquals("두 번째 항목: type 속성 link", "link", typeMenu.items[1].type);
            return null;
        });
    }
}
