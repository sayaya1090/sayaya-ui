package net.sayaya.ui.widget.textbox;

import java.util.Arrays;
import java.util.Set;

import com.google.common.collect.Sets;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;

import net.sayaya.ui.style.StyleChip;
import net.sayaya.ui.widget.InputBase;
import net.sayaya.ui.widget.chip.ChipDeletable;

public class ChipBox extends Composite implements InputBase<String[], ChipBox> {
	private final EventBus bus = new SimpleEventBus();
	private final FlowPanel widget;
	private final InputBase<String, ?> textbox;
	private final HorizontalPanel hp = new HorizontalPanel();
	private final Set<String> chips = Sets.newHashSet();
	public ChipBox(InputBase<String, ?> elem) {
		widget = new FlowPanel();
		textbox = elem;
		initWidget(widget);
		style(this);
		textbox.asWidget().addDomHandler(evt->{
			if(evt.getNativeEvent().getKeyCode() != 13) return;
			String value = textbox.getValue();
			chips.add(value);
			ChipDeletable chip = new ChipDeletable(value, evt2->chips.remove(evt2));
			hp.add(chip);
			chip.addStyleName(StyleChip.GSS.fadeIn());
			ValueChangeEvent.fire(ChipBox.this, getValue());
		}, KeyPressEvent.getType());
		widget.add(textbox);
		ScrollPanel sp = new ScrollPanel();
		widget.add(sp);
		sp.add(hp);
		widget.getElement().getStyle().setDisplay(Display.FLEX);
		sp.getElement().getStyle().setOverflow(Overflow.AUTO);
		hp.setSpacing(10);
	}
	public ChipBox() {
		this(new TextBox());
	}
	
	@Override
	public ChipBox style(ChipBox widet) {
		return this;
	}

	@Override
	public ChipBox setValue(String... value) {
		Arrays.stream(value)
		.peek(chips::add)
		.map(v->new ChipDeletable(v, evt->{
			chips.remove(evt);
			ValueChangeEvent.fire(ChipBox.this, getValue());
		}))
		.forEach(hp::add);
		return this;
	}

	@Override
	public String[] getValue() {
		return chips.stream().toArray(String[]::new);
	}

	@Override
	public boolean isEmpty() {
		return chips.isEmpty();
	}
	
	@Override
	public void fireEvent(GwtEvent<?> event) {
		if(event instanceof ValueChangeEvent) bus.fireEvent(event);
		else super.fireEvent(event);
	}

	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String[]> handler) {
		return bus.addHandler(ValueChangeEvent.getType(), handler);
	}

	@Override
	public ChipBox setEnabled(boolean enabled) {
		textbox.setEnabled(enabled);
		return this;
	}

	@Override
	public boolean isEnabled() {
		return textbox.isEnabled();
	}
}
