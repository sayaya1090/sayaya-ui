package dev.sayaya.ui.chip;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.ChipsElementBuilder.chips;
import static org.jboss.elemento.Elements.*;

public class ChipTypesTest {
    public static void test() {
        printSectionHeader("1. Chip 종류 (Chip Types)");
        printDescription("Material Design Chip은 4가지 타입을 제공합니다:");
        printDescription("- Assist: 작업을 시작하거나 뷰로 이동");
        printDescription("- Filter: 필터링 옵션 표시");
        printDescription("- Input: 사용자 입력 표시");
        printDescription("- Suggestion: 추천 옵션 표시");
        printSeparator();

        var typesSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(typesSection);

        typesSection.appendChild(h(3).text("Chip Types").element());

        // Assist Chip
        var assistExample = addExampleCode(typesSection,
            "📘 Assist Chip",
            "작업을 시작하거나 다른 뷰로 이동하는 데 사용됩니다.",
            """
            var chip = chips()
                .assist()
                .label("Assist")
                .element();
            """);
        var assistChip = chips()
                .assist()
                .label("Assist")
                .element();
        assistExample.addInteractiveDemo(assistChip, false);
        assertEquals("assist 칩: 태그명은 md-assist-chip",
                "MD-ASSIST-CHIP", assistChip.tagName);

        // Filter Chip
        var filterExample = addExampleCode(typesSection,
            "📘 Filter Chip",
            "필터링 옵션을 표시하며 선택/해제가 가능합니다.",
            """
            var chip = chips()
                .filter()
                .label("Filter")
                .element();
            """);
        var filterChip = chips()
                .filter()
                .label("Filter")
                .element();
        filterExample.addInteractiveDemo(filterChip, false);
        assertEquals("filter 칩: 태그명은 md-filter-chip",
                "MD-FILTER-CHIP", filterChip.tagName);

        // Input Chip
        var inputExample = addExampleCode(typesSection,
            "📘 Input Chip",
            "사용자 입력을 표시하며 제거가 가능합니다.",
            """
            var chip = chips()
                .input()
                .label("Input")
                .element();
            """);
        var inputChip = chips()
                .input()
                .label("Input")
                .element();
        inputExample.addInteractiveDemo(inputChip, false);
        assertEquals("input 칩: 태그명은 md-input-chip",
                "MD-INPUT-CHIP", inputChip.tagName);

        // Suggestion Chip
        var suggestionExample = addExampleCode(typesSection,
            "📘 Suggestion Chip",
            "추천 옵션을 표시합니다.",
            """
            var chip = chips()
                .suggestion()
                .label("Suggestion")
                .element();
            """);
        var suggestionChip = chips()
                .suggestion()
                .label("Suggestion")
                .element();
        suggestionExample.addInteractiveDemo(suggestionChip, false);
        assertEquals("suggestion 칩: 태그명은 md-suggestion-chip",
                "MD-SUGGESTION-CHIP", suggestionChip.tagName);
    }
}
