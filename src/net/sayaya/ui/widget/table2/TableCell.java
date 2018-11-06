package net.sayaya.ui.widget.table2;

import javax.validation.constraints.NotNull;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.FocusWidget;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.sayaya.ui.widget.table2.data.Column;
import net.sayaya.ui.widget.table2.data.Value;

@Setter
@Getter
public class TableCell<T> extends FocusWidget {
	private final Table table;
	private final Column<T> column;
	private final Value<T> value;
	private long row = 0;
	private String key;

	@Builder
	public TableCell(@NotNull Table table, @NotNull Column<T> column, @NotNull Value<T> value, long row, @NotNull String key, Integer rowspan, Integer colspan) {
		this.table = table;
		this.column = column;
		this.value = value;
		this.row = row;
		this.key = key;
		setElement(Document.get().createTDElement());
		if(rowspan!=null && rowspan > 1) this.getElement().setAttribute("rowspan", String.valueOf(rowspan));
		if(colspan!=null && colspan > 1) this.getElement().setAttribute("colspan", String.valueOf(colspan));
		if(column!=null && column.getRenderer()!=null) column.getRenderer().render(getElement(), value);
		onAttach();
	}
}
