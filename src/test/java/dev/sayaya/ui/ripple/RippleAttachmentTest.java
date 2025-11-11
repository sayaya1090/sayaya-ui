package dev.sayaya.ui.ripple;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.ButtonElementBuilder.button;
import static dev.sayaya.ui.elements.RippleElementBuilder.ripple;
import static org.jboss.elemento.Elements.*;

public class RippleAttachmentTest {
    public static void test() {
        printSectionHeader("2. Ripple 연결 (Attachment)");
        printDescription("Ripple을 다양한 방법으로 요소에 연결할 수 있습니다.");
        printSeparator();

        var attachmentSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(attachmentSection);

        attachmentSection.appendChild(h(3).text("Ripple Attachment").element());

        // htmlFor property
        var htmlForExample = addExampleCode(attachmentSection,
            "📘 Ripple with htmlFor",
            "htmlFor 속성으로 버튼에 연결합니다.",
            """
            var targetButton = button().filled()
                .id("target-button")
                .text("Target")
                .element();
            var ripple = ripple()
                .htmlFor("target-button")
                .element();
            """);
        var targetButton = button().filled()
                .id("target-button")
                .text("Target")
                .element();
        var htmlForRipple = ripple()
                .htmlFor("target-button")
                .element();
        var htmlForContainer = div()
                .add(targetButton)
                .add(htmlForRipple)
                .element();
        htmlForExample.addInteractiveDemo(htmlForContainer, false);

        assertEquals("htmlFor 속성: target-button",
                "target-button", htmlForRipple.htmlFor);

        // Attach ripple to a button
        var buttonExample = addExampleCode(attachmentSection,
            "📘 Attach to Button",
            "버튼에 ripple을 연결합니다.",
            """
            var testButton = button().filled()
                .text("Ripple Button")
                .element();
            var ripple = ripple()
                .control(testButton)
                .element();
            """);
        var testButton = button().filled()
                .text("Ripple Button")
                .element();
        var attachedRipple = ripple()
                .control(testButton)
                .element();
        var buttonContainer = div()
                .add(testButton)
                .add(attachedRipple)
                .element();
        buttonExample.addInteractiveDemo(buttonContainer, false);

        assertEquals("ripple 연결: control 속성이 버튼을 참조",
                testButton, attachedRipple.control);

        // Attach ripple to a div
        var divExample = addExampleCode(attachmentSection,
            "📘 Attach to Div",
            "div 요소에 ripple을 연결합니다.",
            """
            var customDiv = div()
                .style("width", "150px")
                .style("height", "150px")
                .style("background", "#f0f0f0")
                .element();
            var ripple = ripple()
                .control(customDiv)
                .element();
            """);
        var customDiv = div()
                .style("width", "150px")
                .style("height", "150px")
                .style("background", "#f0f0f0")
                .element();
        var divRipple = ripple()
                .control(customDiv)
                .element();
        var divContainer = div()
                .add(customDiv)
                .add(divRipple)
                .element();
        divExample.addInteractiveDemo(divContainer, false);

        assertEquals("ripple 연결: div에도 연결 가능",
                customDiv, divRipple.control);

        // Custom control
        var customExample = addExampleCode(attachmentSection,
            "📘 Custom Control",
            "커스텀 요소에 ripple을 연결합니다.",
            """
            var customControl = div()
                .css("custom-control")
                .style("width", "100px")
                .style("height", "100px")
                .style("border", "1px solid #ccc")
                .style("border-radius", "8px")
                .style("cursor", "pointer")
                .text("Click me")
                .element();
            var ripple = ripple()
                .control(customControl)
                .element();
            """);
        var customControl = div()
                .css("custom-control")
                .style("width", "100px")
                .style("height", "100px")
                .style("border", "1px solid #ccc")
                .style("border-radius", "8px")
                .style("cursor", "pointer")
                .text("Click me")
                .element();
        var customRipple = ripple()
                .control(customControl)
                .element();
        var customContainer = div()
                .add(customControl)
                .add(customRipple)
                .element();
        customExample.addInteractiveDemo(customContainer, false);

        assertEquals("커스텀 컨트롤: ripple이 커스텀 요소에 연결됨",
                customControl, customRipple.control);
        assertNotNull("커스텀 컨트롤: ripple 요소가 존재", customRipple);

        // Detachment
        var detachExample = addExampleCode(attachmentSection,
            "📘 Detachment",
            "ripple을 분리합니다.",
            """
            var detachButton = button().filledTonal()
                .text("Detach Test")
                .element();
            var ripple = ripple()
                .control(detachButton)
                .element();
            ripple.detach();
            """);
        var detachButton = button().filledTonal()
                .text("Detach Test")
                .element();
        var detachRipple = ripple()
                .control(detachButton)
                .element();
        var detachContainer = div()
                .add(detachButton)
                .add(detachRipple)
                .element();
        detachExample.addInteractiveDemo(detachContainer, false);

        assertEquals("detach 테스트: 초기에는 연결됨",
                detachButton, detachRipple.control);

        detachRipple.detach();

        assertNotNull("detach 테스트: detach 후에도 ripple 요소는 존재", detachRipple);
        log("detach 테스트: detach() 메서드 호출 완료 - PASS");
    }
}
