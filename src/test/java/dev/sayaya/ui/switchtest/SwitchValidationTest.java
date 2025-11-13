package dev.sayaya.ui.switchtest;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.SwitchElementBuilder.sw;
import static org.jboss.elemento.Elements.*;

public class SwitchValidationTest {
    public static void test() {
        printSectionHeader("4. 유효성 검증 (Validation)");
        printDescription("Switch의 유효성 검증 기능을 테스트합니다:");
        printDescription("- checkValidity(): 유효성 검사");
        printDescription("- reportValidity(): 유효성 보고");
        printDescription("- setCustomValidity(): 커스텀 메시지");
        printSeparator();

        var validationSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(validationSection);

        validationSection.appendChild(h(3).text("Validation").element());

        // Required Validation
        var requiredValidationExample = addExampleCode(validationSection,
            "📘 Required Validation (필수 검증)",
            "required가 true일 때 선택되지 않으면 검증 실패합니다.",
            """
            var switchElem = sw()
                .required(true)
                .ariaLabel("Required switch")
                .element();
            
            boolean isValid = switchElem.checkValidity();
            """);
        var requiredSwitch = sw()
                .required(true)
                .ariaLabel("Required switch")
                .element();
        var requiredState = requiredValidationExample.addInteractiveDemo(requiredSwitch);
        requiredState.textContent = "required: " + requiredSwitch.required + 
            " | selected: " + requiredSwitch.selected + 
            " | valid: " + requiredSwitch.checkValidity();

        requiredSwitch.addEventListener("change", evt -> {
            requiredState.textContent = "required: " + requiredSwitch.required + 
                " | selected: " + requiredSwitch.selected + 
                " | valid: " + requiredSwitch.checkValidity();
        });
        requiredSwitch.reportValidity();

        assertTrue("required 속성: true", requiredSwitch.required);
        assertFalse("초기 상태: 선택되지 않아 유효하지 않음", requiredSwitch.checkValidity());

        // CheckValidity
        var checkValidityExample = addExampleCode(validationSection,
            "📘 checkValidity() (유효성 검사)",
            "스위치가 유효한지 검사합니다.",
            """
            var switchElem = sw()
                .required(true)
                .select(true)
                .ariaLabel("Valid switch")
                .element();
            
            boolean isValid = switchElem.checkValidity();
            """);
        var validSwitch = sw()
                .required(true)
                .select(true)
                .ariaLabel("Valid switch")
                .element();
        var validState = checkValidityExample.addInteractiveDemo(validSwitch);
        validState.textContent = "selected: " + validSwitch.selected + " | valid: " + validSwitch.checkValidity();

        validSwitch.addEventListener("change", evt -> {
            validState.textContent = "selected: " + validSwitch.selected + " | valid: " + validSwitch.checkValidity();
        });

        assertTrue("checkValidity: 선택되어 유효함", validSwitch.checkValidity());

        // Custom Validity
        var customValidityExample = addExampleCode(validationSection,
            "📘 setCustomValidity() (커스텀 검증)",
            "커스텀 유효성 메시지를 설정합니다.",
            """
            var switchElem = sw()
                .setCustomValidity("이 스위치를 활성화해야 합니다")
                .ariaLabel("Custom validity")
                .element();
            
            boolean isValid = switchElem.checkValidity();
            String message = switchElem.validationMessage;
            """);
        var customValidSwitch = sw()
                .setCustomValidity("이 스위치를 활성화해야 합니다")
                .ariaLabel("Custom validity")
                .element();
        var customValidState = customValidityExample.addInteractiveDemo(customValidSwitch);
        customValidState.textContent = "valid: " + customValidSwitch.checkValidity() + 
            " | message: '" + customValidSwitch.validationMessage + "'";

        customValidSwitch.addEventListener("change", evt -> {
            customValidState.textContent = "valid: " + customValidSwitch.checkValidity() + 
                " | message: '" + customValidSwitch.validationMessage + "'";
        });

        assertFalse("커스텀 메시지 설정 시 유효하지 않음", customValidSwitch.checkValidity());
        assertEquals("validationMessage 확인", "이 스위치를 활성화해야 합니다", customValidSwitch.validationMessage);

        // Clear Custom Validity
        var clearValidityExample = addExampleCode(validationSection,
            "📘 Clear Custom Validity (검증 초기화)",
            "빈 문자열로 설정하면 커스텀 검증을 초기화합니다.",
            """
            var switchElem = sw()
                .setCustomValidity("에러 메시지")
                .ariaLabel("Clear validity")
                .element();
            
            // 커스텀 검증 초기화
            switchElem.setCustomValidity("");
            boolean isValid = switchElem.checkValidity();
            """);
        var clearValidSwitch = sw()
                .setCustomValidity("에러 메시지")
                .ariaLabel("Clear validity")
                .element();
        
        assertFalse("초기: 유효하지 않음", clearValidSwitch.checkValidity());
        
        clearValidSwitch.setCustomValidity("");
        var clearValidState = clearValidityExample.addInteractiveDemo(clearValidSwitch);
        clearValidState.textContent = "valid: " + clearValidSwitch.checkValidity() + 
            " | message: '" + clearValidSwitch.validationMessage + "'";

        assertTrue("초기화 후: 유효함", clearValidSwitch.checkValidity());
        assertEquals("초기화 후 validationMessage는 빈 문자열", "", clearValidSwitch.validationMessage);
    }
}
