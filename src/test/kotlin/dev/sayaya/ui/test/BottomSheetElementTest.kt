package dev.sayaya.ui.test

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.string.shouldNotContain

@GwtHtml("bottom_sheet.html") // 테스트 서버 루트는 war(src/test/webapp)
internal class BottomSheetElementTest: GwtTestSpec({
    val checks = listOf(
        "Esc 로 닫힘",
        "bottom-sheet 속성 설정",
        "bottom-sheet 업그레이드",
        "bottom-sheet 태그명",
        "modal role dialog",
        "modal 처음 높이는 화면의 50% 이하",
        "standard 는 뒤 화면 클릭을 막지 않음",
        "standard 는 스크림 없음",
        "가운데 정렬",
        "그림자(elevation 1)",
        "너비 최대 640px",
        "닫힌 시트는 숨김",
        "드래그 핸들 32x4",
        "드래그 핸들 불투명도 0.4",
        "드래그 핸들 위아래 패딩 22px",
        "스크림 불투명도 0.32",
        "스크림 클릭으로 닫힘",
        "아래쪽 모서리 0px",
        "열면 드래그 핸들로 포커스",
        "위쪽 모서리 28px",
        "접힌 상태에서 아래로 끌면 닫힘",
        "컨테이너 surface-container-low",
        "펼친 상태에서 아래로 끌면 접힘",
        "펼친 시트 위 여백 56px(창 640 초과)",
        "핸들 클릭으로 펼침",
        "화면 아래에 붙음"
    )
    Given("M3 bottom sheet") {
        // Thread.sleep은 Playwright 이벤트를 처리하지 않으므로 waitForTimeout 사용
        page.waitForTimeout(6500.0)
        val logs = page.getConsoleLogs().map { it.toString() }
        println(logs)
        for (c in checks) {
            Then(c) {
                val log = logs.find { it.startsWith("bs: $c -") }
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
        }
    }
})
