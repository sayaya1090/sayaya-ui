package net.sayaya.ui.widget.table2;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FocusWidget;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.sayaya.ui.widget.table2.data.Column;
import net.sayaya.ui.widget.table2.event.ColumnSelectEventHandler.ColumnSelectEvent;

@Setter
@Getter
public class TableCellHeader<T> extends FocusWidget {
	private static TableCellHeader<?> SELECT_START = null;
	private final Element label = DOM.createLabel();
	private Table parent;
	private Column<T> column;
	@Builder
	public TableCellHeader(Table parent, Column<T> col) {
		this.parent = parent;
		this.column = col;
		setElement(Document.get().createTHElement());
		getElement().appendChild(label);
		getElement().setDraggable(Element.DRAGGABLE_TRUE);
		label.setInnerHTML(col.getLabel());
		addMouseDownHandler(evt->{
			evt.preventDefault();
			evt.stopPropagation();
			SELECT_START = this;
		});
		addMouseMoveHandler(evt->{
			evt.preventDefault();
			evt.stopPropagation();
			if(SELECT_START!=null) ColumnSelectEvent.fire(SELECT_START, this);
		});
		addMouseUpHandler(evt->{
			evt.preventDefault();
			evt.stopPropagation();
			ColumnSelectEvent.fire(SELECT_START, this);
			SELECT_START = null;
		});
	//	addAttachHandler(evt->onAttach());
	}
	
	void setStyleSelect() {
		
	}
}
