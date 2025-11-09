package dev.sayaya.ui.elements;

import dev.sayaya.ui.dom.MdFocusRingElement;
import elemental2.dom.HTMLElement;
import org.jboss.elemento.*;

import static org.jboss.elemento.Elements.htmlElement;

public class FocusRingElementBuilder implements HTMLElementStyleMethods<MdFocusRingElement, FocusRingElementBuilder>,
        HTMLElementVisibilityMethods<MdFocusRingElement, FocusRingElementBuilder>,
        ElementAttributeMethods<MdFocusRingElement, FocusRingElementBuilder>,
        ElementClassListMethods<MdFocusRingElement, FocusRingElementBuilder>,
        ElementEventMethods<MdFocusRingElement, FocusRingElementBuilder> {
    public static FocusRingElementBuilder focusRing() {
        return new FocusRingElementBuilder();
    }
    private final HTMLElementBuilder<MdFocusRingElement> that = htmlElement("md-focus-ring", MdFocusRingElement.class);
    public FocusRingElementBuilder control(IsElement<? extends HTMLElement> elem) {
        return control(elem.element());
    }
    public FocusRingElementBuilder control(HTMLElement elem) {
        element().control = elem;
        return that();
    }
    public FocusRingElementBuilder inward() {
        return inward(true);
    }
    public FocusRingElementBuilder inward(boolean inward) {
        element().inward = inward;
        return that();
    }
    public FocusRingElementBuilder htmlFor(String id) {
        element().htmlFor = id;
        return that();
    }
    public FocusRingElementBuilder attach(IsElement<? extends HTMLElement> control) {
        return attach(control.element());
    }
    public FocusRingElementBuilder attach(HTMLElement control) {
        element().attach(control);
        return that();
    }
    public FocusRingElementBuilder detach() {
        element().detach();
        return that();
    }
    @Override
    public MdFocusRingElement element() {
        return that.element();
    }
    @Override
    public FocusRingElementBuilder that() {
        return this;
    }
}
