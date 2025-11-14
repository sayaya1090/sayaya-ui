package dev.sayaya.ui.card;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.CardElementBuilder.card;
import static org.jboss.elemento.Elements.*;

public class CardVariantsTest {
    public static void test() {
        printSectionHeader("2. Card 변형 비교 (Card Variants)");
        printDescription("세 가지 카드 변형을 비교합니다: Elevated, Filled, Outlined");
        printSeparator();

        var variantsSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(variantsSection);

        variantsSection.appendChild(h(3).text("Card Variants Comparison").element());

        // All variants side by side
        var comparisonExample = addExampleCode(variantsSection,
            "📘 Card Variants Comparison (카드 변형 비교)",
            "세 가지 카드 변형을 나란히 배치하여 차이를 확인합니다.",
            """
            var container = div()
                .style("display", "flex")
                .style("flex-wrap", "wrap")
                .style("gap", "8px")
                .style("color", "var(--md-sys-color-on-surface)")
                .add(card().elevated()
                    .style("width", "192px")
                    .add(div().style("padding", "16px").text("Elevated"))
                )
                .add(card().filled()
                    .style("width", "192px")
                    .add(div().style("padding", "16px").text("Filled"))
                )
                .add(card().outlined()
                    .style("width", "192px")
                    .add(div().style("padding", "16px").text("Outlined"))
                )
                .element();
            """);

        var comparisonContainer = div()
                .style("display", "flex")
                .style("flex-wrap", "wrap")
                .style("gap", "8px")
                .style("color", "var(--md-sys-color-on-surface)")
                .add(card().elevated()
                    .style("width", "192px")
                    .add(div()
                        .style("padding", "16px")
                        .text("Elevated")
                    )
                )
                .add(card().filled()
                    .style("width", "192px")
                    .add(div()
                        .style("padding", "16px")
                        .text("Filled")
                    )
                )
                .add(card().outlined()
                    .style("width", "192px")
                    .add(div()
                        .style("padding", "16px")
                        .text("Outlined")
                    )
                )
                .element();

        comparisonExample.addInteractiveDemo(comparisonContainer, false);
        assertTrue("card variants: 3가지 variant 모두 생성됨", comparisonContainer.childElementCount == 3);
    }
}
