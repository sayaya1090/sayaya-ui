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
    implementation("dev.sayaya:ui:2.5.0.8")
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
| | `button().fab()` | Plain FAB (3가지 크기), Branded FAB, Extended FAB | |
| **Checkbox** | `checkbox()` | indeterminate 상태를 지원하는 선택 컨트롤 | [🔗](https://sayaya1090.github.io/sayaya-ui/checkbox.html) |
| **Chip** | `chips()` | Assist, Filter, Input, Suggestion 칩 | [🔗](https://sayaya1090.github.io/sayaya-ui/chip.html) |
| **Radio** | `radio()` | 그룹 내에서 단일 선택을 위한 라디오 버튼 | [🔗](https://sayaya1090.github.io/sayaya-ui/radio.html) |
| **Dialog** | `dialog()` | Modal 다이얼로그, Alert 다이얼로그 | [🔗](https://sayaya1090.github.io/sayaya-ui/dialog.html) |
| **Divider** | `divider()` | 가로 및 세로 구분선 | [🔗](https://sayaya1090.github.io/sayaya-ui/divider.html) |
| **Icon** | `icon()` | Material Symbols 통합 | [🔗](https://sayaya1090.github.io/sayaya-ui/icon.html) |
| **Focus Ring** | `focusRing()` | 접근성을 위한 포커스 표시 | [🔗](https://sayaya1090.github.io/sayaya-ui/focus_ring.html) |
| **Progress** | `progress()` | Linear, Circular 진행률 표시 | [🔗](https://sayaya1090.github.io/sayaya-ui/progress.html) |
| **Ripple** | `ripple()` | 인터랙티브 리플 효과 | [🔗](https://sayaya1090.github.io/sayaya-ui/ripple.html) |
| **Select** | `select()` | Filled, Outlined 드롭다운 선택 | [🔗](https://sayaya1090.github.io/sayaya-ui/select.html) |
| **Slider** | `slider()` | Continuous, Discrete, Range 슬라이더. M3 Expressive 보완: `size(Size.XS…XL)`, `vertical(true)`(range 제외), `insetIcon("…")`(standard, M 이상) | [🔗](https://sayaya1090.github.io/sayaya-ui/slider.html) [Expressive](https://sayaya1090.github.io/sayaya-ui/slider_expressive.html) |
| **Switch** | `sw()` | 아이콘 지원 토글 스위치 | [🔗](https://sayaya1090.github.io/sayaya-ui/sw.html) |
| **Tabs** | `tabs()` | Primary, Secondary 탭 네비게이션 | [🔗](https://sayaya1090.github.io/sayaya-ui/tabs.html) |
| **Text Field** | `textField()` | Filled, Outlined 텍스트 입력 | [🔗](https://sayaya1090.github.io/sayaya-ui/text_field.html) |
| **List** | `list()` | 단일/다중 선택, 아이콘, 아바타 지원 | [🔗](https://sayaya1090.github.io/sayaya-ui/list.html) |
| **Menu** | `menu()` | 드롭다운 메뉴, 서브메뉴 지원 | [🔗](https://sayaya1090.github.io/sayaya-ui/menu.html) |
| **Card** | `card()` | Elevated, Filled, Outlined 카드 컨테이너 | [🔗](https://sayaya1090.github.io/sayaya-ui/card.html) |
| **Badge** | `badge()` | 알림이나 상태를 나타내는 배지 | [🔗](https://sayaya1090.github.io/sayaya-ui/badge.html) |
| **Expressive (labs/gb)** | `gb()` | Material Web 2.5.0 Expressive 컴포넌트 16종 (`md-gb-*`): button, split button, icon button, fab, icon, card, badge, checkbox, radio, switch, divider, list/list-item, menu/menu-group/menu-item | [🔗](https://sayaya1090.github.io/sayaya-ui/gb.html) |
| **Navigation** | `topAppBar()`, `navigationBar()`, `navigationRail()`, `navigationDrawer()` | Top app bar(small·medium flexible·large flexible, 가운데 정렬·부제), Navigation bar, Navigation rail(접힘 96dp·narrow 80dp / 펼침 220~360dp, modal, 메뉴 버튼, 링크 목적지·↑/↓ 이동), Navigation drawer(고정/모달) | [🔗](https://sayaya1090.github.io/sayaya-ui/navigation.html) |
| **Tooltip / Snackbar / Button group** | `tooltip()`, `snackbar()`, `buttonGroup()` | Plain·Rich tooltip, Snackbar(액션/닫기/자동 닫힘), Standard·Connected button group(xsmall~xlarge, 토글 선택) | [🔗](https://sayaya1090.github.io/sayaya-ui/feedback.html) |
| **Toast** [M3 외 확장] | `toast()` | 데스크톱 웹용 스택 알림(오른쪽 위에 쌓임, info·success·warning·error, 액션/닫기/자동 닫힘·hover 정지). M3 에 없는 컴포넌트로, 모바일에서는 Snackbar 사용 권장 | [🔗](https://sayaya1090.github.io/sayaya-ui/feedback.html) |
| **Side sheet** | `sideSheet()` | Standard·Modal side sheet(end/start, detached, 뒤로·닫기 버튼, 하단 액션, 스크림·Esc 닫기) | [🔗](https://sayaya1090.github.io/sayaya-ui/side_sheet.html) |
| **Bottom sheet** | `bottomSheet()` | Standard·Modal bottom sheet(드래그 핸들로 펼침/접힘, 아래로 끌어 닫기, 스크림·Esc 닫기, 최대 너비 640dp) | [🔗](https://sayaya1090.github.io/sayaya-ui/bottom_sheet.html) |
| **Loading indicator** | `loadingIndicator()` | 7개 M3 도형 shape morph 로딩 지시자(기본/contained, 24~240dp), progressbar 역할 | [🔗](https://sayaya1090.github.io/sayaya-ui/loading_indicator.html) |
| **Toolbar** | `toolbar()` | Docked(bottom app bar 대체, 기본 가운데 정렬·균등 간격 4~32dp, `alignment(Center/Start/End/Edges)`)·Floating toolbar(standard/vibrant, 가로/세로, 화면 고정 위치), 방향키 이동. FAB 와 함께 쓸 때는 M3 웹 지침대로 옆에 따로 배치 | [🔗](https://sayaya1090.github.io/sayaya-ui/toolbar.html) |
| **FAB menu** | `fabMenu()` | FAB 에서 열리는 메뉴(닫기 버튼 전환, primary/secondary/tertiary, Esc·항목 선택으로 닫힘, 항목은 닫기 버튼 뒤로 스크롤 `--md-fab-menu-max-height`) | [🔗](https://sayaya1090.github.io/sayaya-ui/fab_menu.html) |
| **Search** | `search()` | Search bar + docked/full-screen view(제안 필터, combobox·listbox, ↑/↓·Enter·Esc, contained/divided, 포커스 시 바 여백 24→12dp, 이름·보조 문구·행별 동작 항목, 목록 교체, IME 조합 키 무시, SVG 아이콘, inline) | [🔗](https://sayaya1090.github.io/sayaya-ui/search.html) |
| **Date picker** | `datePicker()` | Docked(`field(label)` 텍스트 필드 + 드롭다운, 월·연도 메뉴 버튼, 이전·다음 달 날짜)·Modal(입력/달력 전환 아이콘)·Modal date input(`modalInput()`, locale 형식 힌트·오류) — 오늘·선택 표시, ARIA grid 키보드, 연도 선택, 기간 선택 `range(true)` | [🔗](https://sayaya1090.github.io/sayaya-ui/date_picker.html) |
| **Time picker** | `timePicker()` | Dial·Input(키보드/시계 아이콘으로 전환 `inputMode`, 12/24시간 — 24시간은 0–11 바깥·12–23 안쪽 다이얼, 오전/오후, modal 확인/취소) | [🔗](https://sayaya1090.github.io/sayaya-ui/time_picker.html) |
| **Carousel** | `carousel()` | Multi-browse·Hero·Center-aligned hero(`heroCentered`)·Full-screen(`fullScreen`, 세로 snap)·Uncontained(일반 스크롤)·Multi-aspect ratio(`item(…, width)`) — 가장자리 항목 축소·parallax, slide 역할, 모두 보기 버튼 | [🔗](https://sayaya1090.github.io/sayaya-ui/carousel.html) |
| **Wavy progress** | `wavyProgress()` | M3 Expressive 물결 진행 표시기 — linear·`circular()`, determinate/indeterminate | [🔗](https://sayaya1090.github.io/sayaya-ui/wavy_progress.html) |
| **Centered slider** | `centeredSlider()` | M3 Expressive 가운데 슬라이더(0 기준 양·음 범위, 네이티브 range 키보드) | [🔗](https://sayaya1090.github.io/sayaya-ui/centered_slider.html) |
| **i18n** | `Locales`, 각 빌더의 `locale()`·`label()` | 영어 기본 문구 덮어쓰기, Intl 기반 날짜·시간 표기, 주 시작 요일, 12/24시간, RTL | [🔗](https://sayaya1090.github.io/sayaya-ui/i18n.html) |
| **Shape** | CSS 변수 | 직접 구현 컴포넌트의 모서리를 컴포넌트 변수·시스템 shape 토큰으로 변경 | [🔗](https://sayaya1090.github.io/sayaya-ui/shape.html) |

## 모양(모서리) 사용자 지정

sayaya-ui 가 직접 구현한 컴포넌트의 모든 모서리는 `var(--md-<컴포넌트>-<부위>-shape, var(--md-sys-shape-corner-<토큰>, 기본값))` 순서로 정해집니다. 아무것도 지정하지 않으면 M3 기본 모양 그대로이고, 시스템 shape 토큰을 지정하면 그 토큰을 쓰는 부위가 한꺼번에 바뀝니다.

```css
:root { --md-sys-shape-corner-extra-large: 20px; }          /* date/time picker 컨테이너, carousel 항목 등 */
.compact { --md-date-picker-date-shape: 8px; }  /* 한 부위만 */
```

| 컴포넌트 | 변수 (시스템 토큰) |
|---|---|
| Date picker | `--md-date-picker-container-shape` (extra-large), `-date-shape`, `-year-shape`, `-month-button-shape`, `-nav-button-shape`, `-action-shape` (full) |
| Time picker | `--md-time-picker-container-shape` (extra-large), `-time-selector-shape`, `-period-selector-shape` (small), `-clock-dial-shape`, `-clock-dial-label-shape`, `-clock-dial-selector-handle-shape`, `-clock-dial-selector-center-shape`, `-action-shape` (full) |
| Search | `--md-search-bar-container-shape` (full), `--md-search-view-docked-shape` (extra-large), `--md-search-view-full-screen-shape` (none), `--md-search-icon-button-shape` (full) |
| FAB menu | `--md-fab-menu-fab-shape` (large), `-close-button-shape`, `-item-shape` (full) |
| Toolbar | `--md-toolbar-docked-container-shape` (none), `--md-toolbar-floating-container-shape` (full) |
| Carousel | `--md-carousel-item-shape` (extra-large, 가장자리 잘림에도 적용), `-show-all-shape` (full) |
| Loading indicator | `--md-loading-indicator-container-shape` (full) |
| Wavy progress | `--md-wavy-progress-track-shape`, `-stop-shape` (full) |
| Centered slider | `--md-centered-slider-track-shape`, `-handle-shape`, `-stop-shape` (full) |
| Snackbar / Toast | `--md-snackbar-action-shape`, `-close-shape`, `--md-toast-action-shape`, `-close-shape` (full) |
| Navigation bar / rail 배지 | `--md-navigation-bar-badge-shape`, `-badge-small-shape`, `--md-navigation-rail-badge-shape`, `-badge-small-shape` (full) |
| Side / Bottom sheet | `--md-side-sheet-standard-container-shape` (none), `--md-bottom-sheet-drag-handle-shape` (full). 그 밖의 시트 모서리는 기존처럼 `--md-sys-shape-corner-large/extra-large` 를 따릅니다 |

## 지역화(i18n)

sayaya-ui 가 직접 구현한 컴포넌트(위 표의 Navigation, Tooltip/Snackbar/Button group, Toast, Side·Bottom sheet, 그리고 Loading indicator 이후 항목)의 규칙입니다. Material Web 을 감싼 컴포넌트는 Material Web 의 동작을 따릅니다.

- **UI 문구는 영어가 기본**이고 빌더로 덮어씁니다. locale 을 바꿔도 문구는 번역되지 않습니다.
- **날짜·월·요일·오전/오후 표기, 주 시작 요일, 12/24시간 기본값**은 브라우저 `Intl` 로 locale 을 따릅니다.
- locale 결정 순서: 빌더의 `locale(...)` → `Locales.setDefault(...)` → `<html lang>` → 브라우저 언어 → `en-US`
- `dir="rtl"` 안에서는 툴바·캐러셀·달력의 ←/→ 의미, 캐러셀 시작 위치, 가운데 슬라이더 방향이 뒤집힙니다.

```java
import dev.sayaya.ui.elements.Locales;

Locales.setDefault("ko-KR");   // 날짜·시간 표기 기준(문구는 아래처럼 직접 지정)

datePicker().modal().weekStart(1)
    .label(DatePickerElementBuilder.Label.Cancel, "취소")
    .label(DatePickerElementBuilder.Label.Ok, "확인");
timePicker().dial().hour24(true).label(TimePickerElementBuilder.Label.SelectTime, "시간 선택");
search().docked().label(SearchElementBuilder.Label.Clear, "지우기");

snackbar().closeable(true).closeLabel("닫기");
toast().info().closeable(true).closeLabel("닫기");
navigationRail().menu(true).menuLabels("메뉴 열기", "메뉴 닫기");
sideSheet().modal().closeLabel("닫기").backLabel("뒤로");
bottomSheet().modal().handleLabel("시트 크기 조절");
```

> **동작 변경(2.5.0.4부터):** snackbar·toast 닫기 버튼, navigation rail 메뉴 버튼, side sheet 닫기·뒤로 버튼, bottom sheet 핸들의 접근성 라벨 기본값이 한국어(닫기, 메뉴 열기/닫기, 뒤로, 시트 크기 조절)에서 영어(Close, Open menu/Close menu, Back, Resize sheet)로 바뀌었습니다. 한국어 라벨이 필요하면 위 메서드로 지정하세요.

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

// FAB (Floating Action Button)
var addFab = button().fab()
    .variant(FabElementBuilder.Variant.Primary)
    .size(PlainFabElementBuilder.Size.Large)
    .icon("add")
    .ariaLabel("추가")
    .onClick(e -> create())
    .element();

// Extended FAB
var composeFab = button().fab()
    .icon("edit")
    .label("Compose")
    .ariaLabel("새 글 작성")
    .element();

// Branded FAB
var brandedFab = button().fab().branded()
    .size(BrandedFabElementBuilder.Size.Large)
    .icon("local_fire_department")
    .label("Brand")
    .ariaLabel("브랜드 홍보")
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

### Radio

```java
import static dev.sayaya.ui.elements.RadioElementBuilder.radio;

// 라디오 버튼 그룹
var radioGroup = div()
    .add(label()
        .add("소형: ")
        .add(radio()
            .name("size")
            .value("small")
            .ariaLabel("소형")))
    .add(label()
        .add("중형: ")
        .add(radio()
            .name("size")
            .value("medium")
            .select(true)
            .ariaLabel("중형")))
    .add(label()
        .add("대형: ")
        .add(radio()
            .name("size")
            .value("large")
            .ariaLabel("대형")))
    .element();

// 이벤트 처리
var optionRadio = radio()
    .name("option")
    .value("option1")
    .onChange(e -> handleSelection())
    .onInput(e -> handleInput())
    .required(true)
    .ariaLabel("옵션 1")
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

### Slider

```java
import static dev.sayaya.ui.elements.SliderElementBuilder.slider;

// 연속 슬라이더
var volumeSlider = slider()
    .min(0)
    .max(100)
    .value(50)
    .labeled(true)
    .onInput(e -> updateVolume(volumeSlider.value))
    .element();

// 불연속 슬라이더 (틱 마크)
var stepSlider = slider()
    .min(0)
    .max(10)
    .ticks(1)
    .value(5)
    .labeled(true)
    .element();

// 범위 슬라이더
var priceRangeSlider = slider()
    .min(0)
    .max(1000)
    .range()
    .valueStart(200)
    .valueEnd(800)
    .labeled(true)
    .ariaLabelStart("최소 가격")
    .ariaLabelEnd("최대 가격")
    .element();

// 틱이 있는 범위 슬라이더
var temperatureRange = slider()
    .min(10)
    .max(30)
    .range()
    .valueStart(18)
    .valueEnd(24)
    .ticks(2)
    .labeled(true)
    .element();

// M3 Expressive: 크기(XS 16 / S 24 / M 40 / L 56 / XL 96dp 트랙), 세로, inset icon
var large = slider().size(SliderElementBuilder.Size.L).value(60).element();
var vertical = slider().vertical(true).value(20).element();   // 아래가 최솟값, 길이는 --md-slider-vertical-length(기본 200px)
var volume = slider().size(SliderElementBuilder.Size.M)       // inset icon 은 트랙 40dp 이상(M·L·XL)에서만 보입니다
    .insetIcon("volume_up")                                   // 값이 작아지면 핸들 뒤(비활성 트랙)로 옮겨 갑니다
    .value(70)
    .element();
```

### Switch

```java
import static dev.sayaya.ui.elements.SwitchElementBuilder.sw;

// 기본 스위치
var notificationSwitch = sw()
    .ariaLabel("알림 활성화")
    .onChange(e -> toggleNotifications(notificationSwitch.selected))
    .element();

// 아이콘이 있는 스위치
var wifiSwitch = sw()
    .icons(true)
    .select(true)
    .ariaLabel("Wi-Fi")
    .element();

// 선택된 아이콘만 표시
var darkModeSwitch = sw()
    .showOnlySelectedIcon(true)
    .ariaLabel("다크 모드")
    .onInput(e -> updateTheme(darkModeSwitch.selected))
    .element();

// 필수 스위치 (폼 제출용)
var termsSwitch = sw()
    .name("terms")
    .value("accepted")
    .required(true)
    .ariaLabel("약관 동의")
    .element();
```

### Progress

```java
import static dev.sayaya.ui.elements.ProgressElementBuilder.progress;

// 선형 진행률
var downloadProgress = progress()
    .linear()
    .value(0.65)
    .max(1.0)
    .ariaLabel("다운로드 진행률: 65%")
    .element();

// 버퍼를 포함한 선형 진행률
var videoProgress = progress()
    .linear()
    .value(0.3)
    .buffer(0.7)
    .ariaLabel("재생 중")
    .element();

// 원형 진행률
var uploadProgress = progress()
    .circular()
    .value(45)
    .max(100)
    .ariaLabel("업로드 45%")
    .element();

// 무한 로딩 (선형)
var loadingLinear = progress()
    .linear()
    .indeterminate(true)
    .ariaLabel("로딩 중")
    .element();

// 무한 로딩 (원형, 4색)
var loadingCircular = progress()
    .circular()
    .indeterminate(true)
    .fourColor(true)
    .ariaLabel("처리 중")
    .element();
```

### Tabs

```java
import static dev.sayaya.ui.elements.TabsElementBuilder.tabs;
import static org.jboss.elemento.Elements.div;

// Primary 탭
var primaryTabs = tabs().primary()
    .tab().text("Video").icon("videocam").end()
    .tab().text("Photos").icon("photo").end()
    .tab().text("Audio").icon("audiotrack").end()
    .element();

// Secondary 탭
var secondaryTabs = tabs().secondary()
    .tab().text("Flights").end()
    .tab().text("Trips").end()
    .tab().text("Explore").end()
    .element();

// 인라인 아이콘이 있는 탭
var inlineTabs = tabs().primary()
    .tab().text("Flights").icon("flight").inlineIcon().end()
    .tab().text("Trips").icon("luggage").inlineIcon().end()
    .tab().text("Explore").icon("explore").inlineIcon().end()
    .element();

// 패널과 연결된 탭
HTMLDivElement panel1 = div().textContent("Video 콘텐츠").element();
HTMLDivElement panel2 = div().textContent("Photos 콘텐츠").element();
HTMLDivElement panel3 = div().textContent("Audio 콘텐츠").element();

var tabsWithPanels = tabs().primary()
    .tab().text("Video").icon("videocam").panel(panel1).end()
    .tab().text("Photos").icon("photo").panel(panel2).end()
    .tab().text("Audio").icon("audiotrack").panel(panel3).end()
    .activeTabIndex(0)
    .autoActivate(true)
    .element();

// 활성 탭 설정
var activeTabs = tabs().primary()
    .tab().text("Tab 1").end()
    .tab().text("Tab 2").active().end()
    .tab().text("Tab 3").end()
    .element();

// 수동 활성화 (Enter/Space 키 필요)
var manualTabs = tabs().primary()
    .tab().text("Tab 1").end()
    .tab().text("Tab 2").end()
    .tab().text("Tab 3").end()
    .autoActivate(false)
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

### List

```java
import static dev.sayaya.ui.elements.ListElementBuilder.list;

// 기본 리스트
var basicList = list()
    .item()
        .headline("사과")
        .supportingText("신선하고 달콤한 과일")
    .done()
    .item()
        .headline("바나나")
        .supportingText("에너지가 풍부한 과일")
    .done()
    .item()
        .headline("오렌지")
        .supportingText("비타민 C가 풍부한 과일")
    .done()
    .element();

// 아이콘이 있는 리스트
var iconList = list()
    .item()
        .start(icon("folder"))
        .headline("문서")
    .done()
    .item()
        .start(icon("image"))
        .headline("사진")
    .done()
    .item()
        .start(icon("music_note"))
        .headline("음악")
    .done()
    .element();

// 링크로 동작하는 리스트 항목
var linkList = list()
    .item()
        .headline("Google")
        .type("link")
        .href("https://google.com")
        .targetBlank()
    .done()
    .item()
        .headline("GitHub")
        .type("link")
        .href("https://github.com")
        .targetBlank()
    .done()
    .element();

// 구분선이 있는 리스트
var dividedList = list()
    .item().headline("항목 1").done()
    .divider()
    .item().headline("항목 2").done()
    .divider()
    .item().headline("항목 3").done()
    .element();
```

### Menu

```java
import static dev.sayaya.ui.elements.MenuElementBuilder.menu;
import static dev.sayaya.ui.elements.ButtonElementBuilder.button;

// 기본 메뉴
var anchor = button().filled()
    .text("메뉴 열기")
    .id("menu-anchor")
    .element();

var simpleMenu = menu()
    .anchor("menu-anchor")
    .positioning(MenuElementBuilder.Position.Fixed)
    .item()
        .headline("잘라내기")
        .start(icon("content_cut"))
    .done()
    .item()
        .headline("복사")
        .start(icon("content_copy"))
    .done()
    .item()
        .headline("붙여넣기")
        .start(icon("content_paste"))
    .done()
    .element();

anchor.addEventListener("click", e -> simpleMenu.open = !simpleMenu.open);

// 서브메뉴가 있는 메뉴
var submenuAnchor = button().filled()
    .text("파일")
    .id("file-menu-anchor")
    .element();

var fileMenu = menu()
    .anchor("file-menu-anchor")
    .positioning(MenuElementBuilder.Position.Fixed)
    .overflow() // 서브메뉴 사용 시 필수!
    .item()
        .headline("새 파일")
    .done()
    .sub()
        .item()
            .headline("열기")
            .end(icon("arrow_right"))
        .done()
        .menu()
            .item().headline("최근 파일 1").done()
            .item().headline("최근 파일 2").done()
        .done()
    .done()
    .item()
        .headline("저장")
    .done()
    .element();

submenuAnchor.addEventListener("click", e -> fileMenu.open = !fileMenu.open);

// keepOpen으로 메뉴 열린 상태 유지
var filterMenu = menu()
    .anchor("filter-anchor")
    .positioning(MenuElementBuilder.Position.Fixed)
    .item()
        .headline("전체")
        .keepOpen()
    .done()
    .item()
        .headline("진행 중")
        .keepOpen()
    .done()
    .item()
        .headline("완료")
        .keepOpen()
    .done()
    .element();
```

### Card

```java
import static dev.sayaya.ui.elements.CardElementBuilder.card;

// Elevated 카드
var elevatedCard = card().elevated()
    .style("width", "300px")
    .add(div()
        .style("padding", "16px")
        .add(h(3).text("Elevated Card"))
        .add(p().text("그림자 효과가 있는 카드입니다."))
    )
    .element();

// Filled 카드
var filledCard = card().filled()
    .style("width", "300px")
    .add(div()
        .style("padding", "16px")
        .add(h(3).text("Filled Card"))
        .add(p().text("배경색이 채워진 카드입니다."))
    )
    .element();

// Outlined 카드
var outlinedCard = card().outlined()
    .style("width", "300px")
    .add(div()
        .style("padding", "16px")
        .add(h(3).text("Outlined Card"))
        .add(p().text("외곽선이 있는 카드입니다."))
    )
    .element();

// 클릭 가능한 카드
var clickableCard = card().elevated()
    .style("width", "300px")
    .style("cursor", "pointer")
    .onClick(e -> handleCardClick())
    .ariaLabel("제품 정보 카드")
    .add(div()
        .style("padding", "16px")
        .add(h(3).text("Clickable Card"))
        .add(p().text("이 카드를 클릭해보세요."))
    )
    .element();
```

### Badge

```java
import static dev.sayaya.ui.elements.BadgeElementBuilder.badge;
import static dev.sayaya.ui.elements.IconElementBuilder.icon;

// 숫자 배지
var iconElem = icon("notifications").element();
var container = div()
    .style("position", "relative")
    .style("display", "inline-block")
    .add(iconElem)
    .add(badge()
        .value(3)
        .anchorElement(iconElem)
        .style("position", "absolute")
        .style("top", "calc(0px - var(--_large-size))")
        .style("right", "calc(var(--_large-size) - 5px)")
    )
    .element();

// 점 배지 (빈 배지)
var personIcon = icon("person").element();
var dotBadge = div()
    .style("position", "relative")
    .style("display", "inline-block")
    .add(personIcon)
    .add(badge()
        .anchorElement(personIcon)
        .style("position", "absolute")
        .style("top", "calc(0px - var(--_size))")
        .style("right", "calc(var(--_size) - 2px)")
    )
    .element();

// 텍스트 배지
var textBadge = badge()
    .value("NEW")
    .element();

// 숫자 값이 있는 배지
var numberBadge = badge()
    .value(99)
    .element();
```

### Expressive 컴포넌트 (labs/gb, Material Web 2.5.0)

```java
import static dev.sayaya.ui.elements.GbElementBuilder.*;

// Expressive 버튼
var save = gb().button()
    .color(ButtonColor.Tonal)
    .size(Size.LG)
    .text("저장")
    .onClick(e -> save())
    .element();

// Split Button: leading/trailing 슬롯에는 <button>, 기본 슬롯은 trailing 버튼의 팝오버 메뉴
var send = gb().splitButton()
    .color(SplitButtonColor.Filled)
    .leading(button().text("보내기"))
    .trailing(button().aria("label", "더보기"))
    .menu(gb().menu().item(gb().menuItem().headline("예약 전송")))
    .element();

// 아이콘 버튼 / FAB / 아이콘
var settings = gb().iconButton().color(IconButtonColor.Outlined).width(IconButtonWidth.Wide).icon("settings").ariaLabel("설정").element();
var add = gb().fab().color(FabColor.PrimaryContainer).size(FabSize.LG).icon("add").ariaLabel("추가").element();
var fav = gb().icon("favorite").element();

// 리스트 / 메뉴
var list = gb().list().segmented(true)
    .item(gb().listItem().headline("받은편지함").supportingText("새 메일 3통"))
    .element();
var menu = gb().menu().color(MenuColor.Vibrant)
    .group(gb().menuGroup().checkable(MenuCheckable.Single)
        .item(gb().menuItem().headline("목록 보기").select(true))
        .item(gb().menuItem().headline("격자 보기")))
    .element();

// 폼 컨트롤
var agree = gb().checkbox().name("agree").required(true).element();
var wifi = gb().sw().select(true).onIcon("check").element();
```

> labs 컴포넌트이므로 Material Web 업데이트에 따라 API가 바뀔 수 있습니다. `labs.bundle.js`를 함께 로드해야 합니다.

### Navigation (Top app bar / Navigation bar / Navigation rail / Navigation drawer)

```java
import static dev.sayaya.ui.elements.NavigationElementBuilder.*;

// Top app bar: 스크롤 시 surface-container 로 전환
var appBar = topAppBar().variant(TopAppBarVariant.Small)
    .leading(button().icon("menu").ariaLabel("메뉴"))
    .headline("문서")
    .trailing(button().icon("dark_mode").ariaLabel("테마"))
    .autoScroll(true)
    .element();

// Navigation rail: 접힘(96dp, narrow(true)면 80dp) / 펼침(220~360dp). menu(true): 펼침/접힘 메뉴 버튼, modal(true): 펼치면 본문 위에 표시
var rail = navigationRail()
    .header(button().icon("menu").ariaLabel("메뉴"))
    .item(railItem().icon("home").activeIcon("home").label("홈"))
    .item(railItem().icon("inbox").label("받은편지함").badge("3"))
    .onActivated(index -> route(index))
    .element();
rail.expanded = true;   // 펼침

// Navigation bar (compact 화면)
var bar = navigationBar()
    .tab(navigationTab().icon("home").label("홈"))
    .tab(navigationTab().icon("search").label("검색").badge())
    .onActivated(index -> route(index))
    .element();

// Navigation drawer: 고정형 / 모달형
var drawer = navigationDrawerModal().pivot(DrawerPivot.Start).add(menuList).element();
drawer.opened = true;
```

레이아웃 상태 대응(handbook `DrawerState`, magi `RailModes`):

| 상태 | sayaya-ui |
|---|---|
| COLLAPSE / 접힘 | `rail.expanded = false` |
| EXPAND / 열림 | `rail.expanded = true` |
| HIDE | `rail.hidden = true` |
| OVERLAY (모바일) | `navigationDrawerModal()` 의 `opened = true` |

> `md-navigation-drawer(-modal)` 은 Material Web labs, `md-top-app-bar`·`md-navigation-rail` 은 sayaya-ui 자체 구현(`labs-bundle/src/sayaya-navigation.js`)입니다.
> `md-navigation-bar`/`md-navigation-tab` 은 M3 가이드가 권장하는 **flexible navigation bar** 를 sayaya-ui 가 직접 구현한 것입니다(`labs-bundle/src/sayaya-navbar.js`). labs 의 baseline 바(80dp)는 가이드에서 비권장이라 대체했고, API(`activeIndex`, `label`, `badge`, `navigation-bar-activated`)는 그대로입니다.
> 높이 64dp, 세로형 항목(indicator 56×32dp, 위아래 6dp)·가로형 항목(indicator 높이 40dp, 앞뒤 16dp)은 `md-comp-nav-bar*` 토큰과 Compose `ShortNavigationBar` 기준이며, `layout(NavigationBarLayout.Auto)`(기본)는 바 너비 600dp 미만이면 세로형, 이상이면 가로형·가운데 정렬로 배치합니다. 모두 `labs.bundle.js` 를 로드해야 합니다.

#### 치수 변수·슬롯 (2.5.0.3~)

앱 바·레일의 치수는 CSS 사용자 정의 속성으로 바꿉니다. 기본값은 M3 수치와 같아서, 지정하지 않으면 화면이 바뀌지 않습니다. 레일 변수는 `md-navigation-rail` 에 한 번 지정하면 항목(`md-navigation-rail-item`)까지 상속됩니다. `::part` 로 내부 치수를 덮어쓰는 대신 이 변수를 쓰세요.

| 변수 | 기본값 | 대상 |
|---|---|---|
| `--md-top-app-bar-container-height` | `64px` | 앱 바 행 높이. medium/large 는 이 값 + 48/56px(부제 있으면 + 72/88px) |
| `--md-navigation-rail-collapsed-width` | `96px` | 접힌 레일 너비 |
| `--md-navigation-rail-narrow-width` | `80px` | `narrow(true)` 레일 너비 |
| `--md-navigation-rail-expanded-min-width` / `-max-width` | `220px` / `360px` | 펼친 레일 너비 범위 |
| `--md-navigation-rail-expanded-padding-inline` | `20px` | 펼친 레일 좌우 여백 |
| `--md-navigation-rail-top-space` / `-bottom-space` | `44px` / `20px` | 레일 위·아래 여백 |
| `--md-navigation-rail-item-space` | `4px` | 접힌 레일 항목 간격(펼침은 `--md-navigation-rail-expanded-item-space`, 기본 `0px`) |
| `--md-navigation-rail-item-min-height` | `64px` | 접힌 레일 항목 높이 |
| `--md-navigation-rail-item-padding-block` | `6px` | 접힌 레일 항목 위아래 여백 |
| `--md-navigation-rail-item-indicator-width` / `-height` | `56px` / `32px` | 접힌 레일 indicator 크기 |
| `--md-navigation-rail-item-label-space` | `4px` | 접힌 레일 indicator-라벨 간격 |
| `--md-navigation-rail-item-expanded-indicator-height` | `56px` | 펼친 레일 indicator 최소 높이 |

```java
// 앱 바: 56px 행, 제목 자리에 요소(워크스페이스 셀렉터 등). 텍스트 제목만 한 줄 말줄임이 적용된다
var appBar = topAppBar().headline(workspacePicker).style("--md-top-app-bar-container-height", "56px").element();

// 레일: 접힘 56px·라벨 숨김 [M3 외], 펼침 256px, 항목 보조 텍스트·끝 요소 [M3 외], 아래쪽 footer
var rail = navigationRail()
    .style("--md-navigation-rail-collapsed-width", "56px")
    .style("--md-navigation-rail-item-min-height", "48px")
    .style("--md-navigation-rail-expanded-min-width", "256px")
    .hideCollapsedLabels(true)
    .item(railItem().icon("folder").label("문서").supportingText("12개 항목").trailing(moreButton))
    .footer(railItem().icon("settings").label("설정").on(click, e -> openSettings()))
    .element();
```

- `headline(Element)` / `headline(IsElement<?>)`: 제목 슬롯에 요소를 둡니다. 다시 호출하면 이전 제목을 바꿉니다. DOM 요소는 한 곳에만 붙으므로 medium/large 의 확장 영역 제목에는 복제되지 않습니다(small 에서 쓰는 것을 권장).
- `hideCollapsedLabels(true)` (`hide-collapsed-labels` 속성): 접힌 레일에서 라벨을 숨깁니다. 라벨은 버튼의 `aria-label` 과 `title`(툴팁)로 남고, 펼친 레일에서는 그대로 보입니다.
- `supportingText(String)`, `trailing(Element)` (`supporting-text`/`trailing` 슬롯): 펼친 레일에서만 보입니다.
- `fullWidthIndicator(true)` (`full-width-indicator` 속성, 2.5.0.8~): 펼친 레일에서 모든 항목의 indicator(활성 표시·호버 영역)를 항목 폭만큼 채웁니다. 기본(`false`)은 M3 Expressive 대로 indicator 가 아이콘·라벨 폭에 맞춰지고, 항목 사이 간격은 토큰(`md.comp.nav-rail.expanded.between-item-space`)대로 0 입니다. M3 가이드는 baseline navigation drawer 와 비슷한 모양이 필요할 때 indicator 를 컨테이너만큼 채우는 것을 허용합니다(*"consider modifying the active indicator to fill the container"*). 클릭 영역은 두 경우 모두 레일 전체 폭입니다.
- `expandedItemSpace("4px")` (`--md-navigation-rail-expanded-item-space` 변수, 2.5.0.8~): 펼친 레일의 항목 사이 간격입니다. 기본은 `0` 으로 토큰(`between-item-space`)과 같습니다. 접힌 레일의 간격(기본 `4px`, `collapsed.item.vertical-space`)은 `--md-navigation-rail-item-space` 로 조정합니다.
- 펼침/접힘 전환 모션(2.5.0.8~): `expanded` 가 바뀌면(메뉴 버튼·`toggle()`·빌더 모두) 레일 폭, 항목 높이, indicator 크기와 아이콘 위치가 접힘↔펼침 사이에서 spring 으로 이어지고, 라벨은 중간에 사라졌다가 새 자리에서 나타납니다(Compose Material3 `WideNavigationRail` 방식, Expressive `DefaultSpatial` damping 0.8·stiffness 380, modal 은 `FastSpatial` 0.6·800). 최종 화면은 이전과 같습니다. 운영체제의 동작 줄이기(`prefers-reduced-motion: reduce`)가 켜져 있으면 전환 없이 바로 바뀝니다.
- `footer(...)` (`footer` 슬롯): 레일 맨 아래 영역입니다. footer 의 항목은 펼침 상태와 라벨 숨김을 따르지만, 목적지(`activeIndex`·`onActivated`) 대상이 아니므로 클릭은 직접 처리합니다.
- 항목 내부 part: `button`, `indicator`, `label`, `supporting-text`, `trailing`, `badge`. 레일 part: `footer`, `menu`, `scrim`.

> **동작 변경(2.5.0.8부터):** 레일을 펼치거나 접을 때 바로 바뀌지 않고 약 0.4초 동안 전환 모션이 재생됩니다. 전환 중에는 레일·항목·indicator·아이콘에 인라인 `width`/`height`/`transform` 등이 잠시 붙었다가 끝나면 원래대로 돌아갑니다. 펼침 직후 레일 폭이나 항목 위치를 곧바로 재던 코드는 전환이 끝난 뒤(약 0.5초) 재거나, 테스트에서는 `prefers-reduced-motion: reduce` 를 켜세요.

#### 링크 목적지·키보드·메뉴 아이콘 (2.5.0.6~)

```java
var rail = navigationRail().menu(true)
    .menuIcon(menuSvg)                                  // 선택: menu-icon 슬롯(폰트 없이 기본 SVG가 이미 그려짐)
    .interceptLinks(href -> router.go(href))            // 일반 클릭은 앱 라우터로, 수식 키·가운데 클릭은 새 탭
    .item(railItem().icon(homeSvg).label("홈").href("/home"))
    .item(railItem().icon(inboxSvg).label("받은편지함").href("/inbox").badge("3"))
    .element();
```

- `railItem().href(String)` (`href` 속성): 항목을 `<button>` 대신 진짜 `<a href>` 로 그립니다(part 는 그대로 `button`). 가운데 클릭·Cmd/Ctrl/Shift/Alt 클릭은 브라우저 기본 동작(새 탭·창, 주소 복사)에 맡기고 `navigation-rail-activated` 를 내지 않습니다.
- 일반 클릭은 `navigation-rail-activated`(detail `{activeIndex, item, href}`, **cancelable**)를 냅니다. 앱이 이 이벤트에서 `preventDefault()` 하면 기본 이동을 막고, 하지 않으면 링크대로 이동합니다. Java: `onActivatedEvent(Consumer<CustomEvent<?>>)`, 또는 막고 href 를 넘겨주는 `interceptLinks(Consumer<String>)`.
- 활성 링크 항목에는 `aria-current="page"` 가 붙습니다. 링크 항목이 있는 레일은 `tablist`/`tab` 역할을 쓰지 않습니다(버튼 항목만 있는 레일은 이전과 같이 `tablist`).
- 키보드: 목적지 항목에 포커스가 있을 때 ↓/↑ 는 다음/이전 항목, Home/End 는 처음/끝 항목으로 포커스를 옮깁니다. 첫/끝에서 감기지 않고, disabled 항목은 건너뛰며, 활성화(이동)는 하지 않습니다(Enter/클릭으로 활성화). Tab 은 이전과 같이 항목을 차례로 지납니다. header·footer 는 화살표 대상이 아닙니다.
- 메뉴 버튼 아이콘: 기본값이 아이콘 폰트 없이 그려지는 SVG(menu / menu_open)입니다. `menuIcon(...)`(`menu-icon` 슬롯), `menuOpenIcon(...)`(`menu-open-icon` 슬롯)으로 바꿀 수 있고, 펼침 아이콘을 주지 않으면 펼침에도 `menu-icon` 을 씁니다.

> **동작 변경(2.5.0.6부터):** 레일 내장 메뉴 버튼의 기본 아이콘이 `<md-icon>menu</md-icon>`(Material Symbols 글리프)에서 같은 모양의 SVG 로 바뀌었습니다. 메뉴 버튼 안의 `md-icon` 이나 그 텍스트를 직접 찾던 코드는 `svg[data-icon]` 을 보거나 `menu-icon` 슬롯을 쓰세요.

### Search · 명령 팔레트 (2.5.0.7~)

```java
import static dev.sayaya.ui.elements.SearchElementBuilder.*;

var palette = search().docked()
    .inline(true)          // 목록을 바 아래 흐름 안에 두고 스크림을 쓰지 않음(md-dialog 안에서)
    .filter(false)         // 앱이 정한 목록을 그대로 보여 줌(걸러내지 않음)
    .placeholder("명령 검색");
palette.onInput(q -> server.find(q, items -> palette.setSuggestions(q, items)));   // 늦게 온 결과는 q 가 지금 입력과 같을 때만 반영
palette.setSuggestions(List.of(
    suggestion("새 세션", "세션 · ⌘N", sessionSvg, () -> newSession()).key("cmd:new-session"),
    suggestion("열기", "파일", fileSvg, () -> openFile()),
    suggestion("열기", "세션", sessionSvg, () -> openSession())));               // 같은 이름도 행마다 따로 실행
// md-dialog 를 연 뒤 palette.focus(), 닫을 때 palette.reset()
```

- 항목: `suggestion(name, supportingText, leading, onSelect)`(`leading` 은 `Element`/`IsElement`, 모두 null 가능), `suggestion(name, onSelect)`. 행은 이름(body-large) + 보조 문구(body-medium) + 앞 요소로 그려집니다. 동작(`onSelect`)이 있는 항목을 고르면 목록을 닫고 그 동작만 실행합니다(입력값을 바꾸지 않고 `onSearch` 도 부르지 않음). 동작이 없는 항목은 이전처럼 입력값을 이름으로 바꾸고 `onSearch` 를 부릅니다. 모든 선택은 `onSelect(Consumer<Suggestion>)` 로도 받습니다.
- 목록: `suggestions(Suggestion...)`(뒤에 더함), `setSuggestions(List|...)`(통째로 교체), `setSuggestions(forQuery, list)`(지금 입력값이 `forQuery` 와 다르면 버림), `clearSuggestions()`. 교체할 때 하이라이트된 항목이 새 목록에도 있으면(같은 객체 또는 같은 `key(String)`) 그 행에 남고, 없으면 해제되어 `aria-activedescendant` 가 지워집니다. 입력할 때(사용자가 글자를 바꿀 때)는 이전처럼 하이라이트가 해제됩니다.
- IME: 조합 중(`KeyboardEvent.isComposing`, 또는 Safari 의 조합 확정 `keyCode 229`) keydown 은 Enter·↑/↓·Esc 모두 처리하지 않습니다.
- 아이콘: 앞 검색·뒤로 가기·지우기·기록 아이콘이 폰트 없이 그려지는 SVG(`data-icon` = search/arrow_back/close/history)입니다. `searchIcon(...)`, `backIcon(...)`, `clearIcon(...)`, `historyIcon(Supplier)` 로 바꿉니다.
- 다이얼로그: `focus()` 는 입력에 포커스를 주고 엽니다, `reset()` 은 값을 비우고 닫습니다(`onInput` 은 부르지 않음, 목록은 유지). Esc 는 목록이 열려 있을 때만 소비하고(닫기), 닫혀 있으면 기본 동작을 막지 않아 감싼 다이얼로그가 받습니다.

> **동작 변경(2.5.0.7부터):** 검색의 아이콘이 Material Symbols 글리프 글자에서 SVG 로 바뀌었습니다(`.leading .icon` 의 텍스트를 읽던 코드는 `svg[data-icon]` 을 보세요). 또 닫힌 상태의 Esc 는 더 이상 `preventDefault` 하지 않습니다.

### Tooltip / Snackbar / Button group

```java
import static dev.sayaya.ui.elements.FeedbackElementBuilder.*;

// Plain tooltip: 앵커에 hover/focus 하면 표시, 창에 동시에 하나만
var tip = tooltip().plain().anchor(saveButton).text("문서를 저장합니다").element();

// Rich tooltip: 제목 + 본문 + 액션 (액션이 있으면 툴팁 위로 포인터를 옮겨도 유지)
var info = tooltip().rich().anchor(infoIcon).position(TooltipPosition.Bottom)
    .subhead("권한").text("편집자만 수정할 수 있습니다")
    .action(button().text().text("자세히"))
    .element();

// Snackbar: 한 번에 하나, 닫힘 사유(action/dismiss/timeout/replaced) 제공
var sb = snackbar().text("삭제했습니다").action("실행 취소").closeable(true)
    .onAction(s -> undo()).onClosed(reason -> log(reason))
    .element();
body().add(sb);
sb.show();

// Button group
var actions = buttonGroup().standard().size(ButtonGroupSize.Small)
    .add(button().outlined().text("편집")).add(button().outlined().text("공유")).element();
var range = buttonGroup().connected().size(ButtonGroupSize.Small).selection(ButtonGroupSelection.Single)
    .add(button().outlined().text("일")).add(button().outlined().text("주")).add(button().outlined().text("월"))
    .onSelectionChanged(selected -> apply(selected))
    .element();
```

`md-tooltip`, `md-snackbar`, `md-button-group` 은 Material Web 에 없어 sayaya-ui 가 직접 구현한 엘리먼트입니다(`labs-bundle/src/sayaya-feedback.js`, button group 은 2.5.0.5부터 `labs-bundle/src/sayaya-button-group.js`).
색·모양·타이포·높이·간격은 `@material/web` 2.5.0 의 `md-comp-{plain-tooltip,rich-tooltip,snackbar,button-group-*}` 토큰 값을 따릅니다.
토큰에 없는 레이아웃 값은 m3.material.io 스펙 페이지와 Compose Material3(`Tooltip.kt`, `Snackbar.kt`) 상수로 대조했습니다: plain tooltip 높이 24dp·패딩 8dp·최대 너비 200dp, rich tooltip 패딩 12/16/8dp·최대 너비 320dp, tooltip 최소 너비 40dp·앵커 간격 4dp, snackbar 최대 너비 600dp, 화면 가장자리 여백 8dp(material-components-android `m3_snackbar_margin`).

#### Tooltip 동작 규칙 (2.5.0.5)

| 입력 | 규칙 |
|---|---|
| 마우스 | 앵커에 올리고 `delay`(기본 500ms) 뒤 표시, 떠나고 1.5초 뒤 닫힘 |
| 키보드 | 포커스로 같은 규칙. Esc 로 닫힘. 터치 직후 1초 안의 `focusin`(탭이 만든 포커스)은 무시 |
| 터치 | `longPressDelay`(기본 500ms) 동안 누르고 있으면 표시. 그 전에 떼면 탭(열지 않고 click 은 그대로), 10px 넘게 움직이거나 `pointercancel`(스크롤 시작)이면 취소. 길게 눌러 연 뒤 떼면 1.5초 뒤 닫힘. 길게 누른 뒤 따라오는 click 은 앵커에 전달하지 않음(Compose `TooltipBox`·Android 길게 누르기처럼 탭 동작을 일으키지 않음). 누르는 동안 `contextmenu` 를 막음 |
| 동시 표시 | 창 안에서 하나만. 새 툴팁이 열리면 열려 있던 툴팁은 즉시 닫힘(persistent rich 포함) |
| 표시 위치 | `popover="manual"` 로 top layer 에 올림. 조상의 `overflow`·`transform`·`z-index` 에 잘리거나 가려지지 않고, 열린 동안 `visibility: visible` 이라 `visibility:hidden` 부모 안에서도 보임. `display:none` 조상 안에서는 보이지 않으므로 `document.body` 아래에 두고 `anchor(...)` 로 연결하기를 권장 |
| 수명 | DOM 에서 떼면(`disconnectedCallback`) 닫히고 앵커 리스너·타이머·문서 리스너가 정리됨. 다시 붙이면 같은 앵커에 다시 연결됨 |
| 문구 | `text(...)` 를 다시 부르거나 요소의 `text` 속성을 바꾸면 기본 슬롯 문구가 교체됨(subhead·action 유지) |

```java
var tip = tooltip().plain().anchor(iconButton).text("저장").element();
body().add(tip);                // body 아래에 두고 anchor 로 연결
tip.text = "다른 이름으로 저장"; // 문구만 바꾸기
tip.remove();                   // 떼면 리스너·타이머 정리
```

> **동작 변경(2.5.0.5부터):** `TooltipBuilder.text(String)` 을 여러 번 부르면 이전에는 텍스트를 이어 붙였지만 이제 마지막 문구로 바뀝니다. 또 `delay` 를 지정하지 않은 툴팁은 2.5.0.4 까지 기본값이 적용되지 않아 바로 열렸는데, 이제 문서대로 500ms 뒤 열립니다.

버튼 그룹의 토글·누름 전환: 선택하거나 누를 때 모서리 모양과 너비가 `md.sys.motion.spring.fast.spatial`(damping 0.9, stiffness 1400)을 따라 부드럽게 바뀌고, outlined 버튼의 테두리 색은 `md.sys.motion.spring.default.effects`(damping 1, stiffness 1600)로 사라집니다. Compose Material3 `ToggleButton`/`ButtonGroup` 과 같이 container·content 색은 바로 바뀝니다. CSS 에는 spring 이 없어 두 spring 을 `linear()` 곡선(약 224ms, 231ms)으로 근사했습니다. `prefers-reduced-motion: reduce` 이면 전환하지 않습니다.

### Toast [M3 외 확장]

> **M3 외 확장**: Material Design 3 에는 toast 컴포넌트가 없습니다. M3 의 일시 알림은 snackbar 하나뿐이며, 화면 아래에 한 번에 하나만 뜨는 모바일 중심 패턴입니다. `md-toast` 는 데스크톱 웹 앱에서 여러 알림을 화면 오른쪽 위에 쌓아 보여 주는 관례를 위해 sayaya-ui 가 추가한 엘리먼트입니다(`labs-bundle/src/sayaya-feedback.js`). 색·모양·글꼴은 M3 시스템 토큰을 쓰지만, 배치와 동작은 M3 스펙이 아닙니다.

**언제 무엇을 쓰나**

| 상황 | 권장 | 이유 |
|---|---|---|
| 모바일(폭 600px 미만), 또는 M3 를 그대로 따르는 화면 | `snackbar()` | M3 표준. 화면 아래, 한 번에 하나, 실행 취소 같은 액션 1개 |
| 데스크톱 웹에서 짧은 결과 알림(저장됨·실패 등)이 연달아 생길 때 | `toast()` | 오른쪽 위에 쌓여 서로 덮어쓰지 않음, 레벨(info/success/warning/error)별 색 |
| 오류·경고처럼 사용자가 꼭 읽어야 하는 알림 | `toast().error()` / `toast().warning()` | 직접 닫을 때까지 남고 `role=alert` 로 바로 읽힘 |
| 사용자가 응답해야 하는 결정 | `dialog()` | 토스트·스낵바는 결정을 요구하지 않습니다 |

```java
import static dev.sayaya.ui.elements.FeedbackElementBuilder.*;

// info·success: 3초 뒤 자동으로 닫힘
toast().success().text("저장했습니다").show();

// warning·error: 사용자가 닫을 때까지 유지, 액션 버튼(선택)
toast().error().text("저장하지 못했습니다")
    .action("다시 시도").onAction(t -> save())
    .onClosed(reason -> log(reason))   // action | dismiss | timeout | overflow
    .show();

// 시간·최대 개수 지정. timeout(0) 이면 자동으로 닫지 않음
toast().info().text("새 버전이 있습니다").timeout(5000).max(3).closeable(false).show();
```

- **위치·쌓임**: `show()` 하면 `body` 에 붙는 `.md-toast-stack`(fixed, 위 60px·오른쪽 20px, 간격 8px, 최대 너비 400px, z-index 10002)에 들어갑니다. 새 토스트가 아래에 붙고, `max`(기본 5)를 넘으면 가장 오래된 것부터 `overflow` 사유로 닫힙니다. 위치는 CSS 변수 `--md-toast-top`, `--md-toast-right`, `--md-toast-z-index` 로 바꿀 수 있습니다. 폭 600px 미만에서는 좌우 8px 여백으로 늘어나지만, 모바일에서는 snackbar 를 권장합니다.
- **자동 닫힘**: `timeout` 을 지정하지 않으면 info·success 는 3000ms, warning·error 는 닫지 않음. 포인터나 포커스가 토스트에 있는 동안은 타이머가 멈추고, 떠나면 남은 시간(최소 1초) 뒤 닫힙니다.
- **색**: info `primary`, success `tertiary`, warning `error-container`, error `error`(각각 `on-*` 글자색). 모서리 `corner-small`(8px), 그림자 elevation 2, 본문 `body-medium`(14px).
- **애니메이션**: 나타날 때 오른쪽 20px 에서 미끄러져 들어오고(300ms), 닫힐 때 위로 10px 올라가며 흐려진 뒤(300ms) DOM 에서 제거됩니다. `prefers-reduced-motion: reduce` 이면 애니메이션하지 않습니다.
- **접근성**: info·success 는 `role=status`·`aria-live=polite`, warning·error 는 `role=alert`·`aria-live=assertive`. 닫기 버튼은 `aria-label="닫기"` 를 가진 `<button>` 입니다.

**handbook `ToastContainer` 에서 옮길 때**

이 컴포넌트는 handbook `ui-components` 의 `ToastContainer` 를 옮긴 것으로, 수치(위치·간격·색 역할·애니메이션·3초)를 그대로 따릅니다.

| handbook | sayaya-ui |
|---|---|
| `toastContainer.show(ToastLevel.SUCCESS, msg)` | `toast().success().text(msg).show()` |
| `show(level, msg, autoCloseMs)` | `toast().level(ToastLevel.X).text(msg).timeout(ms).show()` |
| 컨테이너를 DI 로 주입해 직접 DOM 에 붙임 | 창마다 스택 하나를 `show()` 가 자동으로 만듦 |
| `.ui-toast`, `.ui-toast-<level>` 셀렉터 | host 에 같은 클래스가 붙어 그대로 쓸 수 있음 |
| `.ui-toast-close` (div) | shadow DOM 안 `<button part="close">` — Playwright 에서는 `md-toast >> [part=close]` 처럼 접근 |
| 없음 | 액션 버튼, hover 시 타이머 정지, 최대 개수, `role`/`aria-live` |

### Side sheet

```java
import static dev.sayaya.ui.elements.SideSheetElementBuilder.*;

// Standard: 본문 옆에 나란히 (medium/expanded 화면)
var details = sideSheet().standard().headline("세부 정보").divider(true).open(true)
    .add(detailPanel)
    .action(button().filled().text("저장")).action(button().outlined().text("취소"))
    .element();

// Modal: 스크림 위에 표시 (compact 화면). 닫기 버튼·스크림 클릭·Esc 로 닫힘
var filters = sideSheet().modal().headline("필터").add(filterForm)
    .onClosed(reason -> log(reason))   // close | scrim | escape
    .element();
body().add(filters);
filters.show();
```

`md-side-sheet` 는 Material Web 에 없어 sayaya-ui 가 직접 구현한 엘리먼트입니다(`labs-bundle/src/sayaya-sheet.js`).
색·모양·너비(256dp)·elevation·헤드라인은 `md-comp-sheet-side` 토큰, 패딩(24/16dp)·요소 간격 12dp·최대 너비 400dp·detached 여백 16dp·하단 액션 패딩(16/24dp)은
m3.material.io side sheet 스펙 페이지(MDC-Android `m3_side_sheet_margin_detached` 16dp 와 일치)를 따릅니다.
하단 액션 영역은 높이 72dp(위 16 + XSmall 버튼 32 + 아래 24)로, 그 안의 Material 버튼은 XSmall 토큰(`md-comp-button-xsmall` 32dp) 높이로 맞춥니다.

### Bottom sheet

```java
import static dev.sayaya.ui.elements.BottomSheetElementBuilder.*;

// Modal: 스크림 위에 표시. 처음에는 화면 높이의 50% 까지 열리고, 핸들을 누르거나 위로 끌면 펼쳐진다
var share = bottomSheet().modal().add(shareTargets)
    .onClosed(reason -> log(reason))   // scrim | escape | swipe | close
    .element();
body().add(share);
share.show();

// Standard: 스크림 없이 화면 아래에 붙어 뒤 화면을 계속 쓸 수 있다(예: 음악 플레이어)
var player = bottomSheet().standard().add(nowPlaying).element();
```

`md-bottom-sheet` 는 Material Web 에 없어 sayaya-ui 가 직접 구현한 엘리먼트입니다(`labs-bundle/src/sayaya-sheet.js`).
색·모양(위쪽 모서리 28dp)·elevation·드래그 핸들(32×4dp, 불투명도 0.4)은 `md-comp-sheet-bottom` 토큰,
최대 너비 640dp·위 여백 72dp(창 640dp 초과 시 위·옆 56dp)·핸들 위아래 패딩 22dp 는 m3.material.io bottom sheet 스펙 페이지를 따릅니다.

## 빌더 인터페이스

sayaya-ui는 공통 패턴을 위한 재사용 가능한 인터페이스를 제공합니다:

- **`Disableable`** - 비활성화 가능한 컴포넌트. 비활성 여부는 `disabled(boolean)` / `disabled()` / `enabled()` 로 설정하고 `isDisabled()` 로 읽습니다. 반영되는 `disabled` 속성(attribute)의 값 문자열(`""`, `"true"` 등)은 컴포넌트마다 다를 수 있으므로 `getAttribute("disabled")` 값에 의존하지 마세요(직접 볼 때는 `hasAttribute("disabled")`). `setAttribute("disabled", "false")` 는 불리언 속성 규칙상 비활성으로 해석됩니다.
- **`Selectable`** - 선택 상태가 있는 컴포넌트
- **`Requireable`** - 필수 입력이 가능한 컴포넌트
- **`Toggleable`** - 토글 기능이 있는 컴포넌트
- **`Linkable`** - 링크로 동작할 수 있는 컴포넌트
- **`FormAssociable`** - 폼과 통합되는 컴포넌트
- **`Typeable`** - 타입 변형이 있는 컴포넌트
- **`Elevatable`** - 높이가 있는 컴포넌트
- **`Clickable`** - 클릭 이벤트를 지원하는 컴포넌트
- **`Validatable`** - 유효성 검사를 지원하는 컴포넌트
- **`HasAriaLabel`** - ARIA 레이블을 지원하는 컴포넌트
- **`HasIconSlot`** - 아이콘 슬롯이 있는 컴포넌트
- **`HasHeadlineSlot`** - 제목 슬롯이 있는 컴포넌트
- **`HasContentSlot`** - 본문 슬롯이 있는 컴포넌트
- **`HasActionsSlot`** - 액션 버튼 슬롯이 있는 컴포넌트
- **`HasStartSlot`** - 시작 위치 슬롯이 있는 컴포넌트
- **`HasEndSlot`** - 끝 위치 슬롯이 있는 컴포넌트
- **`HasSupportingTextSlot`** - 보조 텍스트 슬롯이 있는 컴포넌트
- **`HasLabel`** - 레이블을 지원하는 컴포넌트
- **`HasValue`** - 값을 가지는 컴포넌트
- **`HasRange`** - 범위(min/max)를 지원하는 컴포넌트
- **`HasErrorState`** - 에러 상태를 지원하는 컴포넌트
- **`HasCustomValidity`** - 커스텀 유효성 검사를 지원하는 컴포넌트
- **`HasInputEvent`** - Input 이벤트를 지원하는 컴포넌트
- **`HasChangeEvent`** - Change 이벤트를 지원하는 컴포넌트
- **`HasDialogEvents`** - Dialog 이벤트를 지원하는 컴포넌트
- **`HasMenuEvents`** - 메뉴 이벤트를 지원하는 컴포넌트

## 개발

### 필수 조건

- JDK 11+
- Gradle 7.0+
- Node.js & npm (Material Web Labs 번들 빌드용)

### 빌드

```bash
./gradlew build
```

빌드 시 자동으로 Material Web Labs 컴포넌트(Card 등)가 번들링됩니다.

### 테스트 실행

```bash
./gradlew test
```

### GWT 개발 모드

```bash
./gradlew gwtDevMode
```

로컬에서 개발 모드로 실행하거나, [라이브 데모](https://sayaya1090.github.io/sayaya-ui/)에서 모든 컴포넌트를 확인할 수 있습니다.

### Material Web Labs 번들

Card, Badge와 같은 Labs 컴포넌트는 별도 번들로 제공됩니다:
- 소스: `src/main/webapp/labs-bundle/`
- 출력: `src/main/webapp/labs.bundle.js`
- 자동 빌드: Gradle 빌드 시 자동으로 npm을 통해 번들 생성
- 포함 컴포넌트: Card (elevated/filled/outlined), Badge, Expressive `md-gb-*` 16종 (Material Web 2.5.0 labs/gb), Navigation drawer(+modal)
- sayaya-ui 자체 구현: top app bar, navigation bar/rail, tooltip, snackbar, button group, side sheet, bottom sheet, toast [M3 외 확장] (`labs-bundle/src/sayaya-*.js`)

### 단일 JS 번들 (Release 첨부물, 2.5.0.3~)

jar 에는 JS 가 들어 있지 않습니다. CDN 없이 쓰려면 릴리스마다 GitHub Release 에 첨부되는 단일 classic-script 번들을 쓰세요.

- 파일: `sayaya-ui-<version>.js` (+ 체크섬 `sayaya-ui-<version>.js.sha256`)
- URL: `https://github.com/sayaya1090/sayaya-ui/releases/download/v<version>/sayaya-ui-<version>.js`
- 내용: `@material/web/all.js`(IIFE) 와 labs 번들 `src/index.js`(IIFE, 기존 rollup 플러그인 그대로)를 이 순서로 이어 붙인 파일. 헤더 주석에 sayaya-ui 버전·빌드 커밋·@material/web 버전이 적힙니다.
- 사용: GWT 앱보다 먼저 `<script src="sayaya-ui-<version>.js"></script>` 로 한 번 로드합니다(`type="module"` 아님).
- 로컬 빌드: `./gradlew buildStandaloneBundle` → `src/main/webapp/labs-bundle/dist/sayaya-ui.js`
- 주의: 이 번들은 `@material/web/all.js` 를 함께 담고 있어, 앱이 Material Web 을 따로 싣는 경우 같은 `md-*` 이름을 두 번 정의하게 됩니다. 어느 쪽을 먼저 실어도 나중에 실린 쪽이 `NotSupportedError`(예: `md-elevation` has already been used)로 멈춥니다(2.5.0.4 첨부물로 확인). 이때는 아래 feedback 번들을 쓰세요.

### 단독 feedback ESM 번들 (Release 첨부물, 2.5.0.5~)

Material Web 을 앱이 따로 번들해 싣는 경우를 위한 ES module 입니다.

- 파일: `sayaya-ui-feedback-<version>.js` (+ 체크섬 `sayaya-ui-feedback-<version>.js.sha256`)
- URL: `https://github.com/sayaya1090/sayaya-ui/releases/download/v<version>/sayaya-ui-feedback-<version>.js`
- 정의하는 요소: `md-tooltip`, `md-snackbar`, `md-toast` 만. `@material/web` 컴포넌트와 `md-button-group` 은 넣지 않습니다(`lit` 은 포함). 각 정의는 이름이 이미 등록돼 있으면 건너뜁니다.
- 사용: 앱의 Material Web 번들 뒤에 `<script type="module" src="sayaya-ui-feedback-<version>.js"></script>`, 또는 앱 코드에서 `import './sayaya-ui-feedback-<version>.js'`.
- 로컬 빌드: `./gradlew buildFeedbackBundle` → `src/main/webapp/labs-bundle/dist/sayaya-ui-feedback.js` (또는 `labs-bundle` 에서 `npm ci && npm run build:feedback`)
- 자체 번들러로 묶을 때: 진입점 `labs-bundle/src/sayaya-feedback.js` 는 `lit` 만 import 하므로 앱의 `lit` 과 함께 묶을 수 있습니다.

### 단독 navigation ESM 번들 (Release 첨부물, 2.5.0.6~)

Material Web 을 앱이 따로 번들해 싣는 경우를 위한 ES module 입니다.

- 파일: `sayaya-ui-navigation-<version>.js` (+ 체크섬 `sayaya-ui-navigation-<version>.js.sha256`)
- URL: `https://github.com/sayaya1090/sayaya-ui/releases/download/v<version>/sayaya-ui-navigation-<version>.js`
- 정의하는 요소: `md-top-app-bar`, `md-navigation-rail`, `md-navigation-rail-item`, `md-navigation-bar`, `md-navigation-tab` 만(`lit` 포함). 각 정의는 이름이 이미 등록돼 있으면 건너뜁니다.
- 넣지 않는 것: `@material/web` 컴포넌트, `md-navigation-drawer(-modal)`(Material Web labs 요소라 앱 번들의 내부 요소와 겹칠 수 있음).
- 필요한 Material Web 요소: 레일 내장 메뉴 버튼(`menu(true)`)은 `md-icon-button` 을 씁니다. 앱이 `@material/web/iconbutton/icon-button.js` 를 함께 실어야 합니다. 메뉴 아이콘은 기본 SVG 라 아이콘 폰트는 필요 없습니다. 항목 아이콘(`icon(String)` = `md-icon`)을 쓰면 `md-icon` 과 아이콘 폰트가 필요하고, `icon(Element)` 로 SVG 를 넣으면 필요 없습니다.
- 사용: 앱의 Material Web 번들 뒤에 `<script type="module" src="sayaya-ui-navigation-<version>.js"></script>`, 또는 앱 코드에서 `import './sayaya-ui-navigation-<version>.js'`. feedback 번들과 함께 실어도 됩니다.
- 로컬 빌드: `./gradlew buildNavigationBundle` → `src/main/webapp/labs-bundle/dist/sayaya-ui-navigation.js` (또는 `labs-bundle` 에서 `npm ci && npm run build:navigation`)
- 자체 번들러로 묶을 때: 진입점 `labs-bundle/src/sayaya-navigation-standalone.js` 는 `lit` 만 import 합니다.

## 아키텍처

```
sayaya-ui/
├── src/main/
│   ├── java/dev/sayaya/ui/
│   │   ├── dom/              # Material Web 요소에 대한 JsInterop 바인딩
│   │   │   ├── MdButtonElement.java
│   │   │   ├── MdCheckboxElement.java
│   │   │   ├── MdCardElement.java
│   │   │   ├── MdBadgeElement.java
│   │   │   ├── MdListElement.java
│   │   │   └── ...
│   │   └── elements/         # 유창한 빌더 API
│   │       ├── ButtonElementBuilder.java
│   │       ├── CheckboxElementBuilder.java
│   │       ├── CardElementBuilder.java
│   │       ├── BadgeElementBuilder.java
│   │       ├── ListElementBuilder.java
│   │       └── interfaces/   # 재사용 가능한 빌더 특성
│   │           ├── Disableable.java
│   │           ├── Clickable.java
│   │           ├── HasAriaLabel.java
│   │           └── ...
│   └── webapp/
│       ├── labs-bundle/      # Material Web Labs 번들 빌드
│       │   ├── package.json
│       │   ├── rollup.config.js
│       │   └── src/index.js
│       └── labs.bundle.js    # 빌드된 Labs 컴포넌트 번들
└── src/test/
    ├── java/             # GWT 테스트 진입점
    │   └── dev/sayaya/ui/
    │       ├── card/     # Card 테스트 케이스
    │       ├── badge/    # Badge 테스트 케이스
    │       ├── list/     # List 테스트 케이스
    │       └── ...
    ├── kotlin/           # Kotest 명세
    └── webapp/           # 테스트 HTML 페이지
        └── labs.bundle.js    # 복사된 Labs 번들
```

## 의존성

- **[Elemento](https://github.com/hal/elemento)** (2.5.9) - GWT용 타입 안전 HTML 빌더
- **[Material Web](https://github.com/material-components/material-web)** - Google의 Material Design 웹 컴포넌트
- **GWT** (2.13.1) - Google Web Toolkit

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

현재 버전: **2.5.0.5**