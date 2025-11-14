# Material Web Labs Bundle

Material Web Labs 컴포넌트들을 번들링하여 `@material/web/all.js`와 함께 사용하기 위한 빌드 스크립트입니다.

## 포함된 컴포넌트

- Card (elevated, filled, outlined)
- Badge (숫자/텍스트 배지, 점 배지)
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
