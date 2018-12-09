package net.sayaya.ui.widget.shape.graph;

import com.google.gwt.i18n.client.NumberFormat;

import net.sayaya.ui.shape.HasStroke;

public class AxisContinuous<T extends Number> extends Axis<T> implements HasStroke {
	private T min, max;
	private T step;
	private NumberFormat nf = NumberFormat.getDecimalFormat();
	
	public AxisContinuous(AXIS_DIRECTION direction) {
		super(direction);
	}
	public AxisContinuous<T> setNumberFormat(NumberFormat nf) {
		this.nf = nf;
		return this;
	}
	
	public AxisContinuous<T> setMin(T min) {
		this.min = min;
		return this;
	}
	
	public AxisContinuous<T> setMax(T max) {
		this.max = max;
		return this;
	}
	
	public T getMin() {
		return min;
	}
	
	public T getMax() {
		return max;
	}
	
	protected String toString(Double value) {
		return nf.format(value);
	}
	
	public AxisContinuous<T> setStep(T step) {
		this.step = step;
		return this;
	}
	
	public T getStep() {
		return step;
	}
	
	@Override
	public double map(T data) {
		double length = max.doubleValue() - min.doubleValue();
		double scaleX = getWidth() / length;
		double translate = -min.doubleValue();
		if(this.getDirection() == AXIS_DIRECTION.Y_LEFT || this.getDirection() == AXIS_DIRECTION.Y_RIGHT) 
			return getY()-(data.doubleValue() + translate) * scaleX;
		else return getX() + (data.doubleValue() + translate) * scaleX;
	}

}
