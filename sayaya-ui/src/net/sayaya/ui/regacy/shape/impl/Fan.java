package net.sayaya.ui.regacy.shape.impl;

import com.google.gwt.canvas.dom.client.Context2d;

import net.sayaya.ui.regacy.shape.HasColor;
import net.sayaya.ui.regacy.shape.HasStroke;

public class Fan extends ShapeInstance<Fan> implements HasColor, HasStroke {
	private double start;
	private double end;
	private double radiusInner;
	private double radiusOut;
	private String color;
	private String borderColor;
	private double borderWidth = 1;
	private double alpha = 1.0;
	public Fan(double radius) {
		this(0, Math.PI*2, radius);
	}
	public Fan(double start, double end, double radiusOut) {
		this(start, end, 0, radiusOut);
	}
	public Fan(double start, double end, double radiusInner, double radiusOut) {
		this.start = start;
		this.end = end;
		this.radiusInner = radiusInner;
		this.radiusOut = radiusOut;
	}
	@Override
	public boolean checkIn(double x, double y) {
		double[] ortho = translate(x, y);
		double r = ortho[0];
		double theta = ortho[1];
		
		if(r < getRadiusInner() || r > getRadiusOut()) return false;
		if(theta < getStart() || theta > getEnd()) return false;
		return true;
	}
	
	private double[] translate(double x, double y) {
		double theta = Math.atan2(y, x);
		if(theta < 0) theta += Math.PI*2;
		return new double[] {
			Math.sqrt(x*x + y*y)
			, theta
		};
	}

	@Override
	public void draw(Context2d context, double progress) {
		context.setLineWidth(borderWidth);
		context.beginPath();
		context.moveTo(radiusInner * Math.cos(start), radiusInner * Math.sin(start));
		context.arc(0, 0, radiusOut, start, end);
		context.lineTo(radiusInner * Math.cos(end), radiusInner * Math.sin(end));
		context.arc(0, 0, radiusInner, end, start, true);
		context.closePath();
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
	public double getStart() {
		return start;
	}
	public Fan setStart(double start) {
		this.start = start;
		return this;
	}
	public double getEnd() {
		return end;
	}
	public Fan setEnd(double end) {
		this.end = end;
		return this;
	}
	public double getRadiusInner() {
		return radiusInner;
	}
	public Fan setRadiusInner(double radiusInner) {
		this.radiusInner = radiusInner;
		return this;
	}
	public double getRadiusOut() {
		return radiusOut;
	}
	public Fan setRadiusOut(double radiusOut) {
		this.radiusOut = radiusOut;
		return this;
	}
	public String getColor() {
		return color;
	}
	public Fan setColor(String color) {
		this.color = color;
		return this;
	}
	@Override
	public String getBorderColor() {
		return borderColor;
	}
	@Override
	public Fan setBorderColor(String borderColor) {
		this.borderColor = borderColor;
		return this;
	}
	@Override
	public Fan setBorderWidth(double width) {
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
	public Fan setAlpha(double alpha) {
		this.alpha = alpha;
		return this;
	}
}
