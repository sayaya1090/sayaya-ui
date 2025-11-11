package dev.sayaya.ui.chip;

import dev.sayaya.ui.dom.MdChipElement;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.ChipsElementBuilder.chips;
import static org.jboss.elemento.Elements.*;

public class SuggestionChipPropertiesTest {
    public static void test() {
        printSectionHeader("6. Suggestion Chip 속성 (Suggestion Chip Properties)");
        printDescription("Suggestion Chip 전용 속성들입니다.");
        printSeparator();

        var suggestionSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(suggestionSection);

        suggestionSection.appendChild(h(3).text("Suggestion Chip Properties").element());

        // Elevated
        var elevatedExample = addExampleCode(suggestionSection,
            "📘 Elevated",
            "그림자 효과가 있는 elevated 스타일을 적용합니다.",
            """
            var chip = chips()
                .suggestion()
                .label("Elevated Suggestion")
                .elevated()
                .element();
            """);
        var elevatedChip = (MdChipElement.MdSuggestionChipElement) chips()
                .suggestion()
                .label("Elevated Suggestion")
                .elevated()
                .element();
        elevatedExample.addInteractiveDemo(elevatedChip, false);
        assertTrue("suggestion 칩 elevated: true",
                elevatedChip.elevated);

        // Href
        var linkExample = addExampleCode(suggestionSection,
            "📘 Link (Href)",
            "Chip을 링크로 만듭니다.",
            """
            var chip = chips()
                .suggestion()
                .label("Link")
                .href("https://example.com")
                .element();
            """);
        var linkChip = (MdChipElement.MdSuggestionChipElement) chips()
                .suggestion()
                .label("Link")
                .href("https://example.com")
                .element();
        linkExample.addInteractiveDemo(linkChip, false);
        assertEquals("suggestion 칩 href: https://example.com",
                "https://example.com", linkChip.href);

        // Target
        var targetExample = addExampleCode(suggestionSection,
            "📘 Target",
            "링크를 새 탭에서 열도록 설정합니다.",
            """
            var chip = chips()
                .suggestion()
                .label("New Tab")
                .href("https://example.com")
                .target("_blank")
                .element();
            """);
        var targetChip = (MdChipElement.MdSuggestionChipElement) chips()
                .suggestion()
                .label("New Tab")
                .href("https://example.com")
                .target("_blank")
                .element();
        targetExample.addInteractiveDemo(targetChip, false);
        assertEquals("suggestion 칩 target: _blank",
                "_blank", targetChip.target);
    }
}
