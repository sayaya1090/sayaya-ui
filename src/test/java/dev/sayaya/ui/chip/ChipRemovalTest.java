package dev.sayaya.ui.chip;

import dev.sayaya.ui.dom.MdChipElement;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.ChipsElementBuilder.chips;
import static org.jboss.elemento.Elements.*;

public class ChipRemovalTest {
    public static void test() {
        printSectionHeader("11. Chip 제거 (Removal)");
        printDescription("Filter와 Input Chip은 제거 기능을 제공합니다.");
        printSeparator();

        var removalSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(removalSection);

        removalSection.appendChild(h(3).text("Chip Removal").element());

        // Filter Chip Removable
        var filterExample = addExampleCode(removalSection,
            "📘 Removable Filter Chip",
            "제거 가능한 Filter Chip입니다.",
            """
            var chip = chips()
                .filter()
                .label("Removable Filter")
                .removable()
                .ariaLabelRemove("Remove filter")
                .element();
            """);
        var removableFilter = (MdChipElement.MdFilterChipElement) chips()
                .filter()
                .label("Removable Filter")
                .removable()
                .ariaLabelRemove("Remove filter")
                .element();
        filterExample.addInteractiveDemo(removableFilter, false);
        assertTrue("removable filter 칩: removable이 true",
                removableFilter.removable);
        assertEquals("removable filter 칩: ariaLabelRemove 설정됨",
                "Remove filter", removableFilter.ariaLabelRemove);

        // Input Chip Remove Only
        var inputExample = addExampleCode(removalSection,
            "📘 Remove Only Input Chip",
            "제거만 가능한 Input Chip입니다 (선택 비활성화).",
            """
            var chip = chips()
                .input()
                .label("Remove Only Input")
                .removeOnly()
                .ariaLabelRemove("Remove input")
                .element();
            """);
        var removeOnlyInput = (MdChipElement.MdInputChipElement) chips()
                .input()
                .label("Remove Only Input")
                .removeOnly()
                .ariaLabelRemove("Remove input")
                .element();
        inputExample.addInteractiveDemo(removeOnlyInput, false);
        assertTrue("removeOnly input 칩: removeOnly가 true",
                removeOnlyInput.removeOnly);
        assertEquals("removeOnly input 칩: ariaLabelRemove 설정됨",
                "Remove input", removeOnlyInput.ariaLabelRemove);
    }
}
