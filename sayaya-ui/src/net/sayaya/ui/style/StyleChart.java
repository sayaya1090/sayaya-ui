package net.sayaya.ui.style;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public class StyleChart {
	public interface ChartResource extends ClientBundle {
		@Source("Chart.gss")
		Style style();
		
		public static interface Style extends CssResource {
			@CssResource.ClassName("even-row")
			String evenRow();
			@CssResource.ClassName("odd-row")
			String oddRow();
			@CssResource.ClassName("error-row")
			String errorRow();
			@CssResource.ClassName("selected-row")
			String selectedRow();
			String empty();
		}
	}
	private static final ChartResource RESOURCE =  GWT.create(ChartResource.class);
	static {
		RESOURCE.style().ensureInjected();
	}
	public static final ChartResource.Style GSS = RESOURCE.style();
}
