package net.sayaya.ui.widget.table2;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.RequiresResize;

import lombok.Builder;
import net.sayaya.ui.widget.table2.data.Address;
import net.sayaya.ui.widget.table2.data.DataSource;
import net.sayaya.ui.widget.table2.data.Range;
import net.sayaya.ui.widget.table2.data.TableConfig;
import net.sayaya.ui.widget.table2.data.TableState;
import net.sayaya.ui.widget.table2.data.Value;
import net.sayaya.ui.widget.table2.event.CellSelectEventHandler.CellSelectEvent;
import net.sayaya.ui.widget.table2.style.StyleTable;

public class Table extends FocusWidget implements RequiresResize {
	private final Element element = Document.get().createDivElement();
	private final Element header = Document.get().createTableElement();
	private final Element thead = Document.get().createTHeadElement();
	private final Element viewport = Document.get().createDivElement();
	private final Element virtualTable = Document.get().createDivElement();
	private final Element contents = Document.get().createTableElement();
	private final Element colgroup = Document.get().createColGroupElement();
	private final Element body = Document.get().createTBodyElement();
	private final RangeSelect rangeSelect = new RangeSelect(this);
	private TableState state = TableState.IDLE;
	private TableConfig config;
	private DataSource source;
	private TableRowHeader headers;
	private final static int ROW_HEIGHT_APPROXIMATE = 22;
	private long offset = 0;
	private long cursor = 0;
	private long rowMax = 0;
	private int pageSize = 80;
	private int fetchSize = pageSize * 2;
	private Map<Long, TableRow> rowMapCache = new HashMap<>();
	
	@Builder
	public Table(TableConfig config, DataSource source) {
		this.config = config;
		this.source = source;
		setElement(element);
		layout();
		style();
		Event.sinkEvents(viewport, Event.ONSCROLL);
		Event.setEventListener(viewport, evt->{
			evt.preventDefault();
			evt.stopPropagation();
			setViewport();
		});
		headers = TableRowHeader.builder().table(this).columns(config.getColumns()).build(); 
		thead.appendChild(headers.getElement());
		for(int i = 0; i < config.getColumns().size(); ++i) {
			Element cg = Document.get().createColElement();
			colgroup.appendChild(cg);
		}
		source.getRowCount(size->{
			rowMax = size;
			virtualTable.getStyle().setHeight(size * ROW_HEIGHT_APPROXIMATE, Unit.PX);
			source.getValues(0, fetchSize, rst->{
				TreeMap<Long, Map<String, Value<?>>> sorted = new TreeMap<>();
				sorted.putAll(rst);
				for(Entry<Long, Map<String, Value<?>>> data: sorted.entrySet()) {
					TableRow tr = createRow(headers, data.getKey(), data.getValue());
					body.appendChild(tr.getElement());
				}
			});
		});
	}
	
	private void layout() {
		element.appendChild(header);
		element.appendChild(viewport);
		viewport.appendChild(virtualTable);
		virtualTable.appendChild(contents);
		virtualTable.appendChild(rangeSelect.getElement());
		header.appendChild(thead);
		contents.appendChild(colgroup);
		contents.appendChild(body);
	}
	
	private void style() {
		header.setClassName(StyleTable.GSS.header());
		viewport.setClassName(StyleTable.GSS.viewport());
		virtualTable.setClassName(StyleTable.GSS.field());
		contents.setClassName(StyleTable.GSS.table());
		contents.getStyle().setPosition(Position.RELATIVE);
	}
	private boolean criticalSection = false;
	private void setViewport() {
		if(criticalSection) return;
		criticalSection = true;
		int scroll = viewport.getScrollTop();
		long c = scroll / ROW_HEIGHT_APPROXIMATE;
		if(c > rowMax-pageSize) c = rowMax-pageSize;
		cursor = c;
		if(cursor > offset+fetchSize || cursor + pageSize < offset) {
			offset = Math.max(0, cursor-(fetchSize-pageSize)/2);
			source.getValues(offset, offset+fetchSize, rst->{
				body.removeAllChildren();
				TreeMap<Long, Map<String, Value<?>>> sorted = new TreeMap<>();
				sorted.putAll(rst);
				for(Entry<Long, Map<String, Value<?>>> data: sorted.entrySet()) {
					TableRow tr = createRow(headers, data.getKey(), data.getValue());
					body.appendChild(tr.getElement());
				}
				long top = offset * ROW_HEIGHT_APPROXIMATE;
				contents.getStyle().setTop(top, Unit.PX);
				onResize();
				criticalSection = false;
			});
		} else if(cursor < offset + (fetchSize-pageSize)/2) {
			offset = Math.max(0, cursor-(fetchSize-pageSize)/2);
			source.getValues(offset, offset+fetchSize, rst->{
				body.removeAllChildren();
				TreeMap<Long, Map<String, Value<?>>> sorted = new TreeMap<>();
				sorted.putAll(rst);
				for(Entry<Long, Map<String, Value<?>>> data: sorted.entrySet()) {
					TableRow tr = createRow(headers, data.getKey(), data.getValue());
					body.appendChild(tr.getElement());
				}
				long top = offset * ROW_HEIGHT_APPROXIMATE;
				contents.getStyle().setTop(top, Unit.PX);
				onResize();
				criticalSection = false;
			});
		} else if(cursor > offset + (fetchSize-pageSize)/2) {
			offset = Math.max(0, cursor-(fetchSize-pageSize)/2);
			source.getValues(offset, offset+fetchSize, rst->{
				body.removeAllChildren();
				TreeMap<Long, Map<String, Value<?>>> sorted = new TreeMap<>();
				sorted.putAll(rst);
				for(Entry<Long, Map<String, Value<?>>> data: sorted.entrySet()) {
					TableRow tr = createRow(headers, data.getKey(), data.getValue());
					body.appendChild(tr.getElement());
				}
				long top = offset * ROW_HEIGHT_APPROXIMATE;
				contents.getStyle().setTop(top, Unit.PX);
				onResize();
				criticalSection = false;
			});
		} else criticalSection = false;
	}
	
	private TableRow createRow(TableRowHeader header, long row, Map<String, Value<?>> values) {
		TableRow tr = TableRow.builder().table(this).header(header).row(row).values(values).build();
		for(TableCell<?> cell: tr.getCells()) {
			int col = header.getIndex(cell.getKey());
			cell.addMouseDownHandler(evt->{
				evt.preventDefault();
				evt.stopPropagation();
				Address selectStart = Address.builder().row(row).col(col).build();
				Range range = Range.builder().start(selectStart).end(selectStart).build();
				rangeSelect.fire(new CellSelectEvent(range));
				state = TableState.SELECTING;
			});
			cell.addMouseMoveHandler(evt->{
				evt.preventDefault();
				evt.stopPropagation();
				if(state == TableState.SELECTING) {
					Range prev = rangeSelect.getRange();
					Address selectStart = Address.builder().row(prev.getStartY()).col(prev.getStartX()).build();
					Address selectEnd = Address.builder().row(row).col(col).build();
					Range range = Range.builder().start(selectStart).end(selectEnd).build();
					rangeSelect.fire(new CellSelectEvent(range));
				}
				/*else if(state == TableState.COPYING) {
					int px = selectRange.square.getAbsoluteLeft();
					int py = selectRange.square.getAbsoluteTop();
					int diffX = evt.getClientX() - px;
					int diffY = evt.getClientY() - py;
					
					int r = 0;
					String k = null;
					if(diffX > diffY) {
						k = cell.getKey();
						r = cellCopySourceEnd.getRow();
					} else {
						k = cellCopySourceEnd.getKey();
						r = cell.getRow();
					}
					if(diffX < 0) k = cellCopySourceEnd.getKey();
					if(diffY < 0) r = cellCopySourceEnd.getRow();
					fire(new CellCopyEvent(cellCopySourceStart, cellCopySourceEnd, cellCopySourceStart, cells.get(r, k)));
				}*/
			});
			cell.addMouseUpHandler(evt->{
				evt.preventDefault();
				evt.stopPropagation();
				if(state == TableState.SELECTING) {
					Range prev = rangeSelect.getRange();
					Address selectStart = Address.builder().row(prev.getStartY()).col(prev.getStartX()).build();
					Address selectEnd = Address.builder().row(row).col(col).build();
					Range range = Range.builder().start(selectStart).end(selectEnd).build();
					rangeSelect.fire(new CellSelectEvent(range));
					state = TableState.IDLE;
				}/* else if(state == TableState.COPYING) {
					state = TableState.IDLE;
					// Copy
					copyRange.setVisible(false);
				}*/
			});
		}
		rowMapCache.put(row, tr);
		return tr;
	}
	
	@Override
	public void onResize() {	
		if(body.getChildCount() <= 0) return;
		Element tr0 = thead.getElementsByTagName("tr").getItem(0);
		Element tr = body.getElementsByTagName("tr").getItem(0);
		int width = 0;
		for(int i = 0; i < tr0.getChildCount(); ++i) {
			Element td = tr.getChild(i).cast();
			Element th = tr0.getChild(i).cast();
			th.getStyle().setWidth(td.getOffsetWidth()-1, Unit.PX);
			width += td.getOffsetWidth();
		}
		header.getStyle().setWidth(width, Unit.PX);
		viewport.getStyle().setWidth(element.getOffsetWidth(), Unit.PX);
		viewport.getStyle().setHeight(element.getOffsetHeight(), Unit.PX);
	}
	
	public TableRow getRow(long row) {
		return rowMapCache.get(row);
	}
}
