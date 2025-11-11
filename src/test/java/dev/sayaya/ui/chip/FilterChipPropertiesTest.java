package dev.sayaya.ui.chip;

import dev.sayaya.ui.dom.MdChipElement;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.ChipsElementBuilder.chips;
import static org.jboss.elemento.Elements.*;

public class FilterChipPropertiesTest {
    public static void test() {
        printSectionHeader("4. Filter Chip 속성 (Filter Chip Properties)");
        printDescription("Filter Chip 전용 속성들입니다.");
        printSeparator();

        var filterSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(filterSection);

        filterSection.appendChild(h(3).text("Filter Chip Properties").element());

        // Elevated
        var elevatedExample = addExampleCode(filterSection,
            "📘 Elevated",
            "그림자 효과가 있는 elevated 스타일을 적용합니다.",
            """
            var chip = chips()
                .filter()
                .label("Elevated Filter")
                .elevated()
                .element();
            """);
        var elevatedChip = (MdChipElement.MdFilterChipElement) chips()
                .filter()
                .label("Elevated Filter")
                .elevated()
                .element();
        elevatedExample.addInteractiveDemo(elevatedChip, false);
        assertTrue("filter 칩 elevated: true",
                elevatedChip.elevated);

        // Selected
        var selectedExample = addExampleCode(filterSection,
            "📘 Selected",
            "Chip을 선택된 상태로 설정합니다.",
            """
            var chip = chips()
                .filter()
                .label("Selected")
                .select()
                .element();
            """);
        var selectedChip = (MdChipElement.MdFilterChipElement) chips()
                .filter()
                .label("Selected")
                .select()
                .element();
        selectedExample.addInteractiveDemo(selectedChip, false);
        assertTrue("filter 칩 selected: true",
                selectedChip.selected);

        // Removable
        var removableExample = addExampleCode(filterSection,
            "📘 Removable",
            "제거 버튼을 추가합니다.",
            """
            var chip = chips()
                .filter()
                .label("Removable")
                .removable()
                .element();
            """);
        var removableChip = (MdChipElement.MdFilterChipElement) chips()
                .filter()
                .label("Removable")
                .removable()
                .element();
        removableExample.addInteractiveDemo(removableChip, false);
        assertTrue("filter 칩 removable: true",
                removableChip.removable);

        // Aria Label Remove
        var ariaRemoveExample = addExampleCode(filterSection,
            "📘 Aria Label Remove",
            "제거 버튼의 접근성 레이블을 설정합니다.",
            """
            var chip = chips()
                .filter()
                .label("Remove")
                .removable()
                .ariaLabelRemove("Remove this chip")
                .element();
            """);
        var ariaRemoveChip = (MdChipElement.MdFilterChipElement) chips()
                .filter()
                .label("Remove")
                .removable()
                .ariaLabelRemove("Remove this chip")
                .element();
        ariaRemoveExample.addInteractiveDemo(ariaRemoveChip, false);
        assertEquals("filter 칩 ariaLabelRemove: Remove this chip",
                "Remove this chip", ariaRemoveChip.ariaLabelRemove);
    }
}
