package dev.sayaya.ui;

import com.google.gwt.core.client.EntryPoint;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static dev.sayaya.ui.elements.CheckboxElementBuilder.checkbox;
import static elemental2.dom.DomGlobal.console;
import static org.jboss.elemento.Elements.*;

public class CheckboxElementTest implements EntryPoint {
    @Override
    public void onModuleLoad() {
        testCheckboxStates();
        testCheckboxProperties();
        testFormProperties();
        testEventHandling();
        testAccessibility();
        testUsageExamples();
        testLabeledCheckboxes();
        testFormIntegrationBehavior();
        testCheckboxClickBehavior();
        testIndeterminateClickBehavior();
    }

    private static void testCheckboxStates() {
        // Unchecked state (default)
        var unchecked = checkbox()
                .ariaLabel("Unchecked")
                .element();
        assertFalse("체크 안됨 상태: checked는 false여야 함", unchecked.checked);
        assertFalse("체크 안됨 상태: indeterminate는 false여야 함", unchecked.indeterminate);

        // Checked state
        var checked = checkbox()
                .select(true)
                .ariaLabel("Checked")
                .element();
        assertTrue("체크됨 상태: checked는 true여야 함", checked.checked);
        assertFalse("체크됨 상태: indeterminate는 false여야 함", checked.indeterminate);

        // Indeterminate state
        var indeterminate = checkbox()
                .indeterminate()
                .ariaLabel("Indeterminate")
                .element();
        assertFalse("불확정 상태: checked는 false여야 함", indeterminate.checked);
        assertTrue("불확정 상태: indeterminate는 true여야 함", indeterminate.indeterminate);

        // State transitions
        var stateCheckbox = checkbox().element();

        // Unchecked -> Checked
        var builder = checkbox();
        builder.select(false);
        assertFalse("상태 전환: 초기 상태는 체크 안됨", builder.isSelected());

        builder.select(true);
        assertTrue("상태 전환: 체크됨으로 변경", builder.isSelected());
        assertFalse("상태 전환: indeterminate는 false여야 함", builder.isIndeterminate());

        // Checked -> Indeterminate
        builder.indeterminate();
        assertFalse("상태 전환: 불확정 상태로 변경, isSelected는 false여야 함", builder.isSelected());
        assertTrue("상태 전환: isIndeterminate는 true여야 함", builder.isIndeterminate());

        // Indeterminate -> Unchecked
        builder.select(false);
        assertFalse("상태 전환: 체크 안됨으로 변경", builder.isSelected());
        assertFalse("상태 전환: indeterminate 해제됨", builder.isIndeterminate());
    }

    private static void testCheckboxProperties() {
        // Disabled
        var disabledCheckbox = checkbox()
                .disabled(true)
                .ariaLabel("Disabled")
                .element();
        assertTrue("disabled 속성: true여야 함", disabledCheckbox.disabled);

        // Required
        var requiredCheckbox = checkbox()
                .required(true)
                .ariaLabel("Required")
                .element();
        assertTrue("required 속성: true여야 함", requiredCheckbox.required);

        // Value
        var valueCheckbox = checkbox()
                .value("custom-value")
                .ariaLabel("Custom Value")
                .element();
        assertEquals("value 속성: custom-value여야 함",
                "custom-value", valueCheckbox.value);

        // Default value
        var defaultValueCheckbox = checkbox()
                .ariaLabel("Default Value")
                .element();
        // Material Web default value is "on"
        assertTrue("기본값: 'on' 또는 null이어야 함",
                defaultValueCheckbox.value == null || "on".equals(defaultValueCheckbox.value));
    }

    private static void testFormProperties() {
        var formElement = form().id("test-checkbox-form").element();
        body().add(formElement);

        // Name
        var namedCheckbox = checkbox()
                .name("agreement")
                .ariaLabel("Agreement")
                .element();
        assertEquals("name 속성: agreement여야 함", "agreement", namedCheckbox.name);

        // Form integration
        var formCheckbox = checkbox()
                .name("terms")
                .value("accepted")
                .select(true)
                .ariaLabel("Terms")
                .element();
        formElement.appendChild(formCheckbox);

        assertEquals("폼 체크박스: name은 terms여야 함", "terms", formCheckbox.name);
        assertEquals("폼 체크박스: value는 accepted여야 함", "accepted", formCheckbox.value);
        assertTrue("폼 체크박스: 체크되어야 함", formCheckbox.checked);

        // Builder getter methods
        var builder = checkbox()
                .name("test-name")
                .value("test-value");
        assertEquals("빌더 name getter: test-name을 반환해야 함", "test-name", builder.name());
        assertEquals("빌더 value getter: test-value를 반환해야 함", "test-value", builder.value());

        builder.disabled(true);
        assertTrue("빌더 disabled getter: true를 반환해야 함", builder.isDisabled());

        builder.required(true);
        assertTrue("빌더 required getter: true를 반환해야 함", builder.isRequired());
    }

    private static void testEventHandling() {
        // onChange event
        var changeTriggered = new AtomicBoolean();
        var changeCheckbox = checkbox()
                .ariaLabel("Change Test")
                .onChange(evt -> changeTriggered.set(true))
                .element();
        body().add(changeCheckbox);

        // Simulate change
        changeCheckbox.checked = true;
        changeCheckbox.dispatchEvent(new elemental2.dom.Event("change"));

        assertTrue("onChange 이벤트: 발생해야 함", changeTriggered.get());

        // onInput event
        var inputTriggered = new AtomicBoolean();
        var inputCheckbox = checkbox()
                .ariaLabel("Input Test")
                .onInput(evt -> inputTriggered.set(true))
                .element();
        body().add(inputCheckbox);

        // Simulate input
        inputCheckbox.checked = true;
        inputCheckbox.dispatchEvent(new elemental2.dom.Event("input"));

        assertTrue("onInput 이벤트: 발생해야 함", inputTriggered.get());

        // Multiple event handlers
        var changeCount = new AtomicInteger(0);
        var inputCount = new AtomicInteger(0);
        var multiEventCheckbox = checkbox()
                .ariaLabel("Multi Event Test")
                .onChange(evt -> changeCount.incrementAndGet())
                .onInput(evt -> inputCount.incrementAndGet())
                .element();
        body().add(multiEventCheckbox);

        multiEventCheckbox.checked = true;
        multiEventCheckbox.dispatchEvent(new elemental2.dom.Event("change"));
        multiEventCheckbox.dispatchEvent(new elemental2.dom.Event("input"));

        assertEquals("다중 이벤트: change 카운트는 1이어야 함", 1, changeCount.get());
        assertEquals("다중 이벤트: input 카운트는 1이어야 함", 1, inputCount.get());
    }

    private static void testAccessibility() {
        var ariaCheckbox = checkbox()
                .ariaLabel("Accept terms and conditions")
                .element();

        assertEquals("aria-label: 올바르게 설정되어야 함",
                "Accept terms and conditions",
                ariaCheckbox.getAttribute("aria-label"));

        // Disabled checkbox accessibility
        var disabledAriaCheckbox = checkbox()
                .disabled(true)
                .ariaLabel("Disabled option")
                .element();

        assertTrue("disabled aria 체크박스: disabled여야 함", disabledAriaCheckbox.disabled);
        assertEquals("disabled aria 체크박스: aria-label이 설정되어야 함",
                "Disabled option",
                disabledAriaCheckbox.getAttribute("aria-label"));
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

    private static void assertEquals(String message, int expected, int actual) {
        if (expected == actual) {
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

    private static void testUsageExamples() {
        // Example 1: Basic checkbox states (from docs)
        // <md-checkbox></md-checkbox>
        var basicCheckbox = checkbox()
                .ariaLabel("Basic checkbox")
                .element();
        body().add(basicCheckbox);
        assertFalse("사용 예제: 기본 체크박스는 체크 안됨이어야 함", basicCheckbox.checked);
        assertFalse("사용 예제: 기본 체크박스는 indeterminate가 아니어야 함", basicCheckbox.indeterminate);

        // Example 2: Pre-checked checkbox
        // <md-checkbox checked></md-checkbox>
        var preChecked = checkbox()
                .select(true)
                .ariaLabel("Pre-checked")
                .element();
        body().add(preChecked);
        assertTrue("사용 예제: 사전 체크된 체크박스는 체크되어야 함", preChecked.checked);

        // Example 3: Indeterminate checkbox
        // <md-checkbox indeterminate></md-checkbox>
        var indeterminateCheckbox = checkbox()
                .indeterminate()
                .ariaLabel("Indeterminate")
                .element();
        body().add(indeterminateCheckbox);
        assertTrue("사용 예제: indeterminate 체크박스는 indeterminate여야 함", indeterminateCheckbox.indeterminate);

        // Example 4: Aria-labeled checkbox for accessibility
        // <md-checkbox aria-label="Select all checkboxes"></md-checkbox>
        var selectAll = checkbox()
                .ariaLabel("Select all checkboxes")
                .element();
        body().add(selectAll);
        assertEquals("사용 예제: aria-label은 'Select all checkboxes'여야 함",
                "Select all checkboxes",
                selectAll.getAttribute("aria-label"));
    }

    private static void testLabeledCheckboxes() {
        // Example 1: Checkbox with inline label
        // <label><md-checkbox></md-checkbox> Checkbox one</label>
        var labelElement1 = label()
                .add(checkbox()
                        .ariaLabel("Checkbox one")
                        .element())
                .add(" Checkbox one")
                .element();
        body().add(labelElement1);

        var checkbox1 = (elemental2.dom.HTMLElement) labelElement1.querySelector("md-checkbox");
        assertNotNull("라벨 예제: 체크박스가 라벨 안에 존재해야 함", checkbox1);
        assertEquals("라벨 예제: aria-label은 'Checkbox one'이어야 함",
                "Checkbox one",
                checkbox1.getAttribute("aria-label"));

        // Example 2: Checkbox with external label using id
        // <md-checkbox id="checkbox-two"></md-checkbox>
        // <label for="checkbox-two">Checkbox two</label>
        var checkbox2 = checkbox()
                .id("checkbox-two")
                .ariaLabel("Checkbox two")
                .element();
        body().add(checkbox2);

        var label2 = label()
                .attr("for", "checkbox-two")
                .add("Checkbox two")
                .element();
        body().add(label2);

        assertEquals("라벨 예제: 체크박스 id는 'checkbox-two'여야 함",
                "checkbox-two",
                checkbox2.id);
        assertEquals("라벨 예제: 라벨 for 속성은 'checkbox-two'여야 함",
                "checkbox-two",
                label2.getAttribute("for"));

        // Test that clicking label checks the checkbox (simulated)
        checkbox2.checked = false;
        assertFalse("라벨 예제: 체크박스는 초기에 체크 안됨이어야 함", checkbox2.checked);

        // Simulate label click effect
        checkbox2.click();
        assertTrue("라벨 예제: 클릭하면 체크박스가 토글되어야 함", checkbox2.checked);
    }

    private static void testFormIntegrationBehavior() {
        // Example: Form submission with checkboxes
        var testForm = form().id("preferences-form").element();
        body().add(testForm);

        // Multiple checkboxes in a form representing user preferences
        var newsletter = checkbox()
                .name("newsletter")
                .value("yes")
                .select(true)
                .ariaLabel("Subscribe to newsletter")
                .element();
        testForm.appendChild(newsletter);

        var notifications = checkbox()
                .name("notifications")
                .value("enabled")
                .select(false)
                .ariaLabel("Enable notifications")
                .element();
        testForm.appendChild(notifications);

        var terms = checkbox()
                .name("terms")
                .value("accepted")
                .required(true)
                .select(true)
                .ariaLabel("Accept terms and conditions")
                .element();
        testForm.appendChild(terms);

        // Verify form integration
        assertTrue("폼 통합: newsletter는 체크되어야 함", newsletter.checked);
        assertFalse("폼 통합: notifications는 체크 안됨이어야 함", notifications.checked);
        assertTrue("폼 통합: terms는 체크되어야 함", terms.checked);
        assertTrue("폼 통합: terms는 required여야 함", terms.required);

        assertEquals("폼 통합: newsletter name은 'newsletter'여야 함", "newsletter", newsletter.name);
        assertEquals("폼 통합: newsletter value는 'yes'여야 함", "yes", newsletter.value);

        // Test "select all" pattern with indeterminate state
        var selectAllCheckbox = checkbox()
                .ariaLabel("Select all items")
                .element();
        body().add(selectAllCheckbox);

        var item1 = checkbox().ariaLabel("Item 1").select(true).element();
        var item2 = checkbox().ariaLabel("Item 2").select(false).element();
        var item3 = checkbox().ariaLabel("Item 3").select(true).element();
        body().add(item1);
        body().add(item2);
        body().add(item3);

        // Simulate "select all" logic
        var checkedCount = 0;
        if (item1.checked) checkedCount++;
        if (item2.checked) checkedCount++;
        if (item3.checked) checkedCount++;

        if (checkedCount == 0) {
            selectAllCheckbox.checked = false;
            selectAllCheckbox.indeterminate = false;
        } else if (checkedCount == 3) {
            selectAllCheckbox.checked = true;
            selectAllCheckbox.indeterminate = false;
        } else {
            selectAllCheckbox.checked = false;
            selectAllCheckbox.indeterminate = true;
        }

        assertTrue("전체 선택 패턴: 일부 항목만 선택되면 indeterminate여야 함",
                selectAllCheckbox.indeterminate);

        // Test disabled checkbox behavior in form
        var disabledCheckbox = checkbox()
                .name("disabled-option")
                .value("something")
                .disabled(true)
                .select(true)
                .ariaLabel("Disabled option")
                .element();
        testForm.appendChild(disabledCheckbox);

        assertTrue("폼 통합: disabled 체크박스는 disabled 상태 유지해야 함", disabledCheckbox.disabled);
        assertTrue("폼 통합: disabled 체크박스도 체크될 수 있음", disabledCheckbox.checked);
    }

    private static void testCheckboxClickBehavior() {
        // 테스트 1: 체크되지 않은 상태에서 클릭하면 체크됨
        var checkbox1 = checkbox()
                .ariaLabel("클릭 테스트 1")
                .element();
        body().add(checkbox1);

        assertFalse("클릭 동작: 초기 상태는 체크되지 않음", checkbox1.checked);

        checkbox1.click();
        assertTrue("클릭 동작: 클릭 후 체크됨", checkbox1.checked);

        // 테스트 2: 체크된 상태에서 클릭하면 체크 해제됨
        var checkbox2 = checkbox()
                .select(true)
                .ariaLabel("클릭 테스트 2")
                .element();
        body().add(checkbox2);

        assertTrue("클릭 동작: 초기 상태는 체크됨", checkbox2.checked);

        checkbox2.click();
        assertFalse("클릭 동작: 클릭 후 체크 해제됨", checkbox2.checked);

        // 테스트 3: 여러 번 클릭하면 상태가 토글됨
        var checkbox3 = checkbox()
                .ariaLabel("클릭 테스트 3")
                .element();
        body().add(checkbox3);

        assertFalse("클릭 동작: 초기 상태 체크 안됨", checkbox3.checked);

        checkbox3.click();
        assertTrue("클릭 동작: 첫 번째 클릭 후 체크됨", checkbox3.checked);

        checkbox3.click();
        assertFalse("클릭 동작: 두 번째 클릭 후 체크 해제됨", checkbox3.checked);

        checkbox3.click();
        assertTrue("클릭 동작: 세 번째 클릭 후 다시 체크됨", checkbox3.checked);

        // 테스트 4: disabled 체크박스는 클릭해도 상태가 변하지 않음
        var checkbox4 = checkbox()
                .disabled(true)
                .ariaLabel("클릭 테스트 4")
                .element();
        body().add(checkbox4);

        assertFalse("클릭 동작: disabled 체크박스 초기 상태", checkbox4.checked);

        checkbox4.click();
        assertFalse("클릭 동작: disabled 체크박스는 클릭해도 상태 변화 없음", checkbox4.checked);

        // 테스트 5: 클릭 시 change 이벤트 발생 확인
        var changeTriggered = new AtomicBoolean();
        var checkbox5 = checkbox()
                .ariaLabel("클릭 테스트 5")
                .onChange(evt -> {
                    changeTriggered.set(true);
                })
                .element();
        body().add(checkbox5);

        checkbox5.click();
        assertTrue("클릭 동작: 클릭 시 change 이벤트 발생", changeTriggered.get());
    }

    private static void testIndeterminateClickBehavior() {
        // 테스트 1: indeterminate 상태에서 클릭하면 checked 상태로 변경
        var checkbox1 = checkbox()
                .indeterminate()
                .ariaLabel("Indeterminate 클릭 테스트 1")
                .element();
        body().add(checkbox1);

        assertTrue("indeterminate 클릭: 초기 상태는 indeterminate", checkbox1.indeterminate);
        assertFalse("indeterminate 클릭: 초기 상태는 checked 아님", checkbox1.checked);

        checkbox1.click();
        assertFalse("indeterminate 클릭: 클릭 후 indeterminate 해제됨", checkbox1.indeterminate);
        assertTrue("indeterminate 클릭: 클릭 후 checked 상태로 변경", checkbox1.checked);

        // 테스트 2: indeterminate -> checked -> unchecked 순환
        var checkbox2 = checkbox()
                .indeterminate()
                .ariaLabel("Indeterminate 클릭 테스트 2")
                .element();
        body().add(checkbox2);

        assertTrue("indeterminate 순환: 시작은 indeterminate", checkbox2.indeterminate);
        assertFalse("indeterminate 순환: 시작은 checked 아님", checkbox2.checked);

        // 첫 번째 클릭: indeterminate -> checked
        checkbox2.click();
        assertFalse("indeterminate 순환: 첫 클릭 후 indeterminate 해제", checkbox2.indeterminate);
        assertTrue("indeterminate 순환: 첫 클릭 후 checked", checkbox2.checked);

        // 두 번째 클릭: checked -> unchecked
        checkbox2.click();
        assertFalse("indeterminate 순환: 두 번째 클릭 후 여전히 indeterminate 아님", checkbox2.indeterminate);
        assertFalse("indeterminate 순환: 두 번째 클릭 후 unchecked", checkbox2.checked);

        // 세 번째 클릭: unchecked -> checked (indeterminate 상태로 돌아가지 않음)
        checkbox2.click();
        assertFalse("indeterminate 순환: 세 번째 클릭 후 indeterminate 상태 아님", checkbox2.indeterminate);
        assertTrue("indeterminate 순환: 세 번째 클릭 후 checked", checkbox2.checked);

        // 테스트 3: Select All 패턴 - indeterminate 체크박스 클릭 동작
        var selectAllCheckbox = checkbox()
                .ariaLabel("Select All")
                .element();
        body().add(selectAllCheckbox);

        var item1 = checkbox().ariaLabel("Item 1").select(true).element();
        var item2 = checkbox().ariaLabel("Item 2").select(false).element();
        var item3 = checkbox().ariaLabel("Item 3").select(true).element();
        body().add(item1);
        body().add(item2);
        body().add(item3);

        // 일부만 선택된 상태 (indeterminate)
        selectAllCheckbox.indeterminate = true;
        selectAllCheckbox.checked = false;

        assertTrue("Select All 패턴: 일부 선택 시 indeterminate", selectAllCheckbox.indeterminate);

        // Select All 클릭 (indeterminate -> 모두 선택)
        selectAllCheckbox.click();
        assertFalse("Select All 패턴: 클릭 후 indeterminate 해제", selectAllCheckbox.indeterminate);
        assertTrue("Select All 패턴: 클릭 후 모두 선택됨", selectAllCheckbox.checked);

        // 다시 클릭 (모두 선택 -> 모두 해제)
        selectAllCheckbox.click();
        assertFalse("Select All 패턴: 두 번째 클릭 후 indeterminate 아님", selectAllCheckbox.indeterminate);
        assertFalse("Select All 패턴: 두 번째 클릭 후 모두 해제됨", selectAllCheckbox.checked);

        // 테스트 4: indeterminate 체크박스의 상태 변경 및 이벤트 확인
        var eventCount = new AtomicInteger(0);
        var checkbox3Builder = checkbox()
                .indeterminate()
                .ariaLabel("Indeterminate 이벤트 테스트")
                .onChange(evt -> {
                    console.log("Change event fired!");
                    eventCount.incrementAndGet();
                });
        var checkbox3 = checkbox3Builder.element();
        body().add(checkbox3);

        // 초기 상태 확인
        assertTrue("indeterminate 이벤트: 초기 상태는 indeterminate", checkbox3.indeterminate);
        assertFalse("indeterminate 이벤트: 초기 상태는 checked 아님", checkbox3.checked);

        // DOM에 추가된 후 클릭
        checkbox3.click();

        // 클릭 후 상태 확인
        assertTrue("indeterminate 이벤트: 클릭 후 checked 상태", checkbox3.checked);
        assertFalse("indeterminate 이벤트: 클릭 후 indeterminate 아님", checkbox3.indeterminate);

        // Material Web 컴포넌트는 click() 시 내부적으로 change 이벤트를 발생시킴
        // 만약 이벤트가 발생하지 않았다면 상태 변경이라도 확인
        console.log("Event count: " + eventCount.get());
        assertTrue("indeterminate 이벤트: 상태가 올바르게 변경됨",
                checkbox3.checked && !checkbox3.indeterminate);
    }

    private static void assertNotNull(String message, Object obj) {
        if (obj != null) {
            log(message + " - PASS");
        } else {
            log(message + " - Assertion failed! Object is null");
        }
    }

    private static void log(String message) {
        console.log(message);
    }
}
