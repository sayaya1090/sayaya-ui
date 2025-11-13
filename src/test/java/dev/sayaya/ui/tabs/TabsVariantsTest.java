package dev.sayaya.ui.tabs;

import elemental2.dom.HTMLElement;
import elemental2.promise.Promise;

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
                .tab().text("홈").done()
                .tab().text("제품").done()
                .tab().text("서비스").done()
                .element();
            """);
        var primaryTabs = tabs()
                .primary()
                .tab().text("홈").done()
                .tab().text("제품").done()
                .tab().text("서비스").done()
                .element();

        primaryExample.addInteractiveDemo(primaryTabs, false);
        primaryTabs.getUpdateComplete().then(result ->{
            assertEquals("탭 개수: 3개여야 함", 3, primaryTabs.tabs.length);
            return Promise.resolve(result);
        });

        // Secondary Tabs
        var secondaryExample = addExampleCode(variantsSection,
            "📘 Secondary Tabs (보조 탭)",
            "서브 내비게이션을 위한 탭입니다.",
            """
            var tabsElem = tabs()
                .secondary()
                .tab().text("전체").done()
                .tab().text("진행 중").done()
                .tab().text("완료").done()
                .element();
            """);
        var secondaryTabs = tabs()
                .secondary()
                .tab().text("전체").done()
                .tab().text("진행 중").done()
                .tab().text("완료").done()
                .element();

        secondaryExample.addInteractiveDemo(secondaryTabs, false);
        secondaryTabs.getUpdateComplete().then(result ->{
            assertEquals("Secondary 탭 개수: 3개여야 함", 3, secondaryTabs.tabs.length);
            return Promise.resolve(result);
        });

        // Primary Tabs with Icons
        var iconsExample = addExampleCode(variantsSection,
            "📘 Primary Tabs with Icons (아이콘)",
            "아이콘이 포함된 주요 탭입니다.",
            """
            var tabsElem = tabs()
                .primary()
                .tab().text("홈").icon("home").done()
                .tab().text("알림").icon("notifications").done()
                .tab().text("설정").icon("settings").done()
                .element();
            """);
        var iconTabs = tabs()
                .primary()
                .tab().text("홈").icon("home").done()
                .tab().text("알림").icon("notifications").done()
                .tab().text("설정").icon("settings").done()
                .element();

        iconsExample.addInteractiveDemo(iconTabs, false);
        iconTabs.getUpdateComplete().then(result ->{
            assertEquals("아이콘 탭 개수: 3개여야 함", 3, iconTabs.tabs.length);
            return Promise.resolve(result);
        });

        return variantsSection;
    }
}
