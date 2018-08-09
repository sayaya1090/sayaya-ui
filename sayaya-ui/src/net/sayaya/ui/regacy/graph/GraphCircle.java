package net.sayaya.ui.regacy.graph;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.canvas.dom.client.Context2d;

import net.sayaya.ui.handler.HasValue;
import net.sayaya.ui.regacy.data.Point;
import net.sayaya.ui.regacy.shape.HasStroke;
import net.sayaya.ui.regacy.shape.impl.Fan;

public class GraphCircle extends Graph<Double> implements HasValue<Double[]>, HasStroke {
	private double min, max;
	private int radiusOuter=20;
	private int radiusInner=0;
	private String backgroundColor;
	private String borderColor;
	private double borderWidth = 1;
	private List<FanAnimated> shapes = new ArrayList<FanAnimated>();
	
	public GraphCircle(int width, int height) {
		super(width, height);
	}

	public GraphCircle setValue(Double value) {
		if(shapes.isEmpty()) this.setValue(new Double[] {value});
		else setValueAt(0, value);
		return this;
	}
	
	@Override
	public GraphCircle setValue(Double[] values) {
		super.setValue(values);
		setShapes();
		for(int i = 0; i < values.length; ++i) setValueAt(i, values[i]);
		return this;
	}
	public GraphCircle setValueAt(int idx, Double value) {
		FanAnimated fan = shapes.get(idx);
		double length = max - min;
		double ratio = Math.PI*2 * value / length;
		super.getValue()[idx] = value;
		fan.setEndNext(ratio);
		return this;
	}
	
	public FanAnimated getShapeAt(int idx) {
		return shapes.get(idx);
	}

	@Override
	protected void setShapes() {
		shapes.clear();
		super.getShapes().clear();
		double length = max - min;
		Double[] values = getValue();
		int centerX = getWidth() / 2;
		int centerY = getHeight() / 2;
		Fan circle = new Fan(0, Math.PI*2, getRadiusInner(), getRadiusOuter()).setX(centerX).setY(centerY)
			.setColor(backgroundColor).setBorderColor(borderColor).setBorderWidth(borderWidth).setRotate(-Math.PI/2);
		add(circle);
		if(values!=null) for(int i = 0; i < values.length; ++i) {
			double value = values[i].doubleValue();
			double ratio = value / length;
			Double end = Math.PI*2 * ratio;
			Agenda agenda = getAgenda()[i];
			FanAnimated shape = (FanAnimated)createFan(0, end, radiusInner, radiusOuter, i, agenda.getName())
				.setStartNext(0).setEndNext(end).setRadiusInnerNext(radiusInner)
				.setRadiusOutNext(radiusOuter)
				.setPointNext(new Point<Integer, Integer>().setX(centerX).setY(centerY))
				.setX(centerX).setY(centerY)
				.setBorderColor(borderColor).setBorderWidth(borderWidth).setColor(agenda.getColor())
			;
			add(shape);
			shapes.add(shape);
		}
	}
	
	@Override
	public void paint(double progress) {
		double rot = -Math.PI /2;
		for(int i = 0; i < shapes.size(); ++i) {
			Agenda agenda = getAgenda()[i];
			String color = agenda.getColor();
			FanAnimated shape = (FanAnimated) shapes.get(i).setRotate(rot).setColor(color).setBorderColor(borderColor);
			rot += FanAnimated.getNext(shape.getEndPrev(), shape.getEndNext(), progress);
		}
		super.paint(progress);
	}
	
	protected FanAnimated createFan(double start, double end, double radiusInner, double radiusOut, int idx, String label) {
		return new FanAnimated(0, 0, radiusInner, radiusOut);
	}
	public final GraphCircle setMin(double min) {
		this.min = min;
		return this;
	}
	protected final double getMin() {
		return min;
	}
	public final GraphCircle setMax(double max) {
		this.max = max;
		return this;
	}
	protected final double getMax() {
		return max;
	}
	public final int getRadiusOuter() {
		return radiusOuter;
	}

	public final GraphCircle setRadiusOuter(int radiusOuter) {
		this.radiusOuter = radiusOuter;
		return this;
	}

	public final int getRadiusInner() {
		return radiusInner;
	}

	public final GraphCircle setRadiusInner(int radiusInner) {
		this.radiusInner = radiusInner;
		return this;
	}

	@Override
	public String getBorderColor() {
		return borderColor;
	}
	@Override
	public GraphCircle setBorderColor(String borderColor) {
		this.borderColor = borderColor;
		return this;
	}
	@Override
	public GraphCircle setBorderWidth(double width) {
		this.borderWidth = width;
		return this;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public GraphCircle setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
		return this;
	}
	
	@SuppressWarnings("unused")
	private final static class FanAnimated extends Fan {
		private Point<Integer, Integer> pointNext = new Point<Integer, Integer>(0, 0);
		private double rotateNext = 0;
		
		private double startNext = 0;
		private double endNext = 0;
		private double radiusInnerNext = 0;
		private double radiusOutNext = 0;
		
		private Point<Integer, Integer> pointPrev = new Point<Integer, Integer>(0, 0);
		private double rotatePrev = 0;
		
		private double startPrev = 0;
		private double endPrev = 0;
		private double radiusInnerPrev = 0;
		private double radiusOuterPrev = 0;
		public static double getNext(double prev, double next, double progress) {
			return prev + progress*(next-prev);
		}
		public static int getNext(int prev, int next, double progress) {
			return prev + (int)(progress*(next-prev));
		}
		public FanAnimated(double radius) {
			this(0, Math.PI*2, radius);
		}
		public FanAnimated(double start, double end, double radiusOut) {
			this(start, end, 0, radiusOut);
		}
		public FanAnimated(double start, double end, double radiusInner, double radiusOut) {
			super(start, end, radiusInner, radiusOut);
		}
		@Override
		public void draw(Context2d context, double progress) {
			if(progress <= 0) {
				pointPrev.setX(getX());
				pointPrev.setY(getY());
				rotatePrev = getRotate();
				startPrev = getStart();
				endPrev = getEnd();
				radiusInnerPrev = getRadiusInner();
				radiusOuterPrev = getRadiusOut();
			}
			int x = getNext(pointPrev.getX(), pointNext.getX(), progress);
			int y = getNext(pointPrev.getY(), pointNext.getY(), progress);
			double rotate = getNext(rotatePrev, rotateNext, progress);
			double radiusInner = getNext(radiusInnerPrev, radiusInnerNext, progress);
			double radiusOut = getNext(radiusOuterPrev, radiusOutNext, progress);
			double start = getNext(startPrev, startNext, progress);
			double end = getNext(endPrev, endNext, progress);
			String color = getColor();
			String borderColor = getBorderColor();
			
			context.setLineWidth(getBorderWidth());
			context.beginPath();
			context.moveTo(radiusInner * Math.cos(start), radiusInner * Math.sin(start));
			context.arc(0, 0, radiusOut, start, end);
			context.lineTo(radiusInner * Math.cos(end), radiusInner * Math.sin(end));
			context.arc(0, 0, radiusInner, end, start, true);
			context.closePath();
			if(color!=null) {
				context.setFillStyle(color);
				context.fill();
			}
			if(borderColor!=null) {
				context.setStrokeStyle(borderColor);
				context.stroke();
			}
			setX(x); setY(y); setRotate(rotate);
			setStart(start);
			setEnd(end);
			setRadiusInner(radiusInner);
			setRadiusOut(radiusOut);
		}
		public Point<Integer, Integer> getPointNext() {
			return pointNext;
		}
		public FanAnimated setPointNext(Point<Integer, Integer> next) {
			this.pointNext = next;
			return this;
		}
		public double getRotateNext() {
			return rotateNext;
		}
		public FanAnimated setRotateNext(double rotateNext) {
			this.rotateNext = rotateNext;
			return this;
		}
		public double getStartNext() {
			return startNext;
		}
		public FanAnimated setStartNext(double startNext) {
			this.startNext = startNext;
			return this;
		}
		public double getEndNext() {
			return endNext;
		}
		public FanAnimated setEndNext(double endNext) {
			this.endNext = endNext;
			return this;
		}
		public double getRadiusInnerNext() {
			return radiusInnerNext;
		}
		public FanAnimated setRadiusInnerNext(double radiusInnerNext) {
			this.radiusInnerNext = radiusInnerNext;
			return this;
		}
		public double getRadiusOutNext() {
			return radiusOutNext;
		}
		public FanAnimated setRadiusOutNext(double radiusOutNext) {
			this.radiusOutNext = radiusOutNext;
			return this;
		}
		public Point<Integer, Integer> getPointPrev() {
			return pointPrev;
		}
		public FanAnimated setPointPrev(Point<Integer, Integer> prev) {
			this.pointPrev = prev;
			return this;
		}
		public double getRotatePrev() {
			return rotatePrev;
		}
		public FanAnimated setRotatePrev(double rotatePrev) {
			this.rotatePrev = rotatePrev;
			return this;
		}
		public double getStartPrev() {
			return startPrev;
		}
		public FanAnimated setStartPrev(double startPrev) {
			this.startPrev = startPrev;
			return this;
		}
		public double getEndPrev() {
			return endPrev;
		}
		public FanAnimated setEndPrev(double endPrev) {
			this.endPrev = endPrev;
			return this;
		}
		public double getRadiusInnerPrev() {
			return radiusInnerPrev;
		}
		public FanAnimated setRadiusInnerPrev(double radiusInnerPrev) {
			this.radiusInnerPrev = radiusInnerPrev;
			return this;
		}
		public double getRadiusOuterPrev() {
			return radiusOuterPrev;
		}
		public FanAnimated setRadiusOuterPrev(double radiusOuterPrev) {
			this.radiusOuterPrev = radiusOuterPrev;
			return this;
		}
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}
}
