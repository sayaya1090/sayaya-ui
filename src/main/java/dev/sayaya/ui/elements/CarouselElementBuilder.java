package dev.sayaya.ui.elements;

import dev.sayaya.ui.elements.interfaces.HasAriaLabel;
import elemental2.dom.*;
import elemental2.dom.DomGlobal;
import jsinterop.base.Js;
import org.jboss.elemento.*;

import java.util.function.Consumer;

import static org.jboss.elemento.Elements.*;

/**
 * M3 carousel — multi-browse / hero / uncontained (sayaya-ui 자체 구현, elemento 엘리먼트만 사용).
 * <pre>{@code
 * import static dev.sayaya.ui.elements.CarouselElementBuilder.*;
 *
 * var photos = carousel().multiBrowse().itemWidth(200).ariaLabel("추천 여행지")
 *     .item("/img/jeju.jpg", "제주").item("/img/busan.jpg", "부산")
 *     .showAll("모두 보기", e -> openAll())
 *     .onActivate(index -> open(index)).element();
 * }</pre>
 * 수치 출처: m3.material.io carousel specs — 컨테이너 앞뒤 여백 16dp(uncontained 는 앞쪽만), 항목 간격 8dp, 항목 모서리 28dp, 작은 항목 너비 40~56dp.
 * 접근성(m3 carousel accessibility): 컨테이너·항목으로 이동, 항목 활성화, 캐러셀 건너뛰기를 지원한다. 항목은 roving tabindex 라 Tab 한 번으로 건너뛰며,
 * ←/→ 로 항목을 옮기고 Enter·Space 로 활성화한다. 세로 스크롤 페이지에서는 showAll 로 "모두 보기" 버튼(여백 4dp)을 캐러셀 아래에 둔다.
 * [파생] multi-browse·hero 의 크기 변화는 공식 keyline 알고리즘이 아니라, 컨테이너 가장자리에 걸린 항목을 clip-path 로 잘라(최소 40dp) 근사한다.
 */
public final class CarouselElementBuilder extends CustomElementBuilder<CarouselElementBuilder> implements
        HasAriaLabel<HTMLDivElement, CarouselElementBuilder> {
    public static final String CLASS = "sayaya-carousel";
    static final String STYLE_ID = "sayaya-carousel-style";
    static final int PADDING = 16, GAP = 8, SMALL_MIN = 40;

    public static CarouselPrepare carousel() { return new CarouselPrepare(); }
    public static final class CarouselPrepare {
        /** 큰·중간·작은 항목이 함께 보인다(사진 둘러보기) */
        public CarouselElementBuilder multiBrowse() { return new CarouselElementBuilder("multi-browse", 200); }
        /** 큰 항목 하나와 작은 항목이 보인다(강조 콘텐츠) */
        public CarouselElementBuilder hero() { return new CarouselElementBuilder("hero", 320); }
        /** 항목 너비가 고정이고 컨테이너 끝까지 흘러 넘친다 */
        public CarouselElementBuilder uncontained() { return new CarouselElementBuilder("uncontained", 200); }
        /** 큰 항목 하나를 가운데 두고 양옆에 작은 항목 두 개를 보인다(M3 center-aligned hero) */
        public CarouselElementBuilder heroCentered() { return new CarouselElementBuilder("hero centered", 320); }
        /** 화면을 꽉 채운 큰 항목 하나씩 세로로 넘긴다(M3 full-screen: 여백 0, 항목 간격 16dp, snap) */
        public CarouselElementBuilder fullScreen() { return new CarouselElementBuilder("full-screen", 0); }
    }

    private final HTMLElement track;
    private final String layout;
    private int itemWidth;
    private int count = 0;
    private Consumer<Integer> onActivate;
    private final LayoutRetry layoutRetry = new LayoutRetry();
    private boolean centeredOnce = false;

    private CarouselElementBuilder(String layout, int itemWidth) {
        injectStyle();
        this.layout = layout;
        this.itemWidth = itemWidth;
        element().classList.add(CLASS);
        for (String c : layout.split(" ")) element().classList.add(c);
        element().setAttribute("role", "region");
        element().setAttribute("aria-roledescription", "carousel");
        track = div().css("track").element();
        element().appendChild(track);
        track.addEventListener("scroll", e -> mask());
        track.addEventListener("keydown", this::onKey);
        DomGlobal.window.addEventListener("resize", e -> mask());
    }
    @Override public CarouselElementBuilder that() { return this; }

    /** 큰 항목 너비(dp=px). 기본 multi-browse·uncontained 200, hero 320 */
    public CarouselElementBuilder itemWidth(int width) {
        this.itemWidth = width;
        NodeList<Element> items = items();
        for (int i = 0; i < items.length; i++) ((HTMLElement) Js.uncheckedCast(items.item(i))).style.setProperty("width", width + "px");
        mask();
        return this;
    }
    /** 이미지 항목 */
    public CarouselElementBuilder item(String imageUrl, String label) {
        var img = img(imageUrl).css("media").attr("alt", "").attr("loading", "lazy").element();
        return item(img, label);
    }
    /** 임의 콘텐츠 항목 */
    public CarouselElementBuilder item(Element content, String label) {
        int index = count++;
        var inner = div().css("content").add(content).element();
        if (label != null && !label.isEmpty()) inner.appendChild(span().css("label").text(label).element());
        var item = div().css("item").attr("role", "group").attr("aria-roledescription", "slide")
                .attr("tabindex", index == 0 ? "0" : "-1").attr("data-index", String.valueOf(index)).add(inner).element();
        item.style.setProperty("width", itemWidth + "px");
        item.addEventListener("click", e -> activate(index));
        track.appendChild(item);
        relabel(label);
        mask();
        return this;
    }
    public CarouselElementBuilder item(IsElement<? extends Element> content, String label) { return item(content.element(), label); }
    /** 너비를 따로 지정한 항목(M3 uncontained multi-aspect ratio: 16:9, 9:16, 1:1, 3:4 등) */
    public CarouselElementBuilder item(Element content, String label, int width) {
        item(content, label);
        ((HTMLElement) Js.uncheckedCast(items().item(count - 1))).style.setProperty("width", width + "px");
        mask();
        return this;
    }
    public CarouselElementBuilder item(String imageUrl, String label, int width) {
        return item(img(imageUrl).css("media").attr("alt", "").attr("loading", "lazy").element(), label, width);
    }
    /** 세로 스크롤 페이지에서 모든 항목을 보는 "모두 보기" 버튼(캐러셀 아래, 여백 4dp) */
    public CarouselElementBuilder showAll(String text, EventListener handler) {
        var b = button().css("show-all").attr("type", "button").text(text).element();
        b.addEventListener("click", handler);
        element().appendChild(div().css("show-all-row").add(b).element());
        return this;
    }
    /** 항목을 누르거나 Enter·Space 로 활성화할 때(0부터) */
    public CarouselElementBuilder onActivate(Consumer<Integer> cb) { this.onActivate = cb; return this; }
    public HTMLElement track() { return track; }

    private NodeList<Element> items() { return track.querySelectorAll(".item"); }
    private void relabel(String lastLabel) {
        NodeList<Element> items = items();
        for (int i = 0; i < items.length; i++) {
            Element it = items.item(i);
            var l = it.querySelector(".label");
            it.setAttribute("aria-label", (i + 1) + " / " + items.length + (l != null ? ": " + l.textContent : ""));
        }
    }
    private void activate(int index) { focusItem(index); if (onActivate != null) onActivate.accept(index); }
    private void focusItem(int index) {
        NodeList<Element> items = items();
        for (int i = 0; i < items.length; i++) items.item(i).setAttribute("tabindex", i == index ? "0" : "-1");
        HTMLElement it = Js.uncheckedCast(items.item(index));
        it.focus();
        // 보이도록 스크롤(LTR·RTL 모두 브라우저가 계산)
        elemental2.core.Function siv = Js.uncheckedCast(Js.asPropertyMap(it).get("scrollIntoView"));
        boolean centered = element().classList.contains("centered") || element().classList.contains("full-screen");
        jsinterop.base.JsPropertyMap<Object> o = jsinterop.base.JsPropertyMap.of("block", centered ? "center" : "nearest", "inline", centered ? "center" : "nearest");
        siv.call(it, o);
        mask();
    }
    private void onKey(Event e) {
        KeyboardEvent k = Js.uncheckedCast(e);
        Element t = Js.uncheckedCast(e.target);
        if (!t.classList.contains("item")) return;
        int i = Integer.parseInt(t.getAttribute("data-index"));
        switch (k.key) {
            case "ArrowRight": case "ArrowLeft": {
                boolean forward = "ArrowRight".equals(k.key) != Locales.rtl(element()); // RTL 에서는 ← 가 다음 항목
                if (forward && i < count - 1) focusItem(i + 1); else if (!forward && i > 0) focusItem(i - 1);
                e.preventDefault(); break;
            }
            case "ArrowDown": case "ArrowUp": { // full-screen 은 세로로 넘긴다
                if (!element().classList.contains("full-screen")) return;
                if ("ArrowDown".equals(k.key) && i < count - 1) focusItem(i + 1); else if ("ArrowUp".equals(k.key) && i > 0) focusItem(i - 1);
                e.preventDefault(); break;
            }
            case "Home": focusItem(0); e.preventDefault(); break;
            case "End": focusItem(count - 1); e.preventDefault(); break;
            case "Enter": case " ": activate(i); e.preventDefault(); break;
            default:
        }
    }
    /** [파생] 가장자리에 걸린 항목을 잘라 크기 변화를 근사하고, 내용은 절반만큼 반대로 밀어 parallax 를 준다 */
    private void mask() {
        if ("uncontained".equals(layout) || "full-screen".equals(layout)) return;
        double v = track.clientWidth;
        double trackLeft = track.getBoundingClientRect().left + track.clientLeft;
        if (layoutRetry.defer(v, this::mask)) return; // 아직 문서에 붙지 않았다 — 붙을 때까지 다음 프레임에 다시
        if (element().classList.contains("centered") && !centeredOnce && count > 1) { // 처음에는 둘째 항목을 가운데에
            centeredOnce = true;
            HTMLElement second = Js.uncheckedCast(items().item(1));
            track.scrollLeft = second.offsetLeft + second.offsetWidth / 2.0 - v / 2;
        }
        double start = PADDING, end = v - PADDING;
        NodeList<Element> items = items();
        for (int i = 0; i < items.length; i++) {
            HTMLElement it = Js.uncheckedCast(items.item(i));
            double x = it.getBoundingClientRect().left - trackLeft, w = it.offsetWidth; // 화면 좌표 기준이라 RTL 에서도 같다
            double left = Math.max(0, start - x), right = Math.max(0, (x + w) - end);
            double visible = w - left - right;
            if (visible < SMALL_MIN) { // 가장자리에서도 작은 항목 최소 너비만큼은 보인다
                if (left > 0) left = Math.max(0, w - right - SMALL_MIN); else right = Math.max(0, w - left - SMALL_MIN);
                visible = w - left - right;
            }
            it.style.setProperty("clip-path", "inset(0 " + right + "px 0 " + left + "px round var(--md-carousel-item-shape,var(--md-sys-shape-corner-extra-large,28px)))");
            it.setAttribute("data-visible", String.valueOf(Math.round(visible)));
            HTMLElement content = Js.uncheckedCast(it.querySelector(".content"));
            if (content != null) content.style.setProperty("transform", "translateX(" + ((left - right) / 2) + "px)");
        }
    }

    private static void injectStyle() {
        HTMLStyleElement style = newStyle(STYLE_ID);
        if (style == null) return;
        String c = "." + CLASS;
        style.textContent =
            c + "{display:block;box-sizing:border-box}"
          // 앞뒤 여백 16dp, 간격 8dp, 스냅
          + c + " .track{position:relative;display:flex;gap:8px;overflow-x:auto;overflow-y:hidden;padding:8px 16px;scroll-padding:0 16px;"
          + "scroll-snap-type:x mandatory;scrollbar-width:none;overscroll-behavior-x:contain}"
          + c + " .track::-webkit-scrollbar{display:none}"
          + c + ".uncontained .track{padding-inline-end:0;scroll-snap-type:none}" // M3: uncontained 는 일반 스크롤 권장
          + c + ".centered .item{scroll-snap-align:center}"
          // full-screen: 여백 0, 항목 간격 16dp, 세로 snap. [미확인] 항목 모서리는 표에 없어 item shape 변수를 그대로 쓴다
          + c + ".full-screen{height:var(--md-carousel-height,100%)}"
          + c + ".full-screen .track{flex-direction:column;height:100%;box-sizing:border-box;overflow-x:hidden;overflow-y:auto;padding:0;gap:16px;scroll-padding:0;scroll-snap-type:y mandatory}"
          + c + ".full-screen .track::after{display:none}"
          + c + ".full-screen .item{width:100%!important;height:100%;scroll-snap-align:center;scroll-snap-stop:always}"
          + c + " .track::after{content:'';flex:0 0 1px}"
          // 항목 모서리 28dp
          + c + " .item{position:relative;flex:none;height:var(--md-carousel-height,220px);border-radius:var(--md-carousel-item-shape,var(--md-sys-shape-corner-extra-large,28px));overflow:hidden;scroll-snap-align:start;cursor:pointer;"
          + "background:var(--md-sys-color-surface-container-highest,#e6e0e9);outline:none}"
          + c + ".hero .item{height:var(--md-carousel-height,280px)}"
          + c + " .item:focus-visible{box-shadow:inset 0 0 0 3px var(--md-sys-color-secondary,#625b71)}"
          + c + " .content{position:absolute;inset:0;will-change:transform}"
          + c + " .media{width:100%;height:100%;object-fit:cover;display:block}"
          + c + " .label{position:absolute;left:16px;right:16px;bottom:16px;font:500 16px/24px Roboto,system-ui,sans-serif;color:#fff;text-shadow:0 1px 2px rgba(0,0,0,.6);white-space:nowrap;overflow:hidden;text-overflow:ellipsis}"
          + c + " .show-all-row{display:flex;justify-content:center;padding:4px}"
          + c + " .show-all{height:40px;padding:0 12px;border:0;border-radius:var(--md-carousel-show-all-shape,var(--md-sys-shape-corner-full,9999px));background:none;cursor:pointer;font:500 14px/20px Roboto,system-ui,sans-serif;color:var(--md-sys-color-primary,#6750a4)}"
          + "@media (prefers-reduced-motion: reduce){" + c + " .content{transform:none!important}}";
        DomGlobal.document.head.appendChild(style);
    }
}
