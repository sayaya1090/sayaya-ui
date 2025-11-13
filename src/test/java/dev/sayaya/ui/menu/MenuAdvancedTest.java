package dev.sayaya.ui.menu;

import dev.sayaya.ui.elements.MenuElementBuilder;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.ButtonElementBuilder.button;
import static dev.sayaya.ui.elements.MenuElementBuilder.menu;
import static org.jboss.elemento.Elements.*;

public class MenuAdvancedTest {
    public static void test() {
        printSectionHeader("4. Menu 고급 기능 (Menu Advanced Features)");
        printDescription("Menu의 고급 기능들을 테스트합니다:");
        printDescription("- positioning: 위치 설정");
        printDescription("- offset: 오프셋 설정");
        printDescription("- quick: 빠른 애니메이션");
        printDescription("- stayOpenOnOutsideClick: 외부 클릭 시 열림 유지");
        printDescription("- typeaheadDelay: 타입어헤드 지연 시간");
        printSeparator();

        var advancedSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(advancedSection);

        advancedSection.appendChild(h(3).text("Menu Advanced Features").element());

        // Positioning
        var positioningExample = addExampleCode(advancedSection,
            "📘 Positioning (위치 설정)",
            "메뉴의 위치 설정 방식을 변경할 수 있습니다.",
            """
            var anchor = button().filled()
                    .text("Fixed 메뉴")
                    .id("anchor12")
                    .element();
            var menuElem = menu()
                .anchor("anchor12")
                .positioning(MenuElementBuilder.Position.Fixed)
                .item()
                    .headline("항목 1")
                .done()
                .item()
                    .headline("항목 2")
                .done()
                .element();
            anchor12.addEventListener("click", e -> menuElem.open = !menuElem.open);
            """);
        var anchor12 = button().filled()
                .text("Fixed 메뉴")
                .id("anchor12")
                .element();
        var positioningMenu = menu()
                .anchor("anchor12")
                .positioning(dev.sayaya.ui.elements.MenuElementBuilder.Position.Fixed)
                .item()
                    .headline("항목 1")
                .done()
                .item()
                    .headline("항목 2")
                .done()
                .element();
        anchor12.addEventListener("click", e -> positioningMenu.open = !positioningMenu.open);
        var positioningContainer = div().add(anchor12).add(positioningMenu).element();
        positioningExample.addInteractiveDemo(positioningContainer, false);
        positioningMenu.getUpdateComplete().then(result -> {
            assertEquals("positioning 속성", "fixed", positioningMenu.positioning);
            return null;
        });

        // Offset
        var offsetExample = addExampleCode(advancedSection,
            "📘 Offset (오프셋 설정)",
            "메뉴의 위치를 X, Y 축으로 이동할 수 있습니다.",
            """
            var anchor = button().filled()
                    .text("오프셋 메뉴")
                    .id("anchor13")
                    .element();
            var menuElem = menu()
                .anchor("anchor13")
                .positioning(MenuElementBuilder.Position.Fixed)
                .offset(20, 10)
                .item()
                    .headline("항목 1")
                .done()
                .item()
                    .headline("항목 2")
                .done()
                .element();
            anchor13.addEventListener("click", e -> menuElem.open = !menuElem.open);
            """);
        var anchor13 = button().filled()
                .text("오프셋 메뉴")
                .id("anchor13")
                .element();
        var offsetMenu = menu()
                .anchor("anchor13")
                .positioning(MenuElementBuilder.Position.Fixed)
                .offset(20, 10)
                .item()
                    .headline("항목 1")
                .done()
                .item()
                    .headline("항목 2")
                .done()
                .element();
        anchor13.addEventListener("click", e -> offsetMenu.open = !offsetMenu.open);
        var offsetContainer = div().add(anchor13).add(offsetMenu).element();
        offsetExample.addInteractiveDemo(offsetContainer, false);
        offsetMenu.getUpdateComplete().then(result -> {
            assertEquals("xOffset 속성", 20.0, offsetMenu.xOffset);
            assertEquals("yOffset 속성", 10.0, offsetMenu.yOffset);
            return null;
        });

        // Quick Animation
        var quickExample = addExampleCode(advancedSection,
            "📘 Quick Animation (빠른 애니메이션)",
            "메뉴의 열림/닫힘 애니메이션을 빠르게 설정할 수 있습니다.",
            """
            var anchor = button().filled()
                    .text("빠른 메뉴")
                    .id("anchor14")
                    .element();
            var menuElem = menu()
                .anchor("anchor14")
                .positioning(MenuElementBuilder.Position.Fixed)
                .quick()
                .item()
                    .headline("항목 1")
                .done()
                .item()
                    .headline("항목 2")
                .done()
                .element();
            anchor14.addEventListener("click", e -> menuElem.open = !menuElem.open);
            """);
        var anchor14 = button().filled()
                .text("빠른 메뉴")
                .id("anchor14")
                .element();
        var quickMenu = menu()
                .anchor("anchor14")
                .positioning(MenuElementBuilder.Position.Fixed)
                .quick()
                .item()
                    .headline("항목 1")
                .done()
                .item()
                    .headline("항목 2")
                .done()
                .element();
        anchor14.addEventListener("click", e -> quickMenu.open = !quickMenu.open);
        var quickContainer = div().add(anchor14).add(quickMenu).element();
        quickExample.addInteractiveDemo(quickContainer, false);
        quickMenu.getUpdateComplete().then(result -> {
            assertTrue("quick 속성이 true", quickMenu.quick);
            return null;
        });

        // Stay Open on Outside Click
        var stayOpenExample = addExampleCode(advancedSection,
            "📘 Stay Open on Outside Click (외부 클릭 시 열림 유지)",
            "외부 영역을 클릭해도 메뉴가 닫히지 않습니다.",
            """
            var anchor = button().filled()
                    .text("고정 메뉴")
                    .id("anchor15")
                    .element();
            var menuElem = menu()
                .anchor("anchor15")
                .positioning(MenuElementBuilder.Position.Fixed)
                .stayOpenOnOutsideClick()
                .item()
                    .headline("항목 1")
                .done()
                .item()
                    .headline("항목 2")
                .done()
                .element();
            anchor15.addEventListener("click", e -> menuElem.open = !menuElem.open);
            """);
        var anchor15 = button().filled()
                .text("고정 메뉴")
                .id("anchor15")
                .element();
        var stayOpenMenu = menu()
                .anchor("anchor15")
                .positioning(MenuElementBuilder.Position.Fixed)
                .stayOpenOnOutsideClick()
                .item()
                    .headline("항목 1")
                .done()
                .item()
                    .headline("항목 2")
                .done()
                .element();
        anchor15.addEventListener("click", e -> stayOpenMenu.open = !stayOpenMenu.open);
        var stayOpenContainer = div().add(anchor15).add(stayOpenMenu).element();
        stayOpenExample.addInteractiveDemo(stayOpenContainer, false);
        stayOpenMenu.getUpdateComplete().then(result -> {
            assertTrue("stayOpenOnOutsideClick 속성이 true", stayOpenMenu.stayOpenOnOutsideClick);
            return null;
        });

        // Typeahead Delay
        var typeaheadExample = addExampleCode(advancedSection,
            "📘 Typeahead Delay (타입어헤드 지연 시간)",
            "키보드 입력 시 검색 지연 시간을 설정할 수 있습니다.",
            """
            var anchor = button().filled()
                    .text("검색 메뉴")
                    .id("anchor16")
                    .element();
            var menuElem = menu()
                .anchor("anchor16")
                .positioning(MenuElementBuilder.Position.Fixed)
                .typeaheadDelay(500)
                .item()
                    .headline("Apple")
                .done()
                .item()
                    .headline("Banana")
                .done()
                .item()
                    .headline("Cherry")
                .done()
                .element();
            anchor16.addEventListener("click", e -> menuElem.open = !menuElem.open);
            """);
        var anchor16 = button().filled()
                .text("검색 메뉴")
                .id("anchor16")
                .element();
        var typeaheadMenu = menu()
                .anchor("anchor16")
                .positioning(MenuElementBuilder.Position.Fixed)
                .typeaheadDelay(500)
                .item()
                    .headline("Apple")
                .done()
                .item()
                    .headline("Banana")
                .done()
                .item()
                    .headline("Cherry")
                .done()
                .element();
        anchor16.addEventListener("click", e -> typeaheadMenu.open = !typeaheadMenu.open);
        var typeaheadContainer = div().add(anchor16).add(typeaheadMenu).element();
        typeaheadExample.addInteractiveDemo(typeaheadContainer, false);
        typeaheadMenu.getUpdateComplete().then(result -> {
            assertEquals("typeaheadDelay 속성", 500.0, typeaheadMenu.typeaheadDelay);
            return null;
        });

        // Corner Positioning
        var cornerExample = addExampleCode(advancedSection,
            "📘 Corner Positioning (코너 위치 설정)",
            "메뉴와 앵커의 코너 위치를 지정할 수 있습니다.",
            """
            var anchor = button().filled()
                    .text("코너 메뉴")
                    .id("anchor17")
                    .element();
            var menuElem = menu()
                .anchor("anchor17")
                .positioning(MenuElementBuilder.Position.Fixed)
                .anchorCorner("end-start")
                .menuCorner("start-start")
                .item()
                    .headline("항목 1")
                .done()
                .item()
                    .headline("항목 2")
                .done()
                .element();
            anchor17.addEventListener("click", e -> menuElem.open = !menuElem.open);
            """);
        var anchor17 = button().filled()
                .text("코너 메뉴")
                .id("anchor17")
                .element();
        var cornerMenu = menu()
                .anchor("anchor17")
                .positioning(MenuElementBuilder.Position.Fixed)
                .anchorCorner("end-start")
                .menuCorner("start-start")
                .item()
                    .headline("항목 1")
                .done()
                .item()
                    .headline("항목 2")
                .done()
                .element();
        anchor17.addEventListener("click", e -> cornerMenu.open = !cornerMenu.open);
        var cornerContainer = div().add(anchor17).add(cornerMenu).element();
        cornerExample.addInteractiveDemo(cornerContainer, false);
        cornerMenu.getUpdateComplete().then(result -> {
            assertEquals("anchorCorner 속성", "end-start", cornerMenu.anchorCorner);
            assertEquals("menuCorner 속성", "start-start", cornerMenu.menuCorner);
            return null;
        });
    }
}
