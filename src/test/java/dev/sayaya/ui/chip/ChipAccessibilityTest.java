package dev.sayaya.ui.chip;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.ChipsElementBuilder.chips;
import static org.jboss.elemento.Elements.*;

public class ChipAccessibilityTest {
    public static void test() {
        printSectionHeader("9. 접근성 (Accessibility)");
        printDescription("Chip의 접근성 속성들입니다.");
        printSeparator();

        var accessibilitySection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(accessibilitySection);

        accessibilitySection.appendChild(h(3).text("Accessibility").element());

        // Aria Label
        var ariaLabelExample = addExampleCode(accessibilitySection,
            "📘 Aria Label",
            "Chip의 접근성 레이블을 설정합니다.",
            """
            var chip = chips()
                .assist()
                .label("Delete")
                .ariaLabel("Delete item from list")
                .element();
            """);
        var ariaChip = chips()
                .assist()
                .label("Delete")
                .ariaLabel("Delete item from list")
                .element();
        ariaLabelExample.addInteractiveDemo(ariaChip, false);
        assertEquals("칩 aria-label: Delete item from list",
                "Delete item from list",
                ariaChip.getAttribute("aria-label"));

        // Disabled with Aria Label
        var disabledAriaExample = addExampleCode(accessibilitySection,
            "📘 Disabled with Aria Label",
            "비활성화된 Chip에 접근성 레이블을 추가합니다.",
            """
            var chip = chips()
                .assist()
                .label("Unavailable")
                .disabled()
                .ariaLabel("This action is currently unavailable")
                .element();
            """);
        var disabledAriaChip = chips()
                .assist()
                .label("Unavailable")
                .disabled()
                .ariaLabel("This action is currently unavailable")
                .element();
        disabledAriaExample.addInteractiveDemo(disabledAriaChip, false);
        assertTrue("칩 disabled: true",
                disabledAriaChip.disabled);
        assertEquals("disabled 칩 aria-label: This action is currently unavailable",
                "This action is currently unavailable",
                disabledAriaChip.getAttribute("aria-label"));
    }
}
