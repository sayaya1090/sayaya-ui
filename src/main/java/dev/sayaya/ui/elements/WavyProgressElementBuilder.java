package dev.sayaya.ui.elements;

import dev.sayaya.ui.elements.interfaces.HasAriaLabel;
import elemental2.dom.*;
import elemental2.dom.DomGlobal;
import org.jboss.elemento.*;

import static org.jboss.elemento.Elements.div;
import static org.jboss.elemento.Elements.htmlElement;

/**
 * M3 Expressive wavy linear progress indicator (sayaya-ui 자체 구현, elemento 엘리먼트만 사용).
 * <pre>{@code
 * import static dev.sayaya.ui.elements.WavyProgressElementBuilder.*;
 *
 * var upload = wavyProgress().value(0.4).ariaLabel("업로드 중").element();
 * var waiting = wavyProgress().indeterminate(true).ariaLabel("불러오는 중").element();
 * }</pre>
 * M3: 표현력을 높이고 싶을 때 활성 트랙에 물결을 줄 수 있다(progress indicators, Shape: Flat and wavy).
 * 수치 출처: Compose Material3 LinearProgressIndicatorTokens — 높이(WaveHeight) 10dp, 진폭 3dp, 파장 40dp(indeterminate 20dp),
 * 두께 4dp, 활성-트랙 간격(TrackActiveSpace) 4dp, 끝 점(StopSize) 4dp. 색은 활성 primary, 트랙 secondary container.
 * <p>CSS 변수: {@code --md-wavy-progress-active-color}, {@code --md-wavy-progress-track-color}.</p>
 */
public final class WavyProgressElementBuilder extends CustomElementBuilder<WavyProgressElementBuilder> implements
        HasAriaLabel<HTMLDivElement, WavyProgressElementBuilder> {
    public static final String CLASS = "sayaya-wavy-progress";
    static final String STYLE_ID = "sayaya-wavy-progress-style";

    public static WavyProgressElementBuilder wavyProgress() { return new WavyProgressElementBuilder(); }

    private WavyProgressElementBuilder() {
        injectStyle();
        element().classList.add(CLASS);
        element().setAttribute("role", "progressbar");
        element().setAttribute("aria-valuemin", "0");
        element().setAttribute("aria-valuemax", "1");
        element().appendChild(div().css("active").element());
        element().appendChild(div().css("track").element());
        element().appendChild(div().css("stop").element());
        value(0);
    }
    @Override public WavyProgressElementBuilder that() { return this; }

    /** 진행률 0~1 (범위를 벗어나면 잘라낸다) */
    public WavyProgressElementBuilder value(double value) {
        double v = Math.max(0, Math.min(1, value));
        this.val = v;
        element().style.setProperty("--_value", String.valueOf(v));
        element().setAttribute("aria-valuenow", String.valueOf(v));
        updateCircle();
        return this;
    }

    // ---------- circular (CircularProgressIndicatorTokens: WaveSize 48, Size 40, 두께 4, 진폭 1.6, 파장 15, 활성-트랙 간격 4) ----------
    static final double RADIUS = (40 - 4) / 2.0, AMPLITUDE = 1.6, WAVELENGTH = 15, TRACK_SPACE = 4, THICKNESS = 4;
    private static final String SVG_NS = "http://www.w3.org/2000/svg";
    private double val = 0;
    private Element svg, activePath, trackPath;
    /** 원형 물결 진행 표시기(M3 progress indicators: circular, Shape: wavy) */
    public WavyProgressElementBuilder circular() {
        if (svg != null) return this;
        element().classList.add("circular");
        svg = DomGlobal.document.createElementNS(SVG_NS, "svg");
        svg.setAttribute("viewBox", "0 0 48 48");
        svg.setAttribute("aria-hidden", "true");
        trackPath = DomGlobal.document.createElementNS(SVG_NS, "path");
        trackPath.setAttribute("class", "c-track");
        trackPath.setAttribute("d", circlePath(0));
        trackPath.setAttribute("pathLength", "1");
        activePath = DomGlobal.document.createElementNS(SVG_NS, "path");
        activePath.setAttribute("class", "c-active");
        activePath.setAttribute("d", circlePath(AMPLITUDE));
        activePath.setAttribute("pathLength", "1");
        svg.appendChild(trackPath);
        svg.appendChild(activePath);
        element().appendChild(svg);
        updateCircle();
        return this;
    }
    /** 둘레에 파장 15dp 의 물결(정수 개)을 얹은 원. 12시 방향에서 시계 방향으로 그린다 */
    static String circlePath(double amplitude) {
        int waves = (int) Math.round(2 * Math.PI * RADIUS / WAVELENGTH);
        StringBuilder d = new StringBuilder();
        int points = 240;
        for (int i = 0; i <= points; i++) {
            double t = 2 * Math.PI * i / points;
            double r = RADIUS + amplitude * Math.sin(waves * t);
            double x = 24 + r * Math.sin(t), y = 24 - r * Math.cos(t);
            d.append(i == 0 ? "M" : "L").append(num(x)).append(' ').append(num(y));
        }
        return d.toString();
    }
    private static String num(double v) { return String.valueOf(Math.round(v * 100) / 100.0); }
    private void updateCircle() {
        if (svg == null) return;
        boolean ind = element().classList.contains("indeterminate");
        double v = ind ? 0.25 : val;
        // 둥근 끝(두께/2)만큼 양쪽이 늘어나므로 보이는 간격이 4dp 가 되도록 경로 길이 비율로 보정
        double gap = (TRACK_SPACE + THICKNESS) / (2 * Math.PI * RADIUS);
        activePath.setAttribute("stroke-dasharray", v <= 0 ? "0 1" : num(v) + " 1");
        double len = 1 - v - 2 * gap;
        trackPath.setAttribute("stroke-dasharray", ind || len <= 0 ? "0 1" : "0 " + num(v <= 0 ? 0 : v + gap) + " " + num(v <= 0 ? 1 : len) + " 1");
    }
    /** 진행률을 알 수 없을 때 */
    public WavyProgressElementBuilder indeterminate(boolean indeterminate) {
        if (indeterminate) { element().classList.add("indeterminate"); element().removeAttribute("aria-valuenow"); }
        else { element().classList.remove("indeterminate"); element().setAttribute("aria-valuenow", element().style.getPropertyValue("--_value")); }
        updateCircle();
        return this;
    }

    /** 진폭 3·두께 4·높이 10 의 한 파장 경로(사인 근사 2차 베지어) */
    static String wave(int wavelength) {
        int half = wavelength / 2, q = wavelength / 4;
        String svg = "<svg xmlns='http://www.w3.org/2000/svg' width='" + wavelength + "' height='10' viewBox='0 0 " + wavelength + " 10'>"
                + "<path d='M0 5 Q" + q + " -1 " + half + " 5 T" + wavelength + " 5' fill='none' stroke='black' stroke-width='4' stroke-linecap='round'/></svg>";
        return "url(\"data:image/svg+xml," + svg.replace("<", "%3C").replace(">", "%3E").replace("#", "%23") + "\")";
    }
    private static void injectStyle() {
        HTMLStyleElement style = newStyle(STYLE_ID);
        if (style == null) return;
        String c = "." + CLASS;
        style.textContent =
            c + "{position:relative;display:block;box-sizing:border-box;height:10px;min-width:80px;overflow:hidden;"
          + "--_active:var(--md-wavy-progress-active-color,var(--md-sys-color-primary,#6750a4));"
          + "--_track:var(--md-wavy-progress-track-color,var(--md-sys-color-secondary-container,#e8def8))}"
          // 활성 트랙: 물결 마스크(파장 40dp), 위상이 흐른다
          + c + " .active{position:absolute;left:0;top:0;height:10px;width:calc(var(--_value) * 100%);background:var(--_active);"
          + "-webkit-mask-image:" + wave(40) + ";mask-image:" + wave(40) + ";-webkit-mask-size:40px 10px;mask-size:40px 10px;"
          + "-webkit-mask-repeat:repeat-x;mask-repeat:repeat-x;animation:sayaya-wave-flow 1s linear infinite}"
          // 트랙: 평평한 4dp, 활성 끝에서 4dp 띄움
          + c + " .track{position:absolute;top:3px;height:4px;border-radius:var(--md-wavy-progress-track-shape,var(--md-sys-shape-corner-full,2px));background:var(--_track);"
          + "left:calc(var(--_value) * 100% + 4px);right:0}"
          // 끝 점 4dp
          + c + " .stop{position:absolute;right:0;top:3px;width:4px;height:4px;border-radius:var(--md-wavy-progress-stop-shape,var(--md-sys-shape-corner-full,50%));background:var(--_active)}"
          + "@keyframes sayaya-wave-flow{from{-webkit-mask-position:0 0;mask-position:0 0}to{-webkit-mask-position:40px 0;mask-position:40px 0}}"
          // Indeterminate: 파장 20dp, 활성 구간이 왼쪽에서 오른쪽으로 지나간다. 끝 점은 없다
          + c + ".indeterminate .active{width:40%;-webkit-mask-image:" + wave(20) + ";mask-image:" + wave(20) + ";"
          + "-webkit-mask-size:20px 10px;mask-size:20px 10px;animation:sayaya-wave-sweep 1.6s cubic-bezier(0.4,0,0.2,1) infinite}"
          + c + ".indeterminate .track{left:0}"
          + c + ".indeterminate .stop{display:none}"
          + "@keyframes sayaya-wave-sweep{from{transform:translateX(-100%)}to{transform:translateX(250%)}}"
          + c + ".circular{width:48px;height:48px;min-width:0;overflow:visible}"
          + c + ".circular .active," + c + ".circular .track," + c + ".circular .stop{display:none}"
          + c + ".circular svg{display:block;width:48px;height:48px}"
          + c + ".circular path{fill:none;stroke-width:4;stroke-linecap:round}"
          + c + ".circular .c-active{stroke:var(--_active)}"
          + c + ".circular .c-track{stroke:var(--_track)}"
          + c + ".circular.indeterminate svg{animation:sayaya-wave-spin 1.5s linear infinite}"
          + "@keyframes sayaya-wave-spin{from{transform:rotate(0deg)}to{transform:rotate(360deg)}}"
          + "@media (prefers-reduced-motion: reduce){" + c + " .active," + c + ".circular svg{animation:none}}";
        DomGlobal.document.head.appendChild(style);
    }
}
