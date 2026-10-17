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
        "md-navigation-bar 클릭 시 activeIndex",
        "navigation bar 라벨 말줄임 없음",
        "navigation bar 활성 아이콘 채움(FILL 1)",
        "navigation bar 비활성 아이콘 외곽선(FILL 0)",
        "medium flexible 부제 있으면 136px",
        "large flexible 부제 있으면 152px",
        "medium flexible 부제 label-large 14px",
        "앱 바 제목 가운데 정렬",
        "center-aligned 는 small+centered",
        "narrow 레일 너비 80px",
        "레일 메뉴 버튼은 위쪽 header",
        "레일 메뉴 아이콘(접힘) menu",
        "메뉴 버튼으로 레일 펼침",
        "레일 메뉴 아이콘(펼침) menu_open",
        "modal 펼친 레일은 화면 기준(fixed)",
        "modal 펼친 레일 모서리 16px",
        "modal 레일 스크림 클릭으로 접힘",
        "접힌 레일 너비 96px",
        "레일 위쪽 여백 44px",
        "접힌 레일 항목 높이 64px",
        "접힌 레일 항목 간격 4px",
        "접힌 레일 활성 라벨 secondary",
        "펼친 레일 너비 220~360px",
        "펼친 레일 indicator 높이 56px",
        "펼친 레일 indicator 앞 여백 16px",
        "펼친 레일 아이콘-라벨 간격 8px",
        "펼친 레일 indicator 는 내용에 맞춤",
        "펼친 레일 활성 라벨 on-secondary-container",
        "펼친 레일 배지는 라벨 바로 옆",
        "small 앱 바 높이 64px",
        "medium flexible 앱 바 높이 112px",
        "large flexible 앱 바 높이 120px",
        "small 제목 title-large 22px",
        "medium flexible 제목 headline-medium 28px",
        "large flexible 제목 display-small 36px",
        "앱 바 trailing 아이콘 on-surface-variant",
        "스크롤된 앱 바 elevation level2",
        "앱 바 좌우 여백 4px",
        "닫힌 모달 drawer 는 클릭을 막지 않음",
        "모달 drawer 는 화면 기준(fixed)",
        "모달 drawer 스크림 0.32",
        "모달 drawer 스크림 클릭으로 닫힘",
        "모달 drawer 열면 너비 360px",
        "펼친 레일 배지가 라벨과 겹치지 않음(점)",
        "펼친 레일 배지가 라벨과 겹치지 않음(숫자)",
        "md-navigation-bar 는 flexible 구현(labs baseline 아님)",
        "navigation bar 높이 64px",
        "세로형 indicator 56x32",
        "세로형 위 패딩 6px",
        "세로형 indicator-라벨 간격 4px",
        "세로형 항목 균등 너비",
        "라벨 label-medium 12px",
        "large 배지 높이 16px",
        "small 배지 6px",
        "가로형 indicator 높이 40px",
        "가로형 indicator 앞 16px",
        "가로형 아이콘-라벨 4px",
        "가로형 라벨이 indicator 안에",
        "가로형 3개 가운데 정렬 양옆 20%",
        "auto 600 미만 세로형",
        "auto 600 이상 가로형", "md-navigation-bar onActivated 이벤트", "md-navigation-tab 클릭 후 active",
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
