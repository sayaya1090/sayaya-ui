package dev.sayaya.ui.slider;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.SliderElementBuilder.slider;
import static org.jboss.elemento.Elements.*;

public class SliderRangeTest {
    public static void test() {
        printSectionHeader("3. Range Slider (범위 슬라이더)");
        printDescription("범위 선택 슬라이더의 특성을 테스트합니다:");
        printDescription("- valueStart/valueEnd: 시작/끝 값");
        printDescription("- Range with ticks: 틱이 있는 범위 슬라이더");
        printDescription("- ARIA 레이블");
        printSeparator();

        var rangeSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(rangeSection);

        rangeSection.appendChild(h(3).text("Range Slider").element());

        // Basic Range
        var basicRangeExample = addExampleCode(rangeSection,
            "📘 Basic Range (기본 범위)",
            "시작값과 끝값을 설정하는 범위 슬라이더입니다.",
            """
            var slider = slider()
                .min(0)
                .max(100)
                .range()
                .valueStart(30)
                .valueEnd(70)
                .element();
            """);
        var basicRangeSlider = slider()
                .min(0)
                .max(100)
                .range()
                .valueStart(30)
                .valueEnd(70)
                .element();
        var basicRangeState = basicRangeExample.addInteractiveDemo(basicRangeSlider);
        basicRangeState.textContent = "start: " + basicRangeSlider.valueStart + " | end: " + basicRangeSlider.valueEnd;
        basicRangeSlider.addEventListener("input", evt -> {
            basicRangeState.textContent = "start: " + basicRangeSlider.valueStart + " | end: " + basicRangeSlider.valueEnd;
        });

        assertEquals("시작값: 30이어야 함", 30.0, basicRangeSlider.valueStart);
        assertEquals("끝값: 70이어야 함", 70.0, basicRangeSlider.valueEnd);

        // Range with Ticks
        var rangeTicksExample = addExampleCode(rangeSection,
            "📘 Range with Ticks (틱이 있는 범위)",
            "틱 마크가 있는 불연속 범위 슬라이더입니다.",
            """
            var slider = slider()
                .min(0)
                .max(10)
                .range()
                .valueStart(2)
                .valueEnd(8)
                .ticks(1)
                .element();
            """);
        var rangeTicksSlider = slider()
                .min(0)
                .max(10)
                .range()
                .valueStart(2)
                .valueEnd(8)
                .ticks(1)
                .element();
        var rangeTicksState = rangeTicksExample.addInteractiveDemo(rangeTicksSlider);
        rangeTicksState.textContent = "start: " + rangeTicksSlider.valueStart + 
            " | end: " + rangeTicksSlider.valueEnd + 
            " | step: " + rangeTicksSlider.step + 
            " | ticks: " + rangeTicksSlider.ticks;
        rangeTicksSlider.addEventListener("input", evt -> {
            rangeTicksState.textContent = "start: " + rangeTicksSlider.valueStart + 
                " | end: " + rangeTicksSlider.valueEnd + 
                " | step: " + rangeTicksSlider.step + 
                " | ticks: " + rangeTicksSlider.ticks;
        });

        assertTrue("range: true여야 함", rangeTicksSlider.range);
        assertTrue("ticks: true여야 함", rangeTicksSlider.ticks);
        assertEquals("step: 1이어야 함", 1.0, rangeTicksSlider.step);

        // Range with Labels
        var rangeLabeledExample = addExampleCode(rangeSection,
            "📘 Range with Labels (레이블이 있는 범위)",
            "값 레이블이 표시되는 범위 슬라이더입니다.",
            """
            var slider = slider()
                .min(0)
                .max(100)
                .range()
                .valueStart(25)
                .valueEnd(75)
                .labeled(true)
                .element();
            """);
        var rangeLabeledSlider = slider()
                .min(0)
                .max(100)
                .range()
                .valueStart(25)
                .valueEnd(75)
                .labeled(true)
                .element();
        var rangeLabeledState = rangeLabeledExample.addInteractiveDemo(rangeLabeledSlider);
        rangeLabeledState.textContent = "labeled: " + rangeLabeledSlider.labeled + 
            " | start: " + rangeLabeledSlider.valueStart + 
            " | end: " + rangeLabeledSlider.valueEnd;
        rangeLabeledSlider.addEventListener("input", evt -> {
            rangeLabeledState.textContent = "labeled: " + rangeLabeledSlider.labeled + 
                " | start: " + rangeLabeledSlider.valueStart + 
                " | end: " + rangeLabeledSlider.valueEnd;
        });

        assertTrue("labeled: true여야 함", rangeLabeledSlider.labeled);

        // Range Getter Methods
        addExampleCode(rangeSection,
            "📘 Range Getter Methods (범위 조회 메서드)",
            "범위 슬라이더의 값을 조회할 수 있습니다.",
            """
            var builder = slider()
                .range()
                .valueStart(20)
                .valueEnd(80);
            
            double start = builder.getValueStart();
            double end = builder.getValueEnd();
            boolean isRange = builder.isRange();
            """);
        var rangeBuilder = slider()
                .range()
                .valueStart(20)
                .valueEnd(80);

        assertEquals("getValueStart(): 20을 반환해야 함", 20.0, rangeBuilder.getValueStart());
        assertEquals("getValueEnd(): 80을 반환해야 함", 80.0, rangeBuilder.getValueEnd());
        assertTrue("isRange(): true를 반환해야 함", rangeBuilder.isRange());
    }
}
