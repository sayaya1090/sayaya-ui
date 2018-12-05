package net.sayaya.ui.widget.shape.impl;

import net.sayaya.ui.shape.HasStroke;
import net.sayaya.ui.widget.SVG;

public class Line extends ShapeInstance<Line> implements HasStroke {
	private final Double[] start;
	private final Double[] end;
	private String color;
	private double borderWidth;
	public Line(SVG svg) {
		this(svg, new Double[] {0.0, 0.0}, new Double[] {0.0, 0.0});
	}
	public Line(SVG svg, Double[] start, Double[] end) {
		super(svg, "line");
		this.start = start;
		this.end = end;
	}
	
	@Override
	public String getBorderColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HasStroke setBorderColor(String color) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HasStroke setBorderWidth(double width) {
		// TODO Auto-generated method stub
		return null;
	}

}
