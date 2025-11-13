package dev.sayaya.ui.slider;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.SliderElementBuilder.slider;
import static org.jboss.elemento.Elements.*;

public class SliderPropertiesTest {
    public static void test() {
        printSectionHeader("2. 기본 속성 (Basic Properties)");
        printDescription("Slider의 기본 속성들을 테스트합니다:");
        printDescription("- min/max: 최소/최대 값");
        printDescription("- step: 증가/감소 단계");
        printDescription("- labeled: 값 레이블 표시");
        printDescription("- disabled: 비활성화");
        printSeparator();

        var propertiesSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(propertiesSection);

        propertiesSection.appendChild(h(3).text("Basic Properties").element());

        // Min/Max
        var minMaxExample = addExampleCode(propertiesSection,
            "📘 Min/Max (최소/최대값)",
            "슬라이더의 최소값과 최대값을 설정합니다.",
            """
            var slider = slider()
                .min(10)
                .max(90)
                .value(50)
                .element();
            """);
        var minMaxSlider = slider()
                .min(10)
                .max(90)
                .value(50)
                .element();
        var minMaxState = minMaxExample.addInteractiveDemo(minMaxSlider);
        minMaxState.textContent = "min: " + minMaxSlider.min + " | max: " + minMaxSlider.max + " | value: " + minMaxSlider.value;
        minMaxSlider.addEventListener("input", evt -> {
            minMaxState.textContent = "min: " + minMaxSlider.min + " | max: " + minMaxSlider.max + " | value: " + minMaxSlider.value;
        });

        assertEquals("min: 10이어야 함", 10.0, minMaxSlider.min);
        assertEquals("max: 90이어야 함", 90.0, minMaxSlider.max);

        // Step
        var stepExample = addExampleCode(propertiesSection,
            "📘 Step (단계)",
            "슬라이더의 증가/감소 단계를 설정합니다.",
            """
            var slider = slider()
                .ticks(5)
                .min(0)
                .max(100)
                .value(50)
                .element();
            """);
        var stepSlider = slider()
                .ticks(5)
                .min(0)
                .max(100)
                .value(50)
                .element();
        var stepState = stepExample.addInteractiveDemo(stepSlider);
        stepState.textContent = "step: " + stepSlider.step + " | value: " + stepSlider.value;
        stepSlider.addEventListener("input", evt -> {
            stepState.textContent = "step: " + stepSlider.step + " | value: " + stepSlider.value;
        });

        assertEquals("step: 5여야 함", 5.0, stepSlider.step);

        // Labeled
        var labeledExample = addExampleCode(propertiesSection,
            "📘 Labeled (레이블 표시)",
            "활성화 시 값 레이블을 표시합니다.",
            """
            var slider = slider()
                .labeled(true)
                .value(75)
                .element();
            """);
        var labeledSlider = slider()
                .labeled(true)
                .value(75)
                .element();
        var labeledState = labeledExample.addInteractiveDemo(labeledSlider);
        labeledState.textContent = "labeled: " + labeledSlider.labeled + " | value: " + labeledSlider.value;
        labeledSlider.addEventListener("input", evt -> {
            labeledState.textContent = "labeled: " + labeledSlider.labeled + " | value: " + labeledSlider.value;
        });

        assertTrue("labeled: true여야 함", labeledSlider.labeled);

        // Disabled
        var disabledExample = addExampleCode(propertiesSection,
            "📘 Disabled (비활성화)",
            "슬라이더를 비활성화합니다.",
            """
            var slider = slider()
                .disabled(true)
                .value(50)
                .element();
            """);
        var disabledSlider = slider()
                .disabled(true)
                .value(50)
                .element();
        var disabledState = disabledExample.addInteractiveDemo(disabledSlider);
        disabledState.textContent = "disabled: " + disabledSlider.disabled + " | value: " + disabledSlider.value;

        assertTrue("disabled: true여야 함", disabledSlider.disabled);

        // Getter methods
        addExampleCode(propertiesSection,
            "📘 Getter Methods (조회 메서드)",
            "빌더 패턴에서 현재 설정된 값을 조회할 수 있습니다.",
            """
            var builder = slider()
                .min(0)
                .max(100)
                .value(50)
                .labeled(true)
                .disabled(true);
            
            double min = builder.getMin();
            double max = builder.getMax();
            double value = builder.getValue();
            boolean labeled = builder.isLabeled();
            boolean disabled = builder.isDisabled();
            """);
        var builder = slider()
                .min(0)
                .max(100)
                .value(50)
                .labeled(true)
                .disabled(true);

        assertEquals("getMin(): 0을 반환해야 함", 0.0, builder.getMin());
        assertEquals("getMax(): 100을 반환해야 함", 100.0, builder.getMax());
        assertEquals("getValue(): 50을 반환해야 함", 50.0, builder.getValue());
        assertTrue("isLabeled(): true를 반환해야 함", builder.isLabeled());
        assertTrue("isDisabled(): true를 반환해야 함", builder.isDisabled());
    }
}
