package net.sayaya.ui.handler;

@FunctionalInterface
public interface HasEnabled<W extends HasEnabled<W>> {
	W setEnabled(boolean enabled);
}
