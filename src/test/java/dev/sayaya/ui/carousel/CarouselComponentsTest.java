package dev.sayaya.ui.carousel;

import elemental2.dom.*;
import dev.sayaya.ui.elements.CarouselElementBuilder;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

import java.util.ArrayList;
import java.util.List;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.CarouselElementBuilder.*;
import static org.jboss.elemento.Elements.div;

/** M3 carousel(multi-browse/hero/uncontained): 스펙 수치(computed style) + 접근성 구조 + 키보드. 메시지 접두어 "ca: ". */
public class CarouselComponentsTest {
    private static final String[] COLORS = {"#6750a4", "#625b71", "#7d5260", "#386a20", "#00639b", "#8c4a00"};
    private static final String[] NAMES = {"제주", "부산", "강릉", "전주", "경주", "여수"};
    private static CSSStyleDeclaration cs(Element e) {
        elemental2.core.Function f = Js.uncheckedCast(Js.asPropertyMap(DomGlobal.window).get("getComputedStyle"));
        return Js.uncheckedCast(f.call(DomGlobal.window, e));
    }
    private static long w(Element e) { return Math.round(e.getBoundingClientRect().width); }
    private static void later(int ms, Runnable r) { DomGlobal.setTimeout(t -> r.run(), ms); }
    private static HTMLElement q(Element root, String sel) { return (HTMLElement) root.querySelector(sel); }
    private static HTMLElement item(Element root, int i) { return q(root, ".item[data-index='" + i + "']"); }
    private static void key(String key) {
        JsPropertyMap<Object> m = JsPropertyMap.of();
        m.set("key", key); m.set("bubbles", true); m.set("cancelable", true);
        DomGlobal.document.activeElement.dispatchEvent(new KeyboardEvent("keydown", Js.<KeyboardEventInit>uncheckedCast(m)));
    }
    private static CarouselElementBuilder fill(CarouselElementBuilder b) {
        for (int i = 0; i < COLORS.length; i++) b.item(div().style("width", "100%").style("height", "100%").style("background", COLORS[i]).element(), NAMES[i]);
        return b;
    }

    public static void test() {
        printSectionHeader("M3 Carousel");
        printDescription("이미지·영상 같은 시각 항목을 가로로 넘겨 보는 캐러셀입니다. multi-browse 는 큰·중간·작은 항목이 함께 보이고, hero 는 큰 항목 하나를 강조하며, uncontained 는 고정 너비 항목이 끝까지 흘러 넘칩니다. 항목은 가장자리에 걸리면 작아지고 내용이 살짝 밀리는 parallax 가 있습니다. Tab 한 번으로 건너뛰고 ←/→ 로 항목을 옮기며 Enter 로 엽니다.");
        printSeparator();
        var section = section("Carousel");
        List<Integer> activated = new ArrayList<>();
        int[] showAll = {0};

        var multi = fill(carousel().multiBrowse().itemWidth(200).ariaLabel("추천 여행지")).onActivate(activated::add)
                .showAll("모두 보기", e -> showAll[0]++);
        var mel = multi.element();
        mel.style.setProperty("width", "480px");
        demo(section, "📘 Multi-browse", "앞뒤 여백 16dp, 항목 간격 8dp, 항목 모서리 28dp 입니다. 컨테이너 가장자리에 걸린 항목은 작아지며(최소 40dp) 스크롤에 맞춰 크기가 바뀝니다. 세로 스크롤 페이지에서는 아래에 '모두 보기' 버튼을 둡니다.",
            """
            carousel().multiBrowse().itemWidth(200).ariaLabel("추천 여행지")
                .item("/img/jeju.jpg", "제주")
                .item("/img/busan.jpg", "부산")
                .showAll("모두 보기", e -> openAll())
                .onActivate(index -> open(index))
                .element();
            """, mel);
        var hero = fill(carousel().hero().ariaLabel("오늘의 추천")).element();
        hero.style.setProperty("width", "480px");
        var unc = fill(carousel().uncontained().itemWidth(160).ariaLabel("최근 본 항목")).element();
        unc.style.setProperty("width", "480px");
        demo(section, "📘 Hero / Uncontained", "hero 는 큰 항목(기본 320dp)을 강조합니다. uncontained 는 앞쪽 여백만 두고 항목이 컨테이너 끝까지 흘러 넘칩니다.",
            """
            carousel().hero().ariaLabel("오늘의 추천")...element();
            carousel().uncontained().itemWidth(160).ariaLabel("최근 본 항목")...element();
            """, div().style("display", "flex").style("flex-direction", "column").style("gap", "16px").add(hero).add(unc).element());

        var track = multi.track();
        assertEquals("ca: role region", "region", mel.getAttribute("role"));
        assertEquals("ca: roledescription carousel", "carousel", mel.getAttribute("aria-roledescription"));
        assertEquals("ca: aria-label", "추천 여행지", mel.getAttribute("aria-label"));
        assertEquals("ca: 항목 role group", 6, mel.querySelectorAll("[role=group][aria-roledescription=slide]").length);
        assertEquals("ca: 항목 aria-label", "1 / 6: 제주", item(mel, 0).getAttribute("aria-label"));
        assertEquals("ca: tab 순서는 하나(건너뛰기)", 1, mel.querySelectorAll(".item[tabindex='0']").length);

        later(500, () -> {
            assertEquals("ca: 앞 여백 16px", "16px", cs(track).getPropertyValue("padding-left"));
            assertEquals("ca: 뒤 여백 16px", "16px", cs(track).getPropertyValue("padding-right"));
            assertEquals("ca: uncontained 뒤 여백 없음", "0px", cs(q(unc, ".track")).getPropertyValue("padding-right"));
            assertEquals("ca: 항목 간격 8px", "8px", cs(track).getPropertyValue("column-gap"));
            assertEquals("ca: 스냅", "x mandatory", cs(track).getPropertyValue("scroll-snap-type"));
            assertEquals("ca: 항목 모서리 28px", "28px", cs(item(mel, 0)).getPropertyValue("border-top-left-radius"));
            assertEquals("ca: 항목 너비 200px", 200, (int) w(item(mel, 0)));
            assertEquals("ca: hero 큰 항목 320px", 320, (int) w(item(hero, 0)));
            assertEquals("ca: 첫 항목은 다 보임", "200", item(mel, 0).getAttribute("data-visible"));
            assertEquals("ca: 둘째 항목도 다 보임", "200", item(mel, 1).getAttribute("data-visible"));
            assertEquals("ca: 가장자리 항목은 최소 40px", "40", item(mel, 2).getAttribute("data-visible"));
            assertTrue("ca: 가장자리 항목 clip-path", cs(item(mel, 2)).getPropertyValue("clip-path").startsWith("inset("));
            assertEquals("ca: uncontained 는 자르지 않음", "none", cs(item(unc, 2)).getPropertyValue("clip-path"));
            track.scrollLeft = 208;
            track.dispatchEvent(new Event("scroll"));
            later(300, () -> {
                assertEquals("ca: 스크롤하면 앞쪽 항목이 작아짐", "40", item(mel, 0).getAttribute("data-visible"));
                assertEquals("ca: 스크롤하면 다음 항목이 커짐", "200", item(mel, 2).getAttribute("data-visible"));
                track.scrollLeft = 0;
                track.dispatchEvent(new Event("scroll"));
                item(mel, 0).focus();
                key("ArrowRight");
                assertTrue("ca: → 다음 항목 포커스", DomGlobal.document.activeElement == item(mel, 1));
                assertEquals("ca: tabindex 이동", "0", item(mel, 1).getAttribute("tabindex"));
                key("Enter");
                assertTrue("ca: Enter 로 활성화", activated.contains(1));
                key("End");
                assertTrue("ca: End 마지막 항목", DomGlobal.document.activeElement == item(mel, 5));
                item(mel, 3).click();
                assertTrue("ca: 클릭으로 활성화", activated.contains(3));
                var sa = q(mel, ".show-all-row");
                assertEquals("ca: 모두 보기 여백 4px", "4px", cs(sa).getPropertyValue("padding-top"));
                q(mel, ".show-all").click();
                assertEquals("ca: 모두 보기 동작", 1, showAll[0]);
            });
        });
        documented(section);
    }

    /** 가이드라인 레이아웃: center-aligned hero, full-screen, uncontained multi-aspect ratio, 여백·스크롤 방식 */
    private static void documented(HTMLElement section) {
        var centered = fill(carousel().heroCentered().ariaLabel("Featured")).element();
        centered.style.setProperty("width", "480px");
        demo(section, "📘 Center-aligned hero", "가이드라인: \"The center-aligned hero layout shows at least one large item and two small items. The large item is centered.\" 큰 항목이 가운데에 snap 되고 양옆 작은 항목은 40–56dp 로 보입니다.",
            """
            carousel().heroCentered().ariaLabel("Featured")
                .item("/img/a.jpg", "A").item("/img/b.jpg", "B").item("/img/c.jpg", "C")
                .element();
            """, centered);
        var full = fill(carousel().fullScreen().ariaLabel("Video feed")).element();
        full.style.setProperty("width", "320px");
        full.style.setProperty("height", "400px");
        demo(section, "📘 Full-screen", "가이드라인: \"The full-screen carousel layout shows one edge-to-edge large item at a time and scrolls vertically.\" 여백 0, 항목 간격 16dp 이며, \"Avoid scrolling freely on full-screen carousels\" 에 따라 한 번에 한 항목씩 snap 합니다. ↑/↓ 로 넘깁니다.",
            """
            carousel().fullScreen().ariaLabel("Video feed")
                .style("--md-carousel-height", "100vh")
                .item(video1, "Clip 1").item(video2, "Clip 2")
                .element();
            """, full);
        var multi = carousel().uncontained().ariaLabel("Gallery")
                .item(div().style("background", "#6750a4").style("height", "100%").element(), "16:9", 391)
                .item(div().style("background", "#625b71").style("height", "100%").element(), "9:16", 124)
                .item(div().style("background", "#7d5260").style("height", "100%").element(), "1:1", 220).element();
        multi.style.setProperty("width", "480px");
        demo(section, "📘 Uncontained multi-aspect ratio", "가이드라인: \"Uncontained multi-aspect ratio carousels only have leading padding, with 8dp of padding between items.\" 항목마다 너비(16:9, 9:16, 1:1)를 지정합니다. uncontained 는 snap 없이 일반 스크롤합니다.",
            """
            carousel().uncontained()
                .item("/img/wide.jpg", "16:9", 391)
                .item("/img/tall.jpg", "9:16", 124)
                .item("/img/square.jpg", "1:1", 220)
                .element();
            """, multi);

        later(1500, () -> {
            var ct = q(centered, ".track");
            assertEquals("ca: 위 여백 8px", "8px", cs(ct).getPropertyValue("padding-top"));
            assertEquals("ca: 아래 여백 8px", "8px", cs(ct).getPropertyValue("padding-bottom"));
            var big = item(centered, 1);
            double mid = ct.getBoundingClientRect().left + ct.clientWidth / 2.0;
            double bigMid = big.getBoundingClientRect().left + big.getBoundingClientRect().width / 2;
            assertTrue("ca: center-aligned 큰 항목이 가운데", Math.abs(mid - bigMid) <= 2);
            assertEquals("ca: center-aligned 큰 항목 320px", "320", big.getAttribute("data-visible"));
            assertEquals("ca: center-aligned 왼쪽 작은 항목 56px", "56", item(centered, 0).getAttribute("data-visible"));
            assertEquals("ca: center-aligned 오른쪽 작은 항목 56px", "56", item(centered, 2).getAttribute("data-visible"));
            assertEquals("ca: center-aligned snap center", "center", cs(big).getPropertyValue("scroll-snap-align"));

            var ft = q(full, ".track");
            assertEquals("ca: full-screen 세로 배치", "column", cs(ft).getPropertyValue("flex-direction"));
            assertEquals("ca: full-screen 여백 0", "0px", cs(ft).getPropertyValue("padding-left"));
            assertEquals("ca: full-screen 항목 간격 16px", "16px", cs(ft).getPropertyValue("row-gap"));
            assertEquals("ca: full-screen 세로 snap", "y mandatory", cs(ft).getPropertyValue("scroll-snap-type"));
            assertEquals("ca: full-screen 한 항목씩(snap-stop always)", "always", cs(item(full, 0)).getPropertyValue("scroll-snap-stop"));
            assertEquals("ca: full-screen 항목이 꽉 참(너비)", 320, (int) w(item(full, 0)));
            assertEquals("ca: full-screen 항목이 꽉 참(높이)", 400, (int) Math.round(item(full, 0).getBoundingClientRect().height));
            item(full, 0).focus();
            key("ArrowDown");
            assertTrue("ca: full-screen ↓ 로 다음 항목", DomGlobal.document.activeElement == item(full, 1));

            var mt = q(multi, ".track");
            assertEquals("ca: uncontained snap 없음", "none", cs(mt).getPropertyValue("scroll-snap-type"));
            assertTrue("ca: multi-aspect 항목 너비", w(item(multi, 0)) == 391 && w(item(multi, 1)) == 124 && w(item(multi, 2)) == 220);
            assertTrue("ca: multi-aspect 앞 여백만", "16px".equals(cs(mt).getPropertyValue("padding-left")) && "0px".equals(cs(mt).getPropertyValue("padding-right")));
            assertEquals("ca: multi-aspect 항목 간격 8px", "8px", cs(mt).getPropertyValue("column-gap"));
        });
    }
}
