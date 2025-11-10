package dev.sayaya.ui;

import static elemental2.dom.DomGlobal.console;

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

    public static void addExampleCode(elemental2.dom.HTMLElement container, String title, String description, String code) {
        var exampleDiv = org.jboss.elemento.Elements.div()
                .style("margin-top", "20px")
                .style("padding", "15px")
                .style("background", "#f8f9fa")
                .style("border-left", "4px solid #007bff")
                .style("border-radius", "4px")
                .element();

        var titleElement = org.jboss.elemento.Elements.div()
                .style("font-weight", "bold")
                .style("color", "#007bff")
                .style("margin-bottom", "8px")
                .text(title)
                .element();
        exampleDiv.appendChild(titleElement);

        if (description != null && !description.isEmpty()) {
            var descElement = org.jboss.elemento.Elements.div()
                    .style("color", "#666")
                    .style("margin-bottom", "10px")
                    .style("font-size", "14px")
                    .text(description)
                    .element();
            exampleDiv.appendChild(descElement);
        }

        var codeElement = org.jboss.elemento.Elements.pre()
                .add(org.jboss.elemento.Elements.code().text(code))
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
    }
}
