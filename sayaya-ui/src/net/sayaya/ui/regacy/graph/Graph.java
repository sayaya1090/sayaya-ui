package net.sayaya.ui.regacy.graph;

import net.sayaya.ui.handler.HasValue;
import net.sayaya.ui.regacy.shape.Animation;
import net.sayaya.ui.regacy.shape.Canvas;

public abstract class Graph<D> extends Canvas implements HasValue<D[]>{
	private Agenda[] agenda = null;
	private D[] data = null;
	private boolean initialized = false;
	private final Animation animation = new Animation(this);
	public Graph(int width, int height) {
		super(width, height);
	}
	public final Graph<D> setAgenda(Agenda... agenda) {
		this.agenda = agenda;
		requireInitialize();
		return this;
	}
	public final Agenda[] getAgenda() {
		return agenda;
	}
	@Override
	public Graph<D> setValue(D[] datas) {
		this.data = datas;
		requireInitialize();
		return this;
	}
	
	@Override
	public final D[] getValue() {
		return data;
	}
	protected abstract void setShapes();
	protected final void requireInitialize() {
		initialized = false;
	}
	public void paint() {
		if(animation.isRunning()) animation.cancel();
		super.paint();
	}
	@Override
	public void paint(double progress) {
		if(!initialized) {
			setShapes();
			initialized = true;
		}
		paintDelegate(progress);
	}
	protected void paintDelegate(double progress) {
		super.paint(progress);
	}
	public void update(int duration) {
		if(!animation.isRunning()) animation.run(duration);
	}
}
