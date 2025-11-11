package dev.sayaya.ui;

import com.google.gwt.core.client.EntryPoint;
import dev.sayaya.ui.dialog.DialogBasicPropertiesTest;
import dev.sayaya.ui.dialog.DialogEventsTest;
import dev.sayaya.ui.dialog.DialogInteractionTest;
import dev.sayaya.ui.dialog.DialogSlotsTest;

public class DialogElementTest implements EntryPoint {
    @Override
    public void onModuleLoad() {
        DialogBasicPropertiesTest.test();
        DialogSlotsTest.test();
        DialogEventsTest.test();
        DialogInteractionTest.test();
    }
}
