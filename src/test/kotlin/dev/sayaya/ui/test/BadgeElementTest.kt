package dev.sayaya.ui.test

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.string.shouldNotContain

@GwtHtml("src/test/webapp/badge.html")
internal class BadgeElementTest: GwtTestSpec({
    Given("Material Design Badge") {
        Thread.sleep(5000) // 비동기 작업 대기
        val logs = page.getConsoleLogs()
        println(logs)

        When("기본 배지를 생성할 때") {
            Then("md-badge 태그가 생성되어야 한다") {
                val log = logs.find { it.toString().contains("태그명은 md-badge") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("숫자 value가 설정되어야 한다") {
                val log = logs.find { it.toString().contains("value는 5") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("텍스트 value가 설정되어야 한다") {
                val log = logs.find { it.toString().contains("value는 New") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }
    }
})
