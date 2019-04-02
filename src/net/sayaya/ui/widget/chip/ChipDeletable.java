package net.sayaya.ui.widget.chip;

import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Event;

import net.sayaya.ui.handler.Callback;
import net.sayaya.ui.icon.Icon;
import net.sayaya.ui.style.StyleChip;
import net.sayaya.ui.widget.Chip;

public class ChipDeletable extends Chip {
	private final Icon delete = Icon.create(Icon.GSS.timesCircle());
	private final String text;
	private final Callback<String> callback;
	public ChipDeletable(String text, Callback<String> deletedCallback) {
		super();
		delete.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		delete.getElement().getStyle().setMarginLeft(5, Unit.PX);
		delete.getElement().getStyle().setProperty("fontSize", "15px");
		setValue(text);
		this.text = text;
		this.callback = deletedCallback;
		this.getElement().appendChild(delete.getElement());
		Event.sinkEvents(delete.getElement(), Event.ONCLICK);
		Event.setEventListener(delete.getElement(), evt->{
			if("click".equals(evt.getType())) delete();
		});
	}
	
	@Override
	public ChipDeletable style(Chip widet) {
		setStyleName(StyleChip.GSS.chip());
		getElement().getStyle().setPaddingRight(5, Unit.PX);
		return this;
	}
	
	public ChipDeletable delete() {
		removeFromParent();
		callback.onSuccess(text);
		return this;
	}
}
