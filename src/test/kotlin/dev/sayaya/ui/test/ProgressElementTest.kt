package dev.sayaya.ui.test

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.string.shouldNotContain

@GwtHtml("src/test/webapp/progress.html")
internal class ProgressElementTest: GwtTestSpec({
    Given("Material Design Progress") {
        Thread.sleep(5000) // 비동기 작업 대기
        val logs = document.getConsoleLogs()
        println(logs)

        When("Progress 변형을 생성할 때") {
            Then("linear progress가 생성되어야 한다") {
                val log = logs.find { it.toString().contains("초기 값: 0.5여야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("linear progress의 최대값이 올바르게 설정되어야 한다") {
                val log = logs.find { it.toString().contains("최대값: 1이어야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("circular progress가 생성되어야 한다") {
                val log = logs.find { it.toString().contains("초기 값: 0.7이어야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("buffer가 설정되어야 한다") {
                val log = logs.find { it.toString().contains("buffer: 0.6이어야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("기본 속성을 설정할 때") {
            Then("value가 올바르게 설정되어야 한다") {
                val log = logs.find { it.toString().startsWith("value: 0.75여야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("max가 올바르게 설정되어야 한다") {
                val log = logs.find { it.toString().startsWith("max: 100이어야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("buffer가 올바르게 설정되어야 한다") {
                val log = logs.find { it.toString().startsWith("buffer: 0.7이어야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("percentage가 올바르게 계산되어야 한다") {
                val log = logs.find { it.toString().contains("percentage 계산 확인") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("getValue가 올바르게 동작해야 한다") {
                val log = logs.find { it.toString().contains("getValue(): 0.5를 반환해야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("getMax가 올바르게 동작해야 한다") {
                val log = logs.find { it.toString().contains("getMax(): 1.0을 반환해야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("getBuffer가 올바르게 동작해야 한다") {
                val log = logs.find { it.toString().contains("getBuffer(): 0.8을 반환해야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("Indeterminate를 사용할 때") {
            Then("linear indeterminate가 올바르게 설정되어야 한다") {
                val log = logs.find { it.toString().startsWith("indeterminate: true여야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("circular indeterminate가 올바르게 설정되어야 한다") {
                val log = logs.find { it.toString().contains("circular indeterminate: true여야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("fourColor가 올바르게 설정되어야 한다") {
                val log = logs.find { it.toString().startsWith("fourColor: true여야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("circular fourColor가 올바르게 설정되어야 한다") {
                val log = logs.find { it.toString().contains("circular fourColor: true여야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("isIndeterminate가 올바르게 동작해야 한다") {
                val log = logs.find { it.toString().contains("isIndeterminate(): true를 반환해야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("isFourColor가 올바르게 동작해야 한다") {
                val log = logs.find { it.toString().contains("isFourColor(): true를 반환해야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }
    }
})
