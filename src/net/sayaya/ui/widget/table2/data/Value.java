package net.sayaya.ui.widget.table2.data;

import lombok.Setter;

@lombok.Data
public final class Value<T> {
	private final T source;
	private T value;
	
	public Value(T source, T value) {
		this.source = source;
		this.value = value;
	}
	
	public boolean isChanged() {
		if(source==null) return value!=null;
		return !source.equals(value);
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder {
		private Builder() {}
		public <T> BuilderComplete<T> source(T source) {
			BuilderComplete<T> builder = new BuilderComplete<T>();
			builder.setSource(source);
			return builder;
		}
		
		public <T> BuilderComplete<T> value(T value) {
			BuilderComplete<T> builder = new BuilderComplete<T>();
			builder.setValue(value);
			return builder;
		}
	}
	
	@Setter
	public final static class BuilderComplete<T> extends Builder {
		private T source;
		private T value;
		
		private BuilderComplete() {}
		public Value<T> build() {
			return new Value<T>(source, value);
		}
	}
}
