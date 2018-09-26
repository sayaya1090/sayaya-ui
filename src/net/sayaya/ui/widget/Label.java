package net.sayaya.ui.widget;

import com.google.gwt.user.client.ui.Composite;

import net.sayaya.ui.handler.HasStyle;
import net.sayaya.ui.style.StyleLabel;

public class Label extends Composite implements HasStyle<Label> {
	private final com.google.gwt.user.client.ui.HTML widget = new com.google.gwt.user.client.ui.HTML();
	public Label(String label) {
		this();
		setValue(label);
	}
	public Label() {
		initWidget(widget);
		style(this);
	}
	
	public Label style(Label w) {
		w.setStyleName(StyleLabel.GSS.label());
		return this;
	}
	
	public Label setValue(String text) {
		widget.setHTML(text);
		return this;
	}
	
	public String getValue() {
		return widget.getText();
	}
}
