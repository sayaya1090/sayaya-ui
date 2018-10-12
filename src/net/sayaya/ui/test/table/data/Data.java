package net.sayaya.ui.test.table.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;

import lombok.Builder;

public class Data {
	private final List<Map<String, Value>> values = new ArrayList<>();
	
	public Value getValue(int row, String key) {
		if(row >= values.size()) return null;
		return values.get(row).get(key);
	}
	
	public Data setValue(int rowIdx, String key, Value value) {
		while(rowIdx >= values.size()) {
			values.add(new HashMap<>());
			GWT.log(rowIdx + "," + values.size());
		}
		Map<String, Value> row = values.get(rowIdx);
		row.put(key, value);
		return this;
	}
	
	public int getRowCount() {
		return values.size();
	}
	
	@lombok.Data
	public static final class Value {
		private final Object source;
		private Object value;
		
		@Builder
		public Value(Object source, Object value) {
			this.source = source;
			this.value = value;
		}
		
		public Value(Object source) {
			this(source, null);
		}
		
		public Value() {
			this(null);
		}
		
		public boolean isChanged() {
			if(source==null) return value!=null;
			return !source.equals(value);
		}
	}
}
