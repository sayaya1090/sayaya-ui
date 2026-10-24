package dev.sayaya.ui.loading;

import elemental2.dom.*;
import jsinterop.base.Js;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.ButtonElementBuilder.button;
import static dev.sayaya.ui.elements.LoadingIndicatorElementBuilder.*;
import static org.jboss.elemento.Elements.div;

/** M3 loading indicator: 스펙 수치(computed style) + 역할·라벨 + shape morph 애니메이션. 메시지 접두어 "li: ". */
public class LoadingIndicatorComponentsTest {
    private static CSSStyleDeclaration cs(Element e) {
        elemental2.core.Function f = Js.uncheckedCast(Js.asPropertyMap(DomGlobal.window).get("getComputedStyle"));
        return Js.uncheckedCast(f.call(DomGlobal.window, e));
    }
    private static HTMLElement active(HTMLElement host) { return (HTMLElement) host.querySelector("." + ACTIVE_CLASS); }
    private static long w(Element e) { return e == null ? -1 : Math.round(e.getBoundingClientRect().width); }
    private static long h(Element e) { return e == null ? -1 : Math.round(e.getBoundingClientRect().height); }
    /** 레이아웃 너비(transform 회전 영향 없음) */
    private static int ow(HTMLElement e) { return e == null ? -1 : e.offsetWidth; }
    private static void later(int ms, Runnable r) { DomGlobal.setTimeout(t -> r.run(), ms); }

    public static void test() {
        printSectionHeader("M3 Loading indicator");
        printDescription("200ms~5s 의 짧고 진행률을 알 수 없는 대기를 보여 주는 지시자입니다. 7개 M3 도형이 반복해서 모양을 바꾸며 회전합니다. 5초가 넘는 작업에는 progress indicator 를 쓰고, 로딩 지시자를 진행률 지시자로 바꾸지 않습니다.");
        printSeparator();
        var section = section("Loading indicator");

        var basic = loadingIndicator().ariaLabel("뉴스 기사 불러오는 중").element();
        demo(section, "📘 Loading indicator", "기본 크기 48dp, 도형 38dp 입니다. progressbar 역할을 가지며, ariaLabel 로 무엇을 불러오는지 적어야 합니다. 페이지나 컨테이너를 불러올 때는 가운데에 둡니다.",
            """
            var loading = loadingIndicator().ariaLabel("뉴스 기사 불러오는 중").element();
            """, basic);

        var contained = loadingIndicator().contained(true).ariaLabel("새로 고침 중").element();
        var overImage = div().style("display", "flex").style("align-items", "center").style("justify-content", "center")
                .style("width", "240px").style("height", "120px").style("border-radius", "12px")
                .style("background", "repeating-linear-gradient(45deg,#cfd8dc 0 12px,#eceff1 12px 24px)").add(contained).element();
        demo(section, "📘 Contained loading indicator", "다른 콘텐츠 위에 놓일 때 원형 컨테이너(primary container)로 대비를 높입니다. 컨테이너가 있으면 지시자 색이 on-primary-container 로 바뀝니다. pull-to-refresh 에도 contained 를 씁니다.",
            """
            var refreshing = loadingIndicator().contained(true).ariaLabel("새로 고침 중").element();
            """, overImage);

        var small = loadingIndicator().size(24).ariaLabel("작게").element();
        var large = loadingIndicator().size(240).ariaLabel("크게").element();
        var tooSmall = loadingIndicator().size(10).element();
        var tooLarge = loadingIndicator().size(300).element();
        var sizes = div().style("display", "flex").style("align-items", "center").style("gap", "24px").add(small).add(large).element();
        demo(section, "📘 크기", "크기는 24dp~240dp 사이에서 바꿀 수 있고 컨테이너와 도형의 비율은 그대로입니다. 범위를 벗어난 값은 24/240 으로 제한됩니다. 아주 큰 크기는 데스크톱 같은 넓은 창에만 씁니다.",
            """
            loadingIndicator().size(24).ariaLabel("작게").element();
            loadingIndicator().size(240).ariaLabel("크게").element();
            """, sizes);

        var inButton = button().filled().text("확인 중")
                .add(loadingIndicator().size(24).style("--md-loading-indicator-active-indicator-color", "var(--md-sys-color-on-primary)")
                        .ariaLabel("양식 확인 중").attr("slot", "icon"))
                .element();
        demo(section, "📘 버튼 안의 loading indicator", "몇 초 걸리는 동작의 버튼에 넣어 진행 중임을 보여 줍니다. 버튼 색과 3:1 이상 대비가 나도록 지시자 색을 --md-loading-indicator-active-indicator-color 로 맞춥니다.",
            """
            button().filled().text("확인 중")
                .add(loadingIndicator().size(24)
                    .style("--md-loading-indicator-active-indicator-color", "var(--md-sys-color-on-primary)")
                    .ariaLabel("양식 확인 중").attr("slot", "icon"))
                .element();
            """, inButton);
        var hidden = div().style("position", "absolute").style("left", "-9999px").add(tooSmall).add(tooLarge).element();
        section.appendChild(hidden);

        assertEquals("li: 태그명 div", "DIV", basic.tagName);
        assertEquals("li: role progressbar", "progressbar", basic.getAttribute("role"));
        assertEquals("li: aria-label 설정", "뉴스 기사 불러오는 중", basic.getAttribute("aria-label"));
        assertEquals("li: 기본 aria-label", "Loading", tooSmall.getAttribute("aria-label"));
        assertEquals("li: 지시자는 aria-hidden", "true", active(basic).getAttribute("aria-hidden"));
        assertEquals("li: 스타일시트 한 번만 주입", 1, DomGlobal.document.querySelectorAll("style#sayaya-loading-indicator-style").length);

        later(300, () -> {
            assertEquals("li: 기본 크기 48px", 48, (int) w(basic));
            assertEquals("li: 기본 높이 48px", 48, (int) h(basic));
            assertEquals("li: 도형 38px", 38, ow(active(basic)));
            assertEquals("li: 기본 컨테이너 없음(투명)", "rgba(0, 0, 0, 0)", cs(basic).getPropertyValue("background-color"));
            assertEquals("li: 지시자 색 primary", "rgb(103, 80, 164)", cs(active(basic)).getPropertyValue("background-color"));
            assertEquals("li: contained 컨테이너 primary-container", "rgb(234, 221, 255)", cs(contained).getPropertyValue("background-color"));
            assertEquals("li: contained 원형", "50%", cs(contained).getPropertyValue("border-top-left-radius"));
            assertEquals("li: contained 지시자 on-primary-container", "rgb(33, 0, 93)", cs(active(contained)).getPropertyValue("background-color"));
            assertEquals("li: contained 크기 48px", 48, (int) w(contained));
            assertEquals("li: 크기 24px", 24, (int) w(small));
            assertEquals("li: 크기 240px", 240, (int) w(large));
            assertEquals("li: 240px 일 때 도형 190px(비율 유지)", 190, ow(active(large)));
            assertEquals("li: 24 미만은 24px 로 제한", 24, (int) w(tooSmall));
            assertEquals("li: 240 초과는 240px 로 제한", 240, (int) w(tooLarge));
            var anim = cs(active(basic)).getPropertyValue("animation-name");
            assertTrue("li: shape morph·회전 애니메이션", anim.contains("sayaya-li-morph") && anim.contains("sayaya-li-spin"));
            assertEquals("li: 무한 반복", "infinite, infinite", cs(active(basic)).getPropertyValue("animation-iteration-count"));
            String clip1 = cs(active(basic)).getPropertyValue("clip-path");
            assertTrue("li: 도형은 clip-path polygon", clip1.startsWith("polygon("));
            later(500, () -> {
                String clip2 = cs(active(basic)).getPropertyValue("clip-path");
                assertTrue("li: 시간이 지나면 도형이 바뀜(morph)", clip2.startsWith("polygon(") && !clip2.equals(clip1));
            });
        });
    }
}
