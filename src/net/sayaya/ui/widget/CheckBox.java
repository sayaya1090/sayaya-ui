package net.sayaya.ui.widget;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;

import net.sayaya.ui.icon.Icon;
import net.sayaya.ui.style.StyleCheckBox;
import net.sayaya.ui.widget.InputBase;

public class CheckBox extends Composite implements InputBase<Boolean, CheckBox> {
	private final EventBus bus = new SimpleEventBus();
	private final Icon icon = Icon.create(Icon.GSS.check());
	private final SimplePanel square = new SimplePanel();
	private final LayoutPanel layout = new LayoutPanel();
	private boolean enabled = true;
	private boolean value = false;
	private final Animation check = new Animation() {
		@Override
		protected void onUpdate(double progress) {
			icon.getElement().getStyle().setOpacity(progress);
			icon.getElement().getStyle().setProperty("transform", "rotate(" + (180-180*progress) + "deg)");
		}
	};
	private final Animation uncheck = new Animation() {
		@Override
		protected void onUpdate(double progress) {
			icon.getElement().getStyle().setOpacity(1-progress);
			icon.getElement().getStyle().setProperty("transform", "rotate(" + 180*progress + "deg)");
		}
	};
	public CheckBox(int size) {
		initWidget(layout);
		layout.add(square);
		square.add(icon);
		style(this);
		setSize(size+"px", size+"px");
		icon.getElement().getStyle().setFontSize(size-2, Unit.PX);
		square.getElement().getStyle().setFontSize(size-2, Unit.PX);
		square.getElement().getStyle().setLineHeight(size-2, Unit.PX);
		layout.addDomHandler(evt->{if(enabled) setValue(!value);}, ClickEvent.getType());
	}
	
	@Override
	public void fireEvent(GwtEvent<?> event) {
		if(event instanceof ValueChangeEvent) bus.fireEvent(event);
		else super.fireEvent(event);
	}

	@Override
	public CheckBox style(CheckBox w) {
		layout.setStyleName(StyleCheckBox.GSS.layout());
		layout.getWidgetContainerElement(square).getStyle().setOverflow(Overflow.VISIBLE);
		icon.addStyleName(StyleCheckBox.GSS.check());
		square.addStyleName(StyleCheckBox.GSS.square());
		return this;
	}

	@Override
	public CheckBox setValue(Boolean value) {
		return setValue(value, false);
	}
	
	public CheckBox setValue(Boolean value, boolean immediate) {
		if(value) {
			if(immediate) check.run(0);
			else check.run(80);
		} else {
			if(immediate) uncheck.run(0);
			else uncheck.run(0);
		}
		boolean oldValue = this.value;
		this.value = value;
		getElement().setAttribute("value", String.valueOf(value));
		ValueChangeEvent.fireIfNotEqual(this, oldValue, value);
		return this;
	}

	@Override
	public Boolean getValue() {
		return this.value;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public CheckBox setEnabled(boolean enabled) {
		this.enabled = enabled;
		square.setVisible(enabled);
		return this;
	}
	
	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Boolean> handler) {
		return bus.addHandler(ValueChangeEvent.getType(), handler);
	}
}
