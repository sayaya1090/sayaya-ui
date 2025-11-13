package dev.sayaya.ui;

import com.google.gwt.core.client.EntryPoint;
import dev.sayaya.ui.tabs.TabsPanelTest;
import dev.sayaya.ui.tabs.TabsPropertiesTest;
import dev.sayaya.ui.tabs.TabsVariantsTest;

public class TabsElementTest implements EntryPoint {
    @Override
    public void onModuleLoad() {
        TabsVariantsTest.test();
        TabsPropertiesTest.test();
        TabsPanelTest.test();
    }
}
