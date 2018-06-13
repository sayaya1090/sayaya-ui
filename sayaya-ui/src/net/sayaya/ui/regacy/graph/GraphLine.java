package net.sayaya.ui.regacy.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.user.client.Window;

import net.sayaya.ui.handler.HasValue;
import net.sayaya.ui.regacy.data.Point;
import net.sayaya.ui.regacy.shape.HasStroke;
import net.sayaya.ui.regacy.shape.impl.Fan;
import net.sayaya.ui.regacy.shape.impl.Line;
import net.sayaya.ui.regacy.shape.impl.Polygon;

public class GraphLine<C, N extends Number> extends Graph<Map<C, N>> implements HasValue<Map<C, N>[]>, HasStroke {
	private final Axis<C> axisX;
	private final AxisContinuous<N> axisY;
	private String borderColor;
	private double borderWidth = 1;
	private List<Polygon> shapes = new ArrayList<Polygon>();
	
	public GraphLine(int width, int height, Axis<C> axisX, AxisContinuous<N> axisY) {
		super(width, height);
		this.axisX = axisX;
		this.axisY = axisY;
	}
	
	@Override
	protected void setShapes() {
		shapes.clear();
		Map<C, N>[] values = getValue();

		List<List<Point<Double, Double>>> points = new ArrayList<List<Point<Double, Double>>>();
		for(int i = 0; i < values.length; ++i) points.add(new LinkedList<Point<Double, Double>>());
		for(int i = 0; i < values.length; ++i) {
			Map<C, N> item = values[i];
			for(Entry<C, N> entry: item.entrySet()) {
				C category = entry.getKey();
				N value = entry.getValue();
				
				double x = axisX.parse(category);
				double y = axisY.parse(value);
				points.get(i).add(new Point<Double, Double>(x, y));
			}
			Collections.sort(points.get(i), (p1, p2)->Double.compare(p1.getX(), p2.getX()));
		}
		for(int i = 0; i < points.size(); ++i) {
			Agenda agenda = getAgenda()[i];
			LinkedList<Point<Double, Double>> p = (LinkedList<Point<Double, Double>>) points.get(i);
			for(int j = 0; j < p.size()-1; ++j) {
				Point<Double, Double> start = p.get(j);
				Point<Double, Double> end = p.get(j+1);
				Line line = new Line(start, end).setX(0).setY(0).setBorderColor(agenda.getColor()).setBorderWidth(1.3);
				Fan circle = new Fan(2.5).setX(start.getX().intValue()).setY(start.getY().intValue()).setColor(agenda.getColor());
				add(line);
				add(circle);
			}
			if(!p.isEmpty()) {
				Point<Double, Double> end = p.get(p.size()-1);
				Fan circle = new Fan(2.5).setX(end.getX().intValue()).setY(end.getY().intValue()).setColor(agenda.getColor());
				add(circle);
			}
		}
		add(axisX).add(axisY);
	}
	
	@Override
	public String getBorderColor() {
		return borderColor;
	}
	@Override
	public GraphLine<C, N> setBorderColor(String borderColor) {
		this.borderColor = borderColor;
		return this;
	}
	public double getBorderWidth() {
		return borderWidth;
	}
	@Override
	public GraphLine<C, N> setBorderWidth(double borderWidth) {
		this.borderWidth = borderWidth;
		return this;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}
}
