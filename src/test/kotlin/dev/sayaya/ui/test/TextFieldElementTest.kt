package dev.sayaya.ui.test

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.string.shouldNotContain

@GwtHtml("src/test/webapp/text_field.html")
internal class TextFieldElementTest: GwtTestSpec({
    Given("Material Design Text Field") {
        Thread.sleep(5000) // 비동기 작업 대기
        val logs = document.getConsoleLogs()
        println(logs)

        When("텍스트 필드 변형을 생성할 때") {
            Then("filled 텍스트 필드가 생성되어야 한다") {
                val log = logs.find { it.toString().startsWith("filled 텍스트 필드") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("outlined 텍스트 필드가 생성되어야 한다") {
                val log = logs.find { it.toString().startsWith("outlined 텍스트 필드") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("기본 속성을 설정할 때") {
            Then("label 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("label 속성") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("value 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("value 속성") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("placeholder 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("placeholder 속성") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("required 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("required 속성") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("disabled 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("disabled 속성") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("name 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("name 속성") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("입력 타입을 설정할 때") {
            Then("email 타입이 적용되어야 한다") {
                val log = logs.find { it.toString().contains("type 속성: 'email'") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("password 타입이 적용되어야 한다") {
                val log = logs.find { it.toString().contains("type 속성: 'password'") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("number 타입이 적용되어야 한다") {
                val log = logs.find { it.toString().contains("type 속성: 'number'") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("min/max/step 속성이 적용되어야 한다") {
                val logs = logs.filter { it.toString().contains("min 속성") || it.toString().contains("max 속성") || it.toString().contains("step 속성") }
                logs.forEach { log ->
                    println(log)
                    (log as String).let { it shouldNotContain "Assertion failed!" }
                }
            }
        }

        When("유효성 검증을 설정할 때") {
            Then("error 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("error 속성") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("errorText 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("errorText 속성") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("pattern 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("pattern 속성") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("maxLength 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("maxLength 속성") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("minLength 속성이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("minLength 속성") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("supporting text가 렌더링되어야 한다") {
                val log = logs.find { it.toString().startsWith("supportingText") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("아이콘 슬롯을 추가할 때") {
            Then("leading icon이 렌더링되어야 한다") {
                val log = logs.find { it.toString().startsWith("leading icon") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("trailing icon이 렌더링되어야 한다") {
                val log = logs.find { it.toString().startsWith("trailing icon") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("양쪽 아이콘이 모두 렌더링되어야 한다") {
                val log = logs.find { it.toString().startsWith("both icons") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("prefix와 suffix를 설정할 때") {
            Then("prefixText가 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("prefixText 속성") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("suffixText가 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("suffixText 속성") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("prefix와 suffix가 함께 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("prefix와 suffix") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("다양한 옵션을 설정할 때") {
            Then("rows와 cols가 적용되어야 한다") {
                val logs = logs.filter { it.toString().startsWith("rows 속성") || it.toString().startsWith("cols 속성") }
                logs.forEach { log ->
                    println(log)
                    (log as String).let { it shouldNotContain "Assertion failed!" }
                }
            }
            Then("readOnly가 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("readOnly 속성") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("multiple이 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("multiple 속성") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("autocomplete가 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("autocomplete 속성") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("inputMode가 적용되어야 한다") {
                val log = logs.find { it.toString().startsWith("inputMode 속성") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("이벤트를 처리할 때") {
            Then("onChange 이벤트가 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("onChange 이벤트") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("onInput 이벤트가 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("onInput 이벤트") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("Constraint 검증을 사용할 때") {
            Then("checkValidity()가 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("constraint validation") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("reportValidity()가 에러를 표시해야 한다") {
                val log = logs.find { it.toString().startsWith("reportValidity") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("ValidityState 객체가 존재해야 한다") {
                val log = logs.find { it.toString().startsWith("ValidityState") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("패턴 검증이 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("pattern validation") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }

        When("커스텀 검증을 사용할 때") {
            Then("setCustomValidity()가 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("custom validation") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("수동 에러 상태 설정이 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("manual error") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
            Then("동적 에러 업데이트가 동작해야 한다") {
                val log = logs.find { it.toString().startsWith("dynamic error") } as String?
                println(log)
                log?.let { it shouldNotContain "Assertion failed!" }
            }
        }
    }
})
