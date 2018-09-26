package net.sayaya.ui.style;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public class StyleTopBar {
	public interface TopBarResource extends ClientBundle {
		@Source("TopBar.gss")
		Style style();
		
		public static interface Style extends CssResource {
			String topbar();
		}
	}
	private static final TopBarResource RESOURCE =  GWT.create(TopBarResource.class);
	static {
		RESOURCE.style().ensureInjected();
	}
	public static final TopBarResource.Style GSS = RESOURCE.style();
}
