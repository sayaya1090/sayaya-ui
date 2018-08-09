package net.sayaya.ui.regacy.graph;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.TextAlign;
import com.google.gwt.canvas.dom.client.Context2d.TextBaseline;

import net.sayaya.ui.handler.HasValue;
import net.sayaya.ui.regacy.data.Point;
import net.sayaya.ui.regacy.shape.Animation;
import net.sayaya.ui.regacy.shape.Canvas;
import net.sayaya.ui.regacy.shape.HasStroke;
import net.sayaya.ui.regacy.shape.impl.Rectangle;
import net.sayaya.ui.regacy.shape.impl.Text;
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
		label = new Text("").setX(width/2).setY(height/2)
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
		if(label!=null) label.setX(width / 2);
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
		int w =(int)(d * width / length);
		bar.setWidthNext(w).setHeightNext(height)
			.setBorderWidth(borderWidth)
			.setX(0).setY(0)
		;
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
