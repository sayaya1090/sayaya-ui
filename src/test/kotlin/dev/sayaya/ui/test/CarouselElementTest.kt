package dev.sayaya.ui.test

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.string.shouldNotContain

@GwtHtml("carousel.html") // 테스트 서버 루트는 war(src/test/webapp)
internal class CarouselElementTest: GwtTestSpec({
    val checks = listOf(
        "role region",
        "roledescription carousel",
        "aria-label",
        "항목 role group",
        "항목 aria-label",
        "tab 순서는 하나(건너뛰기)",
        "앞 여백 16px",
        "뒤 여백 16px",
        "uncontained 뒤 여백 없음",
        "항목 간격 8px",
        "스냅",
        "항목 모서리 28px",
        "항목 너비 200px",
        "hero 큰 항목 320px",
        "첫 항목은 다 보임",
        "둘째 항목도 다 보임",
        "가장자리 항목은 최소 40px",
        "가장자리 항목 clip-path",
        "uncontained 는 자르지 않음",
        "스크롤하면 앞쪽 항목이 작아짐",
        "스크롤하면 다음 항목이 커짐",
        "→ 다음 항목 포커스",
        "tabindex 이동",
        "Enter 로 활성화",
        "End 마지막 항목",
        "클릭으로 활성화",
        "모두 보기 여백 4px",
        "모두 보기 동작"
    )
    Given("M3 Carousel") {
        // Thread.sleep은 Playwright 이벤트를 처리하지 않으므로 waitForTimeout 사용
        page.waitForTimeout(3500.0)
        val logs = page.getConsoleLogs().map { it.toString() }
        println(logs)
        for (c in checks) {
            Then(c) {
                val log = logs.find { it.startsWith("ca: $c -") }
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
        }
    }
})
