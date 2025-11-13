package dev.sayaya.ui.progress;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.ProgressElementBuilder.progress;
import static org.jboss.elemento.Elements.*;

public class ProgressIndeterminateTest {
    public static void test() {
        printSectionHeader("3. Indeterminate (무한 로딩)");
        printDescription("무한 로딩 상태의 진행률을 테스트합니다:");
        printDescription("- indeterminate: 무한 로딩 애니메이션");
        printDescription("- fourColor: 4가지 색상 사용");
        printSeparator();

        var indeterminateSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(indeterminateSection);

        indeterminateSection.appendChild(h(3).text("Indeterminate Progress").element());

        // Linear Indeterminate
        var linearIndeterminateExample = addExampleCode(indeterminateSection,
            "📘 Linear Indeterminate (선형 무한)",
            "무한 로딩 애니메이션을 표시하는 선형 진행률입니다.",
            """
            var progress = progress()
                .linear()
                .indeterminate(true)
                .element();
            """);
        var linearIndeterminate = progress()
                .linear()
                .indeterminate(true)
                .element();
        var linearIndeterminateState = linearIndeterminateExample.addInteractiveDemo(linearIndeterminate);
        linearIndeterminateState.textContent = "indeterminate: " + linearIndeterminate.indeterminate;

        assertTrue("indeterminate: true여야 함", linearIndeterminate.indeterminate);

        // Circular Indeterminate
        var circularIndeterminateExample = addExampleCode(indeterminateSection,
            "📘 Circular Indeterminate (원형 무한)",
            "무한 로딩 애니메이션을 표시하는 원형 진행률입니다.",
            """
            var progress = progress()
                .circular()
                .indeterminate(true)
                .element();
            """);
        var circularIndeterminate = progress()
                .circular()
                .indeterminate(true)
                .element();
        var circularIndeterminateState = circularIndeterminateExample.addInteractiveDemo(circularIndeterminate);
        circularIndeterminateState.textContent = "indeterminate: " + circularIndeterminate.indeterminate;

        assertTrue("circular indeterminate: true여야 함", circularIndeterminate.indeterminate);

        // Four Color Linear
        var fourColorLinearExample = addExampleCode(indeterminateSection,
            "📘 Four Color Linear (4색 선형)",
            "4가지 색상을 사용하는 무한 로딩 선형 진행률입니다.",
            """
            var progress = progress()
                .linear()
                .indeterminate(true)
                .fourColor(true)
                .element();
            """);
        var fourColorLinear = progress()
                .linear()
                .indeterminate(true)
                .fourColor(true)
                .element();
        var fourColorLinearState = fourColorLinearExample.addInteractiveDemo(fourColorLinear);
        fourColorLinearState.textContent = "indeterminate: " + fourColorLinear.indeterminate + 
            " | fourColor: " + fourColorLinear.fourColor;

        assertTrue("fourColor: true여야 함", fourColorLinear.fourColor);

        // Four Color Circular
        var fourColorCircularExample = addExampleCode(indeterminateSection,
            "📘 Four Color Circular (4색 원형)",
            "4가지 색상을 사용하는 무한 로딩 원형 진행률입니다.",
            """
            var progress = progress()
                .circular()
                .indeterminate(true)
                .fourColor(true)
                .element();
            """);
        var fourColorCircular = progress()
                .circular()
                .indeterminate(true)
                .fourColor(true)
                .element();
        var fourColorCircularState = fourColorCircularExample.addInteractiveDemo(fourColorCircular);
        fourColorCircularState.textContent = "indeterminate: " + fourColorCircular.indeterminate + 
            " | fourColor: " + fourColorCircular.fourColor;

        assertTrue("circular fourColor: true여야 함", fourColorCircular.fourColor);

        // Getter methods for indeterminate
        addExampleCode(indeterminateSection,
            "📘 Indeterminate Getters (무한 로딩 조회)",
            "무한 로딩 상태를 조회할 수 있습니다.",
            """
            var builder = progress()
                .linear()
                .indeterminate(true)
                .fourColor(true);
            
            boolean isIndeterminate = builder.isIndeterminate();
            boolean isFourColor = builder.isFourColor();
            """);
        
        var indeterminateBuilder = progress()
                .linear()
                .indeterminate(true)
                .fourColor(true);
        
        assertTrue("isIndeterminate(): true를 반환해야 함", indeterminateBuilder.isIndeterminate());
        assertTrue("isFourColor(): true를 반환해야 함", indeterminateBuilder.isFourColor());
    }
}
