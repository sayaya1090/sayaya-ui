package dev.sayaya.ui.button;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.ButtonElementBuilder.button;
import static org.jboss.elemento.Elements.*;

public class IconButtonTest {
    public static void test() {
        printSectionHeader("8. 아이콘 버튼 (Icon Button)");
        printDescription("아이콘만 있는 버튼의 4가지 스타일과 기능:");
        printDescription("- Standard: 기본 아이콘 버튼");
        printDescription("- Filled: 채워진 배경의 아이콘 버튼");
        printDescription("- Filled Tonal: 토널 배경의 아이콘 버튼");
        printDescription("- Outlined: 테두리만 있는 아이콘 버튼");
        printDescription("- Toggle: on/off 상태를 가지는 토글 버튼");
        printSeparator();

        testIconButtonVariants();
        testIconButtonProperties();
        testIconButtonToggle();
    }

    private static void testIconButtonVariants() {
        var iconVariantsSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(iconVariantsSection);

        iconVariantsSection.appendChild(h(3).text("Icon Button Variants").element());

        // Standard Icon Button
        var standardExample = addExampleCode(iconVariantsSection,
            "📘 Standard Icon Button (기본 아이콘 버튼)",
            "가장 기본적인 아이콘 버튼입니다. 낮은 우선순위 액션에 사용합니다.",
            """
            var button = button().icon("settings").element();
            """);
        var standard = button().icon("settings").element();
        standardExample.addInteractiveDemo(standard, false);
        assertEquals("standard 아이콘 버튼: md-icon-button",
                "MD-ICON-BUTTON", standard.tagName);

        // Filled Icon Button
        var filledExample = addExampleCode(iconVariantsSection,
            "📘 Filled Icon Button (채워진 아이콘 버튼)",
            "가장 높은 시각적 임팩트를 제공합니다. 중요한 액션에 사용합니다.",
            """
            var button = button().icon("favorite").filled().element();
            """);
        var filled = button().icon("favorite").filled().element();
        filledExample.addInteractiveDemo(filled, false);
        assertEquals("filled 아이콘 버튼: md-filled-icon-button",
                "MD-FILLED-ICON-BUTTON", filled.tagName);

        // Filled Tonal Icon Button
        var filledTonalExample = addExampleCode(iconVariantsSection,
            "📘 Filled Tonal Icon Button (토널 아이콘 버튼)",
            "Filled와 Standard의 중간 강조 수준입니다.",
            """
            var button = button().icon("edit").filledTonal().element();
            """);
        var filledTonal = button().icon("edit").filledTonal().element();
        filledTonalExample.addInteractiveDemo(filledTonal, false);
        assertEquals("filled-tonal 아이콘 버튼: md-filled-tonal-icon-button",
                "MD-FILLED-TONAL-ICON-BUTTON", filledTonal.tagName);

        // Outlined Icon Button
        var outlinedExample = addExampleCode(iconVariantsSection,
            "📘 Outlined Icon Button (외곽선 아이콘 버튼)",
            "중간 수준의 강조입니다. 명확한 경계가 필요할 때 사용합니다.",
            """
            var button = button().icon("delete").outlined().element();
            """);
        var outlined = button().icon("delete").outlined().element();
        outlinedExample.addInteractiveDemo(outlined, false);
        assertEquals("outlined 아이콘 버튼: md-outlined-icon-button",
                "MD-OUTLINED-ICON-BUTTON", outlined.tagName);
    }

    private static void testIconButtonProperties() {
        var iconPropsSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(iconPropsSection);

        iconPropsSection.appendChild(h(3).text("Icon Button Properties").element());

        // Icon with String
        var iconExample = addExampleCode(iconPropsSection,
            "📘 Icon (아이콘 설정)",
            "Material Icons의 아이콘 이름을 지정합니다.",
            """
            var button = button().icon("search").element();
            """);
        var iconBtn = button().icon("search").element();
        iconExample.addInteractiveDemo(iconBtn, false);

        var icon = iconBtn.querySelector("md-icon");
        assertNotNull("아이콘: 존재해야 함", icon);

        // Disabled Icon Button
        var disabledExample = addExampleCode(iconPropsSection,
            "📘 Disabled (비활성화)",
            "아이콘 버튼을 비활성화합니다.",
            """
            var button = button().icon("close").disabled(true).element();
            """);
        var disabledBtn = button().icon("close").disabled(true).element();
        disabledExample.addInteractiveDemo(disabledBtn, false);
        assertTrue("disabled: true", disabledBtn.disabled);

        // Link Icon Button
        var linkExample = addExampleCode(iconPropsSection,
            "📘 Icon Button as Link (링크 아이콘 버튼)",
            "아이콘 버튼을 링크로 사용합니다.",
            """
            var button = button().icon("open_in_new")
                .href("https://example.com")
                .target("_blank")
                .element();
            """);
        var linkBtn = button().icon("open_in_new")
                .href("https://example.com")
                .target("_blank")
                .element();
        linkExample.addInteractiveDemo(linkBtn, false);

        assertEquals("링크: href 설정", "https://example.com", linkBtn.href);
        assertEquals("링크: target _blank", "_blank", linkBtn.target);

        // Aria Label (Accessibility)
        var ariaExample = addExampleCode(iconPropsSection,
            "📘 Accessibility (접근성)",
            "아이콘 버튼은 반드시 aria-label을 제공해야 합니다.",
            """
            var button = button().icon("info")
                .ariaLabel("More information")
                .element();
            """);
        var ariaBtn = button().icon("info")
                .ariaLabel("More information")
                .element();
        ariaExample.addInteractiveDemo(ariaBtn, false);

        assertEquals("aria-label: More information",
                "More information",
                ariaBtn.getAttribute("aria-label"));
    }

    private static void testIconButtonToggle() {
        var toggleSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(toggleSection);

        toggleSection.appendChild(h(3).text("Toggle Icon Button").element());

        // Basic Toggle
        var basicToggleExample = addExampleCode(toggleSection,
            "📘 Basic Toggle (기본 토글)",
            "on/off 상태를 가지는 토글 버튼입니다. 두 가지 아이콘을 번갈아 표시합니다.",
            """
            var button = button().icon("visibility")
                .toggle(true)
                .toggle("visibility_off", false)
                .element();
            """);
        var toggleBtn1 = button().icon("visibility")
                .toggle(true)
                .toggle("visibility_off", false)
                .element();
        var basicToggleState = basicToggleExample.addInteractiveDemo(toggleBtn1);
        basicToggleState.textContent = "Selected: " + toggleBtn1.selected;
        toggleBtn1.addEventListener("click", evt -> {
            basicToggleState.textContent = "Selected: " + toggleBtn1.selected;
        });

        assertTrue("toggle: true", toggleBtn1.toggle);
        assertFalse("초기 selected: false", toggleBtn1.selected);

        // Pre-selected Toggle
        var preselectedToggleExample = addExampleCode(toggleSection,
            "📘 Pre-selected Toggle (초기 선택 상태)",
            "토글 버튼의 초기 상태를 선택됨으로 설정합니다.",
            """
            var button = button().icon("circle")
                .toggle(true)
                .toggle("check_circle", true)
                .element();
            """);
        var toggleBtn2 = button().icon("circle")
                .toggle(true)
                .toggle("check_circle", true)
                .element();
        var preselectedToggleState = preselectedToggleExample.addInteractiveDemo(toggleBtn2);
        preselectedToggleState.textContent = "Selected: " + toggleBtn2.selected;
        toggleBtn2.addEventListener("click", evt -> {
            preselectedToggleState.textContent = "Selected: " + toggleBtn2.selected;
        });

        assertTrue("toggle: true", toggleBtn2.toggle);
        assertTrue("초기 selected: true", toggleBtn2.selected);

        var selectedIcon = toggleBtn2.querySelector("[slot='selected']");
        assertNotNull("selected 아이콘: 존재해야 함", selectedIcon);

        // Toggle with Aria Labels
        var ariaToggleExample = addExampleCode(toggleSection,
            "📘 Toggle Accessibility (토글 접근성)",
            "각 상태에 대한 aria-label을 별도로 지정할 수 있습니다.",
            """
            var button = button().icon("circle")
                .toggle("check_circle", true)
                .ariaLabel("Checked")
                .ariaLabelSelected("Unchecked")
                .element();
            """);
        var toggleBtn3 = button().icon("circle")
                .toggle(true)
                .toggle("check_circle")
                .ariaLabel("Unchecked")
                .ariaLabelSelected("Checked")
                .element();
        var ariaToggleState = ariaToggleExample.addInteractiveDemo(toggleBtn3);
        var updateAriaToggleState = new Runnable() {
            @Override
            public void run() {
                var currentLabel = toggleBtn3.selected ?
                    toggleBtn3.getAttribute("aria-label-selected") :
                    toggleBtn3.getAttribute("aria-label");
                ariaToggleState.textContent = "Selected: " + toggleBtn3.selected + " | aria-label: " + currentLabel;
            }
        };
        updateAriaToggleState.run();
        toggleBtn3.addEventListener("click", evt -> updateAriaToggleState.run());

        assertEquals("aria-label: Unchecked",
                "Unchecked", toggleBtn3.getAttribute("aria-label"));
        assertEquals("aria-label-selected: Checked",
                "Checked", toggleBtn3.getAttribute("aria-label-selected"));

        // Filled Tonal Toggle
        var styledToggleExample = addExampleCode(toggleSection,
            "📘 Styled Toggle (스타일이 적용된 토글)",
            "토글 버튼도 다양한 스타일을 적용할 수 있습니다.",
            """
            var button = button().icon().filledTonal()
                .toggle(true)
                .add("notifications_off")
                .toggle("notifications_active")
                .ariaLabel("Enable notifications")
                .ariaLabelSelected("Disable notifications")
                .element();
            """);
        var toggleBtn4 = button().icon("notifications_off").filledTonal()
                .toggle(true)
                .toggle("notifications_active")
                .ariaLabel("Enable notifications")
                .ariaLabelSelected("Disable notifications")
                .element();
        var styledToggleState = styledToggleExample.addInteractiveDemo(toggleBtn4);
        styledToggleState.textContent = "Selected: " + toggleBtn4.selected + " | Style: " + toggleBtn4.tagName;
        toggleBtn4.addEventListener("click", evt -> {
            styledToggleState.textContent = "Selected: " + toggleBtn4.selected + " | Style: " + toggleBtn4.tagName;
        });

        assertEquals("스타일 토글: md-filled-tonal-icon-button",
                "MD-FILLED-TONAL-ICON-BUTTON", toggleBtn4.tagName);
        assertTrue("스타일 토글: toggle true", toggleBtn4.toggle);

        // 토글 접근성: 상태별 aria-label이 서로 달라야 함
        assertTrue("토글 접근성: 상태별 aria-label이 구분됨", toggleBtn3.toggle
                && !toggleBtn3.getAttribute("aria-label").equals(toggleBtn3.getAttribute("aria-label-selected")));

        // flip-icon-in-rtl (변형 전환 후에도 유지)
        var flipBtn = button().icon("arrow_forward").flipIconInRtl(true).element();
        assertTrue("아이콘 버튼 flip-icon-in-rtl: true", flipBtn.flipIconInRtl);
        var flipFilled = button().icon("arrow_forward").flipIconInRtl(true).filled().element();
        assertTrue("아이콘 버튼 flip-icon-in-rtl: filled 전환 후 유지", flipFilled.flipIconInRtl);

        // disabled 아이콘 버튼의 aria-label
        var disabledAria = button().icon("close").disabled(true).ariaLabel("닫기").element();
        assertTrue("disabled 아이콘 버튼 aria-label: disabled true", disabledAria.disabled);
        assertEquals("disabled 아이콘 버튼 aria-label: 닫기", "닫기", disabledAria.getAttribute("aria-label"));

        // 아이콘 버튼 onClick
        int[] iconClicks = {0};
        var clickIconBtn = button().icon("add").ariaLabel("추가").onClick(e -> iconClicks[0]++).element();
        toggleSection.appendChild(clickIconBtn);
        clickIconBtn.click();
        assertEquals("아이콘 버튼 onClick: 클릭 1회", 1, iconClicks[0]);
    }
}
