package net.sayaya.ui.test.table;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;

import net.sayaya.ui.test.table.data.Data.Value;

public class TableCell extends Widget {
	private final Element label = DOM.createLabel();
	public TableCell(Value value) {
		setElement(Document.get().createTDElement());
		getElement().appendChild(label);
		getElement().setAttribute("tabindex", "0");
		getElement().setAttribute("contenteditable", "true");
		if(value!=null) label.setInnerHTML(value.getValue()+"");
	}
}
