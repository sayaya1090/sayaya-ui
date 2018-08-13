package net.sayaya.ui.decorator;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

import net.sayaya.ui.style.StyleTextBox;
import net.sayaya.ui.style.color.Palette;
import net.sayaya.ui.widget.Label;
import net.sayaya.ui.widget.InputBase;

public class Validator {
	@FunctionalInterface
	public static interface Validation<T> {
		boolean validate(T value, Label logger) throws RuntimeException;
	}
	public static <T> TextBoxDecoratorValidator<T> decorate(InputBase<T, ?> widget, double fontSize, Validation<T> validator) {
		return new TextBoxDecoratorValidator<T>(widget, fontSize, validator);
	}
	public final static class TextBoxDecoratorValidator<T> extends Composite implements InputBase<T, TextBoxDecoratorValidator<T>> {
		private final FlowPanel layout = new FlowPanel();
		private final Validation<T> validator;
		private final InputBase<T, ?> widget;
		private final Label label = new Label();
		private boolean isValid = false;
		private TextBoxDecoratorValidator(InputBase<T, ?> w, double fontSize, Validation<T> validator) {
			initWidget(layout);
			widget = w;
			this.validator = validator;
			layout();
			label.getElement().getStyle().setFontSize(fontSize*0.75, Unit.PX);
			widget.asWidget().getElement().getStyle().setFontSize(fontSize, Unit.PX);
			style(this);
			widget.asWidget().addDomHandler(evt->{
				isValid = validator.validate(widget.getValue(), label);
			}, ChangeEvent.getType());
			validator.validate(widget.getValue(), label);
		}
		
		private final void layout() {
			layout.add(widget);
			layout.add(label);
		}
		
		@Override
		public TextBoxDecoratorValidator<T> style(TextBoxDecoratorValidator<T> widet) {
			label.setStyleName(StyleTextBox.GSS.placeholder());
			label.getElement().getStyle().setLeft(3, Unit.PX);
			label.getElement().getStyle().setColor(Palette.getInstance().getColorText3());
			return this;
		}
		
		protected final Label getLogger() {
			return label;
		}

		@Override
		public TextBoxDecoratorValidator<T> setValue(T value) {
			validator.validate(value, label);
			widget.setValue(value);
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
		public TextBoxDecoratorValidator<T> setEnabled(boolean enabled) {
			widget.setEnabled(enabled);
			return this;
		}
		
		@Override
		public boolean isEnabled() {
			return widget.isEnabled();
		}
		
		@Override
		public HandlerRegistration addValueChangeHandler(ValueChangeHandler<T> handler) {
			return widget.addValueChangeHandler(handler);
		}
		
		public boolean isValid() {
			return isValid;
		}
	}
}
