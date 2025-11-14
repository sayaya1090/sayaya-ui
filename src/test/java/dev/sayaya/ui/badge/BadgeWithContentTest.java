package dev.sayaya.ui.badge;

import elemental2.dom.HTMLDivElement;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.BadgeElementBuilder.badge;
import static dev.sayaya.ui.elements.IconElementBuilder.icon;
import static org.jboss.elemento.Elements.*;

public class BadgeWithContentTest {
    public static void test() {
        printSectionHeader("3. Badge with Content (컨텐츠와 함께 사용)");
        printDescription("아이콘과 함께 사용하는 Badge 예제입니다.");
        printSeparator();

        var contentSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(contentSection);

        contentSection.appendChild(h(3).text("Badge with Content").element());

        // Badge on Icon
        var iconExample = addExampleCode(contentSection,
            "📘 Badge on Icon (아이콘에 배지)",
            "아이콘에 배지를 표시합니다.",
            """
            var iconElem = icon("notifications").element();
            var container = div()
                .style("position", "relative")
                .style("display", "inline-block")
                .add(iconElem)
                .add(badge()
                    .value(3)
                    .anchorElement(iconElem)
                    .style("position", "absolute")
                    .style("top", "calc(0px - var(--_large-size) / 2)")
                    .style("right", "calc(var(--_large-size) - 5px)")
                )
                .element();
            """);
        var iconElem = icon("notifications").element();
        var iconContainer = div()
                .style("position", "relative")
                .style("display", "inline-block")
                .add(iconElem)
                .add(badge()
                    .value(3)
                    .anchorElement(iconElem)
                    .style("position", "absolute")
                    .style("top", "calc(0px - var(--_large-size) / 2)")
                    .style("right", "calc(var(--_large-size) - 5px)")
                )
                .element();
        iconExample.addInteractiveDemo(div()
                .style("padding", "20px")
                .add(iconContainer)
                .element(), false);

        // Multiple Badges
        var multipleExample = addExampleCode(contentSection,
            "📘 Multiple Badges (여러 배지)",
            "여러 아이콘에 각각 배지를 표시합니다.",
            """
            var mailIcon = icon("mail").element();
            var chatIcon = icon("chat").element();
            var notifIcon = icon("notifications").element();
            var container = div()
                .style("display", "flex")
                .style("gap", "24px")
                .add(div()
                    .style("position", "relative")
                    .style("display", "inline-block")
                    .add(mailIcon)
                    .add(badge()
                        .value(10)
                        .anchorElement(mailIcon)
                        .style("position", "absolute")
                        .style("top", "calc(0px - var(--_large-size) / 2)")
                        .style("right", "calc(var(--_large-size) - 5px)")
                    )
                )
                .add(div()
                    .style("position", "relative")
                    .style("display", "inline-block")
                    .add(chatIcon)
                    .add(badge()
                        .value(2)
                        .anchorElement(chatIcon)
                        .style("position", "absolute")
                        .style("top", "calc(0px - var(--_large-size) / 2)")
                        .style("right", "calc(var(--_large-size) - 5px)")
                    )
                )
                .add(div()
                    .style("position", "relative")
                    .style("display", "inline-block")
                    .add(notifIcon)
                    .add(badge()
                        .value(99)
                        .anchorElement(notifIcon)
                        .style("position", "absolute")
                        .style("top", "calc(0px - var(--_large-size) / 2)")
                        .style("right", "calc(var(--_large-size) - 5px)")
                    )
                )
                .element();
            """);
        var multipleContainer = div()
                .style("display", "flex")
                .style("gap", "24px")
                .add(createBadgedIcon("mail", 10))
                .add(createBadgedIcon("chat", 2))
                .add(createBadgedIcon("notifications", 99))
                .element();
        multipleExample.addInteractiveDemo(div()
                .style("padding", "20px")
                .add(multipleContainer)
                .element(), false);

        // Badge with Dot Indicator
        var dotExample = addExampleCode(contentSection,
            "📘 Dot Badge Indicator (점 표시)",
            "값 없이 점으로만 표시하는 배지입니다.",
            """
            var personIcon = icon("person").element();
            var container = div()
                .style("position", "relative")
                .style("display", "inline-block")
                .add(personIcon)
                .add(badge()
                    .anchorElement(personIcon)
                    .style("position", "absolute")
                    .style("top", "calc(0px - var(--_size) / 2)")
                    .style("right", "calc(var(--_size) + 2px)")
                )
                .element();
            """);
        var personIcon = icon("person").element();
        var dotContainer = div()
                .style("position", "relative")
                .style("display", "inline-block")
                .add(personIcon)
                .add(badge()
                    .anchorElement(personIcon)
                    .style("position", "absolute")
                    .style("top", "calc(0px - var(--_size) / 2)")
                    .style("right", "calc(var(--_size) + 2px)")
                )
                .element();
        dotExample.addInteractiveDemo(div()
                .style("padding", "20px")
                .add(dotContainer)
                .element(), false);
    }

    private static HTMLDivElement createBadgedIcon(String iconName, int count) {
        var iconElem = icon(iconName).element();
        return div()
                .style("position", "relative")
                .style("display", "inline-block")
                .add(iconElem)
                .add(badge()
                    .value(count)
                    .anchorElement(iconElem)
                    .style("position", "absolute")
                    .style("top", "calc(0px - var(--_large-size) / 2)")
                    .style("right", "calc(var(--_large-size) - 5px)")
                ).element();
    }
}
