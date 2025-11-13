package dev.sayaya.ui.list;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.IconElementBuilder.icon;
import static dev.sayaya.ui.elements.ListElementBuilder.list;
import static elemental2.dom.DomGlobal.console;
import static org.jboss.elemento.Elements.*;

public class ListItemTypesTest {
    public static void test() {
        printSectionHeader("4. List Item 타입 (List Item Types)");
        printDescription("List Item의 다양한 타입들:");
        printDescription("- button: 클릭 가능한 버튼 타입");
        printDescription("- link: 링크 타입");
        printSeparator();

        var typesSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(typesSection);

        typesSection.appendChild(h(3).text("List Item Types").element());

        // Button Type
        var buttonExample = addExampleCode(typesSection,
            "📘 Button Type (버튼 타입)",
            "클릭 가능한 버튼 타입의 리스트 항목입니다.",
            """
            var list = list()
                .item()
                    .type("button")
                    .start(icon("add"))
                    .headline("새 항목 추가")
                    .onClick(evt -> console.log("항목 클릭됨"))
                .done()
                .item()
                    .type("button")
                    .start(icon("delete"))
                    .headline("항목 삭제")
                    .onClick(evt -> console.log("삭제 클릭됨"))
                .done()
                .element();
            """);
        var buttonList = list()
                .item()
                    .type("button")
                    .start(icon("add"))
                    .headline("새 항목 추가")
                    .onClick(evt -> console.log("항목 클릭됨"))
                .done()
                .item()
                    .type("button")
                    .start(icon("delete"))
                    .headline("항목 삭제")
                    .onClick(evt -> console.log("삭제 클릭됨"))
                .done()
                .element();
        buttonExample.addInteractiveDemo(buttonList, false);
        assertEquals("첫 번째 항목 type", "button", buttonList.items[0].type);

        // Link Type
        var linkExample = addExampleCode(typesSection,
            "📘 Link Type (링크 타입)",
            "링크 기능을 가진 리스트 항목입니다.",
            """
            var list = list()
                .item()
                    .type("link")
                    .href("https://material.io")
                    .targetBlank()
                    .start(icon("link"))
                    .headline("Material Design")
                    .supportingText("공식 웹사이트")
                .done()
                .item()
                    .type("link")
                    .href("https://github.com")
                    .targetBlank()
                    .start(icon("code"))
                    .headline("GitHub")
                    .supportingText("코드 저장소")
                .done()
                .element();
            """);
        var linkList = list()
                .item()
                    .type("link")
                    .href("https://material.io")
                    .targetBlank()
                    .start(icon("link"))
                    .headline("Material Design")
                    .supportingText("공식 웹사이트")
                .done()
                .item()
                    .type("link")
                    .href("https://github.com")
                    .targetBlank()
                    .start(icon("code"))
                    .headline("GitHub")
                    .supportingText("코드 저장소")
                .done()
                .element();
        linkExample.addInteractiveDemo(linkList, false);
        assertEquals("첫 번째 항목 type", "link", linkList.items[0].type);
        assertEquals("첫 번째 항목 href", "https://material.io", linkList.items[0].href);
        assertEquals("첫 번째 항목 target", "_blank", linkList.items[0].target);
    }
}
