package net.sayaya.ui.widget.chip;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;

import net.sayaya.ui.handler.HasValue;
import net.sayaya.ui.icon.Icon;
import net.sayaya.ui.style.StyleChip;
import net.sayaya.ui.widget.Chip;

public class ChipToggle extends Chip implements HasValue<Boolean>, HasValueChangeHandlers<Boolean> {
	private final EventBus bus = new SimpleEventBus();
	private final Icon icon = Icon.create(Icon.GSS.check());
	private boolean value = false;
	
	public ChipToggle(String text) {
		super(text);
		this.getElement().insertFirst(icon.getElement());
	}
	
	@Override
	public ChipToggle style(Chip widet) {
		setStyleName(StyleChip.GSS.chip());
		addStyleName(StyleChip.GSS.toggle());
		addClickHandler(evt->{if(isEnabled()) toggle();});
		return this;
	}
	
	public ChipToggle toggle() {
		setValue(!value);
		return this;
	}

	@Override
	public ChipToggle setValue(Boolean value) {
		this.value = value;
		if(value) {
			addStyleName(StyleChip.GSS.toggleTrue());
			removeStyleName(StyleChip.GSS.toggleFalse());
		} else {
			addStyleName(StyleChip.GSS.toggleFalse());
			removeStyleName(StyleChip.GSS.toggleTrue());
		}
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

