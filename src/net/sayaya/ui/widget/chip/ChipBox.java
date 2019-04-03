package net.sayaya.ui.widget.chip;

import java.util.Arrays;
import java.util.Set;

import com.google.common.collect.Sets;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

import net.sayaya.ui.style.StyleChip;
import net.sayaya.ui.style.StyleTextBox;
import net.sayaya.ui.widget.InputBase;

public class ChipBox extends Composite implements InputBase<String[], ChipBox> {
	private final FlowPanel widget;
	private final com.google.gwt.user.client.ui.TextBox textbox;
	private final HorizontalPanel hp = new HorizontalPanel();
	private final Set<String> chips = Sets.newHashSet();
	public ChipBox() {
		widget = new FlowPanel();
		textbox = new com.google.gwt.user.client.ui.TextBox();
		initWidget(widget);
		style(this);
		textbox.addValueChangeHandler(evt->{
			String value = evt.getValue();
			chips.add(value);
			ChipDeletable chip = new ChipDeletable(value, evt2->chips.remove(evt2));
			hp.add(chip);
			chip.addStyleName(StyleChip.GSS.fadeIn());
		});
		widget.add(textbox);
		ScrollPanel sp = new ScrollPanel();
		widget.add(sp);
		sp.add(hp);
		widget.getElement().getStyle().setDisplay(Display.FLEX);
		sp.getElement().getStyle().setOverflow(Overflow.AUTO);
		hp.setSpacing(10);
	}
	
	@Override
	public ChipBox style(ChipBox widet) {
		textbox.setStyleName(StyleTextBox.GSS.textbox());
		textbox.setHeight("36px");
		return this;
	}

	@Override
	public ChipBox setValue(String... value) {
		Arrays.stream(value)
		.peek(chips::add)
		.map(v->new ChipDeletable(v, evt->chips.remove(evt)))
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
	
	public Widget getEditor() {
		return textbox;
	}

	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String[]> handler) {
		// TODO Auto-generated method stub
		return null;
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
