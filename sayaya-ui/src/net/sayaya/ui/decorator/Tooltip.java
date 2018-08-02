package net.sayaya.ui.decorator;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import net.sayaya.ui.style.StyleTooltip;
import net.sayaya.ui.widget.Label;

public class Tooltip {
	public static <T extends IsWidget> T decorate(T widget, String tooltip) {
		Label label = new Label().setValue(tooltip);
		style(label);
		label.getElement().getStyle().setZIndex(Integer.MAX_VALUE);
		RootPanel.get().add(label);
		label.setVisible(false);
		widget.asWidget().addDomHandler(evt->{
			int top = widget.asWidget().getElement().getAbsoluteTop();
			int ch = Window.getClientHeight();
			int cw = Window.getClientWidth();
			int deltaY = 0;
			if(top < ch/2) {
				deltaY = -25;
				label.addStyleName(StyleTooltip.GSS.fadein2());
			} else {
				deltaY = widget.asWidget().getOffsetHeight()+5;
				label.addStyleName(StyleTooltip.GSS.fadein1());
			}
			label.getElement().getStyle().setTop(widget.asWidget().getAbsoluteTop()+deltaY, Unit.PX);
			label.setVisible(true);
			Scheduler.get().scheduleDeferred(()->{
				int left = widget.asWidget().getElement().getAbsoluteLeft();
				int ww = widget.asWidget().getElement().getOffsetWidth();
				int lw = label.getElement().getOffsetWidth();
				double pos = left+(ww-lw)/2.0;
				if(pos+lw > cw) pos = cw-lw-5;
				else if(pos < 5) pos = 5;
				label.getElement().getStyle().setLeft(pos, Unit.PX);
			});
		}, MouseOverEvent.getType());
		
		widget.asWidget().addDomHandler(evt->label.setVisible(false), MouseOutEvent.getType());
		widget.asWidget().addDomHandler(evt->label.setVisible(false), ClickEvent.getType());
		widget.asWidget().addAttachHandler(evt->{
			if(evt.isAttached()) return;
			RootPanel.get().remove(label);
		});
		return widget;
	}
	
	private static void style(Widget w) {
		w.setStyleName(StyleTooltip.GSS.tooltip());
	}
}
