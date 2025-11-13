package dev.sayaya.ui;

import com.google.gwt.core.client.EntryPoint;
import dev.sayaya.ui.slider.SliderEventHandlingTest;
import dev.sayaya.ui.slider.SliderPropertiesTest;
import dev.sayaya.ui.slider.SliderRangeTest;
import dev.sayaya.ui.slider.SliderVariantsTest;

public class SliderElementTest implements EntryPoint {
    @Override
    public void onModuleLoad() {
        SliderVariantsTest.test();
        SliderPropertiesTest.test();
        SliderRangeTest.test();
        SliderEventHandlingTest.test();
    }
}
