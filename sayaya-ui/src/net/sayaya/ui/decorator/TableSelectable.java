package net.sayaya.ui.decorator;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.TextAlign;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.layout.client.Layout.Layer;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SelectionChangeEvent.HasSelectionChangedHandlers;

import net.sayaya.ui.style.StyleChart;
import net.sayaya.ui.widget.CheckBox;
import net.sayaya.ui.widget.SpreadSheet;
import net.sayaya.ui.widget.SpreadSheet.ColumnInfo;
import net.sayaya.ui.widget.SpreadSheet.Data;
import net.sayaya.ui.widget.SpreadSheet.SheetSetting;
import net.sayaya.ui.widget.SpreadSheet.SpreadSheetTable;
import net.sayaya.ui.widget.table.TableBase;

public interface TableSelectable<T> extends TableBase<T>, HasSelectionChangedHandlers {
	public static <T> TableSelectable<T> decorate(TableBase<T> widget, BiFunction<Integer, Data, T> mapper) {
		return new TableSelectableImpl<T>(widget, mapper);
	}
	
	T[] getSelected();
	
	static final class TableSelectableImpl<T> extends ResizeComposite implements TableSelectable<T> {
		private final TableBase<T> base;
		private final BiFunction<Integer, Data, T> mapper;
		private final ColumnInfo columnCheckbox = new ColumnInfo().setData("\t").setRenderer((SpreadSheetTable instance, Element td, int row, int col, String prop, Object value, ColumnInfo columnInfo)->{
			td.removeAllChildren();
			if(value == null) td.setInnerHTML("");
			Boolean cast = (Boolean)value;
			CheckBox check = new CheckBox(22).setValue(cast, true);
			Element elem = check.getElement();
			Event.setEventListener(td, evt->{
				evt.preventDefault();
				if(evt.getTypeInt() == Event.ONCLICK) {
					boolean current = instance.getSettings().getData()[row].get(prop);
					instance.getSettings().getData()[row].put(prop, !current);
					td.removeAllChildren();
					td.appendChild(check.setValue(!current).getElement());
					if(!current) {
						instance.selectRows(row);
						for(int i = 0; i < instance.countCols(); ++i) {
							Element elem2 = instance.getCell(row, i, true);
							if(elem2!=null) elem2.addClassName(StyleChart.GSS.selectedRow());
						}
					} else for(int i = 0; i < instance.countCols(); ++i) {
						Element elem2 = instance.getCell(row, i, true);
						if(elem2!=null) elem2.removeClassName(StyleChart.GSS.selectedRow());
					}
					SelectionChangeEvent.fire(this);
				}
			});
			Event.sinkEvents(td, Event.ONCLICK);
			td.appendChild(elem);
			td.setAttribute("align", "center");
			columnInfo.setReadOnly(true);
			
			Scheduler.get().scheduleDeferred(()->{
				if(cast) {
					for(int i = 0; i < instance.countCols(); ++i) {
						Element elem2 = instance.getCell(row, i, true);
						if(elem2!=null) elem2.addClassName(StyleChart.GSS.selectedRow());
					}
				} else for(int i = 0; i < instance.countCols(); ++i) {
					Element elem2 = instance.getCell(row, i, true);
					if(elem2!=null) elem2.removeClassName(StyleChart.GSS.selectedRow());
				}
			});
			return td;
		});
		private final Element checkAll = DOM.createDiv();
		private final CheckBox tmp = new CheckBox(22).setValue(false, true);
		private final EventBus bus = new SimpleEventBus();
		private TableSelectableImpl(TableBase<T> base, BiFunction<Integer, Data, T> mapper) {
			this.base = base;
			this.mapper = mapper;
			initWidget(base.asWidget());
			style();
			checkAll.appendChild(tmp.getElement());
		}
		
		private final String checkId = DOM.createUniqueId();
		private void style() {
			checkAll.getStyle().setDisplay(Display.BLOCK);
			checkAll.getStyle().setTextAlign(TextAlign.CENTER);
			tmp.getElement().getStyle().setDisplay(Display.INLINE_BLOCK);
			tmp.getElement().getStyle().setTop(6, Unit.PX);
			tmp.getElement().getStyle().setLeft(0, Unit.PX);
			Element square = tmp.getElement().getChild(1).getChild(0).cast();
			square.getStyle().setBackgroundColor("#FFFFFF");
			square.getStyle().setWidth(20, Unit.PX);
			square.getStyle().setHeight(20, Unit.PX);
			Element check = square.getChild(0).cast();
			check.setId(checkId);
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public T[] getSelected() {
			Data[] data = getSetting().getData();
			return (T[]) IntStream.range(0, data.length).filter(i->{
				Data datum = data[i];
				return datum.get(columnCheckbox.getData());
			}).mapToObj(i->{
				Data datum = data[i];
				return mapper.apply(i, datum);
			}).toArray(Object[]::new);
		}
		
		@Override
		public TableSelectable<T> setColumns(ColumnInfo[] columns) {
			ColumnInfo[] proxy = new ColumnInfo[columns.length+1];
			proxy[0] = columnCheckbox;
			for(int i = 0; i < columns.length; ++i) proxy[i+1] = columns[i];
			base.setColumns(proxy);
			String[] headers = base.getSetting().getColHeaders();
			headers[0] = checkAll.getString();
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
			if(value == null) return new Data();
			Data data = base.parse(value).put(columnCheckbox.getData(), false);
			return data;
		}

		@Override
		public TableSelectable<T> setValues(Data... values) {
			Arrays.stream(values).forEach(value->value.put(columnCheckbox.getData(), false));
			updateCheckbox(false);
			base.setValues(values);
			
			DOM.setEventListener(getTable().getElement(), evt->{
				if(evt.getTypeInt() == Event.ONCLICK) {
					evt.preventDefault();
					Element target = Element.as(((NativeEvent) evt).getEventTarget());
					if(checkId.equals(target.getId())) {
						boolean value = !tmp.getValue();
						for(Data data: getSetting().getData()) data.put(columnCheckbox.getData(), value);
						updateCheckbox(value);
						update();
						SelectionChangeEvent.fire(this);
					}
				}
			});
			DOM.sinkEvents(getTable().getElement(), Event.ONCLICK);
			return this;
		}
		
		private void updateCheckbox(boolean value) {
			tmp.setValue(value, true);
			checkAll.removeAllChildren();
			checkAll.appendChild(tmp.getElement());
			String[] headers = base.getSetting().getColHeaders();
			headers[0] = checkAll.getString();
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
		public HandlerRegistration addSelectionChangeHandler(Handler handler) {
			return bus.addHandler(SelectionChangeEvent.getType(), handler);
		}
		
		@Override
		public void fireEvent(GwtEvent<?> event) {
			if(event instanceof SelectionChangeEvent) bus.fireEvent(event);
			else super.fireEvent(event);
		}
	}
}
