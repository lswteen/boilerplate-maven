<template>
  <div class="main-layout">
    <!-- Header -->
    <Header />

    <div class="layout-container">
      <!-- Sidebar -->
      <Sidebar
        :is-collapsed="sidebarCollapsed"
        @toggle="toggleSidebar"
      />

      <!-- Main Content -->
      <main class="main-content" :class="{ 'sidebar-collapsed': sidebarCollapsed }">
        <div class="content-wrapper">
          <router-view />
        </div>
      </main>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import Header from '@/components/Header.vue'
import Sidebar from '@/components/Sidebar.vue'

// 사이드바 상태 관리
const sidebarCollapsed = ref(false)

const toggleSidebar = () => {
  sidebarCollapsed.value = !sidebarCollapsed.value
}
</script>

<style scoped>
.main-layout {
  min-height: 100vh;
  background: linear-gradient(135deg, var(--dark-bg) 0%, var(--darker-bg) 100%);
}

.layout-container {
  display: flex;
  min-height: calc(100vh - 70px);
}

.main-content {
  flex: 1;
  margin-left: 260px;
  transition: margin-left 0.3s ease;
  background: transparent;
  padding-top: 70px;
}

.main-content.sidebar-collapsed {
  margin-left: 70px;
}

.content-wrapper {
  padding: 30px;
  min-height: 100%;
}

/* 반응형 디자인 */
@media (max-width: 768px) {
  .main-content {
    margin-left: 0;
    padding-top: 70px;
  }

  .main-content.sidebar-collapsed {
    margin-left: 0;
  }
}
</style>
