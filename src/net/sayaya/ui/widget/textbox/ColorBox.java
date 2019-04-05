package net.sayaya.ui.widget.textbox;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;

import net.sayaya.ui.style.StyleTextBox;
import net.sayaya.ui.widget.InputBase;

public final class ColorBox extends Composite implements InputBase<String, ColorBox> {
	private final com.google.gwt.user.client.ui.TextBox widget;
	public ColorBox() {
		Element elem = DOM.createElement("input");
		widget = com.google.gwt.user.client.ui.TextBox.wrap(elem);
		elem.setPropertyString("type", "color");
		initWidget(widget);
		style(this);
	}

	@Override
	public ColorBox style(ColorBox w) {
		w.setStyleName(StyleTextBox.GSS.textbox());
		return this;
	}

	@Override
	public ColorBox setValue(String value) {
		widget.setValue(value);
		return this;
	}
	
	public ColorBox setValue(String value, boolean fireEvent) {
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
	public ColorBox setEnabled(boolean enabled) {
		widget.setEnabled(enabled);
		return this;
	}
	
	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
		return widget.addValueChangeHandler(handler);
	}
	
	public ColorBox selectAll() {
		widget.selectAll();
		return this;
	}
}
