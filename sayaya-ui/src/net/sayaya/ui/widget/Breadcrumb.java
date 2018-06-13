package net.sayaya.ui.widget;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

import net.sayaya.ui.activity.Presenter;
import net.sayaya.ui.icon.Icon;
import net.sayaya.ui.place.Place;
import net.sayaya.ui.style.StyleBreadcrumb;

public class Breadcrumb extends Composite {
	private final FlowPanel layout = new FlowPanel();
	private final Element splitter;
	public Breadcrumb() {
		this(Icon.create(Icon.GSS.chevronDoubleRight()));
	}
	public Breadcrumb(String splitter) {
		this(new Label().setValue(splitter));
	}
	public Breadcrumb(Widget splitter) {
		initWidget(layout);
		splitter.addStyleName(StyleBreadcrumb.GSS.splitter());
		this.splitter = (Element) splitter.getElement().cloneNode(true);
		layout.getElement().getStyle().setDisplay(Display.FLEX);
	}
	
	public Breadcrumb add(Place<?> place) {
		add(place, place.toString());
		return this;
	}
	
	public Breadcrumb add(Place<?> place, String label) {
		Anchor anchor = new Anchor();
		anchor.setText(label);
		anchor.setHref("#");
		add(place, anchor);
		return this;
	}
	
	public Breadcrumb add(Place<?> place, Widget label) {
		if(layout.getWidgetCount() > 0) layout.getElement().appendChild(splitter.cloneNode(true));
		layout.add(label);
		label.addDomHandler(evt->Presenter.go(place), ClickEvent.getType());
		label.addStyleName(StyleBreadcrumb.GSS.item());
		return this;
	}
}
