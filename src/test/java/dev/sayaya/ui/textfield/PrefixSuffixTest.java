package dev.sayaya.ui.textfield;

import static dev.sayaya.ui.elements.TextFieldElementBuilder.textField;
import static org.jboss.elemento.Elements.*;

public class PrefixSuffixTest {
    public static void test() {
        TestHelper.printSectionHeader("8. 접두사/접미사 (Prefix & Suffix)");
        TestHelper.printDescription("텍스트 입력 앞뒤에 고정 텍스트:");
        TestHelper.printDescription("- prefixText: 앞 텍스트");
        TestHelper.printDescription("- suffixText: 뒤 텍스트");
        TestHelper.printSeparator();

        var prefixSection = div()
                .style("margin", "20px")
                .style("padding", "20px")
                .style("border", "1px solid #ddd")
                .style("border-radius", "8px")
                .element();
        body().add(prefixSection);

        prefixSection.appendChild(h(3).text("Prefix & Suffix").element());

        // Prefix text
        TestHelper.addExampleCode(prefixSection,
            "📘 Prefix Text (접두사)",
            "입력 필드 앞에 고정 텍스트를 표시합니다. URL이나 통화 기호 등에 유용합니다.",
            """
            var urlField = textField().outlined()
                .label("웹사이트")
                .prefixText("https://")
                .element();
            """);
        var prefixField = textField().outlined()
                .label("웹사이트")
                .prefixText("https://")
                .element();
        prefixSection.appendChild(prefixField);
        TestHelper.assertEquals("prefixText 속성: 'https://'여야 함",
                "https://", prefixField.prefixText);

        // Suffix text
        TestHelper.addExampleCode(prefixSection,
            "📘 Suffix Text (접미사)",
            "입력 필드 뒤에 고정 텍스트를 표시합니다. 단위나 통화 기호 등에 유용합니다.",
            """
            var priceField = textField().filled()
                .label("가격")
                .suffixText("원")
                .element();
            """);
        var suffixField = textField().filled()
                .label("가격")
                .suffixText("원")
                .element();
        prefixSection.appendChild(suffixField);
        TestHelper.assertEquals("suffixText 속성: '원'이어야 함",
                "원", suffixField.suffixText);

        // Both prefix and suffix
        TestHelper.addExampleCode(prefixSection,
            "📘 Prefix + Suffix 조합",
            "접두사와 접미사를 함께 사용할 수 있습니다.",
            """
            var discountField = textField().outlined()
                .label("할인율")
                .prefixText("-")
                .suffixText("%")
                .element();
            """);
        var bothField = textField().outlined()
                .label("할인율")
                .prefixText("-")
                .suffixText("%")
                .element();
        prefixSection.appendChild(bothField);
        TestHelper.assertEquals("prefix와 suffix: prefixText는 '-'여야 함",
                "-", bothField.prefixText);
        TestHelper.assertEquals("prefix와 suffix: suffixText는 '%'여야 함",
                "%", bothField.suffixText);
    }
}
