package dev.sayaya.ui;

import com.google.gwt.core.client.EntryPoint;
import dev.sayaya.ui.divider.*;

public class DividerElementTest implements EntryPoint {
    @Override
    public void onModuleLoad() {
        DividerBasicTest.test();
        DividerInsetTest.test();
        DividerInsetDirectionTest.test();
        DividerAccessibilityTest.test();
        DividerFactoryTest.test();
    }
}
