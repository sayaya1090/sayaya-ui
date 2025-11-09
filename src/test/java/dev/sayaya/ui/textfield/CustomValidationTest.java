package dev.sayaya.ui.textfield;

import org.jboss.elemento.InputType;

import static dev.sayaya.ui.elements.TextFieldElementBuilder.textField;
import static elemental2.dom.DomGlobal.console;
import static org.jboss.elemento.Elements.*;

public class CustomValidationTest {
    public static void test() {
        TestHelper.printSectionHeader("6. 사용자 정의 검증 (Custom Validation)");
        TestHelper.printDescription("사용자 정의 검증 메시지와 동적 에러 관리:");
        TestHelper.printDescription("- setCustomValidity(): 커스텀 에러 메시지");
        TestHelper.printDescription("- error 속성: 동적 에러 상태 변경");
        TestHelper.printDescription("- errorText 속성: 동적 에러 메시지");
        TestHelper.printSeparator();

        var customSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(customSection);

        customSection.appendChild(h(3).text("Custom Validation").element());

        // setCustomValidity() test
        TestHelper.addExampleCode(customSection,
            "📘 setCustomValidity() - 서버 검증",
            "서버에서 검증 후 커스텀 에러를 설정할 수 있습니다 (예: 중복 확인).",
            """
            var usernameField = textField().filled()
                .label("사용자명")
                .element();
            usernameField.onchange = evt -> {
                // 서버 검증 후 (예시: "admin"은 중복으로 가정)
                if ("admin".equals(usernameField.value)) {
                    usernameField.setCustomValidity("이미 사용 중인 사용자명입니다");
                } else {
                    usernameField.setCustomValidity("");  // 에러 제거
                }
 
                return null;
            };
            """);
        var customField = textField().filled()
                .label("사용자명")
                .element();
        customField.onchange = evt -> {
            console.log("admin".equals(customField.value));
            if ("admin".equals(customField.value)) {
                customField.setCustomValidity("이미 사용 중인 사용자명입니다");
            } else {
                customField.setCustomValidity("");
            }
            customField.reportValidity();
            return null;
        };
        customSection.appendChild(customField);

        // Validation tests
        customField.value = "admin";
        customField.setCustomValidity("이미 사용 중인 사용자명입니다");
        TestHelper.assertFalse("custom validation: 커스텀 에러 설정 시 invalid", customField.checkValidity());

        customField.setCustomValidity("");
        TestHelper.assertTrue("custom validation: 에러 제거 시 valid", customField.checkValidity());

        // Test 3: Manual error state
        TestHelper.addExampleCode(customSection,
            "📘 즉시 에러 표시",
            "빌더 패턴에서 바로 에러를 설정할 수 있습니다.",
            """
            var field = textField().outlined()
                .label("비밀번호")
                .value("123")
                .error("비밀번호가 너무 짧습니다")  // String으로 설정
                .element();
            """);
        var manualErrorField = textField().outlined()
                .label("비밀번호")
                .value("123")
                .error("비밀번호가 너무 짧습니다")
                .element();
        customSection.appendChild(manualErrorField);

        TestHelper.assertTrue("manual error: error 상태여야 함", manualErrorField.error);
        TestHelper.assertEquals("manual error: errorText가 설정되어야 함",
                "비밀번호가 너무 짧습니다", manualErrorField.errorText);

        // Dynamic error update test
        TestHelper.addExampleCode(customSection,
            "📘 동적 에러 상태 변경",
            "실시간으로 에러 상태를 변경할 수 있습니다.",
            """
            var amountField = textField().filled()
                .label("금액")
                .type(InputType.number)
                .element();
            amountField.onchange = evt -> {
                // 값 검증 후 동적으로 에러 설정
                try {
                    int amount = Integer.parseInt(amountField.value);
                    if (amount < 1000) {
                        amountField.error = true;
                        amountField.errorText = "최소 금액은 1000원입니다";
                    } else {
                        amountField.error = false;
                        amountField.errorText = "";
                    }
                } catch (Exception e) {
                    amountField.error = true;
                    amountField.errorText = "숫자를 입력하세요";
                }
                return null;
            };
            """);
        var dynamicField = textField().filled()
                .label("금액")
                .type(InputType.number)
                .element();
        dynamicField.onchange = evt -> {
            try {
                int amount = Integer.parseInt(dynamicField.value);
                if (amount < 1000) {
                    dynamicField.error = true;
                    dynamicField.errorText = "최소 금액은 1000원입니다";
                } else {
                    dynamicField.error = false;
                    dynamicField.errorText = "";
                }
            } catch (Exception e) {
                dynamicField.error = true;
                dynamicField.errorText = "숫자를 입력하세요";
            }
            return null;
        };
        customSection.appendChild(dynamicField);

        // Validation tests
        TestHelper.assertFalse("dynamic error: 초기 상태는 에러 없음", dynamicField.error);

        dynamicField.value = "100";
        dynamicField.error = true;
        dynamicField.errorText = "최소 금액은 1000원입니다";
        TestHelper.assertTrue("dynamic error: error 상태로 변경되어야 함", dynamicField.error);
        TestHelper.assertEquals("dynamic error: errorText가 업데이트되어야 함",
                "최소 금액은 1000원입니다", dynamicField.errorText);
    }
}
