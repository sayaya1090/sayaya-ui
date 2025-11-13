package dev.sayaya.ui.switchtest;

import java.util.concurrent.atomic.AtomicInteger;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.SwitchElementBuilder.sw;
import static elemental2.dom.DomGlobal.console;
import static org.jboss.elemento.Elements.*;

public class SwitchEventHandlingTest {
    public static void test() {
        printSectionHeader("3. 이벤트 처리 (Event Handling)");
        printDescription("Switch 이벤트를 처리하는 방법:");
        printDescription("- onChange(): 선택 변경 이벤트");
        printDescription("- onInput(): 입력 이벤트");
        printSeparator();

        var eventSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(eventSection);

        eventSection.appendChild(h(3).text("Event Handling").element());

        // Change Event
        var changeExample = addExampleCode(eventSection,
            "📘 Change Event (변경 이벤트)",
            "사용자가 스위치를 변경하면 발생하는 이벤트입니다.",
            """
            var changeCount = new AtomicInteger(0);
            var switchElem = sw()
                .onChange(evt -> {
                    changeCount.incrementAndGet();
                    console.log("선택 변경됨");
                })
                .ariaLabel("Change Event")
                .element();
            """);
        var changeCount = new AtomicInteger(0);
        var changeSwitch = sw()
                .onChange(evt -> {
                    changeCount.incrementAndGet();
                    console.log("선택 변경됨");
                })
                .ariaLabel("Change Event")
                .element();
        var changeState = changeExample.addInteractiveDemo(changeSwitch);
        changeState.textContent = "변경 횟수: " + changeCount.get() + " | selected: " + changeSwitch.selected;
        changeSwitch.addEventListener("change", evt -> {
            changeState.textContent = "변경 횟수: " + changeCount.get() + " | selected: " + changeSwitch.selected;
        });

        assertEquals("초기 변경 횟수", 0, changeCount.get());

        // Input Event
        var inputExample = addExampleCode(eventSection,
            "📘 Input Event (입력 이벤트)",
            "사용자 상호작용으로 값이 변경될 때마다 발생하는 이벤트입니다.",
            """
            var inputCount = new AtomicInteger(0);
            var switchElem = sw()
                .onInput(evt -> {
                    inputCount.incrementAndGet();
                    console.log("입력 이벤트 발생");
                })
                .ariaLabel("Input Event")
                .element();
            """);
        var inputCount = new AtomicInteger(0);
        var inputSwitch = sw()
                .onInput(evt -> {
                    inputCount.incrementAndGet();
                    console.log("입력 이벤트 발생");
                })
                .ariaLabel("Input Event")
                .element();
        var inputState = inputExample.addInteractiveDemo(inputSwitch);
        inputState.textContent = "입력 횟수: " + inputCount.get() + " | selected: " + inputSwitch.selected;
        inputSwitch.addEventListener("input", evt -> {
            inputState.textContent = "입력 횟수: " + inputCount.get() + " | selected: " + inputSwitch.selected;
        });

        assertEquals("초기 입력 횟수", 0, inputCount.get());

        // Combined Events
        var combinedExample = addExampleCode(eventSection,
            "📘 Combined Events (복합 이벤트)",
            "input과 change 이벤트를 함께 사용하는 예제입니다.",
            """
            var inputCount = new AtomicInteger(0);
            var changeCount = new AtomicInteger(0);
            
            var switchElem = sw()
                .onInput(evt -> {
                    inputCount.incrementAndGet();
                    console.log("Input: " + inputCount.get());
                })
                .onChange(evt -> {
                    changeCount.incrementAndGet();
                    console.log("Change: " + changeCount.get());
                })
                .ariaLabel("Combined Events")
                .element();
            """);
        var combinedInputCount = new AtomicInteger(0);
        var combinedChangeCount = new AtomicInteger(0);
        
        var combinedSwitch = sw()
                .onInput(evt -> {
                    combinedInputCount.incrementAndGet();
                    console.log("Input: " + combinedInputCount.get());
                })
                .onChange(evt -> {
                    combinedChangeCount.incrementAndGet();
                    console.log("Change: " + combinedChangeCount.get());
                })
                .ariaLabel("Combined Events")
                .element();
        
        var combinedState = combinedExample.addInteractiveDemo(combinedSwitch);
        combinedState.textContent = "Input: " + combinedInputCount.get() + 
            " | Change: " + combinedChangeCount.get() + 
            " | Selected: " + combinedSwitch.selected;
        
        combinedSwitch.addEventListener("input", evt -> {
            combinedState.textContent = "Input: " + combinedInputCount.get() + 
                " | Change: " + combinedChangeCount.get() + 
                " | Selected: " + combinedSwitch.selected;
        });
        combinedSwitch.addEventListener("change", evt -> {
            combinedState.textContent = "Input: " + combinedInputCount.get() + 
                " | Change: " + combinedChangeCount.get() + 
                " | Selected: " + combinedSwitch.selected;
        });
        
        assertEquals("초기 input 횟수", 0, combinedInputCount.get());
        assertEquals("초기 change 횟수", 0, combinedChangeCount.get());
    }
}
