package net.sayaya.ui.widget;

import com.google.gwt.user.client.ui.Composite;

import net.sayaya.ui.handler.HasStyle;
import net.sayaya.ui.style.StyleLabel;

public class Label extends Composite implements HasStyle<Label> {
	private final com.google.gwt.user.client.ui.Label widget = new com.google.gwt.user.client.ui.Label();
	public Label() {
		initWidget(widget);
		style(this);
	}
	
	public Label style(Label w) {
		w.setStyleName(StyleLabel.GSS.label());
		return this;
	}
	
	public Label setValue(String text) {
		widget.setText(text);
		return this;
	}
}
