package dev.sayaya.ui.button;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.ButtonElementBuilder.button;
import static org.jboss.elemento.Elements.*;

public class IconSupportTest {
    public static void test() {
        printSectionHeader("4. 아이콘 지원 (Icon Support)");
        printDescription("버튼에 아이콘을 추가하는 방법을 테스트합니다:");
        printDescription("- icon(): 선행 아이콘 (텍스트 앞)");
        printDescription("- icon(name, true): 후행 아이콘 (텍스트 뒤)");
        printSeparator();

        var iconSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(iconSection);

        iconSection.appendChild(h(3).text("Icon Support").element());

        // Leading Icon
        addExampleCode(iconSection,
            "📘 Leading Icon (선행 아이콘)",
            "버튼 텍스트 앞에 아이콘을 표시합니다. 액션의 의미를 시각적으로 강화합니다.",
            """
            var button = button().filled()
                .icon("send")
                .text("Send")
                .element();
            """);
        var leadingBtn = button().filled()
                .icon("send")
                .text("Send")
                .element();
        iconSection.appendChild(leadingBtn);

        var leadingIcon = leadingBtn.querySelector("md-icon");
        assertNotNull("선행 아이콘: 아이콘 요소가 존재해야 함", leadingIcon);
        assertEquals("선행 아이콘: slot은 icon이어야 함",
                "icon", leadingIcon.getAttribute("slot"));
        assertFalse("선행 아이콘: trailingIcon은 false여야 함",
                leadingBtn.trailingIcon);

        // Trailing Icon
        addExampleCode(iconSection,
            "📘 Trailing Icon (후행 아이콘)",
            "버튼 텍스트 뒤에 아이콘을 표시합니다. 다음 단계나 방향을 나타낼 때 유용합니다.",
            """
            var button = button().filled()
                .text("Next")
                .icon("arrow_forward", true)
                .element();
            """);
        var trailingBtn = button().filled()
                .text("Next")
                .icon("arrow_forward", true)
                .element();
        iconSection.appendChild(trailingBtn);

        var trailingIcon = trailingBtn.querySelector("md-icon");
        assertNotNull("후행 아이콘: 아이콘 요소가 존재해야 함", trailingIcon);
        assertEquals("후행 아이콘: slot은 icon이어야 함",
                "icon", trailingIcon.getAttribute("slot"));
        assertTrue("후행 아이콘: trailingIcon은 true여야 함",
                trailingBtn.trailingIcon);

        // Icon with Download
        addExampleCode(iconSection,
            "📘 Icon with Link (아이콘과 링크)",
            "아이콘을 포함한 링크 버튼입니다. 다운로드나 외부 링크에 유용합니다.",
            """
            var button = button().outlined()
                .icon("download")
                .text("Download Report")
                .href("/reports/annual.pdf")
                .download("annual_report.pdf")
                .element();
            """);
        var downloadBtn = button().outlined()
                .icon("download")
                .text("Download Report")
                .href("/reports/annual.pdf")
                .download("annual_report.pdf")
                .element();
        iconSection.appendChild(downloadBtn);

        var downloadIcon = downloadBtn.querySelector("md-icon");
        assertNotNull("다운로드 버튼: 아이콘이 존재해야 함", downloadIcon);
        assertEquals("다운로드 버튼: download 속성이 설정되어야 함",
                "annual_report.pdf", downloadBtn.download);
    }
}
