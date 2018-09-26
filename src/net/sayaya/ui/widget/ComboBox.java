package net.sayaya.ui.widget;

import java.util.Arrays;
import java.util.function.Function;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Composite;

import net.sayaya.ui.style.StyleComboBox;

public class ComboBox<T> extends Composite implements InputBase<T, ComboBox<T>> {
	private final EventBus bus = new SimpleEventBus();
	private final com.google.gwt.user.client.ui.ListBox widget;
	private final Function<T, String> toString;
	private T[] items;
	public ComboBox() {
		this(obj->obj!=null?String.valueOf(obj):"");
	}
	
	public ComboBox(Function<T, String> toString) {
		widget = new com.google.gwt.user.client.ui.ListBox();
		this.toString = toString;
		initWidget(widget);
		style(this);
		widget.addChangeHandler(evt->{
			int select = widget.getSelectedIndex();
			T item = items[select-1];
			ValueChangeEvent.fire(this, item);
		});
		widget.addItem("");
	}
	
	public ComboBox<T> setItems(@SuppressWarnings("unchecked") T... items) {
		this.items = items;
		widget.clear();
		widget.addItem("");
		Arrays.stream(items).map(item->toString.apply(item)).forEach(str->widget.addItem(str));
		return this;
	}
	
	public ComboBox<T> clear() {
		this.items = null;
		widget.clear();
		widget.addItem("");
		return this;
	}
	
	@Override
	public void fireEvent(GwtEvent<?> event) {
		if(event instanceof ValueChangeEvent) bus.fireEvent(event);
		else super.fireEvent(event);
	}
	
	@Override
	public ComboBox<T> style(ComboBox<T> widet) {
		widget.setStyleName(StyleComboBox.GSS.combobox());
		return this;
	}
	
	@Override
	public ComboBox<T> setValue(T value) {
		if(value == null) {
			widget.setSelectedIndex(0);
			return this;
		} else {
			String str = toString.apply(value);
			for(int i = 1; i < widget.getItemCount(); ++i) {
				String item = widget.getItemText(i);
				if(item.equals(str)) {
					widget.setSelectedIndex(i);
					return this;
				}
			}
		return this;
		}
	}
	
	@Override
	public T getValue() {
		if(items==null || items.length<=0) return null;
		return items[widget.getSelectedIndex()-1];
	}
	
	@Override
	public boolean isEmpty() {
		return widget.getSelectedIndex()<=0;
	}
	
	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<T> handler) {
		return bus.addHandler(ValueChangeEvent.getType(), handler);
	}
	
	@Override
	public ComboBox<T> setEnabled(boolean enabled) {
		widget.setEnabled(enabled);
		return this;
	}
	
	@Override
	public boolean isEnabled() {
		return widget.isEnabled();
	}
}