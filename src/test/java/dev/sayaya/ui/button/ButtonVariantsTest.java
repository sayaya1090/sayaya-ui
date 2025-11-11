package dev.sayaya.ui.button;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.ButtonElementBuilder.button;
import static org.jboss.elemento.Elements.*;

public class ButtonVariantsTest {
    public static void test() {
        printSectionHeader("1. 버튼 변형 (Button Variants)");
        printDescription("Material Design은 5가지 버튼 스타일을 제공합니다:");
        printDescription("- Elevated: 패턴 배경에서 시각적 분리");
        printDescription("- Filled: 가장 높은 시각적 임팩트, 중요한 최종 액션");
        printDescription("- Filled Tonal: Filled와 Outlined의 중간");
        printDescription("- Outlined: 중간 강조, 중요하지만 주요하지 않은 액션");
        printDescription("- Text: 가장 낮은 우선순위, 다중 옵션");
        printSeparator();

        var variantsSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(variantsSection);

        variantsSection.appendChild(h(3).text("Button Variants").element());

        // Elevated Button
        var elevatedExample = addExampleCode(variantsSection,
            "📘 Elevated Button (고양된 버튼)",
            "패턴 배경에서 시각적으로 분리하기 위해 사용합니다. 그림자 효과가 있습니다.",
            """
            var button = button().elevated()
                .text("Elevated")
                .element();
            """);
        var elevated = button().elevated()
                .text("Elevated")
                .element();
        elevatedExample.addInteractiveDemo(elevated, false);
        assertEquals("elevated 버튼: 태그명은 md-elevated-button이어야 함",
                "MD-ELEVATED-BUTTON", elevated.tagName);

        // Filled Button
        var filledExample = addExampleCode(variantsSection,
            "📘 Filled Button (채워진 버튼)",
            "가장 높은 시각적 임팩트를 제공합니다. 중요한 최종 액션(저장, 제출 등)에 사용합니다.",
            """
            var button = button().filled()
                .text("Filled")
                .element();
            """);
        var filled = button().filled()
                .text("Filled")
                .element();
        filledExample.addInteractiveDemo(filled, false);
        assertEquals("filled 버튼: 태그명은 md-filled-button이어야 함",
                "MD-FILLED-BUTTON", filled.tagName);

        // Filled Tonal Button
        var filledTonalExample = addExampleCode(variantsSection,
            "📘 Filled Tonal Button (토널 버튼)",
            "Filled와 Outlined의 중간 강조 수준입니다. 부드러운 배경색을 가집니다.",
            """
            var button = button().filledTonal()
                .text("Filled Tonal")
                .element();
            """);
        var filledTonal = button().filledTonal()
                .text("Filled Tonal")
                .element();
        filledTonalExample.addInteractiveDemo(filledTonal, false);
        assertEquals("filled-tonal 버튼: 태그명은 md-filled-tonal-button이어야 함",
                "MD-FILLED-TONAL-BUTTON", filledTonal.tagName);

        // Outlined Button
        var outlinedExample = addExampleCode(variantsSection,
            "📘 Outlined Button (외곽선 버튼)",
            "중간 수준의 강조입니다. 중요하지만 주요하지 않은 액션(취소 등)에 사용합니다.",
            """
            var button = button().outlined()
                .text("Outlined")
                .element();
            """);
        var outlined = button().outlined()
                .text("Outlined")
                .element();
        outlinedExample.addInteractiveDemo(outlined, false);
        assertEquals("outlined 버튼: 태그명은 md-outlined-button이어야 함",
                "MD-OUTLINED-BUTTON", outlined.tagName);

        // Text Button
        var textExample = addExampleCode(variantsSection,
            "📘 Text Button (텍스트 버튼)",
            "가장 낮은 우선순위입니다. 다중 옵션 표시나 덜 중요한 액션에 사용합니다.",
            """
            var button = button().text()
                .text("Text")
                .element();
            """);
        var text = button().text()
                .text("Text")
                .element();
        textExample.addInteractiveDemo(text, false);
        assertEquals("text 버튼: 태그명은 md-text-button이어야 함",
                "MD-TEXT-BUTTON", text.tagName);
    }
}
