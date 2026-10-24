const e=customElements.define.bind(customElements);function t(e,t,i,s){var o,r=arguments.length,n=r<3?t:null===s?s=Object.getOwnPropertyDescriptor(t,i):s;if("object"==typeof Reflect&&"function"==typeof Reflect.decorate)n=Reflect.decorate(e,t,i,s);else for(var a=e.length-1;a>=0;a--)(o=e[a])&&(n=(r<3?o(n):r>3?o(t,i,n):o(t,i))||n);return r>3&&n&&Object.defineProperty(t,i,n),n}customElements.define=(t,i,s)=>{customElements.get(t)||e(t,i,s)},"function"==typeof SuppressedError&&SuppressedError;
/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */
const i=e=>(t,i)=>{void 0!==i?i.addInitializer(()=>{customElements.define(e,t)}):customElements.define(e,t)},s=globalThis,o=s.ShadowRoot&&(void 0===s.ShadyCSS||s.ShadyCSS.nativeShadow)&&"adoptedStyleSheets"in Document.prototype&&"replace"in CSSStyleSheet.prototype,r=Symbol(),n=new WeakMap;
/**
 * @license
 * Copyright 2019 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */let a=class{constructor(e,t,i){if(this._$cssResult$=!0,i!==r)throw Error("CSSResult is not constructable. Use `unsafeCSS` or `css` instead.");this.cssText=e,this.t=t}get styleSheet(){let e=this.o;const t=this.t;if(o&&void 0===e){const i=void 0!==t&&1===t.length;i&&(e=n.get(t)),void 0===e&&((this.o=e=new CSSStyleSheet).replaceSync(this.cssText),i&&n.set(t,e))}return e}toString(){return this.cssText}};const l=e=>new a("string"==typeof e?e:e+"",void 0,r),c=(e,...t)=>{const i=1===e.length?e[0]:t.reduce((t,i,s)=>t+(e=>{if(!0===e._$cssResult$)return e.cssText;if("number"==typeof e)return e;throw Error("Value passed to 'css' function must be a 'css' function result: "+e+". Use 'unsafeCSS' to pass non-literal values, but take care to ensure page security.")})(i)+e[s+1],e[0]);return new a(i,e,r)},d=o?e=>e:e=>e instanceof CSSStyleSheet?(e=>{let t="";for(const i of e.cssRules)t+=i.cssText;return l(t)})(e):e,{is:h,defineProperty:p,getOwnPropertyDescriptor:u,getOwnPropertyNames:m,getOwnPropertySymbols:v,getPrototypeOf:b}=Object,y=globalThis,g=y.trustedTypes,f=g?g.emptyScript:"",x=y.reactiveElementPolyfillSupport,w=(e,t)=>e,_={toAttribute(e,t){switch(t){case Boolean:e=e?f:null;break;case Object:case Array:e=null==e?e:JSON.stringify(e)}return e},fromAttribute(e,t){let i=e;switch(t){case Boolean:i=null!==e;break;case Number:i=null===e?null:Number(e);break;case Object:case Array:try{i=JSON.parse(e)}catch(e){i=null}}return i}},k=(e,t)=>!h(e,t),$={attribute:!0,type:String,converter:_,reflect:!1,useDefault:!1,hasChanged:k};
/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */Symbol.metadata??=Symbol("metadata"),y.litPropertyMetadata??=new WeakMap;let C=class extends HTMLElement{static addInitializer(e){this._$Ei(),(this.l??=[]).push(e)}static get observedAttributes(){return this.finalize(),this._$Eh&&[...this._$Eh.keys()]}static createProperty(e,t=$){if(t.state&&(t.attribute=!1),this._$Ei(),this.prototype.hasOwnProperty(e)&&((t=Object.create(t)).wrapped=!0),this.elementProperties.set(e,t),!t.noAccessor){const i=Symbol(),s=this.getPropertyDescriptor(e,i,t);void 0!==s&&p(this.prototype,e,s)}}static getPropertyDescriptor(e,t,i){const{get:s,set:o}=u(this.prototype,e)??{get(){return this[t]},set(e){this[t]=e}};return{get:s,set(t){const r=s?.call(this);o?.call(this,t),this.requestUpdate(e,r,i)},configurable:!0,enumerable:!0}}static getPropertyOptions(e){return this.elementProperties.get(e)??$}static _$Ei(){if(this.hasOwnProperty(w("elementProperties")))return;const e=b(this);e.finalize(),void 0!==e.l&&(this.l=[...e.l]),this.elementProperties=new Map(e.elementProperties)}static finalize(){if(this.hasOwnProperty(w("finalized")))return;if(this.finalized=!0,this._$Ei(),this.hasOwnProperty(w("properties"))){const e=this.properties,t=[...m(e),...v(e)];for(const i of t)this.createProperty(i,e[i])}const e=this[Symbol.metadata];if(null!==e){const t=litPropertyMetadata.get(e);if(void 0!==t)for(const[e,i]of t)this.elementProperties.set(e,i)}this._$Eh=new Map;for(const[e,t]of this.elementProperties){const i=this._$Eu(e,t);void 0!==i&&this._$Eh.set(i,e)}this.elementStyles=this.finalizeStyles(this.styles)}static finalizeStyles(e){const t=[];if(Array.isArray(e)){const i=new Set(e.flat(1/0).reverse());for(const e of i)t.unshift(d(e))}else void 0!==e&&t.push(d(e));return t}static _$Eu(e,t){const i=t.attribute;return!1===i?void 0:"string"==typeof i?i:"string"==typeof e?e.toLowerCase():void 0}constructor(){super(),this._$Ep=void 0,this.isUpdatePending=!1,this.hasUpdated=!1,this._$Em=null,this._$Ev()}_$Ev(){this._$ES=new Promise(e=>this.enableUpdating=e),this._$AL=new Map,this._$E_(),this.requestUpdate(),this.constructor.l?.forEach(e=>e(this))}addController(e){(this._$EO??=new Set).add(e),void 0!==this.renderRoot&&this.isConnected&&e.hostConnected?.()}removeController(e){this._$EO?.delete(e)}_$E_(){const e=new Map,t=this.constructor.elementProperties;for(const i of t.keys())this.hasOwnProperty(i)&&(e.set(i,this[i]),delete this[i]);e.size>0&&(this._$Ep=e)}createRenderRoot(){const e=this.shadowRoot??this.attachShadow(this.constructor.shadowRootOptions);return((e,t)=>{if(o)e.adoptedStyleSheets=t.map(e=>e instanceof CSSStyleSheet?e:e.styleSheet);else for(const i of t){const t=document.createElement("style"),o=s.litNonce;void 0!==o&&t.setAttribute("nonce",o),t.textContent=i.cssText,e.appendChild(t)}})(e,this.constructor.elementStyles),e}connectedCallback(){this.renderRoot??=this.createRenderRoot(),this.enableUpdating(!0),this._$EO?.forEach(e=>e.hostConnected?.())}enableUpdating(e){}disconnectedCallback(){this._$EO?.forEach(e=>e.hostDisconnected?.())}attributeChangedCallback(e,t,i){this._$AK(e,i)}_$ET(e,t){const i=this.constructor.elementProperties.get(e),s=this.constructor._$Eu(e,i);if(void 0!==s&&!0===i.reflect){const o=(void 0!==i.converter?.toAttribute?i.converter:_).toAttribute(t,i.type);this._$Em=e,null==o?this.removeAttribute(s):this.setAttribute(s,o),this._$Em=null}}_$AK(e,t){const i=this.constructor,s=i._$Eh.get(e);if(void 0!==s&&this._$Em!==s){const e=i.getPropertyOptions(s),o="function"==typeof e.converter?{fromAttribute:e.converter}:void 0!==e.converter?.fromAttribute?e.converter:_;this._$Em=s;const r=o.fromAttribute(t,e.type);this[s]=r??this._$Ej?.get(s)??r,this._$Em=null}}requestUpdate(e,t,i,s=!1,o){if(void 0!==e){const r=this.constructor;if(!1===s&&(o=this[e]),i??=r.getPropertyOptions(e),!((i.hasChanged??k)(o,t)||i.useDefault&&i.reflect&&o===this._$Ej?.get(e)&&!this.hasAttribute(r._$Eu(e,i))))return;this.C(e,t,i)}!1===this.isUpdatePending&&(this._$ES=this._$EP())}C(e,t,{useDefault:i,reflect:s,wrapped:o},r){i&&!(this._$Ej??=new Map).has(e)&&(this._$Ej.set(e,r??t??this[e]),!0!==o||void 0!==r)||(this._$AL.has(e)||(this.hasUpdated||i||(t=void 0),this._$AL.set(e,t)),!0===s&&this._$Em!==e&&(this._$Eq??=new Set).add(e))}async _$EP(){this.isUpdatePending=!0;try{await this._$ES}catch(e){Promise.reject(e)}const e=this.scheduleUpdate();return null!=e&&await e,!this.isUpdatePending}scheduleUpdate(){return this.performUpdate()}performUpdate(){if(!this.isUpdatePending)return;if(!this.hasUpdated){if(this.renderRoot??=this.createRenderRoot(),this._$Ep){for(const[e,t]of this._$Ep)this[e]=t;this._$Ep=void 0}const e=this.constructor.elementProperties;if(e.size>0)for(const[t,i]of e){const{wrapped:e}=i,s=this[t];!0!==e||this._$AL.has(t)||void 0===s||this.C(t,void 0,i,s)}}let e=!1;const t=this._$AL;try{e=this.shouldUpdate(t),e?(this.willUpdate(t),this._$EO?.forEach(e=>e.hostUpdate?.()),this.update(t)):this._$EM()}catch(t){throw e=!1,this._$EM(),t}e&&this._$AE(t)}willUpdate(e){}_$AE(e){this._$EO?.forEach(e=>e.hostUpdated?.()),this.hasUpdated||(this.hasUpdated=!0,this.firstUpdated(e)),this.updated(e)}_$EM(){this._$AL=new Map,this.isUpdatePending=!1}get updateComplete(){return this.getUpdateComplete()}getUpdateComplete(){return this._$ES}shouldUpdate(e){return!0}update(e){this._$Eq&&=this._$Eq.forEach(e=>this._$ET(e,this[e])),this._$EM()}updated(e){}firstUpdated(e){}};C.elementStyles=[],C.shadowRootOptions={mode:"open"},C[w("elementProperties")]=new Map,C[w("finalized")]=new Map,x?.({ReactiveElement:C}),(y.reactiveElementVersions??=[]).push("2.1.2");
/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */
const E={attribute:!0,type:String,converter:_,reflect:!1,hasChanged:k},S=(e=E,t,i)=>{const{kind:s,metadata:o}=i;let r=globalThis.litPropertyMetadata.get(o);if(void 0===r&&globalThis.litPropertyMetadata.set(o,r=new Map),"setter"===s&&((e=Object.create(e)).wrapped=!0),r.set(i.name,e),"accessor"===s){const{name:s}=i;return{set(i){const o=t.get.call(this);t.set.call(this,i),this.requestUpdate(s,o,e,!0,i)},init(t){return void 0!==t&&this.C(s,void 0,e,t),t}}}if("setter"===s){const{name:s}=i;return function(i){const o=this[s];t.call(this,i),this.requestUpdate(s,o,e,!0,i)}}throw Error("Unsupported decorator location: "+s)};function z(e){return(t,i)=>"object"==typeof i?S(e,t,i):((e,t,i)=>{const s=t.hasOwnProperty(i);return t.constructor.createProperty(i,e),s?Object.getOwnPropertyDescriptor(t,i):void 0})(e,t,i)}
/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */function A(e){return z({...e,state:!0,attribute:!1})}
/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */
/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */
function L(e,t){return(t,i,s)=>{const o=t=>t.renderRoot?.querySelector(e)??null;{const{get:e,set:r}="object"==typeof i?t:s??(()=>{const e=Symbol();return{get(){return this[e]},set(t){this[e]=t}}})();return((e,t,i)=>(i.configurable=!0,i.enumerable=!0,Reflect.decorate&&"object"!=typeof t&&Object.defineProperty(e,t,i),i))(t,i,{get(){let t=e.call(this);return void 0===t&&(t=o(this),(null!==t||this.hasUpdated)&&r.call(this,t)),t}})}}}
/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */const P=globalThis,T=e=>e,I=P.trustedTypes,O=I?I.createPolicy("lit-html",{createHTML:e=>e}):void 0,D="$lit$",M=`lit$${Math.random().toFixed(9).slice(2)}$`,q="?"+M,B=`<${q}>`,R=document,N=()=>R.createComment(""),U=e=>null===e||"object"!=typeof e&&"function"!=typeof e,j=Array.isArray,V="[ \t\n\f\r]",H=/<(?:(!--|\/[^a-zA-Z])|(\/?[a-zA-Z][^>\s]*)|(\/?$))/g,F=/-->/g,X=/>/g,W=RegExp(`>|${V}(?:([^\\s"'>=/]+)(${V}*=${V}*(?:[^ \t\n\f\r"'\`<>=]|("|')|))|$)`,"g"),G=/'/g,Y=/"/g,K=/^(?:script|style|textarea|title)$/i,J=(e=>(t,...i)=>({_$litType$:e,strings:t,values:i}))(1),Z=Symbol.for("lit-noChange"),Q=Symbol.for("lit-nothing"),ee=new WeakMap,te=R.createTreeWalker(R,129);function ie(e,t){if(!j(e)||!e.hasOwnProperty("raw"))throw Error("invalid template strings array");return void 0!==O?O.createHTML(t):t}const se=(e,t)=>{const i=e.length-1,s=[];let o,r=2===t?"<svg>":3===t?"<math>":"",n=H;for(let t=0;t<i;t++){const i=e[t];let a,l,c=-1,d=0;for(;d<i.length&&(n.lastIndex=d,l=n.exec(i),null!==l);)d=n.lastIndex,n===H?"!--"===l[1]?n=F:void 0!==l[1]?n=X:void 0!==l[2]?(K.test(l[2])&&(o=RegExp("</"+l[2],"g")),n=W):void 0!==l[3]&&(n=W):n===W?">"===l[0]?(n=o??H,c=-1):void 0===l[1]?c=-2:(c=n.lastIndex-l[2].length,a=l[1],n=void 0===l[3]?W:'"'===l[3]?Y:G):n===Y||n===G?n=W:n===F||n===X?n=H:(n=W,o=void 0);const h=n===W&&e[t+1].startsWith("/>")?" ":"";r+=n===H?i+B:c>=0?(s.push(a),i.slice(0,c)+D+i.slice(c)+M+h):i+M+(-2===c?t:h)}return[ie(e,r+(e[i]||"<?>")+(2===t?"</svg>":3===t?"</math>":"")),s]};class oe{constructor({strings:e,_$litType$:t},i){let s;this.parts=[];let o=0,r=0;const n=e.length-1,a=this.parts,[l,c]=se(e,t);if(this.el=oe.createElement(l,i),te.currentNode=this.el.content,2===t||3===t){const e=this.el.content.firstChild;e.replaceWith(...e.childNodes)}for(;null!==(s=te.nextNode())&&a.length<n;){if(1===s.nodeType){if(s.hasAttributes())for(const e of s.getAttributeNames())if(e.endsWith(D)){const t=c[r++],i=s.getAttribute(e).split(M),n=/([.?@])?(.*)/.exec(t);a.push({type:1,index:o,name:n[2],strings:i,ctor:"."===n[1]?ce:"?"===n[1]?de:"@"===n[1]?he:le}),s.removeAttribute(e)}else e.startsWith(M)&&(a.push({type:6,index:o}),s.removeAttribute(e));if(K.test(s.tagName)){const e=s.textContent.split(M),t=e.length-1;if(t>0){s.textContent=I?I.emptyScript:"";for(let i=0;i<t;i++)s.append(e[i],N()),te.nextNode(),a.push({type:2,index:++o});s.append(e[t],N())}}}else if(8===s.nodeType)if(s.data===q)a.push({type:2,index:o});else{let e=-1;for(;-1!==(e=s.data.indexOf(M,e+1));)a.push({type:7,index:o}),e+=M.length-1}o++}}static createElement(e,t){const i=R.createElement("template");return i.innerHTML=e,i}}function re(e,t,i=e,s){if(t===Z)return t;let o=void 0!==s?i._$Co?.[s]:i._$Cl;const r=U(t)?void 0:t._$litDirective$;return o?.constructor!==r&&(o?._$AO?.(!1),void 0===r?o=void 0:(o=new r(e),o._$AT(e,i,s)),void 0!==s?(i._$Co??=[])[s]=o:i._$Cl=o),void 0!==o&&(t=re(e,o._$AS(e,t.values),o,s)),t}class ne{constructor(e,t){this._$AV=[],this._$AN=void 0,this._$AD=e,this._$AM=t}get parentNode(){return this._$AM.parentNode}get _$AU(){return this._$AM._$AU}u(e){const{el:{content:t},parts:i}=this._$AD,s=(e?.creationScope??R).importNode(t,!0);te.currentNode=s;let o=te.nextNode(),r=0,n=0,a=i[0];for(;void 0!==a;){if(r===a.index){let t;2===a.type?t=new ae(o,o.nextSibling,this,e):1===a.type?t=new a.ctor(o,a.name,a.strings,this,e):6===a.type&&(t=new pe(o,this,e)),this._$AV.push(t),a=i[++n]}r!==a?.index&&(o=te.nextNode(),r++)}return te.currentNode=R,s}p(e){let t=0;for(const i of this._$AV)void 0!==i&&(void 0!==i.strings?(i._$AI(e,i,t),t+=i.strings.length-2):i._$AI(e[t])),t++}}class ae{get _$AU(){return this._$AM?._$AU??this._$Cv}constructor(e,t,i,s){this.type=2,this._$AH=Q,this._$AN=void 0,this._$AA=e,this._$AB=t,this._$AM=i,this.options=s,this._$Cv=s?.isConnected??!0}get parentNode(){let e=this._$AA.parentNode;const t=this._$AM;return void 0!==t&&11===e?.nodeType&&(e=t.parentNode),e}get startNode(){return this._$AA}get endNode(){return this._$AB}_$AI(e,t=this){e=re(this,e,t),U(e)?e===Q||null==e||""===e?(this._$AH!==Q&&this._$AR(),this._$AH=Q):e!==this._$AH&&e!==Z&&this._(e):void 0!==e._$litType$?this.$(e):void 0!==e.nodeType?this.T(e):(e=>j(e)||"function"==typeof e?.[Symbol.iterator])(e)?this.k(e):this._(e)}O(e){return this._$AA.parentNode.insertBefore(e,this._$AB)}T(e){this._$AH!==e&&(this._$AR(),this._$AH=this.O(e))}_(e){this._$AH!==Q&&U(this._$AH)?this._$AA.nextSibling.data=e:this.T(R.createTextNode(e)),this._$AH=e}$(e){const{values:t,_$litType$:i}=e,s="number"==typeof i?this._$AC(e):(void 0===i.el&&(i.el=oe.createElement(ie(i.h,i.h[0]),this.options)),i);if(this._$AH?._$AD===s)this._$AH.p(t);else{const e=new ne(s,this),i=e.u(this.options);e.p(t),this.T(i),this._$AH=e}}_$AC(e){let t=ee.get(e.strings);return void 0===t&&ee.set(e.strings,t=new oe(e)),t}k(e){j(this._$AH)||(this._$AH=[],this._$AR());const t=this._$AH;let i,s=0;for(const o of e)s===t.length?t.push(i=new ae(this.O(N()),this.O(N()),this,this.options)):i=t[s],i._$AI(o),s++;s<t.length&&(this._$AR(i&&i._$AB.nextSibling,s),t.length=s)}_$AR(e=this._$AA.nextSibling,t){for(this._$AP?.(!1,!0,t);e!==this._$AB;){const t=T(e).nextSibling;T(e).remove(),e=t}}setConnected(e){void 0===this._$AM&&(this._$Cv=e,this._$AP?.(e))}}class le{get tagName(){return this.element.tagName}get _$AU(){return this._$AM._$AU}constructor(e,t,i,s,o){this.type=1,this._$AH=Q,this._$AN=void 0,this.element=e,this.name=t,this._$AM=s,this.options=o,i.length>2||""!==i[0]||""!==i[1]?(this._$AH=Array(i.length-1).fill(new String),this.strings=i):this._$AH=Q}_$AI(e,t=this,i,s){const o=this.strings;let r=!1;if(void 0===o)e=re(this,e,t,0),r=!U(e)||e!==this._$AH&&e!==Z,r&&(this._$AH=e);else{const s=e;let n,a;for(e=o[0],n=0;n<o.length-1;n++)a=re(this,s[i+n],t,n),a===Z&&(a=this._$AH[n]),r||=!U(a)||a!==this._$AH[n],a===Q?e=Q:e!==Q&&(e+=(a??"")+o[n+1]),this._$AH[n]=a}r&&!s&&this.j(e)}j(e){e===Q?this.element.removeAttribute(this.name):this.element.setAttribute(this.name,e??"")}}class ce extends le{constructor(){super(...arguments),this.type=3}j(e){this.element[this.name]=e===Q?void 0:e}}class de extends le{constructor(){super(...arguments),this.type=4}j(e){this.element.toggleAttribute(this.name,!!e&&e!==Q)}}class he extends le{constructor(e,t,i,s,o){super(e,t,i,s,o),this.type=5}_$AI(e,t=this){if((e=re(this,e,t,0)??Q)===Z)return;const i=this._$AH,s=e===Q&&i!==Q||e.capture!==i.capture||e.once!==i.once||e.passive!==i.passive,o=e!==Q&&(i===Q||s);s&&this.element.removeEventListener(this.name,this,i),o&&this.element.addEventListener(this.name,this,e),this._$AH=e}handleEvent(e){"function"==typeof this._$AH?this._$AH.call(this.options?.host??this.element,e):this._$AH.handleEvent(e)}}class pe{constructor(e,t,i){this.element=e,this.type=6,this._$AN=void 0,this._$AM=t,this.options=i}get _$AU(){return this._$AM._$AU}_$AI(e){re(this,e)}}const ue=P.litHtmlPolyfillSupport;ue?.(oe,ae),(P.litHtmlVersions??=[]).push("3.3.3");const me=globalThis;
/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */let ve=class extends C{constructor(){super(...arguments),this.renderOptions={host:this},this._$Do=void 0}createRenderRoot(){const e=super.createRenderRoot();return this.renderOptions.renderBefore??=e.firstChild,e}update(e){const t=this.render();this.hasUpdated||(this.renderOptions.isConnected=this.isConnected),super.update(e),this._$Do=((e,t,i)=>{const s=i?.renderBefore??t;let o=s._$litPart$;if(void 0===o){const e=i?.renderBefore??null;s._$litPart$=o=new ae(t.insertBefore(N(),e),e,void 0,i??{})}return o._$AI(e),o})(t,this.renderRoot,this.renderOptions)}connectedCallback(){super.connectedCallback(),this._$Do?.setConnected(!0)}disconnectedCallback(){super.disconnectedCallback(),this._$Do?.setConnected(!1)}render(){return Z}};ve._$litElement$=!0,ve.finalized=!0,me.litElementHydrateSupport?.({LitElement:ve});const be=me.litElementPolyfillSupport;be?.({LitElement:ve}),(me.litElementVersions??=[]).push("4.2.2");
/**
 * @license
 * Copyright 2023 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
class ye extends ve{render(){return J`
      <md-elevation part="elevation"></md-elevation>
      <div class="background"></div>
      <slot></slot>
      <div class="outline"></div>
    `}}
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */const ge=c`:host{--_container-color: var(--md-elevated-card-container-color, var(--md-sys-color-surface-container-low, #f7f2fa));--_container-elevation: var(--md-elevated-card-container-elevation, 1);--_container-shadow-color: var(--md-elevated-card-container-shadow-color, var(--md-sys-color-shadow, #000));--_container-shape: var(--md-elevated-card-container-shape, var(--md-sys-shape-corner-medium, 12px))}
`;ge.styleSheet;
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
const fe=c`:host{border-radius:var(--_container-shape);box-sizing:border-box;display:flex;flex-direction:column;position:relative;z-index:0}md-elevation,.background,.outline{border-radius:inherit;inset:0;pointer-events:none;position:absolute}.background{background:var(--_container-color);z-index:-1}.outline{border:1px solid rgba(0,0,0,0);z-index:1}md-elevation{z-index:-1;--md-elevation-level: var(--_container-elevation);--md-elevation-shadow-color: var(--_container-shadow-color)}slot{border-radius:inherit}
`;fe.styleSheet;
/**
 * @license
 * Copyright 2023 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
let xe=class extends ye{};xe.styles=[fe,ge],xe=t([i("md-elevated-card")],xe);
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
const we=c`:host{--_container-color: var(--md-filled-card-container-color, var(--md-sys-color-surface-container-highest, #e6e0e9));--_container-elevation: var(--md-filled-card-container-elevation, 0);--_container-shadow-color: var(--md-filled-card-container-shadow-color, var(--md-sys-color-shadow, #000));--_container-shape: var(--md-filled-card-container-shape, var(--md-sys-shape-corner-medium, 12px))}
`;we.styleSheet;
/**
 * @license
 * Copyright 2023 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
let _e=class extends ye{};_e.styles=[fe,we],_e=t([i("md-filled-card")],_e);
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
const ke=c`:host{--_container-color: var(--md-outlined-card-container-color, var(--md-sys-color-surface, #fef7ff));--_container-elevation: var(--md-outlined-card-container-elevation, 0);--_container-shadow-color: var(--md-outlined-card-container-shadow-color, var(--md-sys-color-shadow, #000));--_container-shape: var(--md-outlined-card-container-shape, var(--md-sys-shape-corner-medium, 12px));--_outline-color: var(--md-outlined-card-outline-color, var(--md-sys-color-outline-variant, #cac4d0));--_outline-width: var(--md-outlined-card-outline-width, 1px)}.outline{border-color:var(--_outline-color);border-width:var(--_outline-width)}
`;ke.styleSheet;
/**
 * @license
 * Copyright 2023 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
let $e=class extends ye{};$e.styles=[fe,ke],$e=t([i("md-outlined-card")],$e);
/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */
const Ce=1,Ee=2,Se=e=>(...t)=>({_$litDirective$:e,values:t});let ze=class{constructor(e){}get _$AU(){return this._$AM._$AU}_$AT(e,t,i){this._$Ct=e,this._$AM=t,this._$Ci=i}_$AS(e,t){return this.update(e,t)}update(e,t){return this.render(...t)}};
/**
 * @license
 * Copyright 2018 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */const Ae=Se(class extends ze{constructor(e){if(super(e),e.type!==Ce||"class"!==e.name||e.strings?.length>2)throw Error("`classMap()` can only be used in the `class` attribute and must be the only part in the attribute.")}render(e){return" "+Object.keys(e).filter(t=>e[t]).join(" ")+" "}update(e,[t]){if(void 0===this.st){this.st=new Set,void 0!==e.strings&&(this.nt=new Set(e.strings.join(" ").split(/\s/).filter(e=>""!==e)));for(const e in t)t[e]&&!this.nt?.has(e)&&this.st.add(e);return this.render(t)}const i=e.element.classList;for(const e of this.st)e in t||(i.remove(e),this.st.delete(e));for(const e in t){const s=!!t[e];s===this.st.has(e)||this.nt?.has(e)||(s?(i.add(e),this.st.add(e)):(i.remove(e),this.st.delete(e)))}return Z}});
/**
 * @license
 * Copyright 2022 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */class Le extends ve{constructor(){super(...arguments),this.value=""}render(){const e={"md3-badge--large":this.value};return J`<div class="md3-badge ${Ae(e)}">
      <p class="md3-badge__value">${this.value}</p>
    </div>`}}t([z()],Le.prototype,"value",void 0);
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
const Pe=c`:host{--_color: var(--md-badge-color, var(--md-sys-color-error, #b3261e));--_large-color: var(--md-badge-large-color, var(--md-sys-color-error, #b3261e));--_large-label-text-color: var(--md-badge-large-label-text-color, var(--md-sys-color-on-error, #fff));--_large-label-text-font: var(--md-badge-large-label-text-font, var(--md-sys-typescale-label-small-font, var(--md-ref-typeface-plain, Roboto)));--_large-label-text-line-height: var(--md-badge-large-label-text-line-height, var(--md-sys-typescale-label-small-line-height, 1rem));--_large-label-text-size: var(--md-badge-large-label-text-size, var(--md-sys-typescale-label-small-size, 0.6875rem));--_large-label-text-weight: var(--md-badge-large-label-text-weight, var(--md-sys-typescale-label-small-weight, var(--md-ref-typeface-weight-medium, 500)));--_large-shape: var(--md-badge-large-shape, var(--md-sys-shape-corner-full, 9999px));--_large-size: var(--md-badge-large-size, 16px);--_shape: var(--md-badge-shape, var(--md-sys-shape-corner-full, 9999px));--_size: var(--md-badge-size, 6px)}.md3-badge{inset-inline-start:50%;margin-inline-start:6px;margin-block-start:4px;position:absolute;inset-block-start:0px;background-color:var(--_color);border-radius:var(--_shape);height:var(--_size)}.md3-badge:not(.md3-badge--large){width:var(--_size)}.md3-badge.md3-badge--large{display:flex;flex-direction:column;justify-content:center;margin-inline-start:2px;margin-block-start:1px;background-color:var(--_large-color);border-radius:var(--_large-shape);height:var(--_large-size);min-width:var(--_large-size);color:var(--_large-label-text-color)}.md3-badge.md3-badge--large .md3-badge__value{padding:0px 4px;text-align:center}.md3-badge__value{font-family:var(--_large-label-text-font);font-size:var(--_large-label-text-size);line-height:var(--_large-label-text-line-height);font-weight:var(--_large-label-text-weight)}
`;Pe.styleSheet;
/**
 * @license
 * Copyright 2022 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
let Te=class extends Le{};Te.styles=[Pe],Te=t([i("md-badge")],Te);
/**
 * @license
 * Copyright 2020 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */
const Ie=(e,t)=>{const i=e._$AN;if(void 0===i)return!1;for(const e of i)e._$AO?.(t,!1),Ie(e,t);return!0},Oe=e=>{let t,i;do{if(void 0===(t=e._$AM))break;i=t._$AN,i.delete(e),e=t}while(0===i?.size)},De=e=>{for(let t;t=e._$AM;e=t){let i=t._$AN;if(void 0===i)t._$AN=i=new Set;else if(i.has(e))break;i.add(e),Be(t)}};
/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */function Me(e){void 0!==this._$AN?(Oe(this),this._$AM=e,De(this)):this._$AM=e}function qe(e,t=!1,i=0){const s=this._$AH,o=this._$AN;if(void 0!==o&&0!==o.size)if(t)if(Array.isArray(s))for(let e=i;e<s.length;e++)Ie(s[e],!1),Oe(s[e]);else null!=s&&(Ie(s,!1),Oe(s));else Ie(this,e)}const Be=e=>{e.type==Ee&&(e._$AP??=qe,e._$AQ??=Me)};class Re extends ze{constructor(){super(...arguments),this._$AN=void 0}_$AT(e,t,i){super._$AT(e,t,i),De(this),this.isConnected=e._$AU}_$AO(e,t=!0){e!==this.isConnected&&(this.isConnected=e,e?this.reconnected?.():this.disconnected?.()),t&&(Ie(this,e),Oe(this))}setValue(e){if((e=>void 0===e.strings)(this._$Ct))this._$Ct._$AI(e,this);else{const t=[...this._$Ct._$AH];t[this._$Ci]=e,this._$Ct._$AI(t,this,0)}}disconnected(){}reconnected(){}}
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */function Ne(e){return Se(class extends je{constructor(){super(...arguments),this.setupElement=e.setupElement}render(t){return Ae({...t?.classes||{},...e.getClasses(t)})}})}function Ue(e){return Se(class extends je{constructor(){super(...arguments),this.setupElement=e}render(){return Z}})}class je extends Re{update({element:e},t){return e!==this.element&&(this.element=e,this.disconnected(),this.isConnected&&this.reconnected()),this.render(...t)}disconnected(){this.cleanup?.abort()}reconnected(){this.element&&this.setupElement&&(this.cleanup=new AbortController,this.setupElement(this.element,{signal:this.cleanup.signal}))}}
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */const Ve="badge",He="badge-large";const Fe=Ne({getClasses:function({large:e=!1}={}){return{[Ve]:!0,[He]:e}}}),Xe=c`/*!
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */@layer md.sys;@layer md.comp.badge{.badge{--container-color: var(--md-sys-color-error);--shape: var(--md-sys-shape-corner-full);--size: 6px;--label-text-color: var(--md-sys-color-on-error);--label-text: var(--md-sys-typescale-label-sm);--label-text-tracking: var(--md-sys-typescale-label-sm-tracking);--label-text-axes: var(--md-sys-typescale-label-sm-axes)}.badge{display:inline-flex;align-items:center;justify-content:center;background-color:var(--container-color);border-radius:var(--shape);height:var(--size);width:var(--size);box-sizing:border-box}.badge slot{display:none}.badge.badge-large{--size: 16px;width:auto;min-width:var(--size);padding:0 4px;color:var(--label-text-color);font:var(--label-text);letter-spacing:var(--label-text-tracking);font-variation-settings:var(--label-text-axes)}.badge.badge-large slot{display:contents}}
`;
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */Xe.styleSheet;
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
class We extends ve{constructor(){super(...arguments),this.hasContent=!1}render(){return J`<span part="badge" class="${Fe({large:this.hasContent})}">
      <slot @slotchange=${this.handleSlotChange}></slot>
    </span>`}handleSlotChange(e){const t=e.target.assignedNodes();this.hasContent=t.some(e=>e.nodeType!==Node.TEXT_NODE||(e.textContent??"").trim().length>0)}}We.styles=[Xe,c`
      :host {
        display: inline-flex;
      }
      .badge {
        flex: 1;
      }
    `],t([A()],We.prototype,"hasContent",void 0),
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
customElements.define("md-gb-badge",We);
/**
 * @license
 * Copyright 2023 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
const Ge=["role","ariaAtomic","ariaAutoComplete","ariaBusy","ariaChecked","ariaColCount","ariaColIndex","ariaColSpan","ariaCurrent","ariaDisabled","ariaExpanded","ariaHasPopup","ariaHidden","ariaInvalid","ariaKeyShortcuts","ariaLabel","ariaLevel","ariaLive","ariaModal","ariaMultiLine","ariaMultiSelectable","ariaOrientation","ariaPlaceholder","ariaPosInSet","ariaPressed","ariaReadOnly","ariaRequired","ariaRoleDescription","ariaRowCount","ariaRowIndex","ariaRowSpan","ariaSelected","ariaSetSize","ariaSort","ariaValueMax","ariaValueMin","ariaValueNow","ariaValueText"],Ye=Ge.map(Je);function Ke(e){return Ye.includes(e)}function Je(e){return e.replace("aria","aria-").replace(/Elements?/g,"").toLowerCase()}
/**
 * @license
 * Copyright 2023 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */const Ze=Symbol("privateIgnoreAttributeChangesFor");function Qe(e){var t;class i extends e{constructor(){super(...arguments),this[t]=new Set}attributeChangedCallback(e,t,i){if(!Ke(e))return void super.attributeChangedCallback(e,t,i);if(this[Ze].has(e))return;this[Ze].add(e),this.removeAttribute(e),this[Ze].delete(e);const s=tt(e);null===i?delete this.dataset[s]:this.dataset[s]=i,this.requestUpdate(tt(e),t)}getAttribute(e){return Ke(e)?super.getAttribute(et(e)):super.getAttribute(e)}removeAttribute(e){super.removeAttribute(e),Ke(e)&&(super.removeAttribute(et(e)),this.requestUpdate())}}return t=Ze,function(e){for(const t of Ge){const i=Je(t),s=et(i),o=tt(i);e.createProperty(t,{attribute:i,noAccessor:!0}),e.createProperty(Symbol(s),{attribute:s,noAccessor:!0}),Object.defineProperty(e.prototype,t,{configurable:!0,enumerable:!0,get(){return this.dataset[o]??null},set(e){const i=this.dataset[o]??null;e!==i&&(null===e?delete this.dataset[o]:this.dataset[o]=e,this.requestUpdate(t,i))}})}}(i),i}function et(e){return`data-${e}`}function tt(e){return e.replace(/-\w/,e=>e[1].toUpperCase())}
/**
 * @license
 * Copyright 2021 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */function it(e,t){!t.bubbles||e.shadowRoot&&!t.composed||t.stopPropagation();const i=Reflect.construct(t.constructor,[t.type,t]),s=e.dispatchEvent(i);return s||t.preventDefault(),s}
/**
 * @license
 * Copyright 2023 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */const st=Symbol("internals"),ot=Symbol("privateInternals");function rt(e){return class extends e{get[st](){return this[ot]||(this[ot]=this.attachInternals()),this[ot]}}}
/**
 * @license
 * Copyright 2023 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */const nt=Symbol("getFormValue"),at=Symbol("getFormState");function lt(e){class i extends e{get form(){return this[st].form}get labels(){return this[st].labels}get name(){return this.getAttribute("name")??""}set name(e){this.setAttribute("name",e)}get disabled(){return this.hasAttribute("disabled")}set disabled(e){this.toggleAttribute("disabled",Boolean(e))}attributeChangedCallback(e,t,i){if("name"===e||"disabled"===e){const i="disabled"===e?null!==t:t;return void this.requestUpdate(e,i)}super.attributeChangedCallback(e,t,i)}requestUpdate(e,t,i){super.requestUpdate(e,t,i),this[st].setFormValue(this[nt](),this[at]())}[nt](){return this.getAttribute("value")}[at](){return this[nt]()}formDisabledCallback(e){this.disabled=e}}return i.formAssociated=!0,t([z({noAccessor:!0})],i.prototype,"name",null),t([z({type:Boolean,noAccessor:!0})],i.prototype,"disabled",null),i}
/**
 * @license
 * Copyright 2023 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */const ct=Symbol("dispatchHooks");function dt(e,t){const i=e[ct];if(!i)throw new Error(`'${e.type}' event needs setupDispatchHooks().`);i.addEventListener("after",t,{once:!0})}const ht=new WeakMap;function pt(e,...t){let i=ht.get(e);i||(i=new Set,ht.set(e,i));for(const s of t)i.has(s)||(e.addEventListener(s,e=>{const t=new EventTarget;e[ct]=t;const i=new AbortController,o=()=>{i.abort(),t.dispatchEvent(new Event("after"))},r=e=>function(){e.call(this),o()};e.stopPropagation=r(e.stopPropagation),e.stopImmediatePropagation=r(e.stopImmediatePropagation);const n=e.composedPath();let a;a=e.composed&&e.bubbles?n[n.length-1]:e.bubbles?n[0].getRootNode():n[0],a.addEventListener(s,()=>{o()},{once:!0,signal:i.signal})},{capture:!0}),i.add(s))}
/**
 * @license
 * Copyright 2023 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */function ut(e){class i extends e{get name(){return this.getAttribute("name")??""}set name(e){this.setAttribute("name",e)}constructor(...e){super(...e),this.type="submit",this.value="",pt(this,"click"),this.addEventListener("click",async e=>{const t="reset"===this.type,i="submit"===this.type,s=this[st],{form:o}=s;o&&(i||t)&&dt(e,()=>{e.defaultPrevented||(t?o.reset():(o.addEventListener("submit",e=>{Object.defineProperty(e,"submitter",{configurable:!0,enumerable:!0,get:()=>this})},{capture:!0,once:!0}),s.setFormValue(this.value),o.requestSubmit()))})})}}return t([z()],i.prototype,"type",void 0),t([z({reflect:!0})],i.prototype,"value",void 0),i}
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */var mt=c`/*!
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */@layer md.sys;@layer md.comp.focus-ring{.focus-ring-outer,.focus-ring-inner{--focus-ring-outline: none;--focus-ring-offset: 0;--focus-ring-animation: none;outline:var(--focus-ring-outline);outline-offset:var(--focus-ring-offset);animation:var(--focus-ring-animation),var(--ripple-animation, none)}.focus-ring-outer:is(:focus-visible,.focus-visible),.focus-ring-outer:has(.focus-ring-target:focus-visible),.focus-ring-target:focus-visible .focus-ring-outer,:host(:focus-visible) .focus-ring-outer.focus-ring-host{--focus-ring-outline: 3px solid var(--md-sys-color-secondary);--focus-ring-offset: 2px;--focus-ring-animation: focus-ring-outer-grow 150ms cubic-bezier(0.2, 0, 0, 1), focus-ring-outer-shrink 450ms 150ms cubic-bezier(0.2, 0, 0, 1)}.focus-ring-inner:is(:focus-visible,.focus-visible),.focus-ring-inner:has(.focus-ring-target:focus-visible),.focus-ring-target:focus-visible .focus-ring-inner,:host(:focus-visible) .focus-ring-inner.focus-ring-host{--focus-ring-outline: 3px solid var(--md-sys-color-secondary);--focus-ring-offset: -3px;--focus-ring-animation: focus-ring-inner-grow 150ms cubic-bezier(0.2, 0, 0, 1), focus-ring-inner-shrink 450ms 150ms cubic-bezier(0.2, 0, 0, 1)}@keyframes focus-ring-outer-grow{from{outline-width:0}to{outline-width:8px}}@keyframes focus-ring-outer-shrink{from{outline-width:8px}}@keyframes focus-ring-inner-grow{from{outline-width:0;outline-offset:0}to{outline-width:8px;outline-offset:-8px}}@keyframes focus-ring-inner-shrink{from{outline-width:8px;outline-offset:-8px}}}
`.styleSheet;
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */var vt=c`/*!
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */@layer md.sys;@layer md.comp.ripple{@property --ripple-scale{syntax:"<percentage>";inherits:false;initial-value:0%}@property --ripple-hover-opacity{syntax:"<percentage>";inherits:false;initial-value:0%}@property --ripple-press-opacity{syntax:"<percentage>";inherits:false;initial-value:0%}@property --ripple-x{syntax:"<percentage>";inherits:false;initial-value:50%}@property --ripple-y{syntax:"<percentage>";inherits:false;initial-value:50%}.ripple{--ripple-color: currentColor;--ripple-press-transition: --ripple-press-opacity 375ms linear;--ripple-transition: --ripple-hover-opacity 15ms linear, var(--ripple-press-transition);--ripple-animation: ripple-release 375ms linear;--ripple: radial-gradient( hsl(from var(--ripple-color) h s l / var(--ripple-hover-opacity)) ), radial-gradient( circle at var(--ripple-x) var(--ripple-y), hsl(from var(--ripple-color) h s l / var(--ripple-press-opacity)) var(--ripple-scale), transparent calc(var(--ripple-scale) * 1.35) );background-image:var(--ripple);transition:var(--ripple-transition);animation:var(--ripple-animation),var(--focus-ring-animation, none)}.ripple:is(:hover,.hover),.ripple:has(.ripple-target:hover),.ripple-target:hover .ripple,:host(:hover) .ripple.ripple-host{--ripple-hover-opacity: 8%}.ripple:is(:active,.active),.ripple:has(.ripple-target:active),.ripple-target:active .ripple,:host(:active) .ripple.ripple-host{--ripple-press-opacity: 10%;--ripple-press-transition: --ripple-press-opacity 100ms linear;--ripple-animation: ripple-press forwards 450ms cubic-bezier(0.2, 0, 0, 1)}.ripple:is(:disabled,.disabled){--ripple: none}@keyframes ripple-press{from{--ripple-scale: 20%}to{--ripple-scale: 100%;--ripple-x: 50%;--ripple-y: 50%}}@keyframes ripple-release{from{--ripple-scale: 100%}to{--ripple-scale: 100%}}}
`.styleSheet;
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */const bt=Ue((e,t)=>{if(!e.matches("slot"))throw new Error("hasSlotted() must be used on a <slot> element.");e.addEventListener("slotchange",t=>{e.classList.toggle("has-slotted",e.assignedNodes().length>0)},t)});
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */var yt=c`/*!
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */@layer md.sys, md.comp.ripple, md.comp.focus-ring;@layer md.comp.button{.btn{--container-color: transparent;--container-height: auto;--container-elevation: var(--md-sys-elevation-shadow-0);--container-shape: calc(var(--container-height) / 2);--outline-width: 0;--outline-color: transparent;--icon-label-space: 8px;--icon-color: currentColor;--icon-size: 20px;--label-text: var(--md-sys-typescale-label-lg);--label-text-tracking: var(--md-sys-typescale-label-lg-tracking);--label-text-color: currentColor;--leading-space: 0;--state-layer-color: transparent;--trailing-space: 0}.btn:is(.btn-selected,[aria-pressed=true]){--container-shape: var(--md-sys-shape-corner-md)}.btn.btn-filled{--container-color: var(--md-sys-color-primary);--icon-color: var(--md-sys-color-on-primary);--label-text-color: var(--md-sys-color-on-primary)}.btn.btn-filled:where(.btn-unselected,[aria-pressed=false]){--container-color: var(--md-sys-color-surface-container);--icon-color: var(--md-sys-color-on-surface-variant);--label-text-color: var(--md-sys-color-on-surface-variant)}.btn.btn-filled:where(.btn-selected,[aria-pressed=true]){--container-color: var(--md-sys-color-primary);--icon-color: var(--md-sys-color-on-primary);--label-text-color: var(--md-sys-color-on-primary)}.btn.btn-elevated{--container-color: var(--md-sys-color-surface-container-low);--container-elevation: var(--md-sys-elevation-shadow-1);--icon-color: var(--md-sys-color-primary);--label-text-color: var(--md-sys-color-primary)}.btn.btn-elevated:where(.btn-selected,[aria-pressed=true]){--container-color: var(--md-sys-color-primary);--icon-color: var(--md-sys-color-on-primary);--label-text-color: var(--md-sys-color-on-primary)}.btn.btn-tonal{--container-color: var(--md-sys-color-secondary-container);--icon-color: var(--md-sys-color-on-secondary-container);--label-text-color: var(--md-sys-color-on-secondary-container)}.btn.btn-tonal:where(.btn-selected,[aria-pressed=true]){--container-color: var(--md-sys-color-secondary);--icon-color: var(--md-sys-color-on-secondary);--label-text-color: var(--md-sys-color-on-secondary)}.btn.btn-outlined{--icon-color: var(--md-sys-color-on-surface-variant);--label-text-color: var(--md-sys-color-on-surface-variant);--outline-color: var(--md-sys-color-outline-variant);--outline-width: 1px}.btn.btn-outlined:where(.btn-lg){--outline-width: 2px}.btn.btn-outlined:where(.btn-xl){--outline-width: 3px}.btn.btn-outlined:where(.btn-selected,[aria-pressed=true]){--container-color: var(--md-sys-color-inverse-surface);--icon-color: var(--md-sys-color-inverse-on-surface);--label-text-color: var(--md-sys-color-inverse-on-surface)}.btn.btn-text{--label-text-color: var(--md-sys-color-primary);--icon-color: var(--md-sys-color-primary);--state-layer-color: var(--md-sys-color-primary)}.btn.btn-xs{--container-height: 32px;--leading-space: 12px;--trailing-space: 12px}.btn.btn-sm{--container-height: 40px;--leading-space: 16px;--trailing-space: 16px}.btn.btn-md{--container-height: 56px;--leading-space: 24px;--trailing-space: 24px;--icon-size: 24px;--label-text: var(--md-sys-typescale-title-md);--label-text-tracking: var(--md-sys-typescale-title-md-tracking)}.btn.btn-md:where(.btn-selected,[aria-pressed=true]){--container-shape: var(--md-sys-shape-corner-lg)}.btn.btn-lg{--container-height: 96px;--icon-label-space: 12px;--icon-size: 32px;--leading-space: 48px;--trailing-space: 48px;--label-text: var(--md-sys-typescale-headline-sm);--label-text-tracking: var(--md-sys-typescale-headline-sm-tracking)}.btn.btn-lg:where(.btn-selected,[aria-pressed=true]){--container-shape: var(--md-sys-shape-corner-xl)}.btn.btn-xl{--container-height: 136px;--icon-label-space: 16px;--icon-size: 40px;--leading-space: 64px;--trailing-space: 64px;--label-text: var(--md-sys-typescale-headline-lg);--label-text-tracking: var(--md-sys-typescale-headline-lg-tracking)}.btn.btn-xl:where(.btn-selected,[aria-pressed=true]){--container-shape: var(--md-sys-shape-corner-xl)}.btn.btn-square{--container-shape: var(--md-sys-shape-corner-md)}.btn.btn-square:where(.btn-md){--container-shape: var(--md-sys-shape-corner-lg)}.btn.btn-square:where(.btn-lg){--container-shape: var(--md-sys-shape-corner-xl)}.btn.btn-square:where(.btn-xl){--container-shape: var(--md-sys-shape-corner-xl)}.btn.btn-square:where(.btn-selected,[aria-pressed=true]){--container-shape: calc(var(--container-height) / 2)}.btn:is(:active,.active):where(:not(:disabled,.disabled)){--container-shape: var(--md-sys-shape-corner-sm)}.btn:is(:active,.active):where(:not(:disabled,.disabled)):where(.btn-md){--container-shape: var(--md-sys-shape-corner-md)}.btn:is(:active,.active):where(:not(:disabled,.disabled)):where(.btn-lg){--container-shape: var(--md-sys-shape-corner-lg)}.btn:is(:active,.active):where(:not(:disabled,.disabled)):where(.btn-xl){--container-shape: var(--md-sys-shape-corner-lg)}.btn:is(:disabled,.disabled){--container-color: hsl(from var(--md-sys-color-on-surface) h s l / 10%);--container-elevation: var(--md-sys-elevation-shadow-0);--icon-color: hsl(from var(--md-sys-color-on-surface) h s l / 38%);--label-text-color: hsl(from var(--md-sys-color-on-surface) h s l / 38%)}.btn{display:inline-flex;align-items:center;justify-content:center;min-height:var(--container-height);gap:var(--icon-label-space);padding-inline:var(--leading-space) var(--trailing-space);border:var(--outline-width) solid var(--outline-color);border-radius:var(--container-shape);box-shadow:var(--container-elevation);background-color:var(--container-color);color:var(--label-text-color);font:var(--label-text);letter-spacing:var(--label-text-tracking);transition:border-radius 350ms cubic-bezier(0.42, 1.67, 0.21, 0.9),var(--ripple-transition);--md-icon-color: var(--icon-color);--md-icon-size: var(--icon-size)}.btn:not(:disabled,.disabled){cursor:pointer}}
`.styleSheet;
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */const gt="active",ft="checked",xt="disabled",wt="focus",_t="hover",kt="indeterminate",$t="invalid";function Ct(e){return e.matches(".disabled,:disabled,[disabled],[aria-disabled=true]")}
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */const Et="outer",St="inner",zt="focus-ring-outer",At="focus-ring-inner",Lt="focus-visible";function Pt({type:e=Et,focusVisible:t=!1}={}){return{[zt]:e===Et||!e,[At]:e===St,[Lt]:t}}
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */const Tt={ripple:"ripple",hover:_t,active:gt,disabled:xt};function It({hover:e=!1,active:t=!1,disabled:i=!1}={}){return{[Tt.ripple]:!0,[Tt.hover]:e,[Tt.active]:t,[Tt.disabled]:i}}let Ot=!1;function Dt(e,t){if(!Ot){const e=[["--ripple-scale","0%"],["--ripple-hover-opacity","0%"],["--ripple-press-opacity","0%"],["--ripple-x","50%"],["--ripple-y","50%"]];try{for(const[t,i]of e)CSS.registerProperty({name:t,syntax:"<percentage>",inherits:!1,initialValue:i})}catch{}finally{Ot=!0}}let i;const s=()=>{if(e.classList.contains(Tt.active)){const t=e.getAnimations().find(e=>"ripple-press"===e.animationName);t?.cancel(),t?.play()}e.classList.add(Tt.active),clearTimeout(i),i=setTimeout(()=>{e.classList.remove(Tt.active)},225)},o=()=>Ct(e)||!e.matches(`.${Tt.ripple}`);e.addEventListener("pointerdown",t=>{if(o())return;const i=e.getBoundingClientRect(),r=(t.clientX-i.x)/i.width,n=(t.clientY-i.y)/i.height;e.style.setProperty("--ripple-x",100*r+"%"),e.style.setProperty("--ripple-y",100*n+"%"),s()},t),e.addEventListener("keydown",t=>{e.style.removeProperty("--ripple-x"),e.style.removeProperty("--ripple-y")},t),e.addEventListener("click",e=>{0!==e.detail||o()||s()},t)}const Mt=Ue(Dt),qt="filled",Bt="elevated",Rt="tonal",Nt="outlined",Ut="text",jt="xs",Vt="sm",Ht="md",Ft="lg",Xt="xl",Wt="btn",Gt="btn-filled",Yt="btn-elevated",Kt="btn-tonal",Jt="btn-outlined",Zt="btn-text",Qt="btn-xs",ei="btn-sm",ti="btn-md",ii="btn-lg",si="btn-xl",oi="btn-square",ri="btn-unselected",ni="btn-selected",ai=gt,li=xt;
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */const ci=Ne({getClasses:function({color:e,size:t,square:i=!1,selected:s,active:o=!1,disabled:r=!1}={}){return{...It(),...Pt(),[Wt]:!0,[Gt]:e===qt,[Yt]:e===Bt,[Kt]:e===Rt,[Jt]:e===Nt,[Zt]:e===Ut||!e,[Qt]:t===jt,[ei]:t===Vt||!t,[ti]:t===Ht,[ii]:t===Ft,[si]:t===Xt,[oi]:i,[ri]:!1===s,[ni]:!0===s,[ai]:o,[li]:r}},setupElement:function(e,t){pt(e,"click"),Dt(e,t),e.addEventListener("click",t=>{if(Ct(e))return t.stopImmediatePropagation(),void t.preventDefault();dt(t,()=>{const i=e.hasAttribute("aria-pressed")||e.matches(`.${ni},.${ri}`);if(t.defaultPrevented||!i)return;const s="true"===e.ariaPressed;e.ariaPressed=String(!s),e.dispatchEvent(new InputEvent("input",{bubbles:!0,composed:!0})),e.dispatchEvent(new Event("change",{bubbles:!0}))})},t)}}),di=Qe(ut(lt(rt(ve))));
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */class hi extends di{constructor(){super(...arguments),this.hideOutline=!1,this.color="text",this.size="sm",this.square=!1,this.softDisabled=!1,this.selected=!1,this.href="",this.download="",this.target="",this.handleSetShowOutline=e=>{const t=e;this.hideOutline=!t.detail.shown}}get type(){return this.href?"link":super.type}set type(e){this.href&&"link"!==e||(super.type=e)}connectedCallback(){super.connectedCallback(),this.addEventListener("md-gb:set-show-outline",this.handleSetShowOutline)}disconnectedCallback(){super.disconnectedCallback(),this.removeEventListener("md-gb:set-show-outline",this.handleSetShowOutline)}updated(e){super.updated(e),(e.has("disabled")||e.has("softDisabled"))&&this.dispatchSetEnabledEvent()}dispatchSetEnabledEvent(){const e=!(this.disabled||this.softDisabled);if(this.lastFiredEnabledState===e)return;const t=this.shadowRoot?.querySelector('slot[name="container"]');if(t)for(const i of t.assignedElements({flatten:!0}))i.dispatchEvent(new CustomEvent("md-gb:set-enabled",{detail:{enabled:e}}));this.lastFiredEnabledState=e}render(){const e=ci({color:this.color,size:this.size,square:this.square,disabled:this.softDisabled,classes:{"btn-hide-outline":this.hideOutline}}),{ariaLabel:t,ariaHasPopup:i,ariaExpanded:s}=this;return"link"===this.type?J`<a
        part="btn"
        class=${e}
        href=${this.href}
        download=${this.download||Q}
        target=${this.target||Q}
        aria-label=${t||Q}
        aria-haspopup=${i||Q}
        aria-expanded=${s||Q}
        aria-disabled=${this.disabled||this.softDisabled||Q}
        tabindex=${this.disabled&&!this.softDisabled?-1:Q}>
        <slot></slot>
        <slot
          name="container"
          ${bt()}
          @slotchange=${this.handleContainerSlotChange}></slot>
      </a>`:J`<button
      part="btn"
      class=${e}
      ?disabled=${this.disabled}
      aria-disabled=${this.softDisabled||Q}
      aria-label=${t||Q}
      aria-pressed=${"toggle"===this.type?this.selected:Q}
      aria-haspopup=${i||Q}
      aria-expanded=${s||Q}
      @change=${this.handleChange}>
      <slot></slot>
      <slot
        name="container"
        ${bt()}
        @slotchange=${this.handleContainerSlotChange}></slot>
    </button>`}handleContainerSlotChange(e){const t=e.target,i=!(this.disabled||this.softDisabled);for(const e of t.assignedElements({flatten:!0}))e.dispatchEvent(new CustomEvent("md-gb:change-container-slot")),e.dispatchEvent(new CustomEvent("md-gb:set-enabled",{detail:{enabled:i}}))}handleChange(e){this.selected="true"===e.target.ariaPressed,it(this,e)}}hi.shadowRootOptions={mode:"open",delegatesFocus:!0},hi.styles=[mt,vt,yt,c`
      :host {
        display: inline-flex;
        isolation: isolate;
      }
      .btn {
        flex: 1;
        position: relative;
      }
      .btn:has([name='container'].has-slotted) {
        background-color: transparent;
      }
      .btn.btn-hide-outline {
        --outline-color: transparent;
        --container-elevation: var(--md-sys-elevation-shadow-0);
      }
      slot[name='container'] {
        display: block;
        position: absolute;
        inset: 0;
        border-radius: inherit;
        pointer-events: none;
        --color: var(--container-color);
        z-index: -1;
        transition: inherit;
      }
      slot[name='container']::slotted(*) {
        width: 100%;
        height: 100%;
      }
    `],t([A()],hi.prototype,"hideOutline",void 0),t([z()],hi.prototype,"color",void 0),t([z()],hi.prototype,"size",void 0),t([z({type:Boolean})],hi.prototype,"square",void 0),t([z({noAccessor:!0})],hi.prototype,"type",null),t([z({type:Boolean,attribute:"soft-disabled",reflect:!0})],hi.prototype,"softDisabled",void 0),t([z({type:Boolean})],hi.prototype,"selected",void 0),t([z()],hi.prototype,"href",void 0),t([z()],hi.prototype,"download",void 0),t([z()],hi.prototype,"target",void 0),
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
customElements.define("md-gb-button",hi);var pi=c`/*!
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */@layer md.sys, md.comp.ripple, md.comp.focus-ring;@layer md.comp.card{.card{--container-color: transparent;--container-elevation: var(--md-sys-elevation-shadow-0);--container-shape: var(--md-sys-shape-corner-md);--outline-color: transparent;--outline-width: 0;--state-layer-color: var(--md-sys-color-on-surface)}.card:is(:hover,.hover):where(:has(.card-btn:not(:disabled,.disabled))){--container-elevation: var(--md-sys-elevation-shadow-1)}.card.card-filled{--container-color: var(--md-sys-color-surface-container-highest)}.card.card-filled:where(:disabled,.disabled){--container-color: hsl( from var(--md-sys-color-surface-container-highest) h s l / 38% )}.card.card-outlined{--container-color: var(--md-sys-color-surface);--outline-color: var(--md-sys-color-outline-variant);--outline-width: 1px}.card.card-outlined:where(:focus-within,.focus){--outline-color: var(--md-sys-color-on-surface)}.card.card-outlined:where(:disabled,.disabled){--outline-color: hsl(from var(--md-sys-color-outline) h s l / 12%)}.card.card-elevated{--container-color: var(--md-sys-color-surface-container-low);--container-elevation: var(--md-sys-elevation-shadow-1)}.card.card-elevated:is(:hover,.hover):where(:has(.card-btn:not(:disabled,.disabled))){--container-elevation: var(--md-sys-elevation-shadow-2)}.card.card-elevated:where(:disabled,.disabled){--container-color: hsl(from var(--md-sys-color-surface) h s l / 38%)}.card{display:flex;flex-direction:column;position:relative;overflow:hidden;background-color:var(--container-color);border-radius:var(--container-shape);box-shadow:var(--container-elevation);border:var(--outline-width) solid var(--outline-color)}.card .card-btn{appearance:none;background-color:rgba(0,0,0,0);border:none;outline:none;position:absolute;inset:0;color:var(--state-layer-color)}}
`.styleSheet;
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */const ui="elevated",mi="filled",vi="outlined",bi="card",yi="card-elevated",gi="card-filled",fi="card-outlined",xi=_t,wi=wt,_i=xt;const ki=Ne({getClasses:function({color:e,interactive:t=!1,hover:i=!1,focus:s=!1,disabled:o=!1}={}){return{[zt]:t,[bi]:!0,[yi]:e===ui,[gi]:e===mi,[fi]:e===vi||!e,[xi]:i,[wi]:s,[_i]:o}}}),$i=Qe(ve);
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */class Ci extends $i{constructor(){super(...arguments),this.hideOutline=!1,this.color="outlined",this.disabled=!1,this.interactive=!1,this.handleSetShowOutline=e=>{const t=e;this.hideOutline=!t.detail.shown}}connectedCallback(){super.connectedCallback(),this.addEventListener("md-gb:set-show-outline",this.handleSetShowOutline)}disconnectedCallback(){super.disconnectedCallback(),this.removeEventListener("md-gb:set-show-outline",this.handleSetShowOutline)}updated(e){super.updated(e),e.has("disabled")&&this.dispatchSetEnabledEvent()}dispatchSetEnabledEvent(){const e=!this.disabled;if(this.lastFiredEnabledState===e)return;const t=this.shadowRoot?.querySelector('slot[name="container"]');if(t)for(const i of t.assignedElements({flatten:!0}))i.dispatchEvent(new CustomEvent("md-gb:set-enabled",{detail:{enabled:e}}));this.lastFiredEnabledState=e}render(){const{ariaLabel:e}=this;return J`<div
      part="card"
      class="${ki({color:this.color,disabled:this.disabled,interactive:this.interactive,classes:{"card-hide-outline":this.hideOutline}})}">
      ${this.interactive?J`<button
            part="card-btn"
            class="card-btn ripple focus-ring-target"
            ${Mt()}
            ?disabled="${this.disabled}"
            aria-label="${e||Q}"></button>`:Q}
      <slot></slot>
      <slot
        name="container"
        ${bt()}
        @slotchange=${this.handleContainerSlotChange}></slot>
    </div>`}handleContainerSlotChange(e){const t=e.target,i=!this.disabled;for(const e of t.assignedElements({flatten:!0}))e.dispatchEvent(new CustomEvent("md-gb:change-container-slot")),e.dispatchEvent(new CustomEvent("md-gb:set-enabled",{detail:{enabled:i}}))}}Ci.shadowRootOptions={mode:"open",delegatesFocus:!0},Ci.styles=[mt,vt,pi,c`
      :host {
        display: inline-flex;
        isolation: isolate;
      }
      .card {
        flex: 1;
        position: relative;
      }
      .card:has([name='container'].has-slotted) {
        background-color: transparent;
      }
      .card.card-hide-outline {
        --outline-color: transparent;
        --container-elevation: var(--md-sys-elevation-shadow-0);
      }
      slot[name='container'] {
        display: block;
        position: absolute;
        inset: 0;
        border-radius: inherit;
        pointer-events: none;
        --color: var(--container-color);
        z-index: -1;
        transition: inherit;
      }
      slot[name='container']::slotted(*) {
        width: 100%;
        height: 100%;
      }
    `],t([A()],Ci.prototype,"hideOutline",void 0),t([z()],Ci.prototype,"color",void 0),t([z({type:Boolean})],Ci.prototype,"disabled",void 0),t([z({type:Boolean})],Ci.prototype,"interactive",void 0),
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
customElements.define("md-gb-card",Ci);
/**
 * @license
 * Copyright 2023 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
const Ei=Symbol("createValidator"),Si=Symbol("getValidityAnchor"),zi=Symbol("privateValidator"),Ai=Symbol("privateSyncValidity"),Li=Symbol("privateCustomValidationMessage");function Pi(e){var t;class i extends e{constructor(){super(...arguments),this[t]=""}get validity(){return this[Ai](),this[st].validity}get validationMessage(){return this[Ai](),this[st].validationMessage}get willValidate(){return this[Ai](),this[st].willValidate}checkValidity(){return this[Ai](),this[st].checkValidity()}reportValidity(){return this[Ai](),this[st].reportValidity()}setCustomValidity(e){this[Li]=e,this[Ai]()}requestUpdate(e,t,i){super.requestUpdate(e,t,i),this[Ai]()}firstUpdated(e){super.firstUpdated(e),this[Ai]()}[(t=Li,Ai)](){this[zi]||(this[zi]=this[Ei]());const{validity:e,validationMessage:t}=this[zi].getValidity(),i=!!this[Li],s=this[Li]||t;this[st].setValidity({...e,customError:i},s,this[Si]()??void 0)}[Ei](){throw new Error("Implement [createValidator]")}[Si](){throw new Error("Implement [getValidityAnchor]")}}return i}
/**
 * @license
 * Copyright 2023 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */class Ti{constructor(e){this.getCurrentState=e,this.currentValidity={validity:{},validationMessage:""}}getValidity(){const e=this.getCurrentState();if(!(!this.prevState||!this.equals(this.prevState,e)))return this.currentValidity;const{validity:t,validationMessage:i}=this.computeValidity(e);return this.prevState=this.copy(e),this.currentValidity={validationMessage:i,validity:{badInput:t.badInput,customError:t.customError,patternMismatch:t.patternMismatch,rangeOverflow:t.rangeOverflow,rangeUnderflow:t.rangeUnderflow,stepMismatch:t.stepMismatch,tooLong:t.tooLong,tooShort:t.tooShort,typeMismatch:t.typeMismatch,valueMissing:t.valueMissing}},this.currentValidity}}
/**
 * @license
 * Copyright 2023 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */class Ii extends Ti{computeValidity(e){return this.checkboxControl||(this.checkboxControl=document.createElement("input"),this.checkboxControl.type="checkbox"),this.checkboxControl.checked=e.checked,this.checkboxControl.required=e.required,{validity:this.checkboxControl.validity,validationMessage:this.checkboxControl.validationMessage}}equals(e,t){return e.checked===t.checked&&e.required===t.required}copy({checked:e,required:t}){return{checked:e,required:t}}}
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */var Oi=c`/*!
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */@layer md.sys, md.comp.ripple, md.comp.focus-ring;@layer md.comp.checkbox{.checkbox{--container-color: transparent;--container-shape: 2px;--container-size: 18px;--icon-color: transparent;--icon-size: 18px;--outline-color: var(--md-sys-color-on-surface-variant);--outline-width: 2px;--state-layer-color: var(--md-sys-color-on-surface);--state-layer-shape: 50%;--state-layer-size: 40px}.checkbox:is(:hover,.hover){--state-layer-color: var(--md-sys-color-on-surface);--outline-color: var(--md-sys-color-on-surface)}.checkbox:is(:focus-within,.focus){--outline-color: var(--md-sys-color-on-surface)}.checkbox:is(:active,.active){--state-layer-color: var(--md-sys-color-primary);--outline-color: var(--md-sys-color-on-surface)}.checkbox:is(:checked,.checked,:indeterminate,.indeterminate){--outline-width: 0;--container-color: var(--md-sys-color-primary);--icon-color: var(--md-sys-color-on-primary)}.checkbox:is(:checked,.checked,:indeterminate,.indeterminate):where(:hover,.hover){--state-layer-color: var(--md-sys-color-primary)}.checkbox:is(:checked,.checked,:indeterminate,.indeterminate):where(:active,.active){--state-layer-color: var(--md-sys-color-on-surface)}.checkbox:is(:invalid,.invalid){--state-layer-color: var(--md-sys-color-error);--outline-color: var(--md-sys-color-error)}.checkbox:is(:invalid,.invalid):where(:checked,.checked,:indeterminate,.indeterminate){--container-color: var(--md-sys-color-error);--icon-color: var(--md-sys-color-on-error)}.checkbox:is(:disabled,.disabled){--outline-color: hsl(from var(--md-sys-color-on-surface) h s l / 38%)}.checkbox:is(:disabled,.disabled):where(:checked,.checked,:indeterminate,.indeterminate){--container-color: hsl(from var(--md-sys-color-on-surface) h s l / 38%);--icon-color: var(--md-sys-color-surface)}.checkbox{appearance:none;position:relative;display:inline-flex;align-items:center;justify-content:center;width:48px;height:48px;background-image:none;outline:none;margin:0;--icon: "check"}.checkbox:is(:indeterminate,.indeterminate)::before{--icon: "remove"}.checkbox::before{content:var(--icon);display:inline-flex;align-items:center;justify-content:center;box-sizing:border-box;overflow:hidden;aspect-ratio:1;width:var(--container-size);background:var(--container-color);border-radius:var(--container-shape);border:var(--outline-width) solid var(--outline-color);color:var(--icon-color);font:var(--icon-size)/1 var(--md-icon-font)}.checkbox::after{content:"";position:absolute;aspect-ratio:1;background-image:var(--ripple);color:var(--state-layer-color);width:var(--state-layer-size);border-radius:var(--state-layer-shape);outline:var(--focus-ring-outline);outline-offset:var(--focus-ring-offset);transition:var(--ripple-transition);animation:var(--ripple-animation),var(--focus-ring-animation)}}
`.styleSheet;
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */const Di="checkbox",Mi=$t,qi=_t,Bi=wt,Ri=gt,Ni=ft,Ui=kt,ji=xt;const Vi=Ne({getClasses:function({invalid:e=!1,hover:t=!1,focus:i=!1,active:s=!1,checked:o=!1,indeterminate:r=!1,disabled:n=!1}={}){return{...It(),...Pt(),[Di]:!0,[Ni]:o,[Ui]:r,[ji]:n,[Mi]:e,[qi]:t,[Bi]:i,[Ri]:s}},setupElement:function(e,t){Dt(e,t)}}),Hi=Qe(Pi(lt(rt(ve))));
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */class Fi extends Hi{constructor(){super(...arguments),this.error=!1,this.checked=!1,this.indeterminate=!1,this.required=!1,this.value="on",this.dirtyCheckedness=!1}get defaultChecked(){return this.hasAttribute("checked")}set defaultChecked(e){this.toggleAttribute("checked",e||!1)}render(){const{ariaLabel:e,ariaInvalid:t}=this;return J`
      <input
        part="checkbox"
        class="${Vi({invalid:this.error})}"
        type="checkbox"
        aria-checked=${this.indeterminate?"mixed":Q}
        aria-label=${e||Q}
        aria-invalid=${t||this.error||Q}
        ?disabled=${this.disabled}
        ?required=${this.required}
        .indeterminate=${this.indeterminate}
        .checked=${this.checked}
        @input=${this.handleInput}
        @change=${this.handleChange} />
    `}handleInput(e){this.dirtyCheckedness=!0;const t=e.target;this.checked=t.checked,this.indeterminate=t.indeterminate}handleChange(e){this.dirtyCheckedness=!0,it(this,e)}attributeChangedCallback(e,t,i){"checked"===e&&this.dirtyCheckedness||super.attributeChangedCallback(e,t,i)}[nt](){return this.checked?this.value:null}[at](){return String(this.checked)}formResetCallback(){this.dirtyCheckedness=!1,this.checked=this.defaultChecked}formStateRestoreCallback(e){this.checked="true"===e}[Ei](){return new Ii(()=>this)}[Si](){return this.input}}Fi.shadowRootOptions={mode:"open",delegatesFocus:!0},Fi.styles=[mt,vt,Oi,c`
      :host {
        display: inline-flex;
      }
      .checkbox {
        flex: 1;
      }
    `],t([z({type:Boolean})],Fi.prototype,"error",void 0),t([z({type:Boolean})],Fi.prototype,"checked",void 0),t([z({type:Boolean})],Fi.prototype,"indeterminate",void 0),t([z({type:Boolean})],Fi.prototype,"required",void 0),t([z()],Fi.prototype,"value",void 0),t([L("input")],Fi.prototype,"input",void 0),
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
customElements.define("md-gb-checkbox",Fi);var Xi=c`/*!
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */@layer md.sys;@layer md.comp.divider{.divider{--color: var(--md-sys-color-outline-variant);--thickness: 1px;box-sizing:border-box;border:none;margin:unset}.divider:not(.divider-vertical){border-bottom:var(--thickness) solid var(--color);width:100%}.divider.divider-vertical{border-inline-end:var(--thickness) solid var(--color);min-height:100%}}
`.styleSheet;
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */const Wi="divider",Gi="divider-vertical";const Yi=Ne({getClasses:function({vertical:e=!1}={}){return{[Wi]:!0,[Gi]:e}}});
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */class Ki extends ve{constructor(){super(...arguments),this.vertical=!1}render(){return J`<div part="divider" class="${Yi(this)}"></div>`}}Ki.styles=[Xi,c`
      :host {
        display: flex;
      }
      .divider {
        flex: 1;
      }
      :host(:not([vertical])) {
        width: 100%;
      }
      :host([vertical]) {
        min-height: 100%;
      }
    `],t([z({type:Boolean,reflect:!0})],Ki.prototype,"vertical",void 0),
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
customElements.define("md-gb-divider",Ki);var Ji=c`/*!
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */@layer md.sys, md.comp.dependencies;@layer md.comp.fab{.fab{--container-color: transparent;--container-elevation: var(--md-sys-elevation-shadow-3);--container-height: 56px;--container-shape: var(--md-sys-shape-corner-lg);--icon-color: currentColor;--icon-label-space: 12px;--icon-size: 24px;--label-text: var(--md-sys-typescale-label-lg);--label-text-color: currentColor;--label-text-tracking: var(--md-sys-typescale-label-lg-tracking);--leading-space: 16px;--trailing-space: 16px}.fab:is(:hover,.hover){--container-elevation: var(--md-sys-elevation-shadow-4)}.fab:is(:active,.active){--container-elevation: var(--md-sys-elevation-shadow-3)}.fab.fab-primary-container{--container-color: var(--md-sys-color-primary-container);--icon-color: var(--md-sys-color-on-primary-container);--label-text-color: var(--md-sys-color-on-primary-container)}.fab.fab-primary{--container-color: var(--md-sys-color-primary);--icon-color: var(--md-sys-color-on-primary);--label-text-color: var(--md-sys-color-on-primary)}.fab.fab-secondary-container{--container-color: var(--md-sys-color-secondary-container);--icon-color: var(--md-sys-color-on-secondary-container);--label-text-color: var(--md-sys-color-on-secondary-container)}.fab.fab-secondary{--container-color: var(--md-sys-color-secondary);--icon-color: var(--md-sys-color-on-secondary);--label-text-color: var(--md-sys-color-on-secondary)}.fab.fab-tertiary-container{--container-color: var(--md-sys-color-tertiary-container);--icon-color: var(--md-sys-color-on-tertiary-container);--label-text-color: var(--md-sys-color-on-tertiary-container)}.fab.fab-tertiary{--container-color: var(--md-sys-color-tertiary);--icon-color: var(--md-sys-color-on-tertiary);--label-text-color: var(--md-sys-color-on-tertiary)}.fab.fab-md{--container-height: 80px;--container-shape: var(--md-sys-shape-corner-lg-increased);--icon-size: 28px;--label-text: var(--md-sys-typescale-title-lg);--label-text-tracking: var(--md-sys-typescale-title-lg-tracking);--leading-space: 26px;--trailing-space: 26px}.fab.fab-lg{--container-height: 96px;--container-shape: var(--md-sys-shape-corner-xl);--icon-label-space: 16px;--icon-size: 36px;--label-text: var(--md-sys-typescale-headline-sm);--label-text-tracking: var(--md-sys-typescale-headline-sm-tracking);--leading-space: 28px;--trailing-space: 28px}.fab{appearance:none;border:none;display:inline-flex;align-items:center;gap:var(--icon-label-space);height:var(--container-height);background-color:var(--container-color);border-radius:var(--container-shape);box-shadow:var(--container-elevation);color:var(--label-text-color);font:var(--label-text);letter-spacing:var(--label-text-tracking);padding-inline:var(--leading-space) var(--trailing-space);animation:var(--ripple-animation),var(--focus-ring-animation);--md-icon-color: var(--icon-color);--md-icon-size: var(--icon-size)}}
`.styleSheet;
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */const Zi="primary",Qi="primary-container",es="secondary",ts="secondary-container",is="tertiary",ss="tertiary-container",os="md",rs="lg",ns="fab",as="fab-primary",ls="fab-primary-container",cs="fab-secondary",ds="fab-secondary-container",hs="fab-tertiary",ps="fab-tertiary-container",us="fab-md",ms="fab-lg",vs=_t,bs=gt;const ys=Ne({getClasses:function({color:e,size:t,hover:i=!1,active:s=!1}={}){return{...It(),...Pt(),[ns]:!0,[as]:e===Zi,[ls]:e===Qi||!e,[cs]:e===es,[ds]:e===ts,[hs]:e===is,[ps]:e===ss,[us]:t===os,[ms]:t===rs,[vs]:i,[bs]:s}},setupElement:function(e,t){Dt(e,t)}});
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */class gs extends ve{constructor(){super(...arguments),this.color="primary-container",this.size="default"}render(){return J`<button
      part="fab"
      class="${ys({color:this.color,size:this.size})}">
      <slot></slot>
    </button>`}}gs.styles=[mt,vt,Ji,c`
      :host {
        display: inline-flex;
      }
      .fab {
        flex: 1;
      }
    `],t([z()],gs.prototype,"color",void 0),t([z()],gs.prototype,"size",void 0),
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
customElements.define("md-gb-fab",gs);var fs=c`/*!
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */@layer md.sys, md.comp.ripple, md.comp.focus-ring;@layer md.comp.icon-btn{.icon-btn{--container-color: transparent;--container-height: auto;--container-shape: calc(var(--container-height) / 2);--icon-color: currentColor;--icon-size: 24px;--outline-color: transparent;--outline-width: 1px;--leading-space: 0;--trailing-space: 0}.icon-btn:is(.icon-btn-selected,[aria-pressed=true]){--container-shape: var(--md-sys-shape-corner-md);--icon-color: var(--md-sys-color-primary)}.icon-btn.icon-btn-filled{--container-color: var(--md-sys-color-primary);--icon-color: var(--md-sys-color-on-primary)}.icon-btn.icon-btn-filled:where(.icon-btn-unselected,[aria-pressed=false]){--container-color: var(--md-sys-color-surface-container);--icon-color: var(--md-sys-color-on-surface-variant)}.icon-btn.icon-btn-filled:where(.icon-btn-selected,[aria-pressed=true]){--container-color: var(--md-sys-color-primary);--icon-color: var(--md-sys-color-on-primary)}.icon-btn.icon-btn-tonal{--container-color: var(--md-sys-color-secondary-container);--icon-color: var(--md-sys-color-on-secondary-container)}.icon-btn.icon-btn-tonal:where(.icon-btn-selected,[aria-pressed=true]){--container-color: var(--md-sys-color-secondary);--icon-color: var(--md-sys-color-on-secondary)}.icon-btn.icon-btn-outlined{--icon-color: var(--md-sys-color-on-surface-variant);--outline-color: var(--md-sys-color-outline-variant)}.icon-btn.icon-btn-outlined:where(.icon-btn-selected,[aria-pressed=true]){--container-color: var(--md-sys-color-inverse-surface);--icon-color: var(--md-sys-color-inverse-on-surface)}.icon-btn.icon-btn-standard{--icon-color: var(--md-sys-color-on-surface-variant)}.icon-btn.icon-btn-standard:where(.icon-btn-selected,[aria-pressed=true]){--icon-color: var(--md-sys-color-primary)}.icon-btn.icon-btn-xs{--container-height: 32px;--icon-size: 20px;--leading-space: 6px;--trailing-space: 6px}.icon-btn.icon-btn-xs:where(.icon-btn-narrow){--leading-space: 4px;--trailing-space: 4px}.icon-btn.icon-btn-xs:where(.icon-btn-wide){--leading-space: 10px;--trailing-space: 10px}.icon-btn.icon-btn-sm{--container-height: 40px;--leading-space: 8px;--trailing-space: 8px}.icon-btn.icon-btn-sm:where(.icon-btn-narrow){--leading-space: 4px;--trailing-space: 4px}.icon-btn.icon-btn-sm:where(.icon-btn-wide){--leading-space: 14px;--trailing-space: 14px}.icon-btn.icon-btn-md{--container-height: 56px;--leading-space: 16px;--trailing-space: 16px}.icon-btn.icon-btn-md:where(.icon-btn-narrow){--leading-space: 12px;--trailing-space: 12px}.icon-btn.icon-btn-md:where(.icon-btn-wide){--leading-space: 24px;--trailing-space: 24px}.icon-btn.icon-btn-md:where(.icon-btn-selected,[aria-pressed=true]){--container-shape: var(--md-sys-shape-corner-lg)}.icon-btn.icon-btn-lg{--container-height: 96px;--icon-size: 32px;--leading-space: 32px;--trailing-space: 32px}.icon-btn.icon-btn-lg:where(.icon-btn-narrow){--leading-space: 16px;--trailing-space: 16px}.icon-btn.icon-btn-lg:where(.icon-btn-wide){--leading-space: 48px;--outline-width: 2px;--trailing-space: 48px}.icon-btn.icon-btn-lg:where(.icon-btn-selected,[aria-pressed=true]){--container-shape: var(--md-sys-shape-corner-xl)}.icon-btn.icon-btn-xl{--container-height: 136px;--icon-size: 40px;--leading-space: 48px;--outline-width: 3px;--trailing-space: 48px}.icon-btn.icon-btn-xl:where(.icon-btn-narrow){--leading-space: 32px;--trailing-space: 32px}.icon-btn.icon-btn-xl:where(.icon-btn-wide){--leading-space: 72px;--trailing-space: 72px}.icon-btn.icon-btn-xl:where(.icon-btn-selected,[aria-pressed=true]){--container-shape: var(--md-sys-shape-corner-xl)}.icon-btn.icon-btn-square{--container-shape: var(--md-sys-shape-corner-md)}.icon-btn.icon-btn-square:where(.icon-btn-md){--container-shape: var(--md-sys-shape-corner-lg)}.icon-btn.icon-btn-square:where(.icon-btn-lg){--container-shape: var(--md-sys-shape-corner-xl)}.icon-btn.icon-btn-square:where(.icon-btn-xl){--container-shape: var(--md-sys-shape-corner-xl)}.icon-btn.icon-btn-square:where(.icon-btn-selected,[aria-pressed=true]){--container-shape: calc(var(--container-height) / 2)}.icon-btn:is(:active,.active):where(:not(:disabled,.disabled)){--container-shape: var(--md-sys-shape-corner-sm)}.icon-btn:is(:active,.active):where(:not(:disabled,.disabled)):where(.icon-btn-md){--container-shape: var(--md-sys-shape-corner-md)}.icon-btn:is(:active,.active):where(:not(:disabled,.disabled)):where(.icon-btn-lg){--container-shape: var(--md-sys-shape-corner-lg)}.icon-btn:is(:active,.active):where(:not(:disabled,.disabled)):where(.icon-btn-xl){--container-shape: var(--md-sys-shape-corner-lg)}.icon-btn:is(:disabled,.disabled){--container-color: hsl(from var(--md-sys-color-on-surface) h s l / 10%);--icon-color: hsl(from var(--md-sys-color-on-surface) h s l / 38%)}.icon-btn{display:inline-grid;place-items:center;background-color:var(--container-color);border:var(--outline-width) solid var(--outline-color);border-radius:var(--container-shape);height:var(--container-height);color:var(--icon-color);transition:border-radius 350ms cubic-bezier(0.42, 1.67, 0.21, 0.9),var(--ripple-transition);--md-icon-size: var(--icon-size)}.icon-btn:not(.icon-btn-narrow,.icon-btn-wide){aspect-ratio:1}.icon-btn:is(.icon-btn-narrow,.icon-btn-wide){padding-inline:var(--leading-space) var(--trailing-space)}}
`.styleSheet;
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */const xs="filled",ws="tonal",_s="outlined",ks="standard",$s="xs",Cs="sm",Es="md",Ss="lg",zs="xl",As="narrow",Ls="wide",Ps="icon-btn",Ts="icon-btn-filled",Is="icon-btn-tonal",Os="icon-btn-outlined",Ds="icon-btn-standard",Ms="icon-btn-xs",qs="icon-btn-sm",Bs="icon-btn-md",Rs="icon-btn-lg",Ns="icon-btn-xl",Us="icon-btn-square",js="icon-btn-narrow",Vs="icon-btn-wide",Hs="icon-btn-unselected",Fs="icon-btn-selected",Xs=gt,Ws=xt;const Gs=Ne({getClasses:function({color:e,size:t,width:i,square:s=!1,selected:o,active:r=!1,disabled:n=!1}={}){return{...It(),...Pt(),[Ps]:!0,[Ts]:e===xs,[Is]:e===ws,[Os]:e===_s,[Ds]:e===ks||!e,[Ms]:t===$s,[qs]:t===Cs||!t,[Bs]:t===Es,[Rs]:t===Ss,[Ns]:t===zs,[js]:i===As,[Vs]:i===Ls,[Us]:s,[Hs]:!1===o,[Fs]:!0===o,[Xs]:r,[Ws]:n}},setupElement:function(e,t){pt(e,"click"),Dt(e,t),e.addEventListener("click",t=>{if(Ct(e))return t.stopImmediatePropagation(),void t.preventDefault();dt(t,()=>{const i=e.hasAttribute("aria-pressed");if(t.defaultPrevented||!i)return;const s="true"===e.ariaPressed;e.ariaPressed=String(!s),e.dispatchEvent(new InputEvent("input",{bubbles:!0,composed:!0})),e.dispatchEvent(new Event("change",{bubbles:!0}))})},t)}}),Ys=Qe(ut(lt(rt(ve))));
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */class Ks extends Ys{constructor(){super(...arguments),this.color="standard",this.size="sm",this.square=!1,this.width="",this.softDisabled=!1,this.selected=!1,this.href="",this.download="",this.target=""}get type(){return this.href?"link":super.type}set type(e){this.href&&"link"!==e||(super.type=e)}render(){const e=Gs({color:this.color,size:this.size,width:this.width,square:this.square,disabled:this.softDisabled}),{ariaLabel:t,ariaHasPopup:i,ariaExpanded:s}=this;return"link"===this.type?J`<a
        part="icon-btn"
        class=${e}
        href=${this.href}
        download=${this.download||Q}
        target=${this.target||Q}
        aria-label=${t||Q}
        aria-haspopup=${i||Q}
        aria-expanded=${s||Q}
        aria-disabled=${this.disabled||this.softDisabled||Q}
        tabindex=${this.disabled&&!this.softDisabled?-1:Q}>
        <slot></slot>
      </a>`:J`<button
      part="icon-btn"
      class=${e}
      type="button"
      ?disabled=${this.disabled}
      aria-disabled=${this.softDisabled||Q}
      aria-label=${t||Q}
      aria-pressed=${"toggle"===this.type?this.selected:Q}
      aria-haspopup=${i||Q}
      aria-expanded=${s||Q}
      @change=${this.handleChange}>
      <slot></slot>
    </button>`}handleChange(e){this.selected="true"===e.target.ariaPressed,it(this,e)}}Ks.shadowRootOptions={mode:"open",delegatesFocus:!0},Ks.styles=[mt,vt,fs,c`
      :host {
        display: inline-flex;
      }
      .icon-btn {
        flex: 1;
      }
    `],t([z()],Ks.prototype,"color",void 0),t([z()],Ks.prototype,"size",void 0),t([z({type:Boolean})],Ks.prototype,"square",void 0),t([z()],Ks.prototype,"width",void 0),t([z({noAccessor:!0})],Ks.prototype,"type",null),t([z({type:Boolean,attribute:"soft-disabled",reflect:!0})],Ks.prototype,"softDisabled",void 0),t([z({type:Boolean})],Ks.prototype,"selected",void 0),t([z()],Ks.prototype,"href",void 0),t([z()],Ks.prototype,"download",void 0),t([z()],Ks.prototype,"target",void 0),
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
customElements.define("md-gb-icon-button",Ks);var Js=c`/*!
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */@layer md.sys, md.comp.ripple, md.comp.focus-ring, md.comp.checkbox, md.comp.radio;@layer md.comp.list{.list{--container-shape: var(--md-sys-shape-corner-lg);--gap: 0}.list.list-segmented{--gap: 2px;--list-segmented: true}.list{display:flex;flex-direction:column;margin:unset;padding:unset;list-style:none;gap:var(--gap);border-radius:var(--container-shape)}.list>:first-child:not(slot),.list ::slotted(:first-child){border-start-start-radius:inherit;border-start-end-radius:inherit;--first-child: true}.list>:last-child:not(slot),.list ::slotted(:last-child){border-end-start-radius:inherit;border-end-end-radius:inherit;--last-child: true}.list slot{border-radius:inherit}.list-select,.list-select::picker(select){appearance:base-select}.list-select{border:none;background:none;height:fit-content}.list-item{--container-height: 56px;--container-color: transparent;--container-shape: var(--md-sys-shape-corner-xs);--label-text-color: var(--md-sys-color-on-surface);--label-text: var(--md-sys-typescale-body-lg);--label-text-tracking: var(--md-sys-typescale-body-lg-tracking);--leading-space: 16px;--trailing-space: 16px;--between-space: 12px;--top-space: 10px;--bottom-space: 10px;--avatar-size: 40px;--avatar-shape: var(--md-sys-shape-corner-full);--avatar-color: var(--md-sys-color-primary-container);--avatar-label: var(--md-sys-typescale-title-md);--avatar-label-tracking: var(--md-sys-typescale-title-md-tracking);--avatar-label-color: var(--md-sys-color-on-primary-container);--leading-icon-color: var(--md-sys-color-on-surface-variant);--leading-icon-size: 20px;--trailing-icon-color: var(--md-sys-color-on-surface-variant);--trailing-icon-size: 20px;--overline: var(--md-sys-typescale-label-sm);--overline-tracking: var(--md-sys-typescale-label-sm-tracking);--overline-color: var(--md-sys-color-on-surface-variant);--supporting-text: var(--md-sys-typescale-body-md);--supporting-text-tracking: var(--md-sys-typescale-body-md-tracking);--supporting-text-color: var(--md-sys-color-on-surface-variant);--trailing-supporting-text: var(--md-sys-typescale-label-sm);--trailing-supporting-text-tracking: var( --md-sys-typescale-label-sm-tracking );--trailing-supporting-text-color: var(--md-sys-color-on-surface-variant)}@container style(--list-segmented: true){.list-item{--container-color: var(--md-sys-color-surface)}}.list-item:where(:not(.list-item-static)):is(:hover,.hover){--container-shape: var(--md-sys-shape-corner-md);--leading-icon-color: var(--md-sys-color-on-surface);--trailing-icon-color: var(--md-sys-color-on-surface)}.list-item:where(:not(.list-item-static)):is(:focus-within,.focus){--container-shape: var(--md-sys-shape-corner-lg);--leading-icon-color: var(--md-sys-color-on-surface);--trailing-icon-color: var(--md-sys-color-on-surface)}.list-item:where(:not(.list-item-static)):is(:active,.active){--container-shape: var(--md-sys-shape-corner-lg);--leading-icon-color: var(--md-sys-color-on-surface);--trailing-icon-color: var(--md-sys-color-on-surface)}.list-item:is(:checked,.checked){--container-shape: var(--md-sys-shape-corner-lg);--container-color: var(--md-sys-color-secondary-container);--label-text-color: var(--md-sys-color-on-secondary-container);--leading-icon-color: var(--md-sys-color-on-secondary-container);--trailing-icon-color: var(--md-sys-color-on-secondary-container);--overline-color: var(--md-sys-color-on-secondary-container);--supporting-text-color: var(--md-sys-color-on-secondary-container);--trailing-supporting-text-color: var( --md-sys-color-on-secondary-container )}.list-item:is(:disabled,.disabled){--container-shape: var(--md-sys-shape-corner-xs);--container-color: hsl(from var(--md-sys-color-on-surface) h s l / 10%);--label-text-color: hsl(from var(--md-sys-color-on-surface) h s l / 38%);--leading-icon-color: hsl( from var(--md-sys-color-on-surface) h s l / 38% );--trailing-icon-color: hsl( from var(--md-sys-color-on-surface) h s l / 38% );--overline-color: hsl(from var(--md-sys-color-on-surface) h s l / 38%);--supporting-text-color: hsl( from var(--md-sys-color-on-surface) h s l / 38% );--trailing-supporting-text-color: hsl( from var(--md-sys-color-on-surface) h s l / 38% )}.list-item{display:flex;align-items:center;gap:var(--between-space);box-sizing:border-box;min-height:var(--container-height);border-radius:var(--container-shape);background-color:var(--container-color);padding-inline:var(--leading-space) var(--trailing-space);padding-block:var(--top-space) var(--bottom-space);color:var(--label-text-color);font:var(--label-text);letter-spacing:var(--label-text-tracking);appearance:none;border:none;text-align:unset;text-decoration:unset;width:stretch}@container style(--first-child: true){.list-item{border-start-start-radius:inherit;border-start-end-radius:inherit}}@container style(--last-child: true){.list-item{border-end-start-radius:inherit;border-end-end-radius:inherit}}.list-item:not(:disabled,.disabled,.list-item-static){cursor:pointer}.list-item .list-item-content{display:flex;flex-direction:column;flex:1}.list-item .list-item-leading,.list-item .list-item-trailing{display:flex;align-items:center;gap:8px;min-height:28px}.list-item .list-item-leading{--md-icon-color: var(--leading-icon-color);--md-icon-size: var(--leading-icon-size)}.list-item .list-item-trailing{--md-icon-color: var(--trailing-icon-color);--md-icon-size: var(--trailing-icon-size)}.list-item .list-item-overline{font:var(--overline);letter-spacing:var(--overline-tracking);color:var(--overline-color)}.list-item .list-item-supporting-text{font:var(--supporting-text);letter-spacing:var(--supporting-text-tracking);color:var(--supporting-text-color)}.list-item .list-item-trailing-text{font:var(--trailing-supporting-text);letter-spacing:var(--trailing-supporting-text-tracking);color:var(--trailing-supporting-text-color)}.list-item .list-item-avatar{display:grid;place-items:center;background-color:var(--avatar-color);border-radius:var(--avatar-shape);aspect-ratio:1;width:var(--avatar-size);font:var(--avatar-label);letter-spacing:var(--avatar-label-tracking);color:var(--avatar-label-color)}.list-item::checkmark{font:var(--leading-icon-size) var(--md-icon-font);color:var(--leading-icon-color);content:"check"}.list-item:has(.list-item-radio,.list-item-checkbox)::checkmark{display:none}.list-item .list-item-radio,.list-item .list-item-checkbox{width:min-content;height:min-content;--ripple: none;--focus-ring-outline: none}.list-item:is(:checked,.checked) .list-item-radio{--icon-color: var(--md-sys-color-primary);--state-layer-color: var(--md-sys-color-primary);--icon: "radio_button_checked"}.list-item:is(:checked,.checked) .list-item-checkbox{--outline-width: 0;--container-color: var(--md-sys-color-primary);--icon-color: var(--md-sys-color-on-primary)}}
`.styleSheet;
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */const Zs="list",Qs="list-segmented";const eo=Ne({getClasses:function({segmented:e=!1}={}){return{[Zs]:!0,[Qs]:e}}}),to="list-item",io="list-item-static",so=ft,oo=_t,ro=wt,no=gt,ao=xt;const lo=Ne({getClasses:function({static:e=!1,checked:t=!1,hover:i=!1,focus:s=!1,active:o=!1,disabled:r=!1}={}){return{[Tt.ripple]:!e,[At]:!e,[to]:!0,[io]:e,[so]:t,[oo]:i,[ro]:s,[no]:o,[ao]:r}},setupElement:function(e,t){Dt(e,t)}}),co=rt(ve);
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */class ho extends co{constructor(){super(),this.segmented=!1,this[st].role="list"}render(){return J`<div part="list" class="${eo(this)}">
      <slot></slot>
    </div>`}}ho.styles=[Js,c`
      :host {
        display: flex;
      }
      .list {
        flex: 1;
      }
    `],t([z({type:Boolean})],ho.prototype,"segmented",void 0),
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
customElements.define("md-gb-list",ho);
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
const po=Qe(rt(ve));class uo extends po{constructor(){super(),this.checked=!1,this.disabled=!1,this.nonInteractive=!1,this[st].role="listitem"}render(){const e={checked:this.checked,disabled:this.disabled,static:this.nonInteractive};if(this.nonInteractive)return J`<div part="list-item" class="${lo(e)}"
        >${this.renderContent()}</div
      >`;const{ariaLabel:t}=this;return J`<button
      part="list-item"
      class="${lo(e)}"
      ?disabled=${this.disabled}
      aria-label=${t||Q}>
      ${this.renderContent()}
    </button>`}renderContent(){return J`
      <span class="list-item-leading">
        <slot name="avatar" class="list-item-avatar" ${bt()}></slot>
        <slot name="leading" ${bt()}></slot>
      </span>
      <span class="list-item-content">
        <slot name="overline" class="list-item-overline"></slot>
        <slot></slot>
        <slot name="supporting-text" class="list-item-supporting-text"></slot>
      </span>
      <span class="list-item-trailing">
        <slot
          name="trailing-text"
          class="list-item-trailing-text"
          ${bt()}></slot>
        <slot name="trailing" ${bt()}></slot>
      </span>
    `}}uo.shadowRootOptions={...ve.shadowRootOptions,delegatesFocus:!0},uo.styles=[mt,vt,Js,c`
      :host {
        display: flex;
        align-items: center;
      }
      .list-item {
        flex: 1;
        align-items: inherit;
      }
      :is(.list-item-leading, .list-item-trailing):not(:has(.has-slotted)) {
        display: none;
      }
      slot:not(.has-slotted) {
        display: contents;
      }
    `],t([z({type:Boolean})],uo.prototype,"checked",void 0),t([z({type:Boolean})],uo.prototype,"disabled",void 0),t([z({type:Boolean,reflect:!0,attribute:"static"})],uo.prototype,"nonInteractive",void 0),
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
customElements.define("md-gb-list-item",uo);
/**
 * @license
 * Copyright 2021 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */
let mo=class extends Event{constructor(e,t,i,s){super("context-request",{bubbles:!0,composed:!0}),this.context=e,this.contextTarget=t,this.callback=i,this.subscribe=s??!1}};
/**
 * @license
 * Copyright 2021 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */
/**
 * @license
 * Copyright 2021 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */
let vo=class{constructor(e,t,i,s){if(this.subscribe=!1,this.provided=!1,this.value=void 0,this.t=(e,t)=>{this.unsubscribe&&(this.unsubscribe!==t&&(this.provided=!1,this.unsubscribe()),this.subscribe||this.unsubscribe()),this.value=e,this.host.requestUpdate(),this.provided&&!this.subscribe||(this.provided=!0,this.callback&&this.callback(e,t)),this.unsubscribe=t},this.host=e,void 0!==t.context){const e=t;this.context=e.context,this.callback=e.callback,this.subscribe=e.subscribe??!1}else this.context=t,this.callback=i,this.subscribe=s??!1;this.host.addController(this)}hostConnected(){this.dispatchRequest()}hostDisconnected(){this.unsubscribe&&(this.unsubscribe(),this.unsubscribe=void 0)}dispatchRequest(){this.host.dispatchEvent(new mo(this.context,this.host,this.t,this.subscribe))}};
/**
 * @license
 * Copyright 2021 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */class bo{get value(){return this.o}set value(e){this.setValue(e)}setValue(e,t=!1){const i=t||!Object.is(e,this.o);this.o=e,i&&this.updateObservers()}constructor(e){this.subscriptions=new Map,this.updateObservers=()=>{for(const[e,{disposer:t}]of this.subscriptions)e(this.o,t)},void 0!==e&&(this.value=e)}addCallback(e,t,i){if(!i)return void e(this.value);this.subscriptions.has(e)||this.subscriptions.set(e,{disposer:()=>{this.subscriptions.delete(e)},consumerHost:t});const{disposer:s}=this.subscriptions.get(e);e(this.value,s)}clearCallbacks(){this.subscriptions.clear()}}
/**
 * @license
 * Copyright 2021 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */let yo=class extends Event{constructor(e,t){super("context-provider",{bubbles:!0,composed:!0}),this.context=e,this.contextTarget=t}};class go extends bo{constructor(e,t,i){super(void 0!==t.context?t.initialValue:i),this.onContextRequest=e=>{if(e.context!==this.context)return;const t=e.contextTarget??e.composedPath()[0];t!==this.host&&(e.stopPropagation(),this.addCallback(e.callback,t,e.subscribe))},this.onProviderRequest=e=>{if(e.context!==this.context)return;if((e.contextTarget??e.composedPath()[0])===this.host)return;const t=new Set;for(const[e,{consumerHost:i}]of this.subscriptions)t.has(e)||(t.add(e),i.dispatchEvent(new mo(this.context,i,e,!0)));e.stopPropagation()},this.host=e,void 0!==t.context?this.context=t.context:this.context=t,this.attachListeners(),this.host.addController?.(this)}attachListeners(){this.host.addEventListener("context-request",this.onContextRequest),this.host.addEventListener("context-provider",this.onProviderRequest)}hostConnected(){this.host.dispatchEvent(new yo(this.context,this.host))}}
/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */
/**
 * @license
 * Copyright 2022 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */
function fo({context:e,subscribe:t}){return(i,s)=>{"object"==typeof s?s.addInitializer(function(){new vo(this,{context:e,callback:e=>{i.set.call(this,e)},subscribe:t})}):i.constructor.addInitializer(i=>{new vo(i,{context:e,callback:e=>{i[s]=e},subscribe:t})})}}
/**
 * @license
 * Copyright 2023 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */const xo=Symbol("isFocusable"),wo=Symbol("privateIsFocusable"),_o=Symbol("externalTabIndex"),ko=Symbol("isUpdatingTabIndex"),$o=Symbol("updateTabIndex");function Co(e){var i,s,o;class r extends e{constructor(){super(...arguments),this[i]=!0,this[s]=null,this[o]=!1}get[xo](){return this[wo]}set[xo](e){this[xo]!==e&&(this[wo]=e,this[$o]())}connectedCallback(){super.connectedCallback(),this[$o]()}attributeChangedCallback(e,t,i){if("tabindex"===e){if(this.requestUpdate("tabIndex",Number(t??-1)),!this[ko])return this.hasAttribute("tabindex")?void(this[_o]=this.tabIndex):(this[_o]=null,void this[$o]())}else super.attributeChangedCallback(e,t,i)}[(i=wo,s=_o,o=ko,$o)](){const e=this[xo]?0:-1,t=this[_o]??e;this[ko]=!0,this.tabIndex=t,this[ko]=!1}}return t([z({noAccessor:!0})],r.prototype,"tabIndex",void 0),r}
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */var Eo=c`/*!
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */@layer md.sys, md.comp.ripple, md.comp.focus-ring, md.comp.divider;@layer md.comp.menu{.menu{--container-color: var(--md-sys-color-surface-container-low);--container-elevation: var(--md-sys-elevation-shadow-2);--container-shape: var(--md-sys-shape-corner-lg);--gap: 2px;--group-padding: 2px;--group-shape: var(--md-sys-shape-corner-sm);--section-label-text-color: var(--md-sys-color-on-surface-variant)}.menu.menu-vibrant{--container-color: var(--md-sys-color-tertiary-container);--menu-vibrant: true}.menu{flex-direction:column;box-shadow:var(--container-elevation);border-radius:var(--container-shape);background-color:var(--container-color);padding-block:var(--group-padding)}.menu:popover-open,.menu.menu-host{display:flex}.menu:popover-open,:host(:popover-open):has(.menu.menu-host){position-area:end span-end;border:none}:host(:popover-open):has(.menu.menu-host){overflow:visible;background:none;padding:0}.menu.menu-host{overflow:auto}.menu .divider,.menu ::slotted(.divider){margin:8px 16px}.menu-item{--between-space: 12px;--bottom-space: 8px;--container-color: transparent;--height: 44px;--inner-corner-corner-size: none;--label-text-color: var(--md-sys-color-on-surface);--label-text: var(--md-sys-typescale-label-lg);--label-text-tracking: var(--md-sys-typescale-label-lg-tracking);--leading-icon-color: var(--md-sys-color-on-surface-variant);--leading-icon-size: 20px;--leading-space: 16px;--shape: var(--md-sys-shape-corner-xs);--supporting-text-color: var(--md-sys-color-on-surface-variant);--supporting-text: var(--md-sys-typescale-body-sm);--supporting-text-tracking: var(--md-sys-typescale-body-sm-tracking);--top-space: 8px;--trailing-icon-color: var(--md-sys-color-on-surface-variant);--trailing-icon-size: 20px;--trailing-space: 16px;--trailing-supporting-text-color: var(--md-sys-color-on-surface-variant);--trailing-supporting-text: var(--md-sys-typescale-label-lg);--trailing-supporting-text-tracking: var(--md-sys-typescale-label-lg-tracking)}.menu-item:first-child{--inner-corner-corner-size: var(--md-sys-shape-corner-xs);--shape: var(--md-sys-shape-corner-md)}.menu-item:last-child{--inner-corner-corner-size: var(--md-sys-shape-corner-xs);--shape: var(--md-sys-shape-corner-md)}@container style(--menu-vibrant: true){.menu-item{--label-text-color: var(--md-sys-color-on-tertiary-container);--leading-icon-color: var(--md-sys-color-on-tertiary-container);--supporting-text-color: var(--md-sys-color-on-tertiary-container);--trailing-icon-color: var(--md-sys-color-on-tertiary-container);--trailing-supporting-text-color: var(--md-sys-color-on-tertiary-container)}.menu-item:where(:hover,.hover){--leading-icon-color: var(--md-sys-color-tertiary);--trailing-icon-color: var(--md-sys-color-tertiary)}.menu-item:where(:focus-within,.focus){--leading-icon-color: var(--md-sys-color-tertiary);--trailing-icon-color: var(--md-sys-color-tertiary)}.menu-item:where(:active,.active){--leading-icon-color: var(--md-sys-color-tertiary);--trailing-icon-color: var(--md-sys-color-tertiary)}}.menu-item:is(:checked,.checked){--container-color: var(--md-sys-color-tertiary-container);--label-text-color: var(--md-sys-color-on-tertiary-container);--leading-icon-color: var(--md-sys-color-on-tertiary-container);--shape: var(--md-sys-shape-corner-md);--supporting-text-color: var(--md-sys-color-on-tertiary-container);--trailing-icon-color: var(--md-sys-color-on-tertiary-container);--trailing-supporting-text-color: var(--md-sys-color-on-tertiary-container)}@container style(--menu-vibrant: true){.menu-item:is(:checked,.checked){--container-color: var(--md-sys-color-tertiary);--label-text-color: var(--md-sys-color-on-tertiary);--leading-icon-color: var(--md-sys-color-on-tertiary);--supporting-text-color: var(--md-sys-color-on-tertiary);--trailing-icon-color: var(--md-sys-color-on-tertiary);--trailing-supporting-text-color: var(--md-sys-color-on-tertiary)}}.menu-item:is(:disabled,.disabled){--label-text-color: hsl(from var(--md-sys-color-on-surface) h s l / 38%);--leading-icon-color: hsl(from var(--md-sys-color-on-surface) h s l / 38%);--supporting-text-color: hsl(from var(--md-sys-color-on-surface) h s l / 38%);--trailing-icon-color: hsl(from var(--md-sys-color-on-surface) h s l / 38%);--trailing-supporting-text-color: hsl(from var(--md-sys-color-on-surface) h s l / 38%)}@container style(--menu-vibrant: true){.menu-item:is(:disabled,.disabled){--label-text-color: hsl(from var(--md-sys-color-on-tertiary-container) h s l / 38%);--leading-icon-color: hsl(from var(--md-sys-color-on-tertiary-container) h s l / 38%);--supporting-text-color: hsl(from var(--md-sys-color-on-tertiary-container) h s l / 38%);--trailing-icon-color: hsl(from var(--md-sys-color-on-tertiary-container) h s l / 38%);--trailing-supporting-text-color: hsl(from var(--md-sys-color-on-tertiary-container) h s l / 38%)}}.menu-item:is(:disabled,.disabled):where(:checked,.checked){--container-color: hsl(from var(--md-sys-color-tertiary-container) h s l / 38%);--label-text-color: hsl(from var(--md-sys-color-on-tertiary-container) h s l / 38%);--leading-icon-color: hsl(from var(--md-sys-color-on-tertiary-container) h s l / 38%);--supporting-text-color: hsl(from var(--md-sys-color-on-tertiary-container) h s l / 38%);--trailing-icon-color: hsl(from var(--md-sys-color-on-tertiary-container) h s l / 38%);--trailing-supporting-text-color: hsl(from var(--md-sys-color-on-tertiary-container) h s l / 38%)}.menu-item{position:relative;isolation:isolate;display:flex;align-items:center;user-select:none;box-sizing:border-box;gap:var(--between-space);min-height:calc(var(--height) + 4px);border-radius:var(--shape);padding-block:var(--top-space) var(--bottom-space);padding-inline:var(--leading-space) var(--trailing-space);background-image:none;outline:none;color:var(--label-text-color);font:var(--label-text);letter-spacing:var(--label-text-tracking)}.menu-item::before{display:flex;content:"";position:absolute;inset:2px 4px;z-index:-1;border-radius:inherit;background-color:var(--container-color);background-image:var(--ripple);animation:var(--ripple-animation),var(--focus-ring-animation);transition:var(--ripple-transition);outline:var(--focus-ring-outline);outline-offset:var(--focus-ring-offset)}.menu-item:not(:disabled,.disabled){cursor:pointer}.menu-item .menu-item-content{display:flex;flex-direction:column;flex:1}.menu-item .menu-item-supporting-text{color:var(--supporting-text-color);font:var(--supporting-text);letter-spacing:var(--supporting-text-tracking)}.menu-item .menu-item-trailing-text{color:var(--trailing-supporting-text-color);font:var(--trailing-supporting-text);letter-spacing:var(--trailing-supporting-text-tracking)}.menu-item .menu-item-leading,.menu-item .menu-item-trailing{display:flex;align-items:center;gap:8px}.menu-item .menu-item-leading{--md-icon-color: var(--leading-icon-color);--md-icon-size: var(--leading-icon-size)}.menu-item .menu-item-trailing{--md-icon-color: var(--trailing-icon-color);--md-icon-size: var(--trailing-icon-size)}}
`.styleSheet;
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */const So=Symbol("menuContext"),zo="standard",Ao="vibrant",Lo="menu",Po="menu-vibrant";const To=Ne({getClasses:function({color:e}={}){return{[Lo]:!0,[Po]:e===Ao}},setupElement:function(e,t){}}),Io=Symbol("menuItemCheckable"),Oo="menu-item",Do=ft,Mo=_t,qo=wt,Bo=gt,Ro=xt;const No=Ne({getClasses:function({checked:e=!1,hover:t=!1,focus:i=!1,active:s=!1,disabled:o=!1}={}){return{...It(),...Pt({type:St}),[Oo]:!0,[Do]:e,[Mo]:t,[qo]:i,[Bo]:s,[Ro]:o}},setupElement:function(e,t){Dt(e,t)}}),Uo=rt(Co(ve));
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */class jo extends Uo{get items(){return Array.from(this.itemsSet).sort((e,t)=>e.compareDocumentPosition(t)&Node.DOCUMENT_POSITION_PRECEDING?1:-1)}constructor(){super(),this.color=zo,this.itemsSet=new Set,this[st].role="menu",this.addController(new go(this,{context:So,initialValue:{menu:this,getItems:()=>this.items,itemConnected:e=>{this.itemsSet.add(e)},itemDisconnected:e=>{this.itemsSet.delete(e)}}})),this.addEventListener("keydown",e=>{if("Tab"===e.key)return e.preventDefault(),void this.hidePopover();const t=this.items.filter(e=>!e.matches(":disabled,[disabled]")),i=t.findIndex(e=>e.matches(":focus-within"));if(-1===i&&t.length>0)["ArrowDown","ArrowRight","ArrowUp","ArrowLeft","Home","End"].includes(e.key)&&(e.preventDefault(),t[0].focus());else switch(e.key){case"ArrowDown":case"ArrowRight":e.preventDefault(),i<t.length-1?t[i+1].focus():t[0].focus();break;case"ArrowUp":case"ArrowLeft":e.preventDefault(),i>0?t[i-1].focus():t[t.length-1].focus();break;case"Home":e.preventDefault(),t[0].focus();break;case"End":e.preventDefault(),t[t.length-1].focus()}}),this.addEventListener("toggle",e=>{"open"===e.newState?(this.previouslyFocused=e.source,setTimeout(()=>{this.items.find(e=>!e.matches(":disabled,.disabled,[disabled]"))?.focus()})):(this.previouslyFocused?.focus(),this.previouslyFocused=void 0)})}connectedCallback(){super.connectedCallback(),this.popover="auto"}render(){return J`<div part="menu" class="${To({color:this.color})} menu-host">
      <slot></slot>
    </div>`}}jo.styles=[Eo,c``],t([z()],jo.prototype,"color",void 0),
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
customElements.define("md-gb-menu",jo);
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
const Vo=rt(ve);class Ho extends Vo{get menu(){return this.menuContext?.menu||null}get items(){return(this.menuContext?.getItems?.()||[]).filter(e=>this.compareDocumentPosition(e)&Node.DOCUMENT_POSITION_CONTAINED_BY)}constructor(){super(),this.checkable=null,this.menuContext=null,this[st].role="none",this.addEventListener("change",e=>{if("single"===this.checkable){const t=e.composedPath(),i=this.items;for(const e of i)!t.includes(e)&&e.checked&&(e.checked=!1)}})}render(){return J`<slot></slot>`}}Ho.styles=[c`
      :host {
        display: contents;
      }
    `],t([function({context:e}){return(t,i)=>{const s=new WeakMap;if("object"==typeof i)return{get(){return t.get.call(this)},set(e){return s.get(this).setValue(e),t.set.call(this,e)},init(t){return s.set(this,new go(this,{context:e,initialValue:t})),t}};{t.constructor.addInitializer(t=>{s.set(t,new go(t,{context:e}))});const o=Object.getOwnPropertyDescriptor(t,i);let r;if(void 0===o){const e=new WeakMap;r={get(){return e.get(this)},set(t){s.get(this).setValue(t),e.set(this,t)},configurable:!0,enumerable:!0}}else{const e=o.set;r={...o,set(t){s.get(this).setValue(t),e?.call(this,t)}}}return void Object.defineProperty(t,i,r)}}}({context:Io}),z({reflect:!0})],Ho.prototype,"checkable",void 0),t([fo({context:So,subscribe:!0})],Ho.prototype,"menuContext",void 0),
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
customElements.define("md-gb-menu-group",Ho);
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
const Fo=rt(Co(ve));class Xo extends Fo{get menu(){return this.menuContext?.menu||null}constructor(){super(),this.checked=!1,this.disabled=!1,this[st].role="menuitem",pt(this,"click"),this.addEventListener("click",e=>{if(this.disabled)return void e.stopImmediatePropagation();const t=this.checked;dt(e,()=>{e.defaultPrevented||(this.checkable&&(this.checked=!t,this.dispatchEvent(new Event("change",{bubbles:!0})),this.dispatchEvent(new InputEvent("input",{bubbles:!0,composed:!0}))),"multiple"!==this.checkable&&this.menu?.hidePopover())})}),this.addEventListener("keydown",e=>{this.disabled||"Enter"!==e.key&&" "!==e.key||(e.preventDefault(),this.click())})}connectedCallback(){super.connectedCallback(),this.menuContext?.itemConnected(this)}disconnectedCallback(){super.disconnectedCallback(),this.menuContext?.itemDisconnected(this)}render(){return J`<div
      part="menu-item"
      class="${No({checked:this.checked,disabled:this.disabled})} ripple-host focus-ring-host">
      ${this.renderContent()}
    </div>`}renderContent(){return J`
      <span class="menu-item-leading">
        <slot name="leading" ${bt()}>
          ${this.checked?J`<span class="checkmark">check</span>`:Q}
        </slot>
      </span>
      <span class="menu-item-content">
        <slot></slot>
        <slot name="supporting-text" class="menu-item-supporting-text"></slot>
      </span>
      <span class="menu-item-trailing">
        <slot
          name="trailing-text"
          class="menu-item-trailing-text"
          ${bt()}></slot>
        <slot name="trailing" ${bt()}></slot>
      </span>
    `}updated(){"single"===this.checkable?this[st].role="menuitemradio":"multiple"===this.checkable?this[st].role="menuitemcheckbox":this[st].role="menuitem",this.checkable?this[st].ariaChecked=String(this.checked):this[st].ariaChecked=null,this[st].ariaDisabled=String(this.disabled),this[xo]=!this.disabled}}Xo.styles=[mt,vt,Eo,c`
      :host {
        display: flex;
        outline: none;
      }
      .menu-item {
        flex: 1;
      }
      :is(.menu-item-leading, .menu-item-trailing):not(
        :has(.has-slotted, .checkmark)
      ) {
        display: none;
      }
      slot:not(.has-slotted) {
        display: contents;
      }
      .checkmark {
        display: flex;
        font: var(--md-icon-size) var(--md-icon-font);
      }
    `],t([z({type:Boolean,reflect:!0})],Xo.prototype,"checked",void 0),t([z({type:Boolean,reflect:!0})],Xo.prototype,"disabled",void 0),t([fo({context:So,subscribe:!0})],Xo.prototype,"menuContext",void 0),t([A(),fo({context:Io,subscribe:!0})],Xo.prototype,"checkable",void 0),
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
customElements.define("md-gb-menu-item",Xo);
/**
 * @license
 * Copyright 2022 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
class Wo{get controls(){const e=this.host.getAttribute("name");return e&&this.root&&this.host.isConnected?Array.from(this.root.querySelectorAll(`[name="${e}"]`)):[this.host]}constructor(e){this.host=e,this.focused=!1,this.root=null,this.handleFocusIn=()=>{this.focused=!0,this.updateTabIndices()},this.handleFocusOut=()=>{this.focused=!1,this.updateTabIndices()},this.handleKeyDown=e=>{const t="ArrowDown"===e.key,i="ArrowUp"===e.key,s="ArrowLeft"===e.key,o="ArrowRight"===e.key;if(!(s||o||t||i))return;const r=this.controls;if(!r.length)return;e.preventDefault();const n="rtl"===getComputedStyle(this.host).direction?s||t:o||t,a=r.indexOf(this.host);let l=n?a+1:a-1;for(;l!==a;){l>=r.length?l=0:l<0&&(l=r.length-1);const e=r[l];if(!e.hasAttribute("disabled")){for(const t of r)t!==e&&(t.checked=!1,t.tabIndex=-1,t.blur());e.checked=!0,e.tabIndex=0,e.focus(),e.dispatchEvent(new Event("change",{bubbles:!0}));break}n?l++:l--}}}hostConnected(){this.host.addEventListener("keydown",this.handleKeyDown),this.host.addEventListener("focusin",this.handleFocusIn),this.host.addEventListener("focusout",this.handleFocusOut),queueMicrotask(()=>{this.root=this.host.getRootNode(),this.host.checked&&this.uncheckSiblings(),this.updateTabIndices()})}hostDisconnected(){this.host.removeEventListener("keydown",this.handleKeyDown),this.host.removeEventListener("focusin",this.handleFocusIn),this.host.removeEventListener("focusout",this.handleFocusOut),queueMicrotask(()=>{this.updateTabIndices(),this.root=null})}handleCheckedChange(){this.host.checked&&(this.uncheckSiblings(),this.updateTabIndices())}uncheckSiblings(){for(const e of this.controls)e!==this.host&&(e.checked=!1)}updateTabIndices(){const e=this.controls,t=e.find(e=>e.checked);if(t||this.focused){const i=t||this.host;i.tabIndex=0;for(const t of e)t!==i&&(t.tabIndex=-1);return}for(const t of e)t.tabIndex=0}}
/**
 * @license
 * Copyright 2023 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */class Go extends Ti{computeValidity(e){this.radioElement||(this.radioElement=document.createElement("input"),this.radioElement.type="radio",this.radioElement.name="group");let t=!1,i=!1;for(const{checked:s,required:o}of e)o&&(t=!0),s&&(i=!0);return this.radioElement.checked=i,this.radioElement.required=t,{validity:{valueMissing:t&&!i},validationMessage:this.radioElement.validationMessage}}equals(e,t){if(e.length!==t.length)return!1;for(let i=0;i<e.length;i++){const s=e[i],o=t[i];if(s.checked!==o.checked||s.required!==o.required)return!1}return!0}copy(e){return e.map(({checked:e,required:t})=>({checked:e,required:t}))}}
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */var Yo=c`/*!
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */@layer md.sys, md.comp.ripple, md.comp.focus-ring;@layer md.comp.radio{.radio{--icon-color: var(--md-sys-color-on-surface-variant);--icon-size: 20px;--state-layer-color: var(--md-sys-color-on-surface);--state-layer-shape: 50%;--state-layer-size: 40px}.radio:where(:hover,.hover){--icon-color: var(--md-sys-color-on-surface)}.radio:where(:focus-within,.focus){--icon-color: var(--md-sys-color-on-surface)}.radio:where(:active,.active){--state-layer-color: var(--md-sys-color-primary)}.radio:is(:checked,.checked){--icon-color: var(--md-sys-color-primary);--state-layer-color: var(--md-sys-color-primary)}.radio:is(:checked,.checked):where(:active,.active){--state-layer-color: var(--md-sys-color-on-surface)}.radio:is(:disabled,.disabled){--icon-color: hsl(from var(--md-sys-color-on-surface) h s l / 38%)}.radio{appearance:none;position:relative;display:inline-flex;align-items:center;justify-content:center;width:48px;height:48px;background-image:none;outline:none;--icon: "radio_button_unchecked"}.radio::before{content:var(--icon);color:var(--icon-color);font:var(--icon-size)/1 var(--md-icon-font);width:var(--icon-size);aspect-ratio:1}.radio:is(:checked,.checked)::before{--icon: "radio_button_checked"}.radio::after{content:"";position:absolute;aspect-ratio:1;background-image:var(--ripple);color:var(--state-layer-color);width:var(--state-layer-size);border-radius:var(--state-layer-shape);outline:var(--focus-ring-outline);outline-offset:var(--focus-ring-offset);transition:var(--ripple-transition);animation:var(--ripple-animation),var(--focus-ring-animation)}}
`.styleSheet;
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */const Ko="radio",Jo=_t,Zo=wt,Qo=gt,er=ft,tr=xt;const ir=Ne({getClasses:function({hover:e=!1,focus:t=!1,active:i=!1,checked:s=!1,disabled:o=!1}={}){return{...It(),...Pt(),[Ko]:!0,[Jo]:e,[Zo]:t,[Qo]:i,[er]:s,[tr]:o}},setupElement:function(e,t){Dt(e,t)}}),sr=Pi(lt(rt(Co(ve))));
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */class or extends sr{get checked(){return"true"===this[st].ariaChecked}set checked(e){const t=this.checked;t!==e&&(this[st].ariaChecked=String(e),this.requestUpdate("checked",t),this.selectionController.handleCheckedChange())}get defaultChecked(){return this.hasAttribute("checked")}set defaultChecked(e){this.toggleAttribute("checked",e||!1)}constructor(){super(),this.required=!1,this.value="on",this.selectionController=new Wo(this),this.dirtyCheckedness=!1,this.isFocused=!1,this[st].role="radio",this.addController(this.selectionController),pt(this,"click","keydown"),this.addEventListener("click",e=>{this.disabled||this.checked||dt(e,()=>{e.defaultPrevented||(this.checked=!0,this.dirtyCheckedness=!0,this.dispatchEvent(new Event("change",{bubbles:!0})),this.dispatchEvent(new InputEvent("input",{bubbles:!0,composed:!0})))})}),this.addEventListener("keydown",e=>{dt(e,()=>{" "!==e.key||e.defaultPrevented||this.click()})}),this.addEventListener("focus",()=>{this.isFocused=!0}),this.addEventListener("blur",()=>{this.isFocused=!1})}render(){return J`<div
      part="radio"
      class="ripple-host focus-ring-host ${ir({checked:this.checked,disabled:this.disabled,focus:this.isFocused})}"></div>`}attributeChangedCallback(e,t,i){"checked"===e&&this.dirtyCheckedness||super.attributeChangedCallback(e,t,i)}[nt](){return this.checked?this.value:null}[at](){return String(this.checked)}formResetCallback(){this.dirtyCheckedness=!1,this.checked=this.defaultChecked}formStateRestoreCallback(e){this.checked="true"===e}[Ei](){return new Go(()=>this.selectionController?this.selectionController.controls:[this])}[Si](){return this.radio}}or.styles=[mt,vt,Yo,c`
      :host {
        display: inline-flex;
        outline: none;
      }
      .radio {
        flex: 1;
      }
    `],t([z({type:Boolean})],or.prototype,"checked",null),t([z({type:Boolean})],or.prototype,"required",void 0),t([z()],or.prototype,"value",void 0),t([L(".radio")],or.prototype,"radio",void 0),t([A()],or.prototype,"isFocused",void 0),
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
customElements.define("md-gb-radio",or);var rr=c`/*!
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */@layer md.sys, md.comp.ripple, md.comp.focus-ring, md.comp.button;@layer md.comp.split-button{.split-btn{--between-space: 2px}.split-btn .btn{--icon-size: 0;--inner-corner-size: 0;--outer-corner-size: calc(var(--container-height) / 2);--leading-space: 0;--trailing-space: 0}.split-btn .btn-xs{--icon-size: 22px;--inner-corner-size: var(--md-sys-shape-corner-xs)}.split-btn .btn-xs:where(:nth-child(1 of .btn)){--leading-space: 12px;--trailing-space: 10px}.split-btn .btn-xs:where(:nth-last-child(1 of .btn)){--leading-space: 13px;--trailing-space: 13px}.split-btn .btn-xs:where(:hover,.hover){--inner-corner-size: var(--md-sys-shape-corner-sm)}.split-btn .btn-xs:where(:active,.active){--inner-corner-size: var(--md-sys-shape-corner-sm)}.split-btn .btn-sm{--icon-size: 22px;--inner-corner-size: var(--md-sys-shape-corner-xs)}.split-btn .btn-sm:where(:nth-child(1 of .btn)){--leading-space: 16px;--trailing-space: 12px}.split-btn .btn-sm:where(:nth-last-child(1 of .btn)){--leading-space: 13px;--trailing-space: 13px}.split-btn .btn-sm:where(:hover,.hover){--inner-corner-size: var(--md-sys-shape-corner-md)}.split-btn .btn-sm:where(:active,.active){--inner-corner-size: var(--md-sys-shape-corner-md)}.split-btn .btn-md{--icon-size: 26px;--inner-corner-size: var(--md-sys-shape-corner-xs)}.split-btn .btn-md:where(:nth-child(1 of .btn)){--leading-space: 24px;--trailing-space: 24px}.split-btn .btn-md:where(:nth-last-child(1 of .btn)){--leading-space: 15px;--trailing-space: 15px}.split-btn .btn-md:where(:hover,.hover){--inner-corner-size: var(--md-sys-shape-corner-md)}.split-btn .btn-md:where(:active,.active){--inner-corner-size: var(--md-sys-shape-corner-md)}.split-btn .btn-lg{--icon-size: 38px;--inner-corner-size: var(--md-sys-shape-corner-sm)}.split-btn .btn-lg:where(:nth-child(1 of .btn)){--leading-space: 48px;--trailing-space: 48px}.split-btn .btn-lg:where(:nth-last-child(1 of .btn)){--leading-space: 29px;--trailing-space: 29px}.split-btn .btn-lg:where(:hover,.hover){--inner-corner-size: var(--md-sys-shape-corner-lg-increased)}.split-btn .btn-lg:where(:active,.active){--inner-corner-size: var(--md-sys-shape-corner-lg-increased)}.split-btn .btn-xl{--icon-size: 50px;--inner-corner-size: var(--md-sys-shape-corner-md)}.split-btn .btn-xl:where(:nth-child(1 of .btn)){--leading-space: 64px;--trailing-space: 64px}.split-btn .btn-xl:where(:nth-last-child(1 of .btn)){--leading-space: 43px;--trailing-space: 43px}.split-btn .btn-xl:where(:hover,.hover){--inner-corner-size: var(--md-sys-shape-corner-lg-increased)}.split-btn .btn-xl:where(:active,.active){--inner-corner-size: var(--md-sys-shape-corner-lg-increased)}.split-btn:is(.split-btn-selected,:has(:popover-open)) :nth-last-child(1 of .btn){--inner-corner-size: calc(var(--container-height) / 2)}.split-btn{display:inline-flex;align-items:center;gap:var(--between-space)}.split-btn :nth-child(1 of .btn){border-start-start-radius:var(--outer-corner-size);border-end-start-radius:var(--outer-corner-size);border-start-end-radius:var(--inner-corner-size);border-end-end-radius:var(--inner-corner-size)}.split-btn :nth-last-child(1 of .btn){border-start-start-radius:var(--inner-corner-size);border-end-start-radius:var(--inner-corner-size);border-start-end-radius:var(--outer-corner-size);border-end-end-radius:var(--outer-corner-size)}.split-btn :nth-last-child(1 of .btn)::before{content:"arrow_drop_down";font:var(--md-icon-size) var(--md-icon-font);display:flex;align-items:center;aspect-ratio:1;width:var(--md-icon-size);overflow:hidden}.split-btn:is(.split-btn-selected,:has(:popover-open)) :nth-last-child(1 of .btn)::before{transform:rotate(180deg);transition:transform var(--md-sys-motion-duration-short4) var(--md-sys-motion-easing-standard)}}
`.styleSheet;
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */const nr="split-btn",ar="split-btn-selected";const lr=Ne({getClasses:function({selected:e=!1}={}){return{[nr]:!0,[ar]:e}}});
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */class cr extends ve{constructor(){super(...arguments),this.color="filled",this.size="sm",this.selected=!1}render(){const e={color:this.color,size:this.size};return J`<div part="split-btn" class="${lr(this)}">
      <slot
        name="leading"
        part="leading-btn"
        class="${ci(e)}"
        @focusin=${this.updateSlotFocusVisible}
        @focusout=${this.updateSlotFocusVisible}>
      </slot>
      <slot
        name="trailing"
        part="trailing-btn"
        class="${ci(e)}"
        @focusin=${this.updateSlotFocusVisible}
        @focusout=${this.updateSlotFocusVisible}
        @slotchange=${this.handleTrailingSlotchange}>
      </slot>
      <slot></slot>
    </div>`}updateSlotFocusVisible(e){const t=e.currentTarget,i=t.assignedElements().some(e=>e.matches(":focus-visible,:has(:focus-visible)"));t.classList.toggle("focus-visible",i)}handleTrailingSlotchange(e){this.cleanupToggleListener?.abort(),this.cleanupToggleListener=new AbortController;const t=e.target.assignedElements().find(e=>e.matches("button"));t?.popoverTargetElement&&t.popoverTargetElement.addEventListener("toggle",e=>{this.selected="open"===e.newState},{signal:this.cleanupToggleListener.signal})}}cr.styles=[mt,vt,yt,rr,c`
      :host {
        display: inline-flex;
      }
      .split-btn {
        flex: 1;
      }
      [name='leading'] {
        display: contents;
        &::slotted(button) {
          all: inherit;
          display: flex;
        }
      }
      [name='trailing'] {
        position: relative;
        &::slotted(button) {
          position: absolute;
          inset: 0;
          appearance: none;
          background: none;
          border: none;
          outline: none;
        }
      }
    `],t([z()],cr.prototype,"color",void 0),t([z()],cr.prototype,"size",void 0),t([z({type:Boolean})],cr.prototype,"selected",void 0),
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
customElements.define("md-gb-split-button",cr);
/**
 * @license
 * Copyright 2024 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
const dr=Symbol("hasState"),hr=Symbol("toggleState"),pr=Symbol("privateUseDashedIdentifier"),ur=Symbol("privateGetStateIdentifier");var mr=c`/*!
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */@layer md.sys, md.comp.ripple, md.comp.focus-ring;@layer md.comp.switch{.switch{--handle-color: var(--md-sys-color-outline);--handle-size: 16px;--with-icon-handle-size: 24px;--icon-color: var(--md-sys-color-surface-container-highest);--icon-size: 18px;--state-layer-color: var(--md-sys-color-on-surface);--state-layer-size: 40px;--track-color: var(--md-sys-color-surface-container-highest);--track-height: 32px;--track-outline-color: var(--md-sys-color-outline);--track-outline-width: 2px;--track-width: 52px}.switch:is(:hover,.hover){--handle-color: var(--md-sys-color-on-surface-variant)}.switch:is(:checked,.checked,[aria-checked=true]){--track-color: var(--md-sys-color-primary);--track-outline-color: transparent;--handle-color: var(--md-sys-color-on-primary);--handle-size: 24px;--icon-color: var(--md-sys-color-on-primary-container);--state-layer-color: var(--md-sys-color-primary)}.switch:is(:checked,.checked,[aria-checked=true]):where(:hover,.hover){--handle-color: var(--md-sys-color-primary-container)}.switch:is(:active,.active):where(:not(:disabled,.disabled)){--handle-size: 28px}.switch:is(:disabled,.disabled){--handle-color: hsl(from var(--md-sys-color-on-surface) h s l / 38%);--track-color: hsl( from var(--md-sys-color-surface-container-highest) h s l / 12% );--track-outline-color: hsl( from var(--md-sys-color-on-surface) h s l / 12% )}.switch:is(:disabled,.disabled):where(:checked,.checked,[aria-checked=true]){--handle-color: var(--md-sys-color-surface);--icon-color: hsl(from var(--md-sys-color-on-surface) h s l / 38%);--track-color: hsl(from var(--md-sys-color-on-surface) h s l / 12%)}.switch{display:inline-grid;grid-template:". handle ." 1fr/0fr min-content 1fr;place-items:center;appearance:none;background:var(--track-color);border:var(--track-outline-width) solid var(--track-outline-color);border-radius:calc(var(--track-height)/2);width:var(--track-width);height:var(--track-height);margin:0;padding:0;transition:grid-template-columns 75ms cubic-bezier(0.4, 0, 0.2, 1);isolation:isolate;position:relative;color:var(--icon-color);--md-icon-size: var(--icon-size);--icon: ""}.switch:is(:checked,.checked,[aria-checked=true]){grid-template-columns:1fr min-content 0fr}.switch::before{--_handle-size: if( style(--icon: ""): var(--handle-size); else: max(var(--handle-size), var(--with-icon-handle-size)); );content:var(--icon);font:var(--md-icon-size) var(--md-icon-font);display:grid;place-items:center;z-index:-1;grid-area:handle;aspect-ratio:1;height:100%;clip-path:circle(calc(var(--_handle-size) / 2));transition:clip-path 75ms cubic-bezier(0.4, 0, 0.2, 1);border-radius:50%;background:var(--handle-color)}.switch::after{content:"";z-index:-1;position:absolute;grid-area:handle;background-image:var(--ripple);aspect-ratio:1;width:var(--state-layer-size);border-radius:50%;color:var(--state-layer-color);transition:var(--ripple-transition);animation:var(--ripple-animation)}.switch:has(.switch-icon-off:not(slot),.switch-icon-off.has-slotted){--icon: " "}.switch .switch-icon-off:not(slot),.switch .switch-icon-off::slotted(*),.switch .switch-icon-on:not(slot),.switch .switch-icon-on::slotted(*){grid-area:handle}.switch:is(:checked,.checked,[aria-checked=true]) .switch-icon-off,.switch:not(:checked,.checked,[aria-checked=true]) .switch-icon-on{display:none}}
`.styleSheet;
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */const vr="switch",br=ft,yr=_t,gr=gt,fr=xt;const xr=Ne({getClasses:function({checked:e=!1,hover:t=!1,active:i=!1,disabled:s=!1}={}){return{...It(),...Pt(),[vr]:!0,[br]:e,[yr]:t,[gr]:i,[fr]:s}},setupElement:function(e,t){const i=e.matches("button");i&&pt(e,"click"),Dt(e,t),i&&e.addEventListener("click",t=>{if(Ct(e))return;const i="true"===e.ariaChecked;e.ariaChecked=String(!i),dt(t,()=>{t.defaultPrevented?e.ariaChecked=String(i):(e.dispatchEvent(new InputEvent("input",{bubbles:!0,composed:!0})),e.dispatchEvent(new Event("change",{bubbles:!0})))})},t)}}),wr=Qe(Pi(lt(function(e){var t;class i extends e{constructor(){super(...arguments),this[t]=null}[dr](e){return e=this[ur](e),this[st].states.has(e)}[hr](e,t){e=this[ur](e),t?this[st].states.add(e):this[st].states.delete(e)}[(t=pr,ur)](e){if(null===this[pr])try{const e="_test";this[st].states.add(e),this[st].states.delete(e),this[pr]=!1}catch{this[pr]=!0}return this[pr]?`--${e}`:e}}return i}
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */(rt(ve)))));
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */class _r extends wr{constructor(){super(...arguments),this.required=!1,this.value="on",this.dirtyCheckedness=!1}get selected(){return this[dr]("selected")}set selected(e){this[hr]("selected",e)}get defaultSelected(){return this.hasAttribute("selected")}set defaultSelected(e){this.toggleAttribute("selected",e||!1)}render(){const{ariaLabel:e}=this;return J`
      <button
        role="switch"
        part="switch"
        class="${xr()}"
        aria-checked="${this.selected?"true":"false"}"
        aria-label=${e||Q}
        ?disabled=${this.disabled}
        @change=${this.handleChange}>
        <slot name="off-icon" class="switch-icon-off" ${bt()}></slot>
        <slot name="on-icon" class="switch-icon-on"></slot>
      </button>
    `}handleChange(e){this.dirtyCheckedness=!0,this.selected="true"===this.button?.ariaChecked,it(this,e)}attributeChangedCallback(e,t,i){"selected"===e&&this.dirtyCheckedness||super.attributeChangedCallback(e,t,i)}[nt](){return this.selected?this.value:null}[at](){return String(this.selected)}formResetCallback(){this.dirtyCheckedness=!1,this.selected=this.defaultSelected}formStateRestoreCallback(e){this.selected="true"===e}[Ei](){return new Ii(()=>({checked:this.selected,required:this.required}))}[Si](){return this.button}}_r.shadowRootOptions={mode:"open",delegatesFocus:!0},_r.styles=[mt,vt,mr,c`
      :host {
        display: inline-flex;
      }
      .switch {
        flex: 1;
      }
      ::slotted(*) {
        grid-area: handle;
      }
    `],t([z({type:Boolean})],_r.prototype,"selected",null),t([z({type:Boolean})],_r.prototype,"required",void 0),t([z()],_r.prototype,"value",void 0),t([L("button")],_r.prototype,"button",void 0),
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
customElements.define("md-gb-switch",_r);
/**
 * @license
 * Copyright 2022 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
class kr extends ve{render(){return J`<slot></slot>`}connectedCallback(){super.connectedCallback();"false"!==this.getAttribute("aria-hidden")?this.setAttribute("aria-hidden","true"):this.removeAttribute("aria-hidden")}}
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */function $r(e,t){if(e&&"adoptedStyleSheets"in e){const i=t.filter(t=>!e.adoptedStyleSheets.includes(t));return!i.length||(e.adoptedStyleSheets=Array.from(new Set([...e.adoptedStyleSheets,...i])),!0)}return!1}
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */var Cr=c`/*!
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */@property --md-icon-font{syntax:"<string>#";inherits:true;initial-value:"Material Symbols"}@property --md-icon-color{syntax:"<color>";inherits:true;initial-value:currentColor}@property --md-icon-size{syntax:"<length-percentage>";inherits:true;initial-value:24px}@property --md-icon-opsz{syntax:"<number>";inherits:true;initial-value:24}@property --md-icon-wght{syntax:"<number>";inherits:true;initial-value:400}@property --md-icon-fill{syntax:"<number>";inherits:true;initial-value:0}@property --md-icon-grad{syntax:"<number>";inherits:true;initial-value:0}@layer md.sys.icon{.md-icon,:host(.md-icon){display:inline-flex;place-items:center;place-content:center;overflow:hidden;color:var(--md-icon-color);fill:var(--md-icon-color);aspect-ratio:1;min-width:var(--md-icon-size);max-width:var(--md-icon-size);font-size:var(--md-icon-size);font-family:var(--md-icon-font);font-variation-settings:"opsz" var(--md-icon-opsz),"wght" var(--md-icon-wght),"FILL" var(--md-icon-fill),"GRAD" var(--md-icon-grad);-webkit-font-smoothing:antialiased}}
`.styleSheet;
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */class Er extends kr{connectedCallback(){super.connectedCallback(),function(e,t){if(!e)return;const i=(t=Array.isArray(t)?t:[t]).map(e=>e instanceof CSSStyleSheet?e:e.styleSheet);$r(e,i)||($r(e.ownerDocument,i),$r(e.getRootNode(),i))}(this,Cr),this.classList.add("md-icon")}}Er.styles=[Cr],
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
customElements.define("md-gb-icon",Er);
/**
 * @license
 * Copyright 2021 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
const Sr=Qe(ve);class zr extends Sr{constructor(){super(...arguments),this.opened=!1,this.pivot="end"}render(){const e=this.opened?"true":"false",t=this.opened?"false":"true",{ariaLabel:i,ariaModal:s}=this;return J`
      <div
        aria-expanded="${e}"
        aria-hidden="${t}"
        aria-label=${i||Q}
        aria-modal="${s||Q}"
        class="md3-navigation-drawer ${this.getRenderClasses()}"
        role="dialog">
        <md-elevation part="elevation"></md-elevation>
        <div class="md3-navigation-drawer__slot-content">
          <slot></slot>
        </div>
      </div>
    `}getRenderClasses(){return Ae({"md3-navigation-drawer--opened":this.opened,"md3-navigation-drawer--pivot-at-start":"start"===this.pivot})}updated(e){e.has("opened")&&setTimeout(()=>{this.dispatchEvent(new CustomEvent("navigation-drawer-changed",{detail:{opened:this.opened},bubbles:!0,composed:!0}))},250)}}t([z({type:Boolean})],zr.prototype,"opened",void 0),t([z()],zr.prototype,"pivot",void 0);
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
const Ar=c`:host{--_container-color: var(--md-navigation-drawer-container-color, #fff);--_container-height: var(--md-navigation-drawer-container-height, 100%);--_container-shape: var(--md-navigation-drawer-container-shape, 0 16px 16px 0);--_container-width: var(--md-navigation-drawer-container-width, 360px);--_divider-color: var(--md-navigation-drawer-divider-color, #000);--_modal-container-elevation: var(--md-navigation-drawer-modal-container-elevation, 1);--_standard-container-elevation: var(--md-navigation-drawer-standard-container-elevation, 0);--md-elevation-level: var(--_standard-container-elevation);--md-elevation-shadow-color: var(--_divider-color)}:host{display:flex}.md3-navigation-drawer{inline-size:0;box-sizing:border-box;display:flex;justify-content:flex-end;overflow:hidden;overflow-y:auto;visibility:hidden;transition:inline-size .25s cubic-bezier(0.4, 0, 0.2, 1) 0s,visibility 0s cubic-bezier(0.4, 0, 0.2, 1) .25s}md-elevation{z-index:0}.md3-navigation-drawer--opened{visibility:visible;transition:inline-size .25s cubic-bezier(0.4, 0, 0.2, 1) 0s,visibility 0s cubic-bezier(0.4, 0, 0.2, 1) 0s}.md3-navigation-drawer--pivot-at-start{justify-content:flex-start}.md3-navigation-drawer__slot-content{display:flex;flex-direction:column;position:relative}
`;Ar.styleSheet;
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
const Lr=c`.md3-navigation-drawer-modal,.md3-navigation-drawer{background-color:var(--_container-color);border-radius:var(--_container-shape);height:var(--_container-height)}.md3-navigation-drawer-modal.md3-navigation-drawer-modal--opened,.md3-navigation-drawer.md3-navigation-drawer--opened{inline-size:var(--_container-width)}.md3-navigation-drawer-modal .md3-navigation-drawer-modal__slot-content,.md3-navigation-drawer .md3-navigation-drawer__slot-content{min-inline-size:var(--_container-width);max-inline-size:var(--_container-width)}
`;Lr.styleSheet;
/**
 * @license
 * Copyright 2021 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
let Pr=class extends zr{};Pr.styles=[Lr,Ar],Pr=t([i("md-navigation-drawer")],Pr);
/**
 * @license
 * Copyright 2021 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
const Tr=Qe(ve);class Ir extends Tr{constructor(){super(...arguments),this.opened=!1,this.pivot="end"}render(){const e=this.opened?"true":"false",t=this.opened?"false":"true",{ariaLabel:i,ariaModal:s}=this;return J`
      <div
        class="md3-navigation-drawer-modal__scrim ${this.getScrimClasses()}"
        @click="${this.handleScrimClick}">
      </div>
      <div
        aria-expanded=${e}
        aria-hidden=${t}
        aria-label=${i||Q}
        aria-modal=${s||Q}
        class="md3-navigation-drawer-modal ${this.getRenderClasses()}"
        @keydown="${this.handleKeyDown}"
        role="dialog"
        ><div class="md3-elevation-overlay"></div>
        <div class="md3-navigation-drawer-modal__slot-content">
          <slot></slot>
        </div>
      </div>
    `}getScrimClasses(){return Ae({"md3-navigation-drawer-modal--scrim-visible":this.opened})}getRenderClasses(){return Ae({"md3-navigation-drawer-modal--opened":this.opened,"md3-navigation-drawer-modal--pivot-at-start":"start"===this.pivot})}updated(e){e.has("opened")&&setTimeout(()=>{this.dispatchEvent(new CustomEvent("navigation-drawer-changed",{detail:{opened:this.opened},bubbles:!0,composed:!0}))},250)}handleKeyDown(e){"Escape"===e.code&&(this.opened=!1)}handleScrimClick(){this.opened=!this.opened}}t([z({type:Boolean})],Ir.prototype,"opened",void 0),t([z()],Ir.prototype,"pivot",void 0);
/**
 * @license
 * Copyright 2026 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
const Or=c`:host{--_container-color: var(--md-navigation-drawer-modal-container-color, #fff);--_container-height: var(--md-navigation-drawer-modal-container-height, 100%);--_container-shape: var(--md-navigation-drawer-modal-container-shape, 0 16px 16px 0);--_container-width: var(--md-navigation-drawer-modal-container-width, 360px);--_divider-color: var(--md-navigation-drawer-modal-divider-color, #000);--_modal-container-elevation: var(--md-navigation-drawer-modal-modal-container-elevation, 1);--_scrim-color: var(--md-navigation-drawer-modal-scrim-color, );--_scrim-opacity: var(--md-navigation-drawer-modal-scrim-opacity, 0.04);--_standard-container-elevation: var(--md-navigation-drawer-modal-standard-container-elevation, 0);--md-elevation-level: var(--_modal-container-elevation)}.md3-navigation-drawer-modal{bottom:0;box-sizing:border-box;display:flex;justify-content:flex-end;overflow:hidden;position:absolute;top:0;inline-size:0;transition:inline-size .25s cubic-bezier(0.4, 0, 0.2, 1) 0s,visibility 0s cubic-bezier(0.4, 0, 0.2, 1) .25s}.md3-navigation-drawer-modal--opened{transition:inline-size .25s cubic-bezier(0.4, 0, 0.2, 1) 0s,visibility 0s cubic-bezier(0.4, 0, 0.2, 1) 0s}.md3-navigation-drawer-modal--pivot-at-start{justify-content:flex-start}.md3-navigation-drawer-modal__slot-content{display:flex;flex-direction:column;position:relative}.md3-navigation-drawer-modal__scrim{inset:0;opacity:0;position:absolute;visibility:hidden;background-color:var(--_scrim-color);transition:opacity .25s cubic-bezier(0.4, 0, 0.2, 1) 0s,visibility 0s cubic-bezier(0.4, 0, 0.2, 1) .25s}.md3-navigation-drawer-modal--scrim-visible{visibility:visible;opacity:var(--_scrim-opacity);transition:opacity .25s cubic-bezier(0.4, 0, 0.2, 1) 0s,visibility 0s cubic-bezier(0.4, 0, 0.2, 1) 0s}
`;Or.styleSheet;
/**
 * @license
 * Copyright 2021 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
let Dr=class extends Ir{};Dr.styles=[Lr,Or],Dr=t([i("md-navigation-drawer-modal")],Dr);const Mr="0 1px 2px 0 rgba(0,0,0,.3), 0 2px 6px 2px rgba(0,0,0,.15)",qr=(e,t,i,s)=>`font-family: var(--md-sys-typescale-${e}-font, Roboto, sans-serif);\n  font-size: var(--md-sys-typescale-${e}-size, ${t}px); line-height: var(--md-sys-typescale-${e}-line-height, ${i}px);\n  font-weight: var(--md-sys-typescale-${e}-weight, ${s}); letter-spacing: var(--md-sys-typescale-${e}-tracking, normal);`;class Br extends ve{static properties={variant:{type:String,reflect:!0},centered:{type:Boolean,reflect:!0},scrolled:{type:Boolean,reflect:!0},autoScroll:{type:Boolean,attribute:"auto-scroll",reflect:!0},collapseOnScroll:{type:Boolean,attribute:"collapse-on-scroll",reflect:!0},_sub:{state:!0}};static styles=c([`\n    :host { display: block; box-sizing: border-box; width: 100%;\n      background: var(--md-sys-color-surface, #fef7ff); color: var(--md-sys-color-on-surface, #1d1b20);\n      transition: background-color 200ms linear, box-shadow 200ms linear; }\n    :host([hidden]) { display: none; }\n    /* on-scroll: surface-container + elevation level2 (토큰 on-scroll-container-color/elevation) */\n    :host([scrolled]) { background: var(--md-sys-color-surface-container, #f3edf7); box-shadow: ${Mr}; }\n    /* --md-top-app-bar-container-height: 행(small) 높이, 기본 64px(M3). medium/large 는 '행 높이 + 확장 영역'이라 함께 따라간다 */\n    :host([variant='medium']) { min-height: calc(var(--md-top-app-bar-container-height, 64px) + 48px); }   /* medium flexible 112 */\n    :host([variant='medium'][has-subtitle]) { min-height: calc(var(--md-top-app-bar-container-height, 64px) + 72px); } /* 136 */\n    :host([variant='large']) { min-height: calc(var(--md-top-app-bar-container-height, 64px) + 56px); }    /* large flexible 120 */\n    :host([variant='large'][has-subtitle]) { min-height: calc(var(--md-top-app-bar-container-height, 64px) + 88px); } /* 152 */\n    .row { display: flex; align-items: center; height: var(--md-top-app-bar-container-height, 64px); padding: 0 4px; box-sizing: border-box; } /* leading/trailing-space 4 */\n    .leading, .trailing { display: flex; align-items: center; flex: none; }\n    .leading { color: var(--md-sys-color-on-surface, #1d1b20); --md-icon-button-icon-color: var(--md-sys-color-on-surface, #1d1b20); }\n    .trailing { margin-inline-start: auto; color: var(--md-sys-color-on-surface-variant, #49454f); --md-icon-button-icon-color: var(--md-sys-color-on-surface-variant, #49454f); }\n    ::slotted([slot='leading']) { color: var(--md-sys-color-on-surface, #1d1b20); }\n    ::slotted([slot='trailing']) { color: var(--md-sys-color-on-surface-variant, #49454f); }\n    .titles { flex: 1; min-width: 0; display: flex; flex-direction: column; justify-content: center; padding-inline: 12px; }\n    .headline { ${qr("title-large",22,28,400)} color: var(--md-sys-color-on-surface, #1d1b20); min-width: 0; }\n    /* 한 줄 말줄임은 텍스트(span)에만 적용한다. 요소(셀렉터·버튼 등)를 넣으면 잘리거나 포커스 링이 가려지지 않는다 */\n    ::slotted(span[slot='headline']) { display: block; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; }\n    .subtitle { ${qr("label-medium",12,16,500)} color: var(--md-sys-color-on-surface-variant, #49454f); }\n    .subtitle:not(.has) { display: none; }\n    :host([centered]) .titles, :host([centered]) .expanded { text-align: center; }\n    .expanded { display: none; padding: 0 16px 12px; }\n    :host([variant='medium']) .expanded, :host([variant='large']) .expanded { display: block; }\n    :host([variant='medium']) .row .titles, :host([variant='large']) .row .titles { visibility: hidden; }\n    /* medium flexible: headline-medium, subtitle label-large. 64 + 36 + 12 = 112, 64 + 36 + 20 + 16 = 136 [파생] */\n    :host([variant='medium']) .expanded { ${qr("headline-medium",28,36,400)} }\n    :host([variant='medium']) .expanded .xsub { ${qr("label-large",14,20,500)} }\n    :host([variant='medium'][has-subtitle]) .expanded { padding-bottom: 16px; }\n    /* large flexible: display-small, subtitle title-medium. 64 + 44 + 12 = 120, 64 + 44 + 24 + 20 = 152 [파생] */\n    :host([variant='large']) .expanded { ${qr("display-small",36,44,400)} }\n    :host([variant='large']) .expanded .xsub { ${qr("title-medium",16,24,500)} }\n    :host([variant='large'][has-subtitle]) .expanded { padding-bottom: 20px; }\n    .xsub { display: block; color: var(--md-sys-color-on-surface-variant, #49454f); }\n    .xsub:not(.has) { display: none; }\n    /* compress: 스크롤되면 flexible 앱 바가 small 로 줄어든다 */\n    :host([collapse-on-scroll][scrolled]) { min-height: 0; }\n    :host([collapse-on-scroll][scrolled]) .expanded { display: none; }\n    :host([collapse-on-scroll][scrolled]) .row .titles { visibility: visible; }\n  `]);constructor(){super(),this.variant="small",this.centered=!1,this.scrolled=!1,this.autoScroll=!1,this.collapseOnScroll=!1,this._sub=!1,this._onScroll=()=>{this.scrolled=window.scrollY>0}}willUpdate(e){e.has("variant")&&"center-aligned"===this.variant&&(this.variant="small",this.centered=!0)}updated(e){e.has("autoScroll")&&(window.removeEventListener("scroll",this._onScroll),this.autoScroll&&(window.addEventListener("scroll",this._onScroll,{passive:!0}),this._onScroll()))}connectedCallback(){super.connectedCallback(),this._checkSub()}disconnectedCallback(){super.disconnectedCallback(),window.removeEventListener("scroll",this._onScroll)}_checkSub(){const e=!!this.querySelector(':scope > [slot="subtitle"], :scope > [slot="expanded-subtitle"]');this._sub=e,this.toggleAttribute("has-subtitle",e)}render(){const e=this._sub?"has":"";return J`
      <div class="row" part="row">
        <div class="leading"><slot name="leading"></slot></div>
        <div class="titles">
          <div class="headline" part="headline"><slot name="headline"></slot></div>
          <div class="subtitle ${e}" part="subtitle"><slot name="subtitle" @slotchange=${()=>this._checkSub()}></slot></div>
        </div>
        <div class="trailing"><slot name="trailing"></slot></div>
      </div>
      <div class="expanded" part="expanded-headline"><slot name="expanded-headline"></slot>
        <span class="xsub ${e}"><slot name="expanded-subtitle" @slotchange=${()=>this._checkSub()}></slot></span></div>`}}customElements.define("md-top-app-bar",Br);class Rr extends ve{static properties={activeIndex:{type:Number,attribute:"active-index",reflect:!0},alignment:{type:String,reflect:!0},expanded:{type:Boolean,reflect:!0},narrow:{type:Boolean,reflect:!0},modal:{type:Boolean,reflect:!0},menuOpenLabel:{type:String,attribute:"menu-open-label"},menuCloseLabel:{type:String,attribute:"menu-close-label"},menu:{type:Boolean,reflect:!0},hideWhenCollapsed:{type:Boolean,attribute:"hide-when-collapsed",reflect:!0},hideCollapsedLabels:{type:Boolean,attribute:"hide-collapsed-labels",reflect:!0},_header:{state:!0},_footer:{state:!0}};static styles=c([`\n    :host { display: flex; flex-direction: column; box-sizing: border-box; flex: none;\n      width: var(--md-navigation-rail-collapsed-width, 96px); height: 100%;                     /* collapsed container-width 96 */\n      padding: var(--md-navigation-rail-top-space, 44px) 0 var(--md-navigation-rail-bottom-space, 20px); /* top-space 44 */\n      background: var(--md-sys-color-surface, #fef7ff); }\n    :host([narrow]) { width: var(--md-navigation-rail-narrow-width, 80px); }\n    :host([hidden]), :host([hide-when-collapsed]:not([expanded])) { display: none; }\n    .header { display: flex; flex-direction: column; align-items: center; gap: 4px; }\n    .header.has { margin-bottom: 40px; }                             /* header-space-minimum 40 */\n    .items { display: flex; flex-direction: column; align-items: stretch; gap: var(--md-navigation-rail-item-space, 4px); flex: 1; } /* item-vertical-space 4 */\n    .footer { display: flex; flex-direction: column; align-items: stretch; gap: var(--md-navigation-rail-item-space, 4px); flex: none; }\n    .footer:not(.has) { display: none; }\n    :host([alignment='center']) .items { justify-content: center; }\n    :host([alignment='bottom']) .items { justify-content: flex-end; }\n    :host([expanded]) { width: auto; min-width: var(--md-navigation-rail-expanded-min-width, 220px); max-width: var(--md-navigation-rail-expanded-max-width, 360px);\n      padding-inline: var(--md-navigation-rail-expanded-padding-inline, 20px); } /* expanded 220~360, Compose WNRItemHorizontalPadding 20 */\n    :host([expanded]) .header { align-items: flex-start; }\n    :host([expanded]) .items, :host([expanded]) .footer { gap: var(--md-navigation-rail-expanded-item-space, 0px); } /* expanded between-item-space 0 */\n    :host([expanded][modal]) { position: fixed; inset-block: 0; inset-inline-start: 0; z-index: 1001; height: auto;\n      background: var(--md-sys-color-surface-container, #f3edf7); box-shadow: ${Mr};\n      border-start-end-radius: 16px; border-end-end-radius: 16px; }  /* modal-container-color/elevation/shape(corner-large) */\n    .scrim { position: fixed; inset: 0; z-index: -1; background: var(--md-sys-color-scrim, #000); opacity: .32; }\n  `]);constructor(){super(),this.activeIndex=0,this.alignment="top",this.expanded=!1,this.narrow=!1,this.modal=!1,this.menu=!1,this.hideWhenCollapsed=!1,this.hideCollapsedLabels=!1,this._header=!1,this._footer=!1,this.addEventListener("navigation-rail-item-interaction",e=>{const t=this.items.indexOf(e.target);t<0||e.target.disabled||(this.activeIndex=t,this.dispatchEvent(new CustomEvent("navigation-rail-activated",{detail:{activeIndex:t,item:e.target},bubbles:!0,composed:!0})),this.modal&&this.expanded&&this.toggle(!1))}),this.addEventListener("keydown",e=>{"Escape"===e.key&&this.modal&&this.expanded&&this.toggle(!1)})}get items(){return Array.from(this.querySelectorAll(":scope > md-navigation-rail-item:not([slot])"))}toggle(e){const t=void 0===e?!this.expanded:!!e;t!==this.expanded&&(this.expanded=t,this.dispatchEvent(new CustomEvent("expanded-changed",{detail:{expanded:t},bubbles:!0,composed:!0})))}updated(){this._sync()}_sync(){this.items.forEach((e,t)=>{e.active=t===this.activeIndex}),this.querySelectorAll(":scope > md-navigation-rail-item").forEach(e=>{e.expanded=this.expanded,e.hideCollapsedLabels=this.hideCollapsedLabels}),this._header=this.menu||!!this.querySelector(':scope > [slot="header"]'),this._footer=!!this.querySelector(':scope > [slot="footer"]')}render(){const e=this.menu?J`<md-icon-button class="menu" part="menu" aria-label=${this.expanded?this.menuCloseLabel||"Close menu":this.menuOpenLabel||"Open menu"}
        aria-expanded=${this.expanded?"true":"false"} @click=${()=>this.toggle()}><md-icon>${this.expanded?"menu_open":"menu"}</md-icon></md-icon-button>`:Q;return J`
      ${this.modal&&this.expanded?J`<div class="scrim" part="scrim" @click=${()=>this.toggle(!1)}></div>`:Q}
      <div class="header ${this._header?"has":""}">${e}<slot name="header" @slotchange=${()=>this._sync()}></slot></div>
      <div class="items" role="tablist" aria-orientation="vertical"><slot @slotchange=${()=>this._sync()}></slot></div>
      <div class="footer ${this._footer?"has":""}" part="footer"><slot name="footer" @slotchange=${()=>this._sync()}></slot></div>`}}customElements.define("md-navigation-rail",Rr);class Nr extends ve{static properties={active:{type:Boolean,reflect:!0},disabled:{type:Boolean,reflect:!0},label:{type:String},badgeValue:{type:String,attribute:"badge-value"},showBadge:{type:Boolean,attribute:"show-badge"},expanded:{type:Boolean,reflect:!0},hideCollapsedLabels:{type:Boolean,attribute:"hide-collapsed-labels",reflect:!0}};static styles=c([`\n    :host { display: block; }\n    button { all: unset; box-sizing: border-box; width: 100%; display: flex; cursor: pointer; position: relative;  /* target 은 레일 전체 너비 */\n      color: var(--md-sys-color-on-surface-variant, #49454f); }\n    :host([disabled]) button { cursor: default; opacity: 0.38; }\n    .indicator { position: relative; display: flex; align-items: center; justify-content: center; box-sizing: border-box; flex: none;\n      border-radius: var(--md-sys-shape-corner-full, 9999px); }\n    .indicator::before { content: ''; position: absolute; inset: 0; border-radius: inherit; background: currentColor; opacity: 0; pointer-events: none; }\n    button:hover .indicator::before { opacity: var(--md-sys-state-hover-state-layer-opacity, .08); }\n    button:active .indicator::before { opacity: var(--md-sys-state-pressed-state-layer-opacity, .1); }\n    button:focus-visible .indicator { outline: 3px solid var(--md-sys-color-secondary, #625b71); outline-offset: 2px; }\n    :host([active]) .indicator { background: var(--md-sys-color-secondary-container, #e8def8); }\n    .icon { position: relative; display: flex; width: 24px; height: 24px; --md-icon-size: 24px; color: var(--md-sys-color-on-surface-variant, #49454f); }\n    :host([active]) .icon { color: var(--md-sys-color-on-secondary-container, #1d192b); }\n    :host(:not([active])) slot[name='active-icon'], :host([active]) slot[name='inactive-icon'].has-active { display: none; }\n    .label { ${qr("label-medium",12,16,500)} text-align: center; overflow-wrap: anywhere; }  /* 라벨은 줄이지 않는다(필요하면 줄바꿈) */\n    /* 세로형(접힌 레일): 높이 64, 위아래 6, indicator 56x32, indicator-라벨 4. 활성 라벨 secondary\n       치수 변수는 md-navigation-rail 에 지정하면 항목까지 상속된다(기본값 = M3 수치) */\n    :host(:not([expanded])) button { flex-direction: column; align-items: center; min-height: var(--md-navigation-rail-item-min-height, 64px);\n      padding: var(--md-navigation-rail-item-padding-block, 6px) 0; gap: var(--md-navigation-rail-item-label-space, 4px); }\n    :host(:not([expanded])) .indicator { width: var(--md-navigation-rail-item-indicator-width, 56px); height: var(--md-navigation-rail-item-indicator-height, 32px); }\n    :host([hide-collapsed-labels]:not([expanded])) .label { display: none; }\n    :host(:not([expanded])[active]) .label { color: var(--md-sys-color-secondary, #625b71); }\n    /* 가로형(펼친 레일): indicator 높이 56, 앞뒤 16, 아이콘-라벨 8, indicator 는 내용에 맞춤. 활성 라벨 on-secondary-container */\n    :host([expanded]) button { align-items: center; }\n    :host([expanded]) .indicator { min-height: var(--md-navigation-rail-item-expanded-indicator-height, 56px); padding: 0 16px; gap: 8px; justify-content: flex-start; }\n    :host([expanded]) .label { text-align: start; }\n    .text { display: flex; flex-direction: column; min-width: 0; }\n    .supporting { ${qr("body-small",12,16,400)} color: var(--md-sys-color-on-surface-variant, #49454f); }\n    .supporting:not(.has), .trailing:not(.has) { display: none; }\n    .trailing { display: flex; align-items: center; flex: none; margin-inline-start: auto; }\n    :host([expanded]) .indicator.wide { width: 100%; }               /* trailing 이 있으면 indicator 를 항목 끝까지 늘린다 */\n    :host([expanded][active]) .label { color: var(--md-sys-color-on-secondary-container, #1d192b); }\n    /* 배지: 접힌 레일은 아이콘 오른쪽 위, 펼친 레일은 라벨 옆 */\n    .badge { box-sizing: border-box; min-width: 16px; height: 16px; padding: 0 4px; border-radius: var(--md-navigation-rail-badge-shape, var(--md-sys-shape-corner-full, 8px));\n      background: var(--md-sys-color-error, #b3261e); color: var(--md-sys-color-on-error, #fff);\n      ${qr("label-small",11,16,500)} text-align: center; }\n    .badge.dot { min-width: 6px; width: 6px; height: 6px; padding: 0; border-radius: var(--md-navigation-rail-badge-small-shape, var(--md-sys-shape-corner-full, 3px)); }\n    .icon .badge { position: absolute; top: -4px; left: 14px; }\n    .icon .badge.dot { top: 0; left: 18px; }\n    .badge.after { flex: none; }\n  `]);constructor(){super(),this.active=!1,this.disabled=!1,this.label="",this.badgeValue="",this.showBadge=!1,this.expanded=!1,this.hideCollapsedLabels=!1}render(){const e=!!this.querySelector(':scope > [slot="active-icon"]'),t=!!this.querySelector(':scope > [slot="supporting-text"]'),i=!!this.querySelector(':scope > [slot="trailing"]'),s=this.hideCollapsedLabels&&!this.expanded,o=e=>J`<span class="badge ${this.badgeValue?"":"dot"} ${e}" part="badge">${this.badgeValue}</span>`,r=J`<span class="icon"><slot name="active-icon"></slot><slot name="inactive-icon" class=${e?"has-active":""}></slot>
      ${this.showBadge&&!this.expanded?o(""):Q}</span>`,n=J`<span class="label" part="label">${this.label}</span>`,a=()=>this.requestUpdate(),l=J`<span class="text">${n}<span class="supporting ${t?"has":""}" part="supporting-text"><slot name="supporting-text" @slotchange=${a}></slot></span></span>`,c=J`<span class="trailing ${i?"has":""}" part="trailing"><slot name="trailing" @slotchange=${a}></slot></span>`;return J`
      <button part="button" role="tab" aria-selected=${this.active?"true":"false"} aria-disabled=${this.disabled?"true":"false"}
              aria-label=${this.label||Q} title=${s&&this.label?this.label:Q} @click=${this._click}>
        ${this.expanded?J`<span class="indicator ${i?"wide":""}" part="indicator">${r}${l}${this.showBadge?o("after"):Q}${c}</span>`:J`<span class="indicator" part="indicator">${r}</span>${n}`}
      </button>`}focus(){this.shadowRoot?.querySelector("button")?.focus()}_click(){this.disabled||this.dispatchEvent(new CustomEvent("navigation-rail-item-interaction",{bubbles:!0,composed:!0}))}}customElements.define("md-navigation-rail-item",Nr);class Ur extends ve{static properties={activeIndex:{type:Number,attribute:"active-index",reflect:!0},hideInactiveLabels:{type:Boolean,attribute:"hide-inactive-labels"},layout:{type:String,reflect:!0},resolvedLayout:{type:String,attribute:"resolved-layout",reflect:!0}};static styles=c`
    :host { display: flex; box-sizing: border-box; width: 100%; height: 64px;  /* 토큰 container-height */
      background: var(--md-sys-color-surface-container, #f3edf7); box-shadow: ${c(["0 1px 2px 0 rgba(0,0,0,.3), 0 2px 6px 2px rgba(0,0,0,.15)"])}; }
    .items { display: flex; flex: 1; min-width: 0; height: 100%; }
    :host([resolved-layout='vertical']) ::slotted(md-navigation-tab) { flex: 1 1 0; }      /* EqualWeight */
    :host([resolved-layout='horizontal']) .items { justify-content: center; gap: 0; }     /* Centered */
  `;constructor(){super(),this.activeIndex=0,this.hideInactiveLabels=!1,this.layout="auto",this.resolvedLayout="vertical",this._ro=new ResizeObserver(()=>this._resolve()),this.addEventListener("navigation-tab-interaction",e=>{const t=this.tabs.indexOf(e.target);t<0||e.target.disabled||(this.activeIndex=t)})}get tabs(){return Array.from(this.querySelectorAll(":scope > md-navigation-tab"))}connectedCallback(){super.connectedCallback(),this._ro.observe(this)}disconnectedCallback(){super.disconnectedCallback(),this._ro.disconnect()}_resolve(){const e="auto"===this.layout?this.getBoundingClientRect().width>=600?"horizontal":"vertical":this.layout;e!==this.resolvedLayout&&(this.resolvedLayout=e),this._sync()}updated(e){e.has("layout")&&this._resolve(),this._sync(),e.has("activeIndex")&&void 0!==e.get("activeIndex")&&this.dispatchEvent(new CustomEvent("navigation-bar-activated",{detail:{tab:this.tabs[this.activeIndex],activeIndex:this.activeIndex},bubbles:!0,composed:!0}))}_sync(){const e=this.tabs,t=e.length;e.forEach((e,t)=>{e.active=t===this.activeIndex,e.layout=this.resolvedLayout,e.hideInactiveLabel=this.hideInactiveLabels});const i="horizontal"===this.resolvedLayout&&t>0&&t<=6?(100-10*(t+3))/2:0;this.style.paddingInline=i>0?i+"%":""}render(){return J`<nav class="items" role="tablist"><slot @slotchange=${()=>this._sync()}></slot></nav>`}}customElements.define("md-navigation-bar",Ur);class jr extends ve{static properties={active:{type:Boolean,reflect:!0},disabled:{type:Boolean,reflect:!0},hideInactiveLabel:{type:Boolean,attribute:"hide-inactive-label"},label:{type:String},badgeValue:{type:String,attribute:"badge-value"},showBadge:{type:Boolean,attribute:"show-badge"},layout:{type:String,reflect:!0}};static styles=c`
    :host { display: block; min-width: 0; -webkit-tap-highlight-color: transparent; }
    button { all: unset; box-sizing: border-box; width: 100%; height: 100%; cursor: pointer; display: flex; position: relative;
      color: var(--md-sys-color-on-surface-variant, #49454f); }
    :host([disabled]) button { cursor: default; opacity: .38; }
    .indicator { position: relative; display: flex; align-items: center; justify-content: center; box-sizing: border-box;
      border-radius: var(--md-sys-shape-corner-full, 9999px); }
    .indicator::before { content: ''; position: absolute; inset: 0; border-radius: inherit; background: var(--md-sys-color-on-secondary-container, #1d192b); opacity: 0; }
    button:hover .indicator::before { opacity: var(--md-sys-state-hover-state-layer-opacity, .08); }
    button:focus-visible .indicator::before, button:active .indicator::before { opacity: var(--md-sys-state-pressed-state-layer-opacity, .1); }
    button:focus-visible .indicator { outline: 3px solid var(--md-sys-color-secondary, #625b71); outline-offset: 2px; }
    :host([active]) .indicator { background: var(--md-sys-color-secondary-container, #e8def8); }
    .icon { position: relative; display: flex; width: 24px; height: 24px; font-size: 24px; color: var(--md-sys-color-on-surface-variant, #49454f);
      --md-icon-size: 24px; }                                                                /* 토큰 item-icon-size */
    :host([active]) .icon { color: var(--md-sys-color-on-secondary-container, #1d192b); }
    /* 가이드: 활성 목적지는 채운(filled) 아이콘, 비활성은 외곽선 아이콘. md-icon 은 font-variation-settings 를 상속한다 */
    .icon { font-variation-settings: 'FILL' 0; }
    :host([active]) .icon { font-variation-settings: 'FILL' 1; }
    :host(:not([active])) slot[name='active-icon'], :host([active]) slot[name='inactive-icon'].has-active { display: none; }
    /* 가이드: 라벨은 말줄임으로 자르지도, 줄바꿈하지도 않는다(짧은 1~2단어 권장) */
    .label { white-space: nowrap; overflow: visible;
      font-family: var(--md-sys-typescale-label-medium-font, Roboto, sans-serif);
      font-size: var(--md-sys-typescale-label-medium-size, 12px);
      line-height: var(--md-sys-typescale-label-medium-line-height, 16px);
      font-weight: var(--md-sys-typescale-label-medium-weight, 500);
      letter-spacing: var(--md-sys-typescale-label-medium-tracking, 0.5px); }
    :host([active]) .label { color: var(--md-sys-color-secondary, #625b71); }               /* 토큰 item-active-label-text-color */
    .label.hidden { visibility: hidden; }
    /* 세로형: 위아래 6(ContainerBetweenSpace), indicator 56x32, indicator-라벨 4 */
    :host(:not([layout='horizontal'])) button { flex-direction: column; align-items: center; padding: 6px 0; gap: 4px; }
    :host(:not([layout='horizontal'])) .indicator { width: 56px; height: 32px; flex: none; }
    /* 가로형: indicator 높이 40, 앞뒤 16, 아이콘-라벨 4, 아이콘과 라벨이 indicator 안에 */
    :host([layout='horizontal']) button { align-items: center; justify-content: center; padding: 0; }
    :host([layout='horizontal']) .indicator { height: 40px; padding: 0 16px; gap: 4px; }
    :host([layout='horizontal'][active]) .label { color: var(--md-sys-color-on-secondary-container, #1d192b); }
    /* 배지: 아이콘 오른쪽 위에 겹침. small 6px / large 16px, error 색 (md-comp-navigation-bar badge 토큰) */
    .badge { position: absolute; top: -2px; left: 14px; box-sizing: border-box; min-width: 16px; height: 16px; padding: 0 4px; border-radius: var(--md-navigation-bar-badge-shape, var(--md-sys-shape-corner-full, 8px));
      background: var(--md-sys-color-error, #b3261e); color: var(--md-sys-color-on-error, #fff);
      font-family: var(--md-sys-typescale-label-small-font, Roboto, sans-serif); font-size: var(--md-sys-typescale-label-small-size, 11px);
      line-height: 16px; text-align: center; }
    .badge.small { min-width: 6px; width: 6px; height: 6px; padding: 0; border-radius: var(--md-navigation-bar-badge-small-shape, var(--md-sys-shape-corner-full, 3px)); top: 0; left: 18px; }
  `;constructor(){super(),this.active=!1,this.disabled=!1,this.hideInactiveLabel=!1,this.label="",this.badgeValue="",this.showBadge=!1,this.layout="vertical"}focus(){this.shadowRoot?.querySelector("button")?.focus()}_click(){this.disabled||this.dispatchEvent(new CustomEvent("navigation-tab-interaction",{detail:{state:this},bubbles:!0,composed:!0}))}render(){const e=!!this.querySelector(':scope > [slot="active-icon"]'),t="horizontal"===this.layout,i=J`<span class="icon"><slot name="active-icon"></slot><slot name="inactive-icon" class=${e?"has-active":""}></slot>${this.showBadge?J`<span class="badge ${this.badgeValue?"":"small"}" part="badge">${this.badgeValue}</span>`:Q}</span>`,s=J`<span class="label ${this.hideInactiveLabel&&!this.active?"hidden":""}" part="label">${this.label}</span>`;return J`<button role="tab" aria-selected=${this.active?"true":"false"} aria-disabled=${this.disabled?"true":"false"}
        aria-label=${this.label||Q} @click=${this._click}>
      ${t?J`<span class="indicator" part="indicator">${i}${s}</span>`:J`<span class="indicator" part="indicator">${i}</span>${s}`}
    </button>`}}customElements.define("md-navigation-tab",jr);const Vr="0 1px 2px 0 rgba(0,0,0,.3), 0 2px 6px 2px rgba(0,0,0,.15)",Hr="0 1px 3px 0 rgba(0,0,0,.3), 0 4px 8px 3px rgba(0,0,0,.15)";let Fr=null;const Xr="undefined"!=typeof HTMLElement&&Object.prototype.hasOwnProperty.call(HTMLElement.prototype,"popover");class Wr extends ve{static properties={variant:{type:String,reflect:!0},position:{type:String,reflect:!0},for:{type:String,reflect:!0},delay:{type:Number},open:{type:Boolean,reflect:!0},persistent:{type:Boolean,reflect:!0},longPressDelay:{type:Number,attribute:"long-press-delay"}};static styles=c`
    :host { position: fixed; z-index: 1000; display: none; pointer-events: none; box-sizing: border-box; min-width: 40px; /* [토큰 외] Compose TooltipMinWidth */
      inset: auto; margin: 0; border: 0; overflow: visible; /* popover(top layer) UA 기본 스타일(inset:0·margin:auto·border) 되돌림 */ }
    :host([open]) { display: block; visibility: visible; } /* visibility:hidden 인 부모 안에 있어도 열린 동안은 보인다 */
    :host([variant='plain']) {
      min-height: 24px; padding: 4px 8px;            /* [토큰 외] m3 스펙 24dp·8dp, Compose TooltipMinHeight/PlainTooltipContentPadding */
      max-width: 200px;                               /* [토큰 외] Compose plainTooltipMaxWidth */
      border-radius: var(--md-sys-shape-corner-extra-small, 4px);
      background: var(--md-sys-color-inverse-surface, #322f35);
      color: var(--md-sys-color-inverse-on-surface, #f5eff7);
      font-family: var(--md-sys-typescale-body-small-font, Roboto, sans-serif);
      font-size: var(--md-sys-typescale-body-small-size, 12px);
      line-height: var(--md-sys-typescale-body-small-line-height, 16px);
      font-weight: var(--md-sys-typescale-body-small-weight, 400);
      letter-spacing: var(--md-sys-typescale-body-small-tracking, 0.4px);
    }
    :host([variant='rich']) {
      max-width: 320px; padding: 12px 16px 8px;       /* [토큰 외] Compose richTooltipMaxWidth, m3 스펙 패딩 12/16/8 */
      border-radius: var(--md-sys-shape-corner-medium, 12px);
      background: var(--md-sys-color-surface-container, #f3edf7);
      color: var(--md-sys-color-on-surface-variant, #49454f);
      box-shadow: ${c([Vr])};
      font-family: var(--md-sys-typescale-body-medium-font, Roboto, sans-serif);
      font-size: var(--md-sys-typescale-body-medium-size, 14px);
      line-height: var(--md-sys-typescale-body-medium-line-height, 20px);
      font-weight: var(--md-sys-typescale-body-medium-weight, 400);
      letter-spacing: var(--md-sys-typescale-body-medium-tracking, 0.25px);
    }
    :host([variant='rich'][persistent]) { pointer-events: auto; }
    .subhead { display: block; margin-bottom: 4px;
      color: var(--md-sys-color-on-surface-variant, #49454f);
      font-family: var(--md-sys-typescale-title-small-font, Roboto, sans-serif);
      font-size: var(--md-sys-typescale-title-small-size, 14px);
      line-height: var(--md-sys-typescale-title-small-line-height, 20px);
      font-weight: var(--md-sys-typescale-title-small-weight, 500);
      letter-spacing: var(--md-sys-typescale-title-small-tracking, 0.1px); }
    :host([variant='plain']) .subhead, :host([variant='plain']) .actions { display: none; }
    .actions { display: flex; gap: 8px; margin-top: 4px; margin-inline-start: -12px;
      --md-text-button-label-text-color: var(--md-sys-color-primary, #6750a4); }
    .subhead:not(.has), .actions:not(.has) { display: none; }
  `;constructor(){super();const e={};for(const t of["anchor","text"])Object.prototype.hasOwnProperty.call(this,t)&&(e[t]=this[t],delete this[t]);this.variant="plain",this.position="",this.delay=500,this.open=!1,this.persistent=!1,this.longPressDelay=500,this._target=null,this._anchor=null,this._timer=0,this._hide=0,this._press=0,this._pressAt=null,this._longPressed=!1,this._lastTouch=-1/0,this._pending=e,this._enter=e=>{e&&"touch"===e.pointerType||e&&"focusin"===e.type&&performance.now()-this._lastTouch<1e3||this._schedule(!0)},this._leave=e=>{e&&"touch"===e.pointerType||this._schedule(!1)},this._key=e=>{"Escape"===e.key&&this.hide()},this._click=()=>{this._isPersistent()&&(this.open?this.hide():this.show())},this._outside=e=>{const t=e.composedPath?e.composedPath():[];t.includes(this)||this._anchor&&t.includes(this._anchor)||this.hide()},this._down=e=>{"touch"!==e.pointerType||this._isPersistent()||(this._lastTouch=performance.now(),this._cancelPress(),this._pressAt={x:e.clientX,y:e.clientY},this._press=setTimeout(()=>{this._press=0,this._longPressed=!0,clearTimeout(this._timer),clearTimeout(this._hide),this.show()},this.longPressDelay))},this._move=e=>{"touch"===e.pointerType&&this._pressAt&&!this._longPressed&&Math.hypot(e.clientX-this._pressAt.x,e.clientY-this._pressAt.y)>10&&this._cancelPress()},this._up=e=>{if("touch"!==e.pointerType)return;this._lastTouch=performance.now();const t=this._longPressed;this._cancelPress(),t&&("pointerup"===e.type&&this._swallowClick(),clearTimeout(this._hide),this._hide=setTimeout(()=>this.hide(),1500))},this._ctx=e=>{(this._pressAt||this._longPressed)&&e.preventDefault()}}get anchor(){return this._target}set anchor(e){this._target=e||null,this.isConnected&&this._bind(this._target)}get text(){return[...this.childNodes].filter(e=>e.nodeType===Node.TEXT_NODE).map(e=>e.data).join("")}set text(e){this._setText(e)}_setText(e){[...this.childNodes].filter(e=>e.nodeType===Node.TEXT_NODE).forEach(e=>e.remove()),null!=e&&""!==e&&this.appendChild(document.createTextNode(String(e))),this.open&&this.updateComplete.then(()=>this._place())}connectedCallback(){super.connectedCallback();const e=this._pending;this._pending=null,e&&("text"in e&&this._setText(e.text),"anchor"in e&&(this._target=e.anchor||null)),Xr&&!this.hasAttribute("popover")&&this.setAttribute("popover","manual"),!this._target&&this.for&&(this._target=this._lookup()),this._bind(this._target)}disconnectedCallback(){super.disconnectedCallback(),this.hide(),this._cancelPress(),this._bind(null),Fr===this&&(Fr=null)}updated(e){e.has("for")&&this.isConnected&&this.for&&(this._target=this._lookup(),this._bind(this._target))}_lookup(){return this.getRootNode().getElementById?.(this.for)??document.getElementById(this.for)}_listeners(){return[["pointerenter",this._enter],["focusin",this._enter],["pointerleave",this._leave],["focusout",this._leave],["keydown",this._key],["click",this._click],["pointerdown",this._down],["pointermove",this._move],["pointerup",this._up],["pointercancel",this._up],["contextmenu",this._ctx]]}_bind(e){const t=this._anchor;t&&this._listeners().forEach(([e,i])=>t.removeEventListener(e,i)),this._anchor=e||null,e&&(this._listeners().forEach(([t,i])=>e.addEventListener(t,i)),"plain"===this.variant&&!e.hasAttribute("aria-describedby")&&this.id&&e.setAttribute("aria-describedby",this.id))}_cancelPress(){clearTimeout(this._press),this._press=0,this._pressAt=null,this._longPressed=!1}_swallowClick(){const e=this._anchor;let t=0;const i=()=>{clearTimeout(t),window.removeEventListener("click",s,!0)},s=t=>{e&&t.composedPath().includes(e)&&(t.preventDefault(),t.stopImmediatePropagation()),i()};window.addEventListener("click",s,!0),t=setTimeout(i,800)}_isPersistent(){return"rich"===this.variant&&this.persistent}_schedule(e){this._isPersistent()||(clearTimeout(this._timer),clearTimeout(this._hide),e?this._timer=setTimeout(()=>this.show(),this.delay):this._hide=setTimeout(()=>this.hide(),1500))}show(e=0){if(this.isConnected&&(!this._anchor||this._anchor.isConnected)){if(Fr&&Fr!==this&&Fr.hide(),Fr=this,this.setAttribute("role","tooltip"),this.open=!0,Xr&&this.popover&&!this.matches(":popover-open"))try{this.showPopover()}catch(e){}this._isPersistent()&&setTimeout(()=>{this.open&&(document.addEventListener("pointerdown",this._outside,!0),document.addEventListener("focusin",this._outside,!0))}),this.updateComplete.then(()=>this._place()),this.dispatchEvent(new Event("opened",{bubbles:!0,composed:!0})),e>0&&(clearTimeout(this._hide),this._hide=setTimeout(()=>this.hide(),e))}}hide(){if(clearTimeout(this._timer),clearTimeout(this._hide),document.removeEventListener("pointerdown",this._outside,!0),document.removeEventListener("focusin",this._outside,!0),Xr&&this.popover)try{this.matches(":popover-open")&&this.hidePopover()}catch(e){}this.open&&(this.open=!1,Fr===this&&(Fr=null),this.dispatchEvent(new Event("closed",{bubbles:!0,composed:!0})))}_place(){if(!this._anchor)return;const e=this._anchor.getBoundingClientRect(),t=this.getBoundingClientRect(),i="rtl"===getComputedStyle(this).direction;let s,o,r=this.position||("rich"===this.variant?"bottom-end":"top");"start"===r?r=i?"right":"left":"end"===r&&(r=i?"left":"right"),"bottom-end"===r?(s=i?e.left+e.width/2-t.width:e.left+e.width/2,o=e.bottom+4):"bottom"===r?(s=e.left+e.width/2-t.width/2,o=e.bottom+4):"left"===r?(s=e.left-t.width-4,o=e.top+e.height/2-t.height/2):"right"===r?(s=e.right+4,o=e.top+e.height/2-t.height/2):(s=e.left+e.width/2-t.width/2,o=e.top-t.height-4,o<0&&(o=e.bottom+4)),s=Math.max(8,Math.min(s,innerWidth-t.width-8)),this.style.left=s+"px",this.style.top=o+"px"}render(){const e=!!this.querySelector(':scope > [slot="subhead"]'),t=!!this.querySelector(':scope > [slot="actions"]');return J`<slot name="subhead" class="subhead ${e?"has":""}" @slotchange=${()=>this.requestUpdate()}></slot><slot></slot><div class="actions ${t?"has":""}"><slot name="actions" @slotchange=${()=>this.requestUpdate()}></slot></div>`}}customElements.get("md-tooltip")||customElements.define("md-tooltip",Wr);let Gr=null;class Yr extends ve{static properties={open:{type:Boolean,reflect:!0},action:{type:String},closeable:{type:Boolean,reflect:!0},closeLabel:{type:String,attribute:"close-label"},timeout:{type:Number},align:{type:String,reflect:!0},multiline:{type:Boolean,reflect:!0},longAction:{type:Boolean,attribute:"long-action",reflect:!0}};static styles=c`
    :host { position: fixed; z-index: 1001; left: 50%; bottom: 8px; transform: translateX(-50%); /* [토큰 외] MDC-Android m3_snackbar_margin 8dp */
      display: none; box-sizing: border-box; width: max-content; max-width: min(600px, calc(100vw - 16px)); /* [토큰 외] Compose ContainerMaxWidth 600dp, 좌우 여백 m3_snackbar_margin 8dp */ }
    :host([align='start']) { left: 8px; transform: none; }
    :host([open]) { display: block; }
    .container { display: flex; align-items: center; flex-wrap: wrap; box-sizing: border-box;
      min-height: 48px; padding: 0 8px 0 16px; gap: 0 4px;
      border-radius: var(--md-sys-shape-corner-extra-small, 4px);
      background: var(--md-sys-color-inverse-surface, #322f35);
      color: var(--md-sys-color-inverse-on-surface, #f5eff7);
      box-shadow: ${c([Hr])}; }
    :host([multiline]) .container { min-height: 68px; }
    .text { flex: 1; min-width: 0; padding: 14px 8px 14px 0;
      font-family: var(--md-sys-typescale-body-medium-font, Roboto, sans-serif);
      font-size: var(--md-sys-typescale-body-medium-size, 14px);
      line-height: var(--md-sys-typescale-body-medium-line-height, 20px);
      font-weight: var(--md-sys-typescale-body-medium-weight, 400);
      letter-spacing: var(--md-sys-typescale-body-medium-tracking, 0.25px); }
    :host([long-action]) .text { flex-basis: 100%; padding-bottom: 0; }
    :host([long-action]) .trail { margin-inline-start: auto; }
    .trail { display: flex; align-items: center; }
    button { all: unset; box-sizing: border-box; cursor: pointer; display: inline-flex; align-items: center; justify-content: center; position: relative; }
    button::before { content: ''; position: absolute; inset: 0; border-radius: inherit; background: currentColor; opacity: 0; }
    button:hover::before { opacity: var(--md-sys-state-hover-state-layer-opacity, .08); }
    button:focus-visible::before { opacity: var(--md-sys-state-focus-state-layer-opacity, .1); }
    button:active::before { opacity: var(--md-sys-state-pressed-state-layer-opacity, .1); }
    button:focus-visible { outline: 3px solid var(--md-sys-color-inverse-primary, #d0bcff); outline-offset: 2px; }
    .action { height: 40px; padding: 0 12px; border-radius: var(--md-snackbar-action-shape, var(--md-sys-shape-corner-full, 20px));
      color: var(--md-sys-color-inverse-primary, #d0bcff);
      font-family: var(--md-sys-typescale-label-large-font, Roboto, sans-serif);
      font-size: var(--md-sys-typescale-label-large-size, 14px);
      line-height: var(--md-sys-typescale-label-large-line-height, 20px);
      font-weight: var(--md-sys-typescale-label-large-weight, 500);
      letter-spacing: var(--md-sys-typescale-label-large-tracking, 0.1px); }
    .close { width: 40px; height: 40px; border-radius: var(--md-snackbar-close-shape, var(--md-sys-shape-corner-full, 20px)); color: var(--md-sys-color-inverse-on-surface, #f5eff7); }
    .close svg { width: 24px; height: 24px; fill: currentColor; }
  `;constructor(){super(),this.open=!1,this.action="",this.closeable=!1,this.timeout=-1,this.align="center",this.multiline=!1,this.longAction=!1,this._t=0}show(){Gr&&Gr!==this&&Gr.close("replaced"),Gr=this,this.setAttribute("role","status"),this.setAttribute("aria-live","polite"),this.open=!0,clearTimeout(this._t);const e=this.timeout<0?this.action?0:4e3:this.timeout;e>0&&(this._t=setTimeout(()=>this.close("timeout"),e)),this.dispatchEvent(new Event("opened",{bubbles:!0,composed:!0}))}close(e="dismiss"){clearTimeout(this._t),this.open&&(this.open=!1,Gr===this&&(Gr=null),this.dispatchEvent(new CustomEvent("closed",{detail:{reason:e},bubbles:!0,composed:!0})))}_action(){this.dispatchEvent(new Event("action",{bubbles:!0,composed:!0})),this.close("action")}render(){return J`<div class="container" part="container">
      <div class="text"><slot></slot></div>
      <div class="trail">
        ${this.action?J`<button class="action" part="action" @click=${this._action}>${this.action}</button>`:""}
        ${this.closeable?J`<button class="close" part="close" aria-label=${this.closeLabel||"Close"} @click=${()=>this.close("dismiss")}><svg viewBox="0 0 24 24"><path d="M19 6.41 17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z"/></svg></button>`:""}
      </div></div>`}}customElements.get("md-snackbar")||customElements.define("md-snackbar",Yr);const Kr={info:["primary","on-primary","#6750a4","#ffffff"],success:["tertiary","on-tertiary","#7d5260","#ffffff"],warning:["error-container","on-error-container","#f9dedc","#410e0b"],error:["error","on-error","#b3261e","#ffffff"]};let Jr=null;{const e=new CSSStyleSheet;e.replaceSync(".md-toast-stack { position: fixed; z-index: var(--md-toast-z-index, 10002);\n      top: var(--md-toast-top, 60px); right: var(--md-toast-right, 20px); left: auto;\n      display: flex; flex-direction: column; align-items: flex-end; gap: 8px;\n      max-width: min(400px, calc(100vw - 16px)); pointer-events: none; }\n    .md-toast-stack > md-toast { pointer-events: auto; }\n    @media (max-width: 599px) { .md-toast-stack { left: 8px; right: 8px; max-width: none; align-items: stretch; } }"),document.adoptedStyleSheets=[...document.adoptedStyleSheets,e]}class Zr extends ve{static properties={open:{type:Boolean,reflect:!0},level:{type:String,reflect:!0},action:{type:String},closeable:{type:Boolean,reflect:!0},closeLabel:{type:String,attribute:"close-label"},timeout:{type:Number},max:{type:Number},leaving:{type:Boolean,reflect:!0}};static styles=c`
    :host { display: none; box-sizing: border-box; max-width: 100%; }
    :host([open]) { display: block; animation: md-toast-in 300ms ease; }
    :host([leaving]) { animation: md-toast-out ${l(300)}ms ease forwards; }
    @keyframes md-toast-in { from { opacity: 0; transform: translateX(20px); } to { opacity: 1; transform: translateX(0); } }
    @keyframes md-toast-out { from { opacity: 1; } to { opacity: 0; transform: translateY(-10px); } }
    @media (prefers-reduced-motion: reduce) { :host([open]), :host([leaving]) { animation: none; } }
    .container { display: flex; align-items: center; box-sizing: border-box; min-height: 48px;
      padding: 0 4px 0 16px; gap: 4px;
      border-radius: var(--md-sys-shape-corner-small, 8px);   /* handbook 8px = M3 corner small */
      box-shadow: ${c([Vr])};                    /* handbook elevation level2 */
      background: var(--_bg); color: var(--_fg); }
    ${l(Object.entries(Kr).map(([e,[t,i,s,o]])=>`:host([level='${e}']) .container { --_bg: var(--md-sys-color-${t}, ${s}); --_fg: var(--md-sys-color-${i}, ${o}); }`).join("\n"))}
    .text { flex: 1; min-width: 0; padding: 12px 0; overflow-wrap: anywhere;
      font-family: var(--md-sys-typescale-body-medium-font, Roboto, sans-serif);
      font-size: var(--md-sys-typescale-body-medium-size, 14px);
      line-height: var(--md-sys-typescale-body-medium-line-height, 20px);
      font-weight: var(--md-sys-typescale-body-medium-weight, 400);
      letter-spacing: var(--md-sys-typescale-body-medium-tracking, 0.25px); }
    :host(:not([closeable])) .container { padding-inline-end: 8px; }
    button { all: unset; box-sizing: border-box; cursor: pointer; display: inline-flex; align-items: center; justify-content: center; position: relative; flex: none; color: inherit; }
    button::before { content: ''; position: absolute; inset: 0; border-radius: inherit; background: currentColor; opacity: 0; }
    button:hover::before { opacity: var(--md-sys-state-hover-state-layer-opacity, .08); }
    button:focus-visible::before { opacity: var(--md-sys-state-focus-state-layer-opacity, .1); }
    button:active::before { opacity: var(--md-sys-state-pressed-state-layer-opacity, .1); }
    button:focus-visible { outline: 3px solid currentColor; outline-offset: -3px; }
    .action { height: 40px; padding: 0 12px; border-radius: var(--md-toast-action-shape, var(--md-sys-shape-corner-full, 20px));
      font-family: var(--md-sys-typescale-label-large-font, Roboto, sans-serif);
      font-size: var(--md-sys-typescale-label-large-size, 14px);
      line-height: var(--md-sys-typescale-label-large-line-height, 20px);
      font-weight: var(--md-sys-typescale-label-large-weight, 500);
      letter-spacing: var(--md-sys-typescale-label-large-tracking, 0.1px); }
    .close { width: 40px; height: 40px; border-radius: var(--md-toast-close-shape, var(--md-sys-shape-corner-full, 20px)); }
    .close svg { width: 20px; height: 20px; fill: currentColor; }
  `;constructor(){super(),this.open=!1,this.level="info",this.action="",this.closeable=!0,this.timeout=-1,this.max=5,this.leaving=!1,this._t=0,this._rm=0,this._left=0,this._started=0,this._paused=!1,this._pause=()=>{this.open&&!this.leaving&&this._t&&(clearTimeout(this._t),this._t=0,this._left-=Date.now()-this._started,this._paused=!0)},this._resume=()=>{this._paused&&this.open&&!this.leaving&&(this._paused=!1,this._arm(Math.max(this._left,1e3)))}}connectedCallback(){super.connectedCallback(),["pointerenter","focusin"].forEach(e=>this.addEventListener(e,this._pause)),["pointerleave","focusout"].forEach(e=>this.addEventListener(e,this._resume))}updated(e){if(e.has("level")){for(const e of Object.keys(Kr))this.classList.toggle("ui-toast-"+e,e===this.level);const e="warning"===this.level||"error"===this.level;this.setAttribute("role",e?"alert":"status"),this.setAttribute("aria-live",e?"assertive":"polite")}}get effectiveTimeout(){return this.timeout>=0?this.timeout:"warning"===this.level||"error"===this.level?0:3e3}_arm(e){clearTimeout(this._t),this._t=0,e>0&&(this._left=e,this._started=Date.now(),this._t=setTimeout(()=>this.close("timeout"),e))}show(){clearTimeout(this._rm),this.classList.add("ui-toast");const e=(Jr&&Jr.isConnected||(Jr=document.createElement("div"),Jr.className="md-toast-stack",document.body.appendChild(Jr)),Jr);this.parentNode!==e&&e.appendChild(this),this.leaving=!1,this._paused=!1,this.open=!0,this._arm(this.effectiveTimeout);const t=[...e.children].filter(e=>"md-toast"===e.localName&&e.open&&!e.leaving);for(let e=0;e<t.length-Math.max(1,this.max);e++)t[e].close("overflow");this.dispatchEvent(new Event("opened",{bubbles:!0,composed:!0}))}close(e="dismiss"){clearTimeout(this._t),this._t=0,this.open&&!this.leaving&&(this.leaving=!0,this.dispatchEvent(new CustomEvent("closed",{detail:{reason:e},bubbles:!0,composed:!0})),this._rm=setTimeout(()=>{this.open=!1,this.leaving=!1,this.remove()},300))}_action(){this.dispatchEvent(new Event("action",{bubbles:!0,composed:!0})),this.close("action")}render(){return J`<div class="container" part="container">
      <div class="text" part="text"><slot></slot></div>
      ${this.action?J`<button class="action" part="action" @click=${this._action}>${this.action}</button>`:""}
      ${this.closeable?J`<button class="close" part="close" aria-label=${this.closeLabel||"Close"} @click=${()=>this.close("dismiss")}><svg viewBox="0 0 24 24"><path d="M19 6.41 17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z"/></svg></button>`:""}
    </div>`}}customElements.get("md-toast")||customElements.define("md-toast",Zr);const Qr={xsmall:{h:32,std:18,inner:4,pressedInner:4,pressed:8,selected:12},small:{h:40,std:12,inner:8,pressedInner:4,pressed:8,selected:12},medium:{h:56,std:8,inner:8,pressedInner:4,pressed:12,selected:16},large:{h:96,std:8,inner:16,pressedInner:12,pressed:16,selected:28},xlarge:{h:136,std:8,inner:20,pressedInner:16,pressed:16,selected:28}},en=["elevated-button","filled-button","filled-tonal-button","outlined-button","text-button","icon-button","filled-icon-button","filled-tonal-icon-button","outlined-icon-button"],tn={"MD-FILLED-BUTTON":["filled-button","surface-container","on-surface-variant","primary","on-primary"],"MD-FILLED-ICON-BUTTON":["filled-icon-button","surface-container","on-surface-variant","primary","on-primary"],"MD-FILLED-TONAL-BUTTON":["filled-tonal-button","secondary-container","on-secondary-container","secondary","on-secondary"],"MD-FILLED-TONAL-ICON-BUTTON":["filled-tonal-icon-button","secondary-container","on-secondary-container","secondary","on-secondary"],"MD-OUTLINED-BUTTON":["outlined-button",null,"on-surface-variant","inverse-surface","inverse-on-surface"],"MD-OUTLINED-ICON-BUTTON":["outlined-icon-button",null,"on-surface-variant","inverse-surface","inverse-on-surface"]},sn={"surface-container":"#f3edf7","on-surface-variant":"#49454f",primary:"#6750a4","on-primary":"#ffffff","secondary-container":"#e8def8","on-secondary-container":"#1d192b",secondary:"#625b71","on-secondary":"#ffffff","inverse-surface":"#322f35","inverse-on-surface":"#f5eff7"},on=e=>`var(--md-sys-color-${e}, ${sn[e]})`,rn="224ms linear(0, 0.1, 0.296, 0.494, 0.659, 0.783, 0.869, 0.925, 0.96, 0.98, 0.992, 0.998, 1, 1.001, 1.002, 1.001, 1)",nn=["border-start-start-radius","border-start-end-radius","border-end-start-radius","border-end-end-radius"],an=(e,t)=>e.map(e=>`${e} ${t}`).join(", "),ln=new CSSStyleSheet;ln.replaceSync(`\n  :host { border-start-start-radius: var(--_container-shape-start-start); border-start-end-radius: var(--_container-shape-start-end);\n    border-end-start-radius: var(--_container-shape-end-start); border-end-end-radius: var(--_container-shape-end-end); }\n  .icon-button, md-ripple { transition: ${an(nn,rn)}; }\n  .outline { transition: ${an(nn,rn)}, border-color 231ms linear(0, 0.115, 0.321, 0.517, 0.671, 0.783, 0.86, 0.911, 0.945, 0.966, 0.979, 0.987, 0.992, 0.995, 0.997, 0.998, 1); }\n  @media (prefers-reduced-motion: reduce) { .icon-button, md-ripple, .outline { transition: none; } }\n`);class cn extends ve{static properties={variant:{type:String,reflect:!0},size:{type:String,reflect:!0},selection:{type:String,reflect:!0}};static styles=c`
    :host { display: inline-flex; flex-wrap: nowrap; align-items: center; } /* 그룹은 줄바꿈하지 않는다 */
    :host([variant='connected']) { display: flex; }
    :host([variant='connected']) ::slotted(*) { flex: 1 1 0; }
    ::slotted(*) { flex: none; transition: ${l(an([...nn,"width","flex-basis"],rn))}; }
    @media (prefers-reduced-motion: reduce) { ::slotted(*) { transition: none; } }
  `;constructor(){super(),this.variant="standard",this.size="small",this.addEventListener("pointerdown",e=>this._press(e,!0)),["pointerup","pointercancel","pointerleave"].forEach(e=>this.addEventListener(e,e=>this._press(e,!1))),this.addEventListener("click",e=>this._click(e))}get mode(){return this.selection||("connected"===this.variant?"single":"none")}get buttons(){return Array.from(this.children)}get selectedIndices(){return this.buttons.map((e,t)=>e.hasAttribute("selected")?t:-1).filter(e=>e>=0)}_item(e){return this.buttons.find(t=>t===e.target||t.contains(e.target))}_press(e,t){const i=t?this._item(e):this._pressed;if(i)if(clearTimeout(this._releaseTimer),clearTimeout(this._settleTimer),t)"connected"!==this.variant&&(this._widths||(this._widths=this.buttons.map(e=>e.getBoundingClientRect().width)),this.buttons.forEach(e=>{e.style.width=e.getBoundingClientRect().width+"px"}),this.offsetWidth),this._pressed&&this._pressed!==i&&this._pressed.removeAttribute("data-pressed"),this._pressed=i,this._pressedAt=performance.now(),i.setAttribute("data-pressed",""),this._layout();else{const e=Math.max(0,224-(performance.now()-this._pressedAt));this._releaseTimer=setTimeout(()=>this._release(i),e)}}_release(e){e.removeAttribute("data-pressed"),this._pressed=null,this._layout(),this._settleTimer=setTimeout(()=>{this._pressed||(this._widths=null,this._layout())},244)}_click(e){const t=this.mode;if("none"===t)return;const i=this._item(e);if(!i||i.disabled)return;const s=!i.hasAttribute("selected");if("single"===t){if(!s)return;this.buttons.forEach(e=>this._select(e,e===i))}else this._select(i,s);this._layout(),this.dispatchEvent(new CustomEvent("selection-changed",{detail:{selected:this.selectedIndices},bubbles:!0,composed:!0}))}_pressedAria(e,t){"ariaPressed"in e?e.ariaPressed=t:e.setAttribute("aria-pressed",t)}_select(e,t){t?e.setAttribute("selected",""):e.removeAttribute("selected"),this._pressedAria(e,t?"true":"false")}updated(){this._layout()}_morph(e){const t=e.shadowRoot;t?t.adoptedStyleSheets.includes(ln)||(t.adoptedStyleSheets=[...t.adoptedStyleSheets,ln]):e.localName.includes("-")&&customElements.whenDefined(e.localName).then(()=>Promise.resolve(e.updateComplete).then(()=>e.shadowRoot&&this._morph(e)))}_shape(e,t,i,s,o){en.forEach(r=>{e.style.setProperty(`--md-${r}-container-shape-start-start`,t),e.style.setProperty(`--md-${r}-container-shape-end-start`,i),e.style.setProperty(`--md-${r}-container-shape-start-end`,s),e.style.setProperty(`--md-${r}-container-shape-end-end`,o)})}_color(e,t){const i=tn[e.tagName];if(!i)return;const[s,o,r,n,a]=i,l=t&&e.hasAttribute("selected"),c=null===o;if(!t)return["container-color","label-text-color","icon-color","outline-color"].forEach(t=>e.style.removeProperty(`--md-${s}-${t}`)),void(c&&e.style.removeProperty("background-color"));const d=on(l?a:r);e.style.setProperty(`--md-${s}-label-text-color`,d),e.style.setProperty(`--md-${s}-icon-color`,d),c?(e.style.setProperty("background-color",l?on(n):"transparent"),e.style.setProperty(`--md-${s}-outline-color`,l?"transparent":""),l||e.style.removeProperty(`--md-${s}-outline-color`)):e.style.setProperty(`--md-${s}-container-color`,on(l?n:o))}_layout(){const e=Qr[this.size]||Qr.small,t=this.buttons,i="connected"===this.variant,s="none"!==this.mode;this.style.gap=(i?2:e.std)+"px",this.setAttribute("role",s?"toolbar":"group");const o=e.h/2+"px",r=t.findIndex(e=>e.hasAttribute("data-pressed"));t.forEach((n,a)=>{en.forEach(t=>n.style.setProperty(`--md-${t}-container-height`,e.h+"px")),this._morph(n),s&&!n.hasAttribute("selected")&&"false"!==n.ariaPressed&&this._pressedAria(n,"false"),this._color(n,s);const l=a===r,c=s&&n.hasAttribute("selected");if(i){const i=c?o:(l?e.pressedInner:e.inner)+"px",s=0===a?o:i,r=a===t.length-1?o:i;this._shape(n,s,s,r,r),n.style.width="",n.style.minWidth="xsmall"===this.size||"small"===this.size?"48px":""}else{const i=l?e.pressed:c?e.selected:null,s=null===i?o:i+"px";if(this._shape(n,s,s,s,s),n.style.minWidth="0",this._widths&&this._widths.length===t.length){const e=this._widths,i=r>=0?.15*e[r]:0,s=[r-1,r+1].filter(e=>r>=0&&e>=0&&e<t.length),o=a===r?e[a]+i:s.includes(a)?e[a]-i/s.length:e[a];n.style.width=o+"px"}else n.style.width=""}})}render(){return J`<slot @slotchange=${()=>this._layout()}></slot>`}}customElements.get("md-button-group")||customElements.define("md-button-group",cn);const dn="0 1px 2px 0 rgba(0,0,0,.3), 0 1px 3px 1px rgba(0,0,0,.15)";class hn extends ve{static properties={modal:{type:Boolean,reflect:!0},open:{type:Boolean,reflect:!0},pivot:{type:String,reflect:!0},detached:{type:Boolean,reflect:!0},divider:{type:Boolean,reflect:!0},back:{type:Boolean,reflect:!0},headline:{type:String},closeLabel:{type:String,attribute:"close-label"},backLabel:{type:String,attribute:"back-label"}};static styles=c`
    /* 열림/닫힘 전환: 열 때 400ms emphasized decelerate, 닫을 때 200ms emphasized accelerate
       (M3 motion easing.emphasized.decelerate/accelerate, duration medium4/short4 — 시트에 적용한 조합은 [파생]) */
    :host { display: none; box-sizing: border-box; --_width: var(--md-side-sheet-width, 256px);
      --_enter: 400ms cubic-bezier(0.05, 0.7, 0.1, 1); --_exit: 200ms cubic-bezier(0.3, 0, 0.8, 0.15);
      transition: display var(--_exit) allow-discrete; }
    :host([open]) { display: block; transition: display var(--_enter) allow-discrete; }
    /* standard: 너비가 0 에서 펼쳐지며 옆 본문을 민다(닫을 때는 반대) */
    :host(:not([modal])) { height: 100%; flex: none; overflow: hidden; interpolate-size: allow-keywords; width: 0;
      transition: display var(--_exit) allow-discrete, width var(--_exit); }
    :host([open]:not([modal])) { width: auto; transition: display var(--_enter) allow-discrete, width var(--_enter); }
    @starting-style { :host([open]:not([modal])) { width: 0; } }
    :host([modal]) { position: fixed; inset: 0; z-index: 1002; --_hide: calc(100% + 16px) 0; }
    :host([modal][pivot='start']), :host([modal]:dir(rtl)) { --_hide: calc(-100% - 16px) 0; }
    :host([modal][pivot='start']:dir(rtl)) { --_hide: calc(100% + 16px) 0; }
    :host([modal]:not([open])) { pointer-events: none; }
    .scrim { position: absolute; inset: 0; background: var(--md-sys-color-scrim, #000); opacity: 0; transition: opacity var(--_exit); }
    :host([open]) .scrim { opacity: 0.32; transition: opacity var(--_enter); }
    @starting-style { :host([open]) .scrim { opacity: 0; } }
    .container {
      box-sizing: border-box; display: flex; flex-direction: column;
      width: var(--_width); max-width: min(400px, 100%); height: 100%;   /* 토큰 256px, [스펙] max-width 400dp */
      background: var(--md-sys-color-surface, #fef7ff); color: var(--md-sys-color-on-surface-variant, #49454f);
      border-radius: var(--md-side-sheet-standard-container-shape, var(--md-sys-shape-corner-none, 0px)); box-shadow: none;
    }
    :host(:not([modal])[divider]) .container { border-inline-start: 1px solid var(--md-sys-color-outline, #79747e); }
    :host(:not([modal])[divider][pivot='start']) .container { border-inline-start: none; border-inline-end: 1px solid var(--md-sys-color-outline, #79747e); }
    :host([modal]) .container {
      position: absolute; top: 0; bottom: 0; inset-inline-end: 0; height: auto;
      background: var(--md-sys-color-surface-container-low, #f7f2fa); box-shadow: ${c([dn])};
      border-start-start-radius: var(--md-sys-shape-corner-large, 16px); border-end-start-radius: var(--md-sys-shape-corner-large, 16px);
    }
    :host([modal][pivot='start']) .container { inset-inline-end: auto; inset-inline-start: 0;
      border-start-start-radius: 0; border-end-start-radius: 0;
      border-start-end-radius: var(--md-sys-shape-corner-large, 16px); border-end-end-radius: var(--md-sys-shape-corner-large, 16px); }
    :host([detached]) .container { border-radius: var(--md-sys-shape-corner-large, 16px); }
    :host([detached]:not([modal])) { padding: 16px; }                      /* [스펙] 16dp, MDC-Android m3_side_sheet_margin_detached */
    :host([detached][modal]) .container { top: 16px; bottom: 16px; inset-inline-end: 16px; }
    :host([detached][modal][pivot='start']) .container { inset-inline-end: auto; inset-inline-start: 16px; }
    :host([detached][modal]) .container, :host([detached][modal][pivot='start']) .container { border-radius: var(--md-sys-shape-corner-large, 16px); } /* 토큰 detached-container-shape */
    /* modal: 가장자리 밖에서 밀려 들어오고 닫으면 다시 밀려 나간다 */
    :host([modal]) .container { translate: var(--_hide); transition: translate var(--_exit); }
    :host([modal][open]) .container { translate: 0 0; transition: translate var(--_enter); }
    @starting-style { :host([modal][open]) .container { translate: var(--_hide); } }
    @media (prefers-reduced-motion: reduce) { :host, .container, .scrim { transition: none !important; } }
    .header { display: flex; align-items: center; gap: 12px; padding: 24px 24px 16px; }  /* [스펙] 좌우 24, 요소 간 12. 상하 24/16 [미확인] */
    :host([back]) .header { padding-inline-start: 16px; }                 /* [스펙] 아이콘이 있을 때 시작 16 */
    .headline { flex: 1; min-width: 0; color: var(--md-sys-color-on-surface-variant, #49454f);
      font-family: var(--md-sys-typescale-title-large-font, Roboto, sans-serif);
      font-size: var(--md-sys-typescale-title-large-size, 22px);
      line-height: var(--md-sys-typescale-title-large-line-height, 28px);
      font-weight: var(--md-sys-typescale-title-large-weight, 400);
      letter-spacing: var(--md-sys-typescale-title-large-tracking, 0); }
    /* 가이드: 사이드 시트는 세로로만 스크롤하고 가로 스크롤은 쓰지 않는다 */
    .content { flex: 1; min-height: 0; overflow-x: hidden; overflow-y: auto; overflow-wrap: anywhere; padding: 0 24px; }
    .actions { display: none; box-sizing: border-box; align-items: center; justify-content: flex-start; gap: 8px;
      height: 72px; padding: 16px 24px 24px;  /* [스펙] 높이 72, 위 16, 아래 24, 왼쪽 정렬 */
      /* 16 + 32 + 24 = 72: 하단 액션 버튼은 XSmall(토큰 _md-comp-button-xsmall container-height 32px) */
      --md-filled-button-container-height: 32px; --md-outlined-button-container-height: 32px;
      --md-text-button-container-height: 32px; --md-filled-tonal-button-container-height: 32px;
      --md-elevated-button-container-height: 32px; }
    .actions.has { display: flex; }
    :host([divider]) .actions.has { box-shadow: inset 0 1px 0 var(--md-sys-color-outline, #79747e); } /* 높이 72 에 더해지지 않도록 */
  `;constructor(){super(),this.modal=!1,this.open=!1,this.pivot="end",this.detached=!1,this.divider=!1,this.back=!1,this.headline="",this.closeLabel="Close",this.backLabel="Back",this._restore=null,this.addEventListener("keydown",e=>{"Escape"===e.key&&this.open&&this.modal&&(e.stopPropagation(),this.close("escape"))})}show(){this.open||(this._restore=document.activeElement,this.open=!0,this.updateComplete.then(()=>{this.modal&&this.shadowRoot.querySelector(".close")?.focus(),this.dispatchEvent(new Event("opened",{bubbles:!0,composed:!0}))}))}close(e="close"){this.open&&(this.open=!1,this.modal&&this._restore&&this._restore.focus&&this._restore.focus(),this._restore=null,this.dispatchEvent(new CustomEvent("closed",{detail:{reason:e},bubbles:!0,composed:!0})))}render(){const e=!!this.querySelector(':scope > [slot="actions"]');return J`
      ${this.modal?J`<div class="scrim" part="scrim" @click=${()=>this.close("scrim")}></div>`:Q}
      <aside class="container" part="container" role=${this.modal?"dialog":"complementary"}
             aria-modal=${this.modal?"true":Q} aria-label=${this.headline||Q}>
        <div class="header" part="header">
          ${this.back?J`<md-icon-button class="back" aria-label=${this.backLabel}
              @click=${()=>this.dispatchEvent(new Event("back",{bubbles:!0,composed:!0}))}><md-icon>arrow_back</md-icon></md-icon-button>`:Q}
          <div class="headline" part="headline"><slot name="headline">${this.headline}</slot></div>
          <md-icon-button class="close" aria-label=${this.closeLabel} @click=${()=>this.close("close")}><md-icon>close</md-icon></md-icon-button>
        </div>
        <div class="content" part="content"><slot></slot></div>
        <div class="actions ${e?"has":""}" part="actions"><slot name="actions" @slotchange=${()=>this.requestUpdate()}></slot></div>
      </aside>`}}customElements.define("md-side-sheet",hn);class pn extends ve{static properties={modal:{type:Boolean,reflect:!0},open:{type:Boolean,reflect:!0},expanded:{type:Boolean,reflect:!0},dragHandle:{type:Boolean,attribute:"drag-handle"},handleLabel:{type:String,attribute:"handle-label"}};static styles=c`
    /* 열림/닫힘 전환: 아래에서 올라오고 닫으면 내려간다. 열 때 400ms emphasized decelerate, 닫을 때 200ms emphasized accelerate ([파생], side sheet 와 같음) */
    :host { display: none; position: fixed; inset: 0; z-index: 1000; pointer-events: none;
      --_enter: 400ms cubic-bezier(0.05, 0.7, 0.1, 1); --_exit: 200ms cubic-bezier(0.3, 0, 0.8, 0.15);
      transition: display var(--_exit) allow-discrete; }
    :host([open]) { display: block; transition: display var(--_enter) allow-discrete; }
    :host([modal]) { z-index: 1002; pointer-events: auto; }
    :host([modal]:not([open])) { pointer-events: none; }
    .scrim { position: absolute; inset: 0; background: var(--md-sys-color-scrim, #000); opacity: 0; transition: opacity var(--_exit); }
    :host([open]) .scrim { opacity: 0.32; transition: opacity var(--_enter); }
    @starting-style { :host([open]) .scrim { opacity: 0; } }
    .container {
      position: absolute; bottom: 0; left: 50%; transform: translateX(-50%); box-sizing: border-box;
      display: flex; flex-direction: column; pointer-events: auto;
      width: 100%; max-height: calc(100% - 72px);                              /* [스펙] 위 여백 72 */
      background: var(--md-sys-color-surface-container-low, #f7f2fa);
      border-radius: var(--md-sys-shape-corner-extra-large, 28px) var(--md-sys-shape-corner-extra-large, 28px) 0 0;
      box-shadow: ${c([dn])};
      translate: 0 100%; transition: translate var(--_exit);
    }
    :host([open]) .container { translate: 0 0; transition: translate var(--_enter); }
    @starting-style { :host([open]) .container { translate: 0 100%; } }
    :host([expanded]) .container { height: calc(100% - 72px); }
    @media (min-width: 641px) {
      .container { width: min(640px, calc(100% - 112px)); max-height: calc(100% - 56px); }   /* [스펙] 640 초과: 최대 640, 위·옆 56 */
      :host([expanded]) .container { height: calc(100% - 56px); }
    }
    :host([modal]:not([expanded])) .container { max-height: 50%; }            /* 가이드: modal 초기 높이는 화면의 50% 까지 */
    :host([open]) .container.dragging { transition: none; }               /* 끄는 동안은 손가락을 바로 따라간다 */
    @media (prefers-reduced-motion: reduce) { :host, .container, .scrim { transition: none !important; } }
    .handle-area { all: unset; box-sizing: border-box; display: flex; justify-content: center; flex: none;
      width: 100%; padding: 22px 0; cursor: grab; touch-action: none; }      /* [스펙] 핸들 위아래 22 */
    .handle-area:focus-visible .handle { outline: 3px solid var(--md-sys-color-secondary, #625b71); outline-offset: 4px; }
    .handle { width: 32px; height: 4px; border-radius: var(--md-bottom-sheet-drag-handle-shape, var(--md-sys-shape-corner-full, 2px));
      background: var(--md-sys-color-on-surface-variant, #49454f); opacity: 0.4; }
    .content { flex: 1; min-height: 0; overflow-x: hidden; overflow-y: auto; padding: 0 16px;
      color: var(--md-sys-color-on-surface, #1d1b20); }
  `;constructor(){super(),this.modal=!1,this.open=!1,this.expanded=!1,this.dragHandle=!0,this.handleLabel="Resize sheet",this._restore=null,this._dragY=null,this._dy=0,this._moved=!1,this.addEventListener("keydown",e=>{"Escape"===e.key&&this.open&&this.modal&&(e.stopPropagation(),this.close("escape"))})}show(){this.open||(this._restore=document.activeElement,this.open=!0,this.updateComplete.then(()=>{this.modal&&this.shadowRoot.querySelector(".handle-area")?.focus(),this.dispatchEvent(new Event("opened",{bubbles:!0,composed:!0}))}))}close(e="close"){this.open&&(this.open=!1,this.expanded=!1,this.modal&&this._restore&&this._restore.focus&&this._restore.focus(),this._restore=null,this.dispatchEvent(new CustomEvent("closed",{detail:{reason:e},bubbles:!0,composed:!0})))}_container(){return this.shadowRoot.querySelector(".container")}_down(e){this._dragY=e.clientY,this._dy=0,this._moved=!1,this._container().classList.add("dragging");try{e.currentTarget.setPointerCapture(e.pointerId)}catch(e){}}_move(e){null!==this._dragY&&(this._dy=e.clientY-this._dragY,Math.abs(this._dy)>4&&(this._moved=!0),this._container().style.translate=`0 ${Math.max(0,this._dy)}px`)}_up(){if(null===this._dragY)return;const e=this._dy;this._dragY=null;const t=this._container();t.classList.remove("dragging"),t.style.translate="",e>80?this.expanded?this.expanded=!1:this.close("swipe"):e<-80&&(this.expanded=!0)}_click(){this._moved?this._moved=!1:this.expanded=!this.expanded}render(){return J`
      ${this.modal?J`<div class="scrim" part="scrim" @click=${()=>this.close("scrim")}></div>`:Q}
      <section class="container" part="container" role=${this.modal?"dialog":"complementary"} aria-modal=${this.modal?"true":Q}>
        ${this.dragHandle?J`<button class="handle-area" part="handle" aria-label=${this.handleLabel}
            aria-expanded=${this.expanded?"true":"false"} @click=${this._click}
            @pointerdown=${this._down} @pointermove=${this._move} @pointerup=${this._up} @pointercancel=${this._up}>
            <span class="handle"></span></button>`:Q}
        <div class="content" part="content"><slot></slot></div>
      </section>`}}customElements.define("md-bottom-sheet",pn);{const e=new CSSStyleSheet;e.replaceSync(':root { --md-icon-font: "Material Symbols Outlined", "Material Symbols"; }'),document.adoptedStyleSheets=[e,...document.adoptedStyleSheets]}{const e=new CSSStyleSheet;e.replaceSync("md-navigation-drawer-modal { position: fixed; inset: 0; z-index: 1002; pointer-events: none;\n    --md-navigation-drawer-modal-scrim-color: var(--md-sys-color-scrim, #000);\n    --md-navigation-drawer-modal-scrim-opacity: 0.32;\n    --md-navigation-drawer-modal-container-color: var(--md-sys-color-surface-container-low, #f7f2fa); }\n  md-navigation-drawer-modal[opened] { pointer-events: auto; }"),document.adoptedStyleSheets=[e,...document.adoptedStyleSheets],customElements.whenDefined("md-navigation-drawer-modal").then(()=>{const e=customElements.get("md-navigation-drawer-modal"),t=e.prototype.updated;e.prototype.updated=function(e){this.toggleAttribute("opened",!!this.opened),t.call(this,e)}})}customElements.define=e;
//# sourceMappingURL=labs.bundle.js.map
