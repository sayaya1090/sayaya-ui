package dev.sayaya.ui.chip;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.ChipsElementBuilder.chips;
import static org.jboss.elemento.Elements.*;

public class ChipStateTest {
    public static void test() {
        printSectionHeader("12. Chip 상태 관리 (State Management)");
        printDescription("Chip의 활성화/비활성화 상태를 제어합니다.");
        printSeparator();

        var stateSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(stateSection);

        stateSection.appendChild(h(3).text("State Management").element());

        // Disabled Assist Chip
        var disabledExample = addExampleCode(stateSection,
            "📘 Disabled Chip",
            "비활성화된 Chip입니다.",
            """
            var chip = chips()
                .assist()
                .label("Disabled Assist")
                .disabled()
                .element();
            """);
        var disabledAssist = chips()
                .assist()
                .label("Disabled Assist")
                .disabled()
                .element();
        disabledExample.addInteractiveDemo(disabledAssist, false);
        assertTrue("disabled assist 칩: disabled가 true",
                disabledAssist.disabled);

        // Enable/Disable Toggle
        var toggleExample = addExampleCode(stateSection,
            "📘 Enable/Disable Toggle",
            "Chip의 활성화 상태를 동적으로 제어합니다.",
            """
            var chipBuilder = chips()
                .filter()
                .label("Toggle");
            var chip = chipBuilder.element();

            // 비활성화
            chipBuilder.disabled(true);

            // 활성화
            chipBuilder.enabled();
            chipBuilder.enabled(false);  // 다시 비활성화
            """);
        var toggleChipBuilder = chips()
                .filter()
                .label("Toggle");
        var toggleChip = toggleChipBuilder.element();
        toggleExample.addInteractiveDemo(toggleChip, false);
        assertFalse("toggle 칩: 초기에는 활성화",
                toggleChip.disabled);

        toggleChipBuilder.disabled(true);
        assertTrue("toggle 칩: disabled(true) 후 비활성화",
                toggleChip.disabled);

        toggleChipBuilder.enabled();
        assertFalse("toggle 칩: enabled() 후 활성화",
                toggleChip.disabled);

        toggleChipBuilder.enabled(false);
        assertTrue("toggle 칩: enabled(false) 후 비활성화",
                toggleChip.disabled);
    }
}
