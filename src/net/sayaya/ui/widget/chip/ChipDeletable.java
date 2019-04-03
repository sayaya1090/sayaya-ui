package net.sayaya.ui.widget.chip;

import com.google.gwt.core.client.Scheduler;
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
		super(text);
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
		addStyleName(StyleChip.GSS.delete());
		return this;
	}
	
	public ChipDeletable delete() {
		addStyleName(StyleChip.GSS.fadeOut());
		Scheduler.get().scheduleFixedDelay(()->{
			callback.onSuccess(text);
			removeFromParent();
			return false;
		}, 500);
		return this;
	}
}
