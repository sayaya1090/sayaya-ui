package dev.sayaya.ui.dialog;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.ButtonElementBuilder.button;
import static dev.sayaya.ui.elements.DialogElementBuilder.dialog;
import static dev.sayaya.ui.elements.TextFieldElementBuilder.textField;
import static org.jboss.elemento.Elements.*;

public class DialogSlotsTest {
    public static void test() {
        printSectionHeader("2. Dialog 슬롯 (Slots)");
        printDescription("Dialog의 슬롯들을 테스트합니다:");
        printDescription("- headline: 제목 슬롯");
        printDescription("- content: 본문 슬롯");
        printDescription("- actions: 액션 버튼 슬롯");
        printSeparator();

        var slotsSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(slotsSection);

        slotsSection.appendChild(h(3).text("Dialog Slots").element());

        // Complete Dialog with All Slots
        var completeExample = addExampleCode(slotsSection,
            "📘 Complete Dialog (완전한 다이얼로그)",
            "모든 슬롯(headline, content, actions)을 사용한 다이얼로그입니다.",
            """
            var completeDialog = dialog()
                .headline("데이터 삭제")
                .content(div()
                    .add(p().add("정말로 삭제하시겠습니까?"))
                    .add(p().add("이 작업은 되돌릴 수 없습니다.")))
                .actions(div()
                    .add(button().text("취소"))
                    .add(button().text("삭제")))
                .element();

            var openButton = button().text("열기").element();
            openButton.addEventListener("click", evt -> completeDialog.show());
            """);
        var completeDialog = dialog()
                .headline("데이터 삭제")
                .content(div()
                    .add(p().add("정말로 삭제하시겠습니까?"))
                    .add(p().add("이 작업은 되돌릴 수 없습니다.")))
                .actions(div()
                    .add(button().text().text("취소"))
                    .add(button().text().text("삭제")))
                .element();
        var completeButton = button().text().text("열기").element();
        var completeContainer = div()
                .add(completeButton)
                .add(completeDialog)
                .element();
        completeExample.addInteractiveDemo(completeContainer, false);
        completeButton.addEventListener("click", evt -> completeDialog.show());
        var completeButtons = completeDialog.querySelectorAll("[slot='actions'] md-text-button");
        completeButtons.getAt(0).addEventListener("click", evt -> completeDialog.close());
        completeButtons.getAt(1).addEventListener("click", evt -> completeDialog.close());
        assertNotNull("headline 슬롯", completeDialog.querySelector("[slot='headline']"));
        assertNotNull("content 슬롯", completeDialog.querySelector("[slot='content']"));
        assertNotNull("actions 슬롯", completeDialog.querySelector("[slot='actions']"));

        // Headline Slot with String
        var headlineStringExample = addExampleCode(slotsSection,
            "📘 Headline Slot - String (제목 슬롯 - 문자열)",
            "문자열로 제목을 지정합니다.",
            """
            var dialog = dialog()
                .headline("제목입니다")
                .content(div().add("내용"))
                .actions(div().add(button().text("닫기")))
                .element();

            var openButton = button().text("열기").element();
            openButton.addEventListener("click", evt -> dialog.show());
            """);
        var headlineStringDialog = dialog()
                .headline("제목입니다")
                .content(div().add("내용"))
                .actions(div().add(button().text().text("닫기")))
                .element();
        var headlineStringButton = button().text().text("열기").element();
        var headlineStringContainer = div()
                .add(headlineStringButton)
                .add(headlineStringDialog)
                .element();
        headlineStringExample.addInteractiveDemo(headlineStringContainer, false);
        headlineStringButton.addEventListener("click", evt -> headlineStringDialog.show());
        headlineStringDialog.querySelector("[slot='actions'] md-text-button").addEventListener("click", evt -> headlineStringDialog.close());
        var headlineSlot = headlineStringDialog.querySelector("[slot='headline']");
        assertNotNull("headline 슬롯 존재", headlineSlot);

        // Headline Slot with Element
        var headlineElementExample = addExampleCode(slotsSection,
            "📘 Headline Slot - Element (제목 슬롯 - 요소)",
            "HTML 요소로 제목을 지정합니다.",
            """
            var dialog = dialog()
                .headline(h(2).text("커스텀 제목"))
                .content(div().add("내용"))
                .actions(div().add(button().text("닫기")))
                .element();

            var openButton = button().text("열기").element();
            openButton.addEventListener("click", evt -> dialog.show());
            """);
        var headlineElementDialog = dialog()
                .headline(h(2).text("커스텀 제목"))
                .content(div().add("내용"))
                .actions(div().add(button().text().text("닫기")))
                .element();
        var headlineElementButton = button().text().text("열기").element();
        var headlineElementContainer = div()
                .add(headlineElementButton)
                .add(headlineElementDialog)
                .element();
        headlineElementExample.addInteractiveDemo(headlineElementContainer, false);
        headlineElementButton.addEventListener("click", evt -> headlineElementDialog.show());
        headlineElementDialog.querySelector("[slot='actions'] md-text-button").addEventListener("click", evt -> headlineElementDialog.close());
        var customHeadlineSlot = headlineElementDialog.querySelector("[slot='headline']");
        assertNotNull("커스텀 headline 슬롯 존재", customHeadlineSlot);

        // Content Slot with String
        var contentStringExample = addExampleCode(slotsSection,
            "📘 Content Slot - String (본문 슬롯 - 문자열)",
            "문자열로 본문을 지정합니다.",
            """
            var dialog = dialog()
                .headline("제목")
                .content("간단한 메시지")
                .actions(div().add(button().text("확인")))
                .element();

            var openButton = button().text("열기").element();
            openButton.addEventListener("click", evt -> dialog.show());
            """);
        var contentStringDialog = dialog()
                .headline("제목")
                .content("간단한 메시지")
                .actions(div().add(button().filled().text("확인")))
                .element();
        var contentStringButton = button().text().text("열기").element();
        var contentStringContainer = div()
                .add(contentStringButton)
                .add(contentStringDialog)
                .element();
        contentStringExample.addInteractiveDemo(contentStringContainer, false);
        contentStringButton.addEventListener("click", evt -> contentStringDialog.show());
        contentStringDialog.querySelector("[slot='actions'] md-filled-button").addEventListener("click", evt -> contentStringDialog.close());
        var contentSlot = contentStringDialog.querySelector("[slot='content']");
        assertNotNull("content 슬롯 존재", contentSlot);

        // Content Slot with Element
        var contentElementExample = addExampleCode(slotsSection,
            "📘 Content Slot - Element (본문 슬롯 - 요소)",
            "HTML 요소로 본문을 지정합니다.",
            """
            var dialog = dialog()
                .headline("상세 정보")
                .content(div()
                    .add(p().add("첫 번째 문단"))
                    .add(p().add("두 번째 문단")))
                .actions(div().add(button().text("닫기")))
                .element();

            var openButton = button().text("열기").element();
            openButton.addEventListener("click", evt -> dialog.show());
            """);
        var contentElementDialog = dialog()
                .headline("상세 정보")
                .content(div()
                    .add(p().add("첫 번째 문단"))
                    .add(p().add("두 번째 문단")))
                .actions(div().add(button().text().text("닫기")))
                .element();
        var contentElementButton = button().text().text("열기").element();
        var contentElementContainer = div()
                .add(contentElementButton)
                .add(contentElementDialog)
                .element();
        contentElementExample.addInteractiveDemo(contentElementContainer, false);
        contentElementButton.addEventListener("click", evt -> contentElementDialog.show());
        contentElementDialog.querySelector("[slot='actions'] md-text-button").addEventListener("click", evt -> contentElementDialog.close());

        // Content Slot with Form
        var contentFormExample = addExampleCode(slotsSection,
            "📘 Content Slot - Form (본문 슬롯 - 폼)",
            "Form 요소는 자동으로 method='dialog'가 설정됩니다.",
            """
            var form = form()
                .add(label().add("이름: "))
                .add(textField().outlined().label("text").attr("name", "username"))
                .element();
            var dialog = dialog()
                .headline("사용자 입력")
                .content(form)
                .actions(div().add(button().text("제출")))
                .element();

            var openButton = button().text("열기").element();
            openButton.addEventListener("click", evt -> dialog.show());
            """);
        var formElement = form()
                .add(label().add("이름: "))
                .add(textField().outlined().label("text").attr("name", "username"))
                .element();
        var contentFormDialog = dialog()
                .headline("사용자 입력")
                .content(formElement)
                .actions(div().add(button().filled().text("제출")))
                .element();
        var contentFormButton = button().text().text("열기").element();
        var contentFormContainer = div()
                .add(contentFormButton)
                .add(contentFormDialog)
                .element();
        contentFormExample.addInteractiveDemo(contentFormContainer, false);
        contentFormButton.addEventListener("click", evt -> contentFormDialog.show());
        contentFormDialog.querySelector("[slot='actions'] md-filled-button").addEventListener("click", evt -> contentFormDialog.close());
        assertEquals("form method: 'dialog'로 자동 설정",
                "dialog", formElement.method);

        // Actions Slot
        var actionsExample = addExampleCode(slotsSection,
            "📘 Actions Slot (액션 슬롯)",
            "액션 버튼들을 추가합니다.",
            """
            var dialog = dialog()
                .headline("확인")
                .content("작업을 계속하시겠습니까?")
                .actions(div()
                    .add(button().text("취소"))
                    .add(button().text("확인")))
                .element();

            var openButton = button().text("열기").element();
            openButton.addEventListener("click", evt -> dialog.show());
            """);
        var actionsDialog = dialog()
                .headline("확인")
                .content("작업을 계속하시겠습니까?")
                .actions(div()
                    .add(button().text().text("취소"))
                    .add(button().text().text("확인")))
                .element();
        var actionsButton = button().text().text("열기").element();
        var actionsContainer = div()
                .add(actionsButton)
                .add(actionsDialog)
                .element();
        actionsExample.addInteractiveDemo(actionsContainer, false);
        actionsButton.addEventListener("click", evt -> actionsDialog.show());
        var actionButtons = actionsDialog.querySelectorAll("[slot='actions'] md-text-button");
        ((elemental2.dom.HTMLElement) actionButtons.getAt(0)).addEventListener("click", evt -> actionsDialog.close());
        ((elemental2.dom.HTMLElement) actionButtons.getAt(1)).addEventListener("click", evt -> actionsDialog.close());
        var actionsSlot = actionsDialog.querySelector("[slot='actions']");
        assertNotNull("actions 슬롯 존재", actionsSlot);
    }
}
