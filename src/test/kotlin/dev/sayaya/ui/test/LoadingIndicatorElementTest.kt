package dev.sayaya.ui.test

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.string.shouldNotContain

@GwtHtml("loading_indicator.html") // 테스트 서버 루트는 war(src/test/webapp)
internal class LoadingIndicatorElementTest: GwtTestSpec({
    val checks = listOf(
        "태그명 div",
        "role progressbar",
        "aria-label 설정",
        "기본 aria-label",
        "지시자는 aria-hidden",
        "스타일시트 한 번만 주입",
        "버튼 안 지시자는 버튼 배경과 다른 색",
        "filled 버튼 안 지시자 on-primary",
        "버튼 안 지시자는 슬롯 아이콘 크기(18px)",
        "버튼 안 지시자는 버튼 안에 다 들어감",
        "버튼 높이 40px 유지",
        "outlined 버튼 안 지시자 primary",
        "기본 크기 48px",
        "기본 높이 48px",
        "도형 38px",
        "기본 컨테이너 없음(투명)",
        "지시자 색 primary",
        "contained 컨테이너 primary-container",
        "contained 원형",
        "contained 지시자 on-primary-container",
        "contained 크기 48px",
        "크기 24px",
        "크기 240px",
        "240px 일 때 도형 190px(비율 유지)",
        "24 미만은 24px 로 제한",
        "240 초과는 240px 로 제한",
        "shape morph·회전 애니메이션",
        "무한 반복",
        "전체 회전 4666ms 등속",
        "전환 650ms × 28회 주기",
        "움직임 줄이기 설정 지원",
        "도형은 clip-path polygon",
        "시간이 지나면 도형이 바뀜(morph)"
    )
    Given("M3 Loading indicator") {
        // Thread.sleep은 Playwright 이벤트를 처리하지 않으므로 waitForTimeout 사용
        page.waitForTimeout(4000.0)
        val logs = page.getConsoleLogs().map { it.toString() }
        println(logs)
        for (c in checks) {
            Then(c) {
                val log = logs.find { it.startsWith("li: $c -") }
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
        }
    }
})
