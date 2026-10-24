package dev.sayaya.ui.test

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.string.shouldNotContain

@GwtHtml("search.html") // 테스트 서버 루트는 war(src/test/webapp)
internal class SearchElementTest: GwtTestSpec({
    val checks = listOf(
        "role search",
        "입력창 role combobox",
        "aria-autocomplete list",
        "aria-controls 연결",
        "닫힘 aria-expanded false",
        "placeholder",
        "검색 바 높이 56px",
        "검색 바 모서리 full",
        "검색 바 surface-container-high",
        "앞 아이콘 on-surface",
        "뒤 아이콘 on-surface-variant",
        "닫힘 목록 숨김",
        "빈 입력이면 지우기 버튼 숨김",
        "포커스하면 열림",
        "열림 목록 표시",
        "제안 전체 표시",
        "docked 열림 바 위 모서리 28px",
        "docked 열림 바 아래 모서리 0",
        "docked 목록 아래 모서리 28px",
        "docked 목록 배경 surface-container-high",
        "docked 스크림 0.32",
        "입력하면 제안 걸러짐",
        "onInput 호출",
        "입력 있으면 지우기 버튼",
        "↓ 로 첫 제안 활성",
        "활성 제안 aria-selected",
        "Enter 로 제안 선택",
        "선택하면 onSearch",
        "선택 후 닫힘",
        "지우기 버튼으로 비움",
        "제안 없이 Enter 는 입력값 검색",
        "Esc 로 닫힘",
        "스크림 클릭으로 닫힘",
        "full-screen 화면 고정",
        "full-screen 화면 전체 높이",
        "full-screen 헤더 72px",
        "full-screen 모서리 없음",
        "full-screen 뒤로 가기 아이콘",
        "divided 구분선 1px",
        "뒤로 가기로 닫힘"
    )
    Given("M3 Search") {
        // Thread.sleep은 Playwright 이벤트를 처리하지 않으므로 waitForTimeout 사용
        page.waitForTimeout(4000.0)
        val logs = page.getConsoleLogs().map { it.toString() }
        println(logs)
        for (c in checks) {
            Then(c) {
                val log = logs.find { it.startsWith("se: $c -") }
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
        }
    }
})
