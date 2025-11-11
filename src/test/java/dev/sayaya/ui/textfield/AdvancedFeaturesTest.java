package dev.sayaya.ui.textfield;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.TextFieldElementBuilder.textField;
import static org.jboss.elemento.Elements.*;

public class AdvancedFeaturesTest {
    public static void test() {
        printSectionHeader("9. 고급 기능 (Advanced Features)");
        printDescription("다중행, 읽기전용 등의 고급 기능:");
        printDescription("- rows/cols: 다중행 텍스트");
        printDescription("- readOnly: 읽기전용");
        printDescription("- autocomplete: 자동완성");
        printDescription("- inputMode: 입력 모드");
        printSeparator();

        var advancedSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(advancedSection);

        advancedSection.appendChild(h(3).text("Advanced Features").element());

        // Rows and cols
        var multilineExample = addExampleCode(advancedSection,
            "📘 Multiline (rows, cols)",
            "rows와 cols 속성을 사용하여 여러 줄 입력 필드를 만들 수 있습니다.",
            """
            var descriptionField = textField().filled()
                .label("설명")
                .rows(5)        // 5줄 높이
                .cols(40)       // 40자 너비
                .element();
            """);
        var multilineField = textField().filled()
                .label("설명")
                .rows(5)
                .cols(40)
                .element();
        multilineExample.addInteractiveDemo(multilineField, false);
        assertEquals("rows 속성: 5여야 함", 5, multilineField.rows);
        assertEquals("cols 속성: 40이어야 함", 40, multilineField.cols);

        // ReadOnly
        var readOnlyExample = addExampleCode(advancedSection,
            "📘 Read-only",
            "readOnly 속성은 값을 보여주되 수정할 수 없게 합니다. disabled와 달리 선택 및 복사가 가능합니다.",
            """
            var readOnlyField = textField().outlined()
                .label("고정값")
                .value("변경 불가")
                .readOnly(true)
                .element();
            """);
        var readOnlyField = textField().outlined()
                .label("고정값")
                .value("변경 불가")
                .readOnly(true)
                .element();
        readOnlyExample.addInteractiveDemo(readOnlyField, false);
        assertTrue("readOnly 속성: true여야 함", readOnlyField.readOnly);

        // Autocomplete
        var autocompleteExample = addExampleCode(advancedSection,
            "📘 Autocomplete",
            "브라우저 자동완성 기능을 제어합니다. 'email', 'name', 'tel' 등 표준 값을 사용할 수 있습니다.",
            """
            var emailField = textField().outlined()
                .label("이메일")
                .autocomplete("email")
                .element();
            """);
        var autocompleteField = textField().outlined()
                .label("이메일")
                .autocomplete("email")
                .element();
        autocompleteExample.addInteractiveDemo(autocompleteField, false);
        assertEquals("autocomplete 속성: 'email'이어야 함",
                "email", autocompleteField.autocomplete);

        // Input mode
        var inputModeExample = addExampleCode(advancedSection,
            "📘 Input Mode",
            "모바일 기기에서 표시할 키보드 타입을 지정합니다. 'numeric', 'tel', 'email', 'url' 등을 사용할 수 있습니다.",
            """
            var phoneField = textField().filled()
                .label("전화번호")
                .inputMode("tel")   // 전화번호 키패드 표시
                .element();
            """);
        var inputModeField = textField().filled()
                .label("전화번호")
                .inputMode("tel")
                .element();
        inputModeExample.addInteractiveDemo(inputModeField, false);
        assertEquals("inputMode 속성: 'tel'이어야 함",
                "tel", inputModeField.inputMode);
    }
}
