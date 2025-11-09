package dev.sayaya.ui.textfield;

import static dev.sayaya.ui.elements.TextFieldElementBuilder.textField;
import static org.jboss.elemento.Elements.*;

public class BasicPropertiesTest {
    public static void test() {
        TestHelper.printSectionHeader("2. 기본 속성 (Basic Properties)");
        TestHelper.printDescription("TextField의 기본 속성들을 테스트합니다:");
        TestHelper.printDescription("- label: 라벨 텍스트");
        TestHelper.printDescription("- value: 초기 값");
        TestHelper.printDescription("- placeholder: 플레이스홀더");
        TestHelper.printDescription("- required: 필수 입력");
        TestHelper.printDescription("- disabled: 비활성화");
        TestHelper.printDescription("- name: 폼 필드명");
        TestHelper.printSeparator();

        var propertiesSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(propertiesSection);

        propertiesSection.appendChild(h(3).text("Basic Properties").element());

        // Label
        TestHelper.addExampleCode(propertiesSection,
            "📘 Label (라벨)",
            "입력 필드의 라벨을 지정합니다.",
            """
            var field = textField().filled()
                .label("사용자명")
                .element();
            """);
        var labelField = textField().filled()
                .label("사용자명")
                .element();
        propertiesSection.appendChild(labelField);
        TestHelper.assertEquals("label 속성: '사용자명'이어야 함",
                "사용자명", labelField.label);

        // Value
        TestHelper.addExampleCode(propertiesSection,
            "📘 Value (초기값)",
            "필드의 초기값을 설정합니다.",
            """
            var field = textField().filled()
                .label("초기값 예시")
                .value("초기값")
                .element();
            """);
        var valueField = textField().filled()
                .label("초기값 예시")
                .value("초기값")
                .element();
        propertiesSection.appendChild(valueField);
        TestHelper.assertEquals("value 속성: '초기값'이어야 함",
                "초기값", valueField.value);

        // Placeholder
        TestHelper.addExampleCode(propertiesSection,
            "📘 Placeholder (힌트 텍스트)",
            "필드가 비어있을 때 보여줄 힌트 텍스트입니다.",
            """
            var field = textField().outlined()
                .label("Placeholder 예시")
                .placeholder("텍스트를 입력하세요")
                .element();
            """);
        var placeholderField = textField().outlined()
                .label("Placeholder 예시")
                .placeholder("텍스트를 입력하세요")
                .element();
        propertiesSection.appendChild(placeholderField);
        TestHelper.assertEquals("placeholder 속성: '텍스트를 입력하세요'여야 함",
                "텍스트를 입력하세요", placeholderField.placeholder);

        // Required
        TestHelper.addExampleCode(propertiesSection,
            "📘 Required (필수 입력)",
            "필수 입력 필드로 지정합니다. 비어있으면 검증 실패합니다.",
            """
            var field = textField().filled()
                .label("필수 입력")
                .required(true)
                .element();
            field.onchange = evt -> {
                field.reportValidity();  // 비어있으면 에러 표시
                return null;
            };
            """);
        var requiredField = textField().filled()
                .label("필수 입력")
                .required(true)
                .element();
        requiredField.onchange = evt -> {
            requiredField.reportValidity();
            return null;
        };
        propertiesSection.appendChild(requiredField);
        TestHelper.assertTrue("required 속성: true여야 함", requiredField.required);

        // Required validation test
        TestHelper.assertFalse("required validation: 빈 필드는 invalid", requiredField.checkValidity());

        requiredField.value = "값 입력";
        TestHelper.assertTrue("required validation: 값이 있으면 valid", requiredField.checkValidity());

        // Disabled
        TestHelper.addExampleCode(propertiesSection,
            "📘 Disabled (비활성화)",
            "필드를 비활성화하여 입력과 선택을 모두 차단합니다.",
            """
            var field = textField().outlined()
                .label("비활성화")
                .value("수정 불가")
                .disabled(true)
                .element();
            """);
        var disabledField = textField().outlined()
                .label("비활성화")
                .value("수정 불가")
                .disabled(true)
                .element();
        propertiesSection.appendChild(disabledField);
        TestHelper.assertTrue("disabled 속성: true여야 함", disabledField.disabled);

        // Name
        TestHelper.addExampleCode(propertiesSection,
            "📘 Name (폼 필드명)",
            "폼 제출 시 사용될 필드 이름을 지정합니다.",
            """
            var field = textField().filled()
                .label("Name 속성")
                .name("username")
                .element();
            """);
        var nameField = textField().filled()
                .label("Name 속성")
                .name("username")
                .element();
        propertiesSection.appendChild(nameField);
        TestHelper.assertEquals("name 속성: 'username'이어야 함",
                "username", nameField.name);
    }
}
