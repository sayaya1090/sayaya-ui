package dev.sayaya.ui.radio;

import java.util.concurrent.atomic.AtomicInteger;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.RadioElementBuilder.radio;
import static elemental2.dom.DomGlobal.console;
import static org.jboss.elemento.Elements.*;

public class RadioEventHandlingTest {
    public static void test() {
        printSectionHeader("4. 이벤트 처리 (Event Handling)");
        printDescription("Radio 이벤트를 처리하는 방법:");
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
            "사용자가 라디오 버튼을 선택하면 발생하는 이벤트입니다.",
            """
            var changeCount = new AtomicInteger(0);
            var container = div().element();
            
            var radio1 = radio()
                .name("event-test")
                .value("option1")
                .onChange(evt -> {
                    changeCount.incrementAndGet();
                    console.log("Option 1 선택됨");
                })
                .ariaLabel("Option 1")
                .element();
            
            var radio2 = radio()
                .name("event-test")
                .value("option2")
                .onChange(evt -> {
                    changeCount.incrementAndGet();
                    console.log("Option 2 선택됨");
                })
                .ariaLabel("Option 2")
                .element();
            
            container.appendChild(label().add("Option 1: ").add(radio1).element());
            container.appendChild(label().add("Option 2: ").add(radio2).element());
            """);
        
        var changeCount = new AtomicInteger(0);
        var changeContainer = div()
                .style("display", "flex")
                .style("flex-direction", "column")
                .style("gap", "10px")
                .element();
        
        var changeRadio1 = radio()
                .name("event-test")
                .value("option1")
                .onChange(evt -> {
                    changeCount.incrementAndGet();
                    console.log("Option 1 선택됨");
                })
                .ariaLabel("Option 1")
                .element();
        
        var changeRadio2 = radio()
                .name("event-test")
                .value("option2")
                .onChange(evt -> {
                    changeCount.incrementAndGet();
                    console.log("Option 2 선택됨");
                })
                .ariaLabel("Option 2")
                .element();
        
        changeContainer.appendChild(label().add("Option 1: ").add(changeRadio1).element());
        changeContainer.appendChild(label().add("Option 2: ").add(changeRadio2).element());
        
        var changeState = changeExample.addInteractiveDemo(changeContainer);
        changeState.textContent = "선택 횟수: " + changeCount.get() + " | Option1: " + changeRadio1.checked + " | Option2: " + changeRadio2.checked;
        
        changeRadio1.addEventListener("change", evt -> {
            changeState.textContent = "선택 횟수: " + changeCount.get() + " | Option1: " + changeRadio1.checked + " | Option2: " + changeRadio2.checked;
        });
        changeRadio2.addEventListener("change", evt -> {
            changeState.textContent = "선택 횟수: " + changeCount.get() + " | Option1: " + changeRadio1.checked + " | Option2: " + changeRadio2.checked;
        });
        
        assertEquals("초기 변경 횟수", 0, changeCount.get());

        // Input Event
        var inputExample = addExampleCode(eventSection,
            "📘 Input Event (입력 이벤트)",
            "값이 변경될 때마다 발생하는 이벤트입니다.",
            """
            var inputCount = new AtomicInteger(0);
            var container = div().element();
            
            var radio1 = radio()
                .name("input-test")
                .value("a")
                .onInput(evt -> {
                    inputCount.incrementAndGet();
                    console.log("Input 이벤트 발생");
                })
                .ariaLabel("A")
                .element();
            
            var radio2 = radio()
                .name("input-test")
                .value("b")
                .onInput(evt -> {
                    inputCount.incrementAndGet();
                    console.log("Input 이벤트 발생");
                })
                .ariaLabel("B")
                .element();
            
            container.appendChild(label().add("A: ").add(radio1).element());
            container.appendChild(label().add("B: ").add(radio2).element());
            """);
        
        var inputCount = new AtomicInteger(0);
        var inputContainer = div()
                .style("display", "flex")
                .style("flex-direction", "column")
                .style("gap", "10px")
                .element();
        
        var inputRadio1 = radio()
                .name("input-test")
                .value("a")
                .onInput(evt -> {
                    inputCount.incrementAndGet();
                    console.log("Input 이벤트 발생");
                })
                .ariaLabel("A")
                .element();
        
        var inputRadio2 = radio()
                .name("input-test")
                .value("b")
                .onInput(evt -> {
                    inputCount.incrementAndGet();
                    console.log("Input 이벤트 발생");
                })
                .ariaLabel("B")
                .element();
        
        inputContainer.appendChild(label().add("A: ").add(inputRadio1).element());
        inputContainer.appendChild(label().add("B: ").add(inputRadio2).element());
        
        var inputState = inputExample.addInteractiveDemo(inputContainer);
        inputState.textContent = "입력 횟수: " + inputCount.get() + " | A: " + inputRadio1.checked + " | B: " + inputRadio2.checked;
        
        inputRadio1.addEventListener("input", evt -> {
            inputState.textContent = "입력 횟수: " + inputCount.get() + " | A: " + inputRadio1.checked + " | B: " + inputRadio2.checked;
        });
        inputRadio2.addEventListener("input", evt -> {
            inputState.textContent = "입력 횟수: " + inputCount.get() + " | A: " + inputRadio1.checked + " | B: " + inputRadio2.checked;
        });

        // Combined Events
        var combinedExample = addExampleCode(eventSection,
            "📘 Combined Events (복합 이벤트)",
            "input과 change 이벤트를 함께 사용하는 예제입니다.",
            """
            var inputCount = new AtomicInteger(0);
            var changeCount = new AtomicInteger(0);
            
            var radio = radio()
                .name("combined-test")
                .value("test")
                .onInput(evt -> {
                    inputCount.incrementAndGet();
                    console.log("Input: " + inputCount.get());
                })
                .onChange(evt -> {
                    changeCount.incrementAndGet();
                    console.log("Change: " + changeCount.get());
                })
                .ariaLabel("Test Radio")
                .element();
            """);
        
        var combinedInputCount = new AtomicInteger(0);
        var combinedChangeCount = new AtomicInteger(0);
        
        var combinedRadio = radio()
                .name("combined-test")
                .value("test")
                .onInput(evt -> {
                    combinedInputCount.incrementAndGet();
                    console.log("Input: " + combinedInputCount.get());
                })
                .onChange(evt -> {
                    combinedChangeCount.incrementAndGet();
                    console.log("Change: " + combinedChangeCount.get());
                })
                .ariaLabel("Test Radio")
                .element();
        
        var combinedState = combinedExample.addInteractiveDemo(combinedRadio);
        combinedState.textContent = "Input: " + combinedInputCount.get() + 
            " | Change: " + combinedChangeCount.get() + 
            " | Checked: " + combinedRadio.checked;
        
        combinedRadio.addEventListener("input", evt -> {
            combinedState.textContent = "Input: " + combinedInputCount.get() + 
                " | Change: " + combinedChangeCount.get() + 
                " | Checked: " + combinedRadio.checked;
        });
        combinedRadio.addEventListener("change", evt -> {
            combinedState.textContent = "Input: " + combinedInputCount.get() + 
                " | Change: " + combinedChangeCount.get() + 
                " | Checked: " + combinedRadio.checked;
        });
        
        assertEquals("초기 input 횟수", 0, combinedInputCount.get());
        assertEquals("초기 change 횟수", 0, combinedChangeCount.get());
    }
}
