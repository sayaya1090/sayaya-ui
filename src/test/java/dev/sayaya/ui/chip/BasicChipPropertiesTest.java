package dev.sayaya.ui.chip;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.ChipsElementBuilder.chips;
import static org.jboss.elemento.Elements.*;

public class BasicChipPropertiesTest {
    public static void test() {
        printSectionHeader("2. 기본 Chip 속성 (Basic Properties)");
        printDescription("모든 Chip에 공통으로 적용되는 속성들입니다.");
        printSeparator();

        var propertiesSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(propertiesSection);

        propertiesSection.appendChild(h(3).text("Basic Properties").element());

        // Label
        var labelExample = addExampleCode(propertiesSection,
            "📘 Label",
            "Chip의 텍스트 레이블을 설정합니다.",
            """
            var chip = chips()
                .assist()
                .label("Test Label")
                .element();
            """);
        var chip = chips()
                .assist()
                .label("Test Label")
                .element();
        labelExample.addInteractiveDemo(chip, false);
        assertEquals("칩 label: Test Label",
                "Test Label", chip.label);

        // Disabled
        var disabledExample = addExampleCode(propertiesSection,
            "📘 Disabled",
            "Chip을 비활성화합니다.",
            """
            var chip = chips()
                .assist()
                .label("Disabled")
                .disabled()
                .element();
            """);
        var disabledChip = chips()
                .assist()
                .label("Disabled")
                .disabled()
                .element();
        disabledExample.addInteractiveDemo(disabledChip, false);
        assertTrue("칩 disabled: true",
                disabledChip.disabled);

        // Always Focusable
        var focusableExample = addExampleCode(propertiesSection,
            "📘 Always Focusable",
            "비활성화 상태에서도 포커스를 받을 수 있도록 설정합니다.",
            """
            var chip = chips()
                .assist()
                .label("Focusable")
                .disabled()
                .alwaysFocusable(true)
                .element();
            """);
        var focusableChip = chips()
                .assist()
                .label("Focusable")
                .disabled()
                .alwaysFocusable(true)
                .element();
        focusableExample.addInteractiveDemo(focusableChip, false);
        assertTrue("칩 alwaysFocusable: true",
                focusableChip.alwaysFocusable);
    }
}
