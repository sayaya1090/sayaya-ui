package dev.sayaya.ui.list;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.IconElementBuilder.icon;
import static dev.sayaya.ui.elements.ListElementBuilder.list;
import static org.jboss.elemento.Elements.*;

public class ListItemSlotsTest {
    public static void test() {
        printSectionHeader("3. List Item 슬롯 (List Item Slots)");
        printDescription("List Item에 사용할 수 있는 슬롯들:");
        printDescription("- start: 시작 위치 아이콘/이미지");
        printDescription("- end: 끝 위치 아이콘");
        printSeparator();

        var slotsSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(slotsSection);

        slotsSection.appendChild(h(3).text("List Item Slots").element());

        // Start Slot
        var startSlotExample = addExampleCode(slotsSection,
            "📘 Start Slot (시작 슬롯)",
            "항목의 시작 위치에 아이콘이나 이미지를 추가할 수 있습니다.",
            """
            var list = list()
                .item()
                    .start(icon("home"))
                    .headline("홈")
                .done()
                .item()
                    .start(icon("person"))
                    .headline("프로필")
                .done()
                .item()
                    .start(icon("settings"))
                    .headline("설정")
                .done()
                .element();
            """);
        var startSlotList = list()
                .item()
                    .start(icon("home"))
                    .headline("홈")
                .done()
                .item()
                    .start(icon("person"))
                    .headline("프로필")
                .done()
                .item()
                    .start(icon("settings"))
                    .headline("설정")
                .done()
                .element();
        startSlotExample.addInteractiveDemo(startSlotList, false);

        // End Slot
        var endSlotExample = addExampleCode(slotsSection,
            "📘 End Slot (끝 슬롯)",
            "항목의 끝 위치에 아이콘을 추가할 수 있습니다.",
            """
            var list = list()
                .item()
                    .headline("알림")
                    .end(icon("notifications"))
                .done()
                .item()
                    .headline("메시지")
                    .end(icon("message"))
                .done()
                .element();
            """);
        var endSlotList = list()
                .item()
                    .headline("알림")
                    .end(icon("notifications"))
                .done()
                .item()
                    .headline("메시지")
                    .end(icon("message"))
                .done()
                .element();
        endSlotExample.addInteractiveDemo(endSlotList, false);

        // Start and End Slots
        var bothSlotsExample = addExampleCode(slotsSection,
            "📘 Start and End Slots (시작과 끝 슬롯)",
            "시작과 끝 위치에 모두 아이콘을 추가할 수 있습니다.",
            """
            var list = list()
                .item()
                    .start(icon("folder"))
                    .headline("문서")
                    .supportingText("10개 파일")
                    .end(icon("arrow_forward"))
                .done()
                .item()
                    .start(icon("image"))
                    .headline("사진")
                    .supportingText("52개 파일")
                    .end(icon("arrow_forward"))
                .done()
                .element();
            """);
        var bothSlotsList = list()
                .item()
                    .start(icon("folder"))
                    .headline("문서")
                    .supportingText("10개 파일")
                    .end(icon("arrow_forward"))
                .done()
                .item()
                    .start(icon("image"))
                    .headline("사진")
                    .supportingText("52개 파일")
                    .end(icon("arrow_forward"))
                .done()
                .element();
        bothSlotsExample.addInteractiveDemo(bothSlotsList, false);
    }
}
