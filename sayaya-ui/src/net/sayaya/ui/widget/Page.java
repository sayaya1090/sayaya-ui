package net.sayaya.ui.widget;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;

import net.sayaya.ui.handler.HasValue;
import net.sayaya.ui.icon.Icon;
import net.sayaya.ui.style.StylePage;
import net.sayaya.ui.widget.button.ButtonFlat; 

public class Page extends Composite implements HasValue<net.sayaya.ui.dto.Page>, HasValueChangeHandlers<net.sayaya.ui.dto.Page> {
	private final EventBus bus = new SimpleEventBus();
	private final FlowPanel layout = new FlowPanel();
	private final ButtonFlat first = new ButtonFlat().setValue(Icon.create(Icon.GSS.chevronDoubleLeft()));
	private final ButtonFlat prev = new ButtonFlat().setValue(Icon.create(Icon.GSS.caretLeft()));
	private final ButtonFlat next = new ButtonFlat().setValue(Icon.create(Icon.GSS.caretRight()));
	private final ButtonFlat last = new ButtonFlat().setValue(Icon.create(Icon.GSS.chevronDoubleRight()));
	private final IntegerBox showBox = new IntegerBox();
	private final IntegerBox pageBox = new IntegerBox();
	private final net.sayaya.ui.dto.Page value = new net.sayaya.ui.dto.Page().setLimit(1).setOffset(0L);
	private Long total = 0L;
	private Label pageLabel = new Label("Page");
	private Label pageMaxLabel = new Label();
	private Label idx1Label = new Label();
	private Label idx2Label = new Label();
	private Label totalLabel = new Label();
	private Label rppLabel = new Label("Rows per Page");
	public Page() {
		initWidget(layout);
		layout();
		style();
		first.addDomHandler(evt->{
			pageBox.setValue(1, true);
		}, ClickEvent.getType());
		prev.addDomHandler(evt->{
			pageBox.setValue(pageBox.getValue()-1, true);
		}, ClickEvent.getType());
		next.addDomHandler(evt->{
			pageBox.setValue(pageBox.getValue()+1, true);
		}, ClickEvent.getType());
		last.addDomHandler(evt->{
			pageBox.setValue((int)Math.ceil(total/value.getLimit())+1, true);
		}, ClickEvent.getType());
		pageBox.addValueChangeHandler(evt->{
			int idx = value.getLimit()*(evt.getValue()-1);
			setIdx(idx);
			ValueChangeEvent.fire(this, value);
		});
		showBox.addValueChangeHandler(evt->{
			setShow(evt.getValue());
			ValueChangeEvent.fire(this, value);
		});
	}
	
	private void layout() {
		layout.add(first);
		layout.add(prev);
		layout.add(pageLabel);
		layout.add(pageBox);
		layout.add(pageMaxLabel);
		layout.add(next);
		layout.add(last);
		
		layout.add(idx1Label);
		layout.add(idx2Label);
		layout.add(totalLabel);
		layout.add(showBox);
		layout.add(rppLabel);
	}
	
	private void style() {
		setStyleName(StylePage.GSS.page());
		first.addStyleName(StylePage.GSS.iconButton());
		prev.addStyleName(StylePage.GSS.iconButton());
		next.addStyleName(StylePage.GSS.iconButton());
		last.addStyleName(StylePage.GSS.iconButton());
		pageLabel.setStyleName(StylePage.GSS.label());
		pageMaxLabel.setStyleName(StylePage.GSS.label());
		idx1Label.setStyleName(StylePage.GSS.label());
		idx2Label.setStyleName(StylePage.GSS.label());
		totalLabel.setStyleName(StylePage.GSS.label());
		rppLabel.setStyleName(StylePage.GSS.label());
		pageBox.setStyleName(StylePage.GSS.box());
		showBox.setStyleName(StylePage.GSS.box());

		first.addStyleName(StylePage.GSS.item());
		prev.addStyleName(StylePage.GSS.item());
		next.addStyleName(StylePage.GSS.item());
		last.addStyleName(StylePage.GSS.item());
		pageLabel.addStyleName(StylePage.GSS.item());
		pageMaxLabel.addStyleName(StylePage.GSS.item());
		idx1Label.addStyleName(StylePage.GSS.item());
		idx2Label.addStyleName(StylePage.GSS.item());
		totalLabel.addStyleName(StylePage.GSS.item());
		rppLabel.addStyleName(StylePage.GSS.item());
		pageBox.addStyleName(StylePage.GSS.item());
		showBox.addStyleName(StylePage.GSS.item());
		idx1Label.getElement().getStyle().setProperty("marginLeft", "auto");
		rppLabel.getElement().getStyle().setWidth(100, Unit.PX);
	}
	
	@Override
	public void fireEvent(GwtEvent<?> event) {
		if(event instanceof ValueChangeEvent) bus.fireEvent(event);
		else super.fireEvent(event);
	}

	public Long getTotal() {
		return total;
	}

	public Page setTotal(Long total) {
		this.total = total;
		totalLabel.setText("of " + total);
		pageMaxLabel.setText("of " + (Math.ceil(total/value.getLimit())+1));
		idx2Label.setText(String.valueOf(Math.min(total, value.getOffset()+value.getLimit())));
		if(value.getOffset()>=total-value.getLimit()) {
			next.setEnabled(false);
			last.setEnabled(false);
		} else {
			next.setEnabled(true);
			last.setEnabled(true);
		}
		return this;
	}

	public int getShow() {
		return value.getLimit();
	}

	public Page setShow(int show) {
		value.setLimit(show);
		pageBox.setText(String.valueOf(Math.ceil((value.getOffset()+1)/(double)show)));
		showBox.setValue(show);
		pageMaxLabel.setText("of " + (Math.ceil(total/(double)show)+1));
		idx2Label.setText(String.valueOf(Math.min(total, value.getOffset()+show)));
		if(value.getOffset()>=total-value.getLimit()) {
			next.setEnabled(false);
			last.setEnabled(false);
		} else {
			next.setEnabled(true);
			last.setEnabled(true);
		}
		return this;
	}

	public Long getIdx() {
		return value.getOffset();
	}
	
	public Page setIdx(int idx) {
		return setIdx(idx + 0L);
	}

	public Page setIdx(long idx) {
		value.setOffset(idx);
		pageBox.setText(String.valueOf(Math.ceil((idx+1)/(double)value.getLimit())));
		idx1Label.setText((idx+1) + " - ");
		idx2Label.setText(String.valueOf(Math.min(total, idx+value.getLimit())));
		if(idx<=0) {
			prev.setEnabled(false);
			first.setEnabled(false);
		} else {
			prev.setEnabled(true);
			first.setEnabled(true);
		}
		if(idx>=total-value.getLimit()) {
			next.setEnabled(false);
			last.setEnabled(false);
		} else {
			next.setEnabled(true);
			last.setEnabled(true);
		}
		return this;
	}

	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<net.sayaya.ui.dto.Page> handler) {
		return bus.addHandler(ValueChangeEvent.getType(), handler);
	}

	@Override
	public Page setValue(net.sayaya.ui.dto.Page value) {
		setIdx(value.getOffset());
		setShow(value.getLimit());
		return this;
	}

	@Override
	public net.sayaya.ui.dto.Page getValue() {
		return value;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}
}
