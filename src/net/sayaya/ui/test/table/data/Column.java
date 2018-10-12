package net.sayaya.ui.test.table.data;

import net.sayaya.ui.test.table.function.CellEditor;
import net.sayaya.ui.test.table.function.CellRenderer;
import net.sayaya.ui.widget.SpreadSheet.Validator;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@RequiredArgsConstructor
@ToString(exclude= {"renderer", "editor", "validator"})
@EqualsAndHashCode(exclude= {"renderer", "editor", "validator"})
public class Column {
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
	private CellRenderer renderer;
	@NonNull
	private CellEditor editor;
	private Validator validator;
	
	@Builder
	public Column(String label, String key, DataType type, Integer widthMin, Integer widthMax, String format) {
		this.label = label;
		this.key = key;
		this.type = type;
		this.widthMin = widthMin;
		this.widthMax = widthMax;
		this.format = format;
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

