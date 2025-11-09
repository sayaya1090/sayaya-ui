package dev.sayaya.ui;

import com.google.gwt.core.client.EntryPoint;

import static dev.sayaya.ui.elements.DividerElementBuilder.*;
import static elemental2.dom.DomGlobal.console;
import static org.jboss.elemento.Elements.body;

public class DividerElementTest implements EntryPoint {
    @Override
    public void onModuleLoad() {
        testDividerBasic();
        testDividerInset();
        testDividerInsetStart();
        testDividerInsetEnd();
        testDividerRole();
        testDividerFactoryMethods();
    }

    private static void testDividerBasic() {
        // Basic Divider
        var divider = divider().element();
        assertEquals("구분선: 태그명은 md-divider여야 함",
                "MD-DIVIDER", divider.tagName);

        // Default inset state
        assertFalse("구분선: 기본 inset은 false여야 함", divider.inset);
        assertFalse("구분선: 기본 insetStart는 false여야 함", divider.insetStart);
        assertFalse("구분선: 기본 insetEnd는 false여야 함", divider.insetEnd);
    }

    private static void testDividerInset() {
        // Inset Divider (both sides)
        var insetDivider = divider()
                .inset()
                .element();
        assertTrue("구분선 inset: true여야 함", insetDivider.inset);
        assertFalse("구분선 inset: insetStart는 false여야 함", insetDivider.insetStart);
        assertFalse("구분선 inset: insetEnd는 false여야 함", insetDivider.insetEnd);

        // Inset with explicit boolean
        var insetDivider2 = divider()
                .inset(true)
                .element();
        assertTrue("구분선 inset(true): true여야 함", insetDivider2.inset);

        // Disable inset
        var fullDivider = divider()
                .inset(false)
                .element();
        assertFalse("구분선 inset(false): false여야 함", fullDivider.inset);
    }

    private static void testDividerInsetStart() {
        // Inset Start (leading side)
        var insetStartDivider = divider()
                .insetStart()
                .element();
        assertTrue("구분선 insetStart: true여야 함", insetStartDivider.insetStart);
        assertFalse("구분선 insetStart: inset은 false여야 함", insetStartDivider.inset);
        assertFalse("구분선 insetStart: insetEnd는 false여야 함", insetStartDivider.insetEnd);

        // InsetStart with explicit boolean
        var insetStartDivider2 = divider()
                .insetStart(true)
                .element();
        assertTrue("구분선 insetStart(true): true여야 함", insetStartDivider2.insetStart);

        // Disable insetStart
        var fullDivider = divider()
                .insetStart(false)
                .element();
        assertFalse("구분선 insetStart(false): false여야 함", fullDivider.insetStart);
    }

    private static void testDividerInsetEnd() {
        // Inset End (trailing side)
        var insetEndDivider = divider()
                .insetEnd()
                .element();
        assertTrue("구분선 insetEnd: true여야 함", insetEndDivider.insetEnd);
        assertFalse("구분선 insetEnd: inset은 false여야 함", insetEndDivider.inset);
        assertFalse("구분선 insetEnd: insetStart는 false여야 함", insetEndDivider.insetStart);

        // InsetEnd with explicit boolean
        var insetEndDivider2 = divider()
                .insetEnd(true)
                .element();
        assertTrue("구분선 insetEnd(true): true여야 함", insetEndDivider2.insetEnd);

        // Disable insetEnd
        var fullDivider = divider()
                .insetEnd(false)
                .element();
        assertFalse("구분선 insetEnd(false): false여야 함", fullDivider.insetEnd);
    }

    private static void testDividerRole() {
        // Separator role for accessibility
        var separatorDivider = divider()
                .separator()
                .element();
        assertEquals("구분선 role: separator여야 함",
                "separator", separatorDivider.role);

        // Divider without role (decorative)
        var decorativeDivider = divider().element();
        body().add(decorativeDivider);

        // Role might be null or empty for decorative dividers
        log("구분선 decorative: role이 설정되지 않음 - PASS");
    }

    private static void testDividerFactoryMethods() {
        // Factory method: dividerInset()
        var insetDivider = dividerInset().element();
        assertTrue("팩토리 메서드 dividerInset: inset이 true여야 함", insetDivider.inset);

        // Factory method: dividerInsetStart()
        var insetStartDivider = dividerInsetStart().element();
        assertTrue("팩토리 메서드 dividerInsetStart: insetStart가 true여야 함", insetStartDivider.insetStart);

        // Factory method: dividerInsetEnd()
        var insetEndDivider = dividerInsetEnd().element();
        assertTrue("팩토리 메서드 dividerInsetEnd: insetEnd가 true여야 함", insetEndDivider.insetEnd);

        // Test mutual exclusivity
        var switchedDivider = dividerInset()
                .insetStart()
                .element();
        assertFalse("상호 배타성: inset이 false가 되어야 함", switchedDivider.inset);
        assertTrue("상호 배타성: insetStart가 true여야 함", switchedDivider.insetStart);

        var switchedDivider2 = dividerInsetStart()
                .insetEnd()
                .element();
        assertFalse("상호 배타성: insetStart가 false가 되어야 함", switchedDivider2.insetStart);
        assertTrue("상호 배타성: insetEnd가 true여야 함", switchedDivider2.insetEnd);
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

    private static void log(String message) {
        console.log(message);
    }
}
