package dev.sayaya.ui.test

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.string.shouldNotContain

@GwtHtml("fab_menu.html") // 테스트 서버 루트는 war(src/test/webapp)
internal class FabMenuElementTest: GwtTestSpec({
    val checks = listOf(
        "토글 aria-haspopup menu",
        "닫힘 aria-expanded false",
        "aria-controls 연결",
        "토글 aria-label",
        "항목 role menuitem",
        "FAB 56px",
        "FAB 높이 56px",
        "닫힌 FAB 모서리 16px",
        "닫힌 FAB primary-container",
        "닫힘 목록 숨김",
        "닫힌 아이콘",
        "열림 aria-expanded true",
        "열림 아이콘 close",
        "닫기 버튼 모서리 full",
        "닫기 버튼 primary",
        "닫기 버튼 56px",
        "닫기 버튼 아이콘 20px",
        "열림 목록 표시",
        "닫기 버튼-목록 간격 8px",
        "항목 간격 4px",
        "항목 높이 56px",
        "항목 좌우 여백 24px",
        "항목 아이콘-라벨 8px",
        "항목 모서리 full",
        "항목 primary-container",
        "항목 아이콘 24px",
        "항목 그림자",
        "열면 첫 항목 포커스",
        "onToggle(true)",
        "항목 선택 handler",
        "항목 선택 후 닫힘",
        "닫히면 FAB 으로 포커스",
        "Esc 로 닫힘",
        "닫힌 아이콘 복원",
        "secondary 항목 secondary-container",
        "secondary 닫기 버튼 secondary",
        "tertiary 항목 tertiary-container",
        "열어도 FAB 위치 그대로(떠 있는 메뉴)",
        "열어도 자리 크기 그대로",
        "열어도 문서 높이 그대로(숨은 스크롤 없음)",
        "목록은 FAB 위에 뜸",
        "넘치지 않는 메뉴는 스크롤 없음",
        "마지막 항목 그림자가 아래에서 잘리지 않음(여유 15px 이상)",
        "항목 그림자가 옆에서 잘리지 않음(여유 11px 이상)",
        "스크롤바 숨김",
        "6개 항목",
        "목록 최대 높이",
        "넘치면 목록만 스크롤",
        "닫기 버튼은 가려지지 않음"
    )
    Given("M3 FAB menu") {
        // Thread.sleep은 Playwright 이벤트를 처리하지 않으므로 waitForTimeout 사용
        page.waitForTimeout(4500.0)
        val logs = page.getConsoleLogs().map { it.toString() }
        println(logs)
        for (c in checks) {
            Then(c) {
                val log = logs.find { it.startsWith("fm: $c -") }
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
        }
    }
})
