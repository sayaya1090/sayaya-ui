package dev.sayaya.ui;

import com.google.gwt.core.client.EntryPoint;
import dev.sayaya.ui.icon.IconAccessibilityTest;
import dev.sayaya.ui.icon.IconBasicTest;
import dev.sayaya.ui.icon.IconExamplesTest;
import dev.sayaya.ui.icon.IconStylingTest;

public class IconElementTest implements EntryPoint {
    @Override
    public void onModuleLoad() {
        IconBasicTest.test();
        IconAccessibilityTest.test();
        IconStylingTest.test();
        IconExamplesTest.test();
    }
}
