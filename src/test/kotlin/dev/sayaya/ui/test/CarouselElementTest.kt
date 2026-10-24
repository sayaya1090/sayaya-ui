package dev.sayaya.ui.test

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.string.shouldNotContain

@GwtHtml("carousel.html") // 테스트 서버 루트는 war(src/test/webapp)
internal class CarouselElementTest: GwtTestSpec({
    val checks = listOf(
        "role region",
        "roledescription carousel",
        "aria-label",
        "항목 role group",
        "항목 aria-label",
        "tab 순서는 하나(건너뛰기)",
        "앞 여백 16px",
        "뒤 여백 16px",
        "uncontained 뒤 여백 없음",
        "항목 간격 8px",
        "스냅",
        "항목 모서리 28px",
        "항목 너비 200px",
        "hero 큰 항목 320px",
        "첫 항목은 다 보임",
        "둘째 항목도 다 보임",
        "가장자리 항목은 최소 40px",
        "가장자리 항목 clip-path",
        "uncontained 는 자르지 않음",
        "스크롤하면 앞쪽 항목이 작아짐",
        "스크롤하면 다음 항목이 커짐",
        "→ 다음 항목 포커스",
        "tabindex 이동",
        "Enter 로 활성화",
        "End 마지막 항목",
        "클릭으로 활성화",
        "모두 보기 여백 4px",
        "모두 보기 동작",
        "위 여백 8px",
        "아래 여백 8px",
        "center-aligned 큰 항목이 가운데",
        "center-aligned 큰 항목 320px",
        "center-aligned 왼쪽 작은 항목 56px",
        "center-aligned 오른쪽 작은 항목 56px",
        "center-aligned snap center",
        "full-screen 세로 배치",
        "full-screen 여백 0",
        "full-screen 항목 간격 16px",
        "full-screen 세로 snap",
        "full-screen 한 항목씩(snap-stop always)",
        "full-screen 항목이 꽉 참(너비)",
        "full-screen 항목이 꽉 참(높이)",
        "full-screen ↓ 로 다음 항목",
        "uncontained snap 없음",
        "multi-aspect 항목 너비",
        "multi-aspect 앞 여백만",
        "multi-aspect 항목 간격 8px"
    )
    Given("M3 Carousel") {
        // Thread.sleep은 Playwright 이벤트를 처리하지 않으므로 waitForTimeout 사용
        page.waitForTimeout(4500.0)
        val logs = page.getConsoleLogs().map { it.toString() }
        println(logs)
        for (c in checks) {
            Then(c) {
                val log = logs.find { it.startsWith("ca: $c -") }
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
        }
        // 실제 마우스·키보드 입력(Playwright)
        fun num(s: String): Double = (page.evaluate(s) as Number).toDouble()
        val track = "document.querySelector('#ca-drag .track')"
        Then("드래그: 마우스로 끌면 넘어가고 놓으면 가까운 항목에 맞춘다(클릭으로 활성화되지 않음)") {
            page.evaluate("$track.scrollLeft = 0")
            page.locator("#ca-drag").scrollIntoViewIfNeeded()
            page.waitForTimeout(300.0)
            val before = num("window.__caActivated || 0")
            val box = page.locator("#ca-drag .track").boundingBox()!!
            val y = box.y + box.height / 2
            page.mouse().move(box.x + 300, y)
            page.mouse().down()
            page.mouse().move(box.x + 120, y, com.microsoft.playwright.Mouse.MoveOptions().setSteps(12))
            page.mouse().up()
            page.waitForTimeout(900.0)
            val left = num("$track.scrollLeft")
            val after = num("window.__caActivated || 0")
            println("ca-drag scrollLeft=$left activated $before -> $after")
            (Math.abs(left - 208) <= 2).shouldBeTrue()
            (after == before).shouldBeTrue()
        }
        Then("전환: 키보드로 넘기면 부드럽게 스크롤된다") {
            page.evaluate("$track.scrollLeft = 0")
            page.waitForTimeout(300.0)
            page.focus("#ca-drag .item[data-index='0']")
            page.keyboard().press("ArrowRight")
            // 느린 러너에서도 흔들리지 않게 16ms 간격으로 600ms 동안 위치를 모은다
            @Suppress("UNCHECKED_CAST")
            val samples = (page.evaluate("""new Promise(r => { const t = $track, v = []; const s = performance.now();
              (function f() { v.push(t.scrollLeft); if (performance.now() - s < 600) setTimeout(f, 16); else r(v); })(); })""") as List<Number>).map { it.toDouble() }
            page.waitForTimeout(400.0)
            val end = num("$track.scrollLeft")
            println("ca-smooth samples=${samples.size} mids=${samples.filter { it > 0.5 && it < end - 0.5 }.size} end=$end")
            samples.any { it > 0.5 && it < end - 0.5 }.shouldBeTrue()
            (Math.abs(end - 208) <= 2).shouldBeTrue()
        }
    }
})
