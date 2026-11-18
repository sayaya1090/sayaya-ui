package dev.sayaya.ui.test

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.string.shouldNotContain

@GwtHtml("badge.html") // 테스트 서버 루트는 war(src/test/webapp)
internal class BadgeElementTest: GwtTestSpec({
    Given("Material Design Badge") {
        // Thread.sleep은 Playwright 이벤트를 처리하지 않으므로 waitForTimeout 사용
        page.waitForTimeout(5000.0)
        val logs = page.getConsoleLogs()
        // 목록에 등록되지 않은 검사도 실패하면 잡는다(이름 목록만 보면 미등록 실패가 통과로 숨는다)
        Then("실패한 검사 로그가 없어야 한다(미등록 검사 포함)") {
            val failed = logs.map { it.toString() }.filter { it.contains("Assertion failed!") }
            if (failed.isNotEmpty()) throw AssertionError("실패한 검사 로그: $failed")
        }
        println(logs)

        When("기본 배지를 생성할 때") {
            Then("md-badge 태그가 생성되어야 한다") {
                val log = logs.find { it.toString().contains("태그명은 md-badge") } as String?
                println(log)
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
            Then("숫자 value가 설정되어야 한다") {
                val log = logs.find { it.toString().contains("value는 5") } as String?
                println(log)
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
            Then("텍스트 value가 설정되어야 한다") {
                val log = logs.find { it.toString().contains("value는 New") } as String?
                println(log)
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
        }
    }
})
