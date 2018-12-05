package net.sayaya.ui.widget.shape;

import com.google.gwt.event.dom.client.HasMouseDownHandlers;
import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.HasMouseOverHandlers;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.shared.GwtEvent;

public interface Shape extends HasMouseOverHandlers, HasMouseOutHandlers, HasMouseDownHandlers {
	double getX();
	double getY();
	double getRotate();
	
	void fireOver(MouseOverEvent event);
	void fireOut(MouseOutEvent event);
	void fireDown(MouseDownEvent event);
	
	default void fireEvent(GwtEvent<?> event) {
		if(event instanceof MouseOverEvent) fireOver((MouseOverEvent)event);
		else if(event instanceof MouseOutEvent) fireOut((MouseOutEvent)event);
		else if(event instanceof MouseDownEvent) fireDown((MouseDownEvent)event);
	}
}
