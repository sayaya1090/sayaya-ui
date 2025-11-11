package dev.sayaya.ui.chip;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.ChipsElementBuilder.chips;
import static org.jboss.elemento.Elements.*;

public class ChipIconTest {
    public static void test() {
        printSectionHeader("8. Chip 아이콘 (Icon Support)");
        printDescription("Chip에 아이콘을 추가할 수 있습니다.");
        printSeparator();

        var iconSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(iconSection);

        iconSection.appendChild(h(3).text("Icon Support").element());

        // Assist Chip with Icon
        var assistIconExample = addExampleCode(iconSection,
            "📘 Assist Chip with Icon",
            "Assist Chip에 아이콘을 추가합니다.",
            """
            var chip = chips()
                .assist()
                .label("Calendar")
                .icon("event")
                .element();
            """);
        var assistWithIcon = chips()
                .assist()
                .label("Calendar")
                .icon("event")
                .element();
        assistIconExample.addInteractiveDemo(assistWithIcon, false);
        var icon = assistWithIcon.querySelector("md-icon");
        assertNotNull("칩 아이콘: 아이콘이 존재",
                icon);
        assertEquals("칩 아이콘: slot은 icon",
                "icon", icon.getAttribute("slot"));

        // Filter Chip with Icon
        var filterIconExample = addExampleCode(iconSection,
            "📘 Filter Chip with Icon",
            "Filter Chip에 아이콘을 추가합니다.",
            """
            var chip = chips()
                .filter()
                .label("Starred")
                .icon("star")
                .element();
            """);
        var filterWithIcon = chips()
                .filter()
                .label("Starred")
                .icon("star")
                .element();
        filterIconExample.addInteractiveDemo(filterWithIcon, false);
        var filterIcon = filterWithIcon.querySelector("md-icon");
        assertNotNull("filter 칩 아이콘: 아이콘이 존재",
                filterIcon);
    }
}
