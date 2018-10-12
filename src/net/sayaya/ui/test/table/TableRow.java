package net.sayaya.ui.test.table;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.Widget;

public class TableRow extends Widget {
	public TableRow() {
		setElement(Document.get().createTRElement());
	}
}
