package net.sayaya.ui.handler;

public interface Focusable<W extends Focusable<W>> {
	int getTabIndex();
	W setAccessKey(char key);
	W setFocus(boolean focused);
	W setTabIndex(int index);
}
