// sayaya-ui 자체 구현 M3 컴포넌트 (Material Web 2.5.0 에 컴포넌트가 없음)
//   md-tooltip      : plain / rich tooltip
//   md-snackbar     : snackbar
//   (md-button-group 은 sayaya-button-group.js 로 분리)
//   md-toast        : [M3 외 확장] 데스크톱 웹용 스택 알림(handbook ToastContainer 이식). 모바일은 md-snackbar 를 쓴다
// 수치 출처: @material/web/tokens/versions/latest/sass/_md-comp-{plain-tooltip,rich-tooltip,snackbar,button-group-*}.scss
// [토큰 외] 값은 토큰에 없어 m3.material.io 스펙 페이지(tooltips/specs)와 Compose Material3(Tooltip.kt, Snackbar.kt) 상수로 대조한 값이다.
import {LitElement, html, css, unsafeCSS} from 'lit';

const ELEVATION = {
  2: '0 1px 2px 0 rgba(0,0,0,.3), 0 2px 6px 2px rgba(0,0,0,.15)',
  3: '0 1px 3px 0 rgba(0,0,0,.3), 0 4px 8px 3px rgba(0,0,0,.15)',
};

// ---------------------------------------------------------------- tooltip
// 표시 규칙
//   - 마우스: 앵커에 올리고 delay(기본 500ms) 뒤 표시, 떠나고 1.5초 뒤 닫힘(가이드)
//   - 키보드: 포커스로 같은 규칙. 터치 직후(1초 안)의 focusin 은 탭으로 생긴 포커스라 무시한다
//   - 터치: 길게 누르기(longPressDelay, 기본 500ms)로 표시. 그 전에 떼면 탭, TOUCH_SLOP 보다 움직이거나 pointercancel(스크롤 시작)이면 취소.
//           길게 눌러 연 뒤 손을 떼면 1.5초 뒤 닫히고, 뒤따르는 click 은 앵커 동작을 일으키지 않게 막는다
//           (M3 플랫폼 구현과 같다: Compose TooltipBox·Android 길게 누르기는 탭(onClick)을 일으키지 않는다)
//   - 창 안에서 동시에 하나만 열린다(새 툴팁이 열리면 이전 것은 즉시 닫힘)
//   - 표시 위치: popover 를 지원하는 브라우저에서는 popover=manual 로 top layer 에 올려, 조상의 overflow·transform·z-index 에 가려지지 않는다.
//     열린 동안은 visibility:visible 이라 visibility:hidden 인 부모 안에 있어도 보인다(display:none 조상은 예외 — 문서 body 아래 두기를 권장)
//   - DOM 에서 떼면 앵커 리스너·타이머를 정리하고, 다시 붙이면 같은 앵커에 다시 연결한다
let currentTooltip = null; // 창에 동시에 하나만 (magi TipElement 규칙)
const LONG_PRESS_MS = 500; // [토큰 외] 길게 누르기 판정 시간. 소비 측(magi) 요청값
const TOUCH_SLOP = 10;     // [토큰 외] 이 거리(px)보다 움직이면 스크롤로 보고 길게 누르기를 취소한다(선택값)
const TAP_FOCUS_MS = 1000; // [토큰 외] 터치 뒤 이 시간 안의 focusin 은 탭 포커스로 보고 무시한다(선택값)
const HAS_POPOVER = typeof HTMLElement !== 'undefined' && Object.prototype.hasOwnProperty.call(HTMLElement.prototype, 'popover');
export class MdTooltip extends LitElement {
  static properties = {
    variant: {type: String, reflect: true},      // plain | rich
    position: {type: String, reflect: true},     // top | bottom | start | end
    for: {type: String, reflect: true},          // 앵커 id
    delay: {type: Number},                       // ms
    open: {type: Boolean, reflect: true},
    persistent: {type: Boolean, reflect: true},  // rich: 앵커를 떠나도 유지(액션이 있을 때)
    longPressDelay: {type: Number, attribute: 'long-press-delay'}, // 터치 길게 누르기 판정(ms)
  };
  static styles = css`
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
      box-shadow: ${css([ELEVATION[2]])};
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
  `;
  constructor() {
    super();
    // 업그레이드 전에(정의되기 전 생성된 요소에) 설정된 anchor/text 는 인스턴스 속성으로 남아 접근자를 가리므로 옮겨 둔다
    const pending = {};
    for (const k of ['anchor', 'text']) if (Object.prototype.hasOwnProperty.call(this, k)) { pending[k] = this[k]; delete this[k]; }
    this.variant = 'plain'; this.position = ''; // '' = 자동: plain 위쪽, rich 오른쪽 아래(가이드)
    this.delay = 500; this.open = false; this.persistent = false; this.longPressDelay = LONG_PRESS_MS;
    this._target = null; this._anchor = null; this._timer = 0; this._hide = 0;
    this._press = 0; this._pressAt = null; this._longPressed = false; this._lastTouch = -Infinity;
    this._pending = pending;
    this._enter = (e) => {
      if (e && e.pointerType === 'touch') return;                                   // 터치는 길게 누르기로만 연다
      if (e && e.type === 'focusin' && performance.now() - this._lastTouch < TAP_FOCUS_MS) return; // 탭이 만든 포커스
      this._schedule(true);
    };
    this._leave = (e) => { if (e && e.pointerType === 'touch') return; this._schedule(false); };
    this._key = (e) => { if (e.key === 'Escape') this.hide(); };
    // persistent rich: 클릭으로 열고, 다른 요소와 상호작용하면 닫는다(가이드 Persistent rich tooltips)
    this._click = () => { if (this._isPersistent()) { this.open ? this.hide() : this.show(); } };
    this._outside = (e) => {
      const path = e.composedPath ? e.composedPath() : [];
      if (path.includes(this) || (this._anchor && path.includes(this._anchor))) return;
      this.hide();
    };
    // 터치 길게 누르기
    this._down = (e) => {
      if (e.pointerType !== 'touch' || this._isPersistent()) return;
      this._lastTouch = performance.now();
      this._cancelPress();
      this._pressAt = {x: e.clientX, y: e.clientY};
      this._press = setTimeout(() => {
        this._press = 0; this._longPressed = true;
        clearTimeout(this._timer); clearTimeout(this._hide);
        this.show();
      }, this.longPressDelay);
    };
    this._move = (e) => {
      if (e.pointerType !== 'touch' || !this._pressAt || this._longPressed) return;
      if (Math.hypot(e.clientX - this._pressAt.x, e.clientY - this._pressAt.y) > TOUCH_SLOP) this._cancelPress(); // 스크롤로 본다
    };
    this._up = (e) => {
      if (e.pointerType !== 'touch') return;
      this._lastTouch = performance.now();
      const long = this._longPressed;
      this._cancelPress(); // 시간 전에 떼면 탭, pointercancel(스크롤 시작)이면 취소
      if (!long) return;
      if (e.type === 'pointerup') this._swallowClick();
      clearTimeout(this._hide); this._hide = setTimeout(() => this.hide(), 1500); // 떼고 1.5초 뒤 닫힘
    };
    // 길게 누르는 동안 시스템 컨텍스트 메뉴(링크 미리보기 등)가 툴팁을 덮지 않게 한다
    this._ctx = (e) => { if (this._pressAt || this._longPressed) e.preventDefault(); };
  }
  get anchor() { return this._target; }
  set anchor(el) { this._target = el || null; if (this.isConnected) this._bind(this._target); }
  /** 기본 슬롯의 문구(바로 아래 텍스트 노드). 설정하면 기존 텍스트 노드를 바꾼다 — subhead/actions 슬롯은 그대로 둔다 */
  get text() { return [...this.childNodes].filter(n => n.nodeType === Node.TEXT_NODE).map(n => n.data).join(''); }
  set text(v) { this._setText(v); }
  _setText(v) {
    [...this.childNodes].filter(n => n.nodeType === Node.TEXT_NODE).forEach(n => n.remove());
    if (v != null && v !== '') this.appendChild(document.createTextNode(String(v)));
    if (this.open) this.updateComplete.then(() => this._place());
  }
  connectedCallback() {
    super.connectedCallback();
    const p = this._pending; this._pending = null;
    if (p) { if ('text' in p) this._setText(p.text); if ('anchor' in p) this._target = p.anchor || null; }
    if (HAS_POPOVER && !this.hasAttribute('popover')) this.setAttribute('popover', 'manual'); // top layer
    if (!this._target && this.for) this._target = this._lookup();
    this._bind(this._target);
  }
  disconnectedCallback() {
    super.disconnectedCallback();
    // 앵커 리스너·타이머·문서 리스너를 정리한다. 앵커(_target)는 기억해 두었다가 다시 붙으면 다시 연결한다
    this.hide(); this._cancelPress(); this._bind(null);
    if (currentTooltip === this) currentTooltip = null;
  }
  updated(changed) { if (changed.has('for') && this.isConnected && this.for) { this._target = this._lookup(); this._bind(this._target); } }
  _lookup() { return this.getRootNode().getElementById?.(this.for) ?? document.getElementById(this.for); }
  _listeners() {
    return [['pointerenter', this._enter], ['focusin', this._enter], ['pointerleave', this._leave], ['focusout', this._leave],
      ['keydown', this._key], ['click', this._click],
      ['pointerdown', this._down], ['pointermove', this._move], ['pointerup', this._up], ['pointercancel', this._up], ['contextmenu', this._ctx]];
  }
  _bind(el) {
    const a = this._anchor;
    if (a) this._listeners().forEach(([t, f]) => a.removeEventListener(t, f));
    this._anchor = el || null;
    if (el) {
      this._listeners().forEach(([t, f]) => el.addEventListener(t, f));
      if (this.variant === 'plain' && !el.hasAttribute('aria-describedby') && this.id) el.setAttribute('aria-describedby', this.id);
    }
  }
  _cancelPress() { clearTimeout(this._press); this._press = 0; this._pressAt = null; this._longPressed = false; }
  // 길게 누른 뒤 브라우저가 보내는 click 이 앵커 동작(버튼 클릭)을 일으키지 않게 한 번만 막는다
  _swallowClick() {
    const anchor = this._anchor;
    let t = 0;
    const done = () => { clearTimeout(t); window.removeEventListener('click', stop, true); };
    const stop = (ev) => {
      if (anchor && ev.composedPath().includes(anchor)) { ev.preventDefault(); ev.stopImmediatePropagation(); }
      done();
    };
    window.addEventListener('click', stop, true);
    t = setTimeout(done, 800);
  }
  _isPersistent() { return this.variant === 'rich' && this.persistent; }
  _schedule(show) {
    if (this._isPersistent()) return; // persistent rich 는 hover/포커스로 열고 닫지 않는다
    clearTimeout(this._timer); clearTimeout(this._hide);
    if (show) this._timer = setTimeout(() => this.show(), this.delay);
    else this._hide = setTimeout(() => this.hide(), 1500); // 가이드: 대상을 벗어나고 1.5초 뒤 사라진다
  }
  /** 즉시 표시. autoHideMs > 0 이면 그 뒤 닫는다(handbook showImmediate). 앵커가 문서에서 떨어져 있으면 열지 않는다 */
  show(autoHideMs = 0) {
    if (!this.isConnected || (this._anchor && !this._anchor.isConnected)) return;
    if (currentTooltip && currentTooltip !== this) currentTooltip.hide();
    currentTooltip = this;
    this.setAttribute('role', 'tooltip');
    this.open = true;
    if (HAS_POPOVER && this.popover && !this.matches(':popover-open')) { try { this.showPopover(); } catch (_) { /* 연결 전 등: fixed 로 표시 */ } }
    if (this._isPersistent()) setTimeout(() => { if (!this.open) return; document.addEventListener('pointerdown', this._outside, true); document.addEventListener('focusin', this._outside, true); });
    this.updateComplete.then(() => this._place());
    this.dispatchEvent(new Event('opened', {bubbles: true, composed: true}));
    if (autoHideMs > 0) { clearTimeout(this._hide); this._hide = setTimeout(() => this.hide(), autoHideMs); }
  }
  hide() {
    clearTimeout(this._timer); clearTimeout(this._hide);
    document.removeEventListener('pointerdown', this._outside, true); document.removeEventListener('focusin', this._outside, true);
    if (HAS_POPOVER && this.popover) { try { if (this.matches(':popover-open')) this.hidePopover(); } catch (_) { /* 이미 닫힘 */ } }
    if (!this.open) return;
    this.open = false;
    if (currentTooltip === this) currentTooltip = null;
    this.dispatchEvent(new Event('closed', {bubbles: true, composed: true}));
  }
  _place() {
    if (!this._anchor) return;
    const gap = 4; // [토큰 외] Compose SpacingBetweenTooltipAndAnchor
    const r = this._anchor.getBoundingClientRect(), t = this.getBoundingClientRect();
    const rtl = getComputedStyle(this).direction === 'rtl';
    let x, y, p = this.position || (this.variant === 'rich' ? 'bottom-end' : 'top');
    if (p === 'start') p = rtl ? 'right' : 'left'; else if (p === 'end') p = rtl ? 'left' : 'right';
    if (p === 'bottom-end') { x = rtl ? r.left + r.width / 2 - t.width : r.left + r.width / 2; y = r.bottom + gap; } // rich 기본: 오른쪽 아래
    else if (p === 'bottom') { x = r.left + r.width / 2 - t.width / 2; y = r.bottom + gap; }
    else if (p === 'left') { x = r.left - t.width - gap; y = r.top + r.height / 2 - t.height / 2; }
    else if (p === 'right') { x = r.right + gap; y = r.top + r.height / 2 - t.height / 2; }
    else { x = r.left + r.width / 2 - t.width / 2; y = r.top - t.height - gap; if (y < 0) y = r.bottom + gap; }
    x = Math.max(8, Math.min(x, innerWidth - t.width - 8));
    this.style.left = x + 'px'; this.style.top = y + 'px';
  }
  render() {
    const sub = !!this.querySelector(':scope > [slot="subhead"]'), act = !!this.querySelector(':scope > [slot="actions"]');
    return html`<slot name="subhead" class="subhead ${sub ? 'has' : ''}" @slotchange=${() => this.requestUpdate()}></slot><slot></slot><div class="actions ${act ? 'has' : ''}"><slot name="actions" @slotchange=${() => this.requestUpdate()}></slot></div>`;
  }
}
if (!customElements.get('md-tooltip')) customElements.define('md-tooltip', MdTooltip); // 이미 정의돼 있으면 건너뜀(다른 번들과 공존)

// ---------------------------------------------------------------- snackbar
let currentSnackbar = null; // 한 번에 하나 (M3: 동시에 여러 개를 띄우지 않는다)
export class MdSnackbar extends LitElement {
  static properties = {
    open: {type: Boolean, reflect: true},
    action: {type: String},                       // 액션 라벨
    closeable: {type: Boolean, reflect: true},    // 닫기(X) 아이콘
    closeLabel: {type: String, attribute: 'close-label'}, // 닫기 버튼 접근성 라벨, 기본 'Close'
    timeout: {type: Number},                      // ms, 0 이면 자동으로 닫지 않음. -1(기본) = 자동: 액션이 있으면 닫지 않고, 없으면 4초
    align: {type: String, reflect: true},         // center(기본) | start — 가이드: 같은 위치에 일관되게 왼쪽 또는 가운데
    multiline: {type: Boolean, reflect: true},    // 두 줄(68dp)
    longAction: {type: Boolean, attribute: 'long-action', reflect: true}, // 액션을 다음 줄로
  };
  static styles = css`
    :host { position: fixed; z-index: 1001; left: 50%; bottom: 8px; transform: translateX(-50%); /* [토큰 외] MDC-Android m3_snackbar_margin 8dp */
      display: none; box-sizing: border-box; width: max-content; max-width: min(600px, calc(100vw - 16px)); /* [토큰 외] Compose ContainerMaxWidth 600dp, 좌우 여백 m3_snackbar_margin 8dp */ }
    :host([align='start']) { left: 8px; transform: none; }
    :host([open]) { display: block; }
    .container { display: flex; align-items: center; flex-wrap: wrap; box-sizing: border-box;
      min-height: 48px; padding: 0 8px 0 16px; gap: 0 4px;
      border-radius: var(--md-sys-shape-corner-extra-small, 4px);
      background: var(--md-sys-color-inverse-surface, #322f35);
      color: var(--md-sys-color-inverse-on-surface, #f5eff7);
      box-shadow: ${css([ELEVATION[3]])}; }
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
  `;
  constructor() { super(); this.open = false; this.action = ''; this.closeable = false; this.timeout = -1; this.align = 'center'; this.multiline = false; this.longAction = false; this._t = 0; }
  show() {
    if (currentSnackbar && currentSnackbar !== this) currentSnackbar.close('replaced');
    currentSnackbar = this;
    this.setAttribute('role', 'status'); this.setAttribute('aria-live', 'polite');
    this.open = true;
    clearTimeout(this._t);
    // 가이드(웹 접근성): 액션이 있는 스낵바는 액션을 누를 때까지 자동으로 닫지 않는다
    const ms = this.timeout < 0 ? (this.action ? 0 : 4000) : this.timeout;
    if (ms > 0) this._t = setTimeout(() => this.close('timeout'), ms);
    this.dispatchEvent(new Event('opened', {bubbles: true, composed: true}));
  }
  close(reason = 'dismiss') {
    clearTimeout(this._t);
    if (!this.open) return;
    this.open = false;
    if (currentSnackbar === this) currentSnackbar = null;
    this.dispatchEvent(new CustomEvent('closed', {detail: {reason}, bubbles: true, composed: true}));
  }
  _action() { this.dispatchEvent(new Event('action', {bubbles: true, composed: true})); this.close('action'); }
  render() {
    return html`<div class="container" part="container">
      <div class="text"><slot></slot></div>
      <div class="trail">
        ${this.action ? html`<button class="action" part="action" @click=${this._action}>${this.action}</button>` : ''}
        ${this.closeable ? html`<button class="close" part="close" aria-label=${this.closeLabel || 'Close'} @click=${() => this.close('dismiss')}><svg viewBox="0 0 24 24"><path d="M19 6.41 17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z"/></svg></button>` : ''}
      </div></div>`;
  }
}
if (!customElements.get('md-snackbar')) customElements.define('md-snackbar', MdSnackbar); // 이미 정의돼 있으면 건너뜀(다른 번들과 공존)

// ---------------------------------------------------------------- toast  [M3 외 확장]
// M3 에는 toast 컴포넌트가 없다(모바일 기준 snackbar 만 있음). 데스크톱 웹 앱에서 여러 알림을 화면 오른쪽 위에 쌓아 보여 주는
// 관례를 위해 sayaya-ui 가 추가한 컴포넌트다. 동작·수치는 handbook ui-components ToastContainer(ui-components.css)를 옮겼고,
// 색·모양·글꼴은 M3 시스템 토큰(color/shape/typescale/elevation)으로 표현했다.
//   level   : info(primary) | success(tertiary) | warning(error-container) | error(error)  — handbook ToastLevel 색 대응
//   timeout : -1(기본) = 자동: info·success 3000ms 뒤 닫힘, warning·error 는 직접 닫을 때까지 유지. 0 = 닫지 않음
//   쌓임    : 창마다 스택 하나(body 에 붙는 .md-toast-stack). 새 토스트가 아래에 붙고, max(기본 5)를 넘으면 가장 오래된 것부터 닫는다('overflow')
//   접근성  : info·success role=status/aria-live=polite, warning·error role=alert/aria-live=assertive. 포인터·포커스가 머무는 동안 타이머 정지
//   호환    : host 에 handbook 셀렉터 class ui-toast, ui-toast-<level> 를 붙인다(닫기 버튼은 shadow 안이므로 part="close" 로 접근)
const TOAST_LEVELS = {
  info:    ['primary', 'on-primary', '#6750a4', '#ffffff'],
  success: ['tertiary', 'on-tertiary', '#7d5260', '#ffffff'],
  warning: ['error-container', 'on-error-container', '#f9dedc', '#410e0b'],
  error:   ['error', 'on-error', '#b3261e', '#ffffff'],
};
const TOAST_OUT_MS = 300; // 퇴장 애니메이션 길이 = 제거 지연(handbook: CSS 0.3s 와 JS 300ms 가 맞물림)
let toastStack = null;
function toastContainer() {
  if (toastStack && toastStack.isConnected) return toastStack;
  toastStack = document.createElement('div');
  toastStack.className = 'md-toast-stack';
  document.body.appendChild(toastStack);
  return toastStack;
}
{
  const stackStyle = new CSSStyleSheet();
  // [M3 외] handbook: fixed top 60px(앱 바 아래) right 20px, gap 8px, max-width 400px, z-index 10002. 위치는 변수로 바꿀 수 있다.
  // 좁은 화면(600px 미만)에서는 좌우 8px 여백으로 늘린다 — 모바일에서는 snackbar 사용을 권장한다
  stackStyle.replaceSync(`.md-toast-stack { position: fixed; z-index: var(--md-toast-z-index, 10002);
      top: var(--md-toast-top, 60px); right: var(--md-toast-right, 20px); left: auto;
      display: flex; flex-direction: column; align-items: flex-end; gap: 8px;
      max-width: min(400px, calc(100vw - 16px)); pointer-events: none; }
    .md-toast-stack > md-toast { pointer-events: auto; }
    @media (max-width: 599px) { .md-toast-stack { left: 8px; right: 8px; max-width: none; align-items: stretch; } }`);
  document.adoptedStyleSheets = [...document.adoptedStyleSheets, stackStyle];
}
export class MdToast extends LitElement {
  static properties = {
    open: {type: Boolean, reflect: true},
    level: {type: String, reflect: true},         // info | success | warning | error
    action: {type: String},                       // 액션 라벨(선택)
    closeable: {type: Boolean, reflect: true},    // 닫기(X) 버튼, 기본 true
    closeLabel: {type: String, attribute: 'close-label'}, // 닫기 버튼 접근성 라벨, 기본 'Close'
    timeout: {type: Number},                      // ms. -1(기본) 자동, 0 닫지 않음
    max: {type: Number},                          // 스택에 동시에 보일 최대 개수(기본 5)
    leaving: {type: Boolean, reflect: true},      // 퇴장 애니메이션 중
  };
  static styles = css`
    :host { display: none; box-sizing: border-box; max-width: 100%; }
    :host([open]) { display: block; animation: md-toast-in 300ms ease; }
    :host([leaving]) { animation: md-toast-out ${unsafeCSS(TOAST_OUT_MS)}ms ease forwards; }
    @keyframes md-toast-in { from { opacity: 0; transform: translateX(20px); } to { opacity: 1; transform: translateX(0); } }
    @keyframes md-toast-out { from { opacity: 1; } to { opacity: 0; transform: translateY(-10px); } }
    @media (prefers-reduced-motion: reduce) { :host([open]), :host([leaving]) { animation: none; } }
    .container { display: flex; align-items: center; box-sizing: border-box; min-height: 48px;
      padding: 0 4px 0 16px; gap: 4px;
      border-radius: var(--md-sys-shape-corner-small, 8px);   /* handbook 8px = M3 corner small */
      box-shadow: ${css([ELEVATION[2]])};                    /* handbook elevation level2 */
      background: var(--_bg); color: var(--_fg); }
    ${unsafeCSS(Object.entries(TOAST_LEVELS).map(([lv, [bg, fg, bgd, fgd]]) =>
      `:host([level='${lv}']) .container { --_bg: var(--md-sys-color-${bg}, ${bgd}); --_fg: var(--md-sys-color-${fg}, ${fgd}); }`).join('\n'))}
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
  `;
  constructor() {
    super();
    this.open = false; this.level = 'info'; this.action = ''; this.closeable = true; this.timeout = -1; this.max = 5; this.leaving = false;
    this._t = 0; this._rm = 0; this._left = 0; this._started = 0; this._paused = false;
    // 포인터·포커스가 토스트 위에 있는 동안 자동 닫힘을 멈추고, 떠나면 남은 시간(최소 1초)만큼 다시 잰다
    this._pause = () => { if (!this.open || this.leaving || !this._t) return; clearTimeout(this._t); this._t = 0; this._left -= Date.now() - this._started; this._paused = true; };
    this._resume = () => { if (!this._paused || !this.open || this.leaving) return; this._paused = false; this._arm(Math.max(this._left, 1000)); };
  }
  connectedCallback() {
    super.connectedCallback();
    ['pointerenter', 'focusin'].forEach(t => this.addEventListener(t, this._pause));
    ['pointerleave', 'focusout'].forEach(t => this.addEventListener(t, this._resume));
  }
  updated(changed) {
    if (changed.has('level')) {
      for (const lv of Object.keys(TOAST_LEVELS)) this.classList.toggle('ui-toast-' + lv, lv === this.level);
      const urgent = this.level === 'warning' || this.level === 'error';
      this.setAttribute('role', urgent ? 'alert' : 'status');
      this.setAttribute('aria-live', urgent ? 'assertive' : 'polite');
    }
  }
  /** 실제로 쓰일 자동 닫힘 시간(ms). 0 이면 닫지 않음 */
  get effectiveTimeout() {
    if (this.timeout >= 0) return this.timeout;
    return this.level === 'warning' || this.level === 'error' ? 0 : 3000;
  }
  _arm(ms) { clearTimeout(this._t); this._t = 0; if (ms > 0) { this._left = ms; this._started = Date.now(); this._t = setTimeout(() => this.close('timeout'), ms); } }
  /** 스택(.md-toast-stack)에 붙여 표시한다. 이미 붙어 있으면 다시 연다 */
  show() {
    clearTimeout(this._rm);
    this.classList.add('ui-toast');
    const stack = toastContainer();
    if (this.parentNode !== stack) stack.appendChild(this);
    this.leaving = false; this._paused = false;
    this.open = true;
    this._arm(this.effectiveTimeout);
    const live = [...stack.children].filter(t => t.localName === 'md-toast' && t.open && !t.leaving);
    for (let i = 0; i < live.length - Math.max(1, this.max); i++) live[i].close('overflow');
    this.dispatchEvent(new Event('opened', {bubbles: true, composed: true}));
  }
  /** 퇴장 애니메이션 뒤 DOM 에서 떼어 낸다. reason: action | dismiss | timeout | overflow */
  close(reason = 'dismiss') {
    clearTimeout(this._t); this._t = 0;
    if (!this.open || this.leaving) return;
    this.leaving = true;
    this.dispatchEvent(new CustomEvent('closed', {detail: {reason}, bubbles: true, composed: true}));
    this._rm = setTimeout(() => { this.open = false; this.leaving = false; this.remove(); }, TOAST_OUT_MS);
  }
  _action() { this.dispatchEvent(new Event('action', {bubbles: true, composed: true})); this.close('action'); }
  render() {
    return html`<div class="container" part="container">
      <div class="text" part="text"><slot></slot></div>
      ${this.action ? html`<button class="action" part="action" @click=${this._action}>${this.action}</button>` : ''}
      ${this.closeable ? html`<button class="close" part="close" aria-label=${this.closeLabel || 'Close'} @click=${() => this.close('dismiss')}><svg viewBox="0 0 24 24"><path d="M19 6.41 17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z"/></svg></button>` : ''}
    </div>`;
  }
}
if (!customElements.get('md-toast')) customElements.define('md-toast', MdToast); // 이미 정의돼 있으면 건너뜀(다른 번들과 공존)

