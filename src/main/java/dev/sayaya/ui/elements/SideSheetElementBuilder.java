package dev.sayaya.ui.elements;

import dev.sayaya.ui.dom.MdSideSheetElement;
import dev.sayaya.ui.elements.interfaces.HasAriaLabel;
import elemental2.dom.CustomEvent;
import elemental2.dom.Element;
import jsinterop.base.Js;
import org.jboss.elemento.*;

import java.util.function.Consumer;

import static org.jboss.elemento.Elements.htmlContainer;

/**
 * M3 side sheet (sayaya-ui 자체 구현, labs.bundle.js 필요).
 * <pre>{@code
 * import static dev.sayaya.ui.elements.SideSheetElementBuilder.*;
 *
 * var filters = sideSheet().modal().headline("필터").add(filterForm)
 *     .action(button().filled().text("적용")).action(button().outlined().text("취소"))
 *     .onClosed(reason -> ...).element();
 * body().add(filters); filters.show();
 * }</pre>
 * 닫기 아이콘 버튼은 항상 표시된다(M3: 닫기 수단은 항상 있어야 함). modal 은 스크림 클릭·Esc 로도 닫힌다.
 */
public final class SideSheetElementBuilder implements
        HTMLElementStyleMethods<MdSideSheetElement, SideSheetElementBuilder>, HTMLElementVisibilityMethods<MdSideSheetElement, SideSheetElementBuilder>,
        ElementAttributeMethods<MdSideSheetElement, SideSheetElementBuilder>, ElementClassListMethods<MdSideSheetElement, SideSheetElementBuilder>,
        ElementContainerMethods<MdSideSheetElement, SideSheetElementBuilder>, ElementEventMethods<MdSideSheetElement, SideSheetElementBuilder>,
        ElementIdMethods<MdSideSheetElement, SideSheetElementBuilder>, ElementConsumerMethods<MdSideSheetElement, SideSheetElementBuilder>,
        HasAriaLabel<MdSideSheetElement, SideSheetElementBuilder> {
    public static SideSheetPrepare sideSheet() { return new SideSheetPrepare(); }
    public static final class SideSheetPrepare {
        /** 본문 옆에 나란히 놓이는 사이드시트(medium/expanded 화면) */
        public SideSheetElementBuilder standard() { return new SideSheetElementBuilder(false); }
        /** 스크림 위에 뜨는 사이드시트(compact 화면) */
        public SideSheetElementBuilder modal() { return new SideSheetElementBuilder(true); }
    }
    public enum Pivot { Start("start"), End("end");
        public final String value; Pivot(String v) { value = v; } }

    private final HTMLContainerBuilder<MdSideSheetElement> that = htmlContainer("md-side-sheet", MdSideSheetElement.class);
    private SideSheetElementBuilder(boolean modal) { element().modal = modal; }
    @Override public SideSheetElementBuilder that() { return this; }
    @Override public MdSideSheetElement element() { return that.element(); }

    public SideSheetElementBuilder headline(String headline) { element().headline = headline; return this; }
    /** 시트가 붙는 화면 가장자리. 기본 End */
    public SideSheetElementBuilder pivot(Pivot pivot) { element().pivot = pivot.value; return this; }
    /** 화면 가장자리에서 16dp 떨어진 형태 */
    public SideSheetElementBuilder detached(boolean detached) { element().detached = detached; return this; }
    /** standard: 본문 쪽 가장자리 구분선, 공통: 액션 위 구분선 */
    public SideSheetElementBuilder divider(boolean divider) { element().divider = divider; return this; }
    /** 뒤로 아이콘 버튼(다단계 시트) */
    public SideSheetElementBuilder back(boolean back) { element().back = back; return this; }
    public SideSheetElementBuilder closeLabel(String label) { element().closeLabel = label; return this; }
    public SideSheetElementBuilder backLabel(String label) { element().backLabel = label; return this; }
    public SideSheetElementBuilder open(boolean open) { element().open = open; return this; }
    /** 하단 액션 영역(왼쪽 정렬) */
    public SideSheetElementBuilder action(Element action) { action.setAttribute("slot", "actions"); element().appendChild(action); return this; }
    public SideSheetElementBuilder action(IsElement<? extends Element> action) { return action(action.element()); }
    public SideSheetElementBuilder onOpened(Consumer<MdSideSheetElement> cb) { element().addEventListener("opened", e -> cb.accept(element())); return this; }
    public SideSheetElementBuilder onBack(Consumer<MdSideSheetElement> cb) { element().addEventListener("back", e -> cb.accept(element())); return this; }
    /** 닫힘 사유: close | scrim | escape | 직접 close(reason) 에 넘긴 값 */
    public SideSheetElementBuilder onClosed(Consumer<String> cb) {
        element().addEventListener("closed", e -> {
            Object d = ((CustomEvent<?>) Js.uncheckedCast(e)).detail;
            cb.accept(d == null ? null : Js.asString(Js.asPropertyMap(d).get("reason")));
        });
        return this;
    }
}
