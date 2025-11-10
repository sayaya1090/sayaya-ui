package dev.sayaya.ui;

import com.google.gwt.core.client.EntryPoint;
import dev.sayaya.ui.button.*;

public class ButtonElementTest implements EntryPoint {
    @Override
    public void onModuleLoad() {
        ButtonVariantsTest.test();
        BasicPropertiesTest.test();
        FormPropertiesTest.test();
        IconSupportTest.test();
        EventHandlingTest.test();
        AccessibilityTest.test();
        UseCasesTest.test();
        IconButtonTest.test();
    }
}
