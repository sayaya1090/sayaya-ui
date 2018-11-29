package net.sayaya.ui.style;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public class StyleSlideNavigator {
	public interface SlideNavigatorResource extends ClientBundle {
		@Source("SlideNavigator.gss")
		Style style();
		
		public static interface Style extends CssResource {
			String leftbar();
			String foot();
			String knocker();
			@CssResource.ClassName("knocker-icon")
			String knockerIcon();
			
			String item();
			@CssResource.ClassName("item-selected")
			String itemSelected();
			@CssResource.ClassName("item-icon")
			String itemIcon();
			@CssResource.ClassName("item-label")
			String itemLabel();
			@CssResource.ClassName("item-container")
			String itemContainer();
			
			@CssResource.ClassName("item-sub")
			String itemSub();
			@CssResource.ClassName("item-sub-selected")
			String itemSubSelected();
			@CssResource.ClassName("item-sub-icon")
			String itemSubIcon();
			@CssResource.ClassName("item-sub-label")
			String itemSubLabel();
		}
	}
	private static final SlideNavigatorResource RESOURCE =  GWT.create(SlideNavigatorResource.class);
	static {
		RESOURCE.style().ensureInjected();
	}
	public static SlideNavigatorResource.Style GSS = RESOURCE.style();
}
