package net.sayaya.ui.layout;

import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.user.client.ui.FlowPanel;

import net.sayaya.ui.style.StylePopupPanel;

public class PopupPanel extends com.google.gwt.user.client.ui.PopupPanel {
	private final FlowPanel layout = new FlowPanel();
	private PopupPanel(DomEvent<?> evt) {
		super(true);
		setPopupPosition(evt.getNativeEvent().getClientX()-10, evt.getNativeEvent().getClientY()-10);
		setWidget(layout);
		style();
	}

	private void style() {
		setStyleName(StylePopupPanel.GSS.popup());
	}
	
	public static PopupPanel open(DomEvent<?> evt) {
		return new PopupPanel(evt);
	}
}
