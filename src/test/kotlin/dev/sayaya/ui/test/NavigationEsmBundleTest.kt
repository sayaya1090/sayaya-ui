package dev.sayaya.ui.test

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.string.shouldNotContain

/** 단독 ESM 번들(sayaya-ui-navigation.js)을 Material Web 2.5.0 icon-button 과 함께 실었을 때의 공존·링크·키보드·메뉴 아이콘 검증 */
@GwtHtml("navigation_esm.html")
internal class NavigationEsmBundleTest: GwtTestSpec({
    val checks = listOf(
        "단독 번들 import 오류 없음",
        "레일·앱 바·내비게이션 바 정의",
        "다른 md-* 는 정의하지 않음",
        "앱이 정의한 Material Web 요소는 그대로",
        "두 번 실어도 중복 정의 오류 없음",
        "href 항목은 <a href> 로 그림",
        "링크 레일은 tablist 역할을 쓰지 않음",
        "일반 클릭은 activated(index·href)",
        "activated 에서 preventDefault 하면 기본 이동 막음",
        "preventDefault 안 하면 링크대로 이동",
        "수식 키 클릭은 activated 없음·기본 동작 유지",
        "가운데 클릭은 activated 없음·기본 동작 유지",
        "활성 링크 항목 aria-current=page",
        "↓ 다음 항목으로 이동",
        "↓ disabled 항목은 건너뜀",
        "끝에서 ↓ 는 감기지 않음",
        "↑ 이전 항목으로 이동",
        "처음에서 ↑ 는 감기지 않음",
        "End 마지막 항목",
        "Home 첫 항목",
        "화살표 이동은 활성화하지 않음",
        "기본 메뉴 아이콘 SVG(menu)",
        "펼치면 기본 아이콘 menu_open SVG",
        "menu-icon 슬롯으로 아이콘 교체",
        "menu-open-icon 없으면 펼침에도 menu-icon",
        "md-top-app-bar 표시(64px)",
        "검사 끝"
    )
    Given("Material Web 2.5.0 icon-button + sayaya-ui-navigation.js") {
        page.waitForTimeout(4000.0)
        val logs = page.getConsoleLogs().map { it.toString() }
        println(logs)
        for (c in checks) {
            Then(c) {
                val log = logs.find { it.startsWith("navesm: $c -") }
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
        }
    }
})
