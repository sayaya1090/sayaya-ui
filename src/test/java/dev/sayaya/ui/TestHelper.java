package dev.sayaya.ui;

import elemental2.dom.HTMLElement;

import static elemental2.dom.DomGlobal.console;
import static org.jboss.elemento.Elements.*;

public class TestHelper {
    public static void assertEquals(String message, Object expected, Object actual) {
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

    public static void assertEquals(String message, int expected, int actual) {
        if (expected == actual) {
            log(message + " - PASS");
        } else {
            log(message + " - Assertion failed! Expected: " + expected + ", Actual: " + actual);
        }
    }

    public static void assertTrue(String message, boolean condition) {
        if (condition) {
            log(message + " - PASS");
        } else {
            log(message + " - Assertion failed!");
        }
    }

    public static void assertFalse(String message, boolean condition) {
        if (!condition) {
            log(message + " - PASS");
        } else {
            log(message + " - Assertion failed!");
        }
    }

    public static void assertNotNull(String message, Object obj) {
        if (obj != null) {
            log(message + " - PASS");
        } else {
            log(message + " - Assertion failed! Object is null");
        }
    }

    public static void log(String message) {
        console.log(message);
    }

    public static void printSectionHeader(String header) {
        console.log(header);
    }

    public static void printDescription(String description) {
        console.log(description);
    }

    public static void printSeparator() {
        console.log("-".repeat(60) + "\n");
    }

    public static ExampleSection addExampleCode(HTMLElement container, String title, String description, String code) {
        var exampleDiv = div()
                .style("margin-top", "20px")
                .style("padding", "15px")
                .style("background", "#f8f9fa")
                .style("border-left", "4px solid #007bff")
                .style("border-radius", "4px")
                .element();

        var titleElement = div()
                .style("font-weight", "bold")
                .style("color", "#007bff")
                .style("margin-bottom", "8px")
                .text(title)
                .element();
        exampleDiv.appendChild(titleElement);

        if (description != null && !description.isEmpty()) {
            var descElement = div()
                    .style("color", "#666")
                    .style("margin-bottom", "10px")
                    .style("font-size", "14px")
                    .text(description)
                    .element();
            exampleDiv.appendChild(descElement);
        }

        var codeElement = pre()
                .add(code().text(code))
                .style("background", "#fff")
                .style("padding", "12px")
                .style("border-radius", "4px")
                .style("border", "1px solid #ddd")
                .style("overflow-x", "auto")
                .style("font-size", "13px")
                .style("margin", "0")
                .element();
        exampleDiv.appendChild(codeElement);

        container.appendChild(exampleDiv);

        return new ExampleSection(exampleDiv);
    }

    public record ExampleSection(HTMLElement container) {
        public HTMLElement addInteractiveDemo(HTMLElement element) {
            return addInteractiveDemo(element, true);
        }

        public HTMLElement addInteractiveDemo(HTMLElement element, boolean showStateInfo) {
            var demoCard = div()
                    .style("margin-top", "12px")
                    .style("padding", "15px")
                    .style("background", "#fff")
                    .style("border", "1px solid #dee2e6")
                    .style("border-radius", "4px")
                    .element();

            var demoLabel = div()
                    .style("font-size", "11px")
                    .style("color", "#6c757d")
                    .style("margin-bottom", "10px")
                    .style("font-weight", "500")
                    .text("▶ Interactive Demo")
                    .element();
            demoCard.appendChild(demoLabel);

            var elementWrapper = div()
                    .style("display", "flex")
                    .style("align-items", "center")
                    .style("gap", "12px")
                    .style("margin-bottom", showStateInfo ? "10px" : "0")
                    .element();
            elementWrapper.appendChild(element);
            demoCard.appendChild(elementWrapper);

            HTMLElement stateInfo = null;
            if (showStateInfo) {
                stateInfo = div()
                        .style("padding", "8px 10px")
                        .style("background", "#f8f9fa")
                        .style("border-left", "3px solid #007bff")
                        .style("border-radius", "2px")
                        .style("font-size", "12px")
                        .style("font-family", "monospace")
                        .style("color", "#495057")
                        .element();
                demoCard.appendChild(stateInfo);
            }

            container.appendChild(demoCard);

            return stateInfo;
        }
    }
}
