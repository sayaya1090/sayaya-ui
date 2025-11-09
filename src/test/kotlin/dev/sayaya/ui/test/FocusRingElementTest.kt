package dev.sayaya.ui.test

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.string.shouldNotContain

@GwtHtml("src/test/webapp/focus_ring.html")
internal class FocusRingElementTest: GwtTestSpec({
    Given("Material Design Focus Ring") {
        Thread.sleep(5000) // 비동기 작업 대기
        val logs = document.getConsoleLogs()
        println(logs)

        When("기본 포커스 링을 생성할 때") {
            Then("태그명이 올바르게 설정되어야 한다") {
                val log = logs.find { it.toString().startsWith("포커스 링: 태그명") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("기본 visible이 false여야 한다") {
                val log = logs.find { it.toString().startsWith("포커스 링: 기본 visible") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("기본 inward가 false여야 한다") {
                val log = logs.find { it.toString().startsWith("포커스 링: 기본 inward") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("inward 속성을 설정할 때") {
            Then("inward가 true로 설정되어야 한다") {
                val log = logs.find { it.toString().startsWith("포커스 링 inward: true") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("outward가 false로 설정되어야 한다") {
                val log = logs.find { it.toString().startsWith("포커스 링 outward") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("포커스 링을 부착할 때") {
            Then("부모 요소에 올바르게 부착되어야 한다") {
                val log = logs.find { it.toString().startsWith("포커스 링 부착") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("버튼에 포커스 링을 추가할 때") {
            Then("컨테이너에 포커스 링이 존재해야 한다") {
                val log = logs.find { it.toString().startsWith("버튼 포커스 링: 컨테이너") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("inward 스타일이 올바르게 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("버튼 포커스 링 inward") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("입력 요소에 포커스 링을 추가할 때") {
            Then("htmlFor 속성이 올바르게 설정되어야 한다") {
                val log = logs.find { it.toString().startsWith("입력 포커스 링") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("명령형으로 포커스 링을 부착할 때") {
            Then("attach 메서드로 control이 설정되어야 한다") {
                val log = logs.find { it.toString().startsWith("명령형 부착: control이 설정") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("control이 올바른 요소여야 한다") {
                val log = logs.find { it.toString().startsWith("명령형 부착: control이 버튼") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("control 속성으로 직접 설정할 수 있어야 한다") {
                val log = logs.find { it.toString().startsWith("control 속성") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }
    }
})
