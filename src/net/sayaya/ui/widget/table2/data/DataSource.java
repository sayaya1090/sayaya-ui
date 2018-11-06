package net.sayaya.ui.widget.table2.data;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

import lombok.Setter;
import lombok.experimental.Accessors;
import net.sayaya.ui.handler.Callback;

@Accessors(fluent = true)
public abstract class DataSource implements ValueChangeHandler<Long>, HasValueChangeHandlers<Map<String, Value<?>>> {
	@Setter
	private int fetchSize = 10;
	private final Map<Long, Map<String, Value<?>>> cache = new TreeMap<>();
	private final Set<ValueChangeHandler<Map<String, Value<?>>>> handlers = new HashSet<>();
	public abstract void getRowCount(Callback<Long> callback);
	public abstract void load(long offset, int fetchSize, Callback<Map<Long, Map<String, Value<?>>>> callback);
	
	public final void getValues(long rowMin, long rowMax, Callback<Map<Long, Map<String, Value<?>>>> callback) {
		try {
			load(rowMin, (int)(rowMax-rowMin), rst->{
				cache.putAll(rst);
				callback.onSuccess(rst);
			});
		} catch(Exception ex) {
			callback.onFailure(ex);
		}
	}
	
	public final void getValues(long row, Callback<Optional<Map<String, Value<?>>>> callback) {
		try {
			if(!cache.containsKey(row)) {
				load(row, fetchSize, rst->{
					cache.putAll(rst);
					if(cache.containsKey(row)) callback.onSuccess(Optional.of(cache.get(row)));
					else callback.onSuccess(Optional.empty());
				});
			} else callback.onSuccess(Optional.of(cache.get(row)));
		} catch(Exception ex) {
			callback.onFailure(ex);
		}
	}
	
	@SuppressWarnings("unchecked")
	public final <T> void getValue(int row, String key, Class<T> clazz, Callback<Optional<Value<T>>> callback) {
		getValues(row, rst->{
			if(rst.isPresent()) {
				Map<String, Value<?>> values = rst.get();
				if(!values.containsKey(key)) callback.onSuccess(Optional.empty());
				try {
					Value<T> value = (Value<T>) values.get(key);
					callback.onSuccess(Optional.of(value));
				} catch(Exception e) {
					callback.onFailure(e);
				}
			} else callback.onSuccess(Optional.empty());
		});
	}
		
	@Override
	public final void onValueChange(ValueChangeEvent<Long> event) {
		long changeRow = event.getValue();
		load(changeRow, 1, rst->{
			cache.putAll(rst);
			Map<String, Value<?>> values = cache.get(changeRow);
			ValueChangeEvent.fire(this, values);
		});
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public final void fireEvent(GwtEvent<?> event) {
		if(event instanceof ValueChangeEvent) {
			ValueChangeEvent<Map<String, Value<?>>> cast = (ValueChangeEvent<Map<String, Value<?>>>)event;
			for(ValueChangeHandler<Map<String, Value<?>>> handler: handlers) handler.onValueChange(cast);
		}
	}
	
	@Override
	public final HandlerRegistration addValueChangeHandler(ValueChangeHandler<Map<String, Value<?>>> handler) {
		handlers.add(handler);
		return ()->handlers.remove(handler);
	}
}
