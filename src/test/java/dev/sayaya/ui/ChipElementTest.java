package dev.sayaya.ui;

import com.google.gwt.core.client.EntryPoint;
import dev.sayaya.ui.chip.*;

public class ChipElementTest implements EntryPoint {
    @Override
    public void onModuleLoad() {
        ChipTypesTest.test();
        BasicChipPropertiesTest.test();
        AssistChipPropertiesTest.test();
        FilterChipPropertiesTest.test();
        InputChipPropertiesTest.test();
        SuggestionChipPropertiesTest.test();
        ChipSetTest.test();
        ChipIconTest.test();
        ChipAccessibilityTest.test();
        ChipSelectionTest.test();
        ChipRemovalTest.test();
        ChipStateTest.test();
    }
}
