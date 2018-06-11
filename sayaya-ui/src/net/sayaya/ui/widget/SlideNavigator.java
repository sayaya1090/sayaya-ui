package net.sayaya.ui.widget;

import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.layout.client.Layout.AnimationCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;

import net.sayaya.ui.icon.Icon;
import net.sayaya.ui.style.StyleSlideNavigator;

public final class SlideNavigator extends FlowPanel implements ProvidesResize, RequiresResize {
	private final SplitLayoutPanel parent;
	private final SimplePanel openclose = new SimplePanel();
	private final Icon opencloseState = Icon.create(Icon.GSS.caretLeft());
	/*
	private final Image icon = new Image();
	private final Label title = new Label("LIMS");
	private final MenuItem dashboard = new MenuItem(Icon.create(Icon.GSS.chart01()), "Dashboard", new Dashboard());
	private final MenuItem sample = new MenuItem(Icon.create(Icon.GSS.medical17()), "Sample", new Sample());
	private final MenuItem experiment = new MenuItem(Icon.create(Icon.GSS.laboratory06()), "Prep", new Exp());
	private final MenuItem sequencing = new MenuItem(Icon.create(Icon.GSS.laboratory27()), "Sequencing", new Sequencing());
	private final MenuItem analysis = new MenuItem(Icon.create(Icon.GSS.chart34()), "Analysis", new Task());
	private final MenuItem result = new MenuItem(Icon.create(Icon.GSS.medical11()), "Result", new Result());
	private final MenuItem client = new MenuItem(Icon.create(Icon.GSS.program01()), "System", new Client());
	private final MenuItem search = new MenuItem(Icon.create(Icon.GSS.program43()), "Search", new Search());
	private final MenuItem management = new MenuItem(Icon.create(Icon.GSS.service11()), "Management", new Management());
	private final MenuItem[] items = new MenuItem[] {
		dashboard, sample, experiment, sequencing, analysis, result, client, search, management
	};*/
	private final FlowPanel foot = new FlowPanel();
	public SlideNavigator(SplitLayoutPanel parent) {
		this.parent = parent;
		layout();
		style();
		
		openclose.addDomHandler(evt->{
			if(state == State.EXPAND) close();
			else if(state == State.COLLAPSE) open();
		}, ClickEvent.getType());
		
		addAttachHandler(evt->{
			if(!evt.isAttached()) return;
			parent.addWest(openclose, 16);
			parent.getWidgetContainerElement(openclose).getStyle().setTop(40, Unit.PX);
		});
	}
	
	private void layout() {
		getElement().getStyle().setHeight(100, Unit.PCT);
		clear();

		add(foot);
		openclose.add(opencloseState);
	}
	
	private void style() {
		setStyleName(StyleSlideNavigator.GSS.leftbar());
		foot.setStyleName(StyleSlideNavigator.GSS.foot());
		
		openclose.setStyleName(StyleSlideNavigator.GSS.trapezoid());
		opencloseState.getElement().getStyle().setColor("#FFFFFF");
		opencloseState.getElement().getStyle().setFontSize(16, Unit.PX);
		opencloseState.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		opencloseState.getElement().getStyle().setPosition(Position.ABSOLUTE);
		opencloseState.getElement().getStyle().setLeft(-12, Unit.PX);
		opencloseState.getElement().getStyle().setTop(6, Unit.PX);
	}

	@Override
	public void onResize() {
		if(state == State.COLLAPSE) foot.setVisible(false);
		else foot.setVisible(true);
		//Arrays.stream(items).forEach(item->item.onResize(state));
	}
	/*
	private final class MenuItem extends FlexTable implements ProvidesResize {
		private final Icon icon;
		private final Label label = new Label();
		private final FlexTable child = new FlexTable();
		private boolean isSelected = false;
		private final Place<?> place;
		public MenuItem(Icon icon, String label, Place<?> place) {
			this.icon = icon;
			this.label.setText(label);
			this.place = place;
			layout();
			style();
			addDomHandler(evt->{
				Arrays.stream(items).filter(item->this!=item).forEach(item->item.setSelect(false));
				setSelect(true);
			}, ClickEvent.getType());
		}
		
		private void layout() {
			setWidget(0, 0, icon);
			setWidget(0, 1, label);
			getCellFormatter().setWidth(0, 0, "25px");
		}
		
		private void style() {
			setStyleName(StyleMenu.GSS.place());
			label.setStyleName(StyleLabel.GSS.label());
			label.addStyleName(StyleMenu.GSS.placeLabel());
			icon.addStyleName(StyleMenu.GSS.placeIcon());
			child.setStyleName(StyleMenu.GSS.placeSub());
			setCellSpacing(0);
			setCellPadding(0);
			child.setCellSpacing(0);
			child.setCellPadding(0);
			child.setVisible(false);
		}

		public void onResize(State state) {
			if(state == State.COLLAPSE) {
				label.setVisible(false);
				child.removeFromParent();
			} else {
				label.setVisible(true);
				if(isSelected) {
					setWidget(1, 0, child);
					getFlexCellFormatter().setColSpan(1, 0, 2);
				}
			}
		}
		
		public MenuItem addItem(Widget item) {
			child.setVisible(true);
			child.setWidget(child.getRowCount(), 0, item);
			child.getCellFormatter().getElement(child.getRowCount()-1, 0).getStyle().setPadding(0, Unit.PX);
			return this;
		}
		
		public MenuItem clearItem() {
			child.removeAllRows();
			child.removeFromParent();
			child.setVisible(false);
			return this;
		}
		
		private void setSelect(boolean isSelected) {
			this.isSelected = isSelected;
			if(isSelected) {
				addStyleName(StyleMenu.GSS.placeSelected());
				setWidget(1, 0, child);
				getFlexCellFormatter().setColSpan(1, 0, 2);
			} else {
				removeStyleName(StyleMenu.GSS.placeSelected());
				child.removeFromParent();
			}
		}
	}*/
	
	private enum State {
		COLLAPSE, EXPAND
	}
	private State state = State.EXPAND;
	
	public void open() {
		open(null);
	}
	public void open(AnimationCallback callback) {
		parent.setWidgetSize(this, 250);
		openclose.getElement().getStyle().setLeft(250, Unit.PX);
		parent.animate(80, callback);
		state = State.EXPAND;
		opencloseState.removeStyleName(Icon.GSS.caretRight());
		opencloseState.addStyleName(Icon.GSS.caretLeft());
	}
	
	public void close() {
		close(null);
	}
	public void close(AnimationCallback callback) {
		parent.setWidgetSize(this, 60);
		openclose.getElement().getStyle().setLeft(60, Unit.PX);
		parent.animate(80, callback);
		state = State.COLLAPSE;
		opencloseState.removeStyleName(Icon.GSS.caretLeft());
		opencloseState.addStyleName(Icon.GSS.caretRight());
	}
	
	public State getState() {
		return state;
	}
	
	/*
	private final class MenuItemSub extends Label {
		private final Place<?> place;
		public MenuItemSub(String param, Place<?> place) {
			this.place = place;
			setText(param);
			addClickHandler(evt->Presenter.go(place));
			style();
		}
		
		private void style() {
			setStyleName(StyleLabel.GSS.label());
			addStyleName(StyleMenu.GSS.placeSubItem());
		}
		
		private void setSelect(boolean isSelected) {
			if(isSelected) addStyleName(StyleMenu.GSS.placeSubItemSelected());
			else removeStyleName(StyleMenu.GSS.placeSubItemSelected());
		}
	}
	
	public SlideNavigator setPlace(Place<?> place) {
		Arrays.stream(items).forEach(item->{
			if(place.equals(item.place)) {
				item.setSelect(true);
				for(int i = 0; i < item.child.getRowCount(); ++i) {
					Widget sub = item.child.getWidget(i, 0);
					if(!(sub instanceof MenuItemSub)) continue;
					MenuItemSub cast = (MenuItemSub)sub;
					cast.setSelect(place.equals(cast.place));
				}
			}
			else item.setSelect(false);
		});
		onResize();
		return this;
	}*/
}
