package net.sayaya.ui.handler;

public interface HasValue<T> {
	HasValue<T> setValue(T value);
	T getValue();
	boolean isEmpty();
}
