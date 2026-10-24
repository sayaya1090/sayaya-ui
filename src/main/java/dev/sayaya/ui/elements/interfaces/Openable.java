package dev.sayaya.ui.elements.interfaces;

/** 열고 닫는 상태를 가진 빌더(메뉴·검색 뷰·modal picker 등). */
public interface Openable<SELF extends Openable<SELF>> {
    SELF open(boolean open);
    boolean isOpen();
    default SELF open() { return open(true); }
    default SELF close() { return open(false); }
}
