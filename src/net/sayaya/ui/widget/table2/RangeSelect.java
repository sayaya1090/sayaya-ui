package net.sayaya.ui.widget.table2;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Widget;

import net.sayaya.client.test.table.data.Range;
import net.sayaya.client.test.table.event.CellSelectEventHandler;
import net.sayaya.client.test.table.style.StyleTable;

public final class RangeSelect extends Widget implements CellSelectEventHandler {
	private final FocusWidget square = new FocusWidget(DOM.createDiv()) {
		{onAttach();}
	};
	private final Animation run = new Animation() {
		@Override
		protected void onUpdate(double progress) {
			style.setLeft(progress(currentLeft, nextLeft, progress), Unit.PX);
			style.setTop(progress(currentTop, nextTop, progress), Unit.PX);
			style.setWidth(progress(currentWidth, nextWidth, progress), Unit.PX);
			style.setHeight(progress(currentHeight, nextHeight, progress), Unit.PX);
			if(progress >= 1) {
				currentLeft = getElement().getOffsetLeft();
				currentTop = getElement().getOffsetTop();
				currentWidth = getOffsetWidth()-4;
				currentHeight = getOffsetHeight()-4;
			}
		}
			
		private final double progress(int current, int next, double progress) {
			return current + (next-current)*progress;
		}
			
		protected void onCancel() {
			currentLeft = getElement().getOffsetLeft();
			currentTop = getElement().getOffsetTop();
			currentWidth = getOffsetWidth()-4;
			currentHeight = getOffsetHeight()-4;
		}
	};
	private int currentLeft = 0;
	private int currentTop = 0;
	private int currentWidth = 0;
	private int currentHeight = 0;
	private int nextLeft = 0;
	private int nextTop = 0;
	private int nextWidth = 0;
	private int nextHeight = 0;
	private final Element box = Document.get().createDivElement();
	private final Style style = box.getStyle();
	private final Table table;
	private Range range;
	public RangeSelect(Table table) {
		this.table = table;
		setElement(box);
		layout();
		style();
			
	/*	square.addMouseDownHandler(evt->{
			evt.preventDefault();
			evt.stopPropagation();
			TableCell start = selected.stream().findFirst().orElse(null);
			TableCell end = start;
			if(start == null) return;
			int rowMin = start.getRow();
			int colMin = columnIndexes.get(start.getKey());
			int rowMax = rowMin;
			int colMax = colMin;
			for(TableCell cur: selected) {
				int row = cur.getRow();
				int col = columnIndexes.get(cur.getKey());
				if(row < rowMin) {
					start = cur;
					rowMin = row;
					colMin = col;
				} else if(row == rowMin && colMin > col) {
					start = cur;
					rowMin = row;
					colMin = col;
				}
				if(row > rowMax) {
					end = cur;
					rowMax = row;
					colMax = col;
				} else if(row == rowMax && colMax < col) {
					end = cur;
					rowMax = row;
					colMax = col;
				}
			}
			isCopying = true;
			cellCopySourceStart = start;
			cellCopySourceEnd = end;
			copyRange.init(start, end);
			fire(new CellCopyEvent(start, end, start, end));
		});
		square.addMouseUpHandler(evt->{
			evt.preventDefault();
			evt.stopPropagation();
			isCopying = false;
			// Copy
			copyRange.release();
		});*/
	}
		
	private void layout() {
		getElement().appendChild(square.getElement());
	}
		
	private void style() {
		style.setDisplay(Display.NONE);
		setStyleName(StyleTable.GSS.selectRange());
		square.setStyleName(StyleTable.GSS.selectRangeSquare());
	}
	
	public Range getRange() {
		return range;
	}

	@Override
	public void fire(CellSelectEvent evt) {
		if(run.isRunning()) return;
		
		this.range = evt.getRange();
		int sx = range.getStartX();
		long sy = range.getStartY();
		int ex = range.getEndX();
		long ey = range.getEndY();
		if(sx > ex) {
			int tmp = sx;
			sx = ex;
			ex = tmp;
		}
		if(sy > ey) {
			long tmp = sy;
			sy = ey;
			ey = tmp;
		}
		
		TableCell<?> start = table.getRow(sy).getCell(sx);
		TableCell<?> end = table.getRow(ey).getCell(ex);

		int sl = start.getElement().getOffsetLeft();
		int st = start.getElement().getOffsetTop();
		int el = end.getElement().getOffsetLeft();
		int et = end.getElement().getOffsetTop();
		int ew = end.getElement().getOffsetWidth();
		int eh = end.getElement().getOffsetHeight();
		
		nextLeft = sl;
		nextTop = st;
		nextWidth = el+ew-sl-2;
		nextHeight = et+eh-st-2;
		
		if(!isVisible()) {
			currentLeft = sl;
			currentTop = st;
			currentWidth = el+ew-sl-1;
			currentHeight = et+eh-st-1;
			style.setLeft(currentLeft, Unit.PX);
			style.setTop(currentTop, Unit.PX);
			style.setWidth(currentWidth, Unit.PX);
			style.setHeight(currentHeight, Unit.PX);
			setVisible(true);
		} else run.run(100);
	}
}
