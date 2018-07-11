package net.sayaya.ui.widget.textbox.richtext;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RichTextArea.Justification;

import net.sayaya.ui.decorator.Tooltip;
import net.sayaya.ui.icon.Icon;
import net.sayaya.ui.widget.button.ButtonIcon;
import net.sayaya.ui.widget.button.ButtonToggle;
import net.sayaya.ui.widget.textbox.RichTextArea;

public class RichTextEditorFactory {
	public static ButtonIcon alignCenter(RichTextArea editor) {
		ButtonIcon button = new ButtonIcon(Icon.create(Icon.GSS.alignCenter()));
		button.addClickHandler(evt->editor.getFormatter().setJustification(Justification.CENTER));
		return Tooltip.decorate(button, "Align Center");
	}
	public static ButtonIcon alignFull(RichTextArea editor) {
		ButtonIcon button = new ButtonIcon(Icon.create(Icon.GSS.alignJustify()));
		button.addClickHandler(evt->editor.getFormatter().setJustification(Justification.FULL));
		return Tooltip.decorate(button, "Align Full");
	}
	public static ButtonIcon alignLeft(RichTextArea editor) {
		ButtonIcon button = new ButtonIcon(Icon.create(Icon.GSS.alignLeft()));
		button.addClickHandler(evt->editor.getFormatter().setJustification(Justification.LEFT));
		return Tooltip.decorate(button, "Align Left");
	}
	public static ButtonIcon alignRight(RichTextArea editor) {
		ButtonIcon button = new ButtonIcon(Icon.create(Icon.GSS.alignRight()));
		button.addClickHandler(evt->editor.getFormatter().setJustification(Justification.RIGHT));
		return Tooltip.decorate(button, "Align Right");
	}
	public static ButtonToggle bold(RichTextArea editor) {
		ButtonToggle button = new ButtonToggle(Icon.create(Icon.GSS.bold()));
		button.addClickHandler(evt->editor.getFormatter().toggleBold());
		editor.addMouseUpHandler(evt->{
			if(editor.getFormatter().isBold()) button.setValue(true);
			else button.setValue(false);
		});
		return Tooltip.decorate(button, "Bold");
	}
	public static ButtonIcon link(RichTextArea editor) {
		ButtonIcon button = new ButtonIcon(Icon.create(Icon.GSS.link()));
		button.addClickHandler(evt->{
			String url = Window.prompt("URL", "http://0.0.0.0");
			editor.getFormatter().createLink(url);
		});
		return Tooltip.decorate(button, "Link");
	}
}
