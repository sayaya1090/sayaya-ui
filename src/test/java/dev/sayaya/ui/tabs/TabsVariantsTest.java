package dev.sayaya.ui.tabs;

import elemental2.dom.HTMLElement;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.TabsElementBuilder.tabs;
import static org.jboss.elemento.Elements.*;

public class TabsVariantsTest {
    public static HTMLElement test() {
        printSectionHeader("1. Tabs 변형 (Tabs Variants)");
        printDescription("Tabs의 다양한 변형을 테스트합니다:");
        printDescription("- Primary: 주요 탭");
        printDescription("- Secondary: 보조 탭");
        printSeparator();

        var variantsSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(variantsSection);

        variantsSection.appendChild(h(3).text("Tabs Variants").element());

        // Primary Tabs
        var primaryExample = addExampleCode(variantsSection,
            "📘 Primary Tabs (주요 탭)",
            "주요 내비게이션을 위한 탭입니다.",
            """
            var tabsElem = tabs()
                .primary()
                .tab().text("홈").end()
                .tab().text("제품").end()
                .tab().text("서비스").end()
                .element();
            """);
        var primaryTabs = tabs()
                .primary()
                .tab().text("홈").end()
                .tab().text("제품").end()
                .tab().text("서비스").end()
                .element();

        primaryExample.addInteractiveDemo(primaryTabs, false);

        assertEquals("탭 개수: 3개여야 함", 3, primaryTabs.tabs.length);

        // Secondary Tabs
        var secondaryExample = addExampleCode(variantsSection,
            "📘 Secondary Tabs (보조 탭)",
            "서브 내비게이션을 위한 탭입니다.",
            """
            var tabsElem = tabs()
                .secondary()
                .tab().text("전체").end()
                .tab().text("진행 중").end()
                .tab().text("완료").end()
                .element();
            """);
        var secondaryTabs = tabs()
                .secondary()
                .tab().text("전체").end()
                .tab().text("진행 중").end()
                .tab().text("완료").end()
                .element();

        secondaryExample.addInteractiveDemo(secondaryTabs, false);

        assertEquals("Secondary 탭 개수: 3개여야 함", 3, secondaryTabs.tabs.length);

        // Primary Tabs with Icons
        var iconsExample = addExampleCode(variantsSection,
            "📘 Primary Tabs with Icons (아이콘)",
            "아이콘이 포함된 주요 탭입니다.",
            """
            var tabsElem = tabs()
                .primary()
                .tab().text("홈").icon("home").end()
                .tab().text("알림").icon("notifications").end()
                .tab().text("설정").icon("settings").end()
                .element();
            """);
        var iconTabs = tabs()
                .primary()
                .tab().text("홈").icon("home").end()
                .tab().text("알림").icon("notifications").end()
                .tab().text("설정").icon("settings").end()
                .element();

        iconsExample.addInteractiveDemo(iconTabs, false);

        assertEquals("아이콘 탭 개수: 3개여야 함", 3, iconTabs.tabs.length);

        return variantsSection;
    }
}
