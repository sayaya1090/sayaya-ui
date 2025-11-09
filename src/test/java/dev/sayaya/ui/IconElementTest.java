package dev.sayaya.ui;

import com.google.gwt.core.client.EntryPoint;

import static dev.sayaya.ui.elements.IconElementBuilder.icon;
import static elemental2.dom.DomGlobal.console;
import static org.jboss.elemento.Elements.*;

public class IconElementTest implements EntryPoint {
    @Override
    public void onModuleLoad() {
        testIconCreation();
        testIconWithString();
        testAccessibility();
        testStyling();
        testUsageExamples();
        testIconStyles();
        testIconInContext();
    }

    private static void testIconCreation() {
        // Empty icon
        var emptyIcon = icon().element();
        assertEquals("아이콘 생성: 태그명은 md-icon이어야 함",
                "MD-ICON", emptyIcon.tagName);
    }

    private static void testIconWithString() {
        // Icon with name
        var namedIcon = icon("settings").element();
        assertEquals("이름이 있는 아이콘: 태그명은 md-icon이어야 함",
                "MD-ICON", namedIcon.tagName);
        assertEquals("이름이 있는 아이콘: textContent는 'settings'여야 함",
                "settings", namedIcon.textContent);

        // Icon with unicode
        var unicodeIcon = icon("&#xe834").element();
        assertEquals("유니코드 아이콘: 태그명은 md-icon이어야 함",
                "MD-ICON", unicodeIcon.tagName);
        assertNotNull("유니코드 아이콘: textContent는 null이 아니어야 함",
                unicodeIcon.textContent);
    }

    private static void testAccessibility() {
        // Icon with aria-label
        var accessibleIcon = icon("home")
                .attr("aria-label", "Go to home")
                .element();
        assertEquals("접근성: aria-label이 설정되어야 함",
                "Go to home", accessibleIcon.getAttribute("aria-label"));
    }

    private static void testStyling() {
        // Icon with custom style
        var styledIcon = icon("star")
                .style("--md-icon-size", "48px")
                .style("color", "gold")
                .element();

        var style = styledIcon.style;
        assertNotNull("스타일링: style은 null이 아니어야 함", style);
        assertTrue("스타일링: color 속성을 가져야 함",
                style.color.length() > 0 || style.cssText.contains("color"));
    }

    private static void assertEquals(String message, Object expected, Object actual) {
        if (expected == null && actual == null) {
            log(message + " - PASS");
            return;
        }
        if (expected != null && expected.equals(actual)) {
            log(message + " - PASS");
        } else {
            log(message + " - Assertion failed! Expected: " + expected + ", Actual: " + actual);
        }
    }

    private static void assertTrue(String message, boolean condition) {
        if (condition) {
            log(message + " - PASS");
        } else {
            log(message + " - Assertion failed!");
        }
    }

    private static void assertNotNull(String message, Object obj) {
        if (obj != null) {
            log(message + " - PASS");
        } else {
            log(message + " - Assertion failed! Object is null");
        }
    }

    private static void testUsageExamples() {
        // Example 1: Icon by name (from docs)
        // <md-icon>settings</md-icon>
        var settingsIcon = icon("settings").element();
        body().add(settingsIcon);
        assertEquals("사용 예제: 아이콘 이름은 'settings'여야 함",
                "settings", settingsIcon.textContent);
        assertEquals("사용 예제: 태그는 MD-ICON이어야 함",
                "MD-ICON", settingsIcon.tagName);

        // Example 2: Icon by unicode codepoint (from docs)
        // <md-icon>&#xe834</md-icon>
        var codepointIcon = icon("&#xe834").element();
        body().add(codepointIcon);
        assertNotNull("사용 예제: 코드포인트 아이콘은 내용을 가져야 함",
                codepointIcon.textContent);

        // Example 3: Accessible icon with aria-label (from docs)
        // <md-icon tabindex="-1">
        //   <span aria-label="home">&#xe88a</span>
        // </md-icon>
        var accessibleCodepointIcon = icon()
                .attr("tabindex", "-1")
                .add(span()
                        .attr("aria-label", "home")
                        .text("&#xe88a"))
                .element();
        body().add(accessibleCodepointIcon);

        var spanElement = accessibleCodepointIcon.querySelector("span");
        assertNotNull("사용 예제: span이 아이콘 안에 존재해야 함", spanElement);
        assertEquals("사용 예제: span aria-label은 'home'이어야 함",
                "home", spanElement.getAttribute("aria-label"));
        assertEquals("사용 예제: tabindex는 -1이어야 함",
                "-1", accessibleCodepointIcon.getAttribute("tabindex"));

        // Example 4: Common Material Symbols icons
        var homeIcon = icon("home").element();
        var searchIcon = icon("search").element();
        var menuIcon = icon("menu").element();
        var closeIcon = icon("close").element();

        body().add(homeIcon);
        body().add(searchIcon);
        body().add(menuIcon);
        body().add(closeIcon);

        assertEquals("사용 예제: home 아이콘", "home", homeIcon.textContent);
        assertEquals("사용 예제: search 아이콘", "search", searchIcon.textContent);
        assertEquals("사용 예제: menu 아이콘", "menu", menuIcon.textContent);
        assertEquals("사용 예제: close 아이콘", "close", closeIcon.textContent);
    }

    private static void testIconStyles() {
        // Test different icon styles: outlined (default), rounded, sharp
        // Note: Style is controlled by font-family in Material Symbols

        // Default (outlined) style
        var outlinedIcon = icon("favorite").element();
        body().add(outlinedIcon);
        assertEquals("아이콘 스타일: outlined 아이콘 이름", "favorite", outlinedIcon.textContent);

        // Custom size using CSS variable
        // --md-icon-size: Adjust icon size
        var largeIcon = icon("star")
                .style("--md-icon-size", "48px")
                .element();
        body().add(largeIcon);

        var largeStyle = largeIcon.style;
        assertTrue("아이콘 스타일: 큰 아이콘은 크기 스타일을 가져야 함",
                largeStyle.cssText.contains("--md-icon-size") || largeStyle.cssText.contains("48px"));

        // Icon with fill variation
        // fill ranges from 0 (unfilled) to 1 (completely filled)
        var filledIcon = icon("bookmark")
                .style("font-variation-settings", "'FILL' 1")
                .element();
        body().add(filledIcon);

        var fillStyle = filledIcon.style;
        assertTrue("아이콘 스타일: 채워진 아이콘은 fill 변형을 가져야 함",
                fillStyle.cssText.contains("font-variation-settings") || fillStyle.cssText.contains("FILL"));

        // Icon with custom color
        var coloredIcon = icon("palette")
                .style("color", "#1976d2")
                .element();
        body().add(coloredIcon);

        var colorStyle = coloredIcon.style;
        assertNotNull("아이콘 스타일: 색상 아이콘 스타일은 null이 아니어야 함", colorStyle);

        // Icon with weight variation
        var boldIcon = icon("info")
                .style("font-variation-settings", "'wght' 700")
                .element();
        body().add(boldIcon);

        assertTrue("아이콘 스타일: 굵은 아이콘은 가중치 변형을 가져야 함",
                boldIcon.style.cssText.length() > 0);
    }

    private static void testIconInContext() {
        // Scenario 1: Icon in button (already tested in ButtonElementTest, but verify standalone)
        var iconInButton = div().element();
        body().add(iconInButton);

        var iconElement = icon("send")
                .attr("slot", "icon")
                .element();
        iconInButton.appendChild(iconElement);

        assertEquals("컨텍스트 내 아이콘: slot은 'icon'이어야 함",
                "icon", iconElement.getAttribute("slot"));
        assertEquals("컨텍스트 내 아이콘: 아이콘 이름은 'send'여야 함",
                "send", iconElement.textContent);

        // Scenario 2: Navigation icons
        var navBar = div().element();
        body().add(navBar);

        var homeNav = icon("home").attr("aria-label", "Home").element();
        var profileNav = icon("person").attr("aria-label", "Profile").element();
        var settingsNav = icon("settings").attr("aria-label", "Settings").element();

        navBar.appendChild(homeNav);
        navBar.appendChild(profileNav);
        navBar.appendChild(settingsNav);

        assertEquals("컨텍스트 내 아이콘: 네비게이션 아이콘은 aria-label을 가져야 함",
                "Home", homeNav.getAttribute("aria-label"));
        assertEquals("컨텍스트 내 아이콘: 네비게이션 아이콘은 aria-label을 가져야 함",
                "Profile", profileNav.getAttribute("aria-label"));
        assertEquals("컨텍스트 내 아이콘: 네비게이션 아이콘은 aria-label을 가져야 함",
                "Settings", settingsNav.getAttribute("aria-label"));

        // Scenario 3: Status icons
        var statusContainer = div().element();
        body().add(statusContainer);

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

        assertEquals("컨텍스트 내 아이콘: success 아이콘", "check_circle", successIcon.textContent);
        assertEquals("컨텍스트 내 아이콘: error 아이콘", "error", errorIcon.textContent);
        assertEquals("컨텍스트 내 아이콘: warning 아이콘", "warning", warningIcon.textContent);
        assertEquals("컨텍스트 내 아이콘: info 아이콘", "info", infoIcon.textContent);

        // Scenario 4: Action icons
        var actionBar = div().element();
        body().add(actionBar);

        var editIcon = icon("edit").attr("aria-label", "Edit").element();
        var deleteIcon = icon("delete").attr("aria-label", "Delete").element();
        var shareIcon = icon("share").attr("aria-label", "Share").element();
        var downloadIcon = icon("download").attr("aria-label", "Download").element();

        actionBar.appendChild(editIcon);
        actionBar.appendChild(deleteIcon);
        actionBar.appendChild(shareIcon);
        actionBar.appendChild(downloadIcon);

        assertNotNull("컨텍스트 내 아이콘: 액션 아이콘이 존재해야 함", editIcon);
        assertNotNull("컨텍스트 내 아이콘: 액션 아이콘이 존재해야 함", deleteIcon);
        assertNotNull("컨텍스트 내 아이콘: 액션 아이콘이 존재해야 함", shareIcon);
        assertNotNull("컨텍스트 내 아이콘: 액션 아이콘이 존재해야 함", downloadIcon);

        // Scenario 5: Directional icons
        var directionContainer = div().element();
        body().add(directionContainer);

        var arrowUp = icon("arrow_upward").element();
        var arrowDown = icon("arrow_downward").element();
        var arrowLeft = icon("arrow_back").element();
        var arrowRight = icon("arrow_forward").element();

        directionContainer.appendChild(arrowUp);
        directionContainer.appendChild(arrowDown);
        directionContainer.appendChild(arrowLeft);
        directionContainer.appendChild(arrowRight);

        assertEquals("컨텍스트 내 아이콘: 방향 아이콘", "arrow_upward", arrowUp.textContent);
        assertEquals("컨텍스트 내 아이콘: 방향 아이콘", "arrow_downward", arrowDown.textContent);
        assertEquals("컨텍스트 내 아이콘: 방향 아이콘", "arrow_back", arrowLeft.textContent);
        assertEquals("컨텍스트 내 아이콘: 방향 아이콘", "arrow_forward", arrowRight.textContent);
    }

    private static void log(String message) {
        console.log(message);
    }
}
