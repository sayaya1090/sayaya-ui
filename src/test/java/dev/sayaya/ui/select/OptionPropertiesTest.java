package dev.sayaya.ui.select;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.SelectElementBuilder.select;
import static org.jboss.elemento.Elements.*;

public class OptionPropertiesTest {
    public static void test() {
        printSectionHeader("3. 옵션 속성 (Option Properties)");
        printDescription("Select Option의 속성들을 테스트합니다:");
        printDescription("- value: 옵션 값");
        printDescription("- headline: 옵션 표시 텍스트");
        printDescription("- selected: 초기 선택 상태");
        printDescription("- disabled: 옵션 비활성화");
        printDescription("- supportingTextSlot: 옵션 부가 설명");
        printSeparator();

        var optionSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(optionSection);

        optionSection.appendChild(h(3).text("Option Properties").element());

        // Basic Option
        var basicExample = addExampleCode(optionSection,
            "📘 Basic Option (기본 옵션)",
            "value와 headline을 가진 기본 옵션입니다.",
            """
            var select = select().filled()
                .label("과일")
                .option()
                    .value("apple")
                    .headline("사과")
                .done()
                .option()
                    .value("banana")
                    .headline("바나나")
                .done()
                .element();
            """);
        var basicSelect = select().filled()
                .label("과일")
                .option()
                    .value("apple")
                    .headline("사과")
                .done()
                .option()
                    .value("banana")
                    .headline("바나나")
                .done()
                .element();
        basicExample.addInteractiveDemo(basicSelect, false);

        basicSelect.getUpdateComplete().then(result -> {
            var options = basicSelect.options;
            assertTrue("옵션 개수: 2개", options.length == 2);
            assertEquals("첫 번째 옵션 value", "apple", options[0].value);
            return null;
        });

        // Selected Option
        var selectedExample = addExampleCode(optionSection,
            "📘 Selected Option (초기 선택)",
            "초기에 선택된 상태로 표시되는 옵션입니다.",
            """
            var select = select().outlined()
                .label("색상")
                .option()
                    .value("red")
                    .headline("빨강")
                .done()
                .option()
                    .value("blue")
                    .headline("파랑")
                    .select(true)
                .done()
                .element();
            """);
        var selectedSelect = select().outlined()
                .label("색상")
                .option()
                    .value("red")
                    .headline("빨강")
                .done()
                .option()
                    .value("blue")
                    .headline("파랑")
                    .select(true)
                .done()
                .element();
        selectedExample.addInteractiveDemo(selectedSelect, false);

        selectedSelect.getUpdateComplete().then(result -> {
            assertEquals("선택된 값", "blue", selectedSelect.value);
            assertTrue("파랑 옵션이 선택됨", selectedSelect.options[1].selected);
            return null;
        });

        // Disabled Option
        var disabledOptionExample = addExampleCode(optionSection,
            "📘 Disabled Option (비활성화 옵션)",
            "선택할 수 없는 비활성화된 옵션입니다.",
            """
            var select = select().filled()
                .label("도시")
                .option()
                    .value("seoul")
                    .headline("서울")
                .done()
                .option()
                    .value("busan")
                    .headline("부산 (품절)")
                    .disabled(true)
                .done()
                .element();
            """);
        var disabledOptionSelect = select().filled()
                .label("도시")
                .option()
                    .value("seoul")
                    .headline("서울")
                .done()
                .option()
                    .value("busan")
                    .headline("부산 (품절)")
                    .disabled(true)
                .done()
                .element();
        disabledOptionExample.addInteractiveDemo(disabledOptionSelect, false);

        disabledOptionSelect.getUpdateComplete().then(result -> {
            assertTrue("두 번째 옵션 비활성화", disabledOptionSelect.options[1].disabled);
            return null;
        });

        // Option with Supporting Text
        var supportingTextExample = addExampleCode(optionSection,
            "📘 Supporting Text (부가 설명)",
            "옵션에 부가 설명 텍스트를 추가할 수 있습니다.",
            """
            var select = select().outlined()
                .label("계정 선택")
                .option()
                    .value("personal")
                    .headline("개인 계정")
                    .supportingTextSlot("개인용으로 사용")
                .done()
                .option()
                    .value("business")
                    .headline("비즈니스 계정")
                    .supportingTextSlot("업무용으로 사용")
                .done()
                .element();
            """);
        var supportingSelect = select().outlined()
                .label("계정 선택")
                .option()
                    .value("personal")
                    .headline("개인 계정")
                    .supportingText("개인용으로 사용")
                .done()
                .option()
                    .value("business")
                    .headline("비즈니스 계정")
                    .supportingText("업무용으로 사용")
                .done()
                .element();
        supportingTextExample.addInteractiveDemo(supportingSelect, false);

        supportingSelect.getUpdateComplete().then(result -> {
            assertNotNull("supporting text 슬롯이 있는 첫 번째 옵션",
                    supportingSelect.options[0].querySelector("[slot='supporting-text']"));
            return null;
        });
    }
}
