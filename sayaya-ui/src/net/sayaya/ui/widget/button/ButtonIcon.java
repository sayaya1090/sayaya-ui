package net.sayaya.ui.widget.button;

import net.sayaya.ui.icon.Icon;
import net.sayaya.ui.style.StyleButton;
import net.sayaya.ui.widget.Button;

public class ButtonIcon extends Button<ButtonIcon> {
	public ButtonIcon(Icon icon) {
		super(icon);
	}
	
	@Override
	public ButtonIcon style(ButtonIcon widet) {
		setStyleName(StyleButton.GSS.icon());
		return this;
	}
}
