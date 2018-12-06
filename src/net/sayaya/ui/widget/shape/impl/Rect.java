package net.sayaya.ui.widget.shape.impl;

import net.sayaya.ui.shape.HasStroke;
import net.sayaya.ui.widget.SVG;
import net.sayaya.ui.widget.shape.HasColor;

public class Rect extends ShapeInstance<Rect> implements HasStroke, HasColor {
	private double x, y, width, height;
	private String color;
	private double alpha=1.0;
	private String borderColor;
	private double borderWidth;
	public Rect(SVG canvas) {
		super(canvas, "rect");
	}
	
	public double getX() {
		return x;
	}
	
	public Rect setX(double x) {
		this.x = x;
		getElement().setAttribute("x", String.valueOf(x));
		return this;
	}
	
	public double getY() {
		return y;
	}
	
	public Rect setY(double y) {
		this.y = y;
		getElement().setAttribute("y", String.valueOf(y));
		return this;
	}
	
	public double getWidth() {
		return width;
	}
	
	public Rect setWidth(double width) {
		this.width = width;
		getElement().setAttribute("width", String.valueOf(width));
		return this;
	}
	
	public double getHeight() {
		return height;
	}
	
	public Rect setHeight(double height) {
		this.height = height;
		getElement().setAttribute("height", String.valueOf(height));
		return this;
	}

	@Override
	public String getColor() {
		return color;
	}

	@Override
	public Rect setColor(String color) {
		this.color = color;
		getElement().setAttribute("fill", color);
		return this;
	}

	@Override
	public double getAlpha() {
		return alpha;
	}

	@Override
	public Rect setAlpha(double alpha) {
		getElement().setAttribute("fill-opacity", String.valueOf(alpha));
		return this;
	}

	public double getBorderWidth() {
		return borderWidth;
	}
	
	@Override
	public Rect setBorderWidth(double borderWidth) {
		this.borderWidth = borderWidth;
		getElement().setAttribute("stroke-width", String.valueOf(borderWidth));
		return this;
	}

	@Override
	public String getBorderColor() {
		return borderColor;
	}

	@Override
	public Rect setBorderColor(String borderColor) {
		this.borderColor = borderColor;
		getElement().setAttribute("stroke", borderColor);
		return this;
	}
}
