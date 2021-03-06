package net.sayaya.ui.layout;

import java.util.LinkedList;
import java.util.function.Supplier;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

import net.sayaya.ui.icon.Icon;
import net.sayaya.ui.style.StylePopupPanel;
import net.sayaya.ui.style.color.Palette;
import net.sayaya.ui.widget.Button;
import net.sayaya.ui.widget.Label;

public class PopupPanel extends Composite {
	private final com.google.gwt.user.client.ui.PopupPanel container;
	private final FlowPanel layout = new FlowPanel();
	private final LinkedList<SubMenu> children = new LinkedList<>();
	private PopupPanel parent;
	private PopupPanel(DomEvent<?> evt) {
		this();
		int cw = Window.getClientWidth();
		int x = evt.getNativeEvent().getClientX()-10;
		int ww = 152;
		int dx = Math.min(x, cw-ww-5);
		container.setPopupPosition(dx, evt.getNativeEvent().getClientY()-10);
	}
	
	private PopupPanel(Event evt) {
		this();
		int cw = Window.getClientWidth();
		int x = evt.getClientX()-10;
		int ww = 142;
		int dx = Math.min(x, cw-ww-5);
		container.setPopupPosition(dx, evt.getClientY()-10);
	}
	
	private PopupPanel() {
		initWidget(layout);
		container = new com.google.gwt.user.client.ui.PopupPanel(true);
		container.setWidget(this);
		style();
	}

	private void style() {
		setStyleName(StylePopupPanel.GSS.popup());
		container.getElement().getStyle().setZIndex(Integer.MAX_VALUE);
	}
	
	public PopupPanel show() {
		container.show();
		int ch = Window.getClientHeight();
		Scheduler.get().scheduleDeferred(()->{
			int wh = container.getElement().getOffsetHeight();
			int y = container.getAbsoluteTop();
			int dy = Math.min(y, ch-wh-5);
			if(y != dy) {
				container.hide();
				container.setPopupPosition(container.getPopupLeft(), dy);
				container.show();
			}
		});
		return this;
	}
	
	public void hide() {
		container.hide();
		if(parent!=null) parent.hide();
	}
	
	public Item add(String label, ClickHandler handler) {
		Item item = new Item(label);
		item.addClickHandler(evt->{
			hide();
			children.stream().forEach(child->child.release());
		});
		item.addClickHandler(handler);
		item.addMouseOverHandler(evt->{
			children.stream().forEach(child->child.release());
		});
		layout.add(item);
		return item;
	}
	
	public Item add(Icon icon, String label, ClickHandler handler) {
		Item item = new Item(icon, label);
		item.addClickHandler(evt->{
			hide();
			children.stream().forEach(child->child.release());
		});
		item.addClickHandler(handler);
		item.addMouseOverHandler(evt->{
			children.stream().forEach(child->child.release());
		});
		layout.add(item);
		return item;
	}
	
	public PopupPanel addDevider() {
		if(layout.getWidgetCount() <= 0) return this;
		layout.getWidget(layout.getWidgetCount()-1).getElement().getStyle().setProperty("borderBottom", "1px solid " + Palette.getInstance().getColorDevider());
		return this;
	}
	
	public SubMenu add(String label) {
		SubMenu item = new SubMenu(label);
		item.addMouseOverHandler(evt->{
			children.stream().filter(child->child!=item).forEach(child->child.release());
		});
		layout.add(item);
		children.add(item);
		item.child.parent = this;
		return item;
	}
	
	public SubMenu add(Icon icon, String label) {
		SubMenu item = new SubMenu(icon, label);
		item.addMouseOverHandler(evt->{
			children.stream().filter(child->child!=item).forEach(child->child.release());
		});
		layout.add(item);
		children.add(item);
		item.child.parent = this;
		return item;
	}
	
	public static PopupPanel create(DomEvent<?> evt) {
		return new PopupPanel(evt);
	}

	public static PopupPanel create(Event evt) {
		return new PopupPanel(evt);
	}
	
	public final static class Item extends Button<Item> {
		private final FlowPanel layout = new FlowPanel();
		private final Label label = new Label();
		private Supplier<Boolean> chkEnable = ()->true;
		public Item(Icon icon, String label) {
			super();
			layout.getElement().getStyle().setDisplay(Display.FLEX);
			this.label.setStyleName(StylePopupPanel.GSS.label());
			icon.addStyleName(StylePopupPanel.GSS.icon());
			layout.add(icon);
			layout.add(this.label.setValue(label));	
			getElement().appendChild(layout.getElement());
			addAttachHandler(evt->{if(evt.isAttached()) {
				if(chkEnable.get()) setEnabled(true);
				else setEnabled(false);
			}});
		}
		
		public Item(String label) {
			super();
			layout.getElement().getStyle().setDisplay(Display.FLEX);
			this.label.setStyleName(StylePopupPanel.GSS.label());
			layout.add(this.label.setValue(label));
			getElement().appendChild(layout.getElement());
			addAttachHandler(evt->{if(evt.isAttached()) {
				if(chkEnable.get()) setEnabled(true);
				else setEnabled(false);
			}});
		}
		
		@Override
		public Item style(Item widet) {
			setStyleName(StylePopupPanel.GSS.item());
			return this;
		}
		
		public Item setChkEnabled(Supplier<Boolean> chkEnable) {
			this.chkEnable = chkEnable;
			return this;
		}
	}
	
	public final static class SubMenu extends Button<SubMenu> {
		private final FlowPanel layout = new FlowPanel();
		private final Label label = new Label();
		private final PopupPanel child = new PopupPanel();
		private final Icon next = Icon.create(Icon.GSS.caretRight());
		private Supplier<Boolean> chkEnable = ()->true;
		public SubMenu(Icon icon, String label) {
			super();
			layout.getElement().getStyle().setDisplay(Display.FLEX);
			this.label.setStyleName(StylePopupPanel.GSS.label());
			icon.addStyleName(StylePopupPanel.GSS.icon());
			next.addStyleName(StylePopupPanel.GSS.icon());
			next.getElement().getStyle().setRight(10, Unit.PX);
			layout.add(icon);
			layout.add(this.label.setValue(label));
			layout.add(next);
			getElement().appendChild(layout.getElement());
			addMouseOverHandler(evt->{
				addStyleName(StylePopupPanel.GSS.itemHover());
				child.container.setPopupPosition(this.getElement().getAbsoluteLeft()+this.getElement().getOffsetWidth()-9, this.getElement().getAbsoluteTop()-1);
				child.show();
			});
			addAttachHandler(evt->{if(evt.isAttached()) {
				if(chkEnable.get()) setEnabled(true);
				else setEnabled(false);
			}});
		}
		
		public SubMenu(String label) {
			super();
			layout.getElement().getStyle().setDisplay(Display.FLEX);
			this.label.setStyleName(StylePopupPanel.GSS.label());
			next.addStyleName(StylePopupPanel.GSS.icon());
			next.getElement().getStyle().setRight(10, Unit.PX);
			layout.add(this.label.setValue(label));
			layout.add(next);
			getElement().appendChild(layout.getElement());
			addMouseOverHandler(evt->{
				addStyleName(StylePopupPanel.GSS.itemHover());
				child.container.setPopupPosition(this.getElement().getAbsoluteLeft()+this.getElement().getOffsetWidth()-10, this.getElement().getAbsoluteTop()-1);
				child.show();
			});
			addAttachHandler(evt->{if(evt.isAttached()) {
				if(chkEnable.get()) setEnabled(true);
				else setEnabled(false);
			}});
		}
		
		@Override
		public SubMenu style(SubMenu widet) {
			setStyleName(StylePopupPanel.GSS.item());
			return this;
		}
		
		private void release() {
			if(child.container.isShowing()) child.container.hide();
			child.children.stream().forEach(child->child.release());
			removeStyleName(StylePopupPanel.GSS.itemHover());
		}
		
		public SubMenu setChkEnabled(Supplier<Boolean> chkEnable) {
			this.chkEnable = chkEnable;
			return this;
		}
		
		public PopupPanel create() {
			return child;
		}
	}
}