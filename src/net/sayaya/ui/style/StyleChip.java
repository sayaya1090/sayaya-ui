package net.sayaya.ui.style;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public class StyleChip {
	public interface ChipResource extends ClientBundle {
		@Source("Chip.gss")
		Style style();
		
		public static interface Style extends CssResource {
			String chip();
			String delete();
			@CssResource.ClassName("fade-in")
			String fadeIn();
			@CssResource.ClassName("fade-out")
			String fadeOut();
			String toggle();
			@CssResource.ClassName("toggle-false")
			String toggleFalse();
			@CssResource.ClassName("toggle-true")
			String toggleTrue();
		}
	}
	private static final ChipResource RESOURCE =  GWT.create(ChipResource.class);
	static {
		RESOURCE.style().ensureInjected();
	}
	public static final ChipResource.Style GSS = RESOURCE.style();
}