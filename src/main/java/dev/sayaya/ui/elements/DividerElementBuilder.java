package dev.sayaya.ui.elements;

import dev.sayaya.ui.dom.MdDividerElement;
import dev.sayaya.ui.elements.interfaces.HasAriaLabel;
import org.jboss.elemento.*;

import static org.jboss.elemento.Elements.htmlElement;

public class DividerElementBuilder implements HTMLElementStyleMethods<MdDividerElement, DividerElementBuilder>,
        HTMLElementVisibilityMethods<MdDividerElement, DividerElementBuilder>,
        ElementAttributeMethods<MdDividerElement, DividerElementBuilder>,
        ElementClassListMethods<MdDividerElement, DividerElementBuilder>,
        ElementEventMethods<MdDividerElement, DividerElementBuilder>,
        HasAriaLabel<MdDividerElement, DividerElementBuilder> {
    public static DividerElementBuilder divider() {
        return new DividerElementBuilder();
    }
    public static DividerElementBuilder dividerInset() {
        return new DividerElementBuilder().inset();
    }
    public static DividerElementBuilder dividerInsetStart() {
        return new DividerElementBuilder().insetStart();
    }
    public static DividerElementBuilder dividerInsetEnd() {
        return new DividerElementBuilder().insetEnd();
    }

    private final HTMLElementBuilder<MdDividerElement> that;
    private DividerElementBuilder() {
        that = htmlElement("md-divider", MdDividerElement.class);
    }
    public DividerElementBuilder inset() {
        return inset(true);
    }
    public DividerElementBuilder inset(boolean inset) {
        element().inset = inset;
        element().insetStart = false;
        element().insetEnd = false;
        return this;
    }
    public DividerElementBuilder insetStart() {
        return insetStart(true);
    }
    public DividerElementBuilder insetStart(boolean insetStart) {
        element().inset = false;
        element().insetStart = insetStart;
        element().insetEnd = false;
        return this;
    }
    public DividerElementBuilder insetEnd() {
        return insetEnd(true);
    }
    public DividerElementBuilder insetEnd(boolean insetEnd) {
        element().inset = false;
        element().insetStart = false;
        element().insetEnd = insetEnd;
        return this;
    }
    public DividerElementBuilder separator() {
        element().role = "separator";
        return this;
    }
    @Override
    public MdDividerElement element() {
        return that.element();
    }
    @Override
    public DividerElementBuilder that() {
        return this;
    }
}
