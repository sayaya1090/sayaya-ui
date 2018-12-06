package net.sayaya.ui.widget.shape.graph;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.TextAlign;
import com.google.gwt.i18n.client.NumberFormat;

import net.sayaya.ui.widget.SVG;
import net.sayaya.ui.widget.shape.HasStroke;

public class AxisContinuous<T extends Number> extends Axis<T> implements HasStroke {
	private T min, max;
	private T step;
	private NumberFormat nf = NumberFormat.getDecimalFormat();
	private String borderColor;
	private double borderWidth;
	private double width;
	private double height;
	public AxisContinuous(SVG svg, AXIS_DIRECTION direction) {
		super(svg, direction);
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

	protected void drawHorizontalLine(Context2d context, double percent) {
		context.save();
		context.setLineWidth(borderWidth);
		context.setStrokeStyle(borderColor);
		context.beginPath();
		context.moveTo(0, 0);
		context.lineTo(width, 0);
		context.stroke();
		context.restore();
	}
	
	protected void drawStep(Context2d context, double percent) {
		double length = max.doubleValue() - min.doubleValue();
		double scaleX = width / length;
		double translate = -min.doubleValue();
		
		context.save();
		context.setLineWidth(1);
		context.setStrokeStyle(borderColor);
		context.setLineWidth(borderWidth/2);
		context.setFillStyle(borderColor);
		context.setFont("8pt " + borderColor);
		if(isInverted()) context.scale(1.0, -1.0);
		Double p = min.doubleValue();
		double step = this.step.doubleValue();
		double stepSize = 10;
		double textLoc = 20;
		while(p.compareTo(max.doubleValue()) <= 0) {
			context.beginPath();
			context.moveTo((p+translate)*scaleX, 0);
			context.lineTo((p+translate)*scaleX, stepSize);
			context.stroke();
			
			{
				context.save();
				context.setTextAlign(TextAlign.CENTER);
				context.translate((p+translate)*scaleX, textLoc);
				if(isInverted()) context.scale(1.0, -1.0);
				context.rotate(-getRotate());
				context.fillText(toString(p), 0, 4);
				context.restore();
			}
			
			p += step;
		}
		context.restore();
	}
	
	@Override
	public void draw(Context2d context, double progress) {
		drawHorizontalLine(context, progress);
		drawStep(context, progress);
	}

	@Override
	public double map(T data) {
		double length = max.doubleValue() - min.doubleValue();
		double scaleX = width / length;
		double translate = -min.doubleValue();
		if(this.getDirection() == AXIS_DIRECTION.Y_LEFT || this.getDirection() == AXIS_DIRECTION.Y_RIGHT) 
			return getY()-(data.doubleValue() + translate) * scaleX;
		else return getX() + (data.doubleValue() + translate) * scaleX;
	}
	public double getWidth() {
		return width;
	}

	public AxisContinuous<T> setWidth(double width) {
		this.width = width;
		return this;
	}

	public double getHeight() {
		return height;
	}

	public AxisContinuous<T> setHeight(double height) {
		this.height = height;
		return this;
	}

	@Override
	public String getBorderColor() {
		return borderColor;
	}

	@Override
	public AxisContinuous<T> setBorderColor(String color) {
		this.borderColor = color;
		return this;
	}

	@Override
	public AxisContinuous<T> setBorderWidth(double width) {
		this.borderWidth = width;
		return this;
	}
}
