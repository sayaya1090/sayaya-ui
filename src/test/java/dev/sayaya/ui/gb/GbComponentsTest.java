package dev.sayaya.ui.gb;

import elemental2.core.Function;
import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLElement;
import jsinterop.base.Js;

import java.util.function.BooleanSupplier;

import static dev.sayaya.ui.TestHelper.*;
import static dev.sayaya.ui.elements.GbElementBuilder.*;
import static org.jboss.elemento.Elements.body;
import static org.jboss.elemento.Elements.button;
import static org.jboss.elemento.Elements.div;

/**
 * Material Web 2.5.0 labs/gb(md-gb-*) 16종 검증.
 * 즉시: 태그명/속성 설정, 2초 후: 커스텀 엘리먼트 정의·업그레이드(shadowRoot) 및 업그레이드 후 속성 유지.
 */
public class GbComponentsTest {
    public static void test() {
        printSectionHeader("Material Web 2.5.0 labs/gb (Expressive) 컴포넌트");
        printDescription("Material Web 2.5.0 labs/gb 의 Expressive 컴포넌트 16종(md-gb-*)입니다. gb() 빌더로 생성하며, labs 단계라 이후 버전에서 API가 바뀔 수 있습니다.");
        printSeparator();
        var section = section("Expressive 컴포넌트 (gb)");

        var badge = gb().badge().value(3).element();
        check(section, "📘 Badge (md-gb-badge)", "숫자나 짧은 텍스트로 알림 개수를 표시합니다.", "var badge = gb().badge().value(3).element();", badge, "md-gb-badge", () -> "3".equals(badge.textContent));

        var btn = gb().button().color(ButtonColor.Tonal).size(Size.LG).square(true).text("저장").element();
        check(section, "📘 Button (md-gb-button)", "Expressive 버튼. color(filled/elevated/tonal/outlined/text)와 size(xs~xl), square 모양을 지정합니다.", "var btn = gb().button().color(ButtonColor.Tonal).size(Size.LG).square(true).text(\"저장\").element();", btn, "md-gb-button", () -> "tonal".equals(btn.color) && "lg".equals(btn.size) && btn.square);

        var card = gb().card().color(CardColor.Outlined).interactive(true).add(div().text("Card")).element();
        check(section, "📘 Card (md-gb-card)", "color(elevated/filled/outlined)와 interactive(클릭 가능) 카드입니다.", "var card = gb().card().color(CardColor.Outlined).interactive(true).add(div().text(\"Card\")).element();", card, "md-gb-card", () -> "outlined".equals(card.color) && card.interactive);

        var cb = gb().checkbox().select(true).required(true).name("agree").element();
        check(section, "📘 Checkbox (md-gb-checkbox)", "폼과 연동되는 체크박스입니다. name/required 를 지원합니다.", "var cb = gb().checkbox().select(true).required(true).name(\"agree\").element();", cb, "md-gb-checkbox", () -> cb.checked && cb.required);

        var divider = gb().divider().vertical(true).element();
        check(section, "📘 Divider (md-gb-divider)", "구분선입니다. vertical(true)로 세로 구분선을 만듭니다.", "var divider = gb().divider().vertical(true).element();", divider, "md-gb-divider", () -> divider.vertical);

        var fab = gb().fab().color(FabColor.TertiaryContainer).size(FabSize.LG).icon("add").ariaLabel("추가").element();
        check(section, "📘 FAB (md-gb-fab)", "Expressive FAB. color(primary~tertiary-container)와 size(default/md/lg)를 지정합니다.", "var fab = gb().fab().color(FabColor.TertiaryContainer).size(FabSize.LG).icon(\"add\").ariaLabel(\"추가\").element();", fab, "md-gb-fab", () -> "tertiary-container".equals(fab.color) && "lg".equals(fab.size));

        var icon = gb().icon("favorite").element();
        check(section, "📘 Icon (md-gb-icon)", "Material Symbols 아이콘입니다. 페이지에서 Material Symbols Outlined 폰트를 로드해야 합니다.", "var icon = gb().icon(\"favorite\").element();", icon, "md-gb-icon", () -> "favorite".equals(icon.textContent));
        DomGlobal.setTimeout(ignore -> {
            elemental2.core.Function gcs = Js.uncheckedCast(Js.asPropertyMap(DomGlobal.window).get("getComputedStyle"));
            elemental2.dom.CSSStyleDeclaration st = Js.uncheckedCast(gcs.call(DomGlobal.window, icon));
            String family = st.getPropertyValue("font-family");
            assertTrue("gb: md-gb-icon 아이콘 폰트(Material Symbols Outlined) 적용 [" + family + "]", family.contains("Material Symbols Outlined"));
        }, 2000);

        var ib = gb().iconButton().color(IconButtonColor.Outlined).width(IconButtonWidth.Wide).icon("settings").ariaLabel("설정").element();
        check(section, "📘 Icon button (md-gb-icon-button)", "아이콘 버튼. color, size, width(narrow/wide), square 를 지정합니다.", "var ib = gb().iconButton().color(IconButtonColor.Outlined).width(IconButtonWidth.Wide).icon(\"settings\").ariaLabel(\"설정\").element();", ib, "md-gb-icon-button", () -> "outlined".equals(ib.color) && "wide".equals(ib.width));

        var item = gb().listItem().headline("항목").supportingText("설명").select(true).element();
        check(section, "📘 List item (md-gb-list-item)", "headline, supporting-text, leading/trailing 슬롯을 가진 목록 항목입니다.", "var item = gb().listItem().headline(\"항목\").supportingText(\"설명\").select(true).element();", item, "md-gb-list-item", () -> item.checked
                && item.querySelector("[slot='supporting-text']") != null);

        var list = gb().list().segmented(true).item(gb().listItem().headline("A")).element();
        check(section, "📘 List (md-gb-list)", "목록 컨테이너. segmented(true)로 항목을 분리된 카드 모양으로 표시합니다.", "var list = gb().list().segmented(true).item(gb().listItem().headline(\"A\")).element();", list, "md-gb-list", () -> list.segmented && list.childElementCount == 1);

        var mi = gb().menuItem().headline("복사").disabled(true).element();
        check(section, "📘 Menu item (md-gb-menu-item)", "메뉴 항목입니다. disabled/select 를 지원합니다.", "var mi = gb().menuItem().headline(\"복사\").disabled(true).element();", mi, "md-gb-menu-item", () -> mi.disabled);

        var group = gb().menuGroup().checkable(MenuCheckable.Single).item(gb().menuItem().headline("하나")).element();
        check(section, "📘 Menu group (md-gb-menu-group)", "checkable(single/multiple)로 선택 가능한 메뉴 항목 묶음입니다.", "var group = gb().menuGroup().checkable(MenuCheckable.Single).item(gb().menuItem().headline(\"하나\")).element();", group, "md-gb-menu-group", () -> "single".equals(group.checkable));

        var menu = gb().menu().color(MenuColor.Vibrant).item(gb().menuItem().headline("붙여넣기")).element();
        check(section, "📘 Menu (md-gb-menu)", "color(standard/vibrant) 메뉴 컨테이너입니다.", "var menu = gb().menu().color(MenuColor.Vibrant).item(gb().menuItem().headline(\"붙여넣기\")).element();", menu, "md-gb-menu", () -> "vibrant".equals(menu.color));

        var radio = gb().radio().name("size").value("s").select(true).element();
        check(section, "📘 Radio (md-gb-radio)", "같은 name 끼리 하나만 선택되는 라디오 버튼입니다.", "var radio = gb().radio().name(\"size\").value(\"s\").select(true).element();", radio, "md-gb-radio", () -> radio.checked && "s".equals(radio.getAttribute("value")));

        var split = gb().splitButton().color(SplitButtonColor.Filled).size(Size.MD)
                .leading(button().text("보내기")).trailing(button().aria("label", "더보기")).element();
        check(section, "📘 Split button (md-gb-split-button)", "leading 버튼(주 동작)과 trailing 버튼(메뉴 열기)으로 이루어진 버튼입니다. 두 슬롯에는 <button> 을 넣습니다.", "var split = gb().splitButton().color(SplitButtonColor.Filled).size(Size.MD)\n    .leading(button().text(\"보내기\")).trailing(button().aria(\"label\", \"더보기\")).element();", split, "md-gb-split-button", () -> "filled".equals(split.color)
                && split.querySelector("[slot='leading']") != null && split.querySelector("[slot='trailing']") != null);

        var sw = gb().sw().select(true).onIcon("check").element();
        check(section, "📘 Switch (md-gb-switch)", "켜짐/꺼짐 스위치. onIcon/offIcon 으로 아이콘을 표시합니다.", "var sw = gb().sw().select(true).onIcon(\"check\").element();", sw, "md-gb-switch", () -> sw.selected && sw.querySelector("[slot='on-icon']") != null);
    }

    private static void check(HTMLElement section, String title, String description, String code, HTMLElement el, String tag, BooleanSupplier props) {
        demo(section, title, description, code, el);
        assertEquals("gb: " + tag + " 태그명", tag.toUpperCase(), el.tagName);
        assertTrue("gb: " + tag + " 속성 설정", props.getAsBoolean());
        DomGlobal.setTimeout(ignore -> {
            Object registry = Js.asPropertyMap(DomGlobal.window).get("customElements");
            Function get = Js.uncheckedCast(Js.asPropertyMap(registry).get("get"));
            boolean defined = get.call(registry, tag) != null;
            boolean upgraded = defined && Js.asPropertyMap(el).get("shadowRoot") != null;
            assertTrue("gb: " + tag + " 업그레이드", upgraded);
            assertTrue("gb: " + tag + " 업그레이드 후 속성 유지", props.getAsBoolean());
        }, 2000);
    }
}
