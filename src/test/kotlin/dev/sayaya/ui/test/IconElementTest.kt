package dev.sayaya.ui.test

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.string.shouldNotContain

@GwtHtml("src/test/webapp/icon.html")
internal class IconElementTest: GwtTestSpec({
    Given("Material Design Icon") {
        Thread.sleep(5000)
        val logs = page.getConsoleLogs()
        println(logs)

        When("아이콘을 생성할 때") {
            Then("빈 아이콘이 생성되어야 한다") {
                val log = logs.find { it.toString().startsWith("아이콘 생성") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("문자열로 아이콘을 추가할 때") {
            Then("이름으로 아이콘이 생성되어야 한다") {
                val log = logs.find { it.toString().startsWith("이름으로 아이콘") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("유니코드로 아이콘이 생성되어야 한다") {
                val log = logs.find { it.toString().startsWith("유니코드로 아이콘") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("접근성 속성을 설정할 때") {
            Then("aria-label이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("접근성") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("스타일을 적용할 때") {
            Then("커스텀 스타일이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("스타일링") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("사용 예시를 테스트할 때") {
            Then("아이콘 사용 예시가 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("사용 예시") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("아이콘 스타일을 테스트할 때") {
            Then("아이콘 스타일이 올바르게 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("아이콘 스타일") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("컨텍스트에서 아이콘을 사용할 때") {
            Then("컨텍스트 내 아이콘이 올바르게 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("컨텍스트 내 아이콘") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }
    }
})
