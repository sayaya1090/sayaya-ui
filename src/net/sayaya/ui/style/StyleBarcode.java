package net.sayaya.ui.style;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public class StyleBarcode {
	public interface BarcodeResource extends ClientBundle {
		@Source("Barcode.gss")
		Style style();
		
		public static interface Style extends CssResource {
			String barcode();
		}
	}
	private static final BarcodeResource RESOURCE =  GWT.create(BarcodeResource.class);
	static {
		RESOURCE.style().ensureInjected();
	}
	public static final BarcodeResource.Style GSS = RESOURCE.style();
}
