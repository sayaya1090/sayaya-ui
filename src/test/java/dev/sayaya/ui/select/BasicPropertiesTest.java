package dev.sayaya.ui.select;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.SelectElementBuilder.select;
import static org.jboss.elemento.Elements.*;

public class BasicPropertiesTest {
    public static void test() {
        printSectionHeader("2. 기본 속성 (Basic Properties)");
        printDescription("Select의 기본 속성들을 테스트합니다:");
        printDescription("- label: 라벨 텍스트");
        printDescription("- value: 선택된 값");
        printDescription("- required: 필수 선택");
        printDescription("- disabled: 비활성화");
        printDescription("- supportingText: 도움말 텍스트");
        printDescription("- errorText: 에러 메시지");
        printSeparator();

        var propertiesSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(propertiesSection);

        propertiesSection.appendChild(h(3).text("Basic Properties").element());

        // Label
        var labelExample = addExampleCode(propertiesSection,
            "📘 Label (라벨)",
            "Select 필드의 라벨을 지정합니다.",
            """
            var select = select().filled()
                .label("국가 선택")
                .option().value("kr").headline("한국").done()
                .option().value("us").headline("미국").done()
                .element();
            """);
        var labelSelect = select().filled()
                .label("국가 선택")
                .option().value("kr").headline("한국").done()
                .option().value("us").headline("미국").done()
                .element();
        labelExample.addInteractiveDemo(labelSelect, false);
        assertEquals("label 속성: '국가 선택'이어야 함",
                "국가 선택", labelSelect.label);

        // Required
        var requiredExample = addExampleCode(propertiesSection,
            "📘 Required (필수 선택)",
            "필수 선택 필드로 지정합니다. 선택하지 않으면 검증 실패합니다.",
            """
            var select = select().filled()
                .label("필수 선택")
                .required(true)
                .option().value("opt1").headline("옵션 1").done()
                .option().value("opt2").headline("옵션 2").done()
                .element();
            """);
        var requiredSelect = select().filled()
                .label("필수 선택")
                .required(true)
                .option().value("opt1").headline("옵션 1").done()
                .option().value("opt2").headline("옵션 2").done()
                .element();
        requiredExample.addInteractiveDemo(requiredSelect, false);
        assertTrue("required 속성: true여야 함", requiredSelect.required);

        // Disabled
        var disabledExample = addExampleCode(propertiesSection,
            "📘 Disabled (비활성화)",
            "Select를 비활성화하여 선택할 수 없게 합니다.",
            """
            var select = select().outlined()
                .label("비활성화")
                .disabled(true)
                .option().value("val").headline("값").done()
                .element();
            """);
        var disabledSelect = select().outlined()
                .label("비활성화")
                .disabled(true)
                .option().value("val").headline("값").done()
                .element();
        disabledExample.addInteractiveDemo(disabledSelect, false);
        assertTrue("disabled 속성: true여야 함", disabledSelect.disabled);

        // Supporting Text
        var supportingTextExample = addExampleCode(propertiesSection,
            "📘 Supporting Text (도움말 텍스트)",
            "Select 아래에 표시되는 도움말 텍스트입니다.",
            """
            var select = select().filled()
                .label("옵션 선택")
                .supportingText("원하는 옵션을 선택하세요")
                .option().value("a").headline("A").done()
                .option().value("b").headline("B").done()
                .element();
            """);
        var supportingTextSelect = select().filled()
                .label("옵션 선택")
                .supportingText("원하는 옵션을 선택하세요")
                .option().value("a").headline("A").done()
                .option().value("b").headline("B").done()
                .element();
        supportingTextExample.addInteractiveDemo(supportingTextSelect, false);
        assertEquals("supportingText 속성",
                "원하는 옵션을 선택하세요", supportingTextSelect.supportingText);

        // Error Text
        var errorTextExample = addExampleCode(propertiesSection,
            "📘 Error Text (에러 메시지)",
            "검증 실패 시 표시할 에러 메시지를 지정합니다.",
            """
            var select = select().outlined()
                .label("필수 필드")
                .required(true)
                .errorText("반드시 선택해야 합니다")
                .error(true)
                .option().value("1").headline("옵션 1").done()
                .element();
            """);
        var errorSelect = select().outlined()
                .label("필수 필드")
                .required(true)
                .errorText("반드시 선택해야 합니다")
                .error(true)
                .option().value("1").headline("옵션 1").done()
                .element();
        errorTextExample.addInteractiveDemo(errorSelect, false);
        assertEquals("errorText 속성",
                "반드시 선택해야 합니다", errorSelect.errorText);
        assertTrue("error 속성: true", errorSelect.error);
    }
}
