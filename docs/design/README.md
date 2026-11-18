# sayaya-ui 설계 문서 (Unified Process 형식)

이 디렉터리는 sayaya-ui(`dev.sayaya:ui`)의 분석·설계 산출물을 Unified Process(Larman, *Applying UML and Patterns*) 순서로 정리한 것입니다.
코드가 기준이며, 문서의 클래스·메서드·태그·이벤트 이름은 소스에 있는 이름 그대로입니다. 줄 번호는 적지 않고 파일과 식별자로 근거를 답니다.

| 순서 | 산출물 | 갈래 | 파일 |
|---|---|---|---|
| 1 | 비전·요구사항(기능·비기능) | 요구 | [01-requirements.md](01-requirements.md) |
| 2 | 유스케이스 모델 | 분석 | [02-use-cases.md](02-use-cases.md) |
| 3 | 시스템 시퀀스 다이어그램(SSD) | 분석 | [03-system-sequence.md](03-system-sequence.md) |
| 4 | 도메인 모델 | 분석 | [04-domain-model.md](04-domain-model.md) |
| 5 | 설계 시퀀스 다이어그램 | 설계 | [05-design-sequences.md](05-design-sequences.md) |
| 6 | 설계 클래스 다이어그램 | 설계 | [06-design-classes.md](06-design-classes.md) |
| 7 | 패키지·컴포넌트·배포 | 설계 | [07-architecture.md](07-architecture.md) |
| 8 | 추적표·용어집 | 전 과정 | [08-traceability-glossary.md](08-traceability-glossary.md) |

## 개발 사례(development case)

- 반복 단위: 리비전 릴리스(`2.5.0.N`). 한 리비전은 요구 → 구현 → 브라우저 테스트 → 태그 push 릴리스로 끝난다.
- 이 프로젝트가 쓰는 산출물: 요구사항 목록, 유스케이스(간략형 + 핵심 2건 정식형), SSD, 도메인 모델, 설계 시퀀스·클래스, 패키지·배포 다이어그램, 추적표.
- 쓰지 않는 산출물: 데이터 모델(저장소 없음), 상태 기계는 레일 펼침/접힘 하나만 시퀀스로 대신한다.

## 표기에 대해

다이어그램은 Mermaid 로 그렸습니다. Mermaid 는 UML 전체를 표현하지 못합니다.
- 유스케이스 다이어그램은 Mermaid 에 표기가 없어 `flowchart` 로 대신합니다(액터 = 둥근 노드, 유스케이스 = 타원 노드).
- 클래스 다이어그램에서 제네릭 자기 타입(`SELF`)은 `~SELF~` 로 적고, Java 인터페이스는 `<<interface>>` 스테레오타입으로 표시합니다.
- 패키지·배포 다이어그램은 `flowchart` 의 `subgraph` 로 대신합니다.

## 바뀔 예정인 부분

Material Web 공식 labs 구현(`md-gb-nav-rail`, `md-gb-snackbar`, `md-gb-button-group`, `md-gb-toolbar`, `md-gb-app-bar`, `md-gb-nav-bar`)이 정식 출시되면 직접 구현한 커스텀 엘리먼트를 그것으로 교체합니다. 이때 **빌더 API 와 이벤트 이름은 유지**하고 내부 태그만 바꿉니다(어댑터). 05·06·07 의 직접 구현 부분은 교체 후 다시 그립니다.
