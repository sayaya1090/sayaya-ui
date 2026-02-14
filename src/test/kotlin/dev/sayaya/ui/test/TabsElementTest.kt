package dev.sayaya.ui.test

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.string.shouldNotContain

@GwtHtml("src/test/webapp/tabs.html")
internal class TabsElementTest: GwtTestSpec({
    Given("Material Design Tabs") {
        Thread.sleep(5000) // 비동기 작업 대기
        val logs = page.getConsoleLogs()
        println(logs)

        When("Tabs 변형을 생성할 때") {
            Then("primary tabs가 생성되어야 한다") {
                val log = logs.find { it.toString().contains("탭 개수: 3개여야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("secondary tabs가 생성되어야 한다") {
                val log = logs.find { it.toString().contains("Secondary 탭 개수: 3개여야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("아이콘이 포함된 primary tabs가 생성되어야 한다") {
                val log = logs.find { it.toString().contains("아이콘 탭 개수: 3개여야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("autoActivate 속성을 설정할 때") {
            Then("autoActivate가 활성화되어야 한다") {
                val log = logs.find { it.toString().contains("Auto-activate가 활성화되어야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("autoActivate가 비활성화되어야 한다") {
                val log = logs.find { it.toString().contains("Auto-activate가 비활성화되어야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("activeTabIndex 속성을 설정할 때") {
            Then("활성 탭 인덱스가 올바르게 설정되어야 한다") {
                val log = logs.find { it.toString().contains("활성 탭 인덱스: 1이어야 함") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }
    }
})
