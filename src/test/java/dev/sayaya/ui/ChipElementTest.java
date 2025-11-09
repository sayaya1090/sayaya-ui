package dev.sayaya.ui;

import com.google.gwt.core.client.EntryPoint;
import dev.sayaya.ui.dom.MdChipElement;

import static dev.sayaya.ui.elements.ChipsElementBuilder.chips;
import static elemental2.dom.DomGlobal.console;
import static org.jboss.elemento.Elements.body;
import static org.jboss.elemento.Elements.span;

public class ChipElementTest implements EntryPoint {
    @Override
    public void onModuleLoad() {
        testChipTypes();
        testChipProperties();
        testAssistChipProperties();
        testFilterChipProperties();
        testInputChipProperties();
        testSuggestionChipProperties();
        testChipSetProperties();
        testIconSupport();
        testAccessibility();
        testSelectableChips();
        testRemovableChips();
        testDisabledChips();
    }

    private static void testChipTypes() {
        // Assist Chip
        var assistChip = chips()
                .assist()
                .label("Assist")
                .element();
        assertEquals("assist 칩: 태그명은 md-assist-chip이어야 함",
                "MD-ASSIST-CHIP", assistChip.tagName);

        // Filter Chip
        var filterChip = chips()
                .filter()
                .label("Filter")
                .element();
        assertEquals("filter 칩: 태그명은 md-filter-chip이어야 함",
                "MD-FILTER-CHIP", filterChip.tagName);

        // Input Chip
        var inputChip = chips()
                .input()
                .label("Input")
                .element();
        assertEquals("input 칩: 태그명은 md-input-chip이어야 함",
                "MD-INPUT-CHIP", inputChip.tagName);

        // Suggestion Chip
        var suggestionChip = chips()
                .suggestion()
                .label("Suggestion")
                .element();
        assertEquals("suggestion 칩: 태그명은 md-suggestion-chip이어야 함",
                "MD-SUGGESTION-CHIP", suggestionChip.tagName);
    }

    private static void testChipProperties() {
        // Label
        var chip = chips()
                .assist()
                .label("Test Label")
                .element();
        assertEquals("칩 label: Test Label이어야 함", "Test Label", chip.label);

        // Disabled
        var disabledChip = chips()
                .assist()
                .label("Disabled")
                .disabled()
                .element();
        assertTrue("칩 disabled: true여야 함", disabledChip.disabled);

        // Always Focusable
        var focusableChip = chips()
                .assist()
                .label("Focusable")
                .alwaysFocusable(true)
                .element();
        assertTrue("칩 alwaysFocusable: true여야 함", focusableChip.alwaysFocusable);
    }

    private static void testAssistChipProperties() {
        // Elevated
        var elevatedChip = (MdChipElement.MdAssistChipElement) chips()
                .assist()
                .label("Elevated")
                .elevated()
                .element();
        assertTrue("assist 칩 elevated: true여야 함", elevatedChip.elevated);

        // Href
        var linkChip = (MdChipElement.MdAssistChipElement) chips()
                .assist()
                .label("Link")
                .href("https://example.com")
                .element();
        assertEquals("assist 칩 href: https://example.com이어야 함",
                "https://example.com", linkChip.href);

        // Target
        var targetChip = (MdChipElement.MdAssistChipElement) chips()
                .assist()
                .label("New Tab")
                .href("https://example.com")
                .target("_blank")
                .element();
        assertEquals("assist 칩 target: _blank여야 함", "_blank", targetChip.target);
    }

    private static void testFilterChipProperties() {
        // Elevated
        var elevatedChip = (MdChipElement.MdFilterChipElement) chips()
                .filter()
                .label("Elevated Filter")
                .elevated()
                .element();
        assertTrue("filter 칩 elevated: true여야 함", elevatedChip.elevated);

        // Selected
        var selectedChip = (MdChipElement.MdFilterChipElement) chips()
                .filter()
                .label("Selected")
                .select()
                .element();
        assertTrue("filter 칩 selected: true여야 함", selectedChip.selected);

        // Removable
        var removableChip = (MdChipElement.MdFilterChipElement) chips()
                .filter()
                .label("Removable")
                .removable()
                .element();
        assertTrue("filter 칩 removable: true여야 함", removableChip.removable);

        // Aria Label Remove
        var ariaRemoveChip = (MdChipElement.MdFilterChipElement) chips()
                .filter()
                .label("Remove")
                .removable()
                .ariaLabelRemove("Remove this chip")
                .element();
        assertEquals("filter 칩 ariaLabelRemove: Remove this chip이어야 함",
                "Remove this chip", ariaRemoveChip.ariaLabelRemove);
    }

    private static void testInputChipProperties() {
        // Avatar
        var avatarChip = (MdChipElement.MdInputChipElement) chips()
                .input()
                .label("User")
                .avatar()
                .element();
        assertTrue("input 칩 avatar: true여야 함", avatarChip.avatar);

        // Selected
        var selectedChip = (MdChipElement.MdInputChipElement) chips()
                .input()
                .label("Selected")
                .select()
                .element();
        assertTrue("input 칩 selected: true여야 함", selectedChip.selected);

        // Remove Only
        var removeOnlyChip = (MdChipElement.MdInputChipElement) chips()
                .input()
                .label("Remove Only")
                .removeOnly()
                .element();
        assertTrue("input 칩 removeOnly: true여야 함", removeOnlyChip.removeOnly);

        // Href
        var linkChip = (MdChipElement.MdInputChipElement) chips()
                .input()
                .label("Link")
                .href("https://example.com")
                .element();
        assertEquals("input 칩 href: https://example.com이어야 함",
                "https://example.com", linkChip.href);

        // Target
        var targetChip = (MdChipElement.MdInputChipElement) chips()
                .input()
                .label("New Tab")
                .href("https://example.com")
                .target("_blank")
                .element();
        assertEquals("input 칩 target: _blank여야 함", "_blank", targetChip.target);

        // Aria Label Remove
        var ariaRemoveChip = (MdChipElement.MdInputChipElement) chips()
                .input()
                .label("Remove")
                .ariaLabelRemove("Remove this chip")
                .element();
        assertEquals("input 칩 ariaLabelRemove: Remove this chip이어야 함",
                "Remove this chip", ariaRemoveChip.ariaLabelRemove);
    }

    private static void testSuggestionChipProperties() {
        // Elevated
        var elevatedChip = (MdChipElement.MdSuggestionChipElement) chips()
                .suggestion()
                .label("Elevated Suggestion")
                .elevated()
                .element();
        assertTrue("suggestion 칩 elevated: true여야 함", elevatedChip.elevated);

        // Href
        var linkChip = (MdChipElement.MdSuggestionChipElement) chips()
                .suggestion()
                .label("Link")
                .href("https://example.com")
                .element();
        assertEquals("suggestion 칩 href: https://example.com이어야 함",
                "https://example.com", linkChip.href);

        // Target
        var targetChip = (MdChipElement.MdSuggestionChipElement) chips()
                .suggestion()
                .label("New Tab")
                .href("https://example.com")
                .target("_blank")
                .element();
        assertEquals("suggestion 칩 target: _blank여야 함", "_blank", targetChip.target);
    }

    private static void testChipSetProperties() {
        // Basic Chip Set
        var chipSet = chips()
                .assist().label("Chip 1").end()
                .assist().label("Chip 2").end()
                .assist().label("Chip 3").end()
                .element();
        body().add(chipSet);

        assertEquals("칩셋: 태그명은 md-chip-set이어야 함",
                "MD-CHIP-SET", chipSet.tagName);
        assertEquals("칩셋: 3개의 칩을 포함해야 함", 3, chipSet.childElementCount);

        // Aria Label
        var labeledChipSet = chips()
                .ariaLabel("Filter options")
                .filter().label("Option 1").end()
                .filter().label("Option 2").end()
                .element();
        body().add(labeledChipSet);

        assertEquals("칩셋 aria-label: Filter options이어야 함",
                "Filter options",
                labeledChipSet.getAttribute("aria-label"));

        // Aria Labelled By
        var labelElement = span().id("chip-set-label").text("Categories").element();
        body().add(labelElement);

        var labelledByChipSet = chips()
                .ariaLabelledBy("chip-set-label")
                .filter().label("Category 1").end()
                .filter().label("Category 2").end()
                .element();
        body().add(labelledByChipSet);

        assertEquals("칩셋 aria-labelledby: chip-set-label이어야 함",
                "chip-set-label",
                labelledByChipSet.getAttribute("aria-labelledby"));
    }

    private static void testIconSupport() {
        // Assist Chip with Icon
        var assistWithIcon = chips()
                .assist()
                .label("Calendar")
                .icon("event")
                .element();
        body().add(assistWithIcon);

        var icon = assistWithIcon.querySelector("md-icon");
        assertNotNull("칩 아이콘: 아이콘이 존재해야 함", icon);
        assertEquals("칩 아이콘: slot은 icon이어야 함",
                "icon", icon.getAttribute("slot"));

        // Filter Chip with Icon
        var filterWithIcon = chips()
                .filter()
                .label("Starred")
                .icon("star")
                .element();
        body().add(filterWithIcon);

        var filterIcon = filterWithIcon.querySelector("md-icon");
        assertNotNull("filter 칩 아이콘: 아이콘이 존재해야 함", filterIcon);
    }

    private static void testAccessibility() {
        // Aria Label
        var ariaChip = chips()
                .assist()
                .label("Delete")
                .ariaLabel("Delete item from list")
                .element();
        body().add(ariaChip);

        assertEquals("칩 aria-label: Delete item from list이어야 함",
                "Delete item from list",
                ariaChip.getAttribute("aria-label"));

        // Disabled with Aria Label
        var disabledAriaChip = chips()
                .assist()
                .label("Unavailable")
                .disabled()
                .ariaLabel("This action is currently unavailable")
                .element();
        body().add(disabledAriaChip);

        assertTrue("칩 disabled: true여야 함", disabledAriaChip.disabled);
        assertEquals("disabled 칩 aria-label: This action is currently unavailable이어야 함",
                "This action is currently unavailable",
                disabledAriaChip.getAttribute("aria-label"));
    }

    private static void testSelectableChips() {
        // Filter Chip Selection
        var filterChipBuilder = chips()
                .filter()
                .label("Selectable Filter");
        var filterChip = (MdChipElement.MdFilterChipElement) filterChipBuilder.element();
        body().add(filterChip);

        assertFalse("칩 선택: 초기에는 선택되지 않음", filterChipBuilder.isSelected());

        filterChipBuilder.select(true);
        assertTrue("칩 선택: select(true) 후 선택됨", filterChip.selected);

        filterChipBuilder.select(false);
        assertFalse("칩 선택: select(false) 후 선택 해제", filterChip.selected);

        // Input Chip Selection
        var inputChipBuilder = chips()
                .input()
                .label("Selectable Input");
        var inputChip = (MdChipElement.MdInputChipElement) inputChipBuilder.element();
        body().add(inputChip);

        assertFalse("input 칩 선택: 초기에는 선택되지 않음", inputChipBuilder.isSelected());

        inputChipBuilder.select();
        assertTrue("input 칩 선택: select() 후 선택됨", inputChip.selected);
    }

    private static void testRemovableChips() {
        // Filter Chip Removable
        var removableFilter = (MdChipElement.MdFilterChipElement) chips()
                .filter()
                .label("Removable Filter")
                .removable()
                .ariaLabelRemove("Remove filter")
                .element();
        body().add(removableFilter);

        assertTrue("removable filter 칩: removable이 true", removableFilter.removable);
        assertEquals("removable filter 칩: ariaLabelRemove 설정됨",
                "Remove filter", removableFilter.ariaLabelRemove);

        // Input Chip Remove Only
        var removeOnlyInput = (MdChipElement.MdInputChipElement) chips()
                .input()
                .label("Remove Only Input")
                .removeOnly()
                .ariaLabelRemove("Remove input")
                .element();
        body().add(removeOnlyInput);

        assertTrue("removeOnly input 칩: removeOnly가 true", removeOnlyInput.removeOnly);
        assertEquals("removeOnly input 칩: ariaLabelRemove 설정됨",
                "Remove input", removeOnlyInput.ariaLabelRemove);
    }

    private static void testDisabledChips() {
        // Disabled Assist Chip
        var disabledAssist = chips()
                .assist()
                .label("Disabled Assist")
                .disabled()
                .element();
        body().add(disabledAssist);

        assertTrue("disabled assist 칩: disabled가 true", disabledAssist.disabled);

        // Enable/Disable Toggle
        var toggleChipBuilder = chips()
                .filter()
                .label("Toggle");
        var toggleChip = toggleChipBuilder.element();
        body().add(toggleChip);

        assertFalse("toggle 칩: 초기에는 활성화", toggleChip.disabled);

        toggleChipBuilder.disabled(true);
        assertTrue("toggle 칩: disable(true) 후 비활성화", toggleChip.disabled);

        toggleChipBuilder.enabled();
        assertFalse("toggle 칩: enable() 후 활성화", toggleChip.disabled);

        toggleChipBuilder.enabled(false);
        assertTrue("toggle 칩: enable(false) 후 비활성화", toggleChip.disabled);
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

    private static void assertEquals(String message, int expected, int actual) {
        if (expected == actual) {
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
