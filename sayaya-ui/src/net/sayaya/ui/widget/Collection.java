package net.sayaya.ui.widget;

import java.util.HashSet;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.layout.client.Layout.AnimationCallback;
import com.google.gwt.layout.client.Layout.Layer;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.Widget;

import net.sayaya.ui.handler.HasValueWidget;
import net.sayaya.ui.style.StyleCollection;

public class Collection extends ComplexPanel implements RequiresResize, ProvidesResize, AnimationCallback {
	private final UListElement elem = Document.get().createULElement();
	private final HashSet<CollectionItem> children = new HashSet<>();
	public Collection() {
		setElement(elem);
		layout();
		style();
	}
	
	private void layout() {
		
	}
	
	private void style() {
		setStyleName(StyleCollection.GSS.collection());
	}
	
	public Collection add(CollectionItem item) {
		LIElement li = Document.get().createLIElement();
		elem.appendChild(li);
        super.add(item, li);
		return this;
	}
	
	public Collection remove(CollectionItem item) {
		return this;
	}
	
	@Override
	public void clear() {
		super.clear();
		children.clear();
	}
	
	@Override
	public void onResize() {
		for(CollectionItem child: children) child.onResize();
	}

	@Override
	public void onAnimationComplete() {
		for(CollectionItem child: children) child.onAnimationComplete();
	}

	@Override
	public void onLayout(Layer layer, double progress) {
		for(CollectionItem child: children) child.onLayout(layer, progress);
	}
	
	public static class CollectionItem extends Composite implements RequiresResize, ProvidesResize, AnimationCallback {
		private final FlexTable table = new FlexTable();
		public CollectionItem() {
			initWidget(table);
		}
		
		public CollectionItem add(Widget widget) {
			table.setWidget(table.getRowCount(), 0, widget);
			return this;
		}
		
		@Override
		public void onAnimationComplete() {
			for(int i = 0; i < table.getRowCount(); ++i) {
				for(int j = 0; j < table.getCellCount(i); ++j) {
					Widget child = table.getWidget(i, j);
					if(child instanceof AnimationCallback) {
						AnimationCallback cast = (AnimationCallback)child;
						cast.onAnimationComplete();
					}
				}
			}
		}
		
		@Override
		public void onLayout(Layer layer, double progress) {
			for(int i = 0; i < table.getRowCount(); ++i) {
				for(int j = 0; j < table.getCellCount(i); ++j) {
					Widget child = table.getWidget(i, j);
					if(child instanceof AnimationCallback) {
						AnimationCallback cast = (AnimationCallback)child;
						cast.onLayout(layer, progress);
					}
				}
			}
		}
		
		@Override
		public void onResize() {
			for(int i = 0; i < table.getRowCount(); ++i) {
				for(int j = 0; j < table.getCellCount(i); ++j) {
					Widget child = table.getWidget(i, j);
					if(child instanceof RequiresResize) {
						RequiresResize cast = (RequiresResize)child;
						cast.onResize();
					}
				}
			}
		}
	}
	
	public static class CollectionItemHasValue<T> extends CollectionItem implements HasValueWidget<T> {
		private final HasValueWidget<T> controller;
		public CollectionItemHasValue(HasValueWidget<T> controller) {
			super();
			this.controller = controller;
			layout();
			style();
		}
		
		private void layout() {
			super.table.setWidget(0, 1, controller);
			super.table.getFlexCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_RIGHT);
			super.table.getFlexCellFormatter().setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		}
		
		private void style() {
			
		}
		
		@Override
		public CollectionItemHasValue<T> add(Widget widget) {
			super.add(widget);
			super.table.getFlexCellFormatter().setRowSpan(0, 1, super.table.getRowCount());
			return this;
		}
		
		@Override
		public CollectionItemHasValue<T> setValue(T value) {
			controller.setValue(value);
			return this;
		}

		@Override
		public T getValue() {
			return controller.getValue();
		}

		@Override
		public boolean isEmpty() {
			return controller.isEmpty();
		}
		@Override
		public HandlerRegistration addValueChangeHandler(ValueChangeHandler<T> handler) {
			return controller.addValueChangeHandler(handler);
		}
	}
}
