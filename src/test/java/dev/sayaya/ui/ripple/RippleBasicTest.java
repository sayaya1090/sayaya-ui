package dev.sayaya.ui.ripple;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.RippleElementBuilder.ripple;
import static org.jboss.elemento.Elements.*;

public class RippleBasicTest {
    public static void test() {
        printSectionHeader("1. 기본 Ripple (Basic Ripple)");
        printDescription("Ripple은 터치/클릭 시 파급 효과를 제공합니다.");
        printSeparator();

        var rippleSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(rippleSection);

        rippleSection.appendChild(h(3).text("Basic Ripple").element());

        // Basic ripple
        var basicExample = addExampleCode(rippleSection,
            "📘 Basic Ripple",
            "컨테이너에 기본 ripple을 추가합니다.",
            """
            var container = div()
                .style("position", "relative")
                .style("width", "200px")
                .style("height", "100px")
                .add(ripple())
                .element();
            """);
        var container = div()
                .style("position", "relative")
                .style("width", "200px")
                .style("height", "100px")
                .add(ripple())
                .element();
        basicExample.addInteractiveDemo(container, false);

        var rippleElement = container.querySelector("md-ripple");
        assertNotNull("기본 ripple: ripple 요소가 존재", rippleElement);
        assertEquals("기본 ripple: 태그명은 MD-RIPPLE",
                "MD-RIPPLE", rippleElement.tagName);

        // Disabled property
        var disabledExample = addExampleCode(rippleSection,
            "📘 Disabled Ripple",
            "비활성화된 ripple입니다.",
            """
            var ripple = ripple()
                .disabled(true)
                .element();
            """);
        var disabledRipple = ripple()
                .disabled(true)
                .element();
        disabledExample.addInteractiveDemo(disabledRipple, false);

        assertTrue("disabled 속성: true", disabledRipple.disabled);

        // Enable disabled ripple
        disabledRipple.disabled = false;
        assertFalse("비활성화 ripple: disabled를 false로 변경 가능", disabledRipple.disabled);

        // Disable again
        disabledRipple.disabled = true;
        assertTrue("비활성화 ripple: 다시 disabled로 변경 가능", disabledRipple.disabled);
    }
}
