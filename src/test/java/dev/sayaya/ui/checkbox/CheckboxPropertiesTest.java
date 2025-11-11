package dev.sayaya.ui.checkbox;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.CheckboxElementBuilder.checkbox;
import static org.jboss.elemento.Elements.*;

public class CheckboxPropertiesTest {
    public static void test() {
        printSectionHeader("2. 기본 속성 (Basic Properties)");
        printDescription("Checkbox의 기본 속성들을 테스트합니다:");
        printDescription("- disabled: 비활성화");
        printDescription("- required: 필수 선택");
        printDescription("- value: 체크박스 값");
        printDescription("- name: 폼 필드 이름");
        printSeparator();

        var propertiesSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(propertiesSection);

        propertiesSection.appendChild(h(3).text("Basic Properties").element());

        // Disabled
        var disabledExample = addExampleCode(propertiesSection,
            "📘 Disabled (비활성화)",
            "사용자가 체크박스를 선택하거나 변경할 수 없게 합니다.",
            """
            var disabledCheckbox = checkbox()
                .disabled(true)
                .ariaLabel("Disabled")
                .element();
            """);
        var disabledCheckbox = checkbox()
                .disabled(true)
                .ariaLabel("Disabled")
                .element();
        var disabledState = disabledExample.addInteractiveDemo(disabledCheckbox);
        disabledState.textContent = "disabled: " + disabledCheckbox.disabled + " | checked: " + disabledCheckbox.checked;

        disabledCheckbox.addEventListener("change", evt -> {
            disabledState.textContent = "disabled: " + disabledCheckbox.disabled + " | checked: " + disabledCheckbox.checked;
        });

        assertTrue("disabled 속성: true여야 함", disabledCheckbox.disabled);

        // Required
        var requiredExample = addExampleCode(propertiesSection,
            "📘 Required (필수)",
            "폼 제출 시 반드시 체크되어야 함을 나타냅니다.",
            """
            var requiredCheckbox = checkbox()
                .required(true)
                .ariaLabel("Required")
                .element();
            """);
        var requiredCheckbox = checkbox()
                .required(true)
                .ariaLabel("Required")
                .element();
        var requiredState = requiredExample.addInteractiveDemo(requiredCheckbox);
        requiredState.textContent = "required: " + requiredCheckbox.required + " | checked: " + requiredCheckbox.checked;

        requiredCheckbox.addEventListener("change", evt -> {
            requiredState.textContent = "required: " + requiredCheckbox.required + " | checked: " + requiredCheckbox.checked;
        });

        assertTrue("required 속성: true여야 함", requiredCheckbox.required);

        // Value
        var valueExample = addExampleCode(propertiesSection,
            "📘 Value (값)",
            "폼 제출 시 전송될 체크박스의 값을 지정합니다. 기본값은 'on'입니다.",
            """
            var valueCheckbox = checkbox()
                .value("custom-value")
                .ariaLabel("Custom Value")
                .element();
            """);
        var valueCheckbox = checkbox()
                .value("custom-value")
                .ariaLabel("Custom Value")
                .element();
        var valueState = valueExample.addInteractiveDemo(valueCheckbox);
        valueState.textContent = "value: '" + valueCheckbox.value + "' | checked: " + valueCheckbox.checked;

        valueCheckbox.addEventListener("change", evt -> {
            valueState.textContent = "value: '" + valueCheckbox.value + "' | checked: " + valueCheckbox.checked;
        });

        assertEquals("value 속성: custom-value여야 함", "custom-value", valueCheckbox.value);

        // Default value
        var defaultValueExample = addExampleCode(propertiesSection,
            "📘 Default Value (기본값)",
            "value를 지정하지 않으면 기본값 'on'이 사용됩니다.",
            """
            var defaultValueCheckbox = checkbox()
                .ariaLabel("Default Value")
                .element();
            """);
        var defaultValueCheckbox = checkbox()
                .ariaLabel("Default Value")
                .element();
        var defaultValueState = defaultValueExample.addInteractiveDemo(defaultValueCheckbox);
        var defaultVal = defaultValueCheckbox.value == null ? "null" : defaultValueCheckbox.value;
        defaultValueState.textContent = "value: '" + defaultVal + "' | checked: " + defaultValueCheckbox.checked;

        defaultValueCheckbox.addEventListener("change", evt -> {
            var val = defaultValueCheckbox.value == null ? "null" : defaultValueCheckbox.value;
            defaultValueState.textContent = "value: '" + val + "' | checked: " + defaultValueCheckbox.checked;
        });

        assertTrue("기본값: 'on' 또는 null이어야 함",
                defaultValueCheckbox.value == null || "on".equals(defaultValueCheckbox.value));

        // Name
        var nameExample = addExampleCode(propertiesSection,
            "📘 Name (이름)",
            "폼 제출 시 사용될 필드 이름입니다.",
            """
            var namedCheckbox = checkbox()
                .name("agreement")
                .ariaLabel("Agreement")
                .element();
            """);
        var namedCheckbox = checkbox()
                .name("agreement")
                .ariaLabel("Agreement")
                .element();
        var nameState = nameExample.addInteractiveDemo(namedCheckbox);
        nameState.textContent = "name: '" + namedCheckbox.name + "' | checked: " + namedCheckbox.checked;

        namedCheckbox.addEventListener("change", evt -> {
            nameState.textContent = "name: '" + namedCheckbox.name + "' | checked: " + namedCheckbox.checked;
        });

        assertEquals("name 속성: agreement여야 함", "agreement", namedCheckbox.name);

        // Builder getter methods
        addExampleCode(propertiesSection,
            "📘 Builder Getters (빌더 조회 메서드)",
            "빌더 패턴에서 현재 설정된 값을 조회할 수 있습니다.",
            """
            var builder = checkbox()
                .name("test-name")
                .value("test-value")
                .disabled(true)
                .required(true);
            
            String name = builder.name();
            String value = builder.value();
            boolean disabled = builder.isDisabled();
            boolean required = builder.isRequired();
            """);
        var builder = checkbox()
                .name("test-name")
                .value("test-value");
        assertEquals("빌더 name getter: test-name을 반환해야 함", "test-name", builder.name());
        assertEquals("빌더 value getter: test-value를 반환해야 함", "test-value", builder.value());

        builder.disabled(true);
        assertTrue("빌더 disabled getter: true를 반환해야 함", builder.isDisabled());

        builder.required(true);
        assertTrue("빌더 required getter: true를 반환해야 함", builder.isRequired());
    }
}
