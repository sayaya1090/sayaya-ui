package dev.sayaya.ui.test

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.string.shouldNotContain

@GwtHtml("gb.html") // 테스트 서버 루트는 war(src/test/webapp)
internal class GbElementTest: GwtTestSpec({
    val tags = listOf(
        "md-gb-badge", "md-gb-button", "md-gb-card", "md-gb-checkbox", "md-gb-divider", "md-gb-fab",
        "md-gb-icon", "md-gb-icon-button", "md-gb-list", "md-gb-list-item", "md-gb-menu", "md-gb-menu-group",
        "md-gb-menu-item", "md-gb-radio", "md-gb-split-button", "md-gb-switch",
    )
    Given("Material Web 2.5.0 labs/gb 컴포넌트") {
        // Thread.sleep은 Playwright 이벤트를 처리하지 않으므로, 지연 로그(업그레이드 검증)까지 받도록 waitForTimeout 사용
        page.waitForTimeout(5000.0)
        val logs = page.getConsoleLogs().map { it.toString() }
        println(logs)
        for (tag in tags) {
            When("$tag 를 빌더로 생성할 때") {
                for (check in listOf("태그명", "속성 설정", "업그레이드", "업그레이드 후 속성 유지")) {
                    Then("$check 검증이 통과해야 한다") {
                        val log = logs.find { it.startsWith("gb: $tag $check -") }
                        println(log)
                        log.shouldNotBeNull()
                        log shouldNotContain "Assertion failed!"
                    }
                }
            }
        }
    }
})
