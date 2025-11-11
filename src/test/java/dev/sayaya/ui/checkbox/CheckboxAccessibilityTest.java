package dev.sayaya.ui.checkbox;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.CheckboxElementBuilder.checkbox;
import static org.jboss.elemento.Elements.*;

public class CheckboxAccessibilityTest {
    public static void test() {
        printSectionHeader("4. 접근성 (Accessibility)");
        printDescription("Checkbox의 접근성 기능을 테스트합니다:");
        printDescription("- ariaLabel: 스크린 리더용 라벨");
        printDescription("- Label 요소와의 연결");
        printDescription("- 폼 통합");
        printSeparator();

        var accessibilitySection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(accessibilitySection);

        accessibilitySection.appendChild(h(3).text("Accessibility").element());

        // Aria Label
        var ariaExample = addExampleCode(accessibilitySection,
            "📘 Aria Label (접근성 라벨)",
            "스크린 리더가 읽을 수 있도록 라벨을 지정합니다.",
            """
            var ariaCheckbox = checkbox()
                .ariaLabel("Accept terms and conditions")
                .element();
            """);
        var ariaCheckbox = checkbox()
                .ariaLabel("Accept terms and conditions")
                .element();
        var ariaState = ariaExample.addInteractiveDemo(ariaCheckbox);
        ariaState.textContent = "aria-label: '" + ariaCheckbox.getAttribute("aria-label") + "' | checked: " + ariaCheckbox.checked;
        ariaCheckbox.addEventListener("change", evt -> {
            ariaState.textContent = "aria-label: '" + ariaCheckbox.getAttribute("aria-label") + "' | checked: " + ariaCheckbox.checked;
        });
        assertEquals("aria-label: 올바르게 설정되어야 함",
                "Accept terms and conditions",
                ariaCheckbox.getAttribute("aria-label"));

        // Inline label
        var inlineLabelExample = addExampleCode(accessibilitySection,
            "📘 Inline Label (인라인 라벨)",
            "체크박스를 label 요소 안에 배치하여 클릭 영역을 확장합니다.",
            """
            var labelElement = label()
                .add(checkbox().ariaLabel("Checkbox one").element())
                .add(" Checkbox one")
                .element();
            """);
        var labelElement1 = label()
                .add(checkbox().ariaLabel("Checkbox one").element())
                .add(" Checkbox one")
                .element();
        inlineLabelExample.addInteractiveDemo(labelElement1, false);
        var checkbox1 = (elemental2.dom.HTMLElement) labelElement1.querySelector("md-checkbox");
        assertNotNull("라벨 예제: 체크박스가 라벨 안에 존재해야 함", checkbox1);

        // External label
        var externalLabelExample = addExampleCode(accessibilitySection,
            "📘 External Label (외부 라벨)",
            "id와 for 속성을 사용하여 체크박스와 라벨을 연결합니다.",
            """
            var checkbox2 = checkbox()
                .id("checkbox-two")
                .ariaLabel("Checkbox two")
                .element();

            var label2 = label()
                .attr("for", "checkbox-two")
                .add("Checkbox two")
                .element();
            """);
        var checkbox2 = checkbox()
                .id("checkbox-two")
                .ariaLabel("Checkbox two")
                .element();
        var externalLabelDemo = div().style("display", "flex").style("flex-direction", "column").style("gap", "8px").element();
        externalLabelDemo.appendChild(checkbox2);

        var label2 = label()
                .attr("for", "checkbox-two")
                .add("Checkbox two")
                .element();
        externalLabelDemo.appendChild(label2);
        externalLabelExample.addInteractiveDemo(externalLabelDemo, false);

        assertEquals("외부 라벨: 체크박스 id는 'checkbox-two'여야 함",
                "checkbox-two", checkbox2.id);
        assertEquals("외부 라벨: 라벨 for 속성은 'checkbox-two'여야 함",
                "checkbox-two", label2.getAttribute("for"));

        // Form integration
        var formExample = addExampleCode(accessibilitySection,
            "📘 Form Integration (폼 통합)",
            "체크박스를 폼에 통합하여 데이터를 제출합니다.",
            """
            var form = form().id("preferences-form").element();

            var newsletter = checkbox()
                .name("newsletter")
                .value("yes")
                .select(true)
                .ariaLabel("Subscribe to newsletter")
                .element();
            form.appendChild(newsletter);

            var terms = checkbox()
                .name("terms")
                .value("accepted")
                .required(true)
                .select(true)
                .ariaLabel("Accept terms and conditions")
                .element();
            form.appendChild(terms);
            """);
        var testForm = form().id("preferences-form").element();

        var newsletter = checkbox()
                .name("newsletter")
                .value("yes")
                .select(true)
                .ariaLabel("Subscribe to newsletter")
                .element();
        testForm.appendChild(newsletter);

        var terms = checkbox()
                .name("terms")
                .value("accepted")
                .required(true)
                .select(true)
                .ariaLabel("Accept terms and conditions")
                .element();
        testForm.appendChild(terms);

        var formDemo = div().style("display", "flex").style("flex-direction", "column").style("gap", "8px").element();
        formDemo.appendChild(testForm);

        var newsletterLabel = label()
                .add(newsletter)
                .add(" Subscribe to newsletter (name: newsletter, value: yes)")
                .element();
        testForm.appendChild(newsletterLabel);

        var termsLabel = label()
                .add(terms)
                .add(" Accept terms (name: terms, value: accepted, required)")
                .element();
        testForm.appendChild(termsLabel);

        var formState = formExample.addInteractiveDemo(formDemo);

        var updateFormState = new Runnable() {
            @Override
            public void run() {
                formState.textContent = "newsletter: " + newsletter.checked +
                    " | terms: " + terms.checked +
                    " (required: " + terms.required + ")";
            }
        };
        updateFormState.run();

        newsletter.addEventListener("change", evt -> updateFormState.run());
        terms.addEventListener("change", evt -> updateFormState.run());

        assertTrue("폼 통합: newsletter는 체크되어야 함", newsletter.checked);
        assertTrue("폼 통합: terms는 체크되어야 함", terms.checked);
        assertTrue("폼 통합: terms는 required여야 함", terms.required);

        // Select All pattern
        var selectAllExample = addExampleCode(accessibilitySection,
            "📘 Select All Pattern (전체 선택 패턴)",
            "indeterminate 상태를 사용하여 전체 선택 기능을 구현합니다.",
            """
            var selectAllCheckbox = checkbox()
                .ariaLabel("Select all items")
                .element();

            var item1 = checkbox().ariaLabel("Item 1").select(true).element();
            var item2 = checkbox().ariaLabel("Item 2").select(false).element();
            var item3 = checkbox().ariaLabel("Item 3").select(true).element();

            // 일부만 선택된 경우
            var checkedCount = 2; // item1, item3만 선택
            if (checkedCount > 0 && checkedCount < 3) {
                selectAllCheckbox.indeterminate = true;
            }
            """);
        var selectAllCheckbox = checkbox()
                .ariaLabel("Select all items")
                .element();

        var item1 = checkbox().ariaLabel("Item 1").select(true).element();
        var item2 = checkbox().ariaLabel("Item 2").select(false).element();
        var item3 = checkbox().ariaLabel("Item 3").select(true).element();

        var selectAllDemo = div().style("display", "flex").style("flex-direction", "column").style("gap", "8px").element();
        var selectAllLabel = label()
                .add(selectAllCheckbox)
                .add(" Select All")
                .element();
        selectAllDemo.appendChild(selectAllLabel);

        var item1Label = label()
                .add(item1)
                .add(" Item 1")
                .element();
        var item2Label = label()
                .add(item2)
                .add(" Item 2")
                .element();
        var item3Label = label()
                .add(item3)
                .add(" Item 3")
                .element();
        selectAllDemo.appendChild(item1Label);
        selectAllDemo.appendChild(item2Label);
        selectAllDemo.appendChild(item3Label);

        var selectAllState = selectAllExample.addInteractiveDemo(selectAllDemo);

        var updateSelectAllState = new Runnable() {
            @Override
            public void run() {
                var count = 0;
                if (item1.checked) count++;
                if (item2.checked) count++;
                if (item3.checked) count++;

                if (count == 0) {
                    selectAllCheckbox.checked = false;
                    selectAllCheckbox.indeterminate = false;
                } else if (count == 3) {
                    selectAllCheckbox.checked = true;
                    selectAllCheckbox.indeterminate = false;
                } else {
                    selectAllCheckbox.checked = false;
                    selectAllCheckbox.indeterminate = true;
                }

                var state = selectAllCheckbox.indeterminate ? "indeterminate" :
                           (selectAllCheckbox.checked ? "all checked" : "none checked");
                selectAllState.textContent = "Select All 상태: " + state +
                    " | Items checked: " + count + "/3";
            }
        };
        updateSelectAllState.run();

        item1.addEventListener("change", evt -> updateSelectAllState.run());
        item2.addEventListener("change", evt -> updateSelectAllState.run());
        item3.addEventListener("change", evt -> updateSelectAllState.run());

        selectAllCheckbox.addEventListener("change", evt -> {
            var target = (dev.sayaya.ui.dom.MdCheckboxElement) evt.target;
            item1.checked = target.checked;
            item2.checked = target.checked;
            item3.checked = target.checked;
            updateSelectAllState.run();
        });

        assertTrue("전체 선택 패턴: 일부 항목만 선택되면 indeterminate여야 함",
                selectAllCheckbox.indeterminate);

        // Disabled accessibility
        var disabledAriaExample = addExampleCode(accessibilitySection,
            "📘 Disabled Accessibility (비활성화 접근성)",
            "비활성화된 체크박스도 접근성 라벨을 가져야 합니다.",
            """
            var disabledAriaCheckbox = checkbox()
                .disabled(true)
                .ariaLabel("Disabled option")
                .element();
            """);
        var disabledAriaCheckbox = checkbox()
                .disabled(true)
                .ariaLabel("Disabled option")
                .element();
        var disabledAriaLabel = label()
                .add(disabledAriaCheckbox)
                .add(" Disabled option")
                .element();
        var disabledAriaState = disabledAriaExample.addInteractiveDemo(disabledAriaLabel);
        disabledAriaState.textContent = "disabled: " + disabledAriaCheckbox.disabled +
            " | aria-label: '" + disabledAriaCheckbox.getAttribute("aria-label") + "'";

        assertTrue("disabled aria 체크박스: disabled여야 함", disabledAriaCheckbox.disabled);
        assertEquals("disabled aria 체크박스: aria-label이 설정되어야 함",
                "Disabled option",
                disabledAriaCheckbox.getAttribute("aria-label"));
    }
}
