package net.sayaya.ui.widget;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FocusWidget;

import lombok.Builder;

public class SVG extends FocusWidget {
	private final String id = DOM.createUniqueId();
	private final Element element = element(id);
	
	@Builder
	public SVG() {
		setElement(element);
	}
	
	private final native static Element element(String id) /*-{
		var svg = $wnd.document.createElementNS("http://www.w3.org/2000/svg", "svg");
		return svg;
	}-*/;
	
	public final Element create(String id, String tag) {
		Element elem = element(id, tag);
		element.appendChild(elem);
		return elem;
	}
	
	private final native Element element(String id, String tag) /*-{
		var elem = $wnd.document.createElementNS("http://www.w3.org/2000/svg", tag);
		return elem;
	}-*/;
}

