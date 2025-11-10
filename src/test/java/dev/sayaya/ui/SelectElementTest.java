package dev.sayaya.ui;

import com.google.gwt.core.client.EntryPoint;
import dev.sayaya.ui.select.*;

public class SelectElementTest implements EntryPoint {
    @Override
    public void onModuleLoad() {
        SelectVariantsTest.test();
        BasicPropertiesTest.test();
        OptionPropertiesTest.test();
        SelectionMethodsTest.test();
        EventHandlingTest.test();
        ValidationTest.test();
        AdvancedFeaturesTest.test();
    }
}
