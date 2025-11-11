package dev.sayaya.ui.textfield;

import org.jboss.elemento.InputType;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.TextFieldElementBuilder.textField;
import static org.jboss.elemento.Elements.*;

public class ConstraintValidationTest {
    public static void test() {
        printSectionHeader("5. 제약 조건 검증 (Constraint Validation)");
        printDescription("HTML5 표준 제약 조건 검증 API:");
        printDescription("- checkValidity(): 유효성 검사");
        printDescription("- reportValidity(): 유효성 검사 + 에러 표시");
        printDescription("- validity: ValidityState 객체");
        printSeparator();

        var constraintSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(constraintSection);

        constraintSection.appendChild(h(3).text("Constraint Validation").element());

        // checkValidity() test
        var checkValidityExample = addExampleCode(constraintSection,
            "📘 checkValidity() - 유효성 검사",
            "HTML5 표준 검증을 수행하고 true/false를 반환합니다. onChange에서 검증 여부를 확인할 수 있습니다.",
            """
            var field = textField().filled()
                .label("이메일")
                .type(InputType.email)
                .required(true)
                .element();
            field.onchange = evt -> {
                boolean isValid = field.checkValidity();
                if (!isValid) {
                    console.log("입력이 유효하지 않습니다");
                }
                return null;
            };
            """);
        var checkValidityField = textField().filled()
                .label("이메일")
                .type(InputType.email)
                .required(true)
                .element();
        checkValidityField.onchange = evt -> {
            boolean isValid = checkValidityField.checkValidity();
            if (!isValid) {
                checkValidityField.reportValidity();
            }
            return null;
        };
        checkValidityExample.addInteractiveDemo(checkValidityField, false);

        // Validation tests
        checkValidityField.value = "test@example.com";
        assertTrue("checkValidity: 올바른 이메일은 valid", checkValidityField.checkValidity());

        checkValidityField.value = "잘못된이메일";
        assertFalse("checkValidity: 잘못된 이메일은 invalid", checkValidityField.checkValidity());

        // reportValidity() test
        var reportValidityExample = addExampleCode(constraintSection,
            "📘 reportValidity() - 검증 + 에러 표시",
            "유효성 검사 후 자동으로 에러 메시지를 표시합니다.",
            """
            var field = textField().outlined()
                .label("필수 입력")
                .required(true)
                .element();
            field.onchange = evt -> {
                field.reportValidity();  // false면 자동으로 에러 UI 표시
                return null;
            };
            """);
        var reportValidityField = textField().outlined()
                .label("필수 입력")
                .required(true)
                .element();
        reportValidityField.onchange = evt -> {
            reportValidityField.reportValidity();
            return null;
        };
        reportValidityExample.addInteractiveDemo(reportValidityField, false);

        // Validation tests
        assertFalse("reportValidity: 빈 필수 필드는 false 반환", reportValidityField.reportValidity());

        reportValidityField.value = "값 입력";
        assertTrue("reportValidity: 값이 있으면 true 반환", reportValidityField.reportValidity());

        // Test 5: Pattern with suffix-text
        var patternSuffixExample = addExampleCode(constraintSection,
            "📘 Pattern + Suffix 조합",
            "패턴 검증과 접미사를 조합하여 도메인이 고정된 이메일 입력을 만들 수 있습니다.",
            """
            var gmailField = textField().filled()
                .label("Gmail 계정")
                .pattern("[a-zA-Z0-9\\\\-]+")   // 영문, 숫자, 하이픈만
                .suffixText("@gmail.com")
                .value("johndoe")
                .element();

            boolean isValid = gmailField.checkValidity();
            """);
        var emailPattern = textField().filled()
                .name("email")
                .label("Email")
                .pattern("[a-zA-Z0-9\\-]+")
                .suffixText("@gmail.com")
                .value("johndoe")
                .element();
        emailPattern.onchange = evt -> {
            emailPattern.checkValidity();
            emailPattern.reportValidity();
            return null;
        };
        patternSuffixExample.addInteractiveDemo(emailPattern, false);

        var patternValid = emailPattern.checkValidity();
        assertTrue("pattern + suffix: 'johndoe'는 유효한 패턴", patternValid);

        // Test 6: Pattern mismatch
        emailPattern.value = "invalid@email";
        var patternInvalid = !emailPattern.checkValidity();
        assertTrue("pattern + suffix: '@' 포함 시 패턴 불일치", patternInvalid);
        emailPattern.reportValidity();

        // ValidityState test
        var validityStateExample = addExampleCode(constraintSection,
            "📘 ValidityState 객체",
            "validity 속성으로 다양한 검증 상태를 확인할 수 있습니다 (valueMissing, typeMismatch, patternMismatch 등).",
            """
            var field = textField().outlined()
                .label("검증 상태")
                .type(InputType.email)
                .required(true)
                .element();
            field.onchange = evt -> {
                stateCheck.reportValidity();
                // ValidityState 속성 확인
                console.log("valueMissing: " + field.validity.valueMissing);       // 필수값 누락 여부
                console.log("typeMismatch: " + field.validity.typeMismatch);       // 타입 불일치 여부
                console.log("patternMismatch: " + field.validity.patternMismatch); // 패턴 불일치 여부
                console.log("valid: " + field.validity.valid);                     // 전체 유효성
                return null;
            };
            """);
        var stateCheck = textField().outlined()
                .label("검증 상태")
                .type(InputType.email)
                .required(true)
                .element();
        stateCheck.onchange = evt -> {
            stateCheck.reportValidity();
            elemental2.dom.DomGlobal.console.log("valueMissing: " + stateCheck.validity.valueMissing);
            elemental2.dom.DomGlobal.console.log("typeMismatch: " + stateCheck.validity.typeMismatch);
            elemental2.dom.DomGlobal.console.log("patternMismatch: " + stateCheck.validity.patternMismatch);
            elemental2.dom.DomGlobal.console.log("valid: " + stateCheck.validity.valid);
            return null;
        };
        validityStateExample.addInteractiveDemo(stateCheck, false);

        assertNotNull("ValidityState: validity 객체가 존재해야 함", stateCheck.validity);
    }
}
