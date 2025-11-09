package dev.sayaya.ui.test

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.string.shouldNotContain

@GwtHtml("src/test/webapp/chip.html")
internal class ChipElementTest: GwtTestSpec({
    Given("Material Design Chip") {
        Thread.sleep(5000) // 비동기 작업 대기
        val logs = document.getConsoleLogs()
        println(logs)

        When("칩 타입을 생성할 때") {
            Then("assist 칩이 생성되어야 한다") {
                val log = logs.find { it.toString().startsWith("assist 칩") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("filter 칩이 생성되어야 한다") {
                val log = logs.find { it.toString().startsWith("filter 칩") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("input 칩이 생성되어야 한다") {
                val log = logs.find { it.toString().startsWith("input 칩") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("suggestion 칩이 생성되어야 한다") {
                val log = logs.find { it.toString().startsWith("suggestion 칩") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("칩 속성을 설정할 때") {
            Then("label 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("칩 label") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("disabled 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("칩 disabled") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("alwaysFocusable 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("칩 alwaysFocusable") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("assist 칩 속성을 설정할 때") {
            Then("elevated 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("assist 칩 elevated") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("href 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("assist 칩 href") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("target 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("assist 칩 target") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("filter 칩 속성을 설정할 때") {
            Then("elevated 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("filter 칩 elevated") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("selected 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("filter 칩 selected") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("removable 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("filter 칩 removable") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("ariaLabelRemove 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("filter 칩 ariaLabelRemove") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("input 칩 속성을 설정할 때") {
            Then("avatar 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("input 칩 avatar") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("selected 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("input 칩 selected") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("removeOnly 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("input 칩 removeOnly") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("href 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("input 칩 href") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("target 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("input 칩 target") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("ariaLabelRemove 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("input 칩 ariaLabelRemove") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("suggestion 칩 속성을 설정할 때") {
            Then("elevated 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("suggestion 칩 elevated") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("href 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("suggestion 칩 href") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("target 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("suggestion 칩 target") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("칩셋 속성을 설정할 때") {
            Then("칩셋이 올바르게 생성되어야 한다") {
                val log = logs.find { it.toString().startsWith("칩셋: 태그명") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("칩셋에 칩이 포함되어야 한다") {
                val log = logs.find { it.toString().startsWith("칩셋: 3개") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("aria-label이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("칩셋 aria-label") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("aria-labelledby가 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("칩셋 aria-labelledby") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("아이콘을 추가할 때") {
            Then("칩에 아이콘이 추가되어야 한다") {
                val log = logs.find { it.toString().startsWith("칩 아이콘: 아이콘이") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("아이콘 slot이 올바르게 설정되어야 한다") {
                val log = logs.find { it.toString().startsWith("칩 아이콘: slot") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("filter 칩에 아이콘이 추가되어야 한다") {
                val log = logs.find { it.toString().startsWith("filter 칩 아이콘") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("접근성 속성을 설정할 때") {
            Then("aria-label이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("칩 aria-label") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("disabled 상태에서도 aria-label이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("disabled 칩 aria-label") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("선택 가능한 칩을 테스트할 때") {
            Then("filter 칩 초기 선택 상태가 올바르게 설정되어야 한다") {
                val log = logs.find { it.toString().startsWith("칩 선택: 초기") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("filter 칩 선택이 올바르게 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("칩 선택: select(true)") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("filter 칩 선택 해제가 올바르게 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("칩 선택: select(false)") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("input 칩 초기 선택 상태가 올바르게 설정되어야 한다") {
                val log = logs.find { it.toString().startsWith("input 칩 선택: 초기") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("input 칩 선택이 올바르게 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("input 칩 선택: select()") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("제거 가능한 칩을 테스트할 때") {
            Then("removable filter 칩이 올바르게 설정되어야 한다") {
                val log = logs.find { it.toString().startsWith("removable filter 칩: removable") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("removable filter 칩의 ariaLabelRemove가 설정되어야 한다") {
                val log = logs.find { it.toString().startsWith("removable filter 칩: ariaLabelRemove") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("removeOnly input 칩이 올바르게 설정되어야 한다") {
                val log = logs.find { it.toString().startsWith("removeOnly input 칩: removeOnly") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("removeOnly input 칩의 ariaLabelRemove가 설정되어야 한다") {
                val log = logs.find { it.toString().startsWith("removeOnly input 칩: ariaLabelRemove") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("비활성화 칩을 테스트할 때") {
            Then("disabled assist 칩이 올바르게 설정되어야 한다") {
                val log = logs.find { it.toString().startsWith("disabled assist 칩") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("toggle 칩 초기 상태가 활성화되어야 한다") {
                val log = logs.find { it.toString().startsWith("toggle 칩: 초기") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("toggle 칩 disable이 올바르게 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("toggle 칩: disable(true)") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("toggle 칩 enable이 올바르게 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("toggle 칩: enable()") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("toggle 칩 enable(false)가 올바르게 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("toggle 칩: enable(false)") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }
    }
})
