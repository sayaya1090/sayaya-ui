package dev.sayaya.ui.tabs;

import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.TabsElementBuilder.tabs;
import static org.jboss.elemento.Elements.*;

public class TabsPanelTest {
    public static HTMLElement test() {
        printSectionHeader("3. Tabs 패널 (Tabs Panels)");
        printDescription("Tabs와 패널 연동을 테스트합니다:");
        printDescription("- 탭 변경 시 연결된 패널의 표시/숨김이 전환됩니다");
        printSeparator();

        var panelSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(panelSection);

        panelSection.appendChild(h(3).text("Tabs Panels").element());

        // Primary tabs with panels
        var primaryExample = addExampleCode(panelSection,
            "📘 Primary tabs with panels (주요 탭과 패널)",
            "탭이 패널 콘텐츠의 표시를 제어합니다.",
            """
            HTMLDivElement panel1 = div().textContent("Video 콘텐츠").element();
            HTMLDivElement panel2 = div().textContent("Photos 콘텐츠").element();
            HTMLDivElement panel3 = div().textContent("Audio 콘텐츠").element();

            var tabsElem = tabs().primary()
                .tab().text("Video").icon("videocam").panel(panel1).end()
                .tab().text("Photos").icon("photo").panel(panel2).end()
                .tab().text("Audio").icon("audiotrack").panel(panel3).end()
                .element();
            """);

        HTMLDivElement panel1 = div()
                .css("padding", "16px")
                .css("border", "1px solid #ddd")
                .css("border-radius", "4px")
                .css("margin-top", "8px")
                .css("background", "#f8f9fa")
                .text("Video 콘텐츠")
                .element();
        HTMLDivElement panel2 = div()
                .css("padding", "16px")
                .css("border", "1px solid #ddd")
                .css("border-radius", "4px")
                .css("margin-top", "8px")
                .css("background", "#f8f9fa")
                .text("Photos 콘텐츠")
                .element();
        HTMLDivElement panel3 = div()
                .css("padding", "16px")
                .css("border", "1px solid #ddd")
                .css("border-radius", "4px")
                .css("margin-top", "8px")
                .css("background", "#f8f9fa")
                .text("Audio 콘텐츠")
                .element();

        var primaryTabs = tabs().primary()
                .tab().text("Video").icon("videocam").panel(panel1).end()
                .tab().text("Photos").icon("photo").panel(panel2).end()
                .tab().text("Audio").icon("audiotrack").panel(panel3).end()
                .element();

        var primaryContainer = div()
                .add(primaryTabs)
                .add(panel1)
                .add(panel2)
                .add(panel3)
                .element();

        primaryExample.addInteractiveDemo(primaryContainer, false);

        // Secondary tabs with panels
        var secondaryExample = addExampleCode(panelSection,
            "📘 Secondary tabs with panels (보조 탭과 패널)",
            "보조 탭의 패널 전환 동작을 테스트합니다.",
            """
            HTMLDivElement secPanel1 = div().textContent("Flights 콘텐츠").element();
            HTMLDivElement secPanel2 = div().textContent("Trips 콘텐츠").element();
            HTMLDivElement secPanel3 = div().textContent("Explore 콘텐츠").element();

            var tabsElem = tabs().secondary()
                .tab().text("Flights").panel(secPanel1).end()
                .tab().text("Trips").panel(secPanel2).end()
                .tab().text("Explore").panel(secPanel3).end()
                .element();
            """);

        HTMLDivElement secPanel1 = div()
                .css("padding", "16px")
                .css("border", "1px solid #ddd")
                .css("border-radius", "4px")
                .css("margin-top", "8px")
                .css("background", "#f8f9fa")
                .text("Flights 콘텐츠")
                .element();
        HTMLDivElement secPanel2 = div()
                .css("padding", "16px")
                .css("border", "1px solid #ddd")
                .css("border-radius", "4px")
                .css("margin-top", "8px")
                .css("background", "#f8f9fa")
                .text("Trips 콘텐츠")
                .element();
        HTMLDivElement secPanel3 = div()
                .css("padding", "16px")
                .css("border", "1px solid #ddd")
                .css("border-radius", "4px")
                .css("margin-top", "8px")
                .css("background", "#f8f9fa")
                .text("Explore 콘텐츠")
                .element();

        var secondaryTabs = tabs().secondary()
                .tab().text("Flights").panel(secPanel1).end()
                .tab().text("Trips").panel(secPanel2).end()
                .tab().text("Explore").panel(secPanel3).end()
                .element();

        var secondaryContainer = div()
                .add(secondaryTabs)
                .add(secPanel1)
                .add(secPanel2)
                .add(secPanel3)
                .element();

        secondaryExample.addInteractiveDemo(secondaryContainer, false);

        // Tabs with complex panel content
        var complexExample = addExampleCode(panelSection,
            "📘 Tabs with complex panels (복잡한 패널)",
            "패널에 복잡한 콘텐츠 구조를 포함할 수 있습니다.",
            """
            HTMLDivElement complexPanel1 = div()
                .add(div().textContent("Overview Section"))
                .add(div().textContent("상세 내용..."))
                .element();

            var tabsElem = tabs().primary()
                .tab().text("Overview").panel(complexPanel1).end()
                .tab().text("Features").panel(complexPanel2).end()
                .tab().text("Settings").panel(complexPanel3).end()
                .element();
            """);

        HTMLDivElement complexPanel1 = div()
                .css("padding", "16px")
                .css("border", "1px solid #ddd")
                .css("border-radius", "4px")
                .css("margin-top", "8px")
                .css("background", "#f8f9fa")
                .add(div().css("font-weight", "bold").text("Overview Section"))
                .add(div().css("margin-top", "8px").text("이것은 개요 콘텐츠입니다. 더 자세한 정보가 여기에 표시됩니다."))
                .element();

        HTMLDivElement complexPanel2 = div()
                .css("padding", "16px")
                .css("border", "1px solid #ddd")
                .css("border-radius", "4px")
                .css("margin-top", "8px")
                .css("background", "#f8f9fa")
                .add(div().css("font-weight", "bold").text("Features Section"))
                .add(div().css("margin-top", "8px").text("기능 목록이 여기에 표시됩니다."))
                .element();

        HTMLDivElement complexPanel3 = div()
                .css("padding", "16px")
                .css("border", "1px solid #ddd")
                .css("border-radius", "4px")
                .css("margin-top", "8px")
                .css("background", "#f8f9fa")
                .add(div().css("font-weight", "bold").text("Settings Section"))
                .add(div().css("margin-top", "8px").text("설정 옵션이 여기에 표시됩니다."))
                .element();

        var complexTabs = tabs().primary()
                .tab().text("Overview").panel(complexPanel1).end()
                .tab().text("Features").panel(complexPanel2).end()
                .tab().text("Settings").panel(complexPanel3).end()
                .activeTabIndex(0)
                .element();

        var complexContainer = div()
                .add(complexTabs)
                .add(complexPanel1)
                .add(complexPanel2)
                .add(complexPanel3)
                .element();

        complexExample.addInteractiveDemo(complexContainer, false);

        return panelSection;
    }
}
