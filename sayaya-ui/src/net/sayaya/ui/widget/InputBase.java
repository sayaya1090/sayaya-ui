package net.sayaya.ui.widget;

import com.google.gwt.user.client.ui.IsWidget;

import net.sayaya.ui.handler.HasEnabled;
import net.sayaya.ui.handler.HasStyle;
import net.sayaya.ui.handler.HasValue;

public interface InputBase<T, SELF extends IsWidget&HasStyle<SELF>&HasEnabled<SELF>> extends IsWidget, HasStyle<SELF>, HasValue<T>, HasEnabled<SELF> {
	
}
