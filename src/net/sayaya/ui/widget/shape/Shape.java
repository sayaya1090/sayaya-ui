package net.sayaya.ui.widget.shape;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.HasMouseDownHandlers;
import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.HasMouseOverHandlers;

public interface Shape extends HasMouseOverHandlers, HasMouseOutHandlers, HasMouseDownHandlers {
	Element toElement();
	
	Shape transform(Transform transform);
	Shape remove(Transform transform);
	
	public interface Transform {
		String toString();
	}
	public static class Rotate implements Transform {
		private final double degree;
		private final Double x;
		private final Double y;
		public Rotate(double degree) {
			this.degree = degree;
			this.x = null;
			this.y = null;
		}
		public Rotate(double degree, double x, double y) {
			this.degree = degree;
			this.x = x;
			this.y = y;
		}
		public String toString() {
			if(x == null) return "rotate(" + degree + ")";
			else return "rotate(" + degree + "," + x + "," + y + ")";
		}
	}
	
	public static class Translate implements Transform {
		private final double x;
		private final double y;
		public Translate(double x, double y) {
			this.x = x;
			this.y = y;
		}
		public String toString() {
			return "translate(" + x + " " + y + ")";
		}
	}
	
	public static class Scale implements Transform {
		private final double x;
		private final double y;
		public Scale(double x, double y) {
			this.x = x;
			this.y = y;
		}
		public String toString() {
			return "scale(" + x + " " + y + ")";
		}
	}
	
	public static class SkewX implements Transform {
		private final double value;
		public SkewX(double value) {
			this.value = value;
		}
		public String toString() {
			return "skewX(" + value + ")";
		}
	}
	
	public static class SkewY implements Transform {
		private final double value;
		public SkewY(double value) {
			this.value = value;
		}
		public String toString() {
			return "skewY(" + value + ")";
		}
	}
}
