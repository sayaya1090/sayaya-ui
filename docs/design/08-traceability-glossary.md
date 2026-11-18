# 8. 추적표와 용어집

## 추적표 (요구 → 유스케이스 → 시스템 오퍼레이션 → 설계 → 코드 → 테스트)

| 요구 | 유스케이스 | 시스템 오퍼레이션 | 설계 | 코드 | 테스트(Kotest 스펙) |
|---|---|---|---|---|---|
| FR-1 | UC1 | `button()` 등 | 06 A 형태 | `ButtonElementBuilder`, `dom/MdButtonElement` | `ButtonElementTest` 등 |
| FR-2 | UC1 | `card()`, gb 팩토리 | 06 A 형태 | `CardElementBuilder`, `GbElementBuilder` | `CardElementTest`, `GbElementTest` |
| FR-3 | UC1·UC3·UC4·UC5 | `navigationRail()`, `snackbar()`, `datePicker()` … | DS-1~5, 06 B·C | `NavigationElementBuilder`, `FeedbackElementBuilder`, `CustomElementBuilder` 하위, `sayaya-*.js` | `NavigationElementTest`, `FeedbackElementTest`, `DatePickerElementTest` … |
| FR-4 | UC2 | `onActivated`, `onExpandedChanged`, `onClosed` | DS-1, DS-2, DS-5 | `NavBuilder.onIndexEvent`, `ClosedReason` | `NavigationElementTest`, `SideSheetElementTest`, `BottomSheetElementTest` |
| FR-5 | UC1 | `style("--md-…")`, `expandedItemSpace` | 04 CSS변수 | `sayaya-*.js` 의 `var(--md-…)` | `ShapeElementTest`, `NavigationElementTest` |
| FR-6 | UC1 | `locale`, `label` | 06 C 형태 | `HasLocale`, `HasLabels`, `UiLabel`, `Locales` | `I18nElementTest` |
| FR-7 | UC6 | (번들 로드) | 07 컴포넌트 | `rollup.esm-bundle.js`, `sayaya-navigation-standalone.js` | `FeedbackEsmBundleTest`, `NavigationEsmBundleTest` |
| FR-8 | UC8 | `pushTag` | SSD-UC8, 07 배포 | `.github/workflows/publish.yaml` | (CI run 결과) |
| FR-9 | UC7 | — | DS-6 | `TestHelper`, `*ComponentsTest`, `*ElementTest.kt` | 모든 스펙 |
| NFR-1 | — | — | 06 패턴 | 공개 시그니처 유지 | 공개 API diff 확인 |
| NFR-3 | UC6 | — | 07 전술 | `customElements.get` 가드 | `NavigationEsmBundleTest`(두 번 실어도 오류 없음) |
| NFR-4 | UC3·UC5 | — | 07 전술 | `connectedCallback`/`disconnectedCallback` | `NavigationElementTest`(앱 바 재부착), `FeedbackElementTest`(toast·button group 떼기) |
| NFR-5 | UC3·UC4 | — | DS-3 | `prefers-reduced-motion`, aria 속성 | `NavigationElementTest`, `NavigationEsmBundleTest`(키보드) |
| NFR-6 | UC7 | — | DS-6 | 미등록 검사 포함 실패 검사, `:test` 입력 | 모든 스펙 |
| NFR-7 | UC7 | — | 07 | `installLabsBundleDeps` 마커 | (빌드 로그 SKIPPED/실행) |

덮이지 않은 항목: UC8 의 Central Portal 수동 Publish 는 자동 테스트가 없습니다.

## 용어집

| 용어 | 뜻 |
|---|---|
| 빌더 | 정적 팩토리로 시작해 체이닝으로 요소를 구성하고 `element()` 로 DOM 요소를 돌려주는 Java 객체 |
| 자기 타입(`SELF`) | 체이닝 메서드가 하위 빌더 타입을 돌려주게 하는 제네릭 매개변수. `that()` 이 반환 |
| labs | `@material/web/labs` 의 실험 컴포넌트. `gb`(Expressive) 는 `md-gb-*` 태그 |
| 직접 구현 | Material Web 에 없어 이 저장소가 만든 컴포넌트(Lit 엘리먼트 또는 elemento 빌더) |
| dom 바인딩 | `dom/` 의 `@JsType(isNative = true)` 클래스. JS 요소의 속성·메서드를 Java 에서 부름 |
| 토큰 | `md-comp-*`·`md-sys-*` 디자인 토큰. 수치의 우선 출처 |
| `[토큰 외]` / `[파생]` | 토큰에 없어 다른 출처로 정한 값 / 다른 값에서 역산한 값 |
| 단독 ESM 번들 | 일부 엘리먼트만 정의하고 이미 등록된 이름은 건너뛰는 ES module |
| 검사 로그 | `<접두어>: <이름> - PASS` 또는 `- Assertion failed!` 콘솔 한 줄 |
| 미등록 검사 | Kotest 이름 목록에 없는 Java 검사. 전체 실패 로그 검사가 잡는다 |
| 변조 검증 | 고친 지점을 일부러 망가뜨려 그 검사만 실패하는지 보는 확인 |
| 리비전 | 상위 Material Web 버전 뒤에 붙는 네 번째 숫자(`2.5.0.N`) |
