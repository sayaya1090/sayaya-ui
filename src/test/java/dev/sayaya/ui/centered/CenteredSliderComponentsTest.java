package dev.sayaya.ui.centered;

import elemental2.dom.*;
import jsinterop.base.Js;

import java.util.ArrayList;
import java.util.List;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.CenteredSliderElementBuilder.*;

/** M3 Expressive centered slider: 토큰 수치(computed style·배치) + 네이티브 range 동작. 메시지 접두어 "cs: ". */
public class CenteredSliderComponentsTest {
    private static CSSStyleDeclaration cs(Element e) {
        elemental2.core.Function f = Js.uncheckedCast(Js.asPropertyMap(DomGlobal.window).get("getComputedStyle"));
        return Js.uncheckedCast(f.call(DomGlobal.window, e));
    }
    private static long w(Element e) { return Math.round(e.getBoundingClientRect().width); }
    private static long h(Element e) { return Math.round(e.getBoundingClientRect().height); }
    private static long left(Element e, Element base) { return Math.round(e.getBoundingClientRect().left - base.getBoundingClientRect().left); }
    private static void later(int ms, Runnable r) { DomGlobal.setTimeout(t -> r.run(), ms); }
    private static HTMLElement q(Element root, String sel) { return (HTMLElement) root.querySelector(sel); }

    public static void test() {
        printSectionHeader("M3 Centered slider");
        printDescription("음수와 양수 범위에서 값을 고르는 가운데 슬라이더입니다. 0(기본값)이 가운데에 있을 때 쓰며, 활성 트랙은 가운데에서 현재 값까지 그려집니다. 양 끝에 정지 표시가 있고 inset 아이콘은 쓰지 않습니다. 키보드 ←/→·Home/End 로 값을 바꿀 수 있습니다.");
        printSeparator();
        var section = section("Centered slider");
        List<Double> values = new ArrayList<>();

        var builder = centeredSlider().range(-10, 10).value(0).step(1).ariaLabel("좌우 균형").onInput(values::add);
        var el = builder.element();
        el.style.setProperty("width", "400px");
        demo(section, "📘 Centered slider", "트랙 높이 16dp(모서리 full), 활성 트랙 primary, 비활성 트랙 secondary container, 핸들 4×44dp 이며 핸들 양옆에 6dp 간격이 있습니다.",
            """
            centeredSlider().range(-10, 10).value(0).step(1).ariaLabel("좌우 균형")
                .onInput(v -> setBalance(v))
                .element();
            """, el);
        var disabled = centeredSlider().range(-5, 5).value(2).disabled(true).ariaLabel("비활성").element();
        disabled.style.setProperty("width", "240px");
        demo(section, "📘 Disabled", "disabled(true)는 조작을 막고 38% 불투명도로 표시합니다.",
            """
            centeredSlider().range(-5, 5).value(2).disabled(true).element();
            """, disabled);

        var input = builder.input();
        assertEquals("cs: 네이티브 range", "range", input.type);
        assertEquals("cs: 최솟값", "-10", input.min);
        assertEquals("cs: 최댓값", "10", input.max);
        assertEquals("cs: 초깃값 0", "0", input.value);
        assertEquals("cs: aria-label", "좌우 균형", input.getAttribute("aria-label"));
        assertEquals("cs: 정지 표시 양 끝 2개", 2, el.querySelectorAll(".stop").length);

        later(400, () -> {
            var handle = q(el, ".handle"); var active = q(el, ".active");
            var start = q(el, ".inactive.start"); var end = q(el, ".inactive.end");
            assertEquals("cs: 높이 44px", 44, (int) h(el));
            assertEquals("cs: 트랙 높이 16px", 16, (int) h(start));
            assertEquals("cs: 트랙 모서리 full", "9999px", cs(start).getPropertyValue("border-top-left-radius"));
            assertEquals("cs: 비활성 트랙 secondary-container", "rgb(232, 222, 248)", cs(start).getPropertyValue("background-color"));
            assertEquals("cs: 핸들 4px", 4, (int) w(handle));
            assertEquals("cs: 핸들 높이 44px", 44, (int) h(handle));
            assertEquals("cs: 핸들 primary", "rgb(103, 80, 164)", cs(handle).getPropertyValue("background-color"));
            assertEquals("cs: 0 이면 핸들 가운데", 198, (int) left(handle, el));
            assertEquals("cs: 0 이면 활성 트랙 없음", 0, (int) w(active));
            builder.value(5);
            assertEquals("cs: +5 핸들 75%", 298, (int) left(handle, el));
            assertEquals("cs: +5 활성 트랙은 가운데부터", 200, (int) left(active, el));
            assertEquals("cs: +5 활성 트랙은 핸들 6px 앞까지", 92, (int) w(active));
            assertEquals("cs: 활성 트랙 primary", "rgb(103, 80, 164)", cs(active).getPropertyValue("background-color"));
            assertEquals("cs: +5 오른쪽 비활성은 핸들 6px 뒤부터", 308, (int) left(end, el));
            assertEquals("cs: +5 오른쪽 비활성 너비", 92, (int) w(end));
            input.value = "-5";
            input.dispatchEvent(new Event("input"));
            assertEquals("cs: -5 활성 트랙은 핸들 6px 뒤부터", 108, (int) left(active, el));
            assertEquals("cs: -5 활성 트랙은 가운데까지", 92, (int) w(active));
            assertEquals("cs: -5 왼쪽 비활성 너비", 92, (int) w(start));
            assertTrue("cs: onInput 값 전달", values.contains(-5.0));
            assertEquals("cs: value() 읽기", -5.0, builder.value());
            var dInput = (HTMLInputElement) disabled.querySelector("input");
            assertTrue("cs: disabled 입력 막음", dInput.disabled);
            assertEquals("cs: disabled 불투명도 0.38", "0.38", cs(disabled).getPropertyValue("opacity"));
        });
    }
}
