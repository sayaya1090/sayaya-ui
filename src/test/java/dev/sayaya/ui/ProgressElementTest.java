package dev.sayaya.ui;

import com.google.gwt.core.client.EntryPoint;
import dev.sayaya.ui.progress.ProgressIndeterminateTest;
import dev.sayaya.ui.progress.ProgressPropertiesTest;
import dev.sayaya.ui.progress.ProgressVariantsTest;

public class ProgressElementTest implements EntryPoint {
    @Override
    public void onModuleLoad() {
        ProgressVariantsTest.test();
        ProgressPropertiesTest.test();
        ProgressIndeterminateTest.test();
    }
}
