package net.sayaya.ui.layout;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class GridLayout extends Composite implements RequiresResize {
	private final FlowPanel layout = new FlowPanel();
	public GridLayout() {
		initWidget(layout);
		style();
	}
	
	private void style() {
		getElement().getStyle().setProperty("display", "grid");
		getElement().getStyle().setProperty("overflow", "auto");
	}
	
	public GridCell add(Widget w) {
		return add(w, 1, 1);
	}
	public GridCell add(Widget w, int colspan, int rowspan) {
		GridCell sp = new GridCell();
		sp.add(w);
		layout.add(sp);
		return sp.setColspan(colspan).setRowspan(rowspan);
	}

	@Override
	public void onResize() {
		
	}
	
	public GridLayout setTemplateColumn(int n) {
		getElement().getStyle().setProperty("gridTemplateColumns", "repeat(" + n + ", 1fr)");
		return this;
	}
	public GridLayout setGridGap(int px) {
		getElement().getStyle().setProperty("gridGap", px+"px");
		return this;
	}
	
	public GridLayout setCellMinHeight(int px) {
		getElement().getStyle().setProperty("gridAutoRows", "minmax(" + px + "px, auto)");
		return this;
	}
	
	public final static class GridCell extends SimplePanel {
		private int colspan = 1;
		private int rowspan = 1;
		private GridCell() {
			
		}
		
		public GridCell setColspan(int colspan) {
			this.colspan = colspan;
			getElement().getStyle().setProperty("gridColumnEnd", "span " + colspan);
			return this;
		}
		
		int getColspan() {
			return colspan;
		}
		
		public GridCell setRowspan(int rowspan) {
			this.rowspan = rowspan;
			getElement().getStyle().setProperty("gridRowEnd", "span " + rowspan);
			return this;
		}
		
		int getRowspan() {
			return rowspan;
		}
	}
}
