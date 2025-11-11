package dev.sayaya.ui.checkbox;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.CheckboxElementBuilder.checkbox;
import static org.jboss.elemento.Elements.*;

public class CheckboxStatesTest {
    public static void test() {
        printSectionHeader("1. Checkbox 상태 (Checkbox States)");
        printDescription("Checkbox는 3가지 상태를 가집니다:");
        printDescription("- Unchecked: 체크되지 않은 상태");
        printDescription("- Checked: 체크된 상태");
        printDescription("- Indeterminate: 불확정 상태 (부분 선택)");
        printSeparator();

        var statesSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(statesSection);

        statesSection.appendChild(h(3).text("Checkbox States").element());

        // Unchecked state
        var uncheckedExample = addExampleCode(statesSection,
            "📘 Unchecked (체크 안됨)",
            "기본 상태입니다. 선택되지 않은 상태를 나타냅니다.",
            """
            var unchecked = checkbox()
                .ariaLabel("Unchecked")
                .element();
            """);
        var unchecked = checkbox()
                .ariaLabel("Unchecked")
                .element();
        var uncheckedState = uncheckedExample.addInteractiveDemo(unchecked);
        uncheckedState.textContent = "checked: " + unchecked.checked + " | indeterminate: " + unchecked.indeterminate;

        unchecked.addEventListener("change", evt -> {
            uncheckedState.textContent = "checked: " + unchecked.checked + " | indeterminate: " + unchecked.indeterminate;
        });

        assertFalse("체크 안됨 상태: checked는 false여야 함", unchecked.checked);
        assertFalse("체크 안됨 상태: indeterminate는 false여야 함", unchecked.indeterminate);

        // Checked state
        var checkedExample = addExampleCode(statesSection,
            "📘 Checked (체크됨)",
            "선택된 상태입니다.",
            """
            var checked = checkbox()
                .select(true)
                .ariaLabel("Checked")
                .element();
            """);
        var checked = checkbox()
                .select(true)
                .ariaLabel("Checked")
                .element();
        var checkedState = checkedExample.addInteractiveDemo(checked);
        checkedState.textContent = "checked: " + checked.checked + " | indeterminate: " + checked.indeterminate;

        checked.addEventListener("change", evt -> {
            checkedState.textContent = "checked: " + checked.checked + " | indeterminate: " + checked.indeterminate;
        });

        assertTrue("체크됨 상태: checked는 true여야 함", checked.checked);
        assertFalse("체크됨 상태: indeterminate는 false여야 함", checked.indeterminate);

        // Indeterminate state
        var indeterminateExample = addExampleCode(statesSection,
            "📘 Indeterminate (불확정)",
            "부분 선택 상태입니다. 전체 선택 체크박스에서 일부만 선택된 경우 사용됩니다.",
            """
            var indeterminate = checkbox()
                .indeterminate()
                .ariaLabel("Indeterminate")
                .element();
            """);
        var indeterminate = checkbox()
                .indeterminate()
                .ariaLabel("Indeterminate")
                .element();
        var indeterminateState = indeterminateExample.addInteractiveDemo(indeterminate);
        indeterminateState.textContent = "checked: " + indeterminate.checked + " | indeterminate: " + indeterminate.indeterminate;

        indeterminate.addEventListener("change", evt -> {
            indeterminateState.textContent = "checked: " + indeterminate.checked + " | indeterminate: " + indeterminate.indeterminate;
        });

        assertFalse("불확정 상태: checked는 false여야 함", indeterminate.checked);
        assertTrue("불확정 상태: indeterminate는 true여야 함", indeterminate.indeterminate);

        // State transitions
        addExampleCode(statesSection,
            "📘 State Transitions (상태 전환)",
            "상태는 프로그래밍 방식으로 변경할 수 있습니다.",
            """
            var builder = checkbox();
            
            // Unchecked -> Checked
            builder.select(true);
            
            // Checked -> Indeterminate
            builder.indeterminate();
            
            // Indeterminate -> Unchecked
            builder.select(false);
            """);
        var stateBuilder = checkbox();
        stateBuilder.select(false);
        assertFalse("상태 전환: 초기 상태는 체크 안됨", stateBuilder.isSelected());

        stateBuilder.select(true);
        assertTrue("상태 전환: 체크됨으로 변경", stateBuilder.isSelected());
        assertFalse("상태 전환: indeterminate는 false여야 함", stateBuilder.isIndeterminate());

        stateBuilder.indeterminate();
        assertFalse("상태 전환: 불확정 상태로 변경, isSelected는 false여야 함", stateBuilder.isSelected());
        assertTrue("상태 전환: isIndeterminate는 true여야 함", stateBuilder.isIndeterminate());

        stateBuilder.select(false);
        assertFalse("상태 전환: 체크 안됨으로 변경", stateBuilder.isSelected());
        assertFalse("상태 전환: indeterminate 해제됨", stateBuilder.isIndeterminate());
    }
}
