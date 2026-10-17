// sayaya-ui 자체 구현 M3 컴포넌트 (Material Web 2.5.0 에 컴포넌트가 없음)
//   md-tooltip      : plain / rich tooltip
//   md-snackbar     : snackbar
//   md-button-group : standard / connected button group
// 수치 출처: @material/web/tokens/versions/latest/sass/_md-comp-{plain-tooltip,rich-tooltip,snackbar,button-group-*}.scss
// [토큰 외] 값은 토큰에 없어 m3.material.io 스펙 페이지(tooltips/specs)와 Compose Material3(Tooltip.kt, Snackbar.kt) 상수로 대조한 값이다.
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
    :host { position: fixed; z-index: 1000; display: none; pointer-events: none; box-sizing: border-box; min-width: 40px; /* [토큰 외] Compose TooltipMinWidth */ }
    :host([open]) { display: block; }
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
    this.variant = 'plain'; this.position = ''; // '' = 자동: plain 위쪽, rich 오른쪽 아래(가이드) this.delay = 500; this.open = false; this.persistent = false;
    this._anchor = null; this._timer = 0; this._hide = 0;
    this._enter = () => this._schedule(true);
    this._leave = () => this._schedule(false);
    this._key = (e) => { if (e.key === 'Escape') this.hide(); };
    // persistent rich: 클릭으로 열고, 다른 요소와 상호작용하면 닫는다(가이드 Persistent rich tooltips)
    this._click = () => { if (this._isPersistent()) { this.open ? this.hide() : this.show(); } };
    this._outside = (e) => {
      const path = e.composedPath ? e.composedPath() : [];
      if (path.includes(this) || (this._anchor && path.includes(this._anchor))) return;
      this.hide();
    };
  }
  get anchor() { return this._anchor; }
  set anchor(el) { this._bind(el); }
  connectedCallback() { super.connectedCallback(); if (!this._anchor && this.for) this._bind(this.getRootNode().getElementById?.(this.for) ?? document.getElementById(this.for)); }
  disconnectedCallback() { super.disconnectedCallback(); this._bind(null); if (currentTooltip === this) currentTooltip = null; }
  updated(changed) { if (changed.has('for') && this.isConnected && this.for) this._bind(document.getElementById(this.for)); }
  _bind(el) {
    const a = this._anchor;
    if (a) { ['pointerenter','focusin'].forEach(t => a.removeEventListener(t, this._enter)); ['pointerleave','focusout'].forEach(t => a.removeEventListener(t, this._leave)); a.removeEventListener('keydown', this._key); a.removeEventListener('click', this._click); }
    this._anchor = el || null;
    if (el) { ['pointerenter','focusin'].forEach(t => el.addEventListener(t, this._enter)); ['pointerleave','focusout'].forEach(t => el.addEventListener(t, this._leave)); el.addEventListener('keydown', this._key); el.addEventListener('click', this._click);
      if (this.variant === 'plain' && !el.hasAttribute('aria-describedby') && this.id) el.setAttribute('aria-describedby', this.id); }
  }
  _isPersistent() { return this.variant === 'rich' && this.persistent; }
  _schedule(show) {
    if (this._isPersistent()) return; // persistent rich 는 hover/포커스로 열고 닫지 않는다
    clearTimeout(this._timer); clearTimeout(this._hide);
    if (show) this._timer = setTimeout(() => this.show(), this.delay);
    else this._hide = setTimeout(() => this.hide(), 1500); // 가이드: 대상을 벗어나고 1.5초 뒤 사라진다
  }
  /** 즉시 표시. autoHideMs > 0 이면 그 뒤 닫는다(handbook showImmediate). */
  show(autoHideMs = 0) {
    if (currentTooltip && currentTooltip !== this) currentTooltip.hide();
    currentTooltip = this;
    this.setAttribute('role', 'tooltip');
    this.open = true;
    if (this._isPersistent()) setTimeout(() => { document.addEventListener('pointerdown', this._outside, true); document.addEventListener('focusin', this._outside, true); });
    this.updateComplete.then(() => this._place());
    this.dispatchEvent(new Event('opened', {bubbles: true, composed: true}));
    if (autoHideMs > 0) { clearTimeout(this._hide); this._hide = setTimeout(() => this.hide(), autoHideMs); }
  }
  hide() {
    clearTimeout(this._timer); clearTimeout(this._hide);
    document.removeEventListener('pointerdown', this._outside, true); document.removeEventListener('focusin', this._outside, true);
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
    :host { position: fixed; z-index: 1001; left: 50%; bottom: 8px; transform: translateX(-50%); /* [토큰 외] MDC-Android m3_snackbar_margin 8dp */
      display: none; box-sizing: border-box; width: max-content; max-width: min(600px, calc(100vw - 16px)); /* [토큰 외] Compose ContainerMaxWidth 600dp, 좌우 여백 m3_snackbar_margin 8dp */ }
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
// 출처: 토큰 _md-comp-button-group-{standard,connected}-{size}.scss, _md-comp-button-{size}.scss,
//       _md-comp-button-{outlined,filled,tonal}.scss (selected 색), m3.material.io/components/button-groups (specs/guidelines)
const GROUP = {
  //        높이   standard 간격  connected 안쪽(가이드)  connected 누름 안쪽  standard 누름 모양  선택된 둥근 버튼 모양
  xsmall: {h: 32, std: 18, inner: 4,  pressedInner: 4,  pressed: 8,  selected: 12},
  small:  {h: 40, std: 12, inner: 8,  pressedInner: 4,  pressed: 8,  selected: 12},
  medium: {h: 56, std: 8,  inner: 8,  pressedInner: 4,  pressed: 12, selected: 16},
  large:  {h: 96, std: 8,  inner: 16, pressedInner: 12, pressed: 16, selected: 28},
  xlarge: {h: 136, std: 8, inner: 20, pressedInner: 16, pressed: 16, selected: 28},
};
const CONNECTED_GAP = 2, PRESSED_WIDTH = 0.15, CONNECTED_MIN_WIDTH = 48; // XS·S connected: 최소 너비 48dp
const PREFIXES = ['elevated-button','filled-button','filled-tonal-button','outlined-button','text-button',
  'icon-button','filled-icon-button','filled-tonal-icon-button','outlined-icon-button'];
// 토글(선택) 색: [unselected container, unselected label, selected container, selected label]
const TOGGLE = {
  'MD-FILLED-BUTTON': ['filled-button', 'surface-container', 'on-surface-variant', 'primary', 'on-primary'],
  'MD-FILLED-ICON-BUTTON': ['filled-icon-button', 'surface-container', 'on-surface-variant', 'primary', 'on-primary'],
  'MD-FILLED-TONAL-BUTTON': ['filled-tonal-button', 'secondary-container', 'on-secondary-container', 'secondary', 'on-secondary'],
  'MD-FILLED-TONAL-ICON-BUTTON': ['filled-tonal-icon-button', 'secondary-container', 'on-secondary-container', 'secondary', 'on-secondary'],
  'MD-OUTLINED-BUTTON': ['outlined-button', null, 'on-surface-variant', 'inverse-surface', 'inverse-on-surface'],
  'MD-OUTLINED-ICON-BUTTON': ['outlined-icon-button', null, 'on-surface-variant', 'inverse-surface', 'inverse-on-surface'],
};
const SYS = {
  'surface-container': '#f3edf7', 'on-surface-variant': '#49454f', 'primary': '#6750a4', 'on-primary': '#ffffff',
  'secondary-container': '#e8def8', 'on-secondary-container': '#1d192b', 'secondary': '#625b71', 'on-secondary': '#ffffff',
  'inverse-surface': '#322f35', 'inverse-on-surface': '#f5eff7',
};
const sys = (role) => `var(--md-sys-color-${role}, ${SYS[role]})`;
export class MdButtonGroup extends LitElement {
  static properties = {
    variant: {type: String, reflect: true},    // standard | connected
    size: {type: String, reflect: true},       // xsmall | small | medium | large | xlarge
    selection: {type: String, reflect: true},  // none | single | multiple. 지정하지 않으면 connected=single, standard=none
  };
  static styles = css`
    :host { display: inline-flex; flex-wrap: nowrap; align-items: center; } /* 그룹은 줄바꿈하지 않는다 */
    :host([variant='connected']) { display: flex; }
    :host([variant='connected']) ::slotted(*) { flex: 1 1 0; }
    ::slotted(*) { flex: none; transition: width 150ms cubic-bezier(0.2, 0, 0, 1), flex-basis 150ms cubic-bezier(0.2, 0, 0, 1); }
  `;
  constructor() {
    super(); this.variant = 'standard'; this.size = 'small';
    this.addEventListener('pointerdown', (e) => this._press(e, true));
    ['pointerup','pointercancel','pointerleave'].forEach(t => this.addEventListener(t, (e) => this._press(e, false)));
    this.addEventListener('click', (e) => this._click(e));
  }
  /** 실제 선택 방식. connected 는 토글 전용이라(가이드) 지정이 없으면 single. */
  get mode() { return this.selection || (this.variant === 'connected' ? 'single' : 'none'); }
  get buttons() { return Array.from(this.children); }
  get selectedIndices() { return this.buttons.map((b, i) => b.hasAttribute('selected') ? i : -1).filter(i => i >= 0); }
  _item(e) { return this.buttons.find(b => b === e.target || b.contains(e.target)); }
  _press(e, down) {
    const b = down ? this._item(e) : this._pressed;
    if (!b) return;
    if (down) {
      // standard: 누르기 직전 너비를 기억해 두고, 눌린 버튼 +15% 만큼 양옆 버튼이 나눠서 줄어든다(그룹 너비 유지)
      if (this.variant !== 'connected') this._widths = this.buttons.map(x => x.getBoundingClientRect().width);
      this._pressed = b; b.setAttribute('data-pressed', '');
    } else { b.removeAttribute('data-pressed'); this._pressed = null; this._widths = null; }
    this._layout();
  }
  _click(e) {
    const mode = this.mode;
    if (mode === 'none') return;
    const b = this._item(e);
    if (!b || b.disabled) return;
    const on = !b.hasAttribute('selected');
    if (mode === 'single') { if (!on) return; this.buttons.forEach(x => this._select(x, x === b)); }
    else this._select(b, on);
    this._layout();
    this.dispatchEvent(new CustomEvent('selection-changed', {detail: {selected: this.selectedIndices}, bubbles: true, composed: true}));
  }
  // Material 버튼은 host 의 aria-* 를 data-aria-* 로 옮겨 내부 버튼에 위임하므로 ariaPressed 속성(property)으로 설정한다
  _pressedAria(b, v) { if ('ariaPressed' in b) b.ariaPressed = v; else b.setAttribute('aria-pressed', v); }
  _select(b, on) { if (on) b.setAttribute('selected', ''); else b.removeAttribute('selected'); this._pressedAria(b, on ? 'true' : 'false'); }
  updated() { this._layout(); }
  _shape(b, ss, es, se, ee) {
    PREFIXES.forEach(p => {
      b.style.setProperty(`--md-${p}-container-shape-start-start`, ss);
      b.style.setProperty(`--md-${p}-container-shape-end-start`, es);
      b.style.setProperty(`--md-${p}-container-shape-start-end`, se);
      b.style.setProperty(`--md-${p}-container-shape-end-end`, ee);
    });
  }
  _color(b, toggle) {
    const t = TOGGLE[b.tagName];
    if (!t) return;
    const [p, uc, ul, sc, sl] = t, sel = toggle && b.hasAttribute('selected');
    const outlined = uc === null;
    if (!toggle) {
      ['container-color','label-text-color','icon-color','outline-color'].forEach(k => b.style.removeProperty(`--md-${p}-${k}`));
      if (outlined) { b.style.removeProperty('background-color'); }
      return;
    }
    const label = sys(sel ? sl : ul);
    b.style.setProperty(`--md-${p}-label-text-color`, label);
    b.style.setProperty(`--md-${p}-icon-color`, label);
    if (outlined) {
      // outlined 버튼에는 컨테이너 색 변수가 없어 host 배경으로 채운다(host 는 container-shape 로 둥글게 깎인다)
      b.style.setProperty('background-color', sel ? sys(sc) : 'transparent');
      b.style.setProperty(`--md-${p}-outline-color`, sel ? 'transparent' : '');
      if (!sel) b.style.removeProperty(`--md-${p}-outline-color`);
    } else b.style.setProperty(`--md-${p}-container-color`, sys(sel ? sc : uc));
  }
  _layout() {
    const s = GROUP[this.size] || GROUP.small, list = this.buttons, connected = this.variant === 'connected';
    const mode = this.mode, toggle = mode !== 'none';
    this.style.gap = (connected ? CONNECTED_GAP : s.std) + 'px';
    this.setAttribute('role', toggle ? 'toolbar' : 'group');
    const full = s.h / 2 + 'px';
    const pi = list.findIndex(b => b.hasAttribute('data-pressed'));
    list.forEach((b, i) => {
      PREFIXES.forEach(p => b.style.setProperty(`--md-${p}-container-height`, s.h + 'px'));
      if (toggle && !b.hasAttribute('selected') && b.ariaPressed !== 'false') this._pressedAria(b, 'false');
      this._color(b, toggle);
      const pressed = i === pi, sel = toggle && b.hasAttribute('selected');
      if (connected) {
        // connected: 이웃은 반응하지 않고, 누른/선택한 버튼의 안쪽 모서리만 바뀐다(선택 시 50%)
        const inner = sel ? full : (pressed ? s.pressedInner : s.inner) + 'px';
        const startC = i === 0 ? full : inner, endC = i === list.length - 1 ? full : inner;
        this._shape(b, startC, startC, endC, endC);
        b.style.width = '';
        b.style.minWidth = (this.size === 'xsmall' || this.size === 'small') ? CONNECTED_MIN_WIDTH + 'px' : '';
      } else {
        // standard: 누르면 그 버튼 모양이 pressed 로, 선택되면 둥근 모양이 각진 모양으로 바뀐다
        const r = (pressed ? s.pressed : sel ? s.selected : null);
        const c = r === null ? full : r + 'px';
        this._shape(b, c, c, c, c);
        b.style.minWidth = '0';
        if (pi >= 0 && this._widths) {
          const w = this._widths, delta = w[pi] * PRESSED_WIDTH;
          const nb = [pi - 1, pi + 1].filter(k => k >= 0 && k < list.length);
          const width = i === pi ? w[i] + delta : nb.includes(i) ? w[i] - delta / nb.length : w[i];
          b.style.width = width + 'px';
        } else b.style.width = '';
      }
    });
  }
  render() { return html`<slot @slotchange=${() => this._layout()}></slot>`; }
}
customElements.define('md-button-group', MdButtonGroup);
