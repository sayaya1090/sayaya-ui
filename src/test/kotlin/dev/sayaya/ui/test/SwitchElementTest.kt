package dev.sayaya.ui.test

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.string.shouldNotContain

@GwtHtml("src/test/webapp/switch.html")
internal class SwitchElementTest: GwtTestSpec({
    Given("Material Design Switch") {
        Thread.sleep(5000) // 비동기 작업 대기
        val logs = page.getConsoleLogs()
        println(logs)

        When("기본 사용법을 적용할 때") {
            Then("비선택 상태의 스위치가 생성되어야 한다") {
                val log = logs.find { it.toString().contains("초기 상태: 선택되지 않아야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("선택 상태의 스위치가 생성되어야 한다") {
                val log = logs.find { it.toString().contains("선택 상태: true여야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("아이콘이 있는 스위치가 생성되어야 한다") {
                val log = logs.find { it.toString().startsWith("icons: true여야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("선택 아이콘만 표시하는 스위치가 생성되어야 한다") {
                val log = logs.find { it.toString().contains("showOnlySelectedIcon: true여야 함") } as String?
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
                val log = logs.find { it.toString().startsWith("name 속성: notifications여야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("빌더 getter 메서드가 올바르게 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("빌더 name getter") } as String?
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
            Then("초기 입력 횟수가 0이어야 한다") {
                val log = logs.find { it.toString().startsWith("초기 입력 횟수") } as String?
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

        When("유효성 검증을 사용할 때") {
            Then("required 검증이 올바르게 동작해야 한다") {
                val log = logs.find { it.toString().contains("초기 상태: 선택되지 않아 유효하지 않음") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("checkValidity가 올바르게 동작해야 한다") {
                val log = logs.find { it.toString().contains("checkValidity: 선택되어 유효함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("setCustomValidity가 올바르게 동작해야 한다") {
                val log = logs.find { it.toString().contains("커스텀 메시지 설정 시 유효하지 않음") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("validationMessage가 올바르게 설정되어야 한다") {
                val log = logs.find { it.toString().contains("validationMessage 확인") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("커스텀 검증을 초기화할 수 있어야 한다") {
                val log = logs.find { it.toString().contains("초기화 후: 유효함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }
    }
})
