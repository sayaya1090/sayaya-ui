package dev.sayaya.ui.dialog;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.ButtonElementBuilder.button;
import static dev.sayaya.ui.elements.DialogElementBuilder.dialog;
import static elemental2.dom.DomGlobal.console;
import static org.jboss.elemento.Elements.*;

public class DialogInteractionTest {
    public static void test() {
        printSectionHeader("4. Dialog 상호작용 (Interaction)");
        printDescription("Dialog의 show/close 메서드와 returnValue를 테스트합니다:");
        printDescription("- show(): 다이얼로그 열기");
        printDescription("- close(): 다이얼로그 닫기");
        printDescription("- returnValue: 닫힐 때 반환값");
        printSeparator();

        var interactionSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(interactionSection);

        interactionSection.appendChild(h(3).text("Dialog Interaction").element());

        // Show Method
        var showExample = addExampleCode(interactionSection,
            "📘 Show Method (열기 메서드)",
            "show() 메서드로 다이얼로그를 엽니다.",
            """
            var showDialog = dialog()
                .headline("알림")
                .content("show() 메서드로 열렸습니다")
                .actions(div().add(button().text("닫기")))
                .element();

            var showButton = button().text("다이얼로그 열기").element();
            showButton.addEventListener("click", evt -> showDialog.show());
            """);
        var showDialog = dialog()
                .headline("알림")
                .content("show() 메서드로 열렸습니다")
                .actions(div().add(button().text().text("닫기")))
                .element();
        var showButton = button().text()
                .text("다이얼로그 열기")
                .element();
        var showContainer = div()
                .add(showButton)
                .add(showDialog)
                .element();
        showExample.addInteractiveDemo(showContainer, false);
        showButton.addEventListener("click", evt -> showDialog.show());
        showDialog.querySelector("[slot='actions'] md-text-button").addEventListener("click", evt -> showDialog.close());

        // Close Method
        var closeExample = addExampleCode(interactionSection,
            "📘 Close Method (닫기 메서드)",
            "close() 메서드로 다이얼로그를 닫습니다.",
            """
            var closeDialog = dialog()
                .headline("닫기 테스트")
                .content("닫기 버튼을 클릭하세요")
                .actions(div().add(button().text("닫기")))
                .element();

            var closeButton = button().text("열기").element();
            closeButton.addEventListener("click", evt -> closeDialog.show());
            """);
        var closeDialog = dialog()
                .headline("닫기 테스트")
                .content("닫기 버튼을 클릭하세요")
                .actions(div().add(button().text().text("닫기")))
                .element();
        var closeButton = button().text().text("열기").element();
        var closeContainer = div()
                .add(closeButton)
                .add(closeDialog)
                .element();
        closeExample.addInteractiveDemo(closeContainer, false);
        closeButton.addEventListener("click", evt -> closeDialog.show());
        closeDialog.querySelector("[slot='actions'] md-text-button").addEventListener("click", evt -> closeDialog.close());

        // Return Value
        var returnValueExample = addExampleCode(interactionSection,
            "📘 Return Value (반환값)",
            "버튼의 value를 returnValue로 전달할 수 있습니다.",
            """
            var returnValueDialog = dialog()
                .headline("선택하세요")
                .content("어떤 버튼을 누를지 선택하세요")
                .actions(div()
                    .add(button().text("취소").attr("value", "cancel"))
                    .add(button().text("확인").attr("value", "confirm")))
                .element();

            var returnValueButton = button().text("열기").element();
            returnValueButton.addEventListener("click", evt -> {
                returnValueDialog.show();
                returnValueDialog.close().then(result -> {
                    console.log("returnValue: " + returnValueDialog.returnValue);
                    return null;
                });
            });
            """);
        var returnValueDialog = dialog()
                .headline("선택하세요")
                .content("어떤 버튼을 누를지 선택하세요")
                .actions(div()
                    .add(button().text().text("취소").attr("value", "cancel"))
                    .add(button().text().text("확인").attr("value", "confirm")))
                .element();
        var returnValueButton = button().text().text("열기").element();
        var returnValueContainer = div()
                .add(returnValueButton)
                .add(returnValueDialog)
                .element();
        var returnValueState = returnValueExample.addInteractiveDemo(returnValueContainer);
        returnValueState.textContent = "버튼을 눌러 returnValue를 확인하세요";
        returnValueButton.addEventListener("click", evt -> {
            returnValueDialog.show();
            returnValueDialog.close().then(result -> {
                console.log("returnValue: " + returnValueDialog.returnValue);
                returnValueState.textContent = "최근 returnValue: " +
                    (returnValueDialog.returnValue != null ? returnValueDialog.returnValue : "null");
                return null;
            });
        });
        var cancelBtn = (elemental2.dom.HTMLElement) returnValueDialog.querySelectorAll("[slot='actions'] md-text-button").getAt(0);
        var confirmBtn = (elemental2.dom.HTMLElement) returnValueDialog.querySelectorAll("[slot='actions'] md-text-button").getAt(1);
        cancelBtn.addEventListener("click", evt -> returnValueDialog.close());
        confirmBtn.addEventListener("click", evt -> returnValueDialog.close());

        // Quick Dialog (No Animation)
        var quickExample = addExampleCode(interactionSection,
            "📘 Quick Dialog (빠른 다이얼로그)",
            "quick 속성을 사용하면 애니메이션 없이 즉시 열고 닫습니다.",
            """
            var quickDialog = dialog()
                .headline("빠른 전환")
                .content("애니메이션 없이 바로 열립니다")
                .quick(true)
                .actions(div().add(button().text("닫기")))
                .element();

            var quickButton = button().text("즉시 열기").element();
            quickButton.addEventListener("click", evt -> quickDialog.show());
            """);
        var quickDialog = dialog()
                .headline("빠른 전환")
                .content("애니메이션 없이 바로 열립니다")
                .quick(true)
                .actions(div().add(button().text().text("닫기")))
                .element();
        var quickButton = button().text().text("즉시 열기").element();
        var quickContainer = div()
                .add(quickButton)
                .add(quickDialog)
                .element();
        quickExample.addInteractiveDemo(quickContainer, false);
        quickButton.addEventListener("click", evt -> quickDialog.show());
        quickDialog.querySelector("[slot='actions'] md-text-button").addEventListener("click", evt -> quickDialog.close());

        // Confirmation Dialog Pattern
        var confirmationExample = addExampleCode(interactionSection,
            "📘 Confirmation Dialog Pattern (확인 다이얼로그 패턴)",
            "일반적인 확인 다이얼로그 사용 패턴입니다.",
            """
            var confirmationDialog = dialog()
                .headline("작업 확인")
                .content("정말로 이 작업을 수행하시겠습니까?")
                .actions(div()
                    .add(button().text("취소").attr("value", "cancel"))
                    .add(button().text("확인").attr("value", "ok")))
                .element();

            var executeButton = button().text("작업 실행").element();
            executeButton.addEventListener("click", evt -> {
                confirmationDialog.show();
                confirmationDialog.close().then(result -> {
                    if ("ok".equals(confirmationDialog.returnValue)) {
                        console.log("작업이 실행되었습니다");
                    } else {
                        console.log("작업이 취소되었습니다");
                    }
                    return null;
                });
            });
            """);
        var confirmationDialog = dialog()
                .headline("작업 확인")
                .content("정말로 이 작업을 수행하시겠습니까?")
                .actions(div()
                    .add(button().text().text("취소").attr("value", "cancel"))
                    .add(button().text().text("확인").attr("value", "ok")))
                .element();
        var executeButton = button().text().text("작업 실행").element();
        var confirmationContainer = div()
                .add(executeButton)
                .add(confirmationDialog)
                .element();
        var confirmationState = confirmationExample.addInteractiveDemo(confirmationContainer);
        confirmationState.textContent = "작업 실행 버튼을 클릭하세요";
        executeButton.addEventListener("click", evt -> {
            confirmationDialog.show();
            confirmationDialog.close().then(result -> {
                if ("ok".equals(confirmationDialog.returnValue)) {
                    console.log("작업이 실행되었습니다");
                    confirmationState.textContent = "결과: 작업이 실행되었습니다";
                } else {
                    console.log("작업이 취소되었습니다");
                    confirmationState.textContent = "결과: 작업이 취소되었습니다";
                }
                return null;
            });
        });
        var cancelBtn2 = (elemental2.dom.HTMLElement) confirmationDialog.querySelectorAll("[slot='actions'] md-text-button").getAt(0);
        var confirmBtn2 = (elemental2.dom.HTMLElement) confirmationDialog.querySelectorAll("[slot='actions'] md-text-button").getAt(1);
        cancelBtn2.addEventListener("click", evt -> confirmationDialog.close());
        confirmBtn2.addEventListener("click", evt -> confirmationDialog.close());

        // Form Dialog Pattern
        var formExample = addExampleCode(interactionSection,
            "📘 Form Dialog Pattern (폼 다이얼로그 패턴)",
            "폼을 포함한 다이얼로그 패턴입니다.",
            """
            var formDialogForm = form()
                .add(div()
                    .style("margin-bottom", "10px")
                    .add(label().add("이름: "))
                    .add(input("text").attr("name", "name")))
                .add(div()
                    .add(label().add("이메일: "))
                    .add(input("email").attr("name", "email")))
                .element();

            var formDialog = dialog()
                .headline("정보 입력")
                .content(formDialogForm)
                .actions(div()
                    .add(button().text("취소"))
                    .add(button().text("제출").attr("value", "submit")))
                .element();

            var formButton = button().text("폼 열기").element();
            formButton.addEventListener("click", evt -> {
                formDialog.show();
                formDialog.close().then(result -> {
                    if ("submit".equals(formDialog.returnValue)) {
                        console.log("폼이 제출되었습니다");
                    } else {
                        console.log("폼이 취소되었습니다");
                    }
                    return null;
                });
            });
            """);
        var formDialogForm = form()
                .add(div()
                    .style("margin-bottom", "10px")
                    .add(label().add("이름: "))
                    .add(input("text").attr("name", "name")))
                .add(div()
                    .add(label().add("이메일: "))
                    .add(input("email").attr("name", "email")))
                .element();
        var formDialog = dialog()
                .headline("정보 입력")
                .content(formDialogForm)
                .actions(div()
                    .add(button().text().text("취소"))
                    .add(button().text().text("제출").attr("value", "submit")))
                .element();
        var formButton = button().text().text("폼 열기").element();
        var formContainer = div()
                .add(formButton)
                .add(formDialog)
                .element();
        var formState = formExample.addInteractiveDemo(formContainer);
        formState.textContent = "폼 열기 버튼을 클릭하세요";
        formButton.addEventListener("click", evt -> {
            formDialog.show();
            formDialog.close().then(result -> {
                if ("submit".equals(formDialog.returnValue)) {
                    formState.textContent = "폼이 제출되었습니다";
                } else {
                    formState.textContent = "폼이 취소되었습니다";
                }
                return null;
            });
        });
        var formCancelBtn = (elemental2.dom.HTMLElement) formDialog.querySelectorAll("[slot='actions'] md-text-button").getAt(0);
        var formSubmitBtn = (elemental2.dom.HTMLElement) formDialog.querySelectorAll("[slot='actions'] md-text-button").getAt(1);
        formCancelBtn.addEventListener("click", evt -> formDialog.close());
        formSubmitBtn.addEventListener("click", evt -> {
            console.log("폼 제출");
            formDialog.close();
        });
    }
}
