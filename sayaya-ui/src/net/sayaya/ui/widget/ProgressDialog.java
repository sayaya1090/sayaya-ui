package net.sayaya.ui.widget;

import java.util.LinkedList;
import java.util.Objects;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import net.sayaya.ui.handler.Callback;
import net.sayaya.ui.regacy.graph.Gauge;
import net.sayaya.ui.style.color.Palette;

public class ProgressDialog<T> extends DialogBox implements Callback<T> {
	private final Label msg = new Label();
	private final static NumberFormat NF = NumberFormat.getFormat("0.00%");
	private final Gauge<Integer> gauge;
	private int complete = 0;
	private final int max;
	private final Callback<T[]> callback;

	public ProgressDialog(String title, int max, Callback<T[]> callback) {
		this(title, new VerticalPanel(), max, callback);
	}
	
	private ProgressDialog(String title, VerticalPanel vp, int max, Callback<T[]> callback) {
		super(title, vp);
		msg.setText(title);
		this.max = max;
		this.callback = callback;
		gauge = new Gauge<Integer>(300, 15).setMin(0).setMax(max).setValue(0);
		vp.add(msg);
		vp.add(gauge);
		setStyleLabel(msg.getElement().getStyle());
		setStyleGauge(gauge.getElement().getStyle());
		gauge.getLabel().setFont("Noto Sans KR").setColor(Palette.getInstance().getColorText1()).setSize(10);
		gauge.getBar().setColor(Palette.getInstance().getColorDevider());
		gauge.paint();
		vp.getElement().getStyle().setPadding(5, Unit.PX);
		setText("Save");
		center();
		show();
	}
	
	private void setStyleLabel(Style style) {
		style.setProperty("fontFamily", "Noto Sans KR");
		style.setFontSize(12, Unit.PX);
	}
	
	private void setStyleGauge(Style style) {
		style.setBorderWidth(1.0, Unit.PX);
		style.setBorderColor(Palette.getInstance().getColorDevider());
		style.setBorderStyle(BorderStyle.SOLID);
		style.setProperty("borderRadius", "0px");
		style.setFontSize(7, Unit.PX);
	}
	
	public synchronized void done() {
		++complete;
		gauge.getLabel().setText(NF.format(complete / (double)gauge.getMax()));
		gauge.setValue(complete).update(100);
	}
	
	public int getComplete() {
		return complete;
	}

	private final LinkedList<T> completes = new LinkedList<>();
	private Throwable throwable = null;
	@SuppressWarnings("unchecked")
	@Override
	public void onSuccess(T result) {
		done();
		completes.add(result);
		if(getComplete() >= max) {
			hide();
			if(throwable == null) Scheduler.get().scheduleDeferred(()->{
				if(completes.stream().filter(Objects::nonNull).findAny().isPresent()) callback.onSuccess((T[]) completes.stream().toArray());
				else callback.onSuccess(null);
			});
			else Scheduler.get().scheduleDeferred(()->callback.onFailure(throwable));
		}
	}

	@Override
	public void onFailure(Throwable reason) {
		done();
		completes.add(null);
		if(throwable == null) throwable = reason;
		else throwable.addSuppressed(reason);
		if(getComplete() >= max) {
			hide();
			Scheduler.get().scheduleDeferred(()->callback.onFailure(throwable));
		}
	}
}
