package dev.sayaya.ui.textfield;

import static dev.sayaya.ui.elements.TextFieldElementBuilder.textField;
import static org.jboss.elemento.Elements.*;

public class ValidationTest {
    public static void test() {
        TestHelper.printSectionHeader("4. 유효성 검증 (Validation)");
        TestHelper.printDescription("TextField의 유효성 검증 기능:");
        TestHelper.printDescription("- error: 에러 상태 표시");
        TestHelper.printDescription("- errorText: 에러 메시지");
        TestHelper.printDescription("- pattern: 정규식 패턴 검증");
        TestHelper.printDescription("- maxLength/minLength: 길이 제한");
        TestHelper.printDescription("- supportingText: 보조 텍스트");
        TestHelper.printSeparator();

        var validationSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(validationSection);

        validationSection.appendChild(h(3).text("Validation").element());

        // Error state
        TestHelper.addExampleCode(validationSection,
            "📘 Error 상태 표시",
            "에러가 발생했을 때 필드를 붉은색으로 표시하고 에러 메시지를 보여줍니다.",
            """
            var field = textField().filled()
                .label("이메일")
                .value("잘못된이메일")
                .error(true)
                .errorText("유효한 이메일 주소를 입력하세요")
                .element();
            """);
        var errorField = textField().filled()
                .label("이메일 (에러)")
                .value("잘못된이메일")
                .error(true)
                .errorText("유효한 이메일 주소를 입력하세요")
                .element();
        validationSection.appendChild(errorField);
        TestHelper.assertTrue("error 속성: true여야 함", errorField.error);
        TestHelper.assertEquals("errorText 속성: '유효한 이메일 주소를 입력하세요'여야 함",
                "유효한 이메일 주소를 입력하세요", errorField.errorText);

        // Pattern validation
        TestHelper.addExampleCode(validationSection,
            "📘 Pattern 검증 (정규식)",
            "정규식 패턴을 사용하여 입력 형식을 제한할 수 있습니다. 패턴과 맞지 않으면 에러가 표시됩니다.",
            """
            var zipCode = textField().outlined()
                .label("우편번호")
                .pattern("[0-9]{5}")    // 5자리 숫자만 허용
                .placeholder("12345")
                .element();
            zipCode.onchange = evt -> {
                zipCode.reportValidity();  // 패턴 검증
                return null;
            };
            """);
        var patternField = textField().outlined()
                .label("우편번호")
                .pattern("[0-9]{5}")
                .placeholder("12345")
                .element();
        patternField.onchange = evt -> {
            patternField.reportValidity();
            return null;
        };
        validationSection.appendChild(patternField);
        TestHelper.assertEquals("pattern 속성: '[0-9]{5}'여야 함",
                "[0-9]{5}", patternField.pattern);

        // Pattern validation test
        patternField.value = "abc";
        TestHelper.assertFalse("pattern validation: 숫자가 아니면 invalid", patternField.checkValidity());

        patternField.value = "123";
        TestHelper.assertFalse("pattern validation: 5자리가 아니면 invalid", patternField.checkValidity());

        patternField.value = "12345";
        TestHelper.assertTrue("pattern validation: 5자리 숫자면 valid", patternField.checkValidity());

        // MaxLength
        TestHelper.addExampleCode(validationSection,
            "📘 길이 제한 및 문자 카운터",
            "maxLength를 설정하면 자동으로 문자 카운터가 표시됩니다.",
            """
            var message = textField().filled()
                .label("짧은 메시지")
                .maxLength(100)         // 최대 100자
                .supportingText("간단한 메시지를 입력하세요")
                .element();
            """);
        var maxLengthField = textField().filled()
                .label("짧은 메시지")
                .maxLength(100)
                .element();
        validationSection.appendChild(maxLengthField);
        TestHelper.assertEquals("maxLength 속성: 100이어야 함",
                100, maxLengthField.maxLength);

        // MinLength
        TestHelper.addExampleCode(validationSection,
            "📘 Supporting Text (보조 텍스트)",
            "필드 아래에 힌트나 설명을 표시할 수 있습니다.",
            """
            var password = textField().filled()
                .label("비밀번호")
                .minLength(8)
                .supportingText("최소 8자 이상 입력하세요")
                .element();
            """);
        var minLengthField = textField().outlined()
                .label("비밀번호")
                .minLength(8)
                .element();
        validationSection.appendChild(minLengthField);
        TestHelper.assertEquals("minLength 속성: 8이어야 함",
                8, minLengthField.minLength);

        // Character counter
        TestHelper.addExampleCode(validationSection,
            "📘 Character Counter (문자 카운터)",
            "maxLength를 설정하면 자동으로 현재/최대 글자 수를 표시합니다.",
            """
            var field = textField().outlined()
                .label("Title")
                .value("Short")
                .maxLength(10)      // 10/10 형태로 표시
                .element();
            """);
        var counterField = textField().outlined()
                .label("Title")
                .value("Short")
                .maxLength(10)
                .element();
        validationSection.appendChild(counterField);
        TestHelper.assertEquals("character counter: maxLength가 설정되어야 함", 10, counterField.maxLength);
        TestHelper.assertEquals("character counter: 초기 값 길이", 5, counterField.value.length());

        counterField.value = "LongTitle";
        TestHelper.assertEquals("character counter: 업데이트된 값 길이", 9, counterField.value.length());
        TestHelper.assertTrue("character counter: 최대 길이 이내", counterField.value.length() <= counterField.maxLength);

        // Supporting text
        TestHelper.addExampleCode(validationSection,
            "📘 Supporting Text 단독 사용",
            "보조 텍스트만 별도로 사용할 수 있습니다.",
            """
            var field = textField().filled()
                .label("비밀번호")
                .supportingText("최소 8자 이상 입력하세요")
                .element();
            """);
        var supportingField = textField().filled()
                .label("비밀번호")
                .supportingText("최소 8자 이상 입력하세요")
                .element();
        validationSection.appendChild(supportingField);
        TestHelper.assertEquals("supportingText: '최소 8자 이상 입력하세요'여야 함",
                "최소 8자 이상 입력하세요", supportingField.supportingText);

        // Supporting text with maxLength
        TestHelper.addExampleCode(validationSection,
            "📘 Supporting Text + Character Counter 조합",
            "보조 텍스트와 문자 카운터를 함께 사용할 수 있습니다.",
            """
            var field = textField().outlined()
                .label("설명")
                .maxLength(50)
                .supportingText("간단한 설명을 입력하세요")
                .value("테스트")
                .element();
            // 보조 텍스트 왼쪽, 카운터 오른쪽에 표시
            """);
        var supportingWithCounter = textField().outlined()
                .label("설명")
                .maxLength(50)
                .supportingText("간단한 설명을 입력하세요")
                .value("테스트")
                .element();
        validationSection.appendChild(supportingWithCounter);
        TestHelper.assertEquals("supporting + counter: maxLength 설정", 50, supportingWithCounter.maxLength);
        TestHelper.assertEquals("supporting + counter: supporting text 존재",
                "간단한 설명을 입력하세요", supportingWithCounter.supportingText);
    }
}
