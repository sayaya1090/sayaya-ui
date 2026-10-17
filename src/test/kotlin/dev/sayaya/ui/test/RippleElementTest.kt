package dev.sayaya.ui.test

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.string.shouldNotContain

@GwtHtml("ripple.html") // 테스트 서버 루트는 war(src/test/webapp)
internal class RippleElementTest: GwtTestSpec({
    Given("Material Design Ripple") {
        // Thread.sleep은 Playwright 이벤트를 처리하지 않으므로 waitForTimeout 사용
        page.waitForTimeout(5000.0)
        val logs = page.getConsoleLogs()
        println(logs)

        When("기본 ripple을 생성할 때") {
            Then("ripple 요소가 생성되어야 한다") {
                val log = logs.find { it.toString().startsWith("기본 ripple") } as String?
                println(log)
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
        }

        When("ripple 속성을 설정할 때") {
            Then("disabled 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("disabled 속성") } as String?
                println(log)
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
            Then("htmlFor 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("htmlFor 속성") } as String?
                println(log)
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
        }

        When("ripple을 요소에 연결할 때") {
            Then("버튼에 ripple이 연결되어야 한다") {
                val log = logs.find { it.toString().startsWith("ripple 연결: control 속성이") } as String?
                println(log)
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
            Then("div에도 ripple이 연결되어야 한다") {
                val log = logs.find { it.toString().startsWith("ripple 연결: div에도") } as String?
                println(log)
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
        }

        When("htmlFor로 요소를 참조할 때") {
            Then("htmlFor 속성이 설정되어야 한다") {
                val log = logs.find { it.toString().startsWith("htmlFor 속성") } as String?
                println(log)
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
        }

        When("ripple을 비활성화할 때") {
            Then("disabled 상태가 올바르게 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("비활성화 ripple") } as String?
                println(log)
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
        }

        When("커스텀 컨트롤에 ripple을 연결할 때") {
            Then("커스텀 요소에 ripple이 연결되어야 한다") {
                val log = logs.find { it.toString().startsWith("커스텀 컨트롤") } as String?
                println(log)
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
        }

        When("ripple을 분리할 때") {
            Then("detach 메서드가 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("detach 테스트") } as String?
                println(log)
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
        }
    }
})
