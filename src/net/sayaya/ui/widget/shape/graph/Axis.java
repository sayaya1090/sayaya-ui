package net.sayaya.ui.widget.shape.graph;

import net.sayaya.ui.shape.HasStroke;
import net.sayaya.ui.widget.shape.impl.ShapeInstance;

public abstract class Axis<T> extends ShapeInstance<Axis<T>> implements HasStroke {
	private final AXIS_DIRECTION direction;
	private final boolean isInverted;
	private String borderColor;
	private double borderWidth;
	private double x, y;
	private double width;
	private double height;
	
	public Axis(AXIS_DIRECTION direction) {
		super("path");
		this.direction = direction;
		switch(direction) {
		case X_BOTTOM:
			isInverted = false;
			break;
		case X_TOP:
			isInverted = true;
			break;
		case Y_LEFT:
			isInverted = true;
			transform(new Rotate(-Math.PI/2));
			break;
		case Y_RIGHT:
			isInverted = false;
			transform(new Rotate(-Math.PI/2));
			break;
		default:
			isInverted = false;
			break;
		}
	}
	public boolean isInverted() {
		return isInverted;
	}
	public AXIS_DIRECTION getDirection() {
		return direction;
	}

	public abstract double map(T data);
	
	public double getX() {
		return x;
	}
	
	public Axis<T> setX(double x) {
		this.x = x;
		return this;
	}
	
	public double getY() {
		return y;
	}
	
	public Axis<T> setY(double y) {
		this.y = y;
		return this;
	}
	@Override
	public Axis<T> setBorderWidth(double width) {
		this.width = width;
		getElement().setAttribute("stroke-width", String.valueOf(width));
		return this;
	}

	@Override
	public String getBorderColor() {
		return borderColor;
	}

	@Override
	public Axis<T> setBorderColor(String color) {
		this.borderColor = color;
		getElement().setAttribute("stroke", color);
		return this;
	}
	public double getWidth() {
		return width;
	}

	public Axis<T> setWidth(double width) {
		this.width = width;
		return this;
	}

	public double getHeight() {
		return height;
	}

	public Axis<T> setHeight(double height) {
		this.height = height;
		return this;
	}
	public static enum AXIS_DIRECTION {
		X_BOTTOM
		, X_TOP
		, Y_LEFT
		, Y_RIGHT
	}
}
