package dev.sayaya.ui.button;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.ButtonElementBuilder.button;
import static org.jboss.elemento.Elements.*;

public class UseCasesTest {
    public static void test() {
        printSectionHeader("7. 사용 사례 (Use Cases)");
        printDescription("실제 UI 시나리오에서 버튼을 사용하는 방법:");
        printDescription("- 다이얼로그의 주요/보조 액션");
        printDescription("- 폼 제출 버튼");
        printDescription("- 링크 버튼");
        printDescription("- 다중 옵션 표시");
        printSeparator();

        var useCasesSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(useCasesSection);

        useCasesSection.appendChild(h(3).text("Use Cases").element());

        // Dialog Actions
        var dialogExample = addExampleCode(useCasesSection,
            "📘 Dialog Actions (다이얼로그 버튼)",
            "다이얼로그에서는 주요 액션에 Filled, 보조 액션에 Outlined를 사용합니다.",
            """
            var dialog = div().element();
            var confirmBtn = button().filled()
                .text("Confirm")
                .element();
            var cancelBtn = button().outlined()
                .text("Cancel")
                .element();
            dialog.appendChild(cancelBtn);
            dialog.appendChild(confirmBtn);
            """);
        var confirmDialog = div()
                .style("display", "flex")
                .style("gap", "8px")
                .style("justify-content", "flex-end")
                .element();

        var primaryAction = button().filled()
                .text("Confirm")
                .element();
        var secondaryAction = button().outlined()
                .text("Cancel")
                .element();

        confirmDialog.appendChild(secondaryAction);
        confirmDialog.appendChild(primaryAction);
        dialogExample.addInteractiveDemo(confirmDialog, false);

        assertEquals("다이얼로그: 주요 버튼은 filled",
                "MD-FILLED-BUTTON", primaryAction.tagName);
        assertEquals("다이얼로그: 보조 버튼은 outlined",
                "MD-OUTLINED-BUTTON", secondaryAction.tagName);

        // Form Submit
        var submitExample = addExampleCode(useCasesSection,
            "📘 Form Submit (폼 제출)",
            "폼의 주요 제출 버튼은 Filled를 사용합니다.",
            """
            var form = form().id("user-form").element();
            var submitBtn = button().filled()
                .type("submit")
                .icon("send")
                .text("Create Account")
                .element();
            form.appendChild(submitBtn);
            """);
        var submitForm = form().id("user-form").element();

        var submitButton = button().filled()
                .type("submit")
                .icon("send")
                .text("Create Account")
                .element();
        submitForm.appendChild(submitButton);
        submitExample.addInteractiveDemo(submitForm, false);

        assertEquals("폼 제출: submit 타입", "submit", submitButton.type);
        assertEquals("폼 제출: filled 버튼", "MD-FILLED-BUTTON", submitButton.tagName);

        // External Link
        var linkExample = addExampleCode(useCasesSection,
            "📘 External Link (외부 링크)",
            "외부 문서나 사이트로 연결하는 버튼은 Text나 Outlined를 사용합니다.",
            """
            var linkBtn = button().text()
                .text("Documentation")
                .icon("open_in_new", true)
                .href("https://material.io/components/buttons")
                .target("_blank")
                .element();
            """);
        var linkButton = button().text()
                .text("Documentation")
                .icon("open_in_new", true)
                .href("https://material.io/components/buttons")
                .target("_blank")
                .element();
        linkExample.addInteractiveDemo(linkButton, false);

        assertEquals("외부 링크: href 설정",
                "https://material.io/components/buttons", linkButton.href);
        assertEquals("외부 링크: 새 탭에서 열기", "_blank", linkButton.target);

        // Multiple Options
        var optionsExample = addExampleCode(useCasesSection,
            "📘 Multiple Options (다중 옵션)",
            "여러 개의 선택지를 제공할 때는 Text 버튼을 사용합니다.",
            """
            var container = div().element();
            var option1 = button().text().text("Option 1").element();
            var option2 = button().text().text("Option 2").element();
            var option3 = button().text().text("Option 3").element();
            container.appendChild(option1);
            container.appendChild(option2);
            container.appendChild(option3);
            """);
        var optionsContainer = div()
                .style("display", "flex")
                .style("gap", "8px")
                .element();

        var option1 = button().text().text("Option 1").element();
        var option2 = button().text().text("Option 2").element();
        var option3 = button().text().text("Option 3").element();

        optionsContainer.appendChild(option1);
        optionsContainer.appendChild(option2);
        optionsContainer.appendChild(option3);
        optionsExample.addInteractiveDemo(optionsContainer, false);

        assertEquals("다중 옵션: text 버튼", "MD-TEXT-BUTTON", option1.tagName);
        assertEquals("다중 옵션: text 버튼", "MD-TEXT-BUTTON", option2.tagName);
        assertEquals("다중 옵션: text 버튼", "MD-TEXT-BUTTON", option3.tagName);

        // Elevated on Pattern
        var elevatedExample = addExampleCode(useCasesSection,
            "📘 Elevated on Pattern (패턴 배경)",
            "패턴이나 이미지 배경 위에서는 Elevated 버튼이 시각적으로 분리됩니다.",
            """
            var patternBg = div()
                .style("background-image", "url('pattern.png')")
                .element();
            var elevatedBtn = button().elevated()
                .text("Get Started")
                .element();
            patternBg.appendChild(elevatedBtn);
            """);
        var patternedBackground = div()
                .style("padding", "20px")
                .style("background", "repeating-linear-gradient(45deg, #f0f0f0, #f0f0f0 10px, #e0e0e0 10px, #e0e0e0 20px)")
                .element();

        var elevatedOnPattern = button().elevated()
                .text("Get Started")
                .element();
        patternedBackground.appendChild(elevatedOnPattern);
        elevatedExample.addInteractiveDemo(patternedBackground, false);

        assertEquals("패턴 배경: elevated 버튼",
                "MD-ELEVATED-BUTTON", elevatedOnPattern.tagName);

        // Download Button
        var downloadExample = addExampleCode(useCasesSection,
            "📘 Download Button (다운로드 버튼)",
            "파일 다운로드는 아이콘과 함께 명확하게 표시합니다.",
            """
            var downloadBtn = button().outlined()
                .icon("download")
                .text("Download Report")
                .href("/reports/annual.pdf")
                .download("annual_report.pdf")
                .element();
            """);
        var downloadButton = button().outlined()
                .icon("download")
                .text("Download Report")
                .href("/reports/annual.pdf")
                .download("annual_report.pdf")
                .element();
        downloadExample.addInteractiveDemo(downloadButton, false);

        assertEquals("다운로드: download 속성",
                "annual_report.pdf", downloadButton.download);
        assertNotNull("다운로드: 아이콘 존재",
                downloadButton.querySelector("md-icon"));

        // 사용 예시: 타입별 버튼 생성
        assertEquals("사용 예시: filled", "MD-FILLED-BUTTON", button().filled().text("저장").element().tagName);
        assertEquals("사용 예시: outlined", "MD-OUTLINED-BUTTON", button().outlined().text("취소").element().tagName);
        assertEquals("사용 예시: text", "MD-TEXT-BUTTON", button().text().text("더보기").element().tagName);

        // 버튼 우선순위: 강조도 높은 순서 filled > filled-tonal > elevated > outlined > text
        String[] priority = {
                button().filled().element().tagName, button().filledTonal().element().tagName,
                button().elevated().element().tagName, button().outlined().element().tagName,
                button().text().element().tagName};
        assertEquals("버튼 우선순위: 5단계 변형이 모두 다름", 5L, java.util.Arrays.stream(priority).distinct().count());
        assertEquals("버튼 우선순위: 최상위는 filled", "MD-FILLED-BUTTON", priority[0]);
        assertEquals("버튼 우선순위: 최하위는 text", "MD-TEXT-BUTTON", priority[4]);

        // 동적 변경: 생성 후 빌더로 상태 변경
        var dynamic = button().filled().text("동적");
        dynamic.disabled(true);
        assertTrue("동적 변경: disabled(true) 적용", dynamic.element().disabled);
        dynamic.disabled(false);
        assertFalse("동적 변경: disabled(false) 해제", dynamic.element().disabled);
        // text()는 기존 내용을 바꾸지 않고 텍스트 노드를 덧붙인다(아이콘과 함께 쓰기 위함)
        dynamic.text("변경됨");
        assertEquals("동적 변경: text()는 텍스트를 덧붙임", "동적변경됨", dynamic.element().textContent);

        // 비교: disabled vs soft-disabled
        var hard = button().filled().text("disabled").disabled(true).element();
        var soft = button().filled().text("soft").softDisabled(true).element();
        assertTrue("비교: disabled 버튼은 disabled", hard.disabled && !hard.softDisabled);
        assertTrue("비교: soft-disabled 버튼은 disabled 아님", soft.softDisabled && !soft.disabled);
    }
}
