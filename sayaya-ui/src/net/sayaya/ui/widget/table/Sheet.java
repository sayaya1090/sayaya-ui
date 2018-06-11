package net.sayaya.ui.widget.table;

import java.util.Arrays;
import java.util.Date;

import com.google.gwt.dom.client.Element;
import com.google.gwt.layout.client.Layout.AnimationCallback;
import com.google.gwt.layout.client.Layout.Layer;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ProvidesResize;
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

public abstract class Sheet<T> extends ResizeComposite implements ProvidesResize, AnimationCallback {
	private final SheetSetting setting = new SheetSetting()
	.setRowHeaders(false)
	.setAutoColSize(true)
	.setAutoRowSize(false)
	.setManualColumnMove(true)
	.setManualColumnResize(true)
	.setDropdownMenu(true)
	.setFilters(true)
	.setData(new Data[] {})
	.setStretchH("all");
	private final LayoutPanel container = new LayoutPanel();
	private SpreadSheet sheet;
	
	public Sheet() {
		initWidget(container);
	}
	
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
					CheckBox check = new CheckBox(22).setValue(cast).setEnabled(!columnInfo.isReadOnly());
					td.appendChild(check.getElement());
					td.setAttribute("align", "center");
				}
				return td;
			});
		}
		setting.setColumns(columns);
		return this;
	}

	public final SheetSetting getSetting() {
		return setting;
	}
	
	@Override
	public final void onLayout(Layer layer, double progress) {
		container.setVisible(false);
	}
	
	@Override
	public final void onAnimationComplete() {
		container.setVisible(true);
		Widget child = container.getWidget(0);
		if(child instanceof SpreadSheet) ((SpreadSheet)child).render();
	}
	
	public final void update() {
		sheet.update(setting);
	}
	
	protected abstract Data parse(T data);
	public final Sheet<T> setValues(@SuppressWarnings("unchecked") T... values) {
		container.clear();
		if(values!=null && values.length > 0) {
			Data[] data = Arrays.stream(values).map(value->parse(value)).toArray(Data[]::new);
			setting.setData(data).setMaxRows(Math.max(1, data.length));
			sheet = new SpreadSheet(setting);
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
