package net.sayaya.ui.style;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public class StylePage {
	public interface PageResource extends ClientBundle {
		@Source("Page.gss")
		Style style();
		
		public static interface Style extends CssResource {
			String page();
			String item();
			@CssResource.ClassName("icon-button")
			String iconButton();
			String label();
			String box();
		}
	}
	private static final PageResource RESOURCE =  GWT.create(PageResource.class);
	static {
		RESOURCE.style().ensureInjected();
	}
	public static final PageResource.Style GSS = RESOURCE.style();
}
