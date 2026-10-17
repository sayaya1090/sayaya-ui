// sayaya-ui 자체 구현 M3 컴포넌트 (Material Web 2.5.0 에 컴포넌트가 없음)
//   md-tooltip      : plain / rich tooltip
//   md-snackbar     : snackbar
//   md-button-group : standard / connected button group
// 수치 출처: @material/web/tokens/versions/latest/sass/_md-comp-{plain-tooltip,rich-tooltip,snackbar,button-group-*}.scss
// [토큰 외] 표시가 붙은 값은 토큰에 없어 M3 가이드 수치(magi console.css 인용)를 쓴 것이다.
import {LitElement, html, css} from 'lit';

const ELEVATION = {
  2: '0 1px 2px 0 rgba(0,0,0,.3), 0 2px 6px 2px rgba(0,0,0,.15)',
  3: '0 1px 3px 0 rgba(0,0,0,.3), 0 4px 8px 3px rgba(0,0,0,.15)',
};

// ---------------------------------------------------------------- tooltip
let currentTooltip = null; // 창에 동시에 하나만 (magi TipElement 규칙)
export class MdTooltip extends LitElement {
  static properties = {
    variant: {type: String, reflect: true},      // plain | rich
    position: {type: String, reflect: true},     // top | bottom | start | end
    for: {type: String, reflect: true},          // 앵커 id
    delay: {type: Number},                       // ms
    open: {type: Boolean, reflect: true},
    persistent: {type: Boolean, reflect: true},  // rich: 앵커를 떠나도 유지(액션이 있을 때)
  };
  static styles = css`
    :host { position: fixed; z-index: 1000; display: none; pointer-events: none; box-sizing: border-box; }
    :host([open]) { display: block; }
    :host([variant='plain']) {
      min-height: 24px; padding: 4px 8px;            /* [토큰 외] 24dp, 8dp */
      max-width: 200px;                               /* [토큰 외] */
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
      max-width: 312px; padding: 12px 16px 8px;       /* [토큰 외] */
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
    this.variant = 'plain'; this.position = 'top'; this.delay = 500; this.open = false; this.persistent = false;
    this._anchor = null; this._timer = 0; this._hide = 0;
    this._enter = () => this._schedule(true);
    this._leave = () => this._schedule(false);
    this._key = (e) => { if (e.key === 'Escape') this.hide(); };
  }
  get anchor() { return this._anchor; }
  set anchor(el) { this._bind(el); }
  connectedCallback() { super.connectedCallback(); if (!this._anchor && this.for) this._bind(this.getRootNode().getElementById?.(this.for) ?? document.getElementById(this.for)); }
  disconnectedCallback() { super.disconnectedCallback(); this._bind(null); if (currentTooltip === this) currentTooltip = null; }
  updated(changed) { if (changed.has('for') && this.isConnected && this.for) this._bind(document.getElementById(this.for)); }
  _bind(el) {
    const a = this._anchor;
    if (a) { ['pointerenter','focusin'].forEach(t => a.removeEventListener(t, this._enter)); ['pointerleave','focusout'].forEach(t => a.removeEventListener(t, this._leave)); a.removeEventListener('keydown', this._key); }
    this._anchor = el || null;
    if (el) { ['pointerenter','focusin'].forEach(t => el.addEventListener(t, this._enter)); ['pointerleave','focusout'].forEach(t => el.addEventListener(t, this._leave)); el.addEventListener('keydown', this._key);
      if (this.variant === 'plain' && !el.hasAttribute('aria-describedby') && this.id) el.setAttribute('aria-describedby', this.id); }
  }
  _schedule(show) {
    clearTimeout(this._timer); clearTimeout(this._hide);
    if (show) this._timer = setTimeout(() => this.show(), this.delay);
    else if (!(this.variant === 'rich' && this.persistent && this.matches(':hover'))) this._hide = setTimeout(() => this.hide(), this.variant === 'rich' && this.persistent ? 300 : 0);
  }
  /** 즉시 표시. autoHideMs > 0 이면 그 뒤 닫는다(handbook showImmediate). */
  show(autoHideMs = 0) {
    if (currentTooltip && currentTooltip !== this) currentTooltip.hide();
    currentTooltip = this;
    this.setAttribute('role', 'tooltip');
    this.open = true;
    this.updateComplete.then(() => this._place());
    this.dispatchEvent(new Event('opened', {bubbles: true, composed: true}));
    if (autoHideMs > 0) { clearTimeout(this._hide); this._hide = setTimeout(() => this.hide(), autoHideMs); }
  }
  hide() {
    clearTimeout(this._timer); clearTimeout(this._hide);
    if (!this.open) return;
    this.open = false;
    if (currentTooltip === this) currentTooltip = null;
    this.dispatchEvent(new Event('closed', {bubbles: true, composed: true}));
  }
  _place() {
    if (!this._anchor) return;
    const gap = this.variant === 'rich' ? 8 : 4; // [토큰 외]
    const r = this._anchor.getBoundingClientRect(), t = this.getBoundingClientRect();
    const rtl = getComputedStyle(this).direction === 'rtl';
    let x, y, p = this.position;
    if (p === 'start') p = rtl ? 'right' : 'left'; else if (p === 'end') p = rtl ? 'left' : 'right';
    if (p === 'bottom') { x = r.left + r.width / 2 - t.width / 2; y = r.bottom + gap; }
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
customElements.define('md-tooltip', MdTooltip);

// ---------------------------------------------------------------- snackbar
let currentSnackbar = null; // 한 번에 하나 (M3: 동시에 여러 개를 띄우지 않는다)
export class MdSnackbar extends LitElement {
  static properties = {
    open: {type: Boolean, reflect: true},
    action: {type: String},                       // 액션 라벨
    closeable: {type: Boolean, reflect: true},    // 닫기(X) 아이콘
    timeout: {type: Number},                      // ms, <=0 이면 자동으로 닫지 않음
    multiline: {type: Boolean, reflect: true},    // 두 줄(68dp)
    longAction: {type: Boolean, attribute: 'long-action', reflect: true}, // 액션을 다음 줄로
  };
  static styles = css`
    :host { position: fixed; z-index: 1001; left: 50%; bottom: 16px; transform: translateX(-50%); /* [토큰 외] */
      display: none; box-sizing: border-box; min-width: min(344px, calc(100vw - 32px)); max-width: min(672px, calc(100vw - 32px)); /* [토큰 외] */ }
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
    .action { height: 40px; padding: 0 12px; border-radius: 20px;
      color: var(--md-sys-color-inverse-primary, #d0bcff);
      font-family: var(--md-sys-typescale-label-large-font, Roboto, sans-serif);
      font-size: var(--md-sys-typescale-label-large-size, 14px);
      line-height: var(--md-sys-typescale-label-large-line-height, 20px);
      font-weight: var(--md-sys-typescale-label-large-weight, 500);
      letter-spacing: var(--md-sys-typescale-label-large-tracking, 0.1px); }
    .close { width: 40px; height: 40px; border-radius: 20px; color: var(--md-sys-color-inverse-on-surface, #f5eff7); }
    .close svg { width: 24px; height: 24px; fill: currentColor; }
  `;
  constructor() { super(); this.open = false; this.action = ''; this.closeable = false; this.timeout = 4000; this.multiline = false; this.longAction = false; this._t = 0; }
  show() {
    if (currentSnackbar && currentSnackbar !== this) currentSnackbar.close('replaced');
    currentSnackbar = this;
    this.setAttribute('role', 'status'); this.setAttribute('aria-live', 'polite');
    this.open = true;
    clearTimeout(this._t);
    if (this.timeout > 0) this._t = setTimeout(() => this.close('timeout'), this.timeout);
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
        ${this.closeable ? html`<button class="close" part="close" aria-label="닫기" @click=${() => this.close('dismiss')}><svg viewBox="0 0 24 24"><path d="M19 6.41 17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z"/></svg></button>` : ''}
      </div></div>`;
  }
}
customElements.define('md-snackbar', MdSnackbar);

// ---------------------------------------------------------------- button group
const GROUP = { // _md-comp-button-group-{standard,connected}-{size}.scss
  xsmall: {h: 32, std: 18, inner: 8, pressedInner: 4},
  small:  {h: 40, std: 12, inner: 8, pressedInner: 4},
  medium: {h: 56, std: 8, inner: 8, pressedInner: 4},
  large:  {h: 96, std: 8, inner: 16, pressedInner: 12},
  xlarge: {h: 136, std: 8, inner: 20, pressedInner: 16},
};
const CONNECTED_GAP = 2, PRESSED_WIDTH = 0.15;
const PREFIXES = ['elevated-button','filled-button','filled-tonal-button','outlined-button','text-button',
  'icon-button','filled-icon-button','filled-tonal-icon-button','outlined-icon-button'];
export class MdButtonGroup extends LitElement {
  static properties = {
    variant: {type: String, reflect: true},    // standard | connected
    size: {type: String, reflect: true},       // xsmall | small | medium | large | xlarge
    selection: {type: String, reflect: true},  // none | single | multiple (connected 의 토글)
  };
  static styles = css`
    :host { display: inline-flex; flex-wrap: nowrap; align-items: center; } /* 그룹은 줄바꿈하지 않는다 */
    :host([variant='connected']) { display: flex; }
    :host([variant='connected']) ::slotted(*) { flex: 1 1 0; }
    ::slotted(*) { transition: width 150ms cubic-bezier(0.2, 0, 0, 1), flex-basis 150ms cubic-bezier(0.2, 0, 0, 1); }
  `;
  constructor() {
    super(); this.variant = 'standard'; this.size = 'small'; this.selection = 'none';
    this.addEventListener('pointerdown', (e) => this._press(e, true));
    ['pointerup','pointercancel','pointerleave'].forEach(t => this.addEventListener(t, (e) => this._press(e, false)));
    this.addEventListener('click', (e) => this._click(e));
  }
  get buttons() { return Array.from(this.children); }
  get selectedIndices() { return this.buttons.map((b, i) => b.hasAttribute('selected') ? i : -1).filter(i => i >= 0); }
  _item(e) { return this.buttons.find(b => b === e.target || b.contains(e.target)); }
  _press(e, down) {
    const b = down ? this._item(e) : this._pressed;
    if (!b) return;
    if (down) { this._pressed = b; b.setAttribute('data-pressed', ''); }
    else { b.removeAttribute('data-pressed'); this._pressed = null; }
    this._layout();
  }
  _click(e) {
    if (this.selection === 'none') return;
    const b = this._item(e);
    if (!b || b.disabled) return;
    const on = !b.hasAttribute('selected');
    if (this.selection === 'single') { if (!on) return; this.buttons.forEach(x => this._select(x, x === b)); }
    else this._select(b, on);
    this._layout();
    this.dispatchEvent(new CustomEvent('selection-changed', {detail: {selected: this.selectedIndices}, bubbles: true, composed: true}));
  }
  _select(b, on) { if (on) b.setAttribute('selected', ''); else b.removeAttribute('selected'); if ('selected' in b) b.selected = on; b.setAttribute('aria-pressed', on ? 'true' : 'false'); }
  updated() { this._layout(); }
  _layout() {
    const s = GROUP[this.size] || GROUP.small, list = this.buttons, connected = this.variant === 'connected';
    this.style.gap = (connected ? CONNECTED_GAP : s.std) + 'px';
    this.setAttribute('role', this.selection === 'none' ? 'group' : 'toolbar');
    const full = s.h / 2 + 'px';
    list.forEach((b, i) => {
      PREFIXES.forEach(p => b.style.setProperty(`--md-${p}-container-height`, s.h + 'px'));
      if (connected) {
        const pressed = b.hasAttribute('data-pressed'), sel = b.hasAttribute('selected');
        const inner = sel ? full : (pressed ? s.pressedInner : s.inner) + 'px'; // selected inner 50%
        const first = i === 0, last = i === list.length - 1;
        const startC = first ? full : inner, endC = last ? full : inner;
        PREFIXES.forEach(p => {
          b.style.setProperty(`--md-${p}-container-shape-start-start`, startC);
          b.style.setProperty(`--md-${p}-container-shape-end-start`, startC);
          b.style.setProperty(`--md-${p}-container-shape-start-end`, endC);
          b.style.setProperty(`--md-${p}-container-shape-end-end`, endC);
        });
        b.style.width = '';
      } else {
        // standard: 눌린 버튼이 15% 넓어지고 이웃이 그만큼 양보한다
        if (b.hasAttribute('data-pressed')) { if (!b.dataset.w) b.dataset.w = b.getBoundingClientRect().width; b.style.width = (b.dataset.w * (1 + PRESSED_WIDTH)) + 'px'; }
        else { b.style.width = ''; delete b.dataset.w; }
      }
    });
  }
  render() { return html`<slot @slotchange=${() => this._layout()}></slot>`; }
}
customElements.define('md-button-group', MdButtonGroup);
