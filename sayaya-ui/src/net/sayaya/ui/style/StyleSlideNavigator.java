package net.sayaya.ui.style;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public class StyleSlideNavigator {
	public interface SlideNavigatorResource extends ClientBundle {
		@Source("SlideNavigator.gss")
		Style style();
		
		public static interface Style extends CssResource {
			String leftbar();
			String foot();
			String trapezoid();
		}
	}
	private static final SlideNavigatorResource RESOURCE =  GWT.create(SlideNavigatorResource.class);
	static {
		RESOURCE.style().ensureInjected();
	}
	public static final SlideNavigatorResource.Style GSS = RESOURCE.style();
}
