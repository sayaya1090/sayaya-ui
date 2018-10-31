package net.sayaya.ui.dto;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative=true, namespace=JsPackage.GLOBAL, name="Object")
public final class Page {
	@JsProperty(name="page")
	private int page;
	@JsProperty(name="offset")
	private long offset;
	@JsProperty(name="limit")
	private int limit;
	@JsProperty(name="col")
	private String col;
	@JsProperty(name="is_desc")
	private boolean is_desc;
	@JsOverlay
	public Long getOffset() {
		return offset;
	}
	@JsOverlay
	public Page setOffset(Long offset) {
		if(offset!=null) {
			this.offset = offset.longValue();
			if(limit > 0) page = (int) offset.longValue()/limit;
		}
		return this;
	}
	@JsOverlay
	public int getLimit() {
		return limit;
	}
	@JsOverlay
	public Page setLimit(int limit) {
		this.limit = limit;
		// if(offset >= 0) page = (int) offset/limit;
		return this;
	}
	@JsOverlay
	public int getPage() {
		return page;
	}
	@JsOverlay
	public String getCol() {
		return col;
	}
	@JsOverlay
	public Page setCol(String col) {
		this.col = col;
		return this;
	}
	@JsOverlay
	public boolean isDesc() {
		return is_desc;
	}
	@JsOverlay
	public Page setDesc(boolean is_desc) {
		this.is_desc = is_desc;
		return this;
	}
	
}
