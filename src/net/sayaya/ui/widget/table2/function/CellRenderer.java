package net.sayaya.ui.widget.table2.function;

import com.google.gwt.dom.client.Element;

import net.sayaya.ui.widget.table2.data.Value;

@FunctionalInterface
public interface CellRenderer<T> {
	void render(Element td, Value<T> value);
}