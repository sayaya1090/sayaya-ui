package net.sayaya.ui.widget.shape.impl;

import net.sayaya.ui.shape.HasStroke;
import net.sayaya.ui.widget.shape.HasColor;

public class Ellipse extends ShapeInstance<Ellipse> implements HasStroke, HasColor {
	private double x, y, rx, ry;
	private String color;
	private double alpha=1.0;
	private String borderColor;
	private double borderWidth;
	public Ellipse() {
		super("ellipse");
	}
	
	public double getX() {
		return x;
	}
	
	public Ellipse setX(double x) {
		this.x = x;
		getElement().setAttribute("cx", String.valueOf(x));
		return this;
	}
	
	public double getY() {
		return y;
	}
	
	public Ellipse setY(double y) {
		this.y = y;
		getElement().setAttribute("cy", String.valueOf(y));
		return this;
	}
	
	public double getRadiusX() {
		return rx;
	}
	
	public Ellipse setRadiusX(double radius) {
		this.rx = radius;
		getElement().setAttribute("rx", String.valueOf(radius));
		return this;
	}
	
	public double getRadiusY() {
		return ry;
	}
	
	public Ellipse setRadiusY(double radius) {
		this.ry = radius;
		getElement().setAttribute("ry", String.valueOf(radius));
		return this;
	}

	@Override
	public String getColor() {
		return color;
	}

	@Override
	public Ellipse setColor(String color) {
		this.color = color;
		getElement().setAttribute("fill", color);
		return this;
	}

	@Override
	public double getAlpha() {
		return alpha;
	}

	@Override
	public Ellipse setAlpha(double alpha) {
		getElement().setAttribute("fill-opacity", String.valueOf(alpha));
		return this;
	}

	public double getBorderWidth() {
		return borderWidth;
	}
	
	@Override
	public Ellipse setBorderWidth(double borderWidth) {
		this.borderWidth = borderWidth;
		getElement().setAttribute("stroke-width", String.valueOf(borderWidth));
		return this;
	}

	@Override
	public String getBorderColor() {
		return borderColor;
	}

	@Override
	public Ellipse setBorderColor(String borderColor) {
		this.borderColor = borderColor;
		getElement().setAttribute("stroke", borderColor);
		return this;
	}
}
