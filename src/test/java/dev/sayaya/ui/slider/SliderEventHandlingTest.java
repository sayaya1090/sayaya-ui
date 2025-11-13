package dev.sayaya.ui.slider;

import java.util.concurrent.atomic.AtomicInteger;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.SliderElementBuilder.slider;
import static elemental2.dom.DomGlobal.console;
import static org.jboss.elemento.Elements.*;

public class SliderEventHandlingTest {
    public static void test() {
        printSectionHeader("4. 이벤트 처리 (Event Handling)");
        printDescription("Slider 이벤트를 처리하는 방법:");
        printDescription("- onChange(): 값 변경 완료 이벤트");
        printDescription("- onInput(): 값 변경 중 이벤트");
        printSeparator();

        var eventSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(eventSection);

        eventSection.appendChild(h(3).text("Event Handling").element());

        // Input Event
        var inputExample = addExampleCode(eventSection,
            "📘 Input Event (입력 이벤트)",
            "슬라이더 값이 변경될 때마다 발생하는 이벤트입니다.",
            """
            var inputCount = new AtomicInteger(0);
            var slider = slider()
                .min(0)
                .max(100)
                .value(50)
                .onInput(evt -> {
                    inputCount.incrementAndGet();
                    console.log("입력 이벤트 발생");
                })
                .element();
            """);
        var inputCount = new AtomicInteger(0);
        var inputSlider = slider()
                .min(0)
                .max(100)
                .value(50)
                .onInput(evt -> {
                    inputCount.incrementAndGet();
                    console.log("입력 이벤트 발생");
                })
                .element();
        var inputState = inputExample.addInteractiveDemo(inputSlider);
        inputState.textContent = "입력 횟수: " + inputCount.get() + " | 현재 값: " + inputSlider.value;
        inputSlider.addEventListener("input", evt -> {
            inputState.textContent = "입력 횟수: " + inputCount.get() + " | 현재 값: " + inputSlider.value;
        });

        assertEquals("초기 입력 횟수", 0, inputCount.get());

        // Change Event
        var changeExample = addExampleCode(eventSection,
            "📘 Change Event (변경 이벤트)",
            "슬라이더 값 변경이 완료되면 발생하는 이벤트입니다.",
            """
            var changeCount = new AtomicInteger(0);
            var slider = slider()
                .min(0)
                .max(100)
                .value(50)
                .onChange(evt -> {
                    changeCount.incrementAndGet();
                    console.log("변경 이벤트 발생");
                })
                .element();
            """);
        var changeCount = new AtomicInteger(0);
        var changeSlider = slider()
                .min(0)
                .max(100)
                .value(50)
                .onChange(evt -> {
                    changeCount.incrementAndGet();
                    console.log("변경 이벤트 발생");
                })
                .element();
        var changeState = changeExample.addInteractiveDemo(changeSlider);
        changeState.textContent = "변경 횟수: " + changeCount.get() + " | 현재 값: " + changeSlider.value;
        changeSlider.addEventListener("change", evt -> {
            changeState.textContent = "변경 횟수: " + changeCount.get() + " | 현재 값: " + changeSlider.value;
        });

        assertEquals("초기 변경 횟수", 0, changeCount.get());

        // Combined Events
        var combinedExample = addExampleCode(eventSection,
            "📘 Combined Events (복합 이벤트)",
            "input과 change 이벤트를 함께 사용하는 예제입니다.",
            """
            var inputCount = new AtomicInteger(0);
            var changeCount = new AtomicInteger(0);
            
            var slider = slider()
                .min(0)
                .max(100)
                .value(50)
                .onInput(evt -> {
                    inputCount.incrementAndGet();
                    console.log("Input: " + inputCount.get());
                })
                .onChange(evt -> {
                    changeCount.incrementAndGet();
                    console.log("Change: " + changeCount.get());
                })
                .element();
            """);
        var combinedInputCount = new AtomicInteger(0);
        var combinedChangeCount = new AtomicInteger(0);
        
        var combinedSlider = slider()
                .min(0)
                .max(100)
                .value(50)
                .onInput(evt -> {
                    combinedInputCount.incrementAndGet();
                    console.log("Input: " + combinedInputCount.get());
                })
                .onChange(evt -> {
                    combinedChangeCount.incrementAndGet();
                    console.log("Change: " + combinedChangeCount.get());
                })
                .element();
        
        var combinedState = combinedExample.addInteractiveDemo(combinedSlider);
        combinedState.textContent = "Input: " + combinedInputCount.get() + 
            " | Change: " + combinedChangeCount.get() + 
            " | Value: " + combinedSlider.value;
        
        combinedSlider.addEventListener("input", evt -> {
            combinedState.textContent = "Input: " + combinedInputCount.get() + 
                " | Change: " + combinedChangeCount.get() + 
                " | Value: " + combinedSlider.value;
        });
        combinedSlider.addEventListener("change", evt -> {
            combinedState.textContent = "Input: " + combinedInputCount.get() + 
                " | Change: " + combinedChangeCount.get() + 
                " | Value: " + combinedSlider.value;
        });
        
        assertEquals("초기 input 횟수", 0, combinedInputCount.get());
        assertEquals("초기 change 횟수", 0, combinedChangeCount.get());

        // Range Slider Events
        var rangeEventExample = addExampleCode(eventSection,
            "📘 Range Slider Events (범위 슬라이더 이벤트)",
            "범위 슬라이더의 이벤트 처리입니다.",
            """
            var rangeInputCount = new AtomicInteger(0);
            
            var slider = slider()
                .min(0)
                .max(100)
                .range()
                .valueStart(30)
                .valueEnd(70)
                .onInput(evt -> {
                    rangeInputCount.incrementAndGet();
                    console.log("범위 입력 이벤트");
                })
                .element();
            """);
        var rangeInputCount = new AtomicInteger(0);
        
        var rangeEventSlider = slider()
                .min(0)
                .max(100)
                .range()
                .valueStart(30)
                .valueEnd(70)
                .onInput(evt -> {
                    rangeInputCount.incrementAndGet();
                    console.log("범위 입력 이벤트");
                })
                .element();
        
        var rangeEventState = rangeEventExample.addInteractiveDemo(rangeEventSlider);
        rangeEventState.textContent = "입력 횟수: " + rangeInputCount.get() + 
            " | Start: " + rangeEventSlider.valueStart + 
            " | End: " + rangeEventSlider.valueEnd;
        
        rangeEventSlider.addEventListener("input", evt -> {
            rangeEventState.textContent = "입력 횟수: " + rangeInputCount.get() + 
                " | Start: " + rangeEventSlider.valueStart + 
                " | End: " + rangeEventSlider.valueEnd;
        });
        
        assertEquals("초기 범위 입력 횟수", 0, rangeInputCount.get());
    }
}
