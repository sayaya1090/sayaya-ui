package net.sayaya.ui.widget.textbox;

import com.google.gwt.event.dom.client.DomEvent.Type;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;

import net.sayaya.ui.style.StyleLabel;
import net.sayaya.ui.style.StyleTextBox;
import net.sayaya.ui.widget.InputBase;

public class NumberBox extends Composite implements InputBase<Number, NumberBox> {
	private final com.google.gwt.user.client.ui.DoubleBox widget;
	public NumberBox() {
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
	public NumberBox style(NumberBox w) {
		w.setStyleName(StyleTextBox.GSS.textbox());
		w.addStyleName(StyleLabel.GSS.numeric());
		return this;
	}
	
	public <H extends EventHandler> HandlerRegistration addHandler(H handler, Type<H> type) {
		return widget.addDomHandler(handler, type);
	}

	@Override
	public NumberBox setValue(Number value) {
		if(value==null) widget.setValue(null);
		else widget.setValue(value.doubleValue());
		return this;
	}
	
	public NumberBox setValue(Number value, boolean fireEvent) {
		if(value==null) widget.setValue(null, fireEvent);
		else widget.setValue(value.doubleValue(), fireEvent);
		return this;
	}

	@Override
	public Number getValue() {
		return widget.getValue();
	}

	@Override
	public boolean isEmpty() {
		return widget.getValue()==null;
	}
	
	@Override
	public NumberBox setEnabled(boolean enabled) {
		widget.setEnabled(enabled);
		return this;
	}
}
