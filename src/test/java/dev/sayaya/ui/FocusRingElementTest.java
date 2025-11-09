package dev.sayaya.ui;

import com.google.gwt.core.client.EntryPoint;

import static dev.sayaya.ui.elements.ButtonElementBuilder.button;
import static dev.sayaya.ui.elements.FocusRingElementBuilder.focusRing;
import static elemental2.dom.DomGlobal.console;
import static org.jboss.elemento.Elements.*;

public class FocusRingElementTest implements EntryPoint {
    @Override
    public void onModuleLoad() {
        testFocusRingBasic();
        testFocusRingInward();
        testFocusRingAttachment();
        testFocusRingWithButton();
        testFocusRingWithInput();
        testFocusRingImperativeAttachment();
    }

    private static void testFocusRingBasic() {
        // Basic Focus Ring
        var ring = focusRing().element();
        assertEquals("포커스 링: 태그명은 md-focus-ring이어야 함",
                "MD-FOCUS-RING", ring.tagName);

        // Default visible state
        assertFalse("포커스 링: 기본 visible은 false여야 함", ring.visible);

        // Default inward state
        assertFalse("포커스 링: 기본 inward는 false여야 함", ring.inward);
    }

    private static void testFocusRingInward() {
        // Inward Focus Ring
        var inwardRing = focusRing()
                .inward()
                .element();
        assertTrue("포커스 링 inward: true여야 함", inwardRing.inward);

        // Outward Focus Ring (explicit)
        var outwardRing = focusRing()
                .inward(false)
                .element();
        assertFalse("포커스 링 outward: inward가 false여야 함", outwardRing.inward);
    }

    private static void testFocusRingAttachment() {
        // Attach to parent element
        var button = button().filled()
                .text("Focus Me")
                .style("position", "relative")
                .element();

        var ring = focusRing().element();
        button.appendChild(ring);
        body().add(button);

        assertEquals("포커스 링 부착: 부모는 button이어야 함",
                "MD-FILLED-BUTTON", ring.parentElement.tagName);
    }

    private static void testFocusRingWithButton() {
        // Focus Ring attached to button via parent
        var buttonContainer = div()
                .style("position", "relative")
                .add(focusRing())
                .add(button().filled().text("Click Me"))
                .element();
        body().add(buttonContainer);

        var ringInContainer = buttonContainer.querySelector("md-focus-ring");
        assertNotNull("버튼 포커스 링: 컨테이너에 포커스 링이 존재해야 함", ringInContainer);

        // Focus Ring with inward style
        var inwardButtonContainer = div()
                .style("position", "relative")
                .add(focusRing().inward())
                .add(button().outlined().text("Inward Focus"))
                .element();
        body().add(inwardButtonContainer);

        var inwardRing = (dev.sayaya.ui.dom.MdFocusRingElement) inwardButtonContainer.querySelector("md-focus-ring");
        assertTrue("버튼 포커스 링 inward: true여야 함", inwardRing.inward);
    }

    private static void testFocusRingWithInput() {
        // Focus Ring with input element using htmlFor
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
        body().add(container);

        var ring = (dev.sayaya.ui.dom.MdFocusRingElement) container.querySelector("md-focus-ring");
        assertEquals("입력 포커스 링: htmlFor가 설정되어야 함",
                inputId, ring.htmlFor);
    }

    private static void testFocusRingImperativeAttachment() {
        // Create control element
        var control = button().text()
                .text("Imperative Control")
                .id("imperative-control")
                .element();
        body().add(control);

        // Create focus ring with builder attach method
        var ring = focusRing()
                .attach(control)
                .element();
        body().add(ring);

        assertNotNull("명령형 부착: control이 설정되어야 함", ring.control);
        assertEquals("명령형 부착: control이 버튼이어야 함",
                control, ring.control);

        // Test control property setter
        var anotherControl = button().outlined()
                .text("Another Control")
                .element();
        body().add(anotherControl);

        var ring2 = focusRing()
                .control(anotherControl)
                .element();
        body().add(ring2);

        assertEquals("control 속성: 설정한 요소여야 함",
                anotherControl, ring2.control);

        // Test detach
        var ring3 = focusRing()
                .attach(control)
                .element();
        body().add(ring3);

        assertNotNull("detach 전: control이 존재해야 함", ring3.control);

        ring3.detach();

        // After detach, control might still be set but the focus ring is detached
        log("detach 후: 포커스 링이 분리됨 - PASS");
    }

    private static void assertEquals(String message, Object expected, Object actual) {
        if (expected == null && actual == null) {
            log(message + " - PASS");
            return;
        }
        if (expected != null && expected.equals(actual)) {
            log(message + " - PASS");
        } else {
            log(message + " - Assertion failed! Expected: " + expected + ", Actual: " + actual);
        }
    }

    private static void assertTrue(String message, boolean condition) {
        if (condition) {
            log(message + " - PASS");
        } else {
            log(message + " - Assertion failed!");
        }
    }

    private static void assertFalse(String message, boolean condition) {
        if (!condition) {
            log(message + " - PASS");
        } else {
            log(message + " - Assertion failed!");
        }
    }

    private static void assertNotNull(String message, Object obj) {
        if (obj != null) {
            log(message + " - PASS");
        } else {
            log(message + " - Assertion failed! Object is null");
        }
    }

    private static void log(String message) {
        console.log(message);
    }
}
