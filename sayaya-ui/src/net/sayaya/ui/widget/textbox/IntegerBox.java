package net.sayaya.ui.widget.textbox;

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;

import net.sayaya.ui.style.StyleLabel;
import net.sayaya.ui.style.StyleTextBox;
import net.sayaya.ui.widget.InputBase;

public class IntegerBox extends Composite implements InputBase<Integer, IntegerBox> {
	private final com.google.gwt.user.client.ui.IntegerBox widget;
	public IntegerBox() {
		widget = new com.google.gwt.user.client.ui.IntegerBox();
		initWidget(widget);
		widget.addKeyPressHandler(evt->{
			 if(!Character.isDigit(evt.getCharCode())) widget.cancelKey();
		});
		style(this);
	}

	@Override
	public IntegerBox style(IntegerBox w) {
		w.setStyleName(StyleTextBox.GSS.textbox());
		w.addStyleName(StyleLabel.GSS.numeric());
		return this;
	}

	@Override
	public IntegerBox setValue(Integer value) {
		widget.setValue(value);
		return this;
	}
	
	public IntegerBox setValue(Integer value, boolean fireEvent) {
		widget.setValue(value, fireEvent);
		return this;
	}

	@Override
	public Integer getValue() {
		return widget.getValue();
	}

	@Override
	public boolean isEmpty() {
		return widget.getValue()==null;
	}

	@Override
	public IntegerBox setEnabled(boolean enabled) {
		widget.setEnabled(enabled);
		return this;
	}
	
	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Integer> handler) {
		return widget.addValueChangeHandler(handler);
	}
}
