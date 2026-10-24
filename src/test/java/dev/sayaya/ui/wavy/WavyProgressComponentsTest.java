package dev.sayaya.ui.wavy;

import elemental2.dom.*;
import jsinterop.base.Js;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.WavyProgressElementBuilder.*;
import static org.jboss.elemento.Elements.div;

/** M3 Expressive wavy linear progress: 토큰 수치(computed style) + ARIA. 메시지 접두어 "wp: ". */
public class WavyProgressComponentsTest {
    private static CSSStyleDeclaration cs(Element e) {
        elemental2.core.Function f = Js.uncheckedCast(Js.asPropertyMap(DomGlobal.window).get("getComputedStyle"));
        return Js.uncheckedCast(f.call(DomGlobal.window, e));
    }
    private static long w(Element e) { return Math.round(e.getBoundingClientRect().width); }
    private static long h(Element e) { return Math.round(e.getBoundingClientRect().height); }
    private static long left(Element e, Element base) { return Math.round(e.getBoundingClientRect().left - base.getBoundingClientRect().left); }
    private static void later(int ms, Runnable r) { DomGlobal.setTimeout(t -> r.run(), ms); }
    private static HTMLElement q(Element root, String sel) { return (HTMLElement) root.querySelector(sel); }
    private static String mask(Element e, String prop) {
        String v = cs(e).getPropertyValue(prop);
        return v == null || v.isEmpty() ? cs(e).getPropertyValue("-webkit-" + prop) : v;
    }

    public static void test() {
        printSectionHeader("M3 Wavy progress indicator");
        printDescription("M3 Expressive 의 물결 모양 선형 진행 표시기입니다. 표현력을 높이고 싶을 때 활성 트랙에 물결을 줍니다. 같은 과정에는 같은 모양을 일관되게 쓰고, 진행률을 알 수 없으면 indeterminate 로 둡니다.");
        printSeparator();
        var section = section("Wavy progress");
        documented(section);

        var half = wavyProgress().value(0.5).ariaLabel("업로드 중").style("width", "400px").element();
        demo(section, "📘 Determinate", "높이 10dp, 진폭 3dp, 파장 40dp, 두께 4dp 의 물결이 진행률만큼 그려지고 위상이 흐릅니다. 남은 구간은 4dp 띄운 평평한 트랙이며 끝에 4dp 점이 있습니다.",
            """
            var upload = wavyProgress().value(0.5).ariaLabel("업로드 중").element();
            """, half);
        var full = wavyProgress().value(1.5).style("width", "400px").element();
        var none = wavyProgress().value(-1).style("width", "400px").element();
        var ind = wavyProgress().indeterminate(true).ariaLabel("불러오는 중").style("width", "400px").element();
        demo(section, "📘 Indeterminate", "진행률을 알 수 없을 때는 파장 20dp 의 물결 구간이 왼쪽에서 오른쪽으로 지나갑니다.",
            """
            wavyProgress().indeterminate(true).ariaLabel("불러오는 중").element();
            """, div().add(ind).element());
        section.appendChild(div().style("position", "absolute").style("left", "-9999px").add(full).add(none).element());

        assertEquals("wp: role progressbar", "progressbar", half.getAttribute("role"));
        assertEquals("wp: aria-valuemin 0", "0", half.getAttribute("aria-valuemin"));
        assertEquals("wp: aria-valuemax 1", "1", half.getAttribute("aria-valuemax"));
        assertEquals("wp: aria-valuenow 0.5", "0.5", half.getAttribute("aria-valuenow"));
        assertEquals("wp: aria-label", "업로드 중", half.getAttribute("aria-label"));
        assertTrue("wp: indeterminate 는 aria-valuenow 없음", !ind.hasAttribute("aria-valuenow"));

        later(400, () -> {
            var active = q(half, ".active"); var track = q(half, ".track"); var stop = q(half, ".stop");
            assertEquals("wp: 높이 10px", 10, (int) h(half));
            assertEquals("wp: 활성 트랙 50%", 200, (int) w(active));
            assertEquals("wp: 활성-트랙 간격 4px", 204, (int) left(track, half));
            assertEquals("wp: 트랙은 끝까지", 196, (int) w(track));
            assertEquals("wp: 트랙 두께 4px", 4, (int) h(track));
            assertEquals("wp: 끝 점 4px", 4, (int) w(stop));
            assertEquals("wp: 끝 점은 오른쪽 끝", 396, (int) left(stop, half));
            assertEquals("wp: 활성 primary", "rgb(103, 80, 164)", cs(active).getPropertyValue("background-color"));
            assertEquals("wp: 트랙 secondary-container", "rgb(232, 222, 248)", cs(track).getPropertyValue("background-color"));
            assertTrue("wp: 물결 마스크", mask(active, "mask-image").contains("svg"));
            assertEquals("wp: 파장 40px·높이 10px", "40px 10px", mask(active, "mask-size"));
            assertEquals("wp: 물결 위상 애니메이션", "sayaya-wave-flow", cs(active).getPropertyValue("animation-name"));
            assertEquals("wp: 1 초과는 100%", 400, (int) w(q(full, ".active")));
            assertEquals("wp: 0 미만은 0", 0, (int) w(q(none, ".active")));
            assertEquals("wp: indeterminate 파장 20px", "20px 10px", mask(q(ind, ".active"), "mask-size"));
            assertEquals("wp: indeterminate 이동 애니메이션", "sayaya-wave-sweep", cs(q(ind, ".active")).getPropertyValue("animation-name"));
            assertEquals("wp: indeterminate 끝 점 없음", "none", cs(q(ind, ".stop")).getPropertyValue("display"));
            assertEquals("wp: indeterminate 트랙 전체", 400, (int) w(q(ind, ".track")));
        });
    }

    /** 가이드라인: circular wavy("Two variants: linear and circular", "Option to apply a wave to the active track") */
    private static void documented(HTMLElement section) {
        var c = wavyProgress().circular().value(0.6).ariaLabel("Downloading").element();
        var ci = wavyProgress().circular().indeterminate(true).ariaLabel("Loading").element();
        demo(section, "📘 Circular wavy progress", "가이드라인: \"Two variants: linear and circular\", \"Option to apply a wave to the active track for use cases that would benefit from increased expressiveness\", \"Use circular indicators for short, indeterminate activities under 5 seconds\". 크기 48dp, 두께 4dp, 진폭 1.6dp, 파장 15dp, 활성-트랙 간격 4dp(CircularProgressIndicatorTokens).",
            """
            wavyProgress().circular().value(0.6).ariaLabel("Downloading").element();
            wavyProgress().circular().indeterminate(true).ariaLabel("Loading").element();
            """, div().style("display", "flex").style("gap", "24px").add(c).add(ci).element());
        later(500, () -> {
            Element active = c.querySelector(".c-active"), track = c.querySelector(".c-track"); // SVG 요소라 HTMLElement 로 캐스트하지 않는다
            assertEquals("wp: circular 크기 48px", 48, (int) w(c));
            assertEquals("wp: circular 높이 48px", 48, (int) h(c));
            assertEquals("wp: circular role progressbar", "progressbar", c.getAttribute("role"));
            assertEquals("wp: circular aria-valuenow", "0.6", c.getAttribute("aria-valuenow"));
            double[] r = radii(active.getAttribute("d"));
            assertTrue("wp: circular 활성 트랙은 물결(반지름 16.4~19.6)", Math.abs(r[0] - 16.4) < 0.15 && Math.abs(r[1] - 19.6) < 0.15);
            double[] rt = radii(track.getAttribute("d"));
            assertTrue("wp: circular 트랙은 평평한 원(반지름 18)", Math.abs(rt[0] - 18) < 0.05 && Math.abs(rt[1] - 18) < 0.05);
            assertEquals("wp: circular 진행률 0.6 만큼 활성", "0.6 1", active.getAttribute("stroke-dasharray"));
            assertTrue("wp: circular 트랙은 간격 뒤에서 시작", track.getAttribute("stroke-dasharray").startsWith("0 0.67"));
            assertEquals("wp: circular 활성 primary", "rgb(103, 80, 164)", cs(active).getPropertyValue("stroke"));
            assertEquals("wp: circular 트랙 secondary-container", "rgb(232, 222, 248)", cs(track).getPropertyValue("stroke"));
            assertEquals("wp: circular 두께 4px", "4px", cs(active).getPropertyValue("stroke-width"));
            assertEquals("wp: circular 둥근 끝", "round", cs(active).getPropertyValue("stroke-linecap"));
            assertEquals("wp: circular 선형 막대 숨김", "none", cs(q(c, ".active")).getPropertyValue("display"));
            assertEquals("wp: circular indeterminate 회전", "sayaya-wave-spin", cs(ci.querySelector("svg")).getPropertyValue("animation-name"));
            assertTrue("wp: circular indeterminate 는 aria-valuenow 없음", !ci.hasAttribute("aria-valuenow"));
        });
    }
    /** path d 의 점들로 중심(24,24)에서의 최소·최대 반지름을 잰다 */
    private static double[] radii(String d) {
        double min = 1e9, max = 0;
        String[] tokens = d.replace("M", " ").replace("L", " ").trim().split("\\s+");
        for (int i = 0; i + 1 < tokens.length; i += 2) {
            double x = Double.parseDouble(tokens[i]) - 24, y = Double.parseDouble(tokens[i + 1]) - 24;
            double r = Math.sqrt(x * x + y * y);
            min = Math.min(min, r); max = Math.max(max, r);
        }
        return new double[]{min, max};
    }
}
