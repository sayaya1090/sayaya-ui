package net.sayaya.ui.widget;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FocusWidget;

import lombok.Builder;

public class Barcode extends FocusWidget {
	private final String id = DOM.createUniqueId();
	private final Element element = element(id);
	
	@Builder
	public Barcode(String format, long value, Integer width, Integer height, Integer textmargin, String font, Integer fontSize
		, String fontoption, String lineColor, String background) {
		element.getStyle().setProperty("pointerEvents", "none");
		setElement(element);
		if(format != null && !format.trim().isEmpty()) element.setAttribute("jsbarcode-format", format);
		else element.setAttribute("jsbarcode-format", "auto");
		element.setAttribute("jsbarcode-value", String.valueOf(value));
		if(width!=null) element.setAttribute("jsbarcode-width", String.valueOf(width));
		if(height!=null) element.setAttribute("jsbarcode-height", String.valueOf(height));
		if(textmargin!=null) element.setAttribute("jsbarcode-textmargin", String.valueOf(textmargin));
		if(font!=null) element.setAttribute("jsbarcode-font", font);
		if(fontSize!=null) element.setAttribute("jsbarcode-fontsize", String.valueOf(fontSize));
		if(fontoption!=null) element.setAttribute("jsbarcode-fontoptions", fontoption);
		if(lineColor!=null) element.setAttribute("jsbarcode-linecolor", lineColor);
		if(background!=null) element.setAttribute("jsbarcode-background", background);
		addAttachHandler(evt->init(id));
	}
	
	public void init() {
		init(id);
	}
	
	private final native static Element element(String id) /*-{
		var svg = $wnd.document.createElementNS("http://www.w3.org/2000/svg", "svg");
		svg.className.baseVal = id;
		svg.className.indexOf = function(str) {
			return -1;
		};
		return svg;
	}-*/;
	
	private final native static void init(String id) /*-{	
		$wnd.JsBarcode("."+id).init();
	}-*/;
}
