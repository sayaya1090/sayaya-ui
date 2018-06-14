package net.sayaya.ui.dto;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative=true, namespace=JsPackage.GLOBAL, name="Object")
public final class Page {
	@JsProperty(name="offset")
	private long offset;
	@JsProperty(name="limit")
	private int limit;
	@JsOverlay
	public Long getOffset() {
		return offset;
	}
	@JsOverlay
	public Page setOffset(Long offset) {
		if(offset!=null) this.offset = offset.longValue();
		return this;
	}
	@JsOverlay
	public int getLimit() {
		return limit;
	}
	@JsOverlay
	public Page setLimit(int limit) {
		this.limit = limit;
		return this;
	}
}
