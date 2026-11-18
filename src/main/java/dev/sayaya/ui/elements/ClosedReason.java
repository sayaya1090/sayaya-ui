package dev.sayaya.ui.elements;

import elemental2.dom.CustomEvent;
import elemental2.dom.Event;
import jsinterop.base.Js;

/** labs 컴포넌트(sheet·snackbar·toast)의 closed 이벤트에서 detail.reason 을 꺼낸다(없으면 null). 패키지 내부 전용 */
final class ClosedReason {
    private ClosedReason() {}
    static String of(Event e) {
        Object d = ((CustomEvent<?>) Js.uncheckedCast(e)).detail;
        return d == null ? null : Js.asString(Js.asPropertyMap(d).get("reason"));
    }
}
