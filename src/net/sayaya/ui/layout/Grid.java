package net.sayaya.ui.layout;

import java.util.Comparator;
import java.util.function.BiFunction;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.Widget;

import jsinterop.annotations.JsFunction;
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
		this.setting = new GridSetting()
		.setContainerClass(GSS.grid())
		.setItemClass(GSS.item())
		.setItemVisibleClass(GSS.itemVisible())
		.setItemHiddenClass(GSS.itemHidden())
		.setItemPositioningClass(GSS.itemPositioning())
		.setItemDraggingClass(GSS.itemDragging())
		.setItemReleasingClass(GSS.itemReleasing())
		.setDragEnabled(true)
		.setDragSortPredicate(new DragSortPredicate().setAction(DragSortPredicateAction.move).setThreshold(51))
		.setLayout(new Layout().setFillGaps(true).setHorizontal(false).setRounding(true).setAlignBottom(false).setAlignBottom(false));
		if(setting.dragStartPredicate==null) setting.setDragStartPredicate(this, (item, e)->{
			if(e.deltaTime > 300) return true;
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
	
	public GridSetting getSetting() {
		return setting;
	}
	
	@JsFunction
	public static interface _ItemEvent {
		Object apply(MuuriItem item, AnimationEvent event);
	}
	
	@JsType(isNative = true, namespace = JsPackage.GLOBAL, name="Item")	
	public final static class MuuriItem {
		public native Element getElement();
	}
	
	@JsType(isNative = true, namespace = JsPackage.GLOBAL, name="Muuri")	
	public final static class Muuri {
		public Muuri(Element elemen, GridSetting settings) {};
		public native Element getElement();
		public native void synchronize();
		public native void refreshItems();
		public native void layout();
		public native void add(Element elem);
		public native void remove(Element elem);
		public native void show(Element elem);
		public native void hide(Element elem);
		public native void sort(Comparator<?> comp);
	}
	@JsType(isNative = true, namespace= JsPackage.GLOBAL, name="Object")
	public final static class GridSetting {
		@JsProperty
		private boolean dragEnabled;
		@JsProperty
		private Layout layout;
		@JsProperty
		private double layoutOnResize;
		@JsProperty
		private boolean layoutOnInit;
		@JsProperty
		private double layoutDuration;
		@JsProperty
		private Object dragStartPredicate;
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
		public GridSetting setDragStartPredicate(DragStartPredicate dragStartPredicate) {
			this.dragStartPredicate = dragStartPredicate;
			return this;
		}
		
		@JsOverlay
		public GridSetting setDragStartPredicate(Grid grid, BiFunction<Item, AnimationEvent, Boolean> dragStartPredicate) {
			_ItemEvent func = (MuuriItem item, AnimationEvent event)->{
				Element elem = item.getElement();
				for(int i = 0; i < grid.div.getWidgetCount(); ++i) if(elem.getParentElement().getChild(i) == elem) return dragStartPredicate.apply((Item)grid.div.getWidget(i), event);
				return false;
			};
			this.dragStartPredicate = func;
			return this;
		}
		
		@JsOverlay
		public GridSetting setDragSortPredicate(DragSortPredicate dragSortPredicate) {
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
		@JsOverlay
		public GridSetting setLayout(Layout layout) {
			this.layout = layout;
			return this;
		}
	}
	@JsType(isNative = true, namespace= JsPackage.GLOBAL, name="Object")
	public final static class Layout {
		@JsProperty
		private boolean fillGaps;
		@JsProperty
		private boolean horizontal;
		@JsProperty
		private boolean alignRight;
		@JsProperty
		private boolean alignBottom;
		@JsProperty
		private boolean rounding;
		@JsOverlay
		public Layout setFillGaps(boolean fillGaps) {
			this.fillGaps = fillGaps;
			return this;
		}
		@JsOverlay
		public Layout setHorizontal(boolean horizontal) {
			this.horizontal = horizontal;
			return this;
		}
		@JsOverlay
		public Layout setAlignRight(boolean alignRight) {
			this.alignRight = alignRight;
			return this;
		}
		@JsOverlay
		public Layout setAlignBottom(boolean alignBottom) {
			this.alignBottom = alignBottom;
			return this;
		}
		@JsOverlay
		public Layout setRounding(boolean rounding) {
			this.rounding = rounding;
			return this;
		}
	}
	
	@JsType(isNative = true, namespace= JsPackage.GLOBAL, name="Object")
	public final static class DragStartPredicate {
		@JsProperty
		private double distance;
		@JsProperty
		private double delay;
		@JsProperty
		private String handle;
		@JsOverlay
		public DragStartPredicate setDistance(double distance) {
			this.distance = distance;
			return this;
		}
		@JsOverlay
		public DragStartPredicate setDelay(double delay) {
			this.delay = delay;
			return this;
		}
	}
	
	@JsType(isNative = true, namespace= JsPackage.GLOBAL, name="Object")
	public final static class DragSortPredicate {
		@JsProperty
		private DragSortPredicateAction action;
		@JsProperty
		private double threshold;
		@JsProperty
		private double index;
		@JsProperty
		private Grid grid;
		
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
	
	@JsType(isNative = true, namespace= JsPackage.GLOBAL, name="Object")
	public final static class AnimationEvent {
		@JsProperty
		private double deltaTime;
		@JsProperty
		private double distance;
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
