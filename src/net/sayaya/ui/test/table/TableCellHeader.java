package net.sayaya.ui.test.table;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DragStartEvent;
import com.google.gwt.event.dom.client.DragStartHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasDragStartHandlers;
import com.google.gwt.event.dom.client.HasMouseMoveHandlers;
import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.logical.shared.HasResizeHandlers;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;

import net.sayaya.ui.test.table.data.Column;

public class TableCellHeader extends Widget implements HasClickHandlers, HasMouseMoveHandlers, HasMouseOutHandlers, HasResizeHandlers, HasDragStartHandlers {
	private final EventBus bus = new SimpleEventBus();
	private final Element label = DOM.createLabel();
	public TableCellHeader(Column col) {
		setElement(Document.get().createTHElement());
		getElement().appendChild(label);
		getElement().setAttribute("tabindex", "0");
		getElement().setAttribute("contenteditable", "false");
		getElement().setDraggable(Element.DRAGGABLE_TRUE);
		label.setInnerHTML(col.getLabel());
		this.addClickHandler(evt->{
			Window.alert("C");
		});

		this.addDomHandler(evt->{
			GWT.log("FFFWE");
		}, DragStartEvent.getType());
		this.addDomHandler(evt->{
			GWT.log("FFFWE");
		}, ClickEvent.getType());
		DOM.setEventListener(getElement(), evt->{
			GWT.log("FFffFWE" + evt.getType());
		});
	}
	
	@Override
	public void fireEvent(GwtEvent<?> event) {
		if(event instanceof ResizeEvent
		|| event instanceof MouseOutEvent
		|| event instanceof MouseMoveEvent
		|| event instanceof ClickEvent
		|| event instanceof DragStartEvent) {
			bus.fireEvent(event);
			GWT.log("C");
		}
		else super.fireEvent(event);
	}

	@Override
	public HandlerRegistration addResizeHandler(ResizeHandler handler) {
		return bus.addHandler(ResizeEvent.getType(), handler);
	}


	@Override
	public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
		return bus.addHandler(MouseOutEvent.getType(), handler);
	}


	@Override
	public HandlerRegistration addMouseMoveHandler(MouseMoveHandler handler) {
		return bus.addHandler(MouseMoveEvent.getType(), handler);
	}


	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return bus.addHandler(ClickEvent.getType(), handler);
	}

	@Override
	public HandlerRegistration addDragStartHandler(DragStartHandler handler) {
		return bus.addHandler(DragStartEvent.getType(), handler);
	}
}
