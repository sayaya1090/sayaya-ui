package net.sayaya.ui.widget.textbox.richtext;

import net.sayaya.ui.decorator.Tooltip;
import net.sayaya.ui.icon.Icon;
import net.sayaya.ui.widget.button.ButtonToggle;
import net.sayaya.ui.widget.textbox.RichTextArea;

public class RichTextEditorFactory {
	public static ButtonToggle bold(RichTextArea editor) {
		ButtonToggle button = new ButtonToggle(Icon.create(Icon.GSS.timesCircle()));
		button.addValueChangeHandler(evt->editor.getFormatter().toggleBold());
		editor.addMouseUpHandler(evt->{
			if(editor.getFormatter().isBold()) button.setValue(true);
			else button.setValue(false);
		});
		return Tooltip.decorate(button, "Bold");
	}
}
