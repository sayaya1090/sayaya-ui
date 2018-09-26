package net.sayaya.ui.style;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public class StyleButton {
	public interface ButtonResource extends ClientBundle {
		@Source("Button.gss")
		Style style();
		
		public static interface Style extends CssResource {
			String flat();
			String rised();
			String contained();
			String icon();
			@CssResource.ClassName("toggle-true")
			String toggleTrue();
		}
	}
	private static final ButtonResource RESOURCE =  GWT.create(ButtonResource.class);
	static {
		RESOURCE.style().ensureInjected();
	}
	public static final ButtonResource.Style GSS = RESOURCE.style();
}