package net.sayaya.ui.place;

public abstract class Place<T extends Place<T>> extends com.google.gwt.place.shared.Place {
	@Override
	public final boolean equals(Object other) {
		if(other == null) return false;
		if(other.getClass() != getClass()) return false;
		@SuppressWarnings("unchecked")
		T cast = (T) other;
		return equals(cast);
	}
	public boolean isInstanceOf(Object other) {
		if(other == null) return false;
		if(other.getClass() != getClass()) return false;
		@SuppressWarnings("unchecked")
		T cast = (T) other;
		return isInstanceOf(cast);
	}
	public abstract boolean isInstanceOf(T other);
	public boolean equals(T other) {
		return false;
	}
	public abstract String toString();
}
