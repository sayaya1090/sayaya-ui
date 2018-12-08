package net.sayaya.ui.widget.shape;

import com.google.gwt.dom.client.Node;
import com.google.gwt.event.dom.client.HasMouseDownHandlers;
import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.HasMouseOverHandlers;

public interface Shape extends HasMouseOverHandlers, HasMouseOutHandlers, HasMouseDownHandlers {
	Node toElement();
}
