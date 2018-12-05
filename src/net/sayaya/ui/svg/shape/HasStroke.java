package net.sayaya.ui.svg.shape;

public interface HasStroke {
	String getBorderColor();
	HasStroke setBorderColor(String color);
	HasStroke setBorderWidth(double width);
}
