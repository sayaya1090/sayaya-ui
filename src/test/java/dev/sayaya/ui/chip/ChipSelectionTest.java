package dev.sayaya.ui.chip;

import dev.sayaya.ui.dom.MdChipElement;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.ChipsElementBuilder.chips;
import static org.jboss.elemento.Elements.*;

public class ChipSelectionTest {
    public static void test() {
        printSectionHeader("10. Chip 선택 (Selection)");
        printDescription("Filter와 Input Chip은 선택 상태를 관리할 수 있습니다.");
        printSeparator();

        var selectionSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(selectionSection);

        selectionSection.appendChild(h(3).text("Chip Selection").element());

        // Filter Chip Selection
        var filterExample = addExampleCode(selectionSection,
            "📘 Filter Chip Selection",
            "Filter Chip의 선택 상태를 제어합니다.",
            """
            var chipBuilder = chips()
                .filter()
                .label("Selectable Filter");
            var chip = chipBuilder.element();
            
            // 선택 상태 확인
            chipBuilder.isSelected();  // false
            
            // 선택/해제
            chipBuilder.select(true);  // 선택
            chipBuilder.select(false); // 해제
            """);
        var filterChipBuilder = chips()
                .filter()
                .label("Selectable Filter");
        var filterChip = (MdChipElement.MdFilterChipElement) filterChipBuilder.element();
        filterExample.addInteractiveDemo(filterChip, false);
        assertFalse("칩 선택: 초기에는 선택되지 않음",
                filterChipBuilder.isSelected());

        filterChipBuilder.select(true);
        assertTrue("칩 선택: select(true) 후 선택됨",
                filterChip.selected);

        filterChipBuilder.select(false);
        assertFalse("칩 선택: select(false) 후 선택 해제",
                filterChip.selected);

        // Input Chip Selection
        var inputExample = addExampleCode(selectionSection,
            "📘 Input Chip Selection",
            "Input Chip의 선택 상태를 제어합니다.",
            """
            var chipBuilder = chips()
                .input()
                .label("Selectable Input");
            var chip = chipBuilder.element();

            // 선택
            chipBuilder.select();  // 선택
            """);
        var inputChipBuilder = chips()
                .input()
                .label("Selectable Input");
        var inputChip = (MdChipElement.MdInputChipElement) inputChipBuilder.element();
        inputExample.addInteractiveDemo(inputChip, false);
        assertFalse("input 칩 선택: 초기에는 선택되지 않음",
                inputChipBuilder.isSelected());

        inputChipBuilder.select();
        assertTrue("input 칩 선택: select() 후 선택됨",
                inputChip.selected);
    }
}
