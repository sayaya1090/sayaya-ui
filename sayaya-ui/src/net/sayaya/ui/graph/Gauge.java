package net.sayaya.ui.graph;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.TextAlign;
import com.google.gwt.canvas.dom.client.Context2d.TextBaseline;

import net.sayaya.ui.data.Point;
import net.sayaya.ui.handler.HasValue;
import net.sayaya.ui.regacy.shape.impl.Rectangle;
import net.sayaya.ui.regacy.shape.impl.Text;
import net.sayaya.ui.shape.Animation;
import net.sayaya.ui.shape.Canvas;
import net.sayaya.ui.shape.HasStroke;
import net.sayaya.ui.style.color.Palette;

public class Gauge<N extends Number> extends Canvas implements HasValue<N>, HasStroke {
	private N min, max, value;
	private String backgroundColor;
	private String borderColor;
	private double borderWidth = 1;
	private final Animation animation = new Animation(this);
	private final Text label;
	private final RectangleAnimated bar;
	
	public Gauge(int width, int height) {
		super(width, height);
		bar = new RectangleAnimated(0, height);
		label = new Text("").setX(width/2.0).setY(height/2.0)
		.setSize(10)
		.setAlign(TextAlign.CENTER).setBaseline(TextBaseline.MIDDLE)
		.setFont("Noto Sans KR").setColor(Palette.getInstance().getColorText1());
		add(bar);
		add(label);
	}
	
	public void update(int duration) {
		if(!animation.isRunning()) animation.run(duration);
	}
	
	public void paint() {
		if(animation.isRunning()) animation.cancel();
		super.paint();
	}
	
	@Override
	public N getValue() {
		return value;
	}
	
	@Override
	public Gauge<N> setWidth(int width) {
		super.setWidth(width);
		if(label!=null) label.setX(width/2.0);
		return this;
	}
	
	@Override
	public Gauge<N> setValue(N value) {
		this.value = value;
		if(value == null) return this;
		double length = max.doubleValue() - min.doubleValue();
		int width = getWidth();
		int height = getHeight();
		double d = value.doubleValue();
		double w = d * width / length;
		bar.setWidthNext(w).setHeightNext(height)
		.setBorderWidth(borderWidth)
		.setX(0).setY(0);
		return this;
	}
	
	@Override
	public void paint(double progress) {
		super.paint(progress);
	}

	public final Gauge<N> setMin(N min) {
		this.min = min;
		return this;
	}
	public N getMin() {
		return min;
	}
	public final Gauge<N> setMax(N max) {
		this.max = max;
		return this;
	}
	public N getMax() {
		return max;
	}

	@Override
	public String getBorderColor() {
		return borderColor;
	}
	@Override
	public Gauge<N> setBorderColor(String borderColor) {
		this.borderColor = borderColor;
		getElement().getStyle().setBorderColor(borderColor);
		return this;
	}
	@Override
	public Gauge<N> setBorderWidth(double width) {
		this.borderWidth = width;
		return this;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public Gauge<N> setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
		return this;
	}
	
	public Rectangle getBar() {
		return bar;
	}
	
	public Text getLabel() {
		return label;
	}
	
	@SuppressWarnings("unused")
	private final static class RectangleAnimated extends Rectangle {
		private Point<Double, Double> pointNext = new Point<Double, Double>(0.0, 0.0);
		private double rotateNext = 0;
		private double widthNext = 0;
		private double heightNext = 0;
		private Point<Double, Double> pointPrev = new Point<Double, Double>(0.0, 0.0);
		private double rotatePrev = 0;
		private double widthPrev = 0;
		private double heightPrev = 0;
		public RectangleAnimated(int width, int height) {
			super(width, height);
		}
		public static double getNext(double prev, double next, double progress) {
			return prev + progress*(next-prev);
		}
		public static double getNext(int prev, int next, double progress) {
			return prev + progress*(next-prev);
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
			
			double x = getNext(pointPrev.getX(), pointNext.getX(), progress);
			double y = getNext(pointPrev.getY(), pointNext.getY(), progress);
			double rotate = getNext(rotatePrev, rotateNext, progress);
			double width = getNext(widthPrev, widthNext, progress);
			double height = getNext(heightPrev, heightNext, progress);

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

		public Point<Double, Double> getPointNext() {
			return pointNext;
		}

		public RectangleAnimated setPointNext(Point<Double, Double> pointNext) {
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

		public RectangleAnimated setWidthNext(double widthNext) {
			this.widthNext = widthNext;
			return this;
		}

		public double getHeightNext() {
			return heightNext;
		}

		public RectangleAnimated setHeightNext(double heightNext) {
			this.heightNext = heightNext;
			return this;
		}

		public Point<Double, Double> getPointPrev() {
			return pointPrev;
		}

		public RectangleAnimated setPointPrev(Point<Double, Double> pointPrev) {
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
		return false;
	}
}
