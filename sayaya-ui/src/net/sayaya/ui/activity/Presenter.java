package net.sayaya.ui.activity;

import java.util.HashSet;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;

public interface Presenter {
	void goTo(Place place);
	Place getCurrentPlace();
	
	final static ClientFactory clientFactory = GWT.create(ClientFactory.class);
	final static Presenter presenter = new PresenterImpl(clientFactory);
	final static HashSet<ValueChangeHandler<Place>> handlers = new HashSet<>();
	public static void go(Place place) {
		presenter.goTo(place);
		ValueChangeEvent<Place> evt = new ValueChangeEvent<Place>(place) {};
		handlers.forEach(handler->handler.onValueChange(evt));
	}
	
	public static HandlerRegistration addValueChangeHandler(ValueChangeHandler<Place> handler) {
		handlers.add(handler);
		return ()->handlers.remove(handler);
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
