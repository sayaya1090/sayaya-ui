package net.sayaya.ui.widget;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import net.sayaya.ui.style.StyleDialogBox;

public class DialogBox extends com.google.gwt.user.client.ui.DialogBox {
	private final static Set<DialogBox> INSTANCES = new HashSet<>();
	private final Element overlay = DOM.createDiv();
	private final FlexTable layout = new FlexTable();
	private final FlowPanel control = new FlowPanel();
	public DialogBox(String title, Widget contents, Button<?>... buttons) {
		// layout.add(contents);
		layout.setWidget(0, 0, contents);
		if(buttons.length > 0) {
			layout.setWidget(1, 0, control);// layout.add(control);
			layout.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		}
		setText(title);
		layout(buttons);
		style();
	}
	
	private void layout(Button<?>... buttons) {
		add(layout);
		for(Button<?> button: buttons) control.add(button);
		addCloseHandler(evt->RootPanel.get().getElement().removeChild(overlay));
		getElement().getStyle().setZIndex(1001);
		layout.setWidth("100%");
		((Element)this.getContainerElement().getChild(0).cast()).getStyle().setWidth(100, Unit.PCT);
	}
	
	private void style() {
		setStyleName(StyleDialogBox.GSS.dialogBox());
		overlay.setClassName(StyleDialogBox.GSS.overlay());
		layout.getWidget(0, 0).getElement().getStyle().setProperty("maxHeight", "400px");
		layout.getWidget(0, 0).getElement().getStyle().setOverflow(Overflow.AUTO);
		control.getElement().addClassName("control");
		setModal(true);
		setAnimationEnabled(true);
		setAnimationType(AnimationType.CENTER);
	}
	
	private DialogBox parent = null;
	@Override
	public void show() {
		for(DialogBox other: INSTANCES) if(other!=this && other.isVisible()) {
			other.setVisible(false);
			parent = other;
			break;
		}
		RootPanel.get().getElement().appendChild(overlay);
		super.show();
		INSTANCES.add(this);
	}
	
	@Override
	public void hide() {
		INSTANCES.remove(this);
		super.hide();
		if(parent!=null) parent.setVisible(true);
	}
}