package net.sayaya.ui.widget.shape.impl;

import java.util.LinkedList;
import java.util.stream.Collectors;

import net.sayaya.ui.widget.shape.HasColor;
import net.sayaya.ui.widget.shape.HasStroke;
import net.sayaya.ui.widget.shape.impl.Path.Command;

public class Polyline extends ShapeInstance<Polyline> implements HasStroke, HasColor {
	private double x, y, width, height;
	private String color;
	private double alpha=1.0;
	private String borderColor;
	private double borderWidth;
	private final LinkedList<Line> commands = new LinkedList<>();
	public Polyline() {
		super("polyline");
	}
	public double getX() {
		return x;
	}
	
	public Polyline setX(double x) {
		this.x = x;
		return this;
	}
	
	public double getY() {
		return y;
	}
	
	public Polyline setY(double y) {
		this.y = y;
		return this;
	}
	
	public double getWidth() {
		return width;
	}
	
	public Polyline setWidth(double width) {
		this.width = width;
		getElement().setAttribute("width", String.valueOf(width));
		return this;
	}
	
	public double getHeight() {
		return height;
	}
	
	public Polyline setHeight(double height) {
		this.height = height;
		getElement().setAttribute("height", String.valueOf(height));
		return this;
	}

	@Override
	public String getColor() {
		return color;
	}

	@Override
	public Polyline setColor(String color) {
		this.color = color;
		getElement().setAttribute("fill", color);
		return this;
	}

	@Override
	public double getAlpha() {
		return alpha;
	}

	@Override
	public Polyline setAlpha(double alpha) {
		getElement().setAttribute("fill-opacity", String.valueOf(alpha));
		return this;
	}

	public double getBorderWidth() {
		return borderWidth;
	}
	
	@Override
	public Polyline setBorderWidth(double borderWidth) {
		this.borderWidth = borderWidth;
		getElement().setAttribute("stroke-width", String.valueOf(borderWidth));
		return this;
	}

	@Override
	public String getBorderColor() {
		return borderColor;
	}

	public final Polyline build() {
		String d = commands.stream().map(Command::toString).collect(Collectors.joining(" "));
		getElement().setAttribute("points", x + "," + y + " " + d);
		return this;
	}
	
	@Override
	public Polyline setBorderColor(String borderColor) {
		this.borderColor = borderColor;
		getElement().setAttribute("stroke", borderColor);
		return this;
	}
	public Polyline moveTo(double x, double y) {
		setX(x).setY(y);
		return this;
	}
	public Polyline lineTo(double x, double y) {
		commands.add(new Line(x, y));
		return this;
	}
	
	private final static class Line implements Command {
		private final double x, y;
		public Line(double x, double y) {
			this.x = x;
			this.y = y;
		}
		public String toString() {
			return x + "," + y;
		}
	}
}
