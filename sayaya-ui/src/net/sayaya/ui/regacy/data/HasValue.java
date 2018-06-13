package net.sayaya.ui.regacy.data;

public interface HasValue<T> {
	HasValue<T> setValue(T value);
	T getValue();
}
