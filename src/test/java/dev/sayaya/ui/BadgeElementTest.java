package dev.sayaya.ui;

import com.google.gwt.core.client.EntryPoint;
import dev.sayaya.ui.badge.BadgeWithContentTest;
import dev.sayaya.ui.badge.BasicBadgeTest;

public class BadgeElementTest implements EntryPoint {
    @Override
    public void onModuleLoad() {
        BasicBadgeTest.test();
        BadgeWithContentTest.test();
    }
}
