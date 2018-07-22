package net.sayaya.ui.widget.textbox.richtext;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RichTextArea.FontSize;
import com.google.gwt.user.client.ui.RichTextArea.Justification;

import net.sayaya.ui.decorator.Tooltip;
import net.sayaya.ui.icon.Icon;
import net.sayaya.ui.layout.PopupPanel;
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
	public static ButtonIcon fontFamily(RichTextArea editor) {
		ButtonIcon button = new ButtonIcon(Icon.create(Icon.GSS.font()));
		button.addClickHandler(evt->{
			PopupPanel popup = PopupPanel.create(evt);
			popup.add("<font face='Noto Sans KR'>Noto Sans KR</font>", evt2->editor.getFormatter().setFontName("Noto Sans KR"));
			popup.add("<font face='Malgun Gothic'>맑은고딕</font>", evt2->editor.getFormatter().setFontName("Malgun Gothic"));
			popup.add("<font face='Gungsuh'>궁서</font>", evt2->editor.getFormatter().setFontName("Gungsuh"));
			popup.add("<font face='Dotum'>돋움</font>", evt2->editor.getFormatter().setFontName("Dotum"));
			popup.add("<font face='Arial'>Arial</font>", evt2->editor.getFormatter().setFontName("Arial"));
			popup.add("<font face='Times New Roman'>Times New Roman</font>", evt2->editor.getFormatter().setFontName("Times New Roman"));
			popup.add("<font face='Verdana'>Verdana</font>", evt2->editor.getFormatter().setFontName("Verdana"));
			popup.show();
		});
		return Tooltip.decorate(button, "Font family");
	}
	public static ButtonIcon fontSize(RichTextArea editor) {
		ButtonIcon button = new ButtonIcon(Icon.create(Icon.GSS.textHeight()));
		button.addClickHandler(evt->{
			PopupPanel popup = PopupPanel.create(evt);
			popup.add("<font size=1>XXS</font>", evt2->editor.getFormatter().setFontSize(FontSize.XX_SMALL));
			popup.add("<font size=2>XS</font>", evt2->editor.getFormatter().setFontSize(FontSize.X_SMALL));
			popup.add("<font size=3>S</font>", evt2->editor.getFormatter().setFontSize(FontSize.SMALL));
			popup.add("<font size=4>M</font>", evt2->editor.getFormatter().setFontSize(FontSize.MEDIUM));
			popup.add("<div style='margin-top: 4px;'><font size=5>L</font></div>", evt2->editor.getFormatter().setFontSize(FontSize.LARGE));
			popup.add("<div style='margin-top: 8px;'><font size=6>XL</font></div>", evt2->editor.getFormatter().setFontSize(FontSize.X_LARGE));
			popup.add("<div style='margin-top: 12px;'><font size=7>XXL</font></div>", evt2->editor.getFormatter().setFontSize(FontSize.XX_LARGE));
			popup.show();
		});
		return Tooltip.decorate(button, "Font size");
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
	public static ButtonToggle underline(RichTextArea editor) {
		ButtonToggle button = new ButtonToggle(Icon.create(Icon.GSS.underline()));
		button.addClickHandler(evt->editor.getFormatter().toggleUnderline());
		editor.addMouseUpHandler(evt->{
			if(editor.getFormatter().isUnderlined()) button.setValue(true);
			else button.setValue(false);
		});
		return Tooltip.decorate(button, "Underline");
	}
	public static ButtonToggle italic(RichTextArea editor) {
		ButtonToggle button = new ButtonToggle(Icon.create(Icon.GSS.italic()));
		button.addClickHandler(evt->editor.getFormatter().toggleItalic());
		editor.addMouseUpHandler(evt->{
			if(editor.getFormatter().isItalic()) button.setValue(true);
			else button.setValue(false);
		});
		return Tooltip.decorate(button, "Italic");
	}
	public static ButtonToggle strikethrough(RichTextArea editor) {
		ButtonToggle button = new ButtonToggle(Icon.create(Icon.GSS.strikethrough()));
		button.addClickHandler(evt->editor.getFormatter().toggleStrikethrough());
		editor.addMouseUpHandler(evt->{
			if(editor.getFormatter().isStrikethrough()) button.setValue(true);
			else button.setValue(false);
		});
		return Tooltip.decorate(button, "Strikethrough");
	}
	public static ButtonToggle subscript(RichTextArea editor) {
		ButtonToggle button = new ButtonToggle(Icon.create(Icon.GSS.subscript()));
		button.addClickHandler(evt->editor.getFormatter().toggleSubscript());
		editor.addMouseUpHandler(evt->{
			if(editor.getFormatter().isSubscript()) button.setValue(true);
			else button.setValue(false);
		});
		return Tooltip.decorate(button, "Subscript");
	}
	public static ButtonToggle superscript(RichTextArea editor) {
		ButtonToggle button = new ButtonToggle(Icon.create(Icon.GSS.superscript()));
		button.addClickHandler(evt->editor.getFormatter().toggleSuperscript());
		editor.addMouseUpHandler(evt->{
			if(editor.getFormatter().isSuperscript()) button.setValue(true);
			else button.setValue(false);
		});
		return Tooltip.decorate(button, "Superscript");
	}
	/*public static ButtonIcon colorForeground(RichTextArea editor) {
		ButtonIcon button = new ButtonIcon(Icon.create(Icon.GSS.indent()));
		button.addClickHandler(evt->editor.getFormatter().rightIndent());
		return Tooltip.decorate(button, "Foreground color");
	}
	public static ButtonIcon colorBackground(RichTextArea editor) {
		ButtonIcon button = new ButtonIcon(Icon.create(Icon.GSS.col));
		button.addClickHandler(evt->editor.getFormatter().rightIndent());
		return Tooltip.decorate(button, "Background color");
	}*/
	public static ButtonIcon indent(RichTextArea editor) {
		ButtonIcon button = new ButtonIcon(Icon.create(Icon.GSS.indent()));
		button.addClickHandler(evt->editor.getFormatter().rightIndent());
		return Tooltip.decorate(button, "Indent");
	}
	public static ButtonIcon outdent(RichTextArea editor) {
		ButtonIcon button = new ButtonIcon(Icon.create(Icon.GSS.outdent()));
		button.addClickHandler(evt->editor.getFormatter().leftIndent());
		return Tooltip.decorate(button, "Outdent");
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
