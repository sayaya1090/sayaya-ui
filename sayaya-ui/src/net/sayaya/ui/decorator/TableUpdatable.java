package net.sayaya.ui.decorator;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.layout.client.Layout.Layer;
import com.google.gwt.user.client.ui.ResizeComposite;

import net.sayaya.ui.widget.SpreadSheet;
import net.sayaya.ui.widget.SpreadSheet.ColumnInfo;
import net.sayaya.ui.widget.SpreadSheet.Data;
import net.sayaya.ui.widget.SpreadSheet.SheetSetting;
import net.sayaya.ui.widget.table.TableBase;

public interface TableUpdatable<T, U> extends TableBase<T>, HasValueChangeHandlers<U> {
	public static <T, U> TableUpdatable<T, U> decorate(TableBase<T> widget, BiFunction<Integer, Data, U> mapper) {
		return new TableUpdatableImpl<T, U>(widget, mapper);
	}
	
	U[] getUpdated();
	
	static final class TableUpdatableImpl<T, U> extends ResizeComposite implements TableUpdatable<T, U> {
		private final TableBase<T> base;
		private final BiFunction<Integer, Data, U> mapper;
		private final EventBus bus = new SimpleEventBus();
		private TableUpdatableImpl(TableBase<T> base, BiFunction<Integer, Data, U> mapper) {
			this.base = base;
			this.mapper = mapper;
			initWidget(base.asWidget());
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public U[] getUpdated() {
			List<String> headers = Arrays.stream(base.getSetting().getColumns()).map(c->c.getData()).collect(Collectors.toList());
			Data[] data = getSetting().getData();
			return (U[]) IntStream.range(0, data.length).filter(i->{
				Data datum = data[i];
				for(String header: headers) if(datum.isChanged(header)) return true;
				return false;
			}).mapToObj(i->{
				Data datum = data[i];
				return mapper.apply(i, datum);
			}).toArray(Object[]::new);
		}
		
		@Override
		public TableUpdatable<T, U> setColumns(ColumnInfo[] columns) {
			base.setColumns(columns);
			return this;
		}

		@Override
		public SheetSetting getSetting() {
			return base.getSetting();
		}
		
		@Override
		public SpreadSheet getTable() {
			return base.getTable();
		}

		@Override
		public void update() {
			base.update();
		}

		@Override
		public Data parse(T value) {
			return base.parse(value);
		}

		@Override
		public TableUpdatable<T, U> setValues(Data... values) {
			base.setValues(values);
			return this;
		}
		
		@Override
		public void onAnimationComplete() {
			base.onAnimationComplete();
		}

		@Override
		public void onLayout(Layer layer, double progress) {
			base.onLayout(layer, progress);
		}

		@Override
		public HandlerRegistration addValueChangeHandler(ValueChangeHandler<U> handler) {
			return bus.addHandler(ValueChangeEvent.getType(), handler);
		}
		
		@Override
		public void fireEvent(GwtEvent<?> event) {
			if(event instanceof ValueChangeEvent) bus.fireEvent(event);
			else super.fireEvent(event);
		}

	}

}
