package dev.sayaya.ui.test

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.string.shouldNotContain

@GwtHtml("wavy_progress.html") // 테스트 서버 루트는 war(src/test/webapp)
internal class WavyProgressElementTest: GwtTestSpec({
    val checks = listOf(
        "role progressbar",
        "aria-valuemin 0",
        "aria-valuemax 1",
        "aria-valuenow 0.5",
        "aria-label",
        "indeterminate 는 aria-valuenow 없음",
        "높이 10px",
        "활성 트랙 50%",
        "활성-트랙 간격 4px",
        "트랙은 끝까지",
        "트랙 두께 4px",
        "끝 점 4px",
        "끝 점은 오른쪽 끝",
        "활성 primary",
        "트랙 secondary-container",
        "물결 마스크",
        "파장 40px·높이 10px",
        "물결 위상 애니메이션",
        "1 초과는 100%",
        "0 미만은 0",
        "indeterminate 파장 20px",
        "indeterminate 이동 애니메이션",
        "indeterminate 끝 점 없음",
        "indeterminate 트랙 전체"
    )
    Given("M3 Wavy progress") {
        // Thread.sleep은 Playwright 이벤트를 처리하지 않으므로 waitForTimeout 사용
        page.waitForTimeout(3000.0)
        val logs = page.getConsoleLogs().map { it.toString() }
        println(logs)
        for (c in checks) {
            Then(c) {
                val log = logs.find { it.startsWith("wp: $c -") }
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
        }
    }
})
