package net.sayaya.ui.decorator;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

import net.sayaya.ui.style.StyleTextBox;
import net.sayaya.ui.style.color.Palette;
import net.sayaya.ui.widget.InputBase;
import net.sayaya.ui.widget.Label;

public class Placeholder {
	public static <T> TextBoxDecoratorPlaceholder<T> decorate(InputBase<T, ?> widget, double fontSize, String placeholder) {
		return new TextBoxDecoratorPlaceholder<T>(widget, fontSize, placeholder);
	}
	public final static class TextBoxDecoratorPlaceholder<T> extends Composite implements InputBase<T, TextBoxDecoratorPlaceholder<T>> {
		private final FlowPanel layout = new FlowPanel();
		private final InputBase<T, ?> widget;
		private final Label label = new Label();
		private final double fontSizePlaceholder;
		private final double fontSizeLabel;
		private final Animation show = new Animation() {
			@Override
			protected void onUpdate(double progress) {
				double y = progress*(fontSizeLabel*1.2);
				double fs = fontSizeLabel+progress*(fontSizePlaceholder-fontSizeLabel);
				label.getElement().getStyle().setTop(y, Unit.PX);
				label.getElement().getStyle().setFontSize(fs, Unit.PX);
			}
		};
		private final Animation hide = new Animation() {
			@Override
			protected void onUpdate(double progress) {
				double y = (1-progress)*(fontSizeLabel*1.2);
				double fs = (fontSizePlaceholder-fontSizeLabel)*(1-progress)+fontSizeLabel;
				label.getElement().getStyle().setTop(y, Unit.PX);
				label.getElement().getStyle().setFontSize(fs, Unit.PX);
			}
		};
		private TextBoxDecoratorPlaceholder(InputBase<T, ?> w, double fontSize, String placeholder) {
			initWidget(layout);
			widget = w;
			label.setValue(placeholder);
			fontSizePlaceholder = fontSize;
			fontSizeLabel = fontSize*0.75;
			layout();
			style(this);
			
			widget.asWidget().addDomHandler(evt->{
				if(widget.isEmpty()) {
					show.cancel();
					hide.run(80);
				}
				label.getElement().getStyle().setColor(Palette.getInstance().getColor2());
			}, FocusEvent.getType());
			widget.asWidget().addDomHandler(evt->{
				if(widget.isEmpty()) {
					hide.cancel();
					show.run(80);
				}
				label.getElement().getStyle().clearColor();
			}, BlurEvent.getType());
			widget.asWidget().addDomHandler(evt->{
				if(widget.isEmpty()) show.run(0);
				else hide.run(0);
			}, ChangeEvent.getType());
			addAttachHandler(evt->{
				Scheduler.get().scheduleDeferred(()->{
					if(widget.isEmpty()) show.run(0);
					else hide.run(0);
				});
			});
		}
		
		private final void layout() {
			layout.add(label);
			layout.add(widget);
			label.getElement().getStyle().setPosition(Position.RELATIVE);
			label.getElement().getStyle().setZIndex(1);
			widget.asWidget().getElement().getStyle().setPosition(Position.RELATIVE);
			widget.asWidget().getElement().getStyle().setTop(0, Unit.PX);
			widget.asWidget().getElement().getStyle().setZIndex(2);
			widget.asWidget().getElement().getStyle().setBackgroundColor("transparent");
		}
		
		@Override
		public TextBoxDecoratorPlaceholder<T> style(TextBoxDecoratorPlaceholder<T> widet) {
			label.setStyleName(StyleTextBox.GSS.placeholder());
			label.getElement().getStyle().setLeft(3, Unit.PX);
			label.getElement().getStyle().setColor(Palette.getInstance().getColorText3());
			label.getElement().getStyle().setHeight(fontSizeLabel*1.2, Unit.PX);
			widget.asWidget().getElement().getStyle().setFontSize(fontSizePlaceholder, Unit.PX);
			return this;
		}
		
		protected final Label getPlaceholder() {
			return label;
		}

		@Override
		public TextBoxDecoratorPlaceholder<T> setValue(T value) {
			widget.setValue(value);
			if(widget.isEmpty()) show.run(0);
			else hide.run(0);
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
		public TextBoxDecoratorPlaceholder<T> setEnabled(boolean enabled) {
			widget.setEnabled(enabled);
			return this;
		}

		@Override
		public HandlerRegistration addValueChangeHandler(ValueChangeHandler<T> handler) {
			return widget.addValueChangeHandler(handler);
		}
	}
}