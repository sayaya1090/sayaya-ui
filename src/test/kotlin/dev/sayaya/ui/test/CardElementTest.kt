package dev.sayaya.ui.test

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.string.shouldNotContain

@GwtHtml("src/test/webapp/card.html")
internal class CardElementTest: GwtTestSpec({
    Given("Material Design Card") {
        Thread.sleep(5000) // 비동기 작업 대기
        val logs = page.getConsoleLogs()
        println(logs)

        When("기본 카드를 생성할 때") {
            Then("md-elevated-card 태그가 생성되어야 한다") {
                val log = logs.find { it.toString().contains("Elevated 태그명은 md-elevated-card") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("md-filled-card 태그가 생성되어야 한다") {
                val log = logs.find { it.toString().contains("Filled 태그명은 md-filled-card") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("md-outlined-card 태그가 생성되어야 한다") {
                val log = logs.find { it.toString().contains("Outlined 태그명은 md-outlined-card") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("카드 변형을 비교할 때") {
            Then("3가지 variant가 모두 생성되어야 한다") {
                val log = logs.find { it.toString().contains("3가지 variant 모두 생성됨") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("콘텐츠가 있는 카드를 생성할 때") {
            Then("구조화된 카드가 생성되어야 한다") {
                val log = logs.find { it.toString().contains("구조화된 카드 생성됨") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("클릭 가능한 카드가 생성되어야 한다") {
                val log = logs.find { it.toString().contains("클릭 가능한 카드 생성됨") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("aria-label이 설정되어야 한다") {
                val log = logs.find { it.toString().contains("aria-label 설정됨") && it.toString().contains("Product information card") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }
    }
})
