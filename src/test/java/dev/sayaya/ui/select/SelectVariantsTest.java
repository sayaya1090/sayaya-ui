package dev.sayaya.ui.select;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.SelectElementBuilder.select;
import static org.jboss.elemento.Elements.*;

public class SelectVariantsTest {
    public static void test() {
        printSectionHeader("1. Select 변형 (Select Variants)");
        printDescription("Material Design Select는 2가지 스타일을 제공합니다:");
        printDescription("- Filled: 배경이 채워진 스타일");
        printDescription("- Outlined: 외곽선 스타일");
        printSeparator();

        var variantsSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(variantsSection);

        variantsSection.appendChild(h(3).text("Select Variants").element());

        // Filled Select
        var filledExample = addExampleCode(variantsSection,
            "📘 Filled Select (채워진 Select)",
            "배경이 채워진 스타일의 Select입니다. 강조가 필요한 경우 사용합니다.",
            """
            var select = select().filled()
                .label("과일 선택")
                .option()
                    .value("apple")
                    .headline("사과")
                .end()
                .option()
                    .value("banana")
                    .headline("바나나")
                .end()
                .element();
            """);
        var filled = select().filled()
                .label("과일 선택")
                .option()
                    .value("apple")
                    .headline("사과")
                .end()
                .option()
                    .value("banana")
                    .headline("바나나")
                .end()
                .element();
        filledExample.addInteractiveDemo(filled, false);
        assertEquals("filled select: 태그명은 md-filled-select",
                "MD-FILLED-SELECT", filled.tagName);

        // Outlined Select
        var outlinedExample = addExampleCode(variantsSection,
            "📘 Outlined Select (외곽선 Select)",
            "외곽선만 있는 스타일의 Select입니다. 일반적인 폼 필드에 사용합니다.",
            """
            var select = select().outlined()
                .label("색상 선택")
                .option()
                    .value("red")
                    .headline("빨강")
                .end()
                .option()
                    .value("blue")
                    .headline("파랑")
                .end()
                .element();
            """);
        var outlined = select().outlined()
                .label("색상 선택")
                .option()
                    .value("red")
                    .headline("빨강")
                .end()
                .option()
                    .value("blue")
                    .headline("파랑")
                .end()
                .element();
        outlinedExample.addInteractiveDemo(outlined, false);
        assertEquals("outlined select: 태그명은 md-outlined-select",
                "MD-OUTLINED-SELECT", outlined.tagName);
    }
}
