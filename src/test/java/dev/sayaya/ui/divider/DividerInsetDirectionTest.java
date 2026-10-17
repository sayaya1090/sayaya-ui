package dev.sayaya.ui.divider;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.DividerElementBuilder.divider;
import static org.jboss.elemento.Elements.*;

public class DividerInsetDirectionTest {
    public static void test() {
        printSectionHeader("3. Inset Direction (방향별 여백)");
        printDescription("한쪽에만 여백을 추가할 수 있습니다.");
        printSeparator();

        var directionSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(directionSection);

        directionSection.appendChild(h(3).text("Inset Direction").element());

        // Inset Start
        var startExample = addExampleCode(directionSection,
            "📘 Inset Start",
            "시작 부분(leading side)에 여백을 추가합니다.",
            """
            var divider = divider()
                .insetStart()
                .element();
            """);
        var insetStartDivider = divider()
                .insetStart()
                .element();
        startExample.addInteractiveDemo(insetStartDivider, false);
        assertTrue("구분선 insetStart: true", insetStartDivider.insetStart);
        assertFalse("구분선 insetStart: inset은 false", insetStartDivider.inset);
        assertFalse("구분선 insetStart: insetEnd는 false", insetStartDivider.insetEnd);

        // InsetStart with explicit boolean
        var startBooleanExample = addExampleCode(directionSection,
            "📘 Inset Start with Boolean",
            "명시적으로 insetStart 값을 설정합니다.",
            """
            var divider = divider()
                .insetStart(true)
                .element();
            """);
        var insetStartDivider2 = divider()
                .insetStart(true)
                .element();
        startBooleanExample.addInteractiveDemo(insetStartDivider2, false);
        assertTrue("구분선 insetStart(true): true", insetStartDivider2.insetStart);
        var insetStartOff = divider().insetStart(false).element();
        assertFalse("구분선 insetStart(false): insetStart는 false", insetStartOff.insetStart);
        assertFalse("구분선 insetStart(false): insetEnd는 false", insetStartOff.insetEnd);

        // Inset End
        var endExample = addExampleCode(directionSection,
            "📘 Inset End",
            "끝 부분(trailing side)에 여백을 추가합니다.",
            """
            var divider = divider()
                .insetEnd()
                .element();
            """);
        var insetEndDivider = divider()
                .insetEnd()
                .element();
        endExample.addInteractiveDemo(insetEndDivider, false);
        assertTrue("구분선 insetEnd: true", insetEndDivider.insetEnd);
        assertFalse("구분선 insetEnd: inset은 false", insetEndDivider.inset);
        assertFalse("구분선 insetEnd: insetStart는 false", insetEndDivider.insetStart);

        // InsetEnd with explicit boolean
        var endBooleanExample = addExampleCode(directionSection,
            "📘 Inset End with Boolean",
            "명시적으로 insetEnd 값을 설정합니다.",
            """
            var divider = divider()
                .insetEnd(true)
                .element();
            """);
        var insetEndDivider2 = divider()
                .insetEnd(true)
                .element();
        endBooleanExample.addInteractiveDemo(insetEndDivider2, false);
        assertTrue("구분선 insetEnd(true): true", insetEndDivider2.insetEnd);
        var insetEndOff = divider().insetEnd(false).element();
        assertFalse("구분선 insetEnd(false): insetEnd는 false", insetEndOff.insetEnd);
        assertFalse("구분선 insetEnd(false): insetStart는 false", insetEndOff.insetStart);
    }
}
