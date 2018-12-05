package net.sayaya.ui.widget.shape.impl;

import java.util.HashSet;

import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FocusWidget;

import net.sayaya.ui.data.Point;
import net.sayaya.ui.widget.SVG;
import net.sayaya.ui.widget.shape.Shape;

@SuppressWarnings("unchecked")
public class ShapeInstance<S extends ShapeInstance<S>> extends FocusWidget implements Shape {
	private final HashSet<MouseOverHandler> overHandlers = new HashSet<>();
	private final HashSet<MouseOutHandler> outHandlers = new HashSet<>();
	private final HashSet<MouseDownHandler> downHandlers = new HashSet<>();
	private Point<Double, Double> position = new Point<>();
	private double rotate;
	private String id;
	public ShapeInstance(SVG canvas, String tag) {
		id = DOM.createUniqueId();
		setElement(canvas.create(id, tag));
	}
	
	@Override
	public final double getX() {
		return position.getX();
	}
	@Override
	public final double getY() {
		return position.getY();
	}
	@Override
	public final double getRotate() {
		return rotate;
	}
	public final S setX(double x) {
		position.setX(x);
		return (S)this;
	}
	public final S setY(double y) {
		position.setY(y);
		return (S)this;
	}
	public final S setRotate(double rotate) {
		this.rotate = rotate;
		return (S)this;
	}
	@Override
	public final HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
		overHandlers.add(handler);
		return ()->overHandlers.remove(handler);
	}
	@Override
	public final void fireOver(MouseOverEvent event) {
		overHandlers.stream().forEach(handler->handler.onMouseOver(event));
	}
	@Override
	public final HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
		outHandlers.add(handler);
		return ()->outHandlers.remove(handler);
	}
	@Override
	public final void fireOut(MouseOutEvent event) {
		outHandlers.stream().forEach(handler->handler.onMouseOut(null));
	}
	@Override
	public final HandlerRegistration addMouseDownHandler(MouseDownHandler handler) {
		downHandlers.add(handler);
		return ()->downHandlers.remove(handler);
	}
	@Override
	public final void fireDown(MouseDownEvent event) {
		downHandlers.stream().forEach(handler->handler.onMouseDown(null));
	}
}