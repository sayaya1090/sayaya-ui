package net.sayaya.ui.widget.shape.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import net.sayaya.ui.widget.shape.HasColor;
import net.sayaya.ui.widget.shape.HasStroke;

public class Path extends ShapeInstance<Path> implements HasStroke, HasColor {
	private double x, y, width, height;
	private String color;
	private double alpha=1.0;
	private String borderColor;
	private double borderWidth;
	private final LinkedList<Command> commands = new LinkedList<>();
	public Path() {
		super("path");
	}
	
	public double getX() {
		return x;
	}
	
	public Path setX(double x) {
		transform(new Translate(x, 0));
		return this;
	}
	
	public double getY() {
		return y;
	}
	
	public Path setY(double y) {
		transform(new Translate(0, y));
		return this;
	}
	
	public double getWidth() {
		return width;
	}
	
	public Path setWidth(double width) {
		this.width = width;
		getElement().setAttribute("width", String.valueOf(width));
		return this;
	}
	
	public double getHeight() {
		return height;
	}
	
	public Path setHeight(double height) {
		this.height = height;
		getElement().setAttribute("height", String.valueOf(height));
		return this;
	}

	@Override
	public String getColor() {
		return color;
	}

	@Override
	public Path setColor(String color) {
		this.color = color;
		getElement().setAttribute("fill", color);
		return this;
	}

	@Override
	public double getAlpha() {
		return alpha;
	}

	@Override
	public Path setAlpha(double alpha) {
		getElement().setAttribute("fill-opacity", String.valueOf(alpha));
		return this;
	}

	public double getBorderWidth() {
		return borderWidth;
	}
	
	@Override
	public Path setBorderWidth(double borderWidth) {
		this.borderWidth = borderWidth;
		getElement().setAttribute("stroke-width", String.valueOf(borderWidth));
		return this;
	}

	@Override
	public String getBorderColor() {
		return borderColor;
	}

	@Override
	public Path setBorderColor(String borderColor) {
		this.borderColor = borderColor;
		getElement().setAttribute("stroke", borderColor);
		return this;
	}
	
	public Path moveTo(double x, double y) {
		commands.add(new Move(x, y));
		return this;
	}
	public Path lineTo(double x, double y) {
		commands.add(new Line(x, y));
		return this;
	}
	public Path horizontalTo(double x) {
		commands.add(new Horizontal(x));
		return this;
	}
	public Path verticalTo(double y) {
		commands.add(new Vertical(y));
		return this;
	}
	public Path curve(double x1, double y1, double x2, double y2, double x, double y) {
		commands.add(new Bezier3(x1, y1, x2, y2, x, y));
		return new PathProxy(this);
	}
	public Path curve(double x1, double y1, double x, double y) {
		commands.add(new Bezier2(x1, y1, x, y));
		return this;
	}
	public Path arc(double rx, double ry, double xar, int laf, int sf, double x, double y) {
		commands.add(new Arc(rx, ry, xar, laf, sf, x, y));
		return this;
	}
	public Path close() {
		commands.add(new Close());
		return this;
	}
	public Path clear() {
		commands.clear();
		return this;
	}
	protected List<Command> getCommands() {
		return commands;
	}
	public final Path build() {
		String d = commands.stream().map(Command::toString).collect(Collectors.joining(" "));
		getElement().setAttribute("d", d);
		return this;
	}
	private final class PathProxy extends Path {
		private double x, y, width, height;
		private String color;
		private double alpha=1.0;
		private String borderColor;
		private double borderWidth;
		private final LinkedList<Command> commands = new LinkedList<>();
		public PathProxy(Path path) {
			this.x = path.x;
			this.y = path.y;
			
		}
		@Override
		public Path curve(double x1, double y1, double x, double y) {
			commands.add(new Bezier3Link(x1, y1, x, y));
			return this;
		}
	}
	protected static interface Command {
		String toString();
	}
	protected final static class Move implements Command {
		private final double x, y;
		public Move(double x, double y) {
			this.x = x;
			this.y = y;
		}
		public String toString() {
			return "M " + x + " " + y;
		}
	}
	protected final static class Line implements Command {
		private final double x, y;
		public Line(double x, double y) {
			this.x = x;
			this.y = y;
		}
		public String toString() {
			return "L " + x + " " + y;
		}
	}
	protected final static class Horizontal implements Command {
		private final double x;
		public Horizontal(double x) {
			this.x = x;
		}
		public String toString() {
			return "H " + x;
		}
	}
	protected final static class Vertical implements Command {
		private final double y;
		public Vertical(double y) {
			this.y = y;
		}
		public String toString() {
			return "V " + y;
		}
	}
	protected final static class Bezier3 implements Command {
		private final double x, y, x1, y1, x2, y2;
		public Bezier3(double x1, double y1, double x2, double y2, double x, double y) {
			this.x = x;
			this.y = y;
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		}
		public String toString() {
			return "C " + x1 + " " + y1 + ", " + x2 + " " + y2 + ", " + x + " " + y;
		}
	}
	protected final static class Bezier3Link implements Command {
		private final double x, y, x1, y1;
		public Bezier3Link(double x1, double y1, double x, double y) {
			this.x = x;
			this.y = y;
			this.x1 = x1;
			this.y1 = y1;
		}
		public String toString() {
			return "S " + x1 + " " + y1 + ", " + x + " " + y;
		}
	}
	protected final static class Bezier2 implements Command {
		private final double x, y, x1, y1;
		public Bezier2(double x1, double y1, double x, double y) {
			this.x = x;
			this.y = y;
			this.x1 = x1;
			this.y1 = y1;
		}
		public String toString() {
			return "Q " + x1 + " " + y1 + ", " + x + " " + y;
		}
	}
	protected final static class Bezier2Link implements Command {
		private final double x, y;
		public Bezier2Link(double x, double y) {
			this.x = x;
			this.y = y;
		}
		public String toString() {
			return "T " + x + " " + y;
		}
	}
	protected final static class Arc implements Command {
		private final double rx, ry, xar, laf, sf, x, y;
		public Arc(double rx, double ry, double xar, int laf, int sf, double x, double y) {
			this.rx = rx;
			this.ry = ry;
			this.xar = xar;
			this.laf = laf;
			this.sf = sf;
			this.x = x;
			this.y = y;
		}
		public String toString() {
			return "A " + rx + " " + ry + "," + xar + "," + laf + "," + sf + "," + x + " " + y;
		}
	}
	protected final class Close implements Command {
		public String toString() {
			return "Z";
		}
	}
}
