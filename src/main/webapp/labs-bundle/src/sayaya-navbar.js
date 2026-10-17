// sayaya-ui 자체 구현 M3 flexible navigation bar (labs md-navigation-bar 는 비권장 baseline 이라 대체)
// 출처: 토큰 _md-comp-nav-bar / _md-comp-nav-bar-item-vertical / _md-comp-nav-bar-item-horizontal (@material/web 2.5.0)
//       Compose Material3 ShortNavigationBar.kt (배치·패딩 상수), m3.material.io/components/navigation-bar (specs/guidelines)
// API 는 labs md-navigation-bar / md-navigation-tab 과 호환(activeIndex, label, badgeValue, showBadge, 이벤트 이름).
import {LitElement, html, css, nothing} from 'lit';

const LEVEL2 = '0 1px 2px 0 rgba(0,0,0,.3), 0 2px 6px 2px rgba(0,0,0,.15)';

export class MdNavigationBar extends LitElement {
  static properties = {
    activeIndex: {type: Number, attribute: 'active-index', reflect: true},
    hideInactiveLabels: {type: Boolean, attribute: 'hide-inactive-labels'}, // labs 호환. M3: 라벨을 제거하지 말 것
    layout: {type: String, reflect: true},          // auto(기본) | vertical | horizontal
    resolvedLayout: {type: String, attribute: 'resolved-layout', reflect: true},
  };
  static styles = css`
    :host { display: flex; box-sizing: border-box; width: 100%; height: 64px;  /* 토큰 container-height */
      background: var(--md-sys-color-surface-container, #f3edf7); box-shadow: ${css([LEVEL2])}; }
    .items { display: flex; flex: 1; min-width: 0; height: 100%; }
    :host([resolved-layout='vertical']) ::slotted(md-navigation-tab) { flex: 1 1 0; }      /* EqualWeight */
    :host([resolved-layout='horizontal']) .items { justify-content: center; gap: 0; }     /* Centered */
  `;
  constructor() {
    super();
    this.activeIndex = 0; this.hideInactiveLabels = false; this.layout = 'auto'; this.resolvedLayout = 'vertical';
    this._ro = new ResizeObserver(() => this._resolve());
    this.addEventListener('navigation-tab-interaction', (e) => {
      const i = this.tabs.indexOf(e.target);
      if (i < 0 || e.target.disabled) return;
      this.activeIndex = i;
    });
  }
  get tabs() { return Array.from(this.querySelectorAll(':scope > md-navigation-tab')); }
  connectedCallback() { super.connectedCallback(); this._ro.observe(this); }
  disconnectedCallback() { super.disconnectedCallback(); this._ro.disconnect(); }
  _resolve() {
    // compact(<600dp) 는 세로형, medium 은 가로형 (가이드 Configurations)
    const r = this.layout === 'auto' ? (this.getBoundingClientRect().width >= 600 ? 'horizontal' : 'vertical') : this.layout;
    if (r !== this.resolvedLayout) this.resolvedLayout = r;
    this._sync();
  }
  updated(changed) {
    if (changed.has('layout')) this._resolve();
    this._sync();
    if (changed.has('activeIndex') && changed.get('activeIndex') !== undefined) {
      this.dispatchEvent(new CustomEvent('navigation-bar-activated',
        {detail: {tab: this.tabs[this.activeIndex], activeIndex: this.activeIndex}, bubbles: true, composed: true}));
    }
  }
  _sync() {
    const tabs = this.tabs, n = tabs.length;
    tabs.forEach((t, i) => { t.active = i === this.activeIndex; t.layout = this.resolvedLayout; t.hideInactiveLabel = this.hideInactiveLabels; });
    // Centered: 양옆 여백 = (100 - 10 * (n + 3)) / 2 %  (Compose calculateCenteredContentHorizontalPadding)
    const pad = this.resolvedLayout === 'horizontal' && n > 0 && n <= 6 ? (100 - 10 * (n + 3)) / 2 : 0;
    this.style.paddingInline = pad > 0 ? pad + '%' : '';
  }
  render() { return html`<nav class="items" role="tablist"><slot @slotchange=${() => this._sync()}></slot></nav>`; }
}
customElements.define('md-navigation-bar', MdNavigationBar);

export class MdNavigationTab extends LitElement {
  static properties = {
    active: {type: Boolean, reflect: true},
    disabled: {type: Boolean, reflect: true},
    hideInactiveLabel: {type: Boolean, attribute: 'hide-inactive-label'},
    label: {type: String},
    badgeValue: {type: String, attribute: 'badge-value'},
    showBadge: {type: Boolean, attribute: 'show-badge'},
    layout: {type: String, reflect: true},
  };
  static styles = css`
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
    .badge { position: absolute; top: -2px; left: 14px; box-sizing: border-box; min-width: 16px; height: 16px; padding: 0 4px; border-radius: 8px;
      background: var(--md-sys-color-error, #b3261e); color: var(--md-sys-color-on-error, #fff);
      font-family: var(--md-sys-typescale-label-small-font, Roboto, sans-serif); font-size: var(--md-sys-typescale-label-small-size, 11px);
      line-height: 16px; text-align: center; }
    .badge.small { min-width: 6px; width: 6px; height: 6px; padding: 0; border-radius: 3px; top: 0; left: 18px; }
  `;
  constructor() { super(); this.active = false; this.disabled = false; this.hideInactiveLabel = false; this.label = ''; this.badgeValue = ''; this.showBadge = false; this.layout = 'vertical'; }
  focus() { this.shadowRoot?.querySelector('button')?.focus(); }
  _click() { if (this.disabled) return; this.dispatchEvent(new CustomEvent('navigation-tab-interaction', {detail: {state: this}, bubbles: true, composed: true})); }
  render() {
    const hasActive = !!this.querySelector(':scope > [slot="active-icon"]');
    const horizontal = this.layout === 'horizontal';
    const icon = html`<span class="icon"><slot name="active-icon"></slot><slot name="inactive-icon" class=${hasActive ? 'has-active' : ''}></slot>${this.showBadge
      ? html`<span class="badge ${this.badgeValue ? '' : 'small'}" part="badge">${this.badgeValue}</span>` : nothing}</span>`;
    const label = html`<span class="label ${this.hideInactiveLabel && !this.active ? 'hidden' : ''}" part="label">${this.label}</span>`;
    return html`<button role="tab" aria-selected=${this.active ? 'true' : 'false'} aria-disabled=${this.disabled ? 'true' : 'false'}
        aria-label=${this.label || nothing} @click=${this._click}>
      ${horizontal ? html`<span class="indicator" part="indicator">${icon}${label}</span>`
                   : html`<span class="indicator" part="indicator">${icon}</span>${label}`}
    </button>`;
  }
}
customElements.define('md-navigation-tab', MdNavigationTab);
