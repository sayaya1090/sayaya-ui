package net.sayaya.ui.regacy.shape.impl;

import java.util.HashSet;

import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.HandlerRegistration;

import net.sayaya.ui.regacy.data.Point;
import net.sayaya.ui.regacy.shape.Shape;

@SuppressWarnings("unchecked")
public abstract class ShapeInstance<S extends ShapeInstance<S>> implements Shape {
	private final HashSet<MouseOverHandler> overHandlers = new HashSet<>();
	private final HashSet<MouseOutHandler> outHandlers = new HashSet<>();
	private final HashSet<MouseDownHandler> downHandlers = new HashSet<>();
	private Point<Integer, Integer> position = new Point<Integer, Integer>();
	private double rotate;
	@Override
	public final int getX() {
		return position.getX();
	}
	@Override
	public final int getY() {
		return position.getY();
	}
	@Override
	public final double getRotate() {
		return rotate;
	}
	public final S setX(int x) {
		position.setX(x);
		return (S)this;
	}
	public final S setY(int y) {
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
