package net.sayaya.ui.style;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public class StyleSheet {
	public interface SheetResource extends ClientBundle {
		@Source("Sheet.gss")
		Style style();
		
		public static interface Style extends CssResource {
			String sheet();
		}
	}
	private static final SheetResource RESOURCE =  GWT.create(SheetResource.class);
	static {
		RESOURCE.style().ensureInjected();
	}
	public static final SheetResource.Style GSS = RESOURCE.style();
}
