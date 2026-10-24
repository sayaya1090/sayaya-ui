package dev.sayaya.ui.dom;

import elemental2.dom.Element;
import elemental2.dom.HTMLElement;
import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

/** sayaya-ui 자체 구현 M3 tooltip / snackbar / button group, [M3 외 확장] toast 바인딩 (labs-bundle/src/sayaya-feedback.js). */
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
        /** 터치 길게 누르기 판정 시간(ms). 기본 500 */
        public double longPressDelay;
        /** 기본 슬롯 문구(바로 아래 텍스트 노드). 설정하면 기존 문구를 바꾼다 — subhead·actions 는 유지 */
        public String text;
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
        public String align;
        public native void show();
        public native void close();
        public native void close(String reason);
    }

    /** [M3 외 확장] 데스크톱 웹용 스택 알림. show() 하면 화면 오른쪽 위 .md-toast-stack 에 붙는다 */
    @JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
    public static class MdToastElement extends HTMLElement {
        public boolean open;
        public String level;
        public String action;
        public boolean closeable;
        public double timeout;
        public double max;
        public boolean leaving;
        /** 실제 자동 닫힘 시간(ms), 0 이면 닫지 않음 */
        public double effectiveTimeout;
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
