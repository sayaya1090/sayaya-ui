package net.sayaya.ui.style;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public class StyleLabel {
	public interface LabelResource extends ClientBundle {
		@Source("Label.gss")
		Style style();
		
		public static interface Style extends CssResource {
			String label();
			String numeric();
		}
	}
	private static final LabelResource RESOURCE =  GWT.create(LabelResource.class);
	static {
		RESOURCE.style().ensureInjected();
	}
	public static final LabelResource.Style GSS = RESOURCE.style();
}
