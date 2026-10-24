package dev.sayaya.ui.test

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.string.shouldNotContain

@GwtHtml("centered_slider.html") // 테스트 서버 루트는 war(src/test/webapp)
internal class CenteredSliderElementTest: GwtTestSpec({
    val checks = listOf(
        "네이티브 range",
        "최솟값",
        "최댓값",
        "초깃값 0",
        "aria-label",
        "정지 표시 양 끝 2개",
        "높이 44px",
        "트랙 높이 16px",
        "트랙 모서리 full",
        "비활성 트랙 secondary-container",
        "핸들 4px",
        "핸들 높이 44px",
        "핸들 primary",
        "0 이면 핸들 가운데",
        "0 이면 활성 트랙 없음",
        "+5 핸들 75%",
        "+5 활성 트랙은 가운데부터",
        "+5 활성 트랙은 핸들 6px 앞까지",
        "활성 트랙 primary",
        "+5 오른쪽 비활성은 핸들 6px 뒤부터",
        "+5 오른쪽 비활성 너비",
        "-5 활성 트랙은 핸들 6px 뒤부터",
        "-5 활성 트랙은 가운데까지",
        "-5 왼쪽 비활성 너비",
        "onInput 값 전달",
        "value() 읽기",
        "disabled 입력 막음",
        "disabled 불투명도 0.38"
    )
    Given("M3 Centered slider") {
        // Thread.sleep은 Playwright 이벤트를 처리하지 않으므로 waitForTimeout 사용
        page.waitForTimeout(3000.0)
        val logs = page.getConsoleLogs().map { it.toString() }
        println(logs)
        for (c in checks) {
            Then(c) {
                val log = logs.find { it.startsWith("cs: $c -") }
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
        }
    }
})
