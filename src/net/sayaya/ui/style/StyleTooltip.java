package net.sayaya.ui.style;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public class StyleTooltip {
	public interface TooltipResource extends ClientBundle {
		@Source("Tooltip.gss")
		Style style();
		
		public static interface Style extends CssResource {
			String tooltip();
			String fadein1();
			String fadein2();
		}
	}
	private static final TooltipResource RESOURCE =  GWT.create(TooltipResource.class);
	static {
		RESOURCE.style().ensureInjected();
	}
	public static final TooltipResource.Style GSS = RESOURCE.style();
}