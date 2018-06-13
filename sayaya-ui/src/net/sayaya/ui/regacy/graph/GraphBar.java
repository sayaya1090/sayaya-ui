package net.sayaya.ui.regacy.graph;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.canvas.dom.client.Context2d;

import net.sayaya.ui.handler.HasValue;
import net.sayaya.ui.regacy.data.Point;
import net.sayaya.ui.regacy.shape.HasStroke;
import net.sayaya.ui.regacy.shape.impl.Rectangle;

import java.util.TreeMap;

public class GraphBar <C, N extends Number> extends Graph<Map<C, N>> implements HasValue<Map<C, N>[]>, HasStroke {
	private final AxisDiscretized<C> axisX;
	private final AxisContinuous<N> axisY;
	private String borderColor;
	private double borderWidth = 1;
	private int betweenMargin = 20;
	private int withinMargin = 5;
	private HashMap<C, Map<Integer, RectangleAnimated>> shapes = new HashMap<C, Map<Integer, RectangleAnimated>>();
	
	public GraphBar(int width, int height, AxisDiscretized<C> axisX, AxisContinuous<N> axisY) {
		super(width, height);
		this.axisX = axisX;
		this.axisY = axisY;
	}
	
	public GraphBar<C, N> setValueAt(int idx, C category, N value) {
		getValue()[idx].put(category, value);
		double y = axisY.parse(value);
		int height = (int)(axisY.getY() - y);
		if(!shapes.containsKey(category) || !shapes.get(category).containsKey(idx)) {
			int bw = (int)getBarWidth();
			Agenda agenda = getAgenda()[idx];
			double delta = betweenMargin/2.0 + idx*(bw + withinMargin);
			int x = (int) (axisX.parse(category) + delta);
			RectangleAnimated shape = (RectangleAnimated) createBar(bw, height, agenda)
				.setWidthNext(bw).setHeightNext(height)
				.setPointNext(new Point<Integer, Integer> (x+bw, (int)y + height)).setRotateNext(Math.PI)
				.setX(x+bw).setY((int)y + height)
				.setRotate(Math.PI)
				.setColor(agenda.getColor())
				.setBorderWidth(borderWidth).setBorderColor(borderColor)
			;
			if(!shapes.containsKey(category)) shapes.put(category, new TreeMap<Integer, RectangleAnimated>());
			shapes.get(category).put(idx, shape);
			add(shape);
		} else shapes.get(category).get(idx).setHeightNext(height);
		return this;
	}
	
	public RectangleAnimated getShapeAt(int idx, C category) {
		return shapes.get(category).get(idx);
	}
	
	@Override
	protected void setShapes() {
		shapes.clear();
		Map<C, N>[] values = getValue();
		int bw = (int)getBarWidth();

		for(int i = 0; i < values.length; ++i) {
			Map<C, N> item = values[i];
			Agenda agenda = getAgenda()[i];
			double delta = betweenMargin/2.0 + i*(bw + withinMargin);
			for(Entry<C, N> entry: item.entrySet()) {
				C category = entry.getKey();
				N value = entry.getValue();
				int x = (int) (axisX.parse(category) + delta);
				double y = axisY.parse(value);
				int height = (int)(axisY.getY() - y);
				RectangleAnimated shape = (RectangleAnimated) createBar(bw, height, agenda)
					.setWidthNext(bw).setHeightNext(height)
					.setPointNext(new Point<Integer, Integer> (x+bw, (int)y + height)).setRotateNext(Math.PI)
					.setX(x+bw).setY((int)y + height)
					.setRotate(Math.PI)
					.setColor(agenda.getColor())
					.setBorderWidth(borderWidth).setBorderColor(borderColor)
				;
				add(shape);
				if(!shapes.containsKey(category)) shapes.put(category, new TreeMap<Integer, RectangleAnimated>());
				shapes.get(category).put(i, shape);
			}
		}
		add(axisX).add(axisY);
	}
	protected RectangleAnimated createBar(int width, int height, Agenda agenda) {
		return new RectangleAnimated(width, 0);
	}
	public AxisDiscretized<C> getAxisX() {
		return axisX;
	}
	public AxisContinuous<N> getAxisY() {
		return axisY;
	}
	public int getBetweenMargin() {
		return betweenMargin;
	}
	public GraphBar<C, N> setBetweenMargin(int betweenMargin) {
		this.betweenMargin = betweenMargin;
		return this;
	}
	public int getWithinMargin() {
		return withinMargin;
	}
	public GraphBar<C, N> setWithinMargin(int withinMargin) {
		this.withinMargin = withinMargin;
		return this;
	}
	public double getBarWidth() {
		int n = getValue().length;
		double w = axisX.getWidth(null);
		return (w - (n-1)*withinMargin - betweenMargin)/n;
	}
	public String getBorderColor() {
		return borderColor;
	}
	public GraphBar<C, N> setBorderColor(String borderColor) {
		this.borderColor = borderColor;
		return this;
	}
	public double getBorderWidth() {
		return borderWidth;
	}
	public GraphBar<C, N> setBorderWidth(double borderWidth) {
		this.borderWidth = borderWidth;
		return this;
	}
	
	@SuppressWarnings("unused")
	private final static class RectangleAnimated extends Rectangle {
		private Point<Integer, Integer> pointNext = new Point<Integer, Integer>(0, 0);
		private double rotateNext = 0;
		private int widthNext = 0;
		private int heightNext = 0;
		private Point<Integer, Integer> pointPrev = new Point<Integer, Integer>(0, 0);
		private double rotatePrev = 0;
		private int widthPrev = 0;
		private int heightPrev = 0;
		public RectangleAnimated(int width, int height) {
			super(width, height);
		}
		public static double getNext(double prev, double next, double progress) {
			return prev + progress*(next-prev);
		}
		public static int getNext(int prev, int next, double progress) {
			return prev + (int)(progress*(next-prev));
		}
		@Override
		public void draw(Context2d context, double progress) {
			if(progress <= 0) {
				pointPrev.setX(getX());
				pointPrev.setY(getY());
				rotatePrev = getRotate();
				widthPrev = getWidth();
				heightPrev = getHeight();
			}
			
			int x = getNext(pointPrev.getX(), pointNext.getX(), progress);
			int y = getNext(pointPrev.getY(), pointNext.getY(), progress);
			double rotate = getNext(rotatePrev, rotateNext, progress);
			int width = getNext(widthPrev, widthNext, progress);
			int height = getNext(heightPrev, heightNext, progress);

			String color = getColor();
			String borderColor = getBorderColor();
			
			context.setLineWidth(getBorderWidth());
			context.rect(0, 0, width, height);
			if(color!=null) {
				context.setFillStyle(color);
				context.fill();
			}
			if(borderColor!=null) {
				context.setStrokeStyle(borderColor);
				context.stroke();
			}
			setX(x); setY(y); setRotate(rotate);
			setWidth(width);
			setHeight(height);
		}

		public Point<Integer, Integer> getPointNext() {
			return pointNext;
		}

		public RectangleAnimated setPointNext(Point<Integer, Integer> pointNext) {
			this.pointNext = pointNext;
			return this;
		}

		public double getRotateNext() {
			return rotateNext;
		}

		public RectangleAnimated setRotateNext(double rotateNext) {
			this.rotateNext = rotateNext;
			return this;
		}

		public double getWidthNext() {
			return widthNext;
		}

		public RectangleAnimated setWidthNext(int widthNext) {
			this.widthNext = widthNext;
			return this;
		}

		public double getHeightNext() {
			return heightNext;
		}

		public RectangleAnimated setHeightNext(int heightNext) {
			this.heightNext = heightNext;
			return this;
		}

		public Point<Integer, Integer> getPointPrev() {
			return pointPrev;
		}

		public RectangleAnimated setPointPrev(Point<Integer, Integer> pointPrev) {
			this.pointPrev = pointPrev;
			return this;
		}

		public double getRotatePrev() {
			return rotatePrev;
		}

		public RectangleAnimated setRotatePrev(double rotatePrev) {
			this.rotatePrev = rotatePrev;
			return this;
		}

		public double getWidthPrev() {
			return widthPrev;
		}

		public RectangleAnimated setWidthPrev(int widthPrev) {
			this.widthPrev = widthPrev;
			return this;
		}

		public double getHeightPrev() {
			return heightPrev;
		}

		public RectangleAnimated setHeightPrev(int heightPrev) {
			this.heightPrev = heightPrev;
			return this;
		}
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}
}