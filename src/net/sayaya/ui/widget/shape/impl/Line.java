package net.sayaya.ui.widget.shape.impl;

import net.sayaya.ui.data.Point;
import net.sayaya.ui.shape.HasStroke;
import net.sayaya.ui.widget.SVG;

public class Line extends ShapeInstance<Line> implements HasStroke {
	private final Point<Double, Double> start = new Point<>();
	private final Point<Double, Double> end = new Point<>();
	private String color;
	private double width;
	public Line(SVG svg) {
		super(svg, "line");
	}
	
	public Line setStart(Point<Double, Double> start) {
		this.start.setX(start.getX());
		this.start.setY(start.getY());
		getElement().setAttribute("x1", String.valueOf(start.getX()));
		getElement().setAttribute("y1", String.valueOf(start.getY()));
		return this;
	}
	
	public Line setEnd(Point<Double, Double> end) {
		this.end.setX(end.getX());
		this.end.setY(end.getY());
		getElement().setAttribute("x2", String.valueOf(end.getX()));
		getElement().setAttribute("y2", String.valueOf(end.getY()));
		return this;
	}
	
	public double getBorderWidth() {
		return width;
	}
	
	@Override
	public Line setBorderWidth(double width) {
		this.width = width;
		getElement().setAttribute("stroke-width", String.valueOf(width));
		return this;
	}

	@Override
	public String getBorderColor() {
		return color;
	}

	@Override
	public Line setBorderColor(String color) {
		this.color = color;
		getElement().setAttribute("stroke", color);
		return this;
	}
}
