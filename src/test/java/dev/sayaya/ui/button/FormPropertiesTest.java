package dev.sayaya.ui.button;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.ButtonElementBuilder.button;
import static org.jboss.elemento.Elements.*;

public class FormPropertiesTest {
    public static void test() {
        printSectionHeader("3. 폼 속성 (Form Properties)");
        printDescription("버튼을 폼과 연동하여 사용하는 방법을 테스트합니다:");
        printDescription("- type: button, submit, reset");
        printDescription("- name: 폼 데이터의 이름");
        printDescription("- value: 폼 데이터의 값");
        printDescription("- form: 연결할 폼 요소");
        printSeparator();

        var formSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(formSection);

        formSection.appendChild(h(3).text("Form Properties").element());

        // Type - Submit
        addExampleCode(formSection,
            "📘 Type - Submit (제출 버튼)",
            "폼을 제출하는 버튼입니다. 클릭 시 폼의 submit 이벤트가 발생합니다.",
            """
            var form = form().id("test-form").element();
            var button = button().filled()
                .type("submit")
                .text("Submit")
                .element();
            form.appendChild(button);
            """);
        var formElement1 = form().id("test-form-1").element();
        formSection.appendChild(formElement1);
        var submitBtn = button().filled()
                .type("submit")
                .text("Submit")
                .element();
        formElement1.appendChild(submitBtn);
        assertEquals("type 속성: submit이어야 함", "submit", submitBtn.type);

        // Type - Reset
        addExampleCode(formSection,
            "📘 Type - Reset (초기화 버튼)",
            "폼의 모든 필드를 초기값으로 되돌립니다.",
            """
            var form = form().element();
            var button = button().outlined()
                .type("reset")
                .text("Reset")
                .element();
            form.appendChild(button);
            """);
        var formElement2 = form().id("test-form-2").element();
        formSection.appendChild(formElement2);
        var resetBtn = button().outlined()
                .type("reset")
                .text("Reset")
                .element();
        formElement2.appendChild(resetBtn);
        assertEquals("type 속성: reset이어야 함", "reset", resetBtn.type);

        // Name
        addExampleCode(formSection,
            "📘 Name (필드명)",
            "폼 데이터에서 이 버튼을 식별하는 이름입니다.",
            """
            var button = button().filled()
                .name("action")
                .text("Action")
                .element();
            """);
        var namedBtn = button().filled()
                .name("action")
                .text("Action")
                .element();
        formSection.appendChild(namedBtn);
        assertEquals("name 속성: action이어야 함", "action", namedBtn.name);

        // Value
        addExampleCode(formSection,
            "📘 Value (값)",
            "폼 제출 시 이 버튼이 전송할 값입니다.",
            """
            var button = button().filled()
                .name("action")
                .value("save")
                .text("Save")
                .element();
            """);
        var valueBtn = button().filled()
                .name("action")
                .value("save")
                .text("Save")
                .element();
        formSection.appendChild(valueBtn);
        assertEquals("value 속성: save여야 함", "save", valueBtn.value);

        // Form
        addExampleCode(formSection,
            "📘 Form (폼 연결)",
            "버튼을 특정 폼 요소와 연결합니다. 폼 밖에 있어도 작동합니다.",
            """
            var form = form().id("my-form").element();
            // 폼 밖에 있는 버튼
            var button = button().filled()
                .form(form)
                .text("Form Button")
                .element();
            """);
        var formElement3 = form().id("test-form-3").element();
        formSection.appendChild(formElement3);
        var formBtn = button().filled()
                .form(formElement3)
                .text("Form Button")
                .element();
        formSection.appendChild(formBtn);
        assertEquals("form 속성: 속성을 통해 폼 요소를 참조해야 함",
                "test-form-3", formBtn.getAttribute("form"));
    }
}
