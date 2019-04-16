package net.sayaya.ui.activity;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;

public interface Presenter {
	void goTo(Place place);
	Place getCurrentPlace();
	
	final static Set<ValueChangeHandler<Place>> HANDLERS = new HashSet<>();
	final static ClientFactory CLIENT_FACTORY = GWT.create(ClientFactory.class);
	final static Presenter PRESENTER = new PresenterImpl(CLIENT_FACTORY);
	public static void go(Place place) {
		PRESENTER.goTo(place);
		ValueChangeEvent<Place> evt = new ValueChangeEvent<Place>(place) {};
		HANDLERS.forEach(handler->handler.onValueChange(evt));
	}
	public static HandlerRegistration addValueChangeHandler(ValueChangeHandler<Place> handler) {
		HANDLERS.add(handler);
		return ()->HANDLERS.remove(handler);
	}
	public static class PresenterImpl implements Presenter {
		private final PlaceController placeController;
		public PresenterImpl(ClientFactory factory) {
			this.placeController = factory.getPlaceController();
		}
		@Override
		public void goTo(Place place) {
			placeController.goTo(place);
		}
		
		@Override
		public Place getCurrentPlace() {
			return placeController.getWhere();
		}
	}
}
