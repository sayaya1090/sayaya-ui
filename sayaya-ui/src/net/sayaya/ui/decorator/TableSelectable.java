package net.sayaya.ui.decorator;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.Visibility;
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
	public static <T> TableSelectable<T> decorate(TableBase<T> widget, Function<Data, T> mapper) {
		return new TableSelectableImpl<T>(widget, mapper);
	}
	
	T[] getSelected();
	
	static final class TableSelectableImpl<T> extends ResizeComposite implements TableSelectable<T> {
		private final TableBase<T> base;
		private final Function<Data, T> mapper;
		private final String checkHeaderId = DOM.createUniqueId();
		private final ColumnInfo columnCheckbox = new ColumnInfo().setData("<div id='" + checkHeaderId + "'></div>").setRenderer((SpreadSheetTable instance, Element td, int row, int col, String prop, Object value, ColumnInfo columnInfo)->{
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
			return td;
		});
		private final Element checkAll = DOM.createDiv();
		private final CheckBox tmp = new CheckBox(22).setValue(false, true);
		private final EventBus bus = new SimpleEventBus();
		private TableSelectableImpl(TableBase<T> base, Function<Data, T> mapper) {
			this.base = base;
			this.mapper = mapper;
			base.getSetting().setManualColumnMove(false);
			base.getSetting().setManualColumnResize(false);
			initWidget(base.asWidget());
			style();
			DOM.setEventListener(checkAll, evt->{
				if(evt.getTypeInt() == Event.ONCLICK) {
					evt.preventDefault();
					boolean value = !tmp.getValue();
					tmp.setValue(value);
					for(Data data: getSetting().getData()) data.put(columnCheckbox.getData(), value);
					checkAll.removeAllChildren();
					checkAll.appendChild(tmp.getElement());
					SelectionChangeEvent.fire(this);
					update();
				}
			});
			checkAll.appendChild(tmp.getElement());
			DOM.sinkEvents(checkAll, Event.ONCLICK);
			checkAll.getStyle().setVisibility(Visibility.HIDDEN);
			
			base.getSetting().setAfterRender(isForced->{
				Element checkHeader = DOM.getElementById(checkHeaderId);
				if(checkHeader!=null) checkHeader.appendChild(checkAll);
				try {
					for(int i = 0; i < getTable().countRows(); ++i)
						if((boolean) getSetting().getData()[i].get(columnCheckbox.getData()))
							Arrays.stream(getTable().getCells(i, false)).filter(Objects::nonNull).forEach(elem->elem.addClassName(StyleChart.GSS.selectedRow()));
				} catch(Exception e) {}
			});
		}
		
		private void style() {
			checkAll.getStyle().setPosition(Position.RELATIVE);
			checkAll.getStyle().setLeft(-3, Unit.PX);
			checkAll.getStyle().setTop(6, Unit.PX);
			checkAll.getStyle().setWidth(20, Unit.PX);
			checkAll.getStyle().setHeight(20, Unit.PX);
			checkAll.getStyle().setZIndex(999);
			checkAll.getStyle().setBackgroundColor("#FFFFFF");
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public T[] getSelected() {
			return (T[]) Arrays.stream(getSetting().getData())
			.filter(data->data.get(columnCheckbox.getData()))
			.map(data->mapper.apply(data))
			.toArray(Object[]::new);
		}
		
		@Override
		public TableSelectable<T> setColumns(ColumnInfo[] columns) {
			ColumnInfo[] proxy = new ColumnInfo[columns.length+1];
			proxy[0] = columnCheckbox;
			for(int i = 0; i < columns.length; ++i) proxy[i+1] = columns[i];
			base.setColumns(proxy);
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
		public Data parse(T data) {
			return base.parse(data).put("<div id='\" + checkHeaderId + \"'></div>", false);
		}

		@Override
		public TableSelectable<T> setValues(Data... values) {
			Arrays.stream(values).forEach(value->value.put(columnCheckbox.getData(), false));
			base.setValues(values);
			tmp.setValue(false);
			if(values!=null && values.length > 0) checkAll.getStyle().setVisibility(Visibility.VISIBLE);
			else checkAll.getStyle().setVisibility(Visibility.HIDDEN);
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
