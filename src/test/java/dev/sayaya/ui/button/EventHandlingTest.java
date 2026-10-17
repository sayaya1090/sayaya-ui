package dev.sayaya.ui.button;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.ButtonElementBuilder.button;
import static elemental2.dom.DomGlobal.console;
import static elemental2.dom.DomGlobal.setTimeout;
import static org.jboss.elemento.Elements.*;

public class EventHandlingTest {
    public static void test() {
        printSectionHeader("5. 이벤트 처리 (Event Handling)");
        printDescription("버튼 클릭 이벤트를 처리하는 방법을 테스트합니다:");
        printDescription("- onClick(): 클릭 이벤트 핸들러 등록");
        printDescription("- 다중 핸들러 실행");
        printDescription("- disabled 상태에서의 동작");
        printSeparator();

        var eventSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(eventSection);

        eventSection.appendChild(h(3).text("Event Handling").element());

        // Basic Click Event
        var clickExample = addExampleCode(eventSection,
            "📘 Basic Click (기본 클릭)",
            "onClick() 메서드로 클릭 이벤트 핸들러를 등록합니다.",
            """
            var clickCount = new AtomicInteger(0);
            var button = button().filled()
                .text("Click Me")
                .onClick(evt -> {
                    clickCount.incrementAndGet();
                    console.log("Clicked!");
                })
                .element();
            """);
        var clickCount = new AtomicInteger(0);
        var clickBtn = button().filled()
                .text("Click Me")
                .onClick(evt -> {
                    clickCount.incrementAndGet();
                    console.log("Clicked!");
                }).element();
        var clickState = clickExample.addInteractiveDemo(clickBtn);
        clickState.textContent = "Click count: " + clickCount.get();
        clickBtn.addEventListener("click", evt -> {
            clickState.textContent = "Click count: " + clickCount.get();
        });

        clickBtn.click();
        assertEquals("onClick 이벤트: 첫 번째 클릭", 1, clickCount.get());

        setTimeout((p0) -> {
            clickBtn.click();
            assertEquals("onClick 이벤트: 두 번째 클릭", 2, clickCount.get());
        }, 200);

        // Multiple Handlers
        var multiHandlerExample = addExampleCode(eventSection,
            "📘 Multiple Handlers (다중 핸들러)",
            "여러 개의 onClick 핸들러를 등록할 수 있습니다. 모두 순차적으로 실행됩니다.",
            """
            var handler1Triggered = new AtomicBoolean()
            var handler2Triggered = new AtomicBoolean()
            var button = button().outlined()
                .text("Multiple Handlers")
                .onClick(evt -> handler1Triggered.set(true))
                .onClick(evt -> handler2Triggered.set(true))
                .element();
            """);
        var handler1Triggered = new AtomicBoolean();
        var handler2Triggered = new AtomicBoolean();
        var multiHandlerBtn = button().outlined()
                .text("Multiple Handlers")
                .onClick(evt -> handler1Triggered.set(true))
                .onClick(evt -> handler2Triggered.set(true))
                .element();
        var multiHandlerState = multiHandlerExample.addInteractiveDemo(multiHandlerBtn);
        multiHandlerState.textContent = "Handler 1: " + handler1Triggered.get() + " | Handler 2: " + handler2Triggered.get();
        multiHandlerBtn.addEventListener("click", evt -> {
            multiHandlerState.textContent = "Handler 1: " + handler1Triggered.get() + " | Handler 2: " + handler2Triggered.get();
        });

        // md-button은 직전 활성화 클릭과 같은 microtask 안의 클릭을 무시하므로(squelch) 별도 task에서 클릭한다
        setTimeout((p0) -> {
            multiHandlerBtn.click();
            assertTrue("다중 핸들러: 첫 번째 핸들러 실행됨", handler1Triggered.get());
            assertTrue("다중 핸들러: 두 번째 핸들러 실행됨", handler2Triggered.get());
        }, 50);

        // Disabled Button Click
        var disabledExample = addExampleCode(eventSection,
            "📘 Disabled State (비활성화 상태)",
            "disabled 버튼은 클릭 이벤트가 발생하지 않습니다.",
            """
            var disabledClickCount = new AtomicInteger(0);
            var button = button().filled()
                .text("Disabled Button")
                .disabled(true)
                .onClick(evt -> disabledClickCount.incrementAndGet())
                .element();
            button.click();  // 클릭해도 카운트 증가 안함
            """);
        var disabledClickCount = new AtomicInteger(0);
        var disabledBtn = button().filled()
                .text("Disabled Button")
                .disabled(true)
                .onClick(evt -> console.log(disabledClickCount.incrementAndGet()))
                .element();
        var disabledState = disabledExample.addInteractiveDemo(disabledBtn);
        disabledState.textContent = "Click count: " + disabledClickCount.get() + " (disabled: " + disabledBtn.disabled + ")";
        disabledBtn.addEventListener("click", evt -> {
            disabledState.textContent = "Click count: " + disabledClickCount.get() + " (disabled: " + disabledBtn.disabled + ")";
        });

        disabledBtn.click();
        assertEquals("disabled 버튼: 클릭해도 이벤트 발생 안함", 0, disabledClickCount.get());

        // Soft Disabled Button Click
        var softDisabledExample = addExampleCode(eventSection,
            "📘 Soft Disabled State (소프트 비활성화)",
            "softDisabled 버튼은 포커스는 유지하지만 클릭 이벤트는 리스너에 전달되지 않습니다. 접근성을 위해 사용합니다.",
            """
            var softDisabledClickCount = new AtomicInteger(0);
            var button = button().filled()
                .text("Soft Disabled")
                .softDisabled(true)
                .onClick(evt -> softDisabledClickCount.incrementAndGet())
                .element();
            button.click();  // 클릭해도 카운트 증가 안함 (md-button이 이벤트 전파를 막음)
            """);
        var softDisabledClickCount = new AtomicInteger(0);
        var softDisabledBtn = button().filled()
                .text("Soft Disabled")
                .softDisabled(true)
                .onClick(evt -> console.log(softDisabledClickCount.incrementAndGet()))
                .element();
        var softDisabledState = softDisabledExample.addInteractiveDemo(softDisabledBtn);
        softDisabledState.textContent = "Click count: " + softDisabledClickCount.get() + " (softDisabled: " + softDisabledBtn.softDisabled + ")";
        softDisabledBtn.addEventListener("click", evt -> {
            softDisabledState.textContent = "Click count: " + softDisabledClickCount.get() + " (softDisabled: " + softDisabledBtn.softDisabled + ")";
        });

        setTimeout((p0) -> {
            softDisabledBtn.click();
            // md-button은 soft-disabled일 때 stopImmediatePropagation()으로 클릭을 차단한다
            assertEquals("soft-disabled 버튼: 클릭 이벤트가 리스너에 전달되지 않음", 0, softDisabledClickCount.get());
        }, 100);

        // Form Submit Prevention
        var submitExample = addExampleCode(eventSection,
            "📘 Form Submit Prevention (폼 제출 방지)",
            "preventDefault()를 사용하여 폼 제출을 막을 수 있습니다.",
            """
            var testForm = form().id("event-test-form").element();
            var submitClicked = new AtomicBoolean()
            var button = button().filled()
                .type("submit")
                .text("Submit")
                .onClick(evt -> {
                    submitClicked.set(true);
                    evt.preventDefault();  // 실제 제출 방지
                })
                .element();
            testForm.appendChild(button);
            """);
        var testForm = form().id("event-test-form").element();

        var submitClicked = new AtomicBoolean();
        var submitBtn = button().filled()
                .type("submit")
                .text("Submit")
                .onClick(evt -> {
                    submitClicked.set(true);
                    evt.preventDefault();
                })
                .element();
        testForm.appendChild(submitBtn);
        var submitState = submitExample.addInteractiveDemo(testForm);
        submitState.textContent = "Submit clicked: " + submitClicked.get();
        submitBtn.addEventListener("click", evt -> {
            submitState.textContent = "Submit clicked: " + submitClicked.get();
        });

        setTimeout((p0) -> {
            submitBtn.click();
            assertTrue("폼 제출 방지: 클릭 이벤트 발생", submitClicked.get());
        }, 150);
    }
}
