package net.sayaya.ui.widget.textbox;

import java.util.Date;

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Composite;

import net.sayaya.ui.style.StyleTextBox;
import net.sayaya.ui.widget.InputBase;

public class DateBox extends Composite implements InputBase<Date, DateBox> {
	private final com.google.gwt.user.datepicker.client.DateBox widget;
	public DateBox() {
		widget = new com.google.gwt.user.datepicker.client.DateBox();
		initWidget(widget);
		style(this);
	}

	@Override
	public DateBox style(DateBox w) {
		w.setStyleName(StyleTextBox.GSS.textbox());
		return this;
	}

	@Override
	public DateBox setValue(Date value) {
		widget.setValue(value);
		return this;
	}
	
	public DateBox setValue(Date value, boolean fireEvent) {
		widget.setValue(value, fireEvent);
		return this;
	}

	@Override
	public Date getValue() {
		return widget.getValue();
	}
	
	@Override
	public boolean isEnabled() {
		return widget.isEnabled();
	}

	@Override
	public boolean isEmpty() {
		if(widget.isDatePickerShowing()) return false;
		return widget.getValue()==null;
	}

	@Override
	public DateBox setEnabled(boolean enabled) {
		widget.setEnabled(enabled);
		return this;
	}
	
	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Date> handler) {
		return widget.addValueChangeHandler(handler);
	}
	
	public DateBox setFormat(String format) {
		DateTimeFormat dtf = DateTimeFormat.getFormat(format);
		widget.setFormat(new com.google.gwt.user.datepicker.client.DateBox.DefaultFormat(dtf));
		return this;
	}
}
