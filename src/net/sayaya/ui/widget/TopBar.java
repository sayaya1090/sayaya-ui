package net.sayaya.ui.widget;

import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.gwt.user.client.ui.RequiresResize;

import net.sayaya.ui.handler.HasStyle;
import net.sayaya.ui.style.StyleTopBar;

public class TopBar extends FlowPanel implements HasStyle<TopBar>, ProvidesResize, RequiresResize {
	public TopBar() {
		layout();
		style(this);
	}
	
	private void layout() {
		getElement().getStyle().setWidth(100, Unit.PCT);
		getElement().getStyle().setDisplay(Display.FLEX);
	}
	
	@Override
	public TopBar style(TopBar widet) {
		setStyleName(StyleTopBar.GSS.topbar());
		return this;
	}
	
	@Override
	public void onResize() {
		
	}
}
