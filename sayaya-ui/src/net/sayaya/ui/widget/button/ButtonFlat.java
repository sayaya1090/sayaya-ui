package net.sayaya.ui.widget.button;

import net.sayaya.ui.style.StyleButton;
import net.sayaya.ui.widget.Button;

public class ButtonFlat extends Button<ButtonFlat> {
	@Override
	public ButtonFlat style(ButtonFlat widet) {
		setStyleName(StyleButton.GSS.flat());
		return this;
	}
}
