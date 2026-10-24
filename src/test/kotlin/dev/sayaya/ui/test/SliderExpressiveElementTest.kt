package dev.sayaya.ui.test

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.string.shouldNotContain

@GwtHtml("slider_expressive.html") // 테스트 서버 루트는 war(src/test/webapp)
internal class SliderExpressiveElementTest: GwtTestSpec({
    val checks = listOf(
        "XS 트랙 높이 16px",
        "XS 트랙 모서리 8px",
        "XS 핸들 높이 44px",
        "XS 핸들 너비 4px",
        "XS 핸들 높이만큼 공간 확보 44px",
        "S 트랙 높이 24px",
        "S 트랙 모서리 8px",
        "S 핸들 높이 44px",
        "S 핸들 너비 4px",
        "S 핸들 높이만큼 공간 확보 44px",
        "M 트랙 높이 40px",
        "M 트랙 모서리 12px",
        "M 핸들 높이 52px",
        "M 핸들 너비 4px",
        "M 핸들 높이만큼 공간 확보 52px",
        "L 트랙 높이 56px",
        "L 트랙 모서리 16px",
        "L 핸들 높이 68px",
        "L 핸들 너비 4px",
        "L 핸들 높이만큼 공간 확보 68px",
        "XL 트랙 높이 96px",
        "XL 트랙 모서리 28px",
        "XL 핸들 높이 108px",
        "XL 핸들 너비 4px",
        "XL 핸들 높이만큼 공간 확보 108px",
        "세로는 세로로 긴 박스",
        "세로 회전 -90°",
        "세로는 아래가 최솟값(80 이 20 보다 위)",
        "세로 stops 함께 사용",
        "inset icon 이름",
        "inset icon 표시",
        "M 크기 inset icon 24px",
        "값이 크면 활성 트랙 안",
        "활성 트랙 안 아이콘 on-primary",
        "값이 작으면 핸들 뒤로",
        "비활성 트랙 아이콘 on-secondary-container",
        "핸들 뒤 아이콘은 핸들 오른쪽",
        "40dp 미만(XS)은 inset icon 숨김",
        "값이 바뀌면 아이콘 위치도 바뀜"
    )
    Given("M3 Slider (Expressive)") {
        // Thread.sleep은 Playwright 이벤트를 처리하지 않으므로 waitForTimeout 사용
        page.waitForTimeout(4500.0)
        val logs = page.getConsoleLogs().map { it.toString() }
        println(logs)
        for (c in checks) {
            Then(c) {
                val log = logs.find { it.startsWith("sx: $c -") }
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
        }
    }
})
