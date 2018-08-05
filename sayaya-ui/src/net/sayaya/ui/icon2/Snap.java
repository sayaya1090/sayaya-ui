package net.sayaya.ui.icon2;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL, name="Snap")	
public final class Snap {
	public Snap(String id) {};
	public native void animate(Path path, int duration);
	
	
	@JsType(isNative = true, namespace= JsPackage.GLOBAL, name="Object")
	public final static class Path {
		@JsProperty(name="d")
		private String d;
		@JsProperty(name="stroke")
		private String stroke;
		@JsProperty(name="strokeLinecap")
		private String strokeLinecap;
		@JsProperty(name="strokeDasharray")
		private String strokeDaskArray;
		@JsProperty(name="fill")
		private String fill;
		@JsOverlay
		public String getD() {
			return d;
		}
		@JsOverlay
		public Path setD(String d) {
			this.d = d;
			return this;
		}
		@JsOverlay
		public String getStroke() {
			return stroke;
		}
		@JsOverlay
		public Path setStroke(String stroke) {
			this.stroke = stroke;
			return this;
		}
		@JsOverlay
		public String getStrokeLinecap() {
			return strokeLinecap;
		}
		@JsOverlay
		public Path setStrokeLinecap(String strokeLinecap) {
			this.strokeLinecap = strokeLinecap;
			return this;
		}
		@JsOverlay
		public String getStrokeDaskArray() {
			return strokeDaskArray;
		}
		@JsOverlay
		public Path setStrokeDaskArray(String strokeDaskArray) {
			this.strokeDaskArray = strokeDaskArray;
			return this;
		}
		@JsOverlay
		public String getFill() {
			return fill;
		}
		@JsOverlay
		public Path setFill(String fill) {
			this.fill = fill;
			return this;
		}
	}
}
