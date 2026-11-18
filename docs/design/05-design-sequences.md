# 5. 설계 시퀀스 다이어그램

각 다이어그램은 시스템 오퍼레이션 하나를 객체 수준에서 실현합니다. 메시지 이름은 소스의 메서드·이벤트 이름 그대로입니다.

## DS-1 `navigationRail()` … `onActivated(callback)` (UC1·UC2)

```mermaid
sequenceDiagram
  participant App as 앱 코드
  participant NEB as NavigationElementBuilder
  participant RB as NavigationRailBuilder
  participant HC as elemento HTMLContainerBuilder
  participant El as md-navigation-rail(DOM)
  App->>NEB: navigationRail()
  NEB->>RB: new
  RB->>HC: htmlContainer("md-navigation-rail", MdNavigationRailElement)
  HC-->>RB: that
  NEB-->>App: RB
  App->>RB: item(railItem())
  RB->>El: appendChild(md-navigation-rail-item)
  App->>RB: onActivated(callback)
  RB->>RB: onIndexEvent("navigation-rail-activated", callback)
  RB->>El: addEventListener("navigation-rail-activated")
  App->>RB: element()
  RB-->>App: El
```

## DS-2 항목 선택 (UC3)

```mermaid
sequenceDiagram
  actor U as 사용자
  participant It as MdNavigationRailItem
  participant R as MdNavigationRail
  participant J as Java 콜백(onIndexEvent)
  U->>It: click
  It->>It: _click(e)
  alt disabled
    It-->>U: preventDefault
  else 수식 키·보조 버튼 링크
    It-->>U: 브라우저 기본 동작
  else
    It->>R: dispatch navigation-rail-item-interaction {originalEvent}
    R->>R: items.indexOf(item)
    alt footer 항목(index < 0)
      R-->>U: 무시
    else
      R->>R: activeIndex = index
      R->>J: dispatch navigation-rail-activated {activeIndex, item, href}
      opt 앱이 preventDefault
        R->>It: originalEvent.preventDefault()
      end
      opt modal && expanded
        R->>R: toggle(false)
      end
      R->>R: updated() → _sync() (항목 active 갱신)
    end
  end
```

## DS-3 `toggle()` 펼침/접힘과 전환 모션 (UC4)

```mermaid
sequenceDiagram
  participant R as MdNavigationRail
  participant It as MdNavigationRailItem[]
  participant B as 브라우저(rAF)
  R->>R: toggle(force)
  R->>R: expanded = next
  R->>R: willUpdate(changed) → _motionSnapshot() (이전 위치)
  alt prefers-reduced-motion
    R->>R: _motionFrom = null (즉시 전환)
  end
  R->>R: dispatch expanded-changed {expanded}
  R->>R: updated() → _sync()
  R->>It: expanded 전파
  R->>It: updateComplete 대기
  R->>R: _motionStart(from) → _motionSnapshot() (최종 위치)
  loop spring 진행도 p (DefaultSpatial 0.8/380, modal FastSpatial 0.6/800)
    B->>R: frame(now)
    R->>It: 폭·높이·indicator 크기·transform 보간, 라벨 alpha 4(p-0.5)²
  end
  R->>R: _motionStop() (인라인 스타일 복원, 잔상 제거)
```

## DS-4 직접 구현 빌더 생성과 스타일 1회 주입 (UC1, 예: `datePicker()`)

```mermaid
sequenceDiagram
  participant App as 앱 코드
  participant DP as DatePickerElementBuilder
  participant CEB as CustomElementBuilder
  participant Doc as document.head
  App->>DP: datePicker() … (DatePickerPrepare)
  DP->>DP: injectStyle()
  DP->>CEB: injectStyle(STYLE_ID, css)
  CEB->>CEB: newStyle(id)
  alt 같은 id 가 이미 있음
    CEB-->>DP: return (css 를 만들지 않음)
  else
    CEB->>CEB: style.textContent = css.get()
    CEB->>Doc: appendChild(style)
  end
  App->>DP: locale("ko-KR").open(true)
  App->>DP: element()
  DP-->>App: 루트 div
```

## DS-5 닫힘 사유 전달 (UC5, sheet·snackbar·toast)

```mermaid
sequenceDiagram
  participant El as md-snackbar
  participant SB as SnackbarBuilder
  participant CR as ClosedReason
  participant App as 앱 콜백
  SB->>El: addEventListener("closed", e -> cb.accept(ClosedReason.of(e)))
  El->>El: close(reason)
  El->>SB: closed {reason}
  SB->>CR: of(e)
  CR-->>SB: detail.reason 또는 null
  SB->>App: accept(reason)
```

## DS-6 브라우저 테스트 실행 (UC7)

```mermaid
sequenceDiagram
  participant G as Gradle :test
  participant P as Playwright(GwtTestSpec)
  participant Pg as 테스트 페이지(@GwtHtml)
  participant J as *ComponentsTest(Java, GWT)
  participant TH as TestHelper
  G->>G: copyLabsBundleToTest, copyFeedbackBundleToTest, copyNavigationBundleToTest
  G->>P: 스펙 실행
  P->>Pg: 페이지 열기
  Pg->>J: test()
  J->>TH: assertTrue/assertEquals("<접두어>: <이름>", …)
  TH->>Pg: console.log("... - PASS" 또는 "... - Assertion failed!")
  P->>Pg: getConsoleLogs()
  P->>P: Then("실패한 검사 로그가 없어야 한다(미등록 검사 포함)")
  loop 등록된 이름마다
    P->>P: logs.find(이름) → shouldNotContain "Assertion failed!"
  end
```
