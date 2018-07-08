package net.sayaya.ui.widget.textbox;

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;

import net.sayaya.ui.style.StyleTextBox;
import net.sayaya.ui.widget.InputBase;

public class TextArea extends Composite implements InputBase<String, TextArea> {
	private final com.google.gwt.user.client.ui.TextArea widget;
	public TextArea() {
		widget = new com.google.gwt.user.client.ui.TextArea();
		initWidget(widget);
		style(this);
	}

	@Override
	public TextArea style(TextArea w) {
		w.setStyleName(StyleTextBox.GSS.textarea());
		return this;
	}

	@Override
	public TextArea setValue(String value) {
		widget.setValue(value);
		return this;
	}
		
	public TextArea setValue(String value, boolean fireEvent) {
		widget.setValue(value, fireEvent);
		return this;
	}

	@Override
	public String getValue() {
		return widget.getValue();
	}
	
	@Override
	public boolean isEnabled() {
		return widget.isEnabled();
	}

	@Override
	public boolean isEmpty() {
		return widget.getValue()==null || widget.getValue().trim().isEmpty();
	}

	@Override
	public TextArea setEnabled(boolean enabled) {
		widget.setEnabled(enabled);
		return this;
	}
	
	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
		return widget.addValueChangeHandler(handler);
	}
}
