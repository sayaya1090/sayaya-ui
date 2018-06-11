package net.sayaya.ui.widget.textbox;

import com.google.gwt.event.dom.client.DomEvent.Type;
import com.google.gwt.event.shared.EventHandler;
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
	
	public <H extends EventHandler> HandlerRegistration addHandler(H handler, Type<H> type) {
		return widget.addDomHandler(handler, type);
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
}
