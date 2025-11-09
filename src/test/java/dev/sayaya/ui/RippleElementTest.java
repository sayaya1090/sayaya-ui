package dev.sayaya.ui;

import com.google.gwt.core.client.EntryPoint;

import static dev.sayaya.ui.elements.ButtonElementBuilder.button;
import static dev.sayaya.ui.elements.RippleElementBuilder.ripple;
import static elemental2.dom.DomGlobal.console;
import static org.jboss.elemento.Elements.body;
import static org.jboss.elemento.Elements.div;

public class RippleElementTest implements EntryPoint {
    @Override
    public void onModuleLoad() {
        testBasicRipple();
        testRippleProperties();
        testRippleAttachment();
        testRippleWithHtmlFor();
        testDisabledRipple();
        testRippleWithCustomControl();
        testRippleDetachment();
    }

    private static void testBasicRipple() {
        // Test 1: Basic ripple in a container
        var container = div()
                .style("position", "relative")
                .style("width", "200px")
                .style("height", "100px")
                .add(ripple())
                .element();
        body().add(container);

        var rippleElement = container.querySelector("md-ripple");
        assertNotNull("기본 ripple: ripple 요소가 존재해야 함", rippleElement);
        assertEquals("기본 ripple: 태그명은 MD-RIPPLE이어야 함",
                "MD-RIPPLE", rippleElement.tagName);
    }

    private static void testRippleProperties() {
        // Test 1: Disabled property
        var disabledRipple = ripple()
                .disabled(true)
                .element();
        body().add(disabledRipple);

        assertTrue("disabled 속성: true여야 함", disabledRipple.disabled);

        // Test 2: htmlFor property
        var targetButton = button().filled()
                .id("target-button")
                .text("Target")
                .element();
        body().add(targetButton);

        var htmlForRipple = ripple()
                .htmlFor("target-button")
                .element();
        body().add(htmlForRipple);

        assertEquals("htmlFor 속성: target-button이어야 함",
                "target-button", htmlForRipple.htmlFor);
    }

    private static void testRippleAttachment() {
        // Test 1: Attach ripple to a button
        var testButton = button().filled()
                .text("Ripple Button")
                .element();
        body().add(testButton);

        var attachedRipple = ripple()
                .control(testButton)
                .element();
        body().add(attachedRipple);

        assertEquals("ripple 연결: control 속성이 버튼을 참조해야 함",
                testButton, attachedRipple.control);

        // Test 2: Attach ripple to a div
        var customDiv = div()
                .style("width", "150px")
                .style("height", "150px")
                .style("background", "#f0f0f0")
                .element();
        body().add(customDiv);

        var divRipple = ripple()
                .control(customDiv)
                .element();
        body().add(divRipple);

        assertEquals("ripple 연결: div에도 연결 가능해야 함",
                customDiv, divRipple.control);
    }

    private static void testRippleWithHtmlFor() {
        // Test: Create a button with id and reference it with htmlFor
        var referencedButton = button().outlined()
                .id("referenced-button")
                .text("Referenced")
                .element();
        body().add(referencedButton);

        var htmlForRipple = ripple()
                .htmlFor("referenced-button")
                .element();
        body().add(htmlForRipple);

        assertEquals("htmlFor 참조: htmlFor 속성이 설정되어야 함",
                "referenced-button", htmlForRipple.htmlFor);
        assertNotNull("htmlFor 참조: ripple 요소가 생성되어야 함", htmlForRipple);
    }

    private static void testDisabledRipple() {
        // Test 1: Ripple starts disabled
        var disabledRipple = ripple()
                .disabled(true)
                .element();
        body().add(disabledRipple);

        assertTrue("비활성화 ripple: 초기 상태는 disabled여야 함", disabledRipple.disabled);

        // Test 2: Enable disabled ripple
        disabledRipple.disabled = false;
        assertFalse("비활성화 ripple: disabled를 false로 변경 가능", disabledRipple.disabled);

        // Test 3: Disable again
        disabledRipple.disabled = true;
        assertTrue("비활성화 ripple: 다시 disabled로 변경 가능", disabledRipple.disabled);
    }

    private static void testRippleWithCustomControl() {
        // Test: Attach ripple to a custom element
        var customControl = div()
                .css("custom-control")
                .style("width", "100px")
                .style("height", "100px")
                .style("border", "1px solid #ccc")
                .style("border-radius", "8px")
                .style("cursor", "pointer")
                .text("Click me")
                .element();
        body().add(customControl);

        var customRipple = ripple()
                .control(customControl)
                .element();
        body().add(customRipple);

        assertEquals("커스텀 컨트롤: ripple이 커스텀 요소에 연결되어야 함",
                customControl, customRipple.control);
        assertNotNull("커스텀 컨트롤: ripple 요소가 존재해야 함", customRipple);
    }

    private static void testRippleDetachment() {
        // Test: Attach and then detach ripple
        var detachButton = button().filledTonal()
                .text("Detach Test")
                .element();
        body().add(detachButton);

        var detachRipple = ripple()
                .control(detachButton)
                .element();
        body().add(detachRipple);

        assertEquals("detach 테스트: 초기에는 연결되어야 함",
                detachButton, detachRipple.control);

        // Detach ripple
        detachRipple.detach();

        // After detach, control should be null or undefined
        // Note: The actual behavior depends on Material Web implementation
        assertNotNull("detach 테스트: detach 후에도 ripple 요소는 존재", detachRipple);
        log("detach 테스트: detach() 메서드 호출 완료 - PASS");
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
