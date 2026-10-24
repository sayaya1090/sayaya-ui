package dev.sayaya.ui.elements;

import dev.sayaya.ui.elements.interfaces.HasAriaLabel;
import dev.sayaya.ui.elements.interfaces.Disableable;
import elemental2.dom.*;
import elemental2.dom.DomGlobal;
import org.jboss.elemento.*;

import java.util.function.Consumer;

import static org.jboss.elemento.Elements.*;

/**
 * M3 Expressive centered slider (sayaya-ui 자체 구현, elemento 엘리먼트만 사용).
 * <pre>{@code
 * import static dev.sayaya.ui.elements.CenteredSliderElementBuilder.*;
 *
 * var balance = centeredSlider().range(-10, 10).value(0).step(1).ariaLabel("좌우 균형")
 *     .onInput(v -> setBalance(v)).element();
 * }</pre>
 * M3: 음수와 양수 범위에서 값을 고를 때, 0(기본값)이 가운데일 때 쓴다. 활성 트랙은 가운데에서 값까지 그려진다. 양 끝에 정지 표시가 있고,
 * 가운데·범위 슬라이더에는 inset 아이콘을 쓰지 않는다.
 * 수치 출처: Compose Material3 SliderTokens — 트랙 높이 16dp·모서리 full, 활성 트랙 primary, 비활성 트랙 secondary container,
 * 핸들 4×44dp primary, 핸들 양옆 간격 6dp, 정지 표시 4dp.
 * 입력은 네이티브 input[type=range] 가 받으므로 키보드(←/→, Home/End, PageUp/PageDown)와 보조기술이 기본으로 동작한다.
 */
public final class CenteredSliderElementBuilder extends CustomElementBuilder<CenteredSliderElementBuilder> implements
        HasAriaLabel<HTMLDivElement, CenteredSliderElementBuilder>, Disableable<HTMLDivElement, CenteredSliderElementBuilder> {
    public static final String CLASS = "sayaya-centered-slider";
    static final String STYLE_ID = "sayaya-centered-slider-style";
    /** 핸들 너비의 절반(2dp) + 핸들 옆 간격(6dp) */
    private static final int HANDLE_CLEARANCE = 8;

    public static CenteredSliderElementBuilder centeredSlider() { return new CenteredSliderElementBuilder(); }

    private final HTMLInputElement input;
    private final HTMLElement left, active, right, handle;

    private CenteredSliderElementBuilder() {
        injectStyle();
        element().classList.add(CLASS);
        left = div().css("inactive", "start").element();
        right = div().css("inactive", "end").element();
        active = div().css("active").element();
        handle = div().css("handle").element();
        input = Elements.input(InputType.range).css("input").element();
        input.min = "-50"; input.max = "50"; input.step = "1"; input.value = "0";
        element().appendChild(div().css("stop", "start").element());
        element().appendChild(div().css("stop", "end").element());
        element().appendChild(left); element().appendChild(active); element().appendChild(right); element().appendChild(handle);
        element().appendChild(input);
        input.addEventListener("input", e -> layout());
        layout();
        DomGlobal.requestAnimationFrame(t -> layout()); // 문서에 붙은 뒤 방향(dir)을 반영
    }
    @Override public CenteredSliderElementBuilder that() { return this; }

    /** 범위. 가운데 값((min+max)/2)이 활성 트랙의 시작점이다 */
    public CenteredSliderElementBuilder range(double min, double max) { input.min = fmt(min); input.max = fmt(max); layout(); return this; }
    public CenteredSliderElementBuilder step(double step) { input.step = fmt(step); return this; }
    public CenteredSliderElementBuilder value(double value) { input.value = fmt(value); layout(); return this; }
    public CenteredSliderElementBuilder ariaLabel(String label) { input.setAttribute("aria-label", label); return this; }
    public CenteredSliderElementBuilder name(String name) { input.name = name; return this; }
    public CenteredSliderElementBuilder disabled(boolean disabled) {
        input.disabled = disabled;
        if (disabled) element().classList.add("disabled"); else element().classList.remove("disabled");
        return this;
    }
    public CenteredSliderElementBuilder onInput(Consumer<Double> cb) { input.addEventListener("input", e -> cb.accept(value())); return this; }
    public CenteredSliderElementBuilder onChange(Consumer<Double> cb) { input.addEventListener("change", e -> cb.accept(value())); return this; }
    @Override public boolean isDisabled() { return input.disabled; }
    public double value() { return Double.parseDouble(input.value); }
    public HTMLInputElement input() { return input; }

    private static String fmt(double v) { return v == Math.floor(v) ? String.valueOf((long) v) : String.valueOf(v); }
    private void layout() {
        double min = Double.parseDouble(input.min), max = Double.parseDouble(input.max), v = Double.parseDouble(input.value);
        double p = max == min ? 0.5 : (v - min) / (max - min);
        if (Locales.rtl(element())) p = 1 - p; // RTL 에서 네이티브 range 는 오른쪽이 최솟값
        String pos = "calc(" + (p * 100) + "% ";
        handle.style.setProperty("left", pos + "- 2px)");
        if (p >= 0.5) {
            place(left, "0px", "50%");
            place(active, "50%", "max(0px, " + pos + "- 50% - " + HANDLE_CLEARANCE + "px))");
            place(right, pos + "+ " + HANDLE_CLEARANCE + "px)", "max(0px, calc(" + ((1 - p) * 100) + "% - " + HANDLE_CLEARANCE + "px))");
        } else {
            place(left, "0px", "max(0px, " + pos + "- " + HANDLE_CLEARANCE + "px))");
            place(active, pos + "+ " + HANDLE_CLEARANCE + "px)", "max(0px, calc(" + ((0.5 - p) * 100) + "% - " + HANDLE_CLEARANCE + "px))");
            place(right, "50%", "50%");
        }
        if (p == 0.5) active.style.setProperty("width", "0px");
    }
    private static void place(HTMLElement e, String left, String width) { e.style.setProperty("left", left); e.style.setProperty("width", width); }

    private static void injectStyle() {
        HTMLStyleElement style = newStyle(STYLE_ID);
        if (style == null) return;
        String c = "." + CLASS;
        style.textContent =
            c + "{position:relative;display:block;height:44px;min-width:120px;box-sizing:border-box;"
          + "--_active:var(--md-centered-slider-active-track-color,var(--md-sys-color-primary,#6750a4));"
          + "--_inactive:var(--md-centered-slider-inactive-track-color,var(--md-sys-color-secondary-container,#e8def8))}"
          + c + " .inactive," + c + " .active{position:absolute;top:14px;height:16px;border-radius:var(--md-centered-slider-track-shape,var(--md-sys-shape-corner-full,9999px))}" // 트랙 16dp, 세로 가운데
          + c + " .inactive{background:var(--_inactive)}"
          + c + " .active{background:var(--_active)}"
          + c + " .handle{position:absolute;top:0;width:4px;height:44px;border-radius:var(--md-centered-slider-handle-shape,var(--md-sys-shape-corner-full,9999px));background:var(--_active);pointer-events:none}"
          // 양 끝 정지 표시 4dp. [파생] 색은 비활성 트랙 위에서 보이도록 primary
          + c + " .stop{position:absolute;top:20px;width:4px;height:4px;border-radius:var(--md-centered-slider-stop-shape,var(--md-sys-shape-corner-full,50%));background:var(--_active);z-index:1}"
          + c + " .stop.start{left:6px}" + c + " .stop.end{right:6px}"
          + c + " .input{position:absolute;inset:0;width:100%;height:100%;margin:0;opacity:0;cursor:pointer;z-index:2}"
          + c + " .input:focus-visible~.handle," + c + ":focus-within .handle{outline:2px solid var(--md-sys-color-secondary,#625b71);outline-offset:2px}"
          + c + ".disabled{opacity:.38;pointer-events:none}";
        DomGlobal.document.head.appendChild(style);
    }
}
