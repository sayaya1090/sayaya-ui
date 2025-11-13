package dev.sayaya.ui.slider;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.SliderElementBuilder.slider;
import static org.jboss.elemento.Elements.*;

public class SliderVariantsTest {
    public static void test() {
        printSectionHeader("1. Slider 변형 (Slider Variants)");
        printDescription("Slider의 다양한 변형을 테스트합니다:");
        printDescription("- Continuous: 연속적인 값 슬라이더");
        printDescription("- Discrete: 틱 마크가 있는 불연속 슬라이더");
        printDescription("- Range: 범위 선택 슬라이더");
        printSeparator();

        var variantsSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(variantsSection);

        variantsSection.appendChild(h(3).text("Slider Variants").element());

        // Continuous Slider
        var continuousExample = addExampleCode(variantsSection,
            "📘 Continuous Slider (연속 슬라이더)",
            "기본 연속 값 슬라이더입니다.",
            """
            var slider = slider()
                .min(0)
                .max(100)
                .value(50)
                .element();
            """);
        var continuousSlider = slider()
                .min(0)
                .max(100)
                .value(50)
                .element();
        var continuousState = continuousExample.addInteractiveDemo(continuousSlider);
        continuousState.textContent = "value: " + continuousSlider.value + " | min: " + continuousSlider.min + " | max: " + continuousSlider.max;
        continuousSlider.addEventListener("input", evt -> {
            continuousState.textContent = "value: " + continuousSlider.value + " | min: " + continuousSlider.min + " | max: " + continuousSlider.max;
        });

        assertEquals("초기 값: 50이어야 함", 50.0, continuousSlider.value);
        assertEquals("최소값: 0이어야 함", 0.0, continuousSlider.min);
        assertEquals("최대값: 100이어야 함", 100.0, continuousSlider.max);

        // Discrete Slider
        var discreteExample = addExampleCode(variantsSection,
            "📘 Discrete Slider (불연속 슬라이더)",
            "틱 마크가 있는 불연속 값 슬라이더입니다.",
            """
            var slider = slider()
                .min(0)
                .max(10)
                .ticks(1)
                .value(5)
                .element();
            """);
        var discreteSlider = slider()
                .min(0)
                .max(10)
                .ticks(1)
                .value(5)
                .element();
        var discreteState = discreteExample.addInteractiveDemo(discreteSlider);
        discreteState.textContent = "value: " + discreteSlider.value + " | step: " + discreteSlider.step + " | ticks: " + discreteSlider.ticks;
        discreteSlider.addEventListener("input", evt -> {
            discreteState.textContent = "value: " + discreteSlider.value + " | step: " + discreteSlider.step + " | ticks: " + discreteSlider.ticks;
        });

        assertEquals("초기 값: 5여야 함", 5.0, discreteSlider.value);
        assertEquals("step: 1이어야 함", 1.0, discreteSlider.step);
        assertTrue("ticks: true여야 함", discreteSlider.ticks);

        // Range Slider
        var rangeExample = addExampleCode(variantsSection,
            "📘 Range Slider (범위 슬라이더)",
            "범위를 선택할 수 있는 슬라이더입니다.",
            """
            var slider = slider()
                .min(0)
                .max(100)
                .range()
                .valueStart(20)
                .valueEnd(80)
                .element();
            """);
        var rangeSlider = slider()
                .min(0)
                .max(100)
                .range()
                .valueStart(20)
                .valueEnd(80)
                .element();
        var rangeState = rangeExample.addInteractiveDemo(rangeSlider);
        rangeState.textContent = "range: " + rangeSlider.range + " | start: " + rangeSlider.valueStart + " | end: " + rangeSlider.valueEnd;
        rangeSlider.addEventListener("input", evt -> {
            rangeState.textContent = "range: " + rangeSlider.range + " | start: " + rangeSlider.valueStart + " | end: " + rangeSlider.valueEnd;
        });

        assertTrue("range: true여야 함", rangeSlider.range);
        assertEquals("시작값: 20이어야 함", 20.0, rangeSlider.valueStart);
        assertEquals("끝값: 80이어야 함", 80.0, rangeSlider.valueEnd);
    }
}
