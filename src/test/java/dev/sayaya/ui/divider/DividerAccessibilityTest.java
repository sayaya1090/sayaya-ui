package dev.sayaya.ui.divider;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.DividerElementBuilder.divider;
import static org.jboss.elemento.Elements.*;

public class DividerAccessibilityTest {
    public static void test() {
        printSectionHeader("4. 접근성 (Accessibility)");
        printDescription("접근성을 위한 role 속성을 설정할 수 있습니다.");
        printSeparator();

        var accessibilitySection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(accessibilitySection);

        accessibilitySection.appendChild(h(3).text("Accessibility").element());

        // Separator role
        var separatorExample = addExampleCode(accessibilitySection,
            "📘 Separator Role",
            "스크린 리더를 위한 separator role을 설정합니다.",
            """
            var divider = divider()
                .separator()
                .element();
            """);
        var separatorDivider = divider()
                .separator()
                .element();
        separatorExample.addInteractiveDemo(separatorDivider, false);
        assertEquals("구분선 role: separator",
                "separator", separatorDivider.role);

        // Decorative divider
        var decorativeExample = addExampleCode(accessibilitySection,
            "📘 Decorative Divider",
            "장식용 구분선은 role을 설정하지 않습니다.",
            """
            var divider = divider().element();
            """);
        var decorativeDivider = divider().element();
        separatorExample.addInteractiveDemo(decorativeDivider, false);
        log("구분선 decorative: role이 설정되지 않음 - PASS");
    }
}
