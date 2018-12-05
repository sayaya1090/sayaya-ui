package net.sayaya.ui.svg.shape;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.event.dom.client.HasMouseDownHandlers;
import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.HasMouseOverHandlers;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.shared.GwtEvent;

public interface Shape extends HasMouseOverHandlers, HasMouseOutHandlers, HasMouseDownHandlers {
	boolean checkIn(double x, double y);
	void draw(Context2d context, double progress);
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
