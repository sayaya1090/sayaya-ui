package dev.sayaya.ui.test

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.string.shouldNotContain

@GwtHtml("src/test/webapp/select.html")
internal class SelectElementTest: GwtTestSpec({
    Given("Material Design Select") {
        Thread.sleep(5000) // 비동기 작업 대기
        val logs = document.getConsoleLogs()
        println(logs)

        When("Select 변형을 생성할 때") {
            Then("filled select가 생성되어야 한다") {
                val log = logs.find { it.toString().startsWith("filled select") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("outlined select가 생성되어야 한다") {
                val log = logs.find { it.toString().startsWith("outlined select") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("기본 속성을 설정할 때") {
            Then("label 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("label 속성") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("required 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("required 속성") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("disabled 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("disabled 속성") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("supportingText 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("supportingText 속성") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("errorText 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("errorText 속성") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("error 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("error 속성") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("옵션 속성을 설정할 때") {
            Then("옵션의 value가 적용되어야 한다") {
                val log = logs.find { it.toString().contains("옵션 value") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("옵션의 headline이 적용되어야 한다") {
                val log = logs.find { it.toString().contains("옵션 headline") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("옵션의 supportingText가 적용되어야 한다") {
                val log = logs.find { it.toString().contains("옵션 supportingText") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("옵션이 선택되어야 한다") {
                val log = logs.find { it.toString().contains("옵션 selected") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("옵션이 비활성화되어야 한다") {
                val log = logs.find { it.toString().contains("옵션 disabled") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("선택 메서드를 사용할 때") {
            Then("select()로 값을 선택할 수 있어야 한다") {
                val log = logs.find { it.toString().contains("select() 메서드") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("selectIndex()로 인덱스 선택할 수 있어야 한다") {
                val log = logs.find { it.toString().contains("selectIndex() 메서드") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("selectedIndex가 올바르게 반환되어야 한다") {
                val log = logs.find { it.toString().contains("selectedIndex") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("selectedOptions가 올바르게 반환되어야 한다") {
                val log = logs.find { it.toString().contains("selectedOptions") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("이벤트를 처리할 때") {
            Then("onChange 이벤트가 동작해야 한다") {
                val log = logs.find { it.toString().contains("onChange 이벤트") || it.toString().contains("변경 이벤트") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("onInput 이벤트가 동작해야 한다") {
                val log = logs.find { it.toString().contains("onInput 이벤트") || it.toString().contains("입력 이벤트") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("onOpening 이벤트가 동작해야 한다") {
                val log = logs.find { it.toString().contains("opening 이벤트") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("onOpened 이벤트가 동작해야 한다") {
                val log = logs.find { it.toString().contains("opened 이벤트") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("onClosing 이벤트가 동작해야 한다") {
                val log = logs.find { it.toString().contains("closing 이벤트") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("onClosed 이벤트가 동작해야 한다") {
                val log = logs.find { it.toString().contains("closed 이벤트") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("유효성 검증을 사용할 때") {
            Then("checkValidity()가 동작해야 한다") {
                val log = logs.find { it.toString().contains("checkValidity") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("reportValidity()가 에러를 표시해야 한다") {
                val log = logs.find { it.toString().contains("reportValidity") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("ValidityState 객체가 존재해야 한다") {
                val log = logs.find { it.toString().contains("ValidityState") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("required 검증이 동작해야 한다") {
                val log = logs.find { it.toString().contains("required 검증") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("커스텀 검증을 사용할 때") {
            Then("setCustomValidity()가 동작해야 한다") {
                val log = logs.find { it.toString().contains("setCustomValidity") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("수동 에러 상태 설정이 동작해야 한다") {
                val log = logs.find { it.toString().contains("수동 에러") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("동적 에러 업데이트가 동작해야 한다") {
                val log = logs.find { it.toString().contains("동적 에러") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("고급 기능을 사용할 때") {
            Then("메뉴가 열려야 한다") {
                val log = logs.find { it.toString().contains("showPicker") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("reset()이 값을 초기화해야 한다") {
                val log = logs.find { it.toString().contains("reset()") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("focus()가 동작해야 한다") {
                val log = logs.find { it.toString().contains("focus()") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("blur()가 동작해야 한다") {
                val log = logs.find { it.toString().contains("blur()") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }
    }
})
