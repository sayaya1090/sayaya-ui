package dev.sayaya.ui;

import com.google.gwt.core.client.EntryPoint;
import dev.sayaya.ui.select.*;

import static dev.sayaya.ui.TestHelper.printSectionHeader;

public class SelectElementTest implements EntryPoint {
    @Override
    public void onModuleLoad() {
        printSectionHeader("=== Material Design Select 개발자 매뉴얼 ===");

        SelectVariantsTest.test();
        BasicPropertiesTest.test();
        OptionPropertiesTest.test();
        SelectionMethodsTest.test();
        EventHandlingTest.test();
        ValidationTest.test();
        AdvancedFeaturesTest.test();

        printSectionHeader("=== 모든 테스트 완료 ===");
    }
}
