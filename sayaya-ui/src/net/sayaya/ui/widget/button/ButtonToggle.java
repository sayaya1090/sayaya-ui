package net.sayaya.ui.widget.button;

import net.sayaya.ui.handler.HasValue;
import net.sayaya.ui.icon.Icon;
import net.sayaya.ui.style.StyleButton;
import net.sayaya.ui.widget.Button;

public class ButtonToggle extends Button<ButtonToggle> implements HasValue<Boolean> {
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
}
