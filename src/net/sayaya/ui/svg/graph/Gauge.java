package net.sayaya.ui.svg.graph;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.TextAlign;
import com.google.gwt.canvas.dom.client.Context2d.TextBaseline;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;

import net.sayaya.ui.svg.shape.Animation;
import net.sayaya.ui.svg.shape.Canvas;
import net.sayaya.ui.svg.shape.HasStroke;

public class Gauge<N extends Number> extends Canvas implements HasValue<N>, HasStroke {
	private N min, max, value;
	private String backgroundColor;
	private String borderColor;
	private double borderWidth = 1;
	private final Animation animation = new Animation(this);
//	private final Text label;
//	private final RectangleAnimated bar;
	
	public Gauge(int width, int height) {
		super(width, height);
//		bar = new RectangleAnimated(0, height);
//		label = new Text("").setX(width/2.0).setY(height/2.0)
//		.setSize(10)
//		.setAlign(TextAlign.CENTER).setBaseline(TextBaseline.MIDDLE)
//		.setFont("Noto Sans KR").setColor(Palette.getInstance().getColorText1());
//		add(bar);
//		add(label);
	}
	
	public void update(int duration) {
		if(!animation.isRunning()) animation.run(duration);
	}
	
	public void paint() {
		if(animation.isRunning()) animation.cancel();
//		super.paint();
	}
	
	@Override
	public N getValue() {
		return value;
	}

	public final Gauge<N> setMin(N min) {
		this.min = min;
		return this;
	}
	public N getMin() {
		return min;
	}
	public final Gauge<N> setMax(N max) {
		this.max = max;
		return this;
	}
	public N getMax() {
		return max;
	}

	@Override
	public String getBorderColor() {
		return borderColor;
	}
	@Override
	public Gauge<N> setBorderColor(String borderColor) {
		this.borderColor = borderColor;
		getElement().getStyle().setBorderColor(borderColor);
		return this;
	}
	@Override
	public Gauge<N> setBorderWidth(double width) {
		this.borderWidth = width;
		return this;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public Gauge<N> setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
		return this;
	}

	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<N> handler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setValue(N value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setValue(N value, boolean fireEvents) {
		// TODO Auto-generated method stub
		
	}
}
