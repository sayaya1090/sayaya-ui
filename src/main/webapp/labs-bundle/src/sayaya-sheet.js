// sayaya-ui 자체 구현 M3 side sheet (Material Web 2.5.0 에 컴포넌트 없음)
// 출처: 토큰 @material/web/tokens/versions/latest/sass/_md-comp-sheet-side.scss
//       m3.material.io/components/side-sheets/specs (렌더링해 읽음), MDC-Android m3_side_sheet_margin_detached
// [스펙] = m3.material.io 스펙 페이지 수치, [미확인] = 공식 수치를 찾지 못한 값
import {LitElement, html, css, nothing} from 'lit';

const LEVEL1 = '0 1px 2px 0 rgba(0,0,0,.3), 0 1px 3px 1px rgba(0,0,0,.15)';

export class MdSideSheet extends LitElement {
  static properties = {
    modal: {type: Boolean, reflect: true},
    open: {type: Boolean, reflect: true},
    pivot: {type: String, reflect: true},       // end(기본) | start
    detached: {type: Boolean, reflect: true},   // 화면 가장자리에서 16dp 떨어진 형태
    divider: {type: Boolean, reflect: true},    // standard: 본문 쪽 가장자리 구분선, 액션 위 구분선
    back: {type: Boolean, reflect: true},       // 뒤로 아이콘 버튼
    headline: {type: String},
    closeLabel: {type: String, attribute: 'close-label'},
    backLabel: {type: String, attribute: 'back-label'},
  };
  static styles = css`
    :host { display: none; box-sizing: border-box; --_width: var(--md-side-sheet-width, 256px); }
    :host([open]:not([modal])) { display: block; height: 100%; flex: none; }
    :host([open][modal]) { display: block; position: fixed; inset: 0; z-index: 1002; }
    .scrim { position: absolute; inset: 0; background: var(--md-sys-color-scrim, #000); opacity: 0.32; }
    .container {
      box-sizing: border-box; display: flex; flex-direction: column;
      width: var(--_width); max-width: min(400px, 100%); height: 100%;   /* 토큰 256px, [스펙] max-width 400dp */
      background: var(--md-sys-color-surface, #fef7ff); color: var(--md-sys-color-on-surface-variant, #49454f);
      border-radius: 0; box-shadow: none;
    }
    :host(:not([modal])[divider]) .container { border-inline-start: 1px solid var(--md-sys-color-outline, #79747e); }
    :host(:not([modal])[divider][pivot='start']) .container { border-inline-start: none; border-inline-end: 1px solid var(--md-sys-color-outline, #79747e); }
    :host([modal]) .container {
      position: absolute; top: 0; bottom: 0; inset-inline-end: 0; height: auto;
      background: var(--md-sys-color-surface-container-low, #f7f2fa); box-shadow: ${css([LEVEL1])};
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
    .header { display: flex; align-items: center; gap: 12px; padding: 24px 24px 16px; }  /* [스펙] 좌우 24, 요소 간 12. 상하 24/16 [미확인] */
    :host([back]) .header { padding-inline-start: 16px; }                 /* [스펙] 아이콘이 있을 때 시작 16 */
    .headline { flex: 1; min-width: 0; color: var(--md-sys-color-on-surface-variant, #49454f);
      font-family: var(--md-sys-typescale-title-large-font, Roboto, sans-serif);
      font-size: var(--md-sys-typescale-title-large-size, 22px);
      line-height: var(--md-sys-typescale-title-large-line-height, 28px);
      font-weight: var(--md-sys-typescale-title-large-weight, 400);
      letter-spacing: var(--md-sys-typescale-title-large-tracking, 0); }
    .content { flex: 1; min-height: 0; overflow: auto; padding: 0 24px; }
    .actions { display: none; box-sizing: border-box; align-items: center; justify-content: flex-start; gap: 8px;
      min-height: 72px; padding: 16px 24px 24px; }  /* [스펙] 위 16, 아래 24, 왼쪽 정렬. 스펙의 높이 72dp 는 40dp 버튼과 함께 16+40+24=80 이 되어 동시에 만족 불가 */
    .actions.has { display: flex; }
    :host([divider]) .actions.has { border-top: 1px solid var(--md-sys-color-outline, #79747e); }
  `;
  constructor() {
    super();
    this.modal = false; this.open = false; this.pivot = 'end'; this.detached = false; this.divider = false; this.back = false;
    this.headline = ''; this.closeLabel = '닫기'; this.backLabel = '뒤로';
    this._restore = null;
    this.addEventListener('keydown', (e) => { if (e.key === 'Escape' && this.open && this.modal) { e.stopPropagation(); this.close('escape'); } });
  }
  show() {
    if (this.open) return;
    this._restore = document.activeElement;
    this.open = true;
    this.updateComplete.then(() => {
      if (this.modal) this.shadowRoot.querySelector('.close')?.focus();
      this.dispatchEvent(new Event('opened', {bubbles: true, composed: true}));
    });
  }
  close(reason = 'close') {
    if (!this.open) return;
    this.open = false;
    if (this.modal && this._restore && this._restore.focus) this._restore.focus();
    this._restore = null;
    this.dispatchEvent(new CustomEvent('closed', {detail: {reason}, bubbles: true, composed: true}));
  }
  render() {
    const hasActions = !!this.querySelector(':scope > [slot="actions"]');
    return html`
      ${this.modal ? html`<div class="scrim" part="scrim" @click=${() => this.close('scrim')}></div>` : nothing}
      <aside class="container" part="container" role=${this.modal ? 'dialog' : 'complementary'}
             aria-modal=${this.modal ? 'true' : nothing} aria-label=${this.headline || nothing}>
        <div class="header" part="header">
          ${this.back ? html`<md-icon-button class="back" aria-label=${this.backLabel}
              @click=${() => this.dispatchEvent(new Event('back', {bubbles: true, composed: true}))}><md-icon>arrow_back</md-icon></md-icon-button>` : nothing}
          <div class="headline" part="headline"><slot name="headline">${this.headline}</slot></div>
          <md-icon-button class="close" aria-label=${this.closeLabel} @click=${() => this.close('close')}><md-icon>close</md-icon></md-icon-button>
        </div>
        <div class="content" part="content"><slot></slot></div>
        <div class="actions ${hasActions ? 'has' : ''}" part="actions"><slot name="actions" @slotchange=${() => this.requestUpdate()}></slot></div>
      </aside>`;
  }
}
customElements.define('md-side-sheet', MdSideSheet);
