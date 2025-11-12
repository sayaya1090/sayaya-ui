package dev.sayaya.ui.radio;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.RadioElementBuilder.radio;
import static org.jboss.elemento.Elements.*;

public class RadioPropertiesTest {
    public static void test() {
        printSectionHeader("2. 기본 속성 (Basic Properties)");
        printDescription("Radio의 기본 속성들을 테스트합니다:");
        printDescription("- disabled: 비활성화");
        printDescription("- required: 필수 선택");
        printDescription("- value: 라디오 버튼 값");
        printDescription("- name: 그룹 이름");
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
            "사용자가 라디오 버튼을 선택할 수 없게 합니다.",
            """
            var disabledRadio = radio()
                .disabled(true)
                .ariaLabel("Disabled")
                .element();
            """);
        var disabledRadio = radio()
                .disabled(true)
                .ariaLabel("Disabled")
                .element();
        var disabledState = disabledExample.addInteractiveDemo(disabledRadio);
        disabledState.textContent = "disabled: " + disabledRadio.disabled + " | checked: " + disabledRadio.checked;

        disabledRadio.addEventListener("change", evt -> {
            disabledState.textContent = "disabled: " + disabledRadio.disabled + " | checked: " + disabledRadio.checked;
        });

        assertTrue("disabled 속성: true여야 함", disabledRadio.disabled);

        // Required
        var requiredExample = addExampleCode(propertiesSection,
            "📘 Required (필수)",
            "같은 그룹 내에서 하나라도 required면 전체가 필수 선택이 됩니다.",
            """
            var requiredRadio = radio()
                .name("required-group")
                .value("option-1")
                .required(true)
                .ariaLabel("Required Option")
                .element();
            """);
        var requiredRadio = radio()
                .name("required-group")
                .value("option-1")
                .required(true)
                .ariaLabel("Required Option")
                .element();
        var requiredState = requiredExample.addInteractiveDemo(requiredRadio);
        requiredState.textContent = "required: " + requiredRadio.required + " | checked: " + requiredRadio.checked;

        requiredRadio.addEventListener("change", evt -> {
            requiredState.textContent = "required: " + requiredRadio.required + " | checked: " + requiredRadio.checked;
        });

        assertTrue("required 속성: true여야 함", requiredRadio.required);

        // Value with default
        var valueExample = addExampleCode(propertiesSection,
            "📘 Value (값)",
            "폼 제출 시 전송될 라디오 버튼의 값을 지정합니다. 기본값은 'on'입니다.",
            """
            var customValueRadio = radio()
                .value("custom-value")
                .ariaLabel("Custom Value")
                .element();
            """);
        var customValueRadio = radio()
                .value("custom-value")
                .ariaLabel("Custom Value")
                .element();
        var valueState = valueExample.addInteractiveDemo(customValueRadio);
        valueState.textContent = "value: '" + customValueRadio.value + "' | checked: " + customValueRadio.checked;

        customValueRadio.addEventListener("change", evt -> {
            valueState.textContent = "value: '" + customValueRadio.value + "' | checked: " + customValueRadio.checked;
        });

        assertEquals("value 속성: custom-value여야 함", "custom-value", customValueRadio.value);

        // Default value
        var defaultValueExample = addExampleCode(propertiesSection,
            "📘 Default Value (기본값)",
            "value를 지정하지 않으면 기본값 'on'이 사용됩니다.",
            """
            var defaultValueRadio = radio()
                .ariaLabel("Default Value")
                .element();
            """);
        var defaultValueRadio = radio()
                .ariaLabel("Default Value")
                .element();
        var defaultValueState = defaultValueExample.addInteractiveDemo(defaultValueRadio);
        var defaultVal = defaultValueRadio.value == null ? "null" : defaultValueRadio.value;
        defaultValueState.textContent = "value: '" + defaultVal + "' | checked: " + defaultValueRadio.checked;

        defaultValueRadio.addEventListener("change", evt -> {
            var val = defaultValueRadio.value == null ? "null" : defaultValueRadio.value;
            defaultValueState.textContent = "value: '" + val + "' | checked: " + defaultValueRadio.checked;
        });

        assertTrue("기본값: 'on' 또는 null이어야 함",
                defaultValueRadio.value == null || "on".equals(defaultValueRadio.value));

        // Name
        var nameExample = addExampleCode(propertiesSection,
            "📘 Name (이름)",
            "같은 name을 가진 라디오 버튼들은 하나의 그룹을 형성합니다.",
            """
            var namedRadio = radio()
                .name("color-group")
                .value("red")
                .ariaLabel("Red")
                .element();
            """);
        var namedRadio = radio()
                .name("color-group")
                .value("red")
                .ariaLabel("Red")
                .element();
        var nameState = nameExample.addInteractiveDemo(namedRadio);
        nameState.textContent = "name: '" + namedRadio.name + "' | value: '" + namedRadio.value + "' | checked: " + namedRadio.checked;

        namedRadio.addEventListener("change", evt -> {
            nameState.textContent = "name: '" + namedRadio.name + "' | value: '" + namedRadio.value + "' | checked: " + namedRadio.checked;
        });

        assertEquals("name 속성: color-group이어야 함", "color-group", namedRadio.name);

        // Builder getter methods
        addExampleCode(propertiesSection,
            "📘 Builder Getters (빌더 조회 메서드)",
            "빌더 패턴에서 현재 설정된 값을 조회할 수 있습니다.",
            """
            var builder = radio()
                .name("test-name")
                .value("test-value")
                .disabled(true)
                .required(true);
            
            String name = builder.name();
            String value = builder.value();
            boolean disabled = builder.isDisabled();
            boolean required = builder.isRequired();
            boolean selected = builder.isSelected();
            """);
        var builder = radio()
                .name("test-name")
                .value("test-value");
        assertEquals("빌더 name getter: test-name을 반환해야 함", "test-name", builder.name());
        assertEquals("빌더 value getter: test-value를 반환해야 함", "test-value", builder.value());

        builder.disabled(true);
        assertTrue("빌더 disabled getter: true를 반환해야 함", builder.isDisabled());

        builder.required(true);
        assertTrue("빌더 required getter: true를 반환해야 함", builder.isRequired());

        builder.select(true);
        assertTrue("빌더 selected getter: true를 반환해야 함", builder.isSelected());
    }
}
