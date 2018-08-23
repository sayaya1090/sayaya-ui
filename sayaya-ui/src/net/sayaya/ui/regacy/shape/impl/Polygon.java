package net.sayaya.ui.regacy.shape.impl;

import com.google.gwt.canvas.dom.client.Context2d;

import net.sayaya.ui.shape.HasColor;
import net.sayaya.ui.shape.HasStroke;

public class Polygon extends ShapeInstance<Polygon> implements HasColor, HasStroke {
	private final static class Point {
		private double x, y;

		public double getX() {
			return x;
		}

		public Point setX(double x) {
			this.x = x;
			return this;
		}

		public double getY() {
			return y;
		}

		public Point setY(double y) {
			this.y = y;
			return this;
		}
	}
	
	private final Point[] points;
	private final Point pout;
	private String color;
	private double borderWidth = 1;
	private String borderColor;
	private double alpha = 1.0;
	public Polygon(Double[]... points) {
		this(toPoints(points));
	}
	private final static Point[] toPoints(Double[]...points) {
		Point[] points2 = new Point[points.length];
		for(int i = 0; i < points.length; ++i) points2[i] = new Point().setX(points[i][0]).setY(points[i][1]);
		return points2;
	}
	public Polygon(Point... points) {
		this.points =  points;
		double mx = points[0].x;
		for(Point p: points) mx = Math.min(p.x, mx);	
		pout = new Point().setX(mx-1).setY(0);
	}
	@Override
	public boolean checkIn(double x, double y) {
		Point p = new Point().setX(x).setY(y);
		int cross = 0;
		for(int i = 0; i < points.length-1; ++i) {
			Point b1 = points[i];
			Point b2 = points[i+1];
			
			if(chkCross(p, pout, b1, b2)) ++cross;
		}
		Point b1 = points[points.length-1];
		Point b2 = points[0];
		if(chkCross(p, pout, b1, b2)) ++cross;
		return cross%2==1;
	}

	private boolean chkCross(Point a1, Point a2, Point b1, Point b2) {
		double x1  = a1.x;	double y1  = a1.y;
		double x2 = a2.x;	double y2  = a2.y;
		double x3 = b1.x;	double y3  = b1.y;
		double x4 = b2.x;	double y4  = b2.y;
	
		double ta = (x3 - x4) * (y1 - y3) + (y3 - y4) * (x3 - x1);
		double tb = (x3 - x4) * (y2 - y3) + (y3 - y4) * (x3 - x2);
		double tc = (x1 - x2) * (y3 - y1) + (y1 - y2) * (x1 - x3);
		double td = (x1 - x2) * (y4 - y1) + (y1 - y2) * (x1 - x4);

		return ta*tb < 0 && tc*td < 0;
	}
	
	@Override
	public void draw(Context2d context, double progress) {
		context.setLineWidth(borderWidth);
		context.moveTo(points[points.length-1].x, points[points.length-1].y);
		for(int i = 0; i < points.length; ++i) context.lineTo(points[i].x, points[i].y);
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
	public String getColor() {
		return color;
	}

	public Polygon setColor(String color) {
		this.color = color;
		return this;
	}

	public String getBorderColor() {
		return borderColor;
	}

	public Polygon setBorderColor(String borderColor) {
		this.borderColor = borderColor;
		return this;
	}
	@Override
	public Polygon setBorderWidth(double width) {
		this.borderWidth = width;
		return this;
	}
	@Override
	public double getAlpha() {
		return alpha;
	}
	@Override
	public Polygon setAlpha(double alpha) {
		this.alpha = alpha;
		return this;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder("[Polygon ");
		for(Point point: points) {
			sb.append("(").append(point.getX()).append(",").append(point.getY()).append(") ");
		}
		sb.append("]");
		return sb.toString();
	}
}
