package net.sayaya.ui.widget.textbox;

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;

import net.sayaya.ui.style.StyleLabel;
import net.sayaya.ui.style.StyleTextBox;
import net.sayaya.ui.widget.InputBase;

public class DoubleBox extends Composite implements InputBase<Double, DoubleBox> {
	private final com.google.gwt.user.client.ui.DoubleBox widget;
	public DoubleBox() {
		widget = new com.google.gwt.user.client.ui.DoubleBox();
		initWidget(widget);
		widget.addKeyPressHandler(evt->{
			if(evt.getCharCode()=='.' && widget.getText().contains(".")) widget.cancelKey();
			if(!Character.isDigit(evt.getCharCode())
			&& evt.getCharCode() != '.') widget.cancelKey();
		});
		style(this);
	}

	@Override
	public DoubleBox style(DoubleBox w) {
		w.setStyleName(StyleTextBox.GSS.textbox());
		w.addStyleName(StyleLabel.GSS.numeric());
		return this;
	}

	@Override
	public DoubleBox setValue(Double value) {
		if(value==null) widget.setValue(null);
		else widget.setValue(value.doubleValue());
		return this;
	}
	
	public DoubleBox setValue(Double value, boolean fireEvent) {
		if(value==null) widget.setValue(null, fireEvent);
		else widget.setValue(value.doubleValue(), fireEvent);
		return this;
	}

	@Override
	public Double getValue() {
		return widget.getValue();
	}

	@Override
	public boolean isEmpty() {
		return widget.getValue()==null;
	}
	
	@Override
	public DoubleBox setEnabled(boolean enabled) {
		widget.setEnabled(enabled);
		return this;
	}
	
	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Double> handler) {
		return widget.addValueChangeHandler(handler);
	}
}
