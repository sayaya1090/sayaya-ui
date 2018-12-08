package net.sayaya.ui.widget.shape.impl;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FocusWidget;

import net.sayaya.ui.widget.shape.Shape;

public class ShapeInstance<S extends ShapeInstance<S>> extends FocusWidget implements Shape {
	private final String id;
	private HandlerManager handlerManager;
	
	public ShapeInstance(String tag) {
		id = DOM.createUniqueId();
		setElement(element(id, tag));
		Event.setEventListener(getElement(), evt->{
			String type = evt.getType();
			if("mouseover".equals(type)) MouseOverEvent.fireNativeEvent(evt, this);
			else if("mouseout".equals(type)) MouseOutEvent.fireNativeEvent(evt, this);
			else if("click".equals(type)) ClickEvent.fireNativeEvent(evt, this);
		});
	}
	private final native Element element(String id, String tag) /*-{
		var elem = $wnd.document.createElementNS("http://www.w3.org/2000/svg", tag);
		return elem;
	}-*/;
	
	@Override
	public void fireEvent(GwtEvent<?> event) {
		if (handlerManager != null) handlerManager.fireEvent(event);
	}
	
	public final <H extends EventHandler> HandlerRegistration addBitlessHandler(final H handler, DomEvent.Type<H> type) {
		assert handler != null : "handler must not be null";
		assert type != null : "type must not be null";
		sinkBitlessEvent(type.getName());
		if(handlerManager==null) handlerManager = createHandlerManager();
		return handlerManager.addHandler(type, handler);
	}
	 
	@Override
	public final HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
		return addBitlessHandler(handler, MouseOverEvent.getType());
	}
	@Override
	public final HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
		return addBitlessHandler(handler, MouseOutEvent.getType());
	}
	@Override
	public final HandlerRegistration addMouseDownHandler(MouseDownHandler handler) {
		return addBitlessHandler(handler, MouseDownEvent.getType());
	}
	@Override
	public final Element toElement() {
		return getElement();
	}
}

