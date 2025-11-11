package dev.sayaya.ui.icon;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.IconElementBuilder.icon;
import static org.jboss.elemento.Elements.*;

public class IconAccessibilityTest {
    public static void test() {
        printSectionHeader("2. 접근성 (Accessibility)");
        printDescription("아이콘에 aria-label을 추가하여 접근성을 향상시킵니다.");
        printSeparator();

        var accessibilitySection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(accessibilitySection);

        accessibilitySection.appendChild(h(3).text("Accessibility").element());

        // Icon with aria-label
        var ariaExample = addExampleCode(accessibilitySection,
            "📘 Icon with Aria Label",
            "스크린 리더를 위한 aria-label을 설정합니다.",
            """
            var icon = icon("home")
                .attr("aria-label", "Go to home")
                .element();
            """);
        var accessibleIcon = icon("home")
                .attr("aria-label", "Go to home")
                .element();
        ariaExample.addInteractiveDemo(accessibleIcon, false);
        assertEquals("접근성: aria-label이 설정됨",
                "Go to home", accessibleIcon.getAttribute("aria-label"));

        // Accessible icon with span
        var spanExample = addExampleCode(accessibilitySection,
            "📘 Icon with Span",
            "내부 span 요소에 aria-label을 설정합니다.",
            """
            var icon = icon()
                .attr("tabindex", "-1")
                .add(span()
                    .attr("aria-label", "home")
                    .text("&#xe88a"))
                .element();
            """);
        var accessibleCodepointIcon = icon()
                .attr("tabindex", "-1")
                .add(span()
                        .attr("aria-label", "home")
                        .text("&#xe88a"))
                .element();
        spanExample.addInteractiveDemo(accessibleCodepointIcon, false);

        var spanElement = accessibleCodepointIcon.querySelector("span");
        assertNotNull("사용 예제: span이 아이콘 안에 존재", spanElement);
        assertEquals("사용 예제: span aria-label은 'home'",
                "home", spanElement.getAttribute("aria-label"));
        assertEquals("사용 예제: tabindex는 -1",
                "-1", accessibleCodepointIcon.getAttribute("tabindex"));
    }
}
