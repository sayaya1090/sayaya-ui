package dev.sayaya.ui.elements;

import dev.sayaya.ui.dom.MdRippleElement;
import elemental2.dom.HTMLElement;
import org.jboss.elemento.HTMLElementBuilder;
import org.jboss.elemento.HTMLElementStyleMethods;
import org.jboss.elemento.HTMLElementVisibilityMethods;
import org.jboss.elemento.IsElement;

import static org.jboss.elemento.Elements.htmlElement;

public class RippleElementBuilder implements HTMLElementStyleMethods<MdRippleElement, RippleElementBuilder>, HTMLElementVisibilityMethods<MdRippleElement, RippleElementBuilder> {
    public static RippleElementBuilder ripple() {
        return new RippleElementBuilder();
    }
    private final HTMLElementBuilder<MdRippleElement> that = htmlElement("md-ripple", MdRippleElement.class);
    public RippleElementBuilder disabled(boolean disabled) {
        element().disabled = disabled;
        return that();
    }
    public RippleElementBuilder htmlFor(String htmlFor) {
        element().htmlFor = htmlFor;
        return that();
    }
    public RippleElementBuilder control(IsElement<? extends HTMLElement> elem) {
        return control(elem.element());
    }
    public RippleElementBuilder control(HTMLElement elem) {
        element().attach(elem);
        return that();
    }
    @Override
    public MdRippleElement element() {
        return that.element();
    }
    @Override
    public RippleElementBuilder that() {
        return this;
    }
}
