package net.sayaya.ui.widget.chip;

import com.google.gwt.dom.client.Style.Unit;

import net.sayaya.ui.icon.Icon;
import net.sayaya.ui.widget.Chip;

public class ChipIcon extends Chip {
	public ChipIcon(Icon icon, String text) {
		super(text);
		icon.getElement().getStyle().setMarginRight(5, Unit.PX);
		this.getElement().insertFirst(icon.getElement());
		this.getElement().getStyle().setPaddingLeft(5, Unit.PX);
	}
}
