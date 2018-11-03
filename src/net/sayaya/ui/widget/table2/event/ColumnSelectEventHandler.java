package net.sayaya.ui.widget.table2.event;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.SimpleEventBus;

import net.sayaya.ui.widget.table2.TableCellHeader;

public interface ColumnSelectEventHandler extends EventHandler {
	void onSelect(ColumnSelectEvent evt);
	
	public static class ColumnSelectEvent extends GwtEvent<ColumnSelectEventHandler> {
		private static final EventBus EVENT_BUS = GWT.create(SimpleEventBus.class);
		private static Type<ColumnSelectEventHandler> TYPE = new Type<ColumnSelectEventHandler>();
		private final TableCellHeader start;
		private final TableCellHeader end;
		private ColumnSelectEvent(TableCellHeader start, TableCellHeader end) {
			this.start = start;
			this.end = end;
		}
		@Override
		protected void dispatch(ColumnSelectEventHandler handler) {
			handler.onSelect(this);
		}
		@Override
		public Type<ColumnSelectEventHandler> getAssociatedType() {
			return TYPE;
		}
		public TableCellHeader getSourceStart() {
			return start;
		}
		public TableCellHeader getSourceEnd() {
			return end;
		}
		public static Type<ColumnSelectEventHandler> getType() {
			return TYPE;
		}
		public static void register(ColumnSelectEventHandler handler) {
			EVENT_BUS.addHandler(TYPE, handler);
		}
		public static void fire(TableCellHeader start, TableCellHeader end) {
			EVENT_BUS.fireEvent(new ColumnSelectEvent(start, end));
		}
	}
}
