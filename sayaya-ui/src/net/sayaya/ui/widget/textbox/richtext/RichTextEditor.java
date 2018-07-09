package net.sayaya.ui.widget.textbox.richtext;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.google.gwt.user.client.Event;

import net.sayaya.ui.icon.Icon;
import net.sayaya.ui.widget.Button;
import net.sayaya.ui.widget.textbox.RichTextArea;

public abstract class RichTextEditor extends Button {
	private final Icon icon;
	private final BiConsumer<Event, RichTextArea> func;
	public RichTextEditor(Icon icon, BiConsumer<Event, RichTextArea> func) {
		this.icon = icon;
		this.func = func;
		
	}
}
