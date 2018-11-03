package net.sayaya.ui.widget.table2.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import net.sayaya.ui.widget.table2.data.Range;

@FunctionalInterface
public interface CellSelectEventHandler extends EventHandler {
	void fire(CellSelectEvent evt);
	
	public static class CellSelectEvent extends GwtEvent<CellSelectEventHandler> {
		private final static Type<CellSelectEventHandler> TYPE = new Type<CellSelectEventHandler>();
	//	private final TableCell start;
	//	private final TableCell end;
		private final Range range;
		public CellSelectEvent(Range range) {
			this.range = range;
		}
		@Override
		protected void dispatch(CellSelectEventHandler handler) {
			handler.fire(this);
		}
		@Override
		public Type<CellSelectEventHandler> getAssociatedType() {
			return TYPE;
		}
		public Range getRange() {
			return range;
		}
		public static Type<CellSelectEventHandler> getType() {
			return TYPE;
		}
	}
}
