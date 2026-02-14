package dev.sayaya.ui.test

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.string.shouldNotContain

@GwtHtml("src/test/webapp/divider.html")
internal class DividerElementTest: GwtTestSpec({
    Given("Material Design Divider") {
        Thread.sleep(5000) // 비동기 작업 대기
        val logs = page.getConsoleLogs()
        println(logs)

        When("기본 구분선을 생성할 때") {
            Then("태그명이 올바르게 설정되어야 한다") {
                val log = logs.find { it.toString().startsWith("구분선: 태그명") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("기본 inset이 false여야 한다") {
                val log = logs.find { it.toString().startsWith("구분선: 기본 inset은") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("기본 insetStart가 false여야 한다") {
                val log = logs.find { it.toString().startsWith("구분선: 기본 insetStart") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("기본 insetEnd가 false여야 한다") {
                val log = logs.find { it.toString().startsWith("구분선: 기본 insetEnd") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("inset 속성을 설정할 때") {
            Then("inset이 true로 설정되어야 한다") {
                val log = logs.find { it.toString().startsWith("구분선 inset: true") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("inset 설정 시 insetStart는 false여야 한다") {
                val log = logs.find { it.toString().startsWith("구분선 inset: insetStart") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("inset 설정 시 insetEnd는 false여야 한다") {
                val log = logs.find { it.toString().startsWith("구분선 inset: insetEnd") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("inset(true)가 올바르게 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("구분선 inset(true)") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("inset(false)가 올바르게 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("구분선 inset(false)") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("insetStart 속성을 설정할 때") {
            Then("insetStart가 true로 설정되어야 한다") {
                val log = logs.find { it.toString().startsWith("구분선 insetStart: true") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("insetStart 설정 시 inset은 false여야 한다") {
                val log = logs.find { it.toString().startsWith("구분선 insetStart: inset") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("insetStart 설정 시 insetEnd는 false여야 한다") {
                val log = logs.find { it.toString().startsWith("구분선 insetStart: insetEnd") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("insetStart(true)가 올바르게 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("구분선 insetStart(true)") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("insetStart(false)가 올바르게 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("구분선 insetStart(false)") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("insetEnd 속성을 설정할 때") {
            Then("insetEnd가 true로 설정되어야 한다") {
                val log = logs.find { it.toString().startsWith("구분선 insetEnd: true") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("insetEnd 설정 시 inset은 false여야 한다") {
                val log = logs.find { it.toString().startsWith("구분선 insetEnd: inset") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("insetEnd 설정 시 insetStart는 false여야 한다") {
                val log = logs.find { it.toString().startsWith("구분선 insetEnd: insetStart") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("insetEnd(true)가 올바르게 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("구분선 insetEnd(true)") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("insetEnd(false)가 올바르게 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("구분선 insetEnd(false)") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("role 속성을 설정할 때") {
            Then("separator role이 올바르게 설정되어야 한다") {
                val log = logs.find { it.toString().startsWith("구분선 role") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("decorative divider가 올바르게 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("구분선 decorative") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("팩토리 메서드를 사용할 때") {
            Then("dividerInset()이 올바르게 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("팩토리 메서드 dividerInset") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("dividerInsetStart()가 올바르게 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("팩토리 메서드 dividerInsetStart") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("dividerInsetEnd()가 올바르게 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("팩토리 메서드 dividerInsetEnd") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("inset 속성들이 상호 배타적으로 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("상호 배타성") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }
    }
})
