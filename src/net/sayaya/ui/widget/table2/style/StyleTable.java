package net.sayaya.ui.widget.table2.style;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public class StyleTable {
	public interface SheetResource extends ClientBundle {
		@Source("Table.gss")
		Style style();
		
		public static interface Style extends CssResource {
			String header();
			String viewport();
			String field();
			String table();
			@CssResource.ClassName("select-range")
			String selectRange();
			@CssResource.ClassName("select-range-square")
			String selectRangeSquare();
			@CssResource.ClassName("copy-range")
			String copyRange();
		}
	}
	private static final SheetResource RESOURCE =  GWT.create(SheetResource.class);
	static {
		RESOURCE.style().ensureInjected();
	}
	public static final SheetResource.Style GSS = RESOURCE.style();
}
