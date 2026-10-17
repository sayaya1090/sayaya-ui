package dev.sayaya.ui.test

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.string.shouldNotContain

@GwtHtml("navigation.html") // 테스트 서버 루트는 war(src/test/webapp)
internal class NavigationElementTest: GwtTestSpec({
    val tags = listOf("md-top-app-bar", "md-navigation-bar", "md-navigation-tab", "md-navigation-rail",
        "md-navigation-rail-item", "md-navigation-drawer", "md-navigation-drawer-modal")
    val behaviors = listOf(
        "md-top-app-bar scrolled 속성 반영", "md-navigation-rail expanded 항목 전파", "md-navigation-drawer opened 반영",
        "md-navigation-rail 클릭 시 activeIndex", "md-navigation-rail onActivated 이벤트", "md-navigation-rail-item 클릭 후 active",
        "md-navigation-bar 클릭 시 activeIndex", "md-navigation-bar onActivated 이벤트", "md-navigation-tab 클릭 후 active",
    )
    Given("M3 네비게이션 컴포넌트") {
        // Thread.sleep은 Playwright 이벤트를 처리하지 않으므로 waitForTimeout 사용
        page.waitForTimeout(5000.0)
        val logs = page.getConsoleLogs().map { it.toString() }
        println(logs)
        for (tag in tags) {
            When("$tag 를 빌더로 생성할 때") {
                for (check in listOf("태그명", "속성 설정", "업그레이드")) {
                    Then("$check 검증이 통과해야 한다") {
                        val log = logs.find { it.startsWith("nav: $tag $check -") }
                        log.shouldNotBeNull()
                        log shouldNotContain "Assertion failed!"
                    }
                }
            }
        }
        When("상호작용할 때") {
            for (b in behaviors) {
                Then("$b") {
                    val log = logs.find { it.startsWith("nav: $b -") }
                    log.shouldNotBeNull()
                    log shouldNotContain "Assertion failed!"
                }
            }
        }
    }
})
