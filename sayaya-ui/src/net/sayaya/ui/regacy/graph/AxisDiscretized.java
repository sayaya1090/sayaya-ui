package net.sayaya.ui.regacy.graph;

import java.util.HashMap;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.TextAlign;

import net.sayaya.ui.regacy.shape.HasStroke;

@SuppressWarnings("unchecked")
public class AxisDiscretized<T> extends Axis<T> implements HasStroke {
	private T[] variables;
	private HashMap<T, Integer> map = new HashMap<T, Integer>();
	private String borderColor;
	private double borderWidth;
	private int width;
	private int height;

	public AxisDiscretized(AXIS_DIRECTION direction) {
		super(direction);
	}
	public AxisDiscretized<T> setVariables(T... variables) {
		this.variables = variables;
		map.clear();
		for(int i = 0; i < variables.length; ++i) map.put(variables[i], i);
		return this;
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
		int split = variables.length;
		double step = width / (double)split;
		context.save();
		context.setStrokeStyle(borderColor);
		context.setLineWidth(borderWidth/2);
		context.setFillStyle(borderColor);
		context.setFont("8pt " + borderColor);
		int stepSize = 10;
		int textLoc = 20;
		if(isInverted()) context.scale(1.0, -1.0);
		for(int i = 0; i <= split; ++i) {
			context.beginPath();
			context.moveTo(step*i, 0);
			context.lineTo(step*i, stepSize);
			context.stroke();
			
			if(i < split) {
				context.save();
				context.setTextAlign(TextAlign.CENTER);
				context.translate(step*(i+0.5), textLoc);
				if(isInverted()) context.scale(1.0, -1.0);
				context.rotate(-getRotate());
				context.fillText(toString(variables[i]), 0, 0);
				context.restore();
			}
		}
		context.restore();
	}
	@Override
	public double parse(T variable) {
		int i = map.get(variable);
		if(i < 0) return -1;
		
		int split = variables.length;
		double step = width / (double)split;
		return step*i + getX();
	}

	public double getWidth(T variable) {
		int split = variables.length;
		return width / (double)split;
	}
	
	protected String toString(T data) {
		return data.toString();
	}

	@Override
	public boolean checkIn(double x, double y) {
		return false;
	}

	@Override
	public void draw(Context2d context, double progress) {
		drawHorizontalLine(context, progress);
		drawStep(context, progress);
	}

	public int getWidth() {
		return width;
	}

	public AxisDiscretized<T> setWidth(int width) {
		this.width = width;
		return this;
	}

	public int getHeight() {
		return height;
	}

	public AxisDiscretized<T> setHeight(int height) {
		this.height = height;
		return this;
	}

	@Override
	public String getBorderColor() {
		return borderColor;
	}

	@Override
	public AxisDiscretized<T> setBorderColor(String color) {
		this.borderColor = color;
		return this;
	}

	@Override
	public AxisDiscretized<T> setBorderWidth(double width) {
		this.borderWidth = width;
		return this;
	}
}
