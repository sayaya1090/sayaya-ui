package dev.sayaya.ui.elements.interfaces;

import elemental2.dom.HTMLElement;
import org.jboss.elemento.IsElement;

/**
 * 비활성 상태를 가진 빌더.
 * <p>
 * 비활성 여부는 {@link #disabled(boolean)} / {@link #disabled()} / {@link #enabled()} / {@link #enabled(boolean)} 로 설정하고
 * {@link #isDisabled()} 로 읽는다. 구현은 요소의 {@code disabled} 속성(property)을 설정하며, 반영되는 HTML 속성(attribute)의
 * 값 문자열은 컴포넌트마다 다를 수 있다(예: {@code ""} 또는 {@code "true"}). 따라서 {@code getAttribute("disabled")} 의
 * 값에 의존하지 말고, 직접 확인할 때는 {@code hasAttribute("disabled")} 나 {@link #isDisabled()} 를 쓴다.
 * {@code setAttribute("disabled", "false")} 는 HTML 불리언 속성 규칙상 오히려 비활성으로 해석되므로 쓰지 않는다.
 */
public interface Disableable<E extends HTMLElement, SELF extends Disableable<E, SELF>> extends IsElement<E> {
    SELF that();

    SELF disabled(boolean disabled);

    default SELF disabled() {
        return disabled(true);
    }

    default SELF enabled() {
        return disabled(false);
    }

    default SELF enabled(boolean enabled) {
        return disabled(!enabled);
    }

    /** 비활성이면 true. {@code disabled} 속성(attribute)의 존재 여부로 판단하며 값 문자열은 보지 않는다. */
    default boolean isDisabled() {
        return element().hasAttribute("disabled");
    }
}
