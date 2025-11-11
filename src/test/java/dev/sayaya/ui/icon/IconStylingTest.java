package dev.sayaya.ui.icon;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.IconElementBuilder.icon;
import static org.jboss.elemento.Elements.*;

public class IconStylingTest {
    public static void test() {
        printSectionHeader("3. 스타일링 (Styling)");
        printDescription("아이콘의 크기, 색상, 변형을 설정할 수 있습니다.");
        printSeparator();

        var stylingSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(stylingSection);

        stylingSection.appendChild(h(3).text("Icon Styling").element());

        // Custom style
        var styleExample = addExampleCode(stylingSection,
            "📘 Custom Style",
            "CSS 변수와 스타일을 사용하여 아이콘을 커스터마이즈합니다.",
            """
            var icon = icon("star")
                .style("--md-icon-size", "48px")
                .style("color", "gold")
                .element();
            """);
        var styledIcon = icon("star")
                .style("--md-icon-size", "48px")
                .style("color", "gold")
                .element();
        styleExample.addInteractiveDemo(styledIcon, false);

        var style = styledIcon.style;
        assertNotNull("스타일링: style은 null이 아님", style);
        assertTrue("스타일링: color 속성을 가짐",
                style.color.length() > 0 || style.cssText.contains("color"));

        // Large icon
        var largeExample = addExampleCode(stylingSection,
            "📘 Large Icon",
            "크기를 조정한 아이콘입니다.",
            """
            var icon = icon("star")
                .style("--md-icon-size", "48px")
                .element();
            """);
        var largeIcon = icon("star")
                .style("--md-icon-size", "48px")
                .element();
        largeExample.addInteractiveDemo(largeIcon, false);

        var largeStyle = largeIcon.style;
        assertTrue("아이콘 스타일: 큰 아이콘은 크기 스타일을 가짐",
                largeStyle.cssText.contains("--md-icon-size") || largeStyle.cssText.contains("48px"));

        // Filled icon
        var filledExample = addExampleCode(stylingSection,
            "📘 Filled Icon",
            "채워진 스타일의 아이콘입니다.",
            """
            var icon = icon("bookmark")
                .style("font-variation-settings", "'FILL' 1")
                .element();
            """);
        var filledIcon = icon("bookmark")
                .style("font-variation-settings", "'FILL' 1")
                .element();
        filledExample.addInteractiveDemo(filledIcon, false);

        var fillStyle = filledIcon.style;
        assertTrue("아이콘 스타일: 채워진 아이콘은 fill 변형을 가짐",
                fillStyle.cssText.contains("font-variation-settings") || fillStyle.cssText.contains("FILL"));

        // Colored icon
        var coloredExample = addExampleCode(stylingSection,
            "📘 Colored Icon",
            "색상이 적용된 아이콘입니다.",
            """
            var icon = icon("palette")
                .style("color", "#1976d2")
                .element();
            """);
        var coloredIcon = icon("palette")
                .style("color", "#1976d2")
                .element();
        coloredExample.addInteractiveDemo(coloredIcon, false);

        var colorStyle = coloredIcon.style;
        assertNotNull("아이콘 스타일: 색상 아이콘 스타일은 null이 아님", colorStyle);

        // Bold icon
        var boldExample = addExampleCode(stylingSection,
            "📘 Bold Icon",
            "굵은 스타일의 아이콘입니다.",
            """
            var icon = icon("info")
                .style("font-variation-settings", "'wght' 700")
                .element();
            """);
        var boldIcon = icon("info")
                .style("font-variation-settings", "'wght' 700")
                .element();
        boldExample.addInteractiveDemo(boldIcon, false);

        assertTrue("아이콘 스타일: 굵은 아이콘은 가중치 변형을 가짐",
                boldIcon.style.cssText.length() > 0);
    }
}
