package net.sayaya.ui.widget.slidenavigator;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;

import net.sayaya.ui.activity.Presenter;
import net.sayaya.ui.icon.Icon;
import net.sayaya.ui.place.Place;
import net.sayaya.ui.style.StyleSlideNavigator;
import net.sayaya.ui.widget.Label;

public class MenuItemSub extends Composite {
	private final Icon icon;
	private final Label label;
	private final Place<?> place;
	private final HorizontalPanel layout = new HorizontalPanel();
	public MenuItemSub(Icon icon, String param, Place<?> place) {
		this.icon = icon;
		this.label = new Label().setValue(param);
		this.place = place;
		initWidget(layout);
		layout();
		style();
		addDomHandler(evt->{
			Presenter.go(place);
		}, ClickEvent.getType());
	}
	
	private void layout() {
		layout.add(icon);
		layout.add(label);
		
	}
	
	private void style() {
		setStyleName(StyleSlideNavigator.GSS.itemSub());
		icon.addStyleName(StyleSlideNavigator.GSS.itemSubIcon());
		label.setStyleName(StyleSlideNavigator.GSS.itemSubLabel());
	}
	
	private void setSelect(boolean isSelected) {
		if(isSelected) addStyleName(StyleSlideNavigator.GSS.itemSubSelected());
		else removeStyleName(StyleSlideNavigator.GSS.itemSubSelected());
	}
	
	void setPlace(Place<?> place) {
		if(place.equals(this.place)) setSelect(true);
		else setSelect(false);
	}
}
