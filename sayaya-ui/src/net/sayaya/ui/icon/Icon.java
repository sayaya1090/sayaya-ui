package net.sayaya.ui.icon;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Element;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.HTML;

public class Icon extends HTML {
	private static final Resource RESOURCE =  GWT.create(Resource.class);
	static {
		RESOURCE.style().ensureInjected();
	}
	public static final Resource.Style GSS = RESOURCE.style();
	
	public static Icon create(String gss) {
		return new Icon(gss);
	}
	private String code = null;
	public Icon(String icon) {
		setStyleName(Resource.instance.style().icon());
		addStyleName(icon);
		addAttachHandler(evt->{
			if(!evt.isAttached()) return;
			Scheduler.get().scheduleDeferred(()->code = getCode(getElement()).replace("\"", ""));
		});
	}
	
	public String getCode() {
		return code;
	}

	public native static String getCode(Element em) /*-{
		return $wnd.getComputedStyle(em, ':before').getPropertyValue("content");
	}-*/;
	
	public interface Resource extends ClientBundle {
		public static final Resource instance=  GWT.create(Resource.class);
		@Source("Icon.gss")
		Style style();
		
		public static interface Style extends CssResource {
			String icon();
			String regular();
			String light();
			String solid();
			@CssResource.ClassName("align-center")
			String alignCenter();
			@CssResource.ClassName("align-justify")
			String alignJustify();
			@CssResource.ClassName("box")
			String box();
			@CssResource.ClassName("box-check")
			String boxCheck();
			@CssResource.ClassName("caret-circle-down")
			String caretCircleDown();
			@CssResource.ClassName("caret-circle-left")
			String caretCircleLeft();
			@CssResource.ClassName("caret-circle-right")
			String caretCircleRight();
			@CssResource.ClassName("caret-circle-up")
			String caretUp();
			@CssResource.ClassName("caret-down")
			String caretDown();
			@CssResource.ClassName("caret-left")
			String caretLeft();
			@CssResource.ClassName("caret-right")
			String caretRight();
			@CssResource.ClassName("caret-up")
			String caretCircleUp();
			@CssResource.ClassName("caret-square-down")
			String caretSquareDown();
			@CssResource.ClassName("caret-square-left")
			String caretSquareLeft();
			@CssResource.ClassName("caret-square-right")
			String caretSquareRight();
			@CssResource.ClassName("caret-square-up")
			String caretSquareUp();
			@CssResource.ClassName("check")
			String check();
			@CssResource.ClassName("check-circle")
			String checkCircle();
			@CssResource.ClassName("check-square")
			String checkSquare();
			@CssResource.ClassName("chevron-double-down")
			String chevronDoubleDown();
			@CssResource.ClassName("chevron-double-left")
			String chevronDoubleLeft();
			@CssResource.ClassName("chevron-double-right")
			String chevronDoubleRight();
			@CssResource.ClassName("chevron-double-up")
			String chevronDoubleUp();
			@CssResource.ClassName("chevron-down")
			String chevronDown();
			@CssResource.ClassName("chevron-left")
			String chevronLeft();
			@CssResource.ClassName("chevron-right")
			String chevronRight();
			@CssResource.ClassName("chevron-up")
			String chevronUp();
			@CssResource.ClassName("cloud-download")
			String cloudDownload();
			@CssResource.ClassName("cloud-download-alt")
			String cloudDownloadAlt();
			@CssResource.ClassName("file")
			String file();
			@CssResource.ClassName("file-alt")
			String fileAlt();
			@CssResource.ClassName("file-plus")
			String filePlus();
			@CssResource.ClassName("pencil")
			String pencil();
			@CssResource.ClassName("plus")
			String plus();
			@CssResource.ClassName("plus-circle")
			String plusCircle();
			@CssResource.ClassName("save")
			String save();
			@CssResource.ClassName("search")
			String search();
			@CssResource.ClassName("sign-in")
			String signoIn();
			@CssResource.ClassName("sign-in-alt")
			String signoInAlt();
			@CssResource.ClassName("sign-out")
			String signoOut();
			@CssResource.ClassName("sign-out-alt")
			String signoOutAlt();
			String square();
			@CssResource.ClassName("times")
			String times();
			@CssResource.ClassName("times-circle")
			String timesCircle();
			@CssResource.ClassName("times-square")
			String timesSquare();
			@CssResource.ClassName("user-circle")
			String userCircle();
		}
	}
}
