package dev.sayaya.ui.chip;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.ChipsElementBuilder.chips;
import static org.jboss.elemento.Elements.*;

public class ChipSetTest {
    public static void test() {
        printSectionHeader("7. Chip Set (칩 세트)");
        printDescription("여러 Chip을 그룹으로 관리합니다.");
        printSeparator();

        var chipSetSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(chipSetSection);

        chipSetSection.appendChild(h(3).text("Chip Set").element());

        // Basic Chip Set
        var basicExample = addExampleCode(chipSetSection,
            "📘 Basic Chip Set",
            "여러 Chip을 포함하는 세트입니다.",
            """
            var chipSet = chips()
                .assist().label("Chip 1").done()
                .assist().label("Chip 2").done()
                .assist().label("Chip 3").done()
                .element();
            """);
        var chipSet = chips()
                .assist().label("Chip 1").done()
                .assist().label("Chip 2").done()
                .assist().label("Chip 3").done()
                .element();
        basicExample.addInteractiveDemo(chipSet, false);
        assertEquals("칩셋: 태그명은 md-chip-set",
                "MD-CHIP-SET", chipSet.tagName);
        assertEquals("칩셋: 3개의 칩을 포함",
                3, chipSet.childElementCount);

        // Aria Label
        var ariaLabelExample = addExampleCode(chipSetSection,
            "📘 Aria Label",
            "Chip Set의 접근성 레이블을 설정합니다.",
            """
            var chipSet = chips()
                .ariaLabel("Filter options")
                .filter().label("Option 1").done()
                .filter().label("Option 2").done()
                .element();
            """);
        var labeledChipSet = chips()
                .ariaLabel("Filter options")
                .filter().label("Option 1").done()
                .filter().label("Option 2").done()
                .element();
        ariaLabelExample.addInteractiveDemo(labeledChipSet, false);
        assertEquals("칩셋 aria-label: Filter options",
                "Filter options",
                labeledChipSet.getAttribute("aria-label"));

        // Aria Labelled By
        var ariaLabelledByExample = addExampleCode(chipSetSection,
            "📘 Aria Labelled By",
            "Chip Set의 레이블을 다른 요소로 지정합니다.",
            """
            var labelElement = span().id("chip-set-label").text("Categories").element();
            var chipSet = chips()
                .ariaLabelledBy("chip-set-label")
                .filter().label("Category 1").done()
                .filter().label("Category 2").done()
                .element();
            """);
        var labelElement = span().id("chip-set-label").text("Categories").element();
        var labelledByChipSet = chips()
                .ariaLabelledBy("chip-set-label")
                .filter().label("Category 1").done()
                .filter().label("Category 2").done()
                .element();
        var demoContainer = ariaLabelledByExample.addInteractiveDemo(labelledByChipSet);
        demoContainer.append(labelElement);
        assertEquals("칩셋 aria-labelledby: chip-set-label",
                "chip-set-label",
                labelledByChipSet.getAttribute("aria-labelledby"));
    }
}
