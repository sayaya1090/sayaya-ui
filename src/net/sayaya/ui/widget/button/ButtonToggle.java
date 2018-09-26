package net.sayaya.ui.widget.button;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;

import net.sayaya.ui.handler.HasValue;
import net.sayaya.ui.icon.Icon;
import net.sayaya.ui.style.StyleButton;
import net.sayaya.ui.widget.Button;

public class ButtonToggle extends Button<ButtonToggle> implements HasValue<Boolean>, HasValueChangeHandlers<Boolean> {
	private final EventBus bus = new SimpleEventBus();
	private boolean value = false;
	
	public ButtonToggle(Icon icon) {
		super(icon);
	}
	
	@Override
	public ButtonToggle style(ButtonToggle widet) {
		setStyleName(StyleButton.GSS.icon());
		addClickHandler(evt->{if(isEnabled()) toggle();});
		return this;
	}
	
	public ButtonToggle toggle() {
		setValue(!value);
		return this;
	}

	@Override
	public ButtonToggle setValue(Boolean value) {
		this.value = value;
		if(value) addStyleName(StyleButton.GSS.toggleTrue());
		else removeStyleName(StyleButton.GSS.toggleTrue());
		ValueChangeEvent.fire(this, value);
		return this;
	}

	@Override
	public Boolean getValue() {
		return value;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Boolean> handler) {
		return bus.addHandler(ValueChangeEvent.getType(), handler);
	}
	
	@Override
	public void fireEvent(GwtEvent<?> event) {
		if(event instanceof ValueChangeEvent) bus.fireEvent(event);
		else super.fireEvent(event);
	}
}
