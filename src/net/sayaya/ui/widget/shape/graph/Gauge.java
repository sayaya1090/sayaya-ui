package net.sayaya.ui.widget.shape.graph;

import net.sayaya.ui.handler.HasValue;
import net.sayaya.ui.style.color.Color;
import net.sayaya.ui.style.color.Palette;
import net.sayaya.ui.widget.SVG;
import net.sayaya.ui.widget.shape.impl.Rect;
import net.sayaya.ui.widget.shape.impl.Text;
import net.sayaya.ui.widget.shape.impl.Text.AlignmentBaseline;
import net.sayaya.ui.widget.shape.impl.Text.TextAnchor;

public class Gauge<N extends Number> extends SVG implements HasValue<N> {
	private N value;
	private int width;
	private double interval;
	private final Rect bar = new Rect().setX(0).setY(0);
	private final Text label = new Text().setBaseline(AlignmentBaseline.middle)
	.setAnchor(TextAnchor.middle).setColor(Color.Crimson).setValue("Hello World!");
	
	public Gauge(N min, N max) {
		interval = max.doubleValue() - min.doubleValue();
		layout();
		style();
	}
	private void layout() {
		add(bar);
		add(label);
		bar.setX(0).setY(0);
	}
	private void style() {
		getElement().getStyle().setProperty("border", "1px solid " + Palette.getInstance().getColorDevider());
		bar.setBorderColor(Palette.getInstance().getColorDevider()).setBorderWidth(0.5).setColor(Palette.getInstance().getColor1());
	}
	
	public Gauge<N> setWidth(int px) {
		this.width = px;
		if(value!=null) bar.setWidth(width * value.doubleValue() / interval);
		label.setX(px / 2.0);
		setWidth(px + "px");
		return this;
	}
	
	public Gauge<N> setHeight(int px) {
		bar.setHeight(px);
		label.setY(px / 2.0);
		setHeight(px + "px");
		return this;
	}
	
	@Override
	public Gauge<N> setValue(N value) {
		this.value = value;
		bar.setWidth(width * value.doubleValue() / interval);
		return this;
	}
	@Override
	public N getValue() {
		return value;
	}
	@Override
	public boolean isEmpty() {
		return value==null;
	}
	public Rect getBar() {
		return bar;
	}
	public Text getText() {
		return label;
	}
}
