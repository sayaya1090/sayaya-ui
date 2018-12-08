package net.sayaya.ui.widget.shape.impl;

import net.sayaya.ui.shape.HasStroke;
import net.sayaya.ui.widget.shape.HasColor;

public class Circle extends ShapeInstance<Circle> implements HasStroke, HasColor {
	private double x, y, radius;
	private String color;
	private double alpha=1.0;
	private String borderColor;
	private double borderWidth;
	public Circle() {
		super("circle");
	}
	
	public double getX() {
		return x;
	}
	
	public Circle setX(double x) {
		this.x = x;
		getElement().setAttribute("cx", String.valueOf(x));
		return this;
	}
	
	public double getY() {
		return y;
	}
	
	public Circle setY(double y) {
		this.y = y;
		getElement().setAttribute("cy", String.valueOf(y));
		return this;
	}
	
	public double getRadius() {
		return radius;
	}
	
	public Circle setRadius(double radius) {
		this.radius = radius;
		getElement().setAttribute("r", String.valueOf(radius));
		return this;
	}
	
	@Override
	public String getColor() {
		return color;
	}

	@Override
	public Circle setColor(String color) {
		this.color = color;
		getElement().setAttribute("fill", color);
		return this;
	}

	@Override
	public double getAlpha() {
		return alpha;
	}

	@Override
	public Circle setAlpha(double alpha) {
		getElement().setAttribute("fill-opacity", String.valueOf(alpha));
		return this;
	}

	public double getBorderWidth() {
		return borderWidth;
	}
	
	@Override
	public Circle setBorderWidth(double borderWidth) {
		this.borderWidth = borderWidth;
		getElement().setAttribute("stroke-width", String.valueOf(borderWidth));
		return this;
	}

	@Override
	public String getBorderColor() {
		return borderColor;
	}

	@Override
	public Circle setBorderColor(String borderColor) {
		this.borderColor = borderColor;
		getElement().setAttribute("stroke", borderColor);
		return this;
	}
}
