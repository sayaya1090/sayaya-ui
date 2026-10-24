package dev.sayaya.ui.test

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.string.shouldNotContain

@GwtHtml("time_picker.html") // 테스트 서버 루트는 war(src/test/webapp)
internal class TimePickerElementTest: GwtTestSpec({
    val checks = listOf(
        "시 표시",
        "분 표시",
        "처음에는 시 선택",
        "오전 선택",
        "시 숫자 12개",
        "선택된 시 aria-selected",
        "24시간 숫자 24개",
        "24시간 오전/오후 숨김",
        "입력칸 값",
        "다이얼 256px",
        "다이얼 원형",
        "다이얼 surface-container-highest",
        "핸들 48px",
        "핸들 primary",
        "트랙 2px",
        "중심점 8px",
        "핸들은 선택한 시 위",
        "선택된 숫자 on-primary",
        "시간 선택칸 96px",
        "시간 선택칸 높이 80px",
        "시간 선택칸 모서리 8px",
        "선택 칸 primary-container",
        "기본 칸 surface-container-highest",
        "오전/오후 52px",
        "오전/오후 높이 80px",
        "오전/오후 테두리 outline",
        "선택 오전 tertiary-container",
        "카드 모서리 28px",
        "시 고르면 onChange",
        "시 고르면 분 선택으로",
        "분 숫자 00~55",
        "분 고르면 onChange",
        "오후로 바꾸면 15시",
        "오후 선택 표시",
        "시 칸을 누르면 시 선택",
        "24시간 안쪽 고리 20시",
        "입력칸 96px",
        "입력칸 높이 72px",
        "입력 오전/오후 높이 72px",
        "입력칸 inputmode numeric",
        "시 입력하면 onChange",
        "↑ 로 한 시간 증가",
        "범위 밖 분은 59로",
        "modal 닫힘 숨김",
        "modal 열림",
        "modal role dialog",
        "modal 제목",
        "modal 스크림 0.32",
        "modal 확인 전 onChange 없음",
        "modal 취소는 되돌림",
        "modal 확인으로 확정",
        "modal Esc 로 닫힘",
        "24시간 바깥 다이얼 맨 위 00",
        "24시간 안쪽 다이얼 맨 위 12",
        "24시간 바깥 다이얼 0–11",
        "20시는 안쪽 다이얼에서 선택",
        "12시 위치는 안쪽 맨 위",
        "dial 에 키보드 아이콘",
        "키보드 아이콘 라벨",
        "전환 버튼 48px",
        "키보드 아이콘으로 input 전환",
        "input 전환 시 입력칸 높이 72px",
        "input 에 시계 아이콘",
        "전환 후 입력칸 포커스",
        "시계 아이콘으로 dial 복귀",
        "dial 복귀 시 입력한 시 반영",
        "dial 복귀 시 다이얼 9시 선택",
        "전환만으로는 확정 안 됨",
        "확인으로 확정"
    )
    Given("M3 Time picker") {
        // Thread.sleep은 Playwright 이벤트를 처리하지 않으므로 waitForTimeout 사용
        page.waitForTimeout(5000.0)
        val logs = page.getConsoleLogs().map { it.toString() }
        println(logs)
        for (c in checks) {
            Then(c) {
                val log = logs.find { it.startsWith("tp: $c -") }
                log.shouldNotBeNull()
                log shouldNotContain "Assertion failed!"
            }
        }
    }
})
