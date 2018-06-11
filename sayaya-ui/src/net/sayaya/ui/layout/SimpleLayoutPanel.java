package net.sayaya.ui.layout;

import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.layout.client.Layout.AnimationCallback;
import com.google.gwt.layout.client.Layout.Layer;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;

public class SimpleLayoutPanel extends com.google.gwt.user.client.ui.SimpleLayoutPanel implements AcceptsOneWidget, AnimationCallback {
	private IsWidget w;
	public SimpleLayoutPanel() {
		getElement().getStyle().setOverflow(Overflow.AUTO);
	}
	@Override
	public void setWidget(IsWidget w) {
		clear();
		if(w == null) return;
		else {
			this.w = w;
			add(w);
		}
	}

	@Override
	public void onAnimationComplete() {
		if(w instanceof AnimationCallback) {
			AnimationCallback cast = (AnimationCallback)w;
			cast.onAnimationComplete();
		}
	}

	@Override
	public void onLayout(Layer layer, double progress) {
		if(w instanceof AnimationCallback) {
			AnimationCallback cast = (AnimationCallback)w;
			cast.onLayout(layer, progress);
		}
	}
}
