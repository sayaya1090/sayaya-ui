package net.sayaya.ui.widget;

import com.google.gwt.user.client.ui.Composite;

import net.sayaya.ui.handler.HasStyle;
import net.sayaya.ui.icon.Icon;

@SuppressWarnings("unchecked")
public abstract class Button<B extends Button<B>> extends Composite implements HasStyle<B> {
	private final com.google.gwt.user.client.ui.Button widget = new com.google.gwt.user.client.ui.Button();
	public Button() {
		initWidget(widget);
		style((B)this);
	}
	public Button(String text) {
		setText(text);
	}
	public Button(Icon icon) {
		setValue(icon);
	}
	
	public B setValue(String text) {
		widget.setText(text);
		return (B)this;
	}
	
	public B setValue(Icon icon) {
		widget.setHTML(icon.toString());
		return (B)this;
		
	}
	
	public B setText(String text) {
		widget.setText(text);
		return (B)this;
	}
	
	public B setEnabled(boolean enabled) {
		widget.setEnabled(enabled);
		return (B)this;
	}
}
