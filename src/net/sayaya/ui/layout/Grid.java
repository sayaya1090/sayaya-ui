package net.sayaya.ui.layout;

import java.util.function.BiFunction;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.Widget;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import net.sayaya.ui.layout.grid.Item;

public class Grid extends Composite implements RequiresResize, ProvidesResize {
	private Muuri muuri;
	private GridSetting setting;
	private final FlowPanel div = new FlowPanel();
	public Grid() {
		initWidget(div);
		
		setting = new GridSetting()
		.setContainerClass(GSS.grid())
		.setItemClass(GSS.item())
		.setItemVisibleClass(GSS.itemVisible())
		.setItemHiddenClass(GSS.itemHidden())
		.setItemPositioningClass(GSS.itemPositioning())
		.setItemDraggingClass(GSS.itemDragging())
		.setItemReleasingClass(GSS.itemReleasing());
		setting.setDragEnabled(true).setDragSortPredicate((item, e)->{
			return null;
		});
		addAttachHandler(evt->{
			if(evt.isAttached()) muuri = new Muuri(this.getElement(), setting);
		});
	}
	
	public void add(Item item) {
		div.add(item);
		item.setParent(this);
		refresh();
	}
	
	@JsType(isNative = true, namespace = JsPackage.GLOBAL, name="Muuri")	
	public final static class Muuri {
		public Muuri(Element elemen, GridSetting settings) {};
		public native Element getElement();
		public native void synchronize();
		public native void refreshItems();
		public native void layout();
	}
	@JsType(isNative = true, namespace= JsPackage.GLOBAL, name="Object")
	public final static class GridSetting {
		@JsProperty
		private boolean dragEnabled;
		@JsProperty
		private Object dragSortPredicate;
		@JsProperty
		private String containerClass;
		@JsProperty
		private String itemClass;
		@JsProperty
		private String itemVisibleClass;
		@JsProperty
		private String itemHiddenClass;
		@JsProperty
		private String itemPositioningClass;
		@JsProperty
		private String itemDraggingClass;
		@JsProperty
		private String itemReleasingClass;
		
		@JsOverlay
		public GridSetting setDragEnabled(boolean dragEnabled) {
			this.dragEnabled = dragEnabled;
			return this;
		}
		
		@JsOverlay
		public GridSetting setDragSortPredicate(DragSortPredicate dragSortPredicate) {
			this.dragSortPredicate = dragSortPredicate;
			return this;
		}
		
		@JsOverlay
		public GridSetting setDragSortPredicate(BiFunction<Item, Event, DragSortPredicate> dragSortPredicate) {
			this.dragSortPredicate = dragSortPredicate;
			return this;
		}
		@JsOverlay
		public GridSetting setContainerClass(String containerClass) {
			this.containerClass = containerClass;
			return this;
		}
		@JsOverlay
		public GridSetting setItemClass(String itemClass) {
			this.itemClass = itemClass;
			return this;
		}
		@JsOverlay
		public GridSetting setItemVisibleClass(String itemVisibleClass) {
			this.itemVisibleClass = itemVisibleClass;
			return this;
		}
		@JsOverlay
		public GridSetting setItemHiddenClass(String itemHiddenClass) {
			this.itemHiddenClass = itemHiddenClass;
			return this;
		}
		@JsOverlay
		public GridSetting setItemPositioningClass(String itemPositioningClass) {
			this.itemPositioningClass = itemPositioningClass;
			return this;
		}
		@JsOverlay
		public GridSetting setItemDraggingClass(String itemDraggingClass) {
			this.itemDraggingClass = itemDraggingClass;
			return this;
		}
		@JsOverlay
		public GridSetting setItemReleasingClass(String itemReleasingClass) {
			this.itemReleasingClass = itemReleasingClass;
			return this;
		}
	}
	@JsType(isNative = true, namespace= JsPackage.GLOBAL, name="Object")
	public final static class DragSortPredicate {
		@JsProperty
		private DragSortPredicateAction action;
		@JsProperty
		private double threshold;
		@JsOverlay
		public DragSortPredicate setAction(DragSortPredicateAction action) {
			this.action = action;
			return this;
		}
		@JsOverlay
		public DragSortPredicate setThreshold(double threshold) {
			this.threshold = threshold;
			return this;
		}
	}
	
	public enum DragSortPredicateAction {
		move, swap
	}

	@Override
	public void onResize() {
		for(int i = 0; i < div.getWidgetCount(); ++i) {
			Widget child = div.getWidget(i);
			if(child instanceof RequiresResize) ((RequiresResize) child).onResize();
		}
		refresh();
	}
	
	public void refresh() {
		if(muuri!=null) {
			muuri.refreshItems();
			muuri.layout();
		}
	}
	
	private static final Resource RESOURCE =  GWT.create(Resource.class);
	static {
		RESOURCE.style().ensureInjected();
	}
	public static final Resource.Style GSS = RESOURCE.style();
	public interface Resource extends ClientBundle {
		public static final Resource instance=  GWT.create(Resource.class);
		@Source("Grid.gss")
		Style style();
		
		public static interface Style extends CssResource {
			String grid();
			String item();
			@CssResource.ClassName("item-content")
			String itemContent();
			@CssResource.ClassName("item-title")
			String itemTitle();
			@CssResource.ClassName("item-visible")
			String itemVisible();
			@CssResource.ClassName("item-hidden")
			String itemHidden();
			@CssResource.ClassName("item-positioning")
			String itemPositioning();
			@CssResource.ClassName("item-releasing")
			String itemReleasing();
			@CssResource.ClassName("item-dragging")
			String itemDragging();
		}
	}
}
