package dev.sayaya.ui;

import com.google.gwt.core.client.EntryPoint;

import static dev.sayaya.ui.elements.ButtonElementBuilder.button;
import static elemental2.dom.DomGlobal.console;
import static org.jboss.elemento.Elements.*;

public class ButtonElementTest implements EntryPoint {
    @Override
    public void onModuleLoad() {
        testButtonVariants();
        testButtonProperties();
        testFormProperties();
        testIconSupport();
        testEventHandling();
        testAccessibility();
        testUsageExamples();
        testButtonPrioritiesAndUseCases();
        testButtonClickBehavior();
        testDisabledButtonBehavior();
        testIconButtonVariants();
        testIconButtonProperties();
        testIconButtonToggle();
        testIconButtonWithLink();
        testIconButtonAccessibility();
    }

    private static void testButtonVariants() {
        // Elevated Button
        var elevated = button().elevated()
                .text("Elevated")
                .element();
        assertEquals("elevated 버튼: 태그명은 md-elevated-button이어야 함",
                "MD-ELEVATED-BUTTON", elevated.tagName);

        // Filled Button
        var filled = button().filled()
                .text("Filled")
                .element();
        assertEquals("filled 버튼: 태그명은 md-filled-button이어야 함",
                "MD-FILLED-BUTTON", filled.tagName);

        // Filled Tonal Button
        var filledTonal = button().filledTonal()
                .text("Filled Tonal")
                .element();
        assertEquals("filled-tonal 버튼: 태그명은 md-filled-tonal-button이어야 함",
                "MD-FILLED-TONAL-BUTTON", filledTonal.tagName);

        // Outlined Button
        var outlined = button().outlined()
                .text("Outlined")
                .element();
        assertEquals("outlined 버튼: 태그명은 md-outlined-button이어야 함",
                "MD-OUTLINED-BUTTON", outlined.tagName);

        // Text Button
        var text = button().text()
                .text("Text")
                .element();
        assertEquals("text 버튼: 태그명은 md-text-button이어야 함",
                "MD-TEXT-BUTTON", text.tagName);
    }

    private static void testButtonProperties() {
        // Disabled
        var disabledBtn = button().filled()
                .disabled(true)
                .text("Disabled")
                .element();
        assertTrue("disabled 속성: true여야 함", disabledBtn.disabled);

        // Soft Disabled
        var softDisabledBtn = button().filled()
                .softDisabled(true)
                .text("Soft Disabled")
                .element();
        assertTrue("softDisabled 속성: true여야 함", softDisabledBtn.softDisabled);

        // Href (Link Button)
        var linkBtn = button().text()
                .href("https://example.com")
                .text("Link")
                .element();
        assertEquals("href 속성: https://example.com이어야 함",
                "https://example.com", linkBtn.href);

        // Target
        var targetBtn = button().text()
                .href("https://example.com")
                .target("_blank")
                .text("New Tab")
                .element();
        assertEquals("target 속성: _blank여야 함", "_blank", targetBtn.target);

        // Download
        var downloadBtn = button().text()
                .href("/file.pdf")
                .download("document.pdf")
                .text("Download")
                .element();
        assertEquals("download 속성: document.pdf여야 함",
                "document.pdf", downloadBtn.download);
    }

    private static void testFormProperties() {
        var formElement = form().id("test-form").element();
        body().add(formElement);

        // Type
        var submitBtn = button().filled()
                .type("submit")
                .text("Submit")
                .element();
        assertEquals("type 속성: submit이어야 함", "submit", submitBtn.type);

        // Name
        var namedBtn = button().filled()
                .name("action")
                .text("Action")
                .element();
        assertEquals("name 속성: action이어야 함", "action", namedBtn.name);

        // Value
        var valueBtn = button().filled()
                .value("save")
                .text("Save")
                .element();
        assertEquals("value 속성: save여야 함", "save", valueBtn.value);

        // Form
        var formBtn = button().filled()
                .form(formElement)
                .text("Form Button")
                .element();
        body().add(formBtn);

        assertEquals("form 속성: 속성을 통해 폼 요소를 참조해야 함",
                "test-form", formBtn.getAttribute("form"));
    }

    private static void testIconSupport() {
        // Leading Icon
        var leadingBtn = button().filled()
                .icon("send")
                .text("Send")
                .element();
        body().add(leadingBtn);

        var leadingIcon = leadingBtn.querySelector("md-icon");
        assertNotNull("선행 아이콘: 아이콘 요소가 존재해야 함", leadingIcon);
        assertEquals("선행 아이콘: slot은 icon이어야 함",
                "icon", leadingIcon.getAttribute("slot"));
        assertFalse("선행 아이콘: trailingIcon은 false여야 함",
                leadingBtn.trailingIcon);

        // Trailing Icon
        var trailingBtn = button().filled()
                .text("Next")
                .icon("arrow_forward", true)
                .element();
        body().add(trailingBtn);

        var trailingIcon = trailingBtn.querySelector("md-icon");
        assertNotNull("후행 아이콘: 아이콘 요소가 존재해야 함", trailingIcon);
        assertEquals("후행 아이콘: slot은 icon이어야 함",
                "icon", trailingIcon.getAttribute("slot"));
        assertTrue("후행 아이콘: trailingIcon은 true여야 함",
                trailingBtn.trailingIcon);
    }

    private static void testEventHandling() {
        var clicked = new boolean[]{false};
        var clickBtn = button().filled()
                .text("Click Me")
                .onClick(evt -> clicked[0] = true)
                .element();

        // Simulate click
        clickBtn.click();

        assertTrue("onClick 이벤트: 발생해야 함", clicked[0]);
    }

    private static void testAccessibility() {
        var ariaBtn = button().filled()
                .text("Delete")
                .ariaLabel("Delete item from list")
                .element();

        assertEquals("aria-label: 올바르게 설정되어야 함",
                "Delete item from list",
                ariaBtn.getAttribute("aria-label"));
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

    private static void assertFalse(String message, boolean condition) {
        if (!condition) {
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
        // Example 1: Elevated button - visual separation from patterned background
        // <md-elevated-button>Elevated</md-elevated-button>
        var elevated = button().elevated()
                .text("Elevated")
                .element();
        body().add(elevated);
        assertEquals("사용 예제: elevated 버튼 태그는 MD-ELEVATED-BUTTON이어야 함",
                "MD-ELEVATED-BUTTON", elevated.tagName);

        // Example 2: Filled button - highest visual impact, important final actions
        // <md-filled-button>Filled</md-filled-button>
        var filled = button().filled()
                .text("Save Changes")
                .element();
        body().add(filled);
        assertEquals("사용 예제: filled 버튼 태그는 MD-FILLED-BUTTON이어야 함",
                "MD-FILLED-BUTTON", filled.tagName);

        // Example 3: Filled tonal button - middle ground between filled and outlined
        // <md-filled-tonal-button>Tonal</md-filled-tonal-button>
        var tonal = button().filledTonal()
                .text("Export")
                .element();
        body().add(tonal);
        assertEquals("사용 예제: tonal 버튼 태그는 MD-FILLED-TONAL-BUTTON이어야 함",
                "MD-FILLED-TONAL-BUTTON", tonal.tagName);

        // Example 4: Outlined button - medium emphasis, important but not primary
        // <md-outlined-button>Outlined</md-outlined-button>
        var outlined = button().outlined()
                .text("Cancel")
                .element();
        body().add(outlined);
        assertEquals("사용 예제: outlined 버튼 태그는 MD-OUTLINED-BUTTON이어야 함",
                "MD-OUTLINED-BUTTON", outlined.tagName);

        // Example 5: Text button - lowest priority, multiple options
        // <md-text-button>Text</md-text-button>
        var text = button().text()
                .text("Learn More")
                .element();
        body().add(text);
        assertEquals("사용 예제: text 버튼 태그는 MD-TEXT-BUTTON이어야 함",
                "MD-TEXT-BUTTON", text.tagName);

        // Example 6: Button with icon (from docs)
        // <md-filled-tonal-button>
        //   Send
        //   <svg slot="icon" viewBox="0 0 48 48">...</svg>
        // </md-filled-tonal-button>
        var iconButton = button().filledTonal()
                .text("Send")
                .icon("send")
                .element();
        body().add(iconButton);

        var icon = iconButton.querySelector("md-icon");
        assertNotNull("사용 예제: 아이콘이 버튼 안에 존재해야 함", icon);
        assertEquals("사용 예제: 아이콘 slot은 'icon'이어야 함",
                "icon", icon.getAttribute("slot"));
    }

    private static void testButtonPrioritiesAndUseCases() {
        // Scenario 1: Dialog actions - primary and secondary buttons
        // Primary action (filled), secondary action (outlined)
        var confirmDialog = div().element();
        body().add(confirmDialog);

        var primaryAction = button().filled()
                .text("Confirm")
                .element();
        var secondaryAction = button().outlined()
                .text("Cancel")
                .element();

        confirmDialog.appendChild(secondaryAction);
        confirmDialog.appendChild(primaryAction);

        assertEquals("버튼 우선순위: 주요 버튼은 filled여야 함",
                "MD-FILLED-BUTTON", primaryAction.tagName);
        assertEquals("버튼 우선순위: 보조 버튼은 outlined여야 함",
                "MD-OUTLINED-BUTTON", secondaryAction.tagName);

        // Scenario 2: Toolbar with soft-disabled button (remains keyboard focusable)
        var toolbar = div().element();
        body().add(toolbar);

        var toolbarButton = button().text()
                .text("Edit")
                .softDisabled(true)
                .ariaLabel("Edit is currently unavailable")
                .element();
        toolbar.appendChild(toolbarButton);

        assertTrue("버튼 우선순위: soft-disabled는 true여야 함", toolbarButton.softDisabled);
        assertFalse("버튼 우선순위: soft-disabled 버튼은 완전히 비활성화되지 않아야 함", toolbarButton.disabled);

        // Scenario 3: Link button for external navigation
        var linkButton = button().text()
                .text("Documentation")
                .href("https://material.io/components/buttons")
                .target("_blank")
                .element();
        body().add(linkButton);

        assertEquals("버튼 우선순위: href가 설정되어야 함",
                "https://material.io/components/buttons", linkButton.href);
        assertEquals("버튼 우선순위: target은 _blank여야 함",
                "_blank", linkButton.target);

        // Scenario 4: Form submit with filled button (important final action)
        var submitForm = form().id("user-form").element();
        body().add(submitForm);

        var submitButton = button().filled()
                .type("submit")
                .text("Create Account")
                .element();
        submitForm.appendChild(submitButton);

        assertEquals("버튼 우선순위: submit 타입은 'submit'이어야 함",
                "submit", submitButton.type);

        // Scenario 5: Multiple text buttons for low-priority options
        var optionsContainer = div().element();
        body().add(optionsContainer);

        var option1 = button().text().text("Option 1").element();
        var option2 = button().text().text("Option 2").element();
        var option3 = button().text().text("Option 3").element();

        optionsContainer.appendChild(option1);
        optionsContainer.appendChild(option2);
        optionsContainer.appendChild(option3);

        assertEquals("버튼 우선순위: 다중 옵션은 text 버튼이어야 함",
                "MD-TEXT-BUTTON", option1.tagName);
        assertEquals("버튼 우선순위: 다중 옵션은 text 버튼이어야 함",
                "MD-TEXT-BUTTON", option2.tagName);
        assertEquals("버튼 우선순위: 다중 옵션은 text 버튼이어야 함",
                "MD-TEXT-BUTTON", option3.tagName);

        // Scenario 6: Elevated button on patterned background
        var patternedBackground = div()
                .style("background-image", "url('pattern.png')")
                .element();
        body().add(patternedBackground);

        var elevatedOnPattern = button().elevated()
                .text("Get Started")
                .element();
        patternedBackground.appendChild(elevatedOnPattern);

        assertEquals("버튼 우선순위: 시각적 분리를 위한 elevated",
                "MD-ELEVATED-BUTTON", elevatedOnPattern.tagName);

        // Scenario 7: Icon button positions (leading vs trailing)
        var sendButton = button().filled()
                .icon("send")
                .text("Send Message")
                .element();
        body().add(sendButton);

        var nextButton = button().filled()
                .text("Next Step")
                .icon("arrow_forward", true)
                .element();
        body().add(nextButton);

        assertFalse("버튼 우선순위: send 아이콘은 선행이어야 함", sendButton.trailingIcon);
        assertTrue("버튼 우선순위: next 아이콘은 후행이어야 함", nextButton.trailingIcon);

        // Scenario 8: Download button with link
        var downloadButton = button().outlined()
                .text("Download Report")
                .icon("download")
                .href("/reports/annual.pdf")
                .download("annual_report.pdf")
                .element();
        body().add(downloadButton);

        assertEquals("버튼 우선순위: download 속성이 설정되어야 함",
                "annual_report.pdf", downloadButton.download);
        assertNotNull("버튼 우선순위: download 아이콘이 존재해야 함",
                downloadButton.querySelector("md-icon"));
    }

    private static void testButtonClickBehavior() {
        // 테스트 1: 버튼 클릭 시 이벤트 핸들러 실행 확인
        var clickCount = new int[]{0};
        var button1 = button().filled()
                .text("클릭 테스트")
                .onClick(evt -> clickCount[0]++)
                .element();
        body().add(button1);

        assertEquals("버튼 클릭: 초기 클릭 카운트는 0", 0, clickCount[0]);

        button1.click();
        assertEquals("버튼 클릭: 첫 번째 클릭 후 카운트 1", 1, clickCount[0]);

        button1.click();
        assertEquals("버튼 클릭: 두 번째 클릭 후 카운트 2", 2, clickCount[0]);

        button1.click();
        assertEquals("버튼 클릭: 세 번째 클릭 후 카운트 3", 3, clickCount[0]);

        // 테스트 2: 여러 이벤트 핸들러 동시 실행
        var handler1Triggered = new boolean[]{false};
        var handler2Triggered = new boolean[]{false};
        var button2 = button().outlined()
                .text("다중 핸들러 테스트")
                .onClick(evt -> handler1Triggered[0] = true)
                .onClick(evt -> handler2Triggered[0] = true)
                .element();
        body().add(button2);

        button2.click();
        assertTrue("버튼 클릭: 첫 번째 핸들러 실행됨", handler1Triggered[0]);
        assertTrue("버튼 클릭: 두 번째 핸들러 실행됨", handler2Triggered[0]);

        // 테스트 3: 링크 버튼 클릭 동작 (href가 있는 경우)
        var linkButton = button().text()
                .text("링크 버튼")
                .href("#test")
                .onClick(evt -> {
                    console.log("링크 버튼이 클릭되었습니다");
                })
                .element();
        body().add(linkButton);

        linkButton.click();
        assertEquals("버튼 클릭: 링크 버튼의 href 속성 확인", "#test", linkButton.href);

        // 테스트 4: 폼 제출 버튼 동작
        var testForm = form().id("click-test-form").element();
        body().add(testForm);

        var submitClicked = new boolean[]{false};
        var submitButton = button().filled()
                .type("submit")
                .text("제출")
                .onClick(evt -> {
                    submitClicked[0] = true;
                    evt.preventDefault(); // 실제 제출 방지
                })
                .element();
        testForm.appendChild(submitButton);

        submitButton.click();
        assertTrue("버튼 클릭: 제출 버튼 클릭 이벤트 발생", submitClicked[0]);
        assertEquals("버튼 클릭: 제출 버튼 타입 확인", "submit", submitButton.type);
    }

    private static void testDisabledButtonBehavior() {
        // 테스트 1: disabled 버튼은 클릭해도 이벤트가 발생하지 않음
        var disabledClickCount = new int[]{0};
        var disabledButton = button().filled()
                .text("비활성화된 버튼")
                .disabled(true)
                .onClick(evt -> disabledClickCount[0]++)
                .element();
        body().add(disabledButton);

        assertTrue("disabled 버튼: disabled 속성이 true", disabledButton.disabled);

        disabledButton.click();
        assertEquals("disabled 버튼: 클릭해도 이벤트 발생 안함", 0, disabledClickCount[0]);

        // 테스트 2: soft-disabled 버튼은 클릭 이벤트가 발생함 (키보드 포커스 가능)
        var softDisabledClickCount = new int[]{0};
        var softDisabledButton = button().filled()
                .text("Soft 비활성화 버튼")
                .softDisabled(true)
                .onClick(evt -> softDisabledClickCount[0]++)
                .element();
        body().add(softDisabledButton);

        assertTrue("soft-disabled 버튼: softDisabled 속성이 true", softDisabledButton.softDisabled);
        assertFalse("soft-disabled 버튼: disabled 속성은 false", softDisabledButton.disabled);

        softDisabledButton.click();
        assertEquals("soft-disabled 버튼: 클릭 시 이벤트 발생함", 1, softDisabledClickCount[0]);

        // 테스트 3: disabled 상태 동적 변경 테스트
        var dynamicButton = button().outlined()
                .text("동적 비활성화 테스트")
                .element();
        body().add(dynamicButton);

        var dynamicClickCount = new int[]{0};
        dynamicButton.onclick = evt -> dynamicClickCount[0]++;

        // 초기에는 활성화 상태
        assertFalse("동적 변경: 초기에는 활성화", dynamicButton.disabled);

        dynamicButton.click();
        assertEquals("동적 변경: 활성화 상태에서 클릭 가능", 1, dynamicClickCount[0]);

        // 비활성화로 변경
        dynamicButton.disabled = true;
        assertTrue("동적 변경: disabled로 변경됨", dynamicButton.disabled);

        dynamicButton.click();
        assertEquals("동적 변경: 비활성화 후 클릭해도 카운트 증가 안함", 1, dynamicClickCount[0]);

        // 다시 활성화
        dynamicButton.disabled = false;
        assertFalse("동적 변경: 다시 활성화됨", dynamicButton.disabled);

        dynamicButton.click();
        assertEquals("동적 변경: 재활성화 후 클릭 가능", 2, dynamicClickCount[0]);

        // 테스트 4: disabled와 soft-disabled 비교
        var normalDisabled = button().text()
                .text("Normal Disabled")
                .disabled(true)
                .element();
        var softDisabled = button().text()
                .text("Soft Disabled")
                .softDisabled(true)
                .element();

        body().add(normalDisabled);
        body().add(softDisabled);

        assertTrue("비교: normal disabled는 disabled 속성 true", normalDisabled.disabled);
        assertFalse("비교: normal disabled는 softDisabled 속성 false", normalDisabled.softDisabled);

        assertFalse("비교: soft disabled는 disabled 속성 false", softDisabled.disabled);
        assertTrue("비교: soft disabled는 softDisabled 속성 true", softDisabled.softDisabled);
    }

    private static void assertEquals(String message, int expected, int actual) {
        if (expected == actual) {
            log(message + " - PASS");
        } else {
            log(message + " - Assertion failed! Expected: " + expected + ", Actual: " + actual);
        }
    }

    private static void testIconButtonVariants() {
        // Standard Icon Button
        var standard = button().icon()
                .add("settings")
                .element();
        assertEquals("standard 아이콘 버튼: 태그명은 md-icon-button이어야 함",
                "MD-ICON-BUTTON", standard.tagName);

        // Filled Icon Button
        var filled = button().icon().filled()
                .add("favorite")
                .element();
        assertEquals("filled 아이콘 버튼: 태그명은 md-filled-icon-button이어야 함",
                "MD-FILLED-ICON-BUTTON", filled.tagName);

        // Filled Tonal Icon Button
        var filledTonal = button().icon().filledTonal()
                .add("edit")
                .element();
        assertEquals("filled-tonal 아이콘 버튼: 태그명은 md-filled-tonal-icon-button이어야 함",
                "MD-FILLED-TONAL-ICON-BUTTON", filledTonal.tagName);

        // Outlined Icon Button
        var outlined = button().icon().outlined()
                .add("delete")
                .element();
        assertEquals("outlined 아이콘 버튼: 태그명은 md-outlined-icon-button이어야 함",
                "MD-OUTLINED-ICON-BUTTON", outlined.tagName);
    }

    private static void testIconButtonProperties() {
        // Icon Button with String Icon
        var iconBtn = button().icon()
                .icon("search")
                .element();
        body().add(iconBtn);

        var icon = iconBtn.querySelector("md-icon");
        assertNotNull("아이콘 버튼: 아이콘이 존재해야 함", icon);

        // Disabled Icon Button
        var disabledBtn = button().icon()
                .icon("close")
                .disabled(true)
                .element();
        body().add(disabledBtn);

        assertTrue("아이콘 버튼 disabled: true여야 함", disabledBtn.disabled);

        // Flip Icon in RTL
        var rtlBtn = button().icon()
                .icon("arrow_forward")
                .attr("flip-icon-in-rtl", "true")
                .element();
        body().add(rtlBtn);

        assertEquals("아이콘 버튼 flip-icon-in-rtl: true여야 함",
                "true", rtlBtn.getAttribute("flip-icon-in-rtl"));
    }

    private static void testIconButtonToggle() {
        // Toggle Icon Button with String Icons
        var toggleBtn1 = button().icon()
                .toggle(true)
                .add("visibility")
                .toggle("visibility_off", false)
                .element();
        body().add(toggleBtn1);

        assertTrue("토글 아이콘 버튼: toggle이 true여야 함", toggleBtn1.toggle);
        assertFalse("토글 아이콘 버튼: 초기 selected는 false여야 함", toggleBtn1.selected);

        // Toggle Icon Button with Pre-selected State
        var toggleBtn2 = button().icon()
                .toggle(true)
                .add("favorite_border")
                .toggle("favorite", true)
                .element();
        body().add(toggleBtn2);

        assertTrue("토글 아이콘 버튼: toggle이 true여야 함", toggleBtn2.toggle);
        assertTrue("토글 아이콘 버튼: selected가 true여야 함", toggleBtn2.selected);

        var selectedIcon = toggleBtn2.querySelector("[slot='selected']");
        assertNotNull("토글 아이콘 버튼: selected 아이콘이 존재해야 함", selectedIcon);

        // Aria Label for Toggle States
        var toggleBtn3 = button().icon()
                .toggle(true)
                .add("bookmark_border")
                .toggle("bookmark")
                .ariaLabel("Bookmark")
                .ariaLabelSelected("Remove bookmark")
                .element();
        body().add(toggleBtn3);

        assertEquals("토글 아이콘 버튼 aria-label: Bookmark여야 함",
                "Bookmark", toggleBtn3.getAttribute("aria-label"));
        assertEquals("토글 아이콘 버튼 aria-label-selected: Remove bookmark여야 함",
                "Remove bookmark", toggleBtn3.getAttribute("aria-label-selected"));
    }

    private static void testIconButtonWithLink() {
        // Icon Button as Link
        var linkBtn = button().icon()
                .add("open_in_new")
                .href("https://example.com")
                .element();
        body().add(linkBtn);

        assertEquals("아이콘 버튼 href: https://example.com이어야 함",
                "https://example.com", linkBtn.href);

        // Icon Button Link with Target
        var targetBtn = button().icon().filled()
                .add("link")
                .href("https://example.com")
                .target("_blank")
                .element();
        body().add(targetBtn);

        assertEquals("아이콘 버튼 target: _blank여야 함",
                "_blank", targetBtn.target);
    }

    private static void testIconButtonAccessibility() {
        // Icon Button with Aria Label
        var ariaBtn = button().icon()
                .add("info")
                .ariaLabel("More information")
                .element();
        body().add(ariaBtn);

        assertEquals("아이콘 버튼 aria-label: More information이어야 함",
                "More information", ariaBtn.getAttribute("aria-label"));

        // Disabled Icon Button with Aria Label
        var disabledAriaBtn = button().icon().outlined()
                .add("edit")
                .disabled(true)
                .ariaLabel("Edit (disabled)")
                .element();
        body().add(disabledAriaBtn);

        assertTrue("disabled 아이콘 버튼: disabled가 true", disabledAriaBtn.disabled);
        assertEquals("disabled 아이콘 버튼 aria-label: Edit (disabled)여야 함",
                "Edit (disabled)", disabledAriaBtn.getAttribute("aria-label"));

        // Toggle Button Accessibility
        var toggleAccessBtn = button().icon().filledTonal()
                .toggle(true)
                .add("notifications_off")
                .toggle("notifications_active")
                .ariaLabel("Enable notifications")
                .ariaLabelSelected("Disable notifications")
                .element();
        body().add(toggleAccessBtn);

        assertEquals("토글 접근성: aria-label-selected가 설정되어야 함",
                "Disable notifications", toggleAccessBtn.getAttribute("aria-label-selected"));

        // Click Event on Icon Button
        var clicked = new boolean[]{false};
        var clickBtn = button().icon()
                .add("thumb_up")
                .onClick(evt -> clicked[0] = true)
                .element();
        body().add(clickBtn);

        clickBtn.click();
        assertTrue("아이콘 버튼 onClick: 이벤트가 발생해야 함", clicked[0]);
    }

    private static void log(String message) {
        console.log(message);
    }
}
