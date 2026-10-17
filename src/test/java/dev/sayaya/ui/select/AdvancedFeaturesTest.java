package dev.sayaya.ui.select;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.IconElementBuilder.icon;
import static dev.sayaya.ui.elements.SelectElementBuilder.select;
import static org.jboss.elemento.Elements.*;

public class AdvancedFeaturesTest {
    public static void test() {
        printSectionHeader("7. 고급 기능 (Advanced Features)");
        printDescription("Select의 고급 기능들:");
        printDescription("- iconLeading: 선행 아이콘");
        printDescription("- quick: 빠른 메뉴 (애니메이션 없음)");
        printDescription("- clampMenuWidth: 메뉴 너비 제한");
        printDescription("- typeaheadDelay: 타입어헤드 지연 시간");
        printDescription("- form: 폼 연결");
        printSeparator();

        var advancedSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(advancedSection);

        advancedSection.appendChild(h(3).text("Advanced Features").element());

        // Leading Icon
        var iconExample = addExampleCode(advancedSection,
            "📘 Leading Icon (선행 아이콘)",
            "Select 앞에 아이콘을 표시합니다.",
            """
            var select = select().filled()
                .label("위치")
                .iconLeading(icon("location_on"))
                .option().value("home").headline("집").done()
                .option().value("office").headline("사무실").done()
                .element();
            """);
        var iconSelect = select().filled()
                .label("위치")
                .iconLeading(icon("location_on"))
                .option().value("home").headline("집").done()
                .option().value("office").headline("사무실").done()
                .element();
        iconExample.addInteractiveDemo(iconSelect, false);

        assertNotNull("선행 아이콘 존재",
                iconSelect.querySelector("[slot='leading-icon']"));
        // hasLeadingIcon은 leading-icon 슬롯의 slotchange 처리 후 갱신된다
        iconSelect.getUpdateComplete().then(r -> {
            elemental2.dom.DomGlobal.setTimeout(ignore -> assertTrue("hasLeadingIcon 속성", iconSelect.hasLeadingIcon), 300);
            return null;
        });

        // Quick Menu
        var quickExample = addExampleCode(advancedSection,
            "📘 Quick Menu (빠른 메뉴)",
            "애니메이션 없이 즉시 메뉴를 엽니다.",
            """
            var select = select().outlined()
                .label("빠른 선택")
                .quick(true)
                .option().value("1").headline("옵션 1").done()
                .option().value("2").headline("옵션 2").done()
                .element();
            """);
        var quickSelect = select().outlined()
                .label("빠른 선택")
                .quick(true)
                .option().value("1").headline("옵션 1").done()
                .option().value("2").headline("옵션 2").done()
                .element();
        quickExample.addInteractiveDemo(quickSelect, false);

        assertTrue("quick 속성: true", quickSelect.quick);

        // Clamp Menu Width
        var clampExample = addExampleCode(advancedSection,
            "📘 Clamp Menu Width (메뉴 너비 제한)",
            "메뉴의 너비를 Select 너비로 제한합니다.",
            """
            var select = select().filled()
                .label("좁은 메뉴")
                .clampMenuWidth(true)
                .option()
                    .value("short")
                    .headline("짧은 옵션")
                .done()
                .option()
                    .value("long")
                    .headline("매우 매우 긴 옵션 텍스트입니다")
                .done()
                .element();
            """);
        var clampSelect = select().filled()
                .label("좁은 메뉴")
                .clampMenuWidth(true)
                .option()
                    .value("short")
                    .headline("짧은 옵션")
                .done()
                .option()
                    .value("long")
                    .headline("매우 매우 긴 옵션 텍스트입니다")
                .done()
                .element();
        clampExample.addInteractiveDemo(clampSelect, false);

        assertTrue("clampMenuWidth 속성: true", clampSelect.clampMenuWidth);

        // Typeahead Delay
        var typeaheadExample = addExampleCode(advancedSection,
            "📘 Typeahead Delay (타입어헤드 지연)",
            "키보드로 옵션을 검색할 때의 지연 시간을 설정합니다.",
            """
            var select = select().outlined()
                .label("검색 가능")
                .typeaheadDelay(500)  // 500ms 지연
                .option().value("a").headline("Apple").done()
                .option().value("b").headline("Banana").done()
                .option().value("c").headline("Cherry").done()
                .element();
            """);
        var typeaheadSelect = select().outlined()
                .label("검색 가능")
                .typeaheadDelay(500)
                .option().value("a").headline("Apple").done()
                .option().value("b").headline("Banana").done()
                .option().value("c").headline("Cherry").done()
                .element();
        typeaheadExample.addInteractiveDemo(typeaheadSelect, false);

        assertEquals("typeaheadDelay: 500ms", 500.0, typeaheadSelect.typeaheadDelay);

        // Form Association
        var formExample = addExampleCode(advancedSection,
            "📘 Form Association (폼 연결)",
            "Select를 특정 폼과 연결합니다. 폼 밖에 있어도 작동합니다.",
            """
            var form = form().id("user-form").element();

            var select = select().filled()
                .label("선호도")
                .name("preference")
                .form(form)
                .option().value("yes").headline("예").done()
                .option().value("no").headline("아니오").done()
                .element();
            """);
        var formElement = form().id("select-test-form").element();
        advancedSection.appendChild(formElement);

        var formSelect = select().filled()
                .label("선호도")
                .name("preference")
                .value("yes")
                .form(formElement)
                .option().value("yes").headline("예").done()
                .option().value("no").headline("아니오").done()
                .element();
        formExample.addInteractiveDemo(formSelect, false);

        assertEquals("name 속성", "preference", formSelect.name);
        assertEquals("form 속성",
                "select-test-form", formSelect.getAttribute("form"));
        assertEquals("value 속성", "yes", formSelect.getAttribute("value"));

        // ---- 명세 커버리지: 옵션/선택 메서드/메뉴 이벤트/검증/포커스 ----
        int[] opened = {0}, closing = {0}, closed = {0};
        var spec = select().outlined().label("과일").quick(true)
                .option().value("a").headline("사과").supportingText("빨강").done()
                .option().value("b").headline("바나나").done()
                .option().value("c").headline("체리").disabled(true).done()
                .element();
        spec.addEventListener("opened", e -> opened[0]++);
        spec.addEventListener("closing", e -> closing[0]++);
        spec.addEventListener("closed", e -> closed[0]++);
        var required = select().filled().label("필수").required(true)
                .option().value("x").headline("X").done()
                .element();
        var errSelect = select().filled().label("에러").error(true).errorText("잘못된 값")
                .option().value("x").headline("X").done()
                .element();
        body().add(div().add(spec).add(required).add(errSelect));

        spec.getUpdateComplete().then(r -> {
            var opts = spec.options;
            assertTrue("옵션 headline: 첫 옵션 텍스트에 사과", opts.length == 3 && opts[0].textContent.contains("사과"));
            assertNotNull("옵션 supportingText: supporting-text 슬롯", opts[0].querySelector("[slot='supporting-text']"));
            assertTrue("옵션 disabled: 세 번째 옵션 비활성화", opts[2].disabled);

            spec.select("b");
            assertEquals("select() 메서드: 값 b", "b", spec.value);
            assertTrue("옵션 selected: 두 번째 옵션 selected", opts[1].selected && !opts[0].selected);
            spec.selectIndex(0);
            assertEquals("selectIndex() 메서드: 인덱스 0 → 값 a", "a", spec.value);
            assertTrue("selectedOptions: 1개이며 값 a", spec.selectedOptions.length == 1 && "a".equals(spec.selectedOptions[0].value));

            assertNotNull("ValidityState: validity 객체 존재", required.validity);
            assertTrue("required 검증: 값이 없으면 valueMissing", required.validity.valueMissing && !required.checkValidity());

            assertTrue("수동 에러: error true, errorText 설정", errSelect.error && "잘못된 값".equals(errSelect.errorText));
            errSelect.error = false;
            boolean cleared = !errSelect.error;
            errSelect.error = true;
            assertTrue("동적 에러: false → true 갱신", cleared && errSelect.error);

            spec.focus();
            assertTrue("focus(): activeElement가 select", elemental2.dom.DomGlobal.document.activeElement == spec);
            spec.blur();
            assertTrue("blur(): activeElement가 select 아님", elemental2.dom.DomGlobal.document.activeElement != spec);

            spec.showPicker();
            elemental2.dom.DomGlobal.setTimeout(t1 -> {
                assertTrue("showPicker: 메뉴 열림(open)", Boolean.TRUE.equals(jsinterop.base.Js.asPropertyMap(spec).get("open")));
                assertTrue("opened 이벤트: 1회 이상", opened[0] >= 1);
                jsinterop.base.Js.asPropertyMap(spec).set("open", false);
                elemental2.dom.DomGlobal.setTimeout(t2 -> {
                    assertTrue("closing 이벤트: 1회 이상", closing[0] >= 1);
                    assertTrue("closed 이벤트: 1회 이상", closed[0] >= 1);
                }, 800);
            }, 800);
            return null;
        });
    }
}
