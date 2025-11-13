package dev.sayaya.ui.list;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.ListElementBuilder.list;
import static org.jboss.elemento.Elements.*;

public class ListItemPropertiesTest {
    public static void test() {
        printSectionHeader("2. List Item 속성 (List Item Properties)");
        printDescription("List Item의 다양한 속성들을 테스트합니다:");
        printDescription("- disabled: 비활성화");
        printDescription("- tabindex: 탭 순서 제어");
        printSeparator();

        var propertiesSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(propertiesSection);

        propertiesSection.appendChild(h(3).text("List Item Properties").element());

        // Disabled Item
        var disabledExample = addExampleCode(propertiesSection,
            "📘 Disabled Item (비활성화된 항목)",
            "특정 항목을 비활성화할 수 있습니다.",
            """
            var list = list()
                .item()
                    .headline("활성 항목")
                .done()
                .item()
                    .headline("비활성 항목")
                    .disabled(true)
                .done()
                .item()
                    .headline("활성 항목 2")
                .done()
                .element();
            """);
        var disabledList = list()
                .item()
                    .headline("활성 항목")
                .done()
                .item()
                    .headline("비활성 항목")
                    .disabled(true)
                .done()
                .item()
                    .headline("활성 항목 2")
                .done()
                .element();
        disabledExample.addInteractiveDemo(disabledList, false);
        assertTrue("disabled 항목: disabled 속성 true", disabledList.items[1].disabled);

        // Tabindex Control
        var tabindexExample = addExampleCode(propertiesSection,
            "📘 Tabindex Control (탭 순서 제어)",
            "키보드 내비게이션을 위한 탭 순서를 제어할 수 있습니다.",
            """
            var list = list()
                .item()
                    .headline("첫 번째 (tabindex=0)")
                    .tabindex(0)
                .done()
                .item()
                    .headline("두 번째 (tabindex=-1)")
                    .tabindex(-1)
                .done()
                .item()
                    .headline("세 번째 (tabindex=0)")
                    .tabindex(0)
                .done()
                .element();
            """);
        var tabindexList = list()
                .item()
                    .headline("첫 번째 (tabindex=0)")
                    .tabindex(0)
                .done()
                .item()
                    .headline("두 번째 (tabindex=-1)")
                    .tabindex(-1)
                .done()
                .item()
                    .headline("세 번째 (tabindex=0)")
                    .tabindex(0)
                .done()
                .element();
        tabindexExample.addInteractiveDemo(tabindexList, false);
        assertEquals("첫 번째 항목 tabindex", 0, tabindexList.items[0].tabindex);
        assertEquals("두 번째 항목 tabindex", -1, tabindexList.items[1].tabindex);
    }
}
