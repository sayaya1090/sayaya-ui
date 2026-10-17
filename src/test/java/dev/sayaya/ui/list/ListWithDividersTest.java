package dev.sayaya.ui.list;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.IconElementBuilder.icon;
import static dev.sayaya.ui.elements.ListElementBuilder.list;
import static org.jboss.elemento.Elements.*;

public class ListWithDividersTest {
    public static void test() {
        printSectionHeader("5. List with Dividers (구분선이 있는 리스트)");
        printDescription("리스트 항목 사이에 구분선을 추가할 수 있습니다:");
        printDescription("- divider(): 전체 너비 구분선");
        printDescription("- dividerInset(): 양쪽 여백이 있는 구분선");
        printDescription("- dividerInsetStart(): 시작 위치에만 여백이 있는 구분선");
        printSeparator();

        var dividersSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(dividersSection);

        dividersSection.appendChild(h(3).text("List with Dividers").element());

        // Full Divider
        var fullDividerExample = addExampleCode(dividersSection,
            "📘 Full Divider (전체 너비 구분선)",
            "항목 사이에 전체 너비의 구분선을 추가합니다.",
            """
            var list = list()
                .item()
                    .headline("항목 1")
                .done()
                .divider()
                .item()
                    .headline("항목 2")
                .done()
                .divider()
                .item()
                    .headline("항목 3")
                .done()
                .element();
            """);
        var fullDividerList = list()
                .item()
                    .headline("항목 1")
                .done()
                .divider()
                .item()
                    .headline("항목 2")
                .done()
                .divider()
                .item()
                    .headline("항목 3")
                .done()
                .element();
        fullDividerExample.addInteractiveDemo(fullDividerList, false);
        {
            var dividers = fullDividerList.querySelectorAll("md-divider");
            boolean ok = dividers.length == 2;
            for (int i = 0; i < dividers.length; i++) ok = ok && !Boolean.TRUE.equals(jsinterop.base.Js.asPropertyMap(dividers.item(i)).get("inset"));
            assertTrue("Full Divider (전체 너비 구분선): md-divider 2개, inset 없음", ok);
        }

        // Inset Divider
        var insetDividerExample = addExampleCode(dividersSection,
            "📘 Inset Divider (여백이 있는 구분선)",
            "양쪽에 여백이 있는 구분선을 추가합니다.",
            """
            var list = list()
                .item()
                    .start(icon("person"))
                    .headline("사용자 1")
                    .supportingText("user1@example.com")
                .done()
                .dividerInset()
                .item()
                    .start(icon("person"))
                    .headline("사용자 2")
                    .supportingText("user2@example.com")
                .done()
                .dividerInset()
                .item()
                    .start(icon("person"))
                    .headline("사용자 3")
                    .supportingText("user3@example.com")
                .done()
                .element();
            """);
        var insetDividerList = list()
                .item()
                    .start(icon("person"))
                    .headline("사용자 1")
                    .supportingText("user1@example.com")
                .done()
                .dividerInset()
                .item()
                    .start(icon("person"))
                    .headline("사용자 2")
                    .supportingText("user2@example.com")
                .done()
                .dividerInset()
                .item()
                    .start(icon("person"))
                    .headline("사용자 3")
                    .supportingText("user3@example.com")
                .done()
                .element();
        insetDividerExample.addInteractiveDemo(insetDividerList, false);
        {
            var dividers = insetDividerList.querySelectorAll("md-divider");
            boolean ok = dividers.length == 2;
            for (int i = 0; i < dividers.length; i++) ok = ok && Boolean.TRUE.equals(jsinterop.base.Js.asPropertyMap(dividers.item(i)).get("inset"));
            assertTrue("Inset Divider (여백이 있는 구분선): md-divider 2개, inset", ok);
        }

        // Inset Start Divider
        var insetStartDividerExample = addExampleCode(dividersSection,
            "📘 Inset Start Divider (시작 여백이 있는 구분선)",
            "시작 위치에만 여백이 있는 구분선을 추가합니다. 아이콘이 있는 리스트에 적합합니다.",
            """
            var list = list()
                .item()
                    .start(icon("home"))
                    .headline("홈")
                .done()
                .dividerInsetStart()
                .item()
                    .start(icon("work"))
                    .headline("업무")
                .done()
                .dividerInsetStart()
                .item()
                    .start(icon("favorite"))
                    .headline("즐겨찾기")
                .done()
                .element();
            """);
        var insetStartDividerList = list()
                .item()
                    .start(icon("home"))
                    .headline("홈")
                .done()
                .dividerInsetStart()
                .item()
                    .start(icon("work"))
                    .headline("업무")
                .done()
                .dividerInsetStart()
                .item()
                    .start(icon("favorite"))
                    .headline("즐겨찾기")
                .done()
                .element();
        insetStartDividerExample.addInteractiveDemo(insetStartDividerList, false);
        {
            var dividers = insetStartDividerList.querySelectorAll("md-divider");
            boolean ok = dividers.length == 2;
            for (int i = 0; i < dividers.length; i++) ok = ok && Boolean.TRUE.equals(jsinterop.base.Js.asPropertyMap(dividers.item(i)).get("insetStart"));
            assertTrue("Inset Start Divider (시작 여백이 있는 구분선): md-divider 2개, insetStart", ok);
        }
    }
}
