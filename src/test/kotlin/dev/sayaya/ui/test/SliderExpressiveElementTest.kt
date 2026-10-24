package dev.sayaya.ui.test

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.string.shouldNotContain

@GwtHtml("slider_expressive.html") // 테스트 서버 루트는 war(src/test/webapp)
internal class SliderExpressiveElementTest: GwtTestSpec({
    val checks = listOf(
        "XS 트랙 높이 16px",
        "XS 트랙 모서리 8px",
        "XS 핸들 높이 44px",
        "XS 핸들 너비 4px",
        "XS 핸들 높이만큼 공간 확보 44px",
        "S 트랙 높이 24px",
        "S 트랙 모서리 8px",
        "S 핸들 높이 44px",
        "S 핸들 너비 4px",
        "S 핸들 높이만큼 공간 확보 44px",
        "M 트랙 높이 40px",
        "M 트랙 모서리 12px",
        "M 핸들 높이 52px",
        "M 핸들 너비 4px",
        "M 핸들 높이만큼 공간 확보 52px",
        "L 트랙 높이 56px",
        "L 트랙 모서리 16px",
        "L 핸들 높이 68px",
        "L 핸들 너비 4px",
        "L 핸들 높이만큼 공간 확보 68px",
        "XL 트랙 높이 96px",
        "XL 트랙 모서리 28px",
        "XL 핸들 높이 108px",
        "XL 핸들 너비 4px",
        "XL 핸들 높이만큼 공간 확보 108px",
        "세로는 세로로 긴 박스",
        "세로 회전 -90°",
        "세로는 아래가 최솟값(80 이 20 보다 위)",
        "세로 stops 함께 사용",
        "inset icon 이름",
        "inset icon 표시",
        "M 크기 inset icon 24px",
        "값이 크면 활성 트랙 안",
        "활성 트랙 안 아이콘 on-primary",
        "값이 작으면 핸들 뒤로",
        "비활성 트랙 아이콘 on-secondary-container",
        "핸들 뒤 아이콘은 핸들 오른쪽",
        "40dp 미만(XS)은 inset icon 숨김",
        "값이 바뀌면 아이콘 위치도 바뀜"
    )
    Given("M3 Slider (Expressive)") {
        // Thread.sleep은 Playwright 이벤트를 처리하지 않으므로 waitForTimeout 사용
        page.waitForTimeout(4500.0)
        val logs = page.getConsoleLogs().map { it.toString() }
        println(logs)
        for (c in checks) {
            Then(c) {
                val log = logs.find { it.startsWith("sx: $c -") }
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
        }
        // 회전(-90°)된 세로 슬라이더를 실제 입력으로 조작한다(Playwright 키보드·마우스, 합성 이벤트 아님)
        fun value(): Double = (page.evaluate("document.getElementById('sx-v-input').value") as Number).toDouble()
        fun reset() { page.evaluate("(() => { const s = document.getElementById('sx-v-input'); s.value = 50; })()") }
        Then("세로 실제 입력: ArrowUp 은 값을 올리고 ArrowDown 은 내린다") {
            reset()
            page.focus("#sx-v-input")
            page.keyboard().press("ArrowUp")
            val up = value()
            page.keyboard().press("ArrowDown")
            page.keyboard().press("ArrowDown")
            val down = value()
            println("sx-v-input key: up=$up down=$down")
            (up > 50.0).shouldBeTrue()
            (down < 50.0).shouldBeTrue()
        }
        Then("세로 실제 입력: 위쪽 끝을 누르면 큰 값, 아래쪽 끝을 누르면 작은 값") {
            reset()
            val box = page.locator("#sx-v-input").boundingBox()!!
            println("sx-v-input box: x=${box.x} y=${box.y} w=${box.width} h=${box.height}")
            (box.height > box.width).shouldBeTrue()
            val cx = box.x + box.width / 2
            page.mouse().click(cx, box.y + box.height * 0.1)
            val top = value()
            page.mouse().click(cx, box.y + box.height * 0.9)
            val bottom = value()
            println("sx-v-input click: top=$top bottom=$bottom")
            (top > 75.0).shouldBeTrue()
            (bottom < 25.0).shouldBeTrue()
        }
        Then("세로 실제 입력: 아래에서 위로 드래그하면 값이 커진다") {
            reset()
            val box = page.locator("#sx-v-input").boundingBox()!!
            val cx = box.x + box.width / 2
            page.mouse().move(cx, box.y + box.height * 0.5)
            page.mouse().down()
            page.mouse().move(cx, box.y + box.height * 0.2, com.microsoft.playwright.Mouse.MoveOptions().setSteps(10))
            page.mouse().up()
            val dragged = value()
            println("sx-v-input drag: $dragged")
            (dragged > 65.0).shouldBeTrue()
        }
    }
})
