# sayaya-ui 개발 문서

라이브러리를 고치거나 컴포넌트를 추가하는 유지보수자를 위한 문서입니다. 아키텍처에서 시작해 빌드 → 컴포넌트 추가 → 테스트 → 릴리스 순서로 읽으면 됩니다.
요구사항·유스케이스·설계 다이어그램(UP 산출물)은 [docs/design](../design/README.md) 에 있습니다.

| 순서 | 문서 | 내용 |
|---|---|---|
| 1 | [01-architecture.md](01-architecture.md) | 계층, 모듈, Java↔JS 결합점, 런타임 로드 순서, 소스 트리 |
| 2 | [02-build.md](02-build.md) | Gradle 태스크 그래프, npm·rollup 번들 4종, 의존성 버전 |
| 3 | [03-adding-component.md](03-adding-component.md) | 컴포넌트 추가 절차(세 가지 형태별) |
| 4 | [04-testing.md](04-testing.md) | 브라우저 테스트 구조, 작성 규칙, 변조 검증 |
| 5 | [05-release.md](05-release.md) | 버전 규칙, 태그 push 릴리스, 배포 확인 |

문서의 파일·클래스·태스크·이벤트 이름은 소스에 있는 이름 그대로입니다. 줄 번호는 쓰지 않습니다.
