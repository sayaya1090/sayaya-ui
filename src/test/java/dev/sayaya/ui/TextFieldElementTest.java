package dev.sayaya.ui;

import com.google.gwt.core.client.EntryPoint;
import dev.sayaya.ui.textfield.*;

import static elemental2.dom.DomGlobal.console;

public class TextFieldElementTest implements EntryPoint {
    @Override
    public void onModuleLoad() {
        printSectionHeader("=== Material Design TextField 테스트 ===");

        TextFieldVariantsTest.test();
        BasicPropertiesTest.test();
        InputTypesTest.test();
        ValidationTest.test();
        ConstraintValidationTest.test();
        CustomValidationTest.test();
        IconSlotsTest.test();
        PrefixSuffixTest.test();
        AdvancedFeaturesTest.test();
        EventHandlingTest.test();

        printSectionHeader("=== 모든 테스트 완료 ===");
    }

    private static void printSectionHeader(String header) {
        console.log("\n" + "=".repeat(60));
        console.log(header);
        console.log("=".repeat(60));
    }
}
