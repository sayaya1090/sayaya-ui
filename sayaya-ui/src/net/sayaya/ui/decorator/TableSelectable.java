package net.sayaya.ui.decorator;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Stream;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.layout.client.Layout.Layer;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.ResizeComposite;

import net.sayaya.ui.widget.CheckBox;
import net.sayaya.ui.widget.SpreadSheet.ColumnInfo;
import net.sayaya.ui.widget.SpreadSheet.Data;
import net.sayaya.ui.widget.SpreadSheet.SheetSetting;
import net.sayaya.ui.widget.table.TableBase;

public interface TableSelectable<T> extends TableBase<T> {
	public static <T> TableSelectable<T> decorate(TableBase<T> widget, Function<Data, T> mapper) {
		return new TableSelectableImpl<T>(widget, mapper);
	}
	
	T[] getSelected();
	
	static final class TableSelectableImpl<T> extends ResizeComposite implements TableSelectable<T> {
		private final TableBase<T> base;
		private final Function<Data, T> mapper;
		private final ColumnInfo columnCheckbox = new ColumnInfo().setData(" ");
		private final Element checkAll = DOM.createDiv();
		private final CheckBox tmp = new CheckBox(22).setValue(false, true);
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
					tmp.setValue(value, true);
					for(Data data: getSetting().getData()) data.put(columnCheckbox.getData(), value);
					checkAll.removeAllChildren();
					checkAll.appendChild(tmp.getElement());
					update();
				}
			});
			checkAll.appendChild(tmp.getElement());
			DOM.sinkEvents(checkAll, Event.ONCLICK);
			getElement().appendChild(checkAll);
			checkAll.getStyle().setVisibility(Visibility.HIDDEN);
		}
		
		private void style() {
			checkAll.getStyle().setPosition(Position.RELATIVE);
			checkAll.getStyle().setLeft(16, Unit.PX);
			checkAll.getStyle().setTop(8, Unit.PX);
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
			base.setColumns(Stream.concat(Stream.of(columnCheckbox), Arrays.stream(columns)).toArray(ColumnInfo[]::new));
			return this;
		}

		@Override
		public SheetSetting getSetting() {
			return base.getSetting();
		}

		@Override
		public void update() {
			base.update();
		}

		@Override
		public Data parse(T data) {
			return base.parse(data).put(columnCheckbox.getData(), false);
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
	}
}
