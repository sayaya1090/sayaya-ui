package dev.sayaya.ui.divider;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.DividerElementBuilder.*;
import static org.jboss.elemento.Elements.*;

public class DividerFactoryTest {
    public static void test() {
        printSectionHeader("5. 팩토리 메서드 (Factory Methods)");
        printDescription("편리한 팩토리 메서드를 제공합니다.");
        printSeparator();

        var factorySection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(factorySection);

        factorySection.appendChild(h(3).text("Factory Methods").element());

        // dividerInset()
        var insetFactoryExample = addExampleCode(factorySection,
            "📘 dividerInset()",
            "inset이 적용된 구분선을 생성합니다.",
            """
            var divider = dividerInset().element();
            """);
        var insetDivider = dividerInset().element();
        insetFactoryExample.addInteractiveDemo(insetDivider, false);
        assertTrue("팩토리 메서드 dividerInset: inset이 true", insetDivider.inset);

        // dividerInsetStart()
        var startFactoryExample = addExampleCode(factorySection,
            "📘 dividerInsetStart()",
            "insetStart가 적용된 구분선을 생성합니다.",
            """
            var divider = dividerInsetStart().element();
            """);
        var insetStartDivider = dividerInsetStart().element();
        startFactoryExample.addInteractiveDemo(insetStartDivider, false);
        assertTrue("팩토리 메서드 dividerInsetStart: insetStart가 true", insetStartDivider.insetStart);

        // dividerInsetEnd()
        var endFactoryExample = addExampleCode(factorySection,
            "📘 dividerInsetEnd()",
            "insetEnd가 적용된 구분선을 생성합니다.",
            """
            var divider = dividerInsetEnd().element();
            """);
        var insetEndDivider = dividerInsetEnd().element();
        endFactoryExample.addInteractiveDemo(insetEndDivider, false);
        assertTrue("팩토리 메서드 dividerInsetEnd: insetEnd가 true", insetEndDivider.insetEnd);

        // Mutual exclusivity test
        var exclusivityExample = addExampleCode(factorySection,
            "📘 Mutual Exclusivity",
            "inset 속성들은 상호 배타적입니다.",
            """
            var divider = dividerInset()
                .insetStart()
                .element();
            // inset이 false가 되고 insetStart가 true가 됩니다
            """);
        var switchedDivider = dividerInset()
                .insetStart()
                .element();
        exclusivityExample.addInteractiveDemo(switchedDivider, false);
        assertFalse("상호 배타성: inset이 false", switchedDivider.inset);
        assertTrue("상호 배타성: insetStart가 true", switchedDivider.insetStart);

        var switchedDivider2 = dividerInsetStart()
                .insetEnd()
                .element();
        assertFalse("상호 배타성: insetStart가 false", switchedDivider2.insetStart);
        assertTrue("상호 배타성: insetEnd가 true", switchedDivider2.insetEnd);
    }
}
