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
        addExampleCode(useCasesSection,
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
        useCasesSection.appendChild(confirmDialog);

        var primaryAction = button().filled()
                .text("Confirm")
                .element();
        var secondaryAction = button().outlined()
                .text("Cancel")
                .element();

        confirmDialog.appendChild(secondaryAction);
        confirmDialog.appendChild(primaryAction);

        assertEquals("다이얼로그: 주요 버튼은 filled",
                "MD-FILLED-BUTTON", primaryAction.tagName);
        assertEquals("다이얼로그: 보조 버튼은 outlined",
                "MD-OUTLINED-BUTTON", secondaryAction.tagName);

        // Form Submit
        addExampleCode(useCasesSection,
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
        useCasesSection.appendChild(submitForm);

        var submitButton = button().filled()
                .type("submit")
                .icon("send")
                .text("Create Account")
                .element();
        submitForm.appendChild(submitButton);

        assertEquals("폼 제출: submit 타입", "submit", submitButton.type);
        assertEquals("폼 제출: filled 버튼", "MD-FILLED-BUTTON", submitButton.tagName);

        // External Link
        addExampleCode(useCasesSection,
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
        useCasesSection.appendChild(linkButton);

        assertEquals("외부 링크: href 설정",
                "https://material.io/components/buttons", linkButton.href);
        assertEquals("외부 링크: 새 탭에서 열기", "_blank", linkButton.target);

        // Multiple Options
        addExampleCode(useCasesSection,
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
        useCasesSection.appendChild(optionsContainer);

        var option1 = button().text().text("Option 1").element();
        var option2 = button().text().text("Option 2").element();
        var option3 = button().text().text("Option 3").element();

        optionsContainer.appendChild(option1);
        optionsContainer.appendChild(option2);
        optionsContainer.appendChild(option3);

        assertEquals("다중 옵션: text 버튼", "MD-TEXT-BUTTON", option1.tagName);
        assertEquals("다중 옵션: text 버튼", "MD-TEXT-BUTTON", option2.tagName);
        assertEquals("다중 옵션: text 버튼", "MD-TEXT-BUTTON", option3.tagName);

        // Elevated on Pattern
        addExampleCode(useCasesSection,
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
        useCasesSection.appendChild(patternedBackground);

        var elevatedOnPattern = button().elevated()
                .text("Get Started")
                .element();
        patternedBackground.appendChild(elevatedOnPattern);

        assertEquals("패턴 배경: elevated 버튼",
                "MD-ELEVATED-BUTTON", elevatedOnPattern.tagName);

        // Download Button
        addExampleCode(useCasesSection,
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
        useCasesSection.appendChild(downloadButton);

        assertEquals("다운로드: download 속성",
                "annual_report.pdf", downloadButton.download);
        assertNotNull("다운로드: 아이콘 존재",
                downloadButton.querySelector("md-icon"));
    }
}
