package dev.sayaya.ui.tabs;

import elemental2.dom.HTMLElement;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.TabsElementBuilder.tabs;
import static org.jboss.elemento.Elements.*;

public class TabsPropertiesTest {
    public static HTMLElement test() {
        printSectionHeader("2. Tabs 속성 (Tabs Properties)");
        printDescription("Tabs의 다양한 속성을 테스트합니다:");
        printDescription("- autoActivate: 자동 활성화");
        printDescription("- activeTabIndex: 활성 탭 인덱스");
        printDescription("- inlineIcon: 인라인 아이콘");
        printSeparator();

        var propertiesSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(propertiesSection);

        propertiesSection.appendChild(h(3).text("Tabs Properties").element());

        // Auto-activate property test
        var autoActivateExample = addExampleCode(propertiesSection,
            "📘 Auto-activate (자동 활성화)",
            "탭 클릭 시 자동으로 활성화됩니다 (기본 동작).",
            """
            var tabsElem = tabs().primary()
                .tab().text("Tab 1").end()
                .tab().text("Tab 2").end()
                .tab().text("Tab 3").end()
                .autoActivate()
                .element();
            """);
        var autoActivateTabs = tabs().primary()
                .tab().text("Tab 1").end()
                .tab().text("Tab 2").end()
                .tab().text("Tab 3").end()
                .autoActivate()
                .element();

        autoActivateExample.addInteractiveDemo(autoActivateTabs, false);

        assertTrue("Auto-activate가 활성화되어야 함", autoActivateTabs.autoActivate);

        // Manual activate property test
        var manualActivateExample = addExampleCode(propertiesSection,
            "📘 Manual activate (수동 활성화)",
            "Enter/Space 키를 눌러야 활성화됩니다.",
            """
            var tabsElem = tabs().primary()
                .tab().text("Tab 1").end()
                .tab().text("Tab 2").end()
                .tab().text("Tab 3").end()
                .autoActivate(false)
                .element();
            """);
        var manualActivateTabs = tabs().primary()
                .tab().text("Tab 1").end()
                .tab().text("Tab 2").end()
                .tab().text("Tab 3").end()
                .autoActivate(false)
                .element();

        manualActivateExample.addInteractiveDemo(manualActivateTabs, false);

        assertFalse("Auto-activate가 비활성화되어야 함", manualActivateTabs.autoActivate);

        // Active tab index test
        var indexExample = addExampleCode(propertiesSection,
            "📘 Active tab index (활성 탭 인덱스)",
            "두 번째 탭 (인덱스 1)이 기본으로 활성화됩니다.",
            """
            var tabsElem = tabs().primary()
                .tab().text("Tab 1").end()
                .tab().text("Tab 2").end()
                .tab().text("Tab 3").end()
                .activeTabIndex(1)
                .element();
            """);
        var indexTabs = tabs().primary()
                .tab().text("Tab 1").end()
                .tab().text("Tab 2").end()
                .tab().text("Tab 3").end()
                .activeTabIndex(1)
                .element();

        indexExample.addInteractiveDemo(indexTabs, false);

        assertEquals("활성 탭 인덱스: 1이어야 함", 1, indexTabs.activeTabIndex);

        // Inline icon test
        var inlineIconExample = addExampleCode(propertiesSection,
            "📘 Inline icon (인라인 아이콘)",
            "아이콘이 텍스트 옆에 표시됩니다.",
            """
            var tabsElem = tabs().primary()
                .tab().text("Flights").icon("flight").inlineIcon().end()
                .tab().text("Trips").icon("luggage").inlineIcon().end()
                .tab().text("Explore").icon("explore").inlineIcon().end()
                .element();
            """);
        var inlineIconTabs = tabs().primary()
                .tab().text("Flights").icon("flight").inlineIcon().end()
                .tab().text("Trips").icon("luggage").inlineIcon().end()
                .tab().text("Explore").icon("explore").inlineIcon().end()
                .element();

        inlineIconExample.addInteractiveDemo(inlineIconTabs, false);

        // Stacked icon test (default)
        var stackedIconExample = addExampleCode(propertiesSection,
            "📘 Stacked icon (스택 아이콘)",
            "아이콘이 텍스트 위에 표시됩니다 (기본 동작).",
            """
            var tabsElem = tabs().primary()
                .tab().text("Flights").icon("flight").end()
                .tab().text("Trips").icon("luggage").end()
                .tab().text("Explore").icon("explore").end()
                .element();
            """);
        var stackedIconTabs = tabs().primary()
                .tab().text("Flights").icon("flight").end()
                .tab().text("Trips").icon("luggage").end()
                .tab().text("Explore").icon("explore").end()
                .element();

        stackedIconExample.addInteractiveDemo(stackedIconTabs, false);

        return propertiesSection;
    }
}
