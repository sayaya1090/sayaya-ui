package dev.sayaya.ui.textfield;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.TextFieldElementBuilder.textField;
import static org.jboss.elemento.Elements.*;

public class TextFieldVariantsTest {
    public static void test() {
        printSectionHeader("1. TextField 변형 (TextField Variants)");
        printDescription("Material Design은 2가지 TextField 스타일을 제공합니다:");
        printDescription("- Filled: 채워진 배경, 더 강조됨");
        printDescription("- Outlined: 테두리만, 더 가벼움");
        printSeparator();

        var variantsSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(variantsSection);

        variantsSection.appendChild(h(3).text("TextField Variants").element());

        // Filled TextField
        var filledExample = addExampleCode(variantsSection,
            "📘 Filled TextField",
            "채워진 배경 스타일. 주요 입력 필드나 검색창 등에 적합합니다.",
            """
            var filled = textField().filled()
                .label("이름")
                .element();
            """);
        var filled = textField().filled()
                .label("이름")
                .element();
        filledExample.addInteractiveDemo(filled, false);
        assertEquals("filled 텍스트 필드: 태그명은 MD-FILLED-TEXT-FIELD여야 함",
                "MD-FILLED-TEXT-FIELD", filled.tagName);

        // Outlined TextField
        var outlinedExample = addExampleCode(variantsSection,
            "📘 Outlined TextField",
            "테두리 스타일. 설정 화면이나 폼에서 가벼운 느낌을 원할 때 사용합니다.",
            """
            var outlined = textField().outlined()
                .label("이메일")
                .element();
            """);
        var outlined = textField().outlined()
                .label("이메일")
                .element();
        outlinedExample.addInteractiveDemo(outlined, false);
        assertEquals("outlined 텍스트 필드: 태그명은 MD-OUTLINED-TEXT-FIELD여야 함",
                "MD-OUTLINED-TEXT-FIELD", outlined.tagName);
    }
}
