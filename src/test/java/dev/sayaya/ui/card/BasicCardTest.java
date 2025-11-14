package dev.sayaya.ui.card;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.CardElementBuilder.card;
import static org.jboss.elemento.Elements.*;

public class BasicCardTest {
    public static void test() {
        printSectionHeader("1. 기본 Card (Basic Card)");
        printDescription("Material Design Card는 관련 정보를 그룹화하여 표시하는 컨테이너 컴포넌트입니다.");
        printSeparator();

        var basicSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(basicSection);

        basicSection.appendChild(h(3).text("Basic Card").element());

        // Simple Elevated Card
        var elevatedExample = addExampleCode(basicSection,
            "📘 Elevated Card (기본 카드)",
            "기본적인 elevated 카드를 생성합니다.",
            """
            var card = card().elevated()
                .style("width", "192px")
                .add(div()
                    .style("padding", "16px")
                    .text("Elevated Card Content")
                )
                .element();
            """);
        var elevatedCard = card().elevated()
                .style("width", "192px")
                .add(div()
                    .style("padding", "16px")
                    .text("Elevated Card Content")
                )
                .element();
        elevatedExample.addInteractiveDemo(elevatedCard, false);
        assertEquals("card: Elevated 태그명은 md-elevated-card", "MD-ELEVATED-CARD", elevatedCard.tagName);

        // Simple Filled Card
        var filledExample = addExampleCode(basicSection,
            "📘 Filled Card (채워진 카드)",
            "배경색이 채워진 카드를 생성합니다.",
            """
            var card = card().filled()
                .style("width", "192px")
                .add(div()
                    .style("padding", "16px")
                    .text("Filled Card Content")
                )
                .element();
            """);
        var filledCard = card().filled()
                .style("width", "192px")
                .add(div()
                    .style("padding", "16px")
                    .text("Filled Card Content")
                )
                .element();
        filledExample.addInteractiveDemo(filledCard, false);
        assertEquals("card: Filled 태그명은 md-filled-card", "MD-FILLED-CARD", filledCard.tagName);

        // Simple Outlined Card
        var outlinedExample = addExampleCode(basicSection,
            "📘 Outlined Card (외곽선 카드)",
            "외곽선이 있는 카드를 생성합니다.",
            """
            var card = card().outlined()
                .style("width", "192px")
                .add(div()
                    .style("padding", "16px")
                    .text("Outlined Card Content")
                )
                .element();
            """);
        var outlinedCard = card().outlined()
                .style("width", "192px")
                .add(div()
                    .style("padding", "16px")
                    .text("Outlined Card Content")
                )
                .element();
        outlinedExample.addInteractiveDemo(outlinedCard, false);
        assertEquals("card: Outlined 태그명은 md-outlined-card", "MD-OUTLINED-CARD", outlinedCard.tagName);
    }
}
