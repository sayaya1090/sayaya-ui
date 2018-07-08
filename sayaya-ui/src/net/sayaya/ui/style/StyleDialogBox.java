package net.sayaya.ui.style;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public class StyleDialogBox {
	public interface DialogBoxResource extends ClientBundle {
		@Source("DialogBox.gss")
		Style style();
		
		public static interface Style extends CssResource {
			@CssResource.ClassName("dialog-box")
			String dialogBox();
			String overlay();
		}
	}
	private static final DialogBoxResource RESOURCE =  GWT.create(DialogBoxResource.class);
	static {
		RESOURCE.style().ensureInjected();
	}
	public static final DialogBoxResource.Style GSS = RESOURCE.style();
}
