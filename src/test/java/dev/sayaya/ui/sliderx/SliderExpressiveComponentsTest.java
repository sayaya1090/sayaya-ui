package dev.sayaya.ui.sliderx;

import dev.sayaya.ui.elements.SliderElementBuilder.Size;
import elemental2.dom.*;
import jsinterop.base.Js;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.SliderElementBuilder.slider;
import static org.jboss.elemento.Elements.div;

/** M3 Expressive slider 보완: 크기 XS~XL, 세로, inset icon (md-slider 공개 CSS 변수 + 문서 수준 스타일). 메시지 접두어 "sx: ". */
public class SliderExpressiveComponentsTest {
    private static CSSStyleDeclaration cs(Element e) { return cs(e, null); }
    private static CSSStyleDeclaration cs(Element e, String pseudo) {
        elemental2.core.Function f = Js.uncheckedCast(Js.asPropertyMap(DomGlobal.window).get("getComputedStyle"));
        return Js.uncheckedCast(f.call(DomGlobal.window, e, pseudo));
    }
    private static Element sr(Element host, String sel) {
        ShadowRoot root = Js.uncheckedCast(Js.asPropertyMap(host).get("shadowRoot"));
        return root == null ? null : root.querySelector(sel);
    }
    private static long h(Element e) { return Math.round(e.getBoundingClientRect().height); }
    private static long w(Element e) { return Math.round(e.getBoundingClientRect().width); }
    private static void later(int ms, Runnable r) { DomGlobal.setTimeout(t -> r.run(), ms); }

    public static void test() {
        printSectionHeader("M3 Expressive Slider");
        printDescription("가이드라인: \"Has five sizes, vertical and horizontal orientation, and an optional inset icon\". Material Web md-slider 를 감싼 slider() 에 size(XS~XL)·vertical·insetIcon 을 더했습니다. stops 는 ticks(step), value indicator 는 labeled() 입니다.");
        printSeparator();
        var section = section("Slider (Expressive)");

        Size[] sizes = Size.values();
        var sized = new HTMLElement[sizes.length];
        var column = div().style("display", "flex").style("flex-direction", "column").style("gap", "12px").style("width", "320px").element();
        for (int i = 0; i < sizes.length; i++) {
            sized[i] = slider().size(sizes[i]).value(60).element();
            column.appendChild(sized[i]);
        }
        demo(section, "📘 Sizes XS–XL", "가이드라인 표(\"Padding and size measurements for XS, S, M, L, and XL sliders\"): Track height 16/24/40/56/96dp, Handle height 44/44/52/68/108dp, Handle width 4dp, Track shape 8/8/12/16/28dp.",
            """
            slider().size(Size.XS).value(60).element();   // 기본(16dp 트랙)
            slider().size(Size.M).value(60).element();    // 40dp 트랙, inset icon 사용 가능
            slider().size(Size.XL).value(60).element();
            """, column);

        var vLow = slider().vertical(true).value(20).element();
        var vHigh = slider().vertical(true).size(Size.M).value(80).element();
        var vStops = slider().ticks(10).vertical(true).value(50).element();
        var vInput = slider().vertical(true).value(50).element();
        vInput.id = "sx-v-input"; // Playwright 실제 입력(키·포인터) 검사용
        demo(section, "📘 Vertical standard slider", "가이드라인 그림 \"Vertical standard slider with an end stop indicator\". 아래가 최솟값입니다. range slider 는 \"Because of the additional cognitive load of a range slider, avoid using it in vertical orientation\" 에 따라 세로를 제공하지 않습니다.",
            """
            slider().vertical(true).value(20).element();
            slider().ticks(10).vertical(true).value(50).element();   // stops 와 함께
            """, div().style("display", "flex").style("gap", "24px").style("align-items", "flex-start").add(vLow).add(vHigh).add(vStops).add(vInput).element());

        var volHigh = slider().size(Size.M).insetIcon("volume_up").value(70).element();
        var volLow = slider().size(Size.M).insetIcon("volume_down").value(5).element();
        var xsIcon = slider().size(Size.XS).insetIcon("volume_up").value(70).element();
        var col2 = div().style("display", "flex").style("flex-direction", "column").style("gap", "12px").style("width", "320px").add(volHigh).add(volLow).add(xsIcon).element();
        demo(section, "📘 Inset icon", "가이드라인: \"Inset icons change placement based on the handle\", \"Don't use an inset icon with sliders that have track thicknesses under 40dp\", \"Don't use an inset icon on a centered slider / range slider\". standard slider 의 M·L·XL 에서만 보입니다.",
            """
            slider().size(Size.M).insetIcon("volume_up").value(70).element();
            """, col2);

        later(1500, () -> {
            var trackXS = sr(sized[0], ".track"); var nubXS = sr(sized[0], ".handleNub");
            assertEquals("sx: XS 트랙 높이 16px", "16px", trackXS == null ? "(없음)" : cs(trackXS, "::before").getPropertyValue("height"));
            assertEquals("sx: XS 트랙 모서리 8px", "8px", trackXS == null ? "(없음)" : cs(trackXS, "::before").getPropertyValue("border-top-left-radius"));
            assertEquals("sx: XS 핸들 높이 44px", "44px", nubXS == null ? "(없음)" : cs(nubXS).getPropertyValue("height"));
            assertEquals("sx: XS 핸들 너비 4px", "4px", nubXS == null ? "(없음)" : cs(nubXS).getPropertyValue("width"));
            assertEquals("sx: XS 핸들 높이만큼 공간 확보 44px", 44, (int) h(sized[0]));
            var trackS = sr(sized[1], ".track"); var nubS = sr(sized[1], ".handleNub");
            assertEquals("sx: S 트랙 높이 24px", "24px", trackS == null ? "(없음)" : cs(trackS, "::before").getPropertyValue("height"));
            assertEquals("sx: S 트랙 모서리 8px", "8px", trackS == null ? "(없음)" : cs(trackS, "::before").getPropertyValue("border-top-left-radius"));
            assertEquals("sx: S 핸들 높이 44px", "44px", nubS == null ? "(없음)" : cs(nubS).getPropertyValue("height"));
            assertEquals("sx: S 핸들 너비 4px", "4px", nubS == null ? "(없음)" : cs(nubS).getPropertyValue("width"));
            assertEquals("sx: S 핸들 높이만큼 공간 확보 44px", 44, (int) h(sized[1]));
            var trackM = sr(sized[2], ".track"); var nubM = sr(sized[2], ".handleNub");
            assertEquals("sx: M 트랙 높이 40px", "40px", trackM == null ? "(없음)" : cs(trackM, "::before").getPropertyValue("height"));
            assertEquals("sx: M 트랙 모서리 12px", "12px", trackM == null ? "(없음)" : cs(trackM, "::before").getPropertyValue("border-top-left-radius"));
            assertEquals("sx: M 핸들 높이 52px", "52px", nubM == null ? "(없음)" : cs(nubM).getPropertyValue("height"));
            assertEquals("sx: M 핸들 너비 4px", "4px", nubM == null ? "(없음)" : cs(nubM).getPropertyValue("width"));
            assertEquals("sx: M 핸들 높이만큼 공간 확보 52px", 52, (int) h(sized[2]));
            var trackL = sr(sized[3], ".track"); var nubL = sr(sized[3], ".handleNub");
            assertEquals("sx: L 트랙 높이 56px", "56px", trackL == null ? "(없음)" : cs(trackL, "::before").getPropertyValue("height"));
            assertEquals("sx: L 트랙 모서리 16px", "16px", trackL == null ? "(없음)" : cs(trackL, "::before").getPropertyValue("border-top-left-radius"));
            assertEquals("sx: L 핸들 높이 68px", "68px", nubL == null ? "(없음)" : cs(nubL).getPropertyValue("height"));
            assertEquals("sx: L 핸들 너비 4px", "4px", nubL == null ? "(없음)" : cs(nubL).getPropertyValue("width"));
            assertEquals("sx: L 핸들 높이만큼 공간 확보 68px", 68, (int) h(sized[3]));
            var trackXL = sr(sized[4], ".track"); var nubXL = sr(sized[4], ".handleNub");
            assertEquals("sx: XL 트랙 높이 96px", "96px", trackXL == null ? "(없음)" : cs(trackXL, "::before").getPropertyValue("height"));
            assertEquals("sx: XL 트랙 모서리 28px", "28px", trackXL == null ? "(없음)" : cs(trackXL, "::before").getPropertyValue("border-top-left-radius"));
            assertEquals("sx: XL 핸들 높이 108px", "108px", nubXL == null ? "(없음)" : cs(nubXL).getPropertyValue("height"));
            assertEquals("sx: XL 핸들 너비 4px", "4px", nubXL == null ? "(없음)" : cs(nubXL).getPropertyValue("width"));
            assertEquals("sx: XL 핸들 높이만큼 공간 확보 108px", 108, (int) h(sized[4]));
            assertTrue("sx: 세로는 세로로 긴 박스", h(vLow) >= 190 && w(vLow) <= 60);
            assertTrue("sx: 세로 회전 -90°", cs(vLow).getPropertyValue("transform").contains(", -1, 1,"));
            var nubLow = sr(vLow, ".handleNub"); var nubHigh = sr(vHigh, ".handleNub");
            assertTrue("sx: 세로는 아래가 최솟값(80 이 20 보다 위)", nubHigh.getBoundingClientRect().top < nubLow.getBoundingClientRect().top);
            assertTrue("sx: 세로 stops 함께 사용", Js.isTruthy(Js.asPropertyMap(vStops).get("ticks")) && vStops.hasAttribute("data-vertical"));

            assertEquals("sx: inset icon 이름", "volume_up", volHigh.getAttribute("data-inset-icon"));
            assertTrue("sx: inset icon 표시", cs(volHigh, "::before").getPropertyValue("content").contains("volume_up"));
            assertEquals("sx: M 크기 inset icon 24px", "24px", cs(volHigh, "::before").getPropertyValue("font-size"));
            assertEquals("sx: 값이 크면 활성 트랙 안", "before", volHigh.getAttribute("data-icon-side"));
            assertEquals("sx: 활성 트랙 안 아이콘 on-primary", "rgb(255, 255, 255)", cs(volHigh, "::before").getPropertyValue("color"));
            assertEquals("sx: 값이 작으면 핸들 뒤로", "after", volLow.getAttribute("data-icon-side"));
            assertEquals("sx: 비활성 트랙 아이콘 on-secondary-container", "rgb(29, 25, 43)", cs(volLow, "::before").getPropertyValue("color"));
            double iconLeft = parsePx(cs(volLow, "::before").getPropertyValue("left")) + volLow.getBoundingClientRect().left;
            var lowNub = sr(volLow, ".handleNub");
            assertTrue("sx: 핸들 뒤 아이콘은 핸들 오른쪽", iconLeft > lowNub.getBoundingClientRect().right);
            assertEquals("sx: 40dp 미만(XS)은 inset icon 숨김", "none", cs(xsIcon, "::before").getPropertyValue("display"));
            Js.asPropertyMap(volHigh).set("value", 3);
            volHigh.dispatchEvent(new Event("input"));
            assertEquals("sx: 값이 바뀌면 아이콘 위치도 바뀜", "after", volHigh.getAttribute("data-icon-side"));
        });
    }
    private static double parsePx(String v) { try { return Double.parseDouble(v.replace("px", "").trim()); } catch (Exception e) { return -1; } }
}
