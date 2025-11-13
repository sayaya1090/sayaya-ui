package dev.sayaya.ui.select;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.SelectElementBuilder.select;
import static org.jboss.elemento.Elements.*;

public class SelectionMethodsTest {
    public static void test() {
        printSectionHeader("4. 선택 메서드 (Selection Methods)");
        printDescription("프로그래밍 방식으로 옵션을 선택하는 방법:");
        printDescription("- selectByValue(): 값으로 선택");
        printDescription("- selectByIndex(): 인덱스로 선택");
        printDescription("- reset(): 초기값으로 리셋");
        printSeparator();

        var selectionSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(selectionSection);

        selectionSection.appendChild(h(3).text("Selection Methods").element());

        // Select by Value
        var valueExample = addExampleCode(selectionSection,
            "📘 Select by Value (값으로 선택)",
            "특정 값을 가진 옵션을 프로그래밍 방식으로 선택합니다.",
            """
            var select = select().filled()
                .label("언어")
                .option().value("ko").headline("한국어").done()
                .option().value("en").headline("English").done()
                .option().value("ja").headline("日本語").done()
                .element();

            select.select("en");  // English 선택
            """);
        var valueSelect = select().filled()
                .label("언어")
                .option().value("ko").headline("한국어").done()
                .option().value("en").headline("English").done()
                .option().value("ja").headline("日本語").done()
                .element();
        valueExample.addInteractiveDemo(valueSelect, false);

        valueSelect.getUpdateComplete().then(result -> {
            valueSelect.select("en");
            return valueSelect.getUpdateComplete();
        }).then(result -> {
            assertEquals("값으로 선택: en", "en", valueSelect.value);
            return null;
        });

        // Select by Index
        var indexExample = addExampleCode(selectionSection,
            "📘 Select by Index (인덱스로 선택)",
            "인덱스로 옵션을 선택합니다. 인덱스는 0부터 시작합니다.",
            """
            var select = select().outlined()
                .label("크기")
                .option().value("s").headline("Small").done()
                .option().value("m").headline("Medium").done()
                .option().value("l").headline("Large").done()
                .element();

            select.selectIndex(1);  // Medium 선택 (인덱스 1)
            """);
        var indexSelect = select().outlined()
                .label("크기")
                .option().value("s").headline("Small").done()
                .option().value("m").headline("Medium").done()
                .option().value("l").headline("Large").done()
                .element();
        indexExample.addInteractiveDemo(indexSelect, false);

        indexSelect.getUpdateComplete().then(result -> {
            indexSelect.selectIndex(1);
            return indexSelect.getUpdateComplete();
        }).then(result -> {
            assertEquals("인덱스로 선택: Medium", "m", indexSelect.value);
            assertEquals("selectedIndex", 1.0, indexSelect.selectedIndex);
            return null;
        });

        // Reset
        var resetExample = addExampleCode(selectionSection,
            "📘 Reset (초기화)",
            "Select를 초기값으로 되돌립니다.",
            """
            var select = select().filled()
                .label("상태")
                .option().value("draft").headline("임시저장").select(true).done()
                .option().value("published").headline("발행됨").done()
                .element();

            // 다른 값 선택
            select.select("published");

            // 초기값으로 리셋
            select.reset();  // "draft"로 돌아감
            """);
        var resetSelect = select().filled()
                .label("상태")
                .option().value("draft").headline("임시저장").select(true).done()
                .option().value("published").headline("발행됨").done()
                .element();
        resetExample.addInteractiveDemo(resetSelect, false);

        resetSelect.getUpdateComplete().then(result -> {
            resetSelect.select("published");
            return resetSelect.getUpdateComplete();
        }).then(result -> {
            assertEquals("발행됨 선택", "published", resetSelect.value);
            resetSelect.reset();
            return resetSelect.getUpdateComplete();
        }).then(result -> {
            assertEquals("리셋 후 초기값", "draft", resetSelect.value);
            return null;
        });
    }
}
