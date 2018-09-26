package net.sayaya.ui.icon2;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.Widget;

import net.sayaya.ui.handler.Callback;
import net.sayaya.ui.style.color.Palette;

public class Icon extends Widget {
	private final static List<Callback<Void>> init = new LinkedList<>();
	static {
		load1();
		load2();
		load3();
	}
	private static final Resource RESOURCE =  GWT.create(Resource.class);
	static {
		RESOURCE.style().ensureInjected();
	}
	public static final Resource.Style GSS = RESOURCE.style();
	public interface Resource extends ClientBundle {
		public static final Resource instance=  GWT.create(Resource.class);
		@Source("Icon.gss")
		Style style();
		
		public static interface Style extends CssResource {
			String icon();
			String regular();
			String light();
			String solid();
		}
	}
	private final static HashMap<String, Element> SOLID_SYMBOLS = new HashMap<>();
	private final static HashMap<String, Element> REGULAR_SYMBOLS = new HashMap<>();
	private final static HashMap<String, Element> LIGHT_SYMBOLS = new HashMap<>();
	private static boolean initialized = false;
	private final Element svg = createSVG();
	private final String id = Document.get().createUniqueId();
	private String name;
	private HashMap<String, Element> target = REGULAR_SYMBOLS;
	private String color = Palette.getInstance().getColorText1();
	public Icon(String name) {
		this.name = name;
		this.setElement(svg);
		style();
		if(initialized) append(svg, target.get(name), id, color);
		else init.add(r->append(svg, target.get(name), id, color));
	}
	
	private void style() {
		setStyleName(GSS.icon());
		color = Palette.getInstance().getColorText1();
	}
	
	@Override
	public native void setStyleName(String style) /*-{
		var svg = this.@net.sayaya.ui.icon2.Icon::svg;
		var classList = svg.classList;
		if(classList == null) svg.className = style;
		else classList.add(style);
	}-*/;
	
	private void setRegular() {
		if(!initialized) return;
		if(target == REGULAR_SYMBOLS) return;
		else target = REGULAR_SYMBOLS;
		update();
	}
	
	private void setBold() {
		if(!initialized) return;
		if(target == SOLID_SYMBOLS) return;
		else target = SOLID_SYMBOLS;
		update();
	}
	
	private void setLight() {
		if(!initialized) return;
		if(target == LIGHT_SYMBOLS) return;
		else target = LIGHT_SYMBOLS;
		update();
	}
	
	private void setColor(String color) {
		if(!initialized) return;
		if(this.color.equalsIgnoreCase(color)) return;
		this.color = color;
		update();
	}
	
	public Icon morph(String name) {
		Element symbol = target.get(name);
		String path = symbol.getElementsByTagName("path").getItem(0).getAttribute("d");
		svg.getElementsByTagName("path").getItem(0).setAttribute("fill", color);
		Kute.to("#"+id, new Kute.Shape().setPath(path), new Kute.Option().setEasing("easingQuadraticIn").setDuration(100.0).setMorphPrecision(4.0)).start();
		getElement().setAttribute("viewBox", symbol.getAttribute("viewBox"));
		return this;
	}
	

	private void update() {
		append(svg, target.get(name), id, color);
	}
	
	private native Element createSVG() /*-{
		var obj = this;
		var svg = $wnd.document.createElementNS('http://www.w3.org/2000/svg', 'svg');
		svg.setAttribute("area-hidden", "true");
		svg.setAttribute("focusable", "false");
		svg.setAttribute("preserveAspectRatio", "xMidYMid meet");
		
		var MutationObserver = $wnd.MutationObserver || $wnd.WebKitMutationObserver || $wnd.MozMutationObserver;
		var observer = new MutationObserver(function(mutations) {
			mutations.forEach(function(mutation) {
				if (mutation.type == "attributes") {
					var style = $wnd.getComputedStyle(svg, null);
					
					var fw = style.getPropertyValue("font-weight");
					if(fw == null)							obj.@net.sayaya.ui.icon2.Icon::setRegular()();
					else if(fw == "")						obj.@net.sayaya.ui.icon2.Icon::setRegular()();
					else if(fw.toUpperCase() == "NORMAL")	obj.@net.sayaya.ui.icon2.Icon::setRegular()();
					else if(fw.toUpperCase() == "BOLD")		obj.@net.sayaya.ui.icon2.Icon::setBold()();
					else if(fw.toUpperCase() == "BOLDER")	obj.@net.sayaya.ui.icon2.Icon::setBold()();
					else if(fw.toUpperCase() == "LIGHTER")	obj.@net.sayaya.ui.icon2.Icon::setLight()();
					else if(fw >= 700)						obj.@net.sayaya.ui.icon2.Icon::setBold()();
					else if(fw >= 400)						obj.@net.sayaya.ui.icon2.Icon::setRegular()();
					else if(fw < 400)						obj.@net.sayaya.ui.icon2.Icon::setLight()();
					
					var color = style.getPropertyValue("color");
					if(color!=null) obj.@net.sayaya.ui.icon2.Icon::setColor(Ljava/lang/String;)(color);
				}
			});
		});
		observer.observe(svg, {
			attributes: true
		});
		return svg;
	}-*/;
	
	private static native Element append(Element svg, Element symbol, String id, String color) /*-{
		while (svg.firstChild) svg.removeChild(svg.firstChild);
		if(symbol == null) return svg;
		var viewBox = symbol.getAttribute("viewBox");
		svg.setAttribute("viewBox", viewBox);
		
		var path = symbol.getElementsByTagName("path")[0].cloneNode(true);
		path.setAttribute("id", id);
		path.setAttribute("fill", color);
		svg.appendChild(path.cloneNode(true));
		return svg;
	}-*/;
	
	private static native void load1() /*-{
		var req = new XMLHttpRequest();
		req.onreadystatechange=function() {
			if(this.readyState!=4 || this.status!=200) return;
			var set = req.responseXML;
			var symbols = set.getElementsByTagName("symbol");
			for(i=0; i < symbols.length; ++i) {
				var symbol = symbols[i];
				//var head = $wnd.document.getElementsByTagName("head")[0];
				//head.appendChild(symbol);
				@net.sayaya.ui.icon2.Icon::registIcon1(Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;)(symbol.getAttribute("id"), symbol);
			}
			@net.sayaya.ui.icon2.Icon::complete()();
		};
		req.open("GET", "fontawesome/sprites/solid.svg");
		req.send();
	}-*/;
	
	private static native void load2() /*-{
		var req = new XMLHttpRequest();
		req.onreadystatechange=function() {
			if(this.readyState!=4 || this.status!=200) return;
			var set = req.responseXML;
			var symbols = set.getElementsByTagName("symbol");
			for(i=0; i < symbols.length; ++i) {
				var symbol = symbols[i];
				//var head = $wnd.document.getElementsByTagName("head")[0];
				//head.appendChild(symbol);
				@net.sayaya.ui.icon2.Icon::registIcon2(Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;)(symbol.getAttribute("id"), symbol);
			}
			@net.sayaya.ui.icon2.Icon::complete()();
		};
		req.open("GET", "fontawesome/sprites/regular.svg");
		req.send();
	}-*/;
	
	private static native void load3() /*-{
		var req = new XMLHttpRequest();
		req.onreadystatechange=function() {
			if(this.readyState!=4 || this.status!=200) return;
			var set = req.responseXML;
			var symbols = set.getElementsByTagName("symbol");
			for(i=0; i < symbols.length; ++i) {
				var symbol = symbols[i];
				//var head = $wnd.document.getElementsByTagName("head")[0];
				//head.appendChild(symbol);
				@net.sayaya.ui.icon2.Icon::registIcon3(Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;)(symbol.getAttribute("id"), symbol);
			}
			@net.sayaya.ui.icon2.Icon::complete()();
		};
		req.open("GET", "fontawesome/sprites/light.svg");
		req.send();
	}-*/;
	
	private static void registIcon1(String id, JavaScriptObject symbol) {
		SOLID_SYMBOLS.put(id, symbol.cast());
	}
	private static void registIcon2(String id, JavaScriptObject symbol) {
		REGULAR_SYMBOLS.put(id, symbol.cast());
	}
	private static void registIcon3(String id, JavaScriptObject symbol) {
		LIGHT_SYMBOLS.put(id, symbol.cast());
	}
	private static void complete() {
		if(!SOLID_SYMBOLS.isEmpty() && !REGULAR_SYMBOLS.isEmpty() && !LIGHT_SYMBOLS.isEmpty()) {
			initialized = true;
			for(Callback<Void> i: init) i.onSuccess(null);
			init.clear();
		}
	}
}
