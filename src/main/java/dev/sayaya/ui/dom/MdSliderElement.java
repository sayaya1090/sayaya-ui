package dev.sayaya.ui.dom;

import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLFormElement;
import elemental2.dom.HTMLLabelElement;
import elemental2.dom.NodeList;
import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

@JsType(isNative = true, namespace = GLOBAL, name = "HTMLElement")
public class MdSliderElement extends HTMLElement {
    public boolean disabled;
    public double min;
    public double max;
    public Double value;
    public Double valueStart;
    public Double valueEnd;
    public String valueLabel;
    public String valueLabelStart;
    public String valueLabelEnd;
    public String ariaLabelStart;
    public String ariaValueTextStart;
    public String ariaLabelEnd;
    public String ariaValueTextEnd;
    public double step;
    public boolean ticks;
    public boolean labeled;
    public boolean range;
    public String name;
    public String nameStart;
    public String nameEnd;
    public HTMLFormElement form;
    public NodeList<HTMLLabelElement> labels;
}
