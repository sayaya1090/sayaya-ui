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
	public Icon() {
		setStyleName(Resource.instance.style().icon());
	}
	public Icon(String icon) {
		this();
		setIcon(icon);
	}
	
	public Icon setIcon(String icon) {
		if(icon!=null) {
			addStyleName(icon);
			addAttachHandler(evt->{
				if(!evt.isAttached()) return;
				Scheduler.get().scheduleDeferred(()->code = getCode(getElement()).replace("\"", ""));
			});
		}
		return this;
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
			String at();
			String atlas();
			String atom();
			@CssResource.ClassName("atom-alt")
			String atomAlt();
			String award();
			String ban();
			String barcode();
			@CssResource.ClassName("barcode-alt")
			String barcodeAlt();
			@CssResource.ClassName("barcode-read")
			String barcodeRead();
			@CssResource.ClassName("barcode-scan")
			String barcodeScan();
			String bold();
			String bong();
			String box();
			@CssResource.ClassName("box-check")
			String boxCheck();
			String boxes();
			String calendar();
			@CssResource.ClassName("calendar-alt")
			String calendarAlt();
			@CssResource.ClassName("calendar-check")
			String calendarCheck();
			@CssResource.ClassName("calendar-edit")
			String calendarEdit();
			@CssResource.ClassName("calendar-exclamation")
			String calendarExclamation();
			@CssResource.ClassName("calendar-minus")
			String calendarMinus();
			@CssResource.ClassName("calendar-plus")
			String calendarPlus();
			@CssResource.ClassName("calendar-times")
			String calendarTimes();
			@CssResource.ClassName("caret-circle-down")
			String caretCircleDown();
			@CssResource.ClassName("caret-circle-left")
			String caretCircleLeft();
			@CssResource.ClassName("caret-circle-right")
			String caretCircleRight();
			@CssResource.ClassName("caret-circle-up")
			String caretCircleUp();
			@CssResource.ClassName("caret-down")
			String caretDown();
			@CssResource.ClassName("caret-left")
			String caretLeft();
			@CssResource.ClassName("caret-right")
			String caretRight();
			@CssResource.ClassName("caret-up")
			String caretUp();
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
			String dna();
			String edit();
			String eraser();
			String exclamation();
			@CssResource.ClassName("exclamation-triangle")
			String exclamationTriangle();
			@CssResource.ClassName("exclamation-square")
			String exclamationSquare();
			@CssResource.ClassName("exclamation-circle")
			String exclamationCircle();
			@CssResource.ClassName("eye-dropper")
			String eyeDropper();
			@CssResource.ClassName("file")
			String file();
			@CssResource.ClassName("file-alt")
			String fileAlt();
			@CssResource.ClassName("file-download")
			String fileDownload();
			@CssResource.ClassName("file-excel")
			String fileExcel();
			@CssResource.ClassName("file-invoice")
			String fileInvoice();
			@CssResource.ClassName("file-medical")
			String fileMedical();
			@CssResource.ClassName("file-medical-alt")
			String fileMedicalAlt();
			@CssResource.ClassName("file-pdf")
			String filePdf();
			@CssResource.ClassName("file-plus")
			String filePlus();
			String filter();
			String font();
			String glasses();
			String globe();
			String h1();
			String h2();
			String h3();
			@CssResource.ClassName("hand-holding-box")
			String handHoldingBox();
			String hashtag();
			String heading();
			String heart();
			String highlighter();
			String home();
			@CssResource.ClassName("i-cursor")
			String iCursor();
			@CssResource.ClassName("id-badge")
			String idBadge();
			@CssResource.ClassName("id-card")
			String idCard();
			@CssResource.ClassName("id-card-alt")
			String idCardAlt();
			String indent();
			String info();
			@CssResource.ClassName("info-square")
			String infoSquare();
			@CssResource.ClassName("info-circle")
			String infoCircle();
			String italic();
			@CssResource.ClassName("layer-group")
			String layerGroup();
			@CssResource.ClassName("layer-plus")
			String layerPlus();
			@CssResource.ClassName("layer-minus")
			String layerMinus();
			String link();
			String list();
			@CssResource.ClassName("list-alt")
			String listAlt();
			@CssResource.ClassName("list-ol")
			String listOl();
			@CssResource.ClassName("list-ul")
			String listUl();
			String lock();
			@CssResource.ClassName("lock-alt")
			String lockAlt();
			@CssResource.ClassName("lock-open")
			String lockOpen();
			@CssResource.ClassName("lock-open-alt")
			String lockOpenAlt();
			String marker();
			String microscope();
			@CssResource.ClassName("mortar-pestle")
			String mortarPestle();
			String outdent();
			@CssResource.ClassName("paper-plane")
			String paperPlane();
			String paperclip();
			String paragraph();
			String paste();
			String pause();
			@CssResource.ClassName("pause-circle")
			String pauseCircle();
			String pen();
			@CssResource.ClassName("pen-alt")
			String penAlt();
			@CssResource.ClassName("pen-fancy")
			String penFancy();
			@CssResource.ClassName("pen-nib")
			String penNib();
			String pencil();
			String play();
			@CssResource.ClassName("play-circle")
			String playCircle();
			String plus();
			@CssResource.ClassName("plus-circle")
			String plusCircle();
			String prescription();
			@CssResource.ClassName("prescription-bottle")
			String prescriptionBottle();
			@CssResource.ClassName("prescription-bottle-alt")
			String prescriptionBottleAlt();
			String print();
			@CssResource.ClassName("puzzle-piece")
			String puzzlePiece();
			@CssResource.ClassName("quote-left")
			String quoteLeft();
			@CssResource.ClassName("quote-right")
			String quoteRight();
			String redo();
			@CssResource.ClassName("redo-alt")
			String redoAlt();
			String repeat();
			@CssResource.ClassName("repeat-alt")
			String repeatAlt();
			String reply();
			@CssResource.ClassName("reply-alt")
			String replyAlt();
			String save();
			String search();
			String scanner();
			@CssResource.ClassName("scanner-keyboard")
			String scannerKeyboard();
			@CssResource.ClassName("scanner-touchscreen")
			String scannerTouchscreen();
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
			String stop();
			@CssResource.ClassName("stop-circle")
			String stopCircle();
			@CssResource.ClassName("step-backward")
			String stepBackward();
			@CssResource.ClassName("step-forward")
			String stepForward();
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
			String unlock();
			@CssResource.ClassName("unlock-alt")
			String unlockAlt();
			String unlink();
			@CssResource.ClassName("user-chart")
			String userChart();
			@CssResource.ClassName("user-circle")
			String userCircle();
			@CssResource.ClassName("user-lock")
			String userLock();
			String vial();
			String vials();
			String wranch();
		}
	}
}
