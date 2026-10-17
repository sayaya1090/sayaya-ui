# Material Web Labs Bundle

Material Web Labs 컴포넌트들을 번들링하여 `@material/web/all.js`와 함께 사용하기 위한 빌드 스크립트입니다.

## 포함된 컴포넌트

- Card (elevated, filled, outlined)
- Badge (숫자/텍스트 배지, 점 배지)
- Expressive 컴포넌트 `md-gb-*` 16종 (Material Web 2.5.0 `labs/gb`)
- Navigation bar/tab, Navigation drawer(+modal) (Material Web labs)
- sayaya-ui 자체 구현: `sy-top-app-bar`, `sy-navigation-rail`, `sy-navigation-rail-item` (`src/sayaya-navigation.js`)
- 향후 추가 예정: 기타 Labs 컴포넌트

## 빌드 방법

```bash
cd src/main/webapp/labs-bundle
npm install
npm run build
```

빌드 결과물은 `src/main/webapp/labs.bundle.js`에 생성됩니다.

## 사용 방법

HTML에서 다음과 같이 사용:

```html
<script type="module">
    import '@material/web/all.js';
</script>
<script type="module" src="labs.bundle.js"></script>
```

## 새 Labs 컴포넌트 추가

`src/index.js`에 export 추가:

```javascript
export { NewComponent } from '@material/web/labs/new-component.js';
```

## labs/gb 빌드 참고

`labs/gb` 컴포넌트는 CSS module script(`import x from './a.css' with {type: 'css'}`)로 스타일을 불러옵니다.
rollup은 CSS를 파싱하지 못하므로 `rollup.config.js`의 `css-module-to-cssresult` 플러그인이
같은 위치의 `.cssresult.js`(default export = `CSSStyleSheet`)로 연결합니다.
또한 `remove-elevation` 플러그인은 `labs/gb` 아래 모듈에는 적용되지 않습니다(gb는 자체 elevation 토큰을 사용).


## 중복 정의 방지

labs 컴포넌트는 `md-focus-ring`, `md-ripple` 같은 공용 엘리먼트를 다시 import 한다. `all.js` 가 이미 등록한 이름을
다시 정의하면 `NotSupportedError` 로 번들 실행이 중단되므로, `rollup.config.js` 의 `intro`/`outro` 가
번들 실행 중에만 `customElements.define` 을 감싸 이미 등록된 이름은 건너뛰고, 실행이 끝나면 원래대로 되돌린다.
