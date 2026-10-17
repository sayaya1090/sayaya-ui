package dev.sayaya.ui.select;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.SelectElementBuilder.select;
import static org.jboss.elemento.Elements.*;

public class ValidationTest {
    public static void test() {
        printSectionHeader("6. 검증 (Validation)");
        printDescription("Select의 검증 기능을 테스트합니다:");
        printDescription("- required: 필수 선택 검증");
        printDescription("- checkValidity(): 유효성 확인");
        printDescription("- reportValidity(): 유효성 확인 및 UI 표시");
        printDescription("- setCustomValidity(): 커스텀 검증 메시지");
        printSeparator();

        var validationSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(validationSection);

        validationSection.appendChild(h(3).text("Validation").element());

        // Required Validation
        var requiredExample = addExampleCode(validationSection,
            "📘 Required Validation (필수 검증)",
            "필수 필드로 지정하고 검증합니다.",
            """
            var select = select().filled()
                .label("필수 선택")
                .required(true)
                .errorText("반드시 선택해야 합니다")
                .option().value("opt1").headline("옵션 1").done()
                .option().value("opt2").headline("옵션 2").done()
                .element();

            // 검증
            var isValid = select.checkValidity();
            if (!isValid) {
                select.reportValidity();  // 에러 표시
            }
            """);
        var requiredSelect = select().filled()
                .label("필수 선택")
                .required(true)
                .errorText("반드시 선택해야 합니다")
                .option().value("opt1").headline("옵션 1").done()
                .option().value("opt2").headline("옵션 2").done()
                .element();
        var requiredState = requiredExample.addInteractiveDemo(requiredSelect);
        requiredState.textContent = "valid: " + requiredSelect.checkValidity() + " | value: " + requiredSelect.value;
        requiredSelect.addEventListener("change", evt -> {
            requiredState.textContent = "valid: " + requiredSelect.checkValidity() + " | value: " + requiredSelect.value;
        });
        requiredSelect.reportValidity();

        requiredSelect.getUpdateComplete().then(r -> {
            assertFalse("빈 필수 필드는 invalid", requiredSelect.checkValidity());
            requiredSelect.select("opt1");
            assertTrue("값이 있으면 valid", requiredSelect.checkValidity());
            return null;
        });

        // Custom Validity
        var customExample = addExampleCode(validationSection,
            "📘 Custom Validity (커스텀 검증)",
            "프로그래밍 방식으로 커스텀 검증 메시지를 설정합니다.",
            """
            var select = select().outlined()
                .label("등급 선택")
                .option().value("bronze").headline("브론즈").done()
                .option().value("silver").headline("실버").done()
                .option().value("gold").headline("골드").done()
                .setCustomValidity("골드 등급만 선택 가능합니다")
                .element();

            // 선택 시 검증
            select.onChange(evt -> {
                var value = select.value;
                if (!"gold".equals(value)) {
                    select.setCustomValidity("골드 등급만 선택 가능합니다");
                } else {
                    select.setCustomValidity("");  // 에러 제거
                }
                select.reportValidity();
            });
            """);
        var customSelectBuilder = select().outlined()
                .label("등급 선택")
                .option().value("bronze").headline("브론즈").done()
                .option().value("silver").headline("실버").done()
                .option().value("gold").headline("골드").done();
        var customSelect = customSelectBuilder.onChange(evt -> {
            var value = customSelectBuilder.value();
            if (!"gold".equals(value)) {
                customSelectBuilder.setCustomValidity("골드 등급만 선택 가능합니다");
            } else {
                customSelectBuilder.setCustomValidity("");
            }
            customSelectBuilder.reportValidity();
        }).element();
        var customState = customExample.addInteractiveDemo(customSelect);
        customState.textContent = "valid: " + customSelect.checkValidity() + " | value: " + customSelect.value;
        customSelect.addEventListener("change", evt -> {
            customState.textContent = "valid: " + customSelect.checkValidity() + " | value: " + customSelect.value;
        });
        customSelect.setCustomValidity("골드 등급만 선택 가능합니다");
        assertFalse("커스텀 에러가 있으면 invalid", customSelect.checkValidity());

        customSelect.setCustomValidity("");
        assertTrue("커스텀 에러 제거 후 valid", customSelect.checkValidity());

        // Report Validity
        var reportExample = addExampleCode(validationSection,
            "📘 Report Validity (검증 결과 표시)",
            "검증 결과를 사용자에게 시각적으로 표시합니다.",
            """
            var select = select().filled()
                .label("도시")
                .required(true)
                .errorText("도시를 선택해주세요")
                .option().value("seoul").headline("서울").done()
                .option().value("busan").headline("부산").done()
                .element();

            // 폼 제출 시 검증
            form.onsubmit = evt -> {
                if (!select.reportValidity()) {
                    evt.preventDefault();  // 검증 실패 시 제출 방지
                }
            };
            """);
        var reportSelect = select().filled()
                .label("도시")
                .required(true)
                .errorText("도시를 선택해주세요")
                .option().value("seoul").headline("서울").done()
                .option().value("busan").headline("부산").done()
                .element();
        var reportState = reportExample.addInteractiveDemo(reportSelect);
        reportState.textContent = "valid: " + reportSelect.checkValidity() + " | value: " + reportSelect.value;
        reportSelect.addEventListener("change", evt -> {
            reportState.textContent = "valid: " + reportSelect.checkValidity() + " | value: " + reportSelect.value;
        });

        reportSelect.getUpdateComplete().then(r -> {
            assertFalse("reportValidity는 false 반환", reportSelect.reportValidity());
            reportSelect.select("seoul");
            assertTrue("값 선택 후 valid", reportSelect.reportValidity());
            return null;
        });
    }
}
