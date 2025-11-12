package dev.sayaya.ui.radio;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.RadioElementBuilder.radio;
import static org.jboss.elemento.Elements.*;

public class RadioBasicUsageTest {
    public static void test() {
        printSectionHeader("1. 기본 사용법 (Basic Usage)");
        printDescription("Radio 버튼의 기본적인 사용 방법을 테스트합니다:");
        printDescription("- checked: 선택 상태");
        printDescription("- name: 그룹 이름 (같은 이름끼리 하나만 선택됨)");
        printDescription("- value: 라디오 버튼 값");
        printSeparator();

        var basicSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(basicSection);

        basicSection.appendChild(h(3).text("Basic Usage").element());

        // Unchecked Radio
        var uncheckedExample = addExampleCode(basicSection,
            "📘 Unchecked Radio (미선택 상태)",
            "기본 상태의 라디오 버튼입니다.",
            """
            var radio = radio()
                .ariaLabel("Unchecked")
                .element();
            """);
        var uncheckedRadio = radio()
                .ariaLabel("Unchecked")
                .element();
        var uncheckedState = uncheckedExample.addInteractiveDemo(uncheckedRadio);
        uncheckedState.textContent = "checked: " + uncheckedRadio.checked;
        uncheckedRadio.addEventListener("change", evt -> {
            uncheckedState.textContent = "checked: " + uncheckedRadio.checked;
        });

        assertFalse("초기 상태: 선택되지 않아야 함", uncheckedRadio.checked);

        // Checked Radio
        var checkedExample = addExampleCode(basicSection,
            "📘 Checked Radio (선택 상태)",
            "초기 선택 상태로 설정된 라디오 버튼입니다.",
            """
            var radio = radio()
                .select(true)
                .ariaLabel("Checked")
                .element();
            """);
        var checkedRadio = radio()
                .select(true)
                .ariaLabel("Checked")
                .element();
        var checkedState = checkedExample.addInteractiveDemo(checkedRadio);
        checkedState.textContent = "checked: " + checkedRadio.checked;
        checkedRadio.addEventListener("change", evt -> {
            checkedState.textContent = "checked: " + checkedRadio.checked;
        });

        assertTrue("선택 상태: true여야 함", checkedRadio.checked);

        // With Value
        var valueExample = addExampleCode(basicSection,
            "📘 Radio with Value (값 설정)",
            "라디오 버튼에 값을 지정합니다. 폼 제출 시 사용됩니다.",
            """
            var radio = radio()
                .name("option")
                .value("option-1")
                .ariaLabel("Option 1")
                .element();
            """);
        var valueRadio = radio()
                .name("option")
                .value("option-1")
                .ariaLabel("Option 1")
                .element();
        var valueState = valueExample.addInteractiveDemo(valueRadio);
        valueState.textContent = "name: '" + valueRadio.name + "' | value: '" + valueRadio.value + "' | checked: " + valueRadio.checked;
        valueRadio.addEventListener("change", evt -> {
            valueState.textContent = "name: '" + valueRadio.name + "' | value: '" + valueRadio.value + "' | checked: " + valueRadio.checked;
        });

        assertEquals("name: option이어야 함", "option", valueRadio.name);
        assertEquals("value: option-1이어야 함", "option-1", valueRadio.value);
    }
}
