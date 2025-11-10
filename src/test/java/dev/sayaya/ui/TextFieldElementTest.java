package dev.sayaya.ui;

import com.google.gwt.core.client.EntryPoint;
import dev.sayaya.ui.textfield.*;

public class TextFieldElementTest implements EntryPoint {
    @Override
    public void onModuleLoad() {
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
    }
}
