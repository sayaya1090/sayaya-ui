package dev.sayaya.ui.elements;

import dev.sayaya.ui.dom.MdFeedbackElement.*;
import dev.sayaya.ui.elements.interfaces.HasAriaLabel;
import elemental2.dom.CustomEvent;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;
import jsinterop.base.Js;
import org.jboss.elemento.*;

import java.util.function.Consumer;

import static org.jboss.elemento.Elements.htmlContainer;

/**
 * M3 tooltip / snackbar / button group 빌더 (sayaya-ui 자체 구현, labs.bundle.js 필요).
 * <pre>{@code
 * import static dev.sayaya.ui.elements.FeedbackElementBuilder.*;
 *
 * tooltip().plain().anchor(saveButton).text("저장").element();
 * tooltip().rich().anchor(infoIcon).subhead("권한").text("편집자만 수정할 수 있습니다").action(button().text().text("자세히")).element();
 * var sb = snackbar().text("삭제했습니다").action("실행 취소").onAction(e -> undo()).element(); sb.show();
 * buttonGroup().connected().size(ButtonGroupSize.Small).selection(ButtonGroupSelection.Single)
 *     .add(button().outlined().text("일")).add(button().outlined().text("주")).element();
 * }</pre>
 * 수치는 @material/web 2.5.0 의 md-comp 토큰을 따른다.
 */
public final class FeedbackElementBuilder {
    private FeedbackElementBuilder() {}

    public static TooltipPrepare tooltip() { return new TooltipPrepare(); }
    public static SnackbarBuilder snackbar() { return new SnackbarBuilder(); }
    public static ButtonGroupPrepare buttonGroup() { return new ButtonGroupPrepare(); }

    public enum TooltipPosition { Top("top"), Bottom("bottom"), Start("start"), End("end");
        public final String value; TooltipPosition(String v) { value = v; } }
    public enum ButtonGroupSize { XSmall("xsmall"), Small("small"), Medium("medium"), Large("large"), XLarge("xlarge");
        public final String value; ButtonGroupSize(String v) { value = v; } }
    public enum ButtonGroupSelection { None("none"), Single("single"), Multiple("multiple");
        public final String value; ButtonGroupSelection(String v) { value = v; } }

    public abstract static class FbBuilder<E extends HTMLElement, SELF extends FbBuilder<E, SELF>> implements
            HTMLElementStyleMethods<E, SELF>, HTMLElementVisibilityMethods<E, SELF>, ElementAttributeMethods<E, SELF>,
            ElementClassListMethods<E, SELF>, ElementContainerMethods<E, SELF>, ElementEventMethods<E, SELF>,
            ElementTextMethods<E, SELF>, ElementIdMethods<E, SELF>, ElementConsumerMethods<E, SELF>, HasAriaLabel<E, SELF> {
        private final HTMLContainerBuilder<E> that;
        FbBuilder(String tag, Class<E> type) { that = htmlContainer(tag, type); }
        @Override public E element() { return that.element(); }
        protected SELF slot(String slot, Element child) { child.setAttribute("slot", slot); element().appendChild(child); return that(); }
        protected SELF slot(String slot, IsElement<? extends Element> child) { return slot(slot, child.element()); }
    }

    // ---------------- Tooltip ----------------
    public static final class TooltipPrepare {
        public TooltipBuilder plain() { return new TooltipBuilder("plain"); }
        public TooltipBuilder rich() { return new TooltipBuilder("rich"); }
    }
    public static final class TooltipBuilder extends FbBuilder<MdTooltipElement, TooltipBuilder> {
        TooltipBuilder(String variant) { super("md-tooltip", MdTooltipElement.class); element().variant = variant; }
        @Override public TooltipBuilder that() { return this; }
        /** 본문 텍스트. textContent 를 덮어쓰지 않고 덧붙여 subhead/actions 슬롯을 보존한다(호출 순서 무관). */
        @Override public TooltipBuilder text(String text) { element().appendChild(elemental2.dom.DomGlobal.document.createTextNode(text)); return this; }
        /** 이 요소에 hover/focus 하면 표시된다. */
        public TooltipBuilder anchor(Element anchor) { element().anchor = anchor; return this; }
        public TooltipBuilder anchor(IsElement<? extends Element> anchor) { return anchor(anchor.element()); }
        /** 앵커 id 로 연결 */
        public TooltipBuilder htmlFor(String id) { element().htmlFor = id; return this; }
        public TooltipBuilder position(TooltipPosition position) { element().position = position.value; return this; }
        public TooltipBuilder delay(int ms) { element().delay = ms; return this; }
        /** rich: 제목 */
        public TooltipBuilder subhead(String text) { return slot("subhead", Elements.span().text(text)); }
        /** rich: 액션 버튼. 액션이 있으면 포인터가 툴팁으로 옮겨가도 닫히지 않는다. */
        public TooltipBuilder action(IsElement<? extends Element> action) { element().persistent = true; return slot("actions", action); }
        public TooltipBuilder action(Element action) { element().persistent = true; return slot("actions", action); }
        public TooltipBuilder onOpened(Consumer<MdTooltipElement> cb) { element().addEventListener("opened", e -> cb.accept(element())); return this; }
        public TooltipBuilder onClosed(Consumer<MdTooltipElement> cb) { element().addEventListener("closed", e -> cb.accept(element())); return this; }
    }

    // ---------------- Snackbar ----------------
    public static final class SnackbarBuilder extends FbBuilder<MdSnackbarElement, SnackbarBuilder> {
        SnackbarBuilder() { super("md-snackbar", MdSnackbarElement.class); }
        @Override public SnackbarBuilder that() { return this; }
        public SnackbarBuilder action(String label) { element().action = label; return this; }
        public SnackbarBuilder closeable(boolean closeable) { element().closeable = closeable; return this; }
        /** 자동 닫힘(ms). 0 이하면 사용자가 닫을 때까지 유지(액션·닫기 버튼과 함께 쓸 것). */
        public SnackbarBuilder timeout(int ms) { element().timeout = ms; return this; }
        public SnackbarBuilder multiline(boolean multiline) { element().multiline = multiline; return this; }
        /** 긴 액션 라벨: 액션을 메시지 아래 줄에 배치 */
        public SnackbarBuilder longAction(boolean longAction) { element().longAction = longAction; return this; }
        public SnackbarBuilder onAction(Consumer<MdSnackbarElement> cb) { element().addEventListener("action", e -> cb.accept(element())); return this; }
        /** 닫힘 사유: action | dismiss | timeout | replaced */
        public SnackbarBuilder onClosed(Consumer<String> cb) {
            element().addEventListener("closed", e -> {
                Object d = ((CustomEvent<?>) Js.uncheckedCast(e)).detail;
                cb.accept(d == null ? null : Js.asString(Js.asPropertyMap(d).get("reason")));
            });
            return this;
        }
    }

    // ---------------- Button group ----------------
    public static final class ButtonGroupPrepare {
        public ButtonGroupBuilder standard() { return new ButtonGroupBuilder("standard"); }
        public ButtonGroupBuilder connected() { return new ButtonGroupBuilder("connected"); }
    }
    public static final class ButtonGroupBuilder extends FbBuilder<MdButtonGroupElement, ButtonGroupBuilder> {
        ButtonGroupBuilder(String variant) { super("md-button-group", MdButtonGroupElement.class); element().variant = variant; }
        @Override public ButtonGroupBuilder that() { return this; }
        /** 그룹 안 버튼 높이·간격을 함께 정한다(md-comp-button-group 토큰). */
        public ButtonGroupBuilder size(ButtonGroupSize size) { element().size = size.value; return this; }
        /** connected 그룹의 토글 선택 방식 */
        public ButtonGroupBuilder selection(ButtonGroupSelection selection) { element().selection = selection.value; return this; }
        public ButtonGroupBuilder onSelectionChanged(Consumer<int[]> cb) {
            element().addEventListener("selection-changed", e -> {
                double[] d = element().selectedIndices; int[] r = new int[d.length];
                for (int i = 0; i < d.length; i++) r[i] = (int) d[i];
                cb.accept(r);
            });
            return this;
        }
    }
}
