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
        addExampleCode(advancedSection,
            "📘 Leading Icon (선행 아이콘)",
            "Select 앞에 아이콘을 표시합니다.",
            """
            var select = select().filled()
                .label("위치")
                .iconLeading(icon("location_on"))
                .option().value("home").headline("집").end()
                .option().value("office").headline("사무실").end()
                .element();
            """);
        var iconSelect = select().filled()
                .label("위치")
                .iconLeading(icon("location_on"))
                .option().value("home").headline("집").end()
                .option().value("office").headline("사무실").end()
                .element();
        advancedSection.appendChild(iconSelect);

        assertNotNull("선행 아이콘 존재",
                iconSelect.querySelector("[slot='leading-icon']"));
        assertTrue("hasLeadingIcon 속성", iconSelect.hasLeadingIcon);

        // Quick Menu
        addExampleCode(advancedSection,
            "📘 Quick Menu (빠른 메뉴)",
            "애니메이션 없이 즉시 메뉴를 엽니다.",
            """
            var select = select().outlined()
                .label("빠른 선택")
                .quick(true)
                .option().value("1").headline("옵션 1").end()
                .option().value("2").headline("옵션 2").end()
                .element();
            """);
        var quickSelect = select().outlined()
                .label("빠른 선택")
                .quick(true)
                .option().value("1").headline("옵션 1").end()
                .option().value("2").headline("옵션 2").end()
                .element();
        advancedSection.appendChild(quickSelect);

        assertTrue("quick 속성: true", quickSelect.quick);

        // Clamp Menu Width
        addExampleCode(advancedSection,
            "📘 Clamp Menu Width (메뉴 너비 제한)",
            "메뉴의 너비를 Select 너비로 제한합니다.",
            """
            var select = select().filled()
                .label("좁은 메뉴")
                .clampMenuWidth(true)
                .option()
                    .value("short")
                    .headline("짧은 옵션")
                .end()
                .option()
                    .value("long")
                    .headline("매우 매우 긴 옵션 텍스트입니다")
                .end()
                .element();
            """);
        var clampSelect = select().filled()
                .label("좁은 메뉴")
                .clampMenuWidth(true)
                .option()
                    .value("short")
                    .headline("짧은 옵션")
                .end()
                .option()
                    .value("long")
                    .headline("매우 매우 긴 옵션 텍스트입니다")
                .end()
                .element();
        advancedSection.appendChild(clampSelect);

        assertTrue("clampMenuWidth 속성: true", clampSelect.clampMenuWidth);

        // Typeahead Delay
        addExampleCode(advancedSection,
            "📘 Typeahead Delay (타입어헤드 지연)",
            "키보드로 옵션을 검색할 때의 지연 시간을 설정합니다.",
            """
            var select = select().outlined()
                .label("검색 가능")
                .typeaheadDelay(500)  // 500ms 지연
                .option().value("a").headline("Apple").end()
                .option().value("b").headline("Banana").end()
                .option().value("c").headline("Cherry").end()
                .element();
            """);
        var typeaheadSelect = select().outlined()
                .label("검색 가능")
                .typeaheadDelay(500)
                .option().value("a").headline("Apple").end()
                .option().value("b").headline("Banana").end()
                .option().value("c").headline("Cherry").end()
                .element();
        advancedSection.appendChild(typeaheadSelect);

        assertEquals("typeaheadDelay: 500ms", 500.0, typeaheadSelect.typeaheadDelay);

        // Form Association
        addExampleCode(advancedSection,
            "📘 Form Association (폼 연결)",
            "Select를 특정 폼과 연결합니다. 폼 밖에 있어도 작동합니다.",
            """
            var form = form().id("user-form").element();

            var select = select().filled()
                .label("선호도")
                .name("preference")
                .form(form)
                .option().value("yes").headline("예").end()
                .option().value("no").headline("아니오").end()
                .element();
            """);
        var formElement = form().id("select-test-form").element();
        advancedSection.appendChild(formElement);

        var formSelect = select().filled()
                .label("선호도")
                .name("preference")
                .value("yes")
                .form(formElement)
                .option().value("yes").headline("예").end()
                .option().value("no").headline("아니오").end()
                .element();
        advancedSection.appendChild(formSelect);

        assertEquals("name 속성", "preference", formSelect.name);
        assertEquals("form 속성",
                "select-test-form", formSelect.getAttribute("form"));
        assertEquals("value 속성", "yes", formSelect.getAttribute("value"));
    }
}
