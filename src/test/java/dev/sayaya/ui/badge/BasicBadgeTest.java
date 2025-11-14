package dev.sayaya.ui.badge;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.BadgeElementBuilder.badge;
import static org.jboss.elemento.Elements.*;

public class BasicBadgeTest {
    public static void test() {
        printSectionHeader("1. 기본 Badge (Basic Badge)");
        printDescription("Material Design Badge는 알림이나 상태를 나타내는 작은 표시 컴포넌트입니다.");
        printSeparator();

        var basicSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(basicSection);

        basicSection.appendChild(h(3).text("Basic Badge").element());

        // Empty Badge (Dot)
        var emptyExample = addExampleCode(basicSection,
            "📘 Empty Badge (점 표시)",
            "값이 없는 기본 배지를 생성합니다.",
            """
            var badge = badge().element();
            """);
        var emptyBadge = badge().element();
        emptyExample.addInteractiveDemo(div()
                .style("position", "relative")
                .style("display", "inline-block")
                .style("padding", "20px")
                .add(emptyBadge)
                .element(), false);
        assertEquals("badge: 태그명은 md-badge", "MD-BADGE", emptyBadge.tagName);

        // Badge with Number
        var numberExample = addExampleCode(basicSection,
            "📘 Badge with Number (숫자 배지)",
            "숫자 값이 있는 배지를 생성합니다.",
            """
            var badge = badge()
                .value(5)
                .element();
            """);
        var numberBadge = badge()
                .value(5)
                .element();
        numberExample.addInteractiveDemo(div()
                .style("position", "relative")
                .style("display", "inline-block")
                .style("padding", "20px")
                .add(numberBadge)
                .element(), false);
        assertEquals("badge: value는 5", "5", numberBadge.value);

        // Badge with Text
        var textExample = addExampleCode(basicSection,
            "📘 Badge with Text (텍스트 배지)",
            "텍스트 값이 있는 배지를 생성합니다.",
            """
            var badge = badge()
                .value("New")
                .element();
            """);
        var textBadge = badge()
                .value("New")
                .element();
        textExample.addInteractiveDemo(div()
                .style("position", "relative")
                .style("display", "inline-block")
                .style("padding", "20px")
                .add(textBadge)
                .element(), false);
        assertEquals("badge: value는 New", "New", textBadge.value);
    }
}
