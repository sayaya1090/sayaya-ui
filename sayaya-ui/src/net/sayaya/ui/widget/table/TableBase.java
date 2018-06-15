package net.sayaya.ui.widget.table;

import java.util.Arrays;

import com.google.gwt.layout.client.Layout.AnimationCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.gwt.user.client.ui.RequiresResize;

import net.sayaya.ui.widget.SpreadSheet.ColumnInfo;
import net.sayaya.ui.widget.SpreadSheet.Data;
import net.sayaya.ui.widget.SpreadSheet.SheetSetting;

public interface TableBase<T> extends IsWidget, RequiresResize, ProvidesResize, AnimationCallback {
	TableBase<T> setColumns(ColumnInfo[] columns);
	SheetSetting getSetting();
	void update();
	Data parse(T data);
	TableBase<T> setValues(Data... values);
	default TableBase<T> setValues(@SuppressWarnings("unchecked") T... values) {
		if(values!=null && values.length > 0) {
			Data[] data = Arrays.stream(values).map(value->parse(value)).toArray(Data[]::new);
			return setValues(data);
		} else return setValues(new Data[] {});
	}
}
