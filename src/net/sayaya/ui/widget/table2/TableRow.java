package net.sayaya.ui.widget.table2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;

import lombok.Builder;
import net.sayaya.ui.widget.table2.data.Column;
import net.sayaya.ui.widget.table2.data.Value;

@SuppressWarnings({"rawtypes", "unchecked"})
public final class TableRow extends Widget {
	private final Element element = Document.get().createTRElement(); 
	private final List<TableCell> cells;
	
	@Builder
	public TableRow(@NotNull Table table, @NotNull TableRowHeader header, @NotNull Map<String, Value<?>> values, long row) {
		setElement(element);
		List<TableCell> cells = new ArrayList<>();
		for(Column col: header.getColumns()) {
			String key = col.getKey();
			Value value = values!=null?values.get(key):null;
			TableCell cell = TableCell.builder().table(table).column(col).value(value).row(row).key(key).build();
			element.appendChild(cell.getElement());
			cells.add(cell);
		}
		this.cells = cells;
		onAttach();
	}
	
	public List<TableCell> getCells() {
		return cells;
	}
	
	public <T> TableCell<T> getCell(int col) {
		return (TableCell<T>) cells.get(col);
	}
}
