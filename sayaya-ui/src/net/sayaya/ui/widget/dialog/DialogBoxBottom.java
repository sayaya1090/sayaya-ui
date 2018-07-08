package net.sayaya.ui.widget.dialog;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Widget;

import net.sayaya.ui.widget.Button;
import net.sayaya.ui.widget.DialogBox;

public class DialogBoxBottom extends DialogBox {

	public DialogBoxBottom(String title, Widget contents, Button<?>... buttons) {
		super(title, contents, buttons);
		getElement().getStyle().setBottom(0, Unit.PX);
		getElement().getStyle().setLeft(0, Unit.PX);
		getElement().getStyle().setRight(0, Unit.PX);
		getElement().getStyle().clearTop();
		setAnimationEnabled(false);
	}
}
