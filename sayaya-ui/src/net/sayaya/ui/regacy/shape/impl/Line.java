package net.sayaya.ui.regacy.shape.impl;

import com.google.gwt.canvas.dom.client.Context2d;

import net.sayaya.ui.regacy.data.Point;
import net.sayaya.ui.regacy.shape.HasStroke;

public class Line extends ShapeInstance<Line> implements HasStroke {
	private final Integer[] start;
	private final Integer[] end;
	private String color;
	private double borderWidth;
	public Line(Integer[] start, Integer[] end) {
		this.start = start;
		this.end = end;
	}
	
	public Line(Point<? extends Number, ? extends Number> start, Point<? extends Number, ? extends Number> end) {
		this.start = new Integer[] {start.getX().intValue(), start.getY().intValue()};
		this.end  = new Integer[] {end.getX().intValue(), end.getY().intValue()};
	}
	@Override
	public boolean checkIn(double x, double y) {
		return false;
	}

	@Override
	public void draw(Context2d context, double progress) {
		context.setLineWidth(borderWidth);
		context.moveTo(start[0], start[1]);
		context.lineTo(end[0], end[1]);
		if(color!=null) {
			context.setStrokeStyle(color);
			context.stroke();
		}
	}
	
	@Override
	public String getBorderColor() {
		return this.color;
	}
	@Override
	public Line setBorderColor(String color) {
		this.color = color;
		return this;
	}
	@Override
	public Line setBorderWidth(double width) {
		this.borderWidth = width;
		return this;
	}
}
