package net.sayaya.ui.widget.shape.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import net.sayaya.ui.handler.HasValue;
import net.sayaya.ui.widget.SVG;
import net.sayaya.ui.widget.shape.HasStroke;
import net.sayaya.ui.widget.shape.Shape.Rotate;
import net.sayaya.ui.widget.shape.graph.Agenda;
import net.sayaya.ui.widget.shape.impl.Circle;
import net.sayaya.ui.widget.shape.impl.Fan;
import net.sayaya.ui.widget.shape.impl.Line;
import net.sayaya.ui.widget.shape.impl.Text;
import net.sayaya.ui.widget.shape.impl.Text.AlignmentBaseline;
import net.sayaya.ui.widget.shape.impl.Text.TextAnchor;

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
	private List<Fan> fans = new ArrayList<Fan>();
	private List<Text> labels = new ArrayList<Text>();
	private List<Line> lines = new ArrayList<Line>();
	private Agenda[] agendas;
	public GraphCircle() {
		getElement().setInnerHTML("<defs>\r\n" + 
				"    <marker  id=\"Triangle\"  viewBox=\"0 0 10 10\" \r\n" + 
				"        refX=\"0\" refY=\"5\"  markerUnits=\"strokeWidth\"\r\n" + 
				"        markerWidth=\"8\" markerHeight=\"6\" orient=\"auto\">\r\n" + 
				"        <path d=\"M 0 0 L 10 5 L 0 10 z\" />\r\n" + 
				"    </marker>\r\n" + 
				"  </defs>");
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
		for(Fan fan: fans) fan.setX(center);
		setWidth(width + "px");
		return this;
	}
	
	public GraphCircle setHeight(int height) {
		this.height = height;
		double center = height/2.0;
		circleOut.setY(center);
		circleIn.setY(center);
		for(Fan fan: fans) fan.setY(center);
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
		for(Fan fan: fans) fan.setBorderColor(borderColor);
		return this;
	}
	@Override
	public GraphCircle setBorderWidth(double width) {
		this.borderWidth = width;
		circleOut.setBorderWidth(width);
		circleIn.setBorderWidth(width);
		for(Fan fan: fans) fan.setBorderWidth(width);
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
		if(fans.isEmpty()) this.setValue(new Double[] {value});
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
		}).peek(f->fans.add(f))
		.forEach(this::add);
		double start = -Math.PI/2;
		int idx = 0;
		for(Fan fan: fans) {
			if(agendas!=null && agendas.length > idx && agendas[idx]!=null) fan.setColor(agendas[idx++].getColor());
			fan.transform(new Rotate(180 / Math.PI * start));
			start += fan.getEnd();
		}
		return this;
	}
	
	public GraphCircle setValueAt(int idx, Double value) {
		Fan fan = fans.get(idx);
		double length = max - min;
		double ratio = Math.PI*2 * value / length;
		fan.setEnd(ratio);
		return this;
	}
	
	public Fan getFanAt(int idx) {
		return fans.get(idx);
	}

	public Text getLabelAt(int idx) {
		return labels.get(idx);
	}
	
	public GraphCircle setAgenda(Agenda[] agenda) {
		this.agendas = agenda;
		int idx = 0;
		double start = -Math.PI/2;
		LinkedList<Text> rights = new LinkedList<>();
		LinkedList<Text> lefts = new LinkedList<>();
		for(Fan fan: fans) {
			if(agendas!=null && agendas.length > idx && agendas[idx]!=null) {
				Text label = new Text().setColor(agendas[idx].getColor()).setValue(agendas[idx].getName()).setBaseline(AlignmentBaseline.middle);
				add(label);
				fan.setColor(agendas[idx++].getColor());
				if(start + fan.getEnd()/2.0 <= Math.PI) {
					label.setAnchor(TextAnchor.start).setX(width/2.0 + radiusOuter + 20);
					rights.add(label);
				} else {
					label.setAnchor(TextAnchor.end).setX(width/2.0 - radiusOuter - 20);
					lefts.add(label);
				}
				start += fan.getEnd();
				labels.add(label);
			}
		}
		double p = width/2.0;
		double q = height/2.0;
		double r = (radiusOuter + radiusInner) / 2.0;
		for(Text text: rights) {
			text.setY((1+2*rights.indexOf(text)) * (height) / rights.size() / 2);
			Line line = new Line().setBorderColor(borderColor).setBorderWidth(1.0)
			.setStart(text.getX(), text.getY())
			.setEnd(p + Math.sqrt(Math.abs(r*r-(q-text.getY())*(q-text.getY()))), text.getY());
			lines.add(line);
			add(line);
			line.getElement().setAttribute("marker-end", "url(#Triangle)");
		}
		for(Text text: lefts) {
			text.setY((1+2*lefts.indexOf(text)) * (height) / lefts.size() / 2);
			Line line = new Line().setBorderColor(borderColor).setBorderWidth(1.0)
			.setStart(text.getX(), text.getY())
			.setEnd(p - Math.sqrt(Math.abs(r*r-(q-text.getY())*(q-text.getY()))), text.getY());
			lines.add(line);
			add(line);
			line.getElement().setAttribute("marker-end", "url(#Triangle)");
		}
		
		for(int i = 0; i < fans.size(); ++i) {
			Fan t = (Fan) getFanAt(i).setAlpha(0.5);
			int idx2 = i;
			t.addMouseOverHandler(evt->{
				t.setAlpha(1.0);
				getLabelAt(idx2).setAlpha(1.0);
				lines.get(idx2).setAlpha(1.0);
			});
			t.addMouseOutHandler(evt->{
				t.setAlpha(0.5);
				getLabelAt(idx2).setAlpha(0.5);
				lines.get(idx2).setAlpha(0.5);
			});
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
		return fans.isEmpty();
	}
}
