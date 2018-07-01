package net.sayaya.ui.decorator;

import java.util.function.Function;

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.layout.client.Layout.Layer;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;

import net.sayaya.ui.widget.SpreadSheet;
import net.sayaya.ui.widget.SpreadSheet.ColumnInfo;
import net.sayaya.ui.widget.SpreadSheet.Data;
import net.sayaya.ui.widget.SpreadSheet.SheetSetting;
import net.sayaya.ui.widget.table.TableBase;

public interface TableSelectableUpdatable<T> extends TableSelectable<T>, TableUpdatable<T> {
	public static <T> TableSelectableUpdatable<T> decorate(TableBase<T> widget, Function<Data, T> mapper) {
		return new TableSelectableUpdatableImpl<T>(widget, mapper);
	}
	static final class TableSelectableUpdatableImpl<T> extends ResizeComposite implements TableSelectableUpdatable<T> {
		private final TableSelectable<T> selectable;
		private final TableUpdatable<T> updatable;
		public TableSelectableUpdatableImpl(TableBase<T> base, Function<Data, T> mapper) {
			selectable = TableSelectable.decorate(base, mapper);
			updatable = TableUpdatable.decorate(selectable, mapper);
			initWidget(updatable.asWidget());
		}
		@Override
		public T[] getSelected() {
			return selectable.getSelected();
		}
		@Override
		public TableSelectableUpdatableImpl<T> setColumns(ColumnInfo[] columns) {
			updatable.setColumns(columns);
			return this;
		}
		@Override
		public SheetSetting getSetting() {
			return updatable.getSetting();
		}
		@Override
		public void update() {
			updatable.update();
		}
		@Override
		public Data parse(T data) {
			return updatable.parse(data);
		}
		@Override
		public TableSelectableUpdatableImpl<T> setValues(Data... values) {
			updatable.setValues(values);
			return this;
		}
		@Override
		public SpreadSheet getTable() {
			return updatable.getTable();
		}
		@Override
		public void onAnimationComplete() {
			updatable.onAnimationComplete();
		}
		@Override
		public void onLayout(Layer layer, double progress) {
			updatable.onLayout(layer, progress);
		}
		@Override
		public HandlerRegistration addSelectionChangeHandler(Handler handler) {
			return selectable.addSelectionChangeHandler(handler);
		}
		@Override
		public T[] getUpdated() {
			return updatable.getUpdated();
		}
		@Override
		public HandlerRegistration addValueChangeHandler(ValueChangeHandler<T> handler) {
			return updatable.addValueChangeHandler(handler);
		}
	}
}
