package net.sayaya.ui.widget.table2.data;

import lombok.Builder;
import lombok.Data;

@Data
public class Address {
	private final long row;
	private final int col;
	@Builder
	public Address(long row, int col) {
		this.row = row;
		this.col = col;
	}
}
