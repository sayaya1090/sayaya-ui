package net.sayaya.ui.style;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public class StyleButton {
	public interface ButtonResource extends ClientBundle {
		@Source("Button.gss")
		Style style();
		
		public static interface Style extends CssResource {
			String flat();
			String rised();
			String contained();
		}
	}
	private static final ButtonResource RESOURCE =  GWT.create(ButtonResource.class);
	static {
		RESOURCE.style().ensureInjected();
	}
	public static final ButtonResource.Style GSS = RESOURCE.style();
}

/*
 * :root {
    --color-ripple: rgba(255,255,255,0.8);
}

body {
    background: #36353c;
}

.container {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    height: 50px;
    width: 200px;
    margin: auto;
}
*[data-animation="ripple"] {
    position: relative;
    height: 100%;
    width: 100%;
    display: block;
    outline: none;
    padding: 20px;
    color: #fff;
    text-transform: uppercase;
    background: linear-gradient(135deg, #e570e7 0%,#79f1fc 100%);
    box-sizing: border-box;
    text-align: center;
    line-height: 14px;
    font-family: roboto, helvetica;
    font-weight: 200;
    letter-spacing: 1px;
    text-decoration: none;
    box-shadow: 0 5px 3px rgba(0, 0, 0, 0.3);
    cursor: pointer;

    -webkit-tap-highlight-color: transparent;
}

*[data-animation="ripple"]:focus {
    outline: none;
}

*[data-animation="ripple"]::selection {
    background: transparent;
    pointer-events: none;
}*/

/*
const isMobile = window.navigator.userAgent.match(/Mobile/) && window.navigator.userAgent.match(/Mobile/)[0] === "Mobile";
const event = isMobile ? "touchstart" : "click";

const button = document.querySelectorAll('*[data-animation="ripple"]'),
            container = document.querySelector(".container");

for (var i = 0; i < button.length; i++) {
    const currentBtn = button[i];
    
    currentBtn.addEventListener(event, function(e) {
        
        e.preventDefault();
        const button = e.target,
                    rect = button.getBoundingClientRect(),
                    originalBtn = this,
                    btnHeight = rect.height,
                    btnWidth = rect.width;
        let posMouseX = 0,
                posMouseY = 0;
        
        if (isMobile) {
            posMouseX = e.changedTouches[0].pageX - rect.left;
            posMouseY = e.changedTouches[0].pageY - rect.top;
        } else {
            posMouseX = e.x - rect.left;
            posMouseY = e.y - rect.top;
        }
        
        const baseCSS =  `position: absolute;
                                            width: ${btnWidth * 2}px;
                                            height: ${btnWidth * 2}px;
                                            transition: all linear 700ms;
                                            transition-timing-function:cubic-bezier(0.250, 0.460, 0.450, 0.940);
                                            border-radius: 50%;
                                            background: var(--color-ripple);
                                            top:${posMouseY - btnWidth}px;
                                            left:${posMouseX - btnWidth}px;
                                            pointer-events: none;
                                            transform:scale(0)`
        
        var rippleEffect = document.createElement("span");
        rippleEffect.style.cssText = baseCSS;
        
        //prepare the dom
        currentBtn.style.overflow = "hidden";
        this.appendChild(rippleEffect);
        
        //start animation
        setTimeout( function() { 
            rippleEffect.style.cssText = baseCSS + `transform:scale(1); opacity: 0;`;
        }, 5);
        
        setTimeout( function() {
            rippleEffect.remove();
            //window.location.href = currentBtn.href;
        },700);
    })
}*/