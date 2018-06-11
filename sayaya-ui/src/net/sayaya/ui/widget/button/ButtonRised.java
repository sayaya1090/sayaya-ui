package net.sayaya.ui.widget.button;

import net.sayaya.ui.style.StyleButton;
import net.sayaya.ui.widget.Button;

public class ButtonRised extends Button<ButtonRised> {
	@Override
	public ButtonRised style(ButtonRised widet) {
		setStyleName(StyleButton.GSS.rised());
		return this;
	}
}
