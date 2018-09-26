package net.sayaya.ui.decorator;

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;

import net.sayaya.ui.handler.HasValueWidget;

public class ReadOnly {
	public static <T> DecoratorReadOnly<T> decorate(HasValueWidget<T> widget) {
		return new DecoratorReadOnly<T>(widget);
	}
	public final static class DecoratorReadOnly<T> extends Decorator implements HasValueWidget<T> {
		private final HasValueWidget<T> widget;
		private DecoratorReadOnly(HasValueWidget<T> w) {
			super(w);
			widget = w;
		}

		@Override
		public DecoratorReadOnly<T> setValue(T value) {
			return this;
		}

		@Override
		public T getValue() {
			return widget.getValue();
		}

		@Override
		public boolean isEmpty() {
			return widget.isEmpty();
		}

		@Override
		public HandlerRegistration addValueChangeHandler(ValueChangeHandler<T> handler) {
			return widget.addValueChangeHandler(handler);
		}
	}
}
