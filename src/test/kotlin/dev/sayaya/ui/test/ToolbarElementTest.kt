package dev.sayaya.ui.test

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.string.shouldNotContain

@GwtHtml("toolbar.html") // 테스트 서버 루트는 war(src/test/webapp)
internal class ToolbarElementTest: GwtTestSpec({
    val checks = listOf(
        "role toolbar",
        "aria-label",
        "세로 aria-orientation",
        "가로는 aria-orientation 없음",
        "docked 높이 64px",
        "docked 전체 너비",
        "docked 좌우 여백 16px",
        "docked 모서리 없음",
        "docked 배경 surface-container",
        "docked 최소 간격 4px",
        "floating 높이 64px",
        "floating 모서리 full",
        "floating 좌우 여백 8px",
        "floating 간격 4px",
        "floating 배경 surface-container",
        "floating 그림자",
        "vibrant 배경 primary-container",
        "vibrant 아이콘 on-primary-container",
        "세로 너비 64px",
        "세로 배치",
        "고정 위치 fixed",
        "고정 위치 하단 16px",
        "→ 로 다음 항목",
        "← 로 이전 항목",
        "처음에서 ← 는 마지막으로",
        "Home 은 첫 항목",
        "End 는 마지막 항목",
        "세로는 ↓ 로 다음 항목",
        "세로는 → 무시"
    )
    Given("M3 Toolbar") {
        // Thread.sleep은 Playwright 이벤트를 처리하지 않으므로 waitForTimeout 사용
        page.waitForTimeout(4000.0)
        val logs = page.getConsoleLogs().map { it.toString() }
        println(logs)
        for (c in checks) {
            Then(c) {
                val log = logs.find { it.startsWith("tb: $c -") }
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
        }
    }
})
