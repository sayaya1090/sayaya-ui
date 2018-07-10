package net.sayaya.ui.widget.textbox.richtext;

import java.util.function.Consumer;

import com.google.gwt.event.dom.client.ClickEvent;

import net.sayaya.ui.decorator.Tooltip;
import net.sayaya.ui.icon.Icon;
import net.sayaya.ui.widget.button.ButtonToggle;
import net.sayaya.ui.widget.textbox.RichTextArea;

public class RichTextEditor extends ButtonToggle {
	public RichTextEditor(Icon icon, Consumer<ClickEvent> onClick) {
		super(icon);
		addClickHandler(evt->onClick.accept(evt));
	}
	
	public static RichTextEditor bold(RichTextArea editor) {
		RichTextEditor button = new RichTextEditor(Icon.create(Icon.GSS.timesCircle()), evt->{
			editor.getFormatter().isBold();
			editor.getFormatter().toggleBold();
		});
		editor.addMouseUpHandler(evt->{
			if(editor.getFormatter().isBold());
		});
		return Tooltip.decorate(button, "Bold");
	}
}
