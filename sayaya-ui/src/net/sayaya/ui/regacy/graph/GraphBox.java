package net.sayaya.ui.regacy.graph;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.google.gwt.canvas.dom.client.Context2d;

import net.sayaya.ui.handler.HasValue;
import net.sayaya.ui.regacy.data.Point;
import net.sayaya.ui.regacy.shape.HasStroke;
import net.sayaya.ui.regacy.shape.impl.ShapeInstance;

public class GraphBox<C, N extends Number> extends Graph<Map<C, net.sayaya.ui.regacy.graph.GraphBox.Box<N>>> implements HasValue<Map<C, net.sayaya.ui.regacy.graph.GraphBox.Box<N>>[]>, HasStroke {
	private final AxisDiscretized<C> axisX;
	private final AxisContinuous<N> axisY;
	private String borderColor;
	private double borderWidth = 1;
	private int betweenMargin = 20;
	private int withinMargin = 5;
	private HashMap<C, Map<Integer, BoxAnimated>> shapes = new HashMap<C, Map<Integer, BoxAnimated>>();
	
	public GraphBox(int width, int height, AxisDiscretized<C> axisX, AxisContinuous<N> axisY) {
		super(width, height);
		this.axisX = axisX;
		this.axisY = axisY;
	}
	
	public GraphBox<C, N> setValueAt(int idx, C category, Box<N> box) {
		getValue()[idx].put(category, box);
		double min = axisY.parse(box.getMin());
		double q1 = axisY.parse(box.getQ1());
		double med = axisY.parse(box.getMedian());
		double q3 = axisY.parse(box.getQ3());
		double max = axisY.parse(box.getMax());

		int minMap = (int)(axisY.getY() - min);
		int q1Map = (int)(axisY.getY() - q1);
		int medMap = (int)(axisY.getY() - med);
		int q3Map = (int)(axisY.getY() - q3);
		int maxMap = (int)(axisY.getY() - max);
		Box<Integer> nextBox = new Box<Integer>().setMin(minMap).setQ1(q1Map).setMedian(medMap).setQ3(q3Map).setMax(maxMap);
		if(!shapes.containsKey(category) || !shapes.get(category).containsKey(idx)) {
			int bw = (int)getBoxWidth();
			Agenda agenda = getAgenda()[idx];
			double delta = betweenMargin/2.0 + idx*(bw + withinMargin);
			int x = (int) (axisX.parse(category) + delta);
			Box<Integer> initBox = new Box<Integer>().setMin(medMap).setQ1(medMap).setMedian(medMap).setQ3(medMap).setMax(medMap);
			BoxAnimated shape = (BoxAnimated) createBox(bw, initBox, agenda)
				.setBoxNext(nextBox)
				.setPointNext(new Point<Integer, Integer> (x, axisY.getY()))
				.setColor(agenda.getColor())
				.setBorderWidth(borderWidth).setBorderColor(borderColor)
				.setX(x).setY(axisY.getY())
			;
			if(!shapes.containsKey(category)) shapes.put(category, new TreeMap<Integer, BoxAnimated>());
			shapes.get(category).put(idx, shape);
			add(shape);
		} else shapes.get(category).get(idx).setBoxNext(nextBox);
		return this;
	}
	
	public BoxAnimated getShapeAt(int idx, C category) {
		return shapes.get(category).get(idx);
	}
	
	@Override
	protected void setShapes() {
		shapes.clear();
		Map<C, Box<N>>[] values = getValue();
		int bw = (int)getBoxWidth();
		for(int i = 0; i < values.length; ++i) {
			Map<C, Box<N>> item = values[i];
			Agenda agenda = getAgenda()[i];
			double delta = betweenMargin/2.0 + i*(bw + withinMargin);
			for(Entry<C, Box<N>> value: item.entrySet()) {
				C category = value.getKey();
				Box<N> box = value.getValue();
				int x = (int) (axisX.parse(category) + delta);
				double min = axisY.parse(box.getMin());
				double q1 = axisY.parse(box.getQ1());
				double med = axisY.parse(box.getMedian());
				double q3 = axisY.parse(box.getQ3());
				double max = axisY.parse(box.getMax());
				
				int minMap = (int)(axisY.getY() - min);
				int q1Map = (int)(axisY.getY() - q1);
				int medMap = (int)(axisY.getY() - med);
				int q3Map = (int)(axisY.getY() - q3);
				int maxMap = (int)(axisY.getY() - max);
				Box<Integer> initBox = new Box<Integer>().setMin(medMap).setQ1(medMap).setMedian(medMap).setQ3(medMap).setMax(medMap);
				Box<Integer> nextBox = new Box<Integer>().setMin(minMap).setQ1(q1Map).setMedian(medMap).setQ3(q3Map).setMax(maxMap);
				BoxAnimated shape = (BoxAnimated) createBox(bw, initBox, agenda)
					.setBoxNext(nextBox)
					.setPointNext(new Point<Integer, Integer> (x, axisY.getY()))
					.setColor(agenda.getColor())
					.setBorderWidth(borderWidth).setBorderColor(borderColor)
					.setX(x).setY(axisY.getY())
				;
				add(shape);
				if(!shapes.containsKey(category)) shapes.put(category, new TreeMap<Integer, BoxAnimated>());
				shapes.get(category).put(i, shape);	
			}
		}
		add(axisX).add(axisY);
	}
	public AxisDiscretized<C> getAxisX() {
		return axisX;
	}
	public AxisContinuous<N> getAxisY() {
		return axisY;
	}
	protected BoxAnimated createBox(int width, Box<Integer> box, Agenda agenda) {
		return new BoxAnimated(width, box);
	}
	public int getBetweenMargin() {
		return betweenMargin;
	}
	public GraphBox<C, N> setBetweenMargin(int betweenMargin) {
		this.betweenMargin = betweenMargin;
		return this;
	}
	public int getWithinMargin() {
		return withinMargin;
	}
	public GraphBox<C, N> setWithinMargin(int withinMargin) {
		this.withinMargin = withinMargin;
		return this;
	}
	public double getBoxWidth() {
		int n = getValue().length;
		double w = axisX.getWidth(null);
		return (w - (n-1)*withinMargin - betweenMargin)/n;
	}
	public String getBorderColor() {
		return borderColor;
	}
	public GraphBox<C, N> setBorderColor(String borderColor) {
		this.borderColor = borderColor;
		return this;
	}
	public double getBorderWidth() {
		return borderWidth;
	}
	public GraphBox<C, N> setBorderWidth(double borderWidth) {
		this.borderWidth = borderWidth;
		return this;
	}

	public final static class Box<N extends Number> {
		private N min;
		private N q1;
		private N median;
		private N q3;
		private N max;
		public Box() {}
		public Box(N min, N q1, N median, N q3, N max) {
			setMin(min);
			setQ1(q1);
			setMedian(median);
			setQ3(q3);
			setMax(max);
		}
		public N getMin() {
			return min;
		}
		public Box<N> setMin(N min) {
			this.min = min;
			return this;
		}
		public N getQ1() {
			return q1;
		}
		public Box<N> setQ1(N q1) {
			this.q1 = q1;
			return this;
		}
		public N getMedian() {
			return median;
		}
		public Box<N> setMedian(N median) {
			this.median = median;
			return this;
		}
		public N getQ3() {
			return q3;
		}
		public Box<N> setQ3(N q3) {
			this.q3 = q3;
			return this;
		}
		public N getMax() {
			return max;
		}
		public Box<N> setMax(N max) {
			this.max = max;
			return this;
		}
	}
	public static class BoxAnimated extends ShapeInstance<BoxAnimated> implements HasStroke {
		private int width;
		private final Box<Integer> box = new Box<Integer>();
		private Box<Integer> boxPrev;
		private Box<Integer> boxNext;
		
		private Point<Integer, Integer> pointNext = new Point<Integer, Integer>(0, 0);
		private double rotateNext = 0;
		private Point<Integer, Integer> pointPrev = new Point<Integer, Integer>(0, 0);
		// private double rotatePrev = 0.0;
		
		private String color;
		private String borderColor;
		private double borderWidth = 1;
		private BoxAnimated(int width, Box<Integer> box) {
			this.width = width;
			this.box.setMin(box.getMin()).setQ1(box.getQ1()).setMedian(box.getMedian()).setQ3(box.getQ3()).setMax(box.getMax());
		}
		public BoxAnimated setBoxNext(Box<Integer> box) {
			this.boxNext = box;
			return this;
		}

		@Override
		public void draw(Context2d context, double progress) {
			if(progress <= 0) {
				pointPrev.setX(getX());
				pointPrev.setY(getY());
				// rotatePrev = getRotate();
				boxPrev = new Box<Integer>().setMin(box.getMin()).setQ1(box.getQ1()).setMedian(box.getMedian()).setQ3(box.getQ3()).setMax(box.getMax());
			}
			context.setLineWidth(borderWidth);
			context.scale(1.0, -1.0);
			
			// double x = getNext(pointPrev.getX(), pointNext.getX(), progress);
			// double y = getNext(pointPrev.getY(), pointNext.getY(), progress);
			// double rotate = getNext(rotatePrev, rotateNext, progress);
			Box<Double> box = getNext(boxPrev, boxNext, progress);
			
			context.moveTo(width/2, box.getMin());
			context.lineTo(width/2, box.getMax());
			context.moveTo(0, box.getMin());
			context.lineTo(width, box.getMin());
			context.moveTo(0, box.getMax());
			context.lineTo(width, box.getMax());
			if(color!=null) {
				context.setStrokeStyle(color);
				context.stroke();
			}
			context.closePath();
			context.beginPath();
			context.rect(0, box.getQ1(), width, box.getQ3()-box.getQ1());
			context.moveTo(0, box.getMedian());
			context.lineTo(width, box.getMedian());
			if(color!=null) {
				context.setFillStyle(color);
				context.fill();
			}
			if(borderColor!=null) {
				context.setStrokeStyle(borderColor);
				context.stroke();
			}

			
			this.box.setMin(box.getMin().intValue()).setQ1(box.getQ1().intValue()).setMedian(box.getMedian().intValue())
			.setQ3(box.getQ3().intValue()).setMax(box.getMax().intValue());
		}
		
		@Override
		public boolean checkIn(double x, double y) {
			int lx = 0;
			int rx = width;
			int ty = box.getQ1();
			int by = box.getQ3();
			
			if(lx > width-x || rx < width-x) return false;
			if(ty > -y || by < -y) return false;
			return true;
		}
		
		public static double getNext(double prev, double next, double progress) {
			return prev + progress*(next-prev);
		}
		public static Box<Double> getNext(Box<? extends Number> prev, Box<? extends Number> next, double progress) {
			double min = getNext(prev.getMin().doubleValue(), next.getMin().doubleValue(), progress);
			double q1 = getNext(prev.getQ1().doubleValue(), next.getQ1().doubleValue(), progress);
			double med = getNext(prev.getMedian().doubleValue(), next.getMedian().doubleValue(), progress);
			double q3 = getNext(prev.getQ3().doubleValue(), next.getQ3().doubleValue(), progress);
			double max = getNext(prev.getMax().doubleValue(), next.getMax().doubleValue(), progress);
			return new Box<Double>().setMin(min).setQ1(q1).setMedian(med).setQ3(q3).setMax(max);
		}
		
		public Point<Integer, Integer> getPointNext() {
			return pointNext;
		}
		public BoxAnimated setPointNext(Point<Integer, Integer> pointNext) {
			this.pointNext = pointNext;
			return this;
		}
		public double getRotateNext() {
			return rotateNext;
		}
		public BoxAnimated setRotateNext(double rotateNext) {
			this.rotateNext = rotateNext;
			return this;
		}
		public String getColor() {
			return color;
		}
		public BoxAnimated setColor(String color) {
			this.color = color;
			return this;
		}
		@Override
		public String getBorderColor() {
			return borderColor;
		}
		@Override
		public BoxAnimated setBorderColor(String borderColor) {
			this.borderColor = borderColor;
			return this;
		}
		@Override
		public BoxAnimated setBorderWidth(double width) {
			this.borderWidth = width;
			return this;
		}
		public double getBorderWidth() {
			return borderWidth;
		}
	}
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}
}