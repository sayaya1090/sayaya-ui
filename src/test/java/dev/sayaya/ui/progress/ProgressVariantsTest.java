package dev.sayaya.ui.progress;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.ProgressElementBuilder.progress;
import static org.jboss.elemento.Elements.*;

public class ProgressVariantsTest {
    public static void test() {
        printSectionHeader("1. Progress 변형 (Progress Variants)");
        printDescription("Progress의 다양한 변형을 테스트합니다:");
        printDescription("- Linear: 선형 진행률 표시");
        printDescription("- Circular: 원형 진행률 표시");
        printSeparator();

        var variantsSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(variantsSection);

        variantsSection.appendChild(h(3).text("Progress Variants").element());

        // Linear Progress
        var linearExample = addExampleCode(variantsSection,
            "📘 Linear Progress (선형)",
            "선형 진행률 표시입니다.",
            """
            var progress = progress()
                .linear()
                .value(0.5)
                .element();
            """);
        var linearProgress = progress()
                .linear()
                .value(0.5)
                .element();
        var linearState = linearExample.addInteractiveDemo(linearProgress);
        linearState.textContent = "value: " + linearProgress.value + " | max: " + linearProgress.max;

        assertEquals("초기 값: 0.5여야 함", 0.5, linearProgress.value);
        assertEquals("최대값: 1이어야 함 (기본값)", 1.0, linearProgress.max);

        // Circular Progress
        var circularExample = addExampleCode(variantsSection,
            "📘 Circular Progress (원형)",
            "원형 진행률 표시입니다.",
            """
            var progress = progress()
                .circular()
                .value(0.7)
                .element();
            """);
        var circularProgress = progress()
                .circular()
                .value(0.7)
                .element();
        var circularState = circularExample.addInteractiveDemo(circularProgress);
        circularState.textContent = "value: " + circularProgress.value + " | max: " + circularProgress.max;

        assertEquals("초기 값: 0.7이어야 함", 0.7, circularProgress.value);

        // Linear with Buffer
        var bufferExample = addExampleCode(variantsSection,
            "📘 Linear with Buffer (버퍼)",
            "버퍼 영역을 표시하는 선형 진행률입니다.",
            """
            var progress = progress()
                .linear()
                .value(0.3)
                .buffer(0.6)
                .element();
            """);
        var bufferProgress = progress()
                .linear()
                .value(0.3)
                .buffer(0.6)
                .element();
        var bufferState = bufferExample.addInteractiveDemo(bufferProgress);
        bufferState.textContent = "value: " + bufferProgress.value + 
            " | buffer: " + bufferProgress.buffer + 
            " | max: " + bufferProgress.max;

        assertEquals("value: 0.3이어야 함", 0.3, bufferProgress.value);
        assertEquals("buffer: 0.6이어야 함", 0.6, bufferProgress.buffer);
    }
}
