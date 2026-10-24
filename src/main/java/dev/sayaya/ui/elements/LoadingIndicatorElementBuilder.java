package dev.sayaya.ui.elements;

import dev.sayaya.ui.elements.interfaces.HasAriaLabel;
import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLStyleElement;
import org.jboss.elemento.*;

import java.util.function.DoubleFunction;

import static org.jboss.elemento.Elements.div;
import static org.jboss.elemento.Elements.htmlElement;

/**
 * M3 loading indicator (sayaya-ui 자체 구현, elemento 엘리먼트만 사용 — labs.bundle.js·dom 바인딩 불필요).
 * <pre>{@code
 * import static dev.sayaya.ui.elements.LoadingIndicatorElementBuilder.*;
 *
 * var loading = loadingIndicator().ariaLabel("뉴스 기사 불러오는 중").element();
 * var overContent = loadingIndicator().contained(true).size(64).ariaLabel("새로 고침 중").element();
 * }</pre>
 * M3 가이드: 200ms~5s 의 짧고 진행률을 알 수 없는 대기에 쓴다(5s 이상은 progress indicator).
 * 기본 크기 48dp, 도형 38dp, 크기는 24~240dp 범위에서 비율을 유지한다. 다른 콘텐츠 위에 놓일 때는 contained 를 쓴다.
 * <p>CSS 변수: {@code --md-loading-indicator-size}, {@code --md-loading-indicator-active-indicator-color},
 * {@code --md-loading-indicator-container-color}.</p>
 */
public final class LoadingIndicatorElementBuilder implements
        HTMLElementStyleMethods<HTMLDivElement, LoadingIndicatorElementBuilder>, HTMLElementVisibilityMethods<HTMLDivElement, LoadingIndicatorElementBuilder>,
        ElementAttributeMethods<HTMLDivElement, LoadingIndicatorElementBuilder>, ElementClassListMethods<HTMLDivElement, LoadingIndicatorElementBuilder>,
        ElementEventMethods<HTMLDivElement, LoadingIndicatorElementBuilder>, ElementIdMethods<HTMLDivElement, LoadingIndicatorElementBuilder>,
        ElementConsumerMethods<HTMLDivElement, LoadingIndicatorElementBuilder>, HasAriaLabel<HTMLDivElement, LoadingIndicatorElementBuilder> {
    /** 스펙: 기본 48dp, 24~240dp */
    public static final int DEFAULT_SIZE = 48, MIN_SIZE = 24, MAX_SIZE = 240;
    public static final String CLASS = "sayaya-loading-indicator";
    public static final String ACTIVE_CLASS = "sayaya-loading-indicator-active";
    static final String SPIN_CLASS = "sayaya-loading-indicator-spin";
    static final String STYLE_ID = "sayaya-loading-indicator-style";

    public static LoadingIndicatorElementBuilder loadingIndicator() { return new LoadingIndicatorElementBuilder(); }

    private final HTMLContainerBuilder<HTMLDivElement> that = div();
    private LoadingIndicatorElementBuilder() {
        injectStyle();
        element().classList.add(CLASS);
        element().setAttribute("role", "progressbar"); // 스펙: progress bar 역할
        element().setAttribute("aria-label", "Loading"); // 용도를 설명하는 라벨로 바꿀 것(ariaLabel)
        element().appendChild(div().css(SPIN_CLASS).attr("aria-hidden", "true").add(div().css(ACTIVE_CLASS)).element());
    }
    @Override public LoadingIndicatorElementBuilder that() { return this; }
    @Override public HTMLDivElement element() { return that.element(); }

    /** 원형 컨테이너(primary container)와 on-primary-container 지시자. 다른 콘텐츠 위에 놓일 때 사용 */
    public LoadingIndicatorElementBuilder contained(boolean contained) {
        if (contained) element().classList.add("contained"); else element().classList.remove("contained");
        return this;
    }
    /** 전체 크기(dp=px). 24~240 으로 제한되며 컨테이너와 도형의 비율(48:38)은 유지된다 */
    public LoadingIndicatorElementBuilder size(int dp) {
        int v = Math.max(MIN_SIZE, Math.min(MAX_SIZE, dp));
        element().style.setProperty("--md-loading-indicator-size", v + "px");
        return this;
    }

    // ---------- 스타일 ----------
    private static final int POINTS = 48;
    /** Compose Material3 LoadingIndicator.kt: MorphIntervalMillis = 650 */
    private static final int MORPH_MS = 650;
    /** Compose Material3 LoadingIndicator.kt: GlobalRotationDurationMillis = 4666 (LinearEasing, 360°) */
    private static final int GLOBAL_ROTATION_MS = 4666;
    /** 도형 전환마다 +90°(QuarterRotation). 7×90=630° 는 360° 의 배수가 아니므로 4바퀴(28회, 2520°)를 한 주기로 둔다 */
    private static final int CYCLES = 4;
    /** [파생] Compose 는 spring(dampingRatio=0.6, stiffness=200). CSS 로는 넘침(overshoot)이 있는 cubic-bezier 로 근사 */
    private static final String MORPH_EASING = "cubic-bezier(0.34,1.45,0.64,1)";

    private static void injectStyle() {
        if (DomGlobal.document.getElementById(STYLE_ID) != null) return;
        HTMLStyleElement style = htmlElement("style", HTMLStyleElement.class).id(STYLE_ID).element();
        style.textContent = css();
        DomGlobal.document.head.appendChild(style);
    }
    static String css() {
        // 도형 순서는 Compose IndeterminateIndicatorPolygons 와 같다(SoftBurst, Cookie9Sided, Pentagon, Pill, Sunny, Cookie4Sided, Oval).
        // [파생] 각 도형 좌표는 MaterialShapes(RoundedPolygon)의 공식 좌표가 아니라 같은 점 수의 다각형으로 근사한다. clip-path polygon 은 점 수가 같아야 보간되므로 모두 POINTS 개로 만든다.
        String[] shapes = {
                polygon(t -> polar(t, 0.45 + 0.05 * Math.cos(10 * t))),
                polygon(t -> polar(t, 0.44 + 0.06 * Math.cos(9 * t))),
                polygon(t -> polar(t, 0.42 + 0.08 * Math.cos(5 * t))),
                polygon(t -> superellipse(t, 0.5, 0.3, 4, Math.PI / 4)),
                polygon(t -> polar(t, 0.46 + 0.04 * Math.cos(8 * t))),
                polygon(t -> polar(t, 0.42 + 0.08 * Math.cos(4 * t))),
                polygon(t -> superellipse(t, 0.5, 0.35, 2, -Math.PI / 4))
        };
        // 한 주기 = 7개 도형 × 4바퀴 = 28회 전환. 각 전환에서 다음 도형으로 바뀌며 90° 더 회전한다.
        int steps = shapes.length * CYCLES;
        StringBuilder kf = new StringBuilder("@keyframes sayaya-li-morph{");
        for (int i = 0; i <= steps; i++)
            kf.append(round(100.0 * i / steps)).append("%{clip-path:").append(shapes[i % shapes.length])
              .append(";transform:rotate(").append(i * 90).append("deg)}");
        kf.append('}');
        return kf
                + "@keyframes sayaya-li-spin{from{transform:rotate(0deg)}to{transform:rotate(360deg)}}"
                + "." + CLASS + "{--_size:var(--md-loading-indicator-size," + DEFAULT_SIZE + "px);display:inline-flex;align-items:center;justify-content:center;"
                + "flex:none;box-sizing:border-box;width:var(--_size);height:var(--_size);border-radius:var(--md-loading-indicator-container-shape,var(--md-sys-shape-corner-full,50%));vertical-align:middle}"
                + "." + CLASS + ".contained{background:var(--md-loading-indicator-container-color,var(--md-sys-color-primary-container,#eaddff))}"
                + "." + SPIN_CLASS + "{display:flex;align-items:center;justify-content:center;animation:sayaya-li-spin " + GLOBAL_ROTATION_MS + "ms linear infinite}"
                // 스펙: 크기 48dp 일 때 도형 38dp — 비율 38/48 유지
                + "." + ACTIVE_CLASS + "{width:calc(var(--_size) * 38 / 48);height:calc(var(--_size) * 38 / 48);"
                + "background:var(--md-loading-indicator-active-indicator-color,var(--md-sys-color-primary,#6750a4));clip-path:" + shapes[0] + ";"
                + "animation:sayaya-li-morph " + (MORPH_MS * steps) + "ms " + MORPH_EASING + " infinite}"
                + "." + CLASS + ".contained ." + ACTIVE_CLASS + "{background:var(--md-loading-indicator-active-indicator-color,var(--md-sys-color-on-primary-container,#21005d))}"
                // 움직임 줄이기 설정: 회전·전환을 멈추고 첫 도형만 보인다
                + "@media (prefers-reduced-motion: reduce){." + SPIN_CLASS + ",." + ACTIVE_CLASS + "{animation:none}}";
    }
    private static String polygon(DoubleFunction<double[]> f) {
        StringBuilder sb = new StringBuilder("polygon(");
        for (int i = 0; i < POINTS; i++) {
            double t = 2 * Math.PI * i / POINTS - Math.PI / 2;
            double[] p = f.apply(t);
            if (i > 0) sb.append(',');
            sb.append(round(50 + p[0] * 100)).append("% ").append(round(50 + p[1] * 100)).append('%');
        }
        return sb.append(')').toString();
    }
    private static double[] polar(double t, double r) { return new double[]{r * Math.cos(t), r * Math.sin(t)}; }
    private static double[] superellipse(double t, double a, double b, double n, double rot) {
        double c = Math.cos(t), s = Math.sin(t);
        double x = a * Math.signum(c) * Math.pow(Math.abs(c), 2 / n), y = b * Math.signum(s) * Math.pow(Math.abs(s), 2 / n);
        return new double[]{x * Math.cos(rot) - y * Math.sin(rot), x * Math.sin(rot) + y * Math.cos(rot)};
    }
    private static String round(double v) {
        double r = Math.round(v * 100) / 100.0;
        return r == Math.floor(r) ? String.valueOf((long) r) : String.valueOf(r);
    }
}
