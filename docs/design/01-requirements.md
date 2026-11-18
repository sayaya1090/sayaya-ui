# 1. 비전과 요구사항

## 비전

GWT/J2CL 로 웹 앱을 만드는 Java 개발자가 Material Design 3 컴포넌트를 **타입 안전한 빌더 API** 로 쓸 수 있게 한다.
Material Web(`@material/web`)에 있는 컴포넌트는 감싸고(wrap), 없는 M3 컴포넌트는 직접 구현해 같은 빌더 형태로 제공한다.

## 이해관계자와 액터

| 액터 | 설명 |
|---|---|
| 앱 개발자 | 라이브러리를 의존성으로 받아 빌더로 화면을 만든다(주 액터) |
| 최종 사용자 | 앱 화면에서 컴포넌트를 누르고 펼치고 닫는다 |
| 라이브러리 유지보수자 | 컴포넌트를 추가·수정하고 테스트·릴리스한다 |
| 브라우저 | 커스텀 엘리먼트를 등록하고 그린다(보조 액터) |
| CI(GitHub Actions) | 테스트·배포를 실행한다(보조 액터) |
| 저장소(GitHub Packages, Maven Central, GitHub Release) | 산출물을 받는다(보조 액터) |

## 기능 요구사항

| ID | 요구사항 |
|---|---|
| FR-1 | Material Web 컴포넌트(버튼, 체크박스, 칩, 라디오, 다이얼로그, 리스트, 메뉴, 슬라이더, 스위치, 탭, 텍스트 필드 등)를 정적 팩토리 + 체이닝 빌더로 만든다 |
| FR-2 | Material Web labs 컴포넌트(카드, 배지, Expressive `md-gb-*`, navigation drawer)를 같은 방식으로 만든다 |
| FR-3 | Material Web 에 없는 M3 컴포넌트를 직접 구현해 제공한다: Lit 커스텀 엘리먼트(top app bar, navigation rail/bar, tooltip, snackbar, toast, button group, side/bottom sheet)와 elemento 순수 빌더(date/time picker, search, carousel, toolbar, FAB menu, loading indicator, wavy progress, centered slider) |
| FR-4 | 컴포넌트 이벤트(활성화, 펼침/접힘, 닫힘 사유 등)를 Java 콜백으로 받는다 |
| FR-5 | 치수·모양을 CSS 사용자 정의 속성(`--md-*`)으로 바꿀 수 있다(기본값 = M3 토큰) |
| FR-6 | UI 문구를 영어 기본값에서 덮어쓰고, 날짜·시간 서식을 locale 에 맞춘다 |
| FR-7 | Material Web 을 따로 싣는 앱을 위해 단독 ESM 번들(feedback, navigation)과 단일 IIFE 번들을 Release 에 첨부한다 |
| FR-8 | 버전 태그 push 로 jar 를 GitHub Packages·Maven Central 에, JS 번들을 GitHub Release 에 배포한다 |
| FR-9 | 컴포넌트마다 실제 브라우저에서 동작·치수를 검사하는 테스트와 데모 페이지를 둔다 |

## 비기능 요구사항(보충 명세)

| ID | 품질 속성 | 요구사항 |
|---|---|---|
| NFR-1 | 호환성 | 공개 API(타입·메서드 시그니처)는 리비전 사이에 깨지 않는다. 바꿀 때는 별칭 + deprecate 후 메이저 버전에서 |
| NFR-2 | 스펙 일치 | 치수·색은 `@material/web` 의 `md-comp-*` 토큰을 우선 출처로 삼고, 토큰에 없는 값은 출처를 표시한다(`[토큰 외]`, `[파생]`) |
| NFR-3 | 공존성 | 같은 커스텀 엘리먼트 이름을 두 번 정의하지 않는다(이미 등록된 이름이면 건너뜀) |
| NFR-4 | 수명주기 | DOM 에서 떼었다 붙여도 동작이 이어지고, 떼면 리스너·타이머가 남지 않는다 |
| NFR-5 | 접근성 | 역할·aria 속성·키보드 이동을 제공하고 `prefers-reduced-motion` 을 존중한다 |
| NFR-6 | 검증 가능성 | 모든 검사는 브라우저 테스트로 자동 실행되고, 등록하지 않은 검사의 실패도 잡힌다. JS 만 바꿔도 테스트가 다시 돈다 |
| NFR-7 | 빌드 재현성 | `package.json` 이 바뀌면 npm 의존성을 다시 설치한다 |
| NFR-8 | 이식성 | GWT strict 컴파일을 통과한다. 직접 구현 elemento 빌더는 `@JsType` dom 바인딩을 쓰지 않는다 |
