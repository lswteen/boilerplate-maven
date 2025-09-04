<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script setup lang="ts">
// Vue Router가 자동으로 처리
</script>

<style>
#app {
  min-height: 100vh;
}

/* Vue Router 트랜지션 */
.router-link-exact-active {
  /* 활성 링크 스타일은 각 컴포넌트에서 처리 */
}

/* 페이지 전환 애니메이션 */
.page-enter-active, .page-leave-active {
  transition: opacity 0.3s ease;
}

.page-enter-from, .page-leave-to {
  opacity: 0;
}
</style>
