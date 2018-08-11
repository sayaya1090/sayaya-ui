package net.sayaya.ui.shape;

import net.sayaya.ui.shape.Canvas;

public class Animation extends com.google.gwt.animation.client.Animation {
	private final Canvas canvas;
	public Animation(Canvas canvas) {
		this.canvas = canvas;
	}
	
	@Override
	protected void onUpdate(double progress) {
		canvas.cleard();
		canvas.paint(progress);
	}
}
