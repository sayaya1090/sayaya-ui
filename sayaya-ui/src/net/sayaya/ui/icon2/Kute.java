package net.sayaya.ui.icon2;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL, name="KUTE")	
public class Kute {
	public native static Animation fromTo(String pid, Shape from, Shape to);
	public native static Animation fromTo(String pid, Shape from, Shape to, Option option);
	
	public native static Animation to(String pid, Shape path);
	public native static Animation to(String pid, Shape path, Option option);
	
	@JsType(isNative = true, namespace= JsPackage.GLOBAL, name="Object")
	public final static class Shape {
		@JsProperty(name="path")
		private String path;
		@JsOverlay
		public String getPath() {
			return path;
		}
		@JsOverlay
		public Shape setPath(String path) {
			this.path = path;
			return this;
		}
	}
	
	@JsType(isNative = true, namespace= JsPackage.GLOBAL, name="Object")
	public final static class Option {
		@JsProperty(name="easing")
		private String easing;
		@JsProperty(name="yoyo")
		private Boolean yoyo;
		@JsProperty(name="repeat")
		private Double repeat;
		@JsProperty(name="duration")
		private Double duration;
		@JsProperty(name="morphIndex")
		private Double morphIndex;
		@JsProperty(name="morphPrecision")
		private Double morphPrecision;
		@JsOverlay
		public String getEasing() {
			return easing;
		}
		@JsOverlay
		public Option setEasing(String easing) {
			this.easing = easing;
			return this;
		}
		@JsOverlay
		public Boolean getYoyo() {
			return yoyo;
		}
		@JsOverlay
		public Option setYoyo(Boolean yoyo) {
			this.yoyo = yoyo;
			return this;
		}
		@JsOverlay
		public Double getRepeat() {
			return repeat;
		}
		@JsOverlay
		public Option setRepeat(Double repeat) {
			this.repeat = repeat;
			return this;
		}
		@JsOverlay
		public Double getDuration() {
			return duration;
		}
		@JsOverlay
		public Option setDuration(Double duration) {
			this.duration = duration;
			return this;
		}
		@JsOverlay
		public Double getMorphIndex() {
			return morphIndex;
		}
		@JsOverlay
		public Option setMorphIndex(Double morphIndex) {
			this.morphIndex = morphIndex;
			return this;
		}
		@JsOverlay
		public Double getMorphPrecision() {
			return morphPrecision;
		}
		@JsOverlay
		public Option setMorphPrecision(Double morphPrecision) {
			this.morphPrecision = morphPrecision;
			return this;
		}
	}
	
	@JsType(isNative = true, namespace= JsPackage.GLOBAL, name="Object")
	public final static class Animation {
		public native void start();
	}
}
