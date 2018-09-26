package net.sayaya.ui.widget.button;

import net.sayaya.ui.style.StyleButton;
import net.sayaya.ui.widget.Button;

public class ButtonContained extends Button<ButtonContained> {
	@Override
	public ButtonContained style(ButtonContained widet) {
		setStyleName(StyleButton.GSS.contained());
		return this;
	}
}
