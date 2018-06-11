package net.sayaya.ui.widget;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.LayoutPanel;

import net.sayaya.ui.icon.Icon;
import net.sayaya.ui.style.StyleCheckBox;

public class CheckBox extends Composite implements InputBase<Boolean, CheckBox> {
	private final Icon icon = Icon.create(Icon.GSS.check());
	private final Icon square = Icon.create(Icon.GSS.square());
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
		layout.add(icon);
		layout.add(square);
		style(this);
		setSize(size+"px", size+"px");
		icon.getElement().getStyle().setFontSize(size-4, Unit.PX);
		icon.getElement().getStyle().setProperty("lineHeight", "normal");
		square.getElement().getStyle().setFontSize(size, Unit.PX);
		square.getElement().getStyle().setProperty("lineHeight", "normal");
		layout.addDomHandler(evt->{if(enabled) setValue(!value);}, ClickEvent.getType());
		layout.setWidgetTopBottom(icon, 2, Unit.PX, 2, Unit.PX);
		layout.setWidgetLeftRight(icon, 2, Unit.PX, 2, Unit.PX);
		layout.getElement().getStyle().setOverflow(Overflow.AUTO);
	}

	@Override
	public CheckBox style(CheckBox w) {
		icon.addStyleName(StyleCheckBox.GSS.check());
		return this;
	}

	@Override
	public CheckBox setValue(Boolean value) {
		if(value) check.run(80);
		else uncheck.run(80);
		this.value = value;
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
}
