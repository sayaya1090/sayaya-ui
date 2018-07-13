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
			@CssResource.ClassName("align-left")
			String alignLeft();
			@CssResource.ClassName("align-right")
			String alignRight();
			String bold();
			String box();
			@CssResource.ClassName("box-check")
			String boxCheck();
			String boxes();
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
			@CssResource.ClassName("cart-arrow-down")
			String cartArrowDown();
			@CssResource.ClassName("cart-plus")
			String cartPlus();
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
			String clipboard();
			@CssResource.ClassName("clipboard-check")
			String clipboardCheck();
			@CssResource.ClassName("clipboard-list")
			String clipboardList();
			String clone();
			@CssResource.ClassName("cloud-download")
			String cloudDownload();
			@CssResource.ClassName("cloud-download-alt")
			String cloudDownloadAlt();
			String cog();
			String cogs();
			String columns();
			String copy();
			String cut();
			String edit();
			String eraser();
			String exclamation();
			@CssResource.ClassName("exclamation-triangle")
			String exclamationTriangle();
			@CssResource.ClassName("exclamation-square")
			String exclamationSquare();
			@CssResource.ClassName("exclamation-circle")
			String exclamationCircle();
			@CssResource.ClassName("file")
			String file();
			@CssResource.ClassName("file-alt")
			String fileAlt();
			@CssResource.ClassName("file-plus")
			String filePlus();
			String font();
			String glasses();
			String globe();
			String h1();
			String h2();
			String h3();
			@CssResource.ClassName("hand-holding-box")
			String handHoldingBox();
			String heading();
			String highlighter();
			String home();
			@CssResource.ClassName("i-cursor")
			String iCursor();
			String indent();
			String info();
			@CssResource.ClassName("info-square")
			String infoSquare();
			@CssResource.ClassName("info-circle")
			String infoCircle();
			String italic();
			String link();
			String list();
			@CssResource.ClassName("list-alt")
			String listAlt();
			@CssResource.ClassName("list-ol")
			String listOl();
			@CssResource.ClassName("list-ul")
			String listUl();
			@CssResource.ClassName("marker")
			String marker();
			String outdent();
			@CssResource.ClassName("paper-plane")
			String paperPlane();
			String paperclip();
			String paragraph();
			String paste();
			String pen();
			@CssResource.ClassName("pen-alt")
			String penAlt();
			@CssResource.ClassName("pen-fancy")
			String penFancy();
			@CssResource.ClassName("pen-nib")
			String penNib();
			String pencil();
			String plus();
			@CssResource.ClassName("plus-circle")
			String plusCircle();
			String print();
			@CssResource.ClassName("quote-left")
			String quoteLeft();
			@CssResource.ClassName("quote-right")
			String quoteRight();
			String redo();
			@CssResource.ClassName("redo-alt")
			String redoAlt();
			String reply();
			@CssResource.ClassName("reply-alt")
			String replyAlt();
			String save();
			String search();
			String share();
			@CssResource.ClassName("share-all")
			String shareAll();
			@CssResource.ClassName("share-alt")
			String shareAlt();
			@CssResource.ClassName("share-square")
			String shareSquare();
			@CssResource.ClassName("share-alt-square")
			String shareAltSquare();
			@CssResource.ClassName("shopping-bag")
			String shoppingBag();
			@CssResource.ClassName("shopping-basket")
			String shoppingBasket();
			@CssResource.ClassName("shopping-cart")
			String shoppingCart();
			@CssResource.ClassName("sign-in")
			String signoIn();
			@CssResource.ClassName("sign-in-alt")
			String signoInAlt();
			@CssResource.ClassName("sign-out")
			String signoOut();
			@CssResource.ClassName("sign-out-alt")
			String signoOutAlt();
			String sitemap();
			String square();
			String strikethrough();
			String subscript();
			String superscript();
			String sync();
			@CssResource.ClassName("sync-alt")
			String syncAlt();
			String table();
			String tag();
			String tags();
			String tasks();
			@CssResource.ClassName("text-height")
			String textHeight();
			@CssResource.ClassName("text-width")
			String textWidth();
			String th();
			@CssResource.ClassName("th-large")
			String thLarge();
			@CssResource.ClassName("th-list")
			String thList();
			String trash();
			@CssResource.ClassName("trash-alt")
			String trashAlt();
			@CssResource.ClassName("times")
			String times();
			@CssResource.ClassName("times-circle")
			String timesCircle();
			@CssResource.ClassName("times-square")
			String timesSquare();
			String underline();
			String undo();
			@CssResource.ClassName("undo-alt")
			String undoAlt();
			String unlink();
			@CssResource.ClassName("user-circle")
			String userCircle();
			String wranch();
		}
	}
}
