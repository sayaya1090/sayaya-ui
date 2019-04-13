package net.sayaya.ui.activity;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;

public interface Presenter {
	void goTo(Place place);
	Place getCurrentPlace();
	
	final static ClientFactory CLIENT_FACTORY = GWT.create(ClientFactory.class);
	final static Presenter PRESENTER = new PresenterImpl(CLIENT_FACTORY);
	public static void go(Place place) {
		PRESENTER.goTo(place);
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
