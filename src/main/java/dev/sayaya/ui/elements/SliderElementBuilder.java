package dev.sayaya.ui.elements;

import dev.sayaya.ui.dom.MdSliderElement;
import dev.sayaya.ui.elements.interfaces.Disableable;
import dev.sayaya.ui.elements.interfaces.FormAssociable;
import dev.sayaya.ui.elements.interfaces.HasChangeEvent;
import dev.sayaya.ui.elements.interfaces.HasInputEvent;
import org.jboss.elemento.*;

import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLStyleElement;

import static org.jboss.elemento.Elements.htmlElement;

public abstract class SliderElementBuilder<SELF extends SliderElementBuilder<SELF>> implements HTMLElementStyleMethods<MdSliderElement, SELF>, HTMLElementVisibilityMethods<MdSliderElement, SELF>,
        ElementAttributeMethods<MdSliderElement, SELF>, ElementClassListMethods<MdSliderElement, SELF>, ElementEventMethods<MdSliderElement, SELF>,
        Disableable<MdSliderElement, SELF>, FormAssociable<MdSliderElement, SELF>,
        HasInputEvent<MdSliderElement, SELF>, HasChangeEvent<MdSliderElement, SELF> {
    public static SliderContinuousElementBuilder slider() {
        return new SliderContinuousElementBuilder(htmlElement("md-slider", MdSliderElement.class));
    }
    final HTMLElementBuilder<MdSliderElement> that;
    private SliderElementBuilder(HTMLElementBuilder<MdSliderElement> element) {
        this.that = element;
    }

    public SELF labeled() {
        return labeled(true);
    }
    public SELF labeled(boolean labeled) {
        element().labeled = labeled;
        return that();
    }
    public SELF min(double min) {
        element().min = min;
        return that();
    }
    public SELF max(double max) {
        element().max = max;
        return that();
    }
    public SELF step(double step) {
        element().step = step;
        return that();
    }
    public SELF valueLabel(String label) {
        element().valueLabel = label;
        return that();
    }

    @Override public SELF disabled(boolean disabled) {
        element().disabled = disabled;
        return that();
    }

    public double getMin() {
        return element().min;
    }
    public double getMax() {
        return element().max;
    }
    public double getStep() {
        return element().step;
    }
    public boolean isLabeled() {
        return element().labeled;
    }
    public boolean isTicks() {
        return element().ticks;
    }
    public boolean isDisabled() {
        return element().disabled;
    }

    @Override public MdSliderElement element() {return that.element();}

    // ---------- M3 Expressive 보완(md-slider 공개 CSS 변수 + 문서 수준 스타일) ----------
    /**
     * M3 Expressive 슬라이더 크기. m3.material.io sliders specs "Padding and size measurements for XS, S, M, L, and XL sliders":
     * Track height 16/24/40/56/96dp, Handle height 44/44/52/68/108dp, Handle width 4dp, Track shape 8/8/12/16/28dp, Inset icon size –/–/24/24/32dp.
     */
    public enum Size {
        XS(16, 44, 8, "small", 0), S(24, 44, 8, "small", 0), M(40, 52, 12, "medium", 24), L(56, 68, 16, "large", 24), XL(96, 108, 28, "extra-large", 32);
        /** dp 값. shape 는 --md-sys-shape-corner-{shapeToken} 이 없을 때의 기본값, icon 0 은 inset icon 을 쓰지 않는 크기 */
        public final int track, handle, shape, icon;
        final String shapeToken;
        Size(int track, int handle, int shape, String shapeToken, int icon) { this.track = track; this.handle = handle; this.shape = shape; this.shapeToken = shapeToken; this.icon = icon; }
    }
    static final String STYLE_ID = "sayaya-slider-expressive-style";
    /** 트랙 두께·핸들·트랙 모서리를 M3 Expressive 크기로 맞춘다(지정하지 않으면 Material Web 기본 모양) */
    public SELF size(Size size) {
        injectStyle();
        if (size == null) element().removeAttribute("data-size");
        else element().setAttribute("data-size", size.name().toLowerCase());
        updateInset();
        return that();
    }
    /** 세로 배치(아래가 최솟값). 길이는 --md-slider-vertical-length(기본 200px). M3: range slider 는 세로로 쓰지 않는다 */
    SELF applyVertical(boolean vertical) {
        injectStyle();
        if (vertical) element().setAttribute("data-vertical", ""); else element().removeAttribute("data-vertical");
        return that();
    }
    /** standard slider 의 트랙 안 아이콘(Material Symbols 이름). M 이상 크기에서만 보이며, 핸들 위치에 따라 활성/비활성 트랙으로 옮겨 간다 */
    SELF applyInsetIcon(String icon) {
        injectStyle();
        if (icon == null || icon.isEmpty()) { element().removeAttribute("data-inset-icon"); return that(); }
        element().setAttribute("data-inset-icon", icon);
        if (!element().hasAttribute("data-inset-bound")) {
            element().setAttribute("data-inset-bound", "");
            element().addEventListener("input", e -> updateInset());
            DomGlobal.window.addEventListener("resize", e -> updateInset());
        }
        updateInset();
        return that();
    }
    private int insetFrames = 0;
    /** 값 비율(--_sx-v)과 아이콘 위치(data-icon-side)를 갱신한다 */
    void updateInset() {
        if (!element().hasAttribute("data-inset-icon")) return;
        double min = element().min, max = element().max;
        Double v = element().value;
        double frac = max > min && v != null ? Math.max(0, Math.min(1, (v - min) / (max - min))) : 0;
        element().style.setProperty("--_sx-v", String.valueOf(frac));
        double width = element().offsetWidth;
        if (width <= 0) { if (insetFrames++ < 600) DomGlobal.requestAnimationFrame(t -> updateInset()); return; }
        insetFrames = 0;
        String sz = element().getAttribute("data-size");
        int icon = 24;
        for (Size z : Size.values()) if (z.name().equalsIgnoreCase(sz) && z.icon > 0) icon = z.icon;
        // [파생] 활성 트랙 폭이 아이콘 + 앞뒤 여백(8dp) + 핸들 간격보다 좁으면 아이콘을 핸들 뒤(비활성 트랙)로 옮긴다
        double active = (width - 40) * frac;
        element().setAttribute("data-icon-side", active >= icon + 24 ? "before" : "after");
    }
    private static void injectStyle() {
        if (DomGlobal.document.getElementById(STYLE_ID) != null) return;
        HTMLStyleElement style = htmlElement("style", HTMLStyleElement.class).id(STYLE_ID).element();
        String m = "md-slider";
        StringBuilder css = new StringBuilder();
        for (Size z : Size.values()) // 치수는 Size 하나에서만 가져온다
            css.append(m).append("[data-size=").append(z.name().toLowerCase()).append("]{--_sx-track:").append(z.track).append("px;--_sx-handle:").append(z.handle)
               .append("px;--_sx-shape:var(--md-sys-shape-corner-").append(z.shapeToken).append(',').append(z.shape).append("px);--_sx-icon:").append(z.icon)
               .append("px;--_sx-cross:").append(z.handle).append("px}");
        css.append(m).append("[data-size]{--md-slider-active-track-height:var(--_sx-track);--md-slider-inactive-track-height:var(--_sx-track);")
           .append("--md-slider-active-track-shape:var(--_sx-shape);--md-slider-inactive-track-shape:var(--_sx-shape);")
           .append("--md-slider-handle-height:var(--_sx-handle);--md-slider-handle-width:4px;--md-slider-handle-shape:var(--md-sys-shape-corner-full,9999px);")
           .append("padding-block:max(0px,calc((var(--_sx-handle) - var(--md-slider-state-layer-size,40px)) / 2))}")
           // 세로: -90° 회전, 레이아웃 크기는 여백으로 보정(아래가 최솟값)
           .append(m).append("[data-vertical]{--_sx-len:var(--md-slider-vertical-length,200px);inline-size:var(--_sx-len);min-inline-size:0;transform:rotate(-90deg);")
           .append("margin-block:calc((var(--_sx-len) - var(--_sx-cross,var(--md-slider-state-layer-size,40px))) / 2);")
           .append("margin-inline:calc((var(--_sx-cross,var(--md-slider-state-layer-size,40px)) - var(--_sx-len)) / 2)}")
           // inset icon: 활성 트랙 안(on primary) 또는 핸들 뒤 비활성 트랙(on secondary container)
           .append(m).append("[data-inset-icon]{position:relative}")
           .append(m).append("[data-inset-icon]::before{content:attr(data-inset-icon);position:absolute;z-index:1;top:50%;transform:translateY(-50%);pointer-events:none;")
           .append("font-family:'Material Symbols Outlined';font-weight:normal;font-style:normal;line-height:1;font-size:var(--_sx-icon,24px);")
           .append("-webkit-font-feature-settings:'liga';font-feature-settings:'liga';color:var(--md-sys-color-on-primary,#ffffff);")
           .append("inset-inline-start:calc(var(--md-slider-state-layer-size,40px) / 2 + 8px)}")
           .append(m).append("[data-inset-icon][data-icon-side=after]::before{color:var(--md-sys-color-on-secondary-container,#1d192b);")
           .append("inset-inline-start:calc(var(--md-slider-state-layer-size,40px) / 2 + (100% - var(--md-slider-state-layer-size,40px)) * var(--_sx-v,0) + 12px)}")
           .append(m).append(":not([data-size])[data-inset-icon]::before{display:none}");
        for (Size z : Size.values()) if (z.icon == 0) // 문서: "Don't use an inset icon with sliders that have track thicknesses under 40dp"
            css.append(m).append("[data-size=").append(z.name().toLowerCase()).append("][data-inset-icon]::before{display:none}");
        style.textContent = css.toString();
        DomGlobal.document.head.appendChild(style);
    }

    public static class SliderContinuousElementBuilder extends SliderElementBuilder<SliderContinuousElementBuilder> {
        private SliderContinuousElementBuilder(HTMLElementBuilder<MdSliderElement> element) {
            super(element);
            element().range = false;
            element().ticks = false;
        }
        @Override public SliderContinuousElementBuilder that() {return this;}

        public SliderContinuousElementBuilder value(double value) {
            element().value = value;
            updateInset();
            return that();
        }
        /** 세로 배치. 길이는 --md-slider-vertical-length(기본 200px) */
        public SliderContinuousElementBuilder vertical(boolean vertical) { return applyVertical(vertical); }
        /** 트랙 안 아이콘(standard slider 전용, M·L·XL 크기에서만 표시) */
        public SliderContinuousElementBuilder insetIcon(String icon) { return applyInsetIcon(icon); }
        public double getValue() {
            Double val = element().value;
            return val != null ? val : 0;
        }

        public SliderDiscreteElementBuilder ticks(double step) {
            return new SliderDiscreteElementBuilder(that).ticks(step);
        }
        public SliderRangeElementBuilder range() {
            return new SliderRangeElementBuilder(that);
        }
    }

    public static class SliderDiscreteElementBuilder extends SliderElementBuilder<SliderDiscreteElementBuilder> {
        private SliderDiscreteElementBuilder(HTMLElementBuilder<MdSliderElement> element) {
            super(element);
            element().ticks = true;
            element().range = false;
        }

        public SliderDiscreteElementBuilder value(double value) {
            element().value = value;
            return that();
        }
        public double getValue() {
            Double val = element().value;
            return val != null ? val : 0;
        }

        public SliderDiscreteElementBuilder ticks(double step) {
            element().step = step;
            return that();
        }
        /** 세로 배치. 길이는 --md-slider-vertical-length(기본 200px) */
        public SliderDiscreteElementBuilder vertical(boolean vertical) { return applyVertical(vertical); }

        public SliderContinuousElementBuilder continuous() {
            element().ticks = false;
            return new SliderContinuousElementBuilder(that);
        }
        public SliderRangeElementBuilder range() {
            return new SliderRangeElementBuilder(that);
        }
        @Override public SliderDiscreteElementBuilder that() {return this;}
    }

    public static class SliderRangeElementBuilder extends SliderElementBuilder<SliderRangeElementBuilder> {
        private SliderRangeElementBuilder(HTMLElementBuilder<MdSliderElement> element) {
            super(element);
            element().range = true;
            element().ticks = false;
        }

        public SliderRangeElementBuilder valueStart(double value) {
            element().valueStart = value;
            return that();
        }
        public SliderRangeElementBuilder valueEnd(double value) {
            element().valueEnd = value;
            return that();
        }
        public SliderRangeElementBuilder valueLabelStart(String label) {
            element().valueLabelStart = label;
            return that();
        }
        public SliderRangeElementBuilder valueLabelEnd(String label) {
            element().valueLabelEnd = label;
            return that();
        }
        public SliderRangeElementBuilder ariaLabelStart(String label) {
            element().ariaLabelStart = label;
            return that();
        }
        public SliderRangeElementBuilder ariaLabelEnd(String label) {
            element().ariaLabelEnd = label;
            return that();
        }
        public SliderRangeElementBuilder nameStart(String name) {
            element().nameStart = name;
            return that();
        }
        public SliderRangeElementBuilder nameEnd(String name) {
            element().nameEnd = name;
            return that();
        }

        public double getValueStart() {
            Double val = element().valueStart;
            return val != null ? val : 0;
        }
        public double getValueEnd() {
            Double val = element().valueEnd;
            return val != null ? val : 0;
        }
        public boolean isRange() {
            return element().range;
        }

        public SliderRangeDiscreteElementBuilder ticks(double step) {
            return new SliderRangeDiscreteElementBuilder(that).ticks(step);
        }

        @Override public SliderRangeElementBuilder that() {return this;}
    }

    public static class SliderRangeDiscreteElementBuilder extends SliderElementBuilder<SliderRangeDiscreteElementBuilder> {
        private SliderRangeDiscreteElementBuilder(HTMLElementBuilder<MdSliderElement> element) {
            super(element);
            element().range = true;
            element().ticks = true;
        }

        public SliderRangeDiscreteElementBuilder valueStart(double value) {
            element().valueStart = value;
            return that();
        }
        public SliderRangeDiscreteElementBuilder valueEnd(double value) {
            element().valueEnd = value;
            return that();
        }
        public SliderRangeDiscreteElementBuilder ticks(double step) {
            element().step = step;
            return that();
        }
        public SliderRangeDiscreteElementBuilder valueLabelStart(String label) {
            element().valueLabelStart = label;
            return that();
        }
        public SliderRangeDiscreteElementBuilder valueLabelEnd(String label) {
            element().valueLabelEnd = label;
            return that();
        }
        public SliderRangeDiscreteElementBuilder ariaLabelStart(String label) {
            element().ariaLabelStart = label;
            return that();
        }
        public SliderRangeDiscreteElementBuilder ariaLabelEnd(String label) {
            element().ariaLabelEnd = label;
            return that();
        }
        public SliderRangeDiscreteElementBuilder nameStart(String name) {
            element().nameStart = name;
            return that();
        }
        public SliderRangeDiscreteElementBuilder nameEnd(String name) {
            element().nameEnd = name;
            return that();
        }

        public double getValueStart() {
            Double val = element().valueStart;
            return val != null ? val : 0;
        }
        public double getValueEnd() {
            Double val = element().valueEnd;
            return val != null ? val : 0;
        }

        @Override public SliderRangeDiscreteElementBuilder that() {return this;}
    }
}
