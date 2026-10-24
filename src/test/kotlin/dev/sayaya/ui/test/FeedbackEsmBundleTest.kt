package dev.sayaya.ui.test

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.string.shouldNotContain

/** 단독 ESM 번들(sayaya-ui-feedback.js)을 Material Web 2.5.0 일부와 함께 실었을 때의 공존·표시 검증 */
@GwtHtml("feedback_esm.html")
internal class FeedbackEsmBundleTest: GwtTestSpec({
    val checks = listOf(
        "단독 번들 import 오류 없음",
        "md-tooltip 정의",
        "md-snackbar 정의",
        "md-toast 정의",
        "다른 md-* 는 정의하지 않음",
        "앱이 정의한 Material Web 요소는 그대로",
        "두 번 실어도 중복 정의 오류 없음",
        "MW 버튼 앵커에 툴팁 표시(top layer)",
        "툴팁이 MW 버튼 바로 위",
        "md-snackbar 표시",
        "md-toast 표시",
        "검사 끝"
    )
    Given("Material Web 2.5.0 일부 + sayaya-ui-feedback.js") {
        page.waitForTimeout(3000.0)
        val logs = page.getConsoleLogs().map { it.toString() }
        println(logs)
        for (c in checks) {
            Then(c) {
                val log = logs.find { it.startsWith("fbesm: $c -") }
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
        }
    }
})
