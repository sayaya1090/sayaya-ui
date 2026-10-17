package dev.sayaya.ui.list;

import elemental2.promise.Promise;

import java.util.List;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.ListElementBuilder.list;
import static org.jboss.elemento.Elements.*;

public class BasicListTest {
    public static void test() {
        printSectionHeader("1. 기본 List (Basic List)");
        printDescription("Material Design List는 항목들의 목록을 표시하는 컴포넌트입니다.");
        printSeparator();

        var basicSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(basicSection);

        basicSection.appendChild(h(3).text("Basic List").element());

        // Simple List
        var simpleExample = addExampleCode(basicSection,
            "📘 Simple List (간단한 리스트)",
            "기본적인 리스트 항목들을 표시합니다.",
            """
            var list = list()
                .item()
                    .headline("사과")
                .done()
                .item()
                    .headline("바나나")
                .done()
                .item()
                    .headline("오렌지")
                .done()
                .element();
            """);
        var simpleList = list()
                .item()
                    .headline("사과")
                .done()
                .item()
                    .headline("바나나")
                .done()
                .item()
                    .headline("오렌지")
                .done()
                .element();
        simpleExample.addInteractiveDemo(simpleList, false);
        assertEquals("list: 태그명은 md-list", "MD-LIST", simpleList.tagName);
        simpleList.getUpdateComplete().then(s->{
            log(String.valueOf(List.of(simpleList.items)));
            assertTrue("list: items가 3개", simpleList.items.length == 3);
            return Promise.resolve(s);
        });


        // List with Supporting Text
        var supportingTextExample = addExampleCode(basicSection,
            "📘 List with Supporting Text (보조 텍스트가 있는 리스트)",
            "각 항목에 보조 설명 텍스트를 추가할 수 있습니다.",
            """
            var list = list()
                .item()
                    .headline("사과")
                    .supportingText("신선하고 달콤한 과일")
                .done()
                .item()
                    .headline("바나나")
                    .supportingText("에너지가 풍부한 과일")
                .done()
                .element();
            """);
        var supportingList = list()
                .item()
                    .headline("사과")
                    .supportingText("신선하고 달콤한 과일")
                .done()
                .item()
                    .headline("바나나")
                    .supportingText("에너지가 풍부한 과일")
                .done()
                .element();
        supportingTextExample.addInteractiveDemo(supportingList, false);
        assertEquals("Supporting Text (보조 텍스트): 항목마다 supporting-text 슬롯", 2, supportingList.querySelectorAll("[slot='supporting-text']").length);

        // List with Trailing Supporting Text
        var trailingExample = addExampleCode(basicSection,
            "📘 List with Trailing Supporting Text (후행 텍스트가 있는 리스트)",
            "항목의 오른쪽에 추가 정보를 표시할 수 있습니다.",
            """
            var list = list()
                .item()
                    .headline("사과")
                    .supportingText("재고 있음")
                    .trailingSupportingText("+100")
                .done()
                .item()
                    .headline("바나나")
                    .supportingText("재고 있음")
                    .trailingSupportingText("+50")
                .done()
                .element();
            """);
        var trailingList = list()
                .item()
                    .headline("사과")
                    .supportingText("재고 있음")
                    .trailingSupportingText("+100")
                .done()
                .item()
                    .headline("바나나")
                    .supportingText("재고 있음")
                    .trailingSupportingText("+50")
                .done()
                .element();
        trailingExample.addInteractiveDemo(trailingList, false);
        assertEquals("Trailing Supporting Text (후행 텍스트): 항목마다 trailing-supporting-text 슬롯", 2, trailingList.querySelectorAll("[slot='trailing-supporting-text']").length);
    }
}
