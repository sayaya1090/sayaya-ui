package dev.sayaya.ui.test

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.string.shouldNotContain

@GwtHtml("i18n.html") // 테스트 서버 루트는 war(src/test/webapp)
internal class I18nElementTest: GwtTestSpec({
    val checks = listOf(
        "snackbar 닫기 기본 Close",
        "snackbar 닫기 덮어쓰기",
        "toast 닫기 기본 Close",
        "toast 닫기 덮어쓰기",
        "rail 메뉴 기본 Open menu",
        "rail 메뉴 덮어쓰기(접힘)",
        "side sheet 닫기 기본 Close",
        "side sheet 뒤로 기본 Back",
        "side sheet 덮어쓰기",
        "bottom sheet 핸들 기본 Resize sheet",
        "rail 펼치면 Close menu",
        "rail 덮어쓰기(펼침)",
        "지정 locale 우선",
        "html lang 사용",
        "setDefault 가 html lang 보다 우선",
        "en-US 주 시작 일요일",
        "de-DE 주 시작 월요일",
        "en-US 12시간제",
        "en-GB 24시간제",
        "ko-KR 오전 표기",
        "en-US PM 표기",
        "en-US 월 표시",
        "en-US 날짜 aria-label",
        "이전 달 기본 Previous month",
        "en-US 첫 요일 일요일",
        "en-US 요일 narrow",
        "de-DE 월 표시",
        "de-DE 첫 요일 월요일",
        "de-DE 1일은 넷째 칸(목)",
        "de-DE Home 은 월요일",
        "weekStart(1) 지정",
        "modal 취소 기본 Cancel",
        "modal 확인 기본 OK",
        "en-US 헤더 날짜",
        "헤드라인 기본 Select date",
        "date label 덮어쓰기",
        "RTL 달력 ← 는 다음 날",
        "en-US AM 표기",
        "en-US 12시간(오전/오후 표시)",
        "시간 헤드라인 기본 Select time",
        "시 선택 기본 Select hour",
        "ko-KR 오전/오후",
        "en-GB 24시간 기본",
        "en-GB 숫자 24개",
        "hour24(false) 덮어쓰기",
        "time label 덮어쓰기(헤드라인)",
        "time label 덮어쓰기(시)",
        "search 기본 Search",
        "지우기 기본 Clear",
        "search label 덮어쓰기",
        "full-screen 닫기 기본 Close search",
        "닫으면 다시 Search",
        "RTL 툴바 ← 는 다음 항목",
        "RTL 캐러셀 첫 항목은 오른쪽",
        "RTL 캐러셀 첫 항목 다 보임",
        "RTL 캐러셀 가장자리 항목 40px",
        "RTL 캐러셀 ← 는 다음 항목",
        "RTL 슬라이더 +5 핸들 왼쪽 25%",
        "RTL 슬라이더 활성 트랙 시작",
        "RTL 슬라이더 활성 트랙 너비"
    )
    Given("M3 i18n") {
        // Thread.sleep은 Playwright 이벤트를 처리하지 않으므로 waitForTimeout 사용
        page.waitForTimeout(4000.0)
        val logs = page.getConsoleLogs().map { it.toString() }
        println(logs)
        for (c in checks) {
            Then(c) {
                val log = logs.find { it.startsWith("i18: $c -") }
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
        }
    }
})
