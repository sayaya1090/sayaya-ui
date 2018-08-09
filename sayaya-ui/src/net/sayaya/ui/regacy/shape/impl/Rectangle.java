package net.sayaya.ui.regacy.shape.impl;

import com.google.gwt.canvas.dom.client.Context2d;

import net.sayaya.ui.regacy.shape.HasColor;
import net.sayaya.ui.regacy.shape.HasStroke;

public class Rectangle extends ShapeInstance<Rectangle> implements HasColor, HasStroke {
	private int width;
	private int height;
	private String color;
	private String borderColor;
	private double borderWidth = 1;
	private double alpha = 1.0;
	public Rectangle(int width, int height) {
		setWidth(width);
		setHeight(height);
	}

	@Override
	public boolean checkIn(double x, double y) {
		int lx = 0;
		int rx = width;
		int ty = 0;
		int by = height;
		
		if(lx > x || rx < x) return false;
		if(ty > y || by < y) return false;
		return true;
	}
	@Override
	public void draw(Context2d context, double progress) {
		context.setLineWidth(borderWidth);
		context.rect(0, 0, width, height);
		if(color!=null) {
			context.setGlobalAlpha(alpha);
			context.setFillStyle(color);
			context.fill();
		}
		if(borderColor!=null) {
			context.setStrokeStyle(borderColor);
			context.stroke();
		}
	}

	public int getWidth() {
		return width;
	}

	public Rectangle setWidth(int width) {
		this.width = width;
		return this;
	}

	public int getHeight() {
		return height;
	}

	public Rectangle setHeight(int height) {
		this.height = height;
		return this;
	}

	public String getColor() {
		return color;
	}

	public Rectangle setColor(String color) {
		this.color = color;
		return this;
	}

	public String getBorderColor() {
		return borderColor;
	}

	public Rectangle setBorderColor(String borderColor) {
		this.borderColor = borderColor;
		return this;
	}

	@Override
	public Rectangle setBorderWidth(double width) {
		this.borderWidth = width;
		return this;
	}
	
	public double getBorderWidth() {
		return borderWidth;
	}
	@Override
	public double getAlpha() {
		return alpha;
	}
	@Override
	public Rectangle setAlpha(double alpha) {
		this.alpha = alpha;
		return this;
	}
}
