package dev.sayaya.ui.test

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.string.shouldNotContain

@GwtHtml("side_sheet.html") // 테스트 서버 루트는 war(src/test/webapp)
internal class SideSheetElementTest: GwtTestSpec({
    val checks = listOf(
        "Esc 로 닫힘",
        "detached 모든 모서리 16px",
        "detached 여백 16px",
        "detached 위 여백 16px",
        "modal aria-modal",
        "modal role dialog",
        "modal 그림자(elevation 1)",
        "modal 끝 모서리 0px",
        "modal 시작 모서리 16px",
        "modal 오른쪽 가장자리에 붙음",
        "side-sheet 속성 설정",
        "side-sheet 업그레이드",
        "side-sheet 태그명",
        "standard 구분선 1px",
        "standard 그림자 없음(elevation 0)",
        "standard 너비 256px",
        "standard 높이 100%",
        "standard 모서리 0px",
        "start 시트 왼쪽 모서리 16px",
        "닫기 버튼으로 닫힘",
        "닫기 아이콘 버튼 항상 있음",
        "닫힌 standard 는 숨김",
        "뒤로 버튼 이벤트",
        "뒤로 아이콘 있을 때 시작 패딩 16px",
        "스크림 불투명도 0.32",
        "스크림 클릭으로 닫힘",
        "액션 아래 패딩 24px",
        "액션 영역 높이 = 위16 + 버튼 + 아래24 (+구분선 1)",
        "액션 영역 최소 높이 72px",
        "액션 왼쪽 정렬",
        "액션 위 패딩 16px",
        "열면 닫기 버튼으로 포커스",
        "최대 너비 400px",
        "헤더 요소 간격 12px",
        "헤더 좌우 패딩 24px",
        "헤드라인 title-large 22px"
    )
    Given("M3 side sheet") {
        // Thread.sleep은 Playwright 이벤트를 처리하지 않으므로 waitForTimeout 사용
        page.waitForTimeout(6000.0)
        val logs = page.getConsoleLogs().map { it.toString() }
        println(logs)
        for (c in checks) {
            Then(c) {
                val log = logs.find { it.startsWith("ss: $c -") }
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
        }
    }
})
