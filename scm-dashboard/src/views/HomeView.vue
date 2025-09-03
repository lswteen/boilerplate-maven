<template>
  <main>
    <!-- 기존 HelloWorld 컴포넌트는 숨기고 API 테스트만 표시 -->
    <!-- <TheWelcome /> -->

    <!-- API 테스트 컴포넌트 추가 -->
    <ApiTest />
  </main>
</template>

<script setup lang="ts">
import ApiTest from '@/components/ApiTest.vue'
// import TheWelcome from '@/components/TheWelcome.vue'
</script>
