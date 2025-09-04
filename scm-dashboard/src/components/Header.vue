<template>
  <header class="app-header">
    <div class="header-left">
      <!-- 로고/제목 -->
      <div class="logo-section">
        <h1 class="app-title">🏗️ SCM Dashboard</h1>
      </div>
    </div>

    <div class="header-center">
      <!-- 현재 페이지 정보 -->
      <div class="current-page">
        <span class="page-icon">{{ currentPageIcon }}</span>
        <span class="page-title">{{ currentPageTitle }}</span>
      </div>
    </div>

    <div class="header-right">
      <!-- 알림 버튼 -->
      <button class="header-btn notification-btn" title="알림">
        <span class="icon">🔔</span>
        <span class="badge" v-if="notificationCount > 0">{{ notificationCount }}</span>
      </button>

      <!-- API 상태 표시 -->
      <div class="api-status" :class="{ 'connected': apiConnected, 'disconnected': !apiConnected }">
        <span class="status-icon">{{ apiConnected ? '🟢' : '🔴' }}</span>
        <span class="status-text">{{ apiConnected ? 'API 연결됨' : 'API 연결 안됨' }}</span>
      </div>

      <!-- 사용자 정보 -->
      <div class="user-section">
        <div class="user-avatar">👤</div>
        <span class="username">Admin</span>
      </div>
    </div>
  </header>
</template>

<script setup lang="ts">
import { computed, ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { checkApiHealth } from '@/services/api'

const route = useRoute()

// API 연결 상태
const apiConnected = ref(false)
const notificationCount = ref(3) // 예시 알림 개수

// 현재 페이지 정보
const currentPageTitle = computed(() => {
  const routeNames: Record<string, string> = {
    'Dashboard': '대시보드',
    'Projects': '프로젝트 관리',
    'Builds': '빌드 관리',
    'ApiTest': 'API 테스트'
  }
  return routeNames[route.name as string] || '대시보드'
})

const currentPageIcon = computed(() => {
  const routeIcons: Record<string, string> = {
    'Dashboard': '📊',
    'Projects': '📁',
    'Builds': '🔨',
    'ApiTest': '🧪'
  }
  return routeIcons[route.name as string] || '📊'
})

// API 상태 확인
const checkApiStatus = async () => {
  try {
    apiConnected.value = await checkApiHealth()
  } catch (error) {
    apiConnected.value = false
  }
}

// 컴포넌트 마운트 시 API 상태 확인
onMounted(async () => {
  await checkApiStatus()

  // 5분마다 API 상태 확인
  setInterval(checkApiStatus, 300000)
})
</script>

<style scoped>
.app-header {
  height: 60px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 24px;
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 1000;
}

.header-left, .header-center, .header-right {
  display: flex;
  align-items: center;
  gap: 16px;
}

.logo-section {
  display: flex;
  align-items: center;
}

.app-title {
  color: white;
  font-size: 20px;
  font-weight: 700;
  margin: 0;
  text-shadow: 0 1px 3px rgba(0, 0, 0, 0.3);
}

.current-page {
  display: flex;
  align-items: center;
  gap: 8px;
  color: white;
  font-size: 16px;
  font-weight: 500;
}

.page-icon {
  font-size: 18px;
}

.header-btn {
  background: rgba(255, 255, 255, 0.1);
  border: 1px solid rgba(255, 255, 255, 0.2);
  color: white;
  padding: 8px 12px;
  border-radius: 6px;
  cursor: pointer;
  transition: all 0.2s ease;
  position: relative;
}

.header-btn:hover {
  background: rgba(255, 255, 255, 0.2);
}

.notification-btn .badge {
  position: absolute;
  top: -5px;
  right: -5px;
  background: #ff4444;
  color: white;
  border-radius: 50%;
  width: 18px;
  height: 18px;
  font-size: 11px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: bold;
}

.api-status {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 8px 16px;
  border-radius: 24px;
  font-size: 13px;
  font-weight: 500;
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  backdrop-filter: blur(10px);
}

.api-status.connected {
  background: rgba(76, 175, 80, 0.2);
  color: #ffffff;
  border: 1px solid rgba(76, 175, 80, 0.3);
}

.api-status.disconnected {
  background: rgba(244, 67, 54, 0.2);
  color: #ffffff;
  border: 1px solid rgba(244, 67, 54, 0.3);
}

.user-section {
  display: flex;
  align-items: center;
  gap: 8px;
  color: white;
  background: rgba(255, 255, 255, 0.15);
  padding: 6px 12px;
  border-radius: 20px;
  border: 1px solid rgba(255, 255, 255, 0.3);
}

.user-avatar {
  width: 28px;
  height: 28px;
  background: rgba(255, 255, 255, 0.2);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 14px;
}

.username {
  font-size: 14px;
  font-weight: 500;
}

/* 반응형 디자인 */
@media (max-width: 768px) {
  .app-header {
    padding: 0 16px;
  }

  .header-center {
    display: none;
  }

  .app-title {
    font-size: 18px;
  }

  .api-status .status-text {
    display: none;
  }

  .username {
    display: none;
  }
}
</style>
