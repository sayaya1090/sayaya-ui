package net.sayaya.ui.style;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public class StylePopupPanel {
	public interface PopupPanelResource extends ClientBundle {
		@Source("PopupPanel.gss")
		Style style();
		
		public static interface Style extends CssResource {
			String popup();
			String item();
			@CssResource.ClassName("item-hover")
			String itemHover();
			String label();
			String icon();
		}
	}
	private static final PopupPanelResource RESOURCE =  GWT.create(PopupPanelResource.class);
	static {
		RESOURCE.style().ensureInjected();
	}
	public static final PopupPanelResource.Style GSS = RESOURCE.style();
}
