package net.sayaya.ui.widget.textbox;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;

import net.sayaya.ui.style.StyleTextBox;
import net.sayaya.ui.widget.InputBase;

public final class RangeBox extends Composite implements InputBase<String, RangeBox> {
	private final com.google.gwt.user.client.ui.TextBox widget;
	public RangeBox(Double min, Double max, Double step) {
		Element elem = Document.get().createElement("input");
		Document.get().getBody().appendChild(elem);
		widget = com.google.gwt.user.client.ui.TextBox.wrap(elem);
		elem.setPropertyString("type", "range");
		setMin(min);
		setMax(max);
		setStep(step);
		initWidget(widget);
		style(this);
	}

	@Override
	public RangeBox style(RangeBox w) {
		w.setStyleName(StyleTextBox.GSS.textbox());
		return this;
	}

	@Override
	public RangeBox setValue(String value) {
		widget.setValue(value);
		return this;
	}
	
	public RangeBox setValue(String value, boolean fireEvent) {
		widget.setValue(value, fireEvent);
		return this;
	}
	
	public RangeBox setMin(Double min) {
		widget.getElement().setPropertyDouble("min", min);
		return this;
	}
	
	public RangeBox setMax(Double max) {
		widget.getElement().setPropertyDouble("max", max);
		return this;
	}
	
	public RangeBox setStep(Double step) {
		widget.getElement().setPropertyDouble("step", step);
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
	public RangeBox setEnabled(boolean enabled) {
		widget.setEnabled(enabled);
		return this;
	}
	
	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
		return widget.addValueChangeHandler(handler);
	}
	
	public RangeBox selectAll() {
		widget.selectAll();
		return this;
	}
}
