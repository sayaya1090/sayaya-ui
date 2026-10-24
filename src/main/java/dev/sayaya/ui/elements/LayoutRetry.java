package dev.sayaya.ui.elements;

import elemental2.dom.DomGlobal;

/**
 * 아직 문서에 붙지 않아 레이아웃 크기가 0 인 요소를, 붙을 때까지 다음 프레임마다 다시 계산한다(최대 약 10초 = 600프레임).
 * 사용: {@code if (retry.defer(width, this::layout)) return;}
 */
final class LayoutRetry {
    static final int MAX_FRAMES = 600;
    private int frames = 0;
    /** size 가 0 이하이면 다음 프레임에 again 을 예약하고 true(호출부는 return). 크기가 생기면 카운터를 비우고 false */
    boolean defer(double size, Runnable again) {
        if (size <= 0) {
            if (frames++ < MAX_FRAMES) DomGlobal.requestAnimationFrame(t -> again.run());
            return true;
        }
        frames = 0;
        return false;
    }
}
