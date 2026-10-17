package dev.sayaya.ui.textfield;

import org.jboss.elemento.InputType;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.TextFieldElementBuilder.textField;
import static org.jboss.elemento.Elements.*;

public class InputTypesTest {
    public static void test() {
        printSectionHeader("3. 입력 타입 (Input Types)");
        printDescription("다양한 입력 타입을 지원합니다:");
        printDescription("- email: 이메일 입력");
        printDescription("- password: 비밀번호 입력");
        printDescription("- number: 숫자 입력 (min, max, step)");
        printDescription("- tel: 전화번호 입력");
        printDescription("- url: 웹주소 입력");
        printSeparator();

        var typesSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(typesSection);

        typesSection.appendChild(h(3).text("Input Types").element());

        // Email type
        var emailExample = addExampleCode(typesSection,
            "📘 Email 입력",
            "이메일 형식 검증이 자동으로 적용됩니다. 입력을 마치면(포커스 이동 시) 자동으로 검증됩니다.",
            """
            var email = textField().filled()
                .type(InputType.email)
                .label("이메일")
                .placeholder("example@email.com")
                .required(true)
                .element();
            email.onchange = evt -> {
                email.reportValidity();  // 입력 완료 시 검증
                return null;
            };
            """);
        var emailField = textField().filled()
                .type(InputType.email)
                .label("이메일")
                .placeholder("example@email.com")
                .required(true)
                .element();
        emailField.onchange = evt -> {
            emailField.reportValidity();
            return null;
        };
        emailExample.addInteractiveDemo(emailField, false);
        assertEquals("type 속성: 'email'이어야 함",
                "email", emailField.type);

        // Email validation test
        emailField.value = "invalid-email";
        assertFalse("email validation: 잘못된 이메일은 invalid", emailField.checkValidity());

        emailField.value = "valid@example.com";
        assertTrue("email validation: 올바른 이메일은 valid", emailField.checkValidity());

        // Password type
        var passwordExample = addExampleCode(typesSection,
            "📘 Password 입력",
            "입력한 텍스트가 자동으로 마스킹됩니다.",
            """
            var password = textField().outlined()
                .type(InputType.password)
                .label("비밀번호")
                .element();
            """);
        var passwordField = textField().outlined()
                .type(InputType.password)
                .label("비밀번호")
                .element();
        passwordExample.addInteractiveDemo(passwordField, false);
        assertEquals("type 속성: 'password'여야 함",
                "password", passwordField.type);

        // Number type
        var numberExample = addExampleCode(typesSection,
            "📘 Number 입력 (min, max, step)",
            "숫자 입력 시 최소값, 최대값, 증감 단위를 설정할 수 있습니다. 범위를 벗어나면 자동으로 검증됩니다.",
            """
            var age = textField().filled()
                .type(InputType.number)
                .label("나이")
                .min("0")      // 최소값
                .max("150")    // 최대값
                .step("1")     // 증감 단위
                .element();
            age.onchange = evt -> {
                age.reportValidity();  // 범위 검증
                return null;
            };
            """);
        var numberField = textField().filled()
                .type(InputType.number)
                .label("나이")
                .min("0")
                .max("150")
                .step("1")
                .element();
        numberField.onchange = evt -> {
            numberField.reportValidity();
            return null;
        };
        numberExample.addInteractiveDemo(numberField, false);
        assertEquals("type 속성: 'number'여야 함",
                "number", numberField.type);
        assertEquals("min 속성: '0'이어야 함", "0", numberField.min);
        assertEquals("max 속성: '150'이어야 함", "150", numberField.max);
        assertEquals("step 속성: '1'이어야 함", "1", numberField.step);
        var multipleField = textField().filled().label("이메일").multiple(true).element();
        assertTrue("multiple 속성: true여야 함", multipleField.multiple);

        // Number range validation test
        numberField.value = "-10";
        assertFalse("number validation: 최소값 미만은 invalid", numberField.checkValidity());

        numberField.value = "200";
        assertFalse("number validation: 최대값 초과는 invalid", numberField.checkValidity());

        numberField.value = "25";
        assertTrue("number validation: 범위 내 값은 valid", numberField.checkValidity());

        // Tel type
        var telExample = addExampleCode(typesSection,
            "📘 Tel 입력",
            "전화번호 입력 필드입니다. 모바일에서 숫자 키패드가 표시됩니다.",
            """
            var phone = textField().outlined()
                .type(InputType.tel)
                .label("전화번호")
                .placeholder("010-1234-5678")
                .element();
            """);
        var telField = textField().outlined()
                .type(InputType.tel)
                .label("전화번호")
                .placeholder("010-1234-5678")
                .element();
        telExample.addInteractiveDemo(telField, false);
        assertEquals("type 속성: 'tel'이어야 함",
                "tel", telField.type);

        // URL type
        var urlExample = addExampleCode(typesSection,
            "📘 URL 입력",
            "웹 주소 입력 필드입니다. URL 형식 검증이 자동으로 적용됩니다 (프로토콜 포함 필수).",
            """
            var website = textField().filled()
                .type(InputType.url)
                .label("웹사이트")
                .placeholder("https://example.com")
                .required(true)
                .element();
            website.onchange = evt -> {
                website.reportValidity();  // URL 형식 검증
                return null;
            };
            """);
        var urlField = textField().filled()
                .type(InputType.url)
                .label("웹사이트")
                .placeholder("https://example.com")
                .required(true)
                .element();
        urlField.onchange = evt -> {
            urlField.reportValidity();
            return null;
        };
        urlExample.addInteractiveDemo(urlField, false);
        assertEquals("type 속성: 'url'이어야 함",
                "url", urlField.type);

        // URL validation test
        urlField.value = "example.com";
        assertFalse("url validation: 프로토콜 없는 URL은 invalid", urlField.checkValidity());

        urlField.value = "https://example.com";
        assertTrue("url validation: 프로토콜 포함 URL은 valid", urlField.checkValidity());
    }
}
