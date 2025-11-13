package dev.sayaya.ui.switchtest;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.SwitchElementBuilder.sw;
import static org.jboss.elemento.Elements.*;

public class SwitchPropertiesTest {
    public static void test() {
        printSectionHeader("2. 기본 속성 (Basic Properties)");
        printDescription("Switch의 기본 속성들을 테스트합니다:");
        printDescription("- disabled: 비활성화");
        printDescription("- required: 필수 선택");
        printDescription("- value: 스위치 값");
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
            "사용자가 스위치를 변경할 수 없게 합니다.",
            """
            var switchElem = sw()
                .disabled(true)
                .ariaLabel("Disabled")
                .element();
            """);
        var disabledSwitch = sw()
                .disabled(true)
                .ariaLabel("Disabled")
                .element();
        var disabledState = disabledExample.addInteractiveDemo(disabledSwitch);
        disabledState.textContent = "disabled: " + disabledSwitch.disabled + " | selected: " + disabledSwitch.selected;

        disabledSwitch.addEventListener("change", evt -> {
            disabledState.textContent = "disabled: " + disabledSwitch.disabled + " | selected: " + disabledSwitch.selected;
        });

        assertTrue("disabled 속성: true여야 함", disabledSwitch.disabled);

        // Required
        var requiredExample = addExampleCode(propertiesSection,
            "📘 Required (필수)",
            "폼 제출 시 반드시 선택되어야 함을 나타냅니다.",
            """
            var switchElem = sw()
                .required(true)
                .ariaLabel("Required")
                .element();
            """);
        var requiredSwitch = sw()
                .required(true)
                .ariaLabel("Required")
                .element();
        var requiredState = requiredExample.addInteractiveDemo(requiredSwitch);
        requiredState.textContent = "required: " + requiredSwitch.required + " | selected: " + requiredSwitch.selected;

        requiredSwitch.addEventListener("change", evt -> {
            requiredState.textContent = "required: " + requiredSwitch.required + " | selected: " + requiredSwitch.selected;
        });

        assertTrue("required 속성: true여야 함", requiredSwitch.required);

        // Value
        var valueExample = addExampleCode(propertiesSection,
            "📘 Value (값)",
            "폼 제출 시 전송될 스위치의 값을 지정합니다. 기본값은 'on'입니다.",
            """
            var switchElem = sw()
                .value("custom-value")
                .ariaLabel("Custom Value")
                .element();
            """);
        var valueSwitch = sw()
                .value("custom-value")
                .ariaLabel("Custom Value")
                .element();
        var valueState = valueExample.addInteractiveDemo(valueSwitch);
        valueState.textContent = "value: '" + valueSwitch.value + "' | selected: " + valueSwitch.selected;

        valueSwitch.addEventListener("change", evt -> {
            valueState.textContent = "value: '" + valueSwitch.value + "' | selected: " + valueSwitch.selected;
        });

        assertEquals("value 속성: custom-value여야 함", "custom-value", valueSwitch.value);

        // Default value
        var defaultValueExample = addExampleCode(propertiesSection,
            "📘 Default Value (기본값)",
            "value를 지정하지 않으면 기본값 'on'이 사용됩니다.",
            """
            var switchElem = sw()
                .ariaLabel("Default Value")
                .element();
            """);
        var defaultValueSwitch = sw()
                .ariaLabel("Default Value")
                .element();
        var defaultValueState = defaultValueExample.addInteractiveDemo(defaultValueSwitch);
        var defaultVal = defaultValueSwitch.value == null ? "null" : defaultValueSwitch.value;
        defaultValueState.textContent = "value: '" + defaultVal + "' | selected: " + defaultValueSwitch.selected;

        defaultValueSwitch.addEventListener("change", evt -> {
            var val = defaultValueSwitch.value == null ? "null" : defaultValueSwitch.value;
            defaultValueState.textContent = "value: '" + val + "' | selected: " + defaultValueSwitch.selected;
        });

        assertTrue("기본값: 'on' 또는 null이어야 함",
                defaultValueSwitch.value == null || "on".equals(defaultValueSwitch.value));

        // Name
        var nameExample = addExampleCode(propertiesSection,
            "📘 Name (이름)",
            "폼 제출 시 사용될 필드 이름입니다.",
            """
            var switchElem = sw()
                .name("notifications")
                .ariaLabel("Notifications")
                .element();
            """);
        var namedSwitch = sw()
                .name("notifications")
                .ariaLabel("Notifications")
                .element();
        var nameState = nameExample.addInteractiveDemo(namedSwitch);
        nameState.textContent = "name: '" + namedSwitch.name + "' | selected: " + namedSwitch.selected;

        namedSwitch.addEventListener("change", evt -> {
            nameState.textContent = "name: '" + namedSwitch.name + "' | selected: " + namedSwitch.selected;
        });

        assertEquals("name 속성: notifications여야 함", "notifications", namedSwitch.name);

        // Builder getter methods
        addExampleCode(propertiesSection,
            "📘 Builder Getters (빌더 조회 메서드)",
            "빌더 패턴에서 현재 설정된 값을 조회할 수 있습니다.",
            """
            var builder = sw()
                .name("test-name")
                .value("test-value")
                .disabled(true)
                .required(true)
                .select(true)
                .icons(true);
            
            String name = builder.name();
            String value = builder.value();
            boolean disabled = builder.isDisabled();
            boolean required = builder.isRequired();
            boolean selected = builder.isSelected();
            boolean hasIcons = builder.hasIcons();
            """);
        var builder = sw()
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

        builder.icons(true);
        assertTrue("빌더 icons getter: true를 반환해야 함", builder.hasIcons());
    }
}
