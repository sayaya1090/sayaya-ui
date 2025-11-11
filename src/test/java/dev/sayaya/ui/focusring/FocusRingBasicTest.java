package dev.sayaya.ui.focusring;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.FocusRingElementBuilder.focusRing;
import static org.jboss.elemento.Elements.*;

public class FocusRingBasicTest {
    public static void test() {
        printSectionHeader("1. 기본 Focus Ring (Basic Focus Ring)");
        printDescription("Focus Ring은 요소에 포커스가 있을 때 시각적 표시를 제공합니다.");
        printSeparator();

        var focusRingSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(focusRingSection);

        focusRingSection.appendChild(h(3).text("Basic Focus Ring").element());

        // Basic Focus Ring
        var basicExample = addExampleCode(focusRingSection,
            "📘 Basic Focus Ring",
            "기본 포커스 링입니다.",
            """
            var ring = focusRing().element();
            """);
        var ring = focusRing().element();
        basicExample.addInteractiveDemo(ring, false);
        assertEquals("포커스 링: 태그명은 md-focus-ring",
                "MD-FOCUS-RING", ring.tagName);

        assertFalse("포커스 링: 기본 visible은 false", ring.visible);
        assertFalse("포커스 링: 기본 inward는 false", ring.inward);

        // Inward Focus Ring
        var inwardExample = addExampleCode(focusRingSection,
            "📘 Inward Focus Ring",
            "안쪽으로 표시되는 포커스 링입니다.",
            """
            var ring = focusRing()
                .inward()
                .element();
            """);
        var inwardRing = focusRing()
                .inward()
                .element();
        inwardExample.addInteractiveDemo(inwardRing, false);
        assertTrue("포커스 링 inward: true", inwardRing.inward);

        // Outward Focus Ring (explicit)
        var outwardExample = addExampleCode(focusRingSection,
            "📘 Outward Focus Ring",
            "바깥쪽으로 표시되는 포커스 링입니다.",
            """
            var ring = focusRing()
                .inward(false)
                .element();
            """);
        var outwardRing = focusRing()
                .inward(false)
                .element();
        outwardExample.addInteractiveDemo(outwardRing, false);
        assertFalse("포커스 링 outward: inward가 false", outwardRing.inward);
    }
}
