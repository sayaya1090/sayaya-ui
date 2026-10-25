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
        "하나를 열면 다른 검색 바는 스크림 아래",
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
        "걸러진 제안이 없으면 빈 목록 숨김",
        "바 모서리 전환",
        "빈 목록이면 바 하단 모서리도 둥글게 28px",
        "빈 목록이면 바 상단 모서리 28px 유지",
        "테스트 뒤 데모 입력 비움(제안 4개 복원)",
        "full-screen 화면 고정",
        "full-screen 화면 전체 높이",
        "full-screen 헤더 72px",
        "full-screen 모서리 없음",
        "full-screen 뒤로 가기 아이콘",
        "divided 구분선 1px",
        "뒤로 가기로 닫힘",
        "닫힌 바 여백 24px",
        "닫힌 바 너비(400-48)",
        "여백 변수로 변경",
        "여는 도중 목록 페이드 인(opacity < 1)",
        "여는 도중 스크림 페이드 인(opacity < 0.32)",
        "여는 도중 목록 왼쪽 끝 = 바 왼쪽 끝",
        "여는 도중 목록 오른쪽 끝 = 바 오른쪽 끝",
        "포커스하면 여백 12px",
        "포커스하면 바가 24px 넓어짐",
        "목록은 바와 같은 왼쪽 끝",
        "docked 스크림 0.32",
        "열린 검색은 형제 위로(z-index)",
        "닫는 도중 목록이 바로 사라지지 않음(전환)",
        "닫힘 전환 뒤 목록 숨김",
        "닫힘 전환 뒤 z-index 복귀",
        // 2.5.0.7 명령 팔레트
        "항목 이름·보조 문구·앞 요소",
        "focus() 로 입력 포커스·열림",
        "inline 목록은 흐름 안(static)",
        "inline 은 스크림 없음",
        "같은 이름 항목도 행별 동작 실행",
        "동작 항목은 입력값·onSearch 를 건드리지 않고 닫음",
        "↓·Enter 로 동작 항목 실행",
        "조합 중 Enter 는 선택하지 않음",
        "조합 중 ↓ 는 하이라이트를 옮기지 않음",
        "조합 중 Esc 는 닫지 않음",
        "목록 교체 뒤 같은 항목에 하이라이트 유지",
        "사라진 항목이면 하이라이트 해제(aria-activedescendant 없음)",
        "key 가 같으면 새 객체로 바꿔도 하이라이트 유지",
        "늦게 온 다른 질의 결과는 버림",
        "현재 질의 결과는 그대로 반영(filter false)",
        "clearSuggestions 로 비움",
        "열린 상태 Esc 는 소비하고 닫음",
        "닫힌 상태 Esc 는 기본 동작 유지(다이얼로그가 받음)",
        "reset() 으로 값 비우고 닫음",
        "기본 아이콘은 SVG(search·close·arrow_back·history)",
        "아이콘 글자 없음(폰트 불필요)",
        "아이콘 교체(searchIcon·clearIcon·historyIcon)"
    )
    Given("M3 Search") {
        // Thread.sleep은 Playwright 이벤트를 처리하지 않으므로 waitForTimeout 사용
        page.waitForTimeout(4500.0)
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
