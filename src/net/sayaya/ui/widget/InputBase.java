package net.sayaya.ui.widget;

import com.google.gwt.user.client.ui.IsWidget;

import net.sayaya.ui.handler.HasEnabled;
import net.sayaya.ui.handler.HasStyle;
import net.sayaya.ui.handler.HasValueWidget;

public interface InputBase<T, SELF extends IsWidget&HasStyle<SELF>&HasEnabled<SELF>> extends HasStyle<SELF>, HasValueWidget<T>, HasEnabled<SELF> {
	
}
