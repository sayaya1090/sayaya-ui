package net.sayaya.ui.widget.textbox;

import com.google.gwt.user.client.ui.Composite;

import net.sayaya.ui.style.StyleTextBox;
import net.sayaya.ui.widget.InputBase;

public final class TextBox extends Composite implements InputBase<String, TextBox> {
	private final com.google.gwt.user.client.ui.TextBox widget;
	public TextBox() {
		widget = new com.google.gwt.user.client.ui.TextBox();
		initWidget(widget);
		style(this);
	}

	@Override
	public TextBox style(TextBox w) {
		w.setStyleName(StyleTextBox.GSS.textbox());
		return this;
	}

	@Override
	public TextBox setValue(String value) {
		widget.setValue(value);
		return this;
	}
	
	public TextBox setValue(String value, boolean fireEvent) {
		widget.setValue(value, fireEvent);
		return this;
	}

	@Override
	public String getValue() {
		return widget.getValue();
	}

	@Override
	public boolean isEmpty() {
		return widget.getValue()==null || widget.getValue().trim().isEmpty();
	}

	@Override
	public TextBox setEnabled(boolean enabled) {
		widget.setEnabled(enabled);
		return this;
	}
}
