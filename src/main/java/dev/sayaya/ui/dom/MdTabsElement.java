package dev.sayaya.ui.dom;

import elemental2.dom.HTMLElement;
import elemental2.promise.Promise;
import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

@JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
public class MdTabsElement extends HTMLElement {
    public HTMLElement[] tabs;
    public Object activeTab;
    public boolean autoActivate;
    public int activeTabIndex;

    public native Promise<Boolean> getUpdateComplete();
    public native Promise<Void> scrollToTab(int index);
}
