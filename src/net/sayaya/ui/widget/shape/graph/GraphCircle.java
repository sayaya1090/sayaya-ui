package net.sayaya.ui.widget.shape.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sayaya.ui.handler.HasValue;
import net.sayaya.ui.widget.SVG;
import net.sayaya.ui.widget.shape.HasStroke;
import net.sayaya.ui.widget.shape.Shape.Rotate;
import net.sayaya.ui.widget.shape.graph.Agenda;
import net.sayaya.ui.widget.shape.impl.Circle;
import net.sayaya.ui.widget.shape.impl.Fan;

public class GraphCircle extends SVG implements HasValue<Double[]>, HasStroke {
	private double min, max;
	private int width=0, height=0;
	private double radiusOuter=20;
	private double radiusInner=0;
	private String backgroundColor;
	private String borderColor;
	private double borderWidth = 1;
	private final Circle circleOut = new Circle().setX(width/2.0).setY(height/2.0).setRadius(radiusOuter)
	.setColor(backgroundColor).setBorderColor(borderColor).setBorderWidth(borderWidth);
	private final Circle circleIn = new Circle().setX(width/2.0).setY(height/2.0).setRadius(radiusInner)
	.setColor("#FFFFFF").setBorderColor(borderColor).setBorderWidth(borderWidth);
	private List<Fan> shapes = new ArrayList<Fan>();
	private Agenda[] agendas;
	public GraphCircle() {
		add(circleOut);
		add(circleIn);
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
	public final double getRadiusOuter() {
		return radiusOuter;
	}

	public final GraphCircle setRadiusOuter(double radiusOuter) {
		this.radiusOuter = radiusOuter;
		circleOut.setRadius(radiusOuter);
		return this;
	}

	public final double getRadiusInner() {
		return radiusInner;
	}

	public final GraphCircle setRadiusInner(double radiusInner) {
		this.radiusInner = radiusInner;
		circleIn.setRadius(radiusInner);
		return this;
	}
	
	public GraphCircle setWidth(int width) {
		this.width = width;
		double center = width/2.0;
		circleOut.setX(center);
		circleIn.setX(center);
		for(Fan fan: shapes) fan.setX(center);
		setWidth(width + "px");
		return this;
	}
	
	public GraphCircle setHeight(int height) {
		this.height = height;
		double center = height/2.0;
		circleOut.setY(center);
		circleIn.setY(center);
		for(Fan fan: shapes) fan.setY(center);
		setHeight(height + "px");
		return this;
	}
	
	@Override
	public String getBorderColor() {
		return borderColor;
	}
	@Override
	public GraphCircle setBorderColor(String borderColor) {
		this.borderColor = borderColor;
		circleOut.setBorderColor(borderColor);
		circleIn.setBorderColor(borderColor);
		for(Fan fan: shapes) fan.setBorderColor(borderColor);
		return this;
	}
	@Override
	public GraphCircle setBorderWidth(double width) {
		this.borderWidth = width;
		circleOut.setBorderWidth(width);
		circleIn.setBorderWidth(width);
		for(Fan fan: shapes) fan.setBorderWidth(width);
		return this;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}
	
	public GraphCircle setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
		circleOut.setColor(backgroundColor);
		return this;
	}
	
	public GraphCircle setValue(Double value) {
		if(shapes.isEmpty()) this.setValue(new Double[] {value});
		else setValueAt(0, value);
		return this;
	}
	
	@Override
	public GraphCircle setValue(Double[] values) {
		double length = max - min;
		double centerX = width/2.0;
		double centerY = height/2.0;
		Arrays.stream(values)
		.map(v->{
			double ratio = Math.PI*2 * v / length;
			Fan fan = new Fan(0, ratio, radiusInner, radiusOuter);
			fan.setX(centerX).setY(centerY).setBorderColor(borderColor).setBorderWidth(borderWidth);
			return fan;
		}).peek(f->shapes.add(f))
		.forEach(this::add);
		double start = -Math.PI/2;
		int idx = 0;
		for(Fan fan: shapes) {
			if(agendas!=null && agendas.length > idx && agendas[idx]!=null) fan.setColor(agendas[idx++].getColor());
			fan.transform(new Rotate(180 / Math.PI * start));
			start += fan.getEnd();
		}
		return this;
	}
	
	public GraphCircle setValueAt(int idx, Double value) {
		Fan fan = shapes.get(idx);
		double length = max - min;
		double ratio = Math.PI*2 * value / length;
		fan.setEnd(ratio);
		return this;
	}
	
	public Fan getFanAt(int idx) {
		return shapes.get(idx);
	}

	public GraphCircle setAgenda(Agenda[] agenda) {
		this.agendas = agenda;
		int idx = 0;
		for(Fan fan: shapes) {
			if(agendas!=null && agendas.length > idx && agendas[idx]!=null) fan.setColor(agendas[idx++].getColor());
		}
		return this;
	}
	@Override
	public Double[] getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty() {
		return shapes.isEmpty();
	}
}
