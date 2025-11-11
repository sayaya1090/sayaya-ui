# sayaya-ui

[Material Web Components](https://github.com/material-components/material-web)의 GWT/J2CL 래퍼

## 개요

sayaya-ui는 Google의 Material Design 3 웹 컴포넌트에 대한 타입 안전 Java/GWT 바인딩을 제공하여, GWT 애플리케이션에서 최신 Material Design UI 요소를 원활하게 통합할 수 있도록 합니다.

## 특징

- 🎨 **Material Design 3** - 최신 Material Design 사양
- 🔒 **타입 안전** - 컴파일 타임에 완전한 Java 타입 체크
- 🚀 **GWT/J2CL 호환** - GWT와 J2CL 모두 지원
- 🔧 **빌더 패턴** - 유창하고 체이닝 가능한 컴포넌트 생성 API
- ✅ **포괄적인 테스트** - 모든 컴포넌트에 대한 완전한 테스트 커버리지

## 설치

### Gradle

```kotlin
repositories {
    maven {
        name = "GitHubPackages"
        url = uri("https://maven.pkg.github.com/sayaya1090/maven")
        credentials {
            username = project.findProperty("github_username") as String? ?: System.getenv("GITHUB_USERNAME")
            password = project.findProperty("github_password") as String? ?: System.getenv("GITHUB_TOKEN")
        }
    }
}

dependencies {
    implementation("dev.sayaya:sayaya-ui:2.4.1")
}
```

### GWT 모듈

```xml
<inherits name="dev.sayaya.Ui"/>
```

## 지원 컴포넌트

| 컴포넌트 | 빌더 | 설명 | 데모 |
|---------|------|-----|-----|
| **Button** | `button()` | Elevated, Filled, Filled Tonal, Outlined, Text | [🔗](https://sayaya1090.github.io/sayaya-ui/button.html) |
| | `button().icon()` | Standard, Filled, Filled Tonal, Outlined, Toggle | |
| **Checkbox** | `checkbox()` | indeterminate 상태를 지원하는 선택 컨트롤 | [🔗](https://sayaya1090.github.io/sayaya-ui/checkbox.html) |
| **Chip** | `chips()` | Assist, Filter, Input, Suggestion 칩 | [🔗](https://sayaya1090.github.io/sayaya-ui/chip.html) |
| **Dialog** | `dialog()` | Modal 다이얼로그, Alert 다이얼로그 | [🔗](https://sayaya1090.github.io/sayaya-ui/dialog.html) |
| **Divider** | `divider()` | 가로 및 세로 구분선 | [🔗](https://sayaya1090.github.io/sayaya-ui/divider.html) |
| **Icon** | `icon()` | Material Symbols 통합 | [🔗](https://sayaya1090.github.io/sayaya-ui/icon.html) |
| **Focus Ring** | `focusRing()` | 접근성을 위한 포커스 표시 | [🔗](https://sayaya1090.github.io/sayaya-ui/focus_ring.html) |
| **Ripple** | `ripple()` | 인터랙티브 리플 효과 | [🔗](https://sayaya1090.github.io/sayaya-ui/ripple.html) |
| **Select** | `select()` | Filled, Outlined 드롭다운 선택 | [🔗](https://sayaya1090.github.io/sayaya-ui/select.html) |
| **Text Field** | `textField()` | Filled, Outlined 텍스트 입력 | [🔗](https://sayaya1090.github.io/sayaya-ui/text_field.html) |

## 사용 예제

### Button

```java
import static dev.sayaya.ui.elements.ButtonElementBuilder.button;

// 아이콘이 있는 Filled 버튼
var saveButton = button().filled()
    .text("저장")
    .icon("save")
    .onClick(e -> save())
    .element();

// 링크로 동작하는 Outlined 버튼
var linkButton = button().outlined()
    .text("자세히 보기")
    .href("https://example.com")
    .target("_blank")
    .element();

// 토글 기능이 있는 아이콘 버튼
var favoriteButton = button().icon()
    .toggle(true)
    .add("favorite_border")
    .toggle("favorite")
    .ariaLabel("즐겨찾기에 추가")
    .ariaLabelSelected("즐겨찾기에서 제거")
    .element();
```

### Checkbox

```java
import static dev.sayaya.ui.elements.CheckboxElementBuilder.checkbox;

var agreeCheckbox = checkbox()
    .select(false)
    .ariaLabel("약관에 동의")
    .onChange(e -> handleAgreement())
    .element();

// indeterminate 상태 사용
var selectAllCheckbox = checkbox()
    .indeterminate(true)
    .ariaLabel("전체 선택")
    .element();
```

### Chips

```java
import static dev.sayaya.ui.elements.ChipsElementBuilder.chips;

var chipSet = chips()
    .filter()
        .label("JavaScript")
        .select(true)
        .done()
    .filter()
        .label("Java")
        .done()
    .filter()
        .label("Python")
        .done()
    .element();

// 제거 동작이 있는 Input 칩
var tagChips = chips()
    .input()
        .label("프론트엔드")
        .removable(true)
        .onRemove(e -> removeTag("프론트엔드"))
        .done()
    .element();
```

### Dialog

```java
import static dev.sayaya.ui.elements.DialogElementBuilder.dialog;
import static dev.sayaya.ui.elements.DialogElementBuilder.alert;

// 기본 다이얼로그
var confirmDialog = dialog()
    .headline("작업 확인")
    .content("정말로 이 작업을 수행하시겠습니까?")
    .actions(div()
        .add(button().text("취소").attr("value", "cancel"))
        .add(button().text("확인").attr("value", "ok")))
    .element();

// 버튼을 클릭하면 다이얼로그 열기
var openButton = button().text("다이얼로그 열기").element();
openButton.addEventListener("click", evt -> {
    confirmDialog.show();
    confirmDialog.close().then(result -> {
        if ("ok".equals(confirmDialog.returnValue)) {
            console.log("작업이 실행되었습니다");
        }
        return null;
    });
});

// Alert 다이얼로그
var alertDialog = alert()
    .headline("경고")
    .content("중요한 알림 메시지입니다")
    .actions(div().add(button().text("확인")))
    .element();

// 폼이 있는 다이얼로그
var formDialog = dialog()
    .headline("정보 입력")
    .content(form()
        .add(textField().filled().label("이름"))
        .add(textField().filled().label("이메일")))
    .actions(div()
        .add(button().text("취소"))
        .add(button().text("제출").attr("value", "submit")))
    .onClosed(evt -> console.log("다이얼로그가 닫혔습니다"))
    .element();

// 빠른 전환 (애니메이션 없음)
var quickDialog = dialog()
    .headline("알림")
    .content("즉시 표시됩니다")
    .quick(true)
    .element();
```

### Divider

```java
import static dev.sayaya.ui.elements.DividerElementBuilder.divider;

// 가로 구분선
var horizontalDivider = divider().element();

// inset이 있는 세로 구분선
var verticalDivider = divider()
    .inset(true)
    .insetStart("16px")
    .insetEnd("16px")
    .element();
```

### Icon

```java
import static dev.sayaya.ui.elements.IconElementBuilder.icon;

var searchIcon = icon("search")
    .filled(true)
    .style("font-size", "24px")
    .element();

// 커스텀 SVG 아이콘
var customIcon = icon()
    .svg(svgPath -> svgPath
        .d("M10 20v-6h4v6h5v-8h3L12 3 2 12h3v8z")
    )
    .element();
```

### Focus Ring

```java
import static dev.sayaya.ui.elements.FocusRingElementBuilder.focusRing;

var button = button().text()
    .text("클릭하세요")
    .element();

var focus = focusRing()
    .control(button)
    .element();
```

### Ripple

```java
import static dev.sayaya.ui.elements.RippleElementBuilder.ripple;

// 버튼에 연결
var button = button().filled()
    .text("클릭하세요")
    .element();

var rippleEffect = ripple()
    .control(button)
    .element();

// ID로 참조
var rippleById = ripple()
    .htmlFor("my-button-id")
    .element();

// 비활성화된 리플
var noRipple = ripple()
    .disabled(true)
    .element();
```

## 빌더 인터페이스

sayaya-ui는 공통 패턴을 위한 재사용 가능한 인터페이스를 제공합니다:

- **`Disableable`** - 비활성화 가능한 컴포넌트
- **`Selectable`** - 선택 상태가 있는 컴포넌트
- **`Toggleable`** - 토글 기능이 있는 컴포넌트
- **`Linkable`** - 링크로 동작할 수 있는 컴포넌트
- **`FormAssociable`** - 폼과 통합되는 컴포넌트
- **`Typeable`** - 타입 변형이 있는 컴포넌트
- **`Elevatable`** - 높이가 있는 컴포넌트
- **`HasAriaLabel`** - ARIA 레이블을 지원하는 컴포넌트
- **`HasIconSlot`** - 아이콘 슬롯이 있는 컴포넌트
- **`HasHeadlineSlot`** - 제목 슬롯이 있는 컴포넌트
- **`HasContentSlot`** - 본문 슬롯이 있는 컴포넌트
- **`HasActionsSlot`** - 액션 버튼 슬롯이 있는 컴포넌트
- **`HasDialogEvents`** - Dialog 이벤트를 지원하는 컴포넌트
- **`HasMenuEvents`** - 메뉴 이벤트를 지원하는 컴포넌트

## 개발

### 필수 조건

- JDK 11+
- Gradle 7.0+

### 빌드

```bash
./gradlew build
```

### 테스트 실행

```bash
./gradlew test
```

### GWT 개발 모드

```bash
./gradlew gwtDevMode
```

로컬에서 개발 모드로 실행하거나, [라이브 데모](https://sayaya1090.github.io/sayaya-ui/)에서 모든 컴포넌트를 확인할 수 있습니다.

## 아키텍처

```
sayaya-ui/
├── src/main/java/dev/sayaya/ui/
│   ├── dom/              # Material Web 요소에 대한 JsInterop 바인딩
│   │   ├── MdButtonElement.java
│   │   ├── MdCheckboxElement.java
│   │   ├── MdChipElement.java
│   │   ├── MdDialogElement.java
│   │   └── ...
│   └── elements/         # 유창한 빌더 API
│       ├── ButtonElementBuilder.java
│       ├── CheckboxElementBuilder.java
│       ├── ChipsElementBuilder.java
│       ├── DialogElementBuilder.java
│       └── interfaces/   # 재사용 가능한 빌더 특성
│           ├── Disableable.java
│           ├── Selectable.java
│           ├── HasDialogEvents.java
│           └── ...
└── src/test/
    ├── java/             # GWT 테스트 진입점
    ├── kotlin/           # Kotest 명세
    └── webapp/           # 테스트 HTML 페이지
```

## 의존성

- **[Elemento](https://github.com/hal/elemento)** (2.3.2) - GWT용 타입 안전 HTML 빌더
- **[Material Web](https://github.com/material-components/material-web)** - Google의 Material Design 웹 컴포넌트
- **GWT** (2.12.2) - Google Web Toolkit

## 브라우저 지원

sayaya-ui는 Material Web Components와 동일한 브라우저를 지원합니다:
- Chrome (최신)
- Firefox (최신)
- Safari (최신)
- Edge (최신)

## 참고 자료

- [Material Web 문서](https://material-web.dev/)
- [Material Design 3](https://m3.material.io/)
- [GWT 문서](http://www.gwtproject.org/)

## 라이선스

이 프로젝트는 GWT와 동일한 조건으로 라이선스됩니다.

## 버전

현재 버전: **2.4.1**