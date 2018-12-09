package net.sayaya.ui.widget.shape.impl;

public class Fan extends Path {
	private double start;
	private double end;
	private double radiusInner;
	private double radiusOut;
	
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
		update();
	}
	
	private void update() {
		clear();
		if(start > 2*Math.PI) do { start -= 2*Math.PI; } while (start > 2*Math.PI);
		if(end > 2*Math.PI) do { end -= 2*Math.PI; } while (end > 2*Math.PI);
		if(end < start) {
			double tmp = start;
			start = end;
			end = tmp;
		}
		double theta = end-start;
		if(theta > 2*Math.PI) do { theta -= 2*Math.PI; } while (theta > 2*Math.PI);
		double sc = Math.cos(start);
		double ss = Math.sin(start);
		double ec = Math.cos(end);
		double es = Math.sin(end);
		
		moveTo(radiusInner*sc, radiusInner*ss)
		.arc(radiusInner, radiusInner, 0, theta>Math.PI?1:0, 1, radiusInner*ec, radiusInner*es)
		.lineTo(radiusOut*ec, radiusOut*es)
		.arc(radiusOut, radiusOut, 0, theta>Math.PI?1:0, 0, radiusOut*sc, radiusOut*ss)
		.close().build();
	}
	public Fan setStart(double start) {
		this.start = start;
		update();
		return this;
	}
	
	public Fan setEnd(double end) {
		this.end = end;
		update();
		return this;
	}
	public double getStart() {
		return start;
	}
	public double getEnd() {
		return end;
	}
	public Fan setRadiusInner(double radiusInner) {
		this.radiusInner = radiusInner;
		update();
		return this;
	}
	public Fan setRadiusOut(double radiusOut) {
		this.radiusOut = radiusOut;
		update();
		return this;
	}
}
