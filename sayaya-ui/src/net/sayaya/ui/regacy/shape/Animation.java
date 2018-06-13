package net.sayaya.ui.regacy.shape;

public class Animation extends com.google.gwt.animation.client.Animation {
	private final Canvas canvas;
	public Animation(Canvas canvas) {
		this.canvas = canvas;
	}
	
	@Override
	protected void onUpdate(double progress) {
		canvas.clearScene();
		canvas.paint(progress);
	}
}
