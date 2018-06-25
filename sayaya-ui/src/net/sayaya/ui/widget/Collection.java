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
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.gwt.user.client.ui.RequiresResize;

import net.sayaya.ui.handler.HasValueWidget;

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
			layout();
			style();
		}
		
		private void layout() {
			
		}
		
		private void style() {
			
		}
		
		@Override
		public void onAnimationComplete() {
			
		}
		@Override
		public void onLayout(Layer layer, double progress) {
			
		}
		@Override
		public void onResize() {
			
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
			
		}
		
		private void style() {
			
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
