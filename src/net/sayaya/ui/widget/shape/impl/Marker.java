package net.sayaya.ui.widget.shape.impl;

import com.google.gwt.dom.client.Element;

public interface Marker {
	String getId();
	String viewBox();
	default Element toElement() {
		return ShapeInstance.element(getId(), "marker");
	}
}
