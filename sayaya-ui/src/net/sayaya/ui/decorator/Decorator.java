package net.sayaya.ui.decorator;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;

public abstract class Decorator extends Composite {
	public Decorator(IsWidget w) {
		initWidget(w.asWidget());
	}
}
