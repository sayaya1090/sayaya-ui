package net.sayaya.ui.handler;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.user.client.ui.IsWidget;

public interface HasValueWidget<T> extends IsWidget, HasValue<T>, HasValueChangeHandlers<T> {

}
