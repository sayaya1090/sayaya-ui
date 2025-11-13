package dev.sayaya.ui.progress;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.ProgressElementBuilder.progress;
import static org.jboss.elemento.Elements.*;

public class ProgressPropertiesTest {
    public static void test() {
        printSectionHeader("2. 기본 속성 (Basic Properties)");
        printDescription("Progress의 기본 속성들을 테스트합니다:");
        printDescription("- value: 현재 진행률 값");
        printDescription("- max: 최대 값");
        printDescription("- buffer: 버퍼 값 (linear만 해당)");
        printSeparator();

        var propertiesSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(propertiesSection);

        propertiesSection.appendChild(h(3).text("Basic Properties").element());

        // Value
        var valueExample = addExampleCode(propertiesSection,
            "📘 Value (값)",
            "현재 진행률 값을 설정합니다 (0과 max 사이).",
            """
            var progress = progress()
                .linear()
                .value(0.75)
                .element();
            """);
        var valueProgress = progress()
                .linear()
                .value(0.75)
                .element();
        var valueState = valueExample.addInteractiveDemo(valueProgress);
        valueState.textContent = "value: " + valueProgress.value;

        assertEquals("value: 0.75여야 함", 0.75, valueProgress.value);

        // Max
        var maxExample = addExampleCode(propertiesSection,
            "📘 Max (최대값)",
            "진행률의 최대 값을 설정합니다. 기본값은 1입니다.",
            """
            var progress = progress()
                .circular()
                .value(50)
                .max(100)
                .element();
            """);
        var maxProgress = progress()
                .circular()
                .value(50)
                .max(100)
                .element();
        var maxState = maxExample.addInteractiveDemo(maxProgress);
        maxState.textContent = "value: " + maxProgress.value + " | max: " + maxProgress.max;

        assertEquals("value: 50이어야 함", 50.0, maxProgress.value);
        assertEquals("max: 100이어야 함", 100.0, maxProgress.max);

        // Buffer (Linear only)
        var bufferExample = addExampleCode(propertiesSection,
            "📘 Buffer (버퍼 - Linear만)",
            "버퍼링된 양을 표시합니다.",
            """
            var progress = progress()
                .linear()
                .value(0.3)
                .buffer(0.7)
                .max(1)
                .element();
            """);
        var bufferProgress = progress()
                .linear()
                .value(0.3)
                .buffer(0.7)
                .max(1)
                .element();
        var bufferState = bufferExample.addInteractiveDemo(bufferProgress);
        bufferState.textContent = "value: " + bufferProgress.value + 
            " | buffer: " + bufferProgress.buffer + 
            " | max: " + bufferProgress.max;

        assertEquals("buffer: 0.7이어야 함", 0.7, bufferProgress.buffer);

        // Percentage Example
        var percentageExample = addExampleCode(propertiesSection,
            "📘 Percentage (퍼센트)",
            "퍼센트 단위로 진행률을 표시하는 예제입니다.",
            """
            var progress = progress()
                .linear()
                .value(65)
                .max(100)
                .ariaLabel("65% 완료")
                .element();
            """);
        var percentageProgress = progress()
                .linear()
                .value(65)
                .max(100)
                .ariaLabel("65% 완료")
                .element();
        var percentageState = percentageExample.addInteractiveDemo(percentageProgress);
        percentageState.textContent = "value: " + percentageProgress.value + 
            " | max: " + percentageProgress.max + 
            " | percentage: " + (percentageProgress.value / percentageProgress.max * 100) + "%";

        assertEquals("percentage 계산 확인", 65.0, percentageProgress.value / percentageProgress.max * 100);

        // Getter methods
        addExampleCode(propertiesSection,
            "📘 Getter Methods (조회 메서드)",
            "빌더 패턴에서 현재 설정된 값을 조회할 수 있습니다.",
            """
            var linearBuilder = progress()
                .linear()
                .value(0.5)
                .max(1.0)
                .buffer(0.8);
            
            double value = linearBuilder.getValue();
            double max = linearBuilder.getMax();
            double buffer = linearBuilder.getBuffer();
            
            var circularBuilder = progress()
                .circular()
                .value(75)
                .max(100);
            
            double circValue = circularBuilder.getValue();
            double circMax = circularBuilder.getMax();
            """);
        
        var linearBuilder = progress()
                .linear()
                .value(0.5)
                .max(1.0)
                .buffer(0.8);
        
        assertEquals("getValue(): 0.5를 반환해야 함", 0.5, linearBuilder.getValue());
        assertEquals("getMax(): 1.0을 반환해야 함", 1.0, linearBuilder.getMax());
        assertEquals("getBuffer(): 0.8을 반환해야 함", 0.8, linearBuilder.getBuffer());

        var circularBuilder = progress()
                .circular()
                .value(75)
                .max(100);
        
        assertEquals("circular getValue(): 75를 반환해야 함", 75.0, circularBuilder.getValue());
        assertEquals("circular getMax(): 100을 반환해야 함", 100.0, circularBuilder.getMax());
    }
}
