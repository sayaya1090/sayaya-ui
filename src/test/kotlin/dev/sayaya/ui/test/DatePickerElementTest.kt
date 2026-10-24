package dev.sayaya.ui.test

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.string.shouldNotContain

@GwtHtml("date_picker.html") // 테스트 서버 루트는 war(src/test/webapp)
internal class DatePickerElementTest: GwtTestSpec({
    val checks = listOf(
        "role grid",
        "요일 7개",
        "10월 31일",
        "월 표시",
        "1일은 목요일 칸",
        "선택 aria-selected",
        "오늘 aria-current",
        "날짜 aria-label",
        "선택 날짜만 tab 순서",
        "컨테이너 360px",
        "컨테이너 모서리 28px",
        "컨테이너 surface-container-high",
        "컨테이너 그림자(elevation 3)",
        "날짜 40px",
        "날짜 높이 40px",
        "날짜 원형",
        "선택 primary",
        "선택 글자 on-primary",
        "오늘 테두리 1px",
        "오늘 테두리 primary",
        "오늘 글자 primary",
        "→ 다음 날",
        "↓ 다음 주",
        "PageDown 다음 달",
        "PageDown 월 표시",
        "PageUp 이전 달",
        "Home 주의 시작(일요일)",
        "Enter 로 선택·onChange",
        "선택 이동",
        "다음 달 버튼",
        "클릭으로 선택·onChange",
        "modal 닫힘 숨김",
        "modal 열림",
        "modal role dialog",
        "modal aria-modal",
        "modal 제목 연결",
        "modal 헤더 120px",
        "modal 헤더 날짜",
        "modal 스크림 0.32",
        "열면 선택 날짜 포커스",
        "modal 고르면 헤더 갱신",
        "modal 확인 전 onChange 없음",
        "modal 취소는 되돌림",
        "modal 확인으로 확정",
        "modal Esc 로 닫힘",
        "월 버튼 aria-expanded",
        "연도 목록 표시",
        "연도 모드에서 달력 숨김",
        "연도 role listbox",
        "연도 201개",
        "연도 72px",
        "연도 높이 36px",
        "선택 연도 aria-selected",
        "선택 연도 primary",
        "선택 연도 글자 on-primary",
        "다른 연도 on-surface-variant",
        "연도 고르면 달력으로",
        "연도 고르면 목록 닫힘",
        "기간 시작만 고르면 이벤트 없음",
        "거꾸로 골라도 시작·끝 정렬",
        "기간 양 끝 aria-selected",
        "범위 안 날짜 표시",
        "범위 띠 secondary-container",
        "범위 띠 높이 40px",
        "범위 안 글자 on-secondary-container",
        "기간 끝 primary 원",
        "기간 끝 글자 on-primary",
        "시작 띠는 가운데부터",
        "새로 고르면 기간 다시 시작",
        "기간 modal 헤더 128px",
        "기간 modal 헤더 범위",
        "기간 modal 확인 전 이벤트 없음",
        "기간 modal 확인으로 확정"
    )
    Given("M3 Date picker") {
        // Thread.sleep은 Playwright 이벤트를 처리하지 않으므로 waitForTimeout 사용
        page.waitForTimeout(4000.0)
        val logs = page.getConsoleLogs().map { it.toString() }
        println(logs)
        for (c in checks) {
            Then(c) {
                val log = logs.find { it.startsWith("dp: $c -") }
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
        }
    }
})
