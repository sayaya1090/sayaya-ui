package dev.sayaya.ui.button;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.ButtonElementBuilder.button;
import static org.jboss.elemento.Elements.*;

public class BasicPropertiesTest {
    public static void test() {
        printSectionHeader("2. 버튼 기본 속성 (Basic Properties)");
        printDescription("버튼의 기본 속성들을 테스트합니다:");
        printDescription("- disabled: 버튼 비활성화");
        printDescription("- softDisabled: 키보드 포커스는 유지하면서 비활성화");
        printDescription("- href: 링크 버튼 (anchor 역할)");
        printDescription("- target: 링크 열기 방식 (_blank, _self 등)");
        printDescription("- download: 다운로드 링크");
        printSeparator();

        var propertiesSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(propertiesSection);

        propertiesSection.appendChild(h(3).text("Basic Properties").element());

        // Disabled
        addExampleCode(propertiesSection,
            "📘 Disabled (비활성화)",
            "버튼을 완전히 비활성화합니다. 클릭 이벤트가 발생하지 않으며 키보드 포커스도 받을 수 없습니다.",
            """
            var button = button().filled()
                .disabled(true)
                .text("Disabled")
                .element();
            """);
        var disabledBtn = button().filled()
                .disabled(true)
                .text("Disabled")
                .element();
        propertiesSection.appendChild(disabledBtn);
        assertTrue("disabled 속성: true여야 함", disabledBtn.disabled);

        // Soft Disabled
        addExampleCode(propertiesSection,
            "📘 Soft Disabled (소프트 비활성화)",
            "시각적으로는 비활성화되지만 키보드 포커스를 받을 수 있습니다. 접근성을 위해 사용합니다.",
            """
            var button = button().filled()
                .softDisabled(true)
                .text("Soft Disabled")
                .element();
            """);
        var softDisabledBtn = button().filled()
                .softDisabled(true)
                .text("Soft Disabled")
                .element();
        propertiesSection.appendChild(softDisabledBtn);
        assertTrue("softDisabled 속성: true여야 함", softDisabledBtn.softDisabled);

        // Href (Link Button)
        addExampleCode(propertiesSection,
            "📘 Href (링크 버튼)",
            "버튼을 링크로 동작하게 합니다. 클릭 시 지정된 URL로 이동합니다.",
            """
            var button = button().text()
                .href("https://example.com")
                .text("Link")
                .element();
            """);
        var linkBtn = button().text()
                .href("https://example.com")
                .text("Link")
                .element();
        propertiesSection.appendChild(linkBtn);
        assertEquals("href 속성: https://example.com이어야 함",
                "https://example.com", linkBtn.href);

        // Target
        addExampleCode(propertiesSection,
            "📘 Target (링크 열기 방식)",
            "링크를 열 방식을 지정합니다. _blank는 새 탭에서 열기입니다.",
            """
            var button = button().text()
                .href("https://example.com")
                .target("_blank")
                .text("New Tab")
                .element();
            """);
        var targetBtn = button().text()
                .href("https://example.com")
                .target("_blank")
                .text("New Tab")
                .element();
        propertiesSection.appendChild(targetBtn);
        assertEquals("target 속성: _blank여야 함", "_blank", targetBtn.target);

        // Download
        addExampleCode(propertiesSection,
            "📘 Download (다운로드)",
            "링크를 다운로드 링크로 만듭니다. 파일명을 지정할 수 있습니다.",
            """
            var button = button().text()
                .href("/file.pdf")
                .download("document.pdf")
                .text("Download")
                .element();
            """);
        var downloadBtn = button().text()
                .href("/file.pdf")
                .download("document.pdf")
                .text("Download")
                .element();
        propertiesSection.appendChild(downloadBtn);
        assertEquals("download 속성: document.pdf여야 함",
                "document.pdf", downloadBtn.download);
    }
}
