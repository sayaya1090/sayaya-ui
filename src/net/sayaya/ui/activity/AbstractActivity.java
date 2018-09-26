package net.sayaya.ui.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import net.sayaya.ui.handler.Callback;
import net.sayaya.ui.place.Place;

public abstract class AbstractActivity<P extends Place<P>> extends com.google.gwt.activity.shared.AbstractActivity {
	private final P place;
	public AbstractActivity(P place) {
		this.place = place;
	}
	
	@Override
	public final void start(AcceptsOneWidget panel, EventBus eventBus) {
		getScene(place, scene->panel.setWidget(scene));
	}
	
	protected abstract void getScene(P place, Callback<Scene> callback);
}
