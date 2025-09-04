# scm-dashboard
```text
- node 설치 (window, macOS, Linux)

# step 01 프로젝트 생성
cd boilerplate-maven
npm create vue@latest scm-dashboard
선택 은 잘 조정
---
Select features to include in your project: (↑/↓ to navigate, space to
select, a to toggle all, enter to confirm)
│  ◼ TypeScript
│  ◻ JSX Support
│  ◻ Router (SPA development)
│  ◻ Pinia (state management)
│  ◻ Vitest (unit testing)
│  ◻ End-to-End Testing
│  ◻ ESLint (error prevention)
│  ◻ Prettier (code formatting)
---
◼ TypeScript          # API 타입 안정성을 위해 필수
◼ Router (SPA development)  # 페이지 라우팅 필수  
◼ Pinia (state management) # 상태 관리 필수
◼ ESLint (error prevention) # 코드 품질 관리
◼ Prettier (code formatting) # 코드 포매팅
---
◆  Select experimental features to include in your project: (↑/↓ to navigate,
space to select, a to toggle all, enter to confirm)
│  ◻ Oxlint (experimental)
│  ◻ rolldown-vite (experimental)
---
◻ Oxlint (experimental)         # 선택 안함
◻ Rolldown-vite (experimental)  # 선택 안함
---
◆  Skip all example code and start with a blank Vue project?
│  ○ Yes / ● No
---
Enter 종료
---
scm-dashboard/
├── src/
│   ├── components/
│   │   └── HelloWorld.vue      # 카운터 컴포넌트
│   ├── views/
│   │   ├── HomeView.vue        # 홈페이지 (카운터 포함)
│   │   └── AboutView.vue       # About 페이지
│   ├── stores/
│   │   └── counter.ts          # Pinia 상태관리 예제
│   ├── router/
│   │   └── index.ts            # 라우팅 설정
│   └── App.vue                 # 메인 앱 컴포넌트
---
```

# step 02 서버 구동되는지 체크 
```shell
cd scm-dashboard 
npm install
npm run dev
```

# node 실행시 아래와같이 console 처리
```shell
milk@milkui-Macmini scm-dashboard % npm run dev

> scm-dashboard@0.0.0 dev
> vite

3:24:40 PM [vite] (client) Re-optimizing dependencies because lockfile has changed

  VITE v7.1.4  ready in 688 ms

  ➜  Local:   http://localhost:5173/
  ➜  Network: use --host to expose
  ➜  Vue DevTools: Open http://localhost:5173/__devtools__/ as a separate window
  ➜  Vue DevTools: Press Option(⌥)+Shift(⇧)+D in App to toggle the Vue DevTools
  ➜  press h + enter to show help

```

# console 오픈 이후 가능한 inline 명령어 모음
```shell
press r + enter to restart the server
press u + enter to show server url
press o + enter to open in browser
press c + enter to clear console
press q + enter to quit
```

# step 03 
npm install -D sass unplugin-auto-import unplugin-vue-components

# 4. 추가 패키지들 종속성 설치 
npm install axios @types/node @element-plus/icons-vue dayjs lodash-es @types/lodash-es

# 

## Project Setup
```sh
npm install
```
### Compile and Hot-Reload for Development
```sh
npm run dev
```
### Type-Check, Compile and Minify for Production
```sh
npm run build
```
### Lint with [ESLint](https://eslint.org/)
```sh
npm run lint
```
