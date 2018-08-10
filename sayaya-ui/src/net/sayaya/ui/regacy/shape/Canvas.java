package net.sayaya.ui.regacy.shape;

import java.util.LinkedList;
import java.util.List;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.impl.FocusImpl;

public class Canvas extends Composite implements Focusable {
	final com.google.gwt.canvas.client.Canvas canvas = com.google.gwt.canvas.client.Canvas.createIfSupported();
	private final List<Shape> shapes = new LinkedList<Shape>();

	public Canvas(int width, int height) {
		initWidget(canvas);
		setWidth(width);
		setHeight(height);
		canvas.addMouseMoveHandler(event->{
			int x = event.getRelativeX(getElement());
			int y = event.getRelativeY(getElement());
			Canvas.this.onOver(x, y);
		});
		canvas.addMouseOutHandler(Canvas.this::onOut);	
		canvas.addMouseUpHandler(event->{
			int x = event.getRelativeX(getElement());
			int y = event.getRelativeY(getElement());
			onClick(x, y);
		});
		canvas.getElement().setPropertyBoolean("imageSmoothingEnabled", false);
	}
	Canvas(){}
	public final List<Shape> getShapes() {
		return shapes;
	}
	public final Canvas add(Shape shape) {
		shapes.add(shape);
		return this;
	}
	
	public Canvas remove(Shape shape) {
		shapes.remove(shape);
		return this;
	}
	
	public Canvas clear() {
		shapes.clear();
		return this;
	}
	
	public Canvas setWidth(int width) {
		//super.setWidth(width + "px");
		canvas.setCoordinateSpaceWidth(width);
		canvas.getCanvasElement().setWidth(width);
		return this;
	}
	
	public Canvas setHeight(int height) {
		//super.setHeight(height + "px");
		canvas.setCoordinateSpaceHeight(height);
		canvas.getCanvasElement().setHeight(height);
		return this;
	}
	
	public int getWidth() {
		return canvas.getCanvasElement().getWidth();
	}
	
	public int getHeight() {
		return canvas.getCanvasElement().getHeight();
	}
	
	public Canvas clearScene() {
		getContext().clearRect(0, 0, canvas.getCanvasElement().getWidth(), canvas.getCanvasElement().getHeight());
		return this;
	}
	
	public void paint() {
		clearScene();
		paint(1.0);
	}
	public Context2d getContext() {
		return canvas.getContext2d();
	}
	public void paint(double progress) {
		Context2d context = canvas.getContext2d();
		
		context.save();
		context.translate(0.5, 0.5);
		shapes.forEach(shape->{
			context.save();
			context.translate(shape.getX(), shape.getY());
			context.rotate(shape.getRotate());
			context.beginPath();
			shape.draw(context, progress);
			context.closePath();
			context.restore();
		});
		context.restore();
	}
	
	@Override
	public void setWidth(String width) {
	//	Log.log(Level.SEVERE, "Not Suppport operation exception: use Canvas.setWidth(int)");
	}
	
	@Override
	public void setHeight(String height) {
	//	Log.log(Level.SEVERE, "Not Suppport operation exception: use Canvas.setHeight(int)");
	}

	public void onClick(int x, int y) {
		shapes.forEach(shape->{
			double[] translate = translate(x, y, shape);
			if(shape.checkIn(translate[0], translate[1])) shape.fireDown(null); 
		});
	}

	public void onOver(int x, int y) {
		shapes.forEach(shape->{
			double[] translate = translate(x, y, shape);
			if(shape.checkIn(translate[0], translate[1])) shape.fireOver(null);
			else shape.fireOut(null);
		});
	}

	public double[] translate(double x, double y, Shape shape) {
		x = x - shape.getX();
		y = y - shape.getY();
		double theta = -shape.getRotate();
		return new double[] {
			x * Math.cos(theta) - y * Math.sin(theta)
			, x * Math.sin(theta) + y * Math.cos(theta)
		};
	}
	public void onOut(MouseOutEvent event) {
		shapes.forEach(shape->shape.fireOut(event));
	}

	private static final FocusImpl impl = FocusImpl.getFocusImplForWidget();
	@Override
	public int getTabIndex() {
		return impl.getTabIndex(getElement());
	}

	@Override
	public void setAccessKey(char key) {
		getElement().setPropertyString("accessKey", "" + key);
	}

	@Override
	public void setFocus(boolean focused) {
		if (focused) impl.focus(getElement());
		else impl.blur(getElement());
	}

	@Override
	public void setTabIndex(int index) {
		impl.setTabIndex(getElement(), index);
	}
}
