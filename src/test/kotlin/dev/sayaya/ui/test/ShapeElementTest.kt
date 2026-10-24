package dev.sayaya.ui.test

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.string.shouldNotContain

@GwtHtml("shape.html") // 테스트 서버 루트는 war(src/test/webapp)
internal class ShapeElementTest: GwtTestSpec({
    val checks = listOf(
        "loading indicator 컨테이너",
        "docked toolbar",
        "floating toolbar",
        "FAB menu 닫힌 FAB",
        "FAB menu 항목",
        "FAB menu 닫기 버튼",
        "search bar",
        "search docked 열림 바 위 모서리",
        "search docked 뷰 아래 모서리",
        "date picker 컨테이너",
        "date picker 날짜",
        "시스템 토큰 extra-large → date picker",
        "time picker 컨테이너",
        "time picker 시간 칸",
        "time picker 다이얼",
        "시스템 토큰 small → time picker 시간 칸",
        "시스템 토큰 small → 오전/오후",
        "carousel 항목",
        "carousel 잘림도 같은 모서리",
        "wavy progress 트랙",
        "centered slider 트랙",
        "centered slider 핸들",
        "snackbar 닫기 버튼",
        "snackbar 액션",
        "toast 닫기 버튼",
        "standard side sheet 컨테이너",
        "bottom sheet 드래그 핸들",
        "rail 배지"
    )
    Given("M3 Shape") {
        // Thread.sleep은 Playwright 이벤트를 처리하지 않으므로 waitForTimeout 사용
        page.waitForTimeout(4000.0)
        val logs = page.getConsoleLogs().map { it.toString() }
        println(logs)
        for (c in checks) {
            Then(c) {
                val log = logs.find { it.startsWith("sh: $c -") }
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
        }
    }
})
