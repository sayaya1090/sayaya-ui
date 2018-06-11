package net.sayaya.ui.activity;

import com.google.gwt.layout.client.Layout.AnimationCallback;
import com.google.gwt.layout.client.Layout.Layer;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.gwt.user.client.ui.ResizeComposite;

public abstract class Scene extends ResizeComposite implements ProvidesResize, AnimationCallback {
	@Override
	public void onAnimationComplete() {
		
	}

	@Override
	public void onLayout(Layer layer, double progress) {
		
	}
}
