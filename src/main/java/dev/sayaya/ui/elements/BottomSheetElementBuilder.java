package dev.sayaya.ui.elements;

import dev.sayaya.ui.dom.MdBottomSheetElement;
import dev.sayaya.ui.elements.interfaces.HasAriaLabel;
import elemental2.dom.CustomEvent;
import jsinterop.base.Js;
import org.jboss.elemento.*;

import java.util.function.Consumer;

import static org.jboss.elemento.Elements.htmlContainer;

/**
 * M3 bottom sheet (sayaya-ui 자체 구현, labs.bundle.js 필요).
 * <pre>{@code
 * import static dev.sayaya.ui.elements.BottomSheetElementBuilder.*;
 *
 * var share = bottomSheet().modal().add(shareTargets)
 *     .onClosed(reason -> ...).element();   // scrim | escape | swipe | close
 * body().add(share); share.show();
 * }</pre>
 * 화면 아래에 붙고 최대 너비 640dp 로 가운데 놓인다. 드래그 핸들을 누르면 펼침/접힘을 바꾸고, 아래로 끌어 내리면 접히거나 닫힌다.
 * modal 은 처음에 화면 높이의 50% 까지만 열리고, 스크림 클릭·Esc 로도 닫힌다.
 */
public final class BottomSheetElementBuilder implements
        HTMLElementStyleMethods<MdBottomSheetElement, BottomSheetElementBuilder>, HTMLElementVisibilityMethods<MdBottomSheetElement, BottomSheetElementBuilder>,
        ElementAttributeMethods<MdBottomSheetElement, BottomSheetElementBuilder>, ElementClassListMethods<MdBottomSheetElement, BottomSheetElementBuilder>,
        ElementContainerMethods<MdBottomSheetElement, BottomSheetElementBuilder>, ElementEventMethods<MdBottomSheetElement, BottomSheetElementBuilder>,
        ElementIdMethods<MdBottomSheetElement, BottomSheetElementBuilder>, ElementConsumerMethods<MdBottomSheetElement, BottomSheetElementBuilder>,
        HasAriaLabel<MdBottomSheetElement, BottomSheetElementBuilder> {
    public static BottomSheetPrepare bottomSheet() { return new BottomSheetPrepare(); }
    public static final class BottomSheetPrepare {
        /** 본문과 함께 보이는 시트(스크림 없음, 뒤 화면을 계속 쓸 수 있음) */
        public BottomSheetElementBuilder standard() { return new BottomSheetElementBuilder(false); }
        /** 스크림 위에 뜨는 시트(닫을 때까지 뒤 화면을 막음) */
        public BottomSheetElementBuilder modal() { return new BottomSheetElementBuilder(true); }
    }

    private final HTMLContainerBuilder<MdBottomSheetElement> that = htmlContainer("md-bottom-sheet", MdBottomSheetElement.class);
    private BottomSheetElementBuilder(boolean modal) { element().modal = modal; }
    @Override public BottomSheetElementBuilder that() { return this; }
    @Override public MdBottomSheetElement element() { return that.element(); }

    /** 드래그 핸들 표시(기본 true). 끄면 끌어 내리기·크기 조절을 쓸 수 없으니 다른 닫기 수단을 함께 둘 것 */
    public BottomSheetElementBuilder dragHandle(boolean dragHandle) { element().dragHandle = dragHandle; return this; }
    /** 드래그 핸들 버튼의 접근성 이름. 기본 "시트 크기 조절" */
    public BottomSheetElementBuilder handleLabel(String label) { element().handleLabel = label; return this; }
    /** 펼친 상태(최대: 화면 위 72dp 여백까지) */
    public BottomSheetElementBuilder expanded(boolean expanded) { element().expanded = expanded; return this; }
    public BottomSheetElementBuilder open(boolean open) { element().open = open; return this; }
    public BottomSheetElementBuilder onOpened(Consumer<MdBottomSheetElement> cb) { element().addEventListener("opened", e -> cb.accept(element())); return this; }
    /** 닫힘 사유: close | scrim | escape | swipe | 직접 close(reason) 에 넘긴 값 */
    public BottomSheetElementBuilder onClosed(Consumer<String> cb) {
        element().addEventListener("closed", e -> {
            Object d = ((CustomEvent<?>) Js.uncheckedCast(e)).detail;
            cb.accept(d == null ? null : Js.asString(Js.asPropertyMap(d).get("reason")));
        });
        return this;
    }
}
