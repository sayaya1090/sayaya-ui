package net.sayaya.ui.style;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public class StyleBreadcrumb {
	public interface BreadcrumbResource extends ClientBundle {
		@Source("Breadcrumb.gss")
		Style style();
		
		public static interface Style extends CssResource {
			String item();
			String splitter();
		}
	}
	private static final BreadcrumbResource RESOURCE =  GWT.create(BreadcrumbResource.class);
	static {
		RESOURCE.style().ensureInjected();
	}
	public static final BreadcrumbResource.Style GSS = RESOURCE.style();
}
