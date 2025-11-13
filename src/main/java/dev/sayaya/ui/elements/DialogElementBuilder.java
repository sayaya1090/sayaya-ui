package dev.sayaya.ui.elements;

import dev.sayaya.ui.dom.MdDialogElement;
import dev.sayaya.ui.elements.interfaces.*;
import elemental2.dom.HTMLElement;
import elemental2.promise.Promise;
import org.jboss.elemento.*;

import static org.jboss.elemento.Elements.htmlElement;

public class DialogElementBuilder implements HTMLElementStyleMethods<MdDialogElement, DialogElementBuilder>,
        HTMLElementVisibilityMethods<MdDialogElement, DialogElementBuilder>,
        ElementAttributeMethods<MdDialogElement, DialogElementBuilder>,
        ElementClassListMethods<MdDialogElement, DialogElementBuilder>,
        ElementEventMethods<MdDialogElement, DialogElementBuilder>,
        HasHeadlineSlot<MdDialogElement, DialogElementBuilder>,
        HasContentSlot<MdDialogElement, DialogElementBuilder>,
        HasActionsSlot<MdDialogElement, DialogElementBuilder>,
        HasDialogEvents<MdDialogElement, DialogElementBuilder>,
        HasAriaLabel<MdDialogElement, DialogElementBuilder> {
    public static DialogElementBuilder dialog() {
        return new DialogElementBuilder();
    }
    public static DialogElementBuilder alert() {
        return new DialogElementBuilder().type("alert");
    }
    private final HTMLElementBuilder<MdDialogElement> that = htmlElement("md-dialog", MdDialogElement.class);
    public DialogElementBuilder type(String type) {
        element().type = type;
        return that();
    }
    public DialogElementBuilder open() {
        return open(true);
    }
    public DialogElementBuilder open(boolean open) {
        element().open = open;
        return that();
    }
    public DialogElementBuilder quick(boolean quick) {
        element().quick = quick;
        return that();
    }
    public DialogElementBuilder noFocusTrap(boolean noFocusTrap) {
        element().noFocusTrap = noFocusTrap;
        return that();
    }
    public Promise<Void> show() {
        return element().show();
    }
    public Promise<String> close() {
        return element().close();
    }
    @Override public DialogElementBuilder content(HTMLElement element) {
        if(element.tagName.equalsIgnoreCase("form")) element.setAttribute("method", "dialog");
        element.setAttribute("slot", "content");
        add(element);
        return that();
    }
    @Override public MdDialogElement element() {
        return that.element();
    }
    @Override public DialogElementBuilder that() {
        return this;
    }
}
