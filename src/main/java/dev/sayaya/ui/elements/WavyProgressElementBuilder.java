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
public final class WavyProgressElementBuilder implements
        HTMLElementStyleMethods<HTMLDivElement, WavyProgressElementBuilder>, HTMLElementVisibilityMethods<HTMLDivElement, WavyProgressElementBuilder>,
        ElementAttributeMethods<HTMLDivElement, WavyProgressElementBuilder>, ElementClassListMethods<HTMLDivElement, WavyProgressElementBuilder>,
        ElementEventMethods<HTMLDivElement, WavyProgressElementBuilder>, ElementIdMethods<HTMLDivElement, WavyProgressElementBuilder>,
        ElementConsumerMethods<HTMLDivElement, WavyProgressElementBuilder>, HasAriaLabel<HTMLDivElement, WavyProgressElementBuilder> {
    public static final String CLASS = "sayaya-wavy-progress";
    static final String STYLE_ID = "sayaya-wavy-progress-style";

    public static WavyProgressElementBuilder wavyProgress() { return new WavyProgressElementBuilder(); }

    private final HTMLContainerBuilder<HTMLDivElement> that = div();
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
    @Override public HTMLDivElement element() { return that.element(); }

    /** 진행률 0~1 (범위를 벗어나면 잘라낸다) */
    public WavyProgressElementBuilder value(double value) {
        double v = Math.max(0, Math.min(1, value));
        element().style.setProperty("--_value", String.valueOf(v));
        element().setAttribute("aria-valuenow", String.valueOf(v));
        return this;
    }
    /** 진행률을 알 수 없을 때 */
    public WavyProgressElementBuilder indeterminate(boolean indeterminate) {
        if (indeterminate) { element().classList.add("indeterminate"); element().removeAttribute("aria-valuenow"); }
        else { element().classList.remove("indeterminate"); element().setAttribute("aria-valuenow", element().style.getPropertyValue("--_value")); }
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
        if (DomGlobal.document.getElementById(STYLE_ID) != null) return;
        HTMLStyleElement style = htmlElement("style", HTMLStyleElement.class).id(STYLE_ID).element();
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
          + "@media (prefers-reduced-motion: reduce){" + c + " .active{animation:none}}";
        DomGlobal.document.head.appendChild(style);
    }
}
