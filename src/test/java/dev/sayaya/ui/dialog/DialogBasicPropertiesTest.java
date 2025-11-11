package dev.sayaya.ui.dialog;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.ButtonElementBuilder.button;
import static dev.sayaya.ui.elements.DialogElementBuilder.alert;
import static dev.sayaya.ui.elements.DialogElementBuilder.dialog;
import static org.jboss.elemento.Elements.*;

public class DialogBasicPropertiesTest {
    public static void test() {
        printSectionHeader("1. Dialog 기본 속성 (Basic Properties)");
        printDescription("Dialog의 기본 속성들을 테스트합니다:");
        printDescription("- type: dialog 타입 (일반/alert)");
        printDescription("- open: 열림/닫힘 상태");
        printDescription("- quick: 애니메이션 스킵");
        printDescription("- noFocusTrap: 포커스 트랩 비활성화");
        printDescription("- returnValue: 닫힐 때 반환값");
        printSeparator();

        var propertiesSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(propertiesSection);

        propertiesSection.appendChild(h(3).text("Dialog Basic Properties").element());

        // Basic Dialog
        var basicExample = addExampleCode(propertiesSection,
            "📘 Basic Dialog (기본 다이얼로그)",
            "기본 다이얼로그를 생성합니다. 버튼을 클릭하면 다이얼로그가 열립니다.",
            """
            var basicDialog = dialog()
                .headline("안내")
                .content(div().add("다이얼로그 내용"))
                .actions(div().add(button().text("닫기")))
                .element();

            var openButton = button().text("열기").element();
            body().add(openButton);
            body().add(basicDialog);

            openButton.addEventListener("click", evt -> basicDialog.show());
            """);
        var basicDialog = dialog()
                .headline("안내")
                .content(div().add("다이얼로그 내용"))
                .actions(div().add(button().text().text("닫기")))
                .element();
        var basicButton = button().text().text("열기").element();
        var basicContainer = div()
                .add(basicButton)
                .add(basicDialog)
                .element();
        basicExample.addInteractiveDemo(basicContainer, false);
        basicButton.addEventListener("click", evt -> basicDialog.show());
        basicDialog.querySelector("[slot='actions'] md-text-button").addEventListener("click", evt -> basicDialog.close());
        assertEquals("dialog: 태그명은 md-dialog",
                "MD-DIALOG", basicDialog.tagName);

        // Alert Dialog
        var alertExample = addExampleCode(propertiesSection,
            "📘 Alert Dialog (알림 다이얼로그)",
            "중요한 메시지를 표시하는 alert 타입 다이얼로그입니다.",
            """
            var alertDialog = alert()
                .headline("경고")
                .content(div().add("중요한 알림"))
                .actions(div().add(button().text("확인")))
                .element();

            var alertButton = button().text("경고 보기").element();
            alertButton.addEventListener("click", evt -> alertDialog.show());
            """);
        var alertDialog = alert()
                .headline("경고")
                .content(div().add("중요한 알림"))
                .actions(div().add(button().text().text("확인")))
                .element();
        var alertButton = button().text().text("경고 보기").element();
        var alertContainer = div()
                .add(alertButton)
                .add(alertDialog)
                .element();
        alertExample.addInteractiveDemo(alertContainer, false);
        alertButton.addEventListener("click", evt -> alertDialog.show());
        alertDialog.querySelector("[slot='actions'] md-text-button").addEventListener("click", evt -> alertDialog.close());
        assertEquals("alert dialog: type은 'alert'",
                "alert", alertDialog.type);

        // Type Property
        var typeExample = addExampleCode(propertiesSection,
            "📘 Type Property (타입 속성)",
            "type 속성을 직접 설정할 수 있습니다.",
            """
            var typeDialog = dialog()
                .type("alert")
                .headline("타입 지정")
                .content(div().add("타입을 직접 설정"))
                .actions(div().add(button().text("닫기")))
                .element();

            var typeButton = button().text("타입 확인").element();
            typeButton.addEventListener("click", evt -> typeDialog.show());
            """);
        var typeDialog = dialog()
                .type("alert")
                .headline("타입 지정")
                .content(div().add("타입을 직접 설정"))
                .actions(div().add(button().text().text("닫기")))
                .element();
        var typeButton = button().text().text("타입 확인").element();
        var typeContainer = div()
                .add(typeButton)
                .add(typeDialog)
                .element();
        typeExample.addInteractiveDemo(typeContainer, false);
        typeButton.addEventListener("click", evt -> typeDialog.show());
        typeDialog.querySelector("[slot='actions'] md-text-button").addEventListener("click", evt -> typeDialog.close());
        assertEquals("type 속성: 'alert'",
                "alert", typeDialog.type);

        // Open Property
        var openExample = addExampleCode(propertiesSection,
            "📘 Open Property (열림 상태)",
            "open 속성으로 다이얼로그를 페이지 로드 시 즉시 열 수 있습니다.",
            """
            var openDialog = dialog()
                .headline("자동 열림")
                .content(div().add("open 속성이 true"))
                .actions(div().add(button().text("닫기")))
                .open(true)
                .element();

            body().add(openDialog);
            """);
        var openDialog = dialog()
                .headline("자동 열림")
                .content(div().add("open 속성이 true"))
                .actions(div().add(button().text().text("닫기")))
                .open(true)
                .element();
        var openContainer = div()
                .add(div().text("(이 다이얼로그는 자동으로 열립니다)"))
                .add(openDialog)
                .element();
        openExample.addInteractiveDemo(openContainer, false);
        openDialog.querySelector("[slot='actions'] md-text-button").addEventListener("click", evt -> openDialog.close());
        assertTrue("open 속성: true", openDialog.open);

        // Quick Property
        var quickExample = addExampleCode(propertiesSection,
            "📘 Quick Property (빠른 전환)",
            "quick 속성으로 애니메이션을 스킵할 수 있습니다.",
            """
            var quickDialog = dialog()
                .headline("빠른 다이얼로그")
                .content(div().add("애니메이션 없음"))
                .quick(true)
                .actions(div().add(button().text("닫기")))
                .element();

            var quickButton = button().text("빠르게 열기").element();
            quickButton.addEventListener("click", evt -> quickDialog.show());
            """);
        var quickDialog = dialog()
                .headline("빠른 다이얼로그")
                .content(div().add("애니메이션 없음"))
                .quick(true)
                .actions(div().add(button().text().text("닫기")))
                .element();
        var quickButton = button().text().text("빠르게 열기").element();
        var quickContainer = div()
                .add(quickButton)
                .add(quickDialog)
                .element();
        quickExample.addInteractiveDemo(quickContainer, false);
        quickButton.addEventListener("click", evt -> quickDialog.show());
        quickDialog.querySelector("[slot='actions'] md-text-button").addEventListener("click", evt -> quickDialog.close());
        assertTrue("quick 속성: true", quickDialog.quick);

        // NoFocusTrap Property
        var noFocusTrapExample = addExampleCode(propertiesSection,
            "📘 NoFocusTrap Property (포커스 트랩 비활성화)",
            "noFocusTrap 속성으로 포커스가 다이얼로그에 갇히지 않게 할 수 있습니다.",
            """
            var noFocusTrapDialog = dialog()
                .headline("포커스 자유")
                .content(div().add("포커스 트랩 없음"))
                .noFocusTrap(true)
                .actions(div().add(button().text("닫기")))
                .element();

            var noFocusTrapButton = button().text("열기").element();
            noFocusTrapButton.addEventListener("click", evt -> noFocusTrapDialog.show());
            """);
        var noFocusTrapDialog = dialog()
                .headline("포커스 자유")
                .content(div().add("포커스 트랩 없음"))
                .noFocusTrap(true)
                .actions(div().add(button().text().text("닫기")))
                .element();
        var noFocusTrapButton = button().text().text("열기").element();
        var noFocusTrapContainer = div()
                .add(noFocusTrapButton)
                .add(noFocusTrapDialog)
                .element();
        noFocusTrapExample.addInteractiveDemo(noFocusTrapContainer, false);
        noFocusTrapButton.addEventListener("click", evt -> noFocusTrapDialog.show());
        noFocusTrapDialog.querySelector("[slot='actions'] md-text-button").addEventListener("click", evt -> noFocusTrapDialog.close());
        assertTrue("noFocusTrap 속성: true", noFocusTrapDialog.noFocusTrap);
    }
}
