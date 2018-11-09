package net.sayaya.ui.widget.textbox;

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;

import net.sayaya.ui.style.StyleLabel;
import net.sayaya.ui.style.StyleTextBox;
import net.sayaya.ui.widget.InputBase;

public class LongBox extends Composite implements InputBase<Long, LongBox> {
	private final com.google.gwt.user.client.ui.LongBox widget;
	public LongBox() {
		widget = new com.google.gwt.user.client.ui.LongBox();
		initWidget(widget);
		widget.addKeyPressHandler(evt->{
			 if(!Character.isDigit(evt.getCharCode())) widget.cancelKey();
		});
		style(this);
	}

	@Override
	public LongBox style(LongBox w) {
		w.setStyleName(StyleTextBox.GSS.textbox());
		w.addStyleName(StyleLabel.GSS.numeric());
		return this;
	}

	@Override
	public LongBox setValue(Long value) {
		widget.setValue(value);
		return this;
	}
	
	public LongBox setValue(Long value, boolean fireEvent) {
		widget.setValue(value, fireEvent);
		return this;
	}

	@Override
	public Long getValue() {
		return widget.getValue();
	}

	@Override
	public boolean isEmpty() {
		return widget.getValue()==null;
	}

	@Override
	public LongBox setEnabled(boolean enabled) {
		widget.setEnabled(enabled);
		return this;
	}
	
	@Override
	public boolean isEnabled() {
		return widget.isEnabled();
	}
	
	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Long> handler) {
		return widget.addValueChangeHandler(handler);
	}
	
	public LongBox selectAll() {
		widget.selectAll();
		return this;
	}
}
