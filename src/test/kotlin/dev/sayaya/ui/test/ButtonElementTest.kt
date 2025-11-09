package dev.sayaya.ui.test

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.string.shouldNotContain

@GwtHtml("src/test/webapp/button.html")
internal class ButtonElementTest: GwtTestSpec({
    Given("Material Design Button") {
        Thread.sleep(5000) // 비동기 작업 대기
        val logs = document.getConsoleLogs()
        println(logs)
        When("버튼 변형을 생성할 때") {
            Then("elevated 버튼이 생성되어야 한다") {
                val log = logs.find { it.toString().startsWith("elevated 버튼") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("filled 버튼이 생성되어야 한다") {
                val log = logs.find { it.toString().startsWith("filled 버튼") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("filled-tonal 버튼이 생성되어야 한다") {
                val log = logs.find { it.toString().startsWith("filled-tonal 버튼") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("outlined 버튼이 생성되어야 한다") {
                val log = logs.find { it.toString().startsWith("outlined 버튼") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("text 버튼이 생성되어야 한다") {
                val log = logs.find { it.toString().startsWith("text 버튼") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("버튼 속성을 설정할 때") {
            Then("disabled 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("disabled 속성") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("softDisabled 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("softDisabled 속성") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("href 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("href 속성") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("target 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("target 속성") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("download 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("download 속성") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("폼 관련 속성을 설정할 때") {
            Then("type 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("type 속성") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("name 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("name 속성") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("value 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("value 속성") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("form 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("form 속성") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("아이콘을 추가할 때") {
            Then("leading 아이콘이 추가되어야 한다") {
                val log = logs.find { it.toString().startsWith("선행 아이콘") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("trailing 아이콘이 추가되어야 한다") {
                val log = logs.find { it.toString().startsWith("후행 아이콘") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("이벤트를 처리할 때") {
            Then("onClick 이벤트가 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("onClick 이벤트") } as String?
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
        }

        When("사용 예시를 테스트할 때") {
            Then("버튼 타입별 사용 예시가 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("사용 예시") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("버튼 우선순위와 사용 사례를 테스트할 때") {
            Then("버튼 우선순위가 올바르게 설정되어야 한다") {
                val log = logs.find { it.toString().startsWith("버튼 우선순위") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("버튼 클릭 동작을 테스트할 때") {
            Then("버튼 클릭이 올바르게 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("버튼 클릭") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("비활성화 버튼 동작을 테스트할 때") {
            Then("disabled 버튼이 올바르게 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("disabled 버튼") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("soft-disabled 버튼이 올바르게 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("soft-disabled 버튼") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("동적 상태 변경이 올바르게 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("동적 변경") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("disabled와 soft-disabled 비교가 올바르게 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("비교") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("아이콘 버튼 변형을 생성할 때") {
            Then("standard 아이콘 버튼이 생성되어야 한다") {
                val log = logs.find { it.toString().startsWith("standard 아이콘 버튼") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("filled 아이콘 버튼이 생성되어야 한다") {
                val log = logs.find { it.toString().startsWith("filled 아이콘 버튼") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("filled-tonal 아이콘 버튼이 생성되어야 한다") {
                val log = logs.find { it.toString().startsWith("filled-tonal 아이콘 버튼") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("outlined 아이콘 버튼이 생성되어야 한다") {
                val log = logs.find { it.toString().startsWith("outlined 아이콘 버튼") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("아이콘 버튼 속성을 설정할 때") {
            Then("아이콘이 올바르게 추가되어야 한다") {
                val log = logs.find { it.toString().startsWith("아이콘 버튼: 아이콘이") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("disabled 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("아이콘 버튼 disabled") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("flip-icon-in-rtl 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("아이콘 버튼 flip-icon-in-rtl") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("토글 아이콘 버튼을 테스트할 때") {
            Then("토글 기능이 올바르게 설정되어야 한다") {
                val log = logs.find { it.toString().startsWith("토글 아이콘 버튼: toggle이") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("초기 selected 상태가 올바르게 설정되어야 한다") {
                val log = logs.find { it.toString().startsWith("토글 아이콘 버튼: 초기 selected") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("pre-selected 상태가 올바르게 설정되어야 한다") {
                val log = logs.find { it.toString().startsWith("토글 아이콘 버튼: selected가") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("selected 아이콘이 존재해야 한다") {
                val log = logs.find { it.toString().startsWith("토글 아이콘 버튼: selected 아이콘이") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("aria-label이 올바르게 설정되어야 한다") {
                val log = logs.find { it.toString().startsWith("토글 아이콘 버튼 aria-label") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("aria-label-selected가 올바르게 설정되어야 한다") {
                val log = logs.find { it.toString().startsWith("토글 아이콘 버튼 aria-label-selected") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("링크 아이콘 버튼을 테스트할 때") {
            Then("href 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("아이콘 버튼 href") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("target 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("아이콘 버튼 target") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("아이콘 버튼 접근성을 테스트할 때") {
            Then("aria-label이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("아이콘 버튼 aria-label") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("disabled 상태에서 aria-label이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("disabled 아이콘 버튼 aria-label") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("토글 접근성이 올바르게 설정되어야 한다") {
                val log = logs.find { it.toString().startsWith("토글 접근성") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("onClick 이벤트가 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("아이콘 버튼 onClick") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }
    }
})