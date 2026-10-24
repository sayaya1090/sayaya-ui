package dev.sayaya.ui.elements.interfaces;

/** UI 문구(버튼 텍스트·접근성 라벨)를 덮어쓸 수 있는 빌더. 기본값은 각 {@link UiLabel} 의 영어 문구다. */
public interface HasLabels<L extends Enum<L> & UiLabel, SELF extends HasLabels<L, SELF>> {
    SELF label(L label, String text);
}
