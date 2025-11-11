package dev.sayaya.ui.divider;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.DividerElementBuilder.divider;
import static org.jboss.elemento.Elements.*;

public class DividerInsetTest {
    public static void test() {
        printSectionHeader("2. Inset Divider (양쪽 여백)");
        printDescription("양쪽에 여백이 있는 구분선입니다.");
        printSeparator();

        var insetSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(insetSection);

        insetSection.appendChild(h(3).text("Inset Divider").element());

        // Inset Divider
        var insetExample = addExampleCode(insetSection,
            "📘 Inset Divider",
            "양쪽에 여백이 있는 구분선입니다.",
            """
            var divider = divider()
                .inset()
                .element();
            """);
        var insetDivider = divider()
                .inset()
                .element();
        insetExample.addInteractiveDemo(insetDivider, false);
        assertTrue("구분선 inset: true", insetDivider.inset);
        assertFalse("구분선 inset: insetStart는 false", insetDivider.insetStart);
        assertFalse("구분선 inset: insetEnd는 false", insetDivider.insetEnd);

        // Inset with explicit boolean
        var insetExplicitExample = addExampleCode(insetSection,
            "📘 Inset with Boolean",
            "명시적으로 inset 값을 설정합니다.",
            """
            var divider = divider()
                .inset(true)
                .element();
            """);
        var insetDivider2 = divider()
                .inset(true)
                .element();
        insetExplicitExample.addInteractiveDemo(insetDivider2, false);
        assertTrue("구분선 inset(true): true", insetDivider2.inset);

        // Disable inset
        var fullExample = addExampleCode(insetSection,
            "📘 Full Width Divider",
            "inset을 비활성화하여 전체 너비를 사용합니다.",
            """
            var divider = divider()
                .inset(false)
                .element();
            """);
        var fullDivider = divider()
                .inset(false)
                .element();
        fullExample.addInteractiveDemo(fullDivider, false);
        assertFalse("구분선 inset(false): false", fullDivider.inset);
    }
}
