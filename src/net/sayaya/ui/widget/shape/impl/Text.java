package net.sayaya.ui.widget.shape.impl;

import net.sayaya.ui.handler.HasValue;
import net.sayaya.ui.widget.shape.HasColor;
import net.sayaya.ui.widget.shape.impl.ShapeInstance;

public class Text extends ShapeInstance<Text> implements HasValue<String>, HasColor {
	private double x, y;
	private String color;
	private double alpha;
	private String value;
	private AlignmentBaseline baseline;
	private TextAnchor anchor;
	public Text() {
		super("text");
	}

	public double getX() {
		return x;
	}
	
	public Text setX(double x) {
		this.x = x;
		getElement().setAttribute("x", String.valueOf(x));
		return this;
	}
	
	public double getY() {
		return y;
	}
	
	public Text setY(double y) {
		this.y = y;
		getElement().setAttribute("y", String.valueOf(y));
		return this;
	}
	
	@Override
	public String getColor() {
		return color;
	}

	@Override
	public Text setColor(String color) {
		this.color = color;
		getElement().setAttribute("fill", color);
		return this;
	}

	@Override
	public double getAlpha() {
		return alpha;
	}

	@Override
	public Text setAlpha(double alpha) {
		getElement().setAttribute("fill-opacity", String.valueOf(alpha));
		return this;
	}

	@Override
	public Text setValue(String value) {
		this.value = value;
		getElement().setInnerHTML(value);
		return this;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public boolean isEmpty() {
		return value == null || value.trim().isEmpty();
	}

	public AlignmentBaseline getBaseline() {
		return baseline;
	}

	public Text setBaseline(AlignmentBaseline baseline) {
		this.baseline = baseline;
		if(baseline!=null) getElement().setAttribute("alignment-baseline", baseline.name());
		else getElement().setAttribute("alignment-baseline", "auto");
		return this;
	}

	public TextAnchor getAnchor() {
		return anchor;
	}

	public Text setAnchor(TextAnchor anchor) {
		this.anchor = anchor;
		if(anchor!=null) getElement().setAttribute("text-anchor", anchor.name());
		else getElement().setAttribute("text-anchor", "start");
		return this;
	}
	
	public enum AlignmentBaseline {
		auto, baseline, beforeEdge, textBeforeEdge, middle, central, afterEdge, textAfterEdge, ideographic, alphabetic, hanging, mathematical, inherit
	}
	public enum TextAnchor {
		start, middle, end, inherit
	}
}
