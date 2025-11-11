package dev.sayaya.ui.icon;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.IconElementBuilder.icon;
import static org.jboss.elemento.Elements.*;

public class IconExamplesTest {
    public static void test() {
        printSectionHeader("4. 사용 예제 (Usage Examples)");
        printDescription("다양한 시나리오에서의 아이콘 사용 예제입니다.");
        printSeparator();

        var examplesSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(examplesSection);

        examplesSection.appendChild(h(3).text("Usage Examples").element());

        // Common icons
        var commonExample = addExampleCode(examplesSection,
            "📘 Common Icons",
            "자주 사용되는 Material Symbols 아이콘들입니다.",
            """
            var homeIcon = icon("home").element();
            var searchIcon = icon("search").element();
            var menuIcon = icon("menu").element();
            var closeIcon = icon("close").element();
            """);
        var commonContainer = div().element();
        var homeIcon = icon("home").element();
        var searchIcon = icon("search").element();
        var menuIcon = icon("menu").element();
        var closeIcon = icon("close").element();
        commonContainer.appendChild(homeIcon);
        commonContainer.appendChild(searchIcon);
        commonContainer.appendChild(menuIcon);
        commonContainer.appendChild(closeIcon);
        commonExample.addInteractiveDemo(commonContainer, false);

        assertEquals("사용 예제: home 아이콘", "home", homeIcon.textContent);
        assertEquals("사용 예제: search 아이콘", "search", searchIcon.textContent);
        assertEquals("사용 예제: menu 아이콘", "menu", menuIcon.textContent);
        assertEquals("사용 예제: close 아이콘", "close", closeIcon.textContent);

        // Icon in slot
        var slotExample = addExampleCode(examplesSection,
            "📘 Icon in Slot",
            "버튼이나 다른 컴포넌트의 slot에 사용되는 아이콘입니다.",
            """
            var icon = icon("send")
                .attr("slot", "icon")
                .element();
            """);
        var iconElement = icon("send")
                .attr("slot", "icon")
                .element();
        slotExample.addInteractiveDemo(iconElement, false);

        assertEquals("컨텍스트 내 아이콘: slot은 'icon'",
                "icon", iconElement.getAttribute("slot"));
        assertEquals("컨텍스트 내 아이콘: 아이콘 이름은 'send'",
                "send", iconElement.textContent);

        // Navigation icons
        var navExample = addExampleCode(examplesSection,
            "📘 Navigation Icons",
            "네비게이션에 사용되는 아이콘들입니다.",
            """
            var homeNav = icon("home").attr("aria-label", "Home").element();
            var profileNav = icon("person").attr("aria-label", "Profile").element();
            var settingsNav = icon("settings").attr("aria-label", "Settings").element();
            """);
        var navContainer = div().element();
        var homeNav = icon("home").attr("aria-label", "Home").element();
        var profileNav = icon("person").attr("aria-label", "Profile").element();
        var settingsNav = icon("settings").attr("aria-label", "Settings").element();
        navContainer.appendChild(homeNav);
        navContainer.appendChild(profileNav);
        navContainer.appendChild(settingsNav);
        navExample.addInteractiveDemo(navContainer, false);

        assertEquals("컨텍스트 내 아이콘: 네비게이션 아이콘은 aria-label을 가짐",
                "Home", homeNav.getAttribute("aria-label"));
        assertEquals("컨텍스트 내 아이콘: 네비게이션 아이콘은 aria-label을 가짐",
                "Profile", profileNav.getAttribute("aria-label"));
        assertEquals("컨텍스트 내 아이콘: 네비게이션 아이콘은 aria-label을 가짐",
                "Settings", settingsNav.getAttribute("aria-label"));

        // Status icons
        var statusExample = addExampleCode(examplesSection,
            "📘 Status Icons",
            "상태를 표시하는 아이콘들입니다.",
            """
            var successIcon = icon("check_circle").style("color", "green").attr("aria-label", "Success").element();
            var errorIcon = icon("error").style("color", "red").attr("aria-label", "Error").element();
            var warningIcon = icon("warning").style("color", "orange").attr("aria-label", "Warning").element();
            var infoIcon = icon("info").style("color", "blue").attr("aria-label", "Information").element();
            """);
        var statusContainer = div().element();
        var successIcon = icon("check_circle")
                .style("color", "green")
                .attr("aria-label", "Success")
                .element();
        var errorIcon = icon("error")
                .style("color", "red")
                .attr("aria-label", "Error")
                .element();
        var warningIcon = icon("warning")
                .style("color", "orange")
                .attr("aria-label", "Warning")
                .element();
        var infoIcon = icon("info")
                .style("color", "blue")
                .attr("aria-label", "Information")
                .element();
        statusContainer.appendChild(successIcon);
        statusContainer.appendChild(errorIcon);
        statusContainer.appendChild(warningIcon);
        statusContainer.appendChild(infoIcon);
        statusExample.addInteractiveDemo(statusContainer, false);

        assertEquals("컨텍스트 내 아이콘: success 아이콘", "check_circle", successIcon.textContent);
        assertEquals("컨텍스트 내 아이콘: error 아이콘", "error", errorIcon.textContent);
        assertEquals("컨텍스트 내 아이콘: warning 아이콘", "warning", warningIcon.textContent);
        assertEquals("컨텍스트 내 아이콘: info 아이콘", "info", infoIcon.textContent);

        // Action icons
        var actionExample = addExampleCode(examplesSection,
            "📘 Action Icons",
            "액션을 나타내는 아이콘들입니다.",
            """
            var editIcon = icon("edit").attr("aria-label", "Edit").element();
            var deleteIcon = icon("delete").attr("aria-label", "Delete").element();
            var shareIcon = icon("share").attr("aria-label", "Share").element();
            var downloadIcon = icon("download").attr("aria-label", "Download").element();
            """);
        var actionContainer = div().element();
        var editIcon = icon("edit").attr("aria-label", "Edit").element();
        var deleteIcon = icon("delete").attr("aria-label", "Delete").element();
        var shareIcon = icon("share").attr("aria-label", "Share").element();
        var downloadIcon = icon("download").attr("aria-label", "Download").element();
        actionContainer.appendChild(editIcon);
        actionContainer.appendChild(deleteIcon);
        actionContainer.appendChild(shareIcon);
        actionContainer.appendChild(downloadIcon);
        actionExample.addInteractiveDemo(actionContainer, false);

        assertNotNull("컨텍스트 내 아이콘: 액션 아이콘이 존재", editIcon);
        assertNotNull("컨텍스트 내 아이콘: 액션 아이콘이 존재", deleteIcon);
        assertNotNull("컨텍스트 내 아이콘: 액션 아이콘이 존재", shareIcon);
        assertNotNull("컨텍스트 내 아이콘: 액션 아이콘이 존재", downloadIcon);

        // Directional icons
        var directionExample = addExampleCode(examplesSection,
            "📘 Directional Icons",
            "방향을 나타내는 아이콘들입니다.",
            """
            var arrowUp = icon("arrow_upward").element();
            var arrowDown = icon("arrow_downward").element();
            var arrowLeft = icon("arrow_back").element();
            var arrowRight = icon("arrow_forward").element();
            """);
        var directionContainer = div().element();
        var arrowUp = icon("arrow_upward").element();
        var arrowDown = icon("arrow_downward").element();
        var arrowLeft = icon("arrow_back").element();
        var arrowRight = icon("arrow_forward").element();
        directionContainer.appendChild(arrowUp);
        directionContainer.appendChild(arrowDown);
        directionContainer.appendChild(arrowLeft);
        directionContainer.appendChild(arrowRight);
        directionExample.addInteractiveDemo(directionContainer, false);

        assertEquals("컨텍스트 내 아이콘: 방향 아이콘", "arrow_upward", arrowUp.textContent);
        assertEquals("컨텍스트 내 아이콘: 방향 아이콘", "arrow_downward", arrowDown.textContent);
        assertEquals("컨텍스트 내 아이콘: 방향 아이콘", "arrow_back", arrowLeft.textContent);
        assertEquals("컨텍스트 내 아이콘: 방향 아이콘", "arrow_forward", arrowRight.textContent);
    }
}
