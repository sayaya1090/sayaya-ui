package dev.sayaya.ui;

import com.google.gwt.core.client.EntryPoint;
import dev.sayaya.ui.button.*;

import static dev.sayaya.ui.TestHelper.printSectionHeader;

public class ButtonElementTest implements EntryPoint {
    @Override
    public void onModuleLoad() {
        printSectionHeader("=== Material Design Button 개발자 매뉴얼 ===");

        ButtonVariantsTest.test();
        BasicPropertiesTest.test();
        FormPropertiesTest.test();
        IconSupportTest.test();
        EventHandlingTest.test();
        AccessibilityTest.test();
        UseCasesTest.test();
        IconButtonTest.test();

        printSectionHeader("=== 모든 테스트 완료 ===");
    }
}
