package dev.sayaya.ui;

import com.google.gwt.core.client.EntryPoint;
import dev.sayaya.ui.list.*;

public class ListElementTest implements EntryPoint {
    @Override
    public void onModuleLoad() {
        BasicListTest.test();
        ListItemPropertiesTest.test();
        ListItemSlotsTest.test();
        ListItemTypesTest.test();
        ListWithDividersTest.test();
    }
}
