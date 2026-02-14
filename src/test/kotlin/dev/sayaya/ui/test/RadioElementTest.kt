package dev.sayaya.ui.test

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.string.shouldNotContain

@GwtHtml("src/test/webapp/radio.html")
internal class RadioElementTest: GwtTestSpec({
    Given("Material Design Radio") {
        Thread.sleep(5000) // 비동기 작업 대기
        val logs = page.getConsoleLogs()
        println(logs)

        When("기본 사용법을 적용할 때") {
            Then("미선택 상태의 라디오가 생성되어야 한다") {
                val log = logs.find { it.toString().contains("초기 상태: 선택되지 않아야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("선택 상태의 라디오가 생성되어야 한다") {
                val log = logs.find { it.toString().contains("선택 상태: true여야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("라디오에 값이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("name: option이어야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("라디오 value가 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("value: option-1이어야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("기본 속성을 설정할 때") {
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
            Then("custom value가 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("value 속성: custom-value여야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("기본값이 올바르게 설정되어야 한다") {
                val log = logs.find { it.toString().startsWith("기본값: 'on' 또는 null이어야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("name 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("name 속성: color-group이어야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("빌더 name getter가 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("빌더 name getter") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("빌더 value getter가 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("빌더 value getter") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("빌더 disabled getter가 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("빌더 disabled getter") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("빌더 required getter가 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("빌더 required getter") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("빌더 selected getter가 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("빌더 selected getter") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("라디오 그룹을 사용할 때") {
            Then("단일 그룹에서 하나만 선택되어야 한다") {
                val log = logs.find { it.toString().contains("초기 상태: Medium만 선택되어야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("다중 그룹이 독립적으로 동작해야 한다") {
                val log = logs.find { it.toString().contains("초기 상태: Blue와 Small이 선택되어야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("이벤트를 처리할 때") {
            Then("초기 변경 횟수가 0이어야 한다") {
                val log = logs.find { it.toString().startsWith("초기 변경 횟수") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("초기 input 횟수가 0이어야 한다") {
                val log = logs.find { it.toString().startsWith("초기 input 횟수") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("초기 change 횟수가 0이어야 한다") {
                val log = logs.find { it.toString().startsWith("초기 change 횟수") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }
    }
})
