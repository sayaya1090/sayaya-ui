package net.sayaya.ui.widget.table2.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import net.sayaya.ui.widget.table2.TableCell;

@FunctionalInterface
public interface CellCopyEventHandler extends EventHandler {
	void fire(CellCopyEvent evt);
	
	public static class CellCopyEvent extends GwtEvent<CellCopyEventHandler> {
		private static Type<CellCopyEventHandler> TYPE = new Type<CellCopyEventHandler>();
		private final TableCell sourceStart;
		private final TableCell sourceEnd;
		private final TableCell destStart;
		private final TableCell destEnd;
		public CellCopyEvent(TableCell sourceStart, TableCell sourceEnd, TableCell destStart, TableCell destEnd) {
			this.sourceStart = sourceStart;
			this.sourceEnd = sourceEnd;
			this.destStart = destStart;
			this.destEnd = destEnd;
		}
		@Override
		protected void dispatch(CellCopyEventHandler handler) {
			handler.fire(this);
		}
		@Override
		public Type<CellCopyEventHandler> getAssociatedType() {
			return TYPE;
		}
		public TableCell getSourceStart() {
			return sourceStart;
		}
		public TableCell getSourceEnd() {
			return sourceEnd;
		}
		public TableCell getDestStart() {
			return destStart;
		}
		public TableCell getDestEnd() {
			return destEnd;
		}
		public static Type<CellCopyEventHandler> getType() {
			return TYPE;
		}
	}
}
