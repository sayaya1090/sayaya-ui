package dev.sayaya.ui;

import com.google.gwt.core.client.EntryPoint;
import dev.sayaya.ui.radio.RadioBasicUsageTest;
import dev.sayaya.ui.radio.RadioEventHandlingTest;
import dev.sayaya.ui.radio.RadioGroupTest;
import dev.sayaya.ui.radio.RadioPropertiesTest;

public class RadioElementTest implements EntryPoint {
    @Override
    public void onModuleLoad() {
        RadioBasicUsageTest.test();
        RadioPropertiesTest.test();
        RadioGroupTest.test();
        RadioEventHandlingTest.test();
    }
}
