package net.sayaya.ui.widget;

import java.util.LinkedList;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.layout.client.Layout.AnimationCallback;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;

import net.sayaya.ui.icon.Icon;
import net.sayaya.ui.place.Place;
import net.sayaya.ui.style.StyleSlideNavigator;
import net.sayaya.ui.widget.slidenavigator.MenuItem;

public final class SlideNavigator extends Composite implements ProvidesResize, RequiresResize {
	private final static Storage STORAGE = Storage.getLocalStorageIfSupported();
	private final static String MENU_STATE_KEY = "sayaya-ui-menu-collapse";
	private final FlowPanel layout = new FlowPanel();
	private final SplitLayoutPanel parent;
	private final SimplePanel knocker = new SimplePanel();
	private final Icon knockerIcon = Icon.create(Icon.GSS.caretLeft());
	private final FlowPanel foot = new FlowPanel();
	private final LinkedList<MenuItem> children = new LinkedList<>();
	public enum State {
		COLLAPSE, EXPAND
	}

	private State state = "1".equals(STORAGE.getItem(MENU_STATE_KEY))?State.COLLAPSE:State.EXPAND;
	public SlideNavigator(SplitLayoutPanel parent) {
		this.parent = parent;
		initWidget(layout);
		layout();
		style();
		knocker.addDomHandler(evt->{
			if(state == State.EXPAND) close();
			else if(state == State.COLLAPSE) open();
		}, ClickEvent.getType());
		
		addAttachHandler(evt->{
			if(!evt.isAttached()) return;
			parent.addWest(knocker, 16);
			parent.getWidgetContainerElement(knocker).getStyle().setTop(40, Unit.PX);
		});
	}
	
	private void layout() {
		getElement().getStyle().setHeight(100, Unit.PCT);
		layout.add(foot);
		knocker.add(knockerIcon);
	}
	
	private void style() {
		setStyleName(StyleSlideNavigator.GSS.leftbar());
		foot.setStyleName(StyleSlideNavigator.GSS.foot());
		knocker.setStyleName(StyleSlideNavigator.GSS.knocker());
		knockerIcon.addStyleName(StyleSlideNavigator.GSS.knockerIcon());
	}
	
	public SlideNavigator add(MenuItem item) {
		layout.add(item);
		children.add(item);
		item.addClickHandler(evt->open());
		return this;
	}

	@Override
	public void onResize() {
	}
	
	public void open() {
		open(null);
	}
	public void open(AnimationCallback callback) {
		parent.setWidgetSize(this, 250);
		knocker.getElement().getStyle().setLeft(250, Unit.PX);
		parent.animate(80, callback);
		state = State.EXPAND;
		knockerIcon.removeStyleName(Icon.GSS.caretRight());
		knockerIcon.addStyleName(Icon.GSS.caretLeft());
		foot.setVisible(true);
		children.removeIf(child->!child.isAttached());
		children.stream().forEach(item->item.onResize(state));
		STORAGE.setItem(MENU_STATE_KEY, "0");
	}
	
	public void close() {
		close(null);
	}
	public void close(AnimationCallback callback) {
		parent.setWidgetSize(this, 60);
		knocker.getElement().getStyle().setLeft(60, Unit.PX);
		parent.animate(80, callback);
		state = State.COLLAPSE;
		knockerIcon.removeStyleName(Icon.GSS.caretLeft());
		knockerIcon.addStyleName(Icon.GSS.caretRight());
		foot.setVisible(false);
		children.removeIf(child->!child.isAttached());
		children.stream().forEach(item->item.onResize(state));
		STORAGE.setItem(MENU_STATE_KEY, "1");
	}
	
	public State getState() {
		return state;
	}
	
	public SlideNavigator setPlace(Place<?> place) {
		if(state == State.COLLAPSE) {
			parent.setWidgetSize(this, 60);
			knocker.getElement().getStyle().setLeft(60, Unit.PX);
			parent.animate(1, null);
			knockerIcon.removeStyleName(Icon.GSS.caretLeft());
			knockerIcon.addStyleName(Icon.GSS.caretRight());
			foot.setVisible(false);
		} else {
			parent.setWidgetSize(this, 250);
			knocker.getElement().getStyle().setLeft(250, Unit.PX);
			parent.animate(1, null);
			knockerIcon.removeStyleName(Icon.GSS.caretRight());
			knockerIcon.addStyleName(Icon.GSS.caretLeft());
			foot.setVisible(true);
		}
		children.stream().forEach(item->item.setPlace(state, place));
		return this;
	}
}
