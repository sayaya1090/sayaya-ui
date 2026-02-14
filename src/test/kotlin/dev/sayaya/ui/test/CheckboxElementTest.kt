package dev.sayaya.ui.test

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.string.shouldNotContain

@GwtHtml("src/test/webapp/checkbox.html")
internal class CheckboxElementTest: GwtTestSpec({
    Given("Material Design Checkbox") {
        Thread.sleep(5000) // 비동기 작업 대기
        val logs = page.getConsoleLogs()
        println(logs)
        When("체크박스 상태를 설정할 때") {
            Then("unchecked 상태가 올바르게 설정되어야 한다") {
                val log = logs.find { it.toString().startsWith("체크 안됨 상태") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("checked 상태가 올바르게 설정되어야 한다") {
                val log = logs.find { it.toString().startsWith("체크됨 상태") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("indeterminate 상태가 올바르게 설정되어야 한다") {
                val log = logs.find { it.toString().startsWith("indeterminate 상태") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("상태 전환이 올바르게 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("상태 전환") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("체크박스 속성을 설정할 때") {
            Then("disabled 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("disabled 속성") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("required 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("required 속성") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("value 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("value 속성") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("기본 value가 올바르게 설정되어야 한다") {
                val log = logs.find { it.toString().startsWith("기본 value") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("폼 관련 속성을 설정할 때") {
            Then("name 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("name 속성") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("폼 통합이 올바르게 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("폼 체크박스") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("빌더 getter 메서드가 올바르게 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("빌더") && it.toString().contains("getter") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("이벤트를 처리할 때") {
            Then("onChange 이벤트가 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("onChange 이벤트") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("onInput 이벤트가 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("onInput 이벤트") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("여러 이벤트 핸들러가 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("다중 이벤트") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("접근성 속성을 설정할 때") {
            Then("aria-label이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("aria-label") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("disabled 상태에서도 aria-label이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("disabled aria 체크박스") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("사용 예시를 테스트할 때") {
            Then("기본 체크박스 사용 예시가 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("사용 예시") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("라벨과 함께 사용할 때") {
            Then("라벨 예시가 올바르게 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("라벨 예시") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("폼 통합 동작을 테스트할 때") {
            Then("폼 통합이 올바르게 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("폼 통합") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("Select All 패턴이 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("Select All 패턴") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("클릭 동작을 테스트할 때") {
            Then("체크박스 클릭이 올바르게 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("클릭 동작") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("indeterminate 클릭 동작을 테스트할 때") {
            Then("indeterminate 상태에서 클릭이 올바르게 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("indeterminate 클릭") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("indeterminate 순환 동작이 올바르게 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("indeterminate 순환") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("indeterminate 이벤트가 올바르게 발생해야 한다") {
                val log = logs.find { it.toString().startsWith("indeterminate 이벤트") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }
    }
})
