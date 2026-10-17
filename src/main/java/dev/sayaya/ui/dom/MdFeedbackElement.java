package dev.sayaya.ui.dom;

import elemental2.dom.Element;
import elemental2.dom.HTMLElement;
import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

/** sayaya-ui 자체 구현 M3 tooltip / snackbar / button group 바인딩 (labs-bundle/src/sayaya-feedback.js). */
public final class MdFeedbackElement {
    private MdFeedbackElement() {}

    @JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
    public static class MdTooltipElement extends HTMLElement {
        public String variant;
        public String position;
        @jsinterop.annotations.JsProperty(name = "for") public String htmlFor;
        public double delay;
        public boolean open;
        public boolean persistent;
        public Element anchor;
        public native void show();
        public native void show(double autoHideMs);
        public native void hide();
    }

    @JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
    public static class MdSnackbarElement extends HTMLElement {
        public boolean open;
        public String action;
        public boolean closeable;
        public double timeout;
        public boolean multiline;
        public boolean longAction;
        public native void show();
        public native void close();
        public native void close(String reason);
    }

    @JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
    public static class MdButtonGroupElement extends HTMLElement {
        public String variant;
        public String size;
        public String selection;
        public double[] selectedIndices;
    }
}
