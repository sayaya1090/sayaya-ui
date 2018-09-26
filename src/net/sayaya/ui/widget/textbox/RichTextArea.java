package net.sayaya.ui.widget.textbox;

import com.google.gwt.event.dom.client.HasMouseUpHandlers;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.RichTextArea.Formatter;

import net.sayaya.ui.style.StyleTextBox;
import net.sayaya.ui.widget.InputBase;

public class RichTextArea extends Composite implements InputBase<String, RichTextArea>, RequiresResize, HasMouseUpHandlers {
	private final com.google.gwt.user.client.ui.RichTextArea widget;
	public RichTextArea() {
		widget = new com.google.gwt.user.client.ui.RichTextArea();
		initWidget(widget);
		style(this);
	}

	@Override
	public RichTextArea style(RichTextArea w) {
		w.widget.setStyleName(StyleTextBox.GSS.textarea());
		return this;
	}

	@Override
	public RichTextArea setValue(String value) {
		widget.setHTML(value);
		return this;
	}

	@Override
	public String getValue() {
		return widget.getHTML();
	}
	
	@Override
	public boolean isEnabled() {
		return widget.isEnabled();
	}

	@Override
	public boolean isEmpty() {
		return widget.getHTML()==null || widget.getHTML().trim().isEmpty();
	}

	@Override
	public RichTextArea setEnabled(boolean enabled) {
		widget.setEnabled(enabled);
		return this;
	}
	
	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
		throw new UnsupportedOperationException("Unsupported operation");
	}
	
	public Formatter getFormatter() {
		return widget.getFormatter();
	}

	@Override
	public void onResize() {
		
	}

	@Override
	public HandlerRegistration addMouseUpHandler(MouseUpHandler handler) {
		return widget.addMouseUpHandler(handler);
	}
}
