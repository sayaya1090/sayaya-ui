package dev.sayaya.ui.test

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.string.shouldNotContain

@GwtHtml("src/test/webapp/dialog.html")
internal class DialogElementTest: GwtTestSpec({
    Given("Material Design Dialog") {
        Thread.sleep(5000) // 비동기 작업 대기
        val logs = document.getConsoleLogs()
        println(logs)

        When("Dialog 기본 속성을 설정할 때") {
            Then("dialog 태그명이 md-dialog이어야 한다") {
                val log = logs.find { it.toString().startsWith("dialog: 태그명은") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("alert dialog type이 'alert'이어야 한다") {
                val log = logs.find { it.toString().startsWith("alert dialog: type은") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("type 속성이 'alert'이어야 한다") {
                val log = logs.find { it.toString().startsWith("type 속성") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("open 속성이 true여야 한다") {
                val log = logs.find { it.toString().startsWith("open 속성") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("quick 속성이 true여야 한다") {
                val log = logs.find { it.toString().startsWith("quick 속성") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("noFocusTrap 속성이 true여야 한다") {
                val log = logs.find { it.toString().startsWith("noFocusTrap 속성") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("Dialog 슬롯을 사용할 때") {
            Then("headline 슬롯이 존재해야 한다") {
                val log = logs.find { it.toString().startsWith("headline 슬롯 존재") || it.toString().startsWith("headline 슬롯") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("커스텀 headline 슬롯이 존재해야 한다") {
                val log = logs.find { it.toString().startsWith("커스텀 headline 슬롯") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("content 슬롯이 존재해야 한다") {
                val log = logs.find { it.toString().startsWith("content 슬롯") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("form method가 'dialog'로 자동 설정되어야 한다") {
                val log = logs.find { it.toString().startsWith("form method") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("actions 슬롯이 존재해야 한다") {
                val log = logs.find { it.toString().startsWith("actions 슬롯") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("Dialog 이벤트를 처리할 때") {
            Then("open 이벤트 횟수가 초기값이어야 한다") {
                val log = logs.find { it.toString().startsWith("초기 이벤트 횟수") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }
    }
})
