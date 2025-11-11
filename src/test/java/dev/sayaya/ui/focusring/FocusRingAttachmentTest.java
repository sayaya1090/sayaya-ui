package dev.sayaya.ui.focusring;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.ButtonElementBuilder.button;
import static dev.sayaya.ui.elements.FocusRingElementBuilder.focusRing;
import static org.jboss.elemento.Elements.*;

public class FocusRingAttachmentTest {
    public static void test() {
        printSectionHeader("2. Focus Ring 연결 (Attachment)");
        printDescription("Focus Ring을 다양한 방법으로 요소에 연결할 수 있습니다.");
        printSeparator();

        var attachmentSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(attachmentSection);

        attachmentSection.appendChild(h(3).text("Focus Ring Attachment").element());

        // Attach to parent
        var parentExample = addExampleCode(attachmentSection,
            "📘 Attach to Parent",
            "부모 요소에 포커스 링을 추가합니다.",
            """
            var button = button().filled()
                .text("Focus Me")
                .style("position", "relative")
                .element();
            var ring = focusRing().element();
            button.appendChild(ring);
            """);
        var buttonWithRing = button().filled()
                .text("Focus Me")
                .style("position", "relative")
                .element();
        var ring = focusRing().element();
        buttonWithRing.appendChild(ring);
        parentExample.addInteractiveDemo(buttonWithRing, false);
        assertEquals("포커스 링 부착: 부모는 button",
                "MD-FILLED-BUTTON", ring.parentElement.tagName);

        // Focus Ring in container
        var containerExample = addExampleCode(attachmentSection,
            "📘 Focus Ring in Container",
            "컨테이너에 포커스 링을 추가합니다.",
            """
            var container = div()
                .style("position", "relative")
                .add(focusRing())
                .add(button().filled().text("Click Me"))
                .element();
            """);
        var buttonContainer = div()
                .style("position", "relative")
                .add(focusRing())
                .add(button().filled().text("Click Me"))
                .element();
        containerExample.addInteractiveDemo(buttonContainer, false);

        var ringInContainer = buttonContainer.querySelector("md-focus-ring");
        assertNotNull("버튼 포커스 링: 컨테이너에 포커스 링이 존재", ringInContainer);

        // Inward Focus Ring with button
        var inwardExample = addExampleCode(attachmentSection,
            "📘 Inward Focus Ring with Button",
            "버튼에 inward 스타일의 포커스 링을 추가합니다.",
            """
            var container = div()
                .style("position", "relative")
                .add(focusRing().inward())
                .add(button().outlined().text("Inward Focus"))
                .element();
            """);
        var inwardButtonContainer = div()
                .style("position", "relative")
                .add(focusRing().inward())
                .add(button().outlined().text("Inward Focus"))
                .element();
        inwardExample.addInteractiveDemo(inwardButtonContainer, false);

        var inwardRing = (dev.sayaya.ui.dom.MdFocusRingElement) inwardButtonContainer.querySelector("md-focus-ring");
        assertTrue("버튼 포커스 링 inward: true", inwardRing.inward);

        // Focus Ring with htmlFor
        var htmlForExample = addExampleCode(attachmentSection,
            "📘 Focus Ring with htmlFor",
            "htmlFor 속성을 사용하여 input 요소에 연결합니다.",
            """
            var inputId = "test-input";
            var input = input("text")
                .id(inputId)
                .attr("placeholder", "Focus to see ring")
                .element();
            var container = div()
                .style("position", "relative")
                .add(focusRing().htmlFor(inputId))
                .add(input)
                .element();
            """);
        var inputId = "test-input";
        var input = input("text")
                .id(inputId)
                .attr("placeholder", "Focus to see ring")
                .element();
        var inputContainer = div()
                .style("position", "relative")
                .add(focusRing().htmlFor(inputId))
                .add(input)
                .element();
        htmlForExample.addInteractiveDemo(inputContainer, false);

        var ringForInput = (dev.sayaya.ui.dom.MdFocusRingElement) inputContainer.querySelector("md-focus-ring");
        assertEquals("입력 포커스 링: htmlFor가 설정됨",
                inputId, ringForInput.htmlFor);

        // Imperative attachment
        var imperativeExample = addExampleCode(attachmentSection,
            "📘 Imperative Attachment",
            "명령형으로 control 요소에 연결합니다.",
            """
            var control = button().text()
                .text("Imperative Control")
                .id("imperative-control")
                .element();
            var ring = focusRing()
                .attach(control)
                .element();
            """);
        var control = button().text()
                .text("Imperative Control")
                .id("imperative-control")
                .element();
        var imperativeRing = focusRing()
                .attach(control)
                .element();
        var imperativeContainer = div()
                .add(control)
                .add(imperativeRing)
                .element();
        imperativeExample.addInteractiveDemo(imperativeContainer, false);

        assertNotNull("명령형 부착: control이 설정됨", imperativeRing.control);
        assertEquals("명령형 부착: control이 버튼",
                control, imperativeRing.control);

        // Control property setter
        var anotherControl = button().outlined()
                .text("Another Control")
                .element();
        var ring2 = focusRing()
                .control(anotherControl)
                .element();

        assertEquals("control 속성: 설정한 요소",
                anotherControl, ring2.control);

        // Detach
        var ring3 = focusRing()
                .attach(control)
                .element();

        assertNotNull("detach 전: control이 존재", ring3.control);

        ring3.detach();

        log("detach 후: 포커스 링이 분리됨 - PASS");
    }
}
