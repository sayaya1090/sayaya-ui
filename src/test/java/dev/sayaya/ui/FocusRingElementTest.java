package dev.sayaya.ui;

import com.google.gwt.core.client.EntryPoint;
import dev.sayaya.ui.focusring.FocusRingAttachmentTest;
import dev.sayaya.ui.focusring.FocusRingBasicTest;

public class FocusRingElementTest implements EntryPoint {
    @Override
    public void onModuleLoad() {
        FocusRingBasicTest.test();
        FocusRingAttachmentTest.test();
    }
}
