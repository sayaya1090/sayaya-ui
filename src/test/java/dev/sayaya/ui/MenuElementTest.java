package dev.sayaya.ui;

import com.google.gwt.core.client.EntryPoint;
import dev.sayaya.ui.menu.BasicMenuTest;
import dev.sayaya.ui.menu.MenuAdvancedTest;
import dev.sayaya.ui.menu.MenuItemPropertiesTest;
import dev.sayaya.ui.menu.SubMenuTest;

public class MenuElementTest implements EntryPoint {
    @Override
    public void onModuleLoad() {
        BasicMenuTest.test();
        MenuItemPropertiesTest.test();
        SubMenuTest.test();
        MenuAdvancedTest.test();
    }
}
