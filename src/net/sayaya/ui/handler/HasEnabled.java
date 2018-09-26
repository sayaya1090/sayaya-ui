package net.sayaya.ui.handler;

public interface HasEnabled<W extends HasEnabled<W>> {
	W setEnabled(boolean enabled);
	boolean isEnabled();
}
