package net.sayaya.ui.widget;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.impl.FocusImpl;

import net.sayaya.ui.handler.Focusable;
import net.sayaya.ui.handler.HasEnabled;
import net.sayaya.ui.handler.HasStyle;
import net.sayaya.ui.handler.HasValueWidget;

@SuppressWarnings("unchecked")
public interface InputBase<T, SELF extends IsWidget&HasStyle<SELF>&HasEnabled<SELF>&Focusable<SELF>> extends HasStyle<SELF>, HasValueWidget<T>, HasEnabled<SELF>, Focusable<SELF> {
	static final FocusImpl impl = FocusImpl.getFocusImplForWidget();
	@Override
	default int getTabIndex() {
		return impl.getTabIndex(asWidget().getElement());
	}

	@Override
	default SELF setAccessKey(char key) {
		asWidget().getElement().setPropertyString("accessKey", "" + key);
		return (SELF)this;
	}

	@Override
	default SELF setFocus(boolean focused) {
		if(focused) impl.focus(asWidget().getElement());
		else impl.blur(asWidget().getElement());
		return (SELF)this;
	}

	@Override
	default SELF setTabIndex(int index) {
		impl.setTabIndex(asWidget().getElement(), index);
		return (SELF)this;
	}
}
