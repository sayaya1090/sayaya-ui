package net.sayaya.ui.style;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public class StyleComboBox {
	public interface ComboBoxResource extends ClientBundle {
		@Source("ComboBox.gss")
		Style style();
		
		public static interface Style extends CssResource {
			String combobox();
		}
	}
	private static final ComboBoxResource RESOURCE =  GWT.create(ComboBoxResource.class);
	static {
		RESOURCE.style().ensureInjected();
	}
	public static final ComboBoxResource.Style GSS = RESOURCE.style();
}
