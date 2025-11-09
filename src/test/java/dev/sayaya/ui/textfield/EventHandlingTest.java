package dev.sayaya.ui.textfield;

import java.util.concurrent.atomic.AtomicInteger;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.TextFieldElementBuilder.textField;
import static elemental2.dom.DomGlobal.console;
import static org.jboss.elemento.Elements.*;

public class EventHandlingTest {
    public static void test() {
        printSectionHeader("10. 이벤트 처리 (Event Handling)");
        printDescription("TextField의 이벤트:");
        printDescription("- onChange: 값 변경 완료");
        printDescription("- onInput: 입력 중");
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
        addExampleCode(eventSection,
            "📘 onChange 이벤트",
            "값 변경이 완료되었을 때 (포커스를 잃었을 때) 발생합니다. 서버 요청이나 검증 등에 사용됩니다.",
            """
            var field = textField().filled()
                .label("이름");
            field.onChange(e -> {
                    console.log("값이 변경되었습니다: " + field.value());
                    // 서버에 저장하거나 유효성 검증 수행
                });
            """);
        var changeCount = new AtomicInteger(0);
        var changeField = textField().filled()
                .label("변경 감지")
                .onChange(e -> changeCount.incrementAndGet());
        changeField.onChange(e -> console.log("값이 변경되었습니다: " + changeField.value()));
        eventSection.appendChild(changeField.element());

        changeField.value("새 값");
        changeField.element().dispatchEvent(new elemental2.dom.Event("change"));
        assertEquals("onChange 이벤트: 발생해야 함", 1, changeCount.get());

        // onInput event
        addExampleCode(eventSection,
            "📘 onInput 이벤트",
            "사용자가 입력할 때마다 실시간으로 발생합니다. 실시간 검색이나 자동완성 등에 사용됩니다.",
            """
            var searchField = textField().outlined()
                .label("검색")
                .onInput(e -> {
                    console.log("현재 입력값: " + searchField.value);
                    // 실시간 검색 수행
                })
                .element();
            """);
        addExampleCode(eventSection,
            "📘 onChange vs onInput",
            "onChange는 입력 완료 시, onInput은 입력 중 매번 발생합니다.",
            """
            var field = textField().filled()
                .label("비교 테스트")
                .onChange(e -> console.log("Change: 입력 완료"))
                .onInput(e -> console.log("Input: 입력 중..."))
                .element();
            // 'abc' 타이핑 시:
            // - onInput 3번 발생 (a, ab, abc)
            // - onChange 1번 발생 (포커스 이동 시)
            """);
        var inputCount = new AtomicInteger(0);
        var inputField = textField().outlined()
                .label("입력 감지")
                .onChange(e -> console.log("Change: 입력 완료"))
                .onInput(e -> console.log("Input: 입력 중..."))
                .onInput(e->inputCount.incrementAndGet())
                .element();
        eventSection.appendChild(inputField);

        inputField.dispatchEvent(new elemental2.dom.Event("input"));
        assertEquals("onInput 이벤트: 발생해야 함", 1, inputCount.get());
    }
}
