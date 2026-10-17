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
        printSeparator();
        var section = div().style("margin", "20px").element();
        body().add(section);

        var badge = gb().badge().value(3).element();
        check(section, badge, "md-gb-badge", () -> "3".equals(badge.textContent));

        var btn = gb().button().color(ButtonColor.Tonal).size(Size.LG).square(true).text("저장").element();
        check(section, btn, "md-gb-button", () -> "tonal".equals(btn.color) && "lg".equals(btn.size) && btn.square);

        var card = gb().card().color(CardColor.Outlined).interactive(true).add(div().text("Card")).element();
        check(section, card, "md-gb-card", () -> "outlined".equals(card.color) && card.interactive);

        var cb = gb().checkbox().select(true).required(true).name("agree").element();
        check(section, cb, "md-gb-checkbox", () -> cb.checked && cb.required);

        var divider = gb().divider().vertical(true).element();
        check(section, divider, "md-gb-divider", () -> divider.vertical);

        var fab = gb().fab().color(FabColor.TertiaryContainer).size(FabSize.LG).icon("add").ariaLabel("추가").element();
        check(section, fab, "md-gb-fab", () -> "tertiary-container".equals(fab.color) && "lg".equals(fab.size));

        var icon = gb().icon("favorite").element();
        check(section, icon, "md-gb-icon", () -> "favorite".equals(icon.textContent));
        DomGlobal.setTimeout(ignore -> {
            elemental2.core.Function gcs = Js.uncheckedCast(Js.asPropertyMap(DomGlobal.window).get("getComputedStyle"));
            elemental2.dom.CSSStyleDeclaration st = Js.uncheckedCast(gcs.call(DomGlobal.window, icon));
            String family = st.getPropertyValue("font-family");
            assertTrue("gb: md-gb-icon 아이콘 폰트(Material Symbols Outlined) 적용 [" + family + "]", family.contains("Material Symbols Outlined"));
        }, 2000);

        var ib = gb().iconButton().color(IconButtonColor.Outlined).width(IconButtonWidth.Wide).icon("settings").ariaLabel("설정").element();
        check(section, ib, "md-gb-icon-button", () -> "outlined".equals(ib.color) && "wide".equals(ib.width));

        var item = gb().listItem().headline("항목").supportingText("설명").select(true).element();
        check(section, item, "md-gb-list-item", () -> item.checked
                && item.querySelector("[slot='supporting-text']") != null);

        var list = gb().list().segmented(true).item(gb().listItem().headline("A")).element();
        check(section, list, "md-gb-list", () -> list.segmented && list.childElementCount == 1);

        var mi = gb().menuItem().headline("복사").disabled(true).element();
        check(section, mi, "md-gb-menu-item", () -> mi.disabled);

        var group = gb().menuGroup().checkable(MenuCheckable.Single).item(gb().menuItem().headline("하나")).element();
        check(section, group, "md-gb-menu-group", () -> "single".equals(group.checkable));

        var menu = gb().menu().color(MenuColor.Vibrant).item(gb().menuItem().headline("붙여넣기")).element();
        check(section, menu, "md-gb-menu", () -> "vibrant".equals(menu.color));

        var radio = gb().radio().name("size").value("s").select(true).element();
        check(section, radio, "md-gb-radio", () -> radio.checked && "s".equals(radio.getAttribute("value")));

        var split = gb().splitButton().color(SplitButtonColor.Filled).size(Size.MD)
                .leading(button().text("보내기")).trailing(button().aria("label", "더보기")).element();
        check(section, split, "md-gb-split-button", () -> "filled".equals(split.color)
                && split.querySelector("[slot='leading']") != null && split.querySelector("[slot='trailing']") != null);

        var sw = gb().sw().select(true).onIcon("check").element();
        check(section, sw, "md-gb-switch", () -> sw.selected && sw.querySelector("[slot='on-icon']") != null);
    }

    private static void check(HTMLElement section, HTMLElement el, String tag, BooleanSupplier props) {
        section.appendChild(el);
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
