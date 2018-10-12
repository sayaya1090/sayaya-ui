package net.sayaya.ui.test.table;

import java.util.LinkedList;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasMouseMoveHandlers;
import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.logical.shared.HasResizeHandlers;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;

import lombok.Builder;
import lombok.Setter;
import net.sayaya.ui.test.table.data.Column;
import net.sayaya.ui.test.table.data.Data;
import net.sayaya.ui.test.table.data.TableConfig;
import net.sayaya.ui.test.table.data.Data.Value;

@Setter
public class Table extends Widget implements HasClickHandlers, HasMouseMoveHandlers, HasMouseOutHandlers, HasResizeHandlers, HasValueChangeHandlers<Data> {
	private final EventBus bus = new SimpleEventBus();
	private final Element head = Document.get().createTHeadElement();
	private final Element body = Document.get().createTBodyElement();

	private TableConfig config;
	private Data data;
	
	@Builder
	public Table(TableConfig config, Data data) {
		this.config = config;
		this.data = data;
		setElement(Document.get().createTableElement());
		getElement().setAttribute("tabindex", "0");
		getElement().setAttribute("contenteditable", "true");
		layout();
		style();
		DOM.setEventListener(getElement(), evt->{
			if("paste".equalsIgnoreCase(evt.getType()));
		});
		DOM.sinkEvents(getElement(), Event.ONPASTE);
	}
	
	
	private void layout() {
		getElement().appendChild(head);
		getElement().appendChild(body);
	}
	
	private void style() {
		
	}
	public Table update() {
		LinkedList<String> headerKey = new LinkedList<>();
		for(Column col: config.getColumns()) {
			TableCellHeader header = new TableCellHeader(col);
			head.appendChild(header.getElement());
			headerKey.add(col.getKey());
		}
		for(int row = 0; row <= data.getRowCount(); ++row) {
			Element tr = DOM.createTR();
			for(String key: headerKey) {
				Value value = data.getValue(row, key);
				TableCell cell = new TableCell(value);
				tr.appendChild(cell.getElement());
			}
			body.appendChild(tr);
		}
		return this;
	}

	@Override
	public void fireEvent(GwtEvent<?> event) {
		if(event instanceof ValueChangeEvent
		|| event instanceof ResizeEvent
		|| event instanceof MouseOutEvent
		|| event instanceof MouseMoveEvent
		|| event instanceof ClickEvent) bus.fireEvent(event);
		else super.fireEvent(event);
	}

	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Data> handler) {
		return bus.addHandler(ValueChangeEvent.getType(), handler);
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
}
