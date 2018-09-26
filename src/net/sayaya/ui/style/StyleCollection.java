package net.sayaya.ui.style;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public class StyleCollection {
	public interface CollectionResource extends ClientBundle {
		@Source("Collection.gss")
		Style style();
		
		public static interface Style extends CssResource {
			String collection();
		}
	}
	private static final CollectionResource RESOURCE =  GWT.create(CollectionResource.class);
	static {
		RESOURCE.style().ensureInjected();
	}
	public static final CollectionResource.Style GSS = RESOURCE.style();
}
