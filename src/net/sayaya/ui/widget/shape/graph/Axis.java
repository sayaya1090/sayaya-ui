package net.sayaya.ui.widget.shape.graph;

import net.sayaya.ui.widget.shape.impl.ShapeInstance;

public abstract class Axis<T> extends ShapeInstance<Axis<T>> {
	private final AXIS_DIRECTION direction;
	private final boolean isInverted;
	public Axis(AXIS_DIRECTION direction) {
		super("polyline");
		this.direction = direction;
		switch(direction) {
		case X_BOTTOM:
			isInverted = false;
			break;
		case X_TOP:
			isInverted = true;
			break;
		case Y_LEFT:
			isInverted = true;
	//		setRotate(-Math.PI/2);
			break;
		case Y_RIGHT:
			isInverted = false;
	//		setRotate(-Math.PI/2);
			break;
		default:
			isInverted = false;
			break;
		}
	//	if(direction == AXIS_DIRECTION.Y_LEFT || direction == AXIS_DIRECTION.Y_RIGHT) setRotate(-Math.PI/2);
		
	}
	public boolean isInverted() {
		return isInverted;
	}
	public AXIS_DIRECTION getDirection() {
		return direction;
	}

	public abstract double map(T data);
	
	public static enum AXIS_DIRECTION {
		X_BOTTOM
		, X_TOP
		, Y_LEFT
		, Y_RIGHT
	}
}
