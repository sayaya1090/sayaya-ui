package dev.sayaya.ui.test

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.string.shouldNotContain

@GwtHtml("list.html") // 테스트 서버 루트는 war(src/test/webapp)
internal class ListElementTest: GwtTestSpec({
    Given("Material Design List") {
        // Thread.sleep은 Playwright 이벤트를 처리하지 않으므로 waitForTimeout 사용
        page.waitForTimeout(5000.0)
        val logs = page.getConsoleLogs()
        println(logs)

        When("기본 리스트를 생성할 때") {
            Then("md-list 태그가 생성되어야 한다") {
                val log = logs.find { it.toString().contains("태그명은 md-list") } as String?
                println(log)
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
            Then("3개의 항목이 생성되어야 한다") {
                val log = logs.find { it.toString().contains("items가 3개") } as String?
                println(log)
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
            Then("보조 텍스트가 표시되어야 한다") {
                val log = logs.find { it.toString().contains("Supporting Text") || it.toString().contains("보조 텍스트") } as String?
                println(log)
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
            Then("후행 텍스트가 표시되어야 한다") {
                val log = logs.find { it.toString().contains("Trailing Supporting Text") || it.toString().contains("후행 텍스트") } as String?
                println(log)
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
        }

        When("List Item 속성을 설정할 때") {
            Then("항목이 비활성화되어야 한다") {
                val log = logs.find { it.toString().contains("disabled 항목") && it.toString().contains("true") } as String?
                println(log)
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
            Then("tabindex가 설정되어야 한다") {
                val log = logs.find { it.toString().contains("tabindex") && it.toString().contains("0") } as String?
                println(log)
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
            Then("tabindex -1이 설정되어야 한다") {
                val log = logs.find { it.toString().contains("tabindex") && it.toString().contains("-1") } as String?
                println(log)
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
        }

        When("List Item 슬롯을 사용할 때") {
            Then("start 슬롯이 표시되어야 한다") {
                val log = logs.find { it.toString().contains("Start Slot") || it.toString().contains("시작 슬롯") } as String?
                println(log)
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
            Then("end 슬롯이 표시되어야 한다") {
                val log = logs.find { it.toString().contains("End Slot") || it.toString().contains("끝 슬롯") } as String?
                println(log)
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
            Then("start와 end 슬롯이 함께 표시되어야 한다") {
                val log = logs.find { it.toString().contains("Start and End Slots") || it.toString().contains("시작과 끝 슬롯") } as String?
                println(log)
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
        }

        When("List Item 타입을 설정할 때") {
            Then("button 타입이 설정되어야 한다") {
                val log = logs.find { it.toString().contains("type") && it.toString().contains("button") } as String?
                println(log)
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
            Then("link 타입이 설정되어야 한다") {
                val log = logs.find { it.toString().contains("type") && it.toString().contains("link") } as String?
                println(log)
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
            Then("href가 설정되어야 한다") {
                val log = logs.find { it.toString().contains("href") && it.toString().contains("material.io") } as String?
                println(log)
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
            Then("target이 _blank로 설정되어야 한다") {
                val log = logs.find { it.toString().contains("target") && it.toString().contains("_blank") } as String?
                println(log)
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
        }

        When("구분선을 추가할 때") {
            Then("전체 너비 구분선이 추가되어야 한다") {
                val log = logs.find { it.toString().contains("Full Divider") || it.toString().contains("전체 너비 구분선") } as String?
                println(log)
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
            Then("여백이 있는 구분선이 추가되어야 한다") {
                val log = logs.find { it.toString().contains("Inset Divider") || it.toString().contains("여백이 있는 구분선") } as String?
                println(log)
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
            Then("시작 여백이 있는 구분선이 추가되어야 한다") {
                val log = logs.find { it.toString().contains("Inset Start Divider") || it.toString().contains("시작 여백이 있는 구분선") } as String?
                println(log)
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
        }
    }
})
