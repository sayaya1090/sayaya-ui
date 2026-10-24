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
 * M3 tooltip / snackbar / button group, [M3 외 확장] toast 빌더 (sayaya-ui 자체 구현, labs.bundle.js 필요).
 * <pre>{@code
 * import static dev.sayaya.ui.elements.FeedbackElementBuilder.*;
 *
 * tooltip().plain().anchor(saveButton).text("저장").element();
 * tooltip().rich().anchor(infoIcon).subhead("권한").text("편집자만 수정할 수 있습니다").action(button().text().text("자세히")).element();
 * var sb = snackbar().text("삭제했습니다").action("실행 취소").onAction(e -> undo()).element(); sb.show();
 * toast().success().text("저장했습니다").show();          // 웹: 오른쪽 위에 쌓이는 알림
 * toast().error().text("저장하지 못했습니다").show();     // warning·error 는 닫을 때까지 유지
 * buttonGroup().connected().size(ButtonGroupSize.Small).selection(ButtonGroupSelection.Single)
 *     .add(button().outlined().text("일")).add(button().outlined().text("주")).element();
 * }</pre>
 * 수치는 @material/web 2.5.0 의 md-comp 토큰을 따른다.
 */
public final class FeedbackElementBuilder {
    private FeedbackElementBuilder() {}

    public static TooltipPrepare tooltip() { return new TooltipPrepare(); }
    public static SnackbarBuilder snackbar() { return new SnackbarBuilder(); }
    /** [M3 외 확장] 데스크톱 웹용 스택 알림. 모바일·짧은 단일 알림은 {@link #snackbar()} 를 쓴다 */
    public static ToastPrepare toast() { return new ToastPrepare(); }
    public static ButtonGroupPrepare buttonGroup() { return new ButtonGroupPrepare(); }

    public enum TooltipPosition { Top("top"), Bottom("bottom"), Start("start"), End("end");
        public final String value; TooltipPosition(String v) { value = v; } }
    public enum SnackbarAlign { Start("start"), Center("center");
        public final String value; SnackbarAlign(String v) { value = v; } }
    public enum ToastLevel { Info("info"), Success("success"), Warning("warning"), Error("error");
        public final String value; ToastLevel(String v) { value = v; } }
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
        /** 문구를 설정한다. 다시 부르면 이전 문구를 바꾼다(2.5.0.5 부터. 이전에는 이어 붙였다). subhead·action 은 그대로 둔다 */
        @Override public TooltipBuilder text(String text) { element().text = text; return this; }
        /** 이 요소에 hover/focus 하면 표시된다. */
        public TooltipBuilder anchor(Element anchor) { element().anchor = anchor; return this; }
        public TooltipBuilder anchor(IsElement<? extends Element> anchor) { return anchor(anchor.element()); }
        /** 앵커 id 로 연결 */
        public TooltipBuilder htmlFor(String id) { element().htmlFor = id; return this; }
        public TooltipBuilder position(TooltipPosition position) { element().position = position.value; return this; }
        public TooltipBuilder delay(int ms) { element().delay = ms; return this; }
        /** 터치 길게 누르기로 여는 시간(ms). 기본 500 */
        public TooltipBuilder longPressDelay(int ms) { element().longPressDelay = ms; return this; }
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
        /** 닫기 버튼 접근성 라벨(기본 "Close") */
        public SnackbarBuilder closeLabel(String label) { element().setAttribute("close-label", label); return this; }
        /** 화면 아래 정렬. 가이드: 앱 안에서 왼쪽 또는 가운데 중 하나로 일관되게 쓴다 */
        public SnackbarBuilder align(SnackbarAlign align) { element().align = align.value; return this; }
        /** 자동 닫힘(ms). 0 이면 사용자가 닫을 때까지 유지. 지정하지 않으면 액션이 있을 때 닫지 않고(가이드: 웹 접근성), 없을 때 4초 */
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

    // ---------------- Toast [M3 외 확장] ----------------
    public static final class ToastPrepare {
        private ToastPrepare() {}
        /** primary 색. 기본 3초 뒤 자동으로 닫힘 */
        public ToastBuilder info() { return new ToastBuilder(ToastLevel.Info); }
        /** tertiary 색. 기본 3초 뒤 자동으로 닫힘 */
        public ToastBuilder success() { return new ToastBuilder(ToastLevel.Success); }
        /** error-container 색. 기본으로 사용자가 닫을 때까지 유지 */
        public ToastBuilder warning() { return new ToastBuilder(ToastLevel.Warning); }
        /** error 색. 기본으로 사용자가 닫을 때까지 유지 */
        public ToastBuilder error() { return new ToastBuilder(ToastLevel.Error); }
        public ToastBuilder level(ToastLevel level) { return new ToastBuilder(level); }
    }
    public static final class ToastBuilder extends FbBuilder<MdToastElement, ToastBuilder> {
        ToastBuilder(ToastLevel level) { super("md-toast", MdToastElement.class); element().level = level.value; }
        @Override public ToastBuilder that() { return this; }
        public ToastBuilder action(String label) { element().action = label; return this; }
        /** 닫기(X) 버튼. 기본 true */
        public ToastBuilder closeable(boolean closeable) { element().closeable = closeable; return this; }
        /** 닫기 버튼 접근성 라벨(기본 "Close") */
        public ToastBuilder closeLabel(String label) { element().setAttribute("close-label", label); return this; }
        /** 자동 닫힘(ms). 0 이면 사용자가 닫을 때까지 유지. 지정하지 않으면 info·success 3초, warning·error 유지 */
        public ToastBuilder timeout(int ms) { element().timeout = ms; return this; }
        /** 스택에 동시에 보일 최대 개수(기본 5). 넘으면 가장 오래된 토스트가 'overflow' 로 닫힌다 */
        public ToastBuilder max(int max) { element().max = max; return this; }
        public ToastBuilder onAction(Consumer<MdToastElement> cb) { element().addEventListener("action", e -> cb.accept(element())); return this; }
        public ToastBuilder onOpened(Consumer<MdToastElement> cb) { element().addEventListener("opened", e -> cb.accept(element())); return this; }
        /** 닫힘 사유: action | dismiss | timeout | overflow */
        public ToastBuilder onClosed(Consumer<String> cb) {
            element().addEventListener("closed", e -> {
                Object d = ((CustomEvent<?>) Js.uncheckedCast(e)).detail;
                cb.accept(d == null ? null : Js.asString(Js.asPropertyMap(d).get("reason")));
            });
            return this;
        }
        /** 바로 띄운다(스택에 붙임). 반환값은 닫기 등에 쓰는 엘리먼트 */
        public MdToastElement show() { element().show(); return element(); }
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
