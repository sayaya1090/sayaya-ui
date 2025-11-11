package dev.sayaya.ui;

import com.google.gwt.core.client.EntryPoint;
import dev.sayaya.ui.checkbox.CheckboxAccessibilityTest;
import dev.sayaya.ui.checkbox.CheckboxEventHandlingTest;
import dev.sayaya.ui.checkbox.CheckboxPropertiesTest;
import dev.sayaya.ui.checkbox.CheckboxStatesTest;

public class CheckboxElementTest implements EntryPoint {
    @Override
    public void onModuleLoad() {
        CheckboxStatesTest.test();
        CheckboxPropertiesTest.test();
        CheckboxEventHandlingTest.test();
        CheckboxAccessibilityTest.test();
    }
}
