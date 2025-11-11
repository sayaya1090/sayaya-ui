package dev.sayaya.ui.divider;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.DividerElementBuilder.divider;
import static org.jboss.elemento.Elements.*;

public class DividerBasicTest {
    public static void test() {
        printSectionHeader("1. 기본 Divider (Basic Divider)");
        printDescription("Material Design Divider는 콘텐츠를 구분하는 선입니다.");
        printSeparator();

        var dividerSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(dividerSection);

        dividerSection.appendChild(h(3).text("Basic Divider").element());

        // Basic Divider
        var basicExample = addExampleCode(dividerSection,
            "📘 Basic Divider",
            "기본 구분선입니다.",
            """
            var divider = divider().element();
            """);
        var divider = divider().element();
        basicExample.addInteractiveDemo(divider, false);
        assertEquals("구분선: 태그명은 md-divider",
                "MD-DIVIDER", divider.tagName);

        // Default inset state
        assertFalse("구분선: 기본 inset은 false", divider.inset);
        assertFalse("구분선: 기본 insetStart는 false", divider.insetStart);
        assertFalse("구분선: 기본 insetEnd는 false", divider.insetEnd);
    }
}
