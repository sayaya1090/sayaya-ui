package net.sayaya.ui.regacy.shape.impl;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.TextAlign;
import com.google.gwt.canvas.dom.client.Context2d.TextBaseline;

public final class Text extends ShapeInstance<Text> {
	private String text;
	private String color;
	private String font;
	private TextAlign align;
	private TextBaseline baseline = TextBaseline.ALPHABETIC;
	private int size;
	public Text(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}

	public Text setText(String text) {
		this.text = text;
		return this;
	}

	public String getColor() {
		return color;
	}

	public Text setColor(String color) {
		this.color = color;
		return this;
	}

	public String getFont() {
		return font;
	}

	public Text setFont(String font) {
		this.font = font;
		return this;
	}

	public int getSize() {
		return size;
	}

	public Text setSize(int size) {
		this.size = size;
		return this;
	}

	public TextAlign getAlign() {
		return align;
	}

	public Text setAlign(TextAlign align) {
		this.align = align;
		return this;
	}

	public TextBaseline getBaseline() {
		return baseline;
	}

	public Text setBaseline(TextBaseline baseline) {
		this.baseline = baseline;
		return this;
	}

	@Override
	public boolean checkIn(double x, double y) {
		return true;
	}

	@Override
	public void draw(Context2d context, double progress) {
		if(color!=null && !color.isEmpty()) context.setFillStyle(color);
		StringBuilder sb = new StringBuilder();
		if(size>0) sb.append(size + "px ");
		if(font!=null && !font.isEmpty()) sb.append("'" + font + "'");
		if(sb.length() > 0) context.setFont(sb.toString());
		if(align != null) context.setTextAlign(align);
		if(baseline!=null) context.setTextBaseline(baseline);
		context.fillText(text, 0, 0);
	}

}
