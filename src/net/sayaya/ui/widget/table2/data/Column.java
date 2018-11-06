package net.sayaya.ui.widget.table2.data;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.sayaya.ui.widget.table2.function.CellEditor;
import net.sayaya.ui.widget.table2.function.CellRenderer;
import net.sayaya.ui.widget.table2.function.Validator;

@Getter
@Setter
@RequiredArgsConstructor
@ToString(exclude= {"renderer", "editor", "validator"})
@EqualsAndHashCode(exclude= {"renderer", "editor", "validator"})
public class Column<T> {
	@NonNull
	private String label;
	@NonNull
	private String key;
	@NonNull
	private DataType type = DataType.String;
	private Integer widthMin;
	private Integer widthMax;
	private String format;
	@NonNull
	private Align align = Align.Left;
	private boolean readOnly = false;
	@NonNull
	private CellRenderer<T> renderer;
	@NonNull
	private CellEditor<T> editor;
	private Validator validator;
	
	@SuppressWarnings("rawtypes")
	private final static CellRenderer rendererDefault = (td, value)->{
		if(value!=null && value.getValue()!=null) td.setInnerHTML(value.getValue().toString());
	};
	@Builder
	public Column(String label, String key, DataType type, Integer widthMin, Integer widthMax, String format, CellRenderer<T> renderer, CellEditor<T> editor) {
		this.label = label;
		this.key = key;
		this.type = type;
		this.widthMin = widthMin;
		this.widthMax = widthMax;
		this.format = format;
		if(renderer!=null) this.renderer = renderer;
		else this.renderer = rendererDefault;
	}
	
	public String getKey() {
		if(this.key == null) return getLabel();
		else return this.key;
	}
	
	public enum DataType {
		String, Text, Number, Date, List, Image, Link, Formula, Widget
	}
	
	public enum Align {
		Left, Center, Right
	}
}

