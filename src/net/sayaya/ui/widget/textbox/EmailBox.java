package net.sayaya.ui.widget.textbox;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;

import net.sayaya.ui.style.StyleTextBox;
import net.sayaya.ui.widget.InputBase;

public final class EmailBox extends Composite implements InputBase<String, EmailBox> {
	private final com.google.gwt.user.client.ui.TextBox widget;
	public EmailBox() {
		Element elem = Document.get().createElement("input");
		Document.get().getBody().appendChild(elem);
		widget = com.google.gwt.user.client.ui.TextBox.wrap(elem);
		elem.setPropertyString("type", "email");
		initWidget(widget);
		style(this);
	}

	@Override
	public EmailBox style(EmailBox w) {
		w.setStyleName(StyleTextBox.GSS.textbox());
		return this;
	}

	@Override
	public EmailBox setValue(String value) {
		widget.setValue(value);
		return this;
	}
	
	public EmailBox setValue(String value, boolean fireEvent) {
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
	public EmailBox setEnabled(boolean enabled) {
		widget.setEnabled(enabled);
		return this;
	}
	
	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
		return widget.addValueChangeHandler(handler);
	}
	
	public EmailBox selectAll() {
		widget.selectAll();
		return this;
	}
}
