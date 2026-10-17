// sayaya-ui 자체 구현 M3 컴포넌트 (Material Web 2.5.0 에 없음)
// - md-top-app-bar         : M3 Top app bar (small / center-aligned / medium / large)
// - md-navigation-rail     : M3 Navigation rail (80dp)
// - md-navigation-rail-item: rail 목적지 항목
// 색/타이포는 --md-sys-* 토큰을 사용하므로 Material 테마를 그대로 따른다.
import {LitElement, html, css} from 'lit';

export class MdTopAppBar extends LitElement {
  static properties = {
    variant: {type: String, reflect: true},
    scrolled: {type: Boolean, reflect: true},
    autoScroll: {type: Boolean, attribute: 'auto-scroll', reflect: true},
  };
  static styles = css`
    :host {
      display: flex; flex-direction: column; box-sizing: border-box;
      background: var(--md-sys-color-surface, #fef7ff);
      color: var(--md-sys-color-on-surface, #1d1b20);
      transition: background-color 200ms linear;
    }
    :host([hidden]) { display: none; }
    :host([scrolled]) { background: var(--md-sys-color-surface-container, #f3edf7); }
    .row { display: flex; align-items: center; height: 64px; padding: 0 4px; gap: 4px; }
    .leading, .trailing { display: flex; align-items: center; flex: none; }
    .trailing { margin-inline-start: auto; }
    .headline {
      flex: 1; min-width: 0; padding-inline: 12px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;
      font: var(--md-sys-typescale-title-large-weight, 400) var(--md-sys-typescale-title-large-size, 22px)/var(--md-sys-typescale-title-large-line-height, 28px) var(--md-sys-typescale-title-large-font, Roboto, sans-serif);
    }
    :host([variant='center-aligned']) .headline { text-align: center; }
    .expanded { display: none; padding: 0 16px; overflow: hidden; }
    :host([variant='medium']) .expanded, :host([variant='large']) .expanded { display: flex; align-items: flex-end; }
    :host([variant='medium']) .row .headline, :host([variant='large']) .row .headline { visibility: hidden; }
    :host([variant='medium']) .expanded { height: 48px; padding-bottom: 20px;
      font: var(--md-sys-typescale-headline-small-weight, 400) var(--md-sys-typescale-headline-small-size, 24px)/var(--md-sys-typescale-headline-small-line-height, 32px) var(--md-sys-typescale-headline-small-font, Roboto, sans-serif); }
    :host([variant='large']) .expanded { height: 88px; padding-bottom: 28px;
      font: var(--md-sys-typescale-headline-medium-weight, 400) var(--md-sys-typescale-headline-medium-size, 28px)/var(--md-sys-typescale-headline-medium-line-height, 36px) var(--md-sys-typescale-headline-medium-font, Roboto, sans-serif); }
  `;
  constructor() {
    super();
    this.variant = 'small';
    this.scrolled = false;
    this.autoScroll = false;
    this._onScroll = () => { this.scrolled = window.scrollY > 0; };
  }
  updated(changed) {
    if (changed.has('autoScroll')) {
      window.removeEventListener('scroll', this._onScroll);
      if (this.autoScroll) { window.addEventListener('scroll', this._onScroll, {passive: true}); this._onScroll(); }
    }
  }
  disconnectedCallback() { super.disconnectedCallback(); window.removeEventListener('scroll', this._onScroll); }
  render() {
    return html`
      <div class="row" part="row">
        <div class="leading"><slot name="leading"></slot></div>
        <div class="headline" part="headline"><slot name="headline"></slot></div>
        <div class="trailing"><slot name="trailing"></slot></div>
      </div>
      <div class="expanded" part="expanded-headline"><slot name="expanded-headline"></slot></div>`;
  }
}
customElements.define('md-top-app-bar', MdTopAppBar);

export class MdNavigationRail extends LitElement {
  static properties = {
    activeIndex: {type: Number, attribute: 'active-index', reflect: true},
    alignment: {type: String, reflect: true},
    expanded: {type: Boolean, reflect: true},
  };
  static styles = css`
    :host {
      display: flex; flex-direction: column; align-items: center; box-sizing: border-box;
      width: 80px; height: 100%; padding: 44px 0 56px; gap: 12px;
      background: var(--md-sys-color-surface, #fef7ff);
    }
    .header { display: flex; flex-direction: column; align-items: center; gap: 8px; }
    .items { display: flex; flex-direction: column; align-items: center; gap: 12px; flex: 1; }
    :host([alignment='center']) .items { justify-content: center; }
    :host([alignment='bottom']) .items { justify-content: flex-end; }
    :host([hidden]) { display: none; }
    :host([expanded]) { width: 220px; align-items: stretch; padding-inline: 12px; }
    :host([expanded]) .header, :host([expanded]) .items { align-items: stretch; }
  `;
  constructor() {
    super();
    this.activeIndex = 0;
    this.alignment = 'top';
    this.expanded = false;
    this.addEventListener('navigation-rail-item-interaction', (e) => {
      const index = this.items.indexOf(e.target);
      if (index < 0 || e.target.disabled) return;
      this.activeIndex = index;
      this.dispatchEvent(new CustomEvent('navigation-rail-activated', {detail: {activeIndex: index, item: e.target}, bubbles: true, composed: true}));
    });
  }
  get items() { return Array.from(this.querySelectorAll(':scope > md-navigation-rail-item')); }
  updated() { this._sync(); }
  _sync() { this.items.forEach((item, i) => { item.active = i === this.activeIndex; item.expanded = this.expanded; }); }
  render() {
    return html`
      <div class="header"><slot name="header"></slot></div>
      <div class="items" role="tablist" aria-orientation="vertical"><slot @slotchange=${() => this._sync()}></slot></div>`;
  }
}
customElements.define('md-navigation-rail', MdNavigationRail);

export class MdNavigationRailItem extends LitElement {
  static properties = {
    active: {type: Boolean, reflect: true},
    disabled: {type: Boolean, reflect: true},
    label: {type: String},
    badgeValue: {type: String, attribute: 'badge-value'},
    showBadge: {type: Boolean, attribute: 'show-badge'},
    expanded: {type: Boolean, reflect: true},
  };
  static styles = css`
    :host { display: block; width: 80px; }
    button {
      all: unset; box-sizing: border-box; width: 100%; display: flex; flex-direction: column; align-items: center; gap: 4px;
      cursor: pointer; color: var(--md-sys-color-on-surface-variant, #49454f);
      font: var(--md-sys-typescale-label-medium-weight, 500) var(--md-sys-typescale-label-medium-size, 12px)/var(--md-sys-typescale-label-medium-line-height, 16px) var(--md-sys-typescale-label-medium-font, Roboto, sans-serif);
    }
    button:focus-visible .indicator { outline: 3px solid var(--md-sys-color-secondary, #625b71); outline-offset: 2px; }
    :host([disabled]) button { cursor: default; opacity: 0.38; }
    .indicator {
      position: relative; display: flex; align-items: center; justify-content: center;
      width: 56px; height: 32px; border-radius: 16px;
    }
    :host([active]) .indicator { background: var(--md-sys-color-secondary-container, #e8def8); color: var(--md-sys-color-on-secondary-container, #1d192b); }
    :host([active]) button { color: var(--md-sys-color-on-surface, #1d1b20); }
    :host(:not([active])) slot[name='active-icon'], :host([active]) slot[name='inactive-icon'].has-active { display: none; }
    .badge {
      position: absolute; top: 2px; left: 32px; min-width: 16px; height: 16px; padding: 0 4px; box-sizing: border-box;
      border-radius: 8px; background: var(--md-sys-color-error, #b3261e); color: var(--md-sys-color-on-error, #fff);
      font-size: 11px; line-height: 16px; text-align: center;
    }
    :host([expanded]) { width: auto; }
    :host([expanded]) button { flex-direction: row; height: 56px; padding-inline: 16px; gap: 12px; border-radius: 28px; }
    :host([expanded][active]) button { background: var(--md-sys-color-secondary-container, #e8def8); color: var(--md-sys-color-on-secondary-container, #1d192b); }
    :host([expanded]) .indicator { width: 24px; height: 24px; background: none !important; }
    .badge.dot { min-width: 6px; width: 6px; height: 6px; padding: 0; top: 4px; left: 36px; }
    /* 펼친 행: 점 배지는 24px 아이콘의 오른쪽 위, 숫자 배지는 라벨 뒤 행 끝(라벨과 겹치지 않게) */
    :host([expanded]) .badge.dot { top: 0; left: 18px; }
    .badge.trailing { position: static; flex: none; margin-inline-start: auto; }
    :host([expanded]) .label { min-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; }
  `;
  constructor() {
    super();
    this.active = false; this.disabled = false; this.label = ''; this.badgeValue = ''; this.showBadge = false; this.expanded = false;
  }
  render() {
    const hasActive = !!this.querySelector(':scope > [slot="active-icon"]');
    const trailing = this.expanded && this.showBadge && !!this.badgeValue;
    return html`
      <button role="tab" aria-selected=${this.active ? 'true' : 'false'} aria-disabled=${this.disabled ? 'true' : 'false'}
              aria-label=${this.label || ''} @click=${this._click}>
        <span class="indicator">
          <slot name="active-icon"></slot>
          <slot name="inactive-icon" class=${hasActive ? 'has-active' : ''}></slot>
          ${this.showBadge && !trailing ? html`<span class="badge ${this.badgeValue ? '' : 'dot'}" part="badge">${this.badgeValue}</span>` : ''}
        </span>
        <span class="label">${this.label}</span>
        ${trailing ? html`<span class="badge trailing" part="badge">${this.badgeValue}</span>` : ''}
      </button>`;
  }
  focus() { this.shadowRoot?.querySelector('button')?.focus(); }
  _click() {
    if (this.disabled) return;
    this.dispatchEvent(new CustomEvent('navigation-rail-item-interaction', {bubbles: true, composed: true}));
  }
}
customElements.define('md-navigation-rail-item', MdNavigationRailItem);
