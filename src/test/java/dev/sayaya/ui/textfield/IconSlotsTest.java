package dev.sayaya.ui.textfield;

import org.jboss.elemento.InputType;

import static dev.sayaya.ui.elements.TextFieldElementBuilder.textField;
import static org.jboss.elemento.Elements.*;

public class IconSlotsTest {
    public static void test() {
        TestHelper.printSectionHeader("7. 아이콘 슬롯 (Icon Slots)");
        TestHelper.printDescription("TextField에 아이콘을 추가:");
        TestHelper.printDescription("- iconLeading(): 앞 아이콘");
        TestHelper.printDescription("- iconTrailing(): 뒤 아이콘");
        TestHelper.printSeparator();

        var iconSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(iconSection);

        iconSection.appendChild(h(3).text("Icon Slots").element());

        // Leading icon
        TestHelper.addExampleCode(iconSection,
            "📘 Leading Icon (앞 아이콘)",
            "입력 필드 앞에 아이콘을 배치합니다. Material Symbols 아이콘명을 사용합니다.",
            """
            var searchField = textField().filled()
                .label("검색")
                .iconLeading("search")
                .element();
            """);
        var leadingIconField = textField().filled()
                .label("검색")
                .iconLeading("search")
                .element();
        iconSection.appendChild(leadingIconField);
        var leadingIcon = leadingIconField.querySelector("[slot='leading-icon']");
        TestHelper.assertNotNull("leading icon: 아이콘이 존재해야 함", leadingIcon);

        // Trailing icon
        TestHelper.addExampleCode(iconSection,
            "📘 Trailing Icon (뒤 아이콘)",
            "입력 필드 뒤에 아이콘을 배치합니다. 비밀번호 표시/숨김 등에 사용됩니다.",
            """
            var passwordField = textField().outlined()
                .label("비밀번호")
                .type(InputType.password)
                .iconTrailing("visibility")
                .element();
            """);
        var trailingIconField = textField().outlined()
                .label("비밀번호")
                .type(InputType.password)
                .iconTrailing("visibility")
                .element();
        iconSection.appendChild(trailingIconField);
        var trailingIcon = trailingIconField.querySelector("[slot='trailing-icon']");
        TestHelper.assertNotNull("trailing icon: 아이콘이 존재해야 함", trailingIcon);

        // Both icons
        TestHelper.addExampleCode(iconSection,
            "📘 양쪽 아이콘",
            "앞뒤 모두 아이콘을 배치할 수 있습니다.",
            """
            var priceField = textField().filled()
                .label("금액")
                .iconLeading("attach_money")
                .iconTrailing("info")
                .element();
            """);
        var bothIconsField = textField().filled()
                .label("금액")
                .iconLeading("attach_money")
                .iconTrailing("info")
                .element();
        iconSection.appendChild(bothIconsField);
        var leading = bothIconsField.querySelector("[slot='leading-icon']");
        var trailing = bothIconsField.querySelector("[slot='trailing-icon']");
        TestHelper.assertNotNull("both icons: leading 아이콘이 존재해야 함", leading);
        TestHelper.assertNotNull("both icons: trailing 아이콘이 존재해야 함", trailing);
    }
}
