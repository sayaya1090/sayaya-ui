package net.sayaya.ui.style;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public class StyleEmpty {
	public interface EmptyResource extends ClientBundle {
		@Source("Empty.gss")
		Style style();
		
		public static interface Style extends CssResource {
			String empty();
		}
	}
	private static final EmptyResource RESOURCE =  GWT.create(EmptyResource.class);
	static {
		RESOURCE.style().ensureInjected();
	}
	public static final EmptyResource.Style GSS = RESOURCE.style();
}
