package dev.sayaya.ui.icon;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.IconElementBuilder.icon;
import static org.jboss.elemento.Elements.*;

public class IconBasicTest {
    public static void test() {
        printSectionHeader("1. 기본 Icon (Basic Icon)");
        printDescription("Material Design Icon은 Material Symbols를 표시합니다.");
        printSeparator();

        var iconSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(iconSection);

        iconSection.appendChild(h(3).text("Basic Icon").element());

        // Empty icon
        var emptyExample = addExampleCode(iconSection,
            "📘 Empty Icon",
            "빈 아이콘을 생성합니다.",
            """
            var icon = icon().element();
            """);
        var emptyIcon = icon().element();
        emptyExample.addInteractiveDemo(emptyIcon, false);
        assertEquals("아이콘 생성: 태그명은 md-icon",
                "MD-ICON", emptyIcon.tagName);

        // Icon with name
        var namedExample = addExampleCode(iconSection,
            "📘 Icon with Name",
            "이름으로 아이콘을 생성합니다.",
            """
            var icon = icon("settings").element();
            """);
        var namedIcon = icon("settings").element();
        namedExample.addInteractiveDemo(namedIcon, false);
        assertEquals("이름이 있는 아이콘: 태그명은 md-icon",
                "MD-ICON", namedIcon.tagName);
        assertEquals("이름이 있는 아이콘: textContent는 'settings'",
                "settings", namedIcon.textContent);

        // Icon with unicode
        var unicodeExample = addExampleCode(iconSection,
            "📘 Icon with Unicode",
            "유니코드로 아이콘을 생성합니다.",
            """
            var icon = icon("&#xe834").element();
            """);
        var unicodeIcon = icon("&#xe834").element();
        unicodeExample.addInteractiveDemo(unicodeIcon, false);
        assertEquals("유니코드 아이콘: 태그명은 md-icon",
                "MD-ICON", unicodeIcon.tagName);
        assertNotNull("유니코드 아이콘: textContent는 null이 아님",
                unicodeIcon.textContent);
    }
}
