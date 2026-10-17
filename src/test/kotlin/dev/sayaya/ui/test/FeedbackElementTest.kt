package dev.sayaya.ui.test

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.string.shouldNotContain

@GwtHtml("feedback.html") // 테스트 서버 루트는 war(src/test/webapp)
internal class FeedbackElementTest: GwtTestSpec({
    val checks = listOf(
        "button-group 속성 설정",
        "button-group 태그명",
        "connected aria-pressed",
        "connected large 안쪽 모서리 16px",
        "connected 가운데 시작 모서리 8px",
        "connected 간격 2px",
        "connected 단일 선택 전환",
        "connected 모서리가 버튼 내부에 적용",
        "connected 바깥 모서리 full",
        "connected 선택 시 안쪽 모서리 50%",
        "connected 선택 이벤트",
        "connected 안쪽 모서리 small 8px",
        "large 버튼 높이 96px",
        "plain tooltip role",
        "plain tooltip 글자 12px",
        "plain tooltip 높이 24px 이상",
        "plain tooltip 모서리 4px",
        "plain tooltip 좌우 패딩 8px",
        "plain tooltip 줄높이 16px",
        "rich tooltip 그림자(elevation 2)",
        "rich tooltip 모서리 12px",
        "rich tooltip 본문 14px",
        "small 버튼 높이 40px",
        "snackbar role status",
        "snackbar timeout 자동 닫힘",
        "snackbar 그림자(elevation 3)",
        "snackbar 두 줄 높이 68px",
        "snackbar 모서리 4px",
        "snackbar 본문 14px",
        "snackbar 속성 설정",
        "snackbar 액션 이벤트와 닫힘",
        "snackbar 태그명",
        "snackbar 하나만(replaced)",
        "snackbar 한 줄 높이 48px",
        "standard small 간격 12px",
        "standard xsmall 간격 18px",
        "standard 누름 시 너비 +15%",
        "standard 뗌 시 원래 너비",
        "md-button-group 업그레이드",
        "md-snackbar 업그레이드",
        "md-tooltip 업그레이드",
        "tooltip 속성 설정",
        "tooltip 창에 하나만",
        "tooltip 태그명",
        "tooltip 포커스 시 지연 후 표시",
        "tooltip 포커스 해제 시 닫힘",
        "xsmall 버튼 높이 32px"
    )
    Given("M3 tooltip / snackbar / button group") {
        // Thread.sleep은 Playwright 이벤트를 처리하지 않으므로 waitForTimeout 사용
        page.waitForTimeout(6000.0)
        val logs = page.getConsoleLogs().map { it.toString() }
        println(logs)
        for (c in checks) {
            Then(c) {
                val log = logs.find { it.startsWith("fb: $c -") }
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
        }
    }
})
