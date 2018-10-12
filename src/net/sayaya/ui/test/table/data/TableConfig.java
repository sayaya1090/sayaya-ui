package net.sayaya.ui.test.table.data;

import java.util.LinkedList;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
public class TableConfig {
	private Integer rowMin;		// 한번에 표시할 Row 수 최소값
	private Integer rowMax;		// 한번에 표시할 Row 수 최대값
	private int fixedRowTop = 0;// Top Row 고정
	private int fixedColumnLeft = 0;
	private boolean manualRowResize = true;
	private boolean manualColumnResize = true;
	private boolean manualRowMove = true;
	private boolean manualColumnMove = true;
	private boolean hasFilter = true;
	private boolean autoRowSize = true;
	private boolean autoColumnSize = true;
	private final List<Column> columns = new LinkedList<>();
	
	@Builder
	public TableConfig(Integer rowMin, Integer rowMax, int fixedRowTop, int fixedColumnLeft, boolean manualRowResize, boolean manualColumnResize
	, boolean manualRowMove, boolean manualColumnMove, boolean hasFilter, boolean autoRowSize, boolean autoColumnSize, Column... columns) {
		this.rowMin = rowMin;
		this.rowMax = rowMax;
		this.fixedRowTop = fixedRowTop;
		this.fixedColumnLeft = fixedColumnLeft;
		if(columns!=null) for(Column column: columns) this.columns.add(column);
	}
}
