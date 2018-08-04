package net.sayaya.ui.icon2;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL, name="Snap")	
public final class Snap {
	public Snap(String id) {};
	public native void animate(Object obj, int duration);
}
