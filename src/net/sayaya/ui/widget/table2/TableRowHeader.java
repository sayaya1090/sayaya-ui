package net.sayaya.ui.widget.table2;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;

import lombok.Builder;
import net.sayaya.ui.widget.table2.data.Column;

@SuppressWarnings({ "unchecked", "rawtypes" })
public final class TableRowHeader extends Widget {
	private final Element element = Document.get().createTRElement(); 
//	private final Table table;
	private final List<Column> columns;
	private final BiMap<String, Integer> columnIndexes = HashBiMap.create();
	
	@Builder
	public TableRowHeader(@NotNull Table table, @NotNull List<Column> columns) {
		setElement(element);
// 		this.table = table;
		this.columns = columns;
		for(int i = 0; i < columns.size(); ++i) {
			Column<?> col = columns.get(i);
			columnIndexes.put(col.getKey(), i);
		}
		addAttachHandler(evt->{
			for(Column col: columns) {
				TableCellHeader<?> header = TableCellHeader.builder().parent(table).col(col).build();
				element.appendChild(header.getElement());
			}
		});
		onAttach();
	}
	
	public List<Column> getColumns() {
		return columns;
	}
	
	public Column<?> getColumn(String key) {
		return columns.get(columnIndexes.get(key));
	}
	
	public int getIndex(String key) {
		return columnIndexes.get(key);
	}
	
	public String getKey(int idx) {
		return columnIndexes.inverse().get(idx);
	}
}
