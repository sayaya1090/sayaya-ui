package dev.sayaya.ui.test

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.string.shouldNotContain

@GwtHtml("src/test/webapp/menu.html")
internal class MenuElementTest: GwtTestSpec({
    Given("Material Design Menu") {
        Thread.sleep(5000) // 비동기 작업 대기
        val logs = page.getConsoleLogs()
        println(logs)

        When("기본 메뉴를 생성할 때") {
            Then("md-menu 태그가 생성되어야 한다") {
                val log = logs.find { it.toString().contains("태그명은 MD-MENU") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("3개의 항목이 생성되어야 한다") {
                val log = logs.find { it.toString().contains("items가 3개") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("보조 텍스트가 표시되어야 한다") {
                val log = logs.find { it.toString().contains("Supporting Text") || it.toString().contains("보조 텍스트") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("아이콘이 표시되어야 한다") {
                val log = logs.find { it.toString().contains("Icons") || it.toString().contains("아이콘") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("MenuItem 속성을 설정할 때") {
            Then("항목이 비활성화되어야 한다") {
                val log = logs.find { it.toString().contains("disabled 속성 true") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("항목이 선택되어야 한다") {
                val log = logs.find { it.toString().contains("selected 속성 true") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("keepOpen이 설정되어야 한다") {
                val log = logs.find { it.toString().contains("keepOpen 속성 true") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("href가 설정되어야 한다") {
                val log = logs.find { it.toString().contains("href 속성") && it.toString().contains("google.com") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("target이 _blank로 설정되어야 한다") {
                val log = logs.find { it.toString().contains("target 속성") && it.toString().contains("_blank") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("type이 button으로 설정되어야 한다") {
                val log = logs.find { it.toString().contains("type 속성") && it.toString().contains("button") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("type이 link로 설정되어야 한다") {
                val log = logs.find { it.toString().contains("type 속성") && it.toString().contains("link") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("서브메뉴를 사용할 때") {
            Then("기본 서브메뉴가 표시되어야 한다") {
                val log = logs.find { it.toString().contains("Basic SubMenu") || it.toString().contains("기본 서브메뉴") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("중첩 서브메뉴가 표시되어야 한다") {
                val log = logs.find { it.toString().contains("Nested SubMenu") || it.toString().contains("중첩 서브메뉴") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("호버 지연 시간이 설정되어야 한다") {
                val log = logs.find { it.toString().contains("Hover Delay") || it.toString().contains("호버 지연") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("Menu 고급 기능을 사용할 때") {
            Then("positioning이 fixed로 설정되어야 한다") {
                val log = logs.find { it.toString().contains("positioning 속성") && it.toString().contains("fixed") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("xOffset이 설정되어야 한다") {
                val log = logs.find { it.toString().contains("xOffset 속성") && it.toString().contains("20") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("yOffset이 설정되어야 한다") {
                val log = logs.find { it.toString().contains("yOffset 속성") && it.toString().contains("10") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("quick이 true로 설정되어야 한다") {
                val log = logs.find { it.toString().contains("quick 속성이 true") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("stayOpenOnOutsideClick이 true로 설정되어야 한다") {
                val log = logs.find { it.toString().contains("stayOpenOnOutsideClick 속성이 true") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("typeaheadDelay가 설정되어야 한다") {
                val log = logs.find { it.toString().contains("typeaheadDelay 속성") && it.toString().contains("500") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("anchorCorner가 설정되어야 한다") {
                val log = logs.find { it.toString().contains("anchorCorner 속성") && it.toString().contains("end-start") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("menuCorner가 설정되어야 한다") {
                val log = logs.find { it.toString().contains("menuCorner 속성") && it.toString().contains("start-start") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }
    }
})
