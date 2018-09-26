package net.sayaya.ui.style;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public class StyleTextBox {
	public interface TextBoxResource extends ClientBundle {
		@Source("TextBox.gss")
		Style style();
		
		public static interface Style extends CssResource {
			String textbox();
			String textarea();
			String placeholder();
		}
	}
	private static final TextBoxResource RESOURCE =  GWT.create(TextBoxResource.class);
	static {
		RESOURCE.style().ensureInjected();
	}
	public static final TextBoxResource.Style GSS = RESOURCE.style();
}
