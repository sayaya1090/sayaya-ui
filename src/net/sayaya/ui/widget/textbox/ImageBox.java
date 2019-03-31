package net.sayaya.ui.widget.textbox;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;

import net.sayaya.ui.handler.Callback;
import net.sayaya.ui.style.StyleTextBox;
import net.sayaya.ui.widget.InputBase;

public final class ImageBox extends Composite implements InputBase<String, ImageBox> {
	private final EventBus bus = new SimpleEventBus();
	private boolean enabled = true;
	private String value = null;
	public ImageBox() {
		initWidget(new com.google.gwt.user.client.ui.TextBox());
		this.sinkEvents(Event.ONPASTE|Event.KEYEVENTS);
		style(this);
	}
	
	@Override
	public void onBrowserEvent(Event evt) {
		if("paste".equalsIgnoreCase(evt.getType())) {
			getPastedByte(evt, str->setValue(str));
		} else if("keydown".equalsIgnoreCase(evt.getType())){
			if(evt.getKeyCode() == 8				// Backspace
			|| evt.getKeyCode() == 46) {			// Delete
				getElement().getStyle().clearBackgroundImage();
				value = null;
			}
			evt.stopPropagation();
		} else super.onBrowserEvent(evt);
	}
	
	@Override
	public void fireEvent(GwtEvent<?> event) {
		if(event instanceof ValueChangeEvent) bus.fireEvent(event);
		else super.fireEvent(event);
	}
	
	@Override
	public ImageBox style(ImageBox widet) {
		setStyleName(StyleTextBox.GSS.textarea());
		getElement().getStyle().setProperty("backgroundSize", "cover");
		getElement().getStyle().setProperty("backgroundRepeat", "no-repeat");
		getElement().getStyle().setProperty("backgroundPosition", "center center");
		getElement().getStyle().setColor("transparent");
		return this;
	}

	@Override
	public ImageBox setValue(String value) {
		String oldValue = this.value;
		getElement().getStyle().setBackgroundImage("url(" + value + ")");
		this.value = value;
		ValueChangeEvent.fireIfNotEqual(this, oldValue, value);
		return this;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public boolean isEmpty() {
		return value == null || value.isEmpty();
	}

	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
		return bus.addHandler(ValueChangeEvent.getType(), handler);
	}

	@Override
	public ImageBox setEnabled(boolean enabled) {
		this.enabled = enabled;
		return this;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
	private static native void getPastedByte(Event event, final Callback<String> callback) /*-{
		if (event != null && event.clipboardData) { // WebKit/Chrome/Safari
			var items = event.clipboardData.items;
			try { 
				var blob = items[0].getAsFile();
				var reader = new FileReader();
				reader.onload = function(event) {
					callback.@com.google.gwt.core.client.Callback::onSuccess(Ljava/lang/Object;)(event.target.result);
				};
				reader.readAsDataURL(blob);
			} catch (e) {
				callback.@com.google.gwt.core.client.Callback::onFailure(Ljava/lang/Object;)(null);
				return;
			} 
		}  else if ($wnd.clipboardData) { // IE 
			try  {
				var fileList = $wnd.clipboardData.files;
				if(fileList.length > 0) {
					var file = fileList[0];
					var reader = new FileReader();
					reader.onload = function(event) {
						callback.@com.google.gwt.core.client.Callback::onSuccess(Ljava/lang/Object;)(event.target.result);
					};
					reader.readAsDataURL(file);
				} else {
					callback.@com.google.gwt.core.client.Callback::onFailure(Ljava/lang/Object;)(null);
					return;
				}
			} catch (e) { 
				callback.@com.google.gwt.core.client.Callback::onFailure(Ljava/lang/Object;)(null);
			} 
		} else {
			alert("???");
		}
	   
		try { 
			if (netscape.security.PrivilegeManager.enablePrivilege) { 
				netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect"); 
			} else { 
				return; 
			} 
		} 
		catch (ex) { 
			return; 
		} 
		var clip = Components.classes["@mozilla.org/widget/clipboard;1"].getService(Components.interfaces.nsIClipboard); 
		if (!clip) return; 
		var trans = Components.classes["@mozilla.org/widget/transferable;1"].createInstance(Components.interfaces.nsITransferable); 
		if (!trans) return; 
		trans.addDataFlavor("text/unicode"); 
		clip.getData(trans, clip.kGlobalClipboard); 
		var str = new Object(); 
		var strLength = new Object(); 
		trans.getTransferData("text/unicode", str, strLength); 
		if (str) str = str.value.QueryInterface(Components.interfaces.nsISupportsString); 
		if (str) text = str.data.substring(0, strLength.value / 2); 
	}-*/;
}
