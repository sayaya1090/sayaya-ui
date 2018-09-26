package net.sayaya.ui.widget.table;

import java.util.Arrays;
import java.util.Date;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.layout.client.Layout.Layer;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Widget;

import net.sayaya.ui.style.StyleChart;
import net.sayaya.ui.style.StyleLabel;
import net.sayaya.ui.style.color.Palette;
import net.sayaya.ui.widget.CheckBox;
import net.sayaya.ui.widget.SpreadSheet;
import net.sayaya.ui.widget.SpreadSheet.ColumnInfo;
import net.sayaya.ui.widget.SpreadSheet.Data;
import net.sayaya.ui.widget.SpreadSheet.SheetSetting;
import net.sayaya.ui.widget.SpreadSheet.SpreadSheetTable;

public abstract class Sheet<T> extends ResizeComposite implements TableBase<T> {
	private final SheetSetting setting = new SheetSetting()
	.setRowHeaders(false)
	.setAutoColSize(true)
	.setAutoRowSize(false)
	.setManualColumnMove(true)
	.setManualColumnResize(true)
	.setDropdownMenu("filter_by_value", "filter_action_bar")
	.setFilters(true)
	.setData(new Data[] {})
	.setStretchH("all");
	private final LayoutPanel container = new LayoutPanel();
	private SpreadSheet sheet;
	
	public Sheet() {
		initWidget(container);
		setting.setAfterGetColHeader((col, th)->{
			if(!setting.getColumns()[col].isFilter()) th.getChild(0).getChild(0).removeFromParent();
		});
	}
	
	public Element[] getHeader() {
		if(sheet == null) return new Element[] {};
		NodeList<Element> elems = sheet.getElement().getElementsByTagName("th");
		Element[] array = new Element[elems.getLength()/2];
		for(int i = 0; i < elems.getLength()/2; ++i) array[i] = elems.getItem(i).getFirstChildElement();
		return array;
	}
	
	public Element getBody() {
		if(sheet == null) return null;
		NodeList<Element> elems = sheet.getElement().getElementsByTagName("tbody");
		return elems.getItem(0);
	}
	
	@Override
	public Sheet<T> setColumns(ColumnInfo[] columns) {
		for(ColumnInfo c: columns) {
			if(c.getRenderer()==null) c.setRenderer((SpreadSheetTable instance, Element td, int row, int col, String prop, Object value, ColumnInfo columnInfo)->{
				td.removeAllChildren();
				if(value == null) td.setInnerHTML("");
				else if(value instanceof String) {
					td.addClassName(StyleLabel.GSS.label());
					String cast = (String)value;
					td.setInnerHTML(cast);
				} else if(value instanceof Number) {
					td.addClassName(StyleLabel.GSS.label());
					td.addClassName(StyleLabel.GSS.numeric());
					Number cast = (Number)value;
					td.setInnerHTML(cast.toString());
				} else if(value instanceof Date) {
					td.addClassName(StyleLabel.GSS.label());
					Date cast = (Date)value;
					td.setInnerHTML(cast.toString());
				} else if(value instanceof Image) {
					// Image cast = (Image)value;
				} else if(value instanceof Boolean) {
					Boolean cast = (Boolean)value;
					CheckBox check = new CheckBox(22).setValue(cast, true);
					Element elem = check.getElement();
					Event.setEventListener(td, evt->{
						evt.preventDefault();
						if(evt.getTypeInt() == Event.ONCLICK) {
							boolean current = instance.getSettings().getData()[row].get(prop);
							instance.getSettings().getData()[row].put(prop, !current);
							td.removeAllChildren();
							td.appendChild(check.setValue(!current, true).getElement());
						}
					});
					Event.sinkEvents(td, Event.ONCLICK);
					td.appendChild(elem);
					td.setAttribute("align", "center");
					columnInfo.setReadOnly(true);
				}
				return td;
			});
		}
		
		setting.setColumns(columns);
		return this;
	}

	@Override
	public final SheetSetting getSetting() {
		return setting;
	}
	
	@Override
	public final SpreadSheet getTable() {
		return sheet;
	}
	
	@Override
	public final void onLayout(Layer layer, double progress) {
		container.setVisible(false);
	}
	
	@Override
	public final void onAnimationComplete() {
		container.setVisible(true);
		if(container.getWidgetCount() <= 0) return;
		Widget child = container.getWidget(0);
		if(child instanceof SpreadSheet) ((SpreadSheet)child).render();
	}
	
	@Override
	public final void update() {
		if(sheet!=null) sheet.update(setting);
	}
	
	private T[] values = null;
	public final Sheet<T> setValues(@SuppressWarnings("unchecked") T... values) {
		this.values = values;
		if(values!=null && values.length > 0) {
			Data[] data = Arrays.stream(values).map(value->parse(value)).toArray(Data[]::new);
			return setValues(data);
		} else return setValues(new Data[] {});
	}
	
	public final T[] getValues() {
		return values;
	}
	
	@Override
	public final Sheet<T> setValues(Data... data) {
		container.clear();
		if(data!=null && data.length > 0) {
			sheet = new SpreadSheet(setting);
			setting.setData(data).setMaxRows(Math.max(1, data.length));
			container.add(sheet);
			sheet.update(setting);
		} else {
			Label empty = new HTML("<div style='width:100%;top:45%;position:relative;'>Empty result.</div>");
			container.add(empty);
			empty.setStyleName(StyleLabel.GSS.label());
			empty.addStyleName(StyleChart.GSS.empty());
			empty.getElement().getStyle().setColor(Palette.getInstance().getColorText1());
		}
		return this;
	}
}
