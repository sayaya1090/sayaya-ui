package dev.sayaya.ui.checkbox;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.CheckboxElementBuilder.checkbox;
import static elemental2.dom.DomGlobal.console;
import static org.jboss.elemento.Elements.*;

public class CheckboxEventHandlingTest {
    public static void test() {
        printSectionHeader("3. 이벤트 처리 (Event Handling)");
        printDescription("Checkbox 이벤트를 처리하는 방법:");
        printDescription("- onChange(): 체크 상태 변경 이벤트");
        printDescription("- onInput(): 입력 이벤트");
        printDescription("- click(): 클릭 동작");
        printSeparator();

        var eventSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(eventSection);

        eventSection.appendChild(h(3).text("Event Handling").element());

        // onChange event
        var changeExample = addExampleCode(eventSection,
            "📘 onChange Event (변경 이벤트)",
            "체크박스의 체크 상태가 변경될 때 발생합니다.",
            """
            var changeTriggered = new AtomicBoolean();
            var changeCheckbox = checkbox()
                .ariaLabel("Change Test")
                .onChange(evt -> {
                    changeTriggered.set(true);
                    console.log("onChange 이벤트 발생: checked = " + evt.target.checked);
                }).element();
            """);
        var changeTriggered = new AtomicBoolean();
        var changeCheckbox = checkbox()
                .ariaLabel("Change Test")
                .onChange(evt -> {
                    changeTriggered.set(true);
                    var target = (dev.sayaya.ui.dom.MdCheckboxElement) evt.target;
                    console.log("onChange 이벤트 발생: checked = " + target.checked);
                }).element();
        var changeState = changeExample.addInteractiveDemo(changeCheckbox);
        changeState.textContent = "onChange 호출 횟수: 0 | checked: " + changeCheckbox.checked;

        var changeEventCount = new AtomicInteger(0);
        changeCheckbox.addEventListener("change", evt -> {
            changeEventCount.incrementAndGet();
            changeState.textContent = "onChange 호출 횟수: " + changeEventCount.get() + " | checked: " + changeCheckbox.checked;
        });

        changeCheckbox.checked = true;
        changeCheckbox.dispatchEvent(new elemental2.dom.Event("change"));
        assertTrue("onChange 이벤트: 발생해야 함", changeTriggered.get());

        // onInput event
        var inputExample = addExampleCode(eventSection,
            "📘 onInput Event (입력 이벤트)",
            "체크박스 입력이 발생할 때마다 발생합니다.",
            """
            var inputTriggered = new AtomicBoolean();
            var inputCheckbox = checkbox()
                .ariaLabel("Input Test")
                .onInput(evt -> {
                    inputTriggered.set(true);
                    console.log("onInput 이벤트 발생");
                })
                .element();
            """);
        var inputTriggered = new AtomicBoolean();
        var inputCheckbox = checkbox()
                .ariaLabel("Input Test")
                .onInput(evt -> {
                    inputTriggered.set(true);
                    console.log("onInput 이벤트 발생");
                })
                .element();
        var inputState = inputExample.addInteractiveDemo(inputCheckbox);
        inputState.textContent = "onInput 호출 횟수: 0 | checked: " + inputCheckbox.checked;

        var inputEventCount = new AtomicInteger(0);
        inputCheckbox.addEventListener("input", evt -> {
            inputEventCount.incrementAndGet();
            inputState.textContent = "onInput 호출 횟수: " + inputEventCount.get() + " | checked: " + inputCheckbox.checked;
        });

        inputCheckbox.checked = true;
        inputCheckbox.dispatchEvent(new elemental2.dom.Event("input"));
        assertTrue("onInput 이벤트: 발생해야 함", inputTriggered.get());

        // Multiple event handlers
        var multiExample = addExampleCode(eventSection,
            "📘 Multiple Events (다중 이벤트)",
            "하나의 체크박스에 여러 이벤트 핸들러를 등록할 수 있습니다.",
            """
            var changeCount = new AtomicInteger(0);
            var inputCount = new AtomicInteger(0);
            var multiEventCheckbox = checkbox()
                .ariaLabel("Multi Event Test")
                .onChange(evt -> {
                    changeCount.incrementAndGet();
                    console.log("Change 카운트: " + changeCount.get());
                })
                .onInput(evt -> {
                    inputCount.incrementAndGet();
                    console.log("Input 카운트: " + inputCount.get());
                })
                .element();
            """);
        var changeCount = new AtomicInteger(0);
        var inputCount = new AtomicInteger(0);
        var multiEventCheckbox = checkbox()
                .ariaLabel("Multi Event Test")
                .onChange(evt -> {
                    changeCount.incrementAndGet();
                    console.log("Change 카운트: " + changeCount.get());
                })
                .onInput(evt -> {
                    inputCount.incrementAndGet();
                    console.log("Input 카운트: " + inputCount.get());
                })
                .element();
        var multiState = multiExample.addInteractiveDemo(multiEventCheckbox);
        multiState.textContent = "onChange: 0회 | onInput: 0회 | checked: " + multiEventCheckbox.checked;

        multiEventCheckbox.addEventListener("change", evt -> {
            multiState.textContent = "onChange: " + changeCount.get() + "회 | onInput: " + inputCount.get() + "회 | checked: " + multiEventCheckbox.checked;
        });
        multiEventCheckbox.addEventListener("input", evt -> {
            multiState.textContent = "onChange: " + changeCount.get() + "회 | onInput: " + inputCount.get() + "회 | checked: " + multiEventCheckbox.checked;
        });

        multiEventCheckbox.checked = true;
        multiEventCheckbox.dispatchEvent(new elemental2.dom.Event("change"));
        multiEventCheckbox.dispatchEvent(new elemental2.dom.Event("input"));

        assertEquals("다중 이벤트: change 카운트는 1이어야 함", 1, changeCount.get());
        assertEquals("다중 이벤트: input 카운트는 1이어야 함", 1, inputCount.get());

        // Click behavior
        var clickExample = addExampleCode(eventSection,
            "📘 Click Behavior (클릭 동작)",
            "체크박스를 클릭하면 체크 상태가 토글됩니다.",
            """
            var clickCheckbox = checkbox()
                .ariaLabel("Click to toggle")
                .element();
            """);
        var clickCheckbox = checkbox()
                .ariaLabel("Click to toggle")
                .element();
        var clickState = clickExample.addInteractiveDemo(clickCheckbox);
        clickState.textContent = "checked: " + clickCheckbox.checked + " (클릭해보세요!)";

        clickCheckbox.addEventListener("change", evt -> {
            clickState.textContent = "checked: " + clickCheckbox.checked;
        });

        assertFalse("클릭 동작: 초기 상태는 unchecked", clickCheckbox.checked);

        // Indeterminate click behavior
        var indeterminateExample = addExampleCode(eventSection,
            "📘 Indeterminate Click (불확정 상태 클릭)",
            "불확정 상태에서 클릭하면 checked 상태로 변경됩니다.",
            """
            var indeterminateCheckbox = checkbox()
                .indeterminate()
                .ariaLabel("Indeterminate Click Test")
                .onChange(evt -> {
                    console.log("상태 변경: checked = " + evt.target.checked +
                                ", indeterminate = " + evt.target.indeterminate);
                }).element();
            """);
        var indeterminateCheckbox = checkbox()
                .indeterminate()
                .ariaLabel("Indeterminate Click Test")
                .onChange(evt -> {
                    var target = (dev.sayaya.ui.dom.MdCheckboxElement) evt.target;
                    console.log("상태 변경: checked = " + target.checked +
                                ", indeterminate: " + target.indeterminate);
                }).element();
        var indeterminateState = indeterminateExample.addInteractiveDemo(indeterminateCheckbox);
        indeterminateState.textContent = "checked: " + indeterminateCheckbox.checked +
                                         " | indeterminate: " + indeterminateCheckbox.indeterminate;

        indeterminateCheckbox.addEventListener("change", evt -> {
            indeterminateState.textContent = "checked: " + indeterminateCheckbox.checked +
                                             " | indeterminate: " + indeterminateCheckbox.indeterminate;
        });

        assertTrue("불확정 클릭: 초기 상태는 indeterminate", indeterminateCheckbox.indeterminate);
    }
}
