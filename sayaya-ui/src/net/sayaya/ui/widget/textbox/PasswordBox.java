package net.sayaya.ui.widget.textbox;

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;

import net.sayaya.ui.style.StyleTextBox;
import net.sayaya.ui.widget.InputBase;

public final class PasswordBox extends Composite implements InputBase<String, PasswordBox> {
	private final com.google.gwt.user.client.ui.PasswordTextBox widget;
	public PasswordBox() {
		widget = new com.google.gwt.user.client.ui.PasswordTextBox();
		initWidget(widget);
		style(this);
	}

	@Override
	public PasswordBox style(PasswordBox w) {
		w.setStyleName(StyleTextBox.GSS.textbox());
		return this;
	}

	@Override
	public PasswordBox setValue(String value) {
		widget.setValue(value);
		return this;
	}
	
	public PasswordBox setValue(String value, boolean fireEvent) {
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
	public PasswordBox setEnabled(boolean enabled) {
		widget.setEnabled(enabled);
		return this;
	}
	
	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
		return widget.addValueChangeHandler(handler);
	}
}
