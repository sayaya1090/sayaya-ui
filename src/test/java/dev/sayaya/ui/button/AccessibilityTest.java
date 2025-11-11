package dev.sayaya.ui.button;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.ButtonElementBuilder.button;
import static org.jboss.elemento.Elements.*;

public class AccessibilityTest {
    public static void test() {
        printSectionHeader("6. 접근성 (Accessibility)");
        printDescription("스크린 리더 등을 위한 접근성 속성을 테스트합니다:");
        printDescription("- ariaLabel: 요소의 설명 텍스트");
        printSeparator();

        var accessibilitySection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(accessibilitySection);

        accessibilitySection.appendChild(h(3).text("Accessibility").element());

        // Aria Label
        var ariaExample = addExampleCode(accessibilitySection,
            "📘 Aria Label (접근성 레이블)",
            "스크린 리더가 읽을 설명 텍스트를 지정합니다. 아이콘만 있는 버튼에 필수입니다.",
            """
            var button = button().filled()
                .text("Delete")
                .ariaLabel("Delete item from list")
                .element();
            """);
        var ariaBtn = button().filled()
                .text("Delete")
                .ariaLabel("Delete item from list")
                .element();
        ariaExample.addInteractiveDemo(ariaBtn, false);

        assertEquals("aria-label: 올바르게 설정되어야 함",
                "Delete item from list",
                ariaBtn.getAttribute("aria-label"));

        // Icon Button with Aria Label
        var iconAriaExample = addExampleCode(accessibilitySection,
            "📘 Icon-only Button Accessibility (아이콘 전용 버튼)",
            "텍스트가 없는 아이콘 버튼은 반드시 aria-label을 제공해야 합니다.",
            """
            var button = button().icon("info")
                .ariaLabel("More information")
                .element();
            """);
        var iconAriaBtn = button().icon("info")
                .ariaLabel("More information")
                .element();
        iconAriaExample.addInteractiveDemo(iconAriaBtn, false);

        assertEquals("아이콘 버튼 aria-label: More information이어야 함",
                "More information",
                iconAriaBtn.getAttribute("aria-label"));

        // Disabled Button Accessibility
        var disabledAriaExample = addExampleCode(accessibilitySection,
            "📘 Disabled Button (비활성화 버튼)",
            "비활성화된 버튼도 aria-label로 이유를 설명하는 것이 좋습니다.",
            """
            var button = button().filled()
                .text("Edit")
                .disabled(true)
                .ariaLabel("Edit is currently unavailable")
                .element();
            """);
        var disabledAriaBtn = button().filled()
                .text("Edit")
                .disabled(true)
                .ariaLabel("Edit is currently unavailable")
                .element();
        disabledAriaExample.addInteractiveDemo(disabledAriaBtn, false);

        assertTrue("disabled 속성: true", disabledAriaBtn.disabled);
        assertEquals("disabled 버튼 aria-label",
                "Edit is currently unavailable",
                disabledAriaBtn.getAttribute("aria-label"));

        // Soft Disabled with Aria Label
        var softDisabledAriaExample = addExampleCode(accessibilitySection,
            "📘 Soft Disabled (소프트 비활성화)",
            "키보드 포커스를 유지하면서 비활성화 상태를 나타냅니다.",
            """
            var button = button().text()
                .text("Edit")
                .softDisabled(true)
                .ariaLabel("Edit is currently unavailable but you can focus")
                .element();
            """);
        var softDisabledAriaBtn = button().text()
                .text("Edit")
                .softDisabled(true)
                .ariaLabel("Edit is currently unavailable but you can focus")
                .element();
        softDisabledAriaExample.addInteractiveDemo(softDisabledAriaBtn, false);

        assertTrue("soft-disabled 속성: true", softDisabledAriaBtn.softDisabled);
        assertFalse("soft-disabled는 완전히 비활성화되지 않음", softDisabledAriaBtn.disabled);
    }
}
