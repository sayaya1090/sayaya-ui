package dev.sayaya.ui.switchtest;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.SwitchElementBuilder.sw;
import static org.jboss.elemento.Elements.*;

public class SwitchBasicTest {
    public static void test() {
        printSectionHeader("1. 기본 사용법 (Basic Usage)");
        printDescription("Switch의 기본적인 사용 방법을 테스트합니다:");
        printDescription("- selected: 선택/비선택 상태");
        printDescription("- icons: 아이콘 표시");
        printDescription("- showOnlySelectedIcon: 선택된 아이콘만 표시");
        printSeparator();

        var basicSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(basicSection);

        basicSection.appendChild(h(3).text("Basic Usage").element());

        // Unselected Switch
        var unselectedExample = addExampleCode(basicSection,
            "📘 Unselected Switch (비선택 상태)",
            "기본 상태의 스위치입니다.",
            """
            var switchElem = sw()
                .ariaLabel("Unselected")
                .element();
            """);
        var unselectedSwitch = sw()
                .ariaLabel("Unselected")
                .element();
        var unselectedState = unselectedExample.addInteractiveDemo(unselectedSwitch);
        unselectedState.textContent = "selected: " + unselectedSwitch.selected;
        unselectedSwitch.addEventListener("change", evt -> {
            unselectedState.textContent = "selected: " + unselectedSwitch.selected;
        });

        assertFalse("초기 상태: 선택되지 않아야 함", unselectedSwitch.selected);

        // Selected Switch
        var selectedExample = addExampleCode(basicSection,
            "📘 Selected Switch (선택 상태)",
            "초기 선택 상태로 설정된 스위치입니다.",
            """
            var switchElem = sw()
                .select(true)
                .ariaLabel("Selected")
                .element();
            """);
        var selectedSwitch = sw()
                .select(true)
                .ariaLabel("Selected")
                .element();
        var selectedState = selectedExample.addInteractiveDemo(selectedSwitch);
        selectedState.textContent = "selected: " + selectedSwitch.selected;
        selectedSwitch.addEventListener("change", evt -> {
            selectedState.textContent = "selected: " + selectedSwitch.selected;
        });

        assertTrue("선택 상태: true여야 함", selectedSwitch.selected);

        // Switch with Icons
        var iconsExample = addExampleCode(basicSection,
            "📘 Switch with Icons (아이콘)",
            "선택/비선택 상태 모두 아이콘을 표시하는 스위치입니다.",
            """
            var switchElem = sw()
                .icons(true)
                .ariaLabel("Switch with icons")
                .element();
            """);
        var iconsSwitch = sw()
                .icons(true)
                .ariaLabel("Switch with icons")
                .element();
        var iconsState = iconsExample.addInteractiveDemo(iconsSwitch);
        iconsState.textContent = "icons: " + iconsSwitch.icons + " | selected: " + iconsSwitch.selected;
        iconsSwitch.addEventListener("change", evt -> {
            iconsState.textContent = "icons: " + iconsSwitch.icons + " | selected: " + iconsSwitch.selected;
        });

        assertTrue("icons: true여야 함", iconsSwitch.icons);

        // Switch with Only Selected Icon
        var selectedIconExample = addExampleCode(basicSection,
            "📘 Only Selected Icon (선택 아이콘만)",
            "선택된 상태일 때만 아이콘을 표시하는 스위치입니다.",
            """
            var switchElem = sw()
                .showOnlySelectedIcon(true)
                .select(true)
                .ariaLabel("Show only selected icon")
                .element();
            """);
        var selectedIconSwitch = sw()
                .showOnlySelectedIcon(true)
                .select(true)
                .ariaLabel("Show only selected icon")
                .element();
        var selectedIconState = selectedIconExample.addInteractiveDemo(selectedIconSwitch);
        selectedIconState.textContent = "showOnlySelectedIcon: " + selectedIconSwitch.showOnlySelectedIcon + 
            " | icons: " + selectedIconSwitch.icons + 
            " | selected: " + selectedIconSwitch.selected;
        selectedIconSwitch.addEventListener("change", evt -> {
            selectedIconState.textContent = "showOnlySelectedIcon: " + selectedIconSwitch.showOnlySelectedIcon + 
                " | icons: " + selectedIconSwitch.icons + 
                " | selected: " + selectedIconSwitch.selected;
        });

        assertTrue("showOnlySelectedIcon: true여야 함", selectedIconSwitch.showOnlySelectedIcon);
        assertTrue("icons: true여야 함 (자동 설정)", selectedIconSwitch.icons);
    }
}
