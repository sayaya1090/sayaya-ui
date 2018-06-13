package net.sayaya.ui.style;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public class StyleCheckBox {
	public interface CheckBoxResource extends ClientBundle {
		@Source("CheckBox.gss")
		Style style();
		
		public static interface Style extends CssResource {
			String layout();
			String check();
			String square();
		}
	}
	private static final CheckBoxResource RESOURCE =  GWT.create(CheckBoxResource.class);
	static {
		RESOURCE.style().ensureInjected();
	}
	public static final CheckBoxResource.Style GSS = RESOURCE.style();
}
