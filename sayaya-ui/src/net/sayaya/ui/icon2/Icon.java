package net.sayaya.ui.icon2;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.user.client.ui.Widget;

import net.sayaya.ui.handler.Callback;

public class Icon extends Widget {
	private final static HashMap<String, Element> SOLID_SYMBOLS = new HashMap<>();
	private final static HashMap<String, Element> REGULAR_SYMBOLS = new HashMap<>();
	private final static HashMap<String, Element> LIGHT_SYMBOLS = new HashMap<>();
	private static boolean initialized = false;
	private final static List<Callback<Void>> init = new LinkedList<>();
	static {
		load1();
		load2();
		load3();
	}
	private String name;
	private HashMap<String, Element> target = REGULAR_SYMBOLS;
	private final Element div = createSVG();
	private final String id = Document.get().createUniqueId();
	public Icon(String name) {
		this.name = name;
		this.setElement(div);
		style();
		if(initialized) append(div, target.get(name), id);
		else init.add(r->append(div, target.get(name), id));
		
		Scheduler.get().scheduleFixedDelay(()->{
			Snap morph = new Snap("#"+id);
			morph.animate(t(target.get("check").getElementsByTagName("path").getItem(0).getAttribute("d")), 5000);
			return false;
		}, 5000);
	}
	
	private void style() {
		getElement().getStyle().setDisplay(Display.INLINE_BLOCK);
		getElement().getStyle().setProperty("speak", "none");
		getElement().getStyle().setFontWeight(FontWeight.LIGHTER);
	}
	
	private void setRegular() {
		if(target == REGULAR_SYMBOLS) return;
		else target = REGULAR_SYMBOLS;
		append(div, target.get(name), id);
	}
	
	private void setBold() {
		if(target == SOLID_SYMBOLS) return;
		else target = SOLID_SYMBOLS;
		append(div, target.get(name), id);
	}
	
	private void setLight() {
		if(target == LIGHT_SYMBOLS) return;
		else target = LIGHT_SYMBOLS;
		append(div, target.get(name), id);
	}

	private native Object t(String path) /*-{
		return { d: path };
	}-*/;
	public native Element createSVG() /*-{
		var svg = $wnd.document.createElementNS('http://www.w3.org/2000/svg', 'svg');
		svg.setAttribute("area-hidden", "true");
		svg.setAttribute("focusable", "false");
		var obj = this;
		var MutationObserver = $wnd.MutationObserver || $wnd.WebKitMutationObserver || $wnd.MozMutationObserver;
		var observer = new MutationObserver(function(mutations) {
			mutations.forEach(function(mutation) {
				if (mutation.type == "attributes") {
					if(svg.style.fontWeight == null) obj.@net.sayaya.ui.icon2.Icon::setRegular()();
					else if(svg.style.fontWeight == "") obj.@net.sayaya.ui.icon2.Icon::setRegular()();
					else if(svg.style.fontWeight.toUpperCase() == "NORMAL") obj.@net.sayaya.ui.icon2.Icon::setRegular()();
					else if(svg.style.fontWeight.toUpperCase() == "BOLD") obj.@net.sayaya.ui.icon2.Icon::setBold()();
					else if(svg.style.fontWeight.toUpperCase() == "BOLDER") obj.@net.sayaya.ui.icon2.Icon::setBold()();
					else if(svg.style.fontWeight.toUpperCase() == "LIGHTER") obj.@net.sayaya.ui.icon2.Icon::setLight()();
				}
			});
		});
		observer.observe(svg, {
			attributes: true
		});
		return svg;
	}-*/;
	
	public static native Element append(Element svg, Element symbol, String id) /*-{
		while (svg.firstChild) svg.removeChild(svg.firstChild);
		var viewBox = symbol.getAttribute("viewBox");
		svg.setAttribute("viewBox", viewBox);
		var path = symbol.getElementsByTagName("path")[0].cloneNode(true);
		path.setAttribute("id", id);
		svg.appendChild(path.cloneNode(true));
		return svg;
	}-*/;
	
	public static native void load1() /*-{
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
		req.open("GET", "fontawesome-pro/sprites/solid.svg");
		req.send();
	}-*/;
	
	public static native void load2() /*-{
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
		req.open("GET", "fontawesome-pro/sprites/regular.svg");
		req.send();
	}-*/;
	
	public static native void load3() /*-{
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
		req.open("GET", "fontawesome-pro/sprites/light.svg");
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
