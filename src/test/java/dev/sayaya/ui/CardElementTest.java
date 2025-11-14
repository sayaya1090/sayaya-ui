package dev.sayaya.ui;

import com.google.gwt.core.client.EntryPoint;
import dev.sayaya.ui.card.BasicCardTest;
import dev.sayaya.ui.card.CardVariantsTest;
import dev.sayaya.ui.card.CardWithContentTest;

public class CardElementTest implements EntryPoint {
    @Override
    public void onModuleLoad() {
        BasicCardTest.test();
        CardVariantsTest.test();
        CardWithContentTest.test();
    }
}
