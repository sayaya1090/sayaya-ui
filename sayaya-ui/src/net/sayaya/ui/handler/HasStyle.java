package net.sayaya.ui.handler;

import com.google.gwt.user.client.ui.IsWidget;

@FunctionalInterface
public interface HasStyle<W extends HasStyle<W>&IsWidget> {
	W style(W widet);
}
