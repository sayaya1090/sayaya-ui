// sayaya-ui 자체 구현 M3 button group (standard / connected). sayaya-feedback.js 에서 분리 — 단독 feedback 번들(tooltip/snackbar/toast)에 md-button-group 이 들어가지 않도록
import {LitElement, html, css, unsafeCSS} from 'lit';

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
// 토글·누름 전환(shape morph). 토큰 _md-sys-motion.scss: spring.fast.spatial(damping 0.9, stiffness 1400), spring.default.effects(damping 1, stiffness 1600).
// Compose ToggleButton/ButtonGroup 은 모양·너비에 FastSpatial, 테두리 색에 DefaultEffects 를 쓰고 container·content 색은 바로 바꾼다.
// CSS 에는 spring 이 없어 질량 1·초기 속도 0 인 스프링을 목표까지 0.1% 남을 때까지 풀어 linear() 로 근사했다 [파생: 지속 시간·곡선 점]
const FAST_SPATIAL_MS = 224; // [파생] 아래 FAST_SPATIAL 의 지속 시간. 누름 확대가 끝까지 보이도록 최소 유지 시간으로도 쓴다
const FAST_SPATIAL = FAST_SPATIAL_MS + 'ms linear(0, 0.1, 0.296, 0.494, 0.659, 0.783, 0.869, 0.925, 0.96, 0.98, 0.992, 0.998, 1, 1.001, 1.002, 1.001, 1)';
const DEFAULT_EFFECTS = '231ms linear(0, 0.115, 0.321, 0.517, 0.671, 0.783, 0.86, 0.911, 0.945, 0.966, 0.979, 0.987, 0.992, 0.995, 0.997, 0.998, 1)';
const RADII = ['border-start-start-radius', 'border-start-end-radius', 'border-end-start-radius', 'border-end-end-radius'];
const motion = (props, m) => props.map(p => `${p} ${m}`).join(', ');
// Material 버튼 안쪽에서 container-shape 변수를 직접 받는 요소(icon 버튼 본체, outlined 테두리, ripple)는 host 의 전환을 inherit 하지 못하므로
// 그룹 안 버튼의 shadowRoot 에 같은 전환을 넣는다
const INNER_MORPH = new CSSStyleSheet();
// icon 버튼은 host 에 모서리가 없고 안쪽 .icon-button 만 둥글다. outlined 선택 색을 host 배경으로 칠하므로 host 도 같은 container-shape 로 깎는다
// (일반 버튼 host 는 원래 같은 값이라 영향 없음. 모서리 전환은 그룹의 ::slotted(*) transition 이 맡는다)
INNER_MORPH.replaceSync(`
  :host { border-start-start-radius: var(--_container-shape-start-start); border-start-end-radius: var(--_container-shape-start-end);
    border-end-start-radius: var(--_container-shape-end-start); border-end-end-radius: var(--_container-shape-end-end); }
  .icon-button, md-ripple { transition: ${motion(RADII, FAST_SPATIAL)}; }
  .outline { transition: ${motion(RADII, FAST_SPATIAL)}, border-color ${DEFAULT_EFFECTS}; }
  @media (prefers-reduced-motion: reduce) { .icon-button, md-ripple, .outline { transition: none; } }
`);
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
    ::slotted(*) { flex: none; transition: ${unsafeCSS(motion([...RADII, 'width', 'flex-basis'], FAST_SPATIAL))}; }
    @media (prefers-reduced-motion: reduce) { ::slotted(*) { transition: none; } }
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
    clearTimeout(this._releaseTimer); clearTimeout(this._settleTimer);
    if (down) {
      if (this.variant !== 'connected') {
        // standard: 누르기 직전 너비를 기억해 두고, 눌린 버튼 +15% 만큼 양옆 버튼이 나눠서 줄어든다(그룹 너비 유지).
        // 너비 auto -> px 는 CSS transition 이 걸리지 않아 순간적으로 바뀌므로, 지금 너비를 px 로 먼저 고정한 뒤 목표 너비로 바꾼다
        if (!this._widths) this._widths = this.buttons.map(x => x.getBoundingClientRect().width);
        this.buttons.forEach(x => { x.style.width = x.getBoundingClientRect().width + 'px'; });
        void this.offsetWidth; // 고정한 너비를 전환 시작점으로 확정(reflow)
      }
      if (this._pressed && this._pressed !== b) this._pressed.removeAttribute('data-pressed');
      this._pressed = b; this._pressedAt = performance.now(); b.setAttribute('data-pressed', '');
      this._layout();
    } else {
      // 짧은 클릭이라도 확대 전환이 끝까지 보인 뒤 복귀한다(최소 유지 = 전환 시간) [M3 외: 웹 마우스 클릭 보정]
      const wait = Math.max(0, FAST_SPATIAL_MS - (performance.now() - this._pressedAt));
      this._releaseTimer = setTimeout(() => this._release(b), wait);
    }
  }
  _release(b) {
    b.removeAttribute('data-pressed'); this._pressed = null;
    this._layout(); // standard: 기억한 원래 너비(px)로 전환하며 돌아간다
    // 복귀 전환이 끝나면 너비를 auto 로 되돌려 내용·크기 변화에 다시 맞춘다
    this._settleTimer = setTimeout(() => { if (!this._pressed) { this._widths = null; this._layout(); } }, FAST_SPATIAL_MS + 20);
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
  _morph(b) {
    const root = b.shadowRoot;
    if (!root) { // 아직 업그레이드 전이면 정의·첫 렌더 뒤에 넣는다
      if (b.localName.includes('-')) customElements.whenDefined(b.localName).then(() => Promise.resolve(b.updateComplete).then(() => b.shadowRoot && this._morph(b)));
      return;
    }
    if (!root.adoptedStyleSheets.includes(INNER_MORPH)) root.adoptedStyleSheets = [...root.adoptedStyleSheets, INNER_MORPH];
  }
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
      this._morph(b);
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
        if (this._widths && this._widths.length === list.length) {
          // 누르는 동안은 목표 너비, 뗀 뒤 복귀 전환 중에는 원래 너비(px). 둘 다 px 라서 전환이 걸린다
          const w = this._widths, delta = pi >= 0 ? w[pi] * PRESSED_WIDTH : 0;
          const nb = [pi - 1, pi + 1].filter(k => pi >= 0 && k >= 0 && k < list.length);
          const width = i === pi ? w[i] + delta : nb.includes(i) ? w[i] - delta / nb.length : w[i];
          b.style.width = width + 'px';
        } else b.style.width = '';
      }
    });
  }
  render() { return html`<slot @slotchange=${() => this._layout()}></slot>`; }
}
if (!customElements.get('md-button-group')) customElements.define('md-button-group', MdButtonGroup);
