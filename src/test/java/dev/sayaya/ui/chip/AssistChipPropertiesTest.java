package dev.sayaya.ui.chip;

import dev.sayaya.ui.dom.MdChipElement;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.ChipsElementBuilder.chips;
import static org.jboss.elemento.Elements.*;

public class AssistChipPropertiesTest {
    public static void test() {
        printSectionHeader("3. Assist Chip 속성 (Assist Chip Properties)");
        printDescription("Assist Chip 전용 속성들입니다.");
        printSeparator();

        var assistSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(assistSection);

        assistSection.appendChild(h(3).text("Assist Chip Properties").element());

        // Elevated
        var elevatedExample = addExampleCode(assistSection,
            "📘 Elevated",
            "그림자 효과가 있는 elevated 스타일을 적용합니다.",
            """
            var chip = chips()
                .assist()
                .label("Elevated")
                .elevated()
                .element();
            """);
        var elevatedChip = (MdChipElement.MdAssistChipElement) chips()
                .assist()
                .label("Elevated")
                .elevated()
                .element();
        elevatedExample.addInteractiveDemo(elevatedChip, false);
        assertTrue("assist 칩 elevated: true",
                elevatedChip.elevated);

        // Href
        var linkExample = addExampleCode(assistSection,
            "📘 Link (Href)",
            "Chip을 링크로 만듭니다.",
            """
            var chip = chips()
                .assist()
                .label("Link")
                .href("https://example.com")
                .element();
            """);
        var linkChip = (MdChipElement.MdAssistChipElement) chips()
                .assist()
                .label("Link")
                .href("https://example.com")
                .element();
        linkExample.addInteractiveDemo(linkChip, false);
        assertEquals("assist 칩 href: https://example.com",
                "https://example.com", linkChip.href);

        // Target
        var targetExample = addExampleCode(assistSection,
            "📘 Target",
            "링크를 새 탭에서 열도록 설정합니다.",
            """
            var chip = chips()
                .assist()
                .label("New Tab")
                .href("https://example.com")
                .target("_blank")
                .element();
            """);
        var targetChip = (MdChipElement.MdAssistChipElement) chips()
                .assist()
                .label("New Tab")
                .href("https://example.com")
                .target("_blank")
                .element();
        targetExample.addInteractiveDemo(targetChip, false);
        assertEquals("assist 칩 target: _blank",
                "_blank", targetChip.target);
    }
}
