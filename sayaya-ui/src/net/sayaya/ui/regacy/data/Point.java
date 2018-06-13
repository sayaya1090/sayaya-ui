package net.sayaya.ui.regacy.data;

public final class Point<X, Y> {
	private X x;
	private Y y;
	public Point() {}
	public Point(X x, Y y) {
		this.x = x;
		this.y = y;
	}
	public X getX() {
		return x;
	}
	public Point<X, Y> setX(X x) {
		this.x = x;
		return this;
	}
	public Y getY() {
		return y;
	}
	public Point<X, Y> setY(Y y) {
		this.y = y;
		return this;
	}
}
