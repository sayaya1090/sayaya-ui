package net.sayaya.ui.widget.table2.data;

import lombok.Builder;
import lombok.Data;

@Data
public class Range {
	private int startX;
	private long startY;
	private int endX;
	private long endY;
	
	@Builder
	public Range(Address start, Address end) {
		this.startX = start.getCol();
		this.startY = start.getRow();
		this.endX = end.getCol();
		this.endY = end.getRow();
	}
}
