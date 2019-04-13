package net.sayaya.ui.activity;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;

public interface ClientFactory {
	EventBus getEventBus();
	PlaceController getPlaceController();
	
	public static class ClientFactoryImpl implements ClientFactory {
		private static final EventBus EVENT_BUS = GWT.create(SimpleEventBus.class);
		private static final PlaceController PLACE_CONTROLLER = new PlaceController(EVENT_BUS);

		@Override
		public EventBus getEventBus() {
			return EVENT_BUS;
		}
		
		@Override
		public PlaceController getPlaceController() {
			return PLACE_CONTROLLER;
		}
	}
}
