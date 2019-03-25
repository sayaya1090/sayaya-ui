package net.sayaya.ui.layout.grid;

import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.TextTransform;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DragLeaveHandler;
import com.google.gwt.event.dom.client.DragOverHandler;
import com.google.gwt.event.dom.client.DragStartHandler;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasDragLeaveHandlers;
import com.google.gwt.event.dom.client.HasDragOverHandlers;
import com.google.gwt.event.dom.client.HasDragStartHandlers;
import com.google.gwt.event.dom.client.HasDropHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import net.sayaya.ui.icon.Icon;
import net.sayaya.ui.layout.Grid;
import net.sayaya.ui.widget.Button;
import net.sayaya.ui.widget.Label;
import net.sayaya.ui.widget.button.ButtonToggle;

public class Item extends Composite implements RequiresResize, HasClickHandlers, HasDragStartHandlers, HasDragOverHandlers, HasDropHandlers, HasDragLeaveHandlers {
	private final SimplePanel panel = new SimplePanel();
	private final FlowPanel layout = new FlowPanel();
	private final SimplePanel content = new SimplePanel();
	private final FlowPanel header = new FlowPanel();
	private final Label title = new Label("ITEM");
	private final Button<?> pin = new ButtonToggle(Icon.create(Icon.GSS.thumbtack()));
	private Grid parent;
	public Item() {
		initWidget(panel);
		panel.add(layout);
		layout.add(header);
		layout.add(content);
		header.add(title);
		header.add(pin);
		style();
	}
	
	private void style() {
		layout.addStyleName(Grid.GSS.itemContent());
		content.getElement().getStyle().setMargin(5, Unit.PX);
		title.addStyleName(Grid.GSS.itemTitle());
		header.getElement().getStyle().setDisplay(Display.FLEX);
		pin.getElement().getStyle().setProperty("marginLeft", "auto");
	}
	
	public Item add(Widget w) {
		content.add(w);
		if(parent!=null) parent.refresh();
		return this;
	}
	
	public void setTitle(String title) {
		this.title.setValue(title);
	}
	
	public void setParent(Grid grid) {
		parent = grid;
	}
	
	@Override
	public Grid getParent() {
		return parent;
	}
	
	@Override
	public void onResize() {
		Widget child = content.getWidget();
		if(child!=null && child instanceof RequiresResize) ((RequiresResize) child).onResize();
	}
	
	@Override
	public final HandlerRegistration addClickHandler(ClickHandler handler) {
		return null;
		// return bus.addHandler(ClickEvent.getType(), handler);
	}

	@Override
	public final HandlerRegistration addDropHandler(DropHandler handler) {
		return null;
		// return bus.addHandler(DropEvent.getType(), handler);
	}

	@Override
	public final HandlerRegistration addDragOverHandler(DragOverHandler handler) {
		return null;
		// return bus.addHandler(DragOverEvent.getType(), handler);
	}

	@Override
	public final HandlerRegistration addDragStartHandler(DragStartHandler handler) {
		return null;
		// return bus.addHandler(DragStartEvent.getType(), handler);
	}

	@Override
	public HandlerRegistration addDragLeaveHandler(DragLeaveHandler handler) {
		return null;
		// return bus.addHandler(DragLeaveEvent.getType(), handler);
	}
}