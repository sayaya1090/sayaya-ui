package net.sayaya.ui.decorator;

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
			// TODO Auto-generated method stub
			return false;
		}
	}
}
