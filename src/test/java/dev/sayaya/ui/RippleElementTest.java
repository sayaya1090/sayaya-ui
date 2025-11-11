package dev.sayaya.ui;

import com.google.gwt.core.client.EntryPoint;
import dev.sayaya.ui.ripple.RippleAttachmentTest;
import dev.sayaya.ui.ripple.RippleBasicTest;

public class RippleElementTest implements EntryPoint {
    @Override
    public void onModuleLoad() {
        RippleBasicTest.test();
        RippleAttachmentTest.test();
    }
}
