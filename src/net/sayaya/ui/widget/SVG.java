package net.sayaya.ui.widget;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.FocusWidget;

import lombok.Builder;
import net.sayaya.ui.widget.shape.Shape;
import net.sayaya.ui.widget.shape.impl.Marker;

public class SVG extends FocusWidget {
	private final Element element = element();
	private final Element defs = def();
	@Builder
	public SVG() {
		setElement(element);
		element.appendChild(defs);
	}
	
	private final native static Element element() /*-{
		var svg = $wnd.document.createElementNS("http://www.w3.org/2000/svg", "svg");
		return svg;
	}-*/;
	
	private final native static Element def() /*-{
		var defs = $wnd.document.createElementNS("http://www.w3.org/2000/svg", "defs");
		return defs;
	}-*/;
	
	public SVG add(Shape shape) {
		element.appendChild(shape.toElement());
		return this;
	}
	public SVG add(Marker marker) {
		defs.appendChild(marker.toElement());
		return this;
	}
}

