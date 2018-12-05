package net.sayaya.ui.svg.shape;

import java.util.LinkedList;
import java.util.List;

import org.vectomatic.dom.svg.OMSVGDocument;
import org.vectomatic.dom.svg.OMSVGSVGElement;
import org.vectomatic.dom.svg.utils.OMSVGParser;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.impl.FocusImpl;

import net.sayaya.ui.widget.shape.Shape;

public class Canvas extends HTML implements Focusable {
	private static final String SVG_NAMESPACE = "http://www.w3.org/2000/svg";
	private final OMSVGDocument doc = OMSVGParser.currentDocument();
	private final OMSVGSVGElement canvas = doc.createSVGSVGElement();
	private final List<Shape> shapes = new LinkedList<Shape>();
	
	private static native Element createElementNS(final String ns, final String name)/*-{
		return document.createElementNS(ns, name);
	}-*/;
	public Canvas(int width, int height) {
		getElement().appendChild(canvas.getElement());
		canvas.setViewBox(0, 0, width, height);
		canvas.getWidth().getBaseVal().newValueSpecifiedUnits(Unit.PCT, 100);
		canvas.getHeight().getBaseVal().newValueSpecifiedUnits(Unit.PCT, 100);
	}
	Canvas(){}
	public final List<Shape> getShapes() {
		return shapes;
	}
	public final Canvas add(Shape shape) {
		shapes.add(shape);
		return this;
	}
	
	public Canvas remove(Shape shape) {
		shapes.remove(shape);
		return this;
	}
	
	public Canvas removeAll() {
		shapes.clear();
		return this;
	}
	
	public Canvas clear() {
//		getContext().clearRect(0, 0, canvas.getCanvasElement().getWidth(), canvas.getCanvasElement().getHeight());
		return this;
	}
	
	@Override
	public void setWidth(String width) {
		throw new RuntimeException("Not Suppport operation exception: use Canvas.setWidth(int)");
	}
	
	@Override
	public void setHeight(String height) {
		throw new RuntimeException("Not Suppport operation exception: use Canvas.setWidth(int)");
	}

	private static final FocusImpl impl = FocusImpl.getFocusImplForWidget();
	@Override
	public int getTabIndex() {
		return impl.getTabIndex(getElement());
	}

	@Override
	public void setAccessKey(char key) {
		getElement().setPropertyString("accessKey", "" + key);
	}

	@Override
	public void setFocus(boolean focused) {
		if (focused) impl.focus(getElement());
		else impl.blur(getElement());
	}

	@Override
	public void setTabIndex(int index) {
		impl.setTabIndex(getElement(), index);
	}
}
