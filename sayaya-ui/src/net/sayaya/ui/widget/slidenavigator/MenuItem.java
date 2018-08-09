package net.sayaya.ui.widget.slidenavigator;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ProvidesResize;

import net.sayaya.ui.activity.Presenter;
import net.sayaya.ui.icon.Icon;
import net.sayaya.ui.place.Place;
import net.sayaya.ui.style.StyleSlideNavigator;
import net.sayaya.ui.widget.Label;
import net.sayaya.ui.widget.SlideNavigator.State;

public final class MenuItem extends FlexTable implements ProvidesResize, HasClickHandlers {
	private static Set<MenuItem> INSTANCES = new HashSet<>();
	private final Icon icon;
	private final Label label = new Label();
	private final FlowPanel childContainer = new FlowPanel();
	private boolean isSelected = false;
	private final Animation open = new Animation() {
		@Override
		protected void onUpdate(double progress) {
			if(childContainer.isAttached()) {
				childContainer.setHeight("auto");
				int height=childContainer.getOffsetHeight();
				childContainer.setHeight(height*progress + "px");
			}
			if(isSelected) addStyleName(StyleSlideNavigator.GSS.itemSelected());
		}
	};
	private final Animation close = new Animation() {
		@Override
		protected void onUpdate(double progress) {
			if(childContainer.isAttached()) {
				childContainer.setHeight("auto");
				int height=childContainer.getOffsetHeight();
				childContainer.setHeight(height*(1-progress) + "px");
			}
			if(progress >= 1 && !isSelected) removeStyleName(StyleSlideNavigator.GSS.itemSelected());
		}
	};
	private final Place<?> place;
	private final LinkedList<MenuItemSub> children = new LinkedList<>();
	public MenuItem(Icon icon, String label, Place<?> place) {
		this.icon = icon;
		this.label.setValue(label);
		this.place = place;
		layout();
		style();
		HandlerRegistration onClick = addClickHandler(evt->{
			INSTANCES.stream().filter(item->this!=item).forEach(item->item.setSelect(false));
			setSelect(true);
			Presenter.go(place);
		});
		addAttachHandler(evt->{
			if(evt.isAttached()) INSTANCES.add(this);
			else {
				INSTANCES.remove(this);
				onClick.removeHandler();
			}
		});
	}
	
	private void layout() {
		setWidget(0, 0, icon);
		setWidget(0, 1, label);
		getCellFormatter().setWidth(0, 0, "25px");
		childContainer.setHeight("0px");
	}
	
	private void style() {
		this.setStyleName(StyleSlideNavigator.GSS.item());
		icon.addStyleName(StyleSlideNavigator.GSS.itemIcon());
		label.setStyleName(StyleSlideNavigator.GSS.itemLabel());
		childContainer.setStyleName(StyleSlideNavigator.GSS.itemContainer());
		setCellSpacing(0);
		setCellPadding(0);
	}

	public void onResize(State state) {
		if(state == State.COLLAPSE) {
			label.setVisible(false);
			if(isSelected) close.run(80);
		} else {
			label.setVisible(true);
			if(isSelected) open.run(80);
		}
	}
	
	public MenuItem addItem(MenuItemSub child) {
		children.add(child);
		childContainer.add(child);
		setWidget(1, 0, childContainer);
		getFlexCellFormatter().setColSpan(1, 0, 2);
		return this;
	}
	
	public MenuItem clearItem() {
		children.clear();
		childContainer.clear();
		if(getRowCount() > 1) removeRow(1);
		return this;
	}
	
	private void setSelect(boolean isSelected) {
		if(isSelected) {
			if(!this.isSelected) open.run(200);
		} else {
			if(this.isSelected) {
				if(childContainer.isAttached()) childContainer.setHeight("0px");
				removeStyleName(StyleSlideNavigator.GSS.itemSelected());
			}
		}
		this.isSelected = isSelected;
	}
	
	public void setPlace(State state, Place<?> place) {
		boolean selected = false;
		if(place == null) selected = false;
		else if(place.isInstanceOf(this.place)) {
			selected = true;
			isSelected = true;
		} else selected = false;
		
		if(state == State.COLLAPSE) {
			label.setVisible(false);
			if(selected) {
				if(childContainer.isAttached()) childContainer.setHeight("0px");
				addStyleName(StyleSlideNavigator.GSS.itemSelected());
			} else setSelect(false);
		} else {
			label.setVisible(true);
			if(selected) {
				if(childContainer.isAttached()) {
					childContainer.setHeight("auto");
					int height=childContainer.getOffsetHeight();
					childContainer.setHeight(height + "px");
				}
				addStyleName(StyleSlideNavigator.GSS.itemSelected());
				children.stream().forEach(child->child.setPlace(place));
			} else setSelect(false);
		}
	}
	
	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		HandlerRegistration hr1 = this.label.addDomHandler(handler, ClickEvent.getType());
		HandlerRegistration hr2 = this.icon.addDomHandler(handler, ClickEvent.getType());
		return ()->{
			hr1.removeHandler();
			hr2.removeHandler();
		};
	}
}
