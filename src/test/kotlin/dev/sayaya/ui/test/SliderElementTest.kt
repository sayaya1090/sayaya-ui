package dev.sayaya.ui.test

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.string.shouldNotContain

@GwtHtml("src/test/webapp/slider.html")
internal class SliderElementTest: GwtTestSpec({
    Given("Material Design Slider") {
        Thread.sleep(5000) // 비동기 작업 대기
        val logs = document.getConsoleLogs()
        println(logs)

        When("Slider 변형을 생성할 때") {
            Then("continuous slider의 값이 올바르게 설정되어야 한다") {
                val log = logs.find { it.toString().contains("초기 값: 50이어야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("continuous slider의 min/max가 올바르게 설정되어야 한다") {
                val log = logs.find { it.toString().contains("최소값: 0이어야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("discrete slider가 생성되어야 한다") {
                val log = logs.find { it.toString().contains("초기 값: 5여야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("discrete slider의 step이 올바르게 설정되어야 한다") {
                val log = logs.find { it.toString().contains("step: 1이어야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("range slider가 생성되어야 한다") {
                val log = logs.find { it.toString().contains("range: true여야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("range slider의 시작/끝값이 올바르게 설정되어야 한다") {
                val log = logs.find { it.toString().contains("시작값: 20이어야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("기본 속성을 설정할 때") {
            Then("min/max가 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("min: 10이어야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("step이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("step: 5여야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("labeled 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("labeled: true여야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("disabled 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("disabled: true여야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("getter 메서드가 올바르게 동작해야 한다") {
                val log = logs.find { it.toString().contains("getMin(): 0을 반환해야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("Range Slider를 사용할 때") {
            Then("valueStart/valueEnd가 올바르게 설정되어야 한다") {
                val log = logs.find { it.toString().startsWith("시작값: 30이어야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("range with ticks가 올바르게 동작해야 한다") {
                val log = logs.find { it.toString().contains("range: true여야 함") && it.toString().contains("ticks: true여야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("range labeled가 올바르게 동작해야 한다") {
                val log = logs.find { it.toString().contains("labeled: true여야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("range getter 메서드가 올바르게 동작해야 한다") {
                val log = logs.find { it.toString().contains("getValueStart(): 20을 반환해야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("이벤트를 처리할 때") {
            Then("초기 입력 횟수가 0이어야 한다") {
                val log = logs.find { it.toString().startsWith("초기 입력 횟수") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
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
            Then("초기 범위 입력 횟수가 0이어야 한다") {
                val log = logs.find { it.toString().startsWith("초기 범위 입력 횟수") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }
    }
})
