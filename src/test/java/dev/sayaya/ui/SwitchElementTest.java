package dev.sayaya.ui;

import com.google.gwt.core.client.EntryPoint;
import dev.sayaya.ui.switchtest.SwitchBasicTest;
import dev.sayaya.ui.switchtest.SwitchEventHandlingTest;
import dev.sayaya.ui.switchtest.SwitchPropertiesTest;
import dev.sayaya.ui.switchtest.SwitchValidationTest;

public class SwitchElementTest implements EntryPoint {
    @Override
    public void onModuleLoad() {
        SwitchBasicTest.test();
        SwitchPropertiesTest.test();
        SwitchEventHandlingTest.test();
        SwitchValidationTest.test();
    }
}
