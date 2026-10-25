// sayaya-ui 자체 구현 M3 컴포넌트 (Material Web 2.5.0 에 없음)
// - md-top-app-bar         : M3 Expressive app bar (small / medium flexible / large flexible, centered, subtitle)
// - md-navigation-rail     : M3 Expressive navigation rail (collapsed 96dp / expanded 220~360dp, standard·modal)
// - md-navigation-rail-item: rail 목적지 항목
// 출처: 토큰 _md-comp-app-bar*.scss, _md-comp-top-app-bar-*.scss, _md-comp-nav-rail*.scss (@material/web 2.5.0)
//       Compose Material3 WideNavigationRail.kt, m3.material.io/components/{app-bars,navigation-rail} (specs/guidelines)
// [파생] 표시 값은 공식 높이(112/136/120/152)를 맞추도록 줄높이에서 역산한 패딩이다(스펙 측정 이미지는 텍스트로 읽을 수 없음).
import {LitElement, html, css, nothing} from 'lit';

// 메뉴 버튼 기본 아이콘: 아이콘 폰트 없이 그려지는 SVG
// (google/material-design-icons src/navigation/{menu,menu_open}/materialicons/24px.svg 의 path)
const MENU_SVG = html`<svg viewBox="0 0 24 24" width="24" height="24" fill="currentColor" aria-hidden="true" data-icon="menu"><path d="M3 18h18v-2H3v2zm0-5h18v-2H3v2zm0-7v2h18V6H3z"/></svg>`;
const MENU_OPEN_SVG = html`<svg viewBox="0 0 24 24" width="24" height="24" fill="currentColor" aria-hidden="true" data-icon="menu_open"><path d="M3 18h13v-2H3v2zm0-5h10v-2H3v2zm0-7v2h13V6H3zm18 9.59L17.42 12 21 8.41 19.59 7l-5 5 5 5L21 15.59z"/></svg>`;
// 이미 같은 이름이 등록돼 있으면 건너뜀(단독 navigation ESM 번들이 다른 번들과 공존)
const define = (name, ctor) => { if (!customElements.get(name)) customElements.define(name, ctor); };

const ELEV1 = '0 1px 2px 0 rgba(0,0,0,.3), 0 1px 3px 1px rgba(0,0,0,.15)';
const ELEV2 = '0 1px 2px 0 rgba(0,0,0,.3), 0 2px 6px 2px rgba(0,0,0,.15)';
const type = (role, size, lh, weight) => `font-family: var(--md-sys-typescale-${role}-font, Roboto, sans-serif);
  font-size: var(--md-sys-typescale-${role}-size, ${size}px); line-height: var(--md-sys-typescale-${role}-line-height, ${lh}px);
  font-weight: var(--md-sys-typescale-${role}-weight, ${weight}); letter-spacing: var(--md-sys-typescale-${role}-tracking, normal);`;

// ---------------------------------------------------------------- app bar
export class MdTopAppBar extends LitElement {
  static properties = {
    variant: {type: String, reflect: true},          // small | medium | large  (medium/large = M3 Expressive flexible). center-aligned 는 small+centered 로 처리
    centered: {type: Boolean, reflect: true},        // 제목·부제 가운데 정렬 (center-aligned 가 small 에 합쳐진 설정)
    scrolled: {type: Boolean, reflect: true},
    autoScroll: {type: Boolean, attribute: 'auto-scroll', reflect: true},
    collapseOnScroll: {type: Boolean, attribute: 'collapse-on-scroll', reflect: true}, // flexible 앱 바가 스크롤 시 small 로 줄어듦(compress)
    _sub: {state: true},
  };
  static styles = css([`
    :host { display: block; box-sizing: border-box; width: 100%;
      background: var(--md-sys-color-surface, #fef7ff); color: var(--md-sys-color-on-surface, #1d1b20);
      transition: background-color 200ms linear, box-shadow 200ms linear; }
    :host([hidden]) { display: none; }
    /* on-scroll: surface-container + elevation level2 (토큰 on-scroll-container-color/elevation) */
    :host([scrolled]) { background: var(--md-sys-color-surface-container, #f3edf7); box-shadow: ${ELEV2}; }
    /* --md-top-app-bar-container-height: 행(small) 높이, 기본 64px(M3). medium/large 는 '행 높이 + 확장 영역'이라 함께 따라간다 */
    :host([variant='medium']) { min-height: calc(var(--md-top-app-bar-container-height, 64px) + 48px); }   /* medium flexible 112 */
    :host([variant='medium'][has-subtitle]) { min-height: calc(var(--md-top-app-bar-container-height, 64px) + 72px); } /* 136 */
    :host([variant='large']) { min-height: calc(var(--md-top-app-bar-container-height, 64px) + 56px); }    /* large flexible 120 */
    :host([variant='large'][has-subtitle]) { min-height: calc(var(--md-top-app-bar-container-height, 64px) + 88px); } /* 152 */
    .row { display: flex; align-items: center; height: var(--md-top-app-bar-container-height, 64px); padding: 0 4px; box-sizing: border-box; } /* leading/trailing-space 4 */
    .leading, .trailing { display: flex; align-items: center; flex: none; }
    .leading { color: var(--md-sys-color-on-surface, #1d1b20); --md-icon-button-icon-color: var(--md-sys-color-on-surface, #1d1b20); }
    .trailing { margin-inline-start: auto; color: var(--md-sys-color-on-surface-variant, #49454f); --md-icon-button-icon-color: var(--md-sys-color-on-surface-variant, #49454f); }
    ::slotted([slot='leading']) { color: var(--md-sys-color-on-surface, #1d1b20); }
    ::slotted([slot='trailing']) { color: var(--md-sys-color-on-surface-variant, #49454f); }
    .titles { flex: 1; min-width: 0; display: flex; flex-direction: column; justify-content: center; padding-inline: 12px; }
    .headline { ${type('title-large', 22, 28, 400)} color: var(--md-sys-color-on-surface, #1d1b20); min-width: 0; }
    /* 한 줄 말줄임은 텍스트(span)에만 적용한다. 요소(셀렉터·버튼 등)를 넣으면 잘리거나 포커스 링이 가려지지 않는다 */
    ::slotted(span[slot='headline']) { display: block; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; }
    .subtitle { ${type('label-medium', 12, 16, 500)} color: var(--md-sys-color-on-surface-variant, #49454f); }
    .subtitle:not(.has) { display: none; }
    :host([centered]) .titles, :host([centered]) .expanded { text-align: center; }
    .expanded { display: none; padding: 0 16px 12px; }
    :host([variant='medium']) .expanded, :host([variant='large']) .expanded { display: block; }
    :host([variant='medium']) .row .titles, :host([variant='large']) .row .titles { visibility: hidden; }
    /* medium flexible: headline-medium, subtitle label-large. 64 + 36 + 12 = 112, 64 + 36 + 20 + 16 = 136 [파생] */
    :host([variant='medium']) .expanded { ${type('headline-medium', 28, 36, 400)} }
    :host([variant='medium']) .expanded .xsub { ${type('label-large', 14, 20, 500)} }
    :host([variant='medium'][has-subtitle]) .expanded { padding-bottom: 16px; }
    /* large flexible: display-small, subtitle title-medium. 64 + 44 + 12 = 120, 64 + 44 + 24 + 20 = 152 [파생] */
    :host([variant='large']) .expanded { ${type('display-small', 36, 44, 400)} }
    :host([variant='large']) .expanded .xsub { ${type('title-medium', 16, 24, 500)} }
    :host([variant='large'][has-subtitle]) .expanded { padding-bottom: 20px; }
    .xsub { display: block; color: var(--md-sys-color-on-surface-variant, #49454f); }
    .xsub:not(.has) { display: none; }
    /* compress: 스크롤되면 flexible 앱 바가 small 로 줄어든다 */
    :host([collapse-on-scroll][scrolled]) { min-height: 0; }
    :host([collapse-on-scroll][scrolled]) .expanded { display: none; }
    :host([collapse-on-scroll][scrolled]) .row .titles { visibility: visible; }
  `]);
  constructor() {
    super();
    this.variant = 'small'; this.centered = false; this.scrolled = false; this.autoScroll = false; this.collapseOnScroll = false; this._sub = false;
    this._onScroll = () => { this.scrolled = window.scrollY > 0; };
  }
  willUpdate(changed) {
    if (changed.has('variant') && this.variant === 'center-aligned') { this.variant = 'small'; this.centered = true; } // M3 Expressive: center-aligned 는 small 의 설정
  }
  updated(changed) {
    if (changed.has('autoScroll')) {
      window.removeEventListener('scroll', this._onScroll);
      if (this.autoScroll) { window.addEventListener('scroll', this._onScroll, {passive: true}); this._onScroll(); }
    }
  }
  connectedCallback() { super.connectedCallback(); this._checkSub(); }
  disconnectedCallback() { super.disconnectedCallback(); window.removeEventListener('scroll', this._onScroll); }
  _checkSub() { const has = !!this.querySelector(':scope > [slot="subtitle"], :scope > [slot="expanded-subtitle"]'); this._sub = has; this.toggleAttribute('has-subtitle', has); }
  render() {
    const sub = this._sub ? 'has' : '';
    return html`
      <div class="row" part="row">
        <div class="leading"><slot name="leading"></slot></div>
        <div class="titles">
          <div class="headline" part="headline"><slot name="headline"></slot></div>
          <div class="subtitle ${sub}" part="subtitle"><slot name="subtitle" @slotchange=${() => this._checkSub()}></slot></div>
        </div>
        <div class="trailing"><slot name="trailing"></slot></div>
      </div>
      <div class="expanded" part="expanded-headline"><slot name="expanded-headline"></slot>
        <span class="xsub ${sub}"><slot name="expanded-subtitle" @slotchange=${() => this._checkSub()}></slot></span></div>`;
  }
}
define('md-top-app-bar', MdTopAppBar);

// ---------------------------------------------------------------- navigation rail
export class MdNavigationRail extends LitElement {
  static properties = {
    activeIndex: {type: Number, attribute: 'active-index', reflect: true},
    alignment: {type: String, reflect: true},      // top(기본) | center. 메뉴·FAB(header)는 항상 위
    expanded: {type: Boolean, reflect: true},
    narrow: {type: Boolean, reflect: true},        // 접힌 너비 80dp (narrow-container-width)
    modal: {type: Boolean, reflect: true},         // 펼쳤을 때 본문 위에 뜨는 modal expanded rail
    menuOpenLabel: {type: String, attribute: 'menu-open-label'},   // 메뉴 버튼 접근성 라벨(접힘 상태), 기본 'Open menu'
    menuCloseLabel: {type: String, attribute: 'menu-close-label'}, // 메뉴 버튼 접근성 라벨(펼침 상태), 기본 'Close menu'
    menu: {type: Boolean, reflect: true},          // 내장 메뉴 버튼: 펼침/접힘 전환, 펼치면 아이콘이 바뀐다
    hideWhenCollapsed: {type: Boolean, attribute: 'hide-when-collapsed', reflect: true}, // Expanded behavior: hide when collapsed
    hideCollapsedLabels: {type: Boolean, attribute: 'hide-collapsed-labels', reflect: true}, // [M3 외] 접힌 레일에서 라벨을 숨김(아이콘만, 라벨은 툴팁·aria-label)
    _header: {state: true},
    _footer: {state: true},
    _links: {state: true},
  };
  static styles = css([`
    :host { display: flex; flex-direction: column; box-sizing: border-box; flex: none;
      width: var(--md-navigation-rail-collapsed-width, 96px); height: 100%;                     /* collapsed container-width 96 */
      padding: var(--md-navigation-rail-top-space, 44px) 0 var(--md-navigation-rail-bottom-space, 20px); /* top-space 44 */
      background: var(--md-sys-color-surface, #fef7ff); }
    :host([narrow]) { width: var(--md-navigation-rail-narrow-width, 80px); }
    :host([hidden]), :host([hide-when-collapsed]:not([expanded])) { display: none; }
    .header { display: flex; flex-direction: column; align-items: center; gap: 4px; }
    .header.has { margin-bottom: 40px; }                             /* header-space-minimum 40 */
    .items { display: flex; flex-direction: column; align-items: stretch; gap: var(--md-navigation-rail-item-space, 4px); flex: 1; } /* item-vertical-space 4 */
    .footer { display: flex; flex-direction: column; align-items: stretch; gap: var(--md-navigation-rail-item-space, 4px); flex: none; }
    .footer:not(.has) { display: none; }
    :host([alignment='center']) .items { justify-content: center; }
    :host([alignment='bottom']) .items { justify-content: flex-end; }
    :host([expanded]) { width: auto; min-width: var(--md-navigation-rail-expanded-min-width, 220px); max-width: var(--md-navigation-rail-expanded-max-width, 360px);
      padding-inline: var(--md-navigation-rail-expanded-padding-inline, 20px); } /* expanded 220~360, Compose WNRItemHorizontalPadding 20 */
    :host([expanded]) .header { align-items: flex-start; }
    :host([expanded]) .items, :host([expanded]) .footer { gap: var(--md-navigation-rail-expanded-item-space, 0px); } /* expanded between-item-space 0 */
    :host([expanded][modal]) { position: fixed; inset-block: 0; inset-inline-start: 0; z-index: 1001; height: auto;
      background: var(--md-sys-color-surface-container, #f3edf7); box-shadow: ${ELEV2};
      border-start-end-radius: 16px; border-end-end-radius: 16px; }  /* modal-container-color/elevation/shape(corner-large) */
    .scrim { position: fixed; inset: 0; z-index: -1; background: var(--md-sys-color-scrim, #000); opacity: .32; }
  `]);
  constructor() {
    super();
    this.activeIndex = 0; this.alignment = 'top'; this.expanded = false; this.narrow = false; this.modal = false; this.menu = false; this.hideWhenCollapsed = false; this.hideCollapsedLabels = false; this._header = false; this._footer = false; this._links = false;
    this.addEventListener('navigation-rail-item-interaction', (e) => {
      const index = this.items.indexOf(e.target);
      if (index < 0 || e.target.disabled) return;   // footer 항목은 목적지가 아니므로 activeIndex 를 바꾸지 않는다(각자 click 처리)
      this.activeIndex = index;
      const activated = new CustomEvent('navigation-rail-activated',
        {detail: {activeIndex: index, item: e.target, href: e.target.href || null}, bubbles: true, composed: true, cancelable: true});
      this.dispatchEvent(activated);
      // 링크 항목: 앱이 activated 에서 preventDefault() 하면 브라우저 기본 이동을 막는다(앱 라우터가 처리). 안 하면 링크대로 이동
      const original = e.detail && e.detail.originalEvent;
      if (activated.defaultPrevented && original) original.preventDefault();
      if (this.modal && this.expanded) this.toggle(false);
    });
    this.addEventListener('keydown', (e) => {
      if (e.key === 'Escape' && this.modal && this.expanded) { this.toggle(false); return; }
      this._arrowKey(e);
    });
  }
  /** 목적지 항목(기본 슬롯). footer 슬롯의 항목은 activeIndex 대상이 아니다. */
  get items() { return Array.from(this.querySelectorAll(':scope > md-navigation-rail-item:not([slot])')); }
  /**
   * 목적지 사이 키보드 이동: ↓/↑ 다음·이전, Home/End 처음·끝. 첫/끝에서 감기지 않는다. disabled 항목은 건너뛴다.
   * 포커스가 목적지 항목에 있을 때만 동작한다(header·footer 는 대상 아님). Tab 은 기존대로 각 항목을 차례로 지난다.
   */
  _arrowKey(e) {
    if (e.altKey || e.ctrlKey || e.metaKey || e.shiftKey) return;
    const step = {ArrowDown: 1, ArrowUp: -1, Home: 'first', End: 'last'}[e.key];
    if (step === undefined) return;
    const items = this.items;
    const path = e.composedPath();
    const current = items.findIndex((it) => path.includes(it));
    if (current < 0) return;
    let target = null;
    if (step === 'first') target = items.find((it) => !it.disabled);
    else if (step === 'last') target = [...items].reverse().find((it) => !it.disabled);
    else for (let i = current + step; i >= 0 && i < items.length; i += step) { if (!items[i].disabled) { target = items[i]; break; } }
    e.preventDefault();   // 끝에서도 페이지 스크롤로 새지 않게
    if (target && target !== items[current]) target.focus();
  }
  /** 펼침/접힘 전환. expanded-changed 이벤트를 보낸다. */
  toggle(force) {
    const next = force === undefined ? !this.expanded : !!force;
    if (next === this.expanded) return;
    this.expanded = next;
    this.dispatchEvent(new CustomEvent('expanded-changed', {detail: {expanded: next}, bubbles: true, composed: true}));
  }
  updated() { this._sync(); }
  _sync() {
    this.items.forEach((item, i) => { item.active = i === this.activeIndex; });
    this.querySelectorAll(':scope > md-navigation-rail-item').forEach((item) => { item.expanded = this.expanded; item.hideCollapsedLabels = this.hideCollapsedLabels; });
    this._header = this.menu || !!this.querySelector(':scope > [slot="header"]');
    this._footer = !!this.querySelector(':scope > [slot="footer"]');
    this._links = this.items.some((it) => !!it.href);   // 링크 목적지면 tablist 역할을 쓰지 않는다
  }
  render() {
    const menuBtn = this.menu ? html`<md-icon-button class="menu" part="menu" aria-label=${this.expanded ? (this.menuCloseLabel || 'Close menu') : (this.menuOpenLabel || 'Open menu')}
        aria-expanded=${this.expanded ? 'true' : 'false'} @click=${() => this.toggle()}>${this.expanded
          ? html`<slot name="menu-open-icon"><slot name="menu-icon">${MENU_OPEN_SVG}</slot></slot>`
          : html`<slot name="menu-icon">${MENU_SVG}</slot>`}</md-icon-button>` : nothing;
    return html`
      ${this.modal && this.expanded ? html`<div class="scrim" part="scrim" @click=${() => this.toggle(false)}></div>` : nothing}
      <div class="header ${this._header ? 'has' : ''}">${menuBtn}<slot name="header" @slotchange=${() => this._sync()}></slot></div>
      <div class="items" role=${this._links ? nothing : 'tablist'} aria-orientation=${this._links ? nothing : 'vertical'}><slot @slotchange=${() => this._sync()}></slot></div>
      <div class="footer ${this._footer ? 'has' : ''}" part="footer"><slot name="footer" @slotchange=${() => this._sync()}></slot></div>`;
  }
}
define('md-navigation-rail', MdNavigationRail);

export class MdNavigationRailItem extends LitElement {
  static properties = {
    active: {type: Boolean, reflect: true},
    disabled: {type: Boolean, reflect: true},
    label: {type: String},
    href: {type: String, reflect: true},   // 있으면 <a href> 로 그린다(링크 목적지)
    badgeValue: {type: String, attribute: 'badge-value'},
    showBadge: {type: Boolean, attribute: 'show-badge'},
    expanded: {type: Boolean, reflect: true},
    hideCollapsedLabels: {type: Boolean, attribute: 'hide-collapsed-labels', reflect: true}, // 레일이 전파
  };
  static styles = css([`
    :host { display: block; }
    .target { all: unset; box-sizing: border-box; width: 100%; display: flex; cursor: pointer; position: relative;  /* target 은 레일 전체 너비 */
      color: var(--md-sys-color-on-surface-variant, #49454f); }
    :host([disabled]) .target { cursor: default; opacity: 0.38; }
    .indicator { position: relative; display: flex; align-items: center; justify-content: center; box-sizing: border-box; flex: none;
      border-radius: var(--md-sys-shape-corner-full, 9999px); }
    .indicator::before { content: ''; position: absolute; inset: 0; border-radius: inherit; background: currentColor; opacity: 0; pointer-events: none; }
    .target:hover .indicator::before { opacity: var(--md-sys-state-hover-state-layer-opacity, .08); }
    .target:active .indicator::before { opacity: var(--md-sys-state-pressed-state-layer-opacity, .1); }
    .target:focus-visible .indicator { outline: 3px solid var(--md-sys-color-secondary, #625b71); outline-offset: 2px; }
    :host([active]) .indicator { background: var(--md-sys-color-secondary-container, #e8def8); }
    .icon { position: relative; display: flex; width: 24px; height: 24px; --md-icon-size: 24px; color: var(--md-sys-color-on-surface-variant, #49454f); }
    :host([active]) .icon { color: var(--md-sys-color-on-secondary-container, #1d192b); }
    :host(:not([active])) slot[name='active-icon'], :host([active]) slot[name='inactive-icon'].has-active { display: none; }
    .label { ${type('label-medium', 12, 16, 500)} text-align: center; overflow-wrap: anywhere; }  /* 라벨은 줄이지 않는다(필요하면 줄바꿈) */
    /* 세로형(접힌 레일): 높이 64, 위아래 6, indicator 56x32, indicator-라벨 4. 활성 라벨 secondary
       치수 변수는 md-navigation-rail 에 지정하면 항목까지 상속된다(기본값 = M3 수치) */
    :host(:not([expanded])) .target { flex-direction: column; align-items: center; min-height: var(--md-navigation-rail-item-min-height, 64px);
      padding: var(--md-navigation-rail-item-padding-block, 6px) 0; gap: var(--md-navigation-rail-item-label-space, 4px); }
    :host(:not([expanded])) .indicator { width: var(--md-navigation-rail-item-indicator-width, 56px); height: var(--md-navigation-rail-item-indicator-height, 32px); }
    :host([hide-collapsed-labels]:not([expanded])) .label { display: none; }
    :host(:not([expanded])[active]) .label { color: var(--md-sys-color-secondary, #625b71); }
    /* 가로형(펼친 레일): indicator 높이 56, 앞뒤 16, 아이콘-라벨 8, indicator 는 내용에 맞춤. 활성 라벨 on-secondary-container */
    :host([expanded]) .target { align-items: center; }
    :host([expanded]) .indicator { min-height: var(--md-navigation-rail-item-expanded-indicator-height, 56px); padding: 0 16px; gap: 8px; justify-content: flex-start; }
    :host([expanded]) .label { text-align: start; }
    .text { display: flex; flex-direction: column; min-width: 0; }
    .supporting { ${type('body-small', 12, 16, 400)} color: var(--md-sys-color-on-surface-variant, #49454f); }
    .supporting:not(.has), .trailing:not(.has) { display: none; }
    .trailing { display: flex; align-items: center; flex: none; margin-inline-start: auto; }
    :host([expanded]) .indicator.wide { width: 100%; }               /* trailing 이 있으면 indicator 를 항목 끝까지 늘린다 */
    :host([expanded][active]) .label { color: var(--md-sys-color-on-secondary-container, #1d192b); }
    /* 배지: 접힌 레일은 아이콘 오른쪽 위, 펼친 레일은 라벨 옆 */
    .badge { box-sizing: border-box; min-width: 16px; height: 16px; padding: 0 4px; border-radius: var(--md-navigation-rail-badge-shape, var(--md-sys-shape-corner-full, 8px));
      background: var(--md-sys-color-error, #b3261e); color: var(--md-sys-color-on-error, #fff);
      ${type('label-small', 11, 16, 500)} text-align: center; }
    .badge.dot { min-width: 6px; width: 6px; height: 6px; padding: 0; border-radius: var(--md-navigation-rail-badge-small-shape, var(--md-sys-shape-corner-full, 3px)); }
    .icon .badge { position: absolute; top: -4px; left: 14px; }
    .icon .badge.dot { top: 0; left: 18px; }
    .badge.after { flex: none; }
  `]);
  constructor() { super(); this.active = false; this.disabled = false; this.label = ''; this.badgeValue = ''; this.showBadge = false; this.expanded = false; this.hideCollapsedLabels = false; }
  render() {
    const hasActive = !!this.querySelector(':scope > [slot="active-icon"]');
    const hasSupport = !!this.querySelector(':scope > [slot="supporting-text"]');
    const hasTrailing = !!this.querySelector(':scope > [slot="trailing"]');
    const labelHidden = this.hideCollapsedLabels && !this.expanded;
    const badge = (cls) => html`<span class="badge ${this.badgeValue ? '' : 'dot'} ${cls}" part="badge">${this.badgeValue}</span>`;
    const icon = html`<span class="icon"><slot name="active-icon"></slot><slot name="inactive-icon" class=${hasActive ? 'has-active' : ''}></slot>
      ${this.showBadge && !this.expanded ? badge('') : nothing}</span>`;
    const label = html`<span class="label" part="label">${this.label}</span>`;
    const changed = () => this.requestUpdate();
    // supporting-text / trailing 은 펼친 레일에서만 보인다(접힌 레일은 공간이 없다)
    const text = html`<span class="text">${label}<span class="supporting ${hasSupport ? 'has' : ''}" part="supporting-text"><slot name="supporting-text" @slotchange=${changed}></slot></span></span>`;
    const trailing = html`<span class="trailing ${hasTrailing ? 'has' : ''}" part="trailing"><slot name="trailing" @slotchange=${changed}></slot></span>`;
    const content = this.expanded
      ? html`<span class="indicator ${hasTrailing ? 'wide' : ''}" part="indicator">${icon}${text}${this.showBadge ? badge('after') : nothing}${trailing}</span>`
      : html`<span class="indicator" part="indicator">${icon}</span>${label}`;
    const title = labelHidden && this.label ? this.label : nothing;
    // 링크 목적지: 진짜 앵커(가운데 클릭·Cmd/Ctrl 클릭으로 새 탭, 주소 복사). 활성 항목은 aria-current="page"
    if (this.href) return html`
      <a class="target" part="button" href=${this.href} aria-current=${this.active ? 'page' : nothing} aria-disabled=${this.disabled ? 'true' : nothing}
         aria-label=${this.label || nothing} title=${title} @click=${this._click}>${content}</a>`;
    return html`
      <button class="target" part="button" role="tab" aria-selected=${this.active ? 'true' : 'false'} aria-disabled=${this.disabled ? 'true' : 'false'}
              aria-label=${this.label || nothing} title=${title} @click=${this._click}>${content}</button>`;
  }
  focus() { this.shadowRoot?.querySelector('.target')?.focus(); }
  _click(e) {
    if (this.disabled) { e.preventDefault(); return; }
    // 링크: 수식 키(Cmd/Ctrl/Shift/Alt)·보조 버튼 클릭은 브라우저 기본 동작(새 탭·창)에 맡기고 activated 를 내지 않는다.
    // (가운데 클릭은 click 이 아니라 auxclick 이라 여기 오지 않는다)
    if (this.href && (e.button !== 0 || e.metaKey || e.ctrlKey || e.shiftKey || e.altKey)) return;
    this.dispatchEvent(new CustomEvent('navigation-rail-item-interaction', {bubbles: true, composed: true, detail: {originalEvent: e}}));
  }
}
define('md-navigation-rail-item', MdNavigationRailItem);
