package net.sayaya.ui.activity;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;

public interface ClientFactory {
	EventBus getEventBus();
	PlaceController getPlaceController();
	
	public static class ClientFactoryImpl implements ClientFactory {
		private static final EventBus eventBus = new SimpleEventBus();
		private static final PlaceController placeController = new PlaceController(eventBus);

		@Override
		public EventBus getEventBus() {
			return eventBus;
		}
		
		@Override
		public PlaceController getPlaceController() {
			return placeController;
		}
	}
}
