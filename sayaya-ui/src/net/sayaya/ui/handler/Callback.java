package net.sayaya.ui.handler;

@FunctionalInterface
public interface Callback<T> extends com.google.gwt.core.client.Callback<T, Throwable> {
	@Override
	default void onFailure(Throwable reason) {
		
	}
}